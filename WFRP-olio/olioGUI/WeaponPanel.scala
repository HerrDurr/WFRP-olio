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
  val path = this.getClass.getClassLoader.getResource("").getPath
  private val file = Source.fromFile(path + "/data/weapons.txt")(decoder)
  private var weaponList: Vector[String] = Vector()
  try {
   weaponList = DataIO.loadNames(file.reader())
  } finally {
    file.close()
  }
  
  
  
  val dropMenu = new ComboBox(this.weaponList)
  dropMenu.preferredSize = new Dimension(120, 28)
  dropMenu.maximumSize = new Dimension(120, 28)
  val damageLabel = new Label("Dmg: ")
  damageLabel.preferredSize = new Dimension(50, 28)
  val rangeLabel = new Label("Rng: ")
  rangeLabel.preferredSize = new Dimension(70, 28)
  val reloadLabel = new Label("Rld: ")
  reloadLabel.preferredSize = new Dimension(50, 28)
  this.update()
  this.dropMenu.selection.item = weapon.name
  
  this.contents += (dropMenu, damageLabel, rangeLabel, reloadLabel)
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
    rangeLabel.text = " Rng: " + weapon.range
    reloadLabel.text = " Rld: " + weapon.reloadTime
    dropMenu.tooltip = weapon.name + " (" + weapon.group + "): " + {
      var res = ""
      val qual = weapon.qualities
      //if (qual.head != '-') {
        qual.dropRight(1).foreach(res += _ + ", ")
        res += qual.takeRight(1)(0)
      //}
      res
    }
    damageLabel.tooltip = damageLabel.text
    rangeLabel.tooltip = rangeLabel.text
    reloadLabel.tooltip = weapon.reloadTime
  }
  
}