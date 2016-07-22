package org.openurp.edu.laboratory.lab.web.action;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.Semester;
import org.openurp.base.model.TimeSetting;
import org.openurp.edu.laboratory.model.LabRoomApply;
import org.openurp.edu.lesson.util.WeekTimeDigestor;
import org.openurp.edu.web.action.SemesterSupportAction;

public class ArrangeAction extends SemesterSupportAction {

  
  public String list() {
    
    Semester semester = getSemester();
    TimeSetting timeSetting = timeSettingService.getClosestTimeSetting(getProject(), semester, null);
    OqlBuilder<LabRoomApply> builder = OqlBuilder.from(LabRoomApply.class, "apply");
    builder.where("apply.project =:project and apply.semester = :semester", getProject(), semester);
    builder.orderBy(get(Order.ORDER_STR)).limit(getPageLimit());
    List<LabRoomApply> applyList = entityDao.search(builder);
    
    Map<LabRoomApply, String> times = CollectUtils.newHashMap();
    for (LabRoomApply p : applyList) {
      times.put(p,
          WeekTimeDigestor.getInstance().digest(getTextResource(), semester, timeSetting, p.getTimes()));
    }
    put("applyList",applyList);
    put("times", times);
    return forward();
  }
}
