package olioGUI

import scala.swing._
import olioGUI.TalentSelector
import olioGUI.OlioPanel

class TalentMenu(olioPanel: OlioPanel) extends BoxPanel(Orientation.Horizontal) {
  
  val lPanel = new BoxPanel(Orientation.Vertical)
  val cPanel = new BoxPanel(Orientation.Vertical)
  val rPanel = new BoxPanel(Orientation.Vertical)
  
  val allTalents = olioPanel.olio.allTalents
  val talentN = this.allTalents.size
  
  val lTalents = allTalents.take(talentN / 3 + 1)
  val cTalents = allTalents.drop(talentN / 3 + 1).take(talentN / 3 + 1)
  val rTalents = allTalents.drop(talentN / 3 * 2 + 2)
  
  lTalents.foreach(t => lPanel.contents += new TalentSelector(olioPanel, t))
  cTalents.foreach(t => cPanel.contents += new TalentSelector(olioPanel, t))
  rTalents.foreach(t => rPanel.contents += new TalentSelector(olioPanel, t))
  
  
  this.contents += (lPanel, new Separator(Orientation.Vertical), cPanel, new Separator(Orientation.Vertical), rPanel)
  
  def update() = {
    this.lPanel.contents.foreach { _.asInstanceOf[TalentSelector].update() }
    this.cPanel.contents.foreach { _.asInstanceOf[TalentSelector].update() }
    this.rPanel.contents.foreach { _.asInstanceOf[TalentSelector].update() }
  }
  
}