<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">系统</a></li>
        <li><a href="#">运营账号</a></li>
        <li><a href="#">管理角色</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="myInfoNav">
        <ul>
            <li><a href="<@spring.url "/system/operate/accounts"/>">管理运营帐号</a></li>
            <li><a href="<@spring.url "/system/operate/roles"/>" class="hover">管理角色</a></li>
        </ul>
    </div>

    <div class="user_title"><h1>运营角色列表</h1>
        <div class="userTopBtnBox">
            <a href="<@spring.url "/system/operate/role/create"/>" class="ghostBtn">创建角色</a>
        </div>
    </div>

    <div class="seenBox clear">
        <ul>
            <li><div class="user_search"><input name="query" type="text"><button id="query"></button></div></li>
        </ul>
    </div>
    <div id="page"></div>
</div>
<script>
    function pageChange(page) {
        $.post('<@spring.url "/system/operate/roles/page" />',
                {"page": page,"query":$("input[name=query]").val()},
                function (data) {
                    $('#page').html(data);
                });
    }

    $(function(){
        $("#query").click(function(){
            pageChange(1);
        });
        pageChange(1);
    });
</script>
</@c.html>