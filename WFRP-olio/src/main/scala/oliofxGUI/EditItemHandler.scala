package oliofxGUI

import scalafx.scene.control.{TextField, ComboBox, CheckBox, ListView, ToggleButton, Button}
import olioIO.SchemaWFRP.{WeaponQuality, Item, Availability, WeaponMelee, WeaponRanged,
  Craftsmanship}
import olioIO.SchemaWFRP.Item._
import olioIO.DataHelperWFRP
import olioIO.DataHelperWFRP._
import scalafxml.core.macros.sfxml
import scalafxml.core.FXMLLoader
import scalafxml.core.FXMLLoader
import scalafxml.core.DependenciesByType
//import scalafx.scene.Scene
import javafx.{scene => jfxs}
//import scalafx.stage.Stage
import javafx.scene.Scene
import javafx.stage.Stage
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

//import javafx.beans.value.ObservableValue

trait EditItemInterface {
  
  def editItem(aItem: Item)
  
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
                         private val comboRangedGroup: ComboBox[String],
                         private val btnSave: Button,
                         private val btnSaveAs: Button) extends EditItemInterface {
  
  /*
   * Private stuff, e.g. current states.
   */
  //private var currentItem: Option[Item] = None
  private val currentItem: ObjectProperty[Option[Item]] = ObjectProperty(this, "item", None)
  private var currentWeaponMelee: Option[WeaponMelee] = None
  private var currentWeaponRanged: Option[WeaponRanged] = None
  
  
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
  def initBindings = {
    
    // Name
    this.editName.addFocusLostEvent(onChangedName)
    
    // Craftsmanship
    /*
    val craftsListener = new jfxbv.ChangeListener[Craftsmanship.Craftsmanship] {
      def changed(observable: jfxbv.ObservableValue[_ <: Craftsmanship.Craftsmanship], oldValue: Craftsmanship.Craftsmanship, newValue: Craftsmanship.Craftsmanship) {
        currentItem.value = Some( lCraft.set(currentItem.value.getOrElse(Item.createNew))(newValue) )
      }
    }
    this.comboCraftsmanship.value.delegate.addListener(craftsListener)
    */
    this.comboCraftsmanship.addOnChange(onChangeCraftsmanship)
    
    // Encumbrance
    this.editEnc.addFocusLostEvent(onChangedEnc)
    
    // Cost
    /*val costListener = new jfxbv.ChangeListener[Boolean] {
      def changed(observable: jfxbv.ObservableValue[_ <: Boolean], oldValue: Boolean, newValue: Boolean) {
        if (!newValue)
        {
          val newCost = editCost.text.value
          currentItem.value = Some( lCost.set(currentItem.value.getOrElse(Item.createNew))( Some( Cost(newCost) ) ) )
        }
      }
    }
    this.editCost.focused.delegate.addListener(costListener)*/
    this.editCost.addFocusLostEvent(onChangedCost)
  }
  
  
  
  def resetItem(aItem: Option[Item]) = {
    this.currentItem.value = aItem
    this.currentWeaponMelee = None
    this.currentWeaponRanged = None
    if (aItem.isDefined)
    {
      this.currentWeaponMelee = weaponMelee(aItem.get.id)
      this.currentWeaponRanged = weaponRanged(aItem.get.id)
    }
  }
  
  
  def editItem(aItem: Item) = {
    this.resetItem( Some(aItem) )
    
    editName.text = aItem.name.value
    editCost.text = aItem.cost.getOrElse( Item.Cost("-") ).value
    editEnc.text = aItem.encumbrance.value.toString()
    if (aItem.availability.isDefined)
      comboAvailability.getSelectionModel.select( Some( byId(aItem.availability.getOrElse(Availability.avgId)) ) )
    cbIsMelee.setSelected(this.currentWeaponMelee.isDefined)
    cbIsRanged.setSelected(this.currentWeaponRanged.isDefined)
    
    if (cbIsMelee.isSelected())
    {
      // not very much implemented yet
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
  
  def onSave(): Unit = {
    println("Totally saved this!")
    if (this.currentItem.value.isDefined)
      println(this.currentItem.value.get.toString())
  }
  
  def onChangedName = {
    currentItem.value = Some( lName.set(currentItem.value.getOrElse(Item.createNew))( Name(editName.text.value) ) )
  }
  
  def onChangedEnc = {
    val newEnc = editEnc.text.value
    if ( newEnc.isShort )
      currentItem.value = Some( lEnc.set(currentItem.value.getOrElse(Item.createNew))( Encumbrance(newEnc.toShort) ) )
    else
    {
      new Alert(AlertType.Error, "Encumbrance needs to be a numeric value!").showAndWait()
      editEnc.text.value = currentItem.value.getOrElse(Item.createNew).encumbrance.value.toString
    }
  }
    
  def onChangeCraftsmanship(observable: jfxbv.ObservableValue[_ <: Craftsmanship.Craftsmanship], oldValue: Craftsmanship.Craftsmanship, newValue: Craftsmanship.Craftsmanship) = {
    currentItem.value = Some( lCraft.set(currentItem.value.getOrElse(Item.createNew))(newValue) )
  }
  
  def onChangedCost = {
    val newCost = editCost.text.value
    currentItem.value = Some( lCost.set(currentItem.value.getOrElse(Item.createNew))( Some( Cost(newCost) ) ) )
  }
  
}