<#import "../common.ftl" as c />
<#import "../page.ftl" as pager>
<@c.html>
     <div class="breadcrumb"> 
      <ul> 
       <li><a href="#">商品</a></li> 
       <li><a href="#">POS机管理</a></li> 
      </ul> 
     </div> 
     <div class="content clear"> 
      <div class="user_title">
       <h1>POS机列表</h1> 
       <div class="userTopBtnBox"> 
        <a href="#" class="ghostBtn">评论审核</a> 
        <a href="#" class="ghostBtn">管理POS机分类</a> 
        <a href="#" class="ghostBtn">创建POS机分类</a> 
       </div> 
      </div> 
      <div class="seenBox clear"> 
       <ul> 
        <li>
         <div class="user_search">
          <input name="" type="text" class="">
          <button></button>
         </div></li> 
        <li>
         <div class="user_select"> 
          <label>状态筛选</label> 
          <select name=""> 
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
      <div class="uesr_table"> 
      	<#include "page.ftl" />
      </div> 
      
	  <@pager.p page=8 totalPages=10 functionName="posPageChange"/>	 
      
     </div>
<script type="text/javascript">
		function posPageChange(num){
			alert(num);
		}
</script>    
</@c.html>