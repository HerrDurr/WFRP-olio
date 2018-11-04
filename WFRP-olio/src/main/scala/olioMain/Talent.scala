package olioMain

import scala.collection.mutable.Buffer
import scala.io.Source
import scala.io.Codec
import java.nio.charset.CodingErrorAction
import olioIO.DataIO

class Talent(name: String) extends Loadable(name) {
  
  private var attributesOption: Option[Vector[String]] = None
  
  private var skillsOption: Option[Vector[String]] = None
  
  private var weaponsOption: Option[String] = None
  
  private var descr = ""
  
  private var fSkillMods: Option[Tuple2[Vector[Skill], Int]] = None
  private var fDamageMods: Option[Char] = None 
  
  
  val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
  val path = this.getClass.getClassLoader.getResource("").getPath
  val file = Source.fromFile(path + "/data/talents.txt")(decoder)
  try {
    DataIO.loadItem(file.reader(), this)
  } finally {
    file.close()
  }
  
  
  def affects = {
    var res = "-"
    if (this.affectedAttributes.isDefined)
    {
      res += " Characteristics ("
      val attr = this.affectedAttributes.get
      attr.dropRight(1).foreach { res += _ + ", " }
      res += attr.takeRight(1)(0) + ")"
    }
    if (this.affectedSkills.isDefined)
    {
      res += " Skills ("
      val skls = this.affectedSkills.get
      skls.dropRight(1).foreach { res += _ + ", " }
      res += skls.takeRight(1)(0) + ")"
    }
    if (this.affectedWeapons.isDefined) res += " " + this.affectedWeapons.get
    res
  }
  
  //private var shortDescr = ""
  
  def affectedAttributes = this.attributesOption
  
  
  def affectedSkills = this.skillsOption
  
  
  def affectedWeapons = this.weaponsOption
  
  
  def description = this.descr
  
  
  //def shortDescription = this.shortDescr
  
  
  def setAttributes(attributeNames: Vector[String]) = this.attributesOption = Option(attributeNames)
  
  
  def setDescription(input: String) = this.descr = input
  
  
  def setSkills(skillNames: Vector[String]) = this.skillsOption = Option(skillNames)
  
  
  def setWeapons(weapons: String) = this.weaponsOption = Option(weapons) 
  
  
  /**
   * Returns the skill modifier of the talent. Kludge: have to give olio as parameter.
   */
  def modSkills(olio: Olio): Tuple2[Vector[Skill], Int] = {
    if (this.fSkillMods.isEmpty)
    {
      this.name match 
      {
        case "Aethyric Attunement" =>
          this.fSkillMods = Option(olio.allSkills.filter { s => s.name == "Channeling" || s.name == "Magical Sense" }, 10)
        case "Dealmaker" =>
          this.fSkillMods = Option(olio.allSkills.filter { s => s.name == "Evaluate" || s.name == "Haggle" }, 10)
        case "Excellent Vision" =>
          this.fSkillMods = Option(olio.allSkills.filter { s => s.name == "Lip Reading" }, 10)
        case "Keen Senses" =>
          this.fSkillMods = Option(olio.allSkills.filter { s => s.name == "Perception" }, 20)
        case "Linguistics" =>
          this.fSkillMods = Option(olio.allSkills.filter { s => s.name == "Read/Write" || s.name == "Speak Language*" }, 10)
        case "Menacing" =>
          this.fSkillMods = Option(olio.allSkills.filter { s => s.name == "Intimidate" || s.name == "Torture" }, 10)
        case "Orientation" =>
          this.fSkillMods = Option(olio.allSkills.filter { s => s.name == "Navigation" }, 10)
        case "Seasoned Traveller" =>
          this.fSkillMods = Option(olio.allSkills.filter { s => s.name == "Common Knowledge*" || s.name == "Speak Language*" }, 10)
        case "Surgery" =>
          this.fSkillMods = Option(olio.allSkills.filter { s => s.name == "Heal" }, 10)
        case "Super Numerate" =>
          this.fSkillMods = Option(olio.allSkills.filter { s => s.name == "Gamble" || s.name == "Navigation" }, 10)
        case "Trick Riding" =>
          this.fSkillMods = Option(olio.allSkills.filter { s => s.name == "Ride" }, 10)
        case some =>
          ; // ingenting
      }
    }
    this.fSkillMods.getOrElse(Vector[Skill](), 0)
  }
  
    
  /**
   * If the talent permanently modifies the damage of certain weapons, return a tag for
   * those weapons.
   */
  def modDamage: Char = 
  {
    if (this.fDamageMods.isEmpty)
    {
      this.name match 
      {
        case "Mighty Missile" =>
          this.fDamageMods = Option('s')
        case "Mighty Shot" =>
          this.fDamageMods = Option('r')
        case "Street Fighting" =>
          this.fDamageMods = Option('u')
        case "Strike Mighty Blow" =>
          this.fDamageMods = Option('m')
        case some =>
          ; // iiinnngentiing
      }
    }
    this.fDamageMods.getOrElse('-')
  }
  
  
  /**
   * Sets skill and damage modifiers for an olio.
   */
  def setModifiers(olio: Olio, talentAdded: Boolean) = {
    var weaponMod = 1
    
    if (talentAdded)
    {
      if (!this.modSkills(olio)._1.isEmpty) 
        this.modSkills(olio)._1.foreach { s => if (s.modifier == 0) s.setModifier(this.modSkills(olio)._2) }
    } else
    {
      if (!this.modSkills(olio)._1.isEmpty) 
        this.modSkills(olio)._1.foreach { skill => if (skill.modifier != 0) skill.setModifier(- this.modSkills(olio)._2) }
      weaponMod = 0
    }
    if (this.modDamage == 'u') olio.weapons.filter(_.name == "Unarmed").foreach(_.setModifier(weaponMod))
    if (this.modDamage == 'r') olio.weapons.filter(_.range.head != '-').foreach(_.setModifier(weaponMod))
    if (this.modDamage == 'm') olio.weapons.filter(w => w.range.head == '-' && w.name != "Unarmed")
                                           .foreach(_.setModifier(weaponMod))
  }
  
  //def setShortDescr(input: String) = this.shortDescr = input
  
}