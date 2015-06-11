/**
 * 
 */
package com.juxintong.cordova.mpos;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.chinaums.mpos.service.IUmsMposResultListener;
import com.chinaums.mpos.service.IUmsMposService;

/**
 * @author Qiang Zhang
 * 
 */
public class MposBridge extends CordovaPlugin {

	private static final String TAG = MposBridge.class.getSimpleName();
	private IUmsMposService mUmsMposService;
	private ServiceConnection mConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mUmsMposService = IUmsMposService.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			mUmsMposService = null;
		}
	};

	@Override
	public boolean execute(String action, JSONArray data, final CallbackContext cb)
			throws JSONException {
		if ("setupDevice".equals(action)) {
			Log.i(TAG, "Action is setupDevice.");
			JSONObject obj = data.getJSONObject(0);
			String billsMID = obj.getString("billsMID");
			String billsTID = obj.getString("billsTID");
			Log.i(TAG, billsMID + "," + billsTID );
			
			if( mUmsMposService == null ) {
				initService();
			}
			
			//mpos test
			if( mUmsMposService != null ) {
				Bundle bundle = new Bundle();
				bundle.putString("billsMID", billsMID);
				bundle.putString("billsTID", billsTID);
				try {
					mUmsMposService.setDevice(bundle, new IUmsMposResultListener.Stub() {

						@Override
						public void umsServiceResult(Bundle result)
								throws RemoteException {
							String resultStatus = result.getString("resultStatus");
							String resultInfo = result.getString("resultInfo");
							cb.success( resultStatus + "," + resultInfo);
						}
						
					});
				} catch (RemoteException e) {
					Log.e(TAG, e.getMessage());
				}
			} else {
				Log.e(TAG, "mpos service still is null.");
			}
			
			
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		Log.i(TAG, "In initialize.");
		initService();		
	}
	
	private void initService() {
		Context context = cordova.getActivity();
		Intent intent = new Intent();
		SharedPreferences mSharedPreferences = context.getSharedPreferences(
				"deviceType", 0);
		boolean isPhone = mSharedPreferences.getBoolean("isPhone", true);
		String servicePkgname = "com.chinaums.mposplugin";// plugin
		if (!isPhone) {
			servicePkgname = "com.chinaums.mpospluginpad";// pluginpad
		}
		intent.setClassName(servicePkgname,
				"com.chinaums.mpos.service.MposService");

		context.bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
	}

	@Override
	public void onDestroy() {
		if (mConnection != null)
			cordova.getActivity().unbindService(mConnection);
		super.onDestroy();
	}
}
