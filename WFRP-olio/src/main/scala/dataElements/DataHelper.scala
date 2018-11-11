package dataElements

import scalafx.beans.property._
import scala.reflect.ClassTag
import slick.driver.SQLiteDriver.api._
import scalafx.beans.Observable
import scalafx.collections.ObservableBuffer
import scalafx.collections.ObservableHashMap
import slick.ast.TypedType
import slick.ast.ScalaBaseType
import scala.collection.immutable.List

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
  /*
  def CommaTextToArray(rawValue: StringProperty): ObservableBuffer = {
    if (rawValue.isEmpty)
      None
    else
      Some(rawValue.get.split(','))
  }
  * 
  */
  
  abstract class DataPropertyRow {
    
    private var fProperties : Option[ObservableBuffer[Observable]] = None
    
    // I wanna give a Vector[Property] or Vector[ObservableValue]
    // but can't due to the stupid [T,J] in the end
    def properties : ObservableBuffer[Observable] = {
      if (fProperties.isEmpty)
      {
        fProperties = Some(this.initProperties)
      }
      fProperties.getOrElse(ObservableBuffer())
    }
    
    def initProperties : ObservableBuffer[Observable]
    
    
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