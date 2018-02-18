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
package org.ednovo.gooru.shared.util;

import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;


public class ResourceImageUtil {
	public static String protocol="http";
	public static String getYoutubeVideoId(String url) { 
		String pattern = "youtu(?:\\.be|be\\.com)/(?:.*v(?:/|=)|(?:.*/)?)([a-zA-Z0-9-_]+)";
		String videoId = null; 
	    try {
	    	RegExp reg = RegExp.compile(pattern, "gi");
	    	MatchResult res = reg.exec(url);
	    	videoId = res.getGroup(1);
	    } catch (Exception e) {
	    	
		}
		return videoId;
	}
	
	public static String  youtubeImageLink(String videoId) {
		return protocol+"://img.youtube.com/vi/"+videoId+"/1.jpg";
	}
	
	public static String youtubeImageLink(String videoId, String protocol) {
		return protocol+"//img.youtube.com/vi/"+videoId+"/1.jpg";
	}
	
	public static String ensure_has_protocol(final String url)
	{
	    if (!url.startsWith("http://"))
	    {
	        return "http://" + url;
	    }
	    return url;
	}
}
