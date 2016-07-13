package org.openurp.edu.laboratory.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.openurp.edu.base.model.Project;

@Entity(name="org.openurp.edu.laboratory.model.Os")
public class Os extends IntegerIdObject {

  private static final long serialVersionUID = 2146837948077725978L;

  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  @Size(max = 50)
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

}
