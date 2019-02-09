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
}