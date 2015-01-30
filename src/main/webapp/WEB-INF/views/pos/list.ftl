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
          <select name=""> <option>已付款</option> <option>未付款</option> <option>已付定金</option> </select> 
         </div></li> 
       </ul> 
      </div> 
      <div class="uesr_table"> 
       <table width="100%" border="0" cellspacing="0" cellpadding="0" class="b_table"> 
        <colgroup> 
         <col width="200"> 
         <col width="150"> 
         <col> 
         <col> 
         <col> 
         <col width="80"> 
         <col width="80"> 
         <col width="80"> 
         <col width="150"> 
        </colgroup> 
        <thead> 
         <tr> 
          <th>POS机名称</th> 
          <th>品牌型号</th> 
          <th>库存数</th> 
          <th>第三方供货</th> 
          <th>状态</th> 
          <th>上架</th> 
          <th>可租赁</th> 
          <th>可批购</th> 
          <th>操作</th> 
         </tr> 
        </thead> 
        <tbody> 
         <tr> 
          <td>餐饮点餐机</td> 
          <td>掌富 ZF-300</td> 
          <td>200</td> 
          <td>&nbsp;</td> 
          <td><strong class="strong_status">待审核</strong></td> 
          <td>&nbsp;</td> 
          <td>&nbsp;</td> 
          <td>&nbsp;</td> 
          <td><a href="#" class="a_btn">初审通过</a><a href="#" class="a_btn">编辑</a><a href="#" class="a_btn">查看详情</a></td> 
         </tr> 
         <tr> 
          <td>餐饮点餐机</td> 
          <td>掌富 ZF-300</td> 
          <td>200</td> 
          <td>&nbsp;</td> 
          <td><strong class="strong_status">正常</strong></td> 
          <td>是</td> 
          <td>是</td> 
          <td>是</td> 
          <td> <a href="#" class="a_btn">停用</a> <a href="#" class="a_btn">下架</a> <a href="#" class="a_btn">入库</a> <a href="#" class="a_btn">评论管理</a> <a href="#" class="a_btn">不可租赁</a> <a href="#" class="a_btn">不可批购</a> <a href="#" class="a_btn">查看详情</a> </td> 
         </tr> 
         <tr> 
          <td>餐饮点餐机</td> 
          <td>掌富 ZF-300</td> 
          <td>200</td> 
          <td>&nbsp;</td> 
          <td><strong class="strong_status">已停用</strong></td> 
          <td>&nbsp;</td> 
          <td>&nbsp;</td> 
          <td>&nbsp;</td> 
          <td> <a href="#" class="a_btn">启用</a> <a href="#" class="a_btn">编辑</a> <a href="#" class="a_btn">查看详情</a> </td> 
         </tr> 
         <tr> 
          <td>餐饮点餐机</td> 
          <td>掌富 ZF-300</td> 
          <td>200</td> 
          <td>&nbsp;</td> 
          <td><strong class="strong_status">待审核</strong></td> 
          <td>&nbsp;</td> 
          <td>&nbsp;</td> 
          <td>&nbsp;</td> 
          <td> <a href="#" class="a_btn">审核</a> <a href="#" class="a_btn">编辑</a> <a href="#" class="a_btn">查看详情</a> </td> 
         </tr> 
         <tr> 
          <td>餐饮点餐机</td> 
          <td>掌富 ZF-300</td> 
          <td>200</td> 
          <td>是</td> 
          <td><strong class="strong_status">正常</strong></td> 
          <td>是</td> 
          <td>否</td> 
          <td>否</td> 
          <td> <a href="#" class="a_btn">停用</a> <a href="#" class="a_btn">下架</a> <a href="#" class="a_btn">入库</a> <a href="#" class="a_btn">评论管理</a> <a href="#" class="a_btn">可租赁</a> <a href="#" class="a_btn">可批购</a> <a href="#" class="a_btn">查看详情</a> </td> 
         </tr> 
        </tbody> 
       </table> 
      </div> 
      
	  <@pager.p page=8 totalPages=10 functionName="posPageChange"/>	 
      
     </div> 
</@c.html>