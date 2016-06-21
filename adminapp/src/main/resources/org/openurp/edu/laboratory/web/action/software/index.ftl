[#ftl]
[@b.head/]
[@b.toolbar title="软件资源"/]
<table class="indexpanel">
  <tr>
    <td class="index_view">
    [@b.form name="softwareSearchForm" action="!search" target="softwarelist" title="ui.searchForm" theme="search"]
      [@b.textfields names="software.code;代码"/]
      [@b.textfields names="software.name;名称"/]
      [@b.select name="software.os.id" label="操作系统" value="${(software.os.id)!}" required="true" 
               style="width:100px;" items=oses option="id,name" empty="..."/]
      <input type="hidden" name="orderBy" value="software.code"/>
    [/@]
    </td>
    <td class="index_content">[@b.div id="softwarelist" href="!search?orderBy=software.code"/]
    </td>
  </tr>
</table>
[@b.foot/]