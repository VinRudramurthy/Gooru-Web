
/**
 *
*/
package org.ednovo.gooru.client.mvp.classpages.classlist;

import java.util.List;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;


/**
 * @fileName : ClassListUiHandlers.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 13-Mar-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public interface ClassListUiHandlers extends BaseUiHandlers {

	/**
	 * @function addMembers
	 *
	 * @created_date : Mar 17, 2014
	 *
	 * @description
	 *
	 *
	 * @param classpageId
	 * @param emailIds
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	*/

	void addMembers(String classpageId, List<String> emailIds);
    /**
     * @description: update the class information
     * @param classPageId
     * @param collectionType
     * @param title, class tile
     * @param shareType
     */
	void updateClassPageInfo(String classPageId, String collectionType,
			String title, String shareType);

	void removeUserFromClass(ClasspageDo classpageDo, String emailId, int pendingOffSet, boolean pendingFlag,MembersViewVc membersViewVc);

	/**
	 * @function getMembersListByCollectionId
	 *
	 * @created_date : Mar 26, 2014
	 *
	 * @description
	 *
	 *
	 * @param classCode
	 * @param offSet
	 * @param pageSize
	 * @param statusType
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	*/

	void getMembersListByCollectionId(String classCode, int offSet,int pageSize, String statusType,boolean increasePageNum);

	/**
	 * @function getActiveMembersListByCollectionId
	 *
	 * @created_date : Mar 26, 2014
	 *
	 * @description
	 *
	 *
	 * @param classCode
	 * @param offSet
	 * @param pageSize
	 * @param statusType
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	*/

	void getActiveMembersListByCollectionId(String classCode, int offSet,int pageSize, String statusType,boolean increasePageNum,boolean getPendingMembers);
}
