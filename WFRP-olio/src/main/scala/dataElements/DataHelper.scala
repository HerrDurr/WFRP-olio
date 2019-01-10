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
import shapeless.labelled.{FieldType, field}
import scala.collection.mutable.ArraySeq
import scalafx.util.StringConverter
import scalafx.util.converter._
import shapeless.ops.record._
import shapeless.syntax.singleton._

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
  
  /*
  trait DataRecord[A] {
    
    def updateData[B, Repr <: HList](aTag: String, aValue: B)(implicit
      gen: LabelledGeneric.Aux[A, Repr],
      upd: Updater.Aux[Repr, B, Repr]
    ): A = {
      val aSymb = aTag.narrow
      gen.from(upd(gen.to(this), aValue))
    }
    
  }
  object DataRecord {
    
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
  
  trait StringPropertyConverter[A] {
    def convertToProperty(aValue: A): StringProperty
  }
  object StringPropertyConverter {
    def apply[A](implicit converter: StringPropertyConverter[A]): StringPropertyConverter[A] =
      converter
      
    def instance[A](func: A => StringProperty): StringPropertyConverter[A] =
      new StringPropertyConverter[A] {
        def convertToProperty(aValue: A): StringProperty =
          func(aValue)
      }
    
    implicit def convertString: StringPropertyConverter[String] =
      instance{ aStr: String => StringProperty(aStr) }
    
    /**
     * Could be generic: replace String with Head in types
     */
    implicit def convertStringWrapper[A, Repr <: HList, Head](
      implicit
      gen: Generic.Aux[A, Repr],
      isHCons: IsHCons.Aux[Repr, Head, HNil]
      ): StringPropertyConverter[A] = instance{aWrapper: A => StringProperty(gen.to(aWrapper).head.toString()) }
  }
  
  /*
  implicit class StringProps(val aValue: String) extends AnyVal {
    def toStringProperty: StringProperty = StringProperty(this.aValue)
  }*/
  /*
  def getFieldName[A, Repr <: HList](aObj: A, aTag: Witness)(
    implicit
    gen : LabelledGeneric.Aux[A, Repr],
    selector : Selector[Repr, aTag.T],
    witn : Witness.Aux[aTag.T]
  ): String = {
    ( field[aTag.T]( selector(gen.to(aObj)) ) )
    witn.value
  }
  * 
  */
  
  def getFieldName[K, V](aValue: FieldType[K, V])
  (implicit witness: Witness.Aux[K]): K =
    witness.value
  
  def getFieldValue[K, V](aValue: FieldType[K, V]): V =
    aValue
  
  /*
  trait StringVal extends Any {
    def toStringProperty: StringProperty =
      new StringProperty(this, "", this)
  }
  * 
  */
  
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
  
  /*trait Default[A] {
    def default[A]: A
  }
  object Default {
    def apply[A](implicit aDef: Default[A]): Default[A] =
      aDef
      
    def instance[A](func: () => A): Default[A] =
      new Default[A] {
         def default[A]: A = {
           val res = func.apply()
           res
         }
      }
      
    implicit val stringDefault: Default[String]
  }
  * 
  */
  
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
   * Unwraps a case class instance with one parameter from an
   * Option (falling back on some default value in case of None),
   * and still more from its case class.
   */
  def unwrap[A, B <: Option[A], Repr <: HList, Head](aInput: B)(
      implicit
      defaulter: Defaulter[A],
      gen: Generic.Aux[A, Repr],
      isHCons: IsHCons.Aux[Repr, Head, HNil]
      ): Head = {
    unwrap( aInput.getOrElse(defaulter.getDefault(aInput)) )
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
  
  /*
   * not really using this
   */
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
    // better syntax here!
    implicit def caseInt: Case.Aux[Int, IntegerProperty] = at(aInt => IntegerProperty(aInt) )
    implicit def caseShort = at[Short]{ case s: Short => IntegerProperty(s.asInstanceOf[Int]) }
    implicit def caseLong = at[Long]{ LongProperty(_) }
    implicit def caseFloat = at[Float]{ FloatProperty(_) }
    implicit def caseDouble = at[Double]{ DoubleProperty(_) }
    implicit def caseString = at[String]{ StringProperty(_) }
    implicit def caseBoolean = at[Boolean]{ BooleanProperty(_) }
    //implicit def caseIntWrapper: Case.Aux[Int :: HNil, IntegerProperty] = at(aWrap => IntegerProperty(aWrap.head)
    /*implicit def caseArraySeq = at[ArraySeq[B]]{ 
      val res = new ObservableBuffer[B]
      res ++= _
    }*/
    implicit def caseAny = at[AnyRef]{ some: AnyRef => some } // }
  }
  
  trait StrConverterFactory[A] {
    def strConverter(aValue: A): StringConverter[A]
  }
  object StrConverterFactory {
    def apply[A](implicit convFactory: StrConverterFactory[A]): StrConverterFactory[A] =
      convFactory
    
    def instance[A](func: A => StringConverter[A]): StrConverterFactory[A] =
      new StrConverterFactory[A] {
        def strConverter(aValue: A): StringConverter[A] = 
          func(aValue)
      }
    
    // you can make more of these, see scalafx api!
    implicit val strConvFactory: StrConverterFactory[String] =
      instance[String](_ => new DefaultStringConverter)
    implicit val intConvFactory: StrConverterFactory[Int] =
      instance[Int](_ => new IntStringConverter)
    implicit val boolConvFactory: StrConverterFactory[Boolean] =
      instance[Boolean](_ => new BooleanStringConverter)
    implicit val shortConvFactory: StrConverterFactory[Short] =
      instance[Short](_ => new ShortStringConverter)
    implicit val longConvFactory: StrConverterFactory[Long] =
      instance[Long](_ => new LongStringConverter)
    implicit val charConvFactory: StrConverterFactory[Char] =
      instance[Char](_ => new CharStringConverter)
    implicit val doubleConvFactory: StrConverterFactory[Double] =
      instance[Double](_ => new DoubleStringConverter)
    implicit val floatConvFactory: StrConverterFactory[Float] =
      instance[Float](_ => new FloatStringConverter)
    //implicit def optionConvFactory[A, B <: Option[A]]: StrConverterFactory[B] = {
      //instance[B]{ aOpt: B => new OptionStringConverter[A](aOpt) }//(aOpt) )
    //}
      /*
    implicit def stringConverter[A, Repr <: HList, Head](aIn : A)(
      implicit
      convFactory: StrConverterFactory[Head],
      gen: Generic.Aux[A, Repr],
      isHCons: IsHCons[Repr, Head, HNil]
    ): StringConverterFactory[A] =*/
  }
  
  /*class OptionStringConverter[A, B <: Option[A]](aVal : B) extends StringConverter[B] {
    val nullMarkers: Array[String] = Array("", "-", "none", "nil", "null")
    
    def fromString[A, B >: Option[A]](string : String)(
        implicit
        convFact: StrConverterFactory[A]
    ): B = {
      // need aVal for the converter creation!
      if ( aVal.isEmpty || nullMarkers.exists( string.toLowerCase() == _ ) )
        None
      else {
        val convA : StringConverter[A] = convFact.strConverter(aVal.get)
        Some( convA.fromString(string) )
      }
    }
    
    def toString(aOpt: Option[A])(
        implicit
        convFact: StrConverterFactory[A]
        ): String = {
      if (aOpt.isDefined)
        convFact.strConverter(aOpt.get).toString(aOpt.get)
      else
        nullMarkers(0)
    }
    
  }
  * 
  */
  
}