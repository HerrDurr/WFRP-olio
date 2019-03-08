package dataElements

import scalafx.beans.property.ObjectProperty
import DataHelper._
import RowStatus._
import javafx.beans.{value => jfxbv}
import dataElements.CachableObjects._

class TCachedRow/*[D]*/[A <: TAbstractRow](aObject : A, aCache : TCachingStorage[A]) extends TStorageRow[A](aObject, aCache) {
  /*
  private val fObjProperty : ObjectProperty[A] = ObjectProperty(aObject)
  private var fStatus : RowStatus = Unchanged
  this.init()
  // add to aCache
  aCache.addToStorage(this)
  
  private def init(): Unit = {
    this.fObjProperty.delegate.addOnChange(onChangeRow)
  }
  
  private def onChangeRow(
      aObservable: jfxbv.ObservableValue[_ <: TCachableRowObject],
      aOldVal: TCachableRowObject,
      aNewVal: TCachableRowObject) = {
    println("Status before change: " + this.fStatus)
    if (this.fStatus != New) {
      this.updateStatus(Changed)
      println( "Changed to: " + aNewVal.toString() )
    }
    println("Status after change: " + this.fStatus)
  }
  
  private def updateStatus(aStatus : RowStatus) = this.fStatus = aStatus
  
  def delete = this.updateStatus(Deleted)
  
  def data: A = this.fObjProperty.value
  
  def apply(): ObjectProperty[A] = this.fObjProperty
  
  def update(aObject : A) = {
    this.fObjProperty.update(aObject)
  }
  
  def save(): Unit = {
    this.fStatus match {
      case Deleted => {
        this.data.deleteFromDB
        aCache.removeFromCache(this)
      }
      case Unchanged => // nanimo shinai
      case _ => {
        this.data.saveToDB
        this.updateStatus(Unchanged)
      }
    }
  }
  */
}
/*
object TCachedRow {
  def apply[A <: TCachableRowObject](aObject : A, aCache: TCachingStorage[A]) = new TCachedRow(aObject, aCache)
  
  def createNewRow[A <: TCachableRowObject](aObject : A, aCache: TCachingStorage[A]): TCachedRow[A] = {
    val aRow = new TCachedRow(aObject, aCache)
    aRow.updateStatus(New)
    aRow
  }
  
}
*/