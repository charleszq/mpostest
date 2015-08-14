/**
 * 
 */
package com.juxintong.cordova.mpos;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

/**
 * @author Charles
 *
 */
public final class Helper {
	
	static Bundle generateBundle( JSONObject json ) {
		
		Bundle bundle = new Bundle();
		Iterator<String> keys = json.keys();
		while(keys.hasNext()) {
			String key = keys.next();
			String value = json.optString(key);
			Log.d(Helper.class.getSimpleName(), "Key: " + key + " value: " + value);
			Object vObject = processBundleValue(value);
			if( vObject instanceof Boolean ) {
				bundle.putBoolean(key, (boolean) vObject);
			} else {
				bundle.putString( key, vObject.toString());
			}
		}
		return bundle;
	}
	
	static Object processBundleValue( String value ) {
		if( "true".equalsIgnoreCase( value )) return true;
		if( "false".equalsIgnoreCase(value)) return false;
		return value;
	}
	
	static JSONObject generateJSONObject( Bundle bundle ) {
		JSONObject json = new JSONObject();
		for( String key: bundle.keySet()) {
			try {
				Log.d(Helper.class.getSimpleName(), "Key: " + key + " value: " + bundle.getString(key));
				json.putOpt(key, bundle.getString(key));
			} catch (JSONException e) {
				Log.w( Helper.class.getSimpleName(), e.getMessage());
			}
		}
		return json;
	}

}
