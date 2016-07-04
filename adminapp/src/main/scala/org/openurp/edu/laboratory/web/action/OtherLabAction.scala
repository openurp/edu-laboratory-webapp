package org.openurp.edu.laboratory.web.action

import org.openurp.base.model.Semester
import org.openurp.edu.laboratory.model.LabApply
import org.beangle.commons.lang.Numbers
import org.openurp.edu.laboratory.model.Software
import org.openurp.platform.api.security.Securities
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.edu.base.model.Teacher
import org.openurp.edu.laboratory.model.Lab
import org.beangle.data.dao.OqlBuilder
import org.openurp.edu.lesson.model.Lesson
import org.openurp.base.model.Building
import org.beangle.webmvc.api.annotation.param
import org.beangle.webmvc.api.view.View
import org.openurp.edu.laboratory.model.LabItem
import org.beangle.commons.collection.Order
import org.openurp.base.model.School

class OtherLabAction extends RestfulAction[Lab] {

  def getTeacher(): Teacher = {
    val teachers = entityDao.search(OqlBuilder.from(classOf[Teacher], "s").where("s.user.code=:code", Securities.user))
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
  override protected def getQueryBuilder(): OqlBuilder[Lab] = {
    val builder = OqlBuilder.from(classOf[Lab], "lab")
    builder.where("lab.labType.id=:id", 2)
    populateConditions(builder)
    builder.orderBy(get(Order.OrderStr).orNull).limit(getPageLimit)
  }

  override protected def editSetting(entity: Lab): Unit = {
    val school = entityDao.get(classOf[School], 16404)
    val semesters = entityDao.search(OqlBuilder.from(classOf[Semester], "semester").where("semester.calendar.school=:school", school))
    put("semesters", semesters)
    val semesterQuery = OqlBuilder.from(classOf[Semester], "semester").where("semester.code=:code", "2016-1")
    semesterQuery.where("semester.calendar.school=:school", school)
    put("currentSemester", entityDao.search(semesterQuery).head)
    super.editSetting(entity)
  }
  override protected def saveAndRedirect(entity: Lab): View = {
    entity.teacher = getTeacher()
    entity.labType.id = 2
    val contents = get("item.content").get.split(",")
    entity.labItems.clear()
    contents foreach { content =>
      val item = new LabItem()
      item.content = content
      entity.labItems += item
    }
    super.saveAndRedirect(entity)
  }

  def addApply(): String = {
    val labId = longId("lab")
    val lab = entityDao.get(classOf[Lab], labId)
    put("lab", lab)
    val semesters = entityDao.search(OqlBuilder.from(classOf[Semester], "semester").where("semester.calendar.school.id=:id", 16404))
    put("semesters", semesters)
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
