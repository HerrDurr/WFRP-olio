package olioGUI

import scala.swing._

class TalentMenu(olioPanel: OlioPanel) extends BoxPanel(Orientation.Horizontal) {
  
  val lPanel = new BoxPanel(Orientation.Vertical)
  
  olioPanel.olio.allTalents.foreach(t => lPanel.contents += new Label(t.name))
  
  this.contents += lPanel
}