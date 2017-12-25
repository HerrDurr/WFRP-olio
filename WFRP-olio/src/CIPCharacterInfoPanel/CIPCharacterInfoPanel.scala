package CIPCharacterInfoPanel

import scala.swing._
import BorderPanel.Position._

class CIPCharacterInfoPanel extends BorderPanel {
  
  val CIP1_1IconP = new CIP1_1IconPanel
  
  val CIP1_2ContainerP = new BorderPanel
  
  this.layout(CIP1_1IconP) = West
  
  
  
}