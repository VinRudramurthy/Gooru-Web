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
import org.ednovo.gooru.application.shared.model.featured.FeaturedCollectionContentDo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FeaturedContentDeSerializer extends DeSerializer{

	private static final Logger logger = LoggerFactory.getLogger(FeaturedContentDeSerializer.class);
	/**
	 * Deserialize json object to list of {@link FeaturedCollectionContentDo}
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return list of featured collections
	 */

	public List<FeaturedCollectionContentDo> deSerializer(JsonRepresentation jsonRep){

		List<FeaturedCollectionContentDo> featuredContents = new ArrayList<FeaturedCollectionContentDo>();
		try {
			if(jsonRep != null){
				JSONArray featuredContentJsonArray = jsonRep.getJsonArray();
					for(int index=0; index < featuredContentJsonArray.length(); index++){
						JSONObject featuredContentJsonObj = featuredContentJsonArray.getJSONObject(index);
						featuredContents.add(JsonDeserializer.deserialize(featuredContentJsonObj.toString(), FeaturedCollectionContentDo.class));
					}
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
		return featuredContents;
	}
}
