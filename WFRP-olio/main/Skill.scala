package main

import math.min
import math.max
import scala.io.Source
import scala.io.Codec
import java.nio.charset.CodingErrorAction
import olioIO.DataIO
import data._
import scala.collection.mutable.Buffer

class Skill (name: String) extends Loadable(name) {
  
  //Skills can either be Basic skills or Advanced skills
  private var isBasic = false
  //Due to basic skills and skill mastery (+10 and +20 at subsequent picks), a counter for the times the skill has been chosen is needed.
  private var gainedCount = 0
  //Talents contains the list of talents which affect the Skill
  private var talents: Option[Vector[String]] = None
  //Skill level
  private var lvl = 0
  //Each skill has a governing attribute. This attribute also has an id number
  private var attribute = ("",0)
  //Name of the Skill
  private var n = ""
  //The description of the Skill
  private var descr = ""
  
  //The skill's name is given when the skill is created
  this.n = name
  //The skills stats are loaded from data.
  val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
  val path = this.getClass.getClassLoader.getResource("").getPath
  val file = Source.fromFile(path + "/data/skills.txt")(decoder)
  try {
    DataIO.loadItem(file.reader(), this)
  } finally {
    file.close()
  }
  
  
  
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
  def timesGained = this.gainedCount
  
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
   * Returns the names of the talents which affect the skill.
   */
  def skillTalents = this.talents
  
  
  /**
   * Returns the names of the talents which affect the skill as a single String
   */
  def skillTalentsString = {
    var res = ""
    if (this.talents.isEmpty) res = "-"
    else
    {
      val tals = this.talents.get
      tals.dropRight(1).foreach(res += _ + ", ")
      res += tals.takeRight(1)(0)
    }
    res
  }
  
  
  /**
   * Returns the description of the Skill.
   */
  def description = this.descr
  
  
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
  def setGained(times: Int) = {
    this.gainedCount = min(3, max(0, times))
  }
  
  /**
   * Set the skill's level to what you want. This method automatically adds skill mastery, sets a non-chosen advanced skill at 0,
   * and halves the level of a non-chosen basic skill.
   * @param newLevel Basically the level of the skill's corresponding Attribute.
   */
  def setLevel(newLevel: Int) = {
    if (isBasic && gainedCount == 0) this.lvl = newLevel / 2
    else if (gainedCount > 0)
    {
      if (this.gainedCount == 2) this.lvl = min(100, newLevel + 10)
      else if (this.gainedCount == 3) this.lvl = min(100, newLevel + 20)
      else this.lvl = newLevel
    } else this.lvl = 0
  }
  
  /**
   * Change the skill's attribute's name and level to new ones.
   */
  def setAttribute (newName: String) = {
    var id = 0
    newName match {
      case "S" => id = 2
      case "T" => id = 3
      case "Ag" => id = 4
      case "Int" => id = 5
      case "WP" => id = 6
      case "Fel" => id = 7
    }
    this.attribute = (newName, id)
  }
  
  /**
   * Sets the related talents of the skill.
   */
  def setTalents(talentNames: Traversable[String]) = this.talents = Option(talentNames.toVector)
  
  /**
   * Sets the description of the skill.
   */
  def setDescription(input: String) = {
    this.descr = input
  }
  
  /**
   * Trains or untrains the the skill (timesGained 0 - 3). Also uses setLevel to keep track of the effect.
   * @param train Set to true if you are increasing the training level, false if you are decreasing it.
   */
  def train(train: Boolean) = {
    
    if (train) { //If the skill is taken/mastered
      
      /*
      if (this.timesGained < 1) {
        this.setGained(1)
      } else if (this.timesGained == 1) {
        this.setGained(2)
      } else if (this.timesGained == 2) {
        this.setGained(3)
      }
      * 
      */
      this.setGained(this.timesGained + 1)
      
      this.setLevel(this.skillLevel)
      
    } else { //If the skill is taken away or its mastery weakened (for UX purposes)
      
      this.setGained(this.timesGained - 1)
      
      if (this.timesGained == 2) this.setLevel(this.skillLevel - 20)
      else if (this.timesGained == 1) this.setLevel(this.skillLevel - 10)
      else this.setLevel(this.skillLevel)
      
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