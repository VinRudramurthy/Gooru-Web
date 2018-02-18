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

package org.ednovo.gooru.client.mvp.classpages.classlist;

import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.CollaboratorsDo;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;


/**
 * @fileName : IsClassListView.java
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
public interface IsClassListView extends IsViewWithHandlers<ClassListUiHandlers> {
	public void setClassPageDo(ClasspageDo classpageDo);
	public void setShareUrl(Map<String, String> shortenUrl);
	/**
	 * @function setLoadingPanelVisibility
	 *
	 * @created_date : Mar 17, 2014
	 *
	 * @description
	 *
	 *
	 * @param visibility
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	*/

	void setLoadingPanelVisibility(boolean visibility);
	/**
	 * @function displayMembersList
	 *
	 * @created_date : Mar 17, 2014
	 *
	 * @description
	 *
	 *
	 * @param result
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	*/

//	public void displayMembersList(
//			StudentsAssociatedListDo result);
	/**
	 * @function clearMembersListPanel
	 *
	 * @created_date : Mar 17, 2014
	 *
	 * @description
	 *
	 *
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	*/

	public void clearMembersListPanel();
	/**
	 * @function displayPendingMembersList
	 *
	 * @created_date : Mar 17, 2014
	 *
	 * @description
	 *
	 *
	 * @param lstPendingMembers
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	*/

	void displayPendingMembersList(List<CollaboratorsDo> lstPendingMembers,boolean isNew, int totalCount,boolean increasePageNum,boolean insertAtTop);

	/**
	 * @function enableInvite
	 *
	 * @created_date : Mar 17, 2014
	 *
	 * @description
	 *
	 *
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	*/

	public void enableInvite();
	/**
	 *
	 * @function displayInvitationSuccessPopUp
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param listSize
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void displayInvitationSuccessPopUp(int listSize);
	/**
	 * @function displayActiveMembersList
	 *
	 * @created_date : Apr 1, 2014
	 *
	 * @description
	 *
	 *
	 * @param lstActiveMembers
	 * @param isNew
	 * @param totalCount
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	*/
	public void displayActiveMembersList(List<CollaboratorsDo> lstActiveMembers,boolean isNew, int totalCount,boolean increasePageNum);
	/**
	 *
	 * @function insertPendingUserAfterDeletion
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param lstPendingMembers
	 * @parm(s) : @param isNew
	 * @parm(s) : @param totalCount
	 * @parm(s) : @param intPos
	 * @parm(s) : @param insertAtTop
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void insertPendingUserAfterDeletion(CollaboratorsDo lstPendingMembers, boolean isNew,int totalCount,int intPos,boolean insertAtTop);
	/**
	 *
	 * @function getSeeMorePendingLabel
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : Anchor
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public Anchor getSeeMorePendingLabel();
	/**
	 *
	 * @function insertActiveUserAfterDeletion
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param lstPendingMembers
	 * @parm(s) : @param isNew
	 * @parm(s) : @param totalCount
	 * @parm(s) : @param intPos
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void insertActiveUserAfterDeletion(CollaboratorsDo lstPendingMembers,boolean isNew, int totalCount, int intPos);
	/**
	 *
	 * @function getInviteButton
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : Button
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public Button getInviteButton();
	/**
	 *
	 * @function getLblPleaseWait
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : Label
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public Label getLblPleaseWait();
	/**
	 *
	 * @function createAutoSuggestBox
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void createAutoSuggestBox() ;
	/**
	 *
	 * @function clearDataAndErrorMessages
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void clearDataAndErrorMessages();
	/**
	 *
	 * @function removePendiUserWidget
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param membersViewVc
	 * @parm(s) : @param pendingFlag
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void removePendiUserWidget(MembersViewVc membersViewVc,boolean pendingFlag);
	/**
	 *
	 * @function getPendingMembersList
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void getPendingMembersList();
}
