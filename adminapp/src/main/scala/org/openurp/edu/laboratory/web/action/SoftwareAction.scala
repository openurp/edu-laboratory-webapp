package org.openurp.edu.laboratory.web.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.edu.laboratory.model.Software
import org.openurp.edu.laboratory.model.Operation
import org.openurp.base.model.Campus
import org.beangle.data.dao.OqlBuilder
import org.openurp.base.model.Building
import org.openurp.edu.base.model.Classroom
import org.beangle.webmvc.api.view.View
import org.beangle.commons.lang.Numbers
import org.beangle.commons.lang.Strings

class SoftwareAction extends RestfulAction[Software] {

  override def indexSetting(): Unit = {

    put("oses", entityDao.getAll(classOf[Operation]))
    super.indexSetting()
  }

  override protected def editSetting(entity: Software): Unit = {
    put("oses", entityDao.getAll(classOf[Operation]))
    //FIXME 校区写死
    put("classrooms", entityDao.search(OqlBuilder.from(classOf[Classroom], "classroom").orderBy("classroom.name")))
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(entity: Software): View = {
    val software = entity.asInstanceOf[Software]
    if (!software.persisted) {
      software.beginOn = new java.sql.Date(System.currentTimeMillis())
    }
    val oses = getAll("fake.oses", classOf[Int])
    val rooms = getAll("fake.guaPai", classOf[Long])
    software.oses.clear()
    software.oses ++= entityDao.find(classOf[Operation], oses)
    software.classrooms.clear()
    software.classrooms ++= entityDao.find(classOf[Classroom], rooms)
    super.saveAndRedirect(entity)
  }

}
