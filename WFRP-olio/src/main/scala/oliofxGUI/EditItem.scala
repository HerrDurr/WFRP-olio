package oliofxGUI

import scalafx.scene.layout.BorderPane
import olioIO.SchemaWFRP._
import olioIO.SchemaWFRP.Item._
import scalafx.scene.control.TableColumn._
import scalafx.scene.control.{TableView, TableColumn, Button}
import scalafx.collections.ObservableBuffer
import scalafx.beans.property._
import olioIO.DataHelperWFRP._
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.scene.input.MouseEvent
import scalafx.Includes._
import shapeless._
import shapeless.labelled._
import shapeless.record._
import shapeless.tag._
import dataElements.DataHelper._
import scalafx.event.ActionEvent
import scalafx.scene.control.TableCell
import scalafx.util.StringConverter

//class EditItem[Repr <: HList](val aItem : Repr) /*val items: ObservableBuffer[Item])*/(implicit gen: LabelledGeneric.Aux[Item, Repr]) extends BorderPane {
class EditItem(val aItem: Item) extends BorderPane {
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
  //val editableItem = aItem.map(unwrap(_)).map(MapToProperty)
  
  //import olioIO.SchemaWFRP.Item._
  //import dataElements.DataHelper.StringPropertyConverter._
  val gen = LabelledGeneric[Item]
  
  //val genAux = LabelledGeneric.Aux[Item, aItemRepr]
  //val gen = Generic[Item]
  
  //val aName = new StringProperty(aItem, "Name", gen.to(aItem).select[Name].value)
  
  val aGenItem = gen.to(aItem)
  val aName = aGenItem.get('name)
  private var currentName: String = aGenItem.get('name).value
  private var fItemCurrent = aGenItem
  
  def SaveItem() = {
    
  }
  
  
  //private var aCurrentItem: HList = HNil
  //aCurrentItem = aGenItem
  //aCurrentItem.+:(aGenItem)
  /*
  val colName = new TableColumn[Item, String]("Name")
  colName.cellFactory = { p =>
    val cell = new TextFieldTableCell[Item, String]
    //cell.editable = true
    cell
  }
  * 
  */
  //colName.cellValueFactory = cdf => StringProperty(gen.to(cdf.value).get[Name].value)
  /*colName.cellValueFactory = cdf => {
    val prop = new StringProperty(aItem, "name", aName.value) 
    prop.onChange { (_, aOldVal, aNewVal) => aGenItem.updated('name, aNewVal) }
    prop
  }*/
  //val aName = aGenItem.select[Name with KeyTag[Symbol with Tagged[String], Name]]
  //val aName = aGenItem.filter{aMem: FieldType[K, V] => getFieldName(aMem) == "name"}.head
  class StringConvrtr extends StringConverter[String] {
    def fromString(aStr: String) = aStr
    def toString(aStr: String) = aStr
  }
  
  val buff = new ObservableBuffer[Item]
  buff += aItem
  val table = new TableView[Item](buff) {
    editable = true
      
      val colName = new TableColumn[Item, String] {
        text = "Name"
        cellValueFactory = cdf => {
          new StringProperty(aItem, "name", gen.to(cdf.value).get('name).value.toString()) {
            //onChange{ (_, _, aNewVal) => { aCurrentItem = aGenItem.updated('name, aNewVal) } }
            onChange{ (_, _, aNewVal) => currentName = aNewVal }
          }
        }
      }
      colName.cellFactory = {aCol: TableColumn[Item, String] => {
        val cell = new TextFieldTableCell[Item, String](new StringConvrtr) {
          //item.onChange{ (_, _, aNewVal) => aGenItem.updated('name, aNewVal) }
        }
        cell
      }
      }
    columns ++= List(colName)//, colCraftsmanship, colEncumb, colCost, colAvailability)
    
  }
  
  val aTestButton = new Button {
    text = "Print item name"
    onAction = (ae: ActionEvent) => {
      val aUpdated = aGenItem.updated('name, table.colName.text.value)
      println(aUpdated.get('name))
      println(currentName)
    }
  }
  
  this.center = table
  this.bottom = aTestButton
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
  
  def testItem2[Repr <: HList](implicit gen: LabelledGeneric.Aux[Item, Repr]): EditItem = {
    val item = byId(Item.Id(1))
    //val gen = LabelledGeneric[Item]
    //val itemHList = gen.to(item)
    new EditItem(item)
  }
  
}