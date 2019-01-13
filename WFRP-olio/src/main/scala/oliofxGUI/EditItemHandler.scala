package oliofxGUI

import scalafx.scene.control.{TextField, ComboBox, CheckBox, ListView, ToggleButton, Button, MenuButton,
  TitledPane, CheckMenuItem}
import olioIO.SchemaWFRP.{WeaponQuality, Item, Availability, WeaponMelee, WeaponRanged,
  Craftsmanship, Talent}
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
  private val fCurrentItem: ObjectProperty[Option[Item]] = ObjectProperty(this, "item", None)
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
  DataHelperWFRP.getAllAvailabilities.foreach(comboAvailability += Some(_))
  comboAvailability.getSelectionModel.selectFirst()
  
  /*
   * Init qualities: menus and mappings!
   */
  getAllWeaponQualities.foreach( createMenuItemsForQuality(_) )
  
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
  
  
  private def resetItem(aItem: Option[Item]) = {
    this.fCurrentItem.value = aItem
    this.fCurrentWeaponMelee.value = None
    this.fCurrentWeaponRanged = None
    if (aItem.isDefined)
    {
      this.fCurrentWeaponMelee.value = aItem.get.weaponMelee
      this.fCurrentWeaponRanged = aItem.get.weaponRanged
    }
    cbIsRanged.setSelected(this.fCurrentWeaponRanged.isDefined)
    cbIsMelee.setSelected(this.fCurrentWeaponMelee.value.isDefined)
  }
  
  
  def editItem(aItem: Item) = {
    this.resetItem( Some(aItem) )
    
    editName.text = aItem.name.value
    editCost.text = aItem.cost.getOrElse( Item.Cost("-") ).value
    editEnc.text = aItem.encumbrance.value.toString()
    if (aItem.availability.isDefined)
      comboAvailability.getSelectionModel.select( Some( byId(aItem.availability.getOrElse(Availability.avgId)) ) )
    cbIsMelee.setSelected(this.fCurrentWeaponMelee.value.isDefined)
    cbIsRanged.setSelected(this.fCurrentWeaponRanged.isDefined)
    
    if (cbIsMelee.isSelected())
    {
      loadMeleeWeapon()
    }
    
    /*comboCraftsmanship.
                         private val cbMeleeSB: CheckBox,
                         private val editMeleeDmg: TextField,
                         private val listMeleeQualities: ListView[WeaponQuality.Name],
                         private val comboMeleeGroup: ComboBox[String],
                         private val toggleRangedDmg: ToggleButton,
                         private val cbRangedSB: CheckBox,
                         private val editRangedDmg: TextField,
                         private val editRangeShort: TextField,
                         private val editRangeLong: TextField,
                         private val comboReload: ComboBox[String],
                         private val listRangedQualities: ListView[WeaponQuality.Name],
                         private val comboAmmunition: ComboBox[Item],
                         private val comboRangedGroup
                         * 
                         */
  }
  
  /**
   * Creates a WeaponMelee object if current is None. Sets the melee control values according to fCurrentWeaponMelee.
   */
  private def loadMeleeWeapon() = {
    import WeaponMelee._
    if (this.fCurrentWeaponMelee.value.isEmpty)
    {
      this.fCurrentWeaponMelee.value = Some( WeaponMelee.createNew(this.fCurrentItem.value.getOrElse(Item.createNew)) )
    }
    val weap = this.fCurrentWeaponMelee.value.get
    
    editMeleeDmg.text = weap.damageModifier.getOrElse(DamageMod(0)).value.toString
    getAllWeaponQualities.foreach( q => fMeleeQualityMapping.get(q).get.selected = ( weap.qualities.contains(q) ) )
    if (weap.weaponGroupTalentId.isDefined)
      comboMeleeGroup.getSelectionModel.select( weaponGroupTalents.find( _.id == weap.weaponGroupTalentId.get ).get )
    else
      comboMeleeGroup.getSelectionModel.clearSelection()
  }
  
  /**
   * Save Item, WeaponMelee, WeaponRanged.
   */
  def onSave(): Unit = {
    println("Totally saved this!")
    if (this.fCurrentItem.value.isDefined)
      println(this.fCurrentItem.value.get.toString())
  }
  

  def setStage(stage: Stage) = fStage = Some(stage)
  
  private def onChangedName() = {
    fCurrentItem.value = Some( lName.set(fCurrentItem.value.getOrElse(Item.createNew))( Name(editName.text.value) ) )
  }
  
  private def onChangedEnc() = {
    val newEnc = editEnc.text.value
    if ( newEnc.isShort )
      fCurrentItem.value = Some( lEnc.set(fCurrentItem.value.getOrElse(Item.createNew))( Encumbrance(newEnc.toShort) ) )
    else
    {
      new Alert(AlertType.Error, "Encumbrance needs to be a numeric value!").showAndWait()
      editEnc.text.value = fCurrentItem.value.getOrElse(Item.createNew).encumbrance.value.toString
    }
  }
    
  private def onChangeCraftsmanship(observable: jfxbv.ObservableValue[_ <: Craftsmanship.Craftsmanship], oldValue: Craftsmanship.Craftsmanship, newValue: Craftsmanship.Craftsmanship) = {
    fCurrentItem.value = Some( lCraft.set(fCurrentItem.value.getOrElse(Item.createNew))(newValue) )
  }
  
  private def onChangedCost = {
    val newCost = editCost.text.value
    fCurrentItem.value = Some( lCost.set(fCurrentItem.value.getOrElse(Item.createNew))( Some( Cost(newCost) ) ) )
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
    if (this.fCurrentItem.value.isEmpty)
    {
      this.fCurrentItem.value = Some(Item.createNew)
    }
    this.fCurrentItem.value.get
  }
  
  def getCurrentWeaponMelee: WeaponMelee = {
    if (this.fCurrentWeaponMelee.value.isEmpty)
    {
      this.fCurrentWeaponMelee.value = Some( WeaponMelee.createNew(getCurrentItem) )
    }
    this.fCurrentWeaponMelee.value.get
  }
  
}