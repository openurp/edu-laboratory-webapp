package org.openurp.edu.laboratory.model

import org.beangle.data.model.LongId
import org.openurp.edu.lesson.model.Lesson
import org.beangle.data.model.Updated

class MediaApply extends LongId with Updated {
  var lesson: Lesson = _
  var software: String = _
  var state: Boolean = false

}
