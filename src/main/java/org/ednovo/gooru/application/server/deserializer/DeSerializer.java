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

import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.shared.model.content.checkboxSelectedDo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class DeSerializer {
	
	private static String INTEGER_EQ = ".*\\d.*";
	private static final Logger logger = LoggerFactory.getLogger(DeSerializer.class);

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
				logger.error("Exception::", e);
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
				logger.error("Exception::", e);
			}
			return value != null ? value : null;
		} else {
			return null;
		}
	}
	
	protected static Integer getJsonInteger(JSONObject jsonObject, String key) {
		if (jsonObject != null && !jsonObject.isNull(key) && jsonObject.has(key)) {
			int value = 0;
			try {
				value = jsonObject.getInt(key);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
			return value != 0 ? value : 0;
		} else {
			return 0;
		}
	}
	
	protected static ArrayList<checkboxSelectedDo> getJsonArray(JSONObject jsonObject, String key) {
		ArrayList<checkboxSelectedDo> arrayObj = new ArrayList<checkboxSelectedDo>();
		if (jsonObject != null && !jsonObject.isNull(key) && jsonObject.has(key)) {
			JSONArray value = null;
			try {
				value = jsonObject.getJSONArray(key);
				for(int i=0; i<value.length();i++)
				{
					checkboxSelectedDo checkBoxObj = new checkboxSelectedDo();				
					checkBoxObj.setSelected(Boolean.valueOf(value.getJSONObject(i).getString("selected")));
					checkBoxObj.setValue(value.getJSONObject(i).getString("value"));
					arrayObj.add(checkBoxObj);
				}
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
			return arrayObj;
		} else {
			return arrayObj;
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
					logger.error("Exception::", e);
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
					logger.error("Exception::", e);
				}
			}
		}
		return values;
	}

}
