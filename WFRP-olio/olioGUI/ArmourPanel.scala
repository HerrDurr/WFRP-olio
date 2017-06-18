package olioGUI

import scala.swing._
import javax.swing.SpinnerNumberModel

class ArmourPanel(hitMin: Int, hitMax: Int) extends BoxPanel(Orientation.Vertical) {
  
  val spinModel = new SpinnerNumberModel(0, 0, 7, 1)
  val armourSpinner = new Spinner(spinModel)
  //armourSpinner.preferredSize = new Dimension(30, 28)
  armourSpinner.maximumSize = new Dimension(30, 28)
  val hitLabel = new Label(hitMin + "-" + hitMax)
  val upperPanel = new BoxPanel(Orientation. Horizontal)
  upperPanel.contents += (new Label("AP: "), armourSpinner)
  
  this.contents += (upperPanel, hitLabel)
  
}