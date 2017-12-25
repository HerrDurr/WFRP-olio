package main

import scala.math.min
import scala.math.max
import scala.collection.mutable.Buffer
import scala.io.Source
import scala.io.Codec
import java.nio.charset.CodingErrorAction
import olioIO.DataIO
//import data._
import java.awt.Color

class Olio {
  
  private var raceChangeable = ""
  
  private var nameChangeable = ""
  
  private var saveName = ""
  
  private var woundsLeft = 0
  
  private var fortuneLeft = 0
  
  private var col = new Color(200, 200, 200)
  
  private val skillBuffer: Buffer[Skill] = Buffer()
  
  private val talentBuffer: Buffer[Talent] = Buffer()
  
  val weapons: Array[Weapon] = Array(new Weapon("Unarmed"), new Weapon("None"), new Weapon("None"))
  
  val attributes = new Attributes
  
  val career = new Career
  
  val armourPoints = Array(0, 0, 0, 0, 0, 0)
  
  var comments = ""
  
  
  /**
   * A Vector containing all possible Talents.
   */
  val allTalents = {
    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
    val path = this.getClass.getClassLoader.getResource("").getPath
    val file = Source.fromFile(path + "/data/talents.txt")(decoder)
    try {
      DataIO.loadNames(file.reader()).map( new Talent(_) )
    } finally {
      file.close()
    }
  }
  
  /**
   * A Vector containing all possible Skills.
   */
  val allSkills = {
    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
    val path = this.getClass.getClassLoader.getResource("").getPath
    val file = Source.fromFile(path + "/data/skills.txt")(decoder)
    try {
      DataIO.loadNames(file.reader()).map( new Skill(_) )
    } finally {
      file.close()
    }
  }
  
  //OLD IMPLEMENTATION   DataIO.loadBasicSkills(Source.fromFile("data/skills.txt")(decoder).reader()).foreach(skillBuffer += _)
  
  //Gives the Olio all Basic Skills on creation.
  this.allSkills.filter(_.skillBasic).foreach(this.skillBuffer += _)
  
  def colour = this.col
  
  def fortunePoints = this.fortuneLeft
  
  def race = this.raceChangeable
  
  def name = this.nameChangeable
  
  def saveFileName = this.saveName
  
  def setSaveFile(fileName: String) = this.saveName = fileName
  
  def setColour(newColour: Color) = this.col = newColour
  
  def setName(nn: String) = {
    this.nameChangeable = nn
  }
  
  
  def setRace(rr: String) = {
    this.raceChangeable = rr
  }
  
  
  def setFortune(fp: Int) = {
    this.fortuneLeft = max( 0, min(fp, this.attributes.fatePoints) )
  }
  
  
  def currentWounds = this.woundsLeft
  
  def setCurrentWounds(w: Int) = {
    this.woundsLeft = max( 0, min( w, this.attributes.wounds ) )
  }
  
  
  
  
  /*
  def weapons = {
    this.weaponList.getOrElse(Vector(new Weapon("Improvised")))
  }
  * 
  */
  
  
  /**
   * Adds a skill to the olio. If the olio already has the skill, sets the skill to trained (+10%).
   */
  def addSkill(skill: Skill) = {
    val alreadyHere = this.skillBuffer.find(_ == skill)
    if (!alreadyHere.isDefined) this.skillBuffer += skill
    skill.train(true)
  }
  
  
  /**
   * Removes or unmasters a skill from the olio.
   */
  def untrainSkill(skill: Skill) = {
    val alreadyHere = this.skillBuffer.find(_ == skill)
    if (alreadyHere.isDefined)
    {
      val existingSkill = alreadyHere.get
      
      existingSkill.train(false)
      if (!existingSkill.skillBasic && existingSkill.timesGained == 0) this.skillBuffer -= existingSkill
      
    }
  }
  
  
  /**
   * Returns the creature's known skills.
   */
  def skills = {
    this.skillBuffer.toVector
  }
  
  
  /**
   * Checks if the Olio knows the Skill given as parameter
   */
  def hasSkill(skill: Skill) = {
    this.skills.contains(skill)
  }
  
  
  /**
   * Returns a String that lists all talents that the Olio knows that are related to the skill
   * that was given as a parameter.
   */
  def relatedKnownTalents(skill: Skill) = {
    var res = ""
    val skillTalents = skill.skillTalents
    if (skillTalents.isEmpty) res = "-"
    else
    {
      val relTalents = skillTalents.get
      val known = relTalents.filter(t => this.talents.exists(_.name == t))
      if (known.isEmpty) res = "-"
      else
      {
        known.dropRight(1).foreach(res += _ + ", ")
        res += known.takeRight(1)(0)
      }
    }
    res
  }
  
  
  /**
   * Returns the creature's known talents.
   */
  def talents = {
    this.talentBuffer.toVector
  }
  
  
  /**
   * Checks if the Olio knows the Talent given as parameter
   */
  def hasTalent(talent: Talent) = {
    this.talents.contains(talent)
  }
  
  
  
  def addTalent(talent: Talent) = {
    if (!this.hasTalent(talent))
      this.talentBuffer += talent
  }
  
  
  def removeTalent(talent: Talent) = {
    if (this.hasTalent(talent))
      this.talentBuffer -= talent
  }
  
  
  /**
   * Returns a nice list-form String of the Loadable objects given as a Vector parameter.
   */
  def loadablesString(loadables: Vector[Loadable]) = {
    var res = ""
    if (!loadables.isEmpty)
    {
      loadables.dropRight(1).foreach(res += _.name + ", ")
      res += loadables.takeRight(1)(0).name
    } else res = "-"
    res
  }
  
  
}