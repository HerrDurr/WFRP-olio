package main

import scala.math.min

class Olio(val name: String, val race: String) {
  
  
  /*
  val attributes = {
    Vector("WS","BS","S","T","Ag","Int","WP","Fel","A","W","SB","TB","M","Mag","IP","FP").map(x => (x,0))
                                                                                         .toMap
  }
  */
  
  private var woundsLeft = 0
  
  val attributes = new Attributes
  
  
  val career = new Career
  
  def currentWounds = this.woundsLeft
  
  def setCurrentWounds(w: Int) = {
    this.woundsLeft = min(w, this.attributes.wounds)
  }
  
  
  
  
  
  
  
  
  
  /**
   * Returns the creature's known skills and their levels.
   */
  def skills = {
    
  }
  
  
  def weapons {
    
  }
  
}