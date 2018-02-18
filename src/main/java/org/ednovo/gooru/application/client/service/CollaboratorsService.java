/**
 * 
 */
package org.ednovo.gooru.application.client.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.content.CollaboratorsDo;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * 
 * @fileName : CollaboratorsService.java
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
@RemoteServiceRelativePath("gwt-service/collaborators")
public interface CollaboratorsService extends BaseService {

	/**
	 * 
	 * @function getAssociatedCollaborators 
	 * 
	 * @created_date : Jan 29, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param contentId
	 * @param type
	 * @return
	 * @throws GwtException
	 * 
	 * @return : Map<String,ArrayList<CollaboratorsDo>>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public Map<String, ArrayList<CollaboratorsDo>> getAssociatedCollaborators(String contentId, String type)  throws GwtException, ServerDownException;
	/**
	 * 
	 * @function addCollaboratorToCollectionById 
	 * 
	 * @created_date : Jan 29, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param lstEmailId
	 * @param collectionId
	 * @return
	 * @throws GwtException
	 * 
	 * @return : List<CollaboratorsDo>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public ArrayList<CollaboratorsDo> addCollaboratorToCollectionById(List<String> lstEmailId, String collectionId)  throws GwtException, ServerDownException;

	/**
	 * @function getSuggestionByName 
	 * 
	 * @created_date : Jan 29, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @param emailId
	 * @return
	 * 
	 * @return : List<String>
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	public List<String> getSuggestionByName(String emailId)  throws GwtException, ServerDownException;
	/**
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
	
	public void removeCollaboratorsFromListByEmailIds(String collectionId, String toRemove) throws GwtException, ServerDownException;
	
}
