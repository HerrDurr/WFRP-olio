package olioIO

import SlickSchemaWFRP._
import slick.driver.SQLiteDriver.api._

object SlickWFRPDataHelper {
  
  
  
  
  implicit class OlioExtensions[C[_]](aQuery: Query[TableOlio, OlioRow, C]) {
    
    /**
     * Joins Olio with its Attributes
     */
    def withOlioAttributes /*: Query[(TableOlio, TableOlioAttributes), (OlioRow, TableOlioAttributes.TableElementType), C] */ =
    {
      aQuery.join(tableOlioAttributes).on(_.olioId === _.olioId)
    }
    
    def withAttributes =
    {
      aQuery.withOlioAttributes.join(tableAttribute).on(_._2.attributeId === _.attrId)
    }
    
    /**
     * Joins Olio with its Skills
     */
    def withOlioSkills =
    {
      aQuery.join(tableOlioSkills).on(_.olioId === _.olioId)
    }
    
  }
  
  
  implicit class ItemExtensions[C[_]](aQuery: Query[TableItem, ItemRow, C]) {
    
    def withMeleeWeapon =
    {
      aQuery.join(tableWeaponMelee).on(_.itemId === _.weaponId)
    }
    
    def withRangedWeapon =
    {
      aQuery.join(tableWeaponRanged).on(_.itemId === _.weaponId)
    }
    
  }
     
  implicit class MeleeWeaponExtensions[C[_]](aQuery: Query[TableWeaponMelee, WeaponMeleeRow, C]) {
    
    def withItem =
    {
      aQuery.join(tableItem).on(_.weaponId === _.itemId)
    }
    
  }
  
  implicit class RangedWeaponExtensions[C[_]](aQuery: Query[TableWeaponRanged, WeaponRangedRow, C]) {
    
    def withItem =
    {
      aQuery.join(tableItem).on(_.weaponId === _.itemId)
    }
    
  }
  
  
}