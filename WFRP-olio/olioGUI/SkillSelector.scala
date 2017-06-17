package olioGUI

import scala.swing._
import main.Olio
import main.Skill
import javax.swing.SpinnerListModel
import java.awt.geom.Dimension2D
import scala.swing.event.ValueChanged

class SkillSelector (olio: Olio, skill: Skill) extends FlowPanel {
  
  //this.contents += new Label("MOI TESTI123")
  
  
  val nameLabel = new Label(skill.name + " (" + skill.skillAttribute._1 + ")")
  nameLabel.preferredSize = new Dimension(170, 14)
  
  val skillOptions: Array[Object]  = Array("-", "X", "+10", "+20")
  
  val skillModel = new SpinnerListModel(skillOptions)
  
  val spinner = new Spinner(skillModel)
  spinner.preferredSize = new Dimension(40, 18)
  
  //val selector = new ComboBox(skillOptions)
  
  val levelLabel = new Label(skill.skillLevel.toString())
  levelLabel.preferredSize = new Dimension(40, 18)
  
  this.contents += (nameLabel, spinner, levelLabel)
  //this.contents += (nameLabel, selector, levelLabel)
  
  this.listenTo(spinner)
  
  private var previousValue = spinner.value
  
  reactions += {
    
    case componentEvent: ValueChanged => {
      val value = spinner.value
      
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
    }
    
  }
  
}