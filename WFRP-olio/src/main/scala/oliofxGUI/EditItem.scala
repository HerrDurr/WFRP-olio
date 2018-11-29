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
import shapeless._
import dataElements.DataHelper._

class EditItem[Repr <: HList](val aItem : Repr) /*val items: ObservableBuffer[Item])*/(implicit gen: LabelledGeneric.Aux[Item, Repr]) extends BorderPane {
  
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
  /*
  val genItem = Generic[Item]
  
  //val lbldItems = items.map(LabelledGeneric[Item].to(_)).map(getWrappedValue(_))
  
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
  * 
  */
  val editableItem = aItem.map(unwrap(_)).map(MapToProperty)
  
  val table = new TableView[Item](items)
  table.columns ++= List(colName, colCraftsmanship, colEncumb, colCost, colAvailability)
  
  this.center = table
}

object EditItem {
  
  def newItem = {
    Item.createNew
  }
  
  /*def testItems: EditItem = {
    val items = getAllItems
    val buffer = new ObservableBuffer[Item]
    buffer ++= items
    new EditItem(buffer)
  }
  * 
  */
  
  def testItem2[Repr <: HList](implicit gen: LabelledGeneric.Aux[Item, Repr]): EditItem[Repr] = {
    val item = byId(Item.Id(1))
    //val gen = LabelledGeneric[Item]
    val itemHList = gen.to(item)
    new EditItem(itemHList)
  }
  
}