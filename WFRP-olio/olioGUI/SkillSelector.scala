package olioGUI

import scala.swing._
import main.Olio
import main.Skill
import main.Talent
import javax.swing.SpinnerListModel
import java.awt.geom.Dimension2D
import scala.swing.event._
import java.awt.Color

class SkillSelector (olioPanel: OlioPanel, skill: Skill) extends FlowPanel {
  
  
  val olio = olioPanel.olio
  
  val nonSelFont = olioPanel.whFont.deriveFont(14f)
  val selFont = olioPanel.whFontBold.deriveFont(14f)
  
  val nameLabel = new Label(skill.name + " (" + skill.skillAttribute._1 + ")")
  nameLabel.preferredSize = new Dimension(170, 14)
  nameLabel.tooltip = {
      var tooltip = "Related Talents: "
      /*
      val talentsOption = skill.skillTalents
      if (talentsOption.isDefined) {
        var takenTalentNames = ""
        talentsOption.get.foreach( t => if ( olio.hasTalent(olio.allTalents.find(_.name == t).getOrElse(new Talent("Name"))) ) takenTalentNames += t + ", " )
        if (!takenTalentNames.isEmpty()) tooltip += takenTalentNames.dropRight(2) else tooltip = ""
      } else tooltip = ""
      * 
      */
      val talents = skill.skillTalents.getOrElse(Vector("-")).map(_ + ", ")
      talents.dropRight(1)foreach(tooltip += _)
      tooltip += talents.last.dropRight(2)
      tooltip
  }
  
  
  val skillOptions: Array[Object]  = Array("-", "X", "+10", "+20")
  
  val skillModel = new SpinnerListModel(skillOptions)
  
  val spinner = new Spinner(skillModel)
  spinner.preferredSize = new Dimension(40, 18)
  
  /*
  if (skill.timesGained == 1) spinner.peer.setValue(spinner.peer.getNextValue)  //("X")
  else if (skill.timesGained == 2) spinner.value = ("+10")
  else if (skill.timesGained == 3) spinner.value = ("+20")
  * 
  */
  
  //val selector = new ComboBox(skillOptions)
  
  val levelLabel = new Label("")
  levelLabel.preferredSize = new Dimension(40, 18)
  
  
  this.contents += (nameLabel, levelLabel, spinner)
  //this.contents += (nameLabel, selector, levelLabel)
  
  this.listenTo(spinner, this.mouse.moves, nameLabel.mouse.clicks)
  
  private var previousValue = spinner.value.toString()
  
  
  def update() = {
    if (olio.hasSkill(skill)) {
      this.deafTo(spinner)
      if (skill.timesGained == 1) spinner.value = ("X")
      else if (skill.timesGained == 2) spinner.value = ("+10")
      else if (skill.timesGained == 3) spinner.value = ("+20")
      this.listenTo(spinner)
      this.levelLabel.text = skill.skillLevel.toString()
      this.nameLabel.font = selFont
      this.levelLabel.font = selFont
    } else {
      this.levelLabel.text = ""
      this.nameLabel.font = nonSelFont
      this.levelLabel.font = nonSelFont
    }
  }
  
  def highLight(c: Component, hLight: Boolean) = {
      if (hLight) c.foreground = Color.MAGENTA
      else c.foreground = Color.black
    }
  
  reactions += {
    
    case mouseEvent: MouseEntered => {
      highLight (
          olioPanel.attrPanel.contents.find(c => c.isInstanceOf[Label] && c.asInstanceOf[Label].text == skill.skillAttribute._1).get,
          true
          )
    }
    
    case mouseEvent: MouseExited => {
      highLight (
          olioPanel.attrPanel.contents.find(c => c.isInstanceOf[Label] && c.asInstanceOf[Label].text == skill.skillAttribute._1).get,
          false
          )
    }
    
    case clickEvent: MouseClicked => {
      if (clickEvent.clicks > 1) {
        var message = skill.name + " (" + skill.skillAttribute._1 + "): " + skill.skillLevel +
                      "\n\nRelated Talents: "
        val talents = skill.skillTalents.getOrElse(Vector("-")).map(_ + ", ")
        talents.dropRight(1)foreach(message += _)
        message += talents.last.dropRight(2) + "\n\nDescription:\n" + skill.description
        Dialog.showMessage(this, message, skill.name, Dialog.Message.Info)
        
      }
    }
    
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