<#import "../../common.ftl" as c />
<@c.html>
     <div class="breadcrumb"> 
      <ul> 
       <li>商品</li> 
       <li><a href="<@spring.url "/task/outStore/list"/>" class="hover">出库</a></li>
       <li><a href="<@spring.url "/task/outStore/${outStorageId}/info"/>">详情</a></li> 
      </ul> 
     </div> 
     <div class="content clear"> 
      <div class="user_title">
      	<input type="text" id="outStorageId" style="display: none;" value="${outStorageId}"/>
       	<h1>出库记录详情</h1>
      </div>
	   <div class="attributes_box">
	    	<h2>记录信息</h2>
	        <div class="attributes_list clear">
	            <ul>
	                <li>编号：<#if (outStorageId)??>${outStorageId}</#if></li>
	                <li>处理人：<#if (operater)??>${operater}</#if></li>
	                <li>订单编号：<a href="#" class="a_btn orderDetail_a">用户订单<#if (orderNumber)??>（${orderNumber}）</#if></a> </li>
	            </ul>
	        </div> 
	    </div>
       <div class="uesr_table"> 
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table"> 
		    <colgroup> 
		     	<col width="600" />
	            <col />
	            <col width="300" />
		    </colgroup> 
		    <thead> 
		     <tr>
		      <th>商品</th> 
		      <th>数量</th> 
		      <th>终端号</th>
		     </tr> 
		    </thead> 
		    <tbody> 
		     <#if (goods)??>
			  <#list goods as good>
			  	  <tr id="row_${good.id}"> 
			  	  	<td>
                        <div class="td_proBox clear">
                            <a href="#" class="cn_img"><img src="${good.urlPath}" style="width:130px;height:130px" /></a>
                            <div class="td_proBox_info">
                                <h1><#if (good.title)??><a href="#">${good.title}</a></#if></h1>
                                <h3>热销5000件</h3>
                                <ul>
                                    <li><span>品牌型号：</span><div class="c_text"><#if (good.brandName)??>${good.brandName}</#if></div></li>
                                    <li><span>支付通道：</span><div class="c_text"><#if (good.payChannelName)??>${good.payChannelName}</#if></div></li>
                                </ul>
                            </div>
                        </div>
                    </td>
				    <td>${good.quantity}</td> 
				    <td>
				    	<div class="machineNumber">
	                        <span>${good.terminalPort}</span>
	                    </div>
				    </td>
				   </tr>
			  </#list>
			</#if>
		    </tbody> 
		 </table> 
		</div>
		<div class="attributes_box">
        	<div class="item_list clear">
            	<ul>
                	<li class="b"><span class="labelSpan">收货地址：</span>
                    <div class="text"><#if (address)??>${address}</#if></div></li>
                    <li class="block"><span class="labelSpan">物流公司：</span>
                    <div class="text"><#if (wlCompany)??>${wlCompany}</#if></div></li>
                    <li class="block"><span class="labelSpan">物流单号：</span>
                    <div class="text"><#if (wlNum)??>${wlNum}</#if> </div></li>
                </ul>
            </div>
        </div>
         <div class="user_remark">
        	<textarea name="" cols="" rows="" id="remarkContent"></textarea>
            <button class="whiteBtn" onClick="submitData()">备注</button>
        </div>
         <div class="user_record">
        	<h2>备注</h2>
        	<#if (remarks)??>
	 			<#list remarks as remark>
            <div class="ur_item">
            	<div class="ur_item_text">${remark.content}</div>
                <div class="ur_item_name">${remark.userName}&nbsp;<em>${remark.operateTime}</em></div>
            </div>
	            </#list>
		 	</#if>
        </div>
      </div>
<div class="tab orderDetail_tab">
	<a href="#" class="close" id="closeA">关闭</a>
    <div class="tabHead">订单详情</div>
    <div class="tabBody">
    	<div class="orderDetail_info_tab">
    		<#if (orderDetails)??>
            <dl>
            	<dt>收货地址：</dt><dd>${orderDetails.address}&nbsp;${orderDetails.receiver}</dd>
            </dl>
            <dl>
            	<dt>发票类型：</dt><dd>${orderDetails.invoiceName}</dd><dt>发票抬头：</dt><dd>${orderDetails.invoiceInfo}</dd>
            </dl>
            <dl class="leaveWord">
            	<dt>留言：</dt><dd>${orderDetails.comment}</dd>
            </dl>
            <dl>
            	<dt>订单类型：</dt><dd>${orderDetails.typesName}</dd><dt>订单编号：</dt><dd>${orderDetails.orderNumber}</dd>
            </dl>
            <dl>
            	<dt>购买人：</dt><dd><#if (orderDetails.name)??> ${orderDetails.name}<#else>${orderDetails.belongsToUserName}</#if></dd>
                <dt>购买日期：</dt><dd>${orderDetails.createdAt}</dd>
            </dl>
            <dl>
            	<dt>支付类型：</dt><dd>${orderDetails.payTypeName}</dd>
            </dl>
            <dl>
            	<dt>供货商：</dt><dd>${orderDetails.factoryName}</dd><dt>处理人：</dt><dd>${orderDetails.processUserName}</dd>
            </dl>
            <dl>
            	<dt>订单原金额：</dt><dd class="line_through">￥${orderDetails.oldPrice}</dd><dt>订单金额：</dt><dd><strong>￥${orderDetails.actualPrice}</strong></dd>
            </dl>
            </#if>
        </div>
    </div>
    <div class="tabFoot"><button class="blueBtn" onclick="closeA()">确定</button></div>
</div>
 
<script type="text/javascript">
	function closeA(){
		$("#closeA").click();
	}

	function submitData(){
		//备注
		var content=$("#remarkContent").val();
		var outStorageIdStr=$("#outStorageId").val();
		$.post('<@spring.url "/task/outStore/saveRemark" />',
	        {   "id": outStorageIdStr,
	        "remarkContent":content},
	        function (ret) {
	            if(ret.code=='-1'){
            		alert("操作出错，错误信息为："+ret.Message);
            	}else if(ret.code=='1'){
            		//自刷新
            		location.reload();
            		$("#remarkContent").val("");
            	}
	        });	
	}
</script>    
</@c.html>
