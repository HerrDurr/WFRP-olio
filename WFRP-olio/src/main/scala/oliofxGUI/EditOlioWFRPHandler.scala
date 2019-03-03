package oliofxGUI

import scalafxml.core.macros.sfxml
import scalafxml.core.FXMLLoader
import scalafx.scene.control.{TextField, ListView, TableView}
import olioIO.SchemaWFRP.{Olio, Career, AttributeSet, Skill, Talent}
import olioIO.SchemaWFRP.Olio._
import scalafx.beans.property.ObjectProperty

trait EditOlioInterface {
  
  def editOlio(aOlio: Olio)
  
}

@sfxml
class EditOlioWFRPHandler(
    private val edName: TextField,
    private val edRace: TextField,
    private val edCareer: TextField,
    private val lvExCareers: ListView,
    private val tblMainProfile: TableView,
    private val tblSecondaryProfile: TableView
  ) extends EditOlioInterface {
  
  
  private var fCurrentOlio : ObjectProperty[Option[Olio]] = ObjectProperty(this, "Olio", None)
  
  private var fStartingAttributesMain : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Starting Main", None)
  private var fAdvanceAttributesMain : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Advance Main", None)
  private var fTotalAttributesMain : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Total Main", None)
  
  private var fStartingAttributesSecondary : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Starting Secondary", None)
  private var fAdvanceAttributesSecondary : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Advance Secondary", None)
  private var fTotalAttributesSecondary : ObjectProperty[Option[AttributeSet]] = ObjectProperty(this, "Total Secondary", None)
  
  
  
  
  def editOlio(aOlio: Olio) = {
    resetOlio(aOlio)
    resetUI
  }
  
  private def resetOlio(aOlio : Olio) = {
    this.fCurrentOlio.value = aOlio
  }
  
  private def resetUI = {
    ???
  }
  
}