package org.openurp.edu.laboratory.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.openurp.edu.base.model.Project;

@Entity(name = "org.openurp.edu.laboratory.model.Software")
public class Software extends IntegerIdObject {

  private static final long serialVersionUID = -2339755547078724735L;

  @ManyToOne(fetch = FetchType.LAZY)
  private Project project;

  @Size(max = 20)
  private String code;

  @Size(max = 100)
  private String name;

  @Size(max = 20)
  private String version;

  @ManyToMany
  private List<LabRoom> rooms = CollectUtils.newArrayList();

  @ManyToMany
  private List<Os> oses = CollectUtils.newArrayList();

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

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public List<LabRoom> getRooms() {
    return rooms;
  }

  public void setRooms(List<LabRoom> rooms) {
    this.rooms = rooms;
  }

  public List<Os> getOses() {
    return oses;
  }

  public void setOses(List<Os> oses) {
    this.oses = oses;
  }

  public Project getProject() {
    return project;
  }

  public void setProject(Project project) {
    this.project = project;
  }

}
