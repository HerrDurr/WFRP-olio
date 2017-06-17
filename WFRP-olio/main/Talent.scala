package main

class Talent(name: String) extends Loadable(name) {
  
  private var attributeOption: Option[String] = None
  
  private var skillsOption: Option[Vector[Skill]] = None
  
  private var descr = ""
  
  //private var shortDescr = ""
  
  def affectedAttribute = this.attributeOption
  
  
  def affectedSkill = this.skillsOption
  
  
  def description = this.descr
  
  
  //def shortDescription = this.shortDescr
  
  
  def setAttribute(a: String/*, id: Int*/) = this.attributeOption = Option(a)
  
  
  def setDescription(input: String) = this.descr = input
  
  
  def setSkills(skills: Vector[String]) = ???//this.skillsOption = Option(skills)
  
  
  //def setShortDescr(input: String) = this.shortDescr = input
  
}