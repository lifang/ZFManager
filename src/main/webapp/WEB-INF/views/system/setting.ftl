<#import "../common.ftl" as c />
<@c.html>
<div class="right">
    <div class="breadcrumb">
        <ul>
            <li><a href="#">系统</a></li>
            <li><a href="#">系统参数</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title"><h1>设定系统参数</h1>
        </div>
        <div class="attributes_box">
            <div class="item_list clear">
                <ul>
                    <li class="b pll"><span class="labelSpan">交易流水默认分润比例：</span>
                        <div class="text"><input name="${SysConfig.TRADE_RECORD_DEFAULT_PROFIT}" value="${(sysConfigsMap[SysConfig.TRADE_RECORD_DEFAULT_PROFIT].paramValue)!""}" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > ‰</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">租金默认分润比例：</span>
                        <div class="text"><input name="${SysConfig.HIRE_DEFAULT_PROFIT}" value="${(sysConfigsMap[SysConfig.HIRE_DEFAULT_PROFIT].paramValue)!""}" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > ‰</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">批购订单定金支付比例：</span>
                        <div class="text"><input name="${SysConfig.PURCHASE_ORDER_RATIO}" value="${(sysConfigsMap[SysConfig.PURCHASE_ORDER_RATIO].paramValue)!""}" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > ‰</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">允许退货时间：</span>
                        <div class="text"><input name="${SysConfig.RETURN_TIME}" value="${(sysConfigsMap[SysConfig.RETURN_TIME].paramValue)!""}" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 天内</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">允许换货时间：</span>
                        <div class="text"><input name="${SysConfig.CHANGE_TIME}" value="${(sysConfigsMap[SysConfig.CHANGE_TIME].paramValue)!""}" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 天内</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">业务开通状态轮询间隔：</span>
                        <div class="text"><input name="${SysConfig.OPEN_POLLING_INTERVAL}" value="${(sysConfigsMap[SysConfig.OPEN_POLLING_INTERVAL].paramValue)!""}" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 小时</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">注销轮询时间间隔：</span>
                        <div class="text"><input name="${SysConfig.CANCEL_POLLING_INTERVAL}" value="${(sysConfigsMap[SysConfig.CANCEL_POLLING_INTERVAL].paramValue)!""}" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 小时</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">订单过期时间：</span>
                        <div class="text"><input name="${SysConfig.ORDER_EXPIRED_TIME}" value="${(sysConfigsMap[SysConfig.ORDER_EXPIRED_TIME].paramValue)!""}" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 小时</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">业务记录过期时间：</span>
                        <div class="text"><input name="${SysConfig.BUSINESS_EXPIRED_TIME}" value="${(sysConfigsMap[SysConfig.BUSINESS_EXPIRED_TIME].paramValue)!""}" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 天</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">积分获取：</span>
                        <div class="text">
                            <div class="supportArea">
                                <div class="sa_list">
                                    <span class="checkboxRadio_span">购买POS机</span>
                                    <#assign paramValue><#if (sysConfigsMap[SysConfig.INTEGRAL_BUY_POS].paramValue)??>${(sysConfigsMap[SysConfig.INTEGRAL_BUY_POS].paramValue)?number/100}</#if></#assign>
                                    <input name="${SysConfig.INTEGRAL_BUY_POS}" value="${paramValue!""}" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 元 = 1 积分
                                </div>
                                <div class="sa_list">
                                    <span class="checkboxRadio_span">交易流水&nbsp;</span>
                                    <#assign paramValue><#if (sysConfigsMap[SysConfig.INTEGRAL_TRADE].paramValue)??>${(sysConfigsMap[SysConfig.INTEGRAL_TRADE].paramValue)?number/100}</#if></#assign>
                                    <input name="${SysConfig.INTEGRAL_TRADE}" value="${paramValue!""}" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 元 = 1 积分
                                </div>
                            </div>
                        </div>
                    </li>
                    <li class="b pll"><span class="labelSpan">积分兑换：</span>
                        <div class="text"><input name="${SysConfig.INTEGRAL_CONVERT}" value="${(sysConfigsMap[SysConfig.INTEGRAL_CONVERT].paramValue)!""}" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 积分 = 1元</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">批购优惠算法：</span>
                        <div class="text">
                            <div class="supportArea">
                                <div class="sa_list">
                                    <span class="checkboxRadio_span">同型POS购买</span>
                                    <input name="${SysConfig.SHOP_COUNT}" value="${(sysConfigsMap[SysConfig.SHOP_COUNT].paramValue)!""}" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 台
                                    <span>优惠</span> <input name="${SysConfig.SHOP_COUNT}" value="${(sysConfigsMap[SysConfig.SHOP_COUNT].remark)!""}" type="text" class="input_m" onkeyup="value=this.value.replace(/\D+/g,'')" > ‰
                                </div>
                                <div class="sa_list">
                                    <span class="checkboxRadio_span">累积交易流水</span>
                                    <#assign paramValue><#if (sysConfigsMap[SysConfig.TOTAL_MONEY].paramValue)??>${(sysConfigsMap[SysConfig.TOTAL_MONEY].paramValue)?number/100}</#if></#assign>
                                    <input name="${SysConfig.TOTAL_MONEY}" value="${paramValue!""}" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 元
                                    <span>优惠</span> <input name="${SysConfig.TOTAL_MONEY}" value="${(sysConfigsMap[SysConfig.TOTAL_MONEY].remark)!""}" type="text" class="input_m" onkeyup="value=this.value.replace(/\D+/g,'')" > ‰
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
        </div>
        <div class="btnBottom"><button class="blueBtn" id="sumbitData">保存</button></div>
    </div>
</div>
<script>
    $(function(){
        $("#sumbitData").click(function(){
            var hasNull = false;
            var datas = {};
            $(":text").each(function(){
                var value = $(this).val();
                var key = $(this).attr("name");
                if(isNotNull(value)){
                    var data = datas[key];
                    if(!isNotNull(data)){
                        datas[key] = {};
                    }
                    if($(this).attr("class")=="input_m"){
                        datas[key].remark = Number(value);
                    } else{
                        datas[key].paramValue = Number(value);
                    }
                } else{
                    var content = $(this).prev("span").html();
                    if(!isNotNull(content)){
                        content = $(this).parent("div").prev("span").html();
                    }
                    content += "不能为空！";
                    showErrorTip(content);
                    hasNull = true;
                    return false;
                }
            });
            if(hasNull){
                return false;
            }
            $.post("<@spring.url "/system/setting/edit" />",
                    {configs: JSON.stringify(datas)},
                    function (data) {
                        if(data.code==1) {
                            showErrorTip("修改成功！");
                        }

                    });
        });
    });

    function isNotNull(value){
        return value != "" && value != null && value != undefined;
    }
</script>
</@c.html>
