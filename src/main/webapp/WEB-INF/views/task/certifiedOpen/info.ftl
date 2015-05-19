<#import "../../common.ftl" as c /> <@c.html>
<div class="breadcrumb">
	<ul>
		<li>任务</li>
		<li><a href="<@spring.url "/task/certifiedopen/list"/>">认证开通</a></li>
		<li><a href="<@spring.url "/task/certifiedopen/${tinfo.id}/info"/>">详情</a></li>
	</ul>
</div>
<div class="content clear">
	<div class="user_title">
		<h1>
			开通申请详情<#if tinfo.credit??><i class="danger" title="${tinfo.credit!}"></i></#if>
		</h1>
		<div class="userTopBtnBox">
		
			<#if tinfo.status=1>
		   	   <a  onclick="ups(${tinfo.id!},3)" class="ghostBtn">初审通过</a> 
		   	   <a  onclick="ups(${tinfo.id!},5)" class="ghostBtn">二审通过</a> 
		   	   <a  onclick="ups(${tinfo.id!},2)" class="ghostBtn">初审失败</a>
	   	   <#elseif tinfo.status=3>
		   	   <a  onclick="ups(${tinfo.id!},5)" class="ghostBtn">二审通过</a> 
		   	   <a  onclick="ups(${tinfo.id!},4)" class="ghostBtn">二审失败</a>
	   	   <#elseif tinfo.status=5>
		   	   <a  onclick="ups(${tinfo.id!},6)" class="ghostBtn">提交开通申请</a> 
	   	   </#if>
	   	   <#if tinfo.video_status=1>
   	  		   <a href="<@spring.url "/task/certifiedopen/${tinfo.id}/video" />" class="ghostBtn">视频认证</a>
		   	   <a  onclick="upvs(${tinfo.id!},2)" class="ghostBtn">视频认证通过</a> 
		   	   <a  onclick="upvs(${tinfo.id!},3)" class="ghostBtn">视频认证失败</a>
	       <#elseif tinfo.video_status gt 1>
	      	   <a  onclick="upvs(${tinfo.id!},1)" class="ghostBtn">重置视频认证</a> 
	   	   </#if>
		</div>
	</div>
	<div class="attributes_box">
		<h2>终端信息</h2>
		<div class="attributes_list_s clear">
			<ul>
				<li>终端号：<span class="orangeText">${tinfo.serial_num!}</span></li>
				<li>处理人：${tinfo.c2name!}</li>
				<li>终端状态：
				   <#if tinfo.tstatus=1>已开通
			       <#elseif tinfo.tstatus=2>部分开通
			       <#elseif tinfo.tstatus=3>未开通 
			       <#elseif tinfo.tstatus=4>已注销
			       <#elseif tinfo.tstatus=5>已停用</#if></li>
				<li>开通申请状态：
				   <#if tinfo.status=1>待初次预审
			       <#elseif tinfo.status=2>初次预审不通过
			       <#elseif tinfo.status=3>二次预审中  
			       <#elseif tinfo.status=4>二次预审不通过
			       <#elseif tinfo.status=5>待审核
			       <#elseif tinfo.status=6>审核中
			       <#elseif tinfo.status=7>审核失败
			       <#elseif tinfo.status=8>审核成功
			       <#elseif tinfo.status=9>已取消
		       	   </#if></li>
				<li>视频认证状态：
				   <#if tinfo.video_status=0>无需认证
			       <#elseif tinfo.video_status=1>待认证
			       <#elseif tinfo.video_status=2>认证通过
			       <#elseif tinfo.video_status=3>认证失败
		       	   </#if></li>
				<li>品牌型号：${tinfo.good_brand!} ${tinfo.model_number!}</li>
				<li>支付通道：${tinfo.pcname!}</li>
				<li>到账日期：${tinfo.dbcname!}</li>
				<li>用户名：${tinfo.cname!}</li>
				<li>所属代理商：${tinfo.aname!}</li>
				<li>手机：${tinfo.phone!}</li>
				<li>E-mail：${tinfo.email!}</li>
			</ul>
		</div>
	</div>
	<div class="attributes_box">
		<h2>
			开通详情<a onclick="downFile()" class="a_btn">下载开通资料</a>
		</h2>
		<div class="attributes_list_s clear">
			<ul>
				<li>开通方向：${tinfo.types!}</li>
				<li>绑定商户：${tinfo.merchant_name!}</li>
				<li>商家电话：${tinfo.mphone!}</li>
				<li>身份证：${tinfo.card_id!}</li>
			</ul>
		</div>
		<div class="item_list clear">
			<ul>
			    <#if (opendetailsinfos)??> 
			    <#list opendetailsinfos as one> 
			    	<#if (one.types)??&&one.types != 2> 
			    		<li>
                    		 <span class="labelSpan">${one.key!}</span>
                     		 <div class="text">${one.value!}</div>
               			</li>
			    	</#if>
			    </#list> 
			    </#if>
			    
			    <#if (opendetailsinfos)??> 
			    <#list opendetailsinfos as one> 
			    	<#if (one.types)??&&one.types == 2> 
			    		<li>
		                     <span class="labelSpan" >${one.key!}：</span>
		                     <div class="text">
		                     <img src="${one.value!}" class="cover" value="${one.value!}" dbValue="${one.value!}" >
		                     </div>
               			 </li>
			    	</#if>
			    </#list> 
			    </#if>
			</ul>
			<div class="img_info" style="display: none; top: 0px; left: 0px;"><img style="max-width:500px;" src=""></div>
		</div>
	</div>
	<div class="user_remark">
		<textarea id="content" cols="" rows=""></textarea>
		<button class="whiteBtn" onclick="add()">备注</button>
	</div>
	<div class="user_record">
		 <h2>备注</h2>
		 <div id="mark_fresh"><#include "mark.ftl" /></div>
    </div>
</div>

<script type="text/javascript">
	var add=function(){
		var id=${tinfo.id};
		var content=$('#content').val();
		if(""==content.trim()){
			return;
		}
		$.post('<@spring.url "/task/certifiedopen/addmark" />',
				{"applyid": id,"content":content},
		        function (data) {
					 $('#content').val('');
					 $('#mark_fresh').html(data);
		        });
	}
	var ups=function(id,status){
		var tid = ${tinfo.tid};
		$.post('<@spring.url "/task/certifiedopen/upstatus" />',
				{"id": id,"status":status,"tid":tid},
		        function (data) {
					if(1==data){
						location.reload();
					}else if(data ==0){
						alert("操作失败!");
					}else{
						alert(data);
						location.reload();
					}
		        });
	}
	
	var upvs=function(id,status){
		$.post('<@spring.url "/task/certifiedopen/upvstatus" />',
				{"id": id,"status":status},
		        function (data) {
					if(data>0){
						location.reload();
					}else{
						alert("操作失败!");
					}
		        });
	}
	
	 var downFile=function(){
	  $.get('<@spring.url "/terminal/${tinfo.tid}/exportOpenInfo" />',
                function (data) {
                    if(data.code==1){
                        window.location.href = data.result;
                    }
                    if(data.code == -1){
                    	alert(data.message);
                    }
                });
       
    }
</script>
</@c.html>
