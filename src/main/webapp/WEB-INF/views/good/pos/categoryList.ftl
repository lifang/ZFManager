<#import "../../common.ftl" as c />
<@c.html_head ; d>
<#if d="head">
<link href="<@spring.url "/resources/zTree/css/zTreeStyle.css"/>" rel="stylesheet" type="text/css"/>
<style type="text/css">
    .ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}
</style>
<script src="<@spring.url "/resources/zTree/js/jquery.ztree.core-3.5.min.js"/>"></script>
<script src="<@spring.url "/resources/zTree/js/jquery.ztree.excheck-3.5.min.js"/>"></script>
<script src="<@spring.url "/resources/zTree/js/jquery.ztree.exedit-3.5.min.js"/>"></script>
</#if>
<#if d="body">
<div class="breadcrumb">
    <ul>
        <li>商品</li>
        <li><a href="<@spring.url "/good/pos/list"/>">POS机管理</a></li>
        <li><a href="<@spring.url "/good/pos/category/list"/>">管理POS机分类</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>管理POS机分类</h1>
    </div>
    <div id="tree" class="ztree"></div>
</div>
<script type="text/javascript">
    $(function () {

        $.fn.zTree.init(
                $("#tree"),
                {
                    view: {selectedMulti: false, showIcon: false,addHoverDom: addHoverDom,removeHoverDom: removeHoverDom},
                    data: {simpleData: {enable: true}},
                    edit: {enable: true,removeTitle:'删除',renameTitle:'修改',editNameSelectAll: true,showRemoveBtn: showRemoveBtn,showRenameBtn: showRenameBtn},
                    callback: {beforeRename:beforeRename,beforeRemove:beforeRemove}
                },
                [
                    { id:0, pId:-1, name:"全部POS", open:true},
                    <#list categories as category>
                    { id:${category.id}, pId:${category.parentId}, name:"${category.name}", open:false}<#if category_has_next>,</#if>
                    </#list>
                ]);

        function addHoverDom(treeId, treeNode) {
            if(treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
            if(treeNode.level==3) return;
            var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加子分类' onfocus='this.blur();'></span>";
            var sObj = $("#" + treeNode.tId + "_span");
            sObj.after(addStr);
            var btn = $("#addBtn_"+treeNode.tId);
            if (btn) btn.bind("click", function(){
                var result;
                $.ajax(treeNode.id + '/create',{async:false,
                    type:'POST',
                    data:{name:"新分类"},
                    success:function (data) {
                    if (data.code == 1) {
                        result = data.result;
                    } else if (data.code == -1) {
                        showErrorTip(data.message);
                    }
                }});
                if(!result) return false;
                var zTree = $.fn.zTree.getZTreeObj(treeId);
                var node = zTree.addNodes(treeNode, {id:result.id, pId:treeNode.id, name:"新分类"});
                zTree.editName(node[0]);
                return false;
            });
        }
        function removeHoverDom(treeId, treeNode) {
            $("#addBtn_"+treeNode.tId).unbind().remove();
        }
        function beforeRename(treeId, treeNode, newName, isCancel) {
            if(isCancel) return false;
            if(newName.length == 0){
                showErrorTip("名称不能为空！");
                return false;
            }
            var result = false;
            $.ajax(treeNode.id + '/modify',{async:false,
                type:'POST',
                data:{name:newName},
                success:function (data) {
                    if (data.code == 1) {
                        result = true;
                    } else if (data.code == -1) {
                        showErrorTip(data.message);
                    }
                }});
            return result;
        }
        function beforeRemove(treeId, treeNode) {
            if(confirm("确定要删除该分类吗？")){
                var result=false;
                $.ajax(treeNode.id+"/del",{async:false,success:function (data) {
                    if (data.code == 1) {
                        result=true;
                    } else if (data.code == -1) {
                        showErrorTip(data.message);
                    }
                }});
                return result;
            }
            return false;
        }
        function showRemoveBtn(treeId, treeNode) {
            return treeNode.level>0;
        }
        function showRenameBtn(treeId, treeNode) {
            return treeNode.level>0;
        }
    });
</script>
</#if>
</@c.html_head>