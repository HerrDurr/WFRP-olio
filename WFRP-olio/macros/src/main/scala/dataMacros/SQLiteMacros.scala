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
      val insertQuery = ${c.prefix}.quote {
        ${c.prefix}.query[$t].insert(lift($entity))
      }
      run(${c.prefix}.query[$t].filter($filter)).size match {
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
      
  /*def insOrUpdate[T](entity: Tree, filterQuote: Tree)(implicit t: WeakTypeTag[T]): Tree =
    q"""
      import ${c.prefix}._
      val q = ${c.prefix}.quote {
        ${c.prefix}.query[$t]
      } 
      val filter = $filterQuote(q)
      val updateQuery = ${c.prefix}.quote {
        filter.update(lift($entity))
      }
      val insertQuery = quote {
        query[$t].insert(lift($entity))
      }
      run(filter).size match {
          case 1 => run(updateQuery)
          case _ => run(insertQuery)
      }
      ()
    """
    * 
    */
  /*    
  def genericDeleteById[T](id: Tree)(implicit t: WeakTypeTag[T]): Tree =
    q"""
      import ${c.prefix}._
      val filteredQ = ${c.prefix}.quote {
        for {
          e <- $t if (e.id == ${c.prefix}.lift($id))
        } yield {
          (e)
        }
      }
      run(filteredQ).size match {
          case 1 => run(filteredQ.delete)
          case _ => 
      }
      ()
    """
          
  def genericDeleteByTag[T](idTag: Tree)(implicit t: WeakTypeTag[T]): Tree =
    q"""
      import ${c.prefix}._
      val filteredQ = ${c.prefix}.quote {
        for {
          e <- $t if (e.idTag == ${c.prefix}.lift(idTag))
        } yield {
          (e)
        }
      }
      run(filteredQ).size match {
          case 1 => run(filteredQ.delete)
          case _ => 
      }
      ()
    """
    * 
    */
  
  /*def deleteRowGeneric[T](entity: Tree, filterFunc: Tree)(implicit t: WeakTypeTag[T]): Tree =
    q"""
      import ${c.prefix}._
      val q = ${c.prefix}.quote {
        ${c.prefix}.query[$t]
      }
      val filtered = $filterFunc(q)
      val deleteQuery = ${c.prefix}.quote {
        filtered.delete
      }
      run(filtered).size match {
          case 1 => run(deleteQuery)
          case _ => 
      }
      ()
    """  */  
  /*
  def genericFilter[T](filterStr: Tree)(implicit t: WeakTypeTag[T]): Tree =
    q"""
      import ${c.prefix}._
      (q: Query[$t]) => q.filterByKeys($filterStr)
      """
      * 
      */
   
  /*def deleteRowGeneric2[T](entity: Tree, filterStr: Tree)(implicit t: WeakTypeTag[T]): Tree =
    q"""
      import ${c.prefix}._
      val q = ${c.prefix}.quote {
        ${c.prefix}.query[$t]
      }
      def genFilter(q: Query[$t]) = q.filterByKeys($filterStr)
      val filtered = genFilter(q)
      val deleteQuery = ${c.prefix}.quote {
        filtered.delete
      }
      run(filtered).size match {
          case 1 => run(deleteQuery)
          case _ => 
      }
      ()
    """      
        
  def deleteRowGeneric4[T]()
        
  def deleteRowGeneric3[T](filterStr: Tree)(implicit t: WeakTypeTag[T]): Tree =
    q"""
      import ${c.prefix}._
      val q = ${c.prefix}.quote {
        for {
          e <- $t if (${c.prefix}.infix"e.$$filterStr")
        }
        ${c.prefix}.query[$t]
      }
      def genFilter(q: Query[$t]) = q.filterByKeys($filterStr)
      val filtered = genFilter(q)
      val deleteQuery = ${c.prefix}.quote {
        filtered.delete
      }
      run(filtered).size match {
          case 1 => run(deleteQuery)
          case _ => 
      }
      ()
    """ */ 
      
      
}