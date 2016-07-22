/*
 * OpenURP, Agile University Resource Planning Solution
 *
 * Copyright (c) 2014-2016, OpenURP Software.
 *
 * OpenURP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * OpenURP is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with OpenURP.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.openurp.edu.laboratory.teacher.web;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.openurp.edu.laboratory.lab.web.action.ArrangeAction;
import org.openurp.edu.laboratory.lab.web.action.ManageAction;
import org.openurp.edu.laboratory.teacher.web.action.ApplyAction;
import org.openurp.edu.laboratory.teacher.web.action.MediaApplyAction;
import org.openurp.edu.laboratory.teacher.web.action.ProgramAction;

public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(ApplyAction.class);
    bind(ProgramAction.class);
    bind(ManageAction.class);
    bind(MediaApplyAction.class);
    bind(ArrangeAction.class);
  }

}
