package com.comdosoft.financial.manage.controller.system;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.*;
import com.comdosoft.financial.manage.service.*;
import com.comdosoft.financial.manage.utils.HttpFile;
import com.comdosoft.financial.manage.utils.page.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by junxi.qu on 2015/3/10.
 */
@Controller
@RequestMapping("/system/agent")
public class AgentController {

    private Logger LOG = LoggerFactory.getLogger(AgentController.class);

    @Value("${agent}")
    private String agentPath;
    @Value("${filePath}")
    private String filePath;

    @Autowired
    private AgentService agentService;
    @Autowired
    private CityService cityService;
    @Autowired
    private PayChannelService payChannelService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private FactoryService factoryService;
    
    @RequestMapping(value="list",method= RequestMethod.GET)
    public String list(Integer page, Byte status, String keys, Model model){
        findPage(page, status, keys, model);
        return "system/agent_list";
    }

    @RequestMapping(value="page",method=RequestMethod.POST)
    public String page(Integer page, Byte status, String keys, Model model){
        findPage(page, status, keys, model);
        return "system/agent_list_page";
    }

    @RequestMapping(value="{id}/info",method=RequestMethod.GET)
    public String info(@PathVariable Integer id, Model model){
        Agent agent = agentService.findAgentInfo(id);
        model.addAttribute("agent", agent);
        if(agent.getCustomer().getCityId() != null){
           City city = cityService.selectCityAndParent(agent.getCustomer().getCityId());
            model.addAttribute("city", city);
        }
        return "system/agent_info";
    }

    @RequestMapping(value="{id}/edit",method=RequestMethod.GET)
    public String edit(@PathVariable Integer id, Model model){
        Agent agent = agentService.findAgentInfo(id);
        List<City> provinces = cityService.provinces();
        model.addAttribute("agent", agent);
        model.addAttribute("provinces", provinces);
        return "system/agent_create";
    }
    
    @RequestMapping(value="/findCustomerByName",method=RequestMethod.POST)
    @ResponseBody
    public Response findCustomerByName(Integer id,String username,Integer factory_id){
    	Boolean isTrue = false;
    	if(null !=id){
    		Agent agent = agentService.findAgentInfo(id);
        	Integer customer_id = agent.getCustomerId();
        	 isTrue = customerService.findCustomerByUserName(username,customer_id);
    	}else if(null != factory_id){
    		Factory factory = factoryService.findFactoryInfo(factory_id);
    		Integer customer_id = factory.getCustomerId();
    		 isTrue = customerService.findCustomerByUserName(username,customer_id);
    	} else{
    		 isTrue = customerService.findCustomerByUserName(username,null);
    	}
    	if(isTrue){
    		return Response.getError("此ID已存在，无法添加");
    	}else{
    		return Response.getSuccess("");
    	}
    }

    @RequestMapping(value="create",method=RequestMethod.GET)
    public String create( Model model){
        List<City> provinces = cityService.provinces();
        model.addAttribute("provinces", provinces);
        return "system/agent_create";
    }

    @RequestMapping(value="{id}/edit",method=RequestMethod.POST)
    @ResponseBody
    public Response edit(@PathVariable Integer id,
                       Integer types, String name, String cardId, String companyName,
                       String businessLicense, String phone, String email, Integer cityId,
                       String address, String username, String password, Byte accountType,
                       String cardIdPhotoPath, String licenseNoPicPath){
        boolean result = agentService.update(id, types, name, cardId, companyName,
                    businessLicense, phone, email, cityId, address,
                    username, password, accountType, cardIdPhotoPath, licenseNoPicPath);
        if (!result){
            return Response.getError("登录ID已存在");
        }
        return Response.getSuccess(null);
    }

    @RequestMapping(value="create",method=RequestMethod.POST)
    @ResponseBody
    public Response create( Integer types, String name, String cardId, String companyName,
                          String businessLicense, String phone, String email, Integer cityId, String address,
                          String username, String password, Byte accountType,
                          String cardIdPhotoPath, String licenseNoPicPath){
        boolean result = agentService.create(types, name, cardId, companyName,
                businessLicense, phone, email, cityId, address,
                username, password, accountType,cardIdPhotoPath, licenseNoPicPath);
        if (!result){
            return Response.getError("登录ID已存在");
        }
        return Response.getSuccess(null);
    }

    @RequestMapping(value="{id}/firstUnCheck",method=RequestMethod.GET)
    public String firstUnCheck(@PathVariable Integer id, Model model){
        Agent agent = agentService.statusFirstUnCheck(id);
        model.addAttribute("agent", agent);
        return "system/agent_list_page_row";
    }

    @RequestMapping(value="{id}/firstCheck",method=RequestMethod.GET)
    public String firstCheck(@PathVariable Integer id, Model model){
        Agent agent = agentService.statusFirstCheck(id);
        model.addAttribute("agent", agent);
        return "system/agent_list_page_row";
    }

    @RequestMapping(value="{id}/unCheck",method=RequestMethod.GET)
    public String unCheck(@PathVariable Integer id, Model model){
        Agent agent = agentService.statusUnCheck(id);
        model.addAttribute("agent", agent);
        return "system/agent_list_page_row";
    }

    @RequestMapping(value="{id}/check",method=RequestMethod.GET)
    public String check(@PathVariable Integer id, Model model){
        Agent agent = agentService.statusCheck(id);
        model.addAttribute("agent", agent);
        return "system/agent_list_page_row";
    }

    @RequestMapping(value="{id}/stop",method=RequestMethod.GET)
    public String stop(@PathVariable Integer id, Model model){
        Agent agent = agentService.statusStop(id);
        model.addAttribute("agent", agent);
        return "system/agent_list_page_row";
    }

    @RequestMapping(value="{id}/start",method=RequestMethod.GET)
    public String start(@PathVariable Integer id, Model model){
        Agent agent = agentService.statusWaitingFirstCheck(id);
        model.addAttribute("agent", agent);
        return "system/agent_list_page_row";
    }

    @RequestMapping(value="{id}/resetpwd",method=RequestMethod.GET)
    public String resetPassword(@PathVariable Integer id, Model model){
        Agent agent = agentService.findAgentInfo(id);
        model.addAttribute("customer", agent.getCustomer());
        return "system/agent_reset_pwd";
    }

    @RequestMapping(value="{id}/profit",method=RequestMethod.GET)
    public String profit(@PathVariable Integer id, Model model){
        Agent agent = agentService.findAgentInfo(id);
        Multimap<String, AgentProfitSetting> profitSettingMap = ArrayListMultimap.create();
        for(AgentProfitSetting agentProfitSetting : agent.getProfitSettings()){
            profitSettingMap.put(agentProfitSetting.getPayChannelId()+"_"+agentProfitSetting.getTradeTypeId(), agentProfitSetting);
        }
        List<DictionaryTradeType> tradeTypes = dictionaryService.listAllDictionaryTradeTypes();
        model.addAttribute("agent", agent);
        model.addAttribute("tradeTypes", tradeTypes);
        model.addAttribute("profitSettingMap", profitSettingMap);
        return "system/agent_profit";
    }


    @RequestMapping(value="{id}/profit/edit",method=RequestMethod.GET)
    public String editProfit(@PathVariable Integer id, Integer payChannelId, Model model){
        Agent agent = agentService.findAgentInfo(id);
        List<PayChannel> payChannels = payChannelService.findCheckedChannels();
        if (payChannelId != null){
            PayChannel agentPayChannel = payChannelService.findChannelInfo(payChannelId);
            model.addAttribute("agentPayChannel", agentPayChannel);
        }
        Multimap<String, AgentProfitSetting> profitSettingMap = ArrayListMultimap.create();
        for(AgentProfitSetting agentProfitSetting : agent.getProfitSettings()){
            profitSettingMap.put(agentProfitSetting.getPayChannelId()+"_"+agentProfitSetting.getTradeTypeId(), agentProfitSetting);
        }
        List<DictionaryTradeType> tradeTypes = dictionaryService.listAllDictionaryTradeTypes();
        model.addAttribute("agent", agent);
        model.addAttribute("payChannels", payChannels);
        model.addAttribute("tradeTypes", tradeTypes);
        model.addAttribute("profitSettingMap", profitSettingMap);
        return "system/agent_profit_edit_row";
    }

    @RequestMapping(value="{id}/profit/edit",method=RequestMethod.POST)
    public String editProfit(@PathVariable Integer id, Integer payChannelId,
                             String data, Model model) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(data, Map.class);
        int newChannelId = (int)map.get("channelId");
        List<Map<String, Object>> tradeTypeList = (List)map.get("tradeTypes");
        agentService.updateProfit(id, payChannelId, newChannelId, tradeTypeList);


        Agent agent = agentService.findAgentInfo(id);
        PayChannel agentPayChannel = payChannelService.findChannelInfo(newChannelId);
        Multimap<String, AgentProfitSetting> profitSettingMap = ArrayListMultimap.create();
        for(AgentProfitSetting agentProfitSetting : agent.getProfitSettings()){
            profitSettingMap.put(agentProfitSetting.getPayChannelId()+"_"+agentProfitSetting.getTradeTypeId(), agentProfitSetting);
        }
        List<DictionaryTradeType> tradeTypes = dictionaryService.listAllDictionaryTradeTypes();
        model.addAttribute("agent", agent);
        model.addAttribute("agentPayChannel", agentPayChannel);
        model.addAttribute("tradeTypes", tradeTypes);
        model.addAttribute("profitSettingMap", profitSettingMap);
        return "system/agent_profit_info_row";
    }

    @RequestMapping(value="{id}/profit/create",method=RequestMethod.POST)
    public String editProfit(@PathVariable Integer id,
                             String data, Model model) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = objectMapper.readValue(data, Map.class);
        int newChannelId = (int)map.get("channelId");
        List<Map<String, Object>> tradeTypeList = (List)map.get("tradeTypes");
        agentService.createProfit(id, newChannelId, tradeTypeList);

        Agent agent = agentService.findAgentInfo(id);
        PayChannel agentPayChannel = payChannelService.findChannelInfo(newChannelId);
        Multimap<String, AgentProfitSetting> profitSettingMap = ArrayListMultimap.create();
        for(AgentProfitSetting agentProfitSetting : agent.getProfitSettings()){
            profitSettingMap.put(agentProfitSetting.getPayChannelId()+"_"+agentProfitSetting.getTradeTypeId(), agentProfitSetting);
        }
        List<DictionaryTradeType> tradeTypes = dictionaryService.listAllDictionaryTradeTypes();
        model.addAttribute("agent", agent);
        model.addAttribute("agentPayChannel", agentPayChannel);
        model.addAttribute("tradeTypes", tradeTypes);
        model.addAttribute("profitSettingMap", profitSettingMap);
        return "system/agent_profit_info_row";
    }

    private void findPage(Integer page, Byte status, String keys, Model model){
        if (page == null) {
            page = 1;
        }
        if (status != null && status == 0) {
            status = null;
        }
        Page<Agent> agents = agentService.findPages(page, status, keys);
        model.addAttribute("page", agents);
    }

    @RequestMapping(value="uploadImg",method=RequestMethod.POST)
    @ResponseBody
    public Response uploadImg(MultipartFile file,HttpServletRequest request){
        String path = agentPath;
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

        String result = HttpFile.upload(file, path);
        if(result.indexOf("失败")>0){
            LOG.info("文件上传失败");
            return Response.getError("上传失败");
        }
        return Response.getSuccess(result);
    }

}
