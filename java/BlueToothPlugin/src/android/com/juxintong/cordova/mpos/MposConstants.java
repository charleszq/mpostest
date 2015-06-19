/**
 * 
 */
package com.juxintong.cordova.mpos;

/**
 * @author Charles
 *
 */
public final class MposConstants {
	
	public static final String RESULT_DEVICE_ID		= "deviceId";
	public static final String RESULT_PAYSTATE		= "payState"  ;
	public static final String RESULT_RESULTSTATUS	= "resultStatus"  ;
	public static final String RESULT_PRINTSTATE    = "printState"   ;
	public static final String RESULT_RESULTINFO    = "resultInfo"   ;
	public static final String RESULT_OPERATOR      = "Operator"   ;
	public static final String RESULT_ORGID         = "orgId"   ;
	public static final String RESULT_AUTHNO        = "authNo"   ;
	public static final String RESULT_ACQNO         = "acqNo"   ;
	public static final String RESULT_ISSNO         = "issNo"   ;
	public static final String RESULT_MERORDERID    = "merOrderId"   ;
	public static final String RESULT_SALETYPE      = "saleType"   ;
	public static final String RESULT_ORDERID       = "orderId"   ;
	public static final String RESULT_PACCOUNT      = "pAccount"   ;
	public static final String RESULT_PROCESSCODE   = "processCode"   ;
	public static final String RESULT_AMOUNT        = "Amount"   ;
	public static final String RESULT_VOUCHERNO     = "voucherNo"   ;
	public static final String RESULT_VOUCHERDATE   = "voucherDate"   ;
	public static final String RESULT_VOUCHERTIME   = "voucherTime"   ;
	public static final String RESULT_LIQDATE       = "liqDate"   ;
	public static final String RESULT_SERVICECODE   = "serviceCode"   ;
	public static final String RESULT_REFID         = "refId"   ;
	public static final String RESULT_RESPCODE      = "respCode"   ;
	public static final String RESULT_RESPINFO      = "respInfo"   ;
	public static final String RESULT_TERMID        = "termId"   ;
	public static final String RESULT_MERCHANTID    = "merchantId"   ;
	public static final String RESULT_CURRENCYCODE  = "currencyCode"   ;
	public static final String RESULT_BATCHNO       = "batchNo"   ;
	public static final String RESULT_BILLSMID      = "billsMID"   ;
	public static final String RESULT_BILLSMERCNAME = "billsMercName"   ;
	public static final String RESULT_BILLSTID      = "billsTID"   ;
	public static final String RESULT_TXNTYPE       = "txnType"   ;
	public static final String RESULT_DEALDATE      = "dealDate"   ;
	public static final String RESULT_MEMO          = "memo"   ;
	
	//place order input
	public static final String INPUT_AMOUNT 			= "amount";
	public static final String INPUT_MER_ORDER_DESC		= "merOrderDesc";
	public static final String INPUT_OPERATOR			= "operator";
	public static final String INPUT_CONSUMER_PHONE		= "consumerPhone";
	public static final String INPUT_SALES_SLIP_TYPE	= "salesSlipType";
	public static final String INPUT_PAY_TYPE			= "payType";
	
	//place order output
	public static final String OUTPUT_PAY_STATUS		= "payStatus";
	public static final String OUTPUT_PRINT_STATUS 		= "printStatus";
	public static final String OUTPUT_ISS_BANK_NAME		= "issBankName";
		
	//action names
	public static final String ACTION_SETUP_DEVICE 				= "setupDevice";
	public static final String ACTION_GET_DEVICE_ID 			= "getDeviceId";
	public static final String ACTION_QUERY_ORDER 				= "queryOrder";
	public static final String ACTION_PLACE_ORDER 				= "placeOrder";
	public static final String ACTION_TXN_INFO_AND_SIGN 		= "txnInfoAndSign";

	private MposConstants() {
		
	}
}
