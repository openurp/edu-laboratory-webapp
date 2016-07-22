package org.openurp.edu.laboratory.lab.web.action;

import java.util.List;
import java.util.Map;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.time.WeekDay;
import org.beangle.commons.lang.time.WeekState;
import org.beangle.commons.lang.time.WeekTime;
import org.beangle.commons.lang.tuple.Pair;
import org.beangle.commons.text.i18n.TextResource;
import org.beangle.commons.transfer.exporter.DefaultPropertyExtractor;
import org.openurp.base.model.Semester;
import org.openurp.base.model.TimeSetting;
import org.openurp.base.util.WeekTimeBuilder;
import org.openurp.edu.laboratory.model.LabRoomApply;
import org.openurp.edu.lesson.util.WeekTimeDigestor;

public class ApplyPropertyExtractor extends DefaultPropertyExtractor {

  private TimeSetting timeSetting;

  private Map<WeekDay, Pair<Integer, Integer>> offsets = CollectUtils.newHashMap();
  private Semester semester;

  public ApplyPropertyExtractor(TextResource textResource, Semester semester, TimeSetting timeSetting) {
    super(textResource);
    for (WeekDay day : WeekDay.All) {
      offsets.put(day,
          Pair.of(WeekTimeBuilder.getOffset(semester, day), WeekTimeBuilder.getReverseOffset(semester, day)));
    }
    this.semester = semester;
    this.timeSetting = timeSetting;
  }

  @Override
  public Object getPropertyValue(Object target, String property) throws Exception {
    LabRoomApply apply = ((LabRoomApply) target);
    if (property.equals("applyTime")) {
      List<WeekTime> times = CollectUtils.newArrayList();
      for (WeekTime wt : apply.getTimes()) {
        WeekTime time = (WeekTime) wt.clone();
        Pair<Integer, Integer> offset = offsets.get(time.getWeekday());
        int weekOffset = offset._1;
        int reverseOffset = offset._2;
        if (time.getStartOn().getYear() == semester.getBeginOn().getYear()) {
          time.setWeekstate(new WeekState(time.getWeekstate().value >> weekOffset));
        } else {
          time.setWeekstate(new WeekState(time.getWeekstate().value << reverseOffset));
        }
        time.setStartOn(WeekTimeBuilder.getStartOn(semester, time.getWeekday()));
        times.add(time);
      }
      return WeekTimeDigestor.getInstance().digest(null, apply.getSemester(), timeSetting, times);
    } else if (property.equals("softwares")) {
      return getPropertyIn(apply.getSoftwares(), "name");
    } else {
      return PropertyUtils.getProperty(target, property);
    }

  }

}
