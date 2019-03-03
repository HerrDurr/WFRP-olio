package dataElements

//import io.getquill.context.jdbc.JdbcContext
import Rows._

object CachableObjects {
  
  trait TCachableRowCompanion[A <: TAbstractRow] extends TRowCompanion[A] {
    
    def loadRows: List[A]
    
    def cache : TCachingStorage[A]
    
    def getCachedRow(forRow : A): TStorageRow[A] = {
      cache.getRows.find{ cRow: TStorageRow[A] => forRow.filterFunc(cRow.data) }
                   .getOrElse( TStorageRow.createNewRow(forRow, cache) )
    }
    
  }
  
  trait TCachableRowObject/*(implicit ctx : JdbcContext[_, _] with SQLiteQuerier)*/ {
    
    //[A <: TCachableRowObject](aRow : A): Boolean
    
  /* extends TRowTrait {
    
    import ctx._
    
    def saveToDB(
      implicit
      schema : SchemaMeta[TCachableRowObject]
    ): Unit = {
      ctx.testInsOrUpd(this, (q: Query[TCachableRowObject]) => q.filterByKeys(this.filterString))
    }
    
    def deleteFromDB(
      implicit
      schema : SchemaMeta[TCachableRowObject]
    ): Unit = {
      ctx.deleteRowGeneric(this, (q: Query[TCachableRowObject]) => q.filterByKeys(this.filterString))
    }*/
    
  }
  
  trait TCachableRowCompanionWithId[A <: TCachableRowObjectWithId] extends TCachableRowCompanion[A] with TCommonRowCompanionWithId[A] {
    
    def byId(id : Integer): Option[TCachableRowObjectWithId] = this.byId(id, this.cache).map( _.asInstanceOf[TCachableRowObjectWithId] ) //this.cache.getRows.find(_.data.rowId == id).map( _.data.asInstanceOf[TCachableRowObjectWithId] )
    
  }
  
  abstract class TCachableRowObjectWithId(rowId: Int) extends TCommonRowWithId(rowId) with TCachableRowObject {
    
    /*def filterFunc(aRow: TCachableRowObjectWithId): Boolean = {
      if (aRow.isInstanceOf[TCachableRowObjectWithId])
        aRow.asInstanceOf[TCachableRowObjectWithId].rowId == (this.rowId)
      else
        false
    }
    * 
    */
    
  }
  
  trait TCachableRowCompanionWithTag[A <: TCachableRowObjectWithTag] extends TCachableRowCompanion[A] with TCommonRowCompanionWithTag[A] {
    
    def byTag(tag : String): Option[TCachableRowObjectWithTag] = this.byTag(tag, this.cache).map( _.asInstanceOf[TCachableRowObjectWithTag] ) //this.cache.getRows.find(_.data.rowTag == tag).map( _.data.asInstanceOf[TCachableRowObjectWithTag] )
    
  }
  
  abstract class TCachableRowObjectWithTag(rowTag: String) extends TCommonRowWithTag(rowTag) with TCachableRowObject {
    
    /*def filterFunc(aRow: TCachableRowObject): Boolean = {
      if (aRow.isInstanceOf[TCachableRowObjectWithTag])
        aRow.asInstanceOf[TCachableRowObjectWithTag].rowTag == (this.rowTag)
      else
        false
    }
    * 
    */
    
  }
  
  
  
}