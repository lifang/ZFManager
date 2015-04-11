package com.comdosoft.financial.manage.controller.cs;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.comdosoft.financial.manage.domain.zhangfu.Customer;
import com.comdosoft.financial.manage.service.RecordOperateService;
import com.comdosoft.financial.manage.service.SessionService;

/**
 * Aspect to save operating record
 * 
 * @author Leo
 *
 */
@Aspect
@Component
public class OperatingRecordAspect {
	
	@Autowired
	private SessionService sessionService;
	@Autowired
	private RecordOperateService recordOperateService;

	private static final Map<Class<?>, String> FILTER_CLASSES = new HashMap<Class<?>, String>() {
		private static final long serialVersionUID = 1L;
		{
			put(CsAgentController.class, "代理商售后");
			put(CsCancelController.class, "注销");
			put(CsChangeController.class, "换货");
			put(CsLeaseController.class, "租赁退还");
			put(CsRepairController.class, "维修");
			put(CsReturnController.class, "退货");
			put(CsUpdateController.class, "资料更新");
		}
	};
	
	private static final Map<String, String> FILTER_METHODS = new HashMap<String, String>() {
		private static final long serialVersionUID = 1L;
		{
			put("handle", "标记为处理中");
			put("finish", "标记为处理完成");
			put("cancel", "取消");
			put("createMark", "备注");
			put("output", "添加出库记录");
			put("resubmit", "重新提交");
			put("confirmChange", "确认换货");
			put("confirmReturn", "确认退货");
			put("addPay", "添加付款记录");
			put("updatePay", "修改维修价格");
		}
	};

	@Pointcut(value = "execution(* com.comdosoft.financial.manage.controller.cs.*Controller.*(..))"
			+ " && @annotation(org.springframework.web.bind.annotation.RequestMapping)")
	public void pointCut() {
	}

	@After("pointCut()")
	public void after(JoinPoint joinPoint) {
		Signature signature = joinPoint.getSignature();
		Class<?> declareClass = signature.getDeclaringType();
		String methodName = signature.getName();
		
		if (!FILTER_CLASSES.keySet().contains(declareClass) || !FILTER_METHODS.keySet().contains(methodName)) return;
		
		Object[] args = joinPoint.getArgs();
		if (null == args || args.length == 0 || !(args[0] instanceof HttpServletRequest)) return;
		
		HttpServletRequest request = (HttpServletRequest) args[0];
		Customer customer = sessionService.getLoginInfo(request);

		StringBuilder sb = new StringBuilder();
		sb.append(customer.getName() + "执行了");
		sb.append(FILTER_CLASSES.get(declareClass) + "界面的");
		sb.append(FILTER_METHODS.get(methodName) + "操作, ");
		
		sb.append("操作记录的id是");
		Integer targetId = null;
		switch (methodName) {
		case "handle":
		case "finish":
		case "cancel":
		case "output":
		case "resubmit":
		case "confirmChange":
		case "confirmReturn":
		case "addPay":
		case "updatePay":
			targetId = (Integer) args[2];
			break;
		case "createMark":
			targetId = (Integer) args[1];
			break;
		default:
			break;
		}
		if (null != targetId) sb.append(targetId);
		
		Integer type = declareClass == CsAgentController.class ? 13
					: declareClass == CsUpdateController.class ? 14 
					: declareClass == CsChangeController.class ? 15 
					: declareClass == CsReturnController.class ? 16 
					: declareClass == CsRepairController.class ? 17 
					: declareClass == CsCancelController.class ? 18 
					: declareClass == CsLeaseController.class ? 19 : null;
		
		recordOperateService.saveOperateRecord(customer.getId(),
				customer.getName(), customer.getTypes(), type, sb.toString(),
				targetId);
	}
}
