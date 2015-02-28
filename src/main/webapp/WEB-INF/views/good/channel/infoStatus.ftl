<div class="userTopBtnBox">
		<#if channel.status=1>
       		<a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="ghostBtn">编辑</a>
       		<a onClick="firstCheck(${channel.id})" class="ghostBtn">初审通过</a>
       		<a onClick="firstUnCheck(${channel.id})" class="ghostBtn">初审不通过</a>
            <a onClick="check(${channel.id})" class="ghostBtn">审核通过</a>
            <a onClick="unCheck(${channel.id})" class="ghostBtn">审核不通过</a>
       		
       <#elseif channel.status=2>
       		<a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="ghostBtn">编辑</a>
            <a onClick="firstCheck(${channel.id})" class="ghostBtn">初审通过</a>
       		
       <#elseif channel.status=3>
       		<a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="ghostBtn">编辑</a>
            <a onClick="check(${channel.id})" class="ghostBtn">审核通过</a>
       		<a onClick="unCheck(${channel.id})" class="ghostBtn">审核不通过</a>
       		
       <#elseif channel.status=4>
       		<a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="ghostBtn">编辑</a>
       		<a onClick="check(${channel.id})" class="ghostBtn">审核通过</a>
       		
       <#elseif channel.status=5>
            <a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="ghostBtn">编辑</a>
       		<a onClick="stop(${channel.id})" class="ghostBtn">停用</a>
       <#elseif channel.status=6>
       		<a href="<@spring.url "/good/channel/${channel.id}/edit" />" class="ghostBtn">编辑</a>
       		<a onClick="start(${channel.id})" class="ghostBtn">启用</a> 
       </#if>
</div>