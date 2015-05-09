<#import "../page.ftl" as pager>
<input  id="pageNum" type="hidden" value="${pageNum!1}"/>
<div class="user_table">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col width="300">
            <col>
            <col>
            <col width="200">
        </colgroup>
        <thead>
        <tr>
            <th>公告标题</th>
            <th>创建日期</th>
            <th>是否置顶</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#list page.content as message>
        <tr>
            <td>${message.title}</td>
            <td>${message.createAt?string("yyyy/MM/dd HH:mm:ss")}</td>
            <td>${message.isPlacedTop?string("是", "否")}</td>
            <td>
                <#if message.isPlacedTop>
                    <a onclick="cancelTopMessage(${message.id})" class="a_btn">取消置顶</a>
                <#else>
                    <a onclick="topMessage(${message.id})" class="a_btn">置顶</a>
                </#if>
                <a href="<@spring.url "/system/content/webmessage/${message.id}/edit" />" class="a_btn" target="_blank">编辑</a>
                <a onclick="deleteMessage(${message.id})" class="a_btn">删除</a>
                <a href="<@spring.url "/system/content/webmessage/${message.id}/info" />" class="a_btn" target="_blank">查看详情</a></td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
<@pager.p page=page.currentPage totalPages=page.totalPage functionName="pageChange"/>

</div>