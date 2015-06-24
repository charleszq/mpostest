/**
 * 
 */
package com.juxintong.cordova.mpos;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.chinaums.mpos.service.IUmsMposResultListener;
import com.chinaums.mpos.service.IUmsMposService;

/**
 * @author Charles
 *
 */
public class PlaceOrderActionHandler extends AbstractActionHandler {

	public PlaceOrderActionHandler(IUmsMposService service) {
		super(service);
	}

	/* (non-Javadoc)
	 * @see com.juxintong.cordova.mpos.IActionHandler#handle(org.json.JSONArray, org.apache.cordova.CallbackContext)
	 */
	@Override
	public boolean handle(JSONArray data, final CallbackContext cb)
			throws JSONException {
		try {
			JSONObject obj = data.getJSONObject(0);
			Log.d( TAG, "place order: " + obj.toString() );
			
			Bundle bundle = new Bundle();
			bundle.putString(MposConstants.INPUT_AMOUNT, obj.getString(MposConstants.INPUT_AMOUNT));
			bundle.putString(MposConstants.RESULT_MERORDERID, obj.getString(MposConstants.RESULT_MERORDERID));
			bundle.putString(MposConstants.INPUT_MER_ORDER_DESC, obj.getString(MposConstants.INPUT_MER_ORDER_DESC));
			bundle.putString(MposConstants.RESULT_BILLSMID, obj.getString(MposConstants.RESULT_BILLSMID));
			bundle.putString(MposConstants.RESULT_BILLSTID, obj.getString(MposConstants.RESULT_BILLSTID));
			bundle.putString(MposConstants.INPUT_OPERATOR, obj.getString(MposConstants.INPUT_OPERATOR));
			bundle.putString(MposConstants.INPUT_CONSUMER_PHONE, obj.getString(MposConstants.INPUT_CONSUMER_PHONE));
			bundle.putString(MposConstants.INPUT_SALES_SLIP_TYPE, obj.getString(MposConstants.INPUT_SALES_SLIP_TYPE));
			bundle.putString(MposConstants.INPUT_PAY_TYPE, obj.getString(MposConstants.INPUT_PAY_TYPE));
			bundle.putString(MposConstants.RESULT_MEMO, obj.getString(MposConstants.RESULT_MEMO));
			mUmsMposService.pay(bundle, new IUmsMposResultListener.Stub() {

				@Override
				public void umsServiceResult(Bundle result)
						throws RemoteException {
					JSONObject jsonResult = new JSONObject();
					try {
						jsonResult.put(MposConstants.OUTPUT_PAY_STATUS, result.getString(MposConstants.OUTPUT_PAY_STATUS));
						jsonResult.put(MposConstants.OUTPUT_PRINT_STATUS, result.getString(MposConstants.OUTPUT_PRINT_STATUS));
						jsonResult.put(MposConstants.RESULT_RESULTINFO, result.getString(MposConstants.RESULT_RESULTINFO));
						jsonResult.put(MposConstants.RESULT_OPERATOR, result.getString(MposConstants.RESULT_OPERATOR));
						jsonResult.put(MposConstants.RESULT_ORGID, result.getString(MposConstants.RESULT_ORGID));
						jsonResult.put(MposConstants.RESULT_AUTHNO, result.getString(MposConstants.RESULT_AUTHNO));
						jsonResult.put(MposConstants.RESULT_ACQNO, result.getString(MposConstants.RESULT_ACQNO));
						jsonResult.put(MposConstants.RESULT_ISSNO, result.getString(MposConstants.RESULT_ISSNO));
						jsonResult.put(MposConstants.OUTPUT_ISS_BANK_NAME, result.getString(MposConstants.OUTPUT_ISS_BANK_NAME));
						jsonResult.put(MposConstants.RESULT_MERORDERID, result.getString(MposConstants.RESULT_MERORDERID));
						jsonResult.put(MposConstants.RESULT_ORDERID, result.getString(MposConstants.RESULT_ORDERID));
						jsonResult.put(MposConstants.RESULT_PACCOUNT, result.getString(MposConstants.RESULT_PACCOUNT));
						jsonResult.put(MposConstants.RESULT_PROCESSCODE, result.getString(MposConstants.RESULT_PROCESSCODE));
						jsonResult.put(MposConstants.RESULT_AMOUNT, result.getString(MposConstants.RESULT_AMOUNT));
						jsonResult.put(MposConstants.RESULT_VOUCHERNO, result.getString(MposConstants.RESULT_VOUCHERNO));
						jsonResult.put(MposConstants.RESULT_VOUCHERDATE, result.getString(MposConstants.RESULT_VOUCHERDATE));
						jsonResult.put(MposConstants.RESULT_VOUCHERTIME, result.getString(MposConstants.RESULT_VOUCHERTIME));
						jsonResult.put(MposConstants.RESULT_LIQDATE, result.getString(MposConstants.RESULT_LIQDATE));
						jsonResult.put(MposConstants.RESULT_SERVICECODE, result.getString(MposConstants.RESULT_SERVICECODE));
						jsonResult.put(MposConstants.RESULT_REFID, result.getString(MposConstants.RESULT_REFID));
						jsonResult.put(MposConstants.RESULT_RESPCODE, result.getString(MposConstants.RESULT_RESPCODE));
						jsonResult.put(MposConstants.RESULT_RESPINFO, result.getString(MposConstants.RESULT_RESPINFO));
						jsonResult.put(MposConstants.RESULT_TERMID, result.getString(MposConstants.RESULT_TERMID));
						jsonResult.put(MposConstants.RESULT_MERCHANTID, result.getString(MposConstants.RESULT_MERCHANTID));
						jsonResult.put(MposConstants.RESULT_CURRENCYCODE, result.getString(MposConstants.RESULT_CURRENCYCODE));
						jsonResult.put(MposConstants.RESULT_BATCHNO, result.getString(MposConstants.RESULT_BATCHNO));
						jsonResult.put(MposConstants.RESULT_BILLSMID, result.getString(MposConstants.RESULT_BILLSMID));
						jsonResult.put(MposConstants.RESULT_BILLSMERCNAME, result.getString(MposConstants.RESULT_BILLSMERCNAME));
						jsonResult.put(MposConstants.RESULT_BILLSTID, result.getString(MposConstants.RESULT_BILLSTID));
						jsonResult.put(MposConstants.RESULT_TXNTYPE, result.getString(MposConstants.RESULT_TXNTYPE));
						jsonResult.put(MposConstants.RESULT_DEALDATE, result.getString(MposConstants.RESULT_DEALDATE));
						jsonResult.put(MposConstants.RESULT_DEVICE_ID, result.getString(MposConstants.RESULT_DEVICE_ID));
						
						Log.d(TAG, jsonResult.toString());
						cb.success( jsonResult.toString() );
					} catch (JSONException e) {
						cb.error( e.getMessage() );
					}
				}
				
			});
			return true;
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
			cb.error( e.getMessage() );
			return false;
		}
	}

}
