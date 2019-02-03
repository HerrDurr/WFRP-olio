package dataWFRP

import olioIO.SchemaWFRP._

object Types {
  
  /*object EffectType extends Enumeration {
    type EffectType = Value
    val SkillBonus, AttributeBonus, NullifyEffect, FortunePoint, DamageBonus = Value
    
    val enums = this.values.zip( Vector(
        "SB", //SkillBonus
        "AB", //AttributeBonus
        "NE", //NullifyEffect
        "FP", //FortunePoint
        "DB", //DamageBonus
        )
    )
  }
  * 
  */
  /*
   * Still sketching these...
   */
  trait EffectType {
    
  }
  case class SkillBonus(val skill: Skill, val bonus: Int) extends EffectType {
    
  }
  
  object TalentExplain extends Enumeration {
    type TalentExplainType = Value
    lazy val enumsIndexed = List("-", "L", "S", "W").zipWithIndex 
    
    val - = Value( enumsIndexed.find(_._1 == "-").get._2 )
    val Lore = Value( enumsIndexed.find(_._1 == "L").get._2 )
    val Spell = Value( enumsIndexed.find(_._1 == "S").get._2 )
    val WeaponGroup = Value( enumsIndexed.find(_._1 == "W").get._2 )
    
    def enum(tet: TalentExplainType): String = {
      this.enum(tet.id)
    }
    
    def enum(id: Int): String = {
      enumsIndexed.find(_._2 == id).getOrElse(throw new IllegalArgumentException(s"Unknown TalentExplainType: $id"))._1
    }
    
    def byEnum(enum: String): Option[TalentExplainType] = {
      val idxPair = enumsIndexed.find(_._1 == enum)
      if (idxPair.isEmpty)
        None
      else
        byId(idxPair.get._2)
    }
    
    def byEnumOrDefault(enum: String): TalentExplainType = {
      byEnum(enum).getOrElse(this.-)
    }
    
    def byEnumOrThrow(enum: String): TalentExplainType = {
      byEnum(enum).getOrElse(throw new IllegalArgumentException(s"Unknown TalentExplainType: $enum"))
    }
    
    def byId(id: Int): Option[TalentExplainType] = TalentExplain.values.find(_.id == id)
    
    def byIdOrThrow(id: Int): TalentExplainType = byId(id).getOrElse(throw new IllegalArgumentException(s"Unknown TalentExplainType: $id"))
  }
  
}