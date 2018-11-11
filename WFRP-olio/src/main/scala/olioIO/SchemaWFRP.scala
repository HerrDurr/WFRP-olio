package olioIO

import slick.driver.SQLiteDriver.api._
import dataElements.DataHelper._
import scalafx.beans.property._
import scalafx.collections._


object SchemaWFRP {
  
  abstract class DelimitedDataRow {
    
    
    
  }
  
  
  
  case class AttributeRow(idTag: String, name: String) extends DataPropertyRow {
    
    def initProperties: Vector[StringProperty] =
    {
      Vector(new StringProperty(this, "attrId", idTag),
             new StringProperty(this, "attrName", name))
    }
    
  }
  
  /**
   * ATTRIBUTE
   */
  class TableAttribute(tag: Tag) extends Table[AttributeRow/*(String, String)*/](tag, "ATTRIBUTE") {
    def attrId = column[String]("Attribute", O.PrimaryKey, O.Unique, O.Length(3, true))
    def attrName = column[String]("AttributeName", O.Length(20, true))
    def * = (attrId, attrName).mapTo[AttributeRow] // <> (AttributeRow.tupled, AttributeRow.unapply)
    //def * = (attrId, attrName) <> (AttributeRow.tupled, )
    /*
    def * = (attrId, attrName) <> (AttributeRow( Vector(new StringProperty(attrId.value), new StringProperty(attrName.value)) ),
                                   (AttributeRow.Properties[0].value, AttributeRow.Properties[1].value]))
      */                             
  }
  val tableAttribute = TableQuery[TableAttribute]
  
  
  case class SkillRow(id: Int, name: String, attribute: String, isBasic: Boolean) extends DataPropertyRow {
    def initProperties =
    {
      Vector(new IntegerProperty(this, "skillId", id),
             new StringProperty(this, "skillName", name),
             new StringProperty(this, "skillAttribute", attribute),
             new BooleanProperty(this, "skillIsBasic", isBasic))
    }
  }
  
  /**
   * SKILL
   */
  class TableSkill(tag: Tag) extends Table[SkillRow/*(Int, String, String, Boolean)*/](tag, "SKILL") {
    def skillId = column[Int]("SkillId", O.PrimaryKey, O.Unique)
    def skillName = column[String]("SkillName", O.Length(64, true))
    def skillAttribute = column[String]("SkillAttribute", O.Length(3, true))
    def skillIsBasic = column[Boolean]("SkillIsBasic", O.Default(false))
    def * = (skillId, skillName, skillAttribute, skillIsBasic).mapTo[SkillRow]
    // Foreign keys
    def attribute = foreignKey("", skillAttribute, tableAttribute)(_.attrId)
  }
  val tableSkill = TableQuery[TableSkill]
  
  
  case class TalentRow(id: Int, name: String, description: Option[String], weaponGroup: Option[String]) extends DataPropertyRow {
    def initProperties =
    {
      Vector( new IntegerProperty(this, "talentId", id),
              new StringProperty(this, "talentName", name),
              new StringProperty(this, "talentDescription", description.getOrElse("")),
              new StringProperty(this, "talentWeaponGroup", weaponGroup.getOrElse("")) )
    }
  }
  
  /**
   * TALENT
   */
  class TableTalent(tag: Tag) extends Table[TalentRow/*(Int, String, Option[String], Option[String])*/](tag, "TALENT") {
    def talentId = column[Int]("TalentId", O.PrimaryKey, O.Unique)
    def talentName = column[String]("TalentName", O.Unique, O.Length(64, true))
    def talentDescription = column[Option[String]]("TalentDescription", O.Length(1024, true))
    def talentWeaponGroup = column[Option[String]]("TalentWeaponGroup", O.Length(16, true))
    def * = (talentId, talentName, talentDescription, talentWeaponGroup).mapTo[TalentRow]
  }
  val tableTalent = TableQuery[TableTalent]
  
  
  case class AvailabilityRow(idTag: String, name: String, modifier: Option[Short]) extends DataPropertyRow {
    def initProperties =
    {
      Vector( new StringProperty(this, "availabilityId", idTag),
              new StringProperty(this, "availabilityName", name),
              new IntegerProperty(this, "availabilityModifier", modifier.getOrElse(0).asInstanceOf[Int]) )
    }
  }
  
  /**
   * AVAILABILITY
   */
  class TableAvailability(tag: Tag) extends Table[AvailabilityRow](tag, "AVAILABILITY") {
    def availabilityId = column[String]("AvailabilityId", O.PrimaryKey, O.Unique, O.Length(2, true))
    def availabilityName = column[String]("AvailabilityFull", O.Length(24, true))
    def availabilityModifier = column[Option[Short]]("AvailabilityModifier", O.SqlType("SMALLINT (3)"))
    def * = (availabilityId, availabilityName, availabilityModifier).mapTo[AvailabilityRow]
  }
  val tableAvailability = TableQuery[TableAvailability]
  
  
  case class ItemRow(id: Int, name: String, craftsmanship: Char, encumbrance: Short, cost: Option[String], availability: Option[String]) extends DataPropertyRow {
    def initProperties =
    {
      Vector( new IntegerProperty(this, "itemId", id),
              new StringProperty(this, "itemName", name),
              new StringProperty(this, "itemCraftsmanship", craftsmanship.toString),
              new IntegerProperty(this, "itemEncumbrance", encumbrance.asInstanceOf[Int]),
              new StringProperty(this, "itemCost", cost.getOrElse("")),
              new StringProperty(this, "itemAvailability", availability.getOrElse("")) )
    }
  }
  
  /**
   * ITEM
   */
  class TableItem(tag: Tag) extends Table[ItemRow](tag, "ITEM") {
    def itemId = column[Int]("ItemId", O.PrimaryKey, O.Unique)
    def itemName = column[String]("ItemName", O.Length(64, true))
    def itemCraftsmanship = column[Char]("ItemCraftsmanship", O.Default('N'))
    def itemEncumbrance = column[Short]("ItemEncumbrance", O.SqlType("SMALLINT"), O.Default(0))
    def itemCost = column[Option[String]]("ItemCost", O.Length(16, true))
    def itemAvailability = column[Option[String]]("ItemAvailability", O.Length(2, true))
    def * = (itemId, itemName, itemCraftsmanship, itemEncumbrance, itemCost, itemAvailability).mapTo[ItemRow]
    // Foreign keys
    def availability = foreignKey("", itemAvailability, tableAvailability)(_.availabilityId)
  }
  val tableItem = TableQuery[TableItem]
  
  
  case class WeaponQualityRow(idTag: String, name: String) extends DataPropertyRow {
    def initProperties =
    {
      Vector( new StringProperty(this, "quality", idTag),
              new StringProperty(this, "qualityName", name) )
    }
  }
  
  /**
   * WEAPONQUALITIES
   */
  class TableWeaponQualities(tag: Tag) extends Table[WeaponQualityRow](tag, "WEAPONQUALITIES") {
    def quality = column[String]("WeaponQuality", O.PrimaryKey, O.Unique, O.Length(2, true))
    def qualityName = column[String]("WeaponQualName", O.Unique, O.Length(16, true))
    def * = (quality, qualityName).mapTo[WeaponQualityRow]
  }
  val tableWeaponQualities = TableQuery[TableWeaponQualities]
  
  
  case class WeaponMeleeRow(id: Int, twoHanded: Boolean, damageModifier: Option[Short], qualitiesRaw: Option[String], 
      weaponGroupTalentId: Option[Int]) extends DataPropertyRow {
    def initProperties =
    {
      Vector( new IntegerProperty(this, "weaponId", id),
              new BooleanProperty(this, "meleeTwoHanded", twoHanded),
              new IntegerProperty(this, "meleeDamageModifier", damageModifier.getOrElse(0).asInstanceOf[Int]),
              new ObjectProperty(this, "meleeQualities", qualitiesRaw.getOrElse("").split(',').toVector),
              new IntegerProperty(this, "meleeWeaponGroupTalent", weaponGroupTalentId.getOrElse(-1)) )
    }
  }
  
  /**
   * WEAPONMELEE
   */
  class TableWeaponMelee(tag: Tag) extends Table[WeaponMeleeRow](tag, "WEAPONMELEE") {
    def weaponId = column[Int]("WeaponItemId", O.PrimaryKey, O.Unique)
    def meleeTwoHanded = column[Boolean]("WeaponMeleeTwoHanded", O.Default(false))
    // all melee weapons have base damage SB
    //def meleeDamageBase = column[String]("WeaponMeleeDamageBase", O.Length(3, true), O.Default("SB"))
    def meleeDamageModifier = column[Option[Short]]("WeaponMeleeDamageModifier", O.SqlType("SMALLINT"))
    def meleeQualities = column[Option[String]]("WeaponMeleeQualities", O.Length(20, true))
    def meleeWeaponGroupTalent = column[Option[Int]]("WeaponMeleeGroupTalent")
    def * = (weaponId, meleeTwoHanded, meleeDamageModifier, meleeQualities, meleeWeaponGroupTalent).mapTo[WeaponMeleeRow]
    // Foreign keys
    def item = foreignKey("", weaponId, tableItem)(_.itemId)
    def weaponGroupTalent = foreignKey("", meleeWeaponGroupTalent, tableTalent)(_.talentId)
  }
  val tableWeaponMelee = TableQuery[TableWeaponMelee]
  
  
  case class WeaponRangedRow(id: Int, twoHanded: Boolean, baseDamage: Option[String], damageModifier: Option[Short], rangeShort: Option[Short],
      rangeLong: Option[Short], reload: Option[Short], ammoItemId: Option[Int], magazineSize: Option[Short], qualitiesRaw: Option[String], 
      weaponGroupTalentId: Option[Int]) extends DataPropertyRow {
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
  }
  
  /**
   * WEAPONRANGED
   */
  class TableWeaponRanged(tag: Tag) extends Table[WeaponRangedRow](tag, "WEAPONRANGED") {
    def weaponId = column[Int]("WeaponItemId", O.PrimaryKey, O.Unique)
    def rangedTwoHanded = column[Boolean]("WeaponRangedTwoHanded", O.Default(true))
    def rangedDamageBase = column[Option[String]]("WeaponRangedDamageBase", O.Length(3, true))
    def rangedDamageModifier = column[Option[Short]]("WeaponRangedDamageModifier", O.SqlType("SMALLINT"))
    def rangeShort = column[Option[Short]]("WeaponRangeShort", O.SqlType("SMALLINT"))
    def rangeLong = column[Option[Short]]("WeaponRangeLong", O.SqlType("SMALLINT"))
    def rangedReload = column[Option[Short]]("WeaponReload", O.SqlType("SMALLINT"))
    def rangedAmmoId = column[Option[Int]]("WeaponAmmunitionId")
    def rangedMagazineSize = column[Option[Short]]("WeaponMagazineSize", O.SqlType("SMALLINT"), O.Default(Some(1)))
    def rangedQualities = column[Option[String]]("WeaponRangedQualities", O.Length(20, true))
    def rangedWeaponGroupTalent = column[Option[Int]]("WeaponRangedGroupTalent")
    def * = (weaponId, rangedTwoHanded, rangedDamageBase, rangedDamageModifier, rangeShort, rangeLong,
            rangedReload, rangedAmmoId, rangedMagazineSize, rangedQualities, rangedWeaponGroupTalent).mapTo[WeaponRangedRow]
    // Foreign keys
    def item = foreignKey("", weaponId, tableItem)(_.itemId)
    def ammoItem = foreignKey("", rangedAmmoId, tableItem)(_.itemId)
    def weaponGroupTalent = foreignKey("", rangedWeaponGroupTalent, tableTalent)(_.talentId)
  }
  val tableWeaponRanged = TableQuery[TableWeaponRanged]
  
  
  case class OlioRow(id: Int, name: String, careersRaw: Option[String], talentsRaw: Option[String]) extends DataPropertyRow {
    def initProperties =
    {
      Vector( new IntegerProperty(this, "olioId", id),
              new StringProperty(this, "olioName", name),
              new ObjectProperty(this, "olioCareers", careersRaw.getOrElse("").split(',').toVector),
              new ObjectProperty(this, "olioTalents", talentsRaw.getOrElse("").split(',').toVector) )
    }
  }
  
  /**
   * OLIO
   */
  class TableOlio(tag: Tag) extends Table[OlioRow](tag, "OLIO") {
    def olioId = column[Int]("OlioId", O.PrimaryKey, O.Unique)
    def olioName = column[String]("OlioName", O.Length(64, true), O.Default("Nimi"))
    def olioCareers = column[Option[String]]("OlioCareers", O.Length(128, true))
    def olioTalents = column[Option[String]]("OlioTalents", O.Length(128, true))
    def * = (olioId, olioName, olioCareers, olioTalents).mapTo[OlioRow]
  }
  val tableOlio = TableQuery[TableOlio]
  
  
  case class OlioAttributesRow(olio: Int, attribute: String, baseVal: Short) extends DataPropertyRow {
    def initProperties =
    {
      Vector( new IntegerProperty(this, "olioId", olio),
              new StringProperty(this, "attributeId", attribute),
              new IntegerProperty(this, "olioAttributeBaseVal", baseVal.asInstanceOf[Int]) )
    }
  }
  
  /**
   * OLIOATTRIBUTES
   */
  class TableOlioAttributes(tag: Tag) extends Table[OlioAttributesRow](tag, "OLIOATTRIBUTES") {
    def olioId = column[Int]("OlioId")
    def attributeId = column[String]("Attribute", O.Length(3, true))
    def olioAttributeBaseVal = column[Short]("OlioAttributeBaseVal", O.SqlType("SMALLINT (3)"), O.Default(0))
    def * = (olioId, attributeId, olioAttributeBaseVal).mapTo[OlioAttributesRow]
    // Primary key
    def pk = primaryKey("", (olioId, attributeId))
    // Foreign Keys
    def olio = foreignKey("", olioId, tableOlio)(_.olioId)
    def attribute = foreignKey("attributeId_FK", attributeId, tableAttribute)(_.attrId)
  }
  val tableOlioAttributes = TableQuery[TableOlioAttributes]
  
  
  case class OlioSkillsRow(olio: Int, skill: Int, skillTrained: Short) extends DataPropertyRow {
    def initProperties =
    {
      Vector( new IntegerProperty(this, "olioId", olio),
              new IntegerProperty(this, "skillId", skill),
              new IntegerProperty(this, "olioSkillTrained", skillTrained.asInstanceOf[Int]) )
    }
  }
  
  /**
   * OLIOSKILLS
   */
  class TableOlioSkills(tag: Tag) extends Table[OlioSkillsRow](tag, "OLIOSKILLS") {
    def olioId = column[Int]("OlioId")
    def skillId = column[Int]("SkillId")
    def olioSkillTrained = column[Short]("OlioSkillTrained", O.SqlType("SMALLINT (1)"), O.Default(0))
    def * = (olioId, skillId, olioSkillTrained).mapTo[OlioSkillsRow]
    // Primary key
    def pk = primaryKey("", (olioId, skillId))
    // Foreign Keys
    def olio = foreignKey("", olioId, tableOlio)(_.olioId)
    def skill = foreignKey("", skillId, tableSkill)(_.skillId)
  }
  val tableOlioSkills = TableQuery[TableOlioSkills]
  
  
  val tables = Vector(tableAttribute, tableSkill, tableTalent, tableAvailability, tableItem, tableWeaponQualities, tableWeaponMelee,
                     tableWeaponRanged, tableOlio, tableOlioAttributes, tableOlioSkills
                     )
  // all table schemas ++'d together
  val schema = tables.map(_.schema)
                     .reduceLeft((total, current) => total ++ current) 
    /*tableAttribute.schema ++ tableSkill.schema ++ tableTalent.schema ++ tableAvailability.schema ++ tableItem.schema ++ tableWeaponQualities.schema ++
    tableWeaponMelee.schema ++  ++ tableOlio.schema
    * 
    */
  
  
}