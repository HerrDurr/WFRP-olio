package oliofxGUI

import scalafxml.core.macros.sfxml
import scalafx.scene.control.CheckBox
import scalafx.scene.control.TextArea
import scalafx.scene.control.TextField
import olioIO.SchemaWFRP.Talent

trait EditTalentAccordionInterface {
  
  def loadTalent(aTalent: Talent)
  
}

@sfxml
class EditTalentAccordion(
    private val cbGroup : CheckBox,
    private val taDescription: TextArea,
    private val editGroup: TextField,
    private val editName: TextField
  ) extends EditTalentAccordionInterface {
  
  
  def loadTalent(aTalent: Talent) = ???
  
}