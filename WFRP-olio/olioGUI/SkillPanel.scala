package olioGUI

import main._
import scala.swing._

class SkillPanel(olio: Olio) extends GridPanel(3,1) {
  
  val weapons = olio.weapons
  
  val weaponTable = new Table(3, 5) {
    val scroller = new ScrollBar
    scroller.orientation = Orientation.Vertical
  }
  
  this.contents += weaponTable
  
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
  
  /*
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