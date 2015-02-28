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
                    <div class="text">收账宝</div>
                </li>
            </ul>
        </div>
    </div>

    <div class="attributes_box">
        <h2>刷卡交易</h2>
        <div class="item_list clear">
            <ul>
                <li class="b height48"><span class="labelSpan">标准手续费交易<br>分润百分比：</span>
                    <div class="text"><input name="" type="text"></div>
                </li>
                <li class="b"><span class="labelSpan">资金服务费分润：</span>
                    <div class="text">
                        <div class="rate_attributes">
                            <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                <colgroup>
                                    <col width="33%">
                                    <col width="33%">
                                    <col width="34%">
                                </colgroup>
                                <tbody><tr>
                                    <td>结算周期</td>
                                    <td>费率</td>
                                    <td>分润比例</td>
                                </tr>
                                <tr>
                                    <td>T+0</td>
                                    <td>0.3%</td>
                                    <td><input name="" type="text" class="input_l" value="%"></td>
                                </tr>
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
                <li class="b o"><span class="labelSpan">转账：</span>
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
                                <tr>
                                    <td><input name="" type="text" value="%" class="input_m"></td>
                                    <td><input name="" type="text" value="%" class="input_m"></td>
                                    <td><input name="" type="text" value="元" class="input_m"></td>
                                    <td><input name="" type="text" value="元" class="input_m"></td>
                                    <td><input name="" type="text" value="元" class="input_m"></td>
                                    <td><input name="" type="text" value="元" class="input_m"></td>
                                </tr>
                                </tbody></table>
                            <select name="">
                                <option>支付通道中包含的其他交易类型</option>
                            </select>
                            <a href="#" class="pay_add_a">+</a>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>

    <div class="btnBottom"><button class="blueBtn">保存</button></div>
</div>
</@c.html>