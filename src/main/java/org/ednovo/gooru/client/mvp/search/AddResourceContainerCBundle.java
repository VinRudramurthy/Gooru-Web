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
package org.ednovo.gooru.client.mvp.search;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;


public interface AddResourceContainerCBundle extends ClientBundle {
	static final AddResourceContainerCBundle INSTANCE = GWT.create(AddResourceContainerCBundle.class);
	
	public interface  NewAddresourcePopupCss extends CssResource{
		
		String assignmentContentContainer();
		
		String assignmentsCancel();
		
		String addButtonTitle();
		
		String assessmentAddContainer();
		
		String assignmentButtonsContainer();
		
		String navigateButtonsContainer();
		
		String assignmentMainContainer();

		String buttonSelected();
		
		//Container
		String assignmentContainerMainPanel();

		String assignmentContainer();
		
		String myFolderCollectionPopup();

		String assignmentTitleBlackBg();

		String assignmentTitle();

		String assignmentTabContent();

		String buttonDeSelected();

		String assignmentTabTitleBg ();
		
		String myFolderCollectionOk();
		
		String assignmentTitleLabel();

		String assignmentTextBoxContainer();

		String assignmentTextBox();
		
		String assignmentControlsContainer();
		
		String assignmentDueDateTextBox();
		
		String assignmentDateCalender();
		
		String assignmentDirectionTxtArea();
		
		String assignmentDirectionContainer();
		
		String mandatoryLabel();
		
		String assignmentPopupStyle();

		String registerErrorLabel();
		
	String gooruDateBoxError();
		
		String gooruDateError();
		
		String listBox();
		
		String assignmentCollectionsContainer();
		
		String assignmentDirectionsContainer();
		
		String popupContainer();
		
		String popupContent();
		
		String popupHeader();
		
		String popupHeaderText();
		String assignmentSuccessMainContainer();
		// floder level...
		
		String folderStucture();
		String folderLevel();
		String title();
		String root();
		String parent();
		String child();
		String open();
		String arrow();
		String empty();
		String selected();
		String foldercollection();
		String folderAssessment();
		String innerchild();
		String dropdowncontainer();
		String dropdownPlaceHolder();
		String dropdownListContainerScrollPanel();
		String assignmentsystemMessage();
		String successText();
		String successPopupContainer();
		String assignmentErrorMessage();
		String copyResourcePopupContainer();
		String assignmentDirectiomErrorMessage();
		String loadingText();
		String assignButton();
		
		String assignmentTitleLabelForOe();
		String primaryButton();
		String noCollectionMsgContainer();
		String noCollectionMsgOuterContainer();
		String emptyCollMsg();
		String subHeadingCollMsg();
		String selectionCountLabel();
		String selectionErrorLabel();
		String assignmentButtonsContainerCopy();
		String addTextButtonsContainer();
		String addPlayerStyle();
		String addMyCollectionsStyle();
		String addResourceTextStyles();
		String addCollectionTextStyles();
		String collectionChild1();
		String collectionChild2();
	}
	@Source("addresourcepopup.css")
	NewAddresourcePopupCss css();
}
