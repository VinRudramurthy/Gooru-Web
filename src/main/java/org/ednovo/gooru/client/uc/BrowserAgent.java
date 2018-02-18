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
package org.ednovo.gooru.client.uc;

public class BrowserAgent {

	public static native String returnFormFactorView() /*-{
	  	var ua = navigator.userAgent.toLowerCase();
	  	if (ua.indexOf("iphone") != -1 || ua.indexOf("ipod") != -1) {
	    	return "mobile";
	  	} else if (ua.indexOf("ipad") != -1) {
	    	return "tablet";
	  	} else if (ua.indexOf("android") != -1 || ua.indexOf("mobile") != -1) {
	    	var dpi = 160;
	    	var width = $wnd.screen.width / dpi;
	    	var height = $wnd.screen.height / dpi;
	    	var size = Math.sqrt(width*width + height*height);
	    	return (size < 6) ? "mobile" : "tablet";
	  	}
	  	return "desktop";
  	}-*/;
	public static native String returnFormFactorWithSizeView() /*-{
	  	var ua = navigator.userAgent.toLowerCase();
	  	var size=0;
	  	var returnValue="desktop";
	  	if (ua.indexOf("iphone") != -1 || ua.indexOf("ipod") != -1) {
//	  		size = getViewPort();
	    	returnValue = "iphone";
	  	} else if (ua.indexOf("ipad") != -1) {
	    	returnValue = "ipad";
	  	} else if (ua.indexOf("android") != -1 || ua.indexOf("mobile") != -1) {
	    	var dpi = 160;
	    	var width = $wnd.screen.width / dpi;
	    	var height = $wnd.screen.height / dpi;
	    	size = Math.sqrt(width*width + height*height);
	    	returnValue = (size < 6) ? "mobile" : "tablet";
	  	}
	  	return returnValue+"-"+size;
	}-*/;
	
	/*Unused Method
	 * public static native String getViewPort() -{

		var viewPortWidth=0;
		var viewPortHeight=0;
		
		// the more standards compliant browsers (mozilla/netscape/opera/IE7) use window.innerWidth and window.innerHeight
		if (typeof window.innerWidth != 'undefined') {
			viewPortWidth = $wnd.window.innerWidth;
			viewPortHeight = $wnd.window.innerHeight;
		}
		// IE6 in standards compliant mode (i.e. with a valid doctype as the first line in the document)
		else if (typeof document.documentElement != 'undefined'&& typeof document.documentElement.clientWidth != 'undefined' && document.documentElement.clientWidth != 0) {
			viewPortWidth = document.documentElement.clientWidth;
			viewPortHeight = document.documentElement.clientHeight;
		}
		// older versions of IE
		else {
			viewPortWidth = document.getElementsByTagName('body')[0].clientWidth;
			viewPortHeight = document.getElementsByTagName('body')[0].clientHeight;
		}
		return viewPortHeight;
	}-;*/
	
	public static native String getWebBrowserClient() /*-{
		return navigator.userAgent.toLowerCase();
	}-*/;

	public static native String loadCssFile(String filename, String fileType) /*-{
		var style;
		if (fileType=='js'){
			style=document.createElement('script')
			style.setAttribute("type","text/javascript")
			style.setAttribute("src", filename)
		} else if (fileType=='css') {
			style = $doc.createElement('link');
			style.setAttribute('rel', 'stylesheet');
	        style.setAttribute('type', 'text/css');
	     	style.setAttribute('href', filename);
		}
		$doc.getElementsByTagName('head')[0].appendChild( style );
	}-*/;
	/**
	 * 
	 * @function isDevice 
	 * 
	 * @created_date : 26-Feb-2015
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @return
	 * 
	 * @return : boolean
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */

	public static native boolean isDevice() /*-{
	  	var ua = navigator.userAgent.toLowerCase();
	  	var returnValue=false;
	  	if (ua.indexOf("iphone") != -1 || ua.indexOf("ipod") != -1) {
	    	returnValue = true;
	  	} else if (ua.indexOf("ipad") != -1) {
	    	returnValue = true;
	  	} else if (ua.indexOf("android") != -1 || ua.indexOf("mobile") != -1) {
	  		returnValue = true;
	  	}
	  	return returnValue;
	}-*/;
}
