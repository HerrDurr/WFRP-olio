package dataUI

import scalafx.beans.property._
import scalafx.scene.control._
import scalafx.scene.layout._
import dataElements.DataHelper.DataPropertyRow
import scalafx.collections.ObservableBuffer

object ControlFactory {
  
  /**
 	 * Simple Label which automatically listens to changes in the parameter
	 * stringProperty.
	 */
  def dataLabel(property: StringProperty): Label =
  {
    val res = new Label(property.value)
    res.text <== property
    res
  }
  /*
  def createDataLabel(property: IntegerProperty) = 
  {
    val res = new Label(property.value.toString)
    res.text <== property
    res
  }
  * 
  */
  
  /**
   * Creates a TextField that directly edits the given StringProperty.
   */
  def dataTextField(property: StringProperty): TextField =
  {
    val aRes = new TextField
    aRes.text <==> property
    aRes
  }
  
  /**
   * Wrapped in a VBox: a TextField that edits the given 
   * property, and a label above it which displays the 
   * property name.
   */
  def titledTextField(property : StringProperty): VBox =
  {
    val aRes = new VBox
    aRes.children = List(new Label(property.name), dataTextField(property))
    aRes
  }
  
  /*
  def dataPropertyTable(aRows: ObservableBuffer[DataPropertyRow]) =
  {
    val aRes = new TableView(aRows)
    
    // tässä olisi käyttöä Shapelessille!!
    // could make a common case with a Polymorphic function!!
    for (aProp <- aRows.head.properties)
    {
      val propCol = new TableColumn
    }
  }
  * 
  */
  
}