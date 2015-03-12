<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="#">系统</a></li>
            <li><a href="#">代理商</a></li>
            <li><a href="#">设置分润比例</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title"><h1>设置分润比例</h1>
        </div>
        <div class="attributes_box">
            <h2>基础信息</h2>
            <div class="attributes_list_s clear">
                <ul>
                    <li>代理商：${agent.companyName}</li>
                </ul>
            </div>
        </div>
        <div class="uesr_table">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
                <colgroup>
                    <col>
                    <col>
                    <col width="150">
                </colgroup>
                <thead>
                <tr>
                    <th>支付通道</th>
                    <th>分润比例</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <#include "agent_profit_info_row.ftl"/>
                <#include "agent_profit_edit_row.ftl"/>
                </tbody>
            </table>
        </div>
        <div class="btnBottom"><button class="blueBtn">增加支付产品</button></div>
    </div>
<script>

</script>
</@c.html>