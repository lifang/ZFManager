<#import "../../page.ftl" as pager>
<#import "assign.ftl" as assign />
<div class="mask"></div>
    <div class="tab remark_tab">
    	<a href="#" class="close">关闭</a>
        <div class="tabHead">备注</div>
        <input type="hidden" id="bbbb">
        <div class="tabBody">
        	<textarea id="content" cols="" rows=""></textarea>
        </div>
        <div class="tabFoot"><button class="blueBtn" onclick="add()">确定</button></div>
    </div>
<div class="user_table">
	<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
		<colgroup>
			<col width="30" />
			<col />
			<col />
			<col />
			<col />
			<col width="160" />
		</colgroup>
		<thead>
			<tr>
				<th><input name="cb_all" type="checkbox" value="" /></th>
				<th>终端号</th>
				<th>申请日期</th>
				<th>状态</th>
				<th>视频认证状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<#if (apply.content)??> <#list apply.content as one> <#include "pageRow.ftl" /> </#list> </#if>
		</tbody>
	</table>
</div>

<@assign.assign name="certifiedopen" page=apply.currentPage suspend=1/>
<@pager.p page=apply.currentPage totalPages=apply.totalPage functionName="certifiedOpenPageChange"/>
