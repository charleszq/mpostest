/**
 * 
 */
package com.juxintong.cordova.mpos;

import com.chinaums.mpos.service.IUmsMposService;

/**
 * @author Charles
 * 
 */
public abstract class AbstractActionHandler implements IActionHandler {

	protected String TAG;
	protected IUmsMposService mUmsMposService;

	/**
	 * 
	 */
	public AbstractActionHandler(IUmsMposService service) {
		this.TAG = getClass().getSimpleName();
		this.mUmsMposService = service;
	}

}
