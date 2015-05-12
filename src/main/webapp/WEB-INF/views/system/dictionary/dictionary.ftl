<#import "../../common.ftl" as c />
<@c.html>
    <div class="breadcrumb">
        <ul>
            <li>系统</li>
            <li><a href="<@spring.url "/system/dictionary"/>">数据字典</a></li>
        </ul>
    </div>
    <div class="content clear">
        <div class="user_title"><h1>数据字典管理</h1>
        </div>
        <#include "creditType_list.ftl"/>
        <#include "companyAddress_list.ftl"/>
        <#include "openPrivate_list.ftl"/>
        <#include "orderType_list.ftl"/>
        <#include "encryptCardWay_list.ftl"/>
        <#include "orderWay_list.ftl"/>
        <#include "cardType_list.ftl"/>
        <#include "tradeType_list.ftl"/>
        <#include "standardRate_list.ftl"/>
        <#include "billingCycle_list.ftl"/>
    </div>
</@c.html>