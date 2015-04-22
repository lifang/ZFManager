package com.comdosoft.financial.manage.service.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.manage.mapper.zhangfu.RefundMapper;
import com.comdosoft.financial.manage.utils.SysUtils;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class RefundService {
	
	@Value("${page.refund.size}")
    private int pageSize=2;
	
	@Value("${filePath}")
	private String filePath;
    
	@Autowired
	private RefundMapper refundMapper;
	
	public Page<Object> findPages(int page, Byte status, String orderNumber,int userId){
		if (orderNumber != null && orderNumber !="") {
			orderNumber = "%"+orderNumber.trim()+"%";
		}
		long count = refundMapper.getRefundCount(status, orderNumber);
		if (count == 0) {
			return new Page<Object>(new PageRequest(1, pageSize), new ArrayList<Object>(), count);
		}
		
		PageRequest request = new PageRequest(page, pageSize);
		List<Object> result = refundMapper.findPageRefundByKeys(request, status, orderNumber,userId);
		Page<Object> refund = new Page<Object>(request, result, count);
		if (refund.getCurrentPage() > refund.getTotalPage()) {
			request = new PageRequest(refund.getTotalPage(), pageSize);
			result = refundMapper.findPageRefundByKeys(request, status, orderNumber,userId);
			refund = new Page<>(request, result, count);
		}
		return refund;
	}
	
	public Map<Object, Object> getRefundDetails(int id){
		Map<Object, Object> map = new HashMap<Object, Object>();
		map = refundMapper.getRefundDetails(id);
		map.put("returnVoucherFilePath", filePath+map.get("returnVoucherFilePath").toString());
		return map;
	}
	
	public List<Map<Object, Object>> getRefundByDetailRecord(int id){
		return refundMapper.getRefundByDetailRecord(id);
	}
	
	public void addRefundMark(int refundId,String content,int customerId){
		refundMapper.addRefundMark(refundId,content,customerId);
	}
	
	public void updateRefund(int id, String returnVoucherFilePath) {
		refundMapper.updateRefund(id, returnVoucherFilePath);
	}
	
	public void updsateRefundStatus(int id, int status) {
		refundMapper.updsateRefundStatus(id, status);
	}
	
	public void dispatch(String ids, Integer customerId, String customerName) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ids", ids.split(","));
		params.put("customerId", customerId);
		params.put("customerName", customerName);
		refundMapper.dispatchUserByIds(params);
	}
	 /**
     * 图片上传
     * 
     * @param img
     * @param request
     * @return
     * @throws IOException
     */
    public String saveTmpImage(String uploadFilePath,MultipartFile img, HttpServletRequest request) throws IOException {
    	// 保存上传的实体文件
        String fileNamePath = SysUtils.getUploadFileName(request, img, uploadFilePath);
        return fileNamePath;
    }
}
