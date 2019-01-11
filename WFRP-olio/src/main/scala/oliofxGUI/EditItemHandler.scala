package oliofxGUI

import scalafx.scene.control.{TextField, ComboBox, CheckBox, ListView, ToggleButton, Button}
import olioIO.SchemaWFRP.{WeaponQuality, Item, Availability, WeaponMelee, WeaponRanged,
  Craftsmanship}
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
  private var currentItem: Option[Item] = None
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
  
  
  def editItem(aItem: Item) = {
    this.currentItem = Some(aItem)
    this.currentWeaponMelee = weaponMelee(aItem.id)
    this.currentWeaponRanged = weaponRanged(aItem.id)
    
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
  }
  
  
}