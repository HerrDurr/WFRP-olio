package main

class Olio(name: String, race: String) {
  
  val attributes = {
    Vector("WS","BS","S","T","Ag","Int","WP","Fel","A","W","SB","TB","M","Mag","IP","FP").map(x => (x,0))
                                                                                         .toMap
  }
  
  
  val myCareer = new Career
  
  /*
   * Returns the current career.
   */
  def career = this.myCareer.current
  
  /*
   * Returns the previous career.
   */
  def exCareer = this.myCareer.previous
  
  /*
   * Returns the creature's known skills and their levels.
   */
  def skills = {
    
  }
  
  
  def weapons {
    
  }
  
}