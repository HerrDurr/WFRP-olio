package olioGUI

import main._
import scala.swing._
import event._
import scala.math.max

class TopPanel(olio: Olio) extends GridPanel(1, 3) {
  
  val career = olio.career
  
  val nameLabel = new Label(olio.name) {
    listenTo(this.mouse.clicks)
    reactions += {
      case clickEvent: MouseClicked =>
        if(clickEvent.clicks > 1) {
          val input = Dialog.showInput(this, "", initial = this.text)
          input match {
            case Some(n) => {
              olio.setName(n)
              update() /// ÄÄÄÄ how do i do
          }
          case None =>
        }
        }
    }
  }
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
      val woundLabel = new Label("/ " + olio.attributes.wounds)
      
      contents += new Label("Wounds: ")
      contents += woundButton
      contents += woundLabel
      
      listenTo(woundButton)
      
      reactions += {
        case ButtonClicked(woundButton) =>
          val w = Dialog.showInput(contents.head, "Enter current wounds", initial = "")
          w match {
            case Some(n) => woundButton.text = n
            case None =>
          }
      }
    }
    
    
    val fortunePanel = new FlowPanel {
      val fortuneButton = new Button( olio.attributes.fatePoints.toString() )
      val fortuneLabel = new Label( "/ " + olio.attributes.fatePoints.toString() )
      
      contents += new Label("Fortune: ")
      contents += fortuneButton
      contents += fortuneLabel
      
      listenTo(fortuneButton)
      
      reactions += {
        case ButtonClicked(fortuneButton) =>
          fortuneButton.text = max(fortuneButton.text.toInt.-(1), 0).toString
      }
    }
    
    contents += woundPanel
    contents += fortunePanel
  }
  
  val nextDayButton = new Button("Next Day")
  
  this.contents += leftPanel
  this.contents += wfPanel
  this.contents += nextDayButton
  
  
  this.listenTo(careerLabel.mouse.clicks)
  this.listenTo(nameLabel.mouse.clicks)
  
  this.reactions += {
    case clickEvent: MouseClicked =>
      if (clickEvent.clicks > 1) {
        val input = Dialog.showInput(this, "", initial = careerLabel.text)
        input match {
          case Some(n) => {
            career.change(n)
            this.update()
          }
          case None =>
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