package main

import scala.collection.mutable.Buffer

class Group {
  
  private var isPlayerGroup = false
  
  private var groupMembers: Buffer[Olio] = Buffer()
  
  private var groupName = ""
  
  def isPlayerParty = this.isPlayerGroup
  
  def setPlayerParty(yes: Boolean) = this.isPlayerGroup = yes
  
  def members = this.groupMembers.toVector
  
  def addMember(member: Olio) = {
    this.groupMembers += member
    this.groupMembers.sortBy(_.name)
  }
  
  def removeMember(member: Olio) = {
    this.groupMembers = this.groupMembers.filterNot(_ == member)
  }
  
  def name = this.groupName
  
  def changeName(newName: String) = this.groupName = newName 
  
}