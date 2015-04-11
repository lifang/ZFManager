<div class="attributes_box" id="encryptCardWayTable">
    <h2>加密卡方式</h2>
    <div class="dataDictionary">
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <colgroup>
                <col width="50%">
                <col width="50%">
            </colgroup>
            <tbody><tr>
                <td>方式</td>
                <td>操作</td>
            </tr>
            <#list encryptCardWays as encryptCardWay>
                <#include "encryptCardWay_info.ftl"/>
            </#list>
            </tbody></table>
        <a id="addEncryptCardWay" class="pay_add_a">+</a>
    </div>
</div>
<script>
    $(function(){
        $("#addEncryptCardWay").click(function(){
            $.get('<@spring.url "/system/dictionary/encryptCardWay/0/edit" />',
                    function (data) {
                        $("#encryptCardWayTable").find("tbody").append(data);
                    });
        });

        $(document).delegate(".saveEncryptCardWay", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            var name = $tr.find("input").val();
            if(isNull(name, "方式不能为空！")){
                return false;
            }
            if(!isNotNull(value)){
                value = 0;
            }
            $.post('<@spring.url "/system/dictionary/encryptCardWay/" />'+value+'/edit',
                    {name: name},
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

        $(document).delegate(".delEncryptCardWay", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/encryptCardWay/" />'+value+'/delete',
                    function (data) {
                        if(data.code==1) {
                            $tr.remove();
                        }
                    });
        });

        $(document).delegate(".editEncryptCardWay", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/encryptCardWay/" />'+value+'/edit',
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

    });

</script>