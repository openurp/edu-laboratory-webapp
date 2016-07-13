package org.openurp.edu.laboratory.index.web.action

import org.openurp.platform.api.app.UrpApp
import org.beangle.webmvc.api.action.ActionSupport
import org.beangle.security.context.SecurityContext
import org.beangle.data.dao.EntityDao
import org.beangle.data.dao.OqlBuilder
import org.beangle.security.realm.cas.CasConfig
import org.beangle.webmvc.api.annotation.param
import org.beangle.webmvc.api.view.View
import org.beangle.security.mgt.SecurityManager
import org.openurp.platform.api.Urp
import org.openurp.platform.api.security.Securities
import org.openurp.base.model.User
import org.beangle.webmvc.api.annotation.mapping
import org.beangle.security.mgt.SecurityManager
import org.openurp.edu.base.model.Project
import org.beangle.webmvc.api.annotation.action
import org.openurp.platform.api.security.RemoteService
import org.openurp.edu.base.model.Project
import org.beangle.webmvc.api.context.ActionContext

@action("")
class IndexAction extends ActionSupport {
  var entityDao: EntityDao = _
  var casConfig: CasConfig = _
  var securityManager: SecurityManager = _

  @mapping("{project}")
  def project(@param("project") project: String): String = {
    put("menuJson", RemoteService.getMenusJson())
    put("appJson", RemoteService.getAppsJson())
    val projects = entityDao.findBy(classOf[Project], "code", List(project))
    val currentProject = projects.head
    put("currentProject", currentProject)
    put("school", currentProject.school)
    put("projects", entityDao.getAll(classOf[Project]))
    put("user", getUser())
    put("casConfig", casConfig)
    put("webappBase", Urp.webappBase)
    put("thisAppName", UrpApp.name)
    forward()
  }

  def getUser(): User = {
    val users = entityDao.findBy(classOf[User], "code", List(Securities.user))
    if (users.isEmpty) {
      throw new RuntimeException("Cannot find staff with code " + Securities.user)
    } else {
      users.head
    }
  }

  def index(): View = {
    val now = new java.sql.Date(System.currentTimeMillis())
    val builder = OqlBuilder.from(classOf[Project], "p").where("p.beginOn <= :now and( p.endOn is null or p.endOn >= :now)", now).orderBy("p.code").cacheable()
    val projects = entityDao.search(builder)
    if (projects.isEmpty) throw new RuntimeException("Cannot find any valid projects")

    redirect("project", "&project=" + projects.head.code, null)
  }

  def logout(): View = {
    securityManager.logout(SecurityContext.session)
    redirect(to(casConfig.casServer + "/logout"), null)
  }
}