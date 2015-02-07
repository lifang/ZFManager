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
                            <p><input name="" type="text"><button>确定</button></p>
                            <p><a href="#" class="ap_add_btn"><i></i>添加二级分类</a></p>
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
                        <p><a href="#" class="ap_add_btn"><i></i>添加二级分类</a></p>
                    </div>
                </dd>
            </#if>
            </#list>
            </#if>
            <div class="cp_add">
                <p><input name="" type="text"><button>确定</button></p>
                <p><a href="#" class="ap_add_btn"><i></i>添加一级分类</a></p>
            </div>
        </dl>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        bindDeleteClick();
    })

    function bindDeleteClick() {
        $(".pos_delete").click(function () {
            var id = $(this).attr("value");
            $.get('<@spring.url "" />'+'/pos/category/'+id+'/del',
                    function (data) {
                        if(data.code==1){
                            $(this).parent().remove();
                        } else if(data.code==-1) {
                            showErrorTip(data.message);
                        }
                    });

            //alert($(this).parents("ul").prev(".stair").children("span").attr("value"));
        });
    }

</script>
</@c.html>