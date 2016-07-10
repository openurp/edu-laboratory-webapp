[#ftl]
[@b.head/]

[@b.toolbar title="新增和修改实验室申请"]
  bar.addBack();
[/@]

[@b.form action="!saveByLesson" theme="list" name="applyForm"]
   [@b.field label="课程"]
     ${apply.lesson.no} ${apply.lesson.course.name} ${applyableActivityText}
   [/@]
   [@b.textfield label="联系电话" name="apply.tel" required="true" value=apply.tel! maxlength="15"/]
   
   [#list times as time]
   [@b.field label="时间"+(time_index+1)]
     [#assign units = timeSetting.getUnitSpan(time.beginAt,time.endAt)]
     ${time.weekday.name} ${units._1}-${units._2}  [#assign weekList =time.weekstate.weekList]
     [#list weekList as week]
        [#assign name="weekId_"+time.weekday.id+"_"+time.beginAt+"_"+time.endAt]
        [#if existedDates?seq_contains(time.getDate(week))]
        [#assign checked = true]
        [#else][#assign checked = false]
        [/#if ]
		<input id="${name}" name="${name}" class="weekCheck${time_index} weekCheck ui-helper-hidden-accessible" type="checkbox" value="${week}" [#if checked]checked[/#if]>
		<label style="cursor:pointer;background:white;color:${checked?string('black','white')}" class="ui-widget ui-state-active ui-button-text-only [#if week_index==0]ui-corner-left[#elseif !week_has_next]ui-corner-right[/#if]" role="button" title="${time.getDate(week)?string("yyyy-MM-dd")}"> 
		 <span class="ui-button-text">${week}</span>
		</label>
	 [/#list]
	 &nbsp;
	 <select id="cycle_${time_index}"><option value="0">连续周</option><option value="1">单周</option><option value="2">双周</option></select><input type="button" id="selectReverse_${time_index}" value="反选"/>
   [/@]
   [/#list]
   
   [@b.field label='使用软件']
	 <select id="software_select" multiple="true" name="software.id" style="width:600px;">
	 [#list softwares as software]
	   <option value='${software.id}' [#if apply.softwares?seq_contains(software)]selected[/#if]>${software.name}</option>
	 [/#list]
     </select>
    [/@]
   [@b.formfoot id="footer"]
     [#if apply.persisted]
        <input type="hidden" name="apply.id" value="${apply.id}"/>
     [#else]
        <input type="hidden" name="lesson.id" value="${apply.lesson.id}"/>
     [/#if]
      [@b.submit value="提交"/]
   [/@]
[/@]
<script>
function changeWeek(cycle,timeIndex){
	switch(parseInt(cycle)){
   		case 0:
   			jQuery(".weekCheck"+timeIndex).prop("checked",true).next("label").css("color","black");
     		break;
	   	case 1:
	   		jQuery(".weekCheck"+timeIndex).each(function(i){
	   			if(this.value%2==1){
	   				jQuery(this).prop("checked",true).next("label").css("color","black");
	   			}else{
	   				jQuery(this).prop("checked",false).next("label").css("color","white");
	   			}
	   		})
			break;
	   	case 2:
	   		jQuery(".weekCheck"+timeIndex).each(function(i){
	   			if(this.value%2==0){
	   				jQuery(this).prop("checked",true).next("label").css("color","black");
	   			}else{
	   				jQuery(this).prop("checked",false).next("label").css("color","white");
	   			}
	   		})
	   		break;
   }
}
function reverseCheckWeek(timeIndex){
	jQuery(".weekCheck"+timeIndex).each(function(i){
		if(jQuery(this).prop("checked")){
			jQuery(this).prop("checked",false).next("label").css("color","white");
		}else{
			jQuery(this).prop("checked",true).next("label").css("color","black");
		}
	})
}
			
jQuery(document).ready(function(){
    jQuery("label[role='button']").click(function(){
		if(jQuery(this).prev(":checkbox").prop("checked")){
			jQuery(this).css("color","white").prev(".weekCheck").prop('checked',false);
		}else{
			jQuery(this).css("color","black").prev(".weekCheck").prop("checked",true);
		}
	});
	[#list times as time]
	jQuery("#cycle_${time_index}").change(function(){changeWeek(jQuery(this).val(),${time_index})});
	jQuery("#selectReverse_${time_index}").click(function(){ reverseCheckWeek(${time_index})});
	[/#list]
	jQuery("#software_select").chosen({width:"400px"});
});


</script>
[@b.foot/]