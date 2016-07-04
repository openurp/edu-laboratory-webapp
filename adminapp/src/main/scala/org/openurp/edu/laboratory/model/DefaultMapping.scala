package org.openurp.edu.laboratory.model

import scala.reflect.runtime.universe

import org.beangle.data.model.bind.Mapping

/*
 * OpenURP, Agile University Resource Planning Solution
 *
 * Copyright (c) 2014-2015, OpenURP Software.
 *
 * OpenURP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenURP is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with OpenURP.  If not, see <http://www.gnu.org/licenses/>.
 */

class DefaultMapping extends Mapping {

  def binding(): Unit = {
    defaultIdGenerator("auto_increment")

    bind[Software] on (e => declare(
      e.classrooms is ordered,
      e.oses is ordered))

    bind[LabType] on (e => declare(
      e.name & e.id is notnull))

    bind[Operation] on (e => declare(
      e.name is notnull))

    bind[LabItem]

    bind[Lab] on (e => declare(
      e.name & e.stdCount & e.teacher & e.itemCount are notnull,
      e.labApplies is depends("lab"),
      e.labItems is depends("lab")))

    bind[LabApply] on (e => declare(
      e.lab & e.labBuilding are notnull,
      e.softwares is ordered))
    //
    //    bind[LabTime] on (e => declare(
    //      e.labApply & e.weekTime are notnull))
  }
}
