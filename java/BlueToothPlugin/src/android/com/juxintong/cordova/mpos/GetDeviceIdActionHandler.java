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
		Log.d(TAG, "get device id: " + obj.toString());

		Bundle bundle = Helper.generateBundle(obj);

		try {
			mUmsMposService.setDevice(bundle,
					new IUmsMposResultListener.Stub() {

						@Override
						public void umsServiceResult(Bundle result)
								throws RemoteException {
							JSONObject jsonResult = Helper
									.generateJSONObject(result);
							cb.success(jsonResult.toString());
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
