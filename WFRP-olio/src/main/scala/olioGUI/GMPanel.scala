package olioGUI

import scala.swing._
import scala.swing.BorderPanel.Position._
import scala.swing.event._
import scala.io.{Source, Codec}
import olioMain._
import olioIO._
import java.io.File
import java.awt.Font
import javax.swing.JFileChooser
import olioIO.SaverLoader
import java.nio.charset.CodingErrorAction

class GMPanel extends BorderPanel {
  
  // IO path
  val path = this.getClass.getClassLoader.getResource("").getPath
    
  val whFont = Font.createFont(Font.TRUETYPE_FONT, new File(path + "/data/CaslonAntique.ttf"))
  val whFontBold = Font.createFont(Font.TRUETYPE_FONT, new File(path + "/data/CaslonAntique-Bold.ttf"))
  // Making the Group Panel on the left for character selection
  val groupPanelUpper = new BorderPanel
  
  val newButton = new Button("New")
  newButton.tooltip = "Create new character/NPC"
  val loadButton = new Button("Load")
  loadButton.tooltip = "Load a character/NPC"
  // Button for choosing whether to view Player group or Enemy Group
  val playerEnemyButton = new Button("Foes")
  playerEnemyButton.tooltip = "Switch between Player group/Enemy Group"
  // Holder for New and Load buttons
  val newLoadButtonPanel = new GridPanel(1,2)
  
  val groupPanel = new GroupPanel
  
  // Organize Group Panel
  newLoadButtonPanel.contents += (newButton, loadButton)
  groupPanelUpper.layout(playerEnemyButton) = North
  groupPanelUpper.layout(newLoadButtonPanel) = South
  groupPanelUpper.layout(groupPanel) = Center
  
  
  // Add stuff to GMPanel
  this.layout(groupPanelUpper) = West
  
  
  // Event handling
  this.listenTo(newButton, loadButton)
    
    this.reactions += {
      case clickEvent: ButtonClicked => {
        
        /*
        if (clickEvent.source == exitButton)
        {
          val quitChoice = Dialog.showConfirmation(this,
              "Are you sure you want to quit?\nAny unsaved changes to open character sheets will be lost.",
              "Quit?", Dialog.Options.OkCancel, Dialog.Message.Warning)
          if (quitChoice == Dialog.Result.Ok) quit()
        }
        * 
        */
        val olio = new Olio
        val olioPanel = new OlioPanel(olio, whFont, whFontBold)
        if (clickEvent.source == newButton) newOlioSetup(olioPanel)
        else if (clickEvent.source == loadButton) loadOlioSetup(olio)
        olioPanel.update()
          
      }
    }
  
  
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
  
}