<tr>
	<td><input name="cb_row" type="checkbox" value="" cs_id="${one.id!}" cs_status="${one.status!}" cs_num="${one.serial_num!}"/></td>
	<td><#if one.credit??><i class="danger" title="${one.credit!}"></i></#if>${one.serial_num!}</td>
	<td>${one.created_at!}</td>
	<td>
		<strong class="strong_status">
		   <#if one.status=1>待初次预审
	       <#elseif one.status=2>初次预审不通过
	       <#elseif one.status=3>二次预审中  
	       <#elseif one.status=4>二次预审不通过
	       <#elseif one.status=5>待审核
	       <#elseif one.status=6>审核中
	       <#elseif one.status=7>审核失败
	       <#elseif one.status=8>审核成功
	       <#elseif one.status=9>已取消
       	   </#if>
		</strong>
	</td>
	<td> 
		   <#if one.video_status=0>无需认证
	       <#elseif one.video_status=1>待认证
	       <#elseif one.video_status=2>认证通过
	       <#elseif one.video_status=3>认证失败
       	   </#if>
   </td>
   <td>
   	   <#if one.status=1>
		   <#if Roles.hasRole("CERTIFIED_OPEN_FIRST_VERIFY")><a  onclick="ups(${one.id!},3,${apply.currentPage!},'${one.serial_num!}')" class="a_btn">初审通过</a></#if>
		   <#if Roles.hasRole("CERTIFIED_OPEN_SECOND_VERIFY")><a  onclick="ups(${one.id!},5,${apply.currentPage!},'${one.serial_num!}')" class="a_btn">二审通过</a></#if>
		   <#if Roles.hasRole("CERTIFIED_OPEN_FIRST_VERIFY")><a  onclick="ups(${one.id!},2,${apply.currentPage!},'${one.serial_num!}')" class="a_btn">初审失败</a></#if>
   	   <#elseif one.status=3>
		   <#if Roles.hasRole("CERTIFIED_OPEN_SECOND_VERIFY")>
			   <a  onclick="ups(${one.id!},5,${apply.currentPage!},'${one.serial_num!}')" class="a_btn">二审通过</a>
			   <a  onclick="ups(${one.id!},4,${apply.currentPage!},'${one.serial_num!}')" class="a_btn">二审失败</a>
		   </#if>
   	   </#if>
   	   <#if one.video_status=1>
		   <#if Roles.hasRole("CERTIFIED_OPEN_VIDEO_VERIFY")>
	   	   <a href="<@spring.url "/task/certifiedopen/${one.id}/video" />" class="a_btn" target="_blank">视频认证</a> 
	   	   <a  onclick="upvs(${one.id!},2,${apply.currentPage!})" class="a_btn">视频认证通过</a> 
	   	   <a  onclick="upvs(${one.id!},3,${apply.currentPage!})" class="a_btn">视频认证失败</a>
		   </#if>
       <#elseif one.video_status gt 1>
		   <#if Roles.hasRole("CERTIFIED_OPEN_VIDEO_VERIFY")><a  onclick="upvs(${one.id!},1,${apply.currentPage!})" class="a_btn">重置视频认证</a></#if>
   	   </#if>
   	   <a href="<@spring.url "/task/certifiedopen/${one.id}/info" />" class="a_btn" target="_blank">查看详情</a>
       <#if Roles.hasRole("CERTIFIED_OPEN_REMARK")><a  class="a_btn" onclick="beizhu(${one.id!})">备注</a></#if>
   </td>
</tr>
