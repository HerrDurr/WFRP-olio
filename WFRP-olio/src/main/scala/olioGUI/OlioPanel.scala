package olioGUI

import scala.swing._
import scala.swing.event._
import scala.swing.BorderPanel.Position._
import olioMain._
import java.awt.Font
import olioGUI.CentrePanel

class OlioPanel(val olio: Olio, val whFont: Font, val whFontBold: Font) extends BorderPanel {
  
  /*
  val whFont = Font.createFont(Font.TRUETYPE_FONT, new File("data/CaslonAntique.ttf"))
  val whFontBold = Font.createFont(Font.TRUETYPE_FONT, new File("data/CaslonAntique-Bold.ttf"))
  * 
  */
  
  val topPanel = new TopPanel(this)
  val centrePanel = new CentrePanel(this)
  val attrPanel = this.centrePanel.attributePanel
  
  this.layout(topPanel) = North
  this.layout(centrePanel) = Center
  
  
  /**
   * Updates the attrPanel and olio's Skills.
   */
  def update() = {
    this.updateSkills()
    this.centrePanel.update()
    this.topPanel.update()
  }
  
  def updateSkills() = {
    this.olio.skills.foreach { x => x.setLevel(olio.attributes.listValues(x.skillAttribute._2)) }
  }
  
  update()
}