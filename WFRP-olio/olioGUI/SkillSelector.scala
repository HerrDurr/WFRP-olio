package olioGUI

import scala.swing._
import main.Olio
import main.Skill
//import javax.swing.SpinnerListModel
//import javax.swing.JSpinner

class SkillSelector (olio: Olio, skill: Skill) extends GridPanel(1, 3) {
  
  
  val nameLabel = new Label(skill.name + " (" + skill.skillAttribute._1 + ")")
  
  val skillOptions = Vector("-", "X", "+10", "+20")
  
  //val skillModel = new SpinnerListModel(skillOptions)
  
  //val spinner = Component.wrap(new JSpinner(skillModel))
  
  val selector = new ComboBox(skillOptions)
  
  val levelLabel = new Label(skill.skillLevel.toString())
  
  //this.contents += (nameLabel, spinner, levelLabel)
  this.contents += (nameLabel, selector, levelLabel)
  
}