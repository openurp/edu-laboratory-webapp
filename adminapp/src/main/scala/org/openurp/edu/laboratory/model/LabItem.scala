package org.openurp.edu.laboratory.model

import org.beangle.data.model.IntId

class LabItem extends IntId {
  var lab: Lab = _
  var content: String = _
}
