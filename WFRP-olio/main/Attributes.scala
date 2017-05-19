package main

class Attributes {
  
  
  /**
   * Attribute numbering explained:
   * WS=0, BS=1, S=2, T=3, Ag=4, Int=5, WP=6, Fel=7,
   * A=8, W=9, SB=10, TB=11, M=12, Mag=13, IP=14, FP=15
   */
  private val values = new Array[Int](16)
  
  
  /**
   * The following methods can be used to check attribute values.
   */
  def weaponSkill = this.values(0)
  def ballisticSkill = this.values(1)
  def strength = this.values(2)
  def toughness = this.values(3)
  def agility = this.values(4)
  def intelligence = this.values(5)
  def willPower = this.values(6)
  def fellowship = this.values(7)
  def attacks = this.values(8)
  def wounds = this.values(9)
  def strengthBonus = this.values(10)
  def toughnessBonus = this.values(11)
  def movement = this.values(12)
  def magic = this.values(13)
  def insanityPoints = this.values(14)
  def fatePoints = this.values(15)
  
  
  /**
   * Attribute numbering explained:
   * WS=0, BS=1, S=2, T=3, Ag=4, Int=5, WP=6, Fel=7,
   * A=8, W=9, SB=10, TB=11, M=12, Mag=13, IP=14, FP=15
   */
  //private def setAttribute(attr: Int, value: Int) = this.values(attr) = value
  
  def setAttribute(attr: String, value: Int) = {
    
    attr.trim().toLowerCase() match {
      case "ws" =>
        this.values(0) = value
      case "bs" =>
        this.values(1) = value
      case "s" =>
        this.values(2) = value
        this.values(10) = value % 10
      case "t" =>
        this.values(3) = value
        this.values(11) = value % 10
      case "ag" =>
        this.values(4) = value
      case "int" =>
        this.values(5) = value
      case "wp" =>
        this.values(6) = value
      case "fel" =>
        this.values(7) = value
      case "a" =>
        this.values(8) = value
      case "w" =>
        this.values(9) = value
      case "m" =>
        this.values(12) = value
      case "mag" =>
        this.values(13) = value
      case "ip" =>
        this.values(14) = value
      case "fp" =>
        this.values(15) = value
      case other =>
        
    }
    
  }
  
  /**
   * The following methods can be used to easily set a new value for a specific attribute.
   */
  /* NOT IN USE CURRENTLY
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
  */
  
  
  
  
  
  
  /* old Attribute implementation
  private var currentLvl = 0
  private var isMain = false
  private var canAdvance = true
  
  
  def setMain: Unit = {
    this.isMain = true 
  }
  
  
  def lock: Unit = {
    this.canAdvance = false
  }
  
  
  def lvl = this.currentLvl
  
  
  def set(newLvl: Int): Unit = {
    this.currentLvl = newLvl
  }
  
  
  def advance: Unit = {
    if (this.canAdvance) {
      if (this.isMain) this.currentLvl += 5
      else this.currentLvl += 1
    }
  }
  
  
  case object WS extends Attribute {
    this.setMain
  }
  
  case object BS extends Attribute {
    this.setMain
  }
  
  case object S extends Attribute {
    this.setMain
  }
  
  case object T extends Attribute {
    this.setMain
  }
  
  case object Ag extends Attribute {
    this.setMain
  }
  
  case object Int extends Attribute {
    this.setMain
  }
  
  case object WP extends Attribute {
    this.setMain
  }
  
  case object Fel extends Attribute {
    this.setMain
  }
  
  case object A extends Attribute {
    
  }
  
  case object W extends Attribute {
    
  }
  
  case object SB extends Attribute {
   this.lock 
  }
  
  case object TB extends Attribute {
   this.lock 
  }
  
  case object M extends Attribute {
   this.lock 
  }
  
  case object Mag extends Attribute {
   
  }
  
  case object IP extends Attribute {
    
  }
  
  case object FP extends Attribute {
   this.lock 
  }
  */
  
}