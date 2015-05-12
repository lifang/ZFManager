<div class="attributes_box">
    <h2>对公开通资料</h2>
    <div class="dataDictionary" id="openPrivateTable">
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <colgroup>
                <col width="20%">
                <col width="20%">
                <col width="20%">
                <col width="20%">
                <col>
            </colgroup>
            <tbody><tr>
                <td>类型</td>
                <td>名称</td>
                <td>说明</td>
                <td>查询标志</td>
                <td>操作</td>
            </tr>
            <#list openPrivates as openPrivate>
            <#include "openPrivate_info.ftl"/>
            </#list>
            </tbody></table>
        <a id="addOpenPrivate" class="pay_add_a">+</a>
    </div>
</div>
<script>
    $(function(){
        $("#addOpenPrivate").click(function(){
            $.get('<@spring.url "/system/dictionary/openPrivate/0/edit" />',
                    function (data) {
                        $("#openPrivateTable").find("tbody").append(data);
                    });
        });

        $(document).delegate(".saveOpenPrivate", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            var infoType = $tr.find("select").find("option:selected").val();
            var name = $tr.find("input").eq(0).val();
            var introduction = $tr.find("input").eq(1).val();
            var queryMark = $tr.find("input").eq(2).val();
            if(isNull(name, "名称不能为空！")){
                return false;
            }
            if(name.length > 9){
                showErrorTip("名称最多9个字！");
                return false;
            }
            if(!isNotNull(value)){
                value = 0;
            }
            $.post('<@spring.url "/system/dictionary/openPrivate/" />'+value+'/edit',
                    {infoType: infoType,
                        name: name,
                        introduction: introduction,
                        queryMark: queryMark},
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

        $(document).delegate(".delOpenPrivate", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/openPrivate/" />'+value+'/delete',
                    function (data) {
                        if(data.code==1) {
                            $tr.remove();
                        }
                    });
        });

        $(document).delegate(".editOpenPrivate", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/openPrivate/" />'+value+'/edit',
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

    });

</script>
