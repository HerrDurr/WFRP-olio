package olioGUI

import scala.swing._
import scala.swing.event._
import scala.swing.BorderPanel.Position._
import main._
import olioIO.SaverLoader
import scala.io.Source
import scala.io.Codec
import java.io.File
import java.nio.charset.CodingErrorAction
import javax.swing.JFileChooser

object Sheet extends SimpleSwingApplication {
  
  
  def top = new MainFrame {
    
    val path = this.getClass.getProtectionDomain.getCodeSource.getLocation.getPath
    
    this.visible = true
    this.centerOnScreen()
    
    val newOrLoad = Dialog.showOptions(this, "Create or load character?", "Welcome!", Dialog.Options.YesNoCancel, Dialog.Message.Question, null, Seq("New", "Load", "Cancel"), 1)
    
    private var olio: Olio = {
      val character = new Olio
      if (newOrLoad == Dialog.Result.Yes)
      {
        newOlioSetup(character)
      }
      else if (newOrLoad == Dialog.Result.No) 
      {
        val load = loadOlioSetup(character)
        if (!load) quit()
      } else quit()
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
    
    private var olioPanel = new OlioPanel(olio)
    
    this.contents = olioPanel
    
    this.title = "Oliosheet"
    
    this.menuBar = new MenuBar {
      
      contents += new Menu("File") {
        
        contents += new MenuItem(new Action("New (Not working)") {
          def apply {
            val really = Dialog.showConfirmation(menuBar,
                "Create new character? Any unsaved changes to the current character will be lost.",
                "New...", Dialog.Options.OkCancel, Dialog.Message.Question)
            if (really == Dialog.Result.Ok)
            {
              
            }
          }
        })
        
        contents += new MenuItem(new Action("Load") {
          def apply {
            val really = Dialog.showConfirmation(menuBar,
                "Load another character? Any unsaved changes to the current character will be lost.",
                "Open...", Dialog.Options.OkCancel, Dialog.Message.Question)
            if (really == Dialog.Result.Ok)
            {
              loadOlioSetup(olio)
              olioPanel.update()
            }
          }
        })
        
        contents += new MenuItem(new Action("Save As...") {
          def apply {
            val fileChooser = new JFileChooser(path + "/data/saves")
            val fileName = Dialog.showInput(menuBar, "Enter a name for your save file", initial = olio.name)
                                 .getOrElse(olio.name) + ".txt"
            fileChooser.setSelectedFile(new File(fileName))
            val save = fileChooser.showSaveDialog(menuBar.peer)
            if (save == JFileChooser.APPROVE_OPTION) olioIO.SaverLoader.saveOlio(olio, fileChooser.getSelectedFile)
          }
        })
        
        contents += new MenuItem(new Action("Exit") {
          def apply {
            quit()
          }
        })
      }
      
    }
    
    
    def newOlioSetup(olio: Olio) = {
      val name = Dialog.showInput(this, "Enter a name for your character/NPC", "Name", initial = "")
      val race = Dialog.showInput(this, "Enter your character's/NPC's race (or species)", "Race", initial = "Human")
      val career = Dialog.showInput(this, "Enter your character's/NPC's career", "Career", initial = "")
      
      olio.setName(name.getOrElse("Seppo"))
      olio.setRace(race.getOrElse("Human"))
      olio.career.change(career.getOrElse("Dung Shoveler"))
    }
    
    def loadOlioSetup(olio: Olio): Boolean = {
      val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
      //val fileChooser = new FileChooser
      val fileChooser = new JFileChooser(path + "/data/saves")
      fileChooser.showOpenDialog(this.peer)
      if (/*fileChooser.selectedFile == null*/ fileChooser.getSelectedFile != null) {
        val file = Source.fromFile(/*fileChooser.selectedFile*/fileChooser.getSelectedFile)(decoder)
        try {
          SaverLoader.loadOlio(file, olio)
        } finally {
          file.close()
        }
        true
      } else false
    }
    
    /*
    //Saving test
    olio.setName("Timi")
    val saveQuestion = Dialog.showConfirmation(this, "Create test save file?", "Save?", Dialog.Options.YesNo, Dialog.Message.Question, null)
    if (saveQuestion == Dialog.Result.Yes) SaverLoader.saveOlio(olio)
    */
    
  }
  
  
}