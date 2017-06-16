package olioGUI

import scala.swing._
import scala.swing.event._
import scala.swing.BorderPanel.Position._
import main._

object Sheet extends SimpleSwingApplication {
  
  def top = new MainFrame {
    
    
    this.visible = true
    this.centerOnScreen()
    
    val olio = new Olio("Seppo", "Dwarf")
    
    this.contents = new OlioPanel(olio)
    
    this.title = olio.name
    
  }
  
  
}