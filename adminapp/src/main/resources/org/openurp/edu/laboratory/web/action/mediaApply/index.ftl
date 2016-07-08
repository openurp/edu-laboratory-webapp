[#ftl]
[@b.head/]
[@b.toolbar title="教师课程实验征询列表"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="lessonLabForm" action="!search" target="lessonLablist" title="ui.searchForm" theme="search"]
      [@b.select  name="semester.id" label="学年学期" items=semesters?sort_by("code") value=currentSemester option = "id,code" empty="..."/]
      [@b.textfields names="lab.lesson.no;课程序号"/]
      [@b.textfields names="lab.lesson.name;课程名称"/]
      <input type="hidden" name="orderBy" value="lab.id"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="lessonLablist" href="!search?orderBy=lab.id"/]
    </td>
  </tr>
</table>
[@b.foot/]