[#ftl]
[@b.head/]
[@b.toolbar title="多媒体软件申请信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">课程</td>
    <td class="content"> ${apply.lesson.no} ${apply.lesson.course.name} ${applyableActivityText}</td>
  </tr>
  <tr>
    <td class="title" width="20%">使用软件</td>
    <td class="content">${apply.software!}</td>
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