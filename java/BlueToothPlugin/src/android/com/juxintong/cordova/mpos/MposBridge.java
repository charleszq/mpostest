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
public class MposBridge extends CordovaPlugin implements ServiceConnection {

	private static final String TAG = MposBridge.class.getSimpleName();
	private IUmsMposService mUmsMposService;

	@Override
	public boolean execute(final String action, final JSONArray data,
			final CallbackContext cb) throws JSONException {
		if (mUmsMposService == null) {
			initService(new ServiceConnection() {

				@Override
				public void onServiceConnected(ComponentName arg0,
						IBinder service) {
					mUmsMposService = IUmsMposService.Stub.asInterface(service);
					IActionHandler handler = ActionHandlerFactory.getInstance()
							.getActionHandler(mUmsMposService, action);
					try {
						handler.handle(data, cb);
					} catch (JSONException e) {
					}
				}

				@Override
				public void onServiceDisconnected(ComponentName arg0) {
					mUmsMposService = null;
				}

			});
			return true;
		}

		IActionHandler handler = ActionHandlerFactory.getInstance()
				.getActionHandler(mUmsMposService, action);
		if (handler == null) {
			return false;
		} else {
			return handler.handle(data, cb);
		}
	}

	@Override
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
		super.initialize(cordova, webView);
		Log.d(TAG, "In initialize.");
		initService(this);
	}

	private void initService(ServiceConnection connection) {
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

		context.bindService(intent, connection, Service.BIND_AUTO_CREATE);
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "Destroying...");
		cordova.getActivity().unbindService(this);
		super.onDestroy();
	}

	@Override
	public void onServiceConnected(ComponentName arg0, IBinder service) {
		mUmsMposService = IUmsMposService.Stub.asInterface(service);
	}

	@Override
	public void onServiceDisconnected(ComponentName arg0) {
		mUmsMposService = null;
	}
}
