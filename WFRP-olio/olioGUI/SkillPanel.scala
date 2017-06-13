package olioGUI

import main._
import scala.swing._
import data._
import scala.io.Source
import olioIO.WeaponIO
//import javax.swing.table.DefaultTableModel
//import collection.mutable.ArrayBuffer

class SkillPanel(olio: Olio) extends GridPanel(3,1) {
  
  val weapons = olio.weapons
  
  val reader = Source.fromFile("data/weapons.txt").reader()
  
  val weaponList = WeaponIO.loadNames(reader)
  
  
  
  
  
  
  
  
  //val weaponTable = new Table()
  
  /*
   * 
   * val weaponData = Array(Array("Name", "Dmg", "Rng"), Array("Test", "-", "-"))
  private var weaponTable = new Table(weaponData.tail.toArray.map(_.toArray[Any]), weaponData.head)
  
  private var weaponScroller = new ScrollPane(weaponTable)
  
  this.contents += weaponScroller
  
  def update() = {
    val weaponArray: ArrayBuffer[Array[String]] = ArrayBuffer(Array("Name", "Dmg", "Rng"))
    weapons.foreach {
      weapon =>
        //tableModel.addRow(Array[java.lang.Object] (weapon.name, weapon.damage(olio.attributes.strengthBonus).getOrElse('-').toString(), weapon.range))
        //weaponData += Array(weapon.name, weapon.damage(olio.attributes.strengthBonus).getOrElse('-').toString(), weapon.range)
        weaponArray += (Array(weapon.name, weapon.damage(olio.attributes.strengthBonus).getOrElse('-').toString(), weapon.range))
        //weaponTable = new Table(weaponData.tail.toArray.map(_.toArray[Any]), weaponData.head)
        
    }
    weaponTable = new Table(weaponArray.tail.toArray.map(_.toArray[Any]), weaponData.head)
    weaponScroller = new ScrollPane(weaponTable)
    repaint()
  }
  * 
  */
  
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
  
  
}