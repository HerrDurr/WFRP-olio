package olioGUI

import scala.swing._
import main.Olio
import scala.io.Source
import scala.io.Codec
import java.nio.charset.CodingErrorAction
import olioIO.DataIO

class SkillMenu(olio: Olio) extends PopupMenu {
  
  val knownSkills = new ListView {
    olio.skills.foreach( x => contents += new Label(x.name + " (" + x.skillLevel + ")") )
  }
  
  /*
  val skillList = new ListView {
    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
    val reader = Source.fromFile("data/skills.txt")(decoder).reader()
    DataIO.loadNames(reader).foreach(contents += new Label(_))
  }
  * 
  */
  
  contents += knownSkills
  
}