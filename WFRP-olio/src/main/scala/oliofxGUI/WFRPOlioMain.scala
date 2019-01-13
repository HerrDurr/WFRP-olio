package oliofxGUI
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import olioMain.{Olio => oldOlio}
//import slickTester.SlickTest
//import slick.driver.SQLiteDriver.api._
import scalafx.scene.layout.BorderPane.sfxBorderPane2jfx
//import oliofxGUI.OlioSheet
//import olioIO.DBHandler
import olioIO.SchemaWFRP._
import scala.concurrent.ExecutionContext.Implicits.global
import EditItem._
//import olioIO.DataHelperWFRP


object WFRPOlioMain extends JFXApp {
  
  
  stage = new PrimaryStage
  {
    // trippy this here
    // fullScreen = true
    title = "Oliosheetz 2.0"
     
    scene = new Scene
    {
      maximized = true
      val placeHolderOlio = new oldOlio
          
      val borderPane = new BorderPane
      {
        placeHolderOlio.setName("Seppo")
        placeHolderOlio.setRace("Human")
        placeHolderOlio.career.change("Dung Shoveler")
        center = new OlioSheet(placeHolderOlio)        
      }
      //content.add(borderPane)
      //quillTest();
      content.add(testItem2)//testItems)
      // Slick is utter poop
      //SlickTest.testConfig
      //testDB
      //testDB2()
      
      //DataHelperWFRP.copyOldTalentDataToDB(placeHolderOlio)
      //Thread.sleep(5000)
      testEditItem()
      
      
    }
      
    
    
  }

  def quillTest() = {
    import dbContext._
    val q = quote {
      for {
        item <- query[Item]
      } yield {
        (item.name, item.cost)
      }
    }
    
    try
    {
      val qRes = dbContext.run(q)
      qRes.foreach( res => println(res._1 + ", " + res._2) )
      Thread.sleep(1000)
    } finally dbContext.close()
  }
  
  
  /*
  def testDB2() = {
    val db = DBHandler.GetDB()
    try
    {
      //val tables = SchemaWFRP
      
      val createDB = DBIO.seq(schema.create)
      
      val creation = db.run(createDB)
      creation.onComplete(c => println("DB Created!"))
      
      //tables.schema.create.statements.foreach(println)
      Thread.sleep(1000)
      
      //println("Attributes")
      //println("Query 1:")
      /*
      val q1 = for(attr <- tables.tableAttribute)
        yield LiteralColumn("  ") ++ attr.attrId ++ "\t" ++ attr.attrName
      db.stream(q1.result).foreach(println)
      Thread.sleep(1000)
      */
      
      val q2 = for(item <- tableItem)
        yield LiteralColumn("  ") ++ item.itemName.value ++ "\t" ++ item.itemCost.getOrElse(ItemRow.Cost("NA")).value
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
  * 
  */
  
  
}