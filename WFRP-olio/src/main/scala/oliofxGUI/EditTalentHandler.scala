package oliofxGUI

import scalafxml.core.macros.sfxml
import scalafx.scene.control.ComboBox
import scalafx.scene.control.TextArea
import scalafx.scene.control.TextField
import olioIO.SchemaWFRP.Talent
import Talent._
import dataWFRP.Types.TalentExplain
import dataWFRP.Types.TalentExplain.TalentExplainType
import scalafx.beans.property.ObjectProperty
import javafx.beans.{value => jfxbv}
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.stage.Stage

trait EditTalentInterface {
  
  def loadTalent(aTalent: Talent)
  
  def newTalent
  
  def setParentStage(aStage: Stage)
  
}

@sfxml
class EditTalentHandler(
    private val comboSubType : ComboBox[TalentExplainType],
    private val editName: TextField,
    private val editSubTitle: TextField,
    private val taDescription: TextArea
  ) extends EditTalentInterface {
  
  private val fTalent: ObjectProperty[Option[Talent]] = ObjectProperty(this, "talent", None)
  private val fStage: ObjectProperty[Option[Stage]] = ObjectProperty(this, "parent", None)
  
  /*
   * Initialize
   */
  TalentExplain.values.foreach( comboSubType += _ )
  initBindings
  
  private def initBindings() = {
    import dataUI.UtilsUI._
    comboSubType.addOnChange(onChangeSubType)
    editName.addFocusLostEvent(onChangedName)
    editSubTitle.addFocusLostEvent(onChangedSubTitle)
    taDescription.addFocusLostEvent(onChangedDescription)
  }
  
  private def onChangeSubType(
      observable: jfxbv.ObservableValue[_ <: TalentExplainType],
      oldValue: TalentExplainType,
      newValue: TalentExplainType
      ) = {
    this.fTalent.value = Some( lSubType.set(getTalent)(newValue) ) 
    
    editSubTitle.disable = newValue == TalentExplain.-
  }
  
  private def onChangedName = {
    val newVal = editName.text.value
    this.fTalent.value = Some( lName.set(getTalent)(Name(newVal)) )
  }
  
  private def onChangedSubTitle = {
    val newVal = editSubTitle.text.value
    val newOps: Option[SubTitle] = {
      if (newVal == "")
        None
      else
        Some(SubTitle(newVal))
    }
    this.fTalent.value = Some( lSubTitle.set(getTalent)(newOps) )
  }
  
  private def onChangedDescription = {
    val newVal = taDescription.text.value
    this.fTalent.value = Some( lDescription.set(getTalent)( Some(Description( newVal )) ) )
  }
  
  def loadTalent(aTalent: Talent) = {
    this.fTalent.value = Some( aTalent )
    editName.text = aTalent.name.value
    val subTit = {
      if (aTalent.subTitle.isDefined)
        aTalent.subTitle.get.value
      else
        ""
    }
    editSubTitle.text = subTit
    val desc = {
      if (aTalent.description.isDefined)
        aTalent.description.get.value
      else
        ""
    }
    taDescription.text = desc
    comboSubType.getSelectionModel.select( aTalent.subType )
  }
  
  def newTalent = loadTalent(Talent.createNew)
  
  //TODO: finish
  def onSave = {
    // id = -1 => not new
    import olioIO.DataHelperWFRP
    
    if (getTalent.id.value >= 0)
    {
      val old = DataHelperWFRP.talentById(getTalent.id)
      val isUpdate = old.isDefined
      val confirm = new Alert(AlertType.Confirmation) {
        if (fStage.value.isDefined)
          initOwner(fStage.value.get)
          title = "Overwrite Save"
          headerText = "Overwrite Save?"
          contentText = "Are you sure you want to overwrite talent " + old.getOrElse(getTalent).name.value + "?"
      }
    }
  }
  
  def setParentStage(aStage: Stage) = this.fStage.value = Some(aStage)
  
  def getTalent: Talent = {
    if (this.fTalent.value.isEmpty)
      this.fTalent.value = Some( Talent.createNew )
    this.fTalent.value.getOrElse(Talent.createNew)
  }
  
}