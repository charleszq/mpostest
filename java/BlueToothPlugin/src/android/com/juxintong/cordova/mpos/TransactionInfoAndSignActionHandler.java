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
public class TransactionInfoAndSignActionHandler extends AbstractActionHandler {

	/**
	 * @param service
	 */
	public TransactionInfoAndSignActionHandler(IUmsMposService service) {
		super(service);
	}

	/* (non-Javadoc)
	 * @see com.juxintong.cordova.mpos.IActionHandler#handle(org.json.JSONArray, org.apache.cordova.CallbackContext)
	 */
	@Override
	public boolean handle(JSONArray data, final CallbackContext cb)
			throws JSONException {
		JSONObject obj = data.getJSONObject(0);
		Log.d( TAG, "Transaction information and sign action handler: " + obj.toString() );
		
		Bundle bundle = new Bundle();
		bundle.putString(MposConstants.RESULT_ORDERID, obj.getString(MposConstants.RESULT_ORDERID));
		bundle.putString(MposConstants.RESULT_BILLSMID, obj.getString(MposConstants.RESULT_BILLSMID));
		bundle.putString(MposConstants.RESULT_BILLSTID, obj.getString(MposConstants.RESULT_BILLSTID));
		bundle.putString(MposConstants.INPUT_SALES_SLIP_TYPE, obj.getString(MposConstants.INPUT_SALES_SLIP_TYPE));
		
		try {
			mUmsMposService.setDevice(bundle, new IUmsMposResultListener.Stub() {

				@Override
				public void umsServiceResult(Bundle result)
						throws RemoteException {
					try {
						JSONObject jsonResult = new JSONObject();
						jsonResult.put( MposConstants.OUTPUT_PRINT_STATUS, result.getString(MposConstants.OUTPUT_PRINT_STATUS));
						jsonResult.put( MposConstants.RESULT_RESULTINFO, result.getString(MposConstants.RESULT_RESULTINFO));
						jsonResult.put( MposConstants.RESULT_DEVICE_ID, result.getString(MposConstants.RESULT_DEVICE_ID));
						Log.e(TAG,  jsonResult.toString() );
						cb.success( jsonResult.toString() );
					} catch (JSONException e) {
						cb.error(e.getMessage());
						Log.e(TAG, e.getMessage());
					}
				}
			});
			return true;
		} catch (RemoteException e) {
			Log.e(TAG, e.getMessage());
			cb.error(e.getMessage());
			return false;
		}
	}

}
