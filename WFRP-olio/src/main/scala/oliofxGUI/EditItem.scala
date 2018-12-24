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
import scalafx.beans.value.ObservableValue
import scalafx.scene.layout.HBox
import scalafx.util.converter.DefaultStringConverter
import dataUI.ControlFactory._

//import scalafx.util.StringConverter

//class EditItem[Repr <: HList](val aItem : Repr) /*val items: ObservableBuffer[Item])*/(implicit gen: LabelledGeneric.Aux[Item, Repr]) extends BorderPane {
class EditItem/*[Repr <: HList]*/(val aItem: Item)/*(implicit genAux: LabelledGeneric.Aux[Item, Repr])*/ extends BorderPane {
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
  private val gen = LabelledGeneric[Item]
  
  //val genAux = LabelledGeneric.Aux[Item, aItemRepr]
  //val gen = Generic[Item]
  
  //val aName = new StringProperty(aItem, "Name", gen.to(aItem).select[Name].value)
  
  import Item._
  
  private val aGenItem = gen.to(aItem)
  val aName = aGenItem.get('name)
  private var currentName: String = aGenItem.get('name).value
  private var fItemCurrent: Item = gen.from(aGenItem)
  
  def getCurrent(dummy: Any): Item = this.fItemCurrent
  def setCurrent(aNewItem: Item) = this.fItemCurrent = aNewItem
  
  def SaveItem() = {
    import dbContext._
    
    val itemId = this.fItemCurrent.id//get('id)
    
    if (itemId.value >= 0)
    {
      val opt = byId(itemId)
      if (opt.isDefined)
      {
        // update
        val q = quote {
          query[Item].filter(_.id == lift(itemId)).update(lift(fItemCurrent))
        }
        dbContext.run(q)
      }
      else
      {
        // insert
        val q = quote {
          // l'erreur
          //query[Item].insert(lift(fItemCurrent)).onConflictUpdate(_.id)( (t, e) => t -> e )
          query[Item].insert(lift(fItemCurrent))
        }
        dbContext.run(q)
      }
    }
    
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
  
  val buff = new ObservableBuffer[Item]
  buff += fItemCurrent //aItem
  val table = new TableView[Item](buff) {
    editable = true
      
      val colName = new TableColumn[Item, String] {
        text = "Name"
        cellValueFactory = cdf => {
          new StringProperty(aItem, "name", gen.to(cdf.value).get('name).value.toString()) {
            onChange{ (_, _, aNewVal) => { fItemCurrent = lName.set(fItemCurrent)(Name(aNewVal)) } }
          }
        }
      }
      colName.cellFactory = {
        aCol: TableColumn[Item, String] => {
          val cell = new TextFieldTableCell[Item, String](new DefaultStringConverter)
          cell
        }
      }
      val colCraftsmanship = new TableColumn[Item, String] {
        text = "Craftsmanship"
        cellValueFactory = cdf => {
          import Craftsmanship._
          val prop: StringProperty = new StringProperty( aItem, "craftsmanship", gen.to(cdf.value).get('craftsmanship).toString() )
          {
            onChange
            { 
              (_, aOld, aNew) => 
              { 
                val aCrafts = Craftsmanship.byEnumOrName(aNew)
                fItemCurrent = lCraft.set(fItemCurrent)( aCrafts.getOrElse(byEnumOrName(aOld).get) )
                //if (aCrafts.isDefined)
                  //fItemCurrent = lCraft.set(fItemCurrent)( aCrafts.get )
                //else oh my fucking god why can i not undo an edit?!?!!
                  //prop.value = aOld
                // else herja?
              }
            }
          }
          prop
        }
      }
      colCraftsmanship.cellFactory = {
        aCol: TableColumn[Item, String] => {
          new TextFieldTableCell[Item, String](new DefaultStringConverter)
        }
      }
    
      /*
    val colEncumb = tableColumn(aItem, aItem.encumbrance)
    colEncumb.text = "Encumbrance"
    colEncumb.cellValueFactory = cdf => {
      val prop = ObjectProperty(cdf.value.encumbrance.value)
      prop.onChange
      { (_, _, aNew) => 
        {
          fItemCurrent = lEnc.set(fItemCurrent)( Encumbrance(aNew) )
        }
      }
      prop
    }
    * 
    */
    val aItoEnc = { aA: Item => aA.encumbrance }
    /*def aEncOnChange[J1 >: Short]: (ObservableValue[Short, Short], J1, J1) => Unit = { aTup: (ObservableValue[Short, Short], J1, J1) => 
          {
            fItemCurrent = lEnc.set(fItemCurrent)( Encumbrance(aTup._3) )
          }
        }
        *  
        */
    // popsickles -version
    val colEncumb: TableColumn[Item, Short] = tableColumn(fItemCurrent, fItemCurrent.encumbrance/*aItem, aItem.encumbrance*/, aItoEnc, {a: Any => getCurrent(a)}, 
        {aNewItem : Item => fItemCurrent = aNewItem}, lEnc, {aVal: Short => Encumbrance(aVal)})
    /*val colEncumb: TableColumn[Item, Short] = tableColumn(aItem, aItem.encumbrance, aItoEnc,
        { aTup: (ObservableValue[Short, Short], Short, Short) => 
          {
            fItemCurrent = lEnc.set(fItemCurrent)( Encumbrance(aTup._3) )
          }
        }
    )
    * 
    */
      
    columns ++= List(colName, colCraftsmanship, colEncumb)//, colCost, colAvailability)
    
  }
  
  val aTestButton = new Button {
    text = "Print me!"
    onAction = (ae: ActionEvent) => {
      //val aUpdated = aGenItem.updated('name, table.colName.text.value)
      //println(aUpdated.get('name))
      println(fItemCurrent.name.value)  //currentName)
      println(fItemCurrent.toString())
      println(gen.to(fItemCurrent))
    }
  }
  val aSaveBtn = new Button {
    text = "Save"
    onAction = (ae: ActionEvent) => {
      SaveItem()
    }
  }
  
  this.center = table
  this.bottom = new HBox {
    children ++= List(aTestButton, aSaveBtn)
  }
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
    val item = byId(Item.Id(1)).getOrElse(this.newItem)
    //val gen = LabelledGeneric[Item]
    //val itemHList = gen.to(item)
    new EditItem(item)
  }
  
}