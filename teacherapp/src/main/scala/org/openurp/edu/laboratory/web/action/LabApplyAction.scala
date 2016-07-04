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

class LabApplyAction extends RestfulAction[LabApply] {

  def getTeacher(): Teacher = {
    val teachers = entityDao.search(OqlBuilder.from(classOf[Teacher], "s").where("s.user.code=:code", Securities.user))
    if (teachers.isEmpty) {
      throw new RuntimeException("Cannot find teachers with code " + Securities.user)
    } else {
      teachers.head
    }
  }
  override def indexSetting(): Unit = {

    val semesters = entityDao.getAll(classOf[Semester])
    put("semesters", semesters)
    val semesterQuery = OqlBuilder.from(classOf[Semester], "semester").where(":now between semester.beginOn and semester.endOn", new java.util.Date())
    put("currentSemester", entityDao.search(semesterQuery).head)
    super.indexSetting()
  }
  /**
   * 教师课程列表
   * //
   */
  override def search(): String = {
    val semesterQuery = OqlBuilder.from(classOf[Semester], "semester").where(":now between semester.beginOn and semester.endOn", new java.util.Date())
    val semesterId = getInt("semester.id").getOrElse(entityDao.search(semesterQuery).head.id)
    val semester = entityDao.get(classOf[Semester], semesterId);
    val teacher = getTeacher()
    if (teacher == null) { forward("error.teacher.teaNo.needed") }
    // 得到该教师的所有教学任务
    val lessonQuery = OqlBuilder.from(classOf[Lesson], "lesson");
    lessonQuery.where("lesson.semester.id=:semesterId", semesterId);
    if (teacher != null) {
      lessonQuery.join("lesson.teachers", "teacher")
      lessonQuery.where("teacher =:teacher", teacher);
    }
    val lessonList = entityDao.search(lessonQuery);
    put("lessonList", lessonList);
    put("semester", semester);
    forward();
  }

}
