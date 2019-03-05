package olioIO

//import slick.driver.SQLiteDriver.api._
import dataElements.DataHelper._
import scalafx.beans.property._
import scalafx.collections._
import shapeless._
import dataWFRP.Types._
import shapeless.Generic
import dataElements.CachableObjects.{TCachableRowObject, TCachableRowCompanion}
import dataElements.CachableObjects.TCachableRowCompanion
import io.getquill.context.jdbc.JdbcContext
import dataElements.SQLiteQuerier
import dataElements.TCachingStorage
import dataElements.CachableObjects._
import dataElements.Rows._

//import io.getquill.{SqliteJdbcContext, SqliteDialect, CamelCase, MappedEncoding}
//import io.getquill.context.Context

//import io.getquill.Embedded


object SchemaWFRP {
  
  case class Effect(id: Effect.Id, name: Effect.Name) {
    
  }
  object Effect {
    case class Id(val value: Int) extends AnyVal
    case class Name(val value: String) extends AnyVal
  }
  
  case class Attribute(val idTag: Attribute.IdTag, val name: Attribute.Name/*idTag: String, name: String*/) /*extends DataPropertyRow*/ {
    
    //def tupled = (idTag, name)
    //val genericRow = Generic[AttributeRow] 
    
    /*override def initProps: HList = 
    {
      //genericRow.to(this).map(_.value).map(MapToProperty)
      (idTag.value :: name.value :: HNil).map(MapToProperty)
    }
    * 
    */
    
  }
  object Attribute {
    case class IdTag(val value: String) extends AnyVal //MappedTo[String] //extends AnyVal
    case class Name(val value: String) extends AnyVal //MappedTo[String] //extends AnyVal
  }
  
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
  
  
  case class Skill(id: Skill.Id, name: Skill.Name, attribute: Attribute.IdTag, 
      isBasic: Skill.Basic) extends TCachableRowObjectWithId(id.value) {
    
    /*def filterFunc(aRow : TCachableRowObject): Boolean = {
      if (aRow.isInstanceOf[Skill])
        aRow.asInstanceOf[Skill].id == (this.id)
      else
        false
    }
    * 
    */
    
    import dbContext._ 
    def saveToDB: Unit = insertOrUpdate(this, (s: Skill) => s.id == lift(this.id))
    def deleteFromDB: Unit = deleteRow(this, (s: Skill) => s.id == lift(this.id))
  }
  object Skill extends TCachableRowCompanionWithId[Skill] {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Basic(val value: Boolean) extends AnyVal //MappedTo[Boolean]
    def loadRows: List[Skill] = {
      dbContext.loadAll[Skill]
    }
    
    private lazy val fCache = new TCachingStorage[Skill](this, dbContext)
    def cache: TCachingStorage[Skill] = this.fCache
  }
  
  
  case class Talent(id: Talent.Id, name: Talent.Name, subTitle: Option[Talent.SubTitle],
      subType: TalentExplain.TalentExplainType, description: Option[Talent.Description]) 
      extends TCachableRowObjectWithId(id.value) {
    
    override def toString() = {
      var res = name.value
      if (subTitle.isDefined)
        res += " (" + subTitle.get.value + ")"
      res
    }
    
    /*def filterFunc(aRow : TCachableRowObject): Boolean = {
      if (aRow.isInstanceOf[Talent])
        aRow.asInstanceOf[Talent].id == (this.id)
      else
        false
    }*/
    
    import dbContext._ //ctx._
    def saveToDB: Unit = insertOrUpdate(this, (t: Talent) => t.id == lift(this.id))
    def deleteFromDB: Unit = deleteRow(this, (t: Talent) => t.id == lift(this.id))
    //def companion = Talent
  }
  object Talent extends TCachableRowCompanionWithId[Talent] {
    case class Id(val value: Int) extends AnyVal
    case class Name(val value: String) extends AnyVal
    case class SubTitle(val value: String) extends AnyVal
    case class Description(val value: String) extends AnyVal
    
    val lId = lens[Talent] >> 'id
    val lName = lens[Talent] >> 'name
    val lSubTitle = lens[Talent] >> 'subTitle
    val lSubType = lens[Talent] >> 'subType
    val lDescription = lens[Talent] >> 'description
    
    def createNew: Talent = new Talent(Id(-1), Name(""), None, TalentExplain.-, None)
    /**
     * Commatext of Talent Ids
     */
    case class Talents(val value: String) extends AnyVal
    def loadRows: List[Talent] = {
      import dbContext._
      loadAll[Talent]
    }
    
    private lazy val fCache = new TCachingStorage[Talent](this, dbContext)
    def cache: TCachingStorage[Talent] = this.fCache
  }
  
  case class Availability(idTag: Availability.IdTag, name: Availability.Name,
      modifier: Option[Availability.Modifier]) 
      //extends TAbstractRow(idTag :: HNil) {
      //(implicit ctx : WFRPContext)
      extends TCachableRowObjectWithTag(idTag.value) {
    /*def initProperties =
    {
      Vector( new StringProperty(this, "availabilityId", idTag),
              new StringProperty(this, "availabilityName", name),
              new IntegerProperty(this, "availabilityModifier", modifier.getOrElse(0).asInstanceOf[Int]) )
    }
    * 
    */
    
    override def toString() = {
      var res = this.name.value
      if (this.modifier.isDefined)
        res += " (" + this.modifier.get.value.toString + ")"
      res
    }
    
    /*def filterFunc(aRow : TCachableRowObject): Boolean = {
      if (aRow.isInstanceOf[Availability])
        aRow.asInstanceOf[Availability].idTag == (this.idTag)
      else
        false
    }*/
    
    def saveToDB: Unit = {
      import dbContext._
      insertOrUpdate( this, (av : Availability) => av.idTag == lift(this.idTag) )
    }
    def deleteFromDB : Unit = {
      import dbContext._
      deleteRow( this, (av : Availability) => av.idTag == lift(this.idTag) )
    }
    //def companion = Availability
    //def filterString: String = "IdTag = '" + this.idTag.value + "'"
  }
  object Availability extends TCachableRowCompanionWithTag[Availability] {
    case class IdTag(val value: String) extends AnyVal //MappedTo[String]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Modifier(val value: Short) extends AnyVal //MappedTo[Short]
    
    def avgId = IdTag("Av")
    def avg : Option[Availability] = this.byTag(this.avgId).map(_.asInstanceOf[Availability])
    def loadRows : List[Availability] = {
      import dbContext._
      loadAll[Availability]
    }
    
    private lazy val fCache = new TCachingStorage[Availability](this, dbContext)
    def cache: TCachingStorage[Availability] = this.fCache
  }
  
  case class Item(id: Item.Id, name: Item.Name, craftsmanship: Craftsmanship.Craftsmanship, encumbrance: Item.Encumbrance, 
      cost: Option[Item.Cost], availability: Option[Availability]) 
      extends TCachableRowObjectWithId(id.value) {
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
    //val nameProp = new StringProperty(this,"name",this.name.value)
    
    override def toString = this.name.value + " " + this.craftsmanship + ", Enc: " + this.encumbrance.value + ", Cost: " +
      this.cost.getOrElse(Item.Cost("-")).value
    
    /*def filterFunc(aRow : TCachableRowObject): Boolean = {
      if (aRow.isInstanceOf[Item])
        aRow.asInstanceOf[Item].id == (this.id)
      else
        false
    }
    * 
    */
    
    import dbContext._ //ctx._
    def saveToDB: Unit = insertOrUpdate(this, (s: Item) => s.id == lift(this.id))
    def deleteFromDB: Unit = deleteRow(this, (s: Item) => s.id == lift(this.id))
  }
  object Item extends TCachableRowCompanionWithId[Item] {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    //case class Craftsmanship(val value: Char) extends AnyVal //MappedTo[Char]
    case class Encumbrance(val value: Short) extends AnyVal //MappedTo[Short]
    case class Cost(val value: String) extends AnyVal //MappedTo[String]
    
    val lId = lens[Item] >> 'id
    val lName = lens[Item] >> 'name
    val lCraft = lens[Item] >> 'craftsmanship
    val lEnc = lens[Item] >> 'encumbrance
    val lCost = lens[Item] >> 'cost
    val lAvail = lens[Item] >> 'availability
    
    def createNew: Item = {
      new Item(Id(-1), Name(""), Craftsmanship.byEnumOrThrow("N"), Encumbrance(0), Some(Cost("")), Some(Availability.avgId))
    }
    
    def loadRows: List[Item] = dbContext.loadAll[Item]
    
    private lazy val fCache = new TCachingStorage[Item](this, dbContext)
    def cache: TCachingStorage[Item] = this.fCache
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
    def byName(aName : String): Option[Craftsmanship] = {
      val craft = { 
        aName.toLowerCase() match {
          case "normal" => Some(Normal)
          case "poor" => Some(Poor)
          case "good" => Some(Good)
          case "best" => Some(Best)
          case _ => None
        }
      }
      craft
    }
    def byEnumOrName(aStr : String): Option[Craftsmanship] = {
      val res1 = byEnum(aStr)
      if (res1.isDefined) res1
      else byName(aStr)
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
  
  case class WeaponQuality(idTag: WeaponQuality.IdTag, name: WeaponQuality.Name)
    //(implicit ctx : WFRPContext)
    extends TCachableRowObjectWithTag(idTag.value) {
    /*def initProperties =
    {
      Vector( new StringProperty(this, "quality", idTag),
              new StringProperty(this, "qualityName", name) )
    }
    * 
    */
    
    
    /*def filterFunc(aRow : TCachableRowObject): Boolean = {
      if (aRow.isInstanceOf[WeaponQuality])
        aRow.asInstanceOf[WeaponQuality].idTag == (this.idTag)
      else
        false
    }*/
    
    import dbContext._ //ctx.__
    //val test = quote {lift(idTag)}
    def saveToDB: Unit = insertOrUpdate(this, (wQ: WeaponQuality) => wQ.idTag == lift(this.idTag))
    def deleteFromDB: Unit = deleteRow(this, (wQ: WeaponQuality) => wQ.idTag == lift(this.idTag))
    //def filterString : String = "IdTag = '" + this.idTag.value + "'"
    
  }
  object WeaponQuality extends TCachableRowCompanionWithTag[WeaponQuality] {
    case class IdTag(val value: String) extends AnyVal //MappedTo[String]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    
    def loadRows: List[WeaponQuality] = dbContext.loadAll[WeaponQuality]
    def byId(aIdTag : IdTag): Option[WeaponQuality] = {
      import dbContext._
      val q = quote {
        query[WeaponQuality].filter{ wq: WeaponQuality => wq.idTag == lift(aIdTag) }
      }
      run(q).headOption
    }
    
    private lazy val fCache = new TCachingStorage[WeaponQuality](this, dbContext)
    def cache: TCachingStorage[WeaponQuality] = this.fCache
  }
  
  
  case class WeaponMelee(id: Item.Id, twoHanded: WeaponMelee.TwoHanded, damageModifier: Option[WeaponMelee.DamageMod], 
      qualities: Array[WeaponQuality.IdTag], /*qualitiesRaw: Option[String]*/ weaponGroupTalentId: Option[Talent.Id]) {
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
    
    val lId = lens[WeaponMelee] >> 'id
    val lTwoHanded = lens[WeaponMelee] >> 'twoHanded
    val lDamageModifier = lens[WeaponMelee] >> 'damageModifier
    val lQualities = lens[WeaponMelee] >> 'qualities
    val lWeaponGroupTalentId = lens[WeaponMelee] >> 'weaponGroupTalentId
    
    def createNew(aItem: Item) = {
      new WeaponMelee(aItem.id, TwoHanded(false), None, Array(), None)
    }
    def createNew = {
      new WeaponMelee(Item.Id(-1), TwoHanded(false), None, Array(), None)
    }
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
   * Career Entries not stored in db, only Exits. If a Career has no other that exits to it, it can be taken by anyone
   * (of course with any other restrictions still applicable).
   */
  case class Career(id: Career.Id, name: Career.Name, description: Option[Career.Description], attributes: AttributeSet,
      skills: Array[Array[Skill.Id]], talents: Array[Array[Talent.Id]],
      careerExits: Array[Career.Id])
  // trappings maybe don't need to be implemented (yet)
      extends TCachableRowObjectWithId(id.value) {
    
    
    /*def filterFunc(aRow : TCachableRowObject): Boolean = {
      if (aRow.isInstanceOf[Career])
        aRow.asInstanceOf[Career].id == (this.id)
      else
        false
    }*/
    
    import dbContext._ //ctx._
    def saveToDB: Unit = insertOrUpdate(this, (c : Career) => c.id == lift(this.id))
    def deleteFromDB : Unit = deleteRow(this, (c : Career) => c.id == lift(this.id))
    //def companion = Career
    //def filterString: String = "Id = " + this.id.value
  }
  object Career extends TCachableRowCompanionWithId[Career] {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    case class Description(val value: String) extends AnyVal //MappedTo[String]
    /**
     * Commatext of Career Ids
     */
    case class Careers(val value: String) extends AnyVal
    
    def loadRows : List[Career] = dbContext.loadAll[Career]
    
    private lazy val fCache = new TCachingStorage[Career](this, dbContext)
    def cache: TCachingStorage[Career] = this.fCache
  }
  
  case class Race(id: Race.Id, name: Race.Name) extends TCachableRowObjectWithId(id.value) {
     
    import dbContext._ 
    def saveToDB: Unit = insertOrUpdate(this, (r : Race) => r.id == lift(this.id))
    def deleteFromDB : Unit = deleteRow(this, (r : Race) => r.id == lift(this.id))
    
  }
  object Race extends TCachableRowCompanionWithId[Race] {
    case class Id(val value: Int) extends AnyVal
    case class Name(val value: String) extends AnyVal
    
    def loadRows: List[Race] = dbContext.loadAll[Race]
    
    private lazy val fCache = new TCachingStorage[Race](this, dbContext)
    def cache: TCachingStorage[Race] = this.fCache
  }
  
  
  // TODO: Career Table and flesh out Olio Table
  case class Olio(id: Olio.Id, name: Olio.Name, race: Race.Id, baseAttributes: AttributeSet.Id,
      advancedAttributes: Option[AttributeSet.Id], careers: Option[Career.Careers], talents: Option[Talent.Talents],
      skills: Option[TrainedSkill.TrainedSkills]) extends TCommonRowWithId(id.value) {
     
    import dbContext._ 
    def saveToDB: Unit = insertOrUpdate(this, (o : Olio) => o.id == lift(this.id))
    def deleteFromDB : Unit = deleteRow(this, (o : Olio) => o.id == lift(this.id))
  }
  object Olio extends TCommonRowCompanionWithId[Olio] {
    case class Id(val value: Int) extends AnyVal //MappedTo[Int]
    case class Name(val value: String) extends AnyVal //MappedTo[String]
    
    def loadRows: List[Olio] = dbContext.loadAll[Olio]
  }
  
  
  /**
   * DEPRECATED - or is it?
   */
  case class OlioAttributes(olioId: Olio.Id, attribute: Attribute.IdTag, baseVal: OlioAttributes.BaseVal) {
  }
  object OlioAttributes {
    case class BaseVal(val value: Short) extends AnyVal //MappedTo[Short]
  }
  
  /**
   * DEPRECATED
   */
  case class OlioSkills(olioId: Olio.Id, skillId: Skill.Id, skillTrained: OlioSkills.Trained) {
  }
  object OlioSkills {
    case class Trained(val value: Short) extends AnyVal //MappedTo[Short]
  }
  
  case class AttributeSet(id: AttributeSet.Id, weaponSkill: AttributeSet.WS, ballisticSkill: AttributeSet.BS,
      strength: AttributeSet.S, toughness: AttributeSet.T, agility: AttributeSet.Ag, intelligence: AttributeSet.Int,
      willPower: AttributeSet.WP, fellowship: AttributeSet.Fel, attacks: AttributeSet.A, wounds: AttributeSet.W,
      /*movement: AttributeSet.M,*/ magic: AttributeSet.Mag/*, insanityPoints: AttributeSet.IP, fatePoints: AttributeSet.FP*/)
      extends TCommonRowWithId(id.value) {
     
    import dbContext._ 
    def saveToDB: Unit = insertOrUpdate(this, (a : AttributeSet) => a.id == lift(this.id))
    def deleteFromDB : Unit = deleteRow(this, (a : AttributeSet) => a.id == lift(this.id))
  }
  
  object AttributeSet extends TCommonRowCompanionWithId[AttributeSet] {
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
    case class Mag(val value: Short) extends AnyVal {
      def name = "Magic"
    }
    /*case class M(val value: Short) extends AnyVal {
      def name = "Movement"
    }
    case class IP(val value: Short) extends AnyVal {
      def name = "Insanity Points"
    }
    case class FP(val value: Short) extends AnyVal {
      def name = "Fate Points"
    }*/
    
    val lId  = lens[AttributeSet] >> 'id
    val lWS  = lens[AttributeSet] >> 'weaponSkill
    val lBS  = lens[AttributeSet] >> 'ballisticSkill
    val lS   = lens[AttributeSet] >> 'strength
    val lT   = lens[AttributeSet] >> 'toughness
    val lAg  = lens[AttributeSet] >> 'agility
    val lInt = lens[AttributeSet] >> 'intelligence
    val lWP  = lens[AttributeSet] >> 'willPower
    val lFel = lens[AttributeSet] >> 'fellowship
    val lA   = lens[AttributeSet] >> 'attacks
    val lW   = lens[AttributeSet] >> 'wounds
    val lMag = lens[AttributeSet] >> 'magic
    
    def createEmpty(id: Id): AttributeSet = {
      new AttributeSet( id, WS(0), BS(0), S(0), T(0), Ag(0), Int(0), WP(0), Fel(0),
                        A(0), W(0), Mag(0) )//M(0), , IP(0), FP(0) )
    }
    def createEmpty: AttributeSet = {
      this.createEmpty(AttributeSet.Id(-1))
    }
    /*def byId(aId: Id): AttributeSet = {
      import dbContext._
      val q = quote {
        query[AttributeSet].filter{ aSet: AttributeSet => aSet.id == lift(aId) }
      } 
      dbContext.run(q).headOption.getOrElse(createEmpty)
    }*/
    def loadRows: List[AttributeSet] = dbContext.loadAll[AttributeSet]
  
    def queryById(aId: AttributeSet.Id) =
    {
      import dbContext._
      // Eclipse's compiler ain't happy about this, but it seems fine?
      val q = quote {
        query[AttributeSet].filter{ aSet: AttributeSet => aSet.id == lift(aId) }
      }
      dbContext.run(q).headOption
    }
  }
  
  case class TrainedSkill(id: TrainedSkill.Id, skill: Skill, level: TrainedSkill.Level,
      trainedCareer: Option[Career]) extends TCommonRowWithId(id.value) {
     
    import dbContext._ 
    def saveToDB: Unit = insertOrUpdate(this, (tS : TrainedSkill) => tS.id == lift(this.id))
    def deleteFromDB : Unit = deleteRow(this, (tS : TrainedSkill) => tS.id == lift(this.id))
  }
  object TrainedSkill extends TCommonRowCompanionWithId[TrainedSkill] {
    case class Id(val value: Integer) extends AnyVal
    case class Level(val value: Short) extends AnyVal
    
    def loadRows: List[TrainedSkill] = dbContext.loadAll[TrainedSkill]
    
    /**
     * Commatext
     */
    case class TrainedSkills(val value: String) extends AnyVal
  }
  
  
  lazy val dbContext = new WFRPContext //new SqliteJdbcContext(CamelCase, "wfrpdb")
  
}