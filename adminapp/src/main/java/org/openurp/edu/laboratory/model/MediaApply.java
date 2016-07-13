package org.openurp.edu.laboratory.model;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.openurp.base.model.Semester;
import org.openurp.base.model.User;
import org.openurp.edu.base.model.AuditState;
import org.openurp.edu.base.model.Project;
import org.openurp.edu.lesson.model.Lesson;

@Entity(name="org.openurp.edu.laboratory.model.MediaApply")
public class MediaApply extends LongIdObject {

  private static final long serialVersionUID = -7122389209639950634L;

  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  public Project getProject() {
    return project;
  }


  public void setProject(Project project) {
    this.project = project;
  }


  public Semester getSemester() {
    return semester;
  }


  public void setSemester(Semester semester) {
    this.semester = semester;
  }


  public User getBorrower() {
    return borrower;
  }


  public void setBorrower(User borrower) {
    this.borrower = borrower;
  }


  public String getTel() {
    return tel;
  }


  public void setTel(String tel) {
    this.tel = tel;
  }


  public Lesson getLesson() {
    return lesson;
  }


  public void setLesson(Lesson lesson) {
    this.lesson = lesson;
  }


  public String getSoftware() {
    return software;
  }


  public void setSoftware(String software) {
    this.software = software;
  }


  public java.util.Date getUpdatedAt() {
    return updatedAt;
  }


  public void setUpdatedAt(java.util.Date updatedAt) {
    this.updatedAt = updatedAt;
  }


  public AuditState getState() {
    return state;
  }


  public void setState(AuditState state) {
    this.state = state;
  }


  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  @ManyToOne(fetch = FetchType.LAZY)
  private User borrower;

  @Size(max = 15)
  private String tel;

  @ManyToOne(fetch = FetchType.LAZY)
  private Lesson lesson;



  @Size(max = 300)
  private String software;


  @NotNull
  private java.util.Date updatedAt;


  @NotNull
  @Enumerated(value = EnumType.ORDINAL)
  private AuditState state;

  
}
