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
 * @author Charles Zhang
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
		
		if (MposConstants.ACTION_SETUP_DEVICE.equals(action)) {
			return setupDevice( data, cb );
		} else if( MposConstants.ACTION_GET_DEVICE_ID.equals(action) ){
			return getDeviceId( data, cb );
		} else {
			return false;
		}
	}
	
	/**
	 * Gets the device id.
	 * @param data
	 * @param cb
	 * @return
	 * @throws JSONException
	 */
	private boolean getDeviceId( JSONArray data, final CallbackContext cb) throws JSONException {
		JSONObject obj = data.getJSONObject(0);
		Log.d( TAG, "get device id: " + obj.toString() );
		
		Bundle bundle = new Bundle();
		bundle.putString(MposConstants.BILLS_MID, obj.getString(MposConstants.BILLS_MID));
		bundle.putString(MposConstants.BILLS_TID, obj.getString(MposConstants.BILLS_TID));
		
		if( mUmsMposService != null ) {
			try {
				mUmsMposService.setDevice(bundle, new IUmsMposResultListener.Stub() {

					@Override
					public void umsServiceResult(Bundle result)
							throws RemoteException {
						try {
							JSONObject jsonResult = new JSONObject();
							jsonResult.put( MposConstants.RESULT_STATUS, result.getString(MposConstants.RESULT_STATUS));
							jsonResult.put( MposConstants.RESULT_INFO, result.getString(MposConstants.RESULT_INFO));
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
	
	/**
	 * Sets up device.
	 * @param data
	 * @param cb
	 * @return
	 * @throws JSONException
	 */
	private boolean setupDevice( JSONArray data, final CallbackContext cb) throws JSONException {
		JSONObject obj = data.getJSONObject(0);
		Log.i(TAG, "setupdevice" + obj.toString() );
		
		Bundle bundle = new Bundle();
		bundle.putString(MposConstants.BILLS_MID, obj.getString(MposConstants.BILLS_MID));
		bundle.putString(MposConstants.BILLS_TID, obj.getString(MposConstants.BILLS_TID));
		if( mUmsMposService != null ) {
			try {
				mUmsMposService.setDevice(bundle, new IUmsMposResultListener.Stub() {

					@Override
					public void umsServiceResult(Bundle result)
							throws RemoteException {
						JSONObject jsonResult = new JSONObject();
						try {
							jsonResult.put(MposConstants.RESULT_STATUS, result.getString(MposConstants.RESULT_STATUS));
							jsonResult.put(MposConstants.RESULT_INFO, result.getString(MposConstants.RESULT_INFO));
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
