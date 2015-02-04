<#if goods??>
<#list goods as good>
<a value="${good.id}">${good.title}</a>
</#list>
</#if>