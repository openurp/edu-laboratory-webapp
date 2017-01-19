package org.openurp.edu.laboratory.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.orm.hibernate.internal.SessionUtils;
import org.hibernate.SessionFactory;
import org.openurp.edu.laboratory.model.LabRoomApply;
import org.openurp.edu.laboratory.model.MediaApply;
import org.openurp.edu.lesson.model.Lesson;

public class StdCountUpdateServiceImpl extends BaseServiceImpl implements Initializing {

  protected SessionFactory sessionFactory;

  // 15min
  private int statIntevalMills = 900 * 1000;

  public static class StdCountUpdateDaemon extends TimerTask {

    StdCountUpdateServiceImpl stdCountUpdateService;

    public StdCountUpdateDaemon(StdCountUpdateServiceImpl stdCountUpdateService) {
      super();
      this.stdCountUpdateService = stdCountUpdateService;
    }

    @Override
    public void run() {
      stdCountUpdateService.update();
    }
  }

  @Override
  public void init() throws Exception {
    long now = System.currentTimeMillis();
    StdCountUpdateDaemon cacheSync = new StdCountUpdateDaemon(this);
    new Timer("StdCountUpdateDaemon", true).schedule(cacheSync, new Date(now + 2 * 1000), statIntevalMills);
  }

  public void update() {
    SessionUtils.enableBinding(sessionFactory);
    try {

      OqlBuilder<MediaApply> lmBuilder = OqlBuilder.from(MediaApply.class, "apply");
      lmBuilder.where("not exists ( from " + Lesson.class.getName() + " l where l=apply.lesson)");
      lmBuilder.where("apply.lesson is not null");
      List<MediaApply> mappliesLackLesson = entityDao.search(lmBuilder);
      entityDao.remove(mappliesLackLesson);
      if (mappliesLackLesson.size() > 0) {
        logger.info("删除不存在lesson的多媒体申请 :" + mappliesLackLesson.size());
      }

      OqlBuilder<LabRoomApply> lBuilder = OqlBuilder.from(LabRoomApply.class, "apply");
      lBuilder.where("not exists ( from " + Lesson.class.getName() + " l where l=apply.lesson)");
      lBuilder.where("apply.lesson is not null");
      List<LabRoomApply> appliesLackLesson = entityDao.search(lBuilder);
      entityDao.remove(appliesLackLesson);
      if (appliesLackLesson.size() > 0) {
        logger.info("删除不存在lesson的实验室申请 :" + appliesLackLesson.size());
      }

      OqlBuilder<LabRoomApply> builder = OqlBuilder.from(LabRoomApply.class, "apply");
      builder.where("apply.lesson is not null");
      List<LabRoomApply> applies = entityDao.search(builder);

      for (LabRoomApply apply : applies) {
        if (apply.getAttendance() != apply.getLesson().getTeachClass().getStdCount()) {
          logger.info("update stdCount from " + apply.getAttendance() + " to "
              + apply.getLesson().getTeachClass().getStdCount() + " for code "
              + apply.getLesson().getCourse().getCode());
          apply.setAttendance(apply.getLesson().getTeachClass().getStdCount());
        }
      }
      entityDao.saveOrUpdate(applies);

    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      SessionUtils.disableBinding(sessionFactory);
      SessionUtils.closeSession(sessionFactory);
    }

  }

  public void setSessionFactory(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
}
