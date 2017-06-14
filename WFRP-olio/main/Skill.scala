package main

class Skill (name: String) extends Loadable(name) {
  
  //Skills can either be Basic skills or Advanced skills
  private var isBasic = true
  //Talents contains the list of talents which affect the Skill
  private var talents: Option[Vector[Talent]] = None
  //Skill level
  private var lvl = 0
  //Each skill has a governing attribute. This attribute also has a level
  private var attribute = ("",0)
  //Name of the Skill
  private var n = ""
  
  //The skill's name is given when the skill is created
  this.n = name
  
  /**
   * Returns true, if the skill is a Basic skill, false if the skill is an Advanced Skill
   */
  def skillBasic = this.isBasic
  
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
   * Set the skill's level to what you want.
   */
  def setLevel (newLevel: Int) = {
    this.lvl = newLevel
  }
  
  /**
   * Change the skill's attribute's name and level to new ones.
   */
  def setAttribute (newName: String, newLevel: Int) = {
    this.attribute = (newName, newLevel)
  }
  
  /**
   * Change the skill's attribute's level to a new one.
   */
  def setAttribute (newLevel: Int) = {
    this.attribute = (this.attribute._1, newLevel)
  }
  
  
  //TODO: Tee metodi, jonka avulla voi paivittaa skilliin liittyvia talentteja
  
}