package olioIO

import olioIO.SchemaWFRP._
import slick.driver.SQLiteDriver.api._

object WFRPDataHelper {
  
  implicit class OlioExtensions[C[_]](aQuery: Query[TableOlio, OlioRow, C]) {
    
    /**
     * Joins Olio with its Attributes
     */
    def withOlioAttributes /*: Query[(TableOlio, TableOlioAttributes), (OlioRow, TableOlioAttributes.TableElementType), C] */ =
    {
      aQuery.join(tableOlioAttributes).on(_.olioId === _.olioId)
    }
    
    /**
     * Joins Olio with its Skills
     */
    def withOlioSkills =
    {
      aQuery.join(tableOlioSkills).on(_.olioId === _.olioId)
    }
    
  }
  
  
  
}