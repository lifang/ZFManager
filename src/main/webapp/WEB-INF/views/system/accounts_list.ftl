<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">系统</a></li>
        <li><a href="#">运营账号</a></li>
        <li><a href="#">管理运营账号</a></li>
    </ul>
</div>
<div class="content clear">

    <div class="myInfoNav">
        <ul>
            <li><a href="<@spring.url "/system/operate/accounts"/>" class="hover">管理运营账号</a></li>
            <li><a href="<@spring.url "/system/operate/roles"/>">管理角色</a></li>
        </ul>
    </div>

    <div class="user_title"><h1>运营账号列表</h1>
        <div class="userTopBtnBox">
            <a href="<@spring.url "/system/operate/account/create"/>" class="ghostBtn">创建帐号</a>
        </div>
    </div>

    <div class="seenBox clear">
        <ul>
            <li><div class="user_search"><input name="query" type="text"><button id="query"></button></div></li>
            <li><div class="user_select">
                <label>状态筛选</label>
                <select name="status">
                    <option value="">全部</option>
                    <option value="2">已启用</option>
                    <option value="3">已停用</option>
                </select>
            </div></li>
        </ul>
    </div>
    <div id="page"></div>
</div>
<script>
    function userStatus(id){
        $.post("<@spring.url "/system/operate/account/"/>"+id+"/status",function(data){
            $("tr[data-id="+id+"]").replaceWith(data);
        });
    }
    function pageChange(page) {
        $.post('<@spring.url "/system/operate/accounts/page" />',
                {"page": page,"query":$("input[name=query]").val(),"status":$("select[name=status]").val()},
                function (data) {
                    $('#page').html(data);
                });
    }

    $(function(){
        $("#query").click(function(){
            pageChange(1);
        });
        $("select[name=status]").change(function(){
            pageChange(1);
        });
        pageChange(1);
    });
</script>
</@c.html>
