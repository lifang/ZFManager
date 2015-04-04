package com.comdosoft.financial.manage.service.cs;

public class CsConstants {

	/**
	 * 维修状态
	 */
	public static class CsRepairStatus {
		public static final byte NOT_PAID = 1;
		public static final byte SUSPEND = 2;
		public static final byte HANDLE = 3;
		public static final byte FINISH = 4;
		public static final byte CANCEL = 5;
	}

	/**
	 * 退货状态
	 */
	public static class CsReturnStatus {
		public static final byte SUSPEND = 1;
		public static final byte HANDLE = 2;
		public static final byte FINISH = 4;
		public static final byte CANCEL = 5;
	}

	/**
	 * 注销状态
	 */
	public static class CsCancelStatus {
		public static final int SUSPEND = 1;
		public static final int HANDLE = 2;
		public static final int FINISH = 4;
		public static final int CANCEL = 5;
	}

	/**
	 * 换货状态
	 */
	public static class CsChangeStatus {
		public static final byte SUSPEND = 1;
		public static final byte HANDLE = 2;
		public static final byte FINISH = 4;
		public static final byte CANCEL = 5;
	}

	/**
	 * 更新资料状态
	 */
	public static class CsUpdateStatus {
		public static final byte SUSPEND = 1;
		public static final byte HANDLE = 2;
		public static final byte FINISH = 4;
		public static final byte CANCEL = 5;
	}

	/**
	 * 租赁退还状态
	 */
	public static class CsLeaseStatus {
		public static final int SUSPEND = 1;
		public static final int HANDLE = 2;
		public static final int FINISH = 4;
		public static final int CANCEL = 5;
	}

	/**
	 * 代理商售后状态
	 */
	public static class CsAgentStatus {
		public static final byte SUSPEND = 1;
		public static final byte HANDLE = 2;
		public static final byte FINISH = 3;
		public static final byte CANCEL = 4;
	}

	/**
	 * 支付方式
	 */
	public static class PayChannel {
		public static final int ALIPAY = 1;
		public static final int UNIONPAY = 2;
		public static final int CASH = 3;
	}

	/**
	 * 申请材料类型
	 */
	public static class MaterialType {
		public static final int CANCEL = 1;
		public static final int UPDATE = 2;
	}
}
