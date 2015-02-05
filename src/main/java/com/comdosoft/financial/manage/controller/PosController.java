package com.comdosoft.financial.manage.controller;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.manage.domain.Response;
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
	public String info(@PathVariable Long id, Model model){
		Good good = goodService.findGoodInfo(id);
		model.addAttribute("good", good);
		return "pos/info";
	}
	
	@RequestMapping(value="{id}/firstUnCheck",method=RequestMethod.GET)
	public String firstUnCheck(@PathVariable Long id, String source, Model model){
		Good good = goodService.statusFirstUnCheck(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "pos/infoStatus";
		}
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/firstCheck",method=RequestMethod.GET)
	public String firstCheck(@PathVariable Long id, String source, Model model){
		Good good = goodService.statusFirstCheck(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "pos/infoStatus";
		}
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/unCheck",method=RequestMethod.GET)
	public String unCheck(@PathVariable Long id, String source, Model model){
		Good good = goodService.statusUnCheck(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "pos/infoStatus";
		}
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/check",method=RequestMethod.GET)
	public String check(@PathVariable Long id, String source, Boolean isThird, Model model){
		Good good = goodService.statusCheck(id, isThird);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "pos/infoStatus";
		}
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/stop",method=RequestMethod.GET)
	public String stop(@PathVariable Long id, String source, Model model){
		Good good = goodService.statusStop(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "pos/infoStatus";
		}
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/start",method=RequestMethod.GET)
	public String start(@PathVariable Long id, String source, Model model){
		Good good = goodService.statusWaitingFirstCheck(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "pos/infoStatus";
		}
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/publish",method=RequestMethod.GET)
	public String publish(@PathVariable Long id, Model model){
		Good good = goodService.publish(id);
		model.addAttribute("good", good);
		return "pos/pageRowPos";
	}
	
	
	@RequestMapping(value="{id}/unPublish",method=RequestMethod.GET)
	public String unPublish(@PathVariable Long id, Model model){
		Good good = goodService.unPublish(id);
		model.addAttribute("good", good);
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/lease",method=RequestMethod.GET)
	public String lease(@PathVariable Long id, Model model){
		Good good = goodService.lease(id);
		model.addAttribute("good", good);
		return "pos/pageRowPos";
	}
	
	
	@RequestMapping(value="{id}/unLease",method=RequestMethod.GET)
	public String unLease(@PathVariable Long id, Model model){
		Good good = goodService.unLease(id);
		model.addAttribute("good", good);
		return "pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/purchase",method=RequestMethod.GET)
	public String purchase(@PathVariable Long id, Model model){
		Good good = goodService.purchase(id);
		model.addAttribute("good", good);
		return "pos/pageRowPos";
	}
	
	
	@RequestMapping(value="{id}/unPurchase",method=RequestMethod.GET)
	public String unPurchase(@PathVariable Long id, Model model){
		Good good = goodService.unPurchase(id);
		model.addAttribute("good", good);
		return "pos/pageRowPos";
	}	
	
	@RequestMapping(value="{id}/edit",method=RequestMethod.GET)
	public String edit(@PathVariable Long id, Model model){
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
	
	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(String title, 
			String secondTitle, 
			String keyWorlds,
			Integer posCategoryId, 
			Integer factoryId,
			String goodBrandName, 
			String modelNumber, 
			Integer encryptCardWayId,
			Integer signOrderWayId, 
			List<Integer> cardTypes,
			String batteryInfo, 
			String shellMaterial,
			Float price,
			Float retailPrice, 
			Float purchasePrice, 
			Float floorPrice,
			Integer floorPurchaseQuantity, 
			Float leaseDeposit, 
			Integer leaseTime, 
			Integer returnTime, 
			String leaseDescription, 
			String leaseAgreement,
			List<Integer> channels,
			String description,
			List<Integer> photos,
			List<Integer> goods){
	   Good good = new Good();
	   
	   //基础信息
	   good.setTitle(title);
	   good.setSecondTitle(secondTitle);
	   good.setKeyWorlds(keyWorlds);
	   good.setPosCategoryId(posCategoryId);
	   good.setFactoryId(factoryId);
	   //设置品牌TODO
	   good.setModelNumber(modelNumber);
	   good.setEncryptCardWayId(encryptCardWayId);
	   good.setSignOrderWayId(signOrderWayId);
	   //设置卡类型 cardTypes   TODO
	   good.setBatteryInfo(batteryInfo);
	   good.setShellMaterial(shellMaterial);
	   
	   //价格信息
	   if (price != null) {
		   good.setPrice((int)(price*100));
	   }
	   if (retailPrice != null) {
		   good.setRetailPrice((int)(retailPrice*100));
	   }
	   
	   //批购信息
	   if (purchasePrice != null) {
		   good.setPurchasePrice((int)(purchasePrice*100));
	   }
	   if (floorPrice != null) {
		   good.setFloorPrice((int)(floorPrice*100));
	   }
	   good.setFloorPurchaseQuantity(floorPurchaseQuantity);
	   
	   //租赁设置
	   if (leaseDeposit != null) {
		   good.setLeaseDeposit((int)(leaseDeposit*100));
	   }
	   good.setLeaseTime(leaseTime);
	   good.setReturnTime(returnTime);
	   good.setLeaseDescription(leaseDescription);
	   good.setLeaseAgreement(leaseAgreement);
	   
	   //支付通道
	   //设置支付通道 channels   TODO

	   //其他
	   good.setDescription(description);
	   //设置图片 photos   TODO
	   //设置关联商品 goods   TODO
	   
	   
	   good.setHasPurchase(false);
	   good.setHasLease(false);
	   good.setTotalScore(0);
	   good.setTotalComment(0);
	   good.setQuantity(0);
	   good.setCreatedUserId(1);//TODO
	   good.setCreatedUserType((byte)0);//TODO
	   good.setCreatedAt(new Date());
	   good.setUpdatedAt(new Date());
//		hasPurchase : Boolean
//		hasLease : Boolean
//		totalScore : Integer
//		totalComment : Integer
//		status : Byte
//		isPublished : Boolean
//		createdUserId : Integer
//		createdUserType : Byte
//		createdAt : Date
//		updatedAt : Date
//		volumeNumber : Integer  ??
//		purchaseNumber : Integer ??
//		quantity : Integer

//		
//		title : String
//		secondTitle : String
//		keyWorlds : String
//		factoryId : Integer
//		posCategoryId : Integer
//		modelNumber : String
//		encryptCardWayId : Integer
//		signOrderWayId : Integer
//		batteryInfo : String
//		shellMaterial : String
//		retailPrice : Integer
//		purchasePrice : Integer
//		floorPrice : Integer
//		floorPurchaseQuantity : Integer
//		price : Integer
//		leasePrice : Integer
//		leaseTime : Integer
//		goodBrandsId : Integer
//		leaseDeposit : Integer
//		returnTime : Integer
//		description : String
//		leaseDescription : String
//		leaseAgreement : String
		return "pos/list";

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
	

}
