<#if customers??>
    <#list customers as customer>
    <a value="${customer.id}">${customer.username}</a>
    </#list>
</#if>