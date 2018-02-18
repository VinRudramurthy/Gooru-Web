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
package org.ednovo.gooru.application.server.deserializer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Search Team
 *
 */
@Component
public class ShareDeSerializer extends DeSerializer {
	private static final String RAW_URL = "rawUrl";
	private static final String SHORTEN_URL = "shortenUrl";
	private static final String DECODE_RAW_URL = "decodeRawUrl";
	private static final Logger logger = LoggerFactory.getLogger(ShareDeSerializer.class);
	/**
	 * Deserialize json object to map that contains rawUrl and shortenUrl
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return rawUrl and shortenUrl values
	 */
	public Map<String, String> deserializeShortenUrl(JsonRepresentation jsonRep) {
		Map<String, String> shortenUrl = new HashMap<String, String>();
		try {
			JSONObject shortenUrlJsonObject = jsonRep.getJsonObject();
			shortenUrl.put(DECODE_RAW_URL, getJsonString(shortenUrlJsonObject, RAW_URL));
			shortenUrl.put(RAW_URL, URLEncoder.encode(getJsonString(shortenUrlJsonObject, RAW_URL),"UTF-8"));
			shortenUrl.put(SHORTEN_URL, getJsonString(shortenUrlJsonObject, SHORTEN_URL));
		} catch (JSONException e) {
			logger.error("Exception::", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("Exception::", e);
		}
		return shortenUrl;
	}
	public String deserializeShortenUrlFromJson(JsonRepresentation jsonRep) {
		String shortenUrl="";
		try {
			JSONObject shortenUrlJsonObject = jsonRep.getJsonObject();
			shortenUrl=getJsonString(shortenUrlJsonObject, SHORTEN_URL);
		} catch (JSONException e) {
			logger.error("Exception::", e);
		} 
		return shortenUrl;
	}
}
