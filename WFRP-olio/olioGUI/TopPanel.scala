package olioGUI

import main._
import scala.swing._
import event._
import scala.math.max

class TopPanel(olio: Olio) extends GridPanel(1, 3) {
  
  val career = olio.career
  
  val nameLabel = new Label(olio.name)
  val raceLabel = new Label(olio.race)
  val careerLabel = new Label(career.current)
  
  val rcPanel = new GridPanel(1,2) {
    contents += raceLabel
    contents += careerLabel
  }
  
  val lefthis = new GridPanel(2,1) {
    contents += nameLabel
    contents += rcPanel
  }
  
  val wfPanel = new GridPanel(2,1) {
    val woundPanel = new FlowPanel() {
      val woundButton = new Button("0")
      val woundLabel = new Label("/ 0")
      
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
    
    //val fortuneLabel = new Label("Fortune: 0/0")
    val fortunePanel = new FlowPanel {
      val fortuneButton = new Button( olio.attributes(15).toString() )
      val fortuneLabel = new Label( "/ " + olio.attributes(15).toString() )
      
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
  
  this.contents += lefthis
  this.contents += wfPanel
  this.contents += nextDayButton
  
  
  this.listenTo(careerLabel.mouse.clicks)
  
  this.reactions += {
    case clickEvent: MouseClicked =>
      if (clickEvent.clicks > 1) {
        val input = Dialog.showInput(this, "", initial = careerLabel.text)
        input match {
          case Some(n) => {
            career.change(n)
            careerLabel.text = n
          }
          case None =>
        }
      }
      
  }
  
  def update() = {
    this.nameLabel.text = olio.name
    this.careerLabel.text = olio.career.current
    this.raceLabel.text = olio.race
    
  }
  
  def changeCareer(c: String) = ???
  
  def changeRace(r: String) = ???
  
  def changeName(n: String) = ???
  
}