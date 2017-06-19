package olioGUI

import scala.swing._
import java.awt.Graphics
import scala.swing.Graphics2D
import data._
import javax.imageio._
import scala.swing.Image
import java.io.File

class HitPanel extends Panel {
  
  override def paint(g: Graphics2D) = {
    
    preferredSize = new Dimension(200, 300)
    
    g.clearRect(0, 0, size.width, size.height)
    
    val img = ImageIO.read(new File("data/stickman.png"))
    
    g.drawImage(img, 0, 0, size.width, size.height, 0, 0, img.getWidth, img.getHeight, null)
    
    
  }
  
  
}