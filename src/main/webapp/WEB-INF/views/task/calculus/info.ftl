<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
                    <ul>
                       <li><a href="#">售后</a></li>
						<li><a href="<@spring.url "/task/calculus"/>">积分兑换</a></li>
						<li><a href="<@spring.url "/task/calculus/${integralInfo.id}/info"/>">详情</a></li>
                    </ul>
                </div>
                <div class="content clear">
                    <div class="user_title">
                    	<h1>积分兑换申请详情</h1>
                        <div class="userTopBtnBox">
                        	<a href="#" class="ghostBtn">分派</a>
                     	</div>
                    </div>
                    <div class="attributes_box">
                    	<h2>兑换信息</h2>
                        <div class="attributes_list_s clear">
                            <ul>
                                <li>编号：${integralInfo.applyNum}</li>
                                <li>处理人：${integralInfo.processUserName!}</li>
                                <li>申请用户：${integralInfo.name!}</li>
                                <li>用户电话：${integralInfo.phone!}</li>
                                <li>申请时间：${integralInfo.createdAt?datetime}</li>
                                <li>兑换积分数：${integralInfo.quantity!}</li>
                                <li>兑换金额：<strong>￥${integralInfo.price/100!}</strong></li>
                            </ul>
                        </div> 
                    </div>
                 
                    <div class="user_remark">
						<textarea id="textarea_mark" name="" cols="" rows=""></textarea>
						<button class="whiteBtn" onClick="onMark();">备注</button>
					</div>
                    <div class="user_record">
						<h2>追踪记录</h2>
						<div id="mark_container">
						<#list integralInfoMarks as mark>
							<#include "mark.ftl" />
						</#list> 						<div>
					</div>
                  
            	</div>

<script type="text/javascript">

	function onMark() {
		var csUpdateId = ${integralInfo.id};
		var status = ${integralInfo.status}
		var content = $("#textarea_mark").val();
		if (content.length==0) {
			alert("请输入备注内容");
			return;
		}
		$.post('<@spring.url "/task/calculus/mark/create" />',
	    	{"csUpdateId": csUpdateId,
	    	 "content": content},
	    	 function (data) {
	         	if (status==2) {
	    	 		$('#mark_container').prepend(data);
	            	$("#textarea_mark").val("");
	    	 	} else {
	    	 		location.reload();
	    	 	}
	         });
	}	
</script>
</@c.html>