package dataMacros

import io.getquill.context.jdbc.JdbcContext

trait BasicQuerier {
  
  this: JdbcContext[_, _] =>
    
  implicit class FilterByKeys[T](q: Query[T]) {
    def filterByKeys(aFilter : String) = quote(infix"$q WHERE #aFilter".as[Query[T]])
  }
  
}