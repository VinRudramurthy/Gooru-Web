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
package org.ednovo.gooru.application.server;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ContextLoader;

/**
 * @author SearchTeam
 * 
 */
public class AppSessionHolder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3506283039185633839L;

	/**
	 * Class constructor 
	 */
	public AppSessionHolder() {
		perThreadRequest = new ThreadLocal<HttpServletRequest>();
		perThreadResponse = new ThreadLocal<HttpServletResponse>();
	}

	protected ThreadLocal<HttpServletRequest> perThreadRequest;
	protected ThreadLocal<HttpServletResponse> perThreadResponse;

	public static AppSessionHolder getInstance() {
		AppSessionHolder appSessionHolder = (AppSessionHolder) ContextLoader.getCurrentWebApplicationContext().getBean("appSessionHolder");
		return appSessionHolder;
	}

	public void setRequest(HttpServletRequest request) {
		perThreadRequest.set(request);
	}

	public HttpServletRequest getRequest() {
		return perThreadRequest.get();
	}

	public void setResponse(HttpServletResponse response) {
		perThreadResponse.set(response);
	}

	public HttpServletResponse getResponse() {
		return perThreadResponse.get();
	}

}
