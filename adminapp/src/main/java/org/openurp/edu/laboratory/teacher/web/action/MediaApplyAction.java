package org.openurp.edu.laboratory.teacher.web.action;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.Semester;
import org.openurp.base.model.TimeSetting;
import org.openurp.edu.base.model.AuditState;
import org.openurp.edu.base.model.Classroom;
import org.openurp.edu.base.model.Teacher;
import org.openurp.edu.laboratory.model.MediaApply;
import org.openurp.edu.lesson.model.CourseActivity;
import org.openurp.edu.lesson.model.Lesson;
import org.openurp.edu.lesson.util.CourseActivityDigestor;
import org.openurp.edu.web.action.TeacherProjectSupport;

public class MediaApplyAction extends TeacherProjectSupport {

  @Override
  public String innerIndex() {
    return forward();
  }

  private MediaApply getMediaApply() {
    Teacher teacher = getLoginTeacher();
    Long applyId = getLong("mediaApply.id");
    Long lessonId = getLong("lesson.id");
    MediaApply apply;
    if (null != applyId) {
      apply = entityDao.get(MediaApply.class, applyId);
      if (!apply.getLesson().getTeachers().contains(teacher)) { throw new RuntimeException("本课程不属于您！"); }
    } else {
      apply = new MediaApply();
      Lesson lesson = entityDao.get(Lesson.class, lessonId);
      apply.setLesson(lesson);
      apply.setProject(lesson.getProject());
      apply.setSemester(lesson.getSemester());
    }
    populate(apply, "mediaApply");
    return apply;
  }

  private String getTelphone(Teacher teacher, Semester semester) {
    OqlBuilder<String> builder = OqlBuilder.from(MediaApply.class.getName(), "apply");
    builder.where("apply.project = :project", teacher.getProject());
    builder.where("apply.semester = :semester", semester);
    builder.where(":teacher in elements(apply.lesson.teachers)", teacher);
    builder.select("distinct apply.tel");
    List<String> tels = entityDao.search(builder);
    if (tels.isEmpty()) return null;
    else return tels.get(0);

  }

  public String edit() {
    Teacher teacher = getLoginTeacher();
    MediaApply apply = getMediaApply();
    put("mediaApply", apply);
    Long applyId = getLong("mediaApply.id");
    Lesson lesson = null;
    if (null != applyId) {
      apply = entityDao.get(MediaApply.class, applyId);
      lesson = apply.getLesson();
    } else {
      Long lessonId = getLong("lesson.id");
      lesson = entityDao.get(Lesson.class, lessonId);
      apply = new MediaApply();
      apply.setLesson(lesson);
      apply.setTel(getTelphone(teacher, lesson.getSemester()));
    }
    if (!lesson.getTeachers().contains(teacher)) { return forwardError("不是你的课程"); }
    TimeSetting timeSetting = timeSettingService.getClosestTimeSetting(lesson.getProject(),
        lesson.getSemester(), lesson.getCampus());
    Set<CourseActivity> applyableActivities = getApplyableActivities(lesson);
    put("applyableActivityText", CourseActivityDigestor.getInstance().digest(null, timeSetting,
        applyableActivities, ":day :units :weeks :room"));
    return forward();
  }

  private Set<CourseActivity> getApplyableActivities(Lesson lesson) {
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

  public String save() {
    Teacher teacher = getLoginTeacher();
    MediaApply apply = getMediaApply();
    apply.setBorrower(teacher.getUser());
    apply.setUpdatedAt(new java.util.Date());
    apply.setState(AuditState.SUBMITTED);
    entityDao.saveOrUpdate(apply);
    return redirect("lessons", "info.save.success");
  }

  public String lessons() {
    Teacher teacher = getLoginTeacher();
    Semester semester = getSemester();
    OqlBuilder<Lesson> builder = OqlBuilder.from(Lesson.class, "l");
    builder.where(":teacher in elements(l.teachers)", teacher);
    builder.where("l.project =:project and l.semester = :semester", teacher.getProject(), semester);
    List<Lesson> lessonList = entityDao.search(builder);

    Map<Lesson, MediaApply> applyMap = CollectUtils.newHashMap();
    if (!lessonList.isEmpty()) {

      OqlBuilder<MediaApply> abuilder = OqlBuilder.from(MediaApply.class, "a");
      abuilder.where("a.lesson in(:lessons)", lessonList);
      List<MediaApply> applies = entityDao.search(abuilder);
      for (MediaApply p : applies) {
        applyMap.put(p.getLesson(), p);
      }
    }
    put("lessonList", lessonList);
    put("applyMap", applyMap);
    return forward();
  }

}
