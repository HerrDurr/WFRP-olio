package olioGUI

import scala.swing._
import olioMain._
import scala.swing.event._


class CentrePanel(olioPanel: OlioPanel) extends BoxPanel(Orientation.Vertical) {
  
  
  
  val attributePanel = new AttributePanel(olioPanel)
  val rightPanel = new RightPanel(olioPanel)
  val leftPanel = new LeftPanel(olioPanel)
  val commentScroller = new ScrollPane
  val commentPane = new EditorPane { font = olioPanel.whFontBold.deriveFont(16f) }
  commentScroller.contents = commentPane
  commentScroller.horizontalScrollBarPolicy = ScrollPane.BarPolicy.Never
  commentPane.preferredSize = new Dimension(400, 84)
  
  this.contents += (attributePanel, new BoxPanel(Orientation.Horizontal) { contents += (leftPanel, rightPanel) },
                    new Separator(Orientation.Horizontal), commentScroller)
  
  def update() = {
    this.attributePanel.update()
    this.rightPanel.update()
    this.leftPanel.hitPanel.update()
    this.commentPane.text = olioPanel.olio.comments
  }
  
  this.listenTo(commentPane.keys)
  
  reactions += {
    case keyEvent: KeyReleased => {
      olioPanel.olio.comments = commentPane.text
    }
  }
  
}