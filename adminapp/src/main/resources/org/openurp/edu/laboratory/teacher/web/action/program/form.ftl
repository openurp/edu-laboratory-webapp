[#ftl]
[@b.head/]
[@b.toolbar title="新增和修改实验计划"]
  bar.addBack();
[/@]
[@b.form action="!save" theme="list" name="programForm"]
   [@b.field label="课程"]
     ${program.lesson.no} ${program.lesson.course.name} ${program.lesson.courseSchedule.period}课时
   [/@]
   [#if !program.persisted && sameCoursePrograms?size>0]
     [@b.select label="拷贝自" name="copyProgram.id" onchange="copyFrom(this.value)" items=sameCoursePrograms empty="..." option=r"${item.lesson.no} ${item.lesson.course.name}"/]
   [/#if]
   [@b.textfield label="实验学时" required="true" name="program.period" value=program.period check="greaterThanOrEqualTo(1)"/]
   [@b.textfield label="实验项目数" required="true" name="program.itemCount" value=program.itemCount onchange="increseTest(this.value)" check="greaterThanOrEqualTo(1)"/]
   [@b.field label='使用软件' required="true"]
	 <select id="software_select" multiple="true" name="software.id" style="width:600px;">
	 [#list softwares as software]
	   <option value='${software.id}' [#if program.softwares?seq_contains(software)]selected[/#if]>${software.name}</option>
	 [/#list]
     </select>
    [/@]
   [#list program.tests as test]
     [#if test.persisted]
     [@b.textarea label="实验"+(test_index+1)+"的内容" name="test_id_"+test.id  cols="80" rows="4" value=test.content/]
     [#else]
     [@b.textarea label="实验"+(test_index+1)+"的内容" name="test_idx_"+(test_index+1)  cols="80" rows="4" value=test.content/]
     [/#if]
   [/#list]
   [@b.validity]jQuery("#software_select").assert(function(){ if(jQuery("#software_select").val())return true;else return false;}, "请填写软件");[/@]
   [@b.formfoot id="footer"]
     [#if program.persisted]
        <input type="hidden" name="program.id" value="${program.id}"/>
     [#else]
        <input type="hidden" name="lesson.id" value="${program.lesson.id}"/>
     [/#if]
      [@b.submit value="提交"/]
   [/@]
[/@]

[#if !program.persisted]
[@b.form name="copyForm" action="!edit"]
   <input type="hidden" name="lesson.id" value="${program.lesson.id}"/>
[/@]
[/#if]
<script>
  jQuery("#software_select").chosen({width:"400px"});
  var maxTestIdx=${program.tests?size}
  function increseTest(num){
    if(num>15){
      alert("最大不能超过15个项目");
      document.programForm['program.itemCount'].value="0";
      return;
    }
    if(num > maxTestIdx){
      var template ='<li><label for="test_idx_{n}" class="title">实验{n}的内容:</label><textarea id="test_idx_{n}" cols="80" rows="4" name="test_idx_{n}" maxlength="100"></textarea></li>'
      while(maxTestIdx<num){
        var nextLi = template.replace(/\{n\}/g,maxTestIdx+1)
        jQuery(nextLi).insertBefore('#programForm > fieldset > ol > li:last-child');
        maxTestIdx+=1;
      }
    }
  }
  [#if !program.persisted]
  function copyFrom(programId){
     if(programId) {
       bg.form.addInput(document.copyForm,"copyProgram.id",programId);
       bg.form.submit(document.copyForm)
     }
  }
  [/#if]
</script>
[@b.foot/]