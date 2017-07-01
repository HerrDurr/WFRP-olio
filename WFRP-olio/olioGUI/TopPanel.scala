package olioGUI

import main._
import scala.swing._
import scala.swing.Orientation._
import scala.swing.BorderPanel.Position._
import event._
import scala.math.max
import scala.math.min
import java.awt.geom.Dimension2D
import java.awt.Insets._
import javax.swing.JFileChooser
import java.io.File

class TopPanel(olioPanel: OlioPanel) extends FlowPanel {
  
  val path = this.getClass.getClassLoader.getResource("").getPath
  val olio = olioPanel.olio
  val career = olio.career
  val whFont = olioPanel.whFontBold.deriveFont(16f)
  
  
  val nameLabel = new Label(olio.name) { font = whFont }
  val raceLabel = new Label(olio.race) { font = whFont }
  val careerLabel = new Label(career.current) { font = whFont }
  
  val rcPanel = new BoxPanel(Horizontal)
  this.rcPanel.contents += (raceLabel, new Label(" "), careerLabel)
  
  val leftPanel = new BorderPanel {
    this.layout(nameLabel) = North
    //contents += new Separator(Horizontal)
    this.layout(rcPanel) = South
  }
  
  val wfPanel = new GridPanel(2,1) {
    val woundPanel = new FlowPanel() {
      val woundButton = new Button("0") { font = whFont }
      woundButton.preferredSize = new Dimension(28, 28)
      woundButton.margin = new Insets(1,1,1,1)
      val woundLabel = new Label("/ " + olio.attributes.wounds) { font = whFont }
      val descrLabel = new Label("Wounds: ") { font = whFont }
      
      contents += descrLabel
      contents += woundButton
      contents += woundLabel
      
    }
    
    
    //TODO: iffi liittyen Luck-talentiin
    val fortunePanel = new FlowPanel {
      val fortuneButton = new Button( olio.attributes.fatePoints.toString() ) { font = whFont }
      val fortuneLabel = new Label( "/ " + olio.attributes.fatePoints.toString() ) { font = whFont }
      fortuneButton.preferredSize = new Dimension(28, 28)
      fortuneButton.margin = new Insets(1,1,1,1)
      val descrLabel = new Label("Fortune: ") { font = whFont }
      
      contents += descrLabel
      contents += fortuneButton
      contents += fortuneLabel
    }
    
    contents += woundPanel
    contents += fortunePanel
  }
  
  val nextDayButton = new Button("Next Day") { font = whFont }
  this.nextDayButton.preferredSize = new Dimension(100, 60)
  
  val saveColourPanel = new BorderPanel
  val colourButton = new Button("Colour") { font = whFont }
  val saveButton = new Button("Save") { font = whFont }
  saveColourPanel.layout(colourButton) = North
  saveColourPanel.layout(saveButton) = South
  
  this.contents += (leftPanel, wfPanel, nextDayButton, saveColourPanel)
  
  
  this.listenTo(careerLabel.mouse.clicks)
  this.listenTo(nameLabel.mouse.clicks)
  this.listenTo(raceLabel.mouse.clicks)
  this.listenTo(nextDayButton)
  this.listenTo(wfPanel.woundPanel.woundButton)
  this.listenTo(wfPanel.fortunePanel.fortuneButton, colourButton, saveButton)
  
  
  
  this.reactions += {
    
    case clickEvent: ButtonClicked => {
      
      if(clickEvent.source == wfPanel.fortunePanel.fortuneButton)
      {
        olio.setFortune(max(wfPanel.fortunePanel.fortuneButton.text.toInt.-(1), 0))
        update()
      }
      
      else if(clickEvent.source == wfPanel.woundPanel.woundButton)
      {
        val w = Dialog.showInput(contents.head, "Enter current wounds", initial = "")
        w match {
          case Some(n) => {
            olio.setCurrentWounds(n.toInt)
            update()
          }
          case None =>
        }
      }
      
      else if(clickEvent.source == nextDayButton)
      {
        val wounds = olio.currentWounds
        if(wounds >= 3)
          olio.setCurrentWounds(wounds + 1)
        olio.setFortune(olio.attributes.fatePoints)
        update()
      }
      
      else if (clickEvent.source == colourButton)
      {
        val newCol = ColorChooser.showDialog(this, "Choose a colour", olio.colour).getOrElse(olio.colour)
        olio.setColour(newCol)
        olioPanel.update()
      }
      
      else if (clickEvent.source == saveButton)
      {
        val fileChooser = new JFileChooser(path + "/data/saves")
        val fileName = Dialog.showInput(this, "Enter a name for your save file", initial = olio.name)
                             .getOrElse(olio.name) + ".txt"
        fileChooser.setSelectedFile(new File(fileName))
        val save = fileChooser.showSaveDialog(this.peer)
        if (save == JFileChooser.APPROVE_OPTION) olioIO.SaverLoader.saveOlio(olio, fileChooser.getSelectedFile)
        /*val save = Dialog.showConfirmation(this,
            "Would you like to save your character/NPC?\nWARNING: This will overwrite any file with the same title as the character's/NPC's name!",
            "Save?", Dialog.Options.YesNo, Dialog.Message.Warning, null)
        if (save == Dialog.Result.Yes) olioIO.SaverLoader.saveOlio(olio)
        * 
        */
      }
      
    }
    
    
    case clickEvent: MouseClicked => {
      if(clickEvent.clicks > 1)
      {
        
        if(clickEvent.source == this.careerLabel)
        {
          val input = Dialog.showInput(this, "", initial = careerLabel.text)
          input match {
            case Some(n) => {
              career.change(n)
              this.update()
            }
            case None =>
          }
        }
        
        if(clickEvent.source == this.nameLabel) 
        {
          val input = Dialog.showInput(this, "", initial = nameLabel.text)
          input match {
            case Some(n) => {
              olio.setName(n)
              this.update()
            }
            case None =>
          }
        }
        
        if(clickEvent.source == this.raceLabel) 
        {
          val input = Dialog.showInput(this, "", initial = raceLabel.text)
          input match {
            case Some(n) => {
              olio.setRace(n)
              this.update()
            }
            case None =>
          }
        }
        
      }
    }
      
  }
  
  
  def update() = {
    val colour = olio.colour
    val colour2 = this.contrastColour(colour).darker()
    this.nameLabel.text = olio.name
    this.careerLabel.text = olio.career.current
    this.raceLabel.text = olio.race
    this.wfPanel.woundPanel.woundLabel.text = "/ " + olio.attributes.wounds.toString()
    this.wfPanel.woundPanel.woundButton.text = olio.currentWounds.toString()
    this.wfPanel.fortunePanel.fortuneLabel.text = "/ " + olio.attributes.fatePoints.toString()
    this.wfPanel.fortunePanel.fortuneButton.text = olio.fortunePoints.toString()
    this.background = colour
    this.contents.foreach(_.background = colour)
    this.contents.foreach(_.foreground = colour2)
    this.nameLabel.foreground = colour2
    this.rcPanel.background = colour
    this.rcPanel.contents.foreach(_.foreground = colour2)
    this.wfPanel.contents.foreach(_.background = colour)
    this.wfPanel.woundPanel.descrLabel.foreground = colour2
    this.wfPanel.woundPanel.woundLabel.foreground = colour2
    this.wfPanel.fortunePanel.descrLabel.foreground = colour2
    this.wfPanel.fortunePanel.fortuneLabel.foreground = colour2
  }
  
  
  def contrastColour(col: Color) = {
    val r = col.getRed
    val g = col.getGreen
    val b = col.getBlue
    val sum = r + g + b / 2
    /*
    val high = max(r, max(g, b))
    val low = min(r, min(g, b))
    val sum = high + low
    new Color(sum - r, sum - g, sum - b)
     */
    //new Color(255 - r, 255 - g, 255 - b)
    if (sum > (220)) new Color(0, 0, 0)
    else new Color(255, 255, 255)
  }
  
  
}