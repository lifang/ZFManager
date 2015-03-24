<#import "../common.ftl" as c />
<@c.html>
<div class="right">
    <div class="breadcrumb">
        <ul>
            <li><a href="#">系统</a></li>
            <li><a href="#">第三方机构</a></li>
            <li><a href="#">详情</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title"><h1>第三方机构详情</h1>
        </div>

        <div class="item_list clear">
            <ul>
                <li><span class="labelSpan">机构类型：</span>
                    <div class="text">
                        <#if factory.types == Factory.TYPE_PAYMENT>收单机构
                        <#elseif factory.types == Factory.TYPE_MANUFACTURER>生产厂商
                        </#if></div>
                </li>
                <li><span class="labelSpan">机构名称：</span>
                    <div class="text">${factory.name}</div>
                </li>
                <li><span class="labelSpan">登录ID：</span>
                    <div class="text">${(factory.customer.username)!""}</div>
                </li>
                <li><span class="labelSpan">机构LOGO：</span>
                    <div class="text">
                        <img src="<@spring.url "/resources/images/zp.jpg"/>" class="cover" value="${factory.logoFilePath!""}">
                    </div>
                </li>
                <li><span class="labelSpan">机构URL：</span>
                    <div class="text">${factory.websiteUrl!""}</div>
                </li>
                <li><span class="labelSpan">简介：</span>
                    <div class="text">${factory.description!""}</div>
                </li>
                <li><span class="labelSpan">售后收货地址：</span>
                    <div class="text">
                        <#if addresses?? && addresses?size gt 0>
                            <#assign address = addresses[0]/>
                            ${(address.parentCity.name)!""}${(address.city.name)!""} ${(address.address)!""} ${(address.receiver)!""} ${(address.moblephone)!""}
                        </#if>
                    </div>
                </li>
            </ul>
            <div class="img_info"><img src=""></div>
        </div>
    </div>
</div>
</@c.html>