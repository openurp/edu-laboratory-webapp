package org.openurp.edu.laboratory.model

import org.beangle.data.model.IntId
import org.openurp.edu.base.model.Project
import org.openurp.base.model.Room
import org.beangle.data.model.Coded
import org.beangle.data.model.Named
import org.beangle.data.model.TemporalOn
import org.openurp.edu.laboratory.model.code.LabRoomType

/**
 * 实验室房间
 */
class LabRoom extends IntId with Coded with Named with TemporalOn {
  var project: Project = _
  var room: Room = _
  var roomType: LabRoomType = _
  var capacity: Int = _
  var area: java.lang.Float = _
  var lab: Lab = _
}