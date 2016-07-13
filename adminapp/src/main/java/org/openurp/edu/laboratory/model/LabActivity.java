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

@Entity(name = "org.openurp.edu.laboratory.model.LabActivity")
public class LabActivity extends LongIdObject {

  private static final long serialVersionUID = 7827023268571081446L;

  @ManyToOne(fetch = FetchType.LAZY)
  private LabRoomApply apply;

  @Embedded
  private WeekTime time;

  @ManyToMany
  private List<LabRoom> rooms = CollectUtils.newArrayList();

  public LabRoomApply getApply() {
    return apply;
  }

  public void setApply(LabRoomApply apply) {
    this.apply = apply;
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
