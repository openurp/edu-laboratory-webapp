package org.openurp.edu.laboratory.model;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.time.WeekTime;
import org.openurp.base.model.Semester;
import org.openurp.base.model.User;
import org.openurp.edu.base.model.AuditState;
import org.openurp.edu.base.model.Project;
import org.openurp.edu.lesson.model.Lesson;

@Entity(name="org.openurp.edu.laboratory.model.LabRoomApply")
public class LabRoomApply  extends LongIdObject {

  private static final long serialVersionUID = -3018564276085266559L;

  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private User borrower;
  
  @Size(max = 15)
  private String tel;
  
  @ManyToOne(fetch = FetchType.LAZY)
  private Lesson lesson;
  
  @Size(max = 100)
  private String activity;
  
  private int attendance;
  
  @Size(max = 50)
  private String audience;
  
  @ManyToMany
  private List<Software> softwares  = CollectUtils.newArrayList();
  
  @Embedded
  private WeekTime time;
  
  @NotNull
  private java.util.Date updatedAt;
  
  @NotNull
  @Enumerated(value = EnumType.ORDINAL)
  private AuditState state;

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

  public String getActivity() {
    return activity;
  }

  public void setActivity(String activity) {
    this.activity = activity;
  }

  public int getAttendance() {
    return attendance;
  }

  public void setAttendance(int attendance) {
    this.attendance = attendance;
  }

  public String getAudience() {
    return audience;
  }

  public void setAudience(String audience) {
    this.audience = audience;
  }

  public List<Software> getSoftwares() {
    return softwares;
  }

  public void setSoftwares(List<Software> softwares) {
    this.softwares = softwares;
  }

  public WeekTime getTime() {
    return time;
  }

  public void setTime(WeekTime time) {
    this.time = time;
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
  
}
