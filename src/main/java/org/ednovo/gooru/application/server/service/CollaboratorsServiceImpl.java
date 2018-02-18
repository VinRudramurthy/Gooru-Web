package org.ednovo.gooru.application.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.service.CollaboratorsService;
import org.ednovo.gooru.application.server.annotation.ServiceURL;
import org.ednovo.gooru.application.server.request.JsonResponseRepresentation;
import org.ednovo.gooru.application.server.request.ServiceProcessor;
import org.ednovo.gooru.application.server.request.UrlToken;
import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.model.content.CollaboratorsDo;
import org.ednovo.gooru.shared.util.GooruConstants;
import org.json.JSONException;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 *
 * @fileName : CollaboratorsServiceImpl.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: Jan 22, 2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
@Service("collaborators")
@ServiceURL("/collaborators")
public class CollaboratorsServiceImpl extends BaseServiceImpl implements CollaboratorsService {

	/**
	 *
	 */
	private static final Logger logger= LoggerFactory.getLogger(CollaboratorsServiceImpl.class);
	private static final long serialVersionUID = 7664026537827418066L;

	/**
	 * @param contentId
	 * @param type
	 */
	@Override
	public Map<String, ArrayList<CollaboratorsDo>> getAssociatedCollaborators(String contentId, String type)
			throws GwtException {

		JsonRepresentation jsonRep = null;
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_COLLABORATORS, contentId, type);
		String url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.GROUP_BY_STATUS, GooruConstants.TRUE);
		logger.info("getAssociatedCollaborators:"+url);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();

		return deserializeCollaborators(jsonRep);
	}

	@Override
	public List<String> getSuggestionByName(String emailId){
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(),UrlToken.V2_SUGGEST_COLLAB, emailId);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());

		jsonRep =jsonResponseRep.getJsonRepresentation();
		return deserializeSuggestList(jsonRep);
	}


	public  List<String> deserializeSuggestList(JsonRepresentation jsonRep) {
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				return JsonDeserializer.deserialize(jsonRep.getJsonArray()
						.toString(), new TypeReference<List<String>>() {
				});
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return new ArrayList<String>();
	}

	@Override
	public ArrayList<CollaboratorsDo> addCollaboratorToCollectionById(List<String> lstEmailId, String collectionId){
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(),UrlToken.V2_COLLABORATORS, collectionId);
		logger.info("addCollaboratorToCollectionById:"+url);
		String formData = lstEmailId.toString();
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(),getRestPassword(), formData);
		jsonRep =jsonResponseRep.getJsonRepresentation();
		return deserializeCollaboratorsList(jsonRep);
	}
	/**
	 *
	 * @function removeCollaboratorsFromListByEmailIds
	 *
	 * @created_date : Jan 31, 2014
	 *
	 * @description
	 *
	 *
	 * @param collectionId
	 * @param emailId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	@Override
	public void removeCollaboratorsFromListByEmailIds(String collectionId, String toRemove){
		String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_DELETE_COLLABORATORS, collectionId);
		String url = AddQueryParameter.constructQueryParams(partialUrl, GooruConstants.DATA, toRemove);
		logger.info("removeCollaboratorsFromListByEmailIds:"+url);
		ServiceProcessor.delete(url, getRestUsername(), getRestPassword());
	}

	/**
	 *
	 * @function deserializeCollaboratorsList
	 *
	 * @created_date : Jan 27, 2014
	 *
	 * @description
	 *
	 *
	 * @param jsonRep
	 * @return
	 *
	 * @return : ArrayList<CollaboratorsDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public ArrayList<CollaboratorsDo> deserializeCollaboratorsList(JsonRepresentation jsonRep) {
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				return JsonDeserializer.deserialize(jsonRep.getJsonArray()
						.toString(), new TypeReference<ArrayList<CollaboratorsDo>>() {
				});
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return new ArrayList<CollaboratorsDo>();
	}

	/**
	 *
	 * @function deserializeCollaborators
	 *
	 * @created_date : Jan 27, 2014
	 *
	 * @description
	 *
	 *
	 * @param jsonRep
	 * @return
	 *
	 * @return : Map<String,ArrayList<CollaboratorsDo>>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public Map<String, ArrayList<CollaboratorsDo>> deserializeCollaborators(JsonRepresentation jsonRep) {
		try {
			if (jsonRep != null && jsonRep.getSize() != -1) {
				return JsonDeserializer.deserialize(jsonRep.getJsonObject()
						.toString(), new TypeReference<Map<String, ArrayList<CollaboratorsDo>>>() {
				});
			}
		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return new HashMap<String, ArrayList<CollaboratorsDo>>();
	}
}
