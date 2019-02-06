package dataElements

object CachableObjects {
  
  trait TCachableRowCompanion[A <: TCachableRowObject] {
    
    def loadRows: List[A]
    
  }
  
  trait TCachableRowObject extends TRowTrait {
    
  }
}