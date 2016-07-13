package org.openurp.edu.laboratory.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.openurp.edu.base.model.Project;

@Entity(name = "org.openurp.edu.laboratory.model.Lab")
public class Lab extends IntegerIdObject {

  private static final long serialVersionUID = 1594405910754064329L;

  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  @Size(max = 20)
  private String code;

  @Size(max = 50)
  private String name;

  @NotNull
  private java.sql.Date beginOn;

  private java.sql.Date endOn;

  @ManyToOne(fetch = FetchType.LAZY)
  private LabCenter center;

  @Size(max = 100)
  private String fundSource;

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

  public LabCenter getCenter() {
    return center;
  }

  public void setCenter(LabCenter center) {
    this.center = center;
  }

  public String getFundSource() {
    return fundSource;
  }

  public void setFundSource(String fundSource) {
    this.fundSource = fundSource;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

}
