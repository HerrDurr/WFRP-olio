package olioMain

class Career {
  
  private var currentCareer = "None"
  
  private var previousCareer = "None"
  
  def current = this.currentCareer
  
  def previous = this.previousCareer
  
  def change(newCareer: String): Unit = {
    this.previousCareer = this.currentCareer
    this.currentCareer = newCareer
  }
  
}