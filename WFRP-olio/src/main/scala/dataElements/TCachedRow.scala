package dataElements

import scalafx.beans.property.ObjectProperty
import DataHelper._
import RowStatus._
import javafx.beans.{value => jfxbv}

class TCachedRow/*[D]*/(aObject : TRowTrait) {
  
  private val fObjProperty : ObjectProperty[TRowTrait] = ObjectProperty(aObject)
  private var fStatus : RowStatus = Unchanged
  this.init()
  
  def init(): Unit = {
    this.fObjProperty.delegate.addOnChange(onChangeRow)
  }
  
  def onChangeRow(
      aObservable: jfxbv.ObservableValue[_ <: TRowTrait],
      aOldVal: TRowTrait,
      aNewVal: TRowTrait) = {
    if (this.fStatus != New)
      this.updateStatus(Changed)
  }
  
  def updateStatus(aStatus : RowStatus) = this.fStatus = aStatus
  
  def data: TRowTrait = this.fObjProperty.value
  
  def apply(): TRowTrait = this.data
  
  def update(aObject : TRowTrait) = {
    this.fObjProperty.update(aObject)
  }
  
  def save(): Unit = {
    this.fStatus match {
      case Deleted => this.data.deleteFromDB
      case Unchanged => // nanimo shinai
      case _ => this.data.saveToDB
    }
  }
  
}
object TCachedRow {
  
  def createNew(aObject : TRowTrait): TCachedRow = {
    val aRow = new TCachedRow(aObject)
    aRow.updateStatus(New)
    aRow
  }
  
}