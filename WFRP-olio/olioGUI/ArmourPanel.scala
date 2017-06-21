package olioGUI

import scala.swing._
import javax.swing.SpinnerNumberModel

class ArmourPanel(hitMin: Int, hitMax: Int) extends BoxPanel(Orientation.Vertical) {
  
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
  
  this.contents += (upperPanel, hitLabel)
  
  //this.preferredSize = new Dimension(50, 60)
  this.background = transpCol
  
  
}