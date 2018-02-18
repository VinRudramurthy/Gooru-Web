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

import org.ednovo.gooru.application.shared.model.code.CodeDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.json.JSONArray;
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
public class AutoCompleteDeSerializer extends DeSerializer {

	private static final String ATTRIBUTION = "values";
	private static final String SEARCH_RESULTS = "searchResults";
	private static final String CODE = "code";
	private static final String LABEL = "label";
	private static final String NAME = "name";
	private static final String CODE_ID = "codeId";
	private static final String AGGREGATOR = "values";

	private static final Logger logger = LoggerFactory.getLogger(AutoCompleteDeSerializer.class);
	/**
	 * Deserialize json object to List of Standards
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return list of standards
	 */
	public List<StandardFo> deserializeCenturys(JsonRepresentation jsonRep) {
		List<StandardFo> centurysList = new ArrayList<StandardFo>();
		try {
			JSONObject standardJsonObject = jsonRep.getJsonObject();
			JSONArray searchResults = standardJsonObject.getJSONArray(SEARCH_RESULTS);
			for (int i = 0; i < searchResults.length(); i++) {
				JSONObject code = searchResults.getJSONObject(i);
				StandardFo codeDo = new StandardFo();
				if(code.has(CODE_ID)){
					codeDo.setCodeId(code.getInt(CODE_ID));
					codeDo.setLabel(code.getString(NAME));
					centurysList.add(codeDo);
				}
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return centurysList;
	}

	/**
	 * Deserialize json object to List of Standards
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return list of standards
	 */
	public List<CodeDo> deserializeStandards(JsonRepresentation jsonRep) {
		List<CodeDo> standards = new ArrayList<CodeDo>();
		try {
			JSONObject standardJsonObject = jsonRep.getJsonObject();
			JSONArray searchResults = standardJsonObject.getJSONArray(SEARCH_RESULTS);
			for (int i = 0; i < searchResults.length(); i++) {
				JSONObject code = searchResults.getJSONObject(i);
				CodeDo codeDo = new CodeDo();
				codeDo.setCodeId(code.getInt(CODE_ID));
				codeDo.setCode(code.getString(CODE));
				codeDo.setLabel(code.getString(LABEL));
				standards.add(codeDo);
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return standards;
	}

	/**
	 * Deserialize json object to list of resource source
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return  list of source
	 */
	public List<String> deserializeSource(JsonRepresentation jsonRep) {
		List<String> sources = new ArrayList<String>();
		try {
			if (jsonRep.getSize() > 0) {
				JSONArray soruceJsonArray = jsonRep.getJsonArray();
				for (int sourceCount = 0; sourceCount < soruceJsonArray.length(); sourceCount++) {
					JSONObject source = soruceJsonArray.getJSONObject(sourceCount);
					JSONArray publisherArrayObj = new JSONArray(getJsonString(source, ATTRIBUTION));
					for(int i=0;i<publisherArrayObj.length();i++){
						sources.add(publisherArrayObj.getString(i).toString());
					}
					//sources.add(source.getString(ATTRIBUTION));
				}
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return sources;
	}
	/**
	 * Deserialize json object to list of resource source
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return  list of source
	 */
	public List<String> deserializeAggregator(JsonRepresentation jsonRep) {
		List<String> aggregator = new ArrayList<String>();
		try {
			if(jsonRep!=null){
			if (jsonRep.getSize() > 0) {
				JSONArray aggregatorJsonArray = jsonRep.getJsonArray();

				for (int aggregatorCount = 0; aggregatorCount < aggregatorJsonArray.length(); aggregatorCount++) {
					JSONObject aggregatorObj = aggregatorJsonArray.getJSONObject(aggregatorCount);
					//	aggregator.add(aggregatorObj.getString(AGGREGATOR));

					JSONArray aggregatorArrayObj = new JSONArray(getJsonString(aggregatorObj, AGGREGATOR));
					for(int i=0;i<aggregatorArrayObj.length();i++){
								aggregator.add(aggregatorArrayObj.getString(i).toString());

					}
				}
			}
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return aggregator;
	}
}
