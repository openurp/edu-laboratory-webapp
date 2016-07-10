package org.openurp.edu.laboratory.model;

import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.time.WeekTime;

@Entity(name="org.openurp.edu.laboratory.model.ExprActivity")
public class ExprActivity extends LongIdObject {

  private static final long serialVersionUID = 7827023268571081446L;

  @ManyToOne(fetch = FetchType.LAZY)
  private ExprProgram program;
  
  @Embedded
  private WeekTime time;
  
  @ManyToMany
  private List<LabRoom> rooms = CollectUtils.newArrayList();

  public ExprProgram getProgram() {
    return program;
  }

  public void setProgram(ExprProgram program) {
    this.program = program;
  }

  public WeekTime getTime() {
    return time;
  }

  public void setTime(WeekTime time) {
    this.time = time;
  }

  public List<LabRoom> getRooms() {
    return rooms;
  }

  public void setRooms(List<LabRoom> rooms) {
    this.rooms = rooms;
  }
  
  
}
