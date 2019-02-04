package dataElements

import shapeless.HList

abstract class TAbstractRow(aPKeys : HList) extends TRowTrait {
  
  def primaryKeys: HList = this.aPKeys
  
  
  
}