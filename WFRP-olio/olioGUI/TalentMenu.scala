package olioGUI

import scala.swing._

class TalentMenu(olioPanel: OlioPanel) extends BoxPanel(Orientation.Horizontal) {
  
  val lPanel = new BoxPanel(Orientation.Vertical)
  val cPanel = new BoxPanel(Orientation.Vertical)
  val rPanel = new BoxPanel(Orientation.Vertical)
  
  val allTalents = olioPanel.olio.allTalents
  val talentN = this.allTalents.size
  
  val lTalents = allTalents.take(talentN / 3 + 1)
  val cTalents = allTalents.drop(talentN / 3 + 1).take(talentN / 3 + 1)
  val rTalents = allTalents.drop(talentN / 3 * 2 + 2)
  olioPanel.olio.allTalents.foreach(t => lPanel.contents += new Label(t.name))
  
  this.contents += lPanel
}