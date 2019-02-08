package dataElements

import io.getquill.context.jdbc.JdbcContext

abstract class TAbstractRow(implicit ctx : JdbcContext[_, _] with SQLiteQuerier) extends TRowTrait {
    
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
    }
  
}