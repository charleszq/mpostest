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
public class SetupDeviceActionHandler extends AbstractActionHandler {

	public SetupDeviceActionHandler(IUmsMposService service) {
		super(service);
	}

	/* (non-Javadoc)
	 * @see com.juxintong.cordova.mpos.IActionHandler#handle(org.json.JSONArray, org.apache.cordova.CallbackContext)
	 */
	@Override
	public boolean handle(JSONArray data, final CallbackContext cb)
			throws JSONException {
		JSONObject obj = data.getJSONObject(0);
		Log.i(TAG, "setupdevice" + obj.toString() );
		
		Bundle bundle = new Bundle();
		bundle.putString(MposConstants.RESULT_BILLSMID, obj.getString(MposConstants.RESULT_BILLSMID));
		bundle.putString(MposConstants.RESULT_BILLSTID, obj.getString(MposConstants.RESULT_BILLSTID));
		if( mUmsMposService != null ) {
			try {
				mUmsMposService.setDevice(bundle, new IUmsMposResultListener.Stub() {

					@Override
					public void umsServiceResult(Bundle result)
							throws RemoteException {
						JSONObject jsonResult = new JSONObject();
						try {
							jsonResult.put(MposConstants.RESULT_RESULTSTATUS, result.getString(MposConstants.RESULT_RESULTSTATUS));
							jsonResult.put(MposConstants.RESULT_RESULTINFO, result.getString(MposConstants.RESULT_RESULTINFO));
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
				return false;
			}
		} else {
			Log.d(TAG, "Service is not initialized.");
			return false;
		}
	}

}
