package olioGUI

import scala.swing._
import scala.swing.event._
import scala.swing.BorderPanel.Position._
import main._
import olioIO.SaverLoader
import scala.io.Source
import scala.io.Codec
import java.nio.charset.CodingErrorAction

object Sheet extends SimpleSwingApplication {
  
  
  def top = new MainFrame {
    
    this.visible = true
    this.centerOnScreen()
    
    val newOrLoad = Dialog.showOptions(this, "Create or load character?", "Welcome!", Dialog.Options.YesNoCancel, Dialog.Message.Question, null, Seq("New", "Load", "Cancel"), 1)
    
    val olio: Olio = {
      val character = new Olio
      if (newOrLoad == Dialog.Result.Yes)
      {
        
        val name = Dialog.showInput(this, "Enter a name for your character/NPC", "Name", initial = "").getOrElse("Seppo")
        val race = Dialog.showInput(this, "Enter your character's/NPC's race (or species)", "Race", initial = "Human").getOrElse("human")
        val career = Dialog.showInput(this, "Enter your character's/NPC's career", "Career", initial = "").getOrElse("Dung Shoveler")
        
        character.setName(name)
        character.setRace(race)
        character.career.change(career)
      }
      else if (newOrLoad == Dialog.Result.No) 
      {
        val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
        val fileChooser = new FileChooser
        fileChooser.showOpenDialog(this)
        val file = Source.fromFile(fileChooser.selectedFile)(decoder)
        try {
          SaverLoader.loadOlio(file, character)
        } finally {
          file.close()
        }
      }
      character
    }
    
    /*
    val olio = new Olio
    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
    val saveFile = Source.fromFile("data/saves/Seppo.txt")(decoder)
    try {
      SaverLoader.loadOlio(saveFile, olio)
    } finally {
      saveFile.close()
    }
    * 
    */
    
    val olioPanel = new OlioPanel(olio)
    
    this.contents = olioPanel
    
    this.title = olio.name
    
    /*
    //Saving test
    olio.setName("Timi")
    val saveQuestion = Dialog.showConfirmation(this, "Create test save file?", "Save?", Dialog.Options.YesNo, Dialog.Message.Question, null)
    if (saveQuestion == Dialog.Result.Yes) SaverLoader.saveOlio(olio)
    */
    
  }
  
  
}