[#ftl]
[@b.head/]
<link rel="stylesheet" href="${base}/static/jquery/css/chosen.css"/>
<script src="${base}/static/jquery/jquery-chosen.js"></script>
[@b.toolbar title="实验教学任务需求信息临时征询表"]bar.addBack();[/@]
[#assign sa]!saveApplication[/#assign]
[@b.form action=sa  name="applyForm"]
<table  id="my_show">
  <tbody>
    <tr>
      <th>课程类别名称</th><td>${(lesson.courseType.name)!}</td>
      <th>学年度学期</th><td colspan="3">${(lesson.semester.code)!}</td>
      <th>课程序号</th><td>${(lesson.no)!}</td>
    </tr>
   <tr>
     <th>课程名称</th><td>${(lesson.course.name)!}</td>
     <th>教学班级名称</th><td>${(lesson.teachclass.name)!}</td>
     <th>专业名称</th><td>X</td>
     <th>实际人数</th><td>${(lesson.teachclass.stdCount)!}</td>
   </tr>
   <tr>
     <th>课程代码</th><td>${(lesson.course.code)!}</td>
     <th>开课院系</th><td>${(lesson.teachDepart.name)!}</td>
     <th>授课教师</th><td>[#list lesson.teachers as teacher]
                           ${(teacher.user.name)!}
                           [#if teacher_has_next],[/#if]
                         [/#list]</td>
     <th>学生类别</th><td>XXXX</td>
   </tr>
   <tr>
     <th>实际安排</th><td colspan="7">${(lesson.schedule.weekstate)!}</td>
   </tr>
   <tr>
     <th>总课时</th><td>${(lesson.schedule.period)!}</td>
     <th>计划实验总学时(只限数字)<font color="red">*</font></th><td>[@b.textfield name="apply.labPeriod" label="" value="${(apply.labPeriod)!}" required="true" maxlength="20"/]</td>
     <th>实验占总学时的比例<font color="red">*</font></th><td>[@b.textfield name="apply.labProportion" label="" value="${(apply.labProportion)!}" required="true" maxlength="20"/]</td>
     <th>实验项目数(只限数字)<font color="red">*</font></th><td>[@b.textfield name="apply.itemCount" label="" value="${(apply.itemCount)!}" required="true" maxlength="20"/]</td>
   </tr>
   <tr>
      <th>实验室所在教学楼</th><td colspan="7">[@b.select name="apply.labBuilding.id" label="" items=buildings?sort_by("name")  option = "id,name" value = (apply.labBuilding)! empty="..." /]</td>
   </tr>
   <tr>
      <th>实验上机时间<font color="red">*</font></th><td colspan="7">XXXXX</td>
   </tr>
   <tr>
      <th>所需软件<font color="red">*</font></th>
          <td >[@b.field]
              <select id="softwares" multiple="true" name="fake.softwares" style="width:400px;" >
                  [#list softwares! as tag]
                  <option value="${tag.id}" >${tag.name}</option>
                  [/#list]
                </select>[/@]
         </td>
   </tr>
   <tr>
       <th>实验项目安排<font color="red">*</font></th>
         <td colspan="7">
             <textarea id="projectArrange" name="projectArrange" validtype="length[0,100]" maxlength="75" class="easyui-validatebox validatebox-text" invalidmessage="不能超过100个字符！" required="true"></textarea>
             <font color="red">提示：依据实验项目数，每一个实验项目安排字段填写一个实验项目安排</font>
         </td>
   </tr>
   <tr>
     <td>
       [@b.submit value="保存" class="btn btn-default"/]
       <input type="hidden" name="apply.id" value="${(apply.id)!}"/>
     </td>
   </tr>
   </tbody></table>    
[/@]
  <script language="JavaScript">
  jQuery("#softwares").chosen({width:"400px"});
</script>
[@b.foot/]