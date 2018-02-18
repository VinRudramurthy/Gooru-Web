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
package org.ednovo.gooru.application.server.service;

import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.server.request.UrlToken;

/**
 * @author Search Team
 *
 */
public class UrlGenerator {

	public static String generateUrl(String endpoint, UrlToken token, Map<String, String> optionalParams, String... params) {
		String url = generateUrl(endpoint, token, params);
		if (optionalParams != null) {
			for (String key : optionalParams.keySet()) {
				url += "&" + key + "=" + optionalParams.get(key);
			}
		}
		return url;
	}

	public static String generateUrl(String endpoint, UrlToken token, String... params) {
		String url = token.getUrl();
		return endpoint + generateUrl(url, params);
	}

	public static String generateUrl(String endpoint, UrlToken token) {
		String url = token.getUrl();
		return endpoint + url;
	}

	public static String generateUrl(String url, String... params) {
		if (params != null) {
			for (int index = 0; index < params.length; index++) {
				if (params[index] != null){
					url = url.replace("{" + index + "}", params[index]);
				}else{
					AppClientFactory.printSevereLogger("Error in generating URL for : "+index+";  params : "+params.toString());
				}
			}
		}
		return  url;
	}

}
