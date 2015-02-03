<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">用户</a></li>
    </ul>
</div>
<div class="content clear">
    <div class="user_title"><h1>用户列表</h1>
        <div class="userTopBtnBox">
            <a href="#" class="ghostBtn">创建用户</a>
        </div>
    </div>
    <div class="seenBox clear">
        <ul>
            <li><div class="user_search"><input name="" type="text" /><button></button></div></li>
        </ul>
    </div>

    <div class="uesr_table">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
            <colgroup>
                <col width="150" />
                <col width="150" />
                <col width="150" />
                <col />
                <col />
                <col width="80" />
                <col width="150" />
            </colgroup>
            <thead>
            <tr>
                <th>姓名</th>
                <th>电话</th>
                <th>邮件</th>
                <th>拥有终端数</th>
                <th>上次登录时间</th>
                <th>状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>王小小</td>
                <td>0512-56984666</td>
                <td>123@163.com</td>
                <td>2</td>
                <td>2014-12-12  20:30:30</td>
                <td><strong class="strong_status">正常</strong></td>
                <td><a href="#" class="a_btn">重置密码</a><a href="#" class="a_btn">编辑</a>
                    <a href="#" class="a_btn">停用</a><a href="#" class="a_btn">查看详情</a></td>
            </tr>
            <tr>
                <td>王小小</td>
                <td>0512-56984666</td>
                <td>123@163.com</td>
                <td>2</td>
                <td>2014-12-12  20:30:30</td>
                <td><strong class="strong_status">正常</strong></td>
                <td><a href="#" class="a_btn">重置密码</a><a href="#" class="a_btn">编辑</a>
                    <a href="#" class="a_btn">停用</a><a href="#" class="a_btn">查看详情</a></td>
            </tr>
            <tr>
                <td>王小小</td>
                <td>0512-56984666</td>
                <td>123@163.com</td>
                <td>2</td>
                <td>2014-12-12  20:30:30</td>
                <td><strong class="strong_status">停用</strong></td>
                <td><a href="#" class="a_btn">重置密码</a><a href="#" class="a_btn">编辑</a>
                    <a href="#" class="a_btn">启用</a><a href="#" class="a_btn">查看详情</a></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="pageTurn">
        <div class="p_num">
            <a href="#" class="disabled">上一页</a>
            <a href="#" class="current">1</a>
            <a href="#?page=2">2</a>
            <a href="#?page=3">3</a>
            <a href="#?page=4">4</a>
            <a href="#?page=5">5</a>
            ...
            <a href="#?page=199">199</a>
            <a href="#?page=200">200</a>
            <a href="#?page=2">下一页</a>
        </div>
        <div class="p_skip">
            <span>共24页</span>
            <span>到第&nbsp;&nbsp;<input name="" type="text" />&nbsp;&nbsp;页</span>
            <button>确定</button>
        </div>
    </div>
</div>
</@c.html>