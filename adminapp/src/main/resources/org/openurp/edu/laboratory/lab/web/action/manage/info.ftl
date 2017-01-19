[#ftl]
[@b.head/]
[@b.toolbar title="实验室申请信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">课程</td>
    <td class="content"> ${apply.lesson.no} ${apply.lesson.course.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">上课时间安排</td>
    <td class="content">${applyableActivityText}</td>
  </tr>
  <tr>
    <td class="title" width="20%">申请上机时间</td>
    <td class="content">${time}</td>
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
    <td class="title" width="20%">教师</td>
    <td class="content">${apply.borrower.name!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">联系电话</td>
    <td class="content">${apply.tel!}</td>
  </tr>
</table>
[@b.foot/]