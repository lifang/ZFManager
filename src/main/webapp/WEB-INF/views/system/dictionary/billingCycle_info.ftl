<tr>
    <td>${(billingCycle.name)!""}</td>
    <td>${(((billingCycle.serviceRate)!0)/10)?string("0.0")}</td>
    <td>${(billingCycle.description)!""}</td>
    <td><a value="${billingCycle.id}" class="a_btn editBillingCycle">编辑</a>
        <a value="${billingCycle.id}" class="a_btn delBillingCycle">删除</a></td>
</tr>