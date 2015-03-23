
@RequestMapping(value = "/orderWay/{id}/edit", method = RequestMethod.GET)
public String editOrderWay(@PathVariable Integer id, Model model){
if (id != 0){
DictionaryOrderWay orderWay = dictionaryService.findDictionaryOrderWay(id);
model.addAttribute("orderWay", orderWay);
}
return "system/dictionary/orderWay_edit";
}

@RequestMapping(value = "/orderWay/{id}/info", method = RequestMethod.GET)
public String orderWayInfo(@PathVariable Integer id, Model model){
DictionaryOrderWay orderWay = dictionaryService.findDictionaryOrderWay(id);
model.addAttribute("orderWay", orderWay);
return "system/dictionary/orderWay_info";
}

@RequestMapping(value = "/orderWay/{id}/edit", method = RequestMethod.POST)
public String editOrderWay(@PathVariable Integer id, String name, Model model){
DictionaryOrderWay orderWay = null;
if (id != 0){
orderWay = dictionaryService.editDictionaryOrderWay(id, name);
} else {
orderWay = dictionaryService.createDictionaryOrderWay(name);
}
model.addAttribute("orderWay", orderWay);
return "system/dictionary/orderWay_info";
}

@RequestMapping(value = "/orderWay/{id}/delete", method = RequestMethod.GET)
@ResponseBody
public Response deleteOrderWay(@PathVariable Integer id, String name, Model model){
dictionaryService.deleteDictionaryOrderWay(id);
return Response.getSuccess(null);
}
