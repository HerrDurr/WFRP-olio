package dataUI

import scalafx.scene.layout.VBox
import scalafx.beans.property.StringProperty
import scalafx.scene.control.{Label,TextField}
import scalafx.delegate.{AlignmentDelegate, SFXDelegate}

class TitledTextField(val dataProperty : StringProperty) extends VBox
  with AlignmentDelegate[javafx.scene.layout.VBox] with SFXDelegate[javafx.scene.layout.VBox]{
  
  val label = new Label(dataProperty.name)
  val textField = new TextField
  textField.text <==> dataProperty
  this.children = List(label, textField)
}