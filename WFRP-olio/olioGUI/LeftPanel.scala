package olioGUI

import scala.swing._

class LeftPanel(olioPanel: OlioPanel) extends BoxPanel(Orientation.Vertical) {
  
  
  val critButton = new Button("Critical Hit Tables")
  this.contents += critButton
}