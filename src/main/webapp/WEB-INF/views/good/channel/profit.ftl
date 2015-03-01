<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">商品</a></li>
        <li><a href="#">支付通道</a></li>
        <li><a href="#">设置分润</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>设置分润</h1>
    </div>
    <div class="attributes_box">
        <h2>基础信息</h2>
        <div class="item_list clear">
            <ul>
                <li><span class="labelSpan">支付通道：</span>
                    <div class="text">${channel.name}</div>
                </li>
            </ul>
        </div>
    </div>

    <div class="attributes_box">
        <h2>刷卡交易</h2>
        <div class="item_list clear">
            <ul>
                <#list channel.supportTradeTypes as supportTradeType>
                    <#if supportTradeType.tradeType = 1>
                        <#assign baseProfit=supportTradeType.baseProfit />
                    </#if>
                </#list>
                <li class="b height48"><span class="labelSpan">标准手续费交易<br>分润百分比：</span>
                    <div class="text"><input id="baseProfit" type="text" value="${baseProfit!""}"/></div>
                </li>
                <li class="b"><span class="labelSpan">资金服务费率：</span>
                    <div class="text">
                        <div class="rate_attributes">
                            <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                <colgroup>
                                    <col width="33%">
                                    <col width="33%">
                                </colgroup>
                                <tbody><tr>
                                    <td>结算周期</td>
                                    <td>费率</td>
                                </tr>
                                <#list channel.billingCycles as billingCycle>
                                <tr>
                                    <td>${billingCycle.dictionaryBillingCycle.name}</td>
                                    <td>${billingCycle.rate}%</td>
                                </tr>
                                </#list>
                                </tbody></table>
                        </div>
                    </div>
                </li>

            </ul>
        </div>
    </div>

    <div class="attributes_box">
        <h2>其他交易</h2>
        <div class="item_list clear">
            <ul>
                <#list channel.supportTradeTypes as supportTradeType>
                    <#if supportTradeType.tradeType = 2>
                    <li class="b o"><span class="labelSpan">${supportTradeType.dictionaryTradeType.tradeValue}：</span>
                            <div class="text">
                                <div class="rate_attributes mtop">
                                    <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                        <colgroup>
                                            <col width="16%">
                                            <col width="16%">
                                            <col width="16%">
                                            <col width="16%">
                                            <col width="16%">
                                            <col>
                                        </colgroup>
                                        <tbody><tr>
                                            <td>终端费率</td>
                                            <td>基础费率</td>
                                            <td>最低收费</td>
                                            <td>最低分润</td>
                                            <td>最高收费</td>
                                            <td>最高分润</td>
                                        </tr>
                                        <tr class="rate" value="${supportTradeType.id}">
                                            <td><input name="terminalRate" type="text" value="${supportTradeType.terminalRate!""}" class="input_m">%</td>
                                            <td><input name="baseRate" type="text" value="${supportTradeType.baseRate!""}" class="input_m">%</td>
                                            <td><input name="floorCharge" type="text" value="${(supportTradeType.floorCharge?exists)?string((supportTradeType.floorCharge/100)?string("0.00"),'')}" class="input_m">元</td>
                                            <td><input name="floorProfit" type="text" value="${(supportTradeType.floorProfit?exists)?string((supportTradeType.floorProfit/100)?string("0.00"),'')}" class="input_m">元</td>
                                            <td><input name="topCharge" type="text" value="${(supportTradeType.topCharge?exists)?string((supportTradeType.topCharge/100)?string("0.00"),'')}" class="input_m">元</td>
                                            <td><input name="topProfit" type="text" value="${(supportTradeType.topProfit?exists)?string((supportTradeType.topProfit/100)?string("0.00"),'')}" class="input_m">元</td>
                                        </tr>
                                        </tbody></table>
                                </div>
                            </div>
                        </li>
                    </#if>
                </#list>
            </ul>
        </div>
    </div>

    <div class="btnBottom"><button class="blueBtn"onClick="submitData()">保存</button></div>
</div>

<script type="text/javascript">

    function submitData(){
        var tradeTypeIds = new Array();
        var terminalRates = new Array();
        var baseRates = new Array();
        var floorCharges = new Array();
        var floorProfits = new Array();
        var topCharges = new Array();
        var topProfits = new Array();
        var error = false;
        var baseProfit = $("#baseProfit").prop("value");
        if(error = isNotDecimal(baseProfit, "标准手续费交易分润百分比必须大于0小于100!")){
            return false;
        }
        $(".rate").each(function(i){
            var terminalRate = $(this).find("input[name='terminalRate']").prop("value");
            var baseRate = $(this).find("input[name='baseRate']").prop("value");
            var floorCharge = $(this).find("input[name='floorCharge']").prop("value");
            var floorProfit = $(this).find("input[name='floorProfit']").prop("value");
            var topCharge = $(this).find("input[name='topCharge']").prop("value");
            var topProfit = $(this).find("input[name='topProfit']").prop("value");
            tradeTypeIds[i]=$(this).attr("value");
            if((error = isNotDecimal(terminalRate, "终端费率必须大于0小于100!"))
                || (error = isNotDecimal(baseRate, "基础费率必须大于0小于100!"))
                || (error = isNotTwoDecimal(floorCharge, "最低收费必须为2位小数!"))
                || (error = isNotTwoDecimal(floorProfit, "最低分润必须为2位小数!"))
                || (error = isNotTwoDecimal(topCharge, "最高收费必须为2位小数!"))
                || (error = isNotTwoDecimal(topProfit, "最高分润必须为2位小数!"))){
                return false;
            }
            terminalRates[i] = terminalRate;
            baseRates[i] = baseRate;
            floorCharges[i] = floorCharge;
            floorProfits[i] = floorProfit;
            topCharges[i] = topCharge;
            topProfits[i] = topProfit;
        });
        if(~error){

            $.post("<@spring.url "/good/channel/${channel.id}/editProfit" />",
                    { 'baseProfit': baseProfit,
                        'tradeTypeIds':tradeTypeIds,
                        'terminalRates':terminalRates,
                        'baseRates':baseRates,
                        'floorCharges':floorCharges,
                        'floorProfits':floorProfits,
                        'topCharges':topCharges,
                        'topProfits':topProfits
                    },
                    function(data){
                        if(data.code==1){
                            window.location.href="<@spring.url "/good/channel/list" />"
                        }
                    });
        }
    }
    function isNull(value, error){
        if(value.length==0){
            showErrorTip(error);
            return true;
        }
        return false;
    }

    function isNotTwoDecimal(value, error){
        var re=/^\d+\.\d{2}$/;//2位小数
        if(value.length>0 && !(re.test(value))){
            showErrorTip(error);
            return true;
        }
        return false;
    }

    function isNotDecimal(value, error){
        var re=/^([1-9]|([1-9][0-9]))$/;//2位小数
        if(value.length>0 && !(re.test(value))){
            showErrorTip(error);
            return true;
        }
        return false;
    }
</script>
</@c.html>