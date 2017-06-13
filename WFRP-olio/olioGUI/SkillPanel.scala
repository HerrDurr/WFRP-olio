package olioGUI

import main._
import scala.swing._
import collection.mutable.ArrayBuffer
import javax.swing.table.DefaultTableModel

class SkillPanel(olio: Olio) extends GridPanel(3,1) {
  
  val weapons = olio.weapons
  val weaponData = ArrayBuffer(Array("Name", "Dmg", "Rng"), Array("Test", "-", "-"))
  //val weaponTable = new Table()
  
  val weaponTable = new Table(weaponData.tail.toArray.map(_.toArray[Any]), weaponData.head) {
    model = new DefaultTableModel
  }
  
  val tableModel  = weaponTable.model.asInstanceOf[DefaultTableModel]
  
  val weaponScroller = new ScrollPane(weaponTable)
  
  this.contents += weaponScroller
  
  def update() = {
    weapons.foreach {
      weapon =>
        tableModel.addRow(Array[java.lang.Object] (weapon.name, weapon.damage(olio.attributes.strengthBonus).getOrElse('-').toString(), weapon.range))
        //weaponData += Array(weapon.name, weapon.damage(olio.attributes.strengthBonus).getOrElse('-').toString(), weapon.range)
        
        //weaponTable = new Table(weaponData.tail.toArray.map(_.toArray[Any]), weaponData.head)
        
    }
  }
  
  /* uudempi
  def update() = {
    weapons.foreach {
      weapon =>
        val i = weapons.indexOf(weapon)
        weaponTable.update(i, 0, weapon.name)
        weaponTable.update(i, 1, weapon.group)
        weaponTable.update(i, 2, weapon.damage(olio.attributes.strengthBonus).getOrElse("-"))
        weaponTable.update(i, 3, weapon.range)
        weaponTable.update(i, 4, weapon.qualities)
    }
  }
  * 
  */
  
  /* vanhempi
  def update() = {
    olio.weapons.foreach(weapon => weaponTable.contents += new FlowPanel {
      contents += new Label( weapon.name + ": " + weapon.group + ", " +
                            weapon.damage(olio.attributes.strengthBonus).getOrElse("-").toString() )
    })
  }
  * 
  */
  
  update()
  
}