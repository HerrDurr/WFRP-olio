package dataElements

object Rows {
  
  /*
   * Not sure if I want to use these
  trait TQueriableById[A] {
    def queryById(id : Int) : A
  }
  
  trait TQueriableByTag[A] {
    def queryByTag(tag: String) : A
  }
  * 
  */
  
  trait TRowCompanion[A <: TAbstractRow] {
    def loadRows: List[A]
  }
  
  trait TCommonRowCompanionWithId[A <: TCommonRowWithId] extends TRowCompanion[A] /*with TQueriableById[A]*/ {
    
    private var newIndex = 0
    
    def byId(id : Integer, aStorage : TStorage[A]): Option[TCommonRowWithId] = {
      aStorage.getRows.find(_.data.rowId == id).map( _.data.asInstanceOf[TCommonRowWithId] )
    }
    
    def getNewIndex = {
      newIndex -= 1
      newIndex
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
  
  trait TCommonRowCompanionWithTag[A <: TCommonRowWithTag] extends TRowCompanion[A] /*with TQueriableByTag[A]*/ {
    
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