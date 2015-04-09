<tr>
	<td><input name="" type="checkbox" value="" /></td>
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
	   	   <a  onclick="ups(${one.id!},3,${apply.currentPage!})" class="a_btn">初审通过</a> 
	   	   <a  onclick="ups(${one.id!},5,${apply.currentPage!})" class="a_btn">二审通过</a> 
	   	   <a  onclick="ups(${one.id!},2,${apply.currentPage!})" class="a_btn">预审失败</a>
   	   <#elseif one.status=3>
	   	   <a  onclick="ups(${one.id!},5,${apply.currentPage!})" class="a_btn">二审通过</a> 
	   	   <a  onclick="ups(${one.id!},4,${apply.currentPage!})" class="a_btn">预审失败</a>
   	   </#if>
   	   <#if one.video_status=1>
	   	   <a href="<@spring.url "/task/certifiedopen/${one.id}/video" />" class="a_btn">视频认证</a> 
	   	   <a  onclick="upvs(${one.id!},2,${apply.currentPage!})" class="a_btn">视频认证通过</a> 
	   	   <a  onclick="upvs(${one.id!},3,${apply.currentPage!})" class="a_btn">视频认证失败</a>
       <#elseif one.video_status gt 1>
      	   <a  onclick="upvs(${one.id!},1,${apply.currentPage!})" class="a_btn">重置视频认证</a> 
   	   </#if>
   	   <a href="<@spring.url "/task/certifiedopen/${one.id}/info" />" class="a_btn">查看详情</a> 
   	   <a  class="a_btn">备注</a>
   </td>
</tr>
