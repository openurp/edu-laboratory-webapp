[#ftl]
[@b.head/]
[@b.toolbar title="实验室申请信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">课程</td>
    <td class="content"> ${apply.lesson.no} ${apply.lesson.course.name} ${applyableActivityText}</td>
  </tr>
  <tr>
    <td class="title" width="20%">时间</td>
    <td class="content">XXXXXXXXXXXXX</td>
  </tr>
  <tr>
    <td class="title" width="20%">使用软件</td>
    <td class="content">
       [#list apply.softwares as software]
         ${software.name!}
         [#if software_has_next],[/#if]
       [/#list]
   </td>
  </tr>
  <tr>
    <td class="title" width="20%">联系电话</td>
    <td class="content">${apply.tel!}</td>
  </tr>
</table>

[@b.foot/]