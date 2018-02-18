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
package org.ednovo.gooru.application.server.service;

import java.util.Map;

import org.restlet.data.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddQueryParameter {


	private static final Logger logger = LoggerFactory.getLogger(AddQueryParameter.class);

	AddQueryParameter(){

	}


	/**
	 * Constructing a URL with the restlet addQueryParameter for multiple params.
	 *
	 * @param url {@link String}
	 * @param params {@link Map}
	 *
	 * @return reference {@link Reference}
	 */
	public static String constructQueryParams(String url,Map<String, String> params){
		Reference reference = null;
		try {
			reference = new Reference(url);
			for(Map.Entry<String, String> map:params.entrySet()){
				reference.addQueryParameter(map.getKey(),map.getValue());
			}
		} catch (Exception e) {
			logger.error("AddQueryParameter Exception --- ",e);
		}
		return reference.toString();
	}


	/**
	 * Constructing a URL with the restlet addQueryParameter mathod.
	 *
	 * @param url {@link String}
	 * @param paramKey {@link String}
	 * @param paramValue {@link String}
	 *
	 * @return reference {@link Reference}
	 */
	public static String constructQueryParams(String url,String paramKey, String paramValue){
		Reference reference = null;
		try {
			reference = new Reference(url);
			reference.addQueryParameter(paramKey,paramValue);
		} catch (Exception e) {
			logger.error("AddQueryParameter Exception---",e);
		}
		return reference.toString();
	}


}
