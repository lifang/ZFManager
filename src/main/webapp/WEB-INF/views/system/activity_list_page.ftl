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
            <th>活动标题</th>
            <th>创建日期</th>
            <th>URL</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#list page.content as activity>
        <tr>
            <td>${(activity.title)!""}</td>
            <td>${activity.createdAt?string("yyyy/MM/dd HH:mm:ss")}</td>
            <td>${activity.url?html}</td>
            <td>
                <a href="<@spring.url "/system/content/activity/${activity.id}/edit" />" class="a_btn">编辑</a>
                <a onclick="deleteActivity(${activity.id})" class="a_btn">删除</a>
                <a href="<@spring.url "${activity.url}" />" target="_Blank" class="a_btn">预览</a>
            </td>
        </tr>
        </#list>
        </tbody>
    </table>
</div>
<@pager.p page=page.currentPage totalPages=page.totalPage functionName="pageChange"/>
