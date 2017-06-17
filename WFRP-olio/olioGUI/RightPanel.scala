package olioGUI

import main._
import scala.swing._
import data._
import event._
import javax.swing.JFrame

//import javax.swing.table.DefaultTableModel
//import collection.mutable.ArrayBuffer

class RightPanel(olioPanel: OlioPanel) extends GridPanel(3,1) {
  
  val olio = olioPanel.olio
  
  val weapon1 = new WeaponPanel(olio, 0)
  val weapon2 = new WeaponPanel(olio, 1)
  val weapon3 = new WeaponPanel(olio, 2)
  
  val weaponGrid = new GridPanel(3, 1) {
    contents += (weapon1, weapon2, weapon3)
  }
  
  
  val skill1 = new Label("")
  val skill2 = new Label("")
  val skill3 = new Label("")
  val skill4 = new Label("")
  val skillLabels = Vector(skill1, skill2, skill3, skill4)
  
  //Pressing this button shows all skills, which have tickboxes next to them so they can be picked and trained in.
  val skillsButton = new Button("Skills")
  
  val skillGrid = new GridPanel(5,1) {
    skillLabels.foreach(contents += _)
    contents += skillsButton
  }
  
  /*
  val skillPopup = new ListView {
    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
    val reader = Source.fromFile("data/skills.txt")(decoder).reader()
    DataIO.loadNames(reader).foreach(contents += new Label(_))
  }
  * 
  */
  
  val talentsButton = new Button("Talents")
  
  this.contents += (weaponGrid, skillGrid, talentsButton)
  
  this.listenTo(skillsButton, talentsButton)
  
  val skillMenu = new SkillMenu(olioPanel)
  val talentMenu = new TalentMenu(olioPanel)
  
  this.reactions += {
    case clickEvent: ButtonClicked => {
      
      val frame = new Frame
      if (clickEvent.source == skillsButton)
      {
        frame.title = olio.name + "'s Skills"
        frame.contents = skillMenu
      }
      if (clickEvent.source == talentsButton)
      {
        frame.title = olio.name + "'s Talents"
        frame.contents = talentMenu
      }
      frame.visible = true
      frame.setLocationRelativeTo(this)
    }
  }
  
  /**
   * Updates all fields in this Panel. Be sure to give the Olio its Skills before using this!
   */
  def update() = {
    this.weaponGrid.contents.tail.foreach(_.asInstanceOf[WeaponPanel].update())
    this.skillMenu.update()
    val topSkills = olio.skills.sortBy(_.skillLevel).takeRight(4)
    skillLabels.foreach {
      x => {
        val xSkill = topSkills(3 - skillLabels.indexOf(x))
        x.text = ( xSkill.name + " (" + xSkill.skillLevel + ")" )
      }
    }
  }
  
  
  this.update()
  
}