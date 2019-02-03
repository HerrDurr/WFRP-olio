package dataElements

import io.getquill.context.jdbc.JdbcContext
import scala.language.experimental.macros
import dataMacros.SQLiteMacros

trait SQLiteQuerier {
   
  this: JdbcContext[_, _] =>
  def insertOrUpdate[T](entity: T, filter: (T) => Boolean): Unit = macro SQLiteMacros.insertOrUpdate[T]

}