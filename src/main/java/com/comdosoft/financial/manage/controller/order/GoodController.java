/**	______	    		
   /__	__/__  ____     
 	 / / / _ \/ __) _ `/
 	/_/ ()___/_/  \_, /
 				 /___/			
 * 2015年3月23日 下午9:03:13
 */
package com.comdosoft.financial.manage.controller.order;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.DictionaryCardType;
import com.comdosoft.financial.manage.domain.zhangfu.DictionarySignOrderWay;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.manage.domain.zhangfu.GoodBrand;
import com.comdosoft.financial.manage.domain.zhangfu.PayChannel;
import com.comdosoft.financial.manage.domain.zhangfu.PosCategory;
import com.comdosoft.financial.manage.service.DictionaryService;
import com.comdosoft.financial.manage.service.GoodBrandService;
import com.comdosoft.financial.manage.service.PayChannelService;
import com.comdosoft.financial.manage.service.PosCategoryService;

@Controller
@RequestMapping("/good")
public class GoodController {
	@Autowired
	private GoodBrandService goodBrandService;
	@Autowired
	private PosCategoryService posCategoryService;
	@Autowired
	private PayChannelService payChannelService;
	@Autowired
	private DictionaryService dictionaryService;
 	
    @RequestMapping(value="/user/create",method = RequestMethod.GET)
    public String createGet(HttpServletRequest request,Model model){
    	List<GoodBrand> goodBrands = goodBrandService.selectAll();
    	Collection<PosCategory> posCategorys = posCategoryService.listAll();
    	List<PayChannel> payChannels = payChannelService.findCheckedChannels();
    	List<DictionarySignOrderWay> dictionarySignOrderWays = dictionaryService.listAllDictionarySignOrderWays();
    	List<DictionaryCardType> dictionaryCardTypes = dictionaryService.listAllDictionaryCardTypes();
    	List<DictionaryTradeType> dictionaryTradeTypes = dictionaryService.listAllDictionaryTradeTypes();
    	model.addAttribute("goodBrands", goodBrands);
    	model.addAttribute("posCategorys", posCategorys);
    	model.addAttribute("payChannels", payChannels);
    	model.addAttribute("dictionarySignOrderWays", dictionarySignOrderWays);
    	model.addAttribute("dictionaryCardTypes", dictionaryCardTypes);
    	model.addAttribute("dictionaryTradeTypes", dictionaryTradeTypes);
    	return "order/user/goodList";
    }
}
