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
package org.ednovo.gooru.application.server.deserializer;

import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ResourceDo;
import org.ednovo.gooru.application.shared.model.search.CollectionSearchResultDo;
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
public class CollectionDeSerializer extends DeSerializer {
	private static final String ITEM_SEQUENCE = "itemSequence";
	private static final String ITEM_TYPE = "itemType";
	private static final String NARRATION = "narration";
	private static final String NARRATION_TYPE = "narrationType";
	private static final String COLLECTION_ITEMS = "collectionItems";
	public static String TITLE = "title";
	public static String ASSETURI = "assetURI";
	public static String FOLDER = "folder";
	public static String GOORUOID = "gooruOid";
	public static String VIEWS = "views";
	public static String  USER = "user";
	public static String LASTNAME= "lastName";
	public static String GOORU_UId= "gooruUId";
	public static String USER_NAME_DISPLAY = "usernameDisplay";
	public static String FIRSTNAME = "firstName";
	public static String CREATOR = "creator";
	public static String THUMBNAILS = "thumbnails";
	public static String DIMENSIONS = "dimensions";
	public static String DEFAULTIMAGE = "defaultImage";
	public static String ESTIMATEDTIME="estimatedTime";
	public static String ADDS = "adds";
	public static String LIKES = "likes";
	public static String SHARES = "shares";
	public static String THUMBNAIL_URL = "url";
	public static String RESOURCE = "resource";

	private static final Logger logger = LoggerFactory.getLogger(CollectionDeSerializer.class);

	/**
	 * Deserialize json object to list of {@link CollectionSearchResultDo}
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return list of collection
	 */
	public List<CollectionSearchResultDo> deserializeResourceCollections(JsonRepresentation jsonRep) {
		List<CollectionSearchResultDo> collections = null;
		try {
			if (jsonRep != null) {
				collections = new ArrayList<CollectionSearchResultDo>();
				JSONArray collectionLearnMoreJson = jsonRep.getJsonArray();
				for (int collectionIndex = 0; collectionIndex < collectionLearnMoreJson.length(); collectionIndex++) {
					JSONObject collectionJson = new JSONObject(collectionLearnMoreJson.get(collectionIndex).toString());
					JSONObject thumbnailsJson = new JSONObject(getJsonString(collectionJson, THUMBNAILS));
					JSONObject creatorJson = new JSONObject(getJsonString(collectionJson, CREATOR));
					CollectionSearchResultDo collection = new CollectionSearchResultDo();
					collection.setUrl(getJsonString(thumbnailsJson, THUMBNAIL_URL));
					collection.setGooruOid(getJsonString(collectionJson, GOORUOID));
					collection.setTotalViews(stringtoInteger(collectionJson, VIEWS, 0));
					collection.setCreatorName(getJsonString(creatorJson, USER_NAME_DISPLAY));
				}
			}

		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return collections;
	}

	/**
	 * Deserialize json object to {@link CollectionDo}
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return collection object
	 */
	public CollectionDo deseializeCollection(JsonRepresentation jsonRep) {
		CollectionDo collection = null;
		try {
			if (jsonRep != null) {
				JSONObject collectionObject = jsonRep.getJsonObject();
				collection=JsonDeserializer.deserialize(collectionObject.toString(), CollectionDo.class);
			}

		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return collection;

	}

	/**
	 * Deserialize json object to {@link CollectionItemDo}
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return collection item object
	 */
	public CollectionItemDo deserializeReOrderCollectionItem(JsonRepresentation jsonRep) {
		CollectionItemDo collectionItemDo=null;
		try {
			JSONObject collectionItemJsonObject = jsonRep.getJsonObject();
			collectionItemDo = JsonDeserializer.deserialize(collectionItemJsonObject.toString(), CollectionItemDo.class);
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return collectionItemDo;
	}

	/**
	 * Deserialize json object to {@link CollectionDo} after copy the specified collection
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return collection object
	 */
	public CollectionDo deserializeCopyCollection(JsonRepresentation jsonRep){
		CollectionDo collectionDo = null;
		try{
			JSONObject collectionJsonObject = jsonRep.getJsonObject();
			collectionDo = JsonDeserializer.deserialize(collectionJsonObject.toString(), CollectionDo.class);
		}catch(JSONException exception){
			logger.error("Exception::", exception);
		}
		return collectionDo;
	}
	/**
	 * Deserialize json object to {@link CollectionItemDo}
	 * @param collectionItemJsonObject instance of {@link JSONObject}
	 * @return collectionItem object
	 */
	public CollectionItemDo deserializeCollectionItemDo(JSONObject collectionItemJsonObject) {
		CollectionItemDo collectionItemDo = new CollectionItemDo();
		if (collectionItemJsonObject != null) {
			try {
				if (collectionItemJsonObject.get(ITEM_SEQUENCE) != null) {
					//collectionItemDo.setItemSequence(collectionItemJsonObject.get(ITEM_SEQUENCE));
				}
				collectionItemDo.setItemType(collectionItemJsonObject.getString(ITEM_TYPE));
				if (collectionItemJsonObject.get(NARRATION) != null) {
					collectionItemDo.setNarration((String) collectionItemJsonObject.get(NARRATION));
				}
				if (collectionItemJsonObject.get(NARRATION_TYPE) != null) {
					collectionItemDo.setNarrationType((String) collectionItemJsonObject.get(NARRATION_TYPE));
				}
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return collectionItemDo;
	}

	/**
	 * Deserialize json object to list of {@link CollectionItemDo}
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return list of collectionItem
	 */
	public List<CollectionItemDo> getCollectionItems(JsonRepresentation jsonRep){
		List<CollectionItemDo> collectionItemDos = new ArrayList<CollectionItemDo>();
		try{
			JSONArray collectionItemsArray = jsonRep.getJsonArray();
			for(int i=0;i<collectionItemsArray.length();i++){
				CollectionItemDo collectionItemDo = JsonDeserializer.deserialize(collectionItemsArray.get(i).toString(), CollectionItemDo.class);
				collectionItemDos.add(collectionItemDo);
			}
		}catch(JSONException ex){
			logger.error("Exception::", ex);
		}
		return collectionItemDos;
	}

	/**
	 * Deserialize json object to {@link CollectionDo}
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return collection object
	 */
	public CollectionDo getCollection(JsonRepresentation jsonRep){
		CollectionDo collectionDo=null;
		try{
			JSONObject collectionObject = jsonRep.getJsonObject();
			JSONArray collectionItemsArray = collectionObject.getJSONArray(COLLECTION_ITEMS);
			collectionDo= JsonDeserializer.deserialize(collectionObject.toString(), CollectionDo.class);
			List<CollectionItemDo> collectionItemDos= new ArrayList<CollectionItemDo>();
			for(int i=0;i<collectionItemsArray.length();i++){
				//collectionItemDos.add(JsonDeserializer.deserialize(collectionItemsArray.getJSONObject(i).toString(), CollectionItemDo.class));
				collectionDo.setEstimatedTime(getJsonString(collectionObject, ESTIMATEDTIME));
				collectionDo.setAdds(getJsonString(collectionObject, ADDS));
				collectionDo.setLikes(getJsonString(collectionObject, LIKES));
				collectionDo.setShares(getJsonString(collectionObject, SHARES));
				collectionDo.setViews(getJsonString(collectionObject, VIEWS));
				JSONObject collectionItemObj = collectionItemsArray.getJSONObject(i);
				JSONObject resourceObject = collectionItemObj.getJSONObject(RESOURCE);
				CollectionItemDo collectionItem = JsonDeserializer.deserialize(collectionItemObj.toString(), CollectionItemDo.class);
				ResourceDo resourceDo = JsonDeserializer.deserialize(resourceObject.toString(), ResourceDo.class);
				collectionItem.setResource(resourceDo);
				collectionItemDos.add(collectionItem);
			}
			collectionDo.setCollectionItems(collectionItemDos);
		}catch(JSONException ex){
			logger.error("Exception::", ex);
		}
		return collectionDo;
	}

	/**
	 * Deserialize json object into list of {@link CollectionDo}
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return list of collection object
	 */
	public List<CollectionDo> getCollectionList(JsonRepresentation jsonRep){
		List<CollectionDo> collections=null;
		try{
			JSONArray collectionArray = jsonRep.getJsonArray();
			collections = new ArrayList<CollectionDo>();
			if(collectionArray.length() > 0 ){
				for(int i=0;i<collectionArray.length();i++){
					JSONObject collectionObject = collectionArray.getJSONObject(i);
					CollectionDo collectionDo = JsonDeserializer.deserialize(collectionObject.toString(), CollectionDo.class);
					List<CollectionItemDo> collectionItemDos= new ArrayList<CollectionItemDo>();
					JSONArray collectionItemsArray = collectionObject.getJSONArray(COLLECTION_ITEMS);
					for(int j=0;j<collectionItemsArray.length();j++){
						JSONObject collectionItemObj = collectionItemsArray.getJSONObject(j);
						JSONObject resourceObj = collectionItemObj.getJSONObject(RESOURCE);
						CollectionItemDo collectionItem = JsonDeserializer.deserialize(collectionItemObj.toString(), CollectionItemDo.class);
						ResourceDo resourceDo = JsonDeserializer.deserialize(resourceObj.toString(), ResourceDo.class);
						collectionItem.setResource(resourceDo);
						collectionItemDos.add(collectionItem);
					}
					collectionDo.setCollectionItems(collectionItemDos);
					collections.add(collectionDo);
				}
			}
		}catch(Exception ex){
			logger.error("Exception::", ex);
		}
		return collections;
	}

	/**
	 * Deserialize json object into {@link CollectionItemDo}
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return collection item
	 */
	public CollectionItemDo deseializeCollectionItem(JsonRepresentation jsonRep) {
		CollectionItemDo collectionItem = null;
		try {
			if (jsonRep != null) {
				JSONObject collectionItemObject = jsonRep.getJsonObject();
				collectionItem=JsonDeserializer.deserialize(collectionItemObject.toString(), CollectionItemDo.class);
			}

		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return collectionItem;

	}

	/**
	 * Deserialize json object to list of user {@link CollectionDo}
	 * @param jsonRep instance of {@link JsonRepresentation}
	 * @return list of collection
	 */
	public List<CollectionDo> getUserCollection(JsonRepresentation jsonRep){
		List<CollectionDo> collections=null;

		try {

			if (jsonRep != null) {
				JSONArray collectionArray = jsonRep.getJsonArray();
				collections = new ArrayList<CollectionDo>();

				if(collectionArray.length() > 0 ){
					for (int i = 0; i < collectionArray.length(); i++) {
						JSONObject collectionObj = collectionArray.getJSONObject(i);
						CollectionDo collection = JsonDeserializer.deserialize(collectionObj.toString(), CollectionDo.class);
						collections.add(collection);
					}

				}
				/*CollectionDo parentCollection = null;
				parentCollection = JsonDeserializer.deserialize(collectionArray.toString(), CollectionDo.class);
				Set<CollectionItemDo> collectionDos= new HashSet<CollectionItemDo>();
				JSONArray collectionArray = collectionArray.getJSONArray(COLLECTION_ITEMS);
				for(int j=0;j<collectionArray.length();j++){
					JSONObject collectionObj = collectionArray.getJSONObject(j);
					JSONObject collectionResourceObj = collectionObj.getJSONObject("resource");
					CollectionItemDo collection = JsonDeserializer.deserialize(collectionObj.toString(), CollectionItemDo.class);
					ResourceDo resourceDo = JsonDeserializer.deserialize(collectionResourceObj.toString(), ResourceDo.class);
					collection.setResourceDo(resourceDo);
					collectionDos.add(collection);
				}
				parentCollection.setCollectionItems(collectionDos);*/
			}

		} catch (Exception e) {
			logger.error("Exception::", e);
		}

		return collections;
	}

}
