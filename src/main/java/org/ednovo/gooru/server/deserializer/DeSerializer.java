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
package org.ednovo.gooru.server.deserializer;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class DeSerializer {
	
	private static String INTEGER_EQ = ".*\\d.*";

	protected static Integer stringtoInteger(JSONObject jsonObject, String key, Integer defaultValue) {
		Integer value = stringtoInteger(jsonObject, key);
		return value != null ? value : defaultValue;
	}

	protected static Integer stringtoInteger(JSONObject jsonObject, String key) {
		if (jsonObject != null && jsonObject.has(key)) {
			String value = null;
			try {
				value = jsonObject.getString(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return value != null && value.matches(INTEGER_EQ) ? Integer.parseInt(value) : null;
		} else {
			return null;
		}
	}

	protected static String getJsonString(JSONObject jsonObject, String key) {
		if (jsonObject != null && !jsonObject.isNull(key) && jsonObject.has(key)) {
			String value = null;
			try {
				value = jsonObject.getString(key);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return value != null ? value : null;
		} else {
			return null;
		}
	}

	protected static String convertJSONArrayToString(JSONArray jsonArray) {
		return convertJSONArrayToString(jsonArray, " , ");
	}

	protected static String convertJSONArrayToString(JSONArray jsonArray, String separator) {
		StringBuilder value = null;
		if (jsonArray != null) {
			value = new StringBuilder();
			for (int i = 0; i < jsonArray.length(); i++) {
				if (i != 0) {
					value.append(separator);
				}
				try {
					value.append(jsonArray.get(i));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return (value != null) ? value.toString() : "";
	}
	
	protected static List<String> convertJSONArrayToList(JSONArray jsonArray) {
		List<String> values = new ArrayList<String>();
		if (jsonArray != null) {
			for (int i = 0; i < jsonArray.length(); i++) {
				try {
					values.add(jsonArray.getString(i));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return values;
	}

}
