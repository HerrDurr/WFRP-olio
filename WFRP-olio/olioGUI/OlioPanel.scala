package olioGUI

import scala.swing._
import main._
import scala.swing.event._
import scala.swing.BorderPanel.Position._
import scala.math.max
import java.io.File
import java.awt.Font

class OlioPanel(val olio: Olio) extends BorderPanel {
  
  
  val whFont = Font.createFont(Font.TRUETYPE_FONT, new File("data/CaslonAntique.ttf"))
  val whFontBold = Font.createFont(Font.TRUETYPE_FONT, new File("data/CaslonAntique-Bold.ttf"))
  
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
    this.topPanel.update()
  }
  
  def updateSkills() = {
    this.olio.skills.foreach { x => x.setLevel(olio.attributes.listValues(x.skillAttribute._2)) }
  }
  
  update()
}