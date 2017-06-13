package olioGUI

import scala.swing._
import scala.io.Source
import olioIO.WeaponIO
import scala.swing.event._
import main._

class WeaponPanel(olio: Olio, index: Int) extends GridPanel(1, 3) {
  
  private var weapon = olio.weapons(index)
  
  val reader = Source.fromFile("data/weapons.txt").reader()
  
  val weaponList = WeaponIO.loadNames(reader)
  
  val dropMenu = new ComboBox(this.weaponList)
  val damageLabel = new Label("")
  val rangeLabel = new Label("")
  this.update()
  this.dropMenu.selection.item = weapon.name
  
  this.contents += (dropMenu, damageLabel, rangeLabel)
  
  this.listenTo(dropMenu.selection)
  
  reactions += {
    case SelectionChanged(dropMenu) =>
      weapon = new Weapon(this.dropMenu.selection.item)
      olio.weapons(index) = weapon
      this.update()
  }
  
  def update() = {
    damageLabel.text = weapon.damageText(olio)
    rangeLabel.text = weapon.range
  }
  
}