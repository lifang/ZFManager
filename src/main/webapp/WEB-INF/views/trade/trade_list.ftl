<div class="user_title"><h1>${type.tradeValue}交易流水</h1>
    <div class="userTopBtnBox">
        <a href="<@spring.url "/trade/${type.id}/statistics"/>" class="ghostBtn">统计</a>
        <a href="javascript:void(0);" class="ghostBtn file_a">上传交易</a>
    </div>
</div>

<div class="seenBox clear">
    <ul>
        <li><div class="user_select">
            <label>状态筛选</label>
            <select name="trade_type">
                <option value="">全部</option>
                <option value="1">交易成功</option>
                <option value="2">交易失败</option>
                <option value="3">交易结果待确认</option>
            </select>
        </div></li>
        <li><div class="user_date">
            <label>选择日期</label>
            <input name="date_start" type="text" /> - <input name="date_end" type="text" />
        </div>
        </li>
        <li><button id="search" class="ghostBtn">搜索</button></li>
    </ul>
</div>
<div id="trade_page"></div>
<script>
    function pageChange(page) {
        $.post("<@spring.url "/trade/type/${type.id}/page/"/>"+page,
                {
                    status:$("select[name=trade_type]").val(),
                    start:$("input[name=date_start]").val(),
                    end:$("input[name=end_start]").val()
                },
                function(data){
                    $("#trade_page").html(data);
                });
    }
    $(function() {
        $.datepicker.regional['zh-CN'] = {
            closeText: '关闭',
            prevText: '<上月',
            nextText: '下月>',
            currentText: '今天',
            monthNames: ['一月','二月','三月','四月','五月','六月',
                '七月','八月','九月','十月','十一月','十二月'],
            monthNamesShort: ['一','二','三','四','五','六',
                '七','八','九','十','十一','十二'],
            dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],
            dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],
            dayNamesMin: ['日','一','二','三','四','五','六'],
            weekHeader: '周',
            dateFormat: 'yy-mm-dd',
            firstDay: 1,
            isRTL: false,
            showMonthAfterYear: true,
            yearSuffix: '年'};
        $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
        $(".user_date input").datepicker();
        $("#search").click(function(){
            pageChange(1);
        });
        pageChange(1);

        popup(".file_tab",".file_a");//交易转账上传
    });
</script>