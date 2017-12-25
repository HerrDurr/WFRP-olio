package olioGUI

import scala.swing._
import javax.swing.ImageIcon
//import data._
import scala.swing.BorderPanel.Position._

class LeftPanel(olioPanel: OlioPanel) extends BoxPanel(Orientation.Vertical) {
  
  /*
  val hitPanel = new BorderPanel
  val pictureLabel = new Label
  pictureLabel.icon = (new ImageIcon("data/stickman.png"))
  val headPanel = new BoxPanel(Orientation.Horizontal)
  val legPanel = new BoxPanel(Orientation.Horizontal)
  headPanel.contents += (new ArmourPanel(1, 15), new ArmourPanel(56, 80))
  legPanel.contents += (new ArmourPanel(81, 90), new ArmourPanel(91, 100))
  
  hitPanel.layout(headPanel) = North
  hitPanel.layout(new ArmourPanel(16, 35)) = West
  hitPanel.layout(new ArmourPanel(36, 55)) = East
  hitPanel.layout(legPanel) = South
  hitPanel.layout(pictureLabel) = Center
  * 
  */
  val hitPanel = new HitPanel(olioPanel)
  
  
  this.contents += (hitPanel)
  hitPanel.repaint()
  
}