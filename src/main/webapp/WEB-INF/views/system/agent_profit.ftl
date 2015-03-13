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
                    <col width="150">
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
                <#list agent.payChannels as agentPayChannel>
                    <#include "agent_profit_info_row.ftl"/>
                </#list>
                </tbody>
            </table>
        </div>
        <div class="btnBottom"><button class="blueBtn">增加支付产品</button></div>
    </div>
<div id="hiddenEchelonProfit" style="display: none;">
<p>
    <input name="" type="text" onkeyup="value=this.value.replace(/\D+/g,'')" class="input_xs">
    <input name="" type="text" onkeyup="value=this.value.replace(/\D+/g,'')" class="input_xs">%
</p>
</div>
<script>
    $(function () {
        $(document).delegate(".addEchelonProfit", "click", function () {
            var $p = $("#hiddenEchelonProfit").children("p").clone();
            $(this).parent().before($p);
        });

        var profitObject = new Object();
        $(document).delegate(".saveProfit", "click", function () {
            var channelId = $(this).attr("value");
            var $preTd =  $(this).parent().prev();
            profitObject.channelId = $preTd.prev().find("select").find("option:selected").val();
            var tradeTypesArray = new Array();
            profitObject.tradeTypes=tradeTypesArray;
            $preTd.find("tr").eq(1).children("td").each(function(i){
                var tradeTypeObject = new Object();
                tradeTypesArray.push(tradeTypeObject);
                var percentArray = new Array();
                tradeTypeObject.percents = percentArray;
                tradeTypeObject.tradeTypeId = $(this).attr("value");
                $(this).children("p").each(function(i){
                        var $input_s = $(this).find(".input_s");
                        var $input_xs = $(this).find(".input_xs");
                    if($input_s.length > 0){
                            var floorNumber = 0;
                            var percent = $input_s.val();
                            percentArray.push({floorNumber: floorNumber, percent: percent});
                        } else if($input_xs.length > 0){
                            var floorNumber = $input_xs.eq(0).val();
                            var percent = $input_xs.eq(1).val();
                        percentArray.push({floorNumber: floorNumber, percent: percent});
                    }
                });

            });
            var $tr = $(this).parents("tr");
            $.post('<@spring.url "/system/agent/${agent.id}/profit/edit" />',
                    {payChannelId: channelId,
                    data: JSON.stringify(profitObject)},
                    function (data) {
                        $tr.replaceWith(data);
                    });

        });

        $(document).delegate(".editProfit", "click", function () {
            var channelId = $(this).attr("value");
            var $tr = $(this).parents("tr");
            $.get('<@spring.url "/system/agent/${agent.id}/profit/edit" />',
                    {payChannelId: channelId},
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

    })
</script>
</@c.html>