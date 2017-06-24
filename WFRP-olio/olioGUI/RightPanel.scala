package olioGUI

import main._
import scala.swing._
import data._
import event._
import javax.swing.JFrame
//import scala.swing.BorderPanel.Position._

//import javax.swing.table.DefaultTableModel
//import collection.mutable.ArrayBuffer

class RightPanel(olioPanel: OlioPanel) extends BoxPanel(Orientation.Vertical) {
  
  val olio = olioPanel.olio
  
  val weapon1 = new WeaponPanel(olioPanel, 0)
  val weapon2 = new WeaponPanel(olioPanel, 1)
  val weapon3 = new WeaponPanel(olioPanel, 2)
  
  val weaponGrid = new GridPanel(3,1) {
    contents += (weapon1, weapon2, weapon3)
    //this.layout(weapon1) = North
    //this.layout(weapon2) = Center
    //this.layout(weapon3) = South
  }
  
  
  
  val skillLabels = Vector(new Label(""), new Label(""), new Label(""), new Label(""), new Label(""))
  
  //Pressing this button shows all skills, which have tickboxes next to them so they can be picked and trained in.
  val skillsButton = new Button("All Skills")
  
  val skillGrid = new GridPanel(5,1) {
    skillLabels.foreach(contents += _)
    //contents += skillsButton
  }
  this.skillGrid.contents.foreach(_.font = olioPanel.whFont.deriveFont(16f))
  
  /*
  val skillPopup = new ListView {
    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
    val reader = Source.fromFile("data/skills.txt")(decoder).reader()
    DataIO.loadNames(reader).foreach(contents += new Label(_))
  }
  * 
  */
  
  val talentsButton = new Button("Talents")
  
  //val colourButton = new Button("Colour")
  
  val toolBar = new BoxPanel(Orientation.Horizontal)
  
  this.toolBar.contents += (skillsButton, talentsButton)
  this.toolBar.contents.foreach(_.font = olioPanel.whFont.deriveFont(16f))
  
  this.contents += (weaponGrid, skillGrid, /*Swing.VGlue, */toolBar)
  
  this.listenTo(skillsButton, talentsButton)
  
  val skillMenu = new SkillMenu(olioPanel)
  val talentMenu = new TalentMenu(olioPanel)
  
  this.reactions += {
    case clickEvent: ButtonClicked => {
      
      
      /*
      if (clickEvent.source == colourButton)
      {
        val newCol = ColorChooser.showDialog(this, "Choose a colour", olio.colour).getOrElse(olio.colour)
        olio.setColour(newCol)
        olioPanel.update()
      }
      */
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
    val topSkills = olio.skills.filterNot( s => s.name == "Common Knowledge*" || s.name == "Speak Language*" )
                        .sortBy(_.skillLevel).takeRight(4)
    val perception = olio.skills.find(_.name == "Perception").get
    skillLabels(0).text = perception.name + " (" + perception.skillLevel + ")"
    skillLabels(0).tooltip = "Related known Talents: " + olio.relatedKnownTalents(perception)
    skillLabels.takeRight(4).foreach {
      x => {
        val xSkill = topSkills(topSkills.length - skillLabels.indexOf(x))
        x.text = ( xSkill.name + " (" + xSkill.skillLevel + ")" )
        x.tooltip = "Related known Talents: " + olio.relatedKnownTalents(xSkill)
      }
    }
    
    this.talentMenu.update()
  }
  
  
  this.update()
  
}