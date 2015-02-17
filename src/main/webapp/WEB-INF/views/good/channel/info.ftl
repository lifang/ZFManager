<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">商品</a></li>
        <li><a href="#">支付通道</a></li>
        <li><a href="#">支付通道详情</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>支付通道详情</h1>
        <#include "infoStatus.ftl" />
    </div>
    <div class="attributes_box">
        <h2>基础信息</h2>

        <div class="attributes_list clear">
            <ul>
                <li><em>名称：</em><span>${channel.name}</span></li>
                <li><em>收单机构：</em><span>${channel.factory.name}</span></li>
                <li><em>支持区域：</em>
                    <span>
                        <#if (channel.areas)??>
                            <#list channel.areas as area>
                            ${area.name}
                            </#list>
                        </#if>
                    </span></li>
                <li><em>是否支持注销：</em><span><#if channel.supportCancelFlag>是<#else>否</#if></span></li>
            </ul>
        </div>
    </div>

    <div class="attributes_box">
        <h2>交易费率</h2>

        <div class="attributes_list clear">
            <ul>
                <li><em>刷卡交易标准<br>手续费：</em><span>
                                <div class="rate_attributes">
                                    <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                        <colgroup>
                                            <col width="33%">
                                            <col width="33%">
                                            <col width="34%">
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <td>商户类型</td>
                                            <td>费率</td>
                                            <td>说明</td>
                                        </tr>
                                        <#if (channel.standardRates)??>
                                        <#list channel.standardRates as standardRate>
                                        <tr>
                                            <td>${standardRate.name}</td>
                                            <td>${standardRate.standardRate}%</td>
                                            <td><a onClick="setContent('${(standardRate.description)!""}')" class="a_btn description_a">查看说明</a></td>
                                        </tr>
                                        </#list>
                                        </#if>
                                        </tbody>
                                    </table>
                                </div>
                                </span>
                </li>
                <li><em>资金服务费</em><span>
                                <div class="rate_attributes">
                                    <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                        <colgroup>
                                            <col width="33%">
                                            <col width="33%">
                                            <col width="34%">
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <td>结算周期</td>
                                            <td>费率</td>
                                            <td>说明</td>
                                        </tr>
                                        <#if (channel.billingCycles)??>
                                            <#list channel.billingCycles as billingCycle>
                                            <tr>
                                                <td>${billingCycle.dictionaryBillingCycle.name}</td>
                                                <td>${billingCycle.rate}%</td>
                                                <td><a onClick="setContent('${(billingCycle.description)!""}')" class="a_btn description_a">查看说明</a></td>
                                            </tr>
                                            </#list>
                                        </#if>
                                        </tbody>
                                    </table>
                                </div>
                                </span>
                </li>
            </ul>
        </div>
    </div>

    <div class="attributes_box">
        <h2>开通认证</h2>

        <div class="attributes_list clear">
            <ul>
                <li><em>开通费用：</em><span>10元</span></li>
                <li><em>是否需要预审：</em><span>是</span></li>
                <li><em>开通申请条件：</em><span>各种证件齐全</span></li>
                <li><em>开通申请材料：</em><span>各种证件复印件</span></li>
                <li><em>开通协议：</em><span><a href="#" class="a_btn description_a">查看协议</a></span></li>
                <li><em>开通协议：</em><span>
                                <div class="itl_area">
                                    <div class="ab_l"><em>开通等级名称：</em><span>阿萨德可接受的看法</span></div>
                                    <div class="ab_l"><em>开通等级说明：</em><span>开通等级说明01</span></div>
                                    <div class="ab_l"><em>对公开通所需：</em><span>营业执照招聘01/营业执照招聘02/营业执照招聘03营业执照招聘01/营业执照招聘02/营业执照招聘03营业执照招聘01/营业执照招聘02/营业执照招聘03营业执照招聘01/营业执照招聘02/营业执照招聘03营业执照招聘01/营业执照招聘02</span>
                                    </div>

                                </div>
                                </span>
                </li>
                <li><em>注销所需材料：</em><span>
                                <div class="rate_attributes">
                                    <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                        <colgroup>
                                            <col width="33%">
                                            <col width="33%">
                                            <col width="34%">
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <td>材料名称</td>
                                            <td>材料说明</td>
                                            <td>模版</td>
                                        </tr>
                                        <tr>
                                            <td>营业执照</td>
                                            <td>营业执照复印件存档用</td>
                                            <td><a href="#" class="a_btn">查看模版</a></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                </span>
                </li>
                <li><em>更新资料所需<br>材料：</em><span>
                                <div class="rate_attributes">
                                    <table width="100%" border="0" cellspacing="1" cellpadding="0">
                                        <colgroup>
                                            <col width="33%">
                                            <col width="33%">
                                            <col width="34%">
                                        </colgroup>
                                        <tbody>
                                        <tr>
                                            <td>材料名称</td>
                                            <td>材料说明</td>
                                            <td>模版</td>
                                        </tr>
                                        <tr>
                                            <td>营业执照</td>
                                            <td>营业执照复印件存档用</td>
                                            <td><a href="#" class="a_btn">查看模版</a></td>
                                        </tr>
                                        </tbody>
                                    </table>
                                </div>
                                </span>
                </li>
            </ul>
        </div>
    </div>
</div>
<div class="tab description_tab">
    <a href="javascript:void(0);" class="close">关闭</a>
    <div class="tabHead">详细说明</div>
    <div class="tabBody">
        <div class="lease_con">
        </div>
    </div>
</div>
<script type="text/javascript">

    function setContent(content){
        $(".lease_con").html(content);
    }
</script>
</@c.html>