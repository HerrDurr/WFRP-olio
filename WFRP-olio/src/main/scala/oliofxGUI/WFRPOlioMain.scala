package oliofxGUI
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import olioMain.Olio
import slickTester.SlickTest
import slick.driver.SQLiteDriver.api._
import scalafx.scene.layout.BorderPane.sfxBorderPane2jfx
import oliofxGUI.OlioSheet
import olioIO.DBHandler
import olioIO.SchemaWFRP
import scala.concurrent.ExecutionContext.Implicits.global


object WFRPOlioMain extends JFXApp {

  stage = new PrimaryStage
  {
    // trippy this here
    // fullScreen = true
    title = "Oliosheetz 2.0"
    
    scene = new Scene
    {
      maximized = true
      val borderPane = new BorderPane
      {
        val placeHolderOlio = new Olio
        
        placeHolderOlio.setName("Seppo")
        placeHolderOlio.setRace("Human")
        placeHolderOlio.career.change("Dung Shoveler")
        center = new OlioSheet(placeHolderOlio)        
      }
      content.add(borderPane)

      //SlickTest.testConfig
      //testDB
      testDB2()
    }
    
    
  }

  def testDB2() = {
    val db = DBHandler.GetDB()
    try
    {
      val tables = SchemaWFRP
      
      val createDB = DBIO.seq(tables.schema.create)
      
      val creation = db.run(createDB)
      creation.onComplete(c => println("DB Created!"))
      
      //tables.schema.create.statements.foreach(println)
      Thread.sleep(1000)
      
      println("Attributes")
      println("Query 1:")
      val q1 = for(attr <- tables.tableAttribute)
        yield LiteralColumn("  ") ++ attr.attrId ++ "\t" ++ attr.attrName
      db.stream(q1.result).foreach(println)
      Thread.sleep(1000)
      
      val q2 = for(item <- tables.tableItem)
        yield LiteralColumn("  ") ++ item.itemName ++ "\t" ++ item.itemCost.getOrElse("NA")
      println("Query 2, Items:")  
      db.stream(q2.result).foreach(println)
      Thread.sleep(1000)
      /*
      val insert: DBIO[Long] =
        tables.tableItem returning tables.tableItem.map(_.itemId) += (tables.tableItem.baseTableRow.)
        * 
        */
      
    } finally db.close()
    println("Connection closed")
  }
  
  def testDB() = {
    val schema = SlickTest.schema
    SlickTest.db.run(DBIO.seq(
      schema.create
    ))
    
    SlickTest.db.close()
    
  }
  
  
}