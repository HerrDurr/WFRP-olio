package main
import scala.io.Source

/*KUKKUUU!*/

/**
 * Weapons are stored in the weapons.txt file, which is read by the Weapon class.
 *
 *  The entries of a weapon text file (.txt) will be in the form:
 * 
 * #Weapon 1 Name
 * 
 * Group : 
 * Damage :
 * Range : ("-" or "n/N")
 * Qualities : a, b, c, d, ...
 * 
 * #Weapon 2 Name
 * 
 * etc.
 */

class Weapon {
  
  private var weaponGroup = ""
  
  private var weaponName = ""
  
  private var staticDmg: Option[Int] = None
  
  private var additiveDmg: Option[Int] = None
  
  private var weaponRange: Option[String] = None
  
  private var weaponQualities: Option[Vector[String]] = None
  
  
  def name = this.weaponName
  
  
  def changeName(input: String): Unit = {
    this.weaponName = input
  }
  
  
  def group = this.weaponGroup
  
  
  def changeGroup(input: String): Unit = {
    this.weaponGroup = input
  }
  
  
  /**
   * The Olio class will call this, giving its own SB as the parameter, and the method will return
   * the damage rating wrapped in an Option, if the weapon causes damage.
   */
  def damage(SB: Int): Option[Int] = {
    if(this.staticDmg.isDefined)
      return this.staticDmg
    if(this.additiveDmg.isDefined)
      return Option(SB + this.additiveDmg.get)
    else None
  }
  
  
  /**
   * The WeaponIO object will call this method with the damage input from the weapon source file.
   * There are three possible kinds of weapon damage:
   * No damage: "-"
   * Static damage: "n" (n: any lone integer)
   * Additive damage: "SB + n" or simply "SB"
   */
  def setDamage(input: String) = {
    if (input != "-")
    {
      if (!input.contains("SB"))
        this.staticDmg = Option(input.trim().toInt)
      else
        this.additiveDmg = {
          if (input.length() == 2) Option(0)
          else Option( input.dropWhile( c => c != '+' ).trim().toInt )
        }
    }    
  }
  
  
  
  def isRanged = this.weaponRange.isDefined
  
  
  def range = this.weaponRange.getOrElse("-")
  
  
  
  def setRange(input: String): Unit = {
    val x = input.trim()
    if (x.head != '-') 
      this.weaponRange = Option(x)
  }
  
  
  
  def qualities = this.weaponQualities.getOrElse(Vector("-"))
  
  
  
  def setQualities(input: String): Unit = {
    val x = input.trim()
    if (x.head != '-')
      this.weaponQualities = Option(x.split(",").toVector)
  }
  
  
  /*
  name match {
    case "Hand Weapon" =>
      val staticDmg = false
  }
  
  case object HandWeapon extends Weapon {
    this.staticDmg = false
    val isRanged = false
    
  }
  */
}