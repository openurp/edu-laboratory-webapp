package org.openurp.edu.laboratory.teacher.web.action;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Strings;
import org.openurp.base.model.Semester;
import org.openurp.base.model.TimeSetting;
import org.openurp.edu.base.model.Teacher;
import org.openurp.edu.laboratory.model.ExprProgram;
import org.openurp.edu.laboratory.model.ExprTest;
import org.openurp.edu.laboratory.model.LabRoomApply;
import org.openurp.edu.laboratory.model.Software;
import org.openurp.edu.lesson.model.Lesson;
import org.openurp.edu.lesson.util.WeekTimeDigestor;
import org.openurp.edu.web.action.TeacherProjectSupport;

public class ProgramAction extends TeacherProjectSupport {

  @Override
  public String innerIndex() {
    return forward();
  }

  private ExprProgram getProgram() {
    Teacher teacher = getLoginTeacher();
    Long programId = getLong("program.id");
    Long lessonId = getLong("lesson.id");
    ExprProgram program;
    if (null != programId) {
      program = entityDao.get(ExprProgram.class, programId);
      if (!program.getLesson().getTeachers().contains(teacher)) { throw new RuntimeException("本课程不属于您！"); }
    } else {
      program = new ExprProgram();
      Lesson lesson = entityDao.get(Lesson.class, lessonId);
      program.setLesson(lesson);
      program.setProject(lesson.getProject());
      program.setSemester(lesson.getSemester());
    }
    populate(program, "program");
    return program;
  }

  public String edit() {
    Teacher teacher = getLoginTeacher();
    ExprProgram program = getProgram();
    Long copyProgramId = getLong("copyProgram.id");
    if (!program.isPersisted() && null != copyProgramId) {
      ExprProgram copy = entityDao.get(ExprProgram.class, copyProgramId);
      program.setPeriod(copy.getPeriod());
      program.setItemCount(copy.getItemCount());
      for (ExprTest test : copy.getTests()) {
        ExprTest newTest = new ExprTest();
        newTest.setContent(test.getContent());
        newTest.setProgram(program);
        program.getTests().add(newTest);
      }
      program.getSoftwares().addAll(copy.getSoftwares());
    }
    put("program", program);
    OqlBuilder<ExprProgram> builder = OqlBuilder.from(ExprProgram.class, "ep");
    builder.where(":teacher in elements(ep.lesson.teachers)", teacher);
    builder.where("ep.lesson.course = :course", program.getLesson().getCourse());
    builder.where("ep.project = :project and ep.semester=:semester", program.getProject(),
        program.getSemester());
    List<ExprProgram> sameCoursePrograms = entityDao.search(builder);
    put("sameCoursePrograms", sameCoursePrograms);

    OqlBuilder<Software> sbuilder = OqlBuilder.from(Software.class, "s");
    // sbuilder.where("s.project=:project", program.getProject());
    sbuilder.orderBy("s.name");
    put("softwares", entityDao.search(sbuilder));
    return forward();
  }

  public String save() {
    ExprProgram program = getProgram();
    int count = program.getItemCount();
    List<ExprTest> removed = CollectUtils.newArrayList();
    for (ExprTest t : program.getTests()) {
      String testContent = get("test_id_" + t.getId());
      if (Strings.isNotBlank(testContent)) {
        t.setContent(testContent);
      } else {
        removed.add(t);
      }
    }
    program.getTests().removeAll(removed);

    for (int i = 0; i < count; i++) {
      String testContent = get("test_idx_" + (i + 1));
      if (Strings.isNotBlank(testContent)) {
        ExprTest newone = new ExprTest();
        newone.setContent(testContent);
        newone.setProgram(program);
        program.getTests().add(newone);
      }
    }
    program.setItemCount(program.getTests().size());

    String[] softwareIds = getAll("software.id", String.class);
    program.getSoftwares().clear();
    if (softwareIds != null && softwareIds.length > 0) {
      program.getSoftwares().addAll(entityDao.get(Software.class, Strings.transformToInt(softwareIds)));
    }
    entityDao.saveOrUpdate(program);
    program.setUpdatedAt(new java.util.Date());
    return redirect("lessons", "info.save.success");
  }

  public String lessons() {
    Teacher teacher = getLoginTeacher();
    Semester semester = getSemester();
    TimeSetting timeSetting = timeSettingService.getClosestTimeSetting(teacher.getProject(), semester, null);
    OqlBuilder<Lesson> builder = OqlBuilder.from(Lesson.class, "l");
    builder.where(":teacher in elements(l.teachers)", teacher);
    builder.where("l.project =:project and l.semester = :semester", teacher.getProject(), semester);
    List<Lesson> lessonList = entityDao.search(builder);

    Map<Lesson, ExprProgram> programMap = CollectUtils.newHashMap();
    Map<Lesson, LabRoomApply> applyMap = CollectUtils.newHashMap();
    Map<LabRoomApply, String> times = CollectUtils.newHashMap();
    if (!lessonList.isEmpty()) {
      OqlBuilder<ExprProgram> pbuilder = OqlBuilder.from(ExprProgram.class, "ep");
      pbuilder.where("ep.lesson in(:lessons)", lessonList);
      List<ExprProgram> programs = entityDao.search(pbuilder);
      for (ExprProgram p : programs) {
        programMap.put(p.getLesson(), p);
      }

      OqlBuilder<LabRoomApply> abuilder = OqlBuilder.from(LabRoomApply.class, "a");
      abuilder.where("a.lesson in(:lessons)", lessonList);
      List<LabRoomApply> applies = entityDao.search(abuilder);
      for (LabRoomApply p : applies) {
        applyMap.put(p.getLesson(), p);
        times.put(p,
            WeekTimeDigestor.getInstance().digest(getTextResource(), semester, timeSetting, p.getTimes()));
      }
    }
    put("lessonList", lessonList);
    put("programMap", programMap);
    put("applyMap", applyMap);
    put("timeMap", times);
    return forward();
  }
}
