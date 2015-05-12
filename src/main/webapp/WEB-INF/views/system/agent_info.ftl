<#import "../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li>系统</li>
            <li><a href="<@spring.url "/system/agent/list" />">代理商</a></li>
            <li><a href="<@spring.url "/system/agent/${agent.id}/info" />">详情</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title"><h1>代理商详情</h1>
        </div>
        <div class="attributes_box">
            <div class="item_list clear">
                <ul>
                    <li class="block"><span class="labelSpan">类型：</span>
                        <div class="text"><#if agent.types == 1>公司<#else>个人</#if></div>
                    </li>
                    <li class="block"><span class="labelSpan">负责人姓名：</span>
                        <div class="text">${(agent.name)!""}</div>
                    </li>
                    <li class="block"><span class="labelSpan">负责人身份证号：</span>
                        <div class="text">${(agent.cardId)!""}</div>
                    </li>
                    <li class="block"><span class="labelSpan">公司全称：</span>
                        <div class="text">${(agent.companyName)!""}</div>
                    </li>
                    <li class="block"><span class="labelSpan">公司营业执照登记号：</span>
                        <div class="text">${(agent.businessLicense)!""}</div>
                    </li>
                    <li class="block"><span class="labelSpan">手机：</span>
                        <div class="text">${(agent.phone)!""}</div>
                    </li>
                    <li class="block"><span class="labelSpan">邮箱：</span>
                        <div class="text">${(agent.email)!""}</div>
                    </li>
                    <li class="block select2"><span class="labelSpan">所在地：</span>
                        <div class="text">${(city.parentCity.name)!""}${(city.name)!""}${(agent.address)!""}</div>
                    </li>
                    <li class="block"><span class="labelSpan">登录ID：</span>
                        <div class="text">${(agent.customer.username)!""}</div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</@c.html>
