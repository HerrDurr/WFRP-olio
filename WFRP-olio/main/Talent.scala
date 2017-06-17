package main

class Talent(name: String) extends Loadable(name) {
  
  private var attributeOption: Option[(String, Int)] = None
  
  private var skillOption: Option[Skill] = None
  
  private var descr = ""
  
  private var shortDescr = ""
  
  def affectedAttribute = this.attributeOption
  
  
  def affectedSkill = this.skillOption
  
  
  def description = this.descr
  
  
  def shortDescription = this.shortDescr
  
  
  def setAttribute(a: String, id: Int) = this.attributeOption = Option(a, id)
  
  
  def setDescription(input: String) = this.descr = input
  
  
  def setShortDescr(input: String) = this.shortDescr = input
  
}