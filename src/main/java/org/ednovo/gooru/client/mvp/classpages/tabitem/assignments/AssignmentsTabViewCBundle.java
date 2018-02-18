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
package org.ednovo.gooru.client.mvp.classpages.tabitem.assignments;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/*
 * 
 * @fileName : AssignmentsTabViewCBundle.java
 *
 * @description : this class is used to set the CSS representation. 
 *
 *
 * @version : 1.0
 *
 * @date: Jul 10, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface AssignmentsTabViewCBundle extends ClientBundle {

	static final AssignmentsTabViewCBundle INSTANCE = GWT
			.create(AssignmentsTabViewCBundle.class);

	public interface AssignmentsCss extends CssResource {
		
		String assignmentContentContainer();
		String myFolderRightContentWrapperTwo();

		String myCollectionAssigntmentTitleText();

		String myCollectionAssigntmentDueDateText();

		String myCollectionAssigntmentDescriptionText();

		String myCollectionTitleDashedBorderContainer();

		String myCollectionDrandAndDropContainer();

		String myClasspageMyCollectionBg();

		String myClasspageMyCollectionText();

		String myCollectionDragHereImage();

		String myCollectionEditAndDeleteContainer();

		String myCollectionEditBg();

		String myCollectionEditText();

		String myCollectionDeleteContainer();

		String myCollectionDeleteText();

		String metaContainer();

		String metaInfoContainer();

		String myCollectionAssigntmentTitleTextInput();

		String myCollectionAssigntmentDueDateTextInputText();

		String myCollectionAssigntmentDescriptionTextArea();

		String dueDateLblContainerPanel();

		String dueDateContainerPanel();

		String dueDateTxtContainerPanel();
		
		String myAssigntmentTitlePanel();
		
		String myCollectionUpdateText();
		
		String myCollectionAssigntmentDescriptionPanel();
		
		String addCollectionToAssign();
		
		String copyPopupResourcesUserInputTwo();

		String copyPopupleftAndWidth();

		String copyPopupResources();

		String copyPopupResourcesButtonContainer();

		String copyPopupSettingPageAddBtn();

		String copyPopupSettingPageChangePasswordText();

		String copyPopupResourcesCancelButton();
		
		String collectionThumbnail();

		String collectionThumbnails();

		String collectionNotify();

		String collectionHover();

		String mandatoryLabel();
		
		String copyPopUpPanelStyle();
		
		String copyPopupResourcesPanel();
		
		String copyPopUpResourceListImage();
		
		String copyPopUpResourceListBoxTextTop();
		
		String copyPopupResourcesListPanel();
		
		String mandatoryLabelEdit();
		
		String mandatoryLabelEditDir();
		
		String mandatoryLabelEditDueDate();

		String displayNonePanel();
		
		String clickToExpand();
		
		String displayInlinePanel();
		
		String eventContainer();
		
		String noCollectionsMsg();
		
		String addCollectionToAssignPopUpBorder();
		
		String clear();
		String noCollectionMsgForAddPopUp();
		
		String loadingpanelImage();
		
		String addCollectionToAssignmentContainerClick();
		
		String noCollectionMsg();
		
		String cantFindCollectionInfo();
		
		String assignCollection();
		
		String btnContainer();
		
		String editAssignment();
		
	}

	@Source("assignments.css")
	AssignmentsCss css();

}
