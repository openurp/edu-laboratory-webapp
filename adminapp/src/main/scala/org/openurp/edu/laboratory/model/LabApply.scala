package org.openurp.edu.laboratory.model

import org.beangle.data.model.LongId
import org.beangle.data.model.Updated
import org.openurp.edu.base.model.Teacher
import org.openurp.base.model.Building
import org.beangle.commons.collection.Collections
import org.openurp.edu.lesson.model.Lesson
import org.beangle.commons.lang.time.WeekTime

class LabApply extends LongId with Updated {

  var lab: Lab = _
  var labBuilding: Building = _
  var softwares = Collections.newBuffer[Software]
  var weekTime: WeekTime = _
  var state: Boolean = false

}
