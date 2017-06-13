package olioGUI

import scala.swing._
import scala.io.Source
import olioIO.WeaponIO

class WeaponPanel extends GridPanel(1, 3) {
  
  val reader = Source.fromFile("data/weapons.txt").reader()
  
  val weaponList = WeaponIO.loadNames(reader)
  
  
}