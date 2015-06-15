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

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import com.chinaums.mpos.service.IUmsMposService;

/**
 * @author Charles
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
		if( mUmsMposService == null ) {
				initService();
		}
		
		IActionHandler handler = ActionHandlerFactory.getInstance().getActionHandler(mUmsMposService, action);
		if( handler == null ) {
			return false;
		} else {
			return handler.handle(data, cb);
		}
	}

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		Log.d(TAG, "In initialize.");
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
		Log.d(TAG, "Destroying...");
		if (mConnection != null)
			cordova.getActivity().unbindService(mConnection);
		super.onDestroy();
	}
}
