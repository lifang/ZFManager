<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit">
    <title>运营中心全国交易实时统计</title>
    <link href="<@spring.url "/resources/style/style.css"/>" rel="stylesheet" type="text/css" />
    <script src="<@spring.url "/resources/js/jquery-1.11.2.min.js"/>"></script>
    <!--[if lte IE 8]>
    <script type="text/javascript" src="js/excanvas.js"></script>
    <![endif]-->
    <script src="<@spring.url "/resources/js/echarts/echarts.js"/>"></script><!--柱形图表-->
    <script src="<@spring.url "/resources/js/Chart.min.js"/>"></script>
    <script src="<@spring.url "/resources/js/main.js"/>"></script>
</head>
<body>
<div class="box">
    <div class="logo">华尔街金融</div>
    <div class="systemName">运营中心</div>
</div>
</div>
<div class="main">
    <div class="box">
        <div class="realTimeStatistics">
            <div class="rts_box">
                <div id="map" class="map">
                </div>
                <div class="dataStatistics">
                    <dl>
                        <dt>商城开业至今：</dt>
                        <dd>总交易金额 <strong>${tradeTotalDayReport.tradeSum}元</strong></dd>
                        <dd>总交易笔数 <strong>${tradeTotalDayReport.tradeNum}笔</strong></dd>
                        <dd>总成交订单 <strong>${tradeTotalDayReport.orderNum}个</strong></dd>
                        <dd>总注册会员 <strong>${tradeTotalDayReport.newUserNum}人</strong></dd>
                    </dl>
                </div>
                <div class="hour24">
                    <div class="h_title">24小时交易动态</div>
                    <ul>
                        <li><span>交易笔数：</span><em>${tradeDayReport.tradeNum}笔</em></li>
                        <li><span>交易金额：</span><em>${tradeDayReport.tradeSum}元</em></li>
                        <li><span>POS申请开通数：</span><em>${tradeDayReport.terminalApplyNum}台</em></li>
                        <li><span>商城订单数：</span><em>${tradeDayReport.orderNum}单</em></li>
                        <li><span>新增用户数：</span><em>${tradeDayReport.newUserNum}人</em></li>
                    </ul>
                </div>
            </div>
            <div class="rts_barChart clear">
                <div class="barChartBox">
                    <div class="bcb_title">7日交易笔数动态 <em>单位：笔</em></div>
                    <div class="barChart">
                        <canvas id="myChart01" height="200" width="230"></canvas>
                    </div>
                </div>
                <div class="barChartBox">
                    <div class="bcb_title">7日交易金额动态 <em>单位：元</em></div>
                    <div class="barChart">
                        <canvas id="myChart02" height="200" width="230"></canvas>
                    </div>
                </div>
                <div class="barChartBox">
                    <div class="bcb_title">7日POS申请开通数动态 <em>单位：个</em></div>
                    <div class="barChart">
                        <canvas id="myChart03" height="200" width="230"></canvas>
                    </div>
                </div>
                <div class="barChartBox">
                    <div class="bcb_title">7日商城订单数动态 <em>单位：个</em></div>
                    <div class="barChart">
                        <canvas id="myChart04" height="200" width="230"></canvas>
                    </div>
                </div>
                <div class="barChartBox">
                    <div class="bcb_title">7日注册用户数动态 <em>单位：人</em></div>
                    <div class="barChart">
                        <canvas id="myChart05" height="200" width="230"></canvas>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function(){
        var map;
        require.config({
            paths: {
                echarts: '<@spring.url "/resources/js/echarts"/>'
            }
        });
        require(
                ['echarts','echarts/chart/map'],
                function (ec) {
                    map = ec.init(document.getElementById("map"));
                    var option = {
                        series : [
                            {
                                type: 'map',
                                mapType: 'china',
                                hoverable: false,
                                mapLocation: {x : 'center',y : 'center'},
                                itemStyle: {normal: {color:'#A1DBD3',borderWidth:2, borderColor:'#DDF1EE',label: { show: false}}},
                                data:[],
                                geoCoord: {
                                    '安徽': [117.17,31.52], '北京': [116.24,39.55], '重庆': [106.54,29.59],
                                    '福建': [119.18,26.05], '甘肃': [103.51,36.04], '广东': [113.14,23.08],
                                    '广西': [108.19,22.48], '贵州': [106.42,26.35], '海南': [110.2,20.02],
                                    '河北': [114.3,38.02], '河南': [113.4,34.46], '黑龙江': [126.36,45.44],
                                    '湖北': [114.17,30.35], '湖南': [112.59,28.12], '吉林': [125.19,43.54],
                                    '江苏': [118.46,32.03], '江西': [115.55,28.4], '辽宁': [123.25,41.48],
                                    '内蒙古': [111.41,40.48], '宁夏': [106.16,38.27], '青海': [101.48,36.38],
                                    '山东': [117,36.4], '山西': [112.33,37.54], '陕西': [108.57,34.17],
                                    '上海': [121.29,31.14], '四川': [104.04,30.4], '天津': [117.12,39.02],
                                    '西藏': [91.08,29.39], '新疆': [87.36,43.45], '云南': [102.42,25.04],
                                    '浙江': [120.1,30.16],'香港': [115.12,21.23], '澳门': [115.07,21.33],
                                    '台湾': [121.3,25.03]
                                }
                            }
                        ]
                    };
                    map.setOption(option);
                    showMap();
                }
        );

        function flushMap(data){
            var series = map.getSeries().shift();
            series.data=[];
            $(".m_data").fadeOut("normal",function(){$(this).remove();});
            for(var i=0;i<data.length;++i){
                series.data.push({name: data[i].name,itemStyle: {normal: {color: '#20AAC9',label: {show: true}}}});
                var pos = map.chart.map.getPosByGeo(series.mapType,series.geoCoord[data[i].name]);
                tipDiv(data[i].amount,data[i].num,pos);
            }
            map.setSeries([series],true);
        }

        function tipDiv(amount,num,pos){
            var div=$("<div>");
            div.addClass("m_data");
            div.css({"left":pos[0]-20,"top":pos[1]-70});
            div.append($("<i>"));
            var p=$("<p>");
            p.text("交易金额:"+amount);
            div.append(p);
            p=$("<p>");
            p.text("交易笔数:"+num);
            div.append(p);
            div.fadeIn("slow");
            $("#map").append(div);
        }

        function initBar(id,labels,data){
            var ctx = document.getElementById(id).getContext("2d");
            var barChartData = {
                labels : labels,
                datasets : [
                    {
                        fillColor : "rgba(151,187,205,0.5)",
                        strokeColor : "rgba(151,187,205,0.8)",
                        highlightFill : "rgba(151,187,205,0.75)",
                        highlightStroke : "rgba(151,187,205,1)",
                        data : data
                    }
                ]
            };
            new Chart(ctx).Bar(barChartData,{responsive : true, barStrokeWidth : 1,barValueSpacing : 10});
        }

        function showBarChart(){
            var labels =  [<#list tradeDayReportList as report>"${report.createdAt?string("MM-dd")}"<#if report_has_next>,</#if></#list>];
            var data01 =  [<#list tradeDayReportList as report>${report.tradeNum}<#if report_has_next>,</#if></#list>];
            initBar("myChart01",labels,data01);
            var data02 =  [<#list tradeDayReportList as report>${report.tradeSum}<#if report_has_next>,</#if></#list>];
            initBar("myChart02",labels,data02);
            var data03 =  [<#list tradeDayReportList as report>${report.terminalApplyNum}<#if report_has_next>,</#if></#list>];
            initBar("myChart03",labels,data03);
            var data04 =  [<#list tradeDayReportList as report>${report.orderNum}<#if report_has_next>,</#if></#list>];
            initBar("myChart04",labels,data04);
            var data05 =  [<#list tradeDayReportList as report>${report.newUserNum}<#if report_has_next>,</#if></#list>];
            initBar("myChart05",labels,data05);
        }
        showBarChart();
        var page = 1;
        var pageSize = 6;
        function showMap(){
            $.ajax({
                url:"<@spring.url "/real/trade/map"/>",
                async: false,
                type: "POST",
                data: {page:page,pageSize:pageSize},
                success: function(data){
                    flushMap(data.content);
                    page = page+1;
                    if(page > data.totalPage){
                        page = 1;
                    }
                }
            });
            setTimeout(showMap,6000);
        }
    })
</script>
<div class="foot">
    <div class="box">
        <ul class="foot_nav">
            <li><a href="#">关于我们</a></li>
            <li>/</li>
            <li><a href="#">企业文化</a></li>
            <li>/</li>
            <li><a href="#">帮助中心</a></li>
            <li>/</li>
            <li><a href="#">企业招聘</a></li>
        </ul>
        <div class="copyright">版权所有 &copy; 2011-2014 上海掌富网络技术有限公司（备案号 1236548）</div>
    </div>
</div>
</body>
</html>
