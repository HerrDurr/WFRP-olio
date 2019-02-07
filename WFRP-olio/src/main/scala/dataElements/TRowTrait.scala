package dataElements

//import shapeless.HList

trait TRowTrait {
  
  def saveToDB: Unit
  
  def deleteFromDB: Unit
  
  /**
   * Quill infix kludge.
   */
  def filterString: String
  
  //def primaryKeys: HList
  
}