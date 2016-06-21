package org.openurp.edu.laboratory.web

import org.beangle.commons.inject.bind.AbstractBindModule
import org.openurp.edu.laboratory.web.action.SoftwareAction

class DefaultModule extends AbstractBindModule {

  protected override def binding() {
    bind(classOf[SoftwareAction])

  }
}
