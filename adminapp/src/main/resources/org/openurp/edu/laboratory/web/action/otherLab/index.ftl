[#ftl]
[@b.head/]
[@b.toolbar title="课外实验信息"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="labSearchForm" action="!search" target="lablist" title="ui.searchForm" theme="search"]
      [@b.select  name="semester.id" label="学年学期" items=semesters?sort_by("code") value=currentSemester option = "id,code" empty="..."/]
      [@b.textfields names="lab.name;名称"/]
      <input type="hidden" name="orderBy" value="lab.name"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="lablist" href="!search?orderBy=lab.name"/]
    </td>
  </tr>
</table>
[@b.foot/]