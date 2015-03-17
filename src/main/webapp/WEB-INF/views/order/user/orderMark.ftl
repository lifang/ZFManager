<#if order.orderMarks??>
	<#list order.orderMarks as orderMark>
        <div class="ur_item">
        	<div class="ur_item_text">${orderMark.content!""}</div>
            <div class="ur_item_name"><#if orderMark.customer??>${orderMark.customer.name!""}</#if> <em>${orderMark.createdAt?datetime}</em></div>
        </div>
    </#list>
</#if>