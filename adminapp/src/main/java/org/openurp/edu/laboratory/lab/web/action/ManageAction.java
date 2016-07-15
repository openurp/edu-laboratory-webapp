package org.openurp.edu.laboratory.lab.web.action;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.Semester;
import org.openurp.base.model.TimeSetting;
import org.openurp.edu.base.model.Classroom;
import org.openurp.edu.laboratory.model.LabRoomApply;
import org.openurp.edu.lesson.model.CourseActivity;
import org.openurp.edu.lesson.model.Lesson;
import org.openurp.edu.lesson.util.CourseActivityDigestor;
import org.openurp.edu.lesson.util.WeekTimeDigestor;
import org.openurp.edu.web.action.SemesterSupportAction;

public class ManageAction extends SemesterSupportAction {

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
  
  private Set<CourseActivity> getApplyableActivities(Lesson lesson) {
    if(true)return lesson.getCourseSchedule().getActivities();
    
    Set<CourseActivity> activities = lesson.getCourseSchedule().getActivities();
    Set<CourseActivity> applyableActivities = CollectUtils.newHashSet();
    for (CourseActivity ca : activities) {
      if (ca.getRooms().isEmpty()) {
        applyableActivities.add(ca);
      } else {
        for (Classroom cl : ca.getRooms()) {
          if (cl.getRoom() == null) {
            applyableActivities.add(ca);
          }
        }
      }
    }
    return applyableActivities;
  }
  
 public String info() {
   Long applyId=getLong("apply.id");
   LabRoomApply apply = entityDao.get(LabRoomApply.class, applyId);
   put("apply", apply);
   Lesson lesson=apply.getLesson();
   TimeSetting timeSetting = timeSettingService.getClosestTimeSetting(lesson.getProject(),
       lesson.getSemester(), lesson.getCampus());
   Set<CourseActivity> applyableActivities = getApplyableActivities(lesson);
   put("applyableActivityText",
       CourseActivityDigestor.getInstance().digest(null, timeSetting, applyableActivities,
           ":day :units :weeks :room"));
   String time=WeekTimeDigestor.getInstance().digest(getTextResource(), lesson.getSemester(), timeSetting, apply.getTimes());
   put("time", time);
   return forward();
 }
  
  
}
