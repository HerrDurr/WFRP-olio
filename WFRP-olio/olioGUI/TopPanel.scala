package olioGUI

import main._
import scala.swing._
import event._
import scala.math.max
import java.awt.geom.Dimension2D
import java.awt.Insets._

class TopPanel(olio: Olio) extends FlowPanel {
  
  val career = olio.career
  
  
  val nameLabel = new Label(olio.name)
  val raceLabel = new Label(olio.race)
  val careerLabel = new Label(career.current)
  
  val rcPanel = new GridPanel(1,2) {
    contents += raceLabel
    contents += careerLabel
  }
  
  val leftPanel = new GridPanel(2,1) {
    contents += nameLabel
    contents += rcPanel
  }
  
  val wfPanel = new GridPanel(2,1) {
    val woundPanel = new FlowPanel() {
      val woundButton = new Button("0")
      woundButton.preferredSize = new Dimension(28, 28)
      woundButton.margin = new Insets(1,1,1,1)
      val woundLabel = new Label("/ " + olio.attributes.wounds)
      
      contents += new Label("Wounds: ")
      contents += woundButton
      contents += woundLabel
      
    }
    
    
    
    val fortunePanel = new FlowPanel {
      val fortuneButton = new Button( olio.attributes.fatePoints.toString() )
      val fortuneLabel = new Label( "/ " + olio.attributes.fatePoints.toString() )
      fortuneButton.preferredSize = new Dimension(28, 28)
      fortuneButton.margin = new Insets(1,1,1,1)
      
      contents += new Label("Fortune: ")
      contents += fortuneButton
      contents += fortuneLabel
    }
    
    contents += woundPanel
    contents += fortunePanel
  }
  
  val nextDayButton = new Button("Next Day")
  this.nextDayButton.preferredSize = new Dimension(80, 60)
  
  this.contents += leftPanel
  this.contents += wfPanel
  this.contents += nextDayButton
  
  
  this.listenTo(careerLabel.mouse.clicks)
  this.listenTo(nameLabel.mouse.clicks)
  this.listenTo(raceLabel.mouse.clicks)
  this.listenTo(nextDayButton)
  this.listenTo(wfPanel.woundPanel.woundButton)
  this.listenTo(wfPanel.fortunePanel.fortuneButton)
  
  
  
  this.reactions += {
    
    case clickEvent: ButtonClicked => {
      
      if(clickEvent.source == wfPanel.fortunePanel.fortuneButton)
      {
        olio.setFortune(max(wfPanel.fortunePanel.fortuneButton.text.toInt.-(1), 0))
        update()
      }
      
      if(clickEvent.source == wfPanel.woundPanel.woundButton)
      {
        val w = Dialog.showInput(contents.head, "Enter current wounds", initial = "")
        w match {
          case Some(n) => {
            olio.setCurrentWounds(n.toInt)
            update()
          }
          case None =>
        }
      }
      
      if(clickEvent.source == nextDayButton)
      {
        val wounds = olio.currentWounds
        if(wounds >= 3)
          olio.setCurrentWounds(wounds + 1)
        olio.setFortune(olio.attributes.fatePoints)
        update()
      }
      
    }
    
    
    case clickEvent: MouseClicked => {
      if(clickEvent.clicks > 1)
      {
        
        if(clickEvent.source == this.careerLabel)
        {
          val input = Dialog.showInput(this, "", initial = careerLabel.text)
          input match {
            case Some(n) => {
              career.change(n)
              this.update()
            }
            case None =>
          }
        }
        
        if(clickEvent.source == this.nameLabel) 
        {
          val input = Dialog.showInput(this, "", initial = nameLabel.text)
          input match {
            case Some(n) => {
              olio.setName(n)
              this.update()
            }
            case None =>
          }
        }
        
        if(clickEvent.source == this.raceLabel) 
        {
          val input = Dialog.showInput(this, "", initial = raceLabel.text)
          input match {
            case Some(n) => {
              olio.setRace(n)
              this.update()
            }
            case None =>
          }
        }
        
      }
    }
      
  }
  
  
  def update() = {
    this.nameLabel.text = olio.name
    this.careerLabel.text = olio.career.current
    this.raceLabel.text = olio.race
    this.wfPanel.woundPanel.woundLabel.text = "/ " + olio.attributes.wounds.toString()
    this.wfPanel.woundPanel.woundButton.text = olio.currentWounds.toString()
    this.wfPanel.fortunePanel.fortuneLabel.text = "/ " + olio.attributes.fatePoints.toString()
    this.wfPanel.fortunePanel.fortuneButton.text = olio.fortunePoints.toString()
  }
  
  def changeCareer(c: String) = {
    this.olio.career.change(c)
    this.update()
  }
  
  def changeRace(r: String) = {
    this.olio.setRace(r)
    this.update()
  }
  
  def changeName(n: String) = {
    this.olio.setName(n)
    this.update()
  }
  
}