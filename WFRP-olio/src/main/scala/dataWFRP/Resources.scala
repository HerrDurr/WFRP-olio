package dataWFRP

import olioIO.SchemaWFRP._

object Resources {
  
  import dbContext._
  
  private var fTalents: Option[List[Talent]] = None
  
  def allTalents: List[Talent] = {
    if (this.fTalents.isEmpty)
    {
      val q = quote {
        for {
          tal <- query[Talent]
        } yield {
          tal
        }
      }
      val tals = dbContext.run(q)
      this.fTalents = Some(tals)
    }
    this.fTalents.getOrElse(List())
  }
  
  def weaponGroupTalents : List[Talent] = {
    val all = this.allTalents
    all.filter(_.weaponGroup.isDefined)
  }
  
  def weaponGroups : List[Talent.WeaponGroup] = {
    this.weaponGroupTalents.map(_.weaponGroup.get)
  }
  
}