[#ftl]
[@b.head/]
[@b.form name="lessonSearchForm" action="!search" target="contentDiv"]
[@b.grid items=lessons var="lesson" sortable="true"]
  [@b.gridbar title="实验室征询"]
            bar.addItem("添加实验室征询表", "addApplication()");
  [/@]
  [@b.row]
        [@b.boxcol /]
        [@b.col width="10%" property="no" title="课程序号"/]
        [@b.col width="10%" property="course.code" title="课程代码" /]
        [@b.col width="10%" property="course.name" title="课程名称" width="10%"/]
        [@b.col width="10%" property="teachDepart.name" title="开课院系" /]
        [@b.col width="10%" property="courseType.name" title="课程类别" /]
        [@b.col width="10%" property="name" title="上课地点" /]
        [@b.col width="10%" property="schedule.weektime" title="课程安排" /]
        [@b.col property="teachclass.name" title="教学班" width="30%"/]
[/@]
[/@]
[/@]
<script type="text/javaScript">
var form = document.lessonSearchForm;
    function addApplication(){
            var lessonIds = bg.input.getCheckBoxValues("lesson.id");
        if(lessonIds == "" || lessonIds.split(",").length !=1){
                alert("请选择一个进行操作！");
                return false;
        }
        alert(lessonIds)
//       bg.form.addInput(form,"lesson.id",lessonIds);
        bg.form.submit(form, "${b.url('!addApplication')}");
    }
</script>   
[@b.foot/]