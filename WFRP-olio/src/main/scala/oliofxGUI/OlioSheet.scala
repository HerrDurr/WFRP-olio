package oliofxGUI

import scalafx.scene.layout.BorderPane
import olioMain.Olio
import scalafx.scene.layout.StackPane
import scalafx.collections.ObservableBuffer
import scalafx.scene.control.TableView
import scalafx.scene.control.TableView.sfxTableView2jfx
  
// deprecated test stuff
class OlioSheet(val olio : Olio) extends BorderPane {
  
  def initBasicInfoTable: TableView[String] = 
  {
    val infoData = ObservableBuffer(olio.name, olio.race, olio.career.current)
    new TableView(infoData)
  }
  
  val pnlLeft = new StackPane
  {
    children.add(initBasicInfoTable)
  }
  this.left = pnlLeft
}