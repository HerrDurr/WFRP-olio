package olioIO

import SchemaWFRP._

object DataHelperWFRP {
  
  import dbContext._
  
  implicit class OlioHelper(olio: Olio) {
    
    def getBaseAttributes = {
      val q = quote {
        query[AttributeSet].filter(_.id == lift(olio.baseAttributes))
      }
      dbContext.run(q)
    }
    
  }
  
}