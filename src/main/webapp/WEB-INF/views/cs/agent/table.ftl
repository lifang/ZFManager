<#import "../../page.ftl" as pager>
<#import "../assign.ftl" as assign />
<div class="uesr_table"> 
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table"> 
		<colgroup> 
			<col width="30"> 
			<col />
			<col />
			<col />
			<col />
			<col />
			<col width="160" /> 
        </colgroup> 
		<thead> 
			<tr> 
				<th><input name="cb_all" type="checkbox" value="" /></th>
                <th>售后单号</th>
                <th>申请日期</th>
                <th>处理人</th>
				<th>终端数量</th>
				<th>售后状态</th>
				<th>操作</th>
			</tr> 
		</thead> 
		<tbody> 
		<#if (csAgents.content)??>
			<#list csAgents.content as csAgent>
				<#include "row.ftl" />
			</#list>
		</#if>
		</tbody> 
	</table> 
</div>

<div class="tab exchangeGoods_tab">
	<a class="close">关闭</a>
	<div class="tabHead">添加换货出库记录</div>
	<div class="tabBody">
		<textarea id="output_content" name="" cols="" rows="" class="textarea_pe"></textarea>
	</div>
	<div class="tabFoot">
		<button class="blueBtn close" onClick="onOutput();">确定</button>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		popup(".exchangeGoods_tab",".exchangeGoods_a");//添加换货出库记录
	});
	
</script>
<@assign.assign name="agent" page=csAgents.currentPage/>
<@pager.p page=csAgents.currentPage totalPages=csAgents.totalPage functionName="pageChange"/>	