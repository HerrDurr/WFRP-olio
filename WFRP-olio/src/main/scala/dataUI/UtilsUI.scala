package dataUI

import scalafx.scene.control.{TextField, ComboBox}
import javafx.beans.{value => jfxbv}
import java.lang.Boolean
import scalafx.beans.value.ObservableValue

object UtilsUI {
  
  
  implicit class TextFieldOps(val aField: TextField) extends AnyVal {
    
    def addFocusLostEvent(aFunc: => Unit) {
      val listener = new jfxbv.ChangeListener[Boolean] {
        def changed(observable: jfxbv.ObservableValue[_ <: Boolean], oldValue: Boolean, newValue: Boolean) {
          if (!newValue)
            aFunc
        }
      }
      aField.focused.delegate.addListener(listener)
    }
    
  }
  
  
  implicit class ComboBoxOps[R](val aCombo: ComboBox[R]) extends AnyVal {
    
    def addOnChange(aFunc: (jfxbv.ObservableValue[_ <: R], R, R) => Unit) {
      val listener = new jfxbv.ChangeListener[R] {
        def changed(observable: jfxbv.ObservableValue[_ <: R], oldValue: R, newValue: R) {
          aFunc(observable, oldValue, newValue)
        }
      }
      aCombo.value.delegate.addListener(listener)
    }
    
  }
  
}