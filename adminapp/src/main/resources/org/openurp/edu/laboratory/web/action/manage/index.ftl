[#ftl]
[@b.head/]
[@b.toolbar title="教师需求征询列表"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="labApplySearchForm" action="!search" target="labApplylist" title="ui.searchForm" theme="search"]
      [@b.select  name="semester.id" label="学年学期" items=semesters?sort_by("code") value=currentSemester option = "id,code" empty="..."/]
      [@b.textfields names="labApply.lesson.no;课程序号"/]
      [@b.textfields names="labApply.lesson.code;课程代码"/]
      [@b.textfields names="labApply.lesson.name;课程名称"/]
      [@b.textfields names="labApply.teacher.user.name;教师"/]
      [@b.textfields names="labApply.name;名称"/]
      <input type="hidden" name="orderBy" value="labApply.id"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="labApplylist" href="!search?orderBy=labApply.id"/]
    </td>
  </tr>
</table>
[@b.foot/]