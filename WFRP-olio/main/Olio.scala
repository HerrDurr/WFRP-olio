package main

class Olio(val name: String, val race: String) {
  
  
  /*
  val attributes = {
    Vector("WS","BS","S","T","Ag","Int","WP","Fel","A","W","SB","TB","M","Mag","IP","FP").map(x => (x,0))
                                                                                         .toMap
  }
  */
  
  val attributes = new Array[Int](16)
  
  
  val career = new Career
  
  /**
   * Attribute numbering explained:
   * WS=0, BS=1, S=2, T=3, Ag=4, Int=5, WP=6, Fel=7,
   * A=8, W=9, SB=10, TB=11, M=12, Mag=13, IP=14, FP=15
   */
  def setAttribute(attr: Int, value: Int) = this.attributes(attr) = value
  
  /**
   * The following methods can be used to check attribute values.
   */
  def weaponSkill = this.attributes(0)
  def ballisticSkill = this.attributes(1)
  def strength = this.attributes(2)
  def toughness = this.attributes(3)
  def agility = this.attributes(4)
  def intelligence = this.attributes(5)
  def willPower = this.attributes(6)
  def fellowship = this.attributes(7)
  def attacks = this.attributes(8)
  def wounds = this.attributes(9)
  def strengthBonus = this.attributes(10)
  def toughnessBonus = this.attributes(11)
  def movement = this.attributes(12)
  def magic = this.attributes(13)
  def insanityPoints = this.attributes(14)
  def fatePoints = this.attributes(15)
  
  /**
   * The following methods can be used to easily set a new value for a specific attribute.
   */
  def setWS(value: Int) = this.setAttribute(0, value)
  
  def setBS(value: Int) = this.setAttribute(1, value)
  
  def setS(value: Int) = {
    this.setAttribute(2, value)
    this.setAttribute(10, value % 10)
  }
  
  def setT(value: Int) = {
    this.setAttribute(3, value)
    this.setAttribute(11, value % 10)
  }
  
  def setAg(value: Int) = this.setAttribute(4, value)
  
  def setInt(value: Int) = this.setAttribute(5, value)
  
  def setWP(value: Int) = this.setAttribute(6, value)
  
  def setFel(value: Int) = this.setAttribute(7, value)
  
  def setA(value: Int) = this.setAttribute(8, value)
  
  def setW(value: Int) = this.setAttribute(9, value)
  
  def setM(value: Int) = this.setAttribute(12, value)
  
  def setMag(value: Int) = this.setAttribute(13, value)
  
  def setIP(value: Int) = this.setAttribute(14, value)
  
  def setFP(value: Int) = this.setAttribute(15, value)
  
  
  
  /**
   * Returns the creature's known skills and their levels.
   */
  def skills = {
    
  }
  
  
  def weapons {
    
  }
  
}