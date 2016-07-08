package org.openurp.edu.laboratory.web

import org.beangle.commons.inject.bind.AbstractBindModule
import org.openurp.edu.laboratory.web.action.SoftwareAction
import org.openurp.edu.laboratory.web.action.ManageAction
import org.openurp.edu.laboratory.web.action.ApplyAction
import org.openurp.edu.laboratory.web.action.LessonLabAction
import org.openurp.edu.laboratory.web.action.OtherLabAction
import org.openurp.edu.laboratory.web.action.MediaApplyAction

class DefaultModule extends AbstractBindModule {

  protected override def binding() {
    bind(classOf[SoftwareAction])
    bind(classOf[ApplyAction])
    bind(classOf[ManageAction])
    bind(classOf[LessonLabAction])
    bind(classOf[OtherLabAction])
    bind(classOf[MediaApplyAction])
  }
}
