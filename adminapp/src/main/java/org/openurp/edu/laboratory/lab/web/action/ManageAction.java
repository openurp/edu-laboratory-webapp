package org.openurp.edu.laboratory.lab.web.action;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.transfer.exporter.PropertyExtractor;
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
    put("applyList", applyList);
    put("times", times);
    return forward();
  }

  public String labLessons() {
    OqlBuilder<Lesson> builder = OqlBuilder.from(Lesson.class, "l");
    builder.where("l.project =:project and l.semester =:semester", getProject(), getSemester());
    builder
        .where(
            "exists (from l.courseSchedule.activities as activity join activity.rooms as room where room.name like:name)",
            "%机房%");
    builder.where("not exists ( from " + LabRoomApply.class.getName() + " apply where apply.lesson = l)");
    builder.orderBy(get(Order.ORDER_STR)).limit(getPageLimit());
    List<Lesson> lessons = entityDao.search(builder);
    put("lessons", lessons);
    CourseActivityDigestor digestor = CourseActivityDigestor.getInstance().setDelimeter("<br>");
    Map<String, String> arrangeInfo = CollectUtils.newHashMap();
    TimeSetting timeSetting = timeSettingService.getClosestTimeSetting(getProject(), getSemester(), null);
    for (Lesson oneTask : lessons) {
      arrangeInfo.put(oneTask.getId().toString(),
          digestor.digest(getTextResource(), timeSetting, oneTask, ":day :units :weeks :room"));
    }
    put("arrangeInfo", arrangeInfo);
    return forward();
  }

  @Override
  protected OqlBuilder<LabRoomApply> getQueryBuilder() {
    Semester semester = getSemester();
    OqlBuilder<LabRoomApply> builder = OqlBuilder.from(LabRoomApply.class, "apply");
    builder.where("apply.project =:project and apply.semester = :semester", getProject(), semester);
    builder.orderBy(get(Order.ORDER_STR)).limit(getPageLimit());
    return builder;
  }

  protected PropertyExtractor getPropertyExtractor() {
    Semester semester = getSemester();
    TimeSetting timeSetting = timeSettingService.getClosestTimeSetting(getProject(), semester, null);
    return new ApplyPropertyExtractor(entityDao, this.getTextResource(), semester, timeSetting);
  }

  public String info() {
    Long applyId = getLong("apply.id");
    LabRoomApply apply = entityDao.get(LabRoomApply.class, applyId);
    put("apply", apply);
    Lesson lesson = apply.getLesson();
    TimeSetting timeSetting = timeSettingService.getClosestTimeSetting(lesson.getProject(),
        lesson.getSemester(), lesson.getCampus());
    Set<CourseActivity> applyableActivities = lesson.getCourseSchedule().getActivities();
    put("applyableActivityText",
        CourseActivityDigestor.getInstance().digest(null, timeSetting, applyableActivities,
            ":day :units :weeks :room"));
    String time = WeekTimeDigestor.getInstance().digest(getTextResource(), lesson.getSemester(), timeSetting,
        apply.getTimes());
    put("time", time);
    return forward();
  }

}
