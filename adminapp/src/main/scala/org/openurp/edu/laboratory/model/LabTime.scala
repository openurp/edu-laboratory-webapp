package org.openurp.edu.laboratory.model

import org.beangle.commons.lang.time.WeekTime
import org.beangle.data.model.LongId
import org.beangle.commons.collection.Collections

class LabTime extends LongId {
  var weekTime: WeekTime = _
  var labApply: LabApply = _

}
