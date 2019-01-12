package commonLib

object Util {
  
  implicit class OpsNum(val str: String) extends AnyVal {
    def isNumeric() = scala.util.Try(str.toDouble).isSuccess
    
    def isShort() = scala.util.Try(str.toShort).isSuccess
  }
  
}