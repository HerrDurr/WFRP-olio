package dataElements

import scalafx.beans.property.ObjectProperty
import DataHelper._
import RowStatus._
import javafx.beans.{value => jfxbv}
import dataElements.CachableObjects._

class TStorageRow[A <: TAbstractRow](aObject : A, aStorage : TStorage[A]) extends ObjectProperty[A](aStorage, "", aObject) {
  
  //private val fObjProperty : ObjectProperty[A] = ObjectProperty(aObject)
  private var fStatus : RowStatus = Unchanged
  this.init()
  // add to aCache
  aStorage.addToStorage(this)
  
  private def init(): Unit = {
//    this.fObjProperty.delegate.addOnChange(onChangeRow)
    this.delegate.addOnChange(onChangeRow)
  }
  
  private def onChangeRow(
      aObservable: jfxbv.ObservableValue[_ <: TAbstractRow],
      aOldVal: TAbstractRow,
      aNewVal: TAbstractRow) = {
    println("Status before change: " + this.fStatus)
    if (this.fStatus != New) {
      this.updateStatus(Changed)
      println( "Changed to: " + aNewVal.toString() )
    }
    println("Status after change: " + this.fStatus)
  }
  
  private def updateStatus(aStatus : RowStatus) = this.fStatus = aStatus
  
  /**
   * Requires saving for this to be committed to the db.
   */
  def delete = this.updateStatus(Deleted)
  
  def data: A = this.value //this.fObjProperty.value
  
  //def apply(): ObjectProperty[A] = this.fObjProperty
  
  def updateDebug(aObject : A) = {
    println("Updating "+this.data.toString())
//    this.fObjProperty.update(aObject)
    this.update(aObject)
    println("Updated to "+aObject.toString())
  }
  
  def save(): Unit = {
    this.fStatus match {
      case Deleted => {
        this.data.deleteFromDB
        aStorage.removeFromStorage(this)
      }
      case Unchanged => // nanimo shinai
      case _ => {
        this.data.saveToDB
        this.updateStatus(Unchanged)
      }
    }
  }
  
}
object TStorageRow {
  
  def apply[A <: TAbstractRow](aObject : A, aStorage: TStorage[A]) = new TStorageRow(aObject, aStorage)
  
  def createNewRow[A <: TAbstractRow](aObject : A, aStorage: TStorage[A]): TStorageRow[A] = {
    //aStorage.addAsNew(A)
    val aStorRow = TStorageRow(aObject, aStorage)
    aStorRow.updateStatus(New)
    aStorRow
  }
  
}