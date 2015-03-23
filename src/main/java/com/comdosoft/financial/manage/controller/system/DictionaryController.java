package com.comdosoft.financial.manage.controller.system;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.DictionaryCreditType;
import com.comdosoft.financial.manage.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
            List<DictionaryCreditType> creditTypes = dictionaryService.listAllDictionaryCreditTypes();
            model.addAttribute("creditTypes", creditTypes);
            return "system/dictionary/dictionary";
        }

        @RequestMapping(value = "/creditType/{id}/edit", method = RequestMethod.GET)
        public String editCreditType(@PathVariable Integer id, Model model){
            if (id != 0){
                DictionaryCreditType creditType = dictionaryService.findDictionaryCreditType(id);
                model.addAttribute("creditType", creditType);
            }
            return "system/dictionary/creditType_edit";
        }
        @RequestMapping(value = "/creditType/{id}/info", method = RequestMethod.GET)
        public String creditTypeInfo(@PathVariable Integer id, Model model){
            DictionaryCreditType creditType = dictionaryService.findDictionaryCreditType(id);
            model.addAttribute("creditType", creditType);
            return "system/dictionary/creditType_info";
        }

        @RequestMapping(value = "/creditType/{id}/edit", method = RequestMethod.POST)
        public String editCreditType(@PathVariable Integer id, String name, Model model){
            DictionaryCreditType creditType = null;
            if (id != 0){
                creditType = dictionaryService.editDictionaryCreditType(id, name);
            } else {
                creditType = dictionaryService.createDictionaryCreditType(name);
            }
            model.addAttribute("creditType", creditType);
            return "system/dictionary/creditType_info";
        }

        @RequestMapping(value = "/creditType/{id}/delete", method = RequestMethod.GET)
        @ResponseBody
        public Response deleteCreditType(@PathVariable Integer id, String name, Model model){
            dictionaryService.deleteDictionaryCreditType(id);
            return Response.getSuccess(null);
        }


}
