<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li>商品</li>
        <li><a href="<@spring.url "/good/channel/list"/>">支付通道</a></li>
        <li><a href="<@spring.url "/good/channel/${channel.id}/profit" />">设置分润</a></li>
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
                    <#if supportTradeType.tradeType = 1 && (supportTradeType.baseProfit)??>
                        <#assign baseProfit=supportTradeType.baseProfit />
                    </#if>
                </#list>
                <li class="b height48"><span class="labelSpan">标准手续费交易<br>分润(‰)：</span>
                    <div class="text"><input id="baseProfit" type="text" value="${((baseProfit)??)?string((((baseProfit)!0)/10)?string("0.0"),'')}" /></div>
                </li>
                <li class="b"><span class="labelSpan">资金服务费率：</span>
                    <div class="text">
                        <div class="rate_attributes" id="billingCycle">
                            <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                <colgroup>
                                    <col width="33%">
                                    <col width="33%">
                                </colgroup>
                                <thead>
	                                <tr>
	                                    <td>结算周期</td>
	                                    <td>费率</td>
	                                    <td>分润比例</td>
	                                </tr>
                                </thead>
                                <tbody>
                                <#list channel.billingCycles as billingCycle>
                                <tr>
                                    <td>${billingCycle.dictionaryBillingCycle.name}</td>
                                    <td>
                                    	<input type="text" name="rate" class="input_m" value="${((billingCycle.rate)??)?string((((billingCycle.rate)!0)/10)?string("0.0"),'')}"/>&nbsp;‰
                                    	<input type="hidden" name="billingCycleId" value="${billingCycle.id}">
                                    </td>
                                    <td><input type="text" name="profit" class="input_m" value="${((billingCycle.profit)??)?string((((billingCycle.profit)!0)/10)?string("0.0"),'')}"/>&nbsp;‰</td>
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
                                            <td>终端费率(‰)</td>
                                            <td>基础费率(‰)</td>
                                            <td>最低收费(元)</td>
                                            <td>最低分润(元)</td>
                                            <td>最高收费(元)</td>
                                            <td>最高分润(元)</td>
                                        </tr>
                                        <tr class="rate" value="${supportTradeType.id}">
                                            <td><input name="terminalRate" type="text" value="${((supportTradeType.terminalRate)??)?string((((supportTradeType.terminalRate)!0)/10)?string("0.0"),'')}" class="input_m" ></td>
                                            <td><input name="baseRate" type="text" value="${((supportTradeType.baseRate)??)?string((((supportTradeType.baseRate)!0)/10)?string("0.0"),'')}" class="input_m" ></td>
                                            <td><input name="floorCharge" type="text" value="${(supportTradeType.floorCharge??)?string(((supportTradeType.floorCharge!0)/100)?string("0.00"),'')}" class="input_m"></td>
                                            <td><input name="floorProfit" type="text" value="${(supportTradeType.floorProfit??)?string(((supportTradeType.floorProfit!0)/100)?string("0.00"),'')}" class="input_m"></td>
                                            <td><input name="topCharge" type="text" value="${(supportTradeType.topCharge??)?string(((supportTradeType.topCharge!0)/100)?string("0.00"),'')}" class="input_m"></td>
                                            <td><input name="topProfit" type="text" value="${(supportTradeType.topProfit??)?string(((supportTradeType.topProfit!0)/100)?string("0.00"),'')}" class="input_m"></td>
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
        var billingCycleIds = new Array();
        var rates = new Array();
        var profits = new Array();
        var error = false;
        var baseProfit = $("#baseProfit").prop("value");
        if(error = isNotDecimal(baseProfit, "标准手续费交易分润千分比必须小于1000且最多一位小数!")){
            return false;
        }
        if(baseProfit=="" || baseProfit==undefined || baseProfit<0 ||baseProfit>1000){
        	alert("标准手续费交易不能为空，必须在0--1000之间");
        	return false;
        }
        var re = /^(\d+)(\.\d)?$/;
        $("#billingCycle>table>tbody>tr").each(function(i){
        	var billingCycleId = $(this).find("input[name='billingCycleId']").prop("value");
        	var rate = $(this).find("input[name='rate']").prop("value");
        	var profit = $(this).find("input[name='profit']").prop("value");
        	if(rate=='' || profit==''){
        		 showErrorTip("费率和分润比例必须同时输入");
            	 error = true;
                 return false;
        	}
        	if(rate!='' && !re.test(rate)){
            	 showErrorTip("费率最多1位小数！");
            	 error = true;
                 return false;
            }
            if(profit!=''&&!re.test(profit)){
            	 showErrorTip("分润比例最多1位小数！");
            	 error = true;
                 return false;
            }
            billingCycleIds[i] = billingCycleId;
        	rates[i] = (rate==''?rate:rate*10);
        	profits[i] = (profit==''?profit:profit*10);
        });
        $(".rate").each(function(i){
            var terminalRate = $(this).find("input[name='terminalRate']").prop("value");
            var baseRate = $(this).find("input[name='baseRate']").prop("value");
            var floorCharge = $(this).find("input[name='floorCharge']").prop("value");
            var floorProfit = $(this).find("input[name='floorProfit']").prop("value");
            var topCharge = $(this).find("input[name='topCharge']").prop("value");
            var topProfit = $(this).find("input[name='topProfit']").prop("value");
            tradeTypeIds[i]=$(this).attr("value");
            if(terminalRate=='' || baseRate=='' || floorCharge=='' || floorProfit==''||topCharge=='' || topProfit==''){
        		 showErrorTip("其它交易必须都输入");
            	 error = true;
                 return false;
        	}
            if((error = isNotDecimal(terminalRate, "终端费率必须小于1000且最多一位小数!"))
                || (error = isNotDecimal(baseRate, "基础费率必须小于1000且最多一位小数!"))
                || (error = isNotTwoDecimal(floorCharge, "最低收费必须为2位小数!"))
                || (error = isNotTwoDecimal(floorProfit, "最低分润必须为2位小数!"))
                || (error = isNotTwoDecimal(topCharge, "最高收费必须为2位小数!"))
                || (error = isNotTwoDecimal(topProfit, "最高分润必须为2位小数!"))){
                return false;
            }
            terminalRates[i] = (terminalRate==''?terminalRate:terminalRate*10);
            baseRates[i] = (baseRate==''?baseRate:baseRate*10);
            floorCharges[i] = floorCharge*100;
            floorProfits[i] = floorProfit*100;
            topCharges[i] = topCharge*100;
            topProfits[i] = topProfit*100;
        });
        if(!error){

            $.post("<@spring.url "/good/channel/${channel.id}/profit" />",
                    { 'baseProfit':(baseProfit==''?baseProfit:baseProfit*10),
                        'tradeTypeIds':tradeTypeIds,
                        'terminalRates':terminalRates,
                        'baseRates':baseRates,
                        'floorCharges':floorCharges,
                        'floorProfits':floorProfits,
                        'topCharges':topCharges,
                        'topProfits':topProfits,
                        'billingCycleIds':billingCycleIds,
                        'rates':rates,
                        'profits':profits
                    },
                    function(data){
                        if(data.code==1){
                            window.location.href="<@spring.url "/good/channel/list" />"
                        }
                    });
        }
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
        var re=/^([0-9](\.\d)?|([1-9][0-9](\.\d)?)|([1-9][0-9][0-9](\.\d)?))$/;
        if(value.length>0 && !(re.test(value))){
            showErrorTip(error);
            return true;
        }
        return false;
    }
</script>
</@c.html>