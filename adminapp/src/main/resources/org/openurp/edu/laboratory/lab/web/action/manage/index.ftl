[#ftl]
[@b.head/]
[@b.toolbar title="实验室申请列表"/]
[@eams.semesterBar semesterValue=semester formName="lessonForm" submit="searchTable()" name="project.id" semesterName="semester.id" semesterEmpty=false initCallback="searchTable()"]
[/@]
<table class="indexpanel">
	<tr>
		<td class="index_content">
			[@b.div id="contentDiv"/]
		</td>
	</tr>
</table>
<script language="JavaScript">
   	function searchTable(){
   		var form = document.lessonForm;	
		form.action="${b.url('!list')}";
		form.target="contentDiv";
       	bg.form.submit(form);
       	form.action="${b.url('!index')}";
       	form.target="main";
   	}
</script>
[@b.foot/]
