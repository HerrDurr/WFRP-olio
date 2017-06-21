package olioGUI

import scala.swing._
import scala.swing.event._
import scala.swing.BorderPanel.Position._
import main._

object Sheet extends SimpleSwingApplication {
  
  
  def top = new MainFrame {
    
    this.visible = true
    this.centerOnScreen()
    
    val olio = new Olio
    
    val olioPanel = new OlioPanel(olio)
    
    this.contents = olioPanel
    
    this.title = olio.name
    
    
    
    
  }
  
  
}