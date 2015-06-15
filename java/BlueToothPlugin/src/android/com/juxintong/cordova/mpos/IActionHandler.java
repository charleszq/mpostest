/**
 * 
 */
package com.juxintong.cordova.mpos;

import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author Charles
 *
 */
public interface IActionHandler {

	boolean handle( JSONArray data, final CallbackContext cb) throws JSONException;
}
