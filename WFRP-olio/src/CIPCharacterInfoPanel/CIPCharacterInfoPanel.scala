package CIPCharacterInfoPanel

import scala.swing._
import BorderPanel.Position._
import main.Olio

class CIPCharacterInfoPanel(olio: Olio, fullView: Boolean) extends BorderPanel {
  
  val CIP1_1IconP = new CIP1_1IconPanel
  
  val CIP1_2ContainerP = new BorderPanel
  
  val CIP1_2_1StatusAttackGrid = new GridPanel(1,2)
  
  val CIP1_2_2HealthGrid = new GridPanel(1,2)
  
  CIP1_2ContainerP.layout(CIP1_2_1StatusAttackGrid) = Center
  CIP1_2ContainerP.layout(CIP1_2_2HealthGrid) = East
  
  this.layout(CIP1_1IconP) = West
  this.layout(CIP1_2ContainerP) = Center
  
  
  
}