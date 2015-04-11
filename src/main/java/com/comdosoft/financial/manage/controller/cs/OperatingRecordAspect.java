package com.comdosoft.financial.manage.controller.cs;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

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
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OperatingRecordAspect.class);
	
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

	@AfterReturning("pointCut()")
	public void after(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Class<?> declareClass = signature.getDeclaringType();
		String methodName = signature.getName();
		
		if (!FILTER_CLASSES.keySet().contains(declareClass) || !FILTER_METHODS.keySet().contains(methodName)) return;
		
		Object[] args = joinPoint.getArgs();
		if (null == args || args.length == 0) return;
		
		HttpServletRequest request = findHttpServletRequest(args);
		if (null == request) return;
		Customer customer = sessionService.getLoginInfo(request);

		StringBuilder sb = new StringBuilder();
		sb.append(customer.getName() + "执行了");
		sb.append(FILTER_CLASSES.get(declareClass) + "界面的");
		sb.append(FILTER_METHODS.get(methodName) + "操作, ");
		sb.append("操作记录的id是");
		
		Method method = signature.getMethod();
		Integer targetId = findPathVariableId(args, method);
		if (null != targetId) sb.append(targetId);
		
		Integer type = declareClass == CsAgentController.class ? 13
					: declareClass == CsUpdateController.class ? 14 
					: declareClass == CsChangeController.class ? 15 
					: declareClass == CsReturnController.class ? 16 
					: declareClass == CsRepairController.class ? 17 
					: declareClass == CsCancelController.class ? 18 
					: declareClass == CsLeaseController.class ? 19 : null;
		
		LOGGER.debug(sb.toString());
		
		recordOperateService.saveOperateRecord(customer.getId(), 
				customer.getName(), customer.getTypes(), type, sb.toString(), targetId);
	}
	
	private HttpServletRequest findHttpServletRequest(Object[] args) {
		for (Object arg : args) {
			if (arg instanceof HttpServletRequest) return (HttpServletRequest)arg;
		}
		return null;
	}
	
	private Integer findPathVariableId(Object[] args, Method method) {
		Annotation[][] annotations = method.getParameterAnnotations();
		for (int i = 0; i < annotations.length; i++) {
			Annotation[] annos = annotations[i];
			for (int j = 0; j < annos.length; j++) {
				Annotation anno = annos[j];
				if (anno instanceof PathVariable && args[i] instanceof Integer)
					return (Integer) args[i];
			}
		}
		return null;
	}

}
