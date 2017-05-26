package olioGUI

import scala.swing._
import main._
import scala.swing.event._

class AttributePanel(attr: Attributes) extends GridPanel(4,8) {
  
  
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
  
}