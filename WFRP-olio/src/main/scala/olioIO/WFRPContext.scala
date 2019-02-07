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
  
  implicit val encodeAttributeSet = MappedEncoding[AttributeSet, Int](_.id.value)
  implicit val decodeAttributeSet = MappedEncoding[Int, AttributeSet]{ value: Int => AttributeSet.byId(AttributeSet.Id(value)) }
  
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
  
}