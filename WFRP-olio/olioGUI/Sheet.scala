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
import java.awt.Font

object Sheet extends SimpleSwingApplication {
  
  def top = new MainFrame {
    
    //val path = this.getClass.getProtectionDomain.getCodeSource.getLocation.getPath
    val path = this.getClass.getClassLoader.getResource("").getPath
    
    val whFont = Font.createFont(Font.TRUETYPE_FONT, new File(path + "/data/CaslonAntique.ttf"))
    val whFontBold = Font.createFont(Font.TRUETYPE_FONT, new File(path + "/data/CaslonAntique-Bold.ttf"))
    
    this.visible = true
    
    val masterPanel = new BoxPanel(Orientation.Horizontal)
    
    val newButton = new Button("New")
    newButton.tooltip = "Create new character/NPC"
    val loadButton = new Button("Load")
    loadButton.tooltip = "Load a character/NPC"
    val exitButton = new Button("Exit")
    
    masterPanel.contents += (newButton, loadButton, exitButton)
    masterPanel.contents.foreach(_.font = whFont.deriveFont(16f))
    this.contents = masterPanel
    this.title = "Oliosheetz"
    
    this.listenTo(newButton, loadButton, exitButton)
    
    this.reactions += {
      case clickEvent: ButtonClicked => {
        
        if (clickEvent.source == exitButton)
        {
          val quitChoice = Dialog.showConfirmation(this,
              "Are you sure you want to quit?\nAny unsaved changes to open character sheets will be lost.",
              "Quit?", Dialog.Options.OkCancel, Dialog.Message.Warning)
          if (quitChoice == Dialog.Result.Ok) quit()
        }
        
        else
        {
          
          val frame = new Frame
          val olio = new Olio
          val olioPanel = new OlioPanel(olio, whFont, whFontBold)
          if (clickEvent.source == newButton) newOlioSetup(olioPanel)
          else if (clickEvent.source == loadButton) loadOlioSetup(olio)
          olioPanel.update()
          frame.title = "Oliosheet"
          frame.contents = olioPanel
          frame.visible = true
          
        }
        
      }
    }
    
    /*
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
    */
    
    /*
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
    * 
    */
    
    class AttributeDialog(olioPanel: OlioPanel) extends Dialog {
      
      val attributes = olioPanel.olio.attributes
      val aPanel = new BorderPanel
      val upper = new BoxPanel(Orientation.Horizontal)
      val lower = new BoxPanel(Orientation.Horizontal)
      for (id <- 0 to 7)
      {
        upper.contents += new AttributeSelectionPanel(attributes, id)
      }
      for (id <- 8 to 15)
      {
        //if (id < 10 || id > 11)
          lower.contents += new AttributeSelectionPanel(attributes, id)
      }
      //val doneButton = new Button("Done")
      aPanel.layout(upper) = North
      aPanel.layout(lower) = Center
      //aPanel.layout(doneButton) = South
      //aPanel.preferredSize = new Dimension(300, 100)
      this.contents = aPanel
      //this.listenTo(doneButton)
      this.title = "Set Attributes"
      this.modal = true
      this.open()
      /*
      this.reactions += {
        case clickEvent: ButtonClicked => {
          olioPanel.update()
          this.peer.dispose()
        }
      }
      * 
      */
    }
    
    def newOlioSetup(olioPanel: OlioPanel) = {
      val olio = olioPanel.olio
      val name = Dialog.showInput(this, "Enter a name for your character/NPC", "Name", initial = "")
      val race = Dialog.showInput(this, "Enter your character's/NPC's race (or species)", "Race", initial = "Human")
      val career = Dialog.showInput(this, "Enter your character's/NPC's career", "Career", initial = "")
      
      new AttributeDialog(olioPanel)
      
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