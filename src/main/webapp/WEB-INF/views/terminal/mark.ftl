<div class="ur_item">
    <div class="ur_item_text" style="word-break:break-all;">${(terminalMark.content)!""}</div>
    <div class="ur_item_name">${(terminalMark.customer.username)!""} <em><#if (terminalMark.createdAt)??>${(terminalMark.createdAt)?string("yyyy/MM/dd HH:mm:ss")}</#if></em></div>
</div>