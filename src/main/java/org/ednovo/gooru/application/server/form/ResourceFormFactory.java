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
package org.ednovo.gooru.application.server.form;

import org.ednovo.gooru.application.server.serializer.JsonSerializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Search Team
 *
 */
public class ResourceFormFactory {

	private static final Logger logger = LoggerFactory.getLogger(ResourceFormFactory.class);
	/**
	 * Generate form object, respect to individual object
	 * @param object instance of the {@link Object}
	 * @param objectName name of the object
	 * @return form
	 */
	public static Form generateDataForm(Object object, String objectName) {
		Form form = new Form();

		String data = "{\""+objectName+"\" : " + JsonSerializer.serialize(object) +"}";
		form.add("data", data);
		return form;
	}

	public static Form generateDataForm1(Object object, String objectName) {
		Form form = new Form();

		String data = "{\""+objectName+"\" : " + JsonSerializer.serialize(object) +"}";
		form.add("data",JsonSerializer.serialize(object));
		return form;
	}

	/**
	 * Generate form object, respect to individual object
	 * @param object instance of the {@link Object}
	 * @param objectName name of the object
	 * @return form
	 */
	public static String generateStringDataForm(Object object, String objectName) {

		String data = null;

		if (objectName != null){
			data = "{\""+objectName+"\" : " + JsonSerializer.serialize(object) +"}";
		}else{
			data = JsonSerializer.serialize(object);
		}
		return data;
	}

	/**
	 * Generate form object, respect to individual object
	 * @param object instance of the {@link Object}
	 * @param objectName name of the object
	 * @return form
	 */
	public static String generateStringMultiDataForm(Object object, String objectName) {

		String data = null;

		if (objectName != null){
			data = "{\""+objectName+"\" : [" + JsonSerializer.serialize(object) +"]}";
		}else{
			data = JsonSerializer.serialize(object);
		}
		return data;
	}

	/**
	 * Set collection meta info to {@link Form}
	 * @param title of the collection
	 * @param description of the collection
	 * @param grade of the collection
	 * @param sharing of the collection
	 * @param vocabulary of the collection
	 * @param taxonomyCode of the collection
	 * @param updateTaxonomyByCode that codeId or code
	 * @param action add or delete
	 * @return form
	 */
	public static Form updateCollection(String title, String description, String grade, String sharing, String vocabulary, String taxonomyCode, String updateTaxonomyByCode, String mediaType, String action) {
		Form form = new Form();
		if(title != null){
			form.add("title", title);
		}
		if(description != null){
			form.add("description", description);
		}
		if(grade != null){
			form.add("grade", grade);
		}
		if(sharing != null){
			form.add("sharing", sharing);
		}
		if(vocabulary != null){
			form.add("vocabulary", vocabulary);
		}
		if(taxonomyCode != null){
			form.add("taxonomyCode", taxonomyCode);
		}
		if(updateTaxonomyByCode != null){
			form.add("updateTaxonomyByCode", updateTaxonomyByCode);
		}
		if(mediaType != null){
			form.add("mediaType", mediaType);
		}

		if(action != null){
			form.add("action", action);
		}
		return form;
	}

	/**
	 * Set collection meta info to {@link Form}
	 * @param title of the collection
	 * @param description of the collection
	 * @param grade of the collection
	 * @param sharing of the collection
	 * @param vocabulary of the collection
	 * @param taxonomyCode of the collection
	 * @param updateTaxonomyByCode that codeId or code
	 * @param action add or delete
	 * @return form
	 */
	public static Form updateCollectionInfo(String title, String keyPointsVal) {
		Form form = new Form();


		String titleName = "title";
		String keyPointsKey = "keyPoints";
		String dataObj = "{\""+titleName+"\" : \"" + title +"\",\""+keyPointsKey+"\" : \"" + keyPointsVal +"\"}";

		String objectName = "collection";
		String data = "{\""+objectName+"\" : " + JsonSerializer.serialize(dataObj) +"}";
		form.add("data", data);
		return form;
	}
	/**
	 * Set collection meta info to {@link Form}
	 * @param title of the collection
	 * @param description of the collection
	 * @param grade of the collection
	 * @param sharing of the collection
	 * @param vocabulary of the collection
	 * @param taxonomyCode of the collection
	 * @param updateTaxonomyByCode that codeId or code
	 * @param action add or delete
	 * @return form
	 */
	public static Form updateCollectionLanguageObjective(String title, String languageObjective) {
		Form form = new Form();


		String titleName = "title";
		String keyPointsKey = "languageObjective";
		String collectionTypeKey = "collectionType";
		String collectionTypeValue = "collection";
		String addToShelfKey= "addToShelf";
		String addToShelfVal= "true";

		String dataObj = "{\""+titleName+"\" : \"" + title +"\",\""+collectionTypeKey+"\" : \"" + collectionTypeValue +"\",\""+keyPointsKey+"\" : \"" + languageObjective +"\"}";

		String objectName = "collection";

		String data = "{\""+objectName+"\" : " + JsonSerializer.serialize(dataObj) +",\""+addToShelfKey+"\" : \"" + addToShelfVal +"\"}";

		form.add("data", data);
		return form;
	}
	/**
	 * Set collection meta info to {@link Form}
	 * @param title of the collection
	 * @param description of the collection
	 * @param grade of the collection
	 * @param sharing of the collection
	 * @param vocabulary of the collection
	 * @param taxonomyCode of the collection
	 * @param updateTaxonomyByCode that codeId or code
	 * @param action add or delete
	 * @return form
	 */
	public static Form updateCollectionInstructionalMethod(String title, String instructionalMethod, Boolean selectedVal) {
		Form form = new Form();
		try
		{
		String instructionalMethodKey = "instructionalMethod";
		String instructionalMethodSelectedKey = "selected";
		String instructionalMethodSelectedValKey = "value";

		JSONObject instructionalMethodCompleteObject = new JSONObject();
		JSONArray instructionalMethodArray = new JSONArray();
		JSONObject instructionalMethodeObject = new JSONObject();
		instructionalMethodeObject.put(instructionalMethodSelectedKey, selectedVal);
		instructionalMethodeObject.put(instructionalMethodSelectedValKey, instructionalMethod);
		instructionalMethodArray.put(instructionalMethodeObject);

		instructionalMethodCompleteObject.put(instructionalMethodKey, instructionalMethodArray);

		String objectName = "collection";

		JSONObject collectionObject = new JSONObject();
		collectionObject.put(objectName, instructionalMethodCompleteObject);

		String data =  collectionObject.toString();

		form.add("data", data);
		}
		catch(Exception ex)
		{
			logger.error("Exception::", ex);
		}
		return form;
	}
	/**
	 * Set collection meta info to {@link Form}
	 * @param title of the collection
	 * @param description of the collection
	 * @param grade of the collection
	 * @param sharing of the collection
	 * @param vocabulary of the collection
	 * @param taxonomyCode of the collection
	 * @param updateTaxonomyByCode that codeId or code
	 * @param action add or delete
	 * @return form
	 */
	public static Form updateCollectionAudience(String title, String audience, Boolean selectedVal) {
		Form form = new Form();
		try
		{
		String instructionalMethodKey = "audience";
		String instructionalMethodSelectedKey = "selected";
		String instructionalMethodSelectedValKey = "value";

		JSONObject instructionalMethodCompleteObject = new JSONObject();
		JSONArray instructionalMethodArray = new JSONArray();
		JSONObject instructionalMethodeObject = new JSONObject();
		instructionalMethodeObject.put(instructionalMethodSelectedKey, selectedVal);
		instructionalMethodeObject.put(instructionalMethodSelectedValKey, audience);
		instructionalMethodArray.put(instructionalMethodeObject);

		instructionalMethodCompleteObject.put(instructionalMethodKey, instructionalMethodArray);

		String objectName = "collection";

		JSONObject collectionObject = new JSONObject();
		collectionObject.put(objectName, instructionalMethodCompleteObject);

		String data =  collectionObject.toString();

		form.add("data", data);
		}
		catch(Exception ex)
		{
			logger.error("Exception::", ex);
		}
		return form;
	}
	/**
	 * Set collection meta info to {@link Form}
	 * @param title of the collection
	 * @param description of the collection
	 * @param grade of the collection
	 * @param sharing of the collection
	 * @param vocabulary of the collection
	 * @param taxonomyCode of the collection
	 * @param updateTaxonomyByCode that codeId or code
	 * @param action add or delete
	 * @return form
	 */
	public static Form updateCollectionDepthOfKnowledge(String title, String depthOfKnowledge, Boolean selectedVal) {
		Form form = new Form();
		try
		{
		String depthOfKnowledgeKey = "depthOfKnowledges";
		String depthOfKnowledgeSelectedKey = "selected";
		String depthOfKnowledgeSelectedValKey = "value";

		JSONObject depthOfKnowledgeCompleteObject = new JSONObject();
		JSONArray depthOfKnowledgeArray = new JSONArray();
		JSONObject depthOfKnowledgeObject = new JSONObject();
		depthOfKnowledgeObject.put(depthOfKnowledgeSelectedKey, selectedVal);
		depthOfKnowledgeObject.put(depthOfKnowledgeSelectedValKey, depthOfKnowledge);
		depthOfKnowledgeArray.put(depthOfKnowledgeObject);

		depthOfKnowledgeCompleteObject.put(depthOfKnowledgeKey, depthOfKnowledgeArray);

		String objectName = "collection";

		JSONObject collectionObject = new JSONObject();
		collectionObject.put(objectName, depthOfKnowledgeCompleteObject);

		String data =  collectionObject.toString();
		form.add("data", data);
		}
		catch(Exception ex)
		{
			logger.error("Exception::", ex);
		}
		return form;
	}

	/**
	 * Set collection meta info to {@link Form}
	 * @param title of the collection
	 * @param description of the collection
	 * @param grade of the collection
	 * @param sharing of the collection
	 * @param vocabulary of the collection
	 * @param taxonomyCode of the collection
	 * @param updateTaxonomyByCode that codeId or code
	 * @param action add or delete
	 * @return form
	 */
	public static Form updateCollectionLearningSkills(String title, String depthOfKnowledge, Boolean selectedVal) {
		Form form = new Form();
		try
		{
		String learningSkillsKey = "learningSkills";
		String learningSkillsSelectedKey = "selected";
		String learningSkillsSelectedValKey = "value";

		JSONObject depthOfKnowledgeCompleteObject = new JSONObject();
		JSONArray depthOfKnowledgeArray = new JSONArray();
		JSONObject depthOfKnowledgeObject = new JSONObject();
		depthOfKnowledgeObject.put(learningSkillsSelectedKey, selectedVal);
		depthOfKnowledgeObject.put(learningSkillsSelectedValKey, depthOfKnowledge);
		depthOfKnowledgeArray.put(depthOfKnowledgeObject);

		depthOfKnowledgeCompleteObject.put(learningSkillsKey, depthOfKnowledgeArray);

		String objectName = "collection";

		JSONObject collectionObject = new JSONObject();
		collectionObject.put(objectName, depthOfKnowledgeCompleteObject);

		String data =  collectionObject.toString();

		form.add("data", data);
		}
		catch(Exception ex)
		{
			logger.error("Exception::", ex);
		}
		return form;
	}

	public static Form frameTagObject(String tagsStr) {
		Form form = new Form();
		try
		{

/*		List<String> mediaFeatureList = Arrays.asList(tagsStr.split(","));*/



		form.add("data", tagsStr);
		}
		catch(Exception ex)
		{
			logger.error("Exception::", ex);
		}
		return form;
	}

	/**
	 * Set collection item meta info to {@link Form}
	 * @param narration of the collectionItem
	 * @param narrationType of the collectionItem
	 * @param start of the collectionItem
	 * @param stop of the collectionItem
	 * @return form
	 */
	public static Form updateCollectionItem(String narration, String narrationType, String start, String stop){
		Form form = new Form();
		if(narration != null){
			form.add("narration", narration);
		}
		if(narrationType != null){
			form.add("narrationType", narrationType);
		}
		if(start != null){
			form.add("start", start);
		}
		if(stop != null){
			form.add("stop", stop);
		}
		return form;


	}
	public static Form updateNarrationItem(String narration, String narrationType){
		Form form = new Form();
		if(narration != null){
			form.add("narration", narration);
		}
		if(narrationType != null){
			form.add("narrationType", narrationType);
		}
		return form;


	}
}
