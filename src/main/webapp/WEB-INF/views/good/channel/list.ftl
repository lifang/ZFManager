<#import "../../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">商品</a></li>
        <li><a href="#">支付通道</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>支付通道列表</h1>
        <div class="userTopBtnBox">
            <a href="<@spring.url "/good/channel/create" />" class="ghostBtn">创建支付通道</a>
        </div>
    </div>

    <div class="seenBox clear">
        <ul>
            <li>
                <div class="user_search">
                    <input  id="search_keys" type="text" />
                    <input id="hidden_keys" type="hidden" name="keys" value="" />
                    <input id="hidden_status" type="hidden" name="status" value="" />
                    <button id="btn_search"></button>
                </div>
            </li>
            <li><div class="user_select">
                <label>状态筛选</label>
                <select id="select_status">
                    <option value="0">全部</option>
                    <option value="1">待审核</option>
                    <option value="2">初审不通过</option>
                    <option value="3">初审通过</option>
                    <option value="4">审核不通过</option>
                    <option value="5">正常</option>
                    <option value="6">已停用</option>
                </select>
            </div></li>
        </ul>
    </div>
    <div id="page_fresh">
        <#include "pageChannel.ftl" />
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $('#select_status').change(function(){
            var status = $(this).children('option:selected').val();
            $("#hidden_status").val(status);
            channelPageChange(1);
        });

        $("#btn_search").bind("click",
                function() {
                    var keys = $("#search_keys").val();
                    $("#hidden_keys").val(keys);
                    channelPageChange(1);
                });
    });

    function channelPageChange(page) {
        var keys = $("#hidden_keys").val();
        var status = $("#hidden_status").val();
        $.get('<@spring.url "/good/channel/page" />',
                {"page": page,
                    "keys": keys,
                    "status": status
                },
                function (data) {
                    $('#page_fresh').html(data);
                });
    }


    function firstUnCheck(id){
        $.get('<@spring.url "" />'+'/good/channel/'+id+'/firstUnCheck',
                function (data) {
                    $('#row_'+id).replaceWith(data);
                });
    }

    function firstCheck(id){
        $.get('<@spring.url "" />'+'/good/channel/'+id+'/firstCheck',
                function (data) {
                    $('#row_'+id).replaceWith(data);
                });
    }

    function unCheck(id){
        $.get('<@spring.url "" />'+'/good/channel/'+id+'/unCheck',
                function (data) {
                    $('#row_'+id).replaceWith(data);
                });
    }

    function check(id){
        $.get('<@spring.url "" />'+'/good/channel/'+id+'/check',
                function (data) {
                    $('#row_'+id).replaceWith(data);
                });
    }
    function stop(id){
        $.get('<@spring.url "" />'+'/good/channel/'+id+'/stop',
                function (data) {
                    $('#row_'+id).replaceWith(data);
                });
    }
    function start(id){
        $.get('<@spring.url "" />'+'/good/channel/'+id+'/start',
                function (data) {
                    $('#row_'+id).replaceWith(data);
                });
    }


</script>
</@c.html>