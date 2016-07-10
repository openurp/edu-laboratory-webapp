package org.openurp.edu.laboratory.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.openurp.base.model.Room;
import org.openurp.edu.base.model.Project;
import org.openurp.edu.laboratory.model.code.LabRoomType;

@Entity(name = "org.openurp.edu.laboratory.model.LabRoom")
public class LabRoom extends IntegerIdObject {
  private static final long serialVersionUID = 2302049820382438595L;

  @Size(max = 20)
  private String code;
  
  @Size(max = 50)
  private String name;
  
  @NotNull
  private java.sql.Date beginOn;
  
  private java.sql.Date endOn;

  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  private Room room;

  @ManyToOne(fetch = FetchType.LAZY)
  private LabRoomType roomType;

  private int capacity;
  private Float area;

  @ManyToOne(fetch = FetchType.LAZY)
  private Lab lab;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public java.sql.Date getBeginOn() {
    return beginOn;
  }

  public void setBeginOn(java.sql.Date beginOn) {
    this.beginOn = beginOn;
  }

  public java.sql.Date getEndOn() {
    return endOn;
  }

  public void setEndOn(java.sql.Date endOn) {
    this.endOn = endOn;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public Room getRoom() {
    return room;
  }

  public void setRoom(Room room) {
    this.room = room;
  }

  public LabRoomType getRoomType() {
    return roomType;
  }

  public void setRoomType(LabRoomType roomType) {
    this.roomType = roomType;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public Float getArea() {
    return area;
  }

  public void setArea(Float area) {
    this.area = area;
  }

  public Lab getLab() {
    return lab;
  }

  public void setLab(Lab lab) {
    this.lab = lab;
  }

}
