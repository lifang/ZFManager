<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="<@spring.url "/user/list"/>">用户</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>用户列表</h1>
        <div class="userTopBtnBox">
    <#if Roles.hasRole("USER_CREATE")>
            <a href="<@spring.url "/user/create"/>" class="ghostBtn">创建用户</a>
    </#if>
        </div>
    </div>
    <div class="seenBox clear">
        <ul>
            <li>
                <div class="user_search"><input name="query" type="text" placeholder="邮箱、手机号"/>
                    <button id="query"></button>
                </div>
            </li>
        </ul>
    </div>

    <div id="page_fresh">
    </div>

</div>
<script>
    function userStatus(id){
        $.post("<@spring.url "/user/"/>"+id+"/status",function(data){
            $("tr[data-id="+id+"]").replaceWith(data);
        });
    }
    function pageChange(page) {
        $.post('<@spring.url "/user/page" />',
                {"page": page,"query": $.trim($("input[name=query]").val())},
                function (data) {
                    $('#page_fresh').html(data);
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