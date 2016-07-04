package org.openurp.edu.laboratory.web.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.edu.laboratory.model.LabApply
import org.beangle.data.dao.OqlBuilder
import org.openurp.base.model.Semester
import org.openurp.platform.api.security.Securities
import org.openurp.edu.base.model.Teacher
import org.openurp.edu.lesson.model.Lesson
import org.beangle.commons.collection.Collections
import org.openurp.edu.lesson.model.CourseTake
import java.text.DecimalFormat
import org.openurp.base.model.Building
import org.openurp.edu.laboratory.model.Software
import org.openurp.edu.base.model.Classroom
import org.openurp.edu.laboratory.model.Operation
import org.beangle.webmvc.api.view.View
import org.beangle.commons.lang.Numbers
import org.beangle.webmvc.api.annotation.param

class ApplyAction extends RestfulAction[LabApply] {

  def getTeacher(): Teacher = {
    val teachers = entityDao.search(OqlBuilder.from(classOf[Teacher], "s").where("s.user.code=:code", Securities.user))
    if (teachers.isEmpty) {
      throw new RuntimeException("Cannot find teachers with code " + Securities.user)
    } else {
      teachers.head
    }
  }
  override def indexSetting(): Unit = {

    val semesters = entityDao.search(OqlBuilder.from(classOf[Semester], "semester").where("semester.calendar.school.id=:id", 16404))
    put("semesters", semesters)
    val semesterQuery = OqlBuilder.from(classOf[Semester], "semester").where(":now between semester.beginOn and semester.endOn", new java.util.Date())
    semesterQuery.where("semester.calendar.school.id=:id", 16404)
    put("currentSemester", entityDao.search(semesterQuery).head)
    super.indexSetting()
  }
  /**
   * 教师课程列表
   * //
   */
  override def search(): String = {
    val semesterQuery = OqlBuilder.from(classOf[Semester], "semester").where(":now between semester.beginOn and semester.endOn", new java.util.Date())
    semesterQuery.where("semester.calendar.school.id=:id", 16404)
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
    forward();
  }

  def addApplication(): String = {
    val lessonId = longId("lesson")
    val lesson = entityDao.get(classOf[Lesson], lessonId)
    var apply = entityDao.search(OqlBuilder.from(classOf[LabApply], "apply").where("apply.lesson=:leson", lesson))
    if (apply.isEmpty) {
      val application = new LabApply()
      put("application", application)
    } else put("apply", apply.head)
    put("lesson", lesson)
    val buildings = entityDao.search(OqlBuilder.from(classOf[Building], "building").where("building.school.id=:id", 16404))
    put("buildings", buildings)
    val softwares = entityDao.getAll(classOf[Software])
    put("softwares", softwares)
    //    val weeks =
    //    put("weeks", weeks)
    forward()
  }
  def saveApplication(@param("apply.id") id: String): View = {
    val applyId = Numbers.toLong(id)
    val application = entityDao.get(classOf[LabApply], applyId)
    val softwares = getAll("fake.softwares", classOf[Int])
    application.softwares.clear()
    application.softwares ++= entityDao.find(classOf[Software], softwares)
    super.saveAndRedirect(application)
  }

}
