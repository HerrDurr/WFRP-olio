package main

class Weapon(name: String, staticDmg: Boolean) {
  
  name match {
    case "Hand Weapon" =>
      val staticDmg = false
  }
  
  
  
/*  case object HandWeapon extends Weapon {
    this.staticDmg = false
    val isRanged = false
    
  }
  */
}