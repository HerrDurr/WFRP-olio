package olioGUI

import main._
import scala.swing._

class SkillPanel(olio: Olio) extends GridPanel(3,1) {
  
  
  val weaponPanel = new BoxPanel(Orientation.Vertical) {
    olio.weapons.foreach(weapon => contents += new FlowPanel {
      contents += new Label( weapon.name + ", " + weapon.group + ", " +
                            weapon.damage(olio.attributes.strengthBonus).getOrElse("-").toString() )
    })
  }
  
  this.contents += weaponPanel
  
}