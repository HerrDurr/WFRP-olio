package olioGUI

import scala.swing._
import main.Olio
/*import scala.io.Source
import scala.io.Codec
import java.nio.charset.CodingErrorAction
import olioIO.DataIO
* 
*/

class SkillMenu(olio: Olio) extends BoxPanel(Orientation.Vertical) {
  
  //val knownSkills = new ListView[Component]
  //olio.skills.foreach( x => contents += new Label(x.name + " (" + x.skillLevel + ")") )
  //knownSkills.listData = olio.skills.map( s => new SkillSelector(olio, s) )
  
  
  /*
  val skillList = new ListView {
    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
    val reader = Source.fromFile("data/skills.txt")(decoder).reader()
    DataIO.loadNames(reader).foreach(contents += new Label(_))
  }
  * 
  */
  
  //contents += knownSkills
  
  olio.skills.foreach( s => contents += new SkillSelector(olio, s) )
  
}