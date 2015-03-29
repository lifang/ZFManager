<#if channels??>
<#list channels as channel>
<a value="${channel.id}">${channel.name}</a>
</#list>
</#if>