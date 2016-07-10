package org.openurp.edu.laboratory.teacher.web

import org.beangle.commons.inject.bind.AbstractBindModule
import org.openurp.edu.laboratory.teacher.web.action.ApplyAction
import org.openurp.edu.laboratory.teacher.web.action.OtherLabAction
import org.openurp.edu.laboratory.teacher.web.action.ProgramAction

class DefaultModule extends AbstractBindModule {

  protected override def binding() {
    bind(classOf[ApplyAction])
    bind(classOf[ProgramAction])
    bind(classOf[OtherLabAction])
  }
}
