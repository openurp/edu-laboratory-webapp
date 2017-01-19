[#ftl]
[@b.head/]
<div id="weekRoomDiv">
[@b.form name="weekTeacherRoomForm" action="!freeRoomList" target="freeRoomDiv"]
  	<input type="hidden" name="selectedWeekUnits" value=""/>
  	<input type="hidden" name="projectId" value=""/>
  	<input type="hidden" name="selectedWeeks" value=""/>
  	<input type="hidden" name="taskWeekStart" value="${lesson.courseSchedule.firstWeek}"/>
  	<input type="hidden" name="semesterStart" value="${lesson.semester.getStartWeek()}"/>
  	<input type="hidden" name="year" value="${lesson.semester.getStartYear()}"/>
  	<input type="hidden" name="semesterIdForFreeRoom" value="${lesson.semester.id}"/>
  	<input type="hidden" name='labroom.campus.id' value="${(lesson.campus.id)!}"/>
  	<input type="hidden" name="teachClassMaxCount" value="${lesson.teachClass.limitCount}"/>
  	<input type="hidden" name="labroom.courseCapacity" value="${apply.attendance}"/>
  	<input type="hidden" name="labroom.code" value=""/>
[/@]
  	[#assign courseName]${lesson.course.name}[/#assign]
	[@b.toolbar title="${b.text('info.manual.arrangeSetting')?replace('{0}',courseName)}"]
  		bar.addItem("确定","returnToCourseTable(true);");
		bar.addItem("取消","returnToCourseTable(false);","edit-delete.png");
	[/@]
	<table width="100%" align="center">
		<tr>
			<td width="40%" align="center" valign="top">
				<table style="width:100%">
					<tr>
						<td class="padding" style="width:25%">
						<input type="radio" name="occupyWeekType" id="occupyWeekType0" onclick="setOccupyWeek(2);" id=""/><label for="occupyWeekType0">单</label>
						<input type="radio" name="occupyWeekType" id="occupyWeekType1" onclick="setOccupyWeek(3);" id=""/><label for="occupyWeekType1">双</label>
						</td>
					 	<td class="padding" align="right" style="width:75%">
					 	[#if (lesson.teachers?size>1)]
					 	<input type="button" onclick="listTeacher(this)" value="${b.text('action.assignTeacher')}"/>
					 	[/#if]
					 	<input type="button" onclick="setResourse('', '待定', 'room')" value="待定"/>
					 	<input type="button" onclick="setResourse('', '', 'room')" value="清除"/>
					 	<input type="button" onclick="listFreeRoom(false,true);" style="buttonStyle" value="查教室"/>
					 	</td>
			    	</tr>
				    <tr>
				      <td colSpan="6" style="font-size:0px">
				          <img src="${b.theme.iconurl("actions/keyline.png")}" height="2" width="100%" align="top">
				      </td>
				   </tr>
	  			</table>
	  			<table class="gridtable" id="weekRoomTeacherTable">
	  				<thead>
	    			<tr>
	      				<th class="gridselect-top" style="background-color: #c7dbff;" width="15%">
	      					<input type="checkbox" onClick="clickWeekToggleBox(event)">
	      				</th>
				    	<th style="background-color: #c7dbff;" width="15%">${b.text('attr.teachWeek')}</th>
				    	<th style="background-color: #c7dbff;" width="28%">${b.text('entity.teacher')}</th>
				 	    <th style="background-color: #c7dbff;" width="42%">${b.text('entity.classroom')}</th>
	    			</tr>
	    			</thead>
	    			<tbody>
	    				[#assign lessonWeekList = lesson.courseSchedule.weekstate.weekList/]
						[#list lesson.courseSchedule.firstWeek..lesson.courseSchedule.lastWeek as week]
							[#assign weekDisabled=!(lessonWeekList?seq_contains(week))/]
						    <tr>
						        <td align="center">
						            <input [#if weekDisabled] disabled="disabled" style="display:none" [#else] onClick="clickWeekBox(event);"[/#if] type="checkBox" id="weekId${week_index+lesson.courseSchedule.firstWeek-1}"  name="weekId${week_index+lesson.courseSchedule.firstWeek-1}" value="${week}"/>
						            <input type="hidden" id="roomId${week_index+lesson.courseSchedule.firstWeek-1}" name="roomId${week_index+lesson.courseSchedule.firstWeek-1}" value=""/>
						            <input type="hidden" id="teacherId${week_index+lesson.courseSchedule.firstWeek-1}" name="teacherId${week_index+lesson.courseSchedule.firstWeek-1}" value=""/>
						        </td>
								<td align="center" id="weekName${week_index+lesson.courseSchedule.firstWeek-1}" onclick="changeRoomSelected(event);"><span [#if weekDisabled] style="font-style:italic"[/#if]>${(b.text('attr.nthWeek')?replace('{0}',week?string))!}</span></td>
						        <td align="center" id="teacherName${week_index+lesson.courseSchedule.firstWeek-1}" onclick="changeRoomSelected(event);"></td>
						 	    <td align="center" id="roomName${week_index+lesson.courseSchedule.firstWeek-1}"  onclick="changeRoomSelected(event);"></td>
						 	</tr>
						[/#list]
					</tbody>
	   			</table>
	   			<table class="gridtable">
	   				<thead>
	    			<tr>
	   					<th style="background-color: #c7dbff;">
	   						填写排课备注
	   					</th>
	   				</tr>
	   				</thead>
	    			<tbody>
	   				<tr>
	   					<td>
	   						<input id="activityRemark" name="activityRemark"  style="width:99%" maxLength="500"/>
	   					</td>
	   				</tr>
	   				</tbody>
	   			</table>
			</td>
			<td width="60%" align="center" valign="top">
				[@b.div id="freeRoomDiv"/]
			</td>
		</tr>
	</table>
<script language="JavaScript">
		/**
		 * 当鼠标经过工具栏的按钮时
		 * 
		 */
		function RoomMouseOver(e){
			var o=bg.event.getTarget(e);
			while (o&&o.tagName.toLowerCase()!="td"){o=o.parentNode;}
			if(o)o.className="room-td-item-transfer";
		}
		/**
		 * 当鼠标离开工具栏的按钮时
		 */
		function RoomMouseOut(e){
			var o=bg.event.getTarget(e);
			while (o&&o.tagName.toLowerCase()!="td"){o=o.parentNode;}
			if(o)o.className="room-td-item";
		}
</script>
</div>


[#assign reserved=["labroom.id","labroom.code","labroom.room.building.name","labroom.name","labroom.courseCapacity","labroom.campus.id"]/]
[@b.form name="freeRoomForm" action="!freeRoomList" id="freeRoomForm"]
	[#list Parameters?keys as k]
		[#if !reserved?seq_contains(k)]
			<input name="${k}" value="${Parameters[k]}" type="hidden"/>
		[/#if]
	[/#list]
	[@b.grid items=freeLabroomList var="labroom" sortable="false" filterable="true"]
		[@b.gridbar]
		bar.addItem("查找空闲教室","searchOccupancy(0)");
	 	bar.addItem("查找已占用教室","searchOccupancy(1)");
		[/@]
		[@b.gridfilter property="room.campus.id"]
			<select name="labroom.campus.id" style="width:95%" onchange="bg.form.submit(this.form)">
			<option value="">...</option>
			[#list campusList as campus]
			<option value="${campus.id}"  [#if (Parameters['labroom.campus.id']!"")="${campus.id}"]selected="selected"[/#if]>${campus.shortName!}</option>
			[/#list]
			</select>
		[/@]
		[@b.row]
			[@b.boxcol type="radio" width="5%"/]
			[@b.col width="30%" property="name" title="attr.infoname"/]
			[@b.col width="25%" property="room.building.name" title="教学楼" /]
			[@b.col width="10%" property="room.campus.id" title="校区" ]${(labroom.campus.shortName)!}[/@]
			[@b.col width="10%" property="courseCapacity" title="容量" /]
		[/@]
	[/@]
[/@]
<script language="JavaScript">
	document.weekTeacherRoomForm['labroom.courseCapacity'].value = '${Parameters['labroom.courseCapacity']!}';
 	var labrooms = new Array();
    [#list labroomList as labroom]
     labrooms[${labroom_index}]={'id':'${labroom.id}','name':'${labroom.name?replace('\n', '')}','courseCapacity':'${labroom.courseCapacity}'};
    [/#list]
	jQuery("#freeRoomForm tr").click(function(){
    	var roomId="";
    	var roomName = "";
    	var maxCap = 0;
    	jQuery("input[name='labroom.id']").each(function(i){
    		if(jQuery(this).prop('checked')){
    			if(roomId!=""){
        			roomId+=",";
        			roomName+=",";
            	}
        		roomId += labrooms[i].id;
         	roomName +=labrooms[i].name;
         	maxCap += parseInt(labrooms[i].courseCapacity);
    		}
    	})
    	if(roomId){
    		var maxCount = document.freeRoomForm.teachClassMaxCount.value;
        	if(maxCap<maxCount){
        		if(!confirm("所选教室不能容纳该教学班学生"+maxCap+"<"+document.freeRoomForm.teachClassMaxCount.value+",是否确认?")){
        			return;
        		}
        	}
        	parent.setResourse(roomId,roomName,"room");
        }else{
        	parent.setResourse("","","room");
        }
	});
	    
    function searchOccupancy(value){
    	parent.document.getElementById("confilctlabroom").value=value;
    	var form = document.freeRoomForm;
    	bg.form.addInput(form,"confilctlabroom",value);
    	bg.form.submit(form,null,null,null,null,true);
    }
</script>
[@b.foot/]