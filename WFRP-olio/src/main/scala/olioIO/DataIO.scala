package olioIO

import java.io.BufferedReader
import java.io.IOException
import java.io.Reader
import scala.collection.mutable.Buffer
import olioMain._
import olioIO.CorruptedDataFileException

object DataIO {
  
  /* NOT IN USE
  /**
   * Loads all basic skills in the input reader file. Returns the skills in a Vector.
   */
  def loadBasicSkills(input: Reader) = {
    
    val lineReader = new BufferedReader(input)
    val basics: Buffer[Skill] = Buffer()
    
    try {
      
      var currentLine = lineReader.readLine()
      
      findBlock()
      
      while (currentLine != null) {
        
        val name = currentLine.tail
        var isBasic = false
        
        currentLine = lineReader.readLine()
        
        while (!isBasic && currentLine != null && !currentLine.isEmpty() && currentLine.head != '#') {
          val split = currentLine.split(":")
          if (split(0).trim().toLowerCase() == "basic" && split(1).trim().toLowerCase() == "y")
            isBasic = true
          currentLine = lineReader.readLine()
        }
        
        if (isBasic) basics += new Skill(name)
        
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
    
    basics.toVector
    
  }
  */
  
  
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
        
        //If this is the item you're looking for, do things
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
        var done = Array(false, false, false, false, false)
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
            case "reload" =>
              weapon.setReloadTime(data.trim())
              done(3) = true
            case "qualities" =>
              weapon.setQualities(data.trim())
              done(4) = true
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
          val splitLine = splitDataLine
          splitLine(0).trim().toLowerCase() match {
            case "attribute" =>
              skill.setAttribute(splitLine(1).trim())
              done(0) = true
            case "basic" =>
              skill.setBasic("y" == splitLine(1).trim())
              done(1) = true
            case "talents" =>
              skill.setTalents( splitLine(1).split(",").map( _.trim() ) )
              done(2) = true
            case "description" =>
              skill.setDescription(readDescription)
              done(3) = true
          }
          currentLine = lineReader.readLine()
        }
      }
      
      
      /**
       * Reads the .txt file data on the talent and sets its values accordingly.
       */
      def readTalentData() = {
        var done = Array(false, false, false, false)
        val talent = item.asInstanceOf[Talent]
        
        while (!done.forall(_ == true) && !currentLine.isEmpty() && currentLine != null && currentLine.head.toChar != '#') {
          val splitLine = splitDataLine
          splitLine(0).trim().toLowerCase() match {
            case "attributes" =>
              val data = splitLine(1).trim()
              if (data != "-") talent.setAttributes(data.split(",").map( _.trim() ).toVector)
              done(0) = true
            case "skills" =>
              val data = splitLine(1).trim()
              if (data != "-") talent.setSkills(data.split(",").map( _.trim() ).toVector)
              done(1) = true
            case "weapons" =>
              val data = splitLine(1).trim()
              if (data != "-") talent.setWeapons(data)
              done(2) = true
            case "description" =>
              talent.setDescription(readDescription)
              done(3) = true
          }
          currentLine = lineReader.readLine()
        }
      }
      
      
      /**
       * Reads the description of a .txt file. Give the current line (the beginning) as the parameter.
       * Returns the entire description once the block of text is finished.
       */
      def readDescription = {
        currentLine = lineReader.readLine()
        var result = ""
        var done = false
        while (!done && currentLine != null) {
          result += currentLine + "\n"
          currentLine = lineReader.readLine()
          if (currentLine == null || currentLine.trim().isEmpty() || currentLine.head == '#') done = true
        }
        result
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