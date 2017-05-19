package main

import scala.math.min
import scala.math.max

class Olio(n: String, r: String) {
  
  private var raceChangeable = ""
  
  private var nameChangeable = ""
  
  private var woundsLeft = 0
  
  private var fortuneLeft = 0
  
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
  
  
  
  
  
  
  
  
  
  /**
   * Returns the creature's known skills and their levels.
   */
  def skills = {
    
  }
  
  
  def weapons {
    
  }
  
}