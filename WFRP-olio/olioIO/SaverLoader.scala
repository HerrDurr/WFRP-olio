package olioIO

import java.io.IOException
import java.io.PrintWriter
import main.{Olio, Weapon}
import scala.io.Source
import java.awt.Color
import scala.swing.Dialog

object SaverLoader {
  
  
  def loadOlio(input: Source, olio: Olio) = {
    
    val chunkHeader: Array[Char] = new Array(5)
    var chunkName = ""
    var chunkSize = 0
    
    try {
      getData(chunkHeader, input)
      
      chunkName = this.getChunkName(chunkHeader)
      chunkSize = this.getChunkSize(chunkHeader)
      
      while (chunkName != "END") {
        val dataArray: Array[Char] = new Array(chunkSize)
        getData(dataArray, input)
        var dataString = ""
        dataArray.foreach(dataString += _)
        
        if (chunkName == "NAM")
        {
          olio.setName(dataString)
        }
           
        if (chunkName == "RAC")
        {
          olio.setRace(dataString)
        }
             
        if (chunkName == "CAR")
        {
          olio.career.change(dataString)
        }
            
        if (chunkName == "COL")
        {
          val split = dataString.split(",").map(_.toInt)
          olio.setColour(new Color(split(0), split(1), split(2)))
        }
          
        if (chunkName == "ATR")
        {
          val split = dataString.split(",").map(_.toInt)
          for (x <- 0 to split.length - 1)
          {
            var i = x
            if (x == 2 || x == 3)
            {
              olio.attributes.setAttribute(i, split(i))
              olio.attributes.setAttribute(i + 8, split(i) / 10)
            }
            if (x > 9) i = x + 2
            olio.attributes.setAttribute(i, split(x))
          }
        }
          
        if (chunkName == "CRW")
        {
          olio.setCurrentWounds(dataString.toInt)
        }
       
        if (chunkName == "FOR")
        {
          olio.setFortune(dataString.toInt)
        }
          
        if (chunkName == "WPN")
        {
          val names = dataString.split(",")
          val n = names.length
          for (i <- 0 to n - 1)
          {
            olio.weapons(i) = new Weapon(names(i))
          }
        }
          
        if (chunkName == "SKL")
        {
          val skills = dataString.split(",")
          val names = skills.map(_.tail)
          val lvls = skills.map(_.head - '0')
          val n = skills.length
          for (i <- 0 to n - 1)
          {
            val skill = olio.allSkills.find(_.name == names(i)).get
            for (lvl <- 0 to lvls(i) - 1)
            {
              olio.addSkill(skill)
            }
          }
        }
          
        if (chunkName == "TAL")
        {
          val names = dataString.split(",")
          val n = names.length
          for (i <- 0 to n - 1)
          {
            val talent = olio.allTalents.find(_.name == names(i)).get
            olio.addTalent(talent)
          }
        }
         
        if (chunkName == "APO")
        {
          val split = dataString.split(",").map(_.toInt)
          for (i <- 0 to split.length - 1)
          {
            olio.armourPoints(i) = split(i)
          }
        }
          
        if (chunkName == "CMT")
        {
          olio.comments = dataString
        }
          
        getData(chunkHeader, input)
        chunkName = this.getChunkName(chunkHeader)
        chunkSize = this.getChunkSize(chunkHeader)
      }
    } catch {
      
      case e: IOException =>
        val dataException = new CorruptedDataFileException("Reading save data failed.")
        dataException.initCause(e)
        throw dataException
      case e: NoSuchElementException =>
        val dataException = new CorruptedDataFileException("A piece of save data is missing.")
        dataException.initCause(e)
        throw dataException
      case e: IndexOutOfBoundsException =>
        val dataException = new CorruptedDataFileException("Reading the save data failed (index out of bounds).")
        dataException.initCause(e)
        throw dataException
      case e: IllegalArgumentException =>
        val dataException = new CorruptedDataFileException("Illegal argument when trying to load Olio.")
        dataException.initCause(e)
        throw dataException
    }
    
  }
  
  
  
  def saveOlio(olio: Olio) = {
    //TODO: Tee tama
    
    val fileName = "data/saves/" + olio.name.trim() + ".txt"
    val file = new PrintWriter(fileName)
    
    var saveData = ""
    
    try {
      
      
      
      file.print(saveData)
      
    } finally {
      file.close()
    }
    
  }
  
  
  def getChunkName(chunkHeader: Array[Char]) = {
    var name = ""
    chunkHeader.take(3).foreach(name += _)
    name
  }
  
  
  def getChunkSize(chunkHeader: Array[Char]) = {
    10 * (chunkHeader(3) - '0') + (chunkHeader(4) - '0')
  }
  
  def getData(loadTo: Array[Char], input: Source) = {
    
    val size = loadTo.size
    var cursor = 0
    
    while (cursor < size)
    {
      if (input.hasNext) loadTo(cursor) = input.next()
      cursor += 1
    }
    
    /*
    for (i <- 0 to size - 1)
    {
      if (input.hasNext) loadTo(i) = input.next()
    }
    * 
    */
    
  }
  
  
  
}