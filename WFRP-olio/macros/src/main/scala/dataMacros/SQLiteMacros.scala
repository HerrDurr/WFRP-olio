package dataMacros

import scala.reflect.macros.whitebox.{Context => MacroContext}

/*
 * Totally aped from:
 * https://stackoverflow.com/questions/44784310/how-to-write-generic-function-with-scala-quill-io-library/44797199#44797199
 * https://github.com/getquill/quill-example/tree/4723a5e482efb75b04371ad1d6410219b0893364
 */



class SQLiteMacros(val c: MacroContext) {
  
  import c.universe._
  
  /**
   * More popsickle magic. F*ck.
   */
  def insertOrUpdate[T](entity: Tree, filter: Tree)(implicit t: WeakTypeTag[T]): Tree =
    q"""
      import ${c.prefix}._
      val updateQuery = ${c.prefix}.quote {
        ${c.prefix}.query[$t].filter($filter).update(lift($entity))
      }
      val insertQuery = quote {
        query[$t].insert(lift($entity))
      }
      run(${c.prefix}.query[$t].filter($filter)).size match {
          case 1 => run(updateQuery)
          case _ => run(insertQuery)
      }
      ()
    """
      
  def insOrUpdate[T](entity: Tree, filterQuote: Tree)(implicit t: WeakTypeTag[T]): Tree =
    q"""
      import ${c.prefix}._
      val q = ${c.prefix}.quote {
        ${c.prefix}.query[$t]
      } 
      val filter = $filterQuote(q)
      val updateQuery = ${c.prefix}.quote {
        ${c.prefix}.query[$t].filter.update(lift($entity))
      }
      val insertQuery = quote {
        query[$t].insert(lift($entity))
      }
      run(${c.prefix}.query[$t].filter).size match {
          case 1 => run(updateQuery)
          case _ => run(insertQuery)
      }
      ()
    """
  
  def deleteRow[T](entity: Tree, filter: Tree)(implicit t: WeakTypeTag[T]): Tree =
    q"""
      import ${c.prefix}._
      val deleteQuery = ${c.prefix}.quote {
        ${c.prefix}.query[$t].filter($filter).delete
      }
      run(${c.prefix}.query[$t].filter($filter)).size match {
          case 1 => run(deleteQuery)
          case _ => 
      }
      ()
    """
      
  def loadAll[T](implicit t: WeakTypeTag[T]): Tree =
    q"""
      import ${c.prefix}._
      val loadQuery = ${c.prefix}.quote {
        ${c.prefix}.query[$t]
      }
      run(loadQuery)
    """
      
      
}