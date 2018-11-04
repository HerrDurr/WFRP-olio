package olioGUI

import scala.swing._
import olioMain.Attributes
import scala.swing.event.SelectionChanged

class AttributeSelectionPanel(attributes: Attributes, id: Int) extends BoxPanel(Orientation.Vertical) {
  
  val label = new Label(attributes.getAttribute(id))
  val selector = new ComboBox(0 to 100)
  
  this.contents += (label, selector)
  
  this.listenTo(selector.selection)
  
  this.reactions += {
    
    case SelectionChanged(selector) =>
      attributes.setAttribute(id, this.selector.selection.item)
    
  }
}