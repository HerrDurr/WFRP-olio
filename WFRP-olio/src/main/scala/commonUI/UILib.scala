package commonUI

import scalafx.scene.control.ButtonType
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.stage.Stage

object UILib {
  
  /*
   * Alert Button Types
   */
  val ButtonTypeCreateNew = new ButtonType("Create New")
  
  def overwriteDialog(aToOverwrite : String, aParent: Option[Stage]): Alert = {
    new Alert(AlertType.Confirmation) {
      if (aParent.isDefined)
          initOwner(aParent.get)
        title = "Overwrite"
        headerText = "Overwrite data?"
        contentText = "Are you sure you want to overwrite \"" + aToOverwrite + "\"?"
        buttonTypes = Seq(
            ButtonType.Yes, ButtonType.No, ButtonTypeCreateNew)
    }
  }
  
}