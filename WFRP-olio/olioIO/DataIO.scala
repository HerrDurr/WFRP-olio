package olioIO

import java.io.BufferedReader
import java.io.IOException
import java.io.Reader
import main._
import scala.collection.mutable.Buffer

object DataIO {
  
  def loadNames(input: Reader) = {
    
    val lineReader = new BufferedReader(input)
    val names: Buffer[String] = Buffer()
    
    try {
      
      var currentLine = lineReader.readLine()
      
      findBlock()
      
      while (currentLine != null) {
        names += currentLine.tail
        currentLine = lineReader.readLine()
        findBlock()
      }
      
      def findBlock() = {
        var done = false
        while (!done && currentLine != null) {
          currentLine = lineReader.readLine()
          if (currentLine != null && !currentLine.isEmpty() && currentLine.head.toChar == '#') done = true
        }
      }
    } catch {
      
      case e: IOException =>
        val weaponsExc = new CorruptedDataFileException("Reading the data failed.")
        weaponsExc.initCause(e)
        throw weaponsExc
      case e: NoSuchElementException =>
        val weaponsExc = new CorruptedDataFileException("A piece of data is missing.")
        weaponsExc.initCause(e)
        throw weaponsExc
      case e: IndexOutOfBoundsException =>
        val weaponsExc = new CorruptedDataFileException("Reading the data failed (index out of bounds).")
        weaponsExc.initCause(e)
        throw weaponsExc
      case e: IllegalArgumentException =>
        val weaponsExc = new CorruptedDataFileException("Illegal argument.")
        weaponsExc.initCause(e)
        throw weaponsExc
    }
    
    names.toVector
    
  }
  
  
}