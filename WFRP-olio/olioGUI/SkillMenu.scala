package olioGUI

import scala.swing._
import main.Olio
/*import scala.io.Source
import scala.io.Codec
import java.nio.charset.CodingErrorAction
import olioIO.DataIO
* 
*/

class SkillMenu(olio: Olio, olioPanel: OlioPanel) extends BoxPanel(Orientation.Horizontal) {
  
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
  val skillN = olio.allSkills.length
  val lPanel = new BoxPanel(Orientation.Vertical)
  val cPanel = new BoxPanel(Orientation.Vertical)
  val rPanel = new BoxPanel(Orientation.Vertical)
  
  val lSkills = olio.allSkills.take(skillN / 3 + 1)
  val cSkills = olio.allSkills.drop(skillN / 3 + 1).take(skillN / 3 + 1)
  val rSkills = olio.allSkills.drop(skillN / 3 * 2 + 2)
  
  lSkills.foreach( s => lPanel.contents += new SkillSelector(olio, s) )
  cSkills.foreach( s => cPanel.contents += new SkillSelector(olio, s) )
  rSkills.foreach( s => rPanel.contents += new SkillSelector(olio, s) )
  
  this.contents += (lPanel, cPanel, rPanel)
  
  //olio.allSkills.foreach( s => contents += new SkillSelector(olio, s) )
  
}