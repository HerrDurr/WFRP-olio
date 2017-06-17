package olioGUI

import scala.swing._
import main._
import scala.swing.event._
import scala.swing.BorderPanel.Position._
import scala.math.max

class OlioPanel(val olio: Olio) extends BorderPanel {
  
  
  
  val topPanel = new TopPanel(olio)
  val centrePanel = new CentrePanel(this)
  val attrPanel = this.centrePanel.attributePanel
  
  this.layout(topPanel) = North
  this.layout(centrePanel) = Center
  
  
  /**
   * Updates the attrPanel and olio's Skills.
   */
  def update() = {
    this.attrPanel.update()
    this.updateSkills()
    this.centrePanel.rightPanel.update()
  }
  
  def updateSkills() = {
    this.olio.skills.foreach { x => x.setLevel(olio.attributes.listValues(x.skillAttribute._2)) }
  }
                
}