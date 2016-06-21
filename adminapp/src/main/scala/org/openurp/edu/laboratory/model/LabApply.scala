package org.openurp.edu.laboratory.model

import org.beangle.data.model.LongId
import org.beangle.data.model.Updated
import org.openurp.edu.base.model.Teacher
import org.openurp.base.model.Building
import org.beangle.commons.collection.Collections
import org.openurp.edu.lesson.model.Lesson
import org.beangle.commons.lang.time.WeekTime

class LabApply extends LongId with Updated {
  var lesson: Lesson = _
  var name: String = _
  var temp: Boolean = false
  var audience: String = _
  var count: Int = _
  var teacher: Teacher = _
  var tel: String = _
  var labPeriod: String = _
  var itemCount: Int = _
  var labItems = Collections.newBuffer[LabItem]
  var labBuilding: Building = _
  var softwares = Collections.newBuffer[Software]

}
