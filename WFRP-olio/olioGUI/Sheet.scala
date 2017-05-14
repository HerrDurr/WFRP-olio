package olioGUI

import scala.swing._
import scala.swing.event._
import scala.swing.BorderPanel.Position._
import main._

object Sheet extends SimpleSwingApplication {
  
  def top = new MainFrame {
    
    
    this.visible = true
    
    
    val topPanel = new GridPanel(1,4) {
      
      val nameLabel = new Label("Name")
      
      val rcPanel = new GridPanel(2,1) {
        val raceLabel = new Label("Race")
        val careerLabel = new Label("Career")
        contents += raceLabel
        contents += careerLabel
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
      
      contents += nameLabel
      contents += rcPanel
      contents += wfPanel
      contents += nextDayButton
    }
    
    this.contents = new BorderPanel {
      layout(topPanel) = North
    }
    
    
    
  }
  
  
}