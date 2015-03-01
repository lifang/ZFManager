<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">系统</a></li>
        <li><a href="#">消息</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>消息列表</h1>
        <div class="userTopBtnBox">
            <a href="<@spring.url "/system/message/create"/>" class="ghostBtn">发布消息</a>
        </div>
    </div>
    <div class="message_con">
        <div class="mail_title border_b">
            <span class="mail_span"><input name="" type="checkbox" value=""> 全选</span>
            <a href="#">删除所选</a>
            <a href="#">清空所有</a>
            <a href="#">只看未读</a>
        </div>
        <div class="mail_list">
            <span><input name="" type="checkbox" value=""></span>
            <div class="mail_text"><a href="<@spring.url "/system/message/2/view"/>" class="unread">这是一条最新消息，请查看！ </a></div>
            <em>2014/12/12 20:30:21</em>
        </div>
        <div class="mail_list">
            <span><input name="" type="checkbox" value=""></span>
            <div class="mail_text"><a href="<@spring.url "/system/message/2/view"/>" class="unread">这是一条最新消息，请查看！ </a></div>
            <em>2014/12/12 20:30:21</em>
        </div>
        <div class="mail_list">
            <span><input name="" type="checkbox" value=""></span>
            <div class="mail_text"><a href="<@spring.url "/system/message/2/view"/>" class="unread">这是一条最新消息，请查看！ </a></div>
            <em>2014/12/12 20:30:21</em>
        </div>
        <div class="mail_list">
            <span><input name="" type="checkbox" value=""></span>
            <div class="mail_text"><a href="<@spring.url "/system/message/2/view"/>" class="unread">这是一条最新消息，请查看！ </a></div>
            <em>2014/12/12 20:30:21</em>
        </div>
        <div class="mail_list">
            <span><input name="" type="checkbox" value=""></span>
            <div class="mail_text"><a href="<@spring.url "/system/message/2/view"/>" class="unread">这是一条最新消息，请查看！ </a></div>
            <em>2014/12/12 20:30:21</em>
        </div>
        <div class="mail_list">
            <span><input name="" type="checkbox" value=""></span>
            <div class="mail_text"><a href="<@spring.url "/system/message/2/view"/>" class="unread">这是一条最新消息，请查看！ </a></div>
            <em>2014/12/12 20:30:21</em>
        </div>
        <div class="mail_list">
            <span><input name="" type="checkbox" value=""></span>
            <div class="mail_text"><a href="<@spring.url "/system/message/2/view"/>" class="unread">这是一条最新消息，请查看！ </a></div>
            <em>2014/12/12 20:30:21</em>
        </div>
        <div class="mail_list">
            <span><input name="" type="checkbox" value=""></span>
            <div class="mail_text"><a href="<@spring.url "/system/message/2/view"/>" class="unread">这是一条最新消息，请查看！ </a></div>
            <em>2014/12/12 20:30:21</em>
        </div>
        <div class="mail_list">
            <span><input name="" type="checkbox" value=""></span>
            <div class="mail_text"><a href="<@spring.url "/system/message/2/view"/>">这是一条最新消息，请查看！ </a></div>
            <em>2014/12/12 20:30:21</em>
        </div>
        <div class="mail_list">
            <span><input name="" type="checkbox" value=""></span>
            <div class="mail_text"><a href="<@spring.url "/system/message/2/view"/>">这是一条最新消息，请查看！ </a></div>
            <em>2014/12/12 20:30:21</em>
        </div>
        <div class="mail_title border_t">
            <span class="mail_span"><input name="" type="checkbox" value=""> 全选</span>
            <a href="#">删除所选</a>
            <a href="#">清空所有</a>
            <a href="#">只看未读</a>
            <div class="pageMail">
                <div class="p_num">
                    <a href="#">上一页</a>
                    <span>1/9</span>
                    <a href="#">下一页</a>
                </div>
                <div class="p_skip">
                    <span>共24页</span>
                    <span>到第&nbsp;&nbsp;<input name="" type="text">&nbsp;&nbsp;页</span>
                    <button>确定</button>
                </div>
            </div>
        </div>

    </div>
</div>
</@c.html>