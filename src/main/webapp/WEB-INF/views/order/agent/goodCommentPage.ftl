<#import "../../page.ftl" as pager>
 
    	
    	<#if goodComments.content??>
    		<#list goodComments.content as goodComment>
        <div class="evaluate_item">
        	<div class="evaluate_star">
            	<ul>
            		<#if goodComment.score gte 5>
                    	<li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li  class="p_li_o"></li>
                    <#elseif goodComment.score gte 4>
                    	<li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li></li>
                    <#elseif goodComment.score gte 3>
                    	<li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li></li><li></li>
                    <#elseif goodComment.score gte 2>
                    	<li class="p_li_o"></li><li class="p_li_o"></li><li></li><li></li><li></li>
                    <#elseif goodComment.score gte 1>
                    	<li class="p_li_o"></li><li></li><li></li><li></li><li></li>
                    <#else>
                    	<li></li><li></li><li></li><li></li><li></li>
                    </#if>
                </ul>
            </div>
            <div class="evaluate_text">${goodComment.content!""}</div>
			<div class="evaluate_name">
            	<h3><#if goodComment.customer??>${goodComment.customer.name!""}</#if></h3>
                <span>${goodComment.createdAt?string("yyyy-MM-dd   HH:mm:ss")}</span>
            </div>
        </div>
        	</#list>
		</#if>
    
 
<@pager.p page=goodComments.currentPage totalPages=goodComments.totalPage functionName="goodCommentPageChange"/>