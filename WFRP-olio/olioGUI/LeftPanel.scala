package olioGUI

import scala.swing._
import javax.swing.ImageIcon
import data._
import scala.swing.BorderPanel.Position._

class LeftPanel(olioPanel: OlioPanel) extends BoxPanel(Orientation.Vertical) {
  
  
  val hitPanel = new BorderPanel
  val pictureLabel = new Label
  pictureLabel.icon = (new ImageIcon("data/stickman.png"))
  val headPanel = new BoxPanel(Orientation.Horizontal)
  val legPanel = new BoxPanel(Orientation.Horizontal)
  headPanel.contents += (new ArmourPanel(1, 15), new ArmourPanel(16, 40))
  legPanel.contents += (new ArmourPanel(71, 85), new ArmourPanel(86, 100))
  
  hitPanel.layout(headPanel) = North
  hitPanel.layout(new ArmourPanel(41, 55)) = West
  hitPanel.layout(new ArmourPanel(56, 70)) = East
  hitPanel.layout(legPanel) = South
  hitPanel.layout(pictureLabel) = Center
  
  
  val critButton = new Button("Critical Hit Tables")
  this.contents += (hitPanel, critButton)
}