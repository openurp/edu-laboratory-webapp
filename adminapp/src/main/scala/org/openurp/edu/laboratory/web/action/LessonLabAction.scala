package org.openurp.edu.laboratory.web.action

import org.beangle.commons.lang.Numbers
import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.base.model.Semester
import org.openurp.edu.base.model.Teacher
import org.openurp.edu.laboratory.model.{ Lab, LabItem }
import org.openurp.edu.lesson.model.Lesson
import org.openurp.platform.api.security.Securities
import org.beangle.webmvc.api.annotation.param
import org.openurp.base.model.Building
import org.openurp.edu.laboratory.model.LabApply
import org.openurp.edu.laboratory.model.Software
import org.openurp.base.model.School

class LessonLabAction extends RestfulAction[Lab] {

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
    val labs = entityDao.getAll(classOf[Lab])
    val labMap = labs.map(x => (x.lesson, x)).toMap
    put("labMap", labMap)
    forward();
  }

  def addLab(): String = {
    val lessonId = longId("lesson")
    val lesson = entityDao.get(classOf[Lesson], lessonId)
    var labs = entityDao.search(OqlBuilder.from(classOf[Lab], "lab").where("lab.lesson=:lesson", lesson))
    if (labs.isEmpty) { put("lab", null) }
    else put("lab", labs.head)
    put("lesson", lesson)
    forward()
  }

  def saveLab(@param("lesson.id") id: String): View = {
    val lessonId = Numbers.toLong(id)
    val lesson = entityDao.get(classOf[Lesson], lessonId)
    val labId = Numbers.toLong(get("lab.id").orNull)
    val itemCount = Numbers.toInt(get("lab.itemCount").orNull)
    val contents = get("item.content").get.split(",")

    if (labId == 0L) {
      val lab = new Lab()
      contents foreach { content =>
        val item = new LabItem()
        item.content = content
        lab.labItems += item
      }
      lab.itemCount = itemCount
      lab.lesson = lesson
      lab.labType.code = "1"
      lab.name = lesson.course.name
      lab.stdCount = lesson.teachclass.stdCount
      lab.teacher = getTeacher()
      entityDao.saveOrUpdate(lab)
    } else {
      val lab = entityDao.get(classOf[Lab], labId)
      lab.labItems.clear()
      contents foreach { content =>
        val item = new LabItem()
        item.content = content
        lab.labItems += item
      }
      lab.labType.code = "1"
      lab.itemCount = itemCount
      entityDao.saveOrUpdate(lab)
    }

    redirect("search", "info.save.success")
  }

  def addApply(): String = {
    val labId = longId("lab")
    val lab = entityDao.get(classOf[Lab], labId)
    put("lab", lab)
    val lesson = entityDao.get(classOf[Lesson], lab.lesson.id)
    put("lesson", lesson)
    val applies = entityDao.search(OqlBuilder.from(classOf[LabApply], "apply").where("apply.lab=:lab", lab))
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
    val lab = entityDao.get(classOf[Lab], labId)
    val apply = new LabApply()
    apply.lab = lab
    val softwares = getAll("fake.softwares", classOf[Int])
    apply.softwares.clear()
    apply.softwares ++= entityDao.find(classOf[Software], softwares)
    val building = entityDao.get(classOf[Building], intId("apply.labBuilding"))
    apply.labBuilding = building

    entityDao.saveOrUpdate(apply)

    redirect("search", "info.save.success")
  }

}
