private void findPage(Integer page, Byte status, String keys, Model model){
if (page == null) {
page = 1;
}
if (status != null && status == 0) {
status = null;
}
Page<Agent> factories = agentService.findPages(page, status, keys);
    model.addAttribute("page", factories);
    }