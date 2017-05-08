package main

class Olio(name: String, race: String) {
  
  
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
  
  /*
   * Returns the creature's known attributes and their levels.
   */
  def attributes {
    
    
  }
  
  def weapons {
    
  }
  
}