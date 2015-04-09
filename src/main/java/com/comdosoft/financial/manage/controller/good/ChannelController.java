package com.comdosoft.financial.manage.controller.good;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.*;
import com.comdosoft.financial.manage.service.*;
import com.comdosoft.financial.manage.utils.FileUtil;
import com.comdosoft.financial.manage.utils.FreeMarkerUtils;
import com.comdosoft.financial.manage.utils.page.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateModelException;
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
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("good/channel")
public class ChannelController {

    private Logger LOG = LoggerFactory.getLogger(PosController.class);

    @Value("${path.root}")
    private String rootPath;
    @Value("${path.prefix.channel}")
    private String channelPath;

	@Autowired
	private PayChannelService payChannelService;
    @Autowired
    private SupportTradeTypeService supportTradeTypeService ;
    @Autowired
    private FactoryService factoryService;
    @Autowired
    private CityService cityService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private SessionService sessionService;

	@RequestMapping(value="list",method=RequestMethod.GET)
	public String list(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "good/channel/list";
	}

	@RequestMapping(value="page",method=RequestMethod.POST)
	public String page(Integer page, Byte status, String keys, Model model){
		findPage(page, status, keys, model);
		return "good/channel/pageChannel";
	}

	@RequestMapping(value="{id}/firstUnCheck",method=RequestMethod.GET)
	public String firstUnCheck(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusFirstUnCheck(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/firstCheck",method=RequestMethod.GET)
	public String firstCheck(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusFirstCheck(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/unCheck",method=RequestMethod.GET)
	public String unCheck(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusUnCheck(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/check",method=RequestMethod.GET)
	public String check(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusCheck(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/stop",method=RequestMethod.GET)
	public String stop(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusStop(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/start",method=RequestMethod.GET)
	public String start(@PathVariable Integer id, String source, Model model){
		PayChannel channel = payChannelService.statusWaitingFirstCheck(id);
		model.addAttribute("channel", channel);
		if ("info".equals(source)) {
			return "good/channel/infoStatus";
		}
		return "good/channel/pageRowChannel";
	}

	@RequestMapping(value="{id}/info",method=RequestMethod.GET)
	public String info(@PathVariable Integer id, Model model){
		PayChannel channel = payChannelService.findChannelInfo(id);
		model.addAttribute("channel", channel);
		return "good/channel/info";
	}

	@RequestMapping(value="{id}/profit",method=RequestMethod.GET)
	public String profit(@PathVariable Integer id, Model model){
        PayChannel channel = payChannelService.findChannelInfo(id);
        model.addAttribute("channel", channel);
		return "good/channel/profit";
	}

    @RequestMapping(value="{id}/profit",method=RequestMethod.POST)
    @ResponseBody
    public Response profit(@PathVariable Integer id, Integer baseProfit,
                               @RequestParam(value = "tradeTypeIds[]", required = false) Integer[] tradeTypeIds,
                               @RequestParam(value = "terminalRates[]", required = false) Integer[] terminalRates,
                               @RequestParam(value = "baseRates[]", required = false) Integer[] baseRates,
                               @RequestParam(value = "floorCharges[]", required = false) Float[] floorCharges,
                               @RequestParam(value = "floorProfits[]", required = false) Float[] floorProfits,
                               @RequestParam(value = "topCharges[]", required = false) Float[] topCharges,
                               @RequestParam(value = "topProfits[]", required = false) Float[] topProfits){
        supportTradeTypeService.updateSupportTradeTypes(id, baseProfit,
                tradeTypeIds, terminalRates, baseRates, floorCharges, floorProfits, topCharges, topProfits);
        return Response.getSuccess("");
    }

    @RequestMapping(value="create",method=RequestMethod.GET)
    public String create(Model model) throws TemplateModelException {
        List<Factory> factories = factoryService.findCheckedPayFactories();
        List<City> provinces = cityService.provinces();
        List<DictionaryTradeStandardRate> standardRates = dictionaryService.listAllDictionaryTradeStandardRates();
        List<DictionaryBillingCycle> billingCycles = dictionaryService.listAllDictionaryBillingCycles();
        List<DictionaryTradeType> tradeTypes = dictionaryService.listAllDictionaryTradeTypes();
        List<DictionaryOpenPrivateInfo> openPrivateInfos = dictionaryService.listAllDictionaryOpenPrivateInfos();
        model.addAttribute("factories", factories);
        model.addAttribute("provinces", provinces);
        model.addAttribute("standardRates", standardRates);
        model.addAttribute("billingCycles", billingCycles);
        model.addAttribute("tradeTypes", tradeTypes);
        model.addAttribute("openPrivateInfos", openPrivateInfos);
        model.addAttribute("DictionaryTradeType", FreeMarkerUtils.useClass(DictionaryTradeType.class.getName()));
        return "good/channel/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public Response create(
            String name,
            Integer factoryId,
            Integer supportType,
            @RequestParam(value = "regions[]", required = false) Integer[] regions,
            Boolean supportCancel,
            String standardRatesJson,
            String billingCyclesJson,
            String tradeTypesJson,
            Float openingCost,
            Boolean preliminaryVerify,
            String openingRequirement,
            String openingDatum,
            String openingProtocol,
            String openingRequirementsJson,
            String cancelRequirementsJson,
            String updateRequirementsJson,HttpServletRequest request) throws IOException {
        Customer customer = sessionService.getLoginInfo(request);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> standardRates = objectMapper.readValue(standardRatesJson, List.class);
        List<Map<String, Object>> billingCycles = objectMapper.readValue(billingCyclesJson, List.class);
        List<Map<String, Object>> tradeTypes = objectMapper.readValue(tradeTypesJson, List.class);
        List<Map<String, Object>> openingRequirements = objectMapper.readValue(openingRequirementsJson, List.class);
        List<Map<String, Object>> cancelRequirements = objectMapper.readValue(cancelRequirementsJson, List.class);
        List<Map<String, Object>> updateRequirements = objectMapper.readValue(updateRequirementsJson, List.class);
        payChannelService.create(customer.getId(), customer.getTypes(),name, factoryId, supportType, regions, supportCancel, standardRates, billingCycles, tradeTypes, openingCost, preliminaryVerify, openingRequirement,openingDatum,openingProtocol,
                openingRequirements, cancelRequirements, updateRequirements);
        return Response.getSuccess("");
    }

    @RequestMapping(value="{id}/edit",method=RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model) throws TemplateModelException {
        PayChannel channel = payChannelService.findChannelInfo(id);
        for(Iterator<SupportTradeType> it=channel.getSupportTradeTypes().iterator();it.hasNext();){
            SupportTradeType supportTradeType = (SupportTradeType)it.next();
            if(supportTradeType.getTradeType() == SupportTradeType.TYPE_TRADE) {
                it.remove();
            }
        }
        model.addAttribute("channel", channel);
        List<Factory> factories = factoryService.findCheckedPayFactories();
        List<City> provinces = cityService.provinces();
        List<DictionaryTradeStandardRate> standardRates = dictionaryService.listAllDictionaryTradeStandardRates();
        List<DictionaryBillingCycle> billingCycles = dictionaryService.listAllDictionaryBillingCycles();
        List<DictionaryTradeType> tradeTypes = dictionaryService.listAllDictionaryTradeTypes();
        List<DictionaryOpenPrivateInfo> openPrivateInfos = dictionaryService.listAllDictionaryOpenPrivateInfos();
        model.addAttribute("factories", factories);
        model.addAttribute("provinces", provinces);
        model.addAttribute("standardRates", standardRates);
        model.addAttribute("billingCycles", billingCycles);
        model.addAttribute("tradeTypes", tradeTypes);
        model.addAttribute("openPrivateInfos", openPrivateInfos);
        model.addAttribute("DictionaryTradeType", FreeMarkerUtils.useClass(DictionaryTradeType.class.getName()));
        return "good/channel/create";
    }

    @RequestMapping(value = "{id}/edit", method = RequestMethod.POST)
    @ResponseBody
    public Response edit(
            @PathVariable Integer id,
            String name,
            Integer factoryId,
            Integer supportType,
            @RequestParam(value = "regions[]", required = false) Integer[] regions,
            Boolean supportCancel,
            String standardRatesJson,
            String billingCyclesJson,
            String tradeTypesJson,
            Float openingCost,
            Boolean preliminaryVerify,
            String openingRequirement,
            String openingDatum,
            String openingProtocol,
            String openingRequirementsJson,
            String cancelRequirementsJson,
            String updateRequirementsJson
            ) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> standardRates = objectMapper.readValue(standardRatesJson, List.class);
        List<Map<String, Object>> billingCycles = objectMapper.readValue(billingCyclesJson, List.class);
        List<Map<String, Object>> tradeTypes = objectMapper.readValue(tradeTypesJson, List.class);
        List<Map<String, Object>> openingRequirements = objectMapper.readValue(openingRequirementsJson, List.class);
        List<Map<String, Object>> cancelRequirements = objectMapper.readValue(cancelRequirementsJson, List.class);
        List<Map<String, Object>> updateRequirements = objectMapper.readValue(updateRequirementsJson, List.class);
        payChannelService.update(id, name, factoryId, supportType, regions, supportCancel, standardRates, billingCycles, tradeTypes, openingCost, preliminaryVerify, openingRequirement,openingDatum,openingProtocol,
                openingRequirements, cancelRequirements, updateRequirements);
        return Response.getSuccess("");
    }

    private void findPage(Integer page, Byte status, String keys, Model model){
		if (page == null) {
			page = 1;
		}
		if (status != null && status == 0) {
			status = null;
		}
		Page<PayChannel> channels = payChannelService.findPages(null, page, status, keys);
		model.addAttribute("channels", channels);
	}

    @RequestMapping(value="uploadFile",method=RequestMethod.POST)
    @ResponseBody
    public Response uploadImg(MultipartFile file){
        String suffix = file.getOriginalFilename().substring
                (file.getOriginalFilename().lastIndexOf("."));
        String fileName = channelPath+ FileUtil.getPathFileName()+suffix;
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
}
