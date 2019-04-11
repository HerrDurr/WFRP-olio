package oliofxGUI

import scalafxml.core.macros.sfxml
import scalafxml.core.FXMLLoader
import scalafx.scene.control.{TextField, ListView, TableView, TableColumn}
import scalafx.beans.property.ObjectProperty
import scalafx.collections.ObservableBuffer
import javafx.scene.control.{TableColumn => jfxTableColumn}
import olioIO.SchemaWFRP.{Olio, Career, Race, AttributeSet, Skill, Talent}
import olioIO.SchemaWFRP.Olio._
import dataUI.ControlFactory._
import dataElements.TStorage
import dataElements.TStorageRow

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
  
  private var fAttributeStorage: Option[TStorage[AttributeSet]] = None
  private val fAttributeBuffer : ObservableBuffer[AttributeSet] = ObservableBuffer()
  /**
   * A function needed for the TableColumn factory method.
   */
  private val newSetFunc: AttributeSet => Unit = {
    aNewSet : AttributeSet => fAttributeBuffer.replaceAll( (fAttributeBuffer.find( _.id == aNewSet.id ).get), aNewSet )
  }
  
  
  
  def editOlio(aOlio: Olio) = {
    resetOlio(aOlio)
    resetUI
    initMainProfileTable
  }
  
  private def resetOlio(aOlio : Olio) = {
    this.fCurrentOlio.value = Some(aOlio)
    // This inits the AttributeSet rows as well
    this.fAttributeStorage = Some( aOlio.attributeStorage )
    this.fStartingAttributes.value = AttributeSet.byId(aOlio.baseAttributes.value, fAttributeStorage.get)
                                                 .map(_.asInstanceOf[AttributeSet])
    if (fStartingAttributes.value.isEmpty) {
      this.fStartingAttributes.value = Some( AttributeSet.createEmpty )
      TStorageRow.createNewRow(fStartingAttributes.value.get, fAttributeStorage.get)
    }
    this.fAdvanceAttributes.value = AttributeSet.byId(aOlio.advancedAttributes.map(_.value).getOrElse(-1), fAttributeStorage.get)
                                                 .map(_.asInstanceOf[AttributeSet])
    if (fAdvanceAttributes.value.isEmpty) {
      //println("fAdvanceAttributes is Empty, creating new set...")
      this.fAdvanceAttributes.value = Some( AttributeSet.createEmpty )
      TStorageRow.createNewRow(fAdvanceAttributes.value.get, fAttributeStorage.get)
      /*if (fAdvanceAttributes.value.isDefined)
        println("Set creation successful!")
      else
        println("Set creation unsuccessful!")*/
    }
    
    this.fTotalAttributes.value = Some( fStartingAttributes.value.get + fAdvanceAttributes.value.get )
    
    fAttributeBuffer.clear()
    fAttributeBuffer ++= Seq(fStartingAttributes.value.get, fAdvanceAttributes.value.get, fTotalAttributes.value.get)
  }
  
  private def resetUI = {
    
    this.edName.text = this.fCurrentOlio.value.map(_.name.value).getOrElse("")
    this.edRace.text = Race.byId( 
                                  this.fCurrentOlio.value.map(_.race)
                                                         .map(_.value) //{ o: Olio => o.race.value }
                                                         .get
                                )
                                 .map(_.asInstanceOf[Race].name.value).getOrElse("")
  }
  
  /**
   * Initialize the columns of the Main Profile. This is horrible, but making it any more generic
   * and abstract than it already is hurts my head.
   */
  private def initMainProfileTable: Unit = {
    import AttributeSet._
    
    tblMainProfile.items = this.fAttributeBuffer
    
    //val setToWS : AttributeSet => WS = { aSet : AttributeSet => aSet.weaponSkill }
    // @ControlFactory
    val colWS: TableColumn[AttributeSet, Short] = tableColumnNew(
        WS(0),
        { aSet : AttributeSet => aSet.weaponSkill },
        newSetFunc,
        lWS,
        { aVal: Short => WS(aVal) }
    )
    val colBS: TableColumn[AttributeSet, Short] = tableColumnNew(
        BS(0),
        { aSet : AttributeSet => aSet.ballisticSkill },
        newSetFunc,
        lBS,
        { aVal: Short => BS(aVal) }
    )
    val colS: TableColumn[AttributeSet, Short] = tableColumnNew(
        S(0),
        { aSet : AttributeSet => aSet.strength },
        newSetFunc,
        lS,
        { aVal: Short => S(aVal) }
    )
    val colT: TableColumn[AttributeSet, Short] = tableColumnNew(
        T(0),
        { aSet : AttributeSet => aSet.toughness },
        newSetFunc,
        lT,
        { aVal: Short => T(aVal) }
    )
    val colAg: TableColumn[AttributeSet, Short] = tableColumnNew(
        Ag(0),
        { aSet : AttributeSet => aSet.agility },
        newSetFunc,
        lAg,
        { aVal: Short => Ag(aVal) }
    )
    val colInt: TableColumn[AttributeSet, Short] = tableColumnNew(
        Int(0),
        { aSet : AttributeSet => aSet.intelligence },
        newSetFunc,
        lInt,
        { aVal: Short => Int(aVal) }
    )
    val colWP: TableColumn[AttributeSet, Short] = tableColumnNew(
        WP(0),
        { aSet : AttributeSet => aSet.willPower },
        newSetFunc,
        lWP,
        { aVal: Short => WP(aVal) }
    )
    val colFel: TableColumn[AttributeSet, Short] = tableColumnNew(
        Fel(0),
        { aSet : AttributeSet => aSet.fellowship },
        newSetFunc,
        lFel,
        { aVal: Short => Fel(aVal) }
    )
    val mainCols = List(colWS, colBS, colS, colT, colAg, colInt, colWP, colFel)
    
    // We always want the rows in the specific order they're already in.
    mainCols.foreach( _.setSortable(false) )
    tblMainProfile.columns ++= ( mainCols.map(_.delegate) )
    tblMainProfile.editable = true
  }
  
}