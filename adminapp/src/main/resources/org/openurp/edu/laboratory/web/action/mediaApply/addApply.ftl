[#ftl]
[@b.head/]
<link rel="stylesheet" href="${base}/static/jquery/css/chosen.css"/>
<script src="${base}/static/jquery/jquery-chosen.js"></script>
[@b.toolbar title="添加实验征询表"]bar.addBack();[/@]
[@b.form action="!saveApply?lab.id=${lab.id}"  name="actionForm"]
<table class="infoTable">
   <tr>
     <td class="title">课程类别名称</td>
     <td class="content">${(lesson.courseType.name)!}</td>
     <td class="title">学年度学期</td>
     <td class="content">${(lesson.semester.code)!}</td>
     <td class="title">课程序号</td>
     <td class="content">${(lesson.no)!}</td>
   </tr>
   <tr>
     <td class="title">课程名称</td>
     <td class="content">${(lesson.course.name)!}</td>
     <td class="title">教学班级名称</td>
     <td class="content">${(lesson.teachclass.name)!}</td>
     <td class="title">实际人数</td>
     <td class="content">${(lesson.teachclass.stdCount)!}</td>
   </tr>
   <tr>
     <td class="title">课程代码</td>
     <td class="content">${(lesson.course.code)!}</td>
     <td class="title">开课院系</td>
     <td class="content">${(lesson.teachDepart.name)!}</td>
     <td class="title">授课教师</td>
     <td class="content">
        [#list lesson.teachers as teacher]
           ${(teacher.user.name)!}
           [#if teacher_has_next],[/#if]
        [/#list]
     </td>
   </tr>
   <tr>
     <td class="title">实际安排</td>
     <td class="content">${(lesson.schedule.weekstate)!}</td>
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