[#ftl]
[@b.head/]
[@b.grid items=softwares var="software"]
  [@b.gridbar]
    bar.addItem("${b.text("action.new")}",action.add());
    bar.addItem("${b.text("action.modify")}",action.edit());
    bar.addItem("${b.text("action.delete")}",action.remove("确认删除?"));
  [/@]
  [@b.row]
    [@b.boxcol /]
    [@b.col width="10%" property="code" title="代码"]${software.code}[/@]
    [@b.col width="15%" property="name" title="名称"][@b.a href="!info?id=${software.id}"]${software.name}[/@][/@]
     [@b.col width="15%" property="oses" title="操作系统"]
       [#list software.oses as os]
         ${(os.name)!}
        [#if os_has_next],[/#if]
       [/#list]
    [/@]
    [@b.col width="10%" property="version" title="版本"]${software.version!}[/@]
    [@b.col width="15%" property="classrooms" title="所属实验室"]
       [#list software.classrooms as classroom]
         ${(classroom.name)!}
        [#if classroom_has_next],[/#if]
       [/#list]
    [/@]
  [/@]
  [/@]
[@b.foot/]
