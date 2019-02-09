package dataElements

//import shapeless.HList

trait TRowTrait {
  
  def saveToDB: Unit
  
  def deleteFromDB: Unit
  
  /**
   * Quill infix kludge.
   */
  //def filterString: String
  
  /**
   * Even worse kludge, but if it works...
   * Well, it doesn't.
   *//*
  def keyTypeId: Boolean = true
  
  def keyId: Int = -1
  
  def keyTag: String = ""
  * 
  */
  
  //def primaryKeys: HList
  
}