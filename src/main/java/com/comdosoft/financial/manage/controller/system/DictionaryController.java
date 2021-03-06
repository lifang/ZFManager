package com.comdosoft.financial.manage.controller.system;

import com.comdosoft.financial.manage.domain.Response;
import com.comdosoft.financial.manage.domain.zhangfu.*;
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
            List<DictionaryCompanyAddress> companyAddresses = dictionaryService.listAllDictionaryCompanyAddresss();
            List<DictionaryOpenPrivateInfo> openPrivates = dictionaryService.listAllDictionaryOpenPrivateInfos();
            List<DictionaryCustomerOrderType> orderTypes = dictionaryService.listAllDictionaryCustomerOrderTypes();
            List<DictionaryEncryptCardWay> encryptCardWays = dictionaryService.listAllDictionaryEncryptCardWays();
            List<DictionarySignOrderWay> orderWays = dictionaryService.listAllDictionarySignOrderWays();
            List<DictionaryCardType> cardTypes = dictionaryService.listAllDictionaryCardTypes();
            List<DictionaryTradeType> tradeTypes = dictionaryService.listAllDictionaryTradeTypes();
            List<DictionaryTradeStandardRate> standardRates = dictionaryService.listAllDictionaryTradeStandardRates();
            List<DictionaryBillingCycle> billingCycles = dictionaryService.listAllDictionaryBillingCycle();
            model.addAttribute("creditTypes", creditTypes);
            model.addAttribute("companyAddresses", companyAddresses);
            model.addAttribute("openPrivates", openPrivates);
            model.addAttribute("orderTypes", orderTypes);
            model.addAttribute("encryptCardWays", encryptCardWays);
            model.addAttribute("orderWays", orderWays);
            model.addAttribute("cardTypes", cardTypes);
            model.addAttribute("tradeTypes", tradeTypes);
            model.addAttribute("standardRates", standardRates);
            model.addAttribute("billingCycles", billingCycles);
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
        public Response deleteCreditType(@PathVariable Integer id, Model model){
            dictionaryService.deleteDictionaryCreditType(id);
            return Response.getSuccess(null);
        }


        @RequestMapping(value = "/openPrivate/{id}/edit", method = RequestMethod.GET)
        public String editOpenPrivate(@PathVariable Integer id, Model model){
            if (id != 0){
                DictionaryOpenPrivateInfo openPrivate = dictionaryService.findDictionaryOpenPrivateInfo(id);
                model.addAttribute("openPrivate", openPrivate);
            }
            return "system/dictionary/openPrivate_edit";
        }

        @RequestMapping(value = "/openPrivate/{id}/info", method = RequestMethod.GET)
        public String creditOpenPrivate(@PathVariable Integer id, Model model){
            DictionaryOpenPrivateInfo openPrivate = dictionaryService.findDictionaryOpenPrivateInfo(id);
            model.addAttribute("openPrivate", openPrivate);
            return "system/dictionary/openPrivate_info";
        }

        @RequestMapping(value = "/openPrivate/{id}/edit", method = RequestMethod.POST)
        public String editOpenPrivate(@PathVariable Integer id, Byte infoType, String name,
                                      String introduction, String queryMark, Model model){
            DictionaryOpenPrivateInfo openPrivate = null;
            if (id != 0){
                openPrivate = dictionaryService.editDictionaryOpenPrivateInfo(id, infoType, name, introduction, queryMark);
            } else {
                openPrivate = dictionaryService.createDictionaryOpenPrivateInfo(infoType, name, introduction, queryMark);
            }
            model.addAttribute("openPrivate", openPrivate);
            return "system/dictionary/openPrivate_info";
        }

        @RequestMapping(value = "/openPrivate/{id}/delete", method = RequestMethod.GET)
        @ResponseBody
        public Response deleteOpenPrivate(@PathVariable Integer id, Model model){
            dictionaryService.deleteDictionaryOpenPrivateInfo(id);
            return Response.getSuccess(null);
        }


        @RequestMapping(value = "/encryptCardWay/{id}/edit", method = RequestMethod.GET)
        public String editEncryptCardWay(@PathVariable Integer id, Model model){
            if (id != 0){
                DictionaryEncryptCardWay encryptCardWay = dictionaryService.findDictionaryEncryptCardWay(id);
                model.addAttribute("encryptCardWay", encryptCardWay);
            }
            return "system/dictionary/encryptCardWay_edit";
        }
        @RequestMapping(value = "/encryptCardWay/{id}/info", method = RequestMethod.GET)
        public String encryptCardWayInfo(@PathVariable Integer id, Model model){
            DictionaryEncryptCardWay encryptCardWay = dictionaryService.findDictionaryEncryptCardWay(id);
            model.addAttribute("encryptCardWay", encryptCardWay);
            return "system/dictionary/encryptCardWay_info";
        }

        @RequestMapping(value = "/encryptCardWay/{id}/edit", method = RequestMethod.POST)
        public String editEncryptCardWay(@PathVariable Integer id, String name, Model model){
            DictionaryEncryptCardWay encryptCardWay = null;
            if (id != 0){
                encryptCardWay = dictionaryService.editDictionaryEncryptCardWay(id, name);
            } else {
                encryptCardWay = dictionaryService.createDictionaryEncryptCardWay(name);
            }
            model.addAttribute("encryptCardWay", encryptCardWay);
            return "system/dictionary/encryptCardWay_info";
        }

        @RequestMapping(value = "/encryptCardWay/{id}/delete", method = RequestMethod.GET)
        @ResponseBody
        public Response deleteEncryptCardWay(@PathVariable Integer id, Model model){
            dictionaryService.deleteDictionaryEncryptCardWay(id);
            return Response.getSuccess(null);
        }

        @RequestMapping(value = "/orderType/{id}/edit", method = RequestMethod.GET)
        public String editOrderType(@PathVariable Integer id, Model model){
            if (id != 0){
                DictionaryCustomerOrderType orderType = dictionaryService.findDictionaryCustomerOrderType(id);
                model.addAttribute("orderType", orderType);
            }
            return "system/dictionary/orderType_edit";
        }
        @RequestMapping(value = "/orderType/{id}/info", method = RequestMethod.GET)
        public String orderTypeInfo(@PathVariable Integer id, Model model){
            DictionaryCustomerOrderType orderType = dictionaryService.findDictionaryCustomerOrderType(id);
            model.addAttribute("orderType", orderType);
            return "system/dictionary/orderType_info";
        }

        @RequestMapping(value = "/orderType/{id}/edit", method = RequestMethod.POST)
        public String editOrderType(@PathVariable Integer id, String name, Model model){
            DictionaryCustomerOrderType orderType = null;
            if (id != 0){
                orderType = dictionaryService.editDictionaryCustomerOrderType(id, name);
            } else {
                orderType = dictionaryService.createDictionaryCustomerOrderType(name);
            }
            model.addAttribute("orderType", orderType);
            return "system/dictionary/orderType_info";
        }

        @RequestMapping(value = "/orderType/{id}/delete", method = RequestMethod.GET)
        @ResponseBody
        public Response deleteOrderType(@PathVariable Integer id, Model model){
            dictionaryService.deleteDictionaryCustomerOrderType(id);
            return Response.getSuccess(null);
        }

        @RequestMapping(value = "/cardType/{id}/edit", method = RequestMethod.GET)
        public String editCardType(@PathVariable Integer id, Model model){
            if (id != 0){
                DictionaryCardType cardType = dictionaryService.findDictionaryCardType(id);
                model.addAttribute("cardType", cardType);
            }
            return "system/dictionary/cardType_edit";
        }

        @RequestMapping(value = "/cardType/{id}/info", method = RequestMethod.GET)
        public String cardTypeInfo(@PathVariable Integer id, Model model){
            DictionaryCardType cardType = dictionaryService.findDictionaryCardType(id);
            model.addAttribute("cardType", cardType);
            return "system/dictionary/cardType_info";
        }

        @RequestMapping(value = "/cardType/{id}/edit", method = RequestMethod.POST)
        public String editCardType(@PathVariable Integer id, String name, Model model){
            DictionaryCardType cardType = null;
            if (id != 0){
                cardType = dictionaryService.editDictionaryCardType(id, name);
            } else {
                cardType = dictionaryService.createDictionaryCardType(name);
            }
            model.addAttribute("cardType", cardType);
            return "system/dictionary/cardType_info";
        }

        @RequestMapping(value = "/cardType/{id}/delete", method = RequestMethod.GET)
        @ResponseBody
        public Response deleteCardType(@PathVariable Integer id, Model model){
            dictionaryService.deleteDictionaryCardType(id);
            return Response.getSuccess(null);
        }

        @RequestMapping(value = "/orderWay/{id}/edit", method = RequestMethod.GET)
        public String editOrderWay(@PathVariable Integer id, Model model){
            if (id != 0){
                DictionarySignOrderWay orderWay = dictionaryService.findDictionarySignOrderWay(id);
                model.addAttribute("orderWay", orderWay);
            }
            return "system/dictionary/orderWay_edit";
        }

        @RequestMapping(value = "/orderWay/{id}/info", method = RequestMethod.GET)
        public String orderWayInfo(@PathVariable Integer id, Model model){
            DictionarySignOrderWay orderWay = dictionaryService.findDictionarySignOrderWay(id);
            model.addAttribute("orderWay", orderWay);
            return "system/dictionary/orderWay_info";
        }

        @RequestMapping(value = "/orderWay/{id}/edit", method = RequestMethod.POST)
        public String editOrderWay(@PathVariable Integer id, String name, Model model){
            DictionarySignOrderWay orderWay = null;
            if (id != 0){
                orderWay = dictionaryService.editDictionarySignOrderWay(id, name);
            } else {
                orderWay = dictionaryService.createDictionarySignOrderWay(name);
            }
            model.addAttribute("orderWay", orderWay);
            return "system/dictionary/orderWay_info";
        }

        @RequestMapping(value = "/orderWay/{id}/delete", method = RequestMethod.GET)
        @ResponseBody
        public Response deleteOrderWay(@PathVariable Integer id, Model model){
            dictionaryService.deleteDictionarySignOrderWay(id);
            return Response.getSuccess(null);
        }


        @RequestMapping(value = "/companyAddress/{id}/edit", method = RequestMethod.GET)
        public String editCompanyAddress(@PathVariable Integer id, Model model){
            if (id != 0){
                DictionaryCompanyAddress companyAddress = dictionaryService.findDictionaryCompanyAddress(id);
                model.addAttribute("companyAddress", companyAddress);
            }
            return "system/dictionary/companyAddress_edit";
        }
        @RequestMapping(value = "/companyAddress/{id}/info", method = RequestMethod.GET)
        public String companyAddressInfo(@PathVariable Integer id, Model model){
            DictionaryCompanyAddress companyAddress = dictionaryService.findDictionaryCompanyAddress(id);
            model.addAttribute("companyAddress", companyAddress);
            return "system/dictionary/companyAddress_info";
        }

        @RequestMapping(value = "/companyAddress/{id}/edit", method = RequestMethod.POST)
        public String editCompanyAddress(@PathVariable Integer id, String name, Model model){
            DictionaryCompanyAddress companyAddress = null;
            if (id != 0){
                companyAddress = dictionaryService.editDictionaryCompanyAddress(id, name);
            } else {
                companyAddress = dictionaryService.createDictionaryCompanyAddress(name);
            }
            model.addAttribute("companyAddress", companyAddress);
            return "system/dictionary/companyAddress_info";
        }

        @RequestMapping(value = "/companyAddress/{id}/delete", method = RequestMethod.GET)
        @ResponseBody
        public Response deleteCompanyAddress(@PathVariable Integer id, Model model){
            dictionaryService.deleteDictionaryCompanyAddress(id);
            return Response.getSuccess(null);
        }


        @RequestMapping(value = "/tradeType/{id}/edit", method = RequestMethod.GET)
        public String editTradeType(@PathVariable Integer id, Model model){
            if (id != 0){
                DictionaryTradeType tradeType = dictionaryService.findDictionaryTradeType(id);
                model.addAttribute("tradeType", tradeType);
            }
            return "system/dictionary/tradeType_edit";
        }

        @RequestMapping(value = "/tradeType/{id}/info", method = RequestMethod.GET)
        public String creditTradeType(@PathVariable Integer id, Model model){
            DictionaryTradeType tradeType = dictionaryService.findDictionaryTradeType(id);
            model.addAttribute("tradeType", tradeType);
            return "system/dictionary/tradeType_info";
        }

        @RequestMapping(value = "/tradeType/{id}/edit", method = RequestMethod.POST)
        public String editTradeType(@PathVariable Integer id, Byte type, String tradeValue, Model model){
            DictionaryTradeType tradeType = null;
            if (id != 0){
                tradeType = dictionaryService.editDictionaryTradeType(id, type, tradeValue);
            } else {
                tradeType = dictionaryService.createDictionaryTradeType(type, tradeValue);
            }
            model.addAttribute("tradeType", tradeType);
            return "system/dictionary/tradeType_info";
        }

        @RequestMapping(value = "/tradeType/{id}/delete", method = RequestMethod.GET)
        @ResponseBody
        public Response deleteTradeType(@PathVariable Integer id, Model model){
            dictionaryService.deleteDictionaryTradeType(id);
            return Response.getSuccess(null);
        }

        @RequestMapping(value = "/standardRate/{id}/edit", method = RequestMethod.GET)
        public String editStandardRate(@PathVariable Integer id, Model model){
            if (id != 0){
                DictionaryTradeStandardRate standardRate = dictionaryService.findDictionaryTradeStandardRate(id);
                model.addAttribute("standardRate", standardRate);
            }
            return "system/dictionary/standardRate_edit";
        }

        @RequestMapping(value = "/standardRate/{id}/info", method = RequestMethod.GET)
        public String creditStandardRate(@PathVariable Integer id, Model model){
            DictionaryTradeStandardRate standardRate = dictionaryService.findDictionaryTradeStandardRate(id);
            model.addAttribute("standardRate", standardRate);
            return "system/dictionary/standardRate_info";
        }

        @RequestMapping(value = "/standardRate/{id}/edit", method = RequestMethod.POST)
        public String editStandardRate(@PathVariable Integer id, String name, String rate, String description, Model model){
            DictionaryTradeStandardRate standardRate = null;
            if (id != 0){
                standardRate = dictionaryService.editDictionaryTradeStandardRate(id, name, rate, description);
            } else {
                standardRate = dictionaryService.createDictionaryTradeStandardRate(name, rate, description);
            }
            model.addAttribute("standardRate", standardRate);
            return "system/dictionary/standardRate_info";
        }

        @RequestMapping(value = "/standardRate/{id}/delete", method = RequestMethod.GET)
        @ResponseBody
        public Response deleteStandardRate(@PathVariable Integer id, Model model){
            dictionaryService.deleteDictionaryTradeStandardRate(id);
            return Response.getSuccess(null);
        }
        
        @RequestMapping(value = "/billingCycle/{id}/edit", method = RequestMethod.GET)
        public String editBillingCycle(@PathVariable Integer id, Model model){
            if (id != 0){
            	DictionaryBillingCycle billingCycle = dictionaryService.findDictionaryBillingCycle(id);
                model.addAttribute("billingCycle", billingCycle);
            }
            return "system/dictionary/billingCycle_edit";
        }
        @RequestMapping(value = "/billingCycle/{id}/info", method = RequestMethod.GET)
        public String billingCycleInfo(@PathVariable Integer id, Model model){
        	DictionaryBillingCycle billingCycle = dictionaryService.findDictionaryBillingCycle(id);
            model.addAttribute("billingCycle", billingCycle);
            return "system/dictionary/billingCycle_info";
        }

        @RequestMapping(value = "/billingCycle/{id}/edit", method = RequestMethod.POST)
        public String editBillingCycle(@PathVariable Integer id, String name, String rate, String description, Model model){
        	DictionaryBillingCycle billingCycle = null;
            if (id != 0){
            	billingCycle = dictionaryService.editDictionaryBillingCycle(id, name ,rate ,description );
            } else {
            	billingCycle = dictionaryService.createDictionaryBillingCycle(name,rate ,description);
            }
            model.addAttribute("billingCycle", billingCycle);
            return "system/dictionary/billingCycle_info";
        }

        @RequestMapping(value = "/billingCycle/{id}/delete", method = RequestMethod.GET)
        @ResponseBody
        public Response deleteBillingCycle(@PathVariable Integer id, Model model){
            dictionaryService.deleteDictionaryBillingCycle(id);
            return Response.getSuccess(null);
        }

    }
