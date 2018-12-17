package olioIO

import SchemaWFRP._
import shapeless.Generic

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
  
  implicit class AvailabilityHelper(avail: Availability) {
    
    /*def byId(aIdTag: Availability.IdTag) =
    {
      val q = quote {
        query[Availability].filter{ av: Availability => av.idTag == lift(aIdTag) }
      }
      dbContext.run(q)
    }
    * 
    */
    
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
  
}