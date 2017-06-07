package olioGUI

import scala.swing._
import main._
import scala.swing.event._

class AttributePanel(val attr: Attributes) extends GridPanel(4,8) {
  
  
  val wS = new Label("WS")
  val bS = new Label("BS")
  val s = new Label("S")
  val t = new Label("T")
  val ag = new Label("Ag")
  val int = new Label("Int")
  val wP = new Label("WP")
  val fel = new Label("Fel")
  
  val a = new Label("A")
  val w = new Label("W")
  val sB = new Label("SB")
  val tB = new Label("TB")
  val m = new Label("M")
  val mag = new Label("Mag")
  val iP = new Label("IP")
  val fP = new Label("FP")
  
  
  val wsLabel = new Label("")
  val bsLabel = new Label("")
  val sLabel = new Label("")
  val tLabel = new Label("")
  val agLabel = new Label("")
  val intLabel = new Label("")
  val wpLabel = new Label("")
  val felLabel = new Label("")
  
  val aLabel = new Label("")
  val wLabel = new Label("")
  val sbLabel = new Label("")
  val tbLabel = new Label("")
  val mLabel = new Label("")
  val magLabel = new Label("")
  val ipLabel = new Label("")
  val fpLabel = new Label("")
  
  def update() = {
    this.wsLabel.text = this.attr.weaponSkill.toString()
    this.bsLabel.text = this.attr.ballisticSkill.toString()
    this.sLabel.text = this.attr.strength.toString()
    this.tLabel.text = this.attr.toughness.toString()
    this.agLabel.text = this.attr.agility.toString()
    this.intLabel.text = this.attr.intelligence.toString()
    this.wpLabel.text = this.attr.willPower.toString()
    this.felLabel.text = this.attr.fellowship.toString()
    this.aLabel.text = this.attr.attacks.toString()
    this.wLabel.text = this.attr.wounds.toString()
    this.sbLabel.text = this.attr.strengthBonus.toString()
    this.tbLabel.text = this.attr.toughnessBonus.toString()
    this.mLabel.text = this.attr.movement.toString()
    this.magLabel.text = this.attr.magic.toString()
    this.ipLabel.text = this.attr.insanityPoints.toString()
    this.fpLabel.text = this.attr.fatePoints.toString()
  }
  
  /* ei toimi jostain syystä
  this.contents ++ Set(wS, bS, s, t, ag, int, wP, fel,
                       wsLabel, bsLabel, sLabel, tLabel, agLabel, intLabel, wpLabel, felLabel,
                       a, w, sB, tB, m, mag, iP, fP,
                       aLabel, wLabel, sbLabel, tbLabel, mLabel, magLabel, ipLabel, fpLabel)
  */
  
  this.contents += wS
  this.contents += bS
  this.contents += s
  this.contents += t
  this.contents += ag
  this.contents += int
  this.contents += wP
  this.contents += fel
  this.contents += wsLabel
  this.contents += this.bsLabel
  this.contents += this.sLabel
  this.contents += this.tLabel
  this.contents += this.agLabel
  this.contents += this.intLabel
  this.contents += this.wpLabel
  this.contents += this.felLabel
  this.contents += this.a
  this.contents += this.w
  this.contents += this.sB
  this.contents += this.tB
  this.contents += this.m
  this.contents += this.mag
  this.contents += this.iP
  this.contents += this.fP
  this.contents += this.aLabel
  this.contents += this.wLabel
  this.contents += this.sbLabel
  this.contents += this.tbLabel
  this.contents += this.mLabel
  this.contents += this.magLabel
  this.contents += this.ipLabel
  this.contents += this.fpLabel
  
  this.update()
  
  /*
  this.listenTo(this.wsLabel.mouse.clicks, this.bsLabel.mouse.clicks, this.sLabel.mouse.clicks, this.tLabel.mouse.clicks,
                this.agLabel.mouse.clicks, this.intLabel.mouse.clicks, this.wpLabel.mouse.clicks, this.felLabel.mouse.clicks,
                this.aLabel.mouse.clicks, this.wLabel.mouse.clicks, this.mLabel.mouse.clicks, this.magLabel.mouse.clicks,
                this.ipLabel.mouse.clicks, this.fpLabel.mouse.clicks)
  
  this.reactions += {
    
    case clickEvent: MouseClicked => {
      if(clickEvent.clicks > 1)
      {
        
        //WS
        if(clickEvent.source == this.wsLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.wsLabel.text)
          input match {
            case Some(n) => {
              this.attr.setWS(n.toInt)
              this.update()
            }
            case None =>
          }
        }
        
        //BS
        if(clickEvent.source == this.bsLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.bsLabel.text)
          input match {
            case Some(n) => {
              this.attr.setBS(n.toInt)
              this.update()
            }
            case None =>
          }
        }
        
        //S
        if(clickEvent.source == this.sLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.sLabel.text)
          input match {
            case Some(n) => {
              this.attr.setS(n.toInt)
              this.update()
            }
            case None =>
          }
        }
        
        //T
        if(clickEvent.source == this.tLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.tLabel.text)
          input match {
            case Some(n) => {
              this.attr.setT(n.toInt)
              this.update()
            }
            case None =>
          }
        }
        
        //Ag
        if(clickEvent.source == this.agLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.agLabel.text)
          input match {
            case Some(n) => {
              this.attr.setAg(n.toInt)
              this.update()
            }
            case None =>
          }
        }
        
        //Int
        if(clickEvent.source == this.intLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.intLabel.text)
          input match {
            case Some(n) => {
              this.attr.setInt(n.toInt)
              this.update()
            }
            case None =>
          }
        }
        
        //WP
        if(clickEvent.source == this.wpLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.wpLabel.text)
          input match {
            case Some(n) => {
              this.attr.setWP(n.toInt)
              this.update()
            }
            case None =>
          }
        }
        
        //Fel
        if(clickEvent.source == this.felLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.felLabel.text)
          input match {
            case Some(n) => {
              this.attr.setFel(n.toInt)
              this.update()
            }
            case None =>
          }
        }
        
        //A
        if(clickEvent.source == this.aLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.aLabel.text)
          input match {
            case Some(n) => {
              this.attr.setA(n.toInt)
              this.update()
            }
            case None =>
          }
        }
        
        //W
        if(clickEvent.source == this.wLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.wLabel.text)
          input match {
            case Some(n) => {
              this.attr.setW(n.toInt)
              this.update()
            }
            case None =>
          }
        }
        
        //M
        if(clickEvent.source == this.mLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.mLabel.text)
          input match {
            case Some(n) => {
              this.attr.setM(n.toInt)
              this.update()
            }
            case None =>
          }
        }
        
        //Mag
        if(clickEvent.source == this.magLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.magLabel.text)
          input match {
            case Some(n) => {
              this.attr.setMag(n.toInt)
              this.update()
            }
            case None =>
          }
        }
        
        //IP
        if(clickEvent.source == this.ipLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.ipLabel.text)
          input match {
            case Some(n) => {
              this.attr.setIP(n.toInt)
              this.update()
            }
            case None =>
          }
        }
        
        //FP
        if(clickEvent.source == this.fpLabel)
        {
          val input = Dialog.showInput(this, "", initial = this.fpLabel.text)
          input match {
            case Some(n) => {
              this.attr.setFP(n.toInt)
              this.update()
            }
            case None =>
          }
        }
        
      }
    }
    
  }
  */
  
}