<#import "../../common.ftl" as c />
<@c.html>
     <div class="breadcrumb"> 
      <ul> 
       <li><a href="#">商品</a></li> 
       <li><a href="#">出库</a></li> 
      </ul> 
     </div> 
     <div class="content clear"> 
      <div class="user_title">
       <h1>出库单列表</h1> 
       <div class="userTopBtnBox"> 
        <a href="<@spring.url "/good/pos/waitComment/list" />" class="ghostBtn">分派</a>
	   </div>
      </div> 
      <div class="seenBox clear"> 
       <ul> 
        <li>
         <div class="user_search">
          <input id="search_keys" name="" type="text" class="" />
          <input id="hidden_keys" type="hidden" name="keys" value="" />
          <input id="hidden_status" type="hidden" name="status" value="" />
          <button id="btn_search"></button>
         </div></li> 
        <li>
         <div class="user_select"> 
          <label>状态筛选</label> 
          <select id="select_status"> 
	          <option value="0">全部</option> 
	          <option value="1">待审核</option> 
	          <option value="2">初审不通过</option> 
	          <option value="3">初审通过</option> 
	          <option value="4">审核不通过</option> 	         
	          <option value="5">正常</option> 
	          <option value="6">已停用</option> 
          </select> 
         </div></li> 
       </ul> 
      </div> 
      <div id="page_fresh">
      	<#include "pageOutStore.ftl" />
      </div>
     </div>
<div class="mask"></div>

<div class="tab putStorage_tab">
    <a href="#" class="close">关闭</a>
    <div class="tabHead">入库</div>
    <div class="tabBody">
        <textarea name="" cols="" rows="">输入终端号</textarea>
    </div>
    <div class="tabFoot"><button class="blueBtn">确定</button></div>
</div>

<div class="tab approve_tab">
    <a href="javascript:void(0);" class="close">关闭</a>
    <div class="tabBody">
        <div class="approve_con">
            <h2>请确认产品可以通过审核</h2>
            <p><input id="isThird" type="checkbox" /> 第三方库存</p>
        </div>
    </div>
    <div class="tabFoot"><button class="blueBtn" id="checkSure">确认</button></div>
</div>

<script type="text/javascript">

	$(function(){
		$('#select_status').change(function(){
			var status = $(this).children('option:selected').val();
			$("#hidden_status").val(status);
			outStorePageChange(1);
		});
		
		$("#btn_search").bind("click",
	        function() {
			var keys = $("#search_keys").val();
			$("#hidden_keys").val(keys);
			outStorePageChange(1);
	    });
       
	});
	
	function outStorePageChange(page) {
		var keys = $("#hidden_keys").val();
		var status = $("#hidden_status").val();
	    $.get('<@spring.url "/task/outStore/page" />',
	            {"page": page,
	             "keys": keys,
	             "status": status
	            },
	            function (data) {
	                $('#page_fresh').html(data);
					popup(".putStorage_tab",".putStorage_a");//
					popup(".approve_tab",".approve_a");//
	            });
	}

    function checkBtn(id){
 		$("#checkSure").click(function(){check(id)});
    }

	function firstUnCheck(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/firstUnCheck',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
					popup(".putStorage_tab",".putStorage_a");//入库 POS机管理
					popup(".approve_tab",".approve_a");//通过审核
	            });
	}
	
	function firstCheck(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/firstCheck',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
					popup(".putStorage_tab",".putStorage_a");//入库 POS机管理
					popup(".approve_tab",".approve_a");//通过审核
	            });	
	}
	
	function unCheck(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/unCheck',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
					popup(".putStorage_tab",".putStorage_a");//入库 POS机管理
					popup(".approve_tab",".approve_a");//通过审核
	            });	
	}
	
	function check(id){
		var isThird = $('#isThird').prop("checked");
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/check',
				{"isThird":isThird},
	            function (data) {
	                $('#row_'+id).replaceWith(data);
					popup(".putStorage_tab",".putStorage_a");//入库 POS机管理
					popup(".approve_tab",".approve_a");//通过审核
					$('.approve_tab').hide();
					$('.mask').hide();
	            });
	}
	function stop(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/stop',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
					popup(".putStorage_tab",".putStorage_a");//入库 POS机管理
					popup(".approve_tab",".approve_a");//通过审核
	            });
	}
	function start(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/start',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
					popup(".putStorage_tab",".putStorage_a");//入库 POS机管理
					popup(".approve_tab",".approve_a");//通过审核
	            });
	}
	
	function publish(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/publish',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
					popup(".putStorage_tab",".putStorage_a");//入库 POS机管理
					popup(".approve_tab",".approve_a");//通过审核
	            });
	}
	
	function unPublish(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/unPublish',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
					popup(".putStorage_tab",".putStorage_a");//入库 POS机管理
					popup(".approve_tab",".approve_a");//通过审核
	            });
	}
	
	function lease(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/lease',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
					popup(".putStorage_tab",".putStorage_a");//入库 POS机管理
					popup(".approve_tab",".approve_a");//通过审核
	            });
	}
	
	function unLease(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/unLease',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
					popup(".putStorage_tab",".putStorage_a");//入库 POS机管理
					popup(".approve_tab",".approve_a");//通过审核
	            });
	}
	
	function purchase(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/purchase',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
					popup(".putStorage_tab",".putStorage_a");//入库 POS机管理
					popup(".approve_tab",".approve_a");//通过审核
	            });
	}
	
	function unPurchase(id){
		$.get('<@spring.url "" />'+'/good/pos/'+id+'/unPurchase',
	            function (data) {
	                $('#row_'+id).replaceWith(data);
					popup(".putStorage_tab",".putStorage_a");//入库 POS机管理
					popup(".approve_tab",".approve_a");//通过审核
	            });
	}
	
</script>    
</@c.html>