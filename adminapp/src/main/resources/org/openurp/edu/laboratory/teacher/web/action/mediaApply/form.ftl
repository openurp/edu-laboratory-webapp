[#ftl]
[@b.head/]
[@b.toolbar title="新增和修改多媒体教室软件需求"]
  bar.addBack();
[/@]
[@b.form action="!save" theme="list" name="mediaForm"]
   [@b.field label="课程"]
     ${mediaApply.lesson.no} ${mediaApply.lesson.course.name} ${applyableActivityText}
   [/@]
   [@b.textfield label="联系电话" name="mediaApply.tel" required="true" value=mediaApply.tel! maxlength="15"/]
   [@b.textarea label="使用软件" name="mediaApply.software" required="true" cols="80" rows="3" value=mediaApply.software/]
   [@b.formfoot id="footer"]
     [#if mediaApply.persisted]
        <input type="hidden" name="mediaApply.id" value="${mediaApply.id}"/>
     [#else]
        <input type="hidden" name="lesson.id" value="${mediaApply.lesson.id}"/>
     [/#if]
      [@b.submit value="提交"/]
   [/@]
[/@]

[#if !mediaApply.persisted]
[@b.form name="copyForm" action="!edit"]
   <input type="hidden" name="lesson.id" value="${mediaApply.lesson.id}"/>
[/@]
[/#if]
[@b.foot/]