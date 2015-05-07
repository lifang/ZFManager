<#import "../../common.ftl" as c /> <@c.html>
<div class="breadcrumb">
	<ul>
		<#if one.checktype=1>
			<li><a href="<@spring.url "/task/intention/list"/>">购买/申请意向</a></li> 
			<li><a href="<@spring.url "/task/intention/${one.id}/info"/>">详情</a></li>
		</#if>
		<#if one.checktype=2>
			<li><a href="<@spring.url "/task/agentjoin/list"/>">购买/申请意向</a></li>
			<li><a href="<@spring.url "/task/agentjoin/${one.id}/info"/>">详情</a></li>
		</#if>
	</ul>
</div>
<div class="content clear">
	<div class="user_title">
		<#if one.checktype=1>
		<h1>购买意向详情</h1>
		</#if>
		<#if one.checktype=2>
		<h1>申请代理商详情</h1>
		</#if>
		<div class="userTopBtnBox">
			<#if one.status=1><a onclick="ups(${one.id!},2,${one.checktype})" class="ghostBtn">标记为处理中</a>
   			<#elseif one.status=2><a onclick="ups(${one.id!},3,${one.checktype})" class="ghostBtn">标记为已处理</a></td>
  			</#if>
		</div>
	</div>
	<div class="attributes_box">
		<#if one.checktype=1>
		<h2>购买意向信息</h2>
		</#if>
		<#if one.checktype=2>
		<h2>申请代理商信息</h2>
		</#if>
		<div class="attributes_list_s clear">
			<ul>
				<li>处理人：${one.processname!}</li>
				<li>意向人：${one.name!}</li>
				<li>联系电话：${one.phone!}</li>
				<li>状态：   <#if one.status=1>待处理
	      				   <#elseif one.status=2>处理中
					       <#elseif one.status=3>处理完成
				       	   </#if></strong></td> </li>
			</ul>
		</div>
		<#if one.checktype=1>
			<div class="intention_text"><span>购买意向：</span><p>${one.content!}</p></div>
		</#if> 
		 <#if one.checktype=2>
			<div class="intention_text" style="padding-left:95px;"><span>代理商申请类型：</span><p>${one.type!}</p></div>
		</#if>
	</div>
	<div class="user_remark">
		<textarea id="content" cols="" rows=""></textarea>
		<button class="whiteBtn" onclick="add()">备注</button>
	</div>
	<div class="user_record">
		 <h2>追踪记录</h2>
		 <div id="mark_fresh"><#include "mark.ftl" /></div>
    </div>
</div>


<script type="text/javascript">
	var add = function() {
		var id = ${one.id};
		var content = $('#content').val();
		if ("" == content.trim()) {
			return;
		}
		$.post('<@spring.url "/task/intention/addmark" />', {
			"applyid" : id,
			"content" : content
		}, function(data) {
			$('#content').val('');
			$('#mark_fresh').html(data);
		});
	}
	var ups = function(id, status,type) {
		if(type == 1){
			$.post('<@spring.url "/task/intention/upstatus" />', {
			"id" : id,
			"status" : status
			}, function(data) {
				if (1 == data) {
					location.reload();
				} else {
					alert("操作失败!");
				}
			});	
		}
		
		if(type == 2){
			$.post('<@spring.url "/task/agentjoin/upstatus" />',
				{"id": id,"status":status},
		        function (data) {
					if(1==data){
						location.reload();
					}else{
						alert("操作失败!");
					}
			});
		}
		
	}
</script>
</@c.html>
