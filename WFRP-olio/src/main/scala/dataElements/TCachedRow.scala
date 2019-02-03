package dataElements

import scalafx.beans.property.ObjectProperty
import DataHelper._
import RowStatus._
import javafx.beans.{value => jfxbv}

class TCachedRow[D](aObject : D) {
  
  private val fObjProperty : ObjectProperty[D] = ObjectProperty(aObject)
  private var fStatus : RowStatus = Unchanged
  this.init()
  
  def init(): Unit = {
    this.fObjProperty.delegate.addOnChange(onChangeRow)
  }
  
  def onChangeRow(aObservable: jfxbv.ObservableValue[_ <: D], aOldVal: D, aNewVal: D) = {
    if (this.fStatus != New)
      this.updateStatus(Changed)
  }
  
  def updateStatus(aStatus : RowStatus) = this.fStatus = aStatus
  
  def data: D = this.fObjProperty.value
  
  def apply(): D = this.data
  
  def update(aObject : D) = {
    this.fObjProperty.update(aObject)
  }
  
}
object TCachedRow {
  
  def createNew[D](aObject : D): TCachedRow[D] = {
    val aRow = new TCachedRow(aObject)
    aRow.updateStatus(New)
    aRow
  }
  
}