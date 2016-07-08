[#ftl]
[@b.head/]
[@b.toolbar title="添加/修改教学任务实验计划"]bar.addBack();[/@]
[@b.form action="!saveLab?lesson.id=${lesson.id}"  name="actionForm"]
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
     <td class="title">总课时</td>
     <td class="content">${(lesson.schedule.period)!}</td>
     <td class="title">实验总学时</td>
     <td class="content">${(lab.labPeriod)!}</td>
     <td class="title">实验占总学时的比例</td>
     <td class="content">${(lab.labProportion)!}</td>
   </tr>
   <tr>
     <td class="title">实验项目数(只限数字)<font color="red">*</font></td>
     <td>[@b.textfield name="lab.itemCount" label="" value="${(lab.itemCount)!}" required="true" maxlength="20"/]
     <font color="red">提示：依据实验项目数，每一个实验项目安排字段填写一个实验项目</font>
     </td>
   </tr>
   [#list 1..5 as i]
   <tr>
     <td class="title">实验项目</td>
     <td>
      <textarea id="projectArrange" name="item.content" validtype="length[0,100]" maxlength="75" class="easyui-validatebox validatebox-text" invalidmessage="不能超过100个字符！" required="true"></textarea>
     </td>
   </tr>
   [/#list]
   <tr>
     <td>
       [@b.submit value="保存" class="btn btn-default"/]
       <input type="hidden" name="lab.id" value="${(lab.id)!}"/>
     </td>
   </tr>
</table>
[/@]
[@b.foot/]