package dataElements

object Rows {
  
  trait TRowCompanion[A <: TAbstractRow] {
    def loadRows: List[A]
  }
  
  trait TCommonRowCompanionWithId[A <: TCommonRowWithId] extends TRowCompanion[A] {
    
    def byId(id : Integer, aStorage : TStorage[A]): Option[TCommonRowWithId] = {
      aStorage.getRows.find(_.data.rowId == id).map( _.data.asInstanceOf[TCommonRowWithId] )
    }
    
  }
  
  abstract class TCommonRowWithId(val rowId : Int) extends TAbstractRow {
    
    def filterFunc(aRow: TAbstractRow): Boolean = {
      if (aRow.isInstanceOf[TCommonRowWithId])
        aRow.asInstanceOf[TCommonRowWithId].rowId == (this.rowId)
      else
        false
    }
    
  }
  
  trait TCommonRowCompanionWithTag[A <: TCommonRowWithTag] extends TRowCompanion[A] {
    
    def byTag(tag : String, aStorage : TStorage[A]): Option[TCommonRowWithTag] = {
      aStorage.getRows.find(_.data.rowTag == tag).map( _.data.asInstanceOf[TCommonRowWithTag] )
    }
    
  }
  
  abstract class TCommonRowWithTag(val rowTag : String) extends TAbstractRow {
    
    def filterFunc(aRow: TAbstractRow): Boolean = {
      if (aRow.isInstanceOf[TCommonRowWithTag])
        aRow.asInstanceOf[TCommonRowWithTag].rowTag == (this.rowTag)
      else
        false
    }
    
  }
  
}