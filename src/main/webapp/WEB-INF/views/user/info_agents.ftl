<#import "../page.ftl" as pager>
<div class="uesr_table">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col width="50%">
            <col width="50%">
        </colgroup>
        <thead>
        <tr>
            <th>关联代理商</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#if (agents.content)??>
            <#list agents.content as agent>
            <tr>
                <td>${(agent.name)!"- -"}</td>
                <td><a href="#" class="a_btn">取消关联</a></td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>
<@pager.p page=agents.currentPage totalPages=agents.totalPage functionName="agentPageChange"/>