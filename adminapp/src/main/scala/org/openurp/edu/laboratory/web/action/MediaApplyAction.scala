package org.openurp.edu.laboratory.web.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.edu.laboratory.model.MediaApply
import org.openurp.base.model.Semester
import org.openurp.platform.api.security.Securities
import org.openurp.base.model.School
import org.openurp.edu.base.model.Teacher
import org.openurp.edu.laboratory.model.Lab
import org.beangle.data.dao.OqlBuilder
import org.openurp.edu.lesson.model.Lesson

class MediaApplyAction extends RestfulAction[MediaApply] {

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
    forward();
  }

}
