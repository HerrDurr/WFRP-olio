package dataUI

import scalafx.beans.property._
import scalafx.scene.control._
import scalafx.scene.layout._
import dataElements.DataHelper._
import scalafx.collections.ObservableBuffer
import shapeless._
import shapeless.ops.hlist.IsHCons
import scalafx.scene.control.cell.TextFieldTableCell
import scalafx.beans.value.ObservableValue

object ControlFactory {
  
  
  
  def tableColumn[A, B, Repr <: HList, Head](aTableObj: A, aProp: B)(
      implicit
      genB: Generic.Aux[B, Repr],
      //lGenA: LabelledGeneric[A],
      isHCons: IsHCons.Aux[Repr, Head, HNil],
      strConvFactory: StrConverterFactory[Head]
  ): TableColumn[A, Head] = 
  {
    //val genericA = lGenA.to(aTableItem)
    val res = new TableColumn[A, Head] {
      
    }
    res.cellFactory = {
      aCol: TableColumn[A, Head] => {
        new TextFieldTableCell[A, Head]( strConvFactory.strConverter(genB.to(aProp).head) )
      }
    }
    res
  }

  
  def tableColumn[A, B, /*ReprA <: HList,*/ ReprB <: HList, Head, J1 >: Head, PropFunc <: A => B, CFunc <: (ObservableValue[Head, Head], J1, J1) => Unit]
                 (aTableObj: A, aProp: B,
                    aPropFromObjFunc: PropFunc,
                    aOnChangeFunc: CFunc)(
      implicit
      genB: Generic.Aux[B, ReprB],
      //genA: Generic.Aux[A, ReprA],
      isHCons: IsHCons.Aux[ReprB, Head, HNil],
      strConvFactory: StrConverterFactory[Head]
  ): TableColumn[A, Head] = 
  {
    val aVal : Head = genB.to(aProp).head
    val strConverter = strConvFactory.strConverter(aVal)
    //val genericA = lGenA.to(aTableItem)
    val res = new TableColumn[A, Head] {
      cellValueFactory = cdf => 
      {
        val prop = ObjectProperty( genB.to(aPropFromObjFunc(cdf.value)).head )
        prop.onChange(aOnChangeFunc)
        prop
      }
    }
    res.cellFactory = {
      aCol: TableColumn[A, Head] => {
        new TextFieldTableCell[A, Head]( strConverter )
      }
    }
    res
  }
  
  /**
   * MAGICK FUCKEN POPSICKELS!
   * could try to do that above version again, this is 'orrible
   */
  def tableColumn[A, B, ReprB <: HList, Head, J1 >: Head, AtoB <: A => B, ToA <: Any => A, SetA <: A => Unit, NewB <: Head => B]
                    (aTableObj: A, aProp: B,
                    aPropFromObjFunc: AtoB, aGetCurrentFunc: ToA, aSetCurrentFunc: SetA, aLens: Lens[A, B],
                    aNewBFunc: NewB)(
      implicit
      genB: Generic.Aux[B, ReprB],
      //genA: Generic.Aux[A, ReprA],
      isHCons: IsHCons.Aux[ReprB, Head, HNil],
      strConvFactory: StrConverterFactory[Head]
  ): TableColumn[A, Head] = 
  {
    val aVal : Head = genB.to(aProp).head
    val strConverter = strConvFactory.strConverter(aVal)
    //val genericA = lGenA.to(aTableItem)
    val res = new TableColumn[A, Head] {
      cellValueFactory = cdf => 
      {
        val prop = ObjectProperty( genB.to(aPropFromObjFunc(cdf.value)).head )
        prop.onChange{
          (_, _, aNew) => {
            val aCurrent = aGetCurrentFunc(aNew)
            aSetCurrentFunc(aLens.set(aCurrent)( aNewBFunc(aNew)) )
          }
        }
        prop
      }
    }
    res.cellFactory = {
      aCol: TableColumn[A, Head] => {
        new TextFieldTableCell[A, Head]( strConverter )
      }
    }
    res
  }
  
  
  
  /**
   * MAGICK FUCKEN POPSICKELS! Reduced version, slightly clearer
   */
  def tableColumnNew[A, B, ReprB <: HList, Head, J1 >: Head,
                     AtoB <: A => B,
                     SetA <: A => Unit,
                     NewB <: Head => B
                     ]
                    (
                    aPropDummy: B,
                    aPropFromObjFunc: AtoB,
                    aSetCurrentFunc: SetA,
                    aLens: Lens[A, B],
                    aNewBFunc: NewB
                    )(
      implicit
      genB: Generic.Aux[B, ReprB],
      //genA: Generic.Aux[A, ReprA],
      isHCons: IsHCons.Aux[ReprB, Head, HNil],
      strConvFactory: StrConverterFactory[Head]
  ): TableColumn[A, Head] = 
  {
    val aDummy : Head = genB.to(aPropDummy).head
    val strConverter = strConvFactory.strConverter(aDummy)
    //val genericA = lGenA.to(aTableItem)
    val res = new TableColumn[A, Head] {
      cellValueFactory = cdf => 
      {
        val prop = ObjectProperty( genB.to(aPropFromObjFunc(cdf.value)).head )
        prop.onChange{
          (_, _, aNew) => {
            aSetCurrentFunc( aLens.set(cdf.value)(aNewBFunc(aNew)) )
          }
        }
        prop
      }
    }
    res.cellFactory = {
      aCol: TableColumn[A, Head] => {
        new TextFieldTableCell[A, Head]( strConverter )
      }
    }
    res
  }
  
  
  
  /*
  def addCellFactory[A, B, C <: TableColumn[A, B]](aTableItem: A, aItem: B, aCol: C)(
      //implicit
      
  ): Unit = {
    
  }
  * 
  */
  
  /**
 	 * Simple Label which automatically listens to changes in the parameter
	 * stringProperty.
	 */
  def dataLabel(property: StringProperty): Label =
  {
    val res = new Label(property.value)
    res.text <== property
    res
  }
  /*
  def createDataLabel(property: IntegerProperty) = 
  {
    val res = new Label(property.value.toString)
    res.text <== property
    res
  }
  * 
  */
  
  /**
   * Creates a TextField that directly edits the given StringProperty.
   */
  def dataTextField(property: StringProperty): TextField =
  {
    val aRes = new TextField
    aRes.text <==> property
    aRes
  }
  
  /**
   * Wrapped in a VBox: a TextField that edits the given 
   * property, and a label above it which displays the 
   * property name.
   */
  def titledTextField(property : StringProperty): VBox =
  {
    val aRes = new VBox
    aRes.children = List(new Label(property.name), dataTextField(property))
    aRes
  }
  
  /*
  def dataPropertyTable(aRows: ObservableBuffer[DataPropertyRow]) =
  {
    val aRes = new TableView(aRows)
    
    // tässä olisi käyttöä Shapelessille!!
    // could make a common case with a Polymorphic function!!
    for (aProp <- aRows.head.properties)
    {
      val propCol = new TableColumn
    }
  }
  * 
  */
  
}