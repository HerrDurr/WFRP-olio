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
    
    val olio = new Olio
    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
    val saveFile = Source.fromFile("data/saves/Seppo.txt")(decoder)
    try {
      SaverLoader.loadOlio(saveFile, olio)
    } finally {
      saveFile.close()
    }
    
    val olioPanel = new OlioPanel(olio)
    
    this.contents = olioPanel
    
    this.title = olio.name
    
    //Saving test
    olio.setName("Timi")
    val saveQuestion = Dialog.showConfirmation(this, "Create test save file?", "Save?", Dialog.Options.YesNo, Dialog.Message.Question, null)
    if (saveQuestion == Dialog.Result.Yes) SaverLoader.saveOlio(olio)
    
  }
  
  
}