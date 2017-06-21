package olioGUI

import scala.swing._
import scala.swing.event._
import main.Talent
import java.awt.Color

class TalentSelector(olioPanel: OlioPanel, talent: Talent) extends BoxPanel(Orientation.Horizontal) {
  
  val olio = olioPanel.olio
  
  val nonSelFont = olioPanel.whFont.deriveFont(14f)
  val selFont = olioPanel.whFontBold.deriveFont(14f)
  val nonSelCol = new Color(0, 0, 0, 160)
  val selCol = new Color(0, 0, 0, 200)
  
  val nameLabel = new Label(talent.name + " ")
  
  val checkBox = new CheckBox
  
  this.contents += (nameLabel, checkBox)
  
  
  def update() = {
    if (olio.hasTalent(talent)) {
      this.nameLabel.font = selFont
      this.nameLabel.foreground = selCol
    } else {
      this.nameLabel.font = nonSelFont
      this.nameLabel.foreground = nonSelCol
    }
  }
  
  this.listenTo(checkBox)
  
  reactions += {
    case ButtonClicked(checkBox) => {
      if (checkBox.selected) olio.addTalent(talent)
      else olio.removeTalent(talent)
      this.update()
    }
  }
  
  update()
}