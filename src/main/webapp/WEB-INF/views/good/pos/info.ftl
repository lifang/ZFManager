<#import "../../common.ftl" as c />
<@c.html>
	<div class="breadcrumb">
        <ul>
            <li>商品</li>
            <li><a href="<@spring.url "/good/pos/list"/>">POS机管理</a></li>
            <li><a href="<@spring.url "/good/pos/${good.id}/info" />">POS机详情</a></li>
        </ul>
    </div>
    <#include "../../common/pos/info_part.ftl"/>

<div class="tab approve_tab">
		<a href="javascript:void(0);" class="close">关闭</a>
		<div class="tabBody">
	    	<div class="approve_con">
	        	<h2>请确认产品可以通过审核</h2>
	            <p><input id="isThird" type="checkbox" /> 第三方库存</p>
	        </div>
		</div>
	    <div class="tabFoot"><button class="blueBtn" onClick="check(${good.id})">确认</button></div>
	</div>
	
	<script type="text/javascript">

	function firstUnCheck(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/firstUnCheck?source=info',
	            function (data) {
	                $('.userTopBtnBox').replaceWith(data);
	            });
	};
	
	function firstCheck(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/firstCheck?source=info',
	            function (data) {
	                $('.userTopBtnBox').replaceWith(data);
	            });	
	};
	
	function unCheck(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/unCheck?source=info',
	            function (data) {
	                $('.userTopBtnBox').replaceWith(data);
	            });	
	};
	
	function check(id){
		var isThird = $('#isThird').prop("checked");
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/check?source=info',
	            {
	            	"isThird": isThird
	            },
	            function (data) {
					$('.approve_tab').hide();
					$('.mask').hide();
	                $('.userTopBtnBox').replaceWith(data);
	                
	    });
	};
	
	function stop(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/stop?source=info',
	            function (data) {
	                $('.userTopBtnBox').replaceWith(data);
	            });
	};	
	function start(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/start?source=info',
	            function (data) {
	                $('.userTopBtnBox').replaceWith(data);
	            });
	};	
	
	
</script> 
</@c.html>