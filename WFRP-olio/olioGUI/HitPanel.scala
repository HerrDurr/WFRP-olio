package olioGUI

import scala.swing._
import java.awt.Graphics
import scala.swing.Graphics2D
import data._
import javax.imageio._
import scala.swing.Image
import java.io.File
import javax.swing.ImageIcon
import java.awt.Color

class HitPanel extends Label with SequentialContainer.Wrapper {
  
  this.preferredSize = new Dimension(200, 300)
  
  val head = new ArmourPanel(1, 15)
  val rArm = new ArmourPanel(36, 55)
  val lArm = new ArmourPanel(16, 35)
  val body = new ArmourPanel(56, 80)
  val rLeg = new ArmourPanel(91, 100)
  val lLeg = new ArmourPanel(81, 90)
  //val armourPanelWidth = head.size.getWidth.toInt
  //val armourPanelHeight = head.size.getHeight.toInt
  val wid = 65
  val hei = 50
  
  //val transpCol = new Color(255, 255, 255, 255)
  
  this.icon = new ImageIcon("data/stickman.png")
  
  this.contents += (head, rArm, lArm, body, rLeg, lLeg)
  
  val topCorner = this.location
  
  this.head.peer.setBounds(100 - wid / 2 + topCorner.getX.toInt, topCorner.getY.toInt, wid, hei)
  this.rArm.peer.setBounds(200 - wid + topCorner.getX.toInt, 80 + topCorner.getY.toInt, wid, hei)
  this.lArm.peer.setBounds(0 + topCorner.getX.toInt, 80 + topCorner.getY.toInt, wid, hei)
  this.body.peer.setBounds(100 - wid / 2 + topCorner.getX.toInt, 130 + topCorner.getY.toInt, wid, hei)
  this.rLeg.peer.setBounds(200 - wid + topCorner.getX.toInt, 200 + topCorner.getY.toInt, wid, hei)
  this.lLeg.peer.setBounds(0 + topCorner.getX.toInt, 200 + topCorner.getY.toInt, wid, hei)
  
  /*
  //this.contents += (head, rArm, lArm, body, rLeg, lLeg)
  
  val backgroundLabel = new Label
  val img = new ImageIcon("data/stickman.png")
  backgroundLabel.icon = img
  
  this.contents += backgroundLabel
  
  backgroundLabel.peer.setLayout(null)
  
  val topCorner = this.location
  
  this.head.peer.setBounds(85 + topCorner.getX.toInt, topCorner.getY.toInt, 30, 28)
  this.rArm.peer.setBounds(170 + topCorner.getX.toInt, 100 + topCorner.getY.toInt, 30, 28)
  this.lArm.peer.setBounds(0 + topCorner.getX.toInt, 100 + topCorner.getY.toInt, 30, 28)
  this.body.peer.setBounds(85 + topCorner.getX.toInt, 100 + topCorner.getY.toInt, 30, 28)
  this.rLeg.peer.setBounds(170 + topCorner.getX.toInt, 200 + topCorner.getY.toInt, 30, 28)
  this.lLeg.peer.setBounds(0 + topCorner.getX.toInt, 200 + topCorner.getY.toInt, 30, 28)
  * 
  */
  
  /*
  override def paint(g: Graphics2D) = {
    
    preferredSize = new Dimension(200, 300)
    
    g.clearRect(0, 0, size.width, size.height)
    
    //val img = ImageIO.read(new File("data/stickman.png"))
    val img = new ImageIcon("data/stickman.png")
    
    backgroundLabel.peer.setIcon(img)
    
    //g.drawImage(img, 0, 0, size.width, size.height, 0, 0, img.getWidth, img.getHeight, null)
    
    this.head.peer.setBounds(85 + topCorner.getX.toInt, topCorner.getY.toInt, 30, 28)
    this.rArm.peer.setBounds(170 + topCorner.getX.toInt, 100 + topCorner.getY.toInt, 30, 28)
    this.lArm.peer.setBounds(0 + topCorner.getX.toInt, 100 + topCorner.getY.toInt, 30, 28)
    this.body.peer.setBounds(85 + topCorner.getX.toInt, 100 + topCorner.getY.toInt, 30, 28)
    this.rLeg.peer.setBounds(170 + topCorner.getX.toInt, 200 + topCorner.getY.toInt, 30, 28)
    this.lLeg.peer.setBounds(0 + topCorner.getX.toInt, 200 + topCorner.getY.toInt, 30, 28)
    
    
    
  }
  * 
  */
  
  
}