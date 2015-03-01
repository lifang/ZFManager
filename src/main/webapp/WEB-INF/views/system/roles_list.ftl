<#import "../common.ftl" as c />
<@c.html>
<div class="breadcrumb">
    <ul>
        <li><a href="#">系统</a></li>
        <li><a href="#">运营账号</a></li>
        <li><a href="#">管理角色</a></li>
    </ul>
</div>
<div class="content clear">

    <div class="myInfoNav">
        <ul>
            <li><a href="<@spring.url "/system/operate/accounts"/>">管理运营帐号</a></li>
            <li><a class="hover">管理角色</a></li>
        </ul>
    </div>

    <div class="user_title"><h1>运营角色列表</h1>
        <div class="userTopBtnBox">
            <a href="<@spring.url "/system/operate/role/create"/>" class="ghostBtn">创建角色</a>
        </div>
    </div>

    <div class="seenBox clear">
        <ul>
            <li><div class="user_search"><input name="" type="text"><button></button></div></li>
            <li><div class="user_select">
                <label>状态筛选</label>
                <select name="">
                    <option>111</option>
                    <option>222</option>
                    <option>333</option>
                </select>
            </div></li>
        </ul>
    </div>

    <div class="user_table">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
            <colgroup>
                <col width="200">
                <col>
                <col>
            </colgroup>
            <thead>
            <tr>
                <th>角色名称</th>
                <th>创建日期</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>运营</td>
                <td>2014-12-12 20:30:30</td>
                <td><a href="#" class="a_btn">编辑</a></td>
            </tr>
            <tr>
                <td>运营</td>
                <td>2014-12-12 20:30:30</td>
                <td><a href="#" class="a_btn">编辑</a></td>
            </tr>
            <tr>
                <td>运营</td>
                <td>2014-12-12 20:30:30</td>
                <td><a href="#" class="a_btn">编辑</a></td>
            </tr>
            <tr>
                <td>运营</td>
                <td>2014-12-12 20:30:30</td>
                <td><a href="#" class="a_btn">编辑</a></td>
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
            <span>到第&nbsp;&nbsp;<input name="" type="text">&nbsp;&nbsp;页</span>
            <button>确定</button>
        </div>
    </div>
</div>
</@c.html>