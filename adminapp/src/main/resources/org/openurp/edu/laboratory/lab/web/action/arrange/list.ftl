[#ftl]
[@b.form name="applyListForm" id="applyListForm" action="!list" ]
[@b.grid items=applyList var="apply" sortable="true"]
    [@b.gridbar]
      bar.addItem("${b.text('安排机房')}","adjust()");
  [/@]
	[@b.row]
	  [@b.boxcol width="3%"/]
		[@b.col width="5%" property="lesson.no" title="attr.taskNo"]${apply.lesson.no?if_exists}[/@]
		[@b.col width="20%" property="lesson.course.name" title="attr.courseName"]${apply.lesson.course.name?if_exists}[/@]
		[@b.col width="11%" property="lesson.courseType.name" title="entity.courseType"]${apply.lesson.courseType.name?if_exists}[/@]
		[@b.col width="17%" property="lesson.teachClass.name" title="entity.teachClass"]${apply.lesson.teachClass.name?if_exists}[/@]
		[@b.col width="7%" property="borrower.name" title="申请教师"]${apply.borrower.name?if_exists}[/@]
		[@b.col width="5%" property="attendance" title="上课人数"/]
		[@b.col width="15%"  title="上机时间"]${times.get(apply)!}[/@]
    [@b.col width="11%" title="实验室申请"][@b.a href="!info?apply.id=${apply.id}"]查看[/@][/@]
	[/@]
[/@]
[/@]
<script language="JavaScript">
  var canSubmit = false;
    function adjust(){
      canSubmit = false;
      var form= document.applyListForm;
        var applyId = bg.input.getCheckBoxValues("apply.id");
        if (applyId=="" || applyId==null ||applyId.indexOf(",")>-1) {
            alert("请选择一个进行调整");
            return;
        }
        bg.form.addInput(form, "apply.id", applyId);
        canSubmit = true;
        bg.form.submit(form,"arrange!manualArrange.action");
        form.action ="manualArrange!applyList.action";
    }
</script>