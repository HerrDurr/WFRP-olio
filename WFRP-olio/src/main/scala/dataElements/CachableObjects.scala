package dataElements

import io.getquill.context.jdbc.JdbcContext

object CachableObjects {
  
  trait TCachableRowCompanion[A <: TCachableRowObject] {
    
    def loadRows: List[A]
    
  }
  
  abstract class TCachableRowObject(implicit ctx : JdbcContext[_, _] with SQLiteQuerier) extends TRowTrait {
    
    import ctx._
    
    def insOrUpdTest(
      implicit
      schema : SchemaMeta[TCachableRowObject]
    ): Unit = {
      ctx.testInsOrUpd(this, (q: Query[TCachableRowObject]) => q.filterByKeys(this.filterString))
    }
    
  }
}