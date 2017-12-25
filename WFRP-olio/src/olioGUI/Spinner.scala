package olioGUI

import scala.swing._
import javax.swing.SpinnerModel
import javax.swing.JSpinner
import javax.swing.event.ChangeEvent
import javax.swing.event.ChangeListener
import scala.swing.event.ValueChanged

// From SwingPlus, Copyright (c) 2013-2014 Hanns Holger Rutz. All rights reserved.

class Spinner(model: SpinnerModel) extends Component /*with SequentialContainer.Wrapper*/ {
  
  me =>
  
  override lazy val peer: JSpinner = new JSpinner(model) with SuperMixin {
  
    // From SwingPlus, Copyright (c) 2013-2014 Hanns Holger Rutz. All rights reserved.
    override def getBaseline(width: Int, height: Int): Int = {
      val res = super.getBaseline(width, height)
      if (res >= 0) res else {
        getEditor.getBaseline(width, height)
      }
    }
  }
  
  // From SwingPlus, Copyright (c) 2013-2014 Hanns Holger Rutz. All rights reserved.
  def value: Any = this.peer.getValue
  // From SwingPlus, Copyright (c) 2013-2014 Hanns Holger Rutz. All rights reserved.
  def value_=(v: Any) = this.peer.setValue(v.asInstanceOf[AnyRef]) 
  
  peer.addChangeListener(new ChangeListener {
    def stateChanged(e: ChangeEvent): Unit = publish(new ValueChanged(me))
  })
  
}