package olioIO

import slick.driver.SQLiteDriver.api._

object DBHandler {
  
  private var fConfig = "wfrpdb"
  private var fDatabase: Option[Database] = None 
  
  def SetConf(aConfig : String) = fConfig = aConfig
  
  def GetDB(): Database =
  {
    if(fDatabase.isEmpty)
    {
      fDatabase = Some(Database.forConfig(fConfig))
    }
    
    fDatabase.getOrElse(throw new Exception("Failed to load Database!"))
  }
  
}