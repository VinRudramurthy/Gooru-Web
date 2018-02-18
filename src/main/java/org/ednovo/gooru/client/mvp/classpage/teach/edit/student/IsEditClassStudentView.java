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
package org.ednovo.gooru.client.mvp.classpage.teach.edit.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.IsViewWithHandlers;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.CollaboratorsDo;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.Label;


/**
 * @fileName : IsEditStudentView.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 03-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer: 
 */
public interface IsEditClassStudentView extends IsViewWithHandlers<EditClassStudentViewUiHandler>{
	public void setReportView();
	public void createAutoSuggestBox() ;
	public void setNavigationTab();
	public void setRoasterView();
	public void setClassView(ClasspageDo classpageDo);
	public void setShortenUrl(Map<String, String> shortenUrl);
	
	public void displayInvitationSuccessPopUp(int size);
	public Label getLblPleaseWait();
		
	public Button getInviteButton();
		
	void displayPendingMembersList(List<CollaboratorsDo> lstPendingMembers,boolean isNew, int totalCount,boolean increasePageNum,boolean insertAtTop);
	
	public void insertPendingUserAfterDeletion(CollaboratorsDo lstPendingMembers, boolean isNew,int totalCount,int intPos,boolean insertAtTop);
	
	public void displayActiveMembersList(List<CollaboratorsDo> lstActiveMembers,boolean isNew, int totalCount,boolean increasePageNum);
	
	public void enableInvite();
	
	public void insertActiveUserAfterDeletion(CollaboratorsDo lstPendingMembers,boolean isNew, int totalCount, int intPos);
	
	public void getPendingMembersList();
		
	public void removePendingUserWidget(MembersViewVc membersViewVc,boolean pendingFlag);
		
	public void setReportDataView();
		
	public void clearAllErrorLabel();
	
}
