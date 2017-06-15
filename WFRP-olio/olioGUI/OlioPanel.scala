package olioGUI

import scala.swing._
import main._
import scala.swing.event._
import scala.swing.BorderPanel.Position._
import scala.math.max

class OlioPanel(olio: Olio) extends BorderPanel {
  
  
  
  val topPanel = new TopPanel(olio)
  val centrePanel = new CentrePanel(olio)
  val attrPanel = this.centrePanel.attributePanel
  
  this.layout(topPanel) = North
  this.layout(centrePanel) = Center
  
  //Attribute Panel listening
  this.listenTo(this.attrPanel.wsLabel.mouse.clicks, this.attrPanel.bsLabel.mouse.clicks,
                this.attrPanel.sLabel.mouse.clicks, this.attrPanel.tLabel.mouse.clicks,
                this.attrPanel.agLabel.mouse.clicks, this.attrPanel.intLabel.mouse.clicks,
                this.attrPanel.wpLabel.mouse.clicks, this.attrPanel.felLabel.mouse.clicks,
                this.attrPanel.aLabel.mouse.clicks, this.attrPanel.wLabel.mouse.clicks,
                this.attrPanel.mLabel.mouse.clicks, this.attrPanel.magLabel.mouse.clicks,
                this.attrPanel.ipLabel.mouse.clicks, this.attrPanel.fpLabel.mouse.clicks)
  
  this.reactions += {
    
    case clickEvent: MouseClicked => {
      if(clickEvent.clicks > 1)
      {
        
        //WS
        if(clickEvent.source == this.attrPanel.wsLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.attrPanel.wsLabel.text)
          input match {
            case Some(n) => {
              this.olio.attributes.setWS(n.toInt)
              this.attrPanel.update()
            }
            case None =>
          }
        }
        
        //BS
        if(clickEvent.source == this.attrPanel.bsLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.attrPanel.bsLabel.text)
          input match {
            case Some(n) => {
              this.olio.attributes.setBS(n.toInt)
              this.attrPanel.update()
            }
            case None =>
          }
        }
        
        //S
        if(clickEvent.source == this.attrPanel.sLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.attrPanel.sLabel.text)
          input match {
            case Some(n) => {
              this.olio.attributes.setS(n.toInt)
              this.attrPanel.update()
              this.centrePanel.rightPanel.weaponGrid.contents.tail.foreach { panel => panel.asInstanceOf[WeaponPanel].update() }
            }
            case None =>
          }
        }
        
        //T
        if(clickEvent.source == this.attrPanel.tLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.attrPanel.tLabel.text)
          input match {
            case Some(n) => {
              this.olio.attributes.setT(n.toInt)
              this.attrPanel.update()
            }
            case None =>
          }
        }
        
        //Ag
        if(clickEvent.source == this.attrPanel.agLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.attrPanel.agLabel.text)
          input match {
            case Some(n) => {
              this.olio.attributes.setAg(n.toInt)
              this.attrPanel.update()
            }
            case None =>
          }
        }
        
        //Int
        if(clickEvent.source == this.attrPanel.intLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.attrPanel.intLabel.text)
          input match {
            case Some(n) => {
              this.olio.attributes.setInt(n.toInt)
              this.attrPanel.update()
            }
            case None =>
          }
        }
        
        //WP
        if(clickEvent.source == this.attrPanel.wpLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.attrPanel.wpLabel.text)
          input match {
            case Some(n) => {
              this.olio.attributes.setWP(n.toInt)
              this.attrPanel.update()
            }
            case None =>
          }
        }
        
        //Fel
        if(clickEvent.source == this.attrPanel.felLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.attrPanel.felLabel.text)
          input match {
            case Some(n) => {
              this.olio.attributes.setFel(n.toInt)
              this.attrPanel.update()
            }
            case None =>
          }
        }
        
        //A
        if(clickEvent.source == this.attrPanel.aLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.attrPanel.aLabel.text)
          input match {
            case Some(n) => {
              this.olio.attributes.setA(n.toInt)
              this.attrPanel.update()
            }
            case None =>
          }
        }
        
        //W
        if(clickEvent.source == this.attrPanel.wLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.attrPanel.wLabel.text)
          input match {
            case Some(n) => {
              this.olio.attributes.setW(n.toInt)
              this.attrPanel.update()
              this.topPanel.update()
            }
            case None =>
          }
        }
        
        //M
        if(clickEvent.source == this.attrPanel.mLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.attrPanel.mLabel.text)
          input match {
            case Some(n) => {
              this.olio.attributes.setM(n.toInt)
              this.attrPanel.update()
            }
            case None =>
          }
        }
        
        //Mag
        if(clickEvent.source == this.attrPanel.magLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.attrPanel.magLabel.text)
          input match {
            case Some(n) => {
              this.olio.attributes.setMag(n.toInt)
              this.attrPanel.update()
            }
            case None =>
          }
        }
        
        //IP
        if(clickEvent.source == this.attrPanel.ipLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.attrPanel.ipLabel.text)
          input match {
            case Some(n) => {
              this.olio.attributes.setIP(n.toInt)
              this.attrPanel.update()
            }
            case None =>
          }
        }
        
        //FP
        if(clickEvent.source == this.attrPanel.fpLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.attrPanel.fpLabel.text)
          input match {
            case Some(n) => {
              this.olio.attributes.setFP(n.toInt)
              this.attrPanel.update()
              this.topPanel.update()
            }
            case None =>
          }
        }
        
      }
    }
  }
                
}