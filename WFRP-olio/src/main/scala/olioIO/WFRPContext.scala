package olioIO

import io.getquill.{SqliteJdbcContext, SqliteDialect, CamelCase}
import SchemaWFRP._
import dataElements.DataHelper._
import dataWFRP.Types._
import dataElements.SQLiteQuerier

class WFRPContext extends SqliteJdbcContext(CamelCase, "wfrpdb") with SQLiteQuerier {
  
  
  
  /*
   * Encodings and decodings for IDs and/or classes! Because while AnyVals
   * are supported by Quill from the shelf, it seems there's still something
   * wrong with what I'm doing.
   */
  implicit val encodeAttributeSetId = MappedEncoding[AttributeSet.Id, Int](_.value)
  implicit val decodeAttributeSetId = MappedEncoding[Int, AttributeSet.Id](AttributeSet.Id(_))
  
  // This was dangerous, it seems. Resulted in some kind of endless loop or other...
  //implicit val encodeAttributeSet = MappedEncoding[AttributeSet, Int](_.id.value)
  //implicit val decodeAttributeSet: MappedEncoding[Int, AttributeSet] =
  //    MappedEncoding[Int, AttributeSet]{ value: Int => AttributeSet.queryById(AttributeSet.Id(value)).getOrElse(AttributeSet.createEmpty) }
  
  implicit val encodeSkillListWithOptionals = MappedEncoding[Array[Array[Skill.Id]], Option[String]] { 
    skills : Array[Array[Skill.Id]] => ArrayedIntOpsToCommaOps( skills.map( _.map(_.value) ) )//.getOrElse("") 
  }
  implicit val decodeSkillListWithOptionals = MappedEncoding[Option[String], Array[Array[Skill.Id]]] {
    GetCommaOpsArrayedInt(_).map(_.map(Skill.Id(_)))
  }
  
  implicit val encodeTalentListWithOptionals = MappedEncoding[Array[Array[Talent.Id]], Option[String]] { 
    tals : Array[Array[Talent.Id]] => ArrayedIntOpsToCommaOps( tals.map( _.map(_.value) ) )//.getOrElse("") 
  }
  implicit val decodeTalentListWithOptionals = MappedEncoding[Option[String], Array[Array[Talent.Id]]] {
    GetCommaOpsArrayedInt(_).map(_.map(Talent.Id(_)))
  }
  
  implicit val encodeCareerList = MappedEncoding[Array[Career.Id], Option[String]] { 
    cars : Array[Career.Id] => IntArrayToCommaTextOpt( cars.map(_.value) )//.getOrElse("") 
  }
  implicit val decodeCareerList = MappedEncoding[Option[String], Array[Career.Id]] {
    GetCommaOpsInt(_).map( Career.Id(_) )
  }
  
  /*
   * Item
   */
  //implicit val encodeItemId = MappedEncoding[Item.Id, Int](_.value)
  //implicit val decodeItemId = MappedEncoding[Int, Item.Id](Item.Id(_))
  //implicit val encodeItemCraftsmanship = MappedEncoding[Craftsmanship, Char](_.value)
  //implicit val decodeItemCraftsmanship = MappedEncoding[Char, Craftsmanship](Craftsmanship(_))
  
  // why the f can't the compiler find these??!!
  implicit val encodeCraftsmanship: MappedEncoding[Craftsmanship.Craftsmanship, String] = 
      MappedEncoding[Craftsmanship.Craftsmanship, String]{ crft: Craftsmanship.Craftsmanship => Craftsmanship.enum(crft.id) }
  implicit val decodeCraftsmanship: MappedEncoding[String, Craftsmanship.Craftsmanship] =
      MappedEncoding[String, Craftsmanship.Craftsmanship]{ Craftsmanship.byEnumOrThrow(_) }
  
  implicit val encodeTalentExplainType: MappedEncoding[TalentExplain.TalentExplainType, String] = 
      MappedEncoding[TalentExplain.TalentExplainType, String]{ tet: TalentExplain.TalentExplainType => TalentExplain.enum(tet) }
  implicit val decodeTalentExplainType: MappedEncoding[String, TalentExplain.TalentExplainType] =
      MappedEncoding[String, TalentExplain.TalentExplainType]{ TalentExplain.byEnumOrThrow(_) }
  
  implicit val encodeWeaponQualities: MappedEncoding[Array[WeaponQuality.IdTag], String] = 
      MappedEncoding[Array[WeaponQuality.IdTag], String]{ aQuals: Array[WeaponQuality.IdTag] => ArrayToCommaTextOpt( aQuals.map(_.value) ).getOrElse("") }
  implicit val decodeWeaponQualities: MappedEncoding[String, Array[WeaponQuality.IdTag]] =
      MappedEncoding[String, Array[WeaponQuality.IdTag]]{ aStr: String => CommaTextOptToArray( Some(aStr) ).map(WeaponQuality.IdTag(_)) }
  
  implicit val encodeTrainedSkillId = MappedEncoding[TrainedSkill.Id, Int](_.value)
  implicit val decodeTrainedSkillId = MappedEncoding[Int, TrainedSkill.Id](TrainedSkill.Id(_))
  
  implicit val encodeSkill: MappedEncoding[Skill, Int] =
      MappedEncoding[Skill, Int]{ s: Skill => s.id.value }
  // TODO: do something about that .get call...
  implicit val decodeSkill: MappedEncoding[Int, Skill] =
      MappedEncoding[Int, Skill]{ id: Int => Skill.byId(id).map(_.asInstanceOf[Skill]).get }
  
  implicit val encodeCareer: MappedEncoding[Career, Int] =
      MappedEncoding[Career, Int]{ c: Career => c.id.value }
  implicit val decodeCareer: MappedEncoding[Int, Career] =
      MappedEncoding[Int, Career]{ id: Int => Career.byId(id).map(_.asInstanceOf[Career]).get }
  
  /*implicit val encodeCareerOption: MappedEncoding[Option[Career], Option[Int]] =
      MappedEncoding[Option[Career], Option[Int]]{ c: Option[Career] => c.map(_.id.value) }
  implicit val decodeCareerOption: MappedEncoding[Option[Int], Option[Career]] =
      MappedEncoding[Option[Int], Option[Career]]{
        id: Option[Int] =>
          if(id.isDefined)
            Career.byId(id.get).map(_.asInstanceOf[Career])
          else
            None
      }*/
  
  /*implicit val encodeAvailability: MappedEncoding[Availability, String] =
      MappedEncoding[Availability, String]{ a: Availability => a.idTag.value }
  // TODO: refactor that get!
  implicit val decodeAvailability: MappedEncoding[String, Availability] =
      MappedEncoding[String, Availability]
        { str: String => Availability.byTag(str).map(_.asInstanceOf[Availability]).get }*/
  
  /*
   * OLIO
   */
  //implicit val encodeOlioId = MappedEncoding[Olio.Id, Int](_.value)
  //implicit val decodeOlioId = MappedEncoding[Int, Olio.Id](Olio.Id(_))
  /*implicit val encodeOlioCareers = MappedEncoding[Array[Career.Id], Option[String]]
      {c: Array[Career.Id] => IntArrayToCommaTextOpt(c.map(_.value)) }
  implicit val decodeOlioCareers = MappedEncoding[Option[String], Array[Career.Id]]
      {c: Option[String] => CommaTextOptToIntArray(c).map(Career.Id(_)) }
      * 
      */
  
  /*
   * Meta DSLs (see Quill docs). Mostly setting Quill to ignore
   * certain columns on updates/inserts. So that means mostly the
   * primary key values.
   */
  implicit val talentInsertMeta = insertMeta[Talent](_.id)
  implicit val talentUpdateMeta = updateMeta[Talent](_.id)
  
  // Never insert or update self-made ids
  implicit val attributeSetInsertMeta = insertMeta[AttributeSet](_.id)
  implicit val attributeSetUpdateMeta = updateMeta[AttributeSet](_.id)
  
  implicit val itemInsertMeta = insertMeta[Item](_.id)
  implicit val itemUpdateMeta = updateMeta[Item](_.id)
  
  implicit val olioInsertMeta = insertMeta[Olio](_.id)
  implicit val olioUpdateMeta = updateMeta[Olio](_.id)
  
  implicit val trainedSkillInsertMeta = insertMeta[TrainedSkill](_.id)
  implicit val trainedSkillUpdateMeta = updateMeta[TrainedSkill](_.id)
  
}