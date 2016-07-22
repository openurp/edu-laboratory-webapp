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
import org.openurp.edu.laboratory.model.MediaApply;
import org.openurp.edu.lesson.model.CourseActivity;
import org.openurp.edu.lesson.model.Lesson;
import org.openurp.edu.lesson.util.CourseActivityDigestor;
import org.openurp.edu.lesson.util.WeekTimeDigestor;
import org.openurp.edu.web.action.SemesterSupportAction;

public class MediaManageAction  extends SemesterSupportAction {

  public String list() throws Exception {
    Semester semester = getSemester();
    OqlBuilder<MediaApply> builder = OqlBuilder.from(MediaApply.class, "apply");
    builder.where("apply.project =:project and apply.semester = :semester", getProject(), semester);
    builder.orderBy(get(Order.ORDER_STR)).limit(getPageLimit());
    List<MediaApply> applyList = entityDao.search(builder);

    put("applyList", applyList);
    Map<MediaApply, String> rooms = CollectUtils.newHashMap();
    MediaApplyPropertyExtractor extractor=new MediaApplyPropertyExtractor(this.getTextResource());
    for (MediaApply p : applyList) {
      rooms.put(p, (String)extractor.getPropertyValue(p, "mediaRoom"));
    }
    put("applyList", applyList);
    put("rooms", rooms);
    return forward();
  }

  @Override
  protected OqlBuilder<MediaApply> getQueryBuilder() {
    Semester semester = getSemester();
    OqlBuilder<MediaApply> builder = OqlBuilder.from(MediaApply.class, "apply");
    builder.where("apply.project =:project and apply.semester = :semester", getProject(), semester);
    builder.orderBy(get(Order.ORDER_STR));//.limit(getPageLimit());
    return builder;
  }

  protected PropertyExtractor getPropertyExtractor() {
    return new MediaApplyPropertyExtractor(this.getTextResource());
  }

  private Set<CourseActivity> getApplyableActivities(Lesson lesson) {
    if (true) return lesson.getCourseSchedule().getActivities();

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
    Long applyId = getLong("apply.id");
    MediaApply apply = entityDao.get(MediaApply.class, applyId);
    put("apply", apply);
    Lesson lesson = apply.getLesson();
    TimeSetting timeSetting = timeSettingService.getClosestTimeSetting(lesson.getProject(),
        lesson.getSemester(), lesson.getCampus());
    Set<CourseActivity> applyableActivities = getApplyableActivities(lesson);
    put("applyableActivityText", CourseActivityDigestor.getInstance().digest(null, timeSetting,
        applyableActivities, ":day :units :weeks :room"));
    return forward();
  }

}



