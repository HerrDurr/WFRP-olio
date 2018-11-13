package dataElements

import scalafx.beans.property._
//import scala.reflect.ClassTag
import slick.driver.SQLiteDriver.api._
import scalafx.beans.Observable
import scalafx.collections.ObservableBuffer
import scalafx.collections.ObservableHashMap
//import slick.ast.TypedType
//import slick.ast.ScalaBaseType
import scala.collection.immutable.List
import shapeless._
import shapeless.Poly1

object DataHelper {
  
  /*
  trait DataProperty[+T, +J] extends Property[Any, Any] {
    
    
  }
  
  class StringDataProperty extends StringProperty with DataProperty[String, String] {
    
  }
  
  def PropertyFromValue(aValue : T) : DataPropertyMixin[T, J] = {
    
  }
  * 
  */
  
  /**
   * Convert a comma-delimited text value to an Array
   */
  def CommaTextOptToArray(rawValue: Option[String]): Array[String] = {
    if (rawValue.isEmpty)
      Array()
    else
      rawValue.get.split(',')
  }
  
  def ArrayToCommaTextOpt(values: Array[String]): Option[String] = {
    if (values.length < 1)
      None
    else
      Some(values.mkString(","))
  }
  
  
  abstract class DataPropertyRow {
    
    //private var fProperties : Option[ObservableBuffer[Observable]] = None
    private var fProps : Option[HList] = None
    
    // I wanna give a Vector[Property] or Vector[ObservableValue]
    // but can't due to the stupid [T,J] in the end
    /*def properties : ObservableBuffer[Observable] = {
      if (fProperties.isEmpty)
      {
        fProperties = Some(this.initProperties)
      }
      fProperties.getOrElse(ObservableBuffer())
    }
    * 
    */
    
    def props : HList = {
      if (fProps.isEmpty)
      {
        fProps = Some(this.initProps)
      }
      fProps.getOrElse(HNil)
    }
    
    //def initProperties : ObservableBuffer[Observable]
    
    def initProps : HList = HNil
    
  }
  
  object MapToProperty extends Poly1 {
    implicit def caseInt = at[Int]{ IntegerProperty(_) }
    implicit def caseShort = at[Short]{ case s: Short => IntegerProperty(s.asInstanceOf[Int]) }
    implicit def caseLong = at[Long]{ LongProperty(_) }
    implicit def caseFloat = at[Float]{ FloatProperty(_) }
    implicit def caseDouble = at[Double]{ DoubleProperty(_) }
    implicit def caseString = at[String]{ StringProperty(_) }
    implicit def caseBoolean = at[Boolean]{ BooleanProperty(_) }
    implicit def caseAny = at[AnyRef]{ ObjectProperty(_) }
  }
  
  /*
  def valueToProperty[C](aOwner: AnyRef, aName: String, aValue : C)(implicit tt: TypedType[C]): 
    Property[_ >: String with Int with Boolean with Long with Float with Double, _ >: String with Number with Boolean <: java.io.Serializable] =
    {
      tt match
      {
        case ScalaBaseType.stringType =>  new StringDataProperty(aOwner, aName, aValue.asInstanceOf[String]) //new StringProperty(aOwner, aName, aValue.asInstanceOf[String])
        case ScalaBaseType.booleanType => new BooleanDataProperty(aOwner, aName, aValue.asInstanceOf[Boolean])
      }
    }
  
  
  abstract class TypedProp[T, J](implicit final val propType : TypedType[T], 
                                 implicit final val javaType : TypedType[J]) extends Property[T, J]
  
  class StringDataProperty(aOwner: AnyRef, aName: String, aValue: String) extends TypedProp[String, String] {
    
    val innerProp = new StringProperty(aOwner, aName, aValue)
    
    def value = innerProp.value
    def value_=(v: String) = innerProp.value_=(v)
    def delegate = innerProp.delegate
    
  }
  
  class BooleanDataProperty(aOwner: AnyRef, aName: String, aValue: Boolean) extends TypedProp[Boolean, Boolean] {
    
    val innerProp = new BooleanProperty(aOwner, aName, aValue)
    
    def value = innerProp.value
    def value_=(v: Boolean) = innerProp.value_=(v)
    def delegate = innerProp.asInstanceOf[Property[Boolean, Boolean]].delegate
    
  }*/
  /*
  def propertyFromValue[C](aOwner: AnyRef, aName: String, aValue : C)(implicit tt: TypedType[C]) = {
    
    
    
  }
  * 
  */
  
  /*
  def PropertyFromValue(aValue : AnyRef): Property[ClassTag[aValue.getClass], ClassTag[aValue.getClass]] = {
    
    val aClassTag = ClassTag(aValue.getClass)
    // super-fugly kludge mofos!
    val aString : String = ""
    val aInt : Int = 0
    val aBoolean : Boolean = true
    val aFloat : Float = 0.0
    val aLong : Long = 0
    
    aClassTag match {
      case ClassTag(aString.getClass) => new StringProperty
    }
    
  }
  * 
  */
  /*
  def PropertyFromValue(aValue : String): Property[String, String] = {
    new StringProperty(aValue)
  }
  * 
  */
  
  
}