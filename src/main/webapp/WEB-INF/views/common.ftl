<#macro html>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<@head />
<@body>
    <#nested />
</@body>
</html>
</#macro>

<#macro html_head>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
    <@head>
        <#nested "head"/>
    </@head>
    <@body>
        <#nested "body"/>
    </@body>
</html>
</#macro>

<#macro head>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <title>运营中心</title>
    <link href="<@spring.url "/resources/style/style.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<@spring.url "/resources/jquery-ui-1.11.3/jquery-ui.min.css"/>" rel="stylesheet" type="text/css"/>
    <script src="<@spring.url "/resources/js/jquery-1.11.2.min.js"/>"></script>
    <script src="<@spring.url "/resources/jquery-ui-1.11.3/jquery-ui.min.js"/>"></script>
    <script src="<@spring.url "/resources/js/jquery.form.min.js"/>"></script>
    <script src="<@spring.url "/resources/js/main.js"/>"></script>
    <script src="<@spring.url "/resources/js/common.js"/>"></script>
    <#nested />
</head>
</#macro>

<#macro body>
<body>
<@top />
<@body_head />
<@main>
<#nested />
</@main>
<@foot />
</body>
</#macro>

<#macro top>
<div class="topInfo clear">
    <div class="box">
        <div class="top_user"><a href="#">${logged_customer.name!"未命名"}</a><a href="<@spring.url "/logout"/>">退出</a></div>
    </div>
    <div class="clear"></div>
</div>
</#macro>

<#macro body_head>
<div class="head clear">
    <div class="box">
        <div class="logo">华尔街金融</div>
        <div class="systemName">运营中心</div>
    </div>
</div>
</#macro>

<#macro main>
<div class="main">
    <div class="box">
        <@left />
        <div class="right">
            <#nested />
        </div>
    </div>
</div>
</#macro>

<#macro left>
<div class="left">
    <ul>
        <li><a href="<@spring.url "/index"/>"<#if urlCheck(request,"/index")> class="hover"</#if>>运营中心首页</a></li>
        <li><a href="<@spring.url "/real/trade"/>" target="map">全国交易实时统计</a></li>
        <li class="second"><a href="javascript:void(0);">商品<i class="off"></i></a>
            <ol>
                <li><a href="<@spring.url "/good/pos/list"/>"<#if urlCheck(request,"/pos")> class="hover"</#if>>POS机管理</a></li>
                <li><a href="<@spring.url "/good/channel/list"/>"<#if urlCheck(request,"/channel")> class="hover"</#if>>支付通道</a></li>
            </ol>
        </li>
        <li><a href="<@spring.url "/user/list"/>"<#if urlCheck(request,"/user")> class="hover"</#if>>用户</a></li>
        <li><a href="<@spring.url "/terminal/list"/>"<#if urlCheck(request,"/terminal")> class="hover"</#if>>终端</a></li>
        <li><a href="<@spring.url "/trade/index"/>"<#if urlCheck(request,"/trade")> class="hover"</#if>>交易</a></li>
        <li class="second"><a href="javascript:void(0);">订单<i class="off"></i></a>
            <ol>
                <li><a href="<@spring.url "/order/user/list"/>"<#if urlCheck(request,"/order/user")> class="hover"</#if>>用户订单</a></li>
                <li><a href="<@spring.url "/order/batch/list"/>"<#if urlCheck(request,"/order/batch")> class="hover"</#if>>代理商批购</a></li>
                <li><a href="<@spring.url "/order/agent/list"/>"<#if urlCheck(request,"/order/agent")> class="hover"</#if>>代理商代购</a></li>
            </ol>
        </li>
        <li class="second"><a href="javascript:void(0);">售后<i class="off"></i></a>
            <ol>
                <li><a href="<@spring.url "/cs/update/list"/>"<#if urlCheck(request,"/cs/update")> class="hover"</#if>>资料更新</a></li>
                <li><a href="<@spring.url "/cs/agent/list"/>"<#if urlCheck(request,"/cs/agent")> class="hover"</#if>>代理商售后</a></li>
                <li><a href="<@spring.url "/cs/repair/list"/>"<#if urlCheck(request,"/cs/repair")> class="hover"</#if>>维修</a></li>
                <li><a href="<@spring.url "/cs/change/list"/>"<#if urlCheck(request,"/cs/change")> class="hover"</#if>>换货</a></li>
                <li><a href="<@spring.url "/cs/return/list"/>"<#if urlCheck(request,"/cs/return")> class="hover"</#if>>退货</a></li>
                <li><a href="<@spring.url "/cs/lease/list"/>"<#if urlCheck(request,"/cs/lease")> class="hover"</#if>>租赁退还</a></li>
                <li><a href="<@spring.url "/cs/cancel/list"/>"<#if urlCheck(request,"/cs/cancel")> class="hover"</#if>>注销</a></li>
            </ol>
        </li>
        <li class="second"><a href="javascript:void(0);">任务<i class="off"></i></a>
            <ol>
                <li><a href="#">售后库存管理</a></li>
                <li><a href="#">认证开通</a></li>
                <li><a href="#">积分兑换</a></li>
                <li><a href="#">出库</a></li>
                <li><a href="#">退款</a></li>
            </ol>
        </li>
        <li><a href="#">购买意向</a></li>
        <li class="second"><a href="javascript:void(0);"<#if urlCheck(request,"/system")> class="hover"</#if>>系统<i class="<#if urlCheck(request,"/system")>on<#else>off</#if>"></i></a>
            <ol<#if urlCheck(request,"/system")> style="display: block;"</#if>>
                <li><a href="<@spring.url "/system/operate/accounts"/>"<#if urlCheck(request,"/system/operate")> class="hover"</#if>>运营账号</a></li>
                <li><a href="<@spring.url "/system/message/list"/>"<#if urlCheck(request,"/system/message")> class="hover"</#if>>系统消息</a></li>
                <li><a href="<@spring.url "/system/setting"/>"<#if urlCheck(request,"/system/setting")> class="hover"</#if>>系统参数</a></li>
                <li><a href="<@spring.url "/system/dictionary"/>"<#if urlCheck(request,"/system/dictionary")> class="hover"</#if>>数据字典</a></li>
                <li><a href="<@spring.url "/system/factory/list"/>"<#if urlCheck(request,"/system/factory")> class="hover"</#if>>第三方机构</a></li>
                <li><a href="<@spring.url "/system/agent/list"/>"<#if urlCheck(request,"/system/agent")> class="hover"</#if>>代理商</a></li>
                <li><a href="<@spring.url "/system/content/webmessage"/>"<#if urlCheck(request,"/system/content")> class="hover"</#if>>网站内容</a></li>
                <li><a href="<@spring.url "/system/account/setting/modify"/>"<#if urlCheck(request,"/system/account/setting")> class="hover"</#if>>账户设置</a></li>
            </ol>
        </li>
    </ul>
</div>
</#macro>

<#macro foot>
<div class="foot">
    <div class="box">
        <ul class="foot_nav">
            <li><a href="#">关于我们</a></li>
            <li>/</li>
            <li><a href="#">企业文化</a></li>
            <li>/</li>
            <li><a href="#">帮助中心</a></li>
            <li>/</li>
            <li><a href="#">企业招聘</a></li>
        </ul>
        <div class="copyright">版权所有 &copy; 2011-2014 上海掌富网络技术有限公司（备案号 1236548）</div>
    </div>
</div>

<div class="mask"></div>
<div class="tab" id="errorDiv">
	<a href="#" class="close errorClose">关闭</a>
    <div class="tabBody">
    	<p id="errorP"></p>
    </div>
    <div class="tabFoot"><button class="blueBtn" id="errorBtn">确定<tton></div>
</div>
</#macro>

<#function urlCheck request pre>
    <#return request.requestUri?substring(request.contextPath?length)?starts_with(pre)>
</#function>