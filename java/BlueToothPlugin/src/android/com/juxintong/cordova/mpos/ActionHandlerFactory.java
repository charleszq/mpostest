/**
 * 
 */
package com.juxintong.cordova.mpos;

import com.chinaums.mpos.service.IUmsMposService;

/**
 * @author Charles
 *
 */
public final class ActionHandlerFactory {

	private static ActionHandlerFactory instance;
	private ActionHandlerFactory() {
	}
	
	public static ActionHandlerFactory getInstance() {
		if( instance == null )
			instance = new ActionHandlerFactory();
		return instance;
	}
	
	public IActionHandler getActionHandler(IUmsMposService service, String action) {
		if( service == null ) return null;
		
		IActionHandler handler = null;
		if( MposConstants.ACTION_SETUP_DEVICE.equals( action ) ) {
			handler = new SetupDeviceActionHandler(service);
		} else if( MposConstants.ACTION_GET_DEVICE_ID.equals(action)) {
			handler = new GetDeviceIdActionHandler(service);
		} else if( MposConstants.ACTION_QUERY_ORDER.equals(action)) {
			handler = new QueryOrderActionHandler(service);
		} else if( MposConstants.ACTION_PLACE_ORDER.equals(action)) {
			handler = new PlaceOrderActionHandler(service);
		}
		return handler;
	}
}
