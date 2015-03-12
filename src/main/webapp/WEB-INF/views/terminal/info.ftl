<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="#">终端</a></li>
            <li><a href="#">终端详情</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title"><h1>终端详情</h1>
            <div class="userTopBtnBox">
                <a href="#" class="ghostBtn">同步</a>
            </div>
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
                        <#if terminal.status=1>已开通
                        <#elseif terminal.status=2>部分开通
                        <#elseif terminal.status=3>未开通
                        <#elseif terminal.status=4>已注销
                        <#elseif terminal.status=5>已停用
                        </#if>
                    </li>
                    <li>开通申请状态：
                        <#if (terminal.openingApplie.status)??>
                        <#if terminal.openingApplie.status=1>待审核
                        <#elseif terminal.openingApplie.status=2>初审通过
                        <#elseif terminal.openingApplie.status=3>初审不通过
                        <#elseif terminal.openingApplie.status=4>审核通过
                        <#elseif terminal.openingApplie.status=5>审核不通过
                        <#elseif terminal.openingApplie.status=6>已取消
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
                <#list terminal.tradeTypeInfos as tradeTypeInfo>
                <tr>
                    <td>${(tradeTypeInfo.supportTradeType.dictionaryTradeType.tradeValue)!""}
                        <#if tradeTypeInfo.supportTradeType.tradeType == 1 &&  (terminal.billingCycle.dictionaryBillingCycle.name)??>
                            (${terminal.billingCycle.dictionaryBillingCycle.name})
                        </#if>
                    </td>
                    <td>
                        <#if tradeTypeInfo.supportTradeType.tradeType == 1 >
                            <#if (terminal.baseRate)?? && (terminal.billingCycle.rate)??>
                            ${(terminal.baseRate)+(terminal.billingCycle.rate)}%
                            </#if>
                        <#else>
                        ${(tradeTypeInfo.supportTradeType.terminalRate)!""}%
                        </#if>
                    </td>
                    <td>
                       <#if tradeTypeInfo.status=2>未开通
                       <#elseif tradeTypeInfo.status=1>已开通
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
            <h2>开通详情<a  onclick="downloadFile()" class="a_btn">下载开通资料</a></h2>
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
                                    <img src="<@spring.url "/resources/images/zp.jpg" />" value="${(openingInfo.value)!""}" class="cover" />
                                <#else>
                                    ${(openingInfo.value)!""}
                                </#if>
                            </div></li>
                    </#list>
                    </#if>
                </ul>
                <div class="img_info" style="display: none; top: 0px; left: 0px;"><img src="<@spring.url "/resources/images/mt_big.jpg" />"></div>
            </div>
        </div>
        <div class="user_remark">
            <textarea id="textWriteMark" name="" cols="" rows=""></textarea>
            <button class="whiteBtn" onclick="writeMark()">备注</button>
        </div>
        <div class="user_record">
            <h2>追踪记录</h2>
            <#list terminal.terminalMarks as terminalMark>
            <#include "mark.ftl"/>
            </#list>
        </div>
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
    function writeMark(){
        var content = $("#textWriteMark").val();
        if(content!=""){
            $.post('<@spring.url "/terminal/${terminal.id}/mark" />',
                    {"content": content},
                    function (data) {
                        $(".user_record").children("h2").after(data);
                    });
        }
    }

    function downloadFile(){
        $.get('<@spring.url "/terminal/${terminal.id}/exportOpenInfo" />',
                function (data) {
                    if(data.code==1){
                        window.open(data.result);
                    }
                });
    }


</script>
</@c.html>