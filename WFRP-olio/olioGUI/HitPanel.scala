package olioGUI

import scala.swing._
import java.awt.Graphics
import scala.swing.Graphics2D
import data._
import javax.imageio._
import scala.swing.Image
import java.io.File

class HitPanel extends Panel /*with Wrapper[SequentialContainer]*/ {
  
  val head = new ArmourPanel(1, 15)
  
  val rArm = new ArmourPanel(16, 35)
  val lArm = new ArmourPanel(36, 55)
  val body = new ArmourPanel(56, 80)
  val rLeg = new ArmourPanel(81, 90)
  val lLeg = new ArmourPanel(91, 100)
  
  //this.contents += (head, rArm, lArm, body, rLeg, lLeg)
  
  override def paint(g: Graphics2D) = {
    
    preferredSize = new Dimension(200, 300)
    
    g.clearRect(0, 0, size.width, size.height)
    
    val img = ImageIO.read(new File("data/stickman.png"))
    
    g.drawImage(img, 0, 0, size.width, size.height, 0, 0, img.getWidth, img.getHeight, null)
    
    
    
  }
  
  
}