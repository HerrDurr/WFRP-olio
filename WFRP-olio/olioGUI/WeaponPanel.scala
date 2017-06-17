package olioGUI

import scala.swing._
import scala.io.Source
import scala.io.Codec
import java.nio.charset.CodingErrorAction
import olioIO.DataIO
import scala.swing.event._
import main._

class WeaponPanel(olio: Olio, index: Int) extends GridPanel(1, 3) {
  
  private var weapon = olio.weapons(index)
  
  private val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
  private val reader = Source.fromFile("data/weapons.txt")(decoder).reader()
  val weaponList = DataIO.loadNames(reader)
  
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