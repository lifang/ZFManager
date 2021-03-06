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
    <link href="<@spring.url "/resources/style/shop.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<@spring.url "/resources/jquery-ui-1.11.3/jquery-ui.min.css"/>" rel="stylesheet" type="text/css"/>
    <!--放大产品-->
	<link href="<@spring.url "/resources/style/jqzoom.css"/>" rel="stylesheet" type="text/css" />
    <script src="<@spring.url "/resources/js/jquery-1.11.2.min.js"/>"></script>
    <script src="<@spring.url "/resources/jquery-ui-1.11.3/jquery-ui.min.js"/>"></script>
    <script src="<@spring.url "/resources/js/jquery.form.min.js"/>"></script>
    <script src="<@spring.url "/resources/js/main.js"/>"></script>
    <script src="<@spring.url "/resources/js/common.js"/>"></script>
    <script src="<@spring.url "/resources/js/jquery.jqzoom.min.js"/>"></script>
    <script src="<@spring.url "/resources/js/pro-show.js"/>"></script>
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
	<#nested />
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