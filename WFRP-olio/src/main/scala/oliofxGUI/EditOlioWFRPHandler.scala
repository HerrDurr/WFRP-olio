package oliofxGUI

import scalafxml.core.macros.sfxml
import scalafxml.core.FXMLLoader
import scalafx.scene.control.{TextField, ListView, TableView}
import olioIO.SchemaWFRP.{Olio, Career, Race, AttributeSet, Skill, Talent}
import olioIO.SchemaWFRP.Olio._
import scalafx.beans.property.ObjectProperty
import scalafx.scene.control.TableColumn
import dataUI.ControlFactory._

trait EditOlioInterface {
  
  def editOlio(aOlio: Olio)
  
}

@sfxml
class EditOlioWFRPHandler(
    private val edName: TextField,
    private val edRace: TextField,
    private val edCareer: TextField,
    private val lvExCareers: ListView[Career],
    private val tblMainProfile: TableView[AttributeSet],
    private val tblSecondaryProfile: TableView[AttributeSet]
  ) extends EditOlioInterface {
  
  
  private var fCurrentOlio : ObjectProperty[Option[Olio]] = ObjectProperty(this, "Olio", None)
  
  /*private var fStartingAttributesMain : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Starting Main", None)
  private var fAdvanceAttributesMain : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Advance Main", None)
  private var fTotalAttributesMain : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Total Main", None)
  
  private var fStartingAttributesSecondary : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Starting Secondary", None)
  private var fAdvanceAttributesSecondary : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Advance Secondary", None)
  private var fTotalAttributesSecondary : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Total Secondary", None)
  * 
  */
  private var fStartingAttributes : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Starting", None)
  private var fAdvanceAttributes : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Advance", None)
  private var fTotalAttributes : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Total", None)
  
  
  
  def editOlio(aOlio: Olio) = {
    resetOlio(aOlio)
    resetUI
  }
  
  private def resetOlio(aOlio : Olio) = {
    this.fCurrentOlio.value = Some(aOlio)
    //this.fStartingAttributes.value = Some(aOlio.baseAttributes)
  }
  
  private def resetUI = {
    
    this.edName.text = this.fCurrentOlio.value.map(_.name.value).getOrElse("")
    this.edRace.text = Race.byId( this.fCurrentOlio.value.map(_.race)
                                                         .map(_.value) //{ o: Olio => o.race.value }
                                                         .get )
                                                                         .map(_.asInstanceOf[Race].name.value).getOrElse("")
  }
  
  /**
   * Initialize the columns of the Main Profile
   */
  private def initMainProfileTable: Unit = {
    import AttributeSet._
    //val colWS = new TableColumn[AttributeSet, Int] {
    //}
    //val setToWS = {aSet : AttributeSet => aSet.weaponSkill}
    
    /*val colWS: TableColumn[AttributeSet, Short] = tableColumnNew(
        WS(0),
        { aSet : AttributeSet => aSet.weaponSkil }
        { aNewSet : AttributeSet => fItemCurrent = aNewItem },
        lWS,
        { aVal: Short => WS(aVal) }
    )
    
    with tblMainProfile {
      
      
      
    }*/
  }
  
}