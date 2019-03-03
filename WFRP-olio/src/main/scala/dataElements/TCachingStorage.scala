package dataElements

import scala.collection.mutable.Buffer
import dataElements.{TCachedRow, SQLiteQuerier}
import TCachedRow._
import io.getquill.context.jdbc.JdbcContext
//import dataElements.TRowTrait
import dataElements.CachableObjects._

class TCachingStorage[D <: TCachableRowObject](aCompanion : TCachableRowCompanion[D],
    aContext : JdbcContext[_,_] with SQLiteQuerier) {
  
  import aContext._
  
  private val fRows: Buffer[TCachedRow[D]] = Buffer()
  
  
  init()
  
  
  private def init(): Unit = {
    this.fRows ++= aCompanion.loadRows.map(TCachedRow( _, this )) 
  }
  
  /*def loadRows(
      implicit
      aSchema: SchemaMeta[D]    
    ): Unit = {
    val q = quote {
      for {
        obj <- query[D]
      } yield {
        obj
      }
    }
    
    val aList = aContext.run(q)
  }*/
  
  /*def saveRow(aRow : TCachedRow)(
      implicit
      aSchema : SchemaMeta[TRowTrait],
      aEncoder : Encoder[TRowTrait],
      aFilterByKeys : (TRowTrait) => Boolean//D => Boolean
    ) = {
    aContext.insertOrUpdate(aRow.data, aFilterByKeys)
  }*/
  
  // save all rows (check what's new, changed, deleted?)
  def saveChanges = {/*(
      implicit
      aSchema : SchemaMeta[D],
      aEncoder : Encoder[D],
      aFilterByKeys : D => Boolean
    ) = {
    this.fRows.foreach( saveRow(_) )*/
      this.fRows.foreach { _.save() }
      //this.fRows.foreach( this.saveRow(_) )
  }
  
  def getRows = this.fRows.toVector

  // discard changes? no need yet, but good to implement maybe 
  
  // need "CachedRow" or similar, with information of changes
  
  // load cache (gets everything from db)
  
  // match keys (or smth) to get one row
  
  def deleteRow(aRow : D): Unit = {
    val aRowOpt = this.fRows.find(_.data == aRow)
    if (aRowOpt.isDefined)
      aRowOpt.get.delete
  }
  
  def removeFromCache(aRow : TCachedRow[D]): Unit = {
    this.fRows -= aRow
  }
  
  def find(aRowItem : D): Option[TCachedRow[D]] = {
    this.fRows.find{ r: TCachedRow[D] => aRowItem.asInstanceOf[TCachableRowObject].filterFunc(r.data) }
  }
  
  def getCachedRow(aRowItem : D): TCachedRow[D] = {
    this.find(aRowItem).getOrElse(addAsNew(aRowItem))
  }
  
  def addAsNew(aRow : D): TCachedRow[D] = {
     val newCachedRow = TCachedRow.createNewRow(aRow, this)
     this.fRows += newCachedRow
     newCachedRow
  }
  
}
object TCachingStorage {
  
  
  
}