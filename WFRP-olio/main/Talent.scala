package main

import scala.collection.mutable.Buffer

class Talent(name: String, olio: Olio) extends Loadable(name) {
  
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
  
  
  def setSkills(skillNames: Vector[String]) = {
    val skillBuffer: Buffer[Skill] = Buffer()
    skillNames.foreach(n => skillBuffer += olio.allSkills.find(n == _.name).get)
    this.skillsOption = Option(skillBuffer.toVector)
  }
  
  
  //def setShortDescr(input: String) = this.shortDescr = input
  
}