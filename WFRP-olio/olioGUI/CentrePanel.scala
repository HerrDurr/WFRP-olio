package olioGUI

import scala.swing._
import main._
import scala.swing.event._
import scala.swing.BorderPanel.Position._

class CentrePanel(olioPanel: OlioPanel) extends BorderPanel {
  
  
  
  val attributePanel = new AttributePanel(olioPanel.olio.attributes)
  val rightPanel = new RightPanel(olioPanel)
  
  this.layout(attributePanel) = North
  this.layout(rightPanel) = East
}