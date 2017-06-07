package olioIO

import java.io.BufferedReader
import java.io.IOException
import java.io.Reader
import main.Weapon

object WeaponIO {
  
  def loadWeapon(input: Reader, weapon: Weapon) = {
    
    
    val lineReader = new BufferedReader(input)
    
    try {
      
      var currentLine = lineReader.readLine()
      var done = false
      
      findBlock()
      
      while (currentLine != null && !done)
      {
        val weaponName = currentLine.tail.trim().toLowerCase()
        
        //If this is the weapon you're looking for, do things
        if (weaponName == weapon.name.trim().toLowerCase())
        {
          
          readWeaponData()
          done = true
        }
        
        //If it was a different weapon, continue
        else findBlock()
        
      }
      
      if (!done)
        throw new CorruptedWeaponsFileException("Corrupted weapons.txt file.")
      
      
      def readWeaponData() = {
        while (!currentLine.isEmpty() && currentLine != null && currentLine.head.toChar != '#') {
          val data = splitDataLine(1)
          splitDataLine(0).trim().toLowerCase() match {
            case "group" => weapon.changeGroup(data.trim())
            case "damage" => weapon.setDamage(data.trim())
            case "range" => weapon.setRange(data.trim())
            case "qualities" => weapon.setQualities(data.trim())
          }
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
        val weaponsExc = new CorruptedWeaponsFileException("Reading the weapons data failed.")
        weaponsExc.initCause(e)
        throw weaponsExc
      case e: NoSuchElementException =>
        val weaponsExc = new CorruptedWeaponsFileException("A piece of data is missing.")
        weaponsExc.initCause(e)
        throw weaponsExc
      case e: IndexOutOfBoundsException =>
        val weaponsExc = new CorruptedWeaponsFileException("Reading the weapons data failed (index out of bounds).")
        weaponsExc.initCause(e)
        throw weaponsExc
      case e: IllegalArgumentException =>
        val weaponsExc = new CorruptedWeaponsFileException("Illegal argument.")
        weaponsExc.initCause(e)
        throw weaponsExc
    }
    
  }
  
  
}