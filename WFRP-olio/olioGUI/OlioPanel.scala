package olioGUI

import scala.swing._
import main._
import scala.swing.event._
import scala.swing.BorderPanel.Position._
import scala.math.max

class OlioPanel(olio: Olio) extends BorderPanel {
  
  
  
  val topPanel = new TopPanel(olio)
  val centrePanel = new CentrePanel(olio)
  
  this.layout(topPanel) = North
  this.layout(centrePanel) = Center
  
  
  
  
}