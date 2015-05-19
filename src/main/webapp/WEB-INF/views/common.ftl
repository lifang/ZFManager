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
    <link href="<@spring.url "/resources/style/main.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<@spring.url "/resources/jquery-ui-1.11.3/jquery-ui.min.css"/>" rel="stylesheet" type="text/css"/>
    <script src="<@spring.url "/resources/js/jquery-1.11.2.min.js"/>"></script>
    <script src="<@spring.url "/resources/jquery-ui-1.11.3/jquery-ui.min.js"/>"></script>
    <script src="<@spring.url "/resources/js/jquery.form.min.js"/>"></script>
    <script src="<@spring.url "/resources/js/main.js"/>"></script>
    <script src="<@spring.url "/resources/js/common.js"/>"></script>
    <script src="<@spring.url "/resources/js/placeholderFix.js"/>"></script>
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
        <div class="top_user"><span>${logged_customer.name!"未命名"}</span>
            <a href="<@spring.url "/index"/>">运营中心首页</a>
            <a href="<@spring.url "/logout"/>">退出</a></div>
    </div>
    <div class="clear"></div>
</div>
</#macro>

<#macro body_head>
<div class="head clear">
    <div class="box">
        <a href="<@spring.url "/index"/>" class="logo">华尔街金融</a>
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
        <#if Roles.hasRole("USER_ORDER")||Roles.hasRole("AGENT_BATCH_ORDER")||Roles.hasRole("AGENT_ORDER")>
            <li class="second"><a href="javascript:void(0);" <#if urlCheck(request,"/order")> class="hover"</#if>>订单管理<i class="<#if urlCheck(request,"/order")>on<#else>off</#if>" id="menuOrder"></i></a>
                <ol <#if urlCheck(request,"/order")>style="display: block;"</#if>>
                    <#if Roles.hasRole("USER_ORDER")><li><a href="<@spring.url "/order/user/list"/>"<#if urlCheck(request,"/order/user")> class="hover"</#if>>用户订单</a></li></#if>
                    <!--<#if Roles.hasRole("AGENT_BATCH_ORDER")><li><a href="<@spring.url "/order/batch/list"/>"<#if urlCheck(request,"/order/batch")> class="hover"</#if>>代理商批购</a></li></#if>-->
                    <#if Roles.hasRole("AGENT_ORDER")><li><a href="<@spring.url "/order/agent/list"/>"<#if urlCheck(request,"/order/agent")> class="hover"</#if>>代理商采购</a></li></#if>
                </ol>
            </li>
        </#if>
        <#if Roles.hasRole("USER")><li><a href="<@spring.url "/user/list"/>"<#if urlCheck(request,"/user")> class="hover"</#if>>用户管理</a></li></#if>
        <#if Roles.hasRole("POS")||Roles.hasRole("PAY_CHANNEL")>
            <li class="second"><a href="javascript:void(0);" <#if urlCheck(request,"/good")> class="hover"</#if>>商品管理<i class="<#if urlCheck(request,"/good")>on<#else>off</#if>" id="menuGood"></i></a>
                <ol <#if urlCheck(request,"/good")>style="display: block;"</#if>>
                    <#if Roles.hasRole("POS")><li><a href="<@spring.url "/good/pos/list"/>" <#if urlCheck(request,"/good/pos")> class="hover"</#if>>POS机管理</a></li></#if>
                    <#if Roles.hasRole("PAY_CHANNEL")><li><a href="<@spring.url "/good/channel/list"/>"<#if urlCheck(request,"/good/channel")> class="hover"</#if>>支付通道</a></li></#if>

                </ol>
            </li>
        </#if>
        <!--<#if Roles.hasRole("TERMINAL")>
            <li class="second"><a href="javascript:void(0);">终端管理<i class="off" id="menuGood"></i></a>
                <ol>
                    <#if Roles.hasRole("TERMINAL")><li><a href="<@spring.url "/terminal/list"/>" <#if urlCheck(request,"/terminal")> class="hover"</#if>>终端列表管理</a></li></#if>
                    <#if Roles.hasRole("TERMINAL")><li>
                    	<a href="<@spring.url "/terminalCs/terminalService" />" class="hover">
                    		代理商售后
                    	</a></li>
                    	</#if>

                </ol>
            </li>
        </#if>-->
        <#if Roles.hasRole("TERMINAL")><li><a href="<@spring.url "/terminal/list"/>"<#if urlCheck(request,"/terminal")> class="hover"</#if>>终端管理</a></li></#if>
        <#if Roles.hasRole("CS_UPDATE_INFO")||Roles.hasRole("CS_AGENT")||Roles.hasRole("CS_REPAIR")||Roles.hasRole("CS_CHANGE")||Roles.hasRole("CS_RETURN")||Roles.hasRole("CS_LEASE_RETURN")||Roles.hasRole("CS_CANCEL")>
            <li class="second"><a href="javascript:void(0);" <#if urlCheck(request,"/cs")> class="hover"</#if>>售后管理<i class="<#if urlCheck(request,"/cs")>on<#else>off</#if>" id="menuCs"></i></a>
                <ol <#if urlCheck(request,"/cs")>style="display: block;"</#if>> 
                    <#if Roles.hasRole("CS_UPDATE_INFO")><li><a href="<@spring.url "/cs/update/list"/>"<#if urlCheck(request,"/cs/update")> class="hover"</#if>>资料更新</a></li></#if>
                    <#if Roles.hasRole("CS_AGENT")><li><a href="<@spring.url "/cs/agent/list"/>"<#if urlCheck(request,"/cs/agent")> class="hover"</#if>>代理商售后</a></li></#if>
                    <#if Roles.hasRole("CS_REPAIR")><li><a href="<@spring.url "/cs/repair/list"/>"<#if urlCheck(request,"/cs/repair")> class="hover"</#if>>维修</a></li></#if>
                    <#if Roles.hasRole("CS_CHANGE")><li><a href="<@spring.url "/cs/change/list"/>"<#if urlCheck(request,"/cs/change")> class="hover"</#if>>换货</a></li></#if>
                    <#if Roles.hasRole("CS_RETURN")><li><a href="<@spring.url "/cs/return/list"/>"<#if urlCheck(request,"/cs/return")> class="hover"</#if>>退货</a></li></#if>
                    <#if Roles.hasRole("CS_LEASE_RETURN")><li><a href="<@spring.url "/cs/lease/list"/>"<#if urlCheck(request,"/cs/lease")> class="hover"</#if>>租赁退还</a></li></#if>
                    <#if Roles.hasRole("CS_CANCEL")><li><a href="<@spring.url "/cs/cancel/list"/>"<#if urlCheck(request,"/cs/cancel")> class="hover"</#if>>注销</a></li></#if>
                </ol>
            </li>
        </#if>
        <#if Roles.hasRole("STOCK_MANAGE")||Roles.hasRole("CERTIFIED_OPEN")||Roles.hasRole("CALCULUS")||Roles.hasRole("OUT_STORE")||Roles.hasRole("REFUND")>
            <li class="second"><a href="javascript:void(0);" <#if urlCheck(request,"/task")> class="hover"</#if>>任务处理<i class="<#if urlCheck(request,"/task")>on<#else>off</#if>" id="menuTask"></i></a>
                <ol <#if urlCheck(request,"/task")>style="display: block;"</#if>>
                    <#if Roles.hasRole("STOCK_MANAGE")><li><a href="<@spring.url "/task/stockManage/index"/>"<#if urlCheck(request,"/task/stockManage")> class="hover"</#if>>售后库存管理</a></li></#if>
                    <#if Roles.hasRole("CERTIFIED_OPEN")><li><a href="<@spring.url "/task/certifiedopen/list"/>"<#if urlCheck(request,"/task/certifiedopen")> class="hover"</#if>>认证开通</a></li></#if>
                    <#if Roles.hasRole("CALCULUS")> <li><a href="<@spring.url "/task/calculus/list"/>"<#if urlCheck(request,"/task/calculus")> class="hover"</#if>>积分兑换</a></li></#if>
                    <#if Roles.hasRole("OUT_STORE")> <li><a href="<@spring.url "/task/outStore/list"/>"<#if urlCheck(request,"/task/outStore")> class="hover"</#if>>出库</a></li></#if>
                    <#if Roles.hasRole("REFUND")><li><a href="<@spring.url "/task/refund/list"/>"<#if urlCheck(request,"/task/refund")> class="hover"</#if>>退款</a></li></#if>
                </ol>
            </li>
        </#if>
        <#if Roles.hasRole("TRADE_RECORD")><li><a href="<@spring.url "/trade/index"/>"<#if urlCheck(request,"/trade")> class="hover"</#if>>交易流水查询</a></li></#if>
        <#if Roles.hasRole("INTENTION")><li><a href="<@spring.url "/task/intention/list"/>"<#if urlCheck(request,"/task/intention")> class="hover"</#if>>购买/申请意向</a></li></#if>
        <li class="second"><a href="javascript:void(0);"<#if urlCheck(request,"/system")> class="hover"</#if>>系统设置<i class="<#if urlCheck(request,"/system")>on<#else>off</#if>" id="menuSystem"></i></a>
            <ol <#if urlCheck(request,"/system")>style="display: block;"</#if>>
                <li><a href="<@spring.url "/system/account/setting/modify"/>"<#if urlCheck(request,"/system/account/setting")> class="hover"</#if>>修改密码</a></li>
                <#if Roles.hasRole("ZF_ACCOUNT")><li><a href="<@spring.url "/system/operate/accounts"/>"<#if urlCheck(request,"/system/operate")> class="hover"</#if>>运营账号管理</a></li></#if>
                <#if Roles.hasRole("AGENT")><li><a href="<@spring.url "/system/agent/list"/>"<#if urlCheck(request,"/system/agent")> class="hover"</#if>>合作伙伴管理</a></li></#if>
                <#if Roles.hasRole("FACTORY")><li><a href="<@spring.url "/system/factory/list"/>"<#if urlCheck(request,"/system/factory")> class="hover"</#if>>第三方机构管理</a></li></#if>
                <#if Roles.hasRole("SYS_MESSAGE")><li><a href="<@spring.url "/system/message/list"/>"<#if urlCheck(request,"/system/message")> class="hover"</#if>>系统消息</a></li></#if>
                <#if Roles.hasRole("WEB_INFO")><li><a href="<@spring.url "/system/content/webmessage"/>"<#if urlCheck(request,"/system/content")> class="hover"</#if>>网站内容</a></li></#if>
                <#if Roles.hasRole("SYS_DICTIONARY")><li><a href="<@spring.url "/system/dictionary"/>"<#if urlCheck(request,"/system/dictionary")> class="hover"</#if>>数据字典</a></li></#if>
                <#if Roles.hasRole("SYS_CONFIG")><li><a href="<@spring.url "/system/setting"/>"<#if urlCheck(request,"/system/setting")> class="hover"</#if>>系统参数</a></li></#if>
            </ol>
        </li>
    </ul>
</div>
</#macro>

<#macro foot>
<div class="foot">
    <div class="box">
        <div class="copyright">版权所有 &copy; 2011-2015 上海掌富网络技术有限公司（备案号 1236548）</div>
    </div>
</div>

<div class="mask"></div>
<div class="tab" id="errorDiv">
    <a class="close errorClose">关闭</a>
    <div class="tabBody">
        <p id="errorP"></p>
    </div>
    <div class="tabFoot"><button class="blueBtn" id="errorBtn">确定<tton></div>
</div>

<div class="tab videoInform_tab" id="videoShowDiv">
    <a class="close">关闭</a>
    <div class="tabHead">视频认证提示</div>
    <div class="tabBody" id="videoClickDiv">
        <div class="videoInform_tabCon">
            <i></i>你有一条视频认证通知！
        </div>
    </div>
</div>

<#if Roles.hasRole("CERTIFIED_OPEN_VIDEO_VERIFY")>
<script>
    var noticeVideoId;
    var reshVideo;
    $(function(){
        reshVideo = setInterval(taskRefreshVideo, 10000);
        $("#videoClickDiv").click(function(){
            $("#videoShowDiv").css('display','none');
            $(".mask").css('display','none');
            reshVideo = setInterval(taskRefreshVideo, 10000);
            window.open("<@spring.url "/task/certifiedopen/"/>"+noticeVideoId+"/video");
        });

    function taskRefreshVideo(){
        $.get("<@spring.url "/notice/getVideo"/>",
                function(data){
                    if(data.code==1){
                        noticeVideoId = data.result;
                        if(noticeVideoId != 0){
                            clearInterval(reshVideo);
                            var doc_height = $(document).height();
                            var doc_width = $(document).width();
                            var win_height = $(window).height();
                            var win_width = $(window).width();

                            var layer_height = $("#videoShowDiv").height();
                            var layer_width = $("#videoShowDiv").width();
                            var scrollTop = document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop;

                            $(".mask").css({display:'block',height:doc_height});
                            $("#videoShowDiv").css('top',(win_height-layer_height)/2);
                            $("#videoShowDiv").css('left',(win_width-layer_width)/2);
                            $("#videoShowDiv").css('display','block');
                        }
                    }
                }
        );

    }
</script>
</#if>

</#macro>

<#function urlCheck request pre>
    <#return request.requestUri?substring(request.contextPath?length)?starts_with(pre)>
</#function>
