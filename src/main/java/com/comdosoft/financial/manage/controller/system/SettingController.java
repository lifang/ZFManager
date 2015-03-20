package com.comdosoft.financial.manage.controller.system;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.SysConfig;
import com.comdosoft.financial.manage.service.SysConfigService;
import com.comdosoft.financial.manage.utils.FreeMarkerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by quqiang on 15/3/19.
 */
@Controller
@RequestMapping("/system/setting")
public class SettingController {
    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping(value="",method= RequestMethod.GET)
    public String edit(Model model) throws TemplateModelException {
        Map<String, SysConfig> sysConfigsMap = sysConfigService.findConfigsMap();
        model.addAttribute("sysConfigsMap", sysConfigsMap);
        model.addAttribute("SysConfig", FreeMarkerUtils.useClass(SysConfig.class.getName()));
        return "system/setting";
    }

    @RequestMapping(value="edit",method= RequestMethod.POST)
    @ResponseBody
    public Response edit(String configs) throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Map<String, Integer>> maps = (Map<String, Map<String, Integer>>)objectMapper.readValue(configs, Map.class);
        sysConfigService.edit(maps);
        return Response.getSuccess(null);
    }
}
