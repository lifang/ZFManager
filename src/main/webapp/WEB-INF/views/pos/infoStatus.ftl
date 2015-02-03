<div class="userTopBtnBox">
		<#if good.status=1>
       		<a href="<@spring.url "/pos/${good.id}/edit" />" class="ghostBtn">编辑</a> 
       		<a onClick="firstCheck(${good.id})" class="ghostBtn">初审通过</a>
       		<a onClick="firstUnCheck(${good.id})" class="ghostBtn">初审不通过</a>
       		
       <#elseif good.status=2>
       		<a href="<@spring.url "/pos/${good.id}/edit" />" class="ghostBtn">编辑</a> 
            <a onClick="firstCheck(${good.id})" class="ghostBtn">初审通过</a>
       		
       <#elseif good.status=3>
       		<a href="<@spring.url "/pos/${good.id}/edit" />" class="ghostBtn">编辑</a> 
       		<a class="ghostBtn approve_a">审核通过</a> 
       		<a onClick="unCheck(${good.id})" class="ghostBtn">审核不通过</a>
       		
       <#elseif good.status=4>
       		<a href="<@spring.url "/pos/${good.id}/edit" />" class="ghostBtn">编辑</a> 
       		<a onClick="check(${good.id})" class="ghostBtn approve_a">审核通过</a> 
       		
       <#elseif good.status=5>
       		<a onClick="stop(${good.id})" class="ghostBtn">停用</a>
       <#elseif good.status=6>
       		<a href="<@spring.url "/pos/${good.id}/edit" />" class="ghostBtn">编辑</a> 
       		<a onClick="start(${good.id})" class="ghostBtn">启用</a> 
       </#if>
</div>