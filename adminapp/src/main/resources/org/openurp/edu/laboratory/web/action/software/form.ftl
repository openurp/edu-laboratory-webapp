[#ftl]
[@b.head/]
<link rel="stylesheet" href="${base}/static/jquery/css/chosen.css"/>
<script src="${base}/static/jquery/jquery-chosen.js"></script>
[@b.toolbar title="修改软件资源"]bar.addBack();[/@]
[@b.tabs]
  [#assign sa][#if software.id?? && software.id!=0]!update?id=${software.id!}[#else]!save[/#if][/#assign]
  [@b.form action=sa theme="list" name="actionForm"]
    [@b.textfield name="software.code" label="代码" value="${software.code!}" required="true" maxlength="20"/]
    [@b.textfield name="software.name" label="名称" value="${software.name!}" required="true" maxlength="20"/]
    [@b.field label="操作系统" required="true"]
      <select id="oses" multiple="true" name="fake.oses" style="width:400px;" >
          [#list oses! as tag]
          <option value='${tag.id}' [#if software.oses?seq_contains(tag)]selected[/#if]>${tag.name}</option>
          [/#list]
        </select>
    [/@]
    [@b.textfield name="software.version" label="版本" value="${software.version!}" required="true" maxlength="20"/]
    [@b.field label="实验室"  required="true"]
      <select id="classrooms" multiple="true" name="fake.guaPai" style="width:400px;">
          [#list classrooms! as tag]
          <option value='${tag.id}' [#if software.classrooms?seq_contains(tag)]selected[/#if]>${tag.name}</option>
          [/#list]
        </select>
    [/@]
    [@b.formfoot]
      [@b.reset/]&nbsp;&nbsp;[@b.submit value="action.submit"/]
    [/@]
  [/@]
[/@]
<script language="JavaScript">
  jQuery("#classrooms").chosen({width:"400px"});
  jQuery("#oses").chosen({width:"400px"});
</script>
[@b.foot/]
