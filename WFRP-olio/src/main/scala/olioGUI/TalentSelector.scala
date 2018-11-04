package olioGUI

import scala.swing._
import scala.swing.event._
import olioMain.Talent
import java.awt.Color
import olioGUI.OlioPanel

class TalentSelector(olioPanel: OlioPanel, talent: Talent) extends BoxPanel(Orientation.Horizontal) {
  
  val olio = olioPanel.olio
  //val modSkills = this.modifiesSkills
  //val modDamage = this.modifiesDamage
  
  val nonSelFont = olioPanel.whFont.deriveFont(14f)
  val selFont = olioPanel.whFontBold.deriveFont(14f)
  val nonSelCol = new Color(0, 0, 0, 160)
  val selCol = new Color(0, 0, 0, 200)
  
  val nameLabel = new Label(talent.name + " ")
  nameLabel.tooltip = "Affects: " + talent.affects
  
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
  
  this.listenTo(checkBox, nameLabel.mouse.moves, nameLabel.mouse.clicks)
  
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
      if (checkBox.selected)
      {
        olio.addTalent(talent)
        /*
        if (!this.modSkills._1.isEmpty) this.modSkills._1.foreach {
          s => if (s.modifier == 0) s.setModifier(this.modSkills._2) }
        if (this.modDamage == 'u') olio.weapons.filter(_.name == "Unarmed").foreach(_.setModifier(1))
        if (this.modDamage == 'r') olio.weapons.filter(_.range.head != '-').foreach(_.setModifier(1))
        if (this.modDamage == 'm') olio.weapons.filter(w => w.range.head == '-' && w.name != "Unarmed")
                                               .foreach(_.setModifier(1))
                                               * 
                                               */
      }
      else
      {
        olio.removeTalent(talent)
        /*
        if (!this.modSkills._1.isEmpty) this.modSkills._1.foreach {
          s => if (s.modifier != 0) s.setModifier(- this.modSkills._2) }
        if (this.modDamage == 'u') olio.weapons.filter(_.name == "Unarmed").foreach(_.setModifier(0))
        if (this.modDamage == 'r') olio.weapons.filter(_.range.head != '-').foreach(_.setModifier(0))
        if (this.modDamage == 'm') olio.weapons.filter(w => w.range.head == '-' && w.name != "Unarmed")
                                               .foreach(_.setModifier(0))
                                               * 
                                               */
      }
      olioPanel.update()
    }
    
    case clickEvent: MouseClicked => {
      if (clickEvent.clicks > 1) {
        var message = "Affects: " + talent.affects
        message += "\n\nDescription:\n" + talent.description
        Dialog.showMessage(this, message, talent.name, Dialog.Message.Info)
        
      }
    }
    
  }
  
  /*
  /**
   * If the talent permanently modifies the damage of certain weapons, return a tag for
   * those weapons.
   */
  def modifiesDamage: Char = {
    talent.name match {
      case "Mighty Missile" =>
        return 's'
      case "Mighty Shot" =>
        return 'r'
      case "Street Fighting" =>
        return 'u'
      case "Strike Mighty Blow" =>
        return 'm'
      case some =>
        return '-'
    }
  }
  * 
  */
  
  /*
  def modifiesSkills: Tuple2[Vector[Skill], Int] = {
    talent.name match {
      case "Aethyric Attunement" =>
        return (olio.allSkills.filter { s => s.name == "Channeling" || s.name == "Magical Sense" }, 10)
      case "Dealmaker" =>
        return (olio.allSkills.filter { s => s.name == "Evaluate" || s.name == "Haggle" }, 10)
      case "Excellent Vision" =>
        return (olio.allSkills.filter { s => s.name == "Lip Reading" }, 10)
      case "Keen Senses" =>
        return (olio.allSkills.filter { s => s.name == "Perception" }, 20)
      case "Linguistics" =>
        return (olio.allSkills.filter { s => s.name == "Read/Write" || s.name == "Speak Language*" }, 10)
      case "Menacing" =>
        return (olio.allSkills.filter { s => s.name == "Intimidate" || s.name == "Torture" }, 10)
      case "Orientation" =>
        return (olio.allSkills.filter { s => s.name == "Navigation" }, 10)
      case "Seasoned Traveller" =>
        return (olio.allSkills.filter { s => s.name == "Common Knowledge*" || s.name == "Speak Language*" }, 10)
      case "Surgery" =>
        return (olio.allSkills.filter { s => s.name == "Heal" }, 10)
      case "Super Numerate" =>
        return (olio.allSkills.filter { s => s.name == "Gamble" || s.name == "Navigation" }, 10)
      case "Trick Riding" =>
        return (olio.allSkills.filter { s => s.name == "Ride" }, 10)
      case some =>
        return (Vector(), 0)
    }
  }
  * 
  */
  
  update()
}
