package olioIO

import java.io.BufferedReader
import java.io.IOException
import java.io.Reader
import main._
import scala.collection.mutable.Buffer

object DataIO {
  
  /**
   * Used to load the names of all items in a .txt file. A name is recognized as what comes after a '#' -character.
   */
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
      
      
      /**
      * Finds the next block of data that starts with a #.
      */
      def findBlock() = {
        var done = false
        while (!done && currentLine != null) {
          currentLine = lineReader.readLine()
          if (currentLine != null && !currentLine.isEmpty() && currentLine.head.toChar == '#') done = true
        }
      }
      
    } catch {
      
      case e: IOException =>
        val dataException = new CorruptedDataFileException("Reading the data failed.")
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
    }
    
    names.toVector
    
  }

  
  /**
   * Used to load a Weapon, Skill, or Talent based on the name.
   * @param input The Reader of the .txt -file to check for the item.
   * @param n The name of the item.
   */
  def loadItem(input: Reader, item: Loadable) = {
    
    val name = item.name.trim().toLowerCase()
    val lineReader = new BufferedReader(input)
    
    try {
      
      var currentLine = lineReader.readLine()
      var done = false
      
      val fileTag = currentLine.head.toLower
      /*{
        if (currentLine.toLowerCase().contains("skill")) "skill"
        else if (currentLine.toLowerCase().contains("weapon")) "weapon"
        else if (currentLine.toLowerCase().contains("talent")) "talent"
      }
      * 
      */
      
      findBlock()
      
      while (currentLine != null && !done)
      {
        val itemName = currentLine.tail.trim().toLowerCase()
        
        //If this is the weapon you're looking for, do things
        if (itemName == name)
        {
          currentLine = lineReader.readLine()
          if (item.isInstanceOf[Weapon]) readWeaponData()
          else if (item.isInstanceOf[Skill]) readSkillData()
          else if (item.isInstanceOf[Talent]) readTalentData()
          done = true
        }
        
        //If it was a different item, continue
        else findBlock()
        
      }
      
      
      /**
       * Reads the .txt file data on the weapon and sets its values accordingly.
       */
      def readWeaponData() = {
        var done = Array(false, false, false, false)
        val weapon = item.asInstanceOf[Weapon]
        while (!done.forall(_ == true) && !currentLine.isEmpty() && currentLine != null && currentLine.head.toChar != '#') {
          val data = splitDataLine(1)
          splitDataLine(0).trim().toLowerCase() match {
            case "group" =>
              weapon.changeGroup(data.trim())
              done(0) = true
            case "damage" =>
              weapon.setDamage(data.trim())
              done(1) = true
            case "range" =>
              weapon.setRange(data.trim())
              done(2) = true
            case "qualities" =>
              weapon.setQualities(data.trim())
              done(3) = true
          }
          currentLine = lineReader.readLine()
        }
      }
      
      
      /**
       * Reads the .txt file data on the skill and sets its values accordingly.
       */
      def readSkillData() = {
        var done = Array(false, false, false, false)
        val skill = item.asInstanceOf[Skill]
        while (!done.forall(_ == true) && !currentLine.isEmpty() && currentLine != null && currentLine.head.toChar != '#') {
          val data = splitDataLine(1)
          splitDataLine(0).trim().toLowerCase() match {
            case "attribute" =>
              skill.setAttribute(data.trim())
              done(0) = true
            case "basic" =>
              skill.setBasic("y" == data.trim())
              done(1) = true
            case "talents" =>
              skill.setTalents( data.split(",").map( _.trim() ) )
              done(2) = true
            case "description" =>
              
              skill.setDescription(data)
              done(3) = true
          }
          currentLine = lineReader.readLine()
        }
      }
      
      
      /**
       * Reads the .txt file data on the talent and sets its values accordingly.
       */
      def readTalentData() = {
        
      }
      
      
      def readDescription(current: String) = {
        currentLine = lineReader.readLine()
        
      }
      
      
      /**
       * Splits the current line of data at ':' characters.
       */
      def splitDataLine = currentLine.split(":")
      
      
      /**
       * Finds the next block of data that starts with a #.
       */
      def findBlock() = {
        var done = false
        while (!done && currentLine != null) {
          currentLine = lineReader.readLine()
          if (currentLine != null && !currentLine.isEmpty() && currentLine.head.toChar == '#') done = true
        }
      }
      
    } catch {
      
      case e: IOException =>
        val dataException = new CorruptedDataFileException("Reading the data failed.")
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
    }
    
  }
  
  
}