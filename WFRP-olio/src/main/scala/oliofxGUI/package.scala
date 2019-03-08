import scalafxml.core.{FXMLLoader, DependenciesByType}
import olioIO.SchemaWFRP.Olio
import javafx.{scene => jfxs}
import javafx.scene.{Parent => JFXParent}


package object oliofxGUI {
  
  private lazy val editOlioLoader = new FXMLLoader(getClass.getResource("editOlioWFRP.fxml"), new DependenciesByType(Map())) 
  
  /*def testEditItem(): Unit = {
    //println(path)
    import Item._
    val item = Item.cache.getRows.find{ cR : TStorageRow[Item] => cR.data.id == Id(1) }.map(_.data).getOrElse(Item.createNew) //byId(Item.Id(1)).getOrElse(Item.createNew)
    loader.load()
    val root = loader.getRoot[jfxs.Parent]
    val ctrlr: EditItemInterface = loader.getController[EditItemInterface]
    ctrlr.editItem(item)
    val stage: jStage = new jStage {
      ctrlr.setStage(new Stage(this))
      setTitle("Edit Item")
      setScene(new Scene(root))
      show()
    }
    
  }*/
  //sfdasfdsa
  def editOlio(olio: Olio): JFXParent = {
    editOlioLoader.load()
    val root = editOlioLoader.getRoot[jfxs.Parent]
    val ctrlr: EditOlioInterface = editOlioLoader.getController[EditOlioInterface]
    ctrlr.editOlio(olio)
    root
  }
  
  
}