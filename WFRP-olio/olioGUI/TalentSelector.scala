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
  nameLabel.tooltip = talent.description
  
  val checkBox = new CheckBox
  
  this.contents += (nameLabel, checkBox)
  
  
  def update() = {
    if (olio.hasTalent(talent)) {
      this.checkBox.selected = true
      this.nameLabel.font = selFont
      this.nameLabel.foreground = selCol
    } else {
      this.nameLabel.font = nonSelFont
      this.nameLabel.foreground = nonSelCol
    }
  }
  
  this.listenTo(checkBox, nameLabel.mouse.moves)
  
  def highLight(c: Component, hLight: Boolean) = {
      if (hLight) c.foreground = Color.MAGENTA
      else c.foreground = Color.black
    }
  
  reactions += {
    
    case mouseEvent: MouseEntered => {
      if (talent.affectedAttributes.isDefined)
        talent.affectedAttributes.get.foreach( a => highLight (
          olioPanel.attrPanel.contents.find(c => c.isInstanceOf[Label] && c.asInstanceOf[Label].text == a).get,
          true
          ) )
    }
    
    case mouseEvent: MouseExited => {
      if (talent.affectedAttributes.isDefined)
      talent.affectedAttributes.get.foreach( a => highLight (
          olioPanel.attrPanel.contents.find(c => c.isInstanceOf[Label] && c.asInstanceOf[Label].text == a).get,
          false
          ) )
    }
    
    case ButtonClicked(checkBox) => {
      if (checkBox.selected) olio.addTalent(talent)
      else olio.removeTalent(talent)
      olioPanel.update()
    }
    
    
  }
  
  update()
}