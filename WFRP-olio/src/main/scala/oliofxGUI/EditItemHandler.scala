package oliofxGUI

import scalafx.scene.control.{TextField, ComboBox, CheckBox, ListView, ToggleButton, Button, MenuButton,
  TitledPane, CheckMenuItem}
import olioIO.SchemaWFRP._
import olioIO.SchemaWFRP.Item._
import olioIO.DataHelperWFRP
import olioIO.DataHelperWFRP._
import scalafxml.core.macros.sfxml
import scalafxml.core.FXMLLoader
import scalafxml.core.DependenciesByType
//import scalafx.scene.Scene
import javafx.{scene => jfxs}
//import scalafx.stage.Stage
import javafx.scene.Scene
import javafx.beans.{value => jfxbv}
import scalafx.beans.binding.Bindings._
import scalafx.beans.property.ObjectProperty
import scalafx.beans.value.ObservableValue
import olioIO.SchemaWFRP.Craftsmanship
import commonLib.Util._
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import java.lang.Boolean
import scalafx.scene.input.InputMethodEvent
import dataUI.UtilsUI._
import scala.collection.mutable.Map
import dataWFRP.Resources._
import scalafx.stage.Stage
import dataElements.CachableObjects._
import dataElements.TStorageRow
import dataElements.TStorageRow

//import javafx.beans.value.ObservableValue

trait EditItemInterface {
  
  def editItem(aItem: Item)
  
  def setStage(stage: Stage)
  
}

@sfxml
class EditItemHandler(
                         private val editName: TextField,
                         private val comboCraftsmanship: ComboBox[Craftsmanship.Craftsmanship],
                         private val editCost: TextField,
                         private val editEnc: TextField,
                         private val comboAvailability: ComboBox[Option[Availability]],
                         private val cbIsMelee: CheckBox,
                         private val cbIsRanged: CheckBox,
                         private val paneMelee: TitledPane,
                         private val paneRanged: TitledPane,
                         //private val cbMeleeSB: CheckBox,
                         private val editMeleeDmg: TextField,
                         private val menuBtnMeleeQualities: MenuButton,
                         //private val listMeleeQualities: ListView[WeaponQuality.Name],
                         private val comboMeleeGroup: ComboBox[Talent],
                         private val toggleRangedDmg: ToggleButton,
                         private val cbRangedSB: CheckBox,
                         private val editRangedDmg: TextField,
                         private val editRangeShort: TextField,
                         private val editRangeLong: TextField,
                         private val comboReload: ComboBox[String],
                         private val menuBtnRangedQualities: MenuButton,
                         //private val listRangedQualities: ListView[WeaponQuality.Name],
                         private val comboAmmunition: ComboBox[Item],
                         private val comboRangedGroup: ComboBox[Talent],
                         private val btnSave: Button,
                         private val btnSaveAs: Button) extends EditItemInterface {
  
  /*
   * Private stuff, e.g. current states.
   */
  //private var fCurrentItem: Option[Item] = None
  private var fCurrentItem: Option[TStorageRow[Item]] = None //ObjectProperty[Option[Item]] = ObjectProperty(this, "item", None)
  private var fCurrentWeaponMelee: ObjectProperty[Option[WeaponMelee]] = ObjectProperty(this, "melee", None)
  private var fCurrentWeaponRanged: Option[WeaponRanged] = None
  
  private val fMeleeQualityMapping : Map[WeaponQuality, CheckMenuItem] = Map()
  private val fRangedQualityMapping : Map[WeaponQuality, CheckMenuItem] = Map()
  
  private var fStage : Option[Stage] = None
  
  /*
   * init craftsmanship
   */
  for (c <- Craftsmanship.enums) {
    comboCraftsmanship += Craftsmanship.byEnumOrThrow(c._1)
  }
  comboCraftsmanship.getSelectionModel.select(Craftsmanship.Normal)
  
  /*
   * init availability
   * TODO: prettify!
   */
  comboAvailability += None
  //DataHelperWFRP.getAllAvailabilities.foreach(comboAvailability += Some(_))
  Availability.cache.getRows.map(_.data).foreach( comboAvailability += Some(_) )
  comboAvailability.getSelectionModel.selectFirst()
  
  /*
   * Init qualities: menus and mappings!
   */
  //getAllWeaponQualities.foreach( createMenuItemsForQuality(_) )
  WeaponQuality.cache.getRows.map(_.data).foreach( createMenuItemsForQuality(_) )
  
  /*
   * Init WeaponGroups
   * TODO: prettify!
   */
  for (talent <- weaponGroupTalents) {
    comboMeleeGroup += talent
    comboRangedGroup += talent
  }
  
  /*
   * Initialize listeners
   */
  initBindings
  
  
  /**
   * Bindings - listeners - event handlers, what have you.
   * Because of the pure shittiness of ScalaFx, I can't find a way to set for a scalafx property: onChange = ... .
   * Thus, we must journey on using the JavaFx delegates!
   * For edits and such, add listeners for the focused property so we don't fire the event on every character typed.
   * Combos can work when changed, I think. 
   */
  private def initBindings = {
    
    // Basic
    this.editName.addFocusLostEvent(onChangedName)
    
    /*
    val craftsListener = new jfxbv.ChangeListener[Craftsmanship.Craftsmanship] {
      def changed(observable: jfxbv.ObservableValue[_ <: Craftsmanship.Craftsmanship], oldValue: Craftsmanship.Craftsmanship, newValue: Craftsmanship.Craftsmanship) {
        fCurrentItem.value = Some( lCraft.set(fCurrentItem.value.getOrElse(Item.createNew))(newValue) )
      }
    }
    this.comboCraftsmanship.value.delegate.addListener(craftsListener)
    */
    this.comboCraftsmanship.addOnChange(onChangeCraftsmanship)
    this.editEnc.addFocusLostEvent(onChangedEnc)
    this.editCost.addFocusLostEvent(onChangedCost)
    this.comboAvailability.addOnChange(onChangeAvailability)
    
    // Melee
    this.cbIsMelee.addOnCheck(onChangeIsMelee)
    this.editMeleeDmg.addFocusLostEvent(onChangedMeleeDmg)
    
    // Ranged
  }
  
  
  /**
   * Creates CheckMenuItems for both melee and ranged MenuButtons.
   * Adds the Quality and the items to their respective mappings.
   */
  private def createMenuItemsForQuality(aVal : WeaponQuality) = {
    val aMeleeItem = new CheckMenuItem(aVal.name.value)
    // the contextMenu is javaFx's one... *rolls eyes*
    menuBtnMeleeQualities.contextMenu.value.getItems.add(aMeleeItem)
    fMeleeQualityMapping += (aVal -> aMeleeItem)
    
    val aRangedItem = new CheckMenuItem(aVal.name.value)
    menuBtnRangedQualities.contextMenu.value.getItems.add(aRangedItem)
    fRangedQualityMapping += (aVal -> aRangedItem)
  }
  
  // TODO: check
  private def resetItem(aItem: Item) = {
    this.fCurrentWeaponMelee.value = None
    this.fCurrentWeaponRanged = None
    //if (aItem.isDefined)
    //{
      this.fCurrentItem = Item.cache.find(aItem)
      this.fCurrentWeaponMelee.value = aItem.weaponMelee
      this.fCurrentWeaponRanged = aItem.weaponRanged
    //}
    //else
      //this.fCurrentItem = Some(Item.cache.getCachedRow( Item.createNew ))
    cbIsRanged.setSelected(this.fCurrentWeaponRanged.isDefined)
    cbIsMelee.setSelected(this.fCurrentWeaponMelee.value.isDefined)
  }
  
  
  def editItem(aItem: Item) = {
    this.resetItem( aItem )
    
    editName.text = aItem.name.value
    editCost.text = aItem.cost.getOrElse( Item.Cost("-") ).value
    editEnc.text = aItem.encumbrance.value.toString()
    
    comboCraftsmanship.getSelectionModel.select(aItem.craftsmanship)
    import Availability._
    if (aItem.availability.isDefined)
      comboAvailability.getSelectionModel.select( byTag( aItem.availability.map(_.value).getOrElse(avgId.value) ).map(_.asInstanceOf[Availability]) )
    cbIsMelee.setSelected(this.fCurrentWeaponMelee.value.isDefined)
    cbIsRanged.setSelected(this.fCurrentWeaponRanged.isDefined)
    
    if (cbIsMelee.isSelected())
    {
      loadMeleeWeapon()
    }
    
  }
  
  /**
   * Creates a WeaponMelee object if current is None. Sets the melee control values according to fCurrentWeaponMelee.
   */
  private def loadMeleeWeapon() = {
    import WeaponMelee._
    if (this.fCurrentWeaponMelee.value.isEmpty && this.fCurrentItem.isDefined)
    {
      this.fCurrentWeaponMelee.value = Some( WeaponMelee.createNew(this.fCurrentItem.map(_.data).get) )
    }
    if (this.fCurrentWeaponMelee.value.isDefined)
    {
      val weap = this.fCurrentWeaponMelee.value.get
        
      editMeleeDmg.text = weap.damageModifier.getOrElse(DamageMod(0)).value.toString
      getAllWeaponQualities.foreach( q => fMeleeQualityMapping.get(q).get.selected = ( weap.qualities.contains(q) ) )
      if (weap.weaponGroupTalentId.isDefined)
        comboMeleeGroup.getSelectionModel.select( weaponGroupTalents.find( _.id == weap.weaponGroupTalentId.get ).get )
      else
        comboMeleeGroup.getSelectionModel.clearSelection()
    }
  }
  
  /**
   * Save Item, WeaponMelee, WeaponRanged.
   */
  def onSave(): Unit = {
    if (this.fCurrentItem.isDefined)
    {
      this.fCurrentItem.get.save()
      println("Totally saved this!")
      println(this.fCurrentItem.get.data.toString())
    }
  }
  

  def setStage(stage: Stage) = fStage = Some(stage)
  
  private def onChangeAvailability(
      observable: jfxbv.ObservableValue[_ <: Option[Availability]],
      oldValue: Option[Availability],
      newValue: Option[Availability]
      ) = {
    if (this.fCurrentItem.isDefined)
      fCurrentItem.get.update( lAvail.set(fCurrentItem.get.data)( newValue.map(_.idTag) ) )
  }
  
  private def onChangedName() = {
    if (this.fCurrentItem.isDefined)
      fCurrentItem.get.update( lName.set(fCurrentItem.get.data)( Name(editName.text.value) ) )
  }
  
  private def onChangedEnc() = {
    if (this.fCurrentItem.isDefined)
    {
      val newEnc = editEnc.text.value
      if ( newEnc.isShort )
        fCurrentItem.get.update( lEnc.set(fCurrentItem.get.data)( Encumbrance(newEnc.toShort) ) )
      else
      {
        new Alert(AlertType.Error, "Encumbrance needs to be a numeric value!").showAndWait()
        editEnc.text.value = fCurrentItem.map(_.data).getOrElse(Item.createNew).encumbrance.value.toString
      }
    }
  }
    
  private def onChangeCraftsmanship(observable: jfxbv.ObservableValue[_ <: Craftsmanship.Craftsmanship], oldValue: Craftsmanship.Craftsmanship, newValue: Craftsmanship.Craftsmanship) = {
    if (this.fCurrentItem.isDefined)
      fCurrentItem.get.update( lCraft.set(fCurrentItem.get.data)(newValue) )
  }
  
  private def onChangedCost = {
    val newCost = editCost.text.value
    if (this.fCurrentItem.isDefined)
      fCurrentItem.get.update( lCost.set(fCurrentItem.get.data)( Some( Cost(newCost) ) ) )
  }
  
  private def onChangeIsMelee(aChecked: Boolean) = {
    paneMelee.disable = !aChecked
    
    if (aChecked)
    {
      this.loadMeleeWeapon()
    }
  }
  
  private def onChangedMeleeDmg = {
    import WeaponMelee._
    val newDmg = editMeleeDmg.text.value
    if ( newDmg.isShort )
      fCurrentWeaponMelee.value = Some( lDamageModifier.set(getCurrentWeaponMelee)( Some( DamageMod(newDmg.toShort) ) ) )
    else
    {
      new Alert(AlertType.Error, "The damage modifier needs to be a numeric value!").showAndWait()
      editMeleeDmg.text.value = getCurrentWeaponMelee.damageModifier.getOrElse(DamageMod(0)).value.toString
    }
  }
  
  def getCurrentItem: Item = {
    if (this.fCurrentItem.isEmpty)
    {
      this.fCurrentItem = Some( TStorageRow.createNewRow( Item.createNew, Item.cache ) )
    }
    this.fCurrentItem.get.data
  }
  
  def getCurrentWeaponMelee: WeaponMelee = {
    if (this.fCurrentWeaponMelee.value.isEmpty)
    {
      this.fCurrentWeaponMelee.value = Some( WeaponMelee.createNew(getCurrentItem) )
    }
    this.fCurrentWeaponMelee.value.get
  }
  
}