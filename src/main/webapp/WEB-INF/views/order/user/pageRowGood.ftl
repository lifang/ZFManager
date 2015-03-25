  <tr>
    <td>
    	<div class="td_proBox clear">
        	<a href="#" class="cn_img"><img src="images/c.jpg" /></a>
            <div class="td_proBox_info">
            	<h1><a href="#">${good.title!""}</a></h1>
                <h3>${good.secondTitle!""}</h3>
                <ul>
                	<li><span>品牌型号：</span><div class="c_text"><#if good.goodBrand??>${good.goodBrand.name!""}</#if></div></li>
                    <li><span>支付通道：</span><div class="c_text">收账宝</div></li>
                </ul>
            </div>
        </div>
    </td>
    <td><a href="#"><strong>￥${(good.price/100)?string("0.00")}</strong></a></td>
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