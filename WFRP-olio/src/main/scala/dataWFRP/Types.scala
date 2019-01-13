package dataWFRP

object Types {
  
  object TalentExplain extends Enumeration {
    type TalentExplainType = Value
    lazy val enumsIndexed = List("-", "L", "S", "W").zipWithIndex 
    
    val Null = Value( enumsIndexed.find(_._1 == "-").get._2 )
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
    
    def byEnumOrThrow(enum: String): TalentExplainType = {
      byEnum(enum).getOrElse(throw new IllegalArgumentException(s"Unknown TalentExplainType: $enum"))
    }
    
    def byId(id: Int): Option[TalentExplainType] = TalentExplain.values.find(_.id == id)
    
    def byIdOrThrow(id: Int): TalentExplainType = byId(id).getOrElse(throw new IllegalArgumentException(s"Unknown TalentExplainType: $id"))
  }
  
}