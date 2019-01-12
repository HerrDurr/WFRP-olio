package oliofxGUI

import scalafxml.core.macros.sfxml
import scalafx.scene.control.{Accordion, TitledPane}

trait AccordionEditorInterface {
  
}

@sfxml
class AccordionEditorHandler(
    private val accItems: Accordion,
    private val editable: AccordionEditable
  ) extends AccordionEditorInterface {
  
  
  def onNewClick = {
    
  }
  
  def onSave() = {
    
  }
  
}

  
trait AccordionEditable {
  
  def newEditor: TitledPane = {
    ???
  }
  
}