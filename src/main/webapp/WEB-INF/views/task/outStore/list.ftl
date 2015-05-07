<#import "../../common.ftl" as c />
<@c.html>
     <div class="breadcrumb"> 
      <ul> 
       <li>商品</li> 
       <li><a href="<@spring.url "/task/outStore/list"/>" class="hover">出库</a></li>
      </ul> 
     </div> 
     <div class="content clear"> 
      <div class="user_title">
       <h1>出库单列表</h1> 
       <div class="userTopBtnBox">
	<#if Roles.hasRole("OUT_STORE_ASSIGN")>
       	<a id="btn_dispatch" class="ghostBtn assign_a">分派</a>
	</#if>
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
	          <option value="1">待处理</option> 
	          <option value="2">已取消</option> 
	          <option value="3">处理完成</option>
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
		var keys = $("#search_keys").val();
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

</script>    
</@c.html>