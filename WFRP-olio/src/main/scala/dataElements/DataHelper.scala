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
import shapeless.ops.hlist.IsHCons
import scala.collection.mutable.ArraySeq

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
  
  trait PropertyConverter[A] {
    def convert(aValue : A): List[Observable]
  }
  object PropertyConverter {
    def apply[A](implicit converter: PropertyConverter[A]): PropertyConverter[A] =
      converter
    
    def instance[A](func: A => List[Observable]): PropertyConverter[A] =
      new PropertyConverter[A] {
        def convert(aValue: A): List[Observable] = 
          func(aValue)
      }
    /*
    implicit val intConverter : PropertyConverter[Int] =
      new PropertyConverter[Int] {
        def convert(aValue: Int): List[IntegerProperty] =
          new IntegerProperty(aValue)
      }
      *  
      */
  }
  /*
  trait UnWrapper[A] {
    def unWrap(aValue: A): List[AnyVal]
  }
  object UnWrapper {
    def apply[A](implicit unWrapper: UnWrapper[A]): UnWrapper[A] = 
      unWrapper
  }
  * 
  */
  
  trait Defaulter[A] {
    def getDefault[B <: Option[A]](aMaybe : B): A
  }
  object Defaulter {
    def apply[A/*, B <: Option[A]*/](implicit defaulter: Defaulter[A]): Defaulter[A] =
      defaulter
      
    def instance[A/*, Option[A]*/](func: Option[A] => A): Defaulter[A] =
      new Defaulter[A] {
         def getDefault[B <: Option[A]](aMaybe : B): A =
           func(aMaybe)
      }
    
  implicit def intDefaulter: Defaulter[Int] = 
    instance({aPossibleInt: Option[Int] => 0})
  implicit def shortDefaulter: Defaulter[Short] =
    instance({aMaybe: Option[Short] => 0})
  implicit def stringDefaulter: Defaulter[String] =
    instance({aMaybe: Option[String] => ""})
  implicit def booleanDefaulter : Defaulter[Boolean] =
    instance({aMaybe: Option[Boolean] => false})
    
    implicit def genericDefaulter[A, B <: Option[A]](
      implicit
      optInstance: Lazy[Defaulter[A]]
      ): Defaulter[A] = 
        instance({aVal: Option[A] => optInstance.value.getDefault(aVal)})
  }
  
  
  /**
   * Friggin' magick! Testicles, barnacles, dicks, rabbits, and
   * F*CKEN' MAGICK!
   * From: The Type Astronaut's Guide to Shapeless by Dave Gurnell
   * Copyright 2016-17 Dave Gurnell.
   */
  def unwrap[A, Repr <: HList, Head](aInput: A)(
      implicit
      gen: Generic.Aux[A, Repr],
      isHCons: IsHCons.Aux[Repr, Head, HNil]
  ): Head = gen.to(aInput).head
  
  /**
   * WARNING! DOES NOT HANDLE NONES, DO NOT USE! Need to
   * find a way to give defaults for all datatypes... Then
   * we can call getOrElse(getDefault). Trying this above with
   * trait Defaulter[A]
   */
  def unwrap[A](aInput: Option[A]): A = {
    aInput.getOrElse(getDefault(aInput))
  }
  
  def unwrapContents[A, Repr <: HList, Head](aInput: Array[A])(
      implicit
      gen: Generic.Aux[A, Repr],
      isHCons: IsHCons.Aux[Repr, Head, HNil]
  ): ObservableBuffer[Head] = {
    val res = new ObservableBuffer[Head]
    res ++= ( aInput.map(unwrap(_)) )
    res
  }
  
  /**
   * Convert a comma-delimited text value to an Array
   */
  def CommaTextOptToArray(rawValue: Option[String]): Array[String] = {
    if (rawValue.isEmpty)
      Array()
    else
      rawValue.get.split(',')
  }
  
  def CommaTextOptToIntArray(rawValue: Option[String]): Array[Int] = {
    try
      CommaTextOptToArray(rawValue).map(_.toInt)
    catch
    {
      case _: Throwable =>
      {
        println("Conversion from comma-text to Array[Int] failed!")
        // return empty
        Array()
      }
    }
  }
  
  def ArrayToCommaTextOpt(values: Array[String]): Option[String] = {
    if (values.length < 1)
      None
    else
      Some(values.mkString(","))
  }
  
  def IntArrayToCommaTextOpt(aInts : Array[Int]): Option[String] = {
    ArrayToCommaTextOpt( aInts.map(_.toString()) )
  }
  
  /**
   * Comma-separated Some(Text), where some of Text's values
   * are "options", i.e. "/" -separated values. E.g. a WFRP
   * Career has a list of skills and talents, but some of
   * those available have to be chosen instead of another.
   * This function filters out either the non-optional or
   * optional values.
   */
  def GetCommaOps(rawValue: Option[String], aWithOptions: Boolean): Array[String] = {
    val beforeOps = CommaTextOptToArray(rawValue)
    if (aWithOptions)
      beforeOps.filter( _.contains("/") )
    else
      beforeOps.filterNot( _.contains("/") )
  }
  
  def GetCommaOpsInt(rawValue: Option[String]): Array[Int] = {
    try
      GetCommaOps(rawValue, false).map( _.toInt )
    catch
    {
      case _: Throwable =>
      {
        println("Conversion from comma-text to Int-Array failed!")
        // return empty
        Array()
      }
    }
  }
  
  /**
   * A raw text Some("a,b/c/d") to Array( Array("a"),Array("b","c","d") )
   */
  def GetCommaOpsArrayed(rawValue: Option[String]): Array[Array[String]] = {
    GetCommaOps(rawValue, true).map( _.split("/") )
  }
  
  /**
   * A raw text Some("1,2/3/4") to Array( Array(1),Array(2,3,4) )
   */
  def GetCommaOpsArrayedInt(rawValue: Option[String]): Array[Array[Int]] = {
    try
    {
      GetCommaOpsArrayed(rawValue).map( _.map(_.toInt) )
    }
    catch
    {
      case _: Throwable =>
      {
        println("Conversion from comma-text with optional values to Array[Array[Int]] failed!")
        // return empty
        Array()
      }
    }
  }
  
  def ArrayedOpsToCommaOps(aOptArray : Array[Array[String]]): Option[String] = {
    if (aOptArray.length < 1)
      None
    else
    {
      ArrayToCommaTextOpt( aOptArray.map( _.filterNot(_.isEmpty).mkString("/") ) )
    }
  }
  
  def ArrayedIntOpsToCommaOps(aOptInts : Array[Array[Int]]): Option[String] = {
    ArrayedOpsToCommaOps( aOptInts.map( _.map(_.toString) ) )
  }
  
  /**
   * Array("A","B") + Array(Array("C","D")) => Some("A,B,C/D")
   */
  def DataAndOptionalToCommaOps(aData: Array[String], aOptData: Array[Array[String]]): Option[String] = {
    val nonOps = ArrayToCommaTextOpt(aData)
    val ops = ArrayedOpsToCommaOps(aOptData)
    
    if (nonOps.isEmpty)
      ops
    else if (ops.isEmpty)
      nonOps
    else
    { // neither is empty, so "get" is safe
      ArrayToCommaTextOpt( Array(nonOps.get, ops.get) )
    }
  }
  
  /**
   * Array(1,2) + Array(Array(3,4)) => Some("1,2,3/4")
   */
  def DataAndOptionalIntsToCommaOps(aData: Array[Int], aOptData: Array[Array[Int]]): Option[String] = {
    DataAndOptionalToCommaOps( aData.map(_.toString), aOptData.map( _.map(_.toString) ) )
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
    /*implicit def caseArraySeq = at[ArraySeq[B]]{ 
      val res = new ObservableBuffer[B]
      res ++= _
    }*/
    implicit def caseAny = at[AnyRef]{ some: AnyRef => some } // }
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