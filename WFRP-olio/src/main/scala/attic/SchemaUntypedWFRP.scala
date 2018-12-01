package attic

//import slick.driver.SQLiteDriver.api._
import dataElements.DataHelper._
import scalafx.beans.property._
import scalafx.collections._
import shapeless._
import olioIO.WFRPContext
//import io.getquill.{SqliteJdbcContext, SqliteDialect, CamelCase, MappedEncoding}
//import io.getquill.context.Context

//import io.getquill.Embedded


object SchemaUntypedWFRP {
  
  case class Attribute(val idTag: String, val name: String)/* extends DataPropertyRow {
    
    //def tupled = (idTag, name)
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
  */
  /*object Attribute extends Enumeration {
    type Attribute = Value
    val WS = Value(0)
    val BS = Value(1)
    val S = Value(2)
    val T = Value(3)
    val 
  }
  * 
  */
  
  
  case class Skill(id: Int, name: String, attribute: String, 
      isBasic: Boolean) /*extends DataPropertyRow*/ {
    /*def initProperties =
    {
      Vector(new IntegerProperty(this, "skillId", id),
             new StringProperty(this, "skillName", name),
             new StringProperty(this, "skillAttribute", attribute.value),
             new BooleanProperty(this, "skillIsBasic", isBasic))
    }
    * 
    */
  }/*
  object Skill {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Basic(val value: Boolean) extends AnyVal //MappedTo[Boolean]
  }
  * 
  */
  
  
  case class Talent(id: Int, name: String, description: Option[String], 
      weaponGroup: Option[String]) extends DataPropertyRow {
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
  }/*
  object Talent {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Description(val value: String) extends AnyVal //MappedTo[String]
    case class WeaponGroup(val value: String) extends AnyVal //MappedTo[String]
    
    /**
     * Commatext of Talent Ids
     */
    case class Talents(val value: String) extends AnyVal
  }
  * 
  */
  
  case class Availability(idTag: String, name: String, modifier: Option[Short]) extends DataPropertyRow {
    /*def initProperties =
    {
      Vector( new StringProperty(this, "availabilityId", idTag),
              new StringProperty(this, "availabilityName", name),
              new IntegerProperty(this, "availabilityModifier", modifier.getOrElse(0).asInstanceOf[Int]) )
    }
    * 
    */
  }
  object Availability {/*
    case class IdTag(val value: String) extends AnyVal //MappedTo[String]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Modifier(val value: Short) extends AnyVal //MappedTo[Short]
    * 
    */
    
    def avgId = "Av"
  }
  
  case class Item(id: Int, name: String, craftsmanship: String, encumbrance: Short, 
      cost: Option[String], availability: Option[String]) extends DataPropertyRow {
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
  object Item {/*
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    //case class Craftsmanship(val value: Char) extends AnyVal //MappedTo[Char]
    case class Encumbrance(val value: Short) extends AnyVal //MappedTo[Short]
    case class Cost(val value: String) extends AnyVal //MappedTo[String]
    * 
    */
    
    def createNew: Item = {
      //new Item(Id(-1), Name(""), Craftsmanship.byEnumOrThrow("N"), Encumbrance(0), Some(Cost("")), Some(Availability.avgId))
      new Item(-1, "", "N", 0, Some(""), Some(Availability.avgId))
    }
  }
  //case class Craftsmanship(val value: String) extends AnyVal //MappedTo[Char]
  
  object Craftsmanship extends Enumeration {
    type Craftsmanship = Value
    //val enums = List('P','N','G','B').zipWithIndex
    // Had to use Strings instead of Chars since Char encoding hasn't been implemented in Quill.. could try 
    // to do that myself, though
    lazy val enums = List("P","N","G","B").zipWithIndex // List('P','N','G','B').zipWithIndex //.zip(Craftsmanship.values)
    // not using the direct numbers is semantics, really, but
    // being sure is being sure!
    val Poor = Value(enums(0)._2)
    val Normal = Value(enums(1)._2)
    val Good = Value(enums(2)._2)
    val Best = Value(enums(3)._2)
    
    def enum(id: Int): String = {
      enums.find(_._2 == id).getOrElse(throw new IllegalArgumentException(s"Unknown Craftsmanship: $id"))._1
    }
    def byEnum(enum: String): Option[Craftsmanship] = {
      val idxPair = enums.find(_._1 == enum)
      if (idxPair.isEmpty)
        None
      else
        byId(idxPair.get._2)
    }
    def byEnumOrThrow(enum: String): Craftsmanship = {
      byEnum(enum).getOrElse(throw new IllegalArgumentException(s"Unknown Craftsmanship: $enum"))
    }
    def byId(id: Int): Option[Craftsmanship] = Craftsmanship.values.find(_.id == id)
    def byIdOrThrow(id: Int): Craftsmanship = byId(id).getOrElse(throw new IllegalArgumentException(s"Unknown Craftsmanship: $id"))
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
  
  case class WeaponQuality(idTag: String, name: String) extends DataPropertyRow {
    /*def initProperties =
    {
      Vector( new StringProperty(this, "quality", idTag),
              new StringProperty(this, "qualityName", name) )
    }
    * 
    */
  }
  object WeaponQuality {/*
    case class IdTag(val value: String) extends AnyVal //MappedTo[String]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    * 
    */
  }
  
  
  case class WeaponMelee(id: Int, twoHanded: Boolean, damageModifier: Option[Short], 
      qualities: Array[String], /*qualitiesRaw: Option[String]*/ weaponGroupTalentId: Option[Int]) extends DataPropertyRow {
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
  object WeaponMelee {/*
    case class TwoHanded(val value: Boolean) extends AnyVal //MappedTo[Boolean]
    case class DamageMod(val value: Short) extends AnyVal //MappedTo[Short]
    //case class Qualities(val value: String) extends MappedTo[String]
     * 
     */
  }
  
  
  case class WeaponRanged(id: Int, twoHanded: Boolean, damageBase: Option[String], 
                             damageModifier: Option[Short], rangeShort: Option[Short], 
                             rangeLong: Option[Short], reload: Option[Short], 
                             ammunitionId: Option[Int], magazineSize: Option[Short], 
                             qualities: Array[String], weaponGroupTalentId: Option[Int]) /*extends DataPropertyRow*/ {
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
  object WeaponRanged {/*
    case class TwoHanded(val value: Boolean) extends AnyVal //MappedTo[Boolean]
    case class DamageBase(val value: String) extends AnyVal //MappedTo[String]
    case class DamageMod(val value: Short) extends AnyVal //MappedTo[Short]
    case class RangeShort(val value: Short) extends AnyVal //MappedTo[Short]
    case class RangeLong(val value: Short) extends AnyVal //MappedTo[Short]
    case class Reload(val value: Short) extends AnyVal //MappedTo[Short]
    case class MagazineSize(val value: Short) extends AnyVal //MappedTo[Short]
    * 
    */
  }
  
  /**
   * Career Entries not stored in db. If a Career has no other that exits to it, it can be taken by anyone
   * (of course with any other restrictions still applicable).
   */
  case class Career(id: Int, name: String, description: Option[String], attributes: Int,
      skills: Array[Int], skillOptions: Array[Array[Int]], talents: Array[Int], talentOptions: Array[Array[Int]],
      careerExits: Array[Int])
  // attributes in different table
  // trappings maybe don't need to be implemented (yet)
  
  object Career {/*
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Description(val value: String) extends AnyVal //MappedTo[String]
    /**
     * Commatext of Career Ids
     */
    case class Careers(val value: String) extends AnyVal
    * 
    */
  }
  
  
  // TODO: Career Table and flesh out Olio Table
  case class Olio(id: Int, name: String, baseAttributes: Int,
      advancedAttributes: Option[Int], careers: Option[Array[Int]] /*Array[Career.Id]*/, talents: Option[Array[Int]],
      skills: Option[Array[Int]] /*Array[TrainedSkill.Id]*/) extends DataPropertyRow {
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
  object Olio {/*
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    * 
    */
  }
  
  
  /**
   * DEPRECATED - or is it?
   */
  case class OlioAttributes(olioId: Int, attribute: String, baseVal: Short) extends DataPropertyRow {
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
  
  /**
   * DEPRECATED
   */
  case class OlioSkills(olioId:  Int, skillId: Int, skillTrained: Short) extends DataPropertyRow {
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
  
  case class AttributeSet(id: AttributeSet.Id, weaponSkill: AttributeSet.WS, ballisticSkill: AttributeSet.BS,
      strength: AttributeSet.S, toughness: AttributeSet.T, agility: AttributeSet.Ag, intelligence: AttributeSet.Int,
      willPower: AttributeSet.WP, fellowship: AttributeSet.Fel, attacks: AttributeSet.A, wounds: AttributeSet.W,
      movement: AttributeSet.M, magic: AttributeSet.Mag, insanityPoints: AttributeSet.IP, fatePoints: AttributeSet.FP) {
    
    /*def apply(id: AttributeSet.Id) = 
    {
      import dbContext._
      import AttributeSet._
      val flat = id.value
      val qAS = quote {
        query[AttributeSet].filter(_.id == lift(id))
      }
      val res = dbContext.run(qAS)
      AttributeSet.createEmpty(id)
    }
    * 
    */
  
  }
  
  //case class AttributeSetId(val value: Integer) extends AnyVal
  object AttributeSet {
    case class Id(val value: Integer) extends AnyVal
    case class WS(val value: Short) extends AnyVal {
      def name = "Weapon Skill"
    }
    case class BS(val value: Short) extends AnyVal {
      def name = "Ballistic Skill"
    }
    case class S(val value: Short) extends AnyVal {
      def name = "Strength"
    }
    case class T(val value: Short) extends AnyVal {
      def name = "Toughness"
    }
    case class Ag(val value: Short) extends AnyVal {
      def name = "Agility"
    }
    case class Int(val value: Short) extends AnyVal {
      def name = "Intelligence"
    }
    case class WP(val value: Short) extends AnyVal {
      def name = "Will Power"
    }
    case class Fel(val value: Short) extends AnyVal {
      def name = "Fellowship"
    }
    case class A(val value: Short) extends AnyVal {
      def name = "Attacks"
    }
    case class W(val value: Short) extends AnyVal {
      def name = "Wounds"
    }
    case class M(val value: Short) extends AnyVal {
      def name = "Movement"
    }
    case class Mag(val value: Short) extends AnyVal {
      def name = "Magic"
    }
    case class IP(val value: Short) extends AnyVal {
      def name = "Insanity Points"
    }
    case class FP(val value: Short) extends AnyVal {
      def name = "Fate Points"
    }
    def createEmpty(id: Id): AttributeSet = {
      new AttributeSet( id, WS(0), BS(0), S(0), T(0), Ag(0), Int(0), WP(0), Fel(0),
                        A(0), W(0), M(0), Mag(0), IP(0), FP(0) )
    }
    def createEmpty: AttributeSet = {
      this.createEmpty(AttributeSet.Id(-1))
    }
  }
  
  case class TrainedSkill(id: TrainedSkill.Id, skill: Skill, level: TrainedSkill.Level,
      trainedCareer: Option[Career])
  object TrainedSkill {
    case class Id(val value: Integer) extends AnyVal
    case class Level(val value: Short) extends AnyVal
    
    /**
     * Commatext
     */
    case class TrainedSkills(val value: String) extends AnyVal
  }
  
  
  lazy val dbContext = new WFRPContext //new SqliteJdbcContext(CamelCase, "wfrpdb")
  
}