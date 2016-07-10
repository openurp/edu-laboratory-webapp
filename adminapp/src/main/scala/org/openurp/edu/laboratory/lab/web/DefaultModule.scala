package org.openurp.edu.laboratory.lab.web

import org.beangle.commons.inject.bind.AbstractBindModule
import org.openurp.edu.laboratory.lab.web.action.ApplyAuditAction
import org.openurp.edu.laboratory.lab.web.action.SoftwareAction

class DefaultModule extends AbstractBindModule {

  protected override def binding() {
    bind(classOf[SoftwareAction])
    bind(classOf[ApplyAuditAction])
  }
}
