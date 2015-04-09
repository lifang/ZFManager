<#import "../../page.ftl" as pager>
<div class="uesr_table">
    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col />
            <col />
            <col width="250" />
        </colgroup>
        <thead>
        <tr>
            <th>支付通道</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <#if (channels.content)??>
            <#list channels.content as channel>
            <tr>
                <td>${channel.name}</td>
                <td><strong class="strong_status">
                    <#if channel.status=1>待审核
                    <#elseif channel.status=2>初审不通过
                    <#elseif channel.status=3>初审通过
                    <#elseif channel.status=4>审核不通过
                    <#elseif channel.status=5>正常
                    <#elseif channel.status=6>已停用
                    </#if>
                </strong></td>

                <td>
                    <#if channel.status=1>
                        <a href="<@spring.url "/factory/channel/${channel.id}/edit" />" class="a_btn">编辑</a>
                        <a href="<@spring.url "/factory/channel/${channel.id}/info" />" class="a_btn">查看详情</a>

                    <#elseif channel.status=2>
                        <a href="<@spring.url "/factory/channel/${channel.id}/edit" />" class="a_btn">编辑</a>
                        <a href="<@spring.url "/factory/channel/${channel.id}/info" />" class="a_btn">查看详情</a>
                    <#elseif channel.status=3>
                        <a href="<@spring.url "/factory/channel/${channel.id}/edit" />" class="a_btn">编辑</a>
                        <a href="<@spring.url "/factory/channel/${channel.id}/info" />" class="a_btn">查看详情</a>
                    <#elseif channel.status=4>
                        <a href="<@spring.url "/factory/channel/${channel.id}/edit" />" class="a_btn">编辑</a>
                        <a href="<@spring.url "/factory/channel/${channel.id}/info" />" class="a_btn">查看详情</a>
                    <#elseif channel.status=5>
                        <a href="<@spring.url "/factory/channel/${channel.id}/info" />" class="a_btn">查看详情</a>
                    <#elseif channel.status=6>
                        <a href="<@spring.url "/factory/channel/${channel.id}/info" />" class="a_btn">查看详情</a>
                    </#if>
                </td>
            </tr>
            </#list>
        </#if>
        </tbody>
    </table>
</div>
<@pager.p page=channels.currentPage totalPages=channels.totalPage functionName="channelPageChange"/>