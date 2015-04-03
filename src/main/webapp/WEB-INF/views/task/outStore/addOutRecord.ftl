<#import "../../common.ftl" as c />
<@c.html>
     <div class="breadcrumb"> 
      <ul> 
       <li><a href="#">商品</a></li> 
       <li><a href="#">出库</a></li>
       <li><a href="#">添加出库记录</a></li> 
      </ul> 
     </div> 
     <div class="content clear"> 
      <div class="user_title">
       <h1>添加出库记录</h1> 
       <div class="userTopBtnBox"> 
        
	   </div>
      </div> 
      <div class="seenBox clear"> 
       <div class="uesr_table"> 
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table"> 
		    <colgroup> 
		     <col width="150"> 
		     <col width="350"> 
		     <col width="50"> 
		     <col width="350">
		    </colgroup> 
		    <thead> 
		     <tr>
		      <th></th> 
		      <th>商品</th> 
		      <th>数量</th> 
		      <th>终端号</th>
		     </tr> 
		    </thead> 
		    <tbody> 
		     <#if (goods)??>
			  <#list goods as good>
			  	  <tr id="row_${good.id}"> 
				      <td>${good.urlPath}</td> 
				      <td>${good.title}<br/>
				      		${good.brandName}<br/>
				      		${good.channelName}
				      </td>
				      <td>${good.quantity}</td> 
				      <td>
				      	<input type="text"/>
				      </td>
				   </tr>
			  </#list>
			</#if>
		    </tbody> 
		 </table> 
		</div> 
      </div> 
      <div>
		
      	收货地址： <#if (address)??>${address}</#if> <br/>
      	物流公司:<input type="text"/>
      	物流单号：<input type="text"/>
      </div>
      <div class="btnBottom"><button class="blueBtn" onClick="submitData()">
			确定
		</button></div>
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