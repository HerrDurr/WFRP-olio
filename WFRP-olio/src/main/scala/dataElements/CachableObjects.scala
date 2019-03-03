package dataElements

import io.getquill.context.jdbc.JdbcContext

object CachableObjects {
  
  trait TCachableRowCompanion[A <: TCachableRowObject] {
    
    def loadRows: List[A]
    
    def cache : TCachingStorage[A]
    
    def getCachedRow(forRow : A): TCachedRow[A] = {
      cache.getRows.find{ cRow: TCachedRow[A] => forRow.filterFunc(cRow.data) }
                   .getOrElse( cache.addAsNew(forRow) )
    }
    
  }
  
  abstract class TCachableRowObject/*(implicit ctx : JdbcContext[_, _] with SQLiteQuerier)*/ extends TAbstractRow {
    
    def filterFunc(aRow: TCachableRowObject): Boolean//[A <: TCachableRowObject](aRow : A): Boolean
    
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
  
  trait TCachableRowCompanionWithId[A <: TCachableRowObjectWithId] extends TCachableRowCompanion[A] {
    
    def byId(id : Integer): Option[TCachableRowObjectWithId] = this.cache.getRows.find(_.data.rowId == id).map( _.data.asInstanceOf[TCachableRowObjectWithId] )
    
  }
  
  abstract class TCachableRowObjectWithId(val rowId: Int) extends TCachableRowObject {
    
    def filterFunc(aRow: TCachableRowObject): Boolean = {
      if (aRow.isInstanceOf[TCachableRowObjectWithId])
        aRow.asInstanceOf[TCachableRowObjectWithId].rowId == (this.rowId)
      else
        false
    }
    
  }
  
  trait TCachableRowCompanionWithTag[A <: TCachableRowObjectWithTag] extends TCachableRowCompanion[A] {
    
    def byTag(tag : String): Option[TCachableRowObjectWithTag] = this.cache.getRows.find(_.data.rowTag == tag).map( _.data.asInstanceOf[TCachableRowObjectWithTag] )
    
  }
  
  abstract class TCachableRowObjectWithTag(val rowTag: String) extends TCachableRowObject {
    
    def filterFunc(aRow: TCachableRowObject): Boolean = {
      if (aRow.isInstanceOf[TCachableRowObjectWithTag])
        aRow.asInstanceOf[TCachableRowObjectWithTag].rowTag == (this.rowTag)
      else
        false
    }
    
  }
  
  
  
}