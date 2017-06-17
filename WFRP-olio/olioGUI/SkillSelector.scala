package olioGUI

import scala.swing._
import main.Olio
import main.Skill
import javax.swing.SpinnerListModel
import java.awt.geom.Dimension2D
import scala.swing.event.ValueChanged

class SkillSelector (olioPanel: OlioPanel, skill: Skill) extends FlowPanel {
  
  //this.contents += new Label("MOI TESTI123")
  val olio = olioPanel.olio
  
  val nonSelFont = new Font("Arial", 0, 12)
  val selFont = new Font("Arial", java.awt.Font.BOLD, 12)
  
  val nameLabel = new Label(skill.name + " (" + skill.skillAttribute._1 + ")")
  nameLabel.preferredSize = new Dimension(170, 14)
  
  val skillOptions: Array[Object]  = Array("-", "X", "+10", "+20")
  
  val skillModel = new SpinnerListModel(skillOptions)
  
  val spinner = new Spinner(skillModel)
  spinner.preferredSize = new Dimension(40, 18)
  
  //val selector = new ComboBox(skillOptions)
  
  val levelLabel = new Label("")
  levelLabel.preferredSize = new Dimension(40, 18)
  
  this.contents += (nameLabel, spinner, levelLabel)
  //this.contents += (nameLabel, selector, levelLabel)
  
  this.listenTo(spinner)
  
  private var previousValue = spinner.value.toString()
  
  
  def update() = {
    this.levelLabel.text = skill.skillLevel.toString()
    if (olio.hasSkill(skill)) {
      this.nameLabel.font = selFont
      this.levelLabel.font = selFont
    } else {
      this.nameLabel.font = nonSelFont
      this.levelLabel.font = nonSelFont
    }
  }
  
  
  reactions += {
    
    case componentEvent: ValueChanged => {
      val value = spinner.value.toString()
      
      if (previousValue == "-")
      {
        olio.addSkill(skill)
      } else if (previousValue == "X")
      {
        if (value == "-") olio.untrainSkill(skill) else olio.addSkill(skill)
      } else if (previousValue == "+10")
      {
        if (value == "X") olio.untrainSkill(skill) else olio.addSkill(skill)
      } else if (value == "+10") olio.untrainSkill(skill)
      
      previousValue = value
      olioPanel.update()
      this.update()
    }
    
  }
  
  
  this.update()
  
}