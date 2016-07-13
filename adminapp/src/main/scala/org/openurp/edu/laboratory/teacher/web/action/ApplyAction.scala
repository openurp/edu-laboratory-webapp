package org.openurp.edu.laboratory.teacher.web.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.beangle.data.dao.OqlBuilder
import org.openurp.base.model.Semester
import org.openurp.platform.api.security.Securities
import org.openurp.edu.base.model.Teacher
import org.openurp.edu.lesson.model.Lesson
import org.openurp.base.model.Building
import org.openurp.edu.laboratory.model.Software
import org.beangle.webmvc.api.view.View
import org.beangle.commons.lang.Numbers
import org.beangle.webmvc.api.annotation.param
import org.openurp.base.model.Building
import org.openurp.base.model.Semester
import org.openurp.edu.laboratory.model.Software
import org.openurp.edu.lesson.model.Lesson
import org.openurp.edu.laboratory.model.ExprProgram
import org.openurp.edu.laboratory.model.LabRoomApply
import org.openurp.base.model.Building
import org.openurp.base.model.Semester
import org.openurp.edu.laboratory.model.ExprProgram
import org.openurp.edu.laboratory.model.Software
import org.openurp.edu.lesson.model.Lesson

/**
 * 实验室申请
 */
class ApplyAction extends RestfulAction[LabRoomApply] {

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
   * 此处应该找到用户的申请
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

  def add(): String = {
    val lessonId = longId("lesson")
    val lesson = entityDao.get(classOf[Lesson], lessonId)
    var apply = entityDao.search(OqlBuilder.from(classOf[LabRoomApply], "apply").where("apply.lesson=:leson", lesson))
    if (apply.isEmpty) {
      val application = new LabRoomApply()
      put("application", application)
    } else put("apply", apply.head)
    put("lesson", lesson)
    val buildings = entityDao.search(OqlBuilder.from(classOf[Building], "building").where("building.school.id=:id", 16404))
    put("buildings", buildings)
    val softwares = entityDao.getAll(classOf[Software])
    put("softwares", softwares)
    forward()
  }

  def save(@param("apply.id") id: String): View = {
    val applyId = Numbers.toLong(id)
    val application = entityDao.get(classOf[LabRoomApply], applyId)
    val softwares = getAll("fake.softwares", classOf[Int])
    application.softwares.clear()
    application.softwares ++= entityDao.find(classOf[Software], softwares)
    super.saveAndRedirect(application)
  }
  
  def addApply(): String = {
    val labId = longId("lab")
    val lab = entityDao.get(classOf[ExprProgram], labId)
    put("lab", lab)
    val lesson = entityDao.get(classOf[Lesson], lab.lesson.id)
    put("lesson", lesson)
    val applies = entityDao.search(OqlBuilder.from(classOf[LabRoomApply], "apply").where("apply.lab=:lab", lab))
    if (applies.isEmpty) { put("apply", null) }
    else put("apply", applies.head)
    val buildings = entityDao.search(OqlBuilder.from(classOf[Building], "building").where("building.school.id=:id", 16404))
    put("buildings", buildings)
    val softwares = entityDao.getAll(classOf[Software])
    put("softwares", softwares)
    forward()
  }

  def saveApply(@param("lab.id") id: String): View = {
    val labId = Numbers.toLong(id)
    val lab = entityDao.get(classOf[LabRoomApply], labId)
    val apply = new LabRoomApply()
//    apply.lab = lab
    val softwares = getAll("fake.softwares", classOf[Int])
    apply.softwares.clear()
    apply.softwares ++= entityDao.find(classOf[Software], softwares)
//    val building = entityDao.get(classOf[Building], intId("apply.labBuilding"))
//    apply.labBuilding = building
    entityDao.saveOrUpdate(apply)

    redirect("search", "info.save.success")
  }
}
