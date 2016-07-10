package org.openurp.edu.laboratory.index.web

import org.beangle.commons.inject.bind.AbstractBindModule
import org.openurp.edu.laboratory.index.web.action.IndexAction

class DefaultModule extends AbstractBindModule {

  protected override def binding() {
    bind(classOf[IndexAction])
  }
}
