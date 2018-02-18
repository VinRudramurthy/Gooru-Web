/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
 * 
 *  http://www.goorulearning.org/
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
/**
 * 
*/
package org.ednovo.gooru.application.shared.model.content;

import java.util.List;
import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * @fileName : PermissionsDO.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 05-Jun-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer: 
 */
public class PermissionsDO implements IsSerializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5352082220908500490L;
	/**
	 * 
	 */

	//private Array permissions;
	//private String[] permissions;
	private List<String> permissions;

	/** 
	 * This method is to get the permissions
	 */
	public List<String> getPermissions() {
		return permissions;
	}

	/** 
	 * This method is to set the permissions
	 */
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	/** 
	 * This method is to get the permissions
	 *//*
	public String[] getPermissions() {
		return permissions;
	}

	*//** 
	 * This method is to set the permissions
	 *//*
	public void setPermissions(String[] permissions) {
		this.permissions = permissions;
	}*/
	
}
