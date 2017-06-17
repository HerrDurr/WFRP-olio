package olioGUI

import scala.swing._
import main.Olio
/*import scala.io.Source
import scala.io.Codec
import java.nio.charset.CodingErrorAction
import olioIO.DataIO
* 
*/

class SkillMenu(olioPanel: OlioPanel) extends BoxPanel(Orientation.Horizontal) {
  
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
  val allSkills = olioPanel.olio.allSkills
  val skillN = allSkills.length
  val lPanel = new BoxPanel(Orientation.Vertical)
  val cPanel = new BoxPanel(Orientation.Vertical)
  val rPanel = new BoxPanel(Orientation.Vertical)
  
  val lSkills = allSkills.take(skillN / 3 + 1)
  val cSkills = allSkills.drop(skillN / 3 + 1).take(skillN / 3 + 1)
  val rSkills = allSkills.drop(skillN / 3 * 2 + 2)
  
  lSkills.foreach( s => lPanel.contents += new SkillSelector(olioPanel, s) )
  cSkills.foreach( s => cPanel.contents += new SkillSelector(olioPanel, s) )
  rSkills.foreach( s => rPanel.contents += new SkillSelector(olioPanel, s) )
  
  this.contents += (lPanel, cPanel, rPanel)
  
  //olio.allSkills.foreach( s => contents += new SkillSelector(olio, s) )
  
}