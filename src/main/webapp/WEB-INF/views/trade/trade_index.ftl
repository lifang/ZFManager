<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">交易</a></li>
        <li><a href="#">转账</a></li>
    </ul>
</div>
<div class="content clear">

    <div class="dealNav">
        <a href="javascript:void(0);" class="dn_prev">上一页</a>
        <div class="dealNavBox">
            <ul class="li_show" style="left: 0;">
            <#list tradeTypes as tradeType>
                <li data-id="${tradeType.id}"><a href="javascript:void(0);"<#if tradeType_index==0> class="hover"</#if>>${tradeType.tradeValue}</a></li>
            </#list>
            </ul>
        </div>
        <a href="javascript:void(0);" class="dn_next">下一页</a>
    </div>
    <div id="list">
        <#include "trade_list.ftl"/>
    </div>
</div>
<script>
    $(function(){
        $(".li_show > li > a").click(function(){
            $(".li_show > li > a.hover").removeClass();
            $(this).addClass("hover");
            $.get(
                    "<@spring.url "/trade/type/"/>"+$(this).parent().attr("data-id")+"/list",
                    function(data){
                        $("#list").html(data);
            });
        });
    })
</script>
</@c.html>