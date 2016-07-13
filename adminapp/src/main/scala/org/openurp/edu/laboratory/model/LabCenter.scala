package org.openurp.edu.laboratory.model

import org.beangle.data.model.TemporalOn
import org.beangle.data.model.IntId
import org.beangle.data.model.Named
import org.beangle.data.model.Coded
import org.openurp.base.model.School
import org.openurp.edu.base.model.Project

/**
 * 实验中心
 */
class LabCenter extends IntId with Coded with Named with TemporalOn {
  /**项目*/
  var project: Project = _
}