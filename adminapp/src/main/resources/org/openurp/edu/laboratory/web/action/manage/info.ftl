[#ftl]
[@b.head/]
[@b.toolbar title="征询表信息"]
  bar.addBack("${b.text("action.back")}");
[/@]
<table class="infoTable">
  <tr>
    <td class="title" width="20%">代码</td>
    <td class="content">${software.code}</td>
  </tr>
  <tr>
    <td class="title" width="20%">名称</td>
    <td class="content">${software.name}</td>
  </tr>
  <tr>
    <td class="title" width="20%">操作系统</td>
    <td class="content">${software.operation.name!}</td>
  </tr>
  <tr>
    <td class="title" width="20%">版本</td>
    <td class="content">${software.version!}</td>
  </tr>
  [#if software.classrooms ??]
  <tr>
    <td class="title" width="20%">所属实验室</td>
    <td class="content">
      [#list software.classrooms as classroom]
        ${classroom.name}
        [#if classroom_has_next],[/#if]
      [/#list]
    </td>
  </tr>
  [/#if]
  <tr>
    <td class="title" width="20%">备注</td>
    <td class="content">${software.remark!}</td>
  </tr>
</table>

[@b.foot/]