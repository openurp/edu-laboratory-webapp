package org.openurp.edu.laboratory.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.openurp.edu.base.model.Project;

@Entity(name="org.openurp.edu.laboratory.model.LabCenter")
public class LabCenter extends IntegerIdObject {

  private static final long serialVersionUID = 2903568632935771607L;

  @Size(max = 20)
  private String code;

  @Size(max = 100)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  @NotNull
  private java.sql.Date beginOn;

  private java.sql.Date endOn;

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

}
