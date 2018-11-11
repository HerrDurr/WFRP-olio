package dataUI

import scalafx.scene.control.Label
import scalafx.beans.property.StringProperty
import scalafx.delegate.SFXDelegate

/**
 * Simple Label which automatically listens to changes in the parameter
 * stringProperty.
 */
class DataLabel(val stringProperty: StringProperty) extends Label(stringProperty.value) with SFXDelegate[javafx.scene.control.Label] {
  
  // stringProperty's value changes => this label's value changes
  this.text <== this.stringProperty
  
}