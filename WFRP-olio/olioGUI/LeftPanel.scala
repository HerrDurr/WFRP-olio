package olioGUI

import scala.swing._
import javax.swing.ImageIcon
import data._
import scala.swing.BorderPanel.Position._

class LeftPanel(olioPanel: OlioPanel) extends BoxPanel(Orientation.Vertical) {
  
  
  val hitPanel = new BorderPanel
  val pictureLabel = new Label
  pictureLabel.icon = (new ImageIcon("data/stickman.png"))
  
  
  
  hitPanel.layout(pictureLabel) = Center
  
  
  val critButton = new Button("Critical Hit Tables")
  this.contents += (hitPanel, critButton)
}