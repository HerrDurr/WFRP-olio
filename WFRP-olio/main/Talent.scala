package main

import scala.collection.mutable.Buffer

class Talent(name: String) extends Loadable(name) {
  
  private var attributeOption: Option[String] = None
  
  private var skillsOption: Option[Vector[String]] = None
  
  private var weaponsOption: Option[String] = None
  
  private var descr = ""
  
  //private var shortDescr = ""
  
  def affectedAttribute = this.attributeOption
  
  
  def affectedSkill = this.skillsOption
  
  
  def affectedWeapons = this.weaponsOption
  
  
  def description = this.descr
  
  
  //def shortDescription = this.shortDescr
  
  
  def setAttribute(a: String/*, id: Int*/) = this.attributeOption = Option(a)
  
  
  def setDescription(input: String) = this.descr = input
  
  
  def setSkills(skillNames: Vector[String]) = this.skillsOption = Option(skillNames)
  
  
  def setWeapons(weapons: String) = this.weaponsOption = Option(weapons) 
  
  
  //def setShortDescr(input: String) = this.shortDescr = input
  
}