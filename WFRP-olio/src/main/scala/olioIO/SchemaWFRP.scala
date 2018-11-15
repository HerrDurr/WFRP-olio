package olioIO

import slick.driver.SQLiteDriver.api._
import dataElements.DataHelper._
import scalafx.beans.property._
import scalafx.collections._
import shapeless._


object SchemaWFRP {
  
  //case class LiftedAttrRow(idCol: Rep[AttributeRow.IdTag], nameCol: Rep[AttributeRow.Name])
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
    
    def initProps: HList = 
    {
      //genericRow.to(this).map(_.value).map(MapToProperty)
      (idTag.value :: name.value :: HNil).map(MapToProperty)
    }
    
  }
  object AttributeRow {
    case class IdTag(val value: String) extends MappedTo[String] //extends AnyVal
    case class Name(val value: String) extends MappedTo[String] //extends AnyVal
  }
  
  /*
  implicit object AttributeShape extends CaseClassShape({ tup: (Rep[AttributeRow.IdTag],Rep[AttributeRow.Name]) 
                                                            => LiftedAttrRow(tup._1, tup._2) },
                                                        { t: (AttributeRow.IdTag, AttributeRow.Name) 
                                                            => AttributeRow(t._1, t._2) })
                                                            * 
                                                            */
  
  
  /**
   * ATTRIBUTE
   */
  class TableAttribute(tag: Tag) extends Table[AttributeRow/*(String, String)*/](tag, "ATTRIBUTE") {
    def attrId = column[/*String*/AttributeRow.IdTag]("Attribute", O.PrimaryKey, O.Unique, O.Length(3, true))
    def attrName = column[/*String*/AttributeRow.Name]("AttributeName", O.Length(20, true))
    //def * = LiftedAttrRow(attrId, attrName) // used with liftedclass and caseclassshape
    //def * = (attrId, attrName).mapTo[AttributeRow] // <> (AttributeRow.tupled, AttributeRow.unapply)
    def * = (attrId, attrName) <> ( { tuple => new AttributeRow(tuple._1,tuple._2) },
                                    AttributeRow.unapply )
    /*
    def * = (attrId, attrName) <> (AttributeRow( Vector(new StringProperty(attrId.value), new StringProperty(attrName.value)) ),
                                   (AttributeRow.Properties[0].value, AttributeRow.Properties[1].value]))
      */                             
  }
  val tableAttribute = TableQuery[TableAttribute]
  
  
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
    case class Id(val value: Int) extends MappedTo[Int]
    case class Name(val value: String) extends MappedTo[String]
    case class Basic(val value: Boolean) extends MappedTo[Boolean]
  }
  
  /**
   * SKILL
   */
  class TableSkill(tag: Tag) extends Table[SkillRow/*(Int, String, String, Boolean)*/](tag, "SKILL") {
    def skillId = column[SkillRow.Id]("SkillId", O.PrimaryKey, O.Unique)
    def skillName = column[SkillRow.Name]("SkillName", O.Length(64, true))
    def skillAttribute = column[AttributeRow.IdTag]("SkillAttribute", O.Length(3, true))
    def skillIsBasic = column[SkillRow.Basic]("SkillIsBasic", O.Default(new SkillRow.Basic(false)))
    def * = (skillId, skillName, skillAttribute, skillIsBasic) <> //.mapTo[SkillRow]
              ({ t => new SkillRow(t._1, t._2, t._3, t._4) }, SkillRow.unapply)
    // Foreign keys
    def attribute = foreignKey("", skillAttribute, tableAttribute)(_.attrId)
  }
  val tableSkill = TableQuery[TableSkill]
  
  
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
    case class Id(val value: Int) extends MappedTo[Int]
    case class Name(val value: String) extends MappedTo[String]
    case class Description(val value: String) extends MappedTo[String]
    case class WeaponGroup(val value: String) extends MappedTo[String]
  }
  
  /**
   * TALENT
   */
  class TableTalent(tag: Tag) extends Table[TalentRow/*(Int, String, Option[String], Option[String])*/](tag, "TALENT") {
    def talentId = column[TalentRow.Id]("TalentId", O.PrimaryKey, O.Unique)
    def talentName = column[TalentRow.Name]("TalentName", O.Unique, O.Length(64, true))
    def talentDescription = column[Option[TalentRow.Description]]("TalentDescription", O.Length(1024, true))
    def talentWeaponGroup = column[Option[TalentRow.WeaponGroup]]("TalentWeaponGroup", O.Length(16, true))
    def * = (talentId, talentName, talentDescription, talentWeaponGroup) <> //.mapTo[TalentRow]
        ( { t => new TalentRow(t._1, t._2, t._3, t._4) }, TalentRow.unapply )
  }
  val tableTalent = TableQuery[TableTalent]
  
  
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
    case class IdTag(val value: String) extends MappedTo[String]
    case class Name(val value: String) extends MappedTo[String]
    case class Modifier(val value: Short) extends MappedTo[Short]
  }
  
  /**
   * AVAILABILITY
   */
  class TableAvailability(tag: Tag) extends Table[AvailabilityRow](tag, "AVAILABILITY") {
    def availabilityId = column[AvailabilityRow.IdTag]("AvailabilityId", O.PrimaryKey, O.Unique, O.Length(2, true))
    def availabilityName = column[AvailabilityRow.Name]("AvailabilityFull", O.Length(24, true))
    def availabilityModifier = column[Option[AvailabilityRow.Modifier]]("AvailabilityModifier", O.SqlType("SMALLINT (3)"))
    def * = (availabilityId, availabilityName, availabilityModifier) <> //.mapTo[AvailabilityRow]
        ( { t => new AvailabilityRow(t._1, t._2, t._3) }, AvailabilityRow.unapply )
  }
  val tableAvailability = TableQuery[TableAvailability]
  
  
  case class ItemRow(id: ItemRow.Id, name: ItemRow.Name, craftsmanship: ItemRow.Craftsmanship, encumbrance: ItemRow.Encumbrance, 
      cost: Option[ItemRow.Cost], availability: Option[AvailabilityRow.IdTag]) extends DataPropertyRow {
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
  object ItemRow {
    case class Id(val value: Int) extends MappedTo[Int]
    case class Name(val value: String) extends MappedTo[String]
    case class Craftsmanship(val value: Char) extends MappedTo[Char]
    case class Encumbrance(val value: Short) extends MappedTo[Short]
    case class Cost(val value: String) extends MappedTo[String]
  }
  
  /**
   * ITEM
   */
  class TableItem(tag: Tag) extends Table[ItemRow](tag, "ITEM") {
    def itemId = column[ItemRow.Id]("ItemId", O.PrimaryKey, O.Unique)
    def itemName = column[ItemRow.Name]("ItemName", O.Length(64, true))
    def itemCraftsmanship = column[ItemRow.Craftsmanship]("ItemCraftsmanship", O.Default( new ItemRow.Craftsmanship('N') ))
    def itemEncumbrance = column[ItemRow.Encumbrance]("ItemEncumbrance", O.SqlType("SMALLINT"), O.Default( new ItemRow.Encumbrance(0) ))
    def itemCost = column[Option[ItemRow.Cost]]("ItemCost", O.Length(16, true))
    def itemAvailability = column[Option[AvailabilityRow.IdTag]]("ItemAvailability", O.Length(2, true))
    def * = (itemId, itemName, itemCraftsmanship, itemEncumbrance, itemCost, itemAvailability) <> //.mapTo[ItemRow]
        ( { t => new ItemRow(t._1, t._2, t._3, t._4, t._5, t._6) }, ItemRow.unapply )
    // Foreign keys
    def availability = foreignKey("", itemAvailability, tableAvailability)(_.availabilityId)
  }
  val tableItem = TableQuery[TableItem]
  
  
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
    case class IdTag(val value: String) extends MappedTo[String]
    case class Name(val value: String) extends MappedTo[String]
  }
  
  /**
   * WEAPONQUALITIES
   */
  class TableWeaponQualities(tag: Tag) extends Table[WeaponQualityRow](tag, "WEAPONQUALITIES") {
    def quality = column[WeaponQualitiesRow.IdTag]("WeaponQuality", O.PrimaryKey, O.Unique, O.Length(2, true))
    def qualityName = column[WeaponQualitiesRow.Name]("WeaponQualName", O.Unique, O.Length(16, true))
    // why in the everliving fuck does this compile when the others don't???
    def * = (quality, qualityName).mapTo[WeaponQualityRow]
  }
  val tableWeaponQualities = TableQuery[TableWeaponQualities]
  
  
  case class WeaponMeleeRow(id: ItemRow.Id, twoHanded: WeaponMeleeRow.TwoHanded, damageModifier: Option[WeaponMeleeRow.DamageMod], 
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
    case class TwoHanded(val value: Boolean) extends MappedTo[Boolean]
    case class DamageMod(val value: Short) extends MappedTo[Short]
    //case class Qualities(val value: String) extends MappedTo[String]
  }
  
  /**
   * WEAPONMELEE
   */
  class TableWeaponMelee(tag: Tag) extends Table[WeaponMeleeRow](tag, "WEAPONMELEE") {
    def weaponId = column[ItemRow.Id]("WeaponItemId", O.PrimaryKey, O.Unique)
    def meleeTwoHanded = column[WeaponMeleeRow.TwoHanded]("WeaponMeleeTwoHanded", O.Default( new WeaponMeleeRow.TwoHanded(false) ))
    // all melee weapons have base damage SB
    //def meleeDamageBase = column[String]("WeaponMeleeDamageBase", O.Length(3, true), O.Default("SB"))
    def meleeDamageModifier = column[Option[WeaponMeleeRow.DamageMod]]("WeaponMeleeDamageModifier", O.SqlType("SMALLINT"))
    // Turn this Option[String] into an Array[String] at *
    def meleeQualities = column[Option[String]]("WeaponMeleeQualities", O.Length(20, true))
    def meleeWeaponGroupTalent = column[Option[TalentRow.Id]]("WeaponMeleeGroupTalent")
    def * = (weaponId, meleeTwoHanded, meleeDamageModifier, meleeQualities, meleeWeaponGroupTalent) <> //.mapTo[WeaponMeleeRow]
        ( { t => new WeaponMeleeRow(t._1, t._2, t._3, CommaTextOptToArray(t._4).map( new WeaponQualitiesRow.IdTag(_) ), t._5 ) },
          { aRow: WeaponMeleeRow => Some((aRow.id, aRow.twoHanded, aRow.damageModifier, ArrayToCommaTextOpt(aRow.qualities.map(_.value)), aRow.weaponGroupTalentId)) } )
    // Foreign keys
    def item = foreignKey("", weaponId, tableItem)(_.itemId)
    def weaponGroupTalent = foreignKey("", meleeWeaponGroupTalent, tableTalent)(_.talentId)
  }
  val tableWeaponMelee = TableQuery[TableWeaponMelee]
  
  
  case class WeaponRangedRow(id: ItemRow.Id, twoHanded: WeaponRangedRow.TwoHanded, baseDamage: Option[WeaponRangedRow.DamageBase], 
                             damageModifier: Option[WeaponRangedRow.DamageMod], rangeShort: Option[WeaponRangedRow.RangeShort], 
                             rangeLong: Option[WeaponRangedRow.RangeLong], reload: Option[WeaponRangedRow.Reload], 
                             ammoItemId: Option[ItemRow.Id], magazineSize: Option[WeaponRangedRow.MagazineSize], 
                             qualities: Array[WeaponQualitiesRow.IdTag], weaponGroupTalentId: Option[TalentRow.Id]) extends DataPropertyRow {
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
    case class TwoHanded(val value: Boolean) extends MappedTo[Boolean]
    case class DamageBase(val value: String) extends MappedTo[String]
    case class DamageMod(val value: Short) extends MappedTo[Short]
    case class RangeShort(val value: Short) extends MappedTo[Short]
    case class RangeLong(val value: Short) extends MappedTo[Short]
    case class Reload(val value: Short) extends MappedTo[Short]
    case class MagazineSize(val value: Short) extends MappedTo[Short]
  }
  
  /**
   * WEAPONRANGED
   */
  class TableWeaponRanged(tag: Tag) extends Table[WeaponRangedRow](tag, "WEAPONRANGED") {
    def weaponId = column[ItemRow.Id]("WeaponItemId", O.PrimaryKey, O.Unique)
    def rangedTwoHanded = column[WeaponRangedRow.TwoHanded]("WeaponRangedTwoHanded", O.Default( WeaponRangedRow.TwoHanded(true) ))
    def rangedDamageBase = column[Option[WeaponRangedRow.DamageBase]]("WeaponRangedDamageBase", O.Length(3, true))
    def rangedDamageModifier = column[Option[WeaponRangedRow.DamageMod]]("WeaponRangedDamageModifier", O.SqlType("SMALLINT"))
    def rangeShort = column[Option[WeaponRangedRow.RangeShort]]("WeaponRangeShort", O.SqlType("SMALLINT"))
    def rangeLong = column[Option[WeaponRangedRow.RangeLong]]("WeaponRangeLong", O.SqlType("SMALLINT"))
    def rangedReload = column[Option[WeaponRangedRow.Reload]]("WeaponReload", O.SqlType("SMALLINT"))
    def rangedAmmoId = column[Option[ItemRow.Id]]("WeaponAmmunitionId")
    def rangedMagazineSize = column[Option[WeaponRangedRow.MagazineSize]]("WeaponMagazineSize", O.SqlType("SMALLINT"), 
                                                                                                O.Default(Some( WeaponRangedRow.MagazineSize(1) )))
    def rangedQualities = column[Option[String]]("WeaponRangedQualities", O.Length(20, true))
    def rangedWeaponGroupTalent = column[Option[TalentRow.Id]]("WeaponRangedGroupTalent")
    def * = (weaponId, rangedTwoHanded, rangedDamageBase, rangedDamageModifier, rangeShort, rangeLong,
            rangedReload, rangedAmmoId, rangedMagazineSize, rangedQualities, rangedWeaponGroupTalent) <> //.mapTo[WeaponRangedRow]
            ( { t => WeaponRangedRow(t._1, t._2, t._3, t._4, t._5, t._6,
                                     t._7, t._8, t._9, CommaTextOptToArray(t._10).map( WeaponQualitiesRow.IdTag(_) ), t._11) },
              { aRow: WeaponRangedRow => Some((aRow.id, aRow.twoHanded, aRow.baseDamage, aRow.damageModifier, aRow.rangeShort, aRow.rangeLong,
                                              aRow.reload, aRow.ammoItemId, aRow.magazineSize, ArrayToCommaTextOpt( aRow.qualities.map(_.value) ), 
                                              aRow.weaponGroupTalentId)) } )
    // Foreign keys
    def item = foreignKey("", weaponId, tableItem)(_.itemId)
    def ammoItem = foreignKey("", rangedAmmoId, tableItem)(_.itemId)
    def weaponGroupTalent = foreignKey("", rangedWeaponGroupTalent, tableTalent)(_.talentId)
  }
  val tableWeaponRanged = TableQuery[TableWeaponRanged]
  
  case class CareerRow(id: CareerRow.Id, name: CareerRow.Name, description: CareerRow.Description,
      skills, talents, trappings?) // attributes in different table
  
  object CareerRow {
    case class Id(val value: Int) extends MappedTo[Int]
    case class Name(val value: String) extends MappedTo[String]
    case class Description(val value: String) extends MappedTo[String]
  }
  
  class TableCareer(tag: Tag) extends Table[CareerRow](tag, "CAREER") {
    
  }
  val tableCareer = TableQuery[TableCareer]
  
  // TODO: Career Table and flesh out Olio Table
  case class OlioRow(id: OlioRow.Id, name: OlioRow.Name, careersRaw: Option[String], talentsRaw: Option[String]) extends DataPropertyRow {
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
    case class Id(val value: Int) extends MappedTo[Int]
    case class Name(val value: String) extends MappedTo[String]
  }
  
  /**
   * OLIO
   */
  class TableOlio(tag: Tag) extends Table[OlioRow](tag, "OLIO") {
    def olioId = column[OlioRow.Id]("OlioId", O.PrimaryKey, O.Unique)
    def olioName = column[OlioRow.Name]("OlioName", O.Length(64, true), O.Default( OlioRow.Name("Nimi") ))
    def olioCareers = column[Option[String]]("OlioCareers", O.Length(128, true))
    def olioTalents = column[Option[String]]("OlioTalents", O.Length(128, true))
    def * = (olioId, olioName, olioCareers, olioTalents) <> //.mapTo[OlioRow]
            ( { t => OlioRow(t._1, t._2, t._3, t._4) },
              { aRow: OlioRow => Some((aRow.id, aRow.name, aRow.careersRaw, aRow.talentsRaw)) } )
  }
  val tableOlio = TableQuery[TableOlio]
  
  
  case class OlioAttributesRow(olio: OlioRow.Id, attribute: AttributeRow.IdTag, baseVal: OlioAttributesRow.BaseVal) extends DataPropertyRow {
    /*def initProperties =
    {
      Vector( new IntegerProperty(this, "olioId", olio),
              new StringProperty(this, "attributeId", attribute.value),
              new IntegerProperty(this, "olioAttributeBaseVal", baseVal.asInstanceOf[Int]) )
    }*/
  }
  object OlioAttributesRow {
    case class BaseVal(val value: Short) extends MappedTo[Short]
  }
  
  /**
   * OLIOATTRIBUTES
   */
  class TableOlioAttributes(tag: Tag) extends Table[OlioAttributesRow](tag, "OLIOATTRIBUTES") {
    def olioId = column[OlioRow.Id]("OlioId")
    def attributeId = column[AttributeRow.IdTag]("Attribute", O.Length(3, true))
    def olioAttributeBaseVal = column[OlioAttributesRow.BaseVal](
      "OlioAttributeBaseVal", O.SqlType("SMALLINT (3)"), O.Default( OlioAttributesRow.BaseVal(0) ))
    def * = (olioId, attributeId, olioAttributeBaseVal) <> //.mapTo[OlioAttributesRow]
            ( { t => OlioAttributesRow(t._1, t._2, t._3) }, OlioAttributesRow.unapply )
    // Primary key
    def pk = primaryKey("", (olioId, attributeId))
    // Foreign Keys
    def olio = foreignKey("", olioId, tableOlio)(_.olioId)
    def attribute = foreignKey("attributeId_FK", attributeId, tableAttribute)(_.attrId)
  }
  val tableOlioAttributes = TableQuery[TableOlioAttributes]
  
  
  case class OlioSkillsRow(olio: OlioRow.Id, skill: SkillRow.Id, skillTrained: OlioSkillsRow.Trained) extends DataPropertyRow {
    /*def initProperties =
    {
      Vector( new IntegerProperty(this, "olioId", olio),
              new IntegerProperty(this, "skillId", skill.value),
              new IntegerProperty(this, "olioSkillTrained", skillTrained.asInstanceOf[Int]) )
    }*/
  }
  object OlioSkillsRow {
    case class Trained(val value: Short) extends MappedTo[Short]
  }
  
  /**
   * OLIOSKILLS
   */
  class TableOlioSkills(tag: Tag) extends Table[OlioSkillsRow](tag, "OLIOSKILLS") {
    def olioId = column[OlioRow.Id]("OlioId")
    def skillId = column[SkillRow.Id]("SkillId")
    def olioSkillTrained = column[OlioSkillsRow.Trained](
        "OlioSkillTrained", O.SqlType("SMALLINT (1)"), O.Default( OlioSkillsRow.Trained(0) ))
    def * = (olioId, skillId, olioSkillTrained) <> //.mapTo[OlioSkillsRow]
            ( { t => OlioSkillsRow(t._1, t._2, t._3) }, OlioSkillsRow.unapply )
    // Primary key
    def pk = primaryKey("", (olioId, skillId))
    // Foreign Keys
    def olio = foreignKey("", olioId, tableOlio)(_.olioId)
    def skill = foreignKey("", skillId, tableSkill)(_.skillId)
  }
  val tableOlioSkills = TableQuery[TableOlioSkills]
  
  
  val tables = Vector(tableAttribute, tableSkill, tableTalent, tableAvailability, tableItem, tableWeaponQualities, tableWeaponMelee,
                     tableWeaponRanged, tableCareer, tableOlio, tableOlioAttributes, tableOlioSkills
                     )
  // all table schemas ++'d together
  val schema = tables.map(_.schema)
                     .reduceLeft((total, current) => total ++ current) 
    /*tableAttribute.schema ++ tableSkill.schema ++ tableTalent.schema ++ tableAvailability.schema ++ tableItem.schema ++ tableWeaponQualities.schema ++
    tableWeaponMelee.schema ++  ++ tableOlio.schema
    * 
    */
  
  
}