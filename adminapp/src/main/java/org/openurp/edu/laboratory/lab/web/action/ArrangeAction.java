package org.openurp.edu.laboratory.lab.web.action;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.openurp.base.model.Semester;
import org.openurp.base.model.TimeSetting;
import org.openurp.edu.laboratory.model.LabRoom;
import org.openurp.edu.laboratory.model.LabRoomApply;
import org.openurp.edu.lesson.util.WeekTimeDigestor;
import org.openurp.edu.web.action.SemesterSupportAction;

public class ArrangeAction extends SemesterSupportAction {

  public String list() {
    Semester semester = getSemester();
    TimeSetting timeSetting = timeSettingService.getClosestTimeSetting(getProject(), semester, null);
    OqlBuilder<LabRoomApply> builder = OqlBuilder.from(LabRoomApply.class, "apply");
    builder.where("apply.project =:project and apply.semester = :semester", getProject(), semester);
    builder.orderBy(get(Order.ORDER_STR)).limit(getPageLimit());
    List<LabRoomApply> applyList = entityDao.search(builder);

    Map<LabRoomApply, String> times = CollectUtils.newHashMap();
    for (LabRoomApply p : applyList) {
      times.put(p,
          WeekTimeDigestor.getInstance().digest(getTextResource(), semester, timeSetting, p.getTimes()));
    }
    put("applyList", applyList);
    put("times", times);
    return forward();
  }

  public String arrangeSetting() {
    LabRoomApply apply = entityDao.get(LabRoomApply.class, getLongId("apply"));
    OqlBuilder<LabRoom> query = OqlBuilder.from(LabRoom.class, "lr");
    List<LabRoom> rooms = entityDao.search(query);
    put("campusList", getProject().getCampuses());
    put("freeLabroomList", rooms);
    put("lesson",apply.getLesson());
    put("apply", apply);
    // else {
    // String selectedWeekUnitSeq = get("selectedWeekUnits");
    // selectedWeekUnitSeq = selectedWeekUnitSeq.replaceAll("<br>", "");
    // String vaildWeeks = get("selectedWeeks"); // add by qj 为了去掉换行
    // List<WeekTime> timeList = new ArrayList<WeekTime>();
    // String[] selectedWeeksUnits = selectedWeekUnitSeq.split(";");
    // Arrays.sort(selectedWeeksUnits, new Comparator<String>() {
    // public int compare(String arg0, String arg1) {
    // return Numbers.toInt(Strings.remove(arg0, ',')) - Numbers.toInt(Strings.remove(arg1, ','));
    // }
    // });
    // // 初始化TimeUnit的startTime, endTime
    // Integer semesterIdForFreeRoom = getInt("semesterIdForFreeRoom");
    // Semester semester = semesterService.getSemester(semesterIdForFreeRoom);
    // TimeSetting timeSetting = timeSettingService.getClosestTimeSetting(project, semester, null);
    // // for each weekunits
    // for (int j = 0; j < selectedWeeksUnits.length; j++) {
    // WeekTimeBuilder builder = WeekTimeBuilder.on(semester);
    // int weekId = Integer.valueOf(selectedWeeksUnits[j].substring(0, 1)).intValue();
    // int unitId = Integer.valueOf(selectedWeeksUnits[j].substring(2)).intValue();
    // CourseUnit cu = timeSetting.getUnitMap().get(unitId);
    // List<WeekTime> temp = builder.buildOnOldWeekStr(WeekDay.get(weekId), vaildWeeks);
    // for (WeekTime t : temp) {
    // t.setBeginAt(cu.getBeginAt());
    // t.setEndAt(cu.getEndAt());
    // timeList.add(t);
    // }
    // }
    //
    // CourseActivity activity = Model.newInstance(CourseActivity.class);
    // activity.setRooms(new HashSet<Classroom>());
    // activity.getRooms().add(room);
    // // activity.setLesson(lesson);
    // boolean confilctClassroom = getBool("confilctClassroom");
    // OqlBuilder<Classroom> builder = null;
    // if (confilctClassroom) {
    // builder = scheduleRoomService
    // .getOccupancyRoomsOf(getDeparts(), timeList.toArray(new WeekTime[timeList.size()]), activity,
    // project).orderBy("classroom.courseCapacity,classroom.code").limit(getPageLimit());
    // } else {
    // builder = scheduleRoomService
    // .getFreeRoomsOf(getDeparts(), timeList.toArray(new WeekTime[timeList.size()]), activity,
    // project)
    // .orderBy("classroom.courseCapacity,classroom.code").limit(getPageLimit());
    // }
    // put("classroomList", entityDao.search(builder));
    // put("confilctClassroom", confilctClassroom);
    // }
    // if (room.getCampus() != null && room.getCampus().isPersisted()) {
    // put("buildingList",
    // CollectionUtils.intersection(getBaseInfos(Building.class),
    // entityDao.get(Building.class, "campus", room.getCampus())));
    // } else {
    // put("buildingList", Collections.EMPTY_LIST);
    // }
    // put("campusList", getProject().getCampuses());
    // put("configTypeList", codeService.getCodes(ClassroomType.class));
    // put("detectCollision", detectCollision);
    //
    // // 加载查询下拉框中校区下教学楼
    // if (getLong("classroom.room.campus.id") != null) {
    // OqlBuilder<Building> query = OqlBuilder.from(Building.class, "building");
    // query.where("building.campus.id =:campusId", getInt("classroom.room.campus.id"));
    // query.orderBy("building.code");
    // put("buildings", entityDao.search(query));
    // }

    return forward();
  }
}
