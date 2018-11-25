package oliofxGUI

import scalafx.scene.layout.BorderPane
import olioIO.SchemaWFRP._
import scalafx.scene.control.{TableView, TableColumn}
import scalafx.collections.ObservableBuffer
import scalafx.beans.property._
import olioIO.DataHelperWFRP._
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.scene.input.MouseEvent
import scalafx.Includes._
import shapeless.Generic

class EditItem(val items: ObservableBuffer[Item]) extends BorderPane {
  
  /*
  val colName = new TableColumn[Item, Item.Name]("Name")
  colName.cellFactory = { _ => {
      val cell = new TextFieldTableCell[Item, Item.Name]
      cell.editable = true
      cell
    }
  }
  colName.cellValueFactory = cdf => ObjectProperty(cdf.value.name)
  * 
  */
  val genItem = Generic[Item]
  
  
  val colName = new TableColumn[Item, String]("Name")
  colName.cellFactory = { p => {
      val cell = new TextFieldTableCell[Item, String]
      cell.editable = true
      cell.onMouseClicked = { me: MouseEvent => cell.startEdit() }
      cell
    }
  }
  colName.cellValueFactory = cdf => StringProperty(cdf.value.name.value)
  
  val colCraftsmanship = new TableColumn[Item, Craftsmanship.Craftsmanship]("Craftsmanship")
  colCraftsmanship.cellValueFactory = cdf => ObjectProperty(cdf.value.craftsmanship)
  
  val colEncumb = new TableColumn[Item, Item.Encumbrance]("Encumbrance")
  colEncumb.cellValueFactory = cdf => ObjectProperty(cdf.value.encumbrance)
  
  val colCost = new TableColumn[Item, Item.Cost]("Cost")
  colCost.cellValueFactory = cdf => ObjectProperty(cdf.value.cost.getOrElse(Item.Cost("-")))
      
  val colAvailability = new TableColumn[Item, Availability.Name]("Availability")
  colAvailability.cellValueFactory = cdf => ObjectProperty( byId(cdf.value.availability.getOrElse(Availability.avgId)).name )
  
  
  val table = new TableView[Item](items)
  table.columns ++= List(colName, colCraftsmanship, colEncumb, colCost, colAvailability)
  
  this.center = table
}

object EditItem {
  
  def newItem = {
    Item.createNew
  }
  
  def testItems: EditItem = {
    val items = getAllItems
    val buffer = new ObservableBuffer[Item]
    buffer ++= items
    new EditItem(buffer)
  }
  
}