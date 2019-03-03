package dataElements

import scala.collection.mutable.Buffer
import dataElements.{SQLiteQuerier}
import io.getquill.context.jdbc.JdbcContext
//import dataElements.TRowTrait
import Rows._

class TStorage[D <: TAbstractRow](aCompanion : TRowCompanion[D],
    aContext : JdbcContext[_,_] with SQLiteQuerier) {
  
  import aContext._
  
  private val fRows: Buffer[TStorageRow[D]] = Buffer()
  
  
  // save all rows (check what's new, changed, deleted?)
  def saveChanges = {
      this.fRows.foreach { _.save() }
  }
  
  def getRows = this.fRows.toVector

  // discard changes? no need yet, but good to implement maybe 
  
  // load cache (gets everything from db)
  
  // match keys (or smth) to get one row
  
  def deleteRow(aRow : D): Unit = {
    val aRowOpt = this.fRows.find(_.data == aRow)
    if (aRowOpt.isDefined)
      aRowOpt.get.delete
  }
  
  def removeFromStorage(aRow : TStorageRow[D]): Unit = {
    this.fRows -= aRow
  }
  
  def find(aRowItem : D): Option[TStorageRow[D]] = {
    this.fRows.find{ r: TStorageRow[D] => aRowItem.filterFunc(r.data) }
  }
  
  def getStorageRow(aRowItem : D): TStorageRow[D] = {
    this.find(aRowItem).getOrElse( TStorageRow.createNewRow(aRowItem, this) )
  }
  
  def addToStorage(aStorageRow : TStorageRow[D]): Unit = {
    if ( this.find(aStorageRow.data).isEmpty )
      this.fRows += aStorageRow
    else
      throw new Exception(s"Row $aStorageRow already in Storage!")
  }
  
  /*def addAsNew(aRow : D): TStorageRow[D] = {
     val aNewStorageRow = TStorageRow.createNewRow(aRow, this)
     //this.addToStorage( aNewStorageRow )
     aNewStorageRow
  }
  * 
  */
  
}
object TStorage {
  
  
  
}