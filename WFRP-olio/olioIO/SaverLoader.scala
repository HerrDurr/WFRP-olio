package olioIO

import java.io.IOException
import java.io.PrintWriter
import java.io.File
import main.{Olio, Weapon, Group}
import scala.io.Source
import java.awt.Color
import scala.swing.Dialog
import scala.collection.mutable.Buffer

object SaverLoader {
  
  
  def loadGroup() = ???
  
  
  def saveGroup(group: Group) = {
    ???
  }
  
  
  def loadOlio(input: Source, olio: Olio) = {
    
    val chunkHeader: Array[Char] = new Array(6)
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
          for (i <- 0 to split.length - 1)
          {
            olio.attributes.setAttribute(i, split(i))
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
          if (!skills(0).isEmpty)
          {
            val names = skills.map(_.tail)
            val lvls = skills.map(_.head - '0') // - '0' changes Char to Int
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
        }
          
        if (chunkName == "TAL")
        {
          val names = dataString.split(",")
          if (!names(0).isEmpty())
          {
            val n = names.length
            for (i <- 0 to n - 1)
            {
              val talent = olio.allTalents.find(_.name == names(i)).get
              olio.addTalent(talent)
            }
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
  
  
  
  def saveOlio(olio: Olio, saveFile: File) = {
    
    
    //val fileName = "data/saves/" + olio.name.trim() + ".txt"
    //val file = new PrintWriter(fileName)
    val file = new PrintWriter(saveFile)
    
    val saveData: Buffer[Char] = Buffer()
    var header = ""
    
    try {
      
      
      while (header != "END")
      {
        val dataTuple = appendData(header, olio)
        header = dataTuple._1
        dataTuple._2.foreach(saveData += _)
        //saveData += dataTuple._2
      }
      
      saveData.foreach(file.print(_))
      //file.print(saveData)
      
    } catch {
      
      case e: IOException =>
        val dataException = new CorruptedDataFileException("Creating the save data failed.")
        dataException.initCause(e)
        throw dataException
      case e: NoSuchElementException =>
        val dataException = new CorruptedDataFileException("A piece of data is missing.")
        dataException.initCause(e)
        throw dataException
      case e: IndexOutOfBoundsException =>
        val dataException = new CorruptedDataFileException("Reading the data failed (index out of bounds).")
        dataException.initCause(e)
        throw dataException
      case e: IllegalArgumentException =>
        val dataException = new CorruptedDataFileException("Illegal argument.")
        dataException.initCause(e)
        throw dataException
    } finally {
      file.close()
    }
    
  }
  
  def appendData(header: String, olio: Olio): Tuple2[String, Vector[Char]] = {
    
    var resHeader = ""
    var resData: Buffer[Char] = Buffer()
    var data = ""
    
    if (header.isEmpty())
    {
      resHeader = "NAM"
      data = olio.name
    }
    
    else if (header == "NAM")
    {
      resHeader = "RAC"
      data = olio.race
    }
    
    else if (header == "RAC")
    {
      resHeader = "CAR"
      data = olio.career.current
    }
    
    else if (header == "CAR")
    {
      resHeader = "COL"
      data = olio.colour.getRed.toString() + "," + olio.colour.getGreen.toString() + "," + olio.colour.getBlue.toString()
    }
    
    else if (header == "COL")
    {
      resHeader = "ATR"
      val attributeValues = olio.attributes.listValues
      attributeValues.dropRight(1).foreach( data += _ + "," )
      attributeValues.takeRight(1).foreach( data += _ )
    }
    
    else if (header == "ATR")
    {
      resHeader = "CRW"
      data = olio.currentWounds.toString()
    }
    
    else if (header == "CRW")
    {
      resHeader = "FOR"
      data = olio.fortunePoints.toString()
    }
    
    else if (header == "FOR")
    {
      resHeader = "WPN"
      olio.weapons.dropRight(1).foreach(data += _.name + ",")
      data += olio.weapons.takeRight(1)(0).name
    }
    
    else if (header == "WPN")
    {
      resHeader = "SKL"
      val skills = olio.skills.filter(_.timesGained > 0).map(s => s.timesGained.toString() + s.name)
      if (!skills.isEmpty)
      {
        skills.dropRight(1).foreach(data += _ + ",")
        data += skills.takeRight(1)(0)
      }
    }
    
    else if (header == "SKL")
    {
      resHeader = "TAL"
      val talents = olio.talents.map(_.name)
      if (!talents.isEmpty)
      {
        talents.dropRight(1).foreach(data += _ + ",")
        data += talents.takeRight(1)(0)
      }
    }
    
    else if (header == "TAL")
    {
      resHeader = "APO"
      val armourPoints = olio.armourPoints.map(_.toString)
      armourPoints.dropRight(1).foreach(data += _ + ",")
      data += armourPoints.takeRight(1)(0)
    }
    
    else if (header == "APO")
    {
      resHeader = "CMT"
      data += olio.comments
    }
    
    else if (header == "CMT")
    {
      resHeader = "END"
    }
    
    else if (header == "END")
    {
      
    }
    
    resHeader.foreach(resData += _)
    this.chunkSizer(data.length()).toString.foreach(resData += _)
    data.foreach(resData += _)
    //resData += resHeader + this.chunkSizer(data.length()) + data
    (resHeader, resData.toVector)
  }
  
  def chunkSizer(length: Int) = {
    var result = ""
    result += (length / 100)
    result += (length % 100 / 10)
    result += (length % 10)
    result
  }
  
  
  def getChunkName(chunkHeader: Array[Char]) = {
    var name = ""
    chunkHeader.take(3).foreach(name += _)
    name
  }
  
  
  def getChunkSize(chunkHeader: Array[Char]) = {
    100 * (chunkHeader(3) - '0') + 10 * (chunkHeader(4) - '0') + (chunkHeader(5) - '0')
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