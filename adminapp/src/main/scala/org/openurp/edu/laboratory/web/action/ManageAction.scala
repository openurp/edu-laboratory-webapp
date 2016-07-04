package org.openurp.edu.laboratory.web.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.edu.laboratory.model.LabApply
import org.beangle.data.dao.OqlBuilder
import org.openurp.base.model.Semester

class ManageAction extends RestfulAction[LabApply] {

  override def indexSetting(): Unit = {

    val semesters = entityDao.getAll(classOf[Semester])
    put("semesters", semesters)
    val semesterQuery = OqlBuilder.from(classOf[Semester], "semester").where(":now between semester.beginOn and semester.endOn", new java.util.Date())
    put("currentSemester", entityDao.search(semesterQuery).head)
    super.indexSetting()
  }

  override def search(): String = {
    val semesterQuery = OqlBuilder.from(classOf[Semester], "semester").where(":now between semester.beginOn and semester.endOn", new java.util.Date())
    val semesterId = getInt("semester.id").getOrElse(entityDao.search(semesterQuery).head.id)
    val semester = entityDao.get(classOf[Semester], semesterId);
    val applyQuery = OqlBuilder.from(classOf[LabApply], "apply");
    applyQuery.where("apply.lesson.semester.id=:semesterId", semesterId);
    val applies = entityDao.search(applyQuery);
    put("applies", applies);
    forward();
  }
}
