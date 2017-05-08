package main

class Attribute {
  
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
  
}