package slickTester

//import slick.codegen.SourceCodeGenerator
//import scalafx.application.JFXApp
//import scalafx.application.JFXApp.PrimaryStage
//import scalafx.scene.Scene
//import scalafx.scene.layout.BorderPane

import slick.driver.SQLiteDriver.api._

object SlickTest {
  
  /*
  val testConfigSrc = 
    """
      | url = "jdbc:sqlite:WFRPOlioTest.db"
      | driver = org.sqlite.JDBC
      | connectionPool = disabled
      | keepAliveConnection = true
      """
  
  val testConfig = new SSConfig(ConfigFactory.parseString(testConfigSrc))
  * 
  */
  
  val path = this.getClass.getClassLoader.getResource("").getPath

  //val db = Database.forConfig(path + "/main/resources/application.config", ConfigFactory.load(), slick.driver.SQLiteDriver, classLoader)
  val db = Database.forConfig("wfrpdbtest")
  
  class TableAttribute(tag: Tag) extends Table[(String, String)](tag, "ATTRIBUTE") {
    def attrId = column[String]("Attribute", O.PrimaryKey)
    def attrName = column[String]("AttributeName")
    def * = (attrId, attrName)
  }
  val tableAttribute = TableQuery[TableAttribute]
  
  val schema = tableAttribute.schema
  
  /*
  class Suppliers(tag: Tag) extends Table[(Int, String, String, String, String, String)](tag, "SUPPLIERS") {
    def id = column[Int]("SUP_ID", O.PrimaryKey) // This is the primary key column
    def name = column[String]("SUP_NAME")
    def street = column[String]("STREET")
    def city = column[String]("CITY")
    def state = column[String]("STATE")
    def zip = column[String]("ZIP")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, name, street, city, state, zip)
  }
  val suppliers = TableQuery[Suppliers]
  * 
  */
  
  
  
  
  
  /*try
  {
  } finally db.close
  * 
  */
  
  //stage = new PrimaryStage
  //{
    // trippy this here
    // fullScreen = true
    //title = "test"
    /*
    SourceCodeGenerator.main(
      Array("slick.driver.SQLiteDriver", "org.sqlite.JDBC", "C:\\Users\\Tuomas\\git\\WFRP-olio\\WFRP-olio\\db\\WFRPOlio.db", "C:\\Users\\Tuomas\\git\\WFRP-olio\\WFRP-olio\\", "slickTester")
    )
    * 
    */
    
      
    /*
      scene = new Scene
      {
        val borderPane = new BorderPane
        {
                  
        }
        content.add(borderPane)
      }
      * 
      */
    
  
  //}
}