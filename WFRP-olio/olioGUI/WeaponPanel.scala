package olioGUI

import scala.swing._
import scala.io.Source
import scala.io.Codec
import java.nio.charset.CodingErrorAction
import olioIO.DataIO
import scala.swing.event._
import main._

class WeaponPanel(olioPanel: OlioPanel, index: Int) extends BoxPanel(Orientation.Horizontal) {
  
  val olio = olioPanel.olio
  private var weapon = olio.weapons(index)
  
  private val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
  private val file = Source.fromFile("data/weapons.txt")(decoder)
  private var weaponList: Vector[String] = Vector()
  try {
   weaponList = DataIO.loadNames(file.reader())
  } finally {
    file.close()
  }
  
  
  
  val dropMenu = new ComboBox(this.weaponList)
  val damageLabel = new Label("Dmg: ")
  damageLabel.preferredSize = new Dimension(50, 28)
  val rangeLabel = new Label("Rng: ")
  rangeLabel.preferredSize = new Dimension(50, 28)
  this.update()
  this.dropMenu.selection.item = weapon.name
  
  this.contents += (dropMenu, damageLabel, rangeLabel)
  this.contents.foreach(_.font = olioPanel.whFont.deriveFont(16f))
  
  this.listenTo(dropMenu.selection)
  
  reactions += {
    case SelectionChanged(dropMenu) =>
      weapon = new Weapon(this.dropMenu.selection.item)
      olio.weapons(index) = weapon
      this.update()
  }
  
  def update() = {
    damageLabel.text = "Dmg: " + weapon.damageText(olio)
    rangeLabel.text = "Rng: " + weapon.range
  }
  
}