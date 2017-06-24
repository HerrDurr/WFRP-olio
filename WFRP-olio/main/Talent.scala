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
  
  
  //def setShortDescr(input: String) = this.shortDescr = input
  
}