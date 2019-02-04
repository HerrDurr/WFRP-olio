package dataElements

import shapeless.HList

trait TRowTrait {
  
  def saveToDB: Unit
  
  def deleteFromDB: Unit
  
  def primaryKeys: HList
  
}