package olioIO

//import slick.driver.SQLiteDriver.api._
import dataElements.DataHelper._
import scalafx.beans.property._
import scalafx.collections._
import shapeless._
import io.getquill.{SqliteJdbcContext, SqliteDialect, CamelCase}


object SchemaWFRP {
  
  lazy val dbContext = new SqliteJdbcContext(CamelCase, "wfrpdb")
  
  case class AttributeRow(val idTag: AttributeRow.IdTag, val name: AttributeRow.Name/*idTag: String, name: String*/) extends DataPropertyRow {
    
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
  object AttributeRow {
    case class IdTag(val value: String) extends AnyVal //MappedTo[String] //extends AnyVal
    case class Name(val value: String) extends AnyVal //MappedTo[String] //extends AnyVal
  }
  
  
  case class SkillRow(id: SkillRow.Id, name: SkillRow.Name, attribute: AttributeRow.IdTag, 
      isBasic: SkillRow.Basic) extends DataPropertyRow {
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
  object SkillRow {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Basic(val value: Boolean) extends AnyVal //MappedTo[Boolean]
  }
  
  
  case class TalentRow(id: TalentRow.Id, name: TalentRow.Name, description: Option[TalentRow.Description], 
      weaponGroup: Option[TalentRow.WeaponGroup]) extends DataPropertyRow {
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
  object TalentRow {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Description(val value: String) extends AnyVal //MappedTo[String]
    case class WeaponGroup(val value: String) extends AnyVal //MappedTo[String]
  }
  
  case class AvailabilityRow(idTag: AvailabilityRow.IdTag, name: AvailabilityRow.Name, modifier: Option[AvailabilityRow.Modifier]) extends DataPropertyRow {
    /*def initProperties =
    {
      Vector( new StringProperty(this, "availabilityId", idTag),
              new StringProperty(this, "availabilityName", name),
              new IntegerProperty(this, "availabilityModifier", modifier.getOrElse(0).asInstanceOf[Int]) )
    }
    * 
    */
  }
  object AvailabilityRow {
    case class IdTag(val value: String) extends AnyVal //MappedTo[String]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Modifier(val value: Short) extends AnyVal //MappedTo[Short]
  }
  
  
  case class Item(itemId: Item.Id, itemName: Item.Name, itemCraftsmanship: Item.Craftsmanship, itemEncumbrance: Item.Encumbrance, 
      itemCost: Option[Item.Cost], itemAvailability: Option[AvailabilityRow.IdTag]) extends DataPropertyRow {
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
  
  case class WeaponQualityRow(idTag: WeaponQualitiesRow.IdTag, name: WeaponQualitiesRow.Name) extends DataPropertyRow {
    /*def initProperties =
    {
      Vector( new StringProperty(this, "quality", idTag),
              new StringProperty(this, "qualityName", name) )
    }
    * 
    */
  }
  object WeaponQualitiesRow {
    case class IdTag(val value: String) extends AnyVal //MappedTo[String]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
  }
  
  
  case class WeaponMeleeRow(id: Item.Id, twoHanded: WeaponMeleeRow.TwoHanded, damageModifier: Option[WeaponMeleeRow.DamageMod], 
      qualities: Array[WeaponQualitiesRow.IdTag], /*qualitiesRaw: Option[String]*/ weaponGroupTalentId: Option[TalentRow.Id]) extends DataPropertyRow {
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
  object WeaponMeleeRow {
    case class TwoHanded(val value: Boolean) extends AnyVal //MappedTo[Boolean]
    case class DamageMod(val value: Short) extends AnyVal //MappedTo[Short]
    //case class Qualities(val value: String) extends MappedTo[String]
  }
  
  
  case class WeaponRangedRow(id: Item.Id, twoHanded: WeaponRangedRow.TwoHanded, baseDamage: Option[WeaponRangedRow.DamageBase], 
                             damageModifier: Option[WeaponRangedRow.DamageMod], rangeShort: Option[WeaponRangedRow.RangeShort], 
                             rangeLong: Option[WeaponRangedRow.RangeLong], reload: Option[WeaponRangedRow.Reload], 
                             ammoItemId: Option[Item.Id], magazineSize: Option[WeaponRangedRow.MagazineSize], 
                             qualities: Array[WeaponQualitiesRow.IdTag], weaponGroupTalentId: Option[TalentRow.Id]) /*extends DataPropertyRow*/ {
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
  object WeaponRangedRow {
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
  case class CareerRow(id: CareerRow.Id, name: CareerRow.Name, description: Option[CareerRow.Description],
      skills: Array[SkillRow.Id], skillOptions: Array[Array[SkillRow.Id]], talents: Array[TalentRow.Id], talentOptions: Array[Array[TalentRow.Id]],
      careerExits: Array[CareerRow.Id])
  // attributes in different table
  // trappings maybe don't need to be implemented (yet)
  
  object CareerRow {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Description(val value: String) extends AnyVal //MappedTo[String]
  }
  
  
  // TODO: Career Table and flesh out Olio Table
  case class OlioRow(id: OlioRow.Id, name: OlioRow.Name, careers: Array[CareerRow.Id], talents: Array[TalentRow.Id]) extends DataPropertyRow {
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
  object OlioRow {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
  }
  
  
  case class OlioAttributesRow(olio: OlioRow.Id, attribute: AttributeRow.IdTag, baseVal: OlioAttributesRow.BaseVal) extends DataPropertyRow {
    /*def initProperties =
    {
      Vector( new IntegerProperty(this, "olioId", olio),
              new StringProperty(this, "attributeId", attribute.value),
              new IntegerProperty(this, "olioAttributeBaseVal", baseVal.asInstanceOf[Int]) )
    }*/
  }
  object OlioAttributesRow {
    case class BaseVal(val value: Short) extends AnyVal //MappedTo[Short]
  }
  
  
  case class OlioSkillsRow(olio: OlioRow.Id, skill: SkillRow.Id, skillTrained: OlioSkillsRow.Trained) extends DataPropertyRow {
    /*def initProperties =
    {
      Vector( new IntegerProperty(this, "olioId", olio),
              new IntegerProperty(this, "skillId", skill.value),
              new IntegerProperty(this, "olioSkillTrained", skillTrained.asInstanceOf[Int]) )
    }*/
  }
  object OlioSkillsRow {
    case class Trained(val value: Short) extends AnyVal //MappedTo[Short]
  }
  
}