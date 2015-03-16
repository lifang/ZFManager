<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">系统</a></li>
        <li><a href="#">消息</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>消息列表</h1>
        <div class="userTopBtnBox">
            <a href="<@spring.url "/system/message/create"/>" class="ghostBtn">发布消息</a>
        </div>
    </div>
    <div id="page_fresh">
    <#include "message_list_page.ftl"/>
    </div>
</div>
<script>
    $(function () {
        $(document).delegate("input[name='ckAll']", "click", function () {
            $("input[name='chk_list']").prop("checked", this.checked);
        });

        $(document).delegate("#deleteChoose", "click", function () {
            var $chkList = $("[name = 'chk_list']:checked");
            if($chkList.length != 0){
                var ids = new Array();
                $chkList.each(function(){
                    ids.push($(this).prop("value"));
                });
                $.get('<@spring.url "/system/message/delete" />',
                        {"ids": ids},
                        function (data) {
                            if(data.code == 1){
                                pageChange($("#pageNum").val());
                            }
                        });
            }
        });

        $(document).delegate("input[name='chk_list']", "click", function () {
            var $chk = $("[name = 'chk_list']");
            $("input[name='ckAll']").prop("checked", $chk.length == $chk.filter(":checked").length);
        });
    })

    function pageChange(page) {
        $.get('<@spring.url "/system/message/page" />',
                {"page": page},
                function (data) {
                    $('#page_fresh').html(data);
                });
    }
</script>
</@c.html>