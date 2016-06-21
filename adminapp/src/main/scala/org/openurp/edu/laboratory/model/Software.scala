package org.openurp.edu.laboratory.model

import org.beangle.commons.collection.Collections
import org.openurp.code.BaseCodeBean
import org.openurp.edu.base.model.Classroom

class Software extends BaseCodeBean {

  var classrooms = Collections.newBuffer[Classroom]
  var oses = Collections.newBuffer[Operation]
  var version: String = _
}
