package olioIO

import java.io.IOException
import java.io.PrintWriter
import main.Olio
import scala.io.Source

object SaverLoader {
  
  
  var chunkHeader: Array[Char] = Array(5)
  
  def loadOlio(input: Source) {
    
    chunkHeader = input.take(5).toArray
    
    
    
  }
  
  
  
  def saveOlio(olio: Olio) {
    
  }
  
  
  def getChunkName(chunkHeader: Array[Char]) = {
    chunkHeader.take(3).toString()
  }
  
  
  def getChunkSize(chunkHeader: Array[Char]) = {
    10 * (chunkHeader(3) - '0') + (chunkHeader(4) - '0')
  }
  
  def getHeader(loadTo: Array[Char], input: Source) = {
    
    val size = loadTo.size
    
    for (i <- 0 to size - 1) {
      loadTo(i) = input.next()
    }
    
  }
  
  
  
}