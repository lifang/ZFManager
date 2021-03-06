<#import "common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="<@spring.url "/index"/>">运营中心首页</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>待处理业务</h1></div>
    <div class="all_statistics">
        <ul>
            <li><a href="<@spring.url "/good/pos/waitComment/list"/>" target="_blank"><i class="a01"></i>评论审核 <strong class="blue">${commentSum!'0'}</strong> 条</a></li>
            <li><a href="<@spring.url "/good/pos/list"/>" target="_blank"><i class="a02"></i>产品审核 <strong class="blue">${goodSum!'0'}</strong> 条</a></li>
            <li><a href="<@spring.url "/task/certifiedopen/list"/>" target="_blank"><i class="a03"></i>开通预审 <strong class="blue">${openingAppliesSum!'0'}</strong> 条</a></li>
            <li><a href="<@spring.url "/cs/return/list"/>" target="_blank"><i class="a04"></i>退货申请 <strong class="blue">${returnSum!'0'}</strong> 条</a></li>
            <li><a href="<@spring.url "/cs/change/list"/>" target="_blank"><i class="a05"></i>换货申请 <strong class="blue">${changeSum!'0'}</strong> 条</a></li>
            <li><a href="<@spring.url "/cs/repair/list"/>" target="_blank"><i class="a06"></i>维修申请 <strong class="blue">${repairsSum!'0'}</strong> 条</a></li>
            <li><a href="<@spring.url "/cs/cancel/list"/>" target="_blank"><i class="a07"></i>注销申请 <strong class="blue">${cancelsSum!'0'}</strong> 条</a></li>
            <li><a href="<@spring.url "/cs/update/list"/>" target="_blank"><i class="a08"></i>资料更新申请 <strong class="blue">${updateInfosSum!'0'}</strong> 条</a></li>
            <li><a href="<@spring.url "/task/outStore/list"/>" target="_blank"><i class="a09"></i>发货单 <strong class="blue">${storagesSum!'0'}</strong> 条</a></li>
            <li><a href="<@spring.url "/task/refund/list"/>" target="_blank"><i class="a10"></i>退款单 <strong class="blue">${srefundsSum!'0'}</strong> 条</a></li>
            <li><a href="<@spring.url "/cs/agent/list"/>" target="_blank"><i class="a11"></i>代理商售后申请 <strong class="blue">${agentSum!'0'}</strong> 条</a></li>
        </ul>
    </div>
</div>
<#if RequestParameters.role?? &&RequestParameters.role=='no'>
<script>
    $(function(){
        showErrorTip("你没有该功能的权限！");
    });
</script>
</#if>
</@c.html>