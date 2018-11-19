package olioIO

//import slick.driver.SQLiteDriver.api._
import dataElements.DataHelper._
import scalafx.beans.property._
import scalafx.collections._
import shapeless._
import io.getquill.{SqliteJdbcContext, SqliteDialect, CamelCase}


object SchemaWFRP {
  
  lazy val dbContext = new SqliteJdbcContext(CamelCase, "wfrpdb")
  
  case class Attribute(val idTag: Attribute.IdTag, val name: Attribute.Name/*idTag: String, name: String*/) extends DataPropertyRow {
    
    //def tupled = (idTag, name)
    /*def initProperties: Vector[StringProperty] =
    {
      Vector(new StringProperty(this, "attrId", idTag),
             new StringProperty(this, "attrName", name))
    }
    * 
    */
    //val genericRow = Generic[AttributeRow] 
    
    override def initProps: HList = 
    {
      //genericRow.to(this).map(_.value).map(MapToProperty)
      (idTag.value :: name.value :: HNil).map(MapToProperty)
    }
    
  }
  object Attribute {
    case class IdTag(val value: String) extends AnyVal //MappedTo[String] //extends AnyVal
    case class Name(val value: String) extends AnyVal //MappedTo[String] //extends AnyVal
  }
  
  
  case class Skill(id: Skill.Id, name: Skill.Name, attribute: Attribute.IdTag, 
      isBasic: Skill.Basic) /*extends DataPropertyRow*/ {
    /*def initProperties =
    {
      Vector(new IntegerProperty(this, "skillId", id),
             new StringProperty(this, "skillName", name),
             new StringProperty(this, "skillAttribute", attribute.value),
             new BooleanProperty(this, "skillIsBasic", isBasic))
    }
    * 
    */
  }
  object Skill {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Basic(val value: Boolean) extends AnyVal //MappedTo[Boolean]
  }
  
  
  case class Talent(id: Talent.Id, name: Talent.Name, description: Option[Talent.Description], 
      weaponGroup: Option[Talent.WeaponGroup]) extends DataPropertyRow {
    /*
    def initProperties =
    {
      Vector( new IntegerProperty(this, "talentId", id),
              new StringProperty(this, "talentName", name),
              new StringProperty(this, "talentDescription", description.getOrElse("")),
              new StringProperty(this, "talentWeaponGroup", weaponGroup.getOrElse("")) )
    }
    * 
    */
  }
  object Talent {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Description(val value: String) extends AnyVal //MappedTo[String]
    case class WeaponGroup(val value: String) extends AnyVal //MappedTo[String]
  }
  
  case class Availability(idTag: Availability.IdTag, name: Availability.Name, modifier: Option[Availability.Modifier]) extends DataPropertyRow {
    /*def initProperties =
    {
      Vector( new StringProperty(this, "availabilityId", idTag),
              new StringProperty(this, "availabilityName", name),
              new IntegerProperty(this, "availabilityModifier", modifier.getOrElse(0).asInstanceOf[Int]) )
    }
    * 
    */
  }
  object Availability {
    case class IdTag(val value: String) extends AnyVal //MappedTo[String]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Modifier(val value: Short) extends AnyVal //MappedTo[Short]
  }
  
  
  case class Item(itemId: Item.Id, itemName: Item.Name, itemCraftsmanship: Item.Craftsmanship, itemEncumbrance: Item.Encumbrance, 
      itemCost: Option[Item.Cost], itemAvailability: Option[Availability.IdTag]) extends DataPropertyRow {
    /*def initProperties =
    {
      Vector( new IntegerProperty(this, "itemId", id),
              new StringProperty(this, "itemName", name),
              new StringProperty(this, "itemCraftsmanship", craftsmanship.toString),
              new IntegerProperty(this, "itemEncumbrance", encumbrance.asInstanceOf[Int]),
              new StringProperty(this, "itemCost", cost.getOrElse("")),
              new StringProperty(this, "itemAvailability", availability.getOrElse("")) )
    }
    * 
    */
  }
  object Item {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Craftsmanship(val value: Char) extends AnyVal //MappedTo[Char]
    case class Encumbrance(val value: Short) extends AnyVal //MappedTo[Short]
    case class Cost(val value: String) extends AnyVal //MappedTo[String]
  }
  
  /*val quoteItem = quote {
    querySchema[ItemRow](
      "ITEM",
      _.id -> "ItemId",
      _.name -> "ItemName",
      _.craftsmanship -> "ItemCraftsmanship",
      _.encumbrance -> "ItemEncumbrance",
      _.cost -> "ItemCost",
      _.availability -> "ItemAvailability"
  )
  }
  * 
  */
  
  case class WeaponQuality(idTag: WeaponQuality.IdTag, name: WeaponQuality.Name) extends DataPropertyRow {
    /*def initProperties =
    {
      Vector( new StringProperty(this, "quality", idTag),
              new StringProperty(this, "qualityName", name) )
    }
    * 
    */
  }
  object WeaponQuality {
    case class IdTag(val value: String) extends AnyVal //MappedTo[String]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
  }
  
  
  case class WeaponMelee(id: Item.Id, twoHanded: WeaponMelee.TwoHanded, damageModifier: Option[WeaponMelee.DamageMod], 
      qualities: Array[WeaponQuality.IdTag], /*qualitiesRaw: Option[String]*/ weaponGroupTalentId: Option[Talent.Id]) extends DataPropertyRow {
    /*
    def initProperties =
    {
      Vector( new IntegerProperty(this, "weaponId", id),
              new BooleanProperty(this, "meleeTwoHanded", twoHanded),
              new IntegerProperty(this, "meleeDamageModifier", damageModifier.getOrElse(0).asInstanceOf[Int]),
              new ObjectProperty(this, "meleeQualities", qualitiesRaw.getOrElse("").split(',').toVector),
              new IntegerProperty(this, "meleeWeaponGroupTalent", weaponGroupTalentId.getOrElse(-1)) )
    }
    * 
    */
  }
  object WeaponMelee {
    case class TwoHanded(val value: Boolean) extends AnyVal //MappedTo[Boolean]
    case class DamageMod(val value: Short) extends AnyVal //MappedTo[Short]
    //case class Qualities(val value: String) extends MappedTo[String]
  }
  
  
  case class WeaponRanged(id: Item.Id, twoHanded: WeaponRanged.TwoHanded, damageBase: Option[WeaponRanged.DamageBase], 
                             damageModifier: Option[WeaponRanged.DamageMod], rangeShort: Option[WeaponRanged.RangeShort], 
                             rangeLong: Option[WeaponRanged.RangeLong], reload: Option[WeaponRanged.Reload], 
                             ammunitionId: Option[Item.Id], magazineSize: Option[WeaponRanged.MagazineSize], 
                             qualities: Array[WeaponQuality.IdTag], weaponGroupTalentId: Option[Talent.Id]) /*extends DataPropertyRow*/ {
    /*
    def initProperties =
    {
      Vector( new IntegerProperty(this, "weaponId", id),
              new BooleanProperty(this, "rangedTwoHanded", twoHanded),
              new StringProperty(this, "rangedDamageBase", baseDamage.getOrElse("")),
              new IntegerProperty(this, "rangedDamageModifier", damageModifier.getOrElse(0).asInstanceOf[Int]),
              new IntegerProperty(this, "rangeShort", rangeShort.getOrElse(0).asInstanceOf[Int]),
              new IntegerProperty(this, "rangeLong", rangeLong.getOrElse(0).asInstanceOf[Int]),
              new IntegerProperty(this, "rangedReload", reload.getOrElse(0).asInstanceOf[Int]),
              new IntegerProperty(this, "rangedAmmoId", ammoItemId.getOrElse(-1).asInstanceOf[Int]),
              new IntegerProperty(this, "rangedMagazineSize", magazineSize.getOrElse(1).asInstanceOf[Int]),
              new ObjectProperty(this, "rangedQualities", qualitiesRaw.getOrElse("").split(',').toVector),
              new IntegerProperty(this, "rangedWeaponGroupTalent", weaponGroupTalentId.getOrElse(-1)) )
    }
    * 
    */
  }
  object WeaponRanged {
    case class TwoHanded(val value: Boolean) extends AnyVal //MappedTo[Boolean]
    case class DamageBase(val value: String) extends AnyVal //MappedTo[String]
    case class DamageMod(val value: Short) extends AnyVal //MappedTo[Short]
    case class RangeShort(val value: Short) extends AnyVal //MappedTo[Short]
    case class RangeLong(val value: Short) extends AnyVal //MappedTo[Short]
    case class Reload(val value: Short) extends AnyVal //MappedTo[Short]
    case class MagazineSize(val value: Short) extends AnyVal //MappedTo[Short]
  }
  
  /**
   * Career Entries not stored in db. If a Career has no other that exits to it, it can be taken by anyone
   * (of course with any other restrictions still applicable).
   */
  case class Career(id: Career.Id, name: Career.Name, description: Option[Career.Description],
      skills: Array[Skill.Id], skillOptions: Array[Array[Skill.Id]], talents: Array[Talent.Id], talentOptions: Array[Array[Talent.Id]],
      careerExits: Array[Career.Id])
  // attributes in different table
  // trappings maybe don't need to be implemented (yet)
  
  object Career {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Description(val value: String) extends AnyVal //MappedTo[String]
  }
  
  
  // TODO: Career Table and flesh out Olio Table
  case class Olio(id: Olio.Id, name: Olio.Name, careers: Array[Career.Id], talents: Array[Talent.Id]) extends DataPropertyRow {
    /*def initProperties =
    {
      Vector( new IntegerProperty(this, "olioId", id),
              new StringProperty(this, "olioName", name),
              new ObjectProperty(this, "olioCareers", careersRaw.getOrElse("").split(',').toVector),
              new ObjectProperty(this, "olioTalents", talentsRaw.getOrElse("").split(',').toVector) )
    }
    * 
    */
  }
  object Olio {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
  }
  
  
  case class OlioAttributes(olioId: Olio.Id, attribute: Attribute.IdTag, baseVal: OlioAttributes.BaseVal) extends DataPropertyRow {
    /*def initProperties =
    {
      Vector( new IntegerProperty(this, "olioId", olio),
              new StringProperty(this, "attributeId", attribute.value),
              new IntegerProperty(this, "olioAttributeBaseVal", baseVal.asInstanceOf[Int]) )
    }*/
  }
  object OlioAttributes {
    case class BaseVal(val value: Short) extends AnyVal //MappedTo[Short]
  }
  
  
  case class OlioSkills(olioId: Olio.Id, skillId: Skill.Id, skillTrained: OlioSkills.Trained) extends DataPropertyRow {
    /*def initProperties =
    {
      Vector( new IntegerProperty(this, "olioId", olio),
              new IntegerProperty(this, "skillId", skill.value),
              new IntegerProperty(this, "olioSkillTrained", skillTrained.asInstanceOf[Int]) )
    }*/
  }
  object OlioSkills {
    case class Trained(val value: Short) extends AnyVal //MappedTo[Short]
  }
  
}