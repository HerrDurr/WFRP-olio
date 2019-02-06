package olioIO

import SchemaWFRP._
import shapeless.Generic
//import olioMain.{Olio => deprOlio}

object DataHelperWFRP {
  
  import dbContext._
  
  val qTest = quote {
    for {
      o <- query[Olio]
      ba <- query[AttributeSet] if o.baseAttributes == ba.id
    } yield (o.name, ba.weaponSkill)
  }
  val resTest = dbContext.run(qTest)
  
  /**
   * byId could (and should!) be done using Shapeless Magick! Use LabelledGeneric and
   * common names for case class parameters.
   */
  def byId(aIdTag: Availability.IdTag): Availability =
  {
    val q = quote {
      query[Availability].filter{ av: Availability => av.idTag == lift(aIdTag) }
    } 
    dbContext.run(q).headOption.getOrElse(throw new IllegalArgumentException(s"Unknown Availability: $aIdTag"))
  }
  
  def talentById(aTalId: Talent.Id): Option[Talent] =
  {
    val q = quote {
      query[Talent].filter{ tal: Talent => tal.id == lift(aTalId) }
    } 
    dbContext.run(q).headOption
  }
  
  def byId(aId: Item.Id): Option[Item] =
  {
    val q = quote {
      query[Item].filter{ item: Item => item.id == lift(aId) }
    } 
    dbContext.run(q).headOption//.getOrElse(throw new IllegalArgumentException(s"Unknown Item: $aId"))
  }
  
  def insertItem(aItem : Item) : Item.Id = {
    val q = quote {
      query[Item].insert(lift(aItem)).returning(_.id)
    }
    dbContext.run(q)
  }
  
  def attributeSetById(aId: AttributeSet.Id): Option[AttributeSet] = 
  {
    val q = quote {
      query[AttributeSet].filter{ aSet: AttributeSet => aSet.id == lift(aId) }
    } 
    dbContext.run(q).headOption
  }
  
  def careerById(aId: Career.Id): Option[Career] =
  {
    val q = quote {
      query[Career].filter{ career: Career => career.id == lift(aId) }
    } 
    dbContext.run(q).headOption
  }
  
  def insertCareer(aCareer : Career) : Career.Id = {
    val q = quote {
      query[Career].insert(lift(aCareer)).returning(_.id)
    }
    dbContext.run(q)
  }
  
  def insertOrUpdateItem(aItem : Item) : Unit = {
    dbContext.insertOrUpdate(aItem, (i : Item) => i.id == lift(aItem.id) )
  }
  
  def getAllItems: List[Item] =
  {
    val q = quote {
      for {
        item <- query[Item]
      } yield {
        item
      }
    }
    val itemList = dbContext.run(q)
    itemList
  }
  
  implicit class ItemOps(item: Item) {
    
    def weaponMelee: Option[WeaponMelee] = {
      val q = quote {
        query[WeaponMelee].filter{ weapon: WeaponMelee => weapon.id == lift(item.id) }
      } 
      dbContext.run(q).headOption
    }
  
    def weaponRanged: Option[WeaponRanged] = {
      val q = quote {
        query[WeaponRanged].filter{ weapon: WeaponRanged => weapon.id == lift(item.id) }
      } 
      dbContext.run(q).headOption
    } 
    
  }
  
  private var fAvailabilities: Option[List[Availability]] = None
  
  def getAllAvailabilities: List[Availability] =
  {
    if (this.fAvailabilities.isEmpty)
    {
      val q = quote {
        for {
          ava <- query[Availability]
        } yield {
          ava
        }
      }
      val avs = dbContext.run(q)
      this.fAvailabilities = Some(avs)
    }
    
    this.fAvailabilities.getOrElse(List())
  }
  
  private var fWeaponQualities: Option[List[WeaponQuality]] = None
  
  def getAllWeaponQualities: List[WeaponQuality] = {
    if (this.fWeaponQualities.isEmpty)
    {
      val q = quote {
        for {
          wq <- query[WeaponQuality]
        } yield {
          wq
        }
      }
      val wqs = dbContext.run(q)
      this.fWeaponQualities = Some(wqs)
    }
    this.fWeaponQualities.getOrElse(List())
  }
  
  implicit class AttributeSetHelper(attrSet: AttributeSet) {
    
    def queryById(aId: AttributeSet.Id) =
    {
      // Eclipse's compiler ain't happy about this, but it seems fine?
      val q = quote {
        query[AttributeSet].filter{ aSet: AttributeSet => aSet.id == lift(aId) }
      }
      dbContext.run(q)
    }
    
    /**
     * Fetch from db by the Id.
     */
    def apply(aId: AttributeSet.Id): Option[AttributeSet] = {
      val queried = queryById(aId)
      if (!queried.isEmpty)
        Some(queried.head)
      else
        None //AttributeSet.createEmpty(aId)
    }
    
    /**
     * Insert new row to AttributeSet, letting the database
     * set a new Id number (I hope!).
     * @return the new AttributeSet.Id 
     */
    def insertAsNew: AttributeSet.Id = {
      val q = quote {
        query[AttributeSet].insert(lift(attrSet)).returning(_.id)
      }
      dbContext.run(q)
    }
    
    lazy val generic = Generic[AttributeSet]
    lazy val toHList = generic.to(attrSet)
    
  }
  
  implicit class OlioHelper(olio: Olio) {
    
    
    /**
     * Insert new row to Olio, letting the database
     * set a new Id number (I hope!).
     * @return the new Olio.Id 
     */
    def insertAsNew: Olio.Id = {
      val q = quote {
        query[Olio].insert(lift(olio)).returning(_.id)
      }
      dbContext.run(q)
    }
    
    /*
    def getBaseAttributes = {
      val q = quote {
        query[AttributeSet].filter(_.id == olio.baseAttributes)
      }
      dbContext.run(q)
    }
    * 
    */
    
  }
  
  /*
   * Do not run, already ran this!
  def copyOldTalentDataToDB(dummy: deprOlio) = {
    import Talent._
    try
    {
      val aNewTalents = dummy.allTalents.map( aOld => new Talent( Id(-1), Name(aOld.name), Some(Description( aOld.description )), None ) )
      val insertQ = quote {
        //for (tal <- aNewTalents) {
        //  query[Talent].insert( lift(tal) )
        //}
        liftQuery(aNewTalents).foreach( tal => query[Talent].insert(tal) )
      }
      dbContext.run(insertQ)
    } finally println("Talents copied to db!")
    
  }
  * 
  */
  
  
}