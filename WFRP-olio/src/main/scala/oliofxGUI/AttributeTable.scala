package oliofxGUI

import scalafx.scene.control.TableView
import slick.driver.SQLiteDriver.api._
import olioMain.{Olio}
import olioIO.SchemaWFRP._
import olioIO.WFRPDataHelper._

class AttributeTable(olioRow : OlioRow) extends TableView[OlioAttributesRow] {
  //this.colu
  
  //private val attributeQuery: DBIO[Seq[AttributeRow]] = tableAttribute.result
  //val attributes = tableAttribute.result
  
  /*val olioAttributeQuery: Query[] = for
  {
    attrs <- tableOlioAttributes
    olio <- attrs.olio
    attribute <- attrs.attribute
  } yield (attribute, attrs.olioAttributeBaseVal)
  *
  * 
  */
  
  val olioAttributesQuery = tableOlio.filter(_.olioId === olioRow.id).withAttributes
  
  
  
}