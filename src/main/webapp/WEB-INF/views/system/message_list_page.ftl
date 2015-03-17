<#import "../page.ftl" as pager>
<input  id="pageNum" type="hidden" value="${pageNum!1}"/>
<div class="message_con">
    <div class="mail_title border_b">
        <span class="mail_span"><input name="ckAll" type="checkbox" value=""> 全选</span>
        <a id="deleteChoose">删除所选</a>
    </div>
    <#list page.content as message>
    <div class="mail_list">
        <span><input name="chk_list" type="checkbox" value="${(message.id)!""}"></span>
        <div class="mail_text"><a href="<@spring.url "/system/message/${message.id}/view"/>" class="unread">${(message.title)!""}</a></div>
        <em>${message.createdAt?string("yyyy/MM/dd HH:mm:ss")}</em>
    </div>
    </#list>
    <@pager.p page=page.currentPage totalPages=page.totalPage functionName="pageChange"/>

</div>