package olioGUI

import scala.swing._
import main._
import scala.swing.event._
import scala.swing.BorderPanel.Position._

class OlioPanel(olio: Olio) extends BorderPanel {
  
  
  val tPanel = this.topPanel(olio.name, olio.race, olio.myCareer)
  
  this.layout(tPanel) = North
  
  def topPanel(name: String, race: String, career: Career): GridPanel = {
    
    val tPanel = new GridPanel(1,3)
    val nameLabel = new Label(name)
    val raceLabel = new Label(race)
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
      
      val fortuneLabel = new Label("Fortune: 0/0")
      contents += woundPanel
      contents += fortuneLabel
    }
    
    val nextDayButton = new Button("Next Day")
    
    tPanel.contents += leftPanel
    tPanel.contents += wfPanel
    tPanel.contents += nextDayButton
    
    
    tPanel.listenTo(careerLabel.mouse.clicks)
    
    tPanel.reactions += {
      case clickEvent: MouseClicked =>
        if (clickEvent.clicks > 1) {
          val input = Dialog.showInput(tPanel, "", initial = careerLabel.text)
          input match {
            case Some(n) => {
              career.change(n)
              careerLabel.text = n
            }
            case None =>
          }
        }
        
    }
    
    
    tPanel
  }
  
  
  
}