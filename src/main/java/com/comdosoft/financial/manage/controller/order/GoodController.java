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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.comdosoft.financial.manage.domain.zhangfu.DictionaryBillingCycle;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryCardType;
import com.comdosoft.financial.manage.domain.zhangfu.DictionarySignOrderWay;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryTradeType;
import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.GoodBrand;
import com.comdosoft.financial.manage.domain.zhangfu.GoodComment;
import com.comdosoft.financial.manage.domain.zhangfu.PayChannel;
import com.comdosoft.financial.manage.domain.zhangfu.PosCategory;
import com.comdosoft.financial.manage.service.DictionaryService;
import com.comdosoft.financial.manage.service.GoodBrandService;
import com.comdosoft.financial.manage.service.GoodCommentService;
import com.comdosoft.financial.manage.service.GoodService;
import com.comdosoft.financial.manage.service.PayChannelService;
import com.comdosoft.financial.manage.service.PosCategoryService;
import com.comdosoft.financial.manage.utils.page.Page;

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
	@Autowired
	private GoodService goodService;
	@Autowired
	private GoodCommentService goodCommentService;

	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public String createGet(HttpServletRequest request, Integer page,
			Integer goodBrandsId, Integer posCategoryId,
			Integer signOrderWayId, Model model, Integer payChannelId,
			Integer cardTypeId, Integer tradeTypeId,
			Integer billingCycleId,Integer minPrice,Integer maxPrice,Boolean hasLease,
			String orderBy,String orderType) {
		find(page, goodBrandsId, posCategoryId, signOrderWayId, model, payChannelId, cardTypeId, tradeTypeId, billingCycleId, minPrice, maxPrice, hasLease, orderBy, orderType);
//		List<GoodBrand> goodBrands = goodBrandService.selectAll();
//		Collection<PosCategory> posCategorys = posCategoryService.listAll();
//		List<PayChannel> payChannels = payChannelService.findCheckedChannels();
//		List<DictionarySignOrderWay> dictionarySignOrderWays = dictionaryService
//				.listAllDictionarySignOrderWays();
//		List<DictionaryCardType> dictionaryCardTypes = dictionaryService
//				.listAllDictionaryCardTypes();
//		List<DictionaryTradeType> dictionaryTradeTypes = dictionaryService
//				.listAllDictionaryTradeTypes();
//		List<DictionaryBillingCycle> dictionaryBillingCycles = dictionaryService
//				.listAllDictionaryBillingCycles();
//		findPage(page, goodBrandsId, posCategoryId, signOrderWayId, model, payChannelId, cardTypeId, tradeTypeId, billingCycleId, minPrice, maxPrice, hasLease, orderBy, orderType);
//		model.addAttribute("goodBrands", goodBrands);
//		model.addAttribute("posCategorys", posCategorys);
//		model.addAttribute("payChannels", payChannels);
//		model.addAttribute("dictionarySignOrderWays", dictionarySignOrderWays);
//		model.addAttribute("dictionaryCardTypes", dictionaryCardTypes);
//		model.addAttribute("dictionaryTradeTypes", dictionaryTradeTypes);
//		model.addAttribute("dictionaryBillingCycles", dictionaryBillingCycles);
		return "order/user/goodList";
	}

	/**
	 * @description 条件搜索
	 * @author Tory
	 * @date 2015年3月25日 下午11:55:32
	 */
	@RequestMapping(value = "/user/page", method = RequestMethod.GET)
	public String page(Integer page, Integer goodBrandsId,
			Integer posCategoryId, Integer signOrderWayId, Model model,
			Integer payChannelId, Integer cardTypeId, Integer tradeTypeId,
			Integer billingCycleId,Integer minPrice,Integer maxPrice,Boolean hasLease,
			String orderBy,String orderType) {
		find(page, goodBrandsId, posCategoryId, signOrderWayId, model, payChannelId, cardTypeId, tradeTypeId, billingCycleId, minPrice, maxPrice, hasLease, orderBy, orderType);
		return "order/user/goodListFresh";
	}
	
	@RequestMapping(value = "/agent/page", method = RequestMethod.GET)
	public String agentPage(Integer page, Integer goodBrandsId,
			Integer posCategoryId, Integer signOrderWayId, Model model,
			Integer payChannelId, Integer cardTypeId, Integer tradeTypeId,
			Integer billingCycleId,Integer minPrice,Integer maxPrice,Boolean hasLease,
			String orderBy,String orderType) {
		String path="order/agent/goodList";
		find(page, goodBrandsId, posCategoryId, signOrderWayId, model, payChannelId, cardTypeId, tradeTypeId, billingCycleId, minPrice, maxPrice, hasLease, orderBy, orderType);
		if(null!=page){
			path="order/agent/goodListFresh";
		}
		return path;
	}
	
	@RequestMapping(value = "/batch/page", method = RequestMethod.GET)
	public String batchPage(Integer page, Integer goodBrandsId,
			Integer posCategoryId, Integer signOrderWayId, Model model,
			Integer payChannelId, Integer cardTypeId, Integer tradeTypeId,
			Integer billingCycleId,Integer minPrice,Integer maxPrice,Boolean hasLease,
			String orderBy,String orderType) {
		String path="order/batch/goodList";
		find(page, goodBrandsId, posCategoryId, signOrderWayId, model, payChannelId, cardTypeId, tradeTypeId, billingCycleId, minPrice, maxPrice, hasLease, orderBy, orderType);
		if(null!=page){
			path="order/batch/goodListFresh";
		}
		return path;
	}

	public void find(Integer page, Integer goodBrandsId,
			Integer posCategoryId, Integer signOrderWayId, Model model,
			Integer payChannelId, Integer cardTypeId, Integer tradeTypeId,
			Integer billingCycleId,Integer minPrice,Integer maxPrice,Boolean hasLease,
			String orderBy,String orderType){
		List<GoodBrand> goodBrands = goodBrandService.selectAll();
		Collection<PosCategory> posCategorys = posCategoryService.listAll();
		List<PayChannel> payChannels = payChannelService.findCheckedChannels();
		List<DictionarySignOrderWay> dictionarySignOrderWays = dictionaryService
				.listAllDictionarySignOrderWays();
		List<DictionaryCardType> dictionaryCardTypes = dictionaryService
				.listAllDictionaryCardTypes();
		List<DictionaryTradeType> dictionaryTradeTypes = dictionaryService
				.listAllDictionaryTradeTypes();
		List<DictionaryBillingCycle> dictionaryBillingCycles = dictionaryService
				.listAllDictionaryBillingCycles();
		findPage(page, goodBrandsId, posCategoryId, signOrderWayId, model, payChannelId, cardTypeId, tradeTypeId, billingCycleId, minPrice, maxPrice, hasLease, orderBy, orderType);
		model.addAttribute("goodBrands", goodBrands);
		model.addAttribute("posCategorys", posCategorys);
		model.addAttribute("payChannels", payChannels);
		model.addAttribute("dictionarySignOrderWays", dictionarySignOrderWays);
		model.addAttribute("dictionaryCardTypes", dictionaryCardTypes);
		model.addAttribute("dictionaryTradeTypes", dictionaryTradeTypes);
		model.addAttribute("dictionaryBillingCycles", dictionaryBillingCycles);
		model.addAttribute("minPrice", minPrice);
		model.addAttribute("maxPrice", maxPrice);
		model.addAttribute("hasLease", hasLease);
		model.addAttribute("orderBy", orderBy);
		model.addAttribute("orderType", orderType);
		if(null != goodBrandsId){
			for (GoodBrand goodBrand : goodBrands) {
				if ( goodBrand.getId() == goodBrandsId) {
					model.addAttribute("goodBrandSelected", goodBrand);
					break;
				}
			}
		}
		if(null != posCategoryId){
			for (PosCategory posCategory : posCategorys) {
				if (posCategory.getId() == posCategoryId) {
					model.addAttribute("posCategorySelected", posCategory);
					break;
				}
			}
		}
		if(null != payChannelId){
			for (PayChannel payChannel : payChannels) {
				if (payChannel.getId() == payChannelId) {
					model.addAttribute("payChannelSelected", payChannel);
					break;
				}
			}
		}
		if(null != cardTypeId){
			for (DictionaryCardType dictionaryCardType : dictionaryCardTypes) {
				if (dictionaryCardType.getId() == cardTypeId) {
					model.addAttribute("cardTypeSelected", dictionaryCardType);
					break;
				}
			}
		}
		if(null != tradeTypeId){
			for (DictionaryTradeType dictionaryTradeType : dictionaryTradeTypes) {
				if (dictionaryTradeType.getId() == tradeTypeId) {
					model.addAttribute("tradeTypeSelected", dictionaryTradeType);
					break;
				}
			}
		}
		if(null != signOrderWayId){
			for (DictionarySignOrderWay dictionarySignOrderWay : dictionarySignOrderWays) {
				if (dictionarySignOrderWay.getId() == signOrderWayId) {
					model.addAttribute("dictionarySignOrderWaySelected",
							dictionarySignOrderWay);
					break;
				}
			}
		}
		if(null!=billingCycleId){
			for (DictionaryBillingCycle dictionaryBillingCycle : dictionaryBillingCycles) {
				if(billingCycleId==dictionaryBillingCycle.getId()){
					model.addAttribute("dictionaryBillingCycleSelected", dictionaryBillingCycle);
					break;
				}
			}
		}
		
	}
	
	private void findPage(Integer page, Integer goodBrandsId,
			Integer posCategoryId, Integer signOrderWayId, Model model,
			Integer payChannelId, Integer cardTypeId, Integer tradeTypeId,
			Integer billingCycleId,Integer minPrice,Integer maxPrice,Boolean hasLease,
			String orderBy,String orderType) {
		if (page == null || page<1) {
			page = 1;
		}
		if(null!=hasLease&&!hasLease){
			hasLease=null;
		}
		Page<Good> goods = goodService.selectGoods(page, (byte) 5,
				goodBrandsId, posCategoryId, signOrderWayId, payChannelId,
				cardTypeId, tradeTypeId, billingCycleId, minPrice, maxPrice, hasLease, orderBy, orderType);
		model.addAttribute("goods", goods);
	}
	
	@RequestMapping(value = "/user/{id}/detail", method = RequestMethod.GET)
	public String detail(Model model,@PathVariable Integer id,Integer page, Integer payChannelId){
		getDetail(model, id, page, payChannelId);
		return "order/user/goodDetail";
	}
	
	@RequestMapping(value = "/agent/{id}/detail", method = RequestMethod.GET)
	public String detailAgent(Model model,@PathVariable Integer id,Integer page, Integer payChannelId){
		getDetail(model, id, page, payChannelId);
		return "order/agent/goodDetail";
	}
	
	@RequestMapping(value = "/batch/{id}/detail", method = RequestMethod.GET)
	public String detailBatch(Model model,@PathVariable Integer id,Integer page, Integer payChannelId){
		getDetail(model, id, page, payChannelId);
		return "order/batch/goodDetail";
	}
	
	public void getDetail(Model model,Integer id,Integer page, Integer payChannelId){
		if (page == null) {
			page = 1;
		}
		Good good = goodService.findGoodInfoEx(id);
		Page<GoodComment> goodComments = goodCommentService.findCommentPagesByGoodId(id, page);
		if(null==payChannelId){
			payChannelId=good.getChannels().get(0).getId();
		}
		PayChannel findChannelInfo = payChannelService.findChannelInfo(payChannelId);
		model.addAttribute("goodComments", goodComments);
		model.addAttribute("good", good);
		model.addAttribute("payChannel", findChannelInfo);
	}
	
	
	
	@RequestMapping(value = "/user/comment/{id}/page", method = RequestMethod.GET)
	public String commentPage(@PathVariable Integer id,Integer page, Model model){
		if (page == null) {
			page = 1;
		}
		Page<GoodComment> goodComments = goodCommentService.findCommentPagesByGoodId(id, page);
		model.addAttribute("goodComments", goodComments);
		return "order/user/pageGoodComment";
	}
	
	@RequestMapping(value = "/agent/comment/{id}/page", method = RequestMethod.GET)
	public String commentAgentPage(@PathVariable Integer id,Integer page, Model model){
		if (page == null) {
			page = 1;
		}
		Page<GoodComment> goodComments = goodCommentService.findCommentPagesByGoodId(id, page);
		model.addAttribute("goodComments", goodComments);
		return "order/agent/goodCommentPage";
	}

	@RequestMapping(value = "/batch/comment/{id}/page", method = RequestMethod.GET)
	public String commentBatchPage(@PathVariable Integer id,Integer page, Model model){
		if (page == null) {
			page = 1;
		}
		Page<GoodComment> goodComments = goodCommentService.findCommentPagesByGoodId(id, page);
		model.addAttribute("goodComments", goodComments);
		return "order/batch/goodCommentPage";
	}
	
	
}
