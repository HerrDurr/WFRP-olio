package olioGUI

import scala.swing._
import scala.swing.event._
import main.Talent

class TalentSelector(olioPanel: OlioPanel, talent: Talent) extends BoxPanel(Orientation.Horizontal) {
  
  val olio = olioPanel.olio
  
  val nonSelFont = olioPanel.whFont.deriveFont(14f)
  val selFont = olioPanel.whFontBold.deriveFont(14f)
  
  val nameLabel = new Label(talent.name + " ")
  
  val checkBox = new CheckBox
  
  this.contents += (nameLabel, checkBox)
  
  
  def update() = {
    if (olio.hasTalent(talent)) {
      this.levelLabel.text = skill.skillLevel.toString()
      this.nameLabel.font = selFont
      this.levelLabel.font = selFont
    } else {
      this.levelLabel.text = ""
      this.nameLabel.font = nonSelFont
      this.levelLabel.font = nonSelFont
    }
  }
  
  
}