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
public class QueryOrderActionHandler extends AbstractActionHandler {

	/**
	 * @param service
	 */
	public QueryOrderActionHandler(IUmsMposService service) {
		super(service);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.juxintong.cordova.mpos.IActionHandler#handle(org.json.JSONArray,
	 * org.apache.cordova.CallbackContext)
	 */
	@Override
	public boolean handle(JSONArray data, final CallbackContext cb)
			throws JSONException {
		JSONObject obj = data.getJSONObject(0);
		Log.i(TAG, "query order" + obj.toString());
		Bundle bundle = Helper.generateBundle(obj);
		try {
			mUmsMposService.queryOrderInfo(bundle,
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
