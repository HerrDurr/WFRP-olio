package olioGUI

import main._
import scala.swing._
import data._
//import javax.swing.table.DefaultTableModel
//import collection.mutable.ArrayBuffer

class RightPanel(olio: Olio) extends GridPanel(3,1) {
  
  
  val weapon1 = new WeaponPanel(olio, 0)
  val weapon2 = new WeaponPanel(olio, 1)
  val weapon3 = new WeaponPanel(olio, 2)
  
  val weaponGrid = new GridPanel(4, 1) {
    val headers = new GridPanel(1,3) {
      contents += (new Label("Weapon"), new Label("Damage"), new Label("Range"))
    }
    contents += (headers, weapon1, weapon2, weapon3)
  }
  
  
  val skill1 = new Label("-")
  val skill2 = new Label("-")
  val skill3 = new Label("-")
  val skill4 = new Label("-")
  val skillLabels = Vector(skill1, skill2, skill3, skill4)
  val skillGrid = new GridPanel(4,1) {
    skillLabels.foreach(contents += _)
  }
  
  this.contents += (weaponGrid, skillGrid)
  
  /**
   * Updates all fields in this Panel.
   */
  def update() = {
    this.weaponGrid.contents.tail.foreach(_.asInstanceOf[WeaponPanel].update())
    val topSkills = olio.skills.sortBy(_.skillLevel).take(4)
    skillLabels.foreach( x => x.text = topSkills(skillLabels.indexOf(x)).name )
  }
  
  
  
  
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