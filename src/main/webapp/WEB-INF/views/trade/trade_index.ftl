<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="<@spring.url "/trade/index"/>">交易</a></li>
        <li>
        	<a href="<@spring.url "/trade/index"/>">
        	<#list tradeTypes as tradeType>
                <#if ((tradeTypeID!1)==tradeType.id)>${tradeType.tradeValue}</#if>
            </#list>
			</a>
		</li>
    </ul>
</div>
<div class="content clear">

    <div class="dealNav">
        <a href="javascript:void(0);" class="dn_prev">上一页</a>
        <div class="dealNavBox">
            <ul class="li_show" style="left: 0;">
            <#list tradeTypes as tradeType>
                <li data-id="${tradeType.id}"><a href="javascript:void(0);"<#if ((tradeTypeID!1)==tradeType.id)> class="hover"</#if>>${tradeType.tradeValue}</a></li>
            </#list>
            </ul>
        </div>
        <a href="javascript:void(0);" class="dn_next">下一页</a>
    </div>
    <div id="list">
        <#include "trade_list.ftl"/>
    </div>
</div>
<div class="mask"></div>
<div class="tab file_tab">
    <a href="javascript:void(0);" class="close">关闭</a>
    <div class="tabHead">上传交易</div>
    <form id="file_form" action="<@spring.url "/trade/import"/>" method="post" enctype="multipart/form-data">
    <div class="tabBody">
        <input name="file" type="file" />
        <input type="hidden" name="selectTradeType" value=""/>
    </div>
    <div class="tabFoot"><button class="blueBtn">确定</button></div>
    </form>
</div>
<script>
    $(function(){
        $(".li_show > li > a").click(function(){
            $(".li_show > li > a.hover").removeClass();
            $(this).addClass("hover");
            $(".breadcrumb>ul>li:last>a").html($(this).html());
            $.get(
                    "<@spring.url "/trade/type/"/>"+$(this).parent().attr("data-id")+"/list",
                    function(data){
                        $("#list").html(data);
            });
        });

        $("#file_form").submit(function(){
        	$("input[type=hidden]").val($(".li_show > li > a.hover").parent().attr("data-id"));
            $(this).ajaxSubmit({
                success : function(data){
                    $(".mask").hide();
                    $(".file_tab").hide();
                    if(data.code!=1){
                        showErrorTip(data.message);
                    }
                }
            });
            return false;
        });
    })
</script>
</@c.html>