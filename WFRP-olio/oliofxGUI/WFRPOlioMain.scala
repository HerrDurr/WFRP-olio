package oliofxGUI
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.layout.BorderPane
import main.Olio

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
    }
  
  }
  
}