package main

import scala.math.min
import scala.math.max
import scala.collection.mutable.Buffer

class Olio(n: String, r: String) {
  
  private var raceChangeable = ""
  
  private var nameChangeable = ""
  
  private var woundsLeft = 0
  
  private var fortuneLeft = 0
  
  private val skillBuffer: Buffer[Skill] = Buffer()
  
  val weapons: Array[Weapon] = Array(new Weapon("None"), new Weapon("None"), new Weapon("None"))
  
  val attributes = new Attributes
  
  val career = new Career
  
  
  def fortunePoints = this.fortuneLeft
  
  def race = this.raceChangeable
  
  def name = this.nameChangeable
  
  def setName(nn: String) = {
    this.nameChangeable = nn
  }
  
  
  def setRace(rr: String) = {
    this.raceChangeable = rr
  }
  
  
  def setFortune(fp: Int) = {
    this.fortuneLeft = max( 0, min(fp, this.attributes.fatePoints) )
  }
  
  
  this.setName(n) //On setup
  this.setRace(r) //On setup
  
  
  def currentWounds = this.woundsLeft
  
  def setCurrentWounds(w: Int) = {
    this.woundsLeft = max( 0, min( w, this.attributes.wounds ) )
  }
  
  
  
  
  /*
  def weapons = {
    this.weaponList.getOrElse(Vector(new Weapon("Improvised")))
  }
  * 
  */
  
  
  /**
   * Adds a skill to the olio. If the olio already has the skill, sets the skill to trained (+10%).
   */
  def addSkill(skill: Skill) = {
    this.skillBuffer += skill
  }
  
  
  /**
   * Removes a skill from the olio.
   */
  def removeSkill(skill: Skill) = {
    this.skillBuffer -= skill
  }
  
  
  /**
   * Returns the creature's known skills.
   */
  def skills = {
    this.skillBuffer.toVector
  }
  
  
  
}