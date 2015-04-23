<#import "../common.ftl" as c />
<@c.html_head ; d>
<#if d="head">
<link href="<@spring.url "/resources/zTree/css/zTreeStyle.css"/>" rel="stylesheet" type="text/css"/>
<script src="<@spring.url "/resources/zTree/js/jquery.ztree.core-3.5.min.js"/>"></script>
<script src="<@spring.url "/resources/zTree/js/jquery.ztree.excheck-3.5.min.js"/>"></script>
</#if>
<#if d="body">
<div class="breadcrumb">
    <ul>
        <li><a href="#">系统</a></li>
        <li><a href="#">运营账号</a></li>
        <li><a href="#">管理角色</a></li>
        <li><a href="#">创建</a></li>
    </ul>
</div>
<div class="content clear">

    <div class="myInfoNav">
        <ul>
            <li><a href="<@spring.url "/system/operate/accounts"/>">管理运营帐号</a></li>
            <li><a href="<@spring.url "/system/operate/roles"/>" class="hover">管理角色</a></li>
        </ul>
    </div>

    <div class="user_title">
        <h1>编辑角色</h1>
    </div>
    <form action="<@spring.url "/system/operate/role/${role.id}/edit"/>" method="post">
        <div class="item_list clear">
            <ul>
                <li>
                    <span class="labelSpan">角色名称：</span>
                    <div class="text"><input name="role_name" type="text" value="${role.roleName}"></div>
                </li>
                <li>
                    <span class="labelSpan">功能列表：</span>
                    <div class="text"><div id="tree" class="ztree"></div></div>
                </li>
            </ul>
        </div>
        <input type="hidden" name="roles">
        <div class="btnBottom"><button class="blueBtn" type="submit">保存</button></div>
    </form>
</div>
<script>
    $(function(){
        $.fn.zTree.init(
                $("#tree"),
                {
                    view: {selectedMulti: false, showIcon: false},
                    check: {enable: true},
                    data: {simpleData: {enable: true}}
                },
                [
                <#list menuList as menu>
                    { id:${menu.id}, pId:${menu.parentId}, name:"${menu.menuName}", open:false, checked:<#if keys?seq_contains(menu.menuKey)>true<#else>false</#if>}<#if menu_has_next>,</#if>
                </#list>
                ]);

        $("form").submit(function(){
            var nodes = $.fn.zTree.getZTreeObj("tree").getCheckedNodes();
            var roles;
            for(var i in nodes){
                if(!roles){
                    roles = nodes[i].id;
                    continue;
                }
                roles = roles+","+nodes[i].id;
            }
            $("input[name=roles]").val(roles);
            var name=$("input[name=role_name]").val();
            if(!name){
                alert("角色名称不能为空！");
                return false;
            }
            return true;
        });
    });
</script>
</#if>
</@c.html_head>