package org.openurp.edu.laboratory.model

import org.beangle.data.model.LongId
import org.beangle.data.model.Updated
import org.openurp.edu.base.model.Teacher
import org.openurp.base.model.Building
import org.beangle.commons.collection.Collections
import org.openurp.edu.lesson.model.Lesson
import org.beangle.commons.lang.time.WeekTime
import org.openurp.base.model.User
import org.openurp.edu.base.model.Project
import org.openurp.base.model.Semester
import org.openurp.edu.base.States

/**
 * 实验房间申请
 */
class LabRoomApply extends LongId with Updated {
  /**项目*/
  var project: Project = _
  /**学年学期*/
  var semester: Semester = _
  /**借用人*/
  var borrower: User = _
  /**联系电话*/
  var tel: String = _
  /**对应教学任务(临时借用可以为空)*/
  var lesson: Lesson = _

  /**活动事由*/
  var activity: String = _
  /**出席人数*/
  var attendance: Int = _
  /**听课对象*/
  var audience: String = _

  /**所需软件*/
  var softwares = Collections.newBuffer[Software]
  /**时间*/
  var weekTime: WeekTime = _
  /**状态*/
  var state: States.State = _
}
