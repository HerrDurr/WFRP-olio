package olioGUI

import scala.swing._
import main._
import scala.swing.event._
import scala.swing.BorderPanel.Position._

class CentrePanel(olio: Olio, olioPanel: OlioPanel) extends BorderPanel {
  
  
  val attributePanel = new AttributePanel(olio.attributes)
  val rightPanel = new RightPanel(olio, olioPanel)
  
  this.layout(attributePanel) = North
  this.layout(rightPanel) = East
}