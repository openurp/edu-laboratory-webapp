package org.openurp.edu.laboratory.model

import org.beangle.commons.collection.Collections
import org.beangle.data.model.{ LongId, Updated }
import org.openurp.base.model.{ Semester, User }
import org.openurp.edu.base.model.{ Project, Teacher }
import org.openurp.edu.lesson.model.Lesson
import org.beangle.commons.lang.time.WeekTime

/**
 * 实验计划
 */
class ExprProgram extends LongId with Updated {

  var project: Project = _

  var semester: Semester = _

  var lesson: Lesson = _

  var period: String = _

  var itemCount: Int = _

  var tests = Collections.newBuffer[ExprTest]

  var activities = Collections.newBuffer[ExprActivity]
}

/**
 * 实验项目
 */
class ExprTest extends LongId {
  var program: ExprProgram = _
  var content: String = _
}

/**
 * 实验活动
 */
class ExprActivity extends LongId {
  var program: ExprProgram = _
  var time: WeekTime = _
  var rooms = Collections.newBuffer[LabRoom]
}
