<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="#">管理终端</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title"><h1>终端列表</h1>
        </div>

        <div class="seenBox clear">
            <ul>
                <li>
                    <div class="user_search">
                        <select id="search_key_type" name="search_key_type">
                            <option value="1">终端号</option>
                            <option value="2">订单编号</option>
                        </select>
                        <input id="search_keys" name="" type="text" placeholder="请输入终端号或订单编号查询">
                        <input id="hidden_keys" type="hidden" name="keys">
                        <input id="hidden_status" type="hidden" name="status">
                        <input id="hidden_key_type" type="hidden" name="key_type">
                        <button id="btn_search"></button>
                    </div>
                </li>
                <li><div class="user_select">
                    <label>状态筛选</label>
                    <select id="select_status">
                        <option value="0">全部</option>
                        <option value="3">未开通</option>
                        <option value="2">部分开通</option>
                        <option value="1">已开通</option>
                        <option value="4">已注销</option>
                        <option value="5">已停用</option>
                    </select>
                </div></li>
            </ul>
        </div>
        <div id="page_fresh">
            <#include "pageTerminal.ftl" />
        </div>
    </div>
<script type="text/javascript">
    $(function(){
        $('#select_status').change(function(){
            var status = $(this).children('option:selected').val();
            $("#hidden_status").val(status);
            terminalPageChange(1);
        });

        $("#btn_search").bind("click",
                function() {
                    var keys=$.trim($("#search_keys").val());
                    var key_type=$("#search_key_type").val();
                    $("#hidden_keys").val(keys);
                    $("#hidden_key_type").val(key_type);
                    terminalPageChange(1);
                });
    });

    function terminalPageChange(page) {
        var keys = $("#hidden_keys").val();
        var status = $("#hidden_status").val();
        var key_type=$("#hidden_key_type").val();
        if(key_type==1){
            var terminalNum=keys;
        }else{
            var orderNum=keys;
        }
        $.post('<@spring.url "/factory/terminal/page" />',
                {page: page,
                    terminalNum: terminalNum,
                    orderNum: orderNum,
                    status: status
                },
                function (data) {
                    $('#page_fresh').html(data);
                });
    }

</script>
</@c.html>