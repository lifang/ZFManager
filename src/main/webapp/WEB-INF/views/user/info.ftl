<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="<@spring.url "/user/list"/>">用户</a></li>
        <li><a href="<@spring.url "/user/${customer.id}/info"/>">用户详情</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>用户详情</h1>
        <div class="userTopBtnBox">
         <#include "info_status.ftl"/>
         </div>
    </div>
    <div class="detailCon">
        <div class="attributes_list clear">
            <ul>
                <li>姓名：${customer.name!"- -"}</li>
                <li>所在地：
                <#if city??>
                    ${(city.parentCity.name)!""}${city.name}
                <#else>- -
                </#if></li>
                <li>手机：${customer.phone!"- -"}</li>
                <li>邮箱：${customer.email!"- -"}</li>
                <li>上次登录：<#if customer.lastLoginedAt??>${customer.lastLoginedAt?datetime}<#else>- -</#if></li>
                <li id="li_integral">积分：${(customer.integral)!0}分</li>
                <input type="hidden" value="${(customer.integral)!0}" id="jifen_value">
            </ul>
        </div>
    </div>
    <div class="userDetail_tab">
        <div class="udt_title">
            <ul>
                <li class="hover">商户资料</li>
                <li>终端</li>
                <li>关联代理商</li>
            </ul>
        </div>
        <div class="udt_con">
            <div id="merchants"></div>
            <div id="terminals" style="display: none;"></div>
            <div id="agents" style="display: none;"></div>
        </div>
    </div>
</div>
<div id="div_integral" class="tab creditsExchange_tab" style="top: 272.5px; left: 495px; display: none;">
    <a class="close">close</a>
    <div class="tabHead">调整积分</div>
    <div class="tabBody">
        <div class="item_list clear">
            <ul>
                <li><span class="labelSpan"><input name="types" type="radio" value="1" checked class=""> 增加：</span>
                    <div class="text"><input name="num1" type="text" class="" onkeyup="value=this.value.replace(/\D+/g,'')"></div>
                </li>
                <li><span class="labelSpan"><input name="types" type="radio" value="2" class=""> 减少：</span>
                    <div class="text"><input name="num2" type="text" class="" onkeyup="value=this.value.replace(/\D+/g,'')"></div>
                </li>
                <li><span class="labelSpan"> 调整原因：</span>
                    <div class="text"><textarea id="reason" cols="" rows=""></textarea></div>
                </li>
            </ul>
        </div>
    </div>
    <div class="tabFoot"><button onclick="integral(${customer.id})" class="blueBtn">确定</button></div>
</div>
<script>
    function merchantPageChange(page){
        $.post("<@spring.url "/user/${customer.id}/info/merchants"/>",
                {page:page},
                function(data){
                    $("#merchants").html(data);
                });
    }
    function terminalPageChange(page){
        $.post("<@spring.url "/user/${customer.id}/info/terminals"/>",
                {page:page},
                function(data){
                    $("#terminals").html(data);
                });
    }
    function agentPageChange(page){
        $.post("<@spring.url "/user/${customer.id}/info/agents"/>",
                {page:page},
                function(data){
                    $("#agents").html(data);
                });
    }
    function userStatus(id){
        $.post("<@spring.url "/user/"/>"+id+"/status?source=info",function(data){
            $(".userTopBtnBox").replaceWith(data);
        });
    }

    function integral(id){
        var types=$("input[name='types']:checked").val();
        var num;
        if(types == 1){
            num = $("input[name='num1']").val();
        } else{
            num = $("input[name='num2']").val();
        }
        if(isNull(num,"积分不能为空！")){
            return false;
        }
		var jifen = $("#jifen_value").val();
		if(Number(num)>Number(jifen)){
			 alert("没有那么多积分可调整");
			 return false;
		}
        var reason = $("#reason").val();
        if(isNull(reason,"调整原因不能为空！")){
            return false;
        }

        $.post("<@spring.url "/user/"/>"+id+"/integral",
                {type:types,
                    num:num,
                    reason:reason
                },function(data){
                    $("#div_integral").hide();
                    $('.mask').hide();
                    $("#li_integral").html("积分："+data.result);
        });


    }

    $(function(){
        merchantPageChange(1);
        terminalPageChange(1);
        agentPageChange(1);
    });
</script>
</@c.html>