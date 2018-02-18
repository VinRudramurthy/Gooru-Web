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

import org.ednovo.gooru.application.shared.model.search.AutoSuggestKeywordSearchDo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AutoSearchKeyWordDeSerializer extends DeSerializer{
	private static final String KEYWORD = "keyword";
	private static final Logger logger = LoggerFactory.getLogger(AutoSearchKeyWordDeSerializer.class);
	public List<AutoSuggestKeywordSearchDo> deserializeAutoKeyword(JsonRepresentation jsonRep) {
			List<AutoSuggestKeywordSearchDo> autoKeywordsList = new ArrayList<AutoSuggestKeywordSearchDo>();
	try {
			if(jsonRep!=null && jsonRep.getSize()!=-1){
			  if(jsonRep.getJsonArray()!=null){
					JSONArray autokeywordJsonArray=jsonRep.getJsonArray();
					for (int i = 0; i < autokeywordJsonArray.length(); i++) {
						JSONObject keywordJsonObject = autokeywordJsonArray.getJSONObject(i);
						String autokeyword=keywordJsonObject.getString(KEYWORD);
						AutoSuggestKeywordSearchDo autoSuggestKeywordSearchDo = new AutoSuggestKeywordSearchDo();
						autoSuggestKeywordSearchDo.setKeyword(autokeyword);
						autoKeywordsList.add(autoSuggestKeywordSearchDo);
					}
				}
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return autoKeywordsList;
	}
}
