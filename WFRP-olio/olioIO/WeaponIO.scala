package olioIO

import java.io.BufferedReader
import java.io.IOException
import java.io.Reader
import main.Weapon
import scala.collection.mutable.Buffer

object WeaponIO {
  
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
        val weaponsExc = new CorruptedDataFileException("Reading the weapons data failed.")
        weaponsExc.initCause(e)
        throw weaponsExc
      case e: NoSuchElementException =>
        val weaponsExc = new CorruptedDataFileException("A piece of data is missing.")
        weaponsExc.initCause(e)
        throw weaponsExc
      case e: IndexOutOfBoundsException =>
        val weaponsExc = new CorruptedDataFileException("Reading the weapons data failed (index out of bounds).")
        weaponsExc.initCause(e)
        throw weaponsExc
      case e: IllegalArgumentException =>
        val weaponsExc = new CorruptedDataFileException("Illegal argument.")
        weaponsExc.initCause(e)
        throw weaponsExc
    }
    
    names.toVector
    
  }
  
  
  def loadWeapon(input: Reader, weapon: Weapon) = {
    
    val name = weapon.name.trim().toLowerCase()
    val lineReader = new BufferedReader(input)
    
    try {
      
      var currentLine = lineReader.readLine()
      var done = false
      
      findBlock()
      
      while (currentLine != null && !done)
      {
        val weaponName = currentLine.tail.trim().toLowerCase()
        
        //If this is the weapon you're looking for, do things
        if (weaponName == name)
        {
          currentLine = lineReader.readLine()
          readWeaponData()
          done = true
        }
        
        //If it was a different weapon, continue
        else findBlock()
        
      }
      
      if (!done)
        throw new CorruptedDataFileException("Corrupted weapons.txt file.")
      
      
      def readWeaponData() = {
        var done = Array(false, false, false, false)
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
      
      
      def splitDataLine = currentLine.split(":")
      
      
      def findBlock() = {
        var done = false
        while (!done && currentLine != null) {
          currentLine = lineReader.readLine()
          if (currentLine != null && !currentLine.isEmpty() && currentLine.head.toChar == '#') done = true
        }
      }
      
    } catch {
      
      case e: IOException =>
        val weaponsExc = new CorruptedDataFileException("Reading the weapons data failed.")
        weaponsExc.initCause(e)
        throw weaponsExc
      case e: NoSuchElementException =>
        val weaponsExc = new CorruptedDataFileException("A piece of data is missing.")
        weaponsExc.initCause(e)
        throw weaponsExc
      case e: IndexOutOfBoundsException =>
        val weaponsExc = new CorruptedDataFileException("Reading the weapons data failed (index out of bounds).")
        weaponsExc.initCause(e)
        throw weaponsExc
      case e: IllegalArgumentException =>
        val weaponsExc = new CorruptedDataFileException("Illegal argument.")
        weaponsExc.initCause(e)
        throw weaponsExc
    }
    
  }
  
  
}