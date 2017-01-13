package org.openurp.edu.laboratory.teacher.web.action;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.time.WeekState;
import org.beangle.commons.lang.time.WeekTime;
import org.beangle.commons.lang.time.WeekTimes;
import org.beangle.struts2.convention.route.Action;
import org.openurp.base.model.Semester;
import org.openurp.base.model.TimeSetting;
import org.openurp.base.util.WeekStates;
import org.openurp.base.util.WeekTimeBuilder;
import org.openurp.edu.base.model.AuditState;
import org.openurp.edu.base.model.Classroom;
import org.openurp.edu.base.model.Teacher;
import org.openurp.edu.laboratory.model.ExprProgram;
import org.openurp.edu.laboratory.model.LabRoomApply;
import org.openurp.edu.laboratory.model.Software;
import org.openurp.edu.lesson.model.CourseActivity;
import org.openurp.edu.lesson.model.Lesson;
import org.openurp.edu.lesson.util.CourseActivityDigestor;
import org.openurp.edu.web.action.TeacherProjectSupport;

public class ApplyAction extends TeacherProjectSupport {

  @Override
  public String innerIndex() {

    return null;
  }

  public String edit() {
    return forward();
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

  private List<WeekTime> toRelativeTimes(Lesson lesson, Collection<CourseActivity> activities) {
    Semester semester = lesson.getSemester();
    List<WeekTime> times = CollectUtils.newArrayList();
    for (CourseActivity ca : activities) {
      WeekTime time = (WeekTime) ca.getTime().clone();
      int weekOffset = WeekTimeBuilder.getOffset(semester, time.getWeekday());
      int reverseOffset = WeekTimeBuilder.getReverseOffset(semester, time.getWeekday());
      time.setStartOn(WeekTimeBuilder.getStartOn(semester, time.getWeekday()));
      if (time.getStartOn().getYear() == semester.getBeginOn().getYear()) {
        time.setWeekstate(new WeekState(time.getWeekstate().value >> weekOffset));
      } else {
        time.setWeekstate(new WeekState(time.getWeekstate().value << reverseOffset));
      }
      times.add(time);
    }
    times = WeekTimes.mergeTimes(times);
    return times;
  }

  public String applyByLesson() {
    Teacher teacher = getLoginTeacher();
    Long applyId = getLong("apply.id");
    Lesson lesson = null;
    LabRoomApply apply = null;
    if (null != applyId) {
      apply = entityDao.get(LabRoomApply.class, applyId);
      lesson = apply.getLesson();
    } else {
      Long lessonId = getLong("lesson.id");
      lesson = entityDao.get(Lesson.class, lessonId);
      apply = new LabRoomApply();
      apply.setLesson(lesson);

      OqlBuilder<ExprProgram> pbuilder = OqlBuilder.from(ExprProgram.class, "p");
      pbuilder.where("p.lesson=:lesson", lesson);
      List<ExprProgram> programs = entityDao.search(pbuilder);
      apply.getSoftwares().addAll(programs.get(0).getSoftwares());
      apply.setTel(getTelphone(teacher, lesson.getSemester()));

    }
    if (!lesson.getTeachers().contains(teacher)) { return forwardError("不是你的课程"); }

    TimeSetting timeSetting = timeSettingService.getClosestTimeSetting(lesson.getProject(),
        lesson.getSemester(), lesson.getCampus());
    put("timeSetting", timeSetting);

    Set<CourseActivity> applyableActivities = getApplyableActivities(lesson);
    put("applyableActivityText", CourseActivityDigestor.getInstance().digest(null, timeSetting,
        applyableActivities, ":day :units :weeks :room"));
    if (apply.isTransient()) {
      for (CourseActivity ca : applyableActivities) {
        if (ca.getRooms().isEmpty()) {
          apply.getTimes().add(ca.getTime());
        } else {
          for (Classroom cl : ca.getRooms()) {
            if (cl.getRoom() == null) {
              apply.getTimes().add(ca.getTime());
            }
          }
        }
      }
    }

    Set<java.util.Date> existedDates = CollectUtils.newHashSet();
    for (WeekTime s : apply.getTimes()) {
      existedDates.addAll(s.getDates());
    }
    put("existedDates", existedDates);
    List<WeekTime> times = toRelativeTimes(lesson, applyableActivities);
    put("times", times);

    OqlBuilder<Software> sbuilder = OqlBuilder.from(Software.class, "s");
    // sbuilder.where("s.project=:project", lesson.getProject());
    sbuilder.orderBy("s.name");
    put("softwares", entityDao.search(sbuilder));

    put("apply", apply);
    return forward("lessonForm");
  }

  public String saveByLesson() {
    Teacher teacher = getLoginTeacher();
    Long applyId = getLong("apply.id");
    Lesson lesson = null;
    LabRoomApply apply = null;
    if (null != applyId) {
      apply = entityDao.get(LabRoomApply.class, applyId);
      lesson = apply.getLesson();
    } else {
      Long lessonId = getLong("lesson.id");
      lesson = entityDao.get(Lesson.class, lessonId);
      apply = new LabRoomApply();
    }
    populate(apply, "apply");
    apply.setLesson(lesson);
    apply.setProject(lesson.getProject());
    apply.setSemester(lesson.getSemester());
    apply.setBorrower(teacher.getUser());
    apply.setActivity(lesson.getNo() + " " + lesson.getCourse().getName());
    apply.setAudience(lesson.getTeachClass().getName());
    apply.setAttendance(lesson.getTeachClass().getStdCount());
    apply.setState(AuditState.SUBMITTED);
    apply.setUpdatedAt(new java.util.Date());
    apply.getTimes().clear();
    if (!lesson.getTeachers().contains(teacher)) { return forwardError("不是你的课程"); }
    Set<CourseActivity> applyableActivities = getApplyableActivities(lesson);
    List<WeekTime> times = toRelativeTimes(lesson, applyableActivities);
    List<WeekTime> selected = CollectUtils.newArrayList();
    for (WeekTime time : times) {
      String name = "weekId_" + time.getWeekday().getId() + "_" + time.getBeginAt() + "_" + time.getEndAt();
      String[] weeks = getAll(name, String.class);
      if (null != weeks && weeks.length > 0) {
        time.setWeekstate(WeekStates.build(Strings.join(weeks)));
        selected.add(time);
      }
    }
    if (!selected.isEmpty()) {
      for (WeekTime wt : selected) {
        WeekTimeBuilder builder = WeekTimeBuilder.on(lesson.getSemester());
        List<WeekTime> rs = builder.build(wt.getWeekday(), wt.getWeekstate().getWeekList());
        for (WeekTime wtrs : rs) {
          wtrs.setBeginAt(wt.getBeginAt());
          wtrs.setEndAt(wt.getEndAt());
        }
        apply.getTimes().addAll(rs);
      }
    }
    String[] softwareIds = getAll("software.id", String.class);
    apply.getSoftwares().clear();
    if (softwareIds != null && softwareIds.length > 0) {
      apply.getSoftwares().addAll(entityDao.get(Software.class, Strings.transformToInt(softwareIds)));
    }
    if (!apply.getTimes().isEmpty()) {
      entityDao.saveOrUpdate(apply);
    }
    return redirect(new Action(ProgramAction.class, "lessons"), "info.save.success", null);
  }

  private String getTelphone(Teacher teacher, Semester semester) {
    OqlBuilder<String> builder = OqlBuilder.from(LabRoomApply.class.getName(), "apply");
    builder.where("apply.project =:project", teacher.getProject());
    builder.where("apply.semester = :semester", semester);
    builder.where(":teacher in elements(apply.lesson.teachers)", teacher);
    builder.select("distinct apply.tel");
    List<String> tels = entityDao.search(builder);
    if (tels.isEmpty()) return null;
    else return tels.get(0);

  }

}
