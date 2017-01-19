package org.openurp.edu.laboratory;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.openurp.edu.laboratory.service.impl.StdCountUpdateServiceImpl;

public class LabModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind("stdCountUpdateServiceImpl", StdCountUpdateServiceImpl.class).lazy(false);
  }

}
