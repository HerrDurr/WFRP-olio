package olioGUI

import scala.swing._
import olioMain.Olio
import javax.swing.border.Border
import olioGUI.SkillSelector
import olioGUI.OlioPanel

/*import scala.io.Source
import scala.io.Codec
import java.nio.charset.CodingErrorAction
import olioIO.DataIO
* 
*/

class SkillMenu(olioPanel: OlioPanel) extends BoxPanel(Orientation.Horizontal) {
  
  
  /*
  val skillList = new ListView {
    val decoder = Codec.UTF8.decoder.onMalformedInput(CodingErrorAction.IGNORE)
    val reader = Source.fromFile("data/skills.txt")(decoder).reader()
    DataIO.loadNames(reader).foreach(contents += new Label(_))
  }
  * 
  */
  
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
  
  this.contents += (lPanel, new Separator(Orientation.Vertical), cPanel, new Separator(Orientation.Vertical), rPanel)
  
  def update() = {
    lPanel.contents.foreach(_.asInstanceOf[SkillSelector].update())
    cPanel.contents.foreach(_.asInstanceOf[SkillSelector].update())
    rPanel.contents.foreach(_.asInstanceOf[SkillSelector].update())
  }
  
}