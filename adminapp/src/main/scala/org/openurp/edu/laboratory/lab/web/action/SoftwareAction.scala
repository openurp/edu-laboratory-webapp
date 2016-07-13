package org.openurp.edu.laboratory.lab.web.action

import org.beangle.data.dao.OqlBuilder
import org.beangle.webmvc.api.view.View
import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.edu.base.model.Classroom
import org.openurp.edu.laboratory.model.{ LabRoom, Os, Software }
import org.openurp.edu.base.model.Classroom
import org.openurp.edu.laboratory.model.LabRoom
import org.openurp.edu.laboratory.model.Os

class SoftwareAction extends RestfulAction[Software] {

  override def indexSetting(): Unit = {

    put("oses", entityDao.getAll(classOf[Os]))
    super.indexSetting()
  }

  override protected def editSetting(entity: Software): Unit = {
    put("oses", entityDao.getAll(classOf[Os]))
    //FIXME 校区写死
    put("classrooms", entityDao.search(OqlBuilder.from(classOf[Classroom], "classroom").orderBy("classroom.name")))
    super.editSetting(entity)
  }

  override protected def saveAndRedirect(entity: Software): View = {
    val software = entity.asInstanceOf[Software]
    val oses = getAll("fake.oses", classOf[Int])
    val rooms = getAll("fake.guaPai", classOf[Int])
    software.oses.clear()
    software.oses ++= entityDao.find(classOf[Os], oses)
    software.rooms.clear()
    software.rooms ++= entityDao.find(classOf[LabRoom], rooms)
    super.saveAndRedirect(entity)
  }

}
