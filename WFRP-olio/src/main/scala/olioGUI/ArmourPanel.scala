package olioGUI

import scala.swing._
import javax.swing.SpinnerNumberModel
import scala.swing.event._
import olioGUI.Spinner
import olioGUI.OlioPanel

class ArmourPanel(olioPanel: OlioPanel, index: Int, hitMin: Int, hitMax: Int) extends BoxPanel(Orientation.Vertical) {
  
  val whFont = olioPanel.whFont.deriveFont(14f)
  val spinModel = new SpinnerNumberModel(0, 0, 7, 1)
  val armourSpinner = new Spinner(spinModel)
  armourSpinner.preferredSize = new Dimension(30, 28)
  armourSpinner.maximumSize = new Dimension(30, 28)
  val apLabel = new Label("AP: ")
  val hitLabel = new Label(hitMin + "-" + hitMax)
  val upperPanel = new BoxPanel(Orientation. Horizontal)
  val transpCol = new Color(255, 255, 255, 0)
  upperPanel.background = transpCol
  //apLabel.background = transpCol
  upperPanel.contents += (apLabel, armourSpinner)
  upperPanel.contents.foreach(_.font = whFont)
  
  this.contents += (upperPanel, hitLabel)
  this.contents.foreach(_.font = whFont)
  
  //this.preferredSize = new Dimension(50, 60)
  this.background = transpCol
  
  this.listenTo(armourSpinner)
  
  reactions += {
    case adjustingEvent: ValueChanged => {
      olioPanel.olio.armourPoints(index) = this.armourSpinner.value.asInstanceOf[Int]
    }
  }
  
}