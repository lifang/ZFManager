package com.comdosoft.financial.manage.controller.system;

import com.comdosoft.financial.manage.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by quqiang on 15/3/23.
 */
@Controller
@RequestMapping("system/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String list(Model model){
        return "system/dictionary";
    }
}
