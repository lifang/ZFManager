<#import "commonOrder.ftl" as c />
<@c.html>
<div class="main mshop">
	<div class="box">
	<div class="category_group">
    	<div class="crumbs_nav">
        	<ul>
            	<li><a href="#">您当前选择</a><i></i></li>
                <li class="crumbs_nav_drop"><a href="#" class="hover"><span class="cnd_p">POS机品牌：掌付</span><span class="cnd_x"></span></a><i></i></li>
            </ul>
        </div>
    	<div class="category_item">
        	<h4>POS机品牌：</h4>
            <a href="javascript:void(0);" class="more">更多<i></i></a>
            <div class="category_item_con">
            	<ul>
            		<#if goodBrands??>
                    <li><a href="#" class="hover">掌付</a></li>
	                    <#list goodBrands as goodBrand>
		                    <li><a href="#">${goodBrand.name!""}</a></li>
	                    </#list>
                    </#if>
                 </ul>
            </div>
        </div>
        <div class="category_item">
        	<h4>POS机类型：</h4>
            <div class="category_item_con">
            	<ul>
            		<#if posCategorys??>
	                    <#list posCategorys as posCategory>
		                    <li><a href="#">${posCategory.name!""}</a></li>
	                    </#list>
                    </#if>
                </ul>
            </div>
        </div>
        <div class="category_item">
        	<h4>支付通道：</h4>
            <div class="category_item_con">
            	<ul>
            		<#if payChannels??>
	                    <#list payChannels as payChannel>
		                    <li><a href="#">${payChannel.name!""}</a></li>
	                    </#list>
                    </#if>
                </ul>
            </div>
        </div>
        <div class="category_item">
        	<h4>支持卡类型：</h4>
            <div class="category_item_con">
            	<ul>
            		<#if dictionaryCardTypes??>
	                    <#list dictionaryCardTypes as dictionaryCardType>
		                    <li><a href="#">${dictionaryCardType.cardType!""}</a></li>
	                    </#list>
                    </#if>
                </ul>
            </div>
        </div>
        <div class="category_item">
        	<h4>支持交易类型：</h4>
            <div class="category_item_con">
            	<ul>
                	<#if dictionaryCardTypes??>
	                    <#list dictionaryTradeTypes as dictionaryTradeType>
		                    <li><a href="#">${dictionaryTradeType.tradeValue!""}</a></li>
	                    </#list>
                    </#if>
                </ul>
            </div>
        </div>
        <div class="category_item category_item_noBorder">
        	<h4>签购单方式：</h4>
            <div class="category_item_con">
            	<ul>
            		<#if dictionarySignOrderWays??>
	                    <#list dictionarySignOrderWays as dictionarySignOrderWay>
		                    <li><a href="#">${dictionarySignOrderWay.signOrderWay!""}</a></li>
	                    </#list>
                    </#if>
                </ul>
            </div>
        </div>
    </div>
    <div class="sortbar clear">
    	<div class="sortbar_ul">
            <ul>
                <li class="default_sort hover"><a href="javasrcipt:void(0);">综合排序</a></li>
                <li class="on_1"><span>按价格</span>
                    <div class="droplist">
                        <a href="javascript:void(0);" class="dashed">按价格从高到低</a>
                        <a href="javascript:void(0);">按价格从低到高</a>
                    </div>
                </li>
                <li class="default_sort" title="按销量从高到低"><a href="javascript:void(0);">按销量</a></li>
                <li class="default_sort" title="按评价从多到少"><a href="javascript:void(0);">按评价</a></li>
            </ul>
        </div>
        <div class="accountTime">
        	<label>到账时间</label>
            <div class="selectBox">
            	<i></i>
            	<div class="tag_select"><span>选择到账时间选择到账时间</span></div>
                <input name="" type="text" value="" class="tag_input">
                <ul>
                	<li>T+1<input name="" type="text" value="1"></li>
                	<li>T+2<input name="" type="text" value="2"></li>
                	<li>T+4<input name="" type="text" value="3"></li>
                	<li>T+3<input name="" type="text" value="4"></li>
            	</ul>
           </div>
        </div>
        <div class="price">
        	<label>价格</label>
            <input name="" type="text" placeholder="¥" /> - <input name="" type="text" placeholder="¥" />
        </div>
        <div class="lease"><input name="" type="checkbox" value="" /> 支持租赁</div>
        <div class="sortBtn"><button class="btn">确定</button></div>
        <div class="page_top">
        	<div class="page_info"><span>2</span>/20</div>
            <a href="javascript:void(0);" class="page_prev"><i></i></a>
            <a href="javascript:void(0);" class="page_next"><i></i></a>
        </div>
    </div>
    
    <div class="commodityBox">
    	<table width="100%" cellspacing="0" cellpadding="0" class="b_table">
        <colgroup>
            <col width="640" />
            <col />
            <col />
            <col />
         </colgroup>
         <thead>
          <tr>
            <th width="640">商品</th>
            <th>价格</th>
            <th>销量</th>
            <th>评价</th>
          </tr>
         </thead>
          <tr>
            <td>
            	<div class="td_proBox clear">
                	<a href="#" class="cn_img"><img src="images/c.jpg" /></a>
                    <div class="td_proBox_info">
                    	<h1><a href="#">汉米SS3010收银机 触摸屏POS机收款机 超市餐饮服装点餐机奶茶店</a></h1>
                        <h3>热销5000件</h3>
                        <ul>
                        	<li><span>品牌型号：</span><div class="c_text">掌富ZF-300</div></li>
                            <li><span>支付通道：</span><div class="c_text">收账宝</div></li>
                        </ul>
                    </div>
                </div>
            </td>
            <td><a href="#"><strong>￥458.00</strong></a></td>
            <td><a href="#">月销量<em>35352</em>件</a></td>
            <td>
            	<div class="evaluate">
                	<a href="#">
                    <ul>
                        <li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li></li>
                    </ul>
                    </a>
                </div>
            </td>
          </tr>
          <tr>
            <td>
            	<div class="td_proBox clear">
                	<a href="#" class="cn_img"><img src="images/c.jpg" /></a>
                    <div class="td_proBox_info">
                    	<h1><a href="#">汉米SS3010收银机 触摸屏POS机收款机 超市餐饮服装点餐机奶茶店</a></h1>
                        <h3>热销5000件</h3>
                        <ul>
                        	<li><span>品牌型号：</span><div class="c_text">掌富ZF-300</div></li>
                            <li><span>支付通道：</span><div class="c_text">收账宝</div></li>
                        </ul>
                    </div>
                </div>
            </td>
            <td><a href="#"><strong>￥458.00</strong></a></td>
            <td><a href="#">月销量<em>35352</em>件</a></td>
            <td>
            	<div class="evaluate">
                	<a href="#">
                    <ul>
                        <li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li class="p_li_o"></li><li></li>
                    </ul>
                    </a>
                </div>
            </td>
          </tr>
        </table>
		<div class="pageTurn"> 
        	<div class="p_num">
            <a href="#" class="disabled">上一页</a> 
            <a href="#" class="current">1</a> 
            <a href="#?page=2">2</a> 
            <a href="#?page=3">3</a> 
            <a href="#?page=4">4</a> 
            <a href="#?page=5">5</a>
            ...
            <a href="#?page=199">199</a> 
            <a href="#?page=200">200</a> 
            <a href="#?page=2">下一页</a> 
            </div>
            <div class="p_skip">
            	 <span>共24页</span>
                 <span>到第&nbsp;&nbsp;<input name="" type="text" />&nbsp;&nbsp;页</span>
                 <button>确定</button>
            </div>
        </div>
    </div>
    
</div>
</div>
</@c.html>