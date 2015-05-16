<div class="content clear">

    <div class="user_title"><h1>终端详情</h1>
    <#if !(isFactory??) || !isFactory>
        <div class="userTopBtnBox">
            <a href="javascript:void(0);" class="ghostBtn" onclick="synizeTerminal(${terminal.id})">同步终端状态</a>
            <#if terminal.status==1 || terminal.status==2 || terminal.status==3>
    			<!--<a href="javascript:void(0);" onclick="judgeUpdate(${terminal.id})" class="ghostBtn">更新资料</a>
    			<a href="javascript:void(0);" onclick="judgeReturn(${terminal.id})" class="ghostBtn">退货</a>
    			<a href="javascript:void(0);" onclick="judgeChang(${terminal.id})" class="ghostBtn">换货</a>-->
    		</#if>
        </div>
    </#if>
    </div>
    <div class="attributes_box">
        <h2>终端信息</h2>
        <div class="attributes_list_s clear">
            <ul>
                <li>终端号：<span class="orangeText">${(terminal.serialNum)!""}</span></li>
                <li>用户名：${(terminal.customer.name)!""}</li>
                <li>手机：${(terminal.customer.phone)!""}</li>
                <li>Email：${(terminal.customer.email)!""}</li>
                <li>所属代理商：${(terminal.agent.name)!""}</li>	
                <li>终端状态：
                <#if (terminal.status)??>
                    <#if terminal.status=1>已开通
                    <#elseif terminal.status=2>部分开通
                    <#elseif terminal.status=3>未开通
                    <#elseif terminal.status=4>已注销
                    <#elseif terminal.status=5>已停用
                    </#if>
                </#if>
                </li>
                <li>开通申请状态：
                <#if (terminal.openingApplie.status)??>
                    <#if terminal.openingApplie.status=1>待初次预审
                    <#elseif terminal.openingApplie.status=2>待初预审不通过
                    <#elseif terminal.openingApplie.status=3>二次预审中
                    <#elseif terminal.openingApplie.status=4>二次预审不通过
                    <#elseif terminal.openingApplie.status=5>待审核
                    <#elseif terminal.openingApplie.status=6>审核中
                    <#elseif terminal.openingApplie.status=7>审核失败
                    <#elseif terminal.openingApplie.status=8>审核成功
                    <#elseif terminal.openingApplie.status=9>已取消
                    </#if>
                </#if>
                </li>
                <li>POS产品：${(terminal.good.title)!""}</li>
                <li>支付通道：${(terminal.payChannel.name)!""}</li>
                <li>订单号：${(terminal.order.orderNumber)!""}</li>
                <li>订购时间：<#if (terminal.order.createdAt)??>${(terminal.order.createdAt)?string("yyyy/MM/dd  HH:mm:ss")}</#if></li>
            </ul>
        </div>
    </div>
    <div class="attributes_table">
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <colgroup>
                <col width="25%">
                <col width="25%">
                <col width="50%">
            </colgroup>
            <thead>
            <tr>
                <th>交易类型</th>
                <th>费率</th>
                <th>状态</th>
            </tr>
            </thead>
            <tbody>
            <#list terminal.tradeTypeList as tradeType>
            <tr>
                <td><#if (tradeType.tradeValue)??>${tradeType.tradeValue}</#if>
                </td>
                <td>
                	<#if (tradeType.tradeValue)='消费'><#if (tradeType.serviceRate)??><#if (tradeType.baseRate)??> ${(tradeType.serviceRate+tradeType.baseRate)/10}‰</#if></#if>
                	<#elseif (tradeType.tradeValue)!='消费'><#if (tradeType.terminalRate)??>${tradeType.terminalRate/10}‰</#if>
                	</#if>
                </td>
                <td>
                <#if (tradeType.status)??>
                    <#if tradeType.status=2>未开通
                    <#elseif tradeType.status=1>已开通
                    </#if>
                  </#if>
                </td>
            </tr>
            </#list>
            </tbody></table>
    </div>
<#if (terminal.good.hasLease)?? && terminal.good.hasLease>
    <div class="attributes_box">
        <h2>租赁信息</h2>
        <div class="attributes_list_s clear">
            <ul>
                <li>租赁日期：<#if (terminal.order.updatedAt)??>${(terminal.order.updatedAt)?string("yyyy/MM/dd")}</#if></li>
                <li>最短租赁时间：<#if (terminal.good.leaseTime)??>${terminal.good.leaseTime}个月</#if> </li>
                <li>最长租赁时间：<#if (terminal.good.returnTime)??>${terminal.good.returnTime}个月</#if></li>
                <li>租赁押金：<strong><#if (terminal.good.leaseDeposit)??>￥${(terminal.good.leaseDeposit/100)?string("0.00")}</#if></strong></li>
                <li>月租金：<strong><#if (terminal.good.leasePrice)??>￥${(terminal.good.leasePrice/100)?string("0.00")}</#if></strong></li>
                <li>租赁说明：<a href="#" class="a_btn leaseExplain_a">点击查看</a></li>
            </ul>
        </div>
    </div>
</#if>
    <div class="attributes_box">
        <#if videoUrl??>
        <h2>开通详情 <a href="<@spring.url ""/>${videoUrl}" class="a_btn">下载视频</a></h2>
        </#if>
        <h2>开通详情
        <#if Roles.hasRole("DOWNLOAD_DATA") || !(isFactory??) || !isFactory>
        <#if (terminal.openingApplie.terminalOpeningInfos)??>
            <#list terminal.openingApplie.terminalOpeningInfos as openingInfo>
            <#if openingInfo.types == 2>
                <a onclick="downloadFile()" class="a_btn">下载开通资料</a>
                <#break>
            </#if>
            </#list>
        </#if>
        </#if>
        </h2>
        <div class="attributes_list_s clear">
            <ul>
                <li>开通方向：<#if (terminal.openingApplie.types)??><#if terminal.openingApplie.types==1>对公<#else>对私</else></#if></#if></li>
                <li>绑定商户：${(terminal.merchant.title)!""}</li>
                <li>商家电话：${(terminal.merchant.phone)!""}</li>
                <li>身份证：${(terminal.merchant.legalPersonCardId)!""}</li>
            </ul>
        </div>
        <div class="item_list clear">
            <ul>
            <#if (terminal.openingApplie.terminalOpeningInfos)??>
                <#list terminal.openingApplie.terminalOpeningInfos as openingInfo>
                    <li><span class="labelSpan">${(openingInfo.key)!""}：</span>
                        <div class="text">
                            <#if openingInfo.types == 2>
                                <img src="<@spring.url "/resources/images/zp.jpg"/>"
                                     <#if (openingInfo.value)??>value="${openingInfo.value}" </#if>
                                     class="cover" />
                            <#else>
                            ${(openingInfo.value)!""}
                            </#if>
                        </div></li>
                </#list>
            </#if>
            </ul>
            <div class="img_info" style="display: none; top: 0px; left: 0px;"><img style="max-width:500px;"></div>
        </div>
    </div>
    <#if !(isFactory??) || !isFactory>
    <div class="user_remark">
        <textarea id="textWriteMark" name="" cols="" rows="" onkeyup="checkLength(this,600)"></textarea>
        <button class="whiteBtn" onclick="writeMark()">备注</button>
    </div>
    <div class="user_record">
        <h2>备注</h2>
    <#list terminal.terminalMarks as terminalMark>
        <#include "../../terminal/mark.ftl"/>
    </#list>
    </div>
    </#if>
</div>

<div class="tab leaseExplain_tab">
    <a href="javascript:void(0);" class="close">关闭</a>
    <div class="tabHead">租赁说明</div>
    <div class="tabBody">
        <div class="leaseExplain_con">
        ${(terminal.good.leaseDescription)!""}
        </div>
    </div>
</div>
<script>
	function judgeUpdate(terminalId){
		$.post('<@spring.url "/terminalCs/judgeUpdate" />', {"terminalId":terminalId},
		function (data) {  //绑定
	          if (data != null && data != undefined) {
	        	  if(data.code == -1){
	        		  alert("已有该终端更新申请！");
	        	  }else if(data.code == 1){
	        	  	  //页面跳转
	        	  	  window.location.href = "../../terminalCs/getWebApplyDetails?terminalId="+terminalId+"&type=1";
	        	  }
	          }
	    });
	}
	
	function judgeReturn(terminalId){
		$.post('<@spring.url "/terminalCs/judgeChang" />', {"terminalId":terminalId},
		function (data) {  //绑定
	          if (data != null && data != undefined) {
	        	  if(data.code == -1){
	        		  alert("已有该终端换货申请！");
	        	  }else if(data.code == 1){
	        	  	  //页面跳转 退货
	        	  	  window.location.href = "../../terminalCs/getWebApplyDetails?terminalId="+terminalId+"&type=2";
	        	  }
	          }
	    });
	}
	function judgeChang(terminalId){
		$.post('<@spring.url "/terminalCs/judgeChang" />', {"terminalId":terminalId},
		function (data) {  //绑定
	          if (data != null && data != undefined) {
	        	  if(data.code == -1){
	        		  alert("已有该终端换货申请！");
	        	  }else if(data.code == 1){
	        	  	  //页面跳转
	        	  	  window.location.href = "../../terminalCs/getWebApplyDetails?terminalId="+terminalId+"&type=3";
	        	  }
	          }
	    });
	}
	
	

function synizeTerminal(terminalId){
		var id = terminalId;
			if(id==''){
				alert("没有终端号");
				return false;
			}
			$.post('<@spring.url "/terminal/syncStatus" />',
				{"terminalId":id},
				function(data){
				    var data = eval("("+data+")");
					if(data.code==1){
						alert("同步成功");
						location.reload();
					}else{
						alert(data.message);
						return false;
					}
				});
	}
	<#-- 控制长度-->
	function checkLength(obj,lengthStr){
		var temp=$(obj).val();
		if(temp.length>=lengthStr){
			$(obj).val("");
			$(obj).css("border-color","red");
			alert("输入长度超过最大长度限制，最大长度为："+lengthStr);
		}else{
			$(obj).css("border-color","darkgrey");
		}
	}

    function writeMark(){
        var $tarea = $("#textWriteMark");
        var content = $tarea.val();
        if(isNull(content, "内容不能为空！")){
            return false;
        }
        if(content!=""){
            $.post('<@spring.url "/terminal/${terminal.id}/mark" />',
                    {"content": content},
                    function (data) {
                        $tarea.val("");
                        $(".user_record").children("h2").after(data);
                    });
        }
    }

    function downloadFile(){
        $.get('<@spring.url "/terminal/${terminal.id}/exportOpenInfo" />',
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
