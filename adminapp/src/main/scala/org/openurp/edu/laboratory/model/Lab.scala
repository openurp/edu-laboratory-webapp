package org.openurp.edu.laboratory.model

import org.beangle.data.model.LongId
import org.beangle.data.model.Updated
import org.openurp.edu.lesson.model.Lesson
import org.beangle.commons.collection.Collections
import org.openurp.edu.base.model.Teacher
import org.openurp.base.model.Semester

class Lab extends LongId with Updated {

  var lesson: Lesson = _
  var teacher: Teacher = _
  var tel: String = _
  var labType: LabType = _

  var name: String = _
  var stdCount: Int = _
  var audience: String = _
  var semester: Semester = _

  var labPeriod: String = _
  var labProportion: String = _

  var itemCount: Int = _
  var labItems = Collections.newBuffer[LabItem]

  var labApplies = Collections.newBuffer[LabApply]
}
