package dataElements

import io.getquill.context.jdbc.JdbcContext

abstract class TAbstractRow/*(implicit ctx : JdbcContext[_, _] with SQLiteQuerier)*/ extends TRowTrait {
    
    //import ctx._
    
    
    /*def saveToDB(
      implicit
      schema : SchemaMeta[TAbstractRow],
      encoder : Encoder[TAbstractRow]
    ): Unit = {
      ctx.testInsOrUpd(this, (q: Query[TAbstractRow]) => q.filterByKeys(this.filterString))
    }*/
    
   /* def deleteFromDB(
      implicit
      schema : SchemaMeta[TAbstractRow]
    ): Unit = {
      //ctx.deleteRowGeneric(this, (q: Query[TAbstractRow]) => q.filterByKeys(this.filterString))
      //ctx.deleteRowGeneric2(this, this.filterString)
      if (this.keyTypeId)
        ctx.deleteById(this.keyId)
      else
        ctx.deleteByTag(this.keyTag)
    }
    * 
    */
  
}