package olioGUI

import scala.swing._
import main._
import scala.swing.event._
import scala.swing.BorderPanel.Position._

class CentrePanel(olio: Olio) extends BorderPanel {
  
  
  val attributePanel = new AttributePanel(olio.attributes)
  
  this.layout(attributePanel) = North
  
}