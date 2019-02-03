package dataUI

import scalafx.scene.control.{TextField, ComboBox, CheckBox, TextArea}
import javafx.beans.{value => jfxbv}
import java.lang.Boolean
import scalafx.beans.value.ObservableValue
import dataElements.DataHelper._

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
  
  implicit class TextAreaOps(val aArea: TextArea) extends AnyVal {
    
    def addFocusLostEvent(aFunc: => Unit) {
      val listener = new jfxbv.ChangeListener[Boolean] {
        def changed(observable: jfxbv.ObservableValue[_ <: Boolean], oldValue: Boolean, newValue: Boolean) {
          if (!newValue)
            aFunc
        }
      }
      aArea.focused.delegate.addListener(listener)
    }
    
  }
  
  implicit class ComboBoxOps[R](val aCombo: ComboBox[R]) extends AnyVal {
    
    def addOnChange(aFunc: (jfxbv.ObservableValue[_ <: R], R, R) => Unit) {
      /*val listener = new jfxbv.ChangeListener[R] {
        def changed(observable: jfxbv.ObservableValue[_ <: R], oldValue: R, newValue: R) {
          aFunc(observable, oldValue, newValue)
        }
      }
      aCombo.value.delegate.addListener(listener)
      * 
      */
      aCombo.value.delegate.addOnChange(aFunc)
    }
    
  }
  
  implicit class CheckBoxOps(val aCheckBox: CheckBox) extends AnyVal {
    
    def addOnCheck(aFunc: Boolean => Unit) {
      val listener = new jfxbv.ChangeListener[Boolean] {
        def changed(observable: jfxbv.ObservableValue[_ <: Boolean], oldValue: Boolean, newValue: Boolean) {
            aFunc(newValue)
        }
      }
      aCheckBox.selected.delegate.addListener(listener)
    }
  }
  
}