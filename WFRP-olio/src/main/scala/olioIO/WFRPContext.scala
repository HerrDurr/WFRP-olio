package olioIO

import io.getquill.{SqliteJdbcContext, SqliteDialect, CamelCase}
import SchemaWFRP._
import dataElements.DataHelper._

class WFRPContext extends SqliteJdbcContext(CamelCase, "wfrpdb") {
  
  /*
   * Encodings and decodings for IDs and/or classes! Because while AnyVals
   * are supported by Quill from the shelf, it seems there's still something
   * wrong with what I'm doing.
   */
  implicit val encodeAttributeSetId = MappedEncoding[AttributeSet.Id, Int](_.value)
  implicit val decodeAttributeSetId = MappedEncoding[Int, AttributeSet.Id](AttributeSet.Id(_))
  
  //implicit val encodeAttributeSet = MappedEncoding[AttributeSet, Int](_.id.value)
  //implicit val decodeAttributeSet = MappedEncoding[Int, AttributeSet](value: Int => AttributeSet.createEmpty(AttributeSet.Id(value))))
  
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
  // Never insert or update self-made ids
  implicit val attributeSetInsertMeta = insertMeta[AttributeSet](_.id)
  implicit val attributeSetUpdateMeta = updateMeta[AttributeSet](_.id)
  
  implicit val itemInsertMeta = insertMeta[Item](_.id)
  implicit val itemUpdateMeta = updateMeta[Item](_.id)
  
  implicit val olioInsertMeta = insertMeta[Olio](_.id)
  implicit val olioUpdateMeta = updateMeta[Olio](_.id)
  
}