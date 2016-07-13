package org.openurp.edu.laboratory.teacher.web.action

import org.beangle.commons.lang.Numbers
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.Semester
import org.openurp.edu.base.model.Teacher
import org.openurp.edu.lesson.model.Lesson
import org.openurp.platform.api.security.Securities
import org.beangle.webmvc.api.annotation.param
import org.openurp.base.model.School
import org.openurp.base.model.School
import org.openurp.base.model.Semester
import org.openurp.edu.lesson.model.Lesson
import org.openurp.edu.laboratory.model.ExprProgram
import org.openurp.edu.laboratory.model.ExprTest
import org.openurp.base.model.School
import org.openurp.base.model.Semester
import org.openurp.edu.lesson.model.Lesson

class ProgramAction extends RestfulAction[ExprProgram] {

  def getTeacher(): Teacher = {
    val teachers = entityDao.search(OqlBuilder.from(classOf[Teacher], "s").where("s.user.code=:code", Securities.user).where("s.project.id=:id", 5))
    if (teachers.isEmpty) {
      throw new RuntimeException("Cannot find teachers with code " + Securities.user)
    } else {
      teachers.head
    }
  }

  override def indexSetting(): Unit = {
    val school = entityDao.get(classOf[School], 16404)
    val semesters = entityDao.search(OqlBuilder.from(classOf[Semester], "semester").where("semester.calendar.school=:school", school))
    put("semesters", semesters)
    val semesterQuery = OqlBuilder.from(classOf[Semester], "semester").where("semester.code=:code", "2016-1")
    semesterQuery.where("semester.calendar.school=:school", school)
    put("currentSemester", entityDao.search(semesterQuery).head)
    super.indexSetting()
  }
  /**
   * 教师课程列表
   * //
   */
  override def search(): String = {
    val school = entityDao.get(classOf[School], 16404)
    val semesterQuery = OqlBuilder.from(classOf[Semester], "semester").where("semester.code=:code", "2016-1")
    semesterQuery.where("semester.calendar.school=:school", school)
    val semesterId = getInt("semester.id").getOrElse(entityDao.search(semesterQuery).head.id)
    val semester = entityDao.get(classOf[Semester], semesterId);
    val teacher = getTeacher()
    if (teacher == null) { forward("error.teacher.teaNo.needed") }
    // 得到该教师的所有教学任务
    val lessonQuery = OqlBuilder.from(classOf[Lesson], "lesson");
    lessonQuery.limit(getPageLimit)
    lessonQuery.where("lesson.semester.id=:semesterId", semesterId);
    if (teacher != null) {
      lessonQuery.join("lesson.teachers", "teacher")
      lessonQuery.where("teacher =:teacher", teacher);
    }
    val lessons = entityDao.search(lessonQuery);
    put("lessons", lessons);
    val labs = entityDao.getAll(classOf[ExprProgram])
    val labMap = labs.map(x => (x.lesson, x)).toMap
    put("labMap", labMap)
    forward();
  }

  def add(): String = {
    val lessonId = longId("lesson")
    val lesson = entityDao.get(classOf[Lesson], lessonId)
    var labs = entityDao.search(OqlBuilder.from(classOf[ExprProgram], "lab").where("lab.lesson=:lesson", lesson))
    if (labs.isEmpty) { put("lab", null) }
    else put("lab", labs.head)
    put("lesson", lesson)
    forward()
  }

  def save(@param("lesson.id") id: String): View = {
    val lessonId = Numbers.toLong(id)
    val lesson = entityDao.get(classOf[Lesson], lessonId)
    val labId = Numbers.toLong(get("lab.id").orNull)
    val itemCount = Numbers.toInt(get("lab.itemCount").orNull)
    val contents = get("item.content").get.split(",")

    if (labId == 0L) {
      val lab = new ExprProgram()
      contents foreach { content =>
        val item = new ExprTest()
        item.content = content
        lab.tests += item
      }
      lab.itemCount = itemCount
      lab.lesson = lesson
      //      lab.labType.code = "1"
      //      lab.name = lesson.course.name
      //      lab.stdCount = lesson.teachclass.stdCount
      //      lab.teacher = getTeacher()
      entityDao.saveOrUpdate(lab)
    } else {
      val lab = entityDao.get(classOf[ExprProgram], labId)
      lab.tests.clear()
      contents foreach { content =>
        val item = new ExprTest()
        item.content = content
        lab.tests += item
      }
      //      lab.labType.code = "1"
      lab.itemCount = itemCount
      entityDao.saveOrUpdate(lab)
    }

    redirect("search", "info.save.success")
  }

}
