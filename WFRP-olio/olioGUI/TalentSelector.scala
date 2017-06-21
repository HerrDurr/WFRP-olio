package olioGUI

import scala.swing._
import scala.swing.event._
import main.Talent

class TalentSelector(olioPanel: OlioPanel, talent: Talent) extends BoxPanel(Orientation.Horizontal) {
  
  val olio = olioPanel.olio
  
  val nonSelFont = new Font("Arial", 0, 12)
  val selFont = new Font("Arial", java.awt.Font.BOLD, 12)
  
  
  
}