package org.ednovo.gooru.application.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.shared.model.content.CollaboratorsDo;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * 
 * @fileName : CollaboratorsAsync.java
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
public interface CollaboratorsServiceAsync extends BaseServiceAsync {
	
	void getAssociatedCollaborators(String contentId, String type, AsyncCallback<Map<String, ArrayList<CollaboratorsDo>>> callback);
	
	void addCollaboratorToCollectionById(List<String> lstEmailId, String collectionId, AsyncCallback<ArrayList<CollaboratorsDo>> callback);
	
	void getSuggestionByName(String emailId, AsyncCallback<List<String>> callback);
	
	void removeCollaboratorsFromListByEmailIds(String collectionId, String toRemove, AsyncCallback<Void> callback);
}
