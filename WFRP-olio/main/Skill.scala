package main

import math.min
import scala.io.Source
import olioIO.DataIO
import data._

class Skill (name: String) extends Loadable(name) {
  
  //Skills can either be Basic skills or Advanced skills
  private var isBasic = false
  //Since everyone has basic skills at half level by default, we have to keep track which basic skills have been chosen.
  private var isTaken = false
  //Talents contains the list of talents which affect the Skill
  private var talents: Option[Vector[Talent]] = None
  //Skill level
  private var lvl = 0
  //Each skill has a governing attribute. This attribute also has an id number
  private var attribute = ("",0)
  //Name of the Skill
  private var n = ""
  //The description of the Skill
  private var descr = ""
  //Skills can be taken twice for the +10% trained bonus.
  private var isTrained = false
  
  //The skill's name is given when the skill is created
  this.n = name
  //The skills stats are loaded from data.
  val reader = Source.fromFile("data/skills.txt").reader()
  DataIO.loadItem(reader, this)
  
  /**
   * Returns the name of the skill when calling the object.
   */
  override def toString() = this.name
  
  
  /**
   * Returns true, if the skill is a Basic skill, false if the skill is an Advanced Skill
   */
  def skillBasic = this.isBasic
  
  /**
   * Returns true if the Olio has chosen this skill.
   */
  def skillTaken = this.isTaken
  
  /**
   * Returns skill's level
   */
  def skillLevel = this.lvl
  
  /**
   * Returns skill's name.
   */
  def skillName = this.n
  
  /**
   * Returns the skill's governing attribute's name (a String) and level (an Int).
   */
  def skillAttribute = this.attribute
  
  /**
   * Returns the talents which affect the skill.
   */
  def skillTalents = this.talents
  
  /**
   * Returns the description of the Skill.
   */
  def description = this.descr
  
  
  /**
   * Returns true if the skill has been trained (= taken twice).
   */
  def trained = this.isTrained
  
  
  /**
   * Sets the skill to basic or advanced.
   * @param basic true => basic; false => advanced
   */
  def setBasic(basic: Boolean) = {
    this.isBasic = basic
  }
  
  /**
   * Sets a skill as taken or not (required to keep track for basic skills).
   */
  def setTaken(taken: Boolean) = {
    this.isTaken = taken
  }
  
  /**
   * Set the skill's level to what you want. This method automatically adds +10 to the level if the skill is trained.
   */
  def setLevel (newLevel: Int) = {
    if (this.trained) this.lvl = min(100, newLevel + 10)
    else this.lvl = newLevel
  }
  
  /**
   * Change the skill's attribute's name and level to new ones.
   */
  def setAttribute (newName: String) = {
    var id = 0
    newName match {
      case "BS" => id = 1
      case "S" => id = 2
      case "T" => id = 3
      case "Ag" => id = 4
      case "Int" => id = 5
      case "WP" => id = 6
      case "Fel" => id = 7
      case "A" => id = 8
      case "W" => id = 9
      case "M" => id = 12
      case "Mag" => id = 13
      case "IP" => id = 14
      case "FP" => id = 15
    }
    this.attribute = (newName, id)
  }
  
  /**
   * Sets the related talents of the skill.
   */
  def setTalents(talentList: Traversable[String]) = {
      //TODO: Tee metodi, jonka avulla voi paivittaa skilliin liittyvia talentteja
  }
  
  /**
   * Sets the description of the skill.
   */
  def setDescription(input: String) = {
    this.descr = input
  }
  
  /**
   * Sets the skill to trained or untrained. Also adds 10 to the skill's level if the skill was not already trained, and vice versa if the training is taken away.
   */
  def setTrained(train: Boolean) = {
    if (train) {
      if (!this.trained) {
        this.isTrained = train
        this.setLevel(this.skillLevel)
      }
    } else {
      if (this.trained) {
        this.isTrained = train
        this.setLevel(this.skillLevel - 10)
      }
    }
  }
  
  
  
  
  
  /* Kyseessa Attributen id-numero, jolla sen loytaa
  /**
   * Change the skill's attribute's level to a new one.
   */
  def setAttribute (newLevel: Int) = {
    this.attribute = (this.attribute._1, newLevel)
  }
  * 
  */
  
  

  
}