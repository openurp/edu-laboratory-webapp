package org.openurp.edu.laboratory.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.openurp.base.model.Semester;
import org.openurp.edu.base.model.Project;
import org.openurp.edu.lesson.model.Lesson;

@Entity(name = "org.openurp.edu.laboratory.model.ExprProgram")
public class ExprProgram extends LongIdObject {

  private static final long serialVersionUID = 7475077451220860287L;

  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  @ManyToOne(fetch = FetchType.LAZY)
  private Semester semester;

  @ManyToOne(fetch = FetchType.LAZY)
  private Lesson lesson;

  private int period;
  private int itemCount;

  @ManyToMany
  private List<Software> softwares  = CollectUtils.newArrayList();
  
  @OneToMany(mappedBy = "program", orphanRemoval = true)
  @Cascade(CascadeType.ALL)
  private List<ExprTest> tests = CollectUtils.newArrayList();

  private java.util.Date updatedAt;

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

  public Lesson getLesson() {
    return lesson;
  }

  public void setLesson(Lesson lesson) {
    this.lesson = lesson;
  }

  public int getPeriod() {
    return period;
  }

  public void setPeriod(int period) {
    this.period = period;
  }

  public int getItemCount() {
    return itemCount;
  }

  public void setItemCount(int itemCount) {
    this.itemCount = itemCount;
  }

  public List<ExprTest> getTests() {
    return tests;
  }

  public void setTests(List<ExprTest> tests) {
    this.tests = tests;
  }

  public java.util.Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(java.util.Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public List<Software> getSoftwares() {
    return softwares;
  }

  public void setSoftwares(List<Software> softwares) {
    this.softwares = softwares;
  }

  
}
