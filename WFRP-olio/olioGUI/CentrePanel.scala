package olioGUI

import scala.swing._
import main._
import scala.swing.event._


class CentrePanel(olioPanel: OlioPanel) extends BoxPanel(Orientation.Vertical) {
  
  
  
  val attributePanel = new AttributePanel(olioPanel)
  val rightPanel = new RightPanel(olioPanel)
  val leftPanel = new LeftPanel(olioPanel)
  
  this.contents += (attributePanel, new BoxPanel(Orientation.Horizontal) { contents += (leftPanel, rightPanel) })
}