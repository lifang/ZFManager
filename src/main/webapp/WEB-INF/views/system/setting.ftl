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
                        <div class="text"><input name="" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > %</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">租金默认分润比例：</span>
                        <div class="text"><input name="" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > %</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">默认分润比例：</span>
                        <div class="text"><input name="" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > %</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">允许退货时间：</span>
                        <div class="text"><input name="" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 天内</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">允许换货时间：</span>
                        <div class="text"><input name="" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 天内</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">业务开通状态轮询间隔：</span>
                        <div class="text"><input name="" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 小时</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">注销轮询时间间隔：</span>
                        <div class="text"><input name="" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 小时</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">订单过期时间：</span>
                        <div class="text"><input name="" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 小时</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">业务记录过期时间：</span>
                        <div class="text"><input name="" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 天</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">积分获取：</span>
                        <div class="text">
                            <div class="supportArea">
                                <div class="sa_list">
                                    <span class="checkboxRadio_span">购买POS机</span>
                                    <input name="" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 元 = 1 积分
                                </div>
                                <div class="sa_list">
                                    <span class="checkboxRadio_span">交易流水&nbsp;</span>
                                    <input name="" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 元 = 1 积分
                                </div>
                            </div>
                        </div>
                    </li>
                    <li class="b pll"><span class="labelSpan">积分兑换：</span>
                        <div class="text"><input name="" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 积分 = 1元</div>
                    </li>
                    <li class="b pll"><span class="labelSpan">批购优惠算法：</span>
                        <div class="text">
                            <div class="supportArea">
                                <div class="sa_list">
                                    <span class="checkboxRadio_span">同型POS购买&nbsp;</span>
                                    <input name="" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 台
                                    <span>优惠</span> <input name="" type="text" class="input_m" onkeyup="value=this.value.replace(/\D+/g,'')" > %
                                </div>
                                <div class="sa_list">
                                    <span class="checkboxRadio_span">累积交易流水</span>
                                    <input name="" type="text" class="input_l" onkeyup="value=this.value.replace(/\D+/g,'')" > 元
                                    <span>优惠</span> <input name="" type="text" class="input_m" onkeyup="value=this.value.replace(/\D+/g,'')" > %
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
                    datas[key] = value;
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

        });

    });

    function isNotNull(value){
        return value != "" && value != null && value != undefined;
    }
</script>
</@c.html>
