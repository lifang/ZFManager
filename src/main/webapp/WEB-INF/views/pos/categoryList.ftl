<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">商品</a></li>
        <li><a href="#">POS机管理</a></li>
        <li><a href="#">管理POS机分类</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>管理POS机分类</h1>
    </div>
    <div class="classify_pos">
        <dl>
            <dt><div class="stair"><i></i><span>全部POS</span></div></dt>
            <#if categories?? && (categories?size > 0)>
            <#list categories as category>
                <#if category.parentId??>
                    <#assign isFirst = false>
                <#else>
                    <#assign isFirst = true>
                </#if>
                <#if isFirst>
                    <#if category_index = 0>
                        <dd><div class="stair"><i></i><span value="${category.id}">${category.name}</span></div>
                        <ul>
                    <#else>
                    </ul>
                        <div class="cp_add">
                            <p><input name="" type="text"><button class="add_second_btn">确定</button></p>
                            <p><a class="ap_add_btn add_second_a"><i></i>添加二级分类</a></p>
                        </div>
                    </dd>
                    <dd><div class="stair"><i></i><span value="${category.id}">${category.name}</span></div>
                    <ul>
                    </#if>
                <#else>
                    <li>${category.name}<i class="pos_delete" value="${category.id}"></i></li>
                </#if>
            <#if !(category_has_next)>
                </ul>
                    <div class="cp_add">
                        <p><input name="" type="text"><button>确定</button></p>
                        <p><a class="ap_add_btn add_second_a"><i></i>添加二级分类</a></p>
                    </div>
                </dd>
            </#if>
            </#list>
            </#if>
            <div class="cp_add">
                <p><input name="" type="text"><button>确定</button></p>
                <p><a class="ap_add_btn add_first_a"><i></i>添加一级分类</a></p>
            </div>
        </dl>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        bindDeleteClick();
        bindAddSecondClick();
    })
    <!--绑定删除分类按钮-->
    function bindDeleteClick() {
        $(".pos_delete").click(function () {
            var id = $(this).attr("value");
            var p =  $(this).parent();
            $.get('<@spring.url "" />' + '/pos/category/' + id + '/del',
                    function (data) {
                        if (data.code == 1) {
                            p.remove();
                        } else if (data.code == -1) {
                            showErrorTip(data.message);
                        }
                    });

        });
    }
    <!--绑定添加分类按钮-->
    function bindAddSecondClick() {
        $(".add_second_a").click(function () {
            var addP =  $(this).parent().prev();
//          alert($(this).parents("ul").prev(".stair").children("span").attr("value"));
            addP.toggle();
        });

        $(".add_second_btn").click(function(){
            var parentId = $(this).parents(".cp_add").prev().prev().children("span").attr("value");
            var textInput =  $(this).prev();
            var name = textInput.val();
            if(name.length == 0){
                showErrorTip("名称不能为空！");
                return false;
            }
            var ul = $(this).parents(".cp_add").prev();
            alert(ul.html());
            $.post('<@spring.url "" />' + '/pos/category/' + id + '/create',
                    function (data) {
                        if (data.code == 1) {
                            var id = data.result;
                            textInput.val("");
                            ul.append("<p></p>");
                        } else if (data.code == -1) {
                            showErrorTip(data.message);
                        }
                    });
        });

        $(".add_first_a").click(function(){
            var parentId = $(this).parents(".cp_add").prev().prev().children("span").attr("value");
            var textInput =  $(this).prev();
            var name = textInput.val();
            if(name.length == 0){
                showErrorTip("名称不能为空！");
                return false;
            }
            var ul = $(this).parents(".cp_add").prev();
            alert(ul.html());
            $.post('<@spring.url "" />' + '/pos/category/' + id + '/create',
                    function (data) {
                        if (data.code == 1) {
                            var id = data.result;
                            textInput.val("");
                            ul.append("<p></p>");
                        } else if (data.code == -1) {
                            showErrorTip(data.message);
                        }
                    });
        });
    }

</script>
</@c.html>