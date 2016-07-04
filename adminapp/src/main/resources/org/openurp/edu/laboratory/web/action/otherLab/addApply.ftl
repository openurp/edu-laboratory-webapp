[#ftl]
[@b.head/]
<link rel="stylesheet" href="${base}/static/jquery/css/chosen.css"/>
<script src="${base}/static/jquery/jquery-chosen.js"></script>
[@b.toolbar title="添加实验征询表"]bar.addBack();[/@]
[@b.form action="!saveApply?lab.id=${lab.id}"  name="actionForm"]
<table class="infoTable">
   <tr>
     <td class="title">学年学期</td>
     <td class="content">${(lab.semester.code)!}</td>
   </tr>
   <tr>
     <td class="title">实验课程名称</td>
     <td class="content">${(lab.name)!}</td>
   </tr>
   <tr>
     <td class="title">联系电话</td>
     <td class="content"> ${(lab.tel)!}</td>
   </tr>
   <tr>
     <td class="title">听课对象</td>
     <td class="content">${(lab.audience)!}</td>
   </tr>
   <tr>
     <td class="title">上机人数</td>
     <td class="content">${(lab.stdCount)!}</td>
   </tr>
   <tr>
     <td class="title">实验室所在教学楼<font color="red">*</font></td>
     <td colspan="7">[@b.select name="apply.labBuilding.id" label="" items=buildings?sort_by("name")  option = "id,name" value = (apply.labBuilding)! empty="..." /]</td>
   </tr>
   <tr>
     <td class="title">实验上机时间<font color="red">*</font></td>
     <td colspan="7">XXXXX</td>
   </tr>
   <tr>
     <td class="title">所需软件<font color="red">*</font></td>
     <td >
        [@b.field]
           <select id="softwares" multiple="true" name="fake.softwares" style="width:400px;" >
              [#list softwares! as tag]
              <option value="${tag.id}" >${tag.name}</option>
              [/#list]
           </select>
        [/@]
     </td>
   </tr>
   <tr>
     <td>
       [@b.submit value="保存" class="btn btn-default"/]
       <input type="hidden" name="lab.id" value="${(lab.id)!}"/>
     </td>
   </tr>
</table>
[/@]
<script language="JavaScript">
  jQuery("#softwares").chosen({width:"400px"});
</script>
[@b.foot/]
