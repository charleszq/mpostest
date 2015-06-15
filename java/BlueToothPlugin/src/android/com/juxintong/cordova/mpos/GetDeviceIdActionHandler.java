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
public class GetDeviceIdActionHandler extends AbstractActionHandler {

	public GetDeviceIdActionHandler(IUmsMposService service) {
		super(service);
	}

	@Override
	public boolean handle(JSONArray data, final CallbackContext cb)
			throws JSONException {
		JSONObject obj = data.getJSONObject(0);
		Log.d( TAG, "get device id: " + obj.toString() );
		
		Bundle bundle = new Bundle();
		bundle.putString(MposConstants.RESULT_BILLSMID, obj.getString(MposConstants.RESULT_BILLSMID));
		bundle.putString(MposConstants.RESULT_BILLSTID, obj.getString(MposConstants.RESULT_BILLSTID));
		
		if( mUmsMposService != null ) {
			try {
				mUmsMposService.setDevice(bundle, new IUmsMposResultListener.Stub() {

					@Override
					public void umsServiceResult(Bundle result)
							throws RemoteException {
						try {
							JSONObject jsonResult = new JSONObject();
							jsonResult.put( MposConstants.RESULT_RESULTSTATUS, result.getString(MposConstants.RESULT_RESULTSTATUS));
							jsonResult.put( MposConstants.RESULT_RESULTINFO, result.getString(MposConstants.RESULT_RESULTINFO));
							jsonResult.put( MposConstants.RESULT_DEVICE_ID, result.getString(MposConstants.RESULT_DEVICE_ID));
							Log.e(TAG,  jsonResult.toString() );
							cb.success( jsonResult.toString() );
						} catch (JSONException e) {
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
		} else {
			Log.d(TAG, "service is not initialized.");
			return false;
		}
	}

}
