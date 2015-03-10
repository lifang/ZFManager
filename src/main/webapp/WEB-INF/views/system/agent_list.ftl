<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li><a href="#">系统</a></li>
            <li><a href="#">代理商</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title"><h1>代理商列表</h1>
            <div class="userTopBtnBox">
                <a href="#" class="ghostBtn">创建代理商</a>
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
        <#include "agent_list_page.ftl"/>
    </div>
<script>
</script>
</@c.html>
