<div class="attributes_box">
    <h2>银行卡类型</h2>
    <div class="dataDictionary" id="cardTypeTable">
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <colgroup>
                <col width="50%">
                <col width="50%">
            </colgroup>
            <tbody><tr>
                <td>类型</td>
                <td>操作</td>
            </tr>
            <#list cardTypes as cardType>
                <#include "cardType_info.ftl"/>
            </#list>
            </tbody></table>
        <a id="addCardType" class="pay_add_a">+</a>
    </div>
</div>
<script>
    $(function(){
        $("#addCardType").click(function(){
            $.get('<@spring.url "/system/dictionary/cardType/0/edit" />',
                    function (data) {
                        $("#cardTypeTable").find("tbody").append(data);
                    });
        });

        $(document).delegate(".saveCardType", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            var name = $tr.find("input").val();
            if(isNull(name, "类型不能为空！")){
                return false;
            }
            if(!isNotNull(value)){
                value = 0;
            }
            $.post('<@spring.url "/system/dictionary/cardType/" />'+value+'/edit',
                    {name: name},
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

        $(document).delegate(".delCardType", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/cardType/" />'+value+'/delete',
                    function (data) {
                        if(data.code==1) {
                            $tr.remove();
                        }
                    });
        });

        $(document).delegate(".editCardType", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/cardType/" />'+value+'/edit',
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

    });

</script>