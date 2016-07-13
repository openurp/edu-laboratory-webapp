package org.openurp.edu.laboratory.lab.web.action;

import java.util.List;

import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.Semester;
import org.openurp.edu.laboratory.model.LabRoomApply;
import org.openurp.edu.web.action.SemesterSupportAction;

public class ManageAction extends SemesterSupportAction {

  public String list() {
    Semester semester = getSemester();
    OqlBuilder<LabRoomApply> builder = OqlBuilder.from(LabRoomApply.class, "l");
    builder.where("l.project =:project and l.semester = :semester", getProject(), semester);
    builder.limit(getPageLimit());
    List<LabRoomApply> applyList = entityDao.search(builder);
    put("applyList",applyList);
    return forward();
    
  
  }
  
  
  
  
}
