package com.comdosoft.financial.manage.service.task;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.comdosoft.financial.manage.mapper.zhangfu.RefundMapper;
import com.comdosoft.financial.manage.utils.page.Page;
import com.comdosoft.financial.manage.utils.page.PageRequest;

@Service
public class RefundService {
	
	//@Value("${page.refund.size}")
    private int pageSize=2;
    
    @Value("${path.prefix.refunde}")
	private String pathPrefixRefunde;
	
	@Autowired
	private RefundMapper refundMapper;
	
	public Page<Object> findPages(int page, Byte status, String orderNumber){
		if (orderNumber != null) {
			orderNumber = "%"+orderNumber+"%";
		}
		long count = refundMapper.getRefundCount(status, orderNumber);
		if (count == 0) {
			return new Page<Object>(new PageRequest(1, pageSize), new ArrayList<Object>(), count);
		}
		
		PageRequest request = new PageRequest(page, pageSize);
		List<Object> result = refundMapper.findPageRefundByKeys(request, status, orderNumber);
		Page<Object> refund = new Page<Object>(request, result, count);
		if (refund.getCurrentPage() > refund.getTotalPage()) {
			request = new PageRequest(refund.getTotalPage(), pageSize);
			result = refundMapper.findPageRefundByKeys(request, status, orderNumber);
			refund = new Page<>(request, result, count);
		}
		return refund;
	}
	
	public Map<Object, Object> getRefundDetails(int id){
		return refundMapper.getRefundDetails(id);
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
    public String saveTmpImage(MultipartFile img, HttpServletRequest request) throws IOException {
        String fileName = Calendar.getInstance().getTimeInMillis() + ".jpg";
        // String realPath = dirRoot + imgTempPath;
        String realPath = request.getServletContext().getRealPath(pathPrefixRefunde);
        File dir = new File(realPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        InputStream stream = img.getInputStream();
        // Thumbnails.of(stream).size(480, 480).toFile(realPath + File.separator + fileName);
        stream.close();
        return pathPrefixRefunde + fileName;
    }
}
