package com.comdosoft.financial.manage.controller.good;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
import com.comdosoft.financial.manage.domain.zhangfu.GoodComment;
import com.comdosoft.financial.manage.domain.zhangfu.GoodDetailPictures;
import com.comdosoft.financial.manage.domain.zhangfu.GoodMobilePictures;
import com.comdosoft.financial.manage.domain.zhangfu.PayChannel;
import com.comdosoft.financial.manage.domain.zhangfu.PosCategory;
import com.comdosoft.financial.manage.domain.zhangfu.Terminal;
import com.comdosoft.financial.manage.service.DictionaryService;
import com.comdosoft.financial.manage.service.FactoryService;
import com.comdosoft.financial.manage.service.GoodCommentService;
import com.comdosoft.financial.manage.service.GoodService;
import com.comdosoft.financial.manage.service.PayChannelService;
import com.comdosoft.financial.manage.service.PosCategoryService;
import com.comdosoft.financial.manage.service.SessionService;
import com.comdosoft.financial.manage.service.TerminalService;
import com.comdosoft.financial.manage.utils.HttpFile;
import com.comdosoft.financial.manage.utils.page.Page;

@Controller
@RequestMapping("/good/pos")
public class PosController {
	
	private Logger LOG = LoggerFactory.getLogger(PosController.class);
	
	@Value("${path.root}")
	private String rootPath;
    @Value("${path.prefix.pos}")
    private String posPath;
    @Value("${sysPosPath}")
    private String sysPosPath ;
    @Value("${filePath}")
    private String filePath ;
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
    private TerminalService terminalService;
	@Autowired
	private SessionService sessionService;
	
	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "good/pos/list";
	}
	
	@RequestMapping(value="page",method=RequestMethod.POST)
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

    @RequestMapping(value="{id}/topHome",method=RequestMethod.GET)
    public String topHome(@PathVariable Integer id, Model model){
        Good good = goodService.topHome(id);
        model.addAttribute("good", good);
        return "good/pos/pageRowPos";
    }
	
	@RequestMapping(value="{id}/unTopHome",method=RequestMethod.GET)
	public String unTopHome(@PathVariable Integer id, Model model){
		Good good = goodService.unTopHome(id);
		model.addAttribute("good", good);
		return "good/pos/pageRowPos";
	}	
	
	@RequestMapping(value="{id}/edit",method=RequestMethod.GET)
	public String edit(@PathVariable Integer id, Model model){
		Good good = goodService.findGoodInfo(id);
		Collection<PosCategory> posCategories = posCategoryService.listOrderAll();
		List<Factory> factories = factoryService.findCheckedManufacturers();
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

	@RequestMapping(value="create",method=RequestMethod.GET)
	public String create(Model model){
		Collection<PosCategory> posCategories = posCategoryService.listOrderAll();
		List<Factory> factories = factoryService.findCheckedManufacturers();
		List<DictionarySignOrderWay> signOrderWays = dictionaryService.listAllDictionarySignOrderWays();
		List<DictionaryCardType> cardTypes = dictionaryService.listAllDictionaryCardTypes();
		List<DictionaryEncryptCardWay> encryptCardWays = dictionaryService.listAllDictionaryEncryptCardWays();
		model.addAttribute("posCategories", posCategories);
		model.addAttribute("factories", factories);
		model.addAttribute("signOrderWays", signOrderWays);
		model.addAttribute("cardTypes", cardTypes);
		model.addAttribute("encryptCardWays", encryptCardWays);

		return "good/pos/create";
	}
	
	@RequestMapping(value="searchChannel",method=RequestMethod.POST)
	public String searchChannel(String name, Model model){
		if (name != null && name.length() > 0) {
			List<PayChannel> channels = payChannelService.findCheckedChannelsLikeName(name);
			model.addAttribute("channels", channels);
		}
		return "good/pos/searchChannel";
	}
	
	@RequestMapping(value="searchGood",method=RequestMethod.POST)
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
//        String filePath = posPath+FileUtil.getPathFileName();
//        String osPath = rootPath + filePath;
//		String fileName = osPath+"/o.jpg";
//        String urlPath = filePath+"/o.jpg";
//		try {
//			File osFile = new File(fileName);
//			if (!osFile.getParentFile().exists()) {
//				osFile.getParentFile().mkdirs();
//			}
//			file.transferTo(osFile);
//            //大图
//            Thumbnails.of(fileName).size(400, 400)
//                    .toFile(osPath+"/b.jpg");
//            
//            Thumbnails.of(fileName).size(55, 55)
//                    .toFile(osPath+"/s.jpg");
//		} catch (Exception e) {
//			LOG.error("", e);
//			return Response.getError("上传失败！");
//		}
//		return Response.getSuccess(urlPath);
	  // HttpFile hf=new HttpFile();
        String path = sysPosPath ;
        String[] suffixes = {"BMP","JPG","JPEG","PNG","GIF"};
        String name = file.getOriginalFilename();
        int length = name.length();
        boolean flg = false;
        for(String suffix : suffixes){
            if(name.substring(name.indexOf(".")+1,length).equalsIgnoreCase(suffix)){
                flg = true;
                break;
            }
        }
        if(!flg){
            return Response.getError("上传文件类型不正确，只能是图片格式，请重新上传");
        }
        if(file.getSize() > 2 * 1024 * 1024){
            return Response.getError("上传文件超过2MB，请重新上传");
        }
	    String result=HttpFile.uploadPos(file,sysPosPath);
	    if(result.split("/").length>1){
	        return Response.getSuccess(filePath+result);
	    }else{
	        return Response.getError(result);
	    }
	   
	    
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
		Page<Good> goods = goodService.findPages(null, page, status, keys);
		model.addAttribute("goods", goods);
	}

    @RequestMapping(value = "category/list", method = RequestMethod.GET)
    public String categoryList(Model model) {
        List<PosCategory> categories = posCategoryService.listAll();
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
    
    @RequestMapping(value = "category/{id}/modify", method = RequestMethod.POST)
    @ResponseBody
    public Response modifyCategory(@PathVariable Integer id,String name) {
    	PosCategory category = posCategoryService.modify(id,name);
        return Response.getSuccess(category);
    }

	@RequestMapping(value = "category/{parentId}/create", method = RequestMethod.POST)
	@ResponseBody
	public Response createCategory(@PathVariable Integer parentId, String name) {
		PosCategory posCategory = posCategoryService.create(parentId, name);
		Response response = Response.getSuccess(posCategory);
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

    @RequestMapping(value = "{id}/importTerminal", method = RequestMethod.POST)
    @ResponseBody
    public Response importTerminal(@PathVariable Integer id, String data, Model model) {
        List<String> errorCodes = terminalService.importTerminal(id, data);
        if(errorCodes.size() > 0 ){
            String error = "导入失败，以下终端号重复：\n"
                    + errorCodes.toString();
            return Response.getError(error);
        }
        return Response.getSuccess("");
    }
    
    @RequestMapping(value="{id}/imgInfo",method=RequestMethod.GET)
	public String imgInfo(@PathVariable Integer id, Model model){
		List<GoodDetailPictures> goodImgs = goodService.findGoodImg(id);
		for(GoodDetailPictures g:goodImgs){
			g.setUrlPath(filePath+g.getUrlPath());
		}
		if(goodImgs.isEmpty()){
			goodImgs = null;
		}
		model.addAttribute("goodImgs", goodImgs);
		model.addAttribute("goodId", id);
		return "good/pos/imgInfo";
	}
    
    @RequestMapping(value="uploadPosImg",method=RequestMethod.POST)
    @ResponseBody
    public Response uploadImg(MultipartFile file,Integer goodId,HttpServletRequest request){
        String path = sysPosPath ;
    	String[] suffixes = {"BMP","JPG","JPEG","PNG","GIF"};
    	String name = file.getOriginalFilename();
    	int length = name.length();
    	boolean flg = false;
    	for(String suffix : suffixes){
    		if(name.substring(name.indexOf(".")+1,length).equalsIgnoreCase(suffix)){
    			flg = true;
    			break;
    		}
    	}
    	if(!flg){
			return Response.getError("上传文件类型不正确，只能是图片格式，请重新上传");
		}
        if(file.getSize() > 2 * 1024 * 1024){
            return Response.getError("上传文件超过2M，请重新上传");
        }
    	String result = HttpFile.upload(file, path);
    	if(result.indexOf("失败")>0){
    		LOG.info("文件上传失败");
    		return Response.getError("上传失败");
    	}
    	//保存数据库
    	int id = goodService.saveGoodImg(result,goodId);
    	List<Object> list = new ArrayList<Object>();
    	list.add(filePath+result);
    	list.add(id);
        return Response.getSuccess(list);
    }
    
    @RequestMapping(value="delete",method=RequestMethod.POST)
    @ResponseBody
	public Response deleteImg(Integer id, Model model){
    	try {
			HttpFile.postDel(goodService.getGoodImg(id).getUrlPath());
		} catch (IOException e) {
			e.printStackTrace();
			return Response.getError("删除失败");
		}
    	goodService.deleteImg(id);
		return Response.getSuccess("删除成功");
	}
    
    @RequestMapping(value="{id}/store",method=RequestMethod.GET)
	public String store(@PathVariable Integer id, Model model){
		Good good = goodService.findGoodInfo(id);
		model.addAttribute("good", good);
		List<Terminal> terminalList = goodService.findTerminalList(id);
		model.addAttribute("terminalList", terminalList);
		return "good/pos/store";
	}
    @RequestMapping(value="deleteTerminal",method=RequestMethod.POST)
    @ResponseBody
    public Response deleteTerminal(String terminalNum,Integer goodId){
    	goodService.deleteTerminal(terminalNum,goodId);
    	return Response.getSuccess("ok");
    	
    }
    @SuppressWarnings("unchecked")
	@RequestMapping(value="{id}/removeStore",method=RequestMethod.POST)
    @ResponseBody
    public Response removeStore(@PathVariable Integer id,String data, Model model){
    	String[] terminals = data.split(",");
    	Map<String,Object> invalids = terminalService.isTerminalInvalid(terminals);
    	StringBuilder error = new StringBuilder("终端号为");
    	if(!invalids.isEmpty()){
    		List<String> customerIdExist = (List<String>)invalids.get("customerIdExist");
    		List<String> agentIdExist = (List<String>)invalids.get("agentIdExist");
    		List<String> IsReturnCsDepots = (List<String>)invalids.get("IsReturnCsDepots");
    		List<String> notExist = (List<String>)invalids.get("notExist");
    		List<String> hasOrderId = (List<String>)invalids.get("hasOrderId");
    		if(!notExist.isEmpty()){
    			error.append(notExist.toString());
    			return Response.getError(error.toString().replaceAll("\\,", "\\，").replaceAll("\\[|\\]", ""));
    		}else{
    			if(!customerIdExist.isEmpty()){
        			error.append(customerIdExist.toString());
        		}
    			if(!agentIdExist.isEmpty()){
        			error.append(agentIdExist.toString());
        		}
    			if(!IsReturnCsDepots.isEmpty()){
        			error.append(IsReturnCsDepots.toString());
        		}
    			if(!hasOrderId.isEmpty()){
        			error.append(hasOrderId.toString());
        		}
    			if("终端号为".equals(error)){
    				terminalService.removeStorage(id,terminals);
    	    	    return Response.getSuccess("清除库存成功");
    			}else{
    				return Response.getError(error.toString().replaceAll("\\]\\[", "\\，").replaceAll("\\,", "\\，").replaceAll("\\[|\\]", ""));
    			}
    		}
    	}	
    	return Response.getSuccess("清除库存成功");
    }
    
    @RequestMapping(value="{id}/cellPhoneImgInfo",method=RequestMethod.GET)
	public String cellPhoneImgInfo(@PathVariable Integer id, Model model){
		List<GoodMobilePictures> goodImgs = goodService.findCellPhoneGoodImg(id);
		for(GoodMobilePictures g:goodImgs){
			g.setUrlPath(filePath+g.getUrlPath());
		}
		if(goodImgs.isEmpty()){
			goodImgs = null;
		}
		model.addAttribute("goodImgs", goodImgs);
		model.addAttribute("goodId", id);
		return "good/pos/cellPhoneImgInfo";
	}
    
    @RequestMapping(value="uploadMobilePosImg",method=RequestMethod.POST)
    @ResponseBody
    public Response uploadMobilePosImg(MultipartFile file,Integer goodId,HttpServletRequest request){
        String path = sysPosPath ;
    	String[] suffixes = {"BMP","JPG","JPEG","PNG","GIF"};
    	String name = file.getOriginalFilename();
    	int length = name.length();
    	boolean flg = false;
    	for(String suffix : suffixes){
    		if(name.substring(name.indexOf(".")+1,length).equalsIgnoreCase(suffix)){
    			flg = true;
    			break;
    		}
    	}
    	if(!flg){
			return Response.getError("上传文件类型不正确，只能是图片格式，请重新上传");
		}
        if(file.getSize() > 2 * 1024 * 1024){
            return Response.getError("上传文件超过2M，请重新上传");
        }
    	String result = HttpFile.upload(file, path);
    	if(result.indexOf("失败")>0){
    		LOG.info("文件上传失败");
    		return Response.getError("上传失败");
    	}
    	//保存数据库
    	int id = goodService.saveMobileGoodImg(result,goodId);
    	List<Object> list = new ArrayList<Object>();
    	list.add(filePath+result);
    	list.add(id);
        return Response.getSuccess(list);
    }
    
    @RequestMapping(value="deleteMobile",method=RequestMethod.POST)
    @ResponseBody
	public Response deleteMobile(Integer id, Model model){
    	try {
			HttpFile.postDel(goodService.getMobileGoodImg(id).getUrlPath());
		} catch (IOException e) {
			e.printStackTrace();
			return Response.getError("删除失败");
		}
    	goodService.deleteMobileImg(id);
		return Response.getSuccess("删除成功");
	}
}
