package main

import scala.collection.mutable.Buffer
import scala.io.Source
import scala.io.Codec
import java.nio.charset.CodingErrorAction
import olioIO.DataIO
import data._

class Talent(name: String) extends Loadable(name) {
  
  private var attributesOption: Option[Vector[String]] = None
  
  private var skillsOption: Option[Vector[String]] = None
  
  private var weaponsOption: Option[String] = None
  
  private var descr = ""
  
  
  val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
  val path = this.getClass.getClassLoader.getResource("").getPath
  val file = Source.fromFile(path + "/data/talents.txt")(decoder)
  try {
    DataIO.loadItem(file.reader(), this)
  } finally {
    file.close()
  }
  
  
  //private var shortDescr = ""
  
  def affectedAttributes = this.attributesOption
  
  
  def affectedSkill = this.skillsOption
  
  
  def affectedWeapons = this.weaponsOption
  
  
  def description = this.descr
  
  
  //def shortDescription = this.shortDescr
  
  
  def setAttributes(attributeNames: Vector[String]) = this.attributesOption = Option(attributeNames)
  
  
  def setDescription(input: String) = this.descr = input
  
  
  def setSkills(skillNames: Vector[String]) = this.skillsOption = Option(skillNames)
  
  
  def setWeapons(weapons: String) = this.weaponsOption = Option(weapons) 
  
  
  //def setShortDescr(input: String) = this.shortDescr = input
  
}