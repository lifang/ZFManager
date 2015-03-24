<div class="attributes_box">
    <h2>商户风险标签</h2>
    <div class="dataDictionary" id="creditTypeTable">
        <table width="100%" border="0" cellspacing="1" cellpadding="0">
            <colgroup>
                <col width="50%">
                <col width="50%">
            </colgroup>
            <tbody><tr>
                <td>商户风险标签</td>
                <td>操作</td>
            </tr>
            <#list creditTypes as creditType>
                <#include "creditType_info.ftl"/>
            </#list>
            </tbody></table>
        <a id="addCreditType" class="pay_add_a">+</a>
    </div>
</div>
<script>
    $(function(){
        $("#addCreditType").click(function(){
            $.get('<@spring.url "/system/dictionary/creditType/0/edit" />',
                    function (data) {
                        $("#creditTypeTable").find("tbody").append(data);
                    });
        });

        $(document).delegate(".saveCreditType", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            var name = $tr.find("input").val();
            if(checkNull(name, "名称不能为空！")){
                return false;
            }
            if(!isNotNull(value)){
                value = 0;
            }
            $.post('<@spring.url "/system/dictionary/creditType/" />'+value+'/edit',
                    {name: name},
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

        $(document).delegate(".delCreditType", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/creditType/" />'+value+'/delete',
                    function (data) {
                        if(data.code==1) {
                            $tr.remove();
                        }
                    });
        });

        $(document).delegate(".editCreditType", "click", function () {
            var $tr = $(this).parents("tr");
            var value = $(this).attr("value");
            $.get('<@spring.url "/system/dictionary/creditType/" />'+value+'/edit',
                    function (data) {
                        $tr.replaceWith(data);
                    });
        });

    });

</script>