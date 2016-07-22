package org.openurp.edu.laboratory.lab.web.action;

import java.util.Set;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.text.i18n.TextResource;
import org.beangle.commons.transfer.exporter.DefaultPropertyExtractor;
import org.openurp.edu.base.model.Classroom;
import org.openurp.edu.laboratory.model.MediaApply;
import org.openurp.edu.lesson.model.CourseActivity;


public class MediaApplyPropertyExtractor extends DefaultPropertyExtractor {



  public MediaApplyPropertyExtractor(TextResource textResource) {
    super(textResource);
  }

  @Override
  public Object getPropertyValue(Object target, String property) throws Exception {
    MediaApply apply = ((MediaApply) target);
    if (property.equals("mediaRoom")) {
      Set<Classroom> rooms= CollectUtils.newHashSet();
      for (CourseActivity activity:apply.getLesson().getCourseSchedule().getActivities()){
        rooms.addAll(activity.getRooms());
      }
      return this.getPropertyIn(rooms, "name");
      }
    else {
      return PropertyUtils.getProperty(target, property);
    }

  }

}
