package com.comdosoft.financial.manage.controller.good;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.*;
import com.comdosoft.financial.manage.service.*;
import com.comdosoft.financial.manage.utils.FileUtil;
import com.comdosoft.financial.manage.utils.page.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/good/pos")
public class PosController {
	
	private Logger LOG = LoggerFactory.getLogger(PosController.class);
	
	@Value("${path.root}")
	private String rootPath;
    @Value("${path.prefix.pos}")
    private String posPath;
	
	@Autowired
	private GoodService goodService ;
	@Autowired
	private PosCategoryService posCategoryService;
	@Autowired
	private FactoryService factoryService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private PayChannelService payChannelService;
    @Autowired
    private GoodCommentService goodCommentService;
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "good/pos/list";
	}
	
	@RequestMapping(value="page",method=RequestMethod.GET)
	public String page(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "good/pos/pagePos";
	}
	
	@RequestMapping(value="{id}/info",method=RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model){
		Good good = goodService.findGoodInfo(id);
		model.addAttribute("good", good);
		return "good/pos/info";
	}
	
	@RequestMapping(value="{id}/firstUnCheck",method=RequestMethod.GET)
	public String firstUnCheck(@PathVariable Integer id, String source, Model model){
		Good good = goodService.statusFirstUnCheck(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "good/pos/infoStatus";
		}
		return "good/pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/firstCheck",method=RequestMethod.GET)
	public String firstCheck(@PathVariable Integer id, String source, Model model){
		Good good = goodService.statusFirstCheck(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "good/pos/infoStatus";
		}
		return "good/pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/unCheck",method=RequestMethod.GET)
	public String unCheck(@PathVariable Integer id, String source, Model model){
		Good good = goodService.statusUnCheck(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "good/pos/infoStatus";
		}
		return "good/pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/check",method=RequestMethod.GET)
	public String check(@PathVariable Integer id, String source, Boolean isThird, Model model){
		Good good = goodService.statusCheck(id, isThird);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "good/pos/infoStatus";
		}
		return "good/pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/stop",method=RequestMethod.GET)
	public String stop(@PathVariable Integer id, String source, Model model){
		Good good = goodService.statusStop(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "good/pos/infoStatus";
		}
		return "good/pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/start",method=RequestMethod.GET)
	public String start(@PathVariable Integer id, String source, Model model){
		Good good = goodService.statusWaitingFirstCheck(id);
		model.addAttribute("good", good);
		if ("info".equals(source)) {
			return "good/pos/infoStatus";
		}
		return "good/pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/publish",method=RequestMethod.GET)
	public String publish(@PathVariable Integer id, Model model){
		Good good = goodService.publish(id);
		model.addAttribute("good", good);
		return "good/pos/pageRowPos";
	}
	
	
	@RequestMapping(value="{id}/unPublish",method=RequestMethod.GET)
	public String unPublish(@PathVariable Integer id, Model model){
		Good good = goodService.unPublish(id);
		model.addAttribute("good", good);
		return "good/pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/lease",method=RequestMethod.GET)
	public String lease(@PathVariable Integer id, Model model){
		Good good = goodService.lease(id);
		model.addAttribute("good", good);
		return "good/pos/pageRowPos";
	}
	
	
	@RequestMapping(value="{id}/unLease",method=RequestMethod.GET)
	public String unLease(@PathVariable Integer id, Model model){
		Good good = goodService.unLease(id);
		model.addAttribute("good", good);
		return "good/pos/pageRowPos";
	}
	
	@RequestMapping(value="{id}/purchase",method=RequestMethod.GET)
	public String purchase(@PathVariable Integer id, Model model){
		Good good = goodService.purchase(id);
		model.addAttribute("good", good);
		return "good/pos/pageRowPos";
	}
	
	
	@RequestMapping(value="{id}/unPurchase",method=RequestMethod.GET)
	public String unPurchase(@PathVariable Integer id, Model model){
		Good good = goodService.unPurchase(id);
		model.addAttribute("good", good);
		return "good/pos/pageRowPos";
	}	
	
	@RequestMapping(value="{id}/edit",method=RequestMethod.GET)
	public String edit(@PathVariable Integer id, Model model){
		Good good = goodService.findGoodInfo(id);
		Collection<PosCategory> posCategories = posCategoryService.listAll();
		List<Factory> factories = factoryService.findCheckedFactories();
		List<DictionarySignOrderWay> signOrderWays = dictionaryService.listAllDictionarySignOrderWays();
		List<DictionaryCardType> cardTypes = dictionaryService.listAllDictionaryCardTypes();
		List<DictionaryEncryptCardWay> encryptCardWays = dictionaryService.listAllDictionaryEncryptCardWays();
		model.addAttribute("good", good);
		model.addAttribute("posCategories", posCategories);
		model.addAttribute("factories", factories);
		model.addAttribute("signOrderWays", signOrderWays);
		model.addAttribute("cardTypes", cardTypes);
		model.addAttribute("encryptCardWays", encryptCardWays);

		return "good/pos/create";
	}
	
	@RequestMapping(value="searchChannel",method=RequestMethod.GET)
	public String searchChannel(String name, Model model){
		if (name != null && name.length() > 0) {
			List<PayChannel> channels = payChannelService.findCheckedChannelsLikeName(name);
			model.addAttribute("channels", channels);
		}
		return "good/pos/searchChannel";
	}
	
	@RequestMapping(value="searchGood",method=RequestMethod.GET)
	public String searchGood(String name, Model model){
		if (name != null && name.length() > 0) {
			List<Good> goods = goodService.findCheckedGoodsLikeKey(name);
			model.addAttribute("goods", goods);
		}
		return "good/pos/searchGood";
	}
	
	@RequestMapping(value="uploadImg",method=RequestMethod.POST)
	@ResponseBody
	public Response uploadImg(MultipartFile file){
		String fileName = posPath+FileUtil.getPathFileName()+".jpg";
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
	
	@RequestMapping(value = "{id}/edit", method = RequestMethod.POST)
	@ResponseBody
	public Response edit(
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
		Page<Good> goods = goodService.findPages(page, status, keys);
		model.addAttribute("goods", goods);
	}

    @RequestMapping(value = "category/list", method = RequestMethod.GET)
    public String categoryList(Model model) {
        Collection<PosCategory> categories = posCategoryService.listAll();
        model.addAttribute("categories", categories);
        return "good/pos/categoryList";
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

	@RequestMapping(value = "category/{parentId}/create", method = RequestMethod.POST)
	@ResponseBody
	public Response createCategory(@PathVariable Integer parentId, String name) {
		PosCategory posCategory = posCategoryService.create(parentId, name);
		Response response = Response.getSuccess("");
        response.setResult(posCategory);
        return  response;
	}

    @RequestMapping(value = "waitComment/list", method = RequestMethod.GET)
    public String waitCommentList(Integer page, Model model) {
        if (page == null) {
            page = 1;
        }
        Page<GoodComment> comments = goodCommentService.findWaitingPages(page);
        model.addAttribute("comments", comments);
        return  "good/pos/waitCommentList";
    }

    @RequestMapping(value = "waitComment/page", method = RequestMethod.GET)
    public String waitCommentPage(Integer page, Model model) {
        if (page == null) {
            page = 1;
        }
        Page<GoodComment> comments = goodCommentService.findWaitingPages(page);
        model.addAttribute("comments", comments);
        return "good/pos/waitCommentPage";
    }

    @RequestMapping(value = "comment/{id}/check", method = RequestMethod.GET)
    @ResponseBody
    public Response commentCheck(@PathVariable Integer id, HttpServletRequest request) {
        Customer customer = sessionService.getLoginInfo(request);
        goodCommentService.check(customer.getId(), id);
        return  Response.getSuccess("");
    }

    @RequestMapping(value = "comment/{id}/delete", method = RequestMethod.GET)
    @ResponseBody
    public Response commentDelete(@PathVariable Integer id, HttpServletRequest request) {
        goodCommentService.delete(id);
        return  Response.getSuccess("");
    }

    @RequestMapping(value = "{id}/comments", method = RequestMethod.GET)
    public String waitCommentList(@PathVariable Integer id, Integer page, Model model) {
        if (page == null) {
            page = 1;
        }
        Good good = goodService.findGood(id);
        Page<GoodComment> comments = goodCommentService.findCommentPages(id, page);
        model.addAttribute("comments", comments);
        model.addAttribute("good", good);
        return  "good/pos/commentList";
    }

    @RequestMapping(value = "{id}/comments/page", method = RequestMethod.GET)
    public String commentPage(@PathVariable Integer id, Integer page, Model model) {
        if (page == null) {
            page = 1;
        }
        Good good = goodService.findGood(id);
        Page<GoodComment> comments = goodCommentService.findCommentPages(id, page);
        model.addAttribute("comments", comments);
        model.addAttribute("good", good);
        return  "good/pos/commentPage";
    }

	@RequestMapping(value = "comment/create", method = RequestMethod.POST)
	@ResponseBody
	public Response createComment( Integer goodId, Integer score, String content, HttpServletRequest request) {
		Customer customer = sessionService.getLoginInfo(request);
		goodCommentService.create(goodId, customer.getId(), score, content);
		return  Response.getSuccess("");
	}


}
