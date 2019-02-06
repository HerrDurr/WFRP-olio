package dataElements

import scalafx.beans.property.ObjectProperty
import DataHelper._
import RowStatus._
import javafx.beans.{value => jfxbv}
import dataElements.CachableObjects._

class TCachedRow/*[D]*/[A <: TCachableRowObject](aObject : A, aCompanion : TCachableRowCompanion[A]) {
  
  private val fObjProperty : ObjectProperty[TCachableRowObject] = ObjectProperty(aObject)
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
  
  def update(aObject : TCachableRowObject) = {
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
  
  def createNew[A <: TCachableRowObject](aObject : A, aCompanion : TCachableRowCompanion[A]): TCachedRow[A] = {
    val aRow = new TCachedRow(aObject, aCompanion)
    aRow.updateStatus(New)
    aRow
  }
  
}