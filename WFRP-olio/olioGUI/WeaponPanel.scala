package olioGUI

import scala.swing._
import scala.io.Source
import olioIO.WeaponIO
import scala.swing.event._
import main.Weapon

class WeaponPanel extends GridPanel(1, 3) {
  
  val reader = Source.fromFile("data/weapons.txt").reader()
  
  val weaponList = WeaponIO.loadNames(reader)
  
  val dropMenu = new ComboBox(this.weaponList)
  val damageLabel = new Label("-")
  val rangeLabel = new Label("-")
  
  this.contents += dropMenu
  
  this.listenTo(dropMenu.selection)
  
  
  reactions += {
    case SelectionChanged(dropMenu) =>
      val choice = new Weapon(this.dropMenu.selection.item)
      
  }
  
}