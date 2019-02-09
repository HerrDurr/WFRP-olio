package dataElements

import io.getquill.context.jdbc.JdbcContext
import scala.language.experimental.macros
import dataMacros.SQLiteMacros
//import dataMacros.BasicQuerier

trait SQLiteQuerier /*extends BasicQuerier*/ {
   
  this: JdbcContext[_, _] =>
  def insertOrUpdate[T](entity: T, filter: (T) => Boolean): Unit = macro SQLiteMacros.insertOrUpdate[T]
  
  def loadAll[T]: List[T] = macro SQLiteMacros.loadAll[T]
  
  def deleteRow[T](entity: T, filter: (T) => Boolean): Unit = macro SQLiteMacros.deleteRow[T]
  
  /*implicit class FilterByKeys[T](q: Query[T]) {
    def filterByKeys(aFilter : String) = quote(infix"$q WHERE #aFilter".as[Query[T]])
  }*/
  
  // this don't workd
  /*def testInsOrUpd[T](entity: T, filterQuote: (Query[T]) => AnyRef with Quoted[Query[T]]): Unit =
    macro SQLiteMacros.insOrUpdate[T]
    * 
    */
  
  // these don't work
  /*def deleteRowGeneric[T](entity: T, filterFunc: (Query[T]) => AnyRef with Quoted[Query[T]]): Unit =
    macro SQLiteMacros.deleteRowGeneric[T]
  
  def deleteRowGeneric2[T](entity: T, filterStr: String): Unit =
    macro SQLiteMacros.deleteRowGeneric2[T]
    * 
    */
  
  // these don't work either.. sigh
  /*def deleteById[T](id: Int): Unit = macro SQLiteMacros.genericDeleteById[T]
  
  def deleteByTag[T](idTag: String): Unit = macro SQLiteMacros.genericDeleteByTag[T]
  * 
  */
  
}