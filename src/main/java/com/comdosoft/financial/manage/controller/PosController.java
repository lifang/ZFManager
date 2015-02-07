package com.comdosoft.financial.manage.controller;

import java.io.File;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryCardType;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryEncryptCardWay;
import com.comdosoft.financial.manage.domain.zhangfu.DictionarySignOrderWay;
import com.comdosoft.financial.manage.domain.zhangfu.Factory;
import com.comdosoft.financial.manage.domain.zhangfu.Good;
import com.comdosoft.financial.manage.domain.zhangfu.PayChannel;
import com.comdosoft.financial.manage.domain.zhangfu.PosCategory;
import com.comdosoft.financial.manage.service.DictionaryCardTypeService;
import com.comdosoft.financial.manage.service.DictionaryEncryptCardWayService;
import com.comdosoft.financial.manage.service.DictionarySignOrderWayService;
import com.comdosoft.financial.manage.service.FactoryService;
import com.comdosoft.financial.manage.service.GoodService;
import com.comdosoft.financial.manage.service.PayChannelService;
import com.comdosoft.financial.manage.service.PosCategoryService;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.utils.Constants;
import com.comdosoft.financial.manage.utils.FileUtil;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/pos")
public class PosController {
	
	private Logger LOG = LoggerFactory.getLogger(PosController.class);
	
	@Value("${filepath.root}")
	private String rootPath;
	
	@Autowired
	private GoodService goodService ;
	@Autowired
	private PosCategoryService posCategoryService;
	@Autowired
	private FactoryService factoryService;
	@Autowired
	private DictionarySignOrderWayService dictionarySignOrderWayService;
	@Autowired
	private DictionaryCardTypeService dictionaryCardTypeService;	
	@Autowired
	private DictionaryEncryptCardWayService dictionaryEncryptCardWayService;
	@Autowired
	private PayChannelService payChannelService;
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "pos/list";
	}
	
	@RequestMapping(value="page",method=RequestMethod.GET)
	public String page(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "pos/pagePos";
	}
	
	@RequestMapping(value="{id}/info",method=RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model){
		Good good = goodService.findGoodInfo(id);
		model.addAttribute("good", good);
		return "pos/info";
	}
	
	@RequestMapping(value="{id}/firstUnCheck",method=RequestMethod.GET)
	public String firstUnCheck(@PathVariable Integer id, String source, Model model){
		Good good = goodService.statusFirstUnCheck(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "pos/infoStatus";
		}
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/firstCheck",method=RequestMethod.GET)
	public String firstCheck(@PathVariable Integer id, String source, Model model){
		Good good = goodService.statusFirstCheck(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "pos/infoStatus";
		}
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/unCheck",method=RequestMethod.GET)
	public String unCheck(@PathVariable Integer id, String source, Model model){
		Good good = goodService.statusUnCheck(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "pos/infoStatus";
		}
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/check",method=RequestMethod.GET)
	public String check(@PathVariable Integer id, String source, Boolean isThird, Model model){
		Good good = goodService.statusCheck(id, isThird);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "pos/infoStatus";
		}
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/stop",method=RequestMethod.GET)
	public String stop(@PathVariable Integer id, String source, Model model){
		Good good = goodService.statusStop(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "pos/infoStatus";
		}
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/start",method=RequestMethod.GET)
	public String start(@PathVariable Integer id, String source, Model model){
		Good good = goodService.statusWaitingFirstCheck(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "pos/infoStatus";
		}
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/publish",method=RequestMethod.GET)
	public String publish(@PathVariable Integer id, Model model){
		Good good = goodService.publish(id);
		model.addAttribute("good", good);
		return "pos/pageRowPos";
	}
	
	
	@RequestMapping(value="{id}/unPublish",method=RequestMethod.GET)
	public String unPublish(@PathVariable Integer id, Model model){
		Good good = goodService.unPublish(id);
		model.addAttribute("good", good);
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/lease",method=RequestMethod.GET)
	public String lease(@PathVariable Integer id, Model model){
		Good good = goodService.lease(id);
		model.addAttribute("good", good);
		return "pos/pageRowPos";
	}
	
	
	@RequestMapping(value="{id}/unLease",method=RequestMethod.GET)
	public String unLease(@PathVariable Integer id, Model model){
		Good good = goodService.unLease(id);
		model.addAttribute("good", good);
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/purchase",method=RequestMethod.GET)
	public String purchase(@PathVariable Integer id, Model model){
		Good good = goodService.purchase(id);
		model.addAttribute("good", good);
		return "pos/pageRowPos";
	}
	
	
	@RequestMapping(value="{id}/unPurchase",method=RequestMethod.GET)
	public String unPurchase(@PathVariable Integer id, Model model){
		Good good = goodService.unPurchase(id);
		model.addAttribute("good", good);
		return "pos/pageRowPos";
	}	
	
	@RequestMapping(value="{id}/edit",method=RequestMethod.GET)
	public String edit(@PathVariable Integer id, Model model){
		Good good = goodService.findGoodInfo(id);
		Collection<PosCategory> posCategories = posCategoryService.listAll();
		List<Factory> factories = factoryService.findCheckedFactories();
		List<DictionarySignOrderWay> signOrderWays = dictionarySignOrderWayService.listAll();
		List<DictionaryCardType> cardTypes = dictionaryCardTypeService.listAll();
		List<DictionaryEncryptCardWay> encryptCardWays = dictionaryEncryptCardWayService.listAll();
		model.addAttribute("good", good);
		model.addAttribute("posCategories", posCategories);
		model.addAttribute("factories", factories);
		model.addAttribute("signOrderWays", signOrderWays);
		model.addAttribute("cardTypes", cardTypes);
		model.addAttribute("encryptCardWays", encryptCardWays);

		return "pos/create";
	}
	
	@RequestMapping(value="searchChannel",method=RequestMethod.GET)
	public String searchChannel(String name, Model model){
		if (name != null && name.length() > 0) {
			List<PayChannel> channels = payChannelService.findCheckedChannelsLikeName(name);
			model.addAttribute("channels", channels);
		}
		return "pos/searchChannel";
	}
	
	@RequestMapping(value="searchGood",method=RequestMethod.GET)
	public String searchGood(String name, Model model){
		if (name != null && name.length() > 0) {
			List<Good> goods = goodService.findCheckedGoodsLikeKey(name);
			model.addAttribute("goods", goods);
		}
		return "pos/searchGood";
	}
	
	@RequestMapping(value="uploadImg",method=RequestMethod.POST)
	@ResponseBody
	public Response uploadImg(MultipartFile file){
		String fileName = Constants.PATH_PREFIX_POS+FileUtil.getFilePath()+".jpg";
		try {
			File osFile = new File(rootPath + fileName);
			if (!osFile.getParentFile().exists()) {
				osFile.getParentFile().mkdirs();
			}
			file.transferTo(osFile);
		} catch (Exception e) {
			LOG.error("", e);
			return Response.getError("上传失败！");
		}
		return Response.getSuccess(fileName);
	}
	
	@RequestMapping(value = "create", method = RequestMethod.POST)
	@ResponseBody
	public Response create(
			String title,
			String secondTitle,
			String keyWorlds,
			Integer posCategoryId,
			Integer factoryId,
			String goodBrandName,
			String modelNumber,
			Integer encryptCardWayId,
			Integer signOrderWayId,
			@RequestParam(value = "cardTypes[]", required = false) Integer[] cardTypes,
			String batteryInfo,
			String shellMaterial,
			Float price,
			Float retailPrice,
			Float purchasePrice,
			Float floorPrice,
			Integer floorPurchaseQuantity,
			Float leaseDeposit,
			Float leasePrice,
			Integer leaseTime,
			Integer returnTime,
			String leaseDescription,
			String leaseAgreement,
			@RequestParam(value = "channels[]", required = false) Integer[] channels,
			String description,
			@RequestParam(value = "photoUrls[]", required = false) String[] photoUrls,
			@RequestParam(value = "goods[]", required = false) Integer[] goods,
			HttpServletRequest request) {
		Customer customer = sessionService.getLoginInfo(request);
		goodService.create(title, secondTitle, keyWorlds, posCategoryId,
				factoryId, goodBrandName, modelNumber, encryptCardWayId,
				signOrderWayId, cardTypes, batteryInfo, shellMaterial, price,
				retailPrice, purchasePrice, floorPrice, floorPurchaseQuantity,
				leaseDeposit, leasePrice, leaseTime, returnTime, leaseDescription,
				leaseAgreement, channels, description, photoUrls, goods,
				customer.getId(), customer.getTypes());
		return Response.getSuccess("");
	}
	
	@RequestMapping(value = "{id}/update", method = RequestMethod.POST)
	@ResponseBody
	public Response update(
			@PathVariable Integer id,
			String title,
			String secondTitle,
			String keyWorlds,
			Integer posCategoryId,
			Integer factoryId,
			String goodBrandName,
			String modelNumber,
			Integer encryptCardWayId,
			Integer signOrderWayId,
			@RequestParam(value = "cardTypes[]", required = false) Integer[] cardTypes,
			String batteryInfo,
			String shellMaterial,
			Float price,
			Float retailPrice,
			Float purchasePrice,
			Float floorPrice,
			Integer floorPurchaseQuantity,
			Float leaseDeposit,
			Float leasePrice,
			Integer leaseTime,
			Integer returnTime,
			String leaseDescription,
			String leaseAgreement,
			@RequestParam(value = "channels[]", required = false) Integer[] channels,
			String description,
			@RequestParam(value = "photoUrls[]", required = false) String[] photoUrls,
			@RequestParam(value = "goods[]", required = false) Integer[] goods) {
		goodService.update(id, title, secondTitle, keyWorlds, posCategoryId,
				factoryId, goodBrandName, modelNumber, encryptCardWayId,
				signOrderWayId, cardTypes, batteryInfo, shellMaterial, price,
				retailPrice, purchasePrice, floorPrice, floorPurchaseQuantity,
				leaseDeposit, leasePrice, leaseTime, returnTime, leaseDescription,
				leaseAgreement, channels, description, photoUrls, goods);
		return Response.getSuccess("");
	}
	
	@RequestMapping(value = "{id}/in", method = RequestMethod.POST)
	@ResponseBody
	public Response in(@PathVariable Integer id, String data){
		return Response.getSuccess("");
	}



	private void findPage(Integer page, Byte status, String keys, Model model){
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<Good> goods = goodService.findPages(page, Constants.PAGE_SIZE, status, keys);
		model.addAttribute("goods", goods);
	}

    @RequestMapping(value = "category/list", method = RequestMethod.GET)
    public String categoryList(Model model) {
        Collection<PosCategory> categories = posCategoryService.listAll();
        model.addAttribute("categories", categories);
        return "pos/categoryList";
    }

    @RequestMapping(value = "category/{id}/del", method = RequestMethod.GET)
    @ResponseBody
    public Response deleteCategory(@PathVariable Integer id) {
        boolean result = posCategoryService.delete(id);
        if (!result) {
            return Response.getError("该分类已被使用");
        }
        return Response.getSuccess("");
    }

	@RequestMapping(value = "category/{id}/create", method = RequestMethod.GET)
	@ResponseBody
	public Response createCategory(@PathVariable Integer id) {

		boolean result = posCategoryService.delete(id);
		if (!result) {
			return Response.getError("该分类已被使用");
		}
		return Response.getSuccess("");
	}
}
