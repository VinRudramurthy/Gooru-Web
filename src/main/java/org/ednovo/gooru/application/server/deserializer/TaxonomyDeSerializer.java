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

import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.model.code.LibraryCodeDo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * @author Search Team
 *
 */
@Component
public class TaxonomyDeSerializer extends DeSerializer {

	private static final String NODE = "node";
	private static final String LABEL = "label";
	private static final String CODE_ID = "codeId";
	private static final String TYPE = "type";
	private static final String DEPTH = "depth";
	private static final Logger logger = LoggerFactory.getLogger(TaxonomyDeSerializer.class);
	/**
	 * Deserialize json object to {@link LibraryCodeDo}
	 * @param depthLimit of Taxonomy
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return instance of {@link LibraryCodeDo}
	 */
	public LibraryCodeDo getTree(int depthLimit, JsonRepresentation jsonRep) {
		LibraryCodeDo codeDo = null;
		try {
			JSONObject taxonomyJsonObject = jsonRep.getJsonObject();
			JSONObject rootNode = taxonomyJsonObject.getJSONObject(NODE);
			codeDo = convertNodeToDo(rootNode, depthLimit);
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return codeDo;

	}

	/**
	 * Deserialize json object to {@link LibraryCodeDo}
	 * @param nodeJsonObject instance of {@link JsonRepresentation}
	 * @param depthLimit of Taxonomy
	 * @return {@link LibraryCodeDo}
	 */
	public LibraryCodeDo convertNodeToDo(JSONObject nodeJsonObject, int depthLimit) {
		LibraryCodeDo libraryCodeDo = new LibraryCodeDo();
		try {
			if (nodeJsonObject.has(LABEL)) {
				libraryCodeDo.setLabel((String) nodeJsonObject.get(LABEL));
			}
			libraryCodeDo.setCodeId((Integer) nodeJsonObject.get(CODE_ID));
			libraryCodeDo.setType((String) nodeJsonObject.get(TYPE));
			libraryCodeDo.setDepth((Integer) nodeJsonObject.get(DEPTH));
			if (nodeJsonObject.has(NODE)) {
				List<LibraryCodeDo> codeDos = new ArrayList<LibraryCodeDo>();
				JSONArray childNodes = nodeJsonObject.getJSONArray(NODE);
				for (int i = 0; i < childNodes.length(); i++) {
					JSONObject childNode = childNodes.getJSONObject(i);
					int depth = (Integer) childNode.get(DEPTH);
					if (depth <= depthLimit || depthLimit == 0) {
						LibraryCodeDo codeDo = convertNodeToDo(childNode, depthLimit);
						if (codeDo != null) {
							codeDos.add(codeDo);
						}
					}
				}
				libraryCodeDo.setNode(codeDos);
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return libraryCodeDo;
	}

	/**
	 * Deserialize json object to list of {@link LibraryCodeDo}
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return list of {@link LibraryCodeDo}
	 */
	public List<LibraryCodeDo> getCourse(JsonRepresentation jsonRep)  {
		List<LibraryCodeDo> libraryCodeDo = null;
		if (jsonRep != null) {
			try {
				libraryCodeDo = JsonDeserializer.deserialize(jsonRep.getJsonArray().toString(), new TypeReference<List<LibraryCodeDo>>() {
				});
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return libraryCodeDo;
	}
}
