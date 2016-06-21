package org.openurp.edu.laboratory.web.action

import org.beangle.webmvc.entity.action.RestfulAction
import org.openurp.edu.laboratory.model.Software
import org.openurp.edu.laboratory.model.Operation
import org.openurp.base.model.Campus
import org.beangle.data.dao.OqlBuilder
import org.openurp.base.model.Building
import org.openurp.edu.base.model.Classroom
import org.beangle.webmvc.api.view.View

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
    super.saveAndRedirect(entity)

  }

}
