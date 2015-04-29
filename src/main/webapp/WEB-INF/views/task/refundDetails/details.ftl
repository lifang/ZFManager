<#import "../../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
                    <ul>
                        <li><a href="javascript:void(0)">任务</a></li>
                        <li><a href="javascript:void(0)">退款</a></li>
                        <li><a href="javascript:void(0)">详情</a></li>
                    </ul>
                </div>
                <div class="content clear">
                    <div class="user_title">
                    	<h1>退款单详情</h1>
                        <div class="userTopBtnBox">
                        <#if refundDetails.status == 1>
                        <a href="<@spring.url '/task/refund/${refundDetails.id}/updsateRefundStatus' />" class="ghostBtn">标记完成</a>
                        </#if>
                     	</div>
                    </div>
                    <div class="attributes_box">
                    	<h2>退款信息</h2>
                        <div class="attributes_list_s clear">
                            <ul>
                                <li>编号：${(refundDetails.applyNum)!""}</li>
                                <li>处理人：${(refundDetails.processUserName)!""}</li>
                                <li>相关业务：<a href="<@spring.url '/cs/return/${refundDetails.returnId!}/info' />" class="a_btn">${(refundDetails.returnApplyNum)!""}</a></li>
                                <li>联系人：${(refundDetails.payee)!""}</li>
                                <li>联系电话：${(refundDetails.payeePhone)!""}</li>
                                <li>退款金额：<strong>￥${(refundDetails.returnPrice)!""}</strong></li>
                            </ul>
                        </div> 
                    </div>
                    <div class="attributes_box">
                    	<h2>退款凭证</h2>
                    	<div class="item_list clear">
                            <ul>
                                <li><span class="labelSpan">退款凭证：</span>
                                	<div class="text">
                                    	<div class="item_photoBox">
                                    	 <form id="fileForm" method="post" action="<@spring.url '/task/refund/${refundDetails.id}/upload/tempImage' />" enctype="multipart/form-data">  
                                            <img  src="../../../resources/images/zp.jpg" class="cover" />
                                            <a href="javascript:void(0);" class="informImg_a">
                                                <span>重新上传退款凭证</span><input name="img" multiple type="file" onchange="setSpanName()">
                                            </a>
                                            </form>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                            <div class="img_info"><img id="refundeims" src="${(refundDetails.returnVoucherFilePath)!}" /></div>
                        </div> 
                    </div>
                    <div class="user_remark">
                    <input id="refundId" type="hidden" value="${(refundDetails.id)!''}" />
                    	<textarea id="refunsMark" name="" cols="" rows=""></textarea>
                        <button id="addRefundMark" class="whiteBtn">备注</button>
                     </div>
                     <#if (refundRecord)??>
                     	<#include "refundRecord.ftl" />
                     </#if>
            	</div>
            	 <script type="text/javascript">
            	
				 $(function(){
        			$('#addRefundMark').click(function(){
            			var content = $("#refunsMark").val();
            			var refundId = $("#refundId").val();
            			if(content == "" || content == null){
            			}else{
            			addRefundMark(content,refundId);
            			}
        			});
    			});
    			
    			function addRefundMark(content,refundId) {
        			$.post('<@spring.url "/task/refund/addRefundMark" />',
                	{"content": content,
                    	"refundId": refundId
                	},
                	function (data) {
                	if(data == null){
                		alert("备注失败！");
                	}else{
                		$('#record').html(data);
                	}
                	});
    			}
    			
    			// 上传
            function setSpanName(){
            	$("#fileForm").ajaxSubmit({
            		success : function(data) {
            			$("#refundeims").attr("src",data);
            		}
            	});
            }
           </script>
</@c.html>