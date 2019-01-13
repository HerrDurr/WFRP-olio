package dataWFRP

import olioIO.SchemaWFRP._
import Types._

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
    import TalentExplain._
    val all = this.allTalents
    all.filter(_.subType == WeaponGroup)
  }
  
  def weaponGroups : List[Talent.SubTitle] = {
    this.weaponGroupTalents.map(_.subTitle.getOrElse(Talent.SubTitle("")))
  }
  
}