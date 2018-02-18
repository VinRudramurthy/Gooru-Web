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
package org.ednovo.gooru.client.mvp.play.collection.preview.home.assign;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;



/**
 * @author Search Team
 *
 */
public interface AssignPopUpCBundle extends ClientBundle{
	
	static final AssignPopUpCBundle INSTANCE = GWT.create(AssignPopUpCBundle.class);
	
	public interface  CollectionAssignCss extends CssResource{
	
		/////Assign Tab
		String mainContainer();
		
		String labelText();

		String dropdownContainer();

		String controlsContainer();

		String labelTitleText();

		String placeHolderText();

		String arrow();
		
		String inputTextBoxSyle();

		String assignmentsContainer();
		
		String loadingImageForSelfEditCustomize();

		String buttonAssignContainer();

		String scrollPanelContainer();
		
		String processing();
		
		String assignContentLoginCustomize();
		
		String classpageTitleText();
		
		String selectedClasspageText();
		
		String disableAssignButon();
		
		String loadingImageMainDiv();
		
		String loadingImageForSelfEdit();
		
		String loadingImageForShare();
		
		String activeAssignButton();
		//Success Popup.
		String assignDesc();
		
		String assignDescLogin();

		String assignMiddle();

		String assignCollection();

		String assignSprite();

		String assignCollectionIcon();

		String assignCollectionDesc();

		String assignTo();

		String assignAssignmentTitle();
		
		String assignHeader();

		String assignTitle();
		
		String assignCloseMarker();
				
		String assignCloseMark();
		
		String assign();
		
		String assignContent();
		
		String shareContent();
		
		String conent();
		
		String conentLogin();
		
		String conentLoginPop();
		
		String labelTextDisable();
		
		String labelNoClasspageText();
		
		String labelTeachLink();
		
		String noClasspageContianer();
		
		String loadingpanelImage();
		
		String container();
		
		String noAssignments();
		
		String assignClasspageTitle();

		String labelTitlePrivateText();
		
		String errorMessage();
		
		String popup();
		
		String popupInner();
		
		String popupHeader();
		
		String popupHeaderTitle();
		
		String popupContent();
		
		String contentTitle();
		
		String btn();
		
		String LoginText();
		

		String heightClasspagecontainer();

		
		String popupDesc();
		
		String popupcornerBg();
		
		String url();
		
		String shareTextarea();
		
		String sociaMediaText();
		
		String clear();
		
		String socialiconContainer();
		
		String socialiconOuterDiv();
		
		String fbButtonBg();
		
		String twitterButtonBg();
		
		String shareButtonBg();
		

		
		String btnAlign();
		
		String fb();
		
		String twitter();
		
		String email();
		
		String sprite();
		
		String contentBorder();
		
		String contentBorderShare();
		
		String inputBlockOuter();
		
		String inputBlock();
		
		String popupTitle();
		String popupTitleLogin();
		
		String popupDescLogin();
		String pwdTxt();
		String signUp();

		String classpageContainerFont();
		
		String shareLinkContainer();
		
		String shareLink();
		
		String shareLinkFlowPanel();
		
		String shareLinkBoxContainer();
		
		String shareLinkBox();
		
		String shareLinkSwitchUrl();
		
		String shareLinkSwitchEmbed();
		
		String shareLinkBoxDisabled();
		
		String labeldNoClassPageText();
		
		String noClassPageBanner();
		
		String assignClassPageBtn();
		
		String cpSuccessMsg();
		
		String txtboxDesc();
		
		String txtboxerrorColor();
		
		String popupButtons();
		
		String assignmentLabel();
		
		String popupTextbox();
		
		String collectionSearchRenamePopupButtons();
		
		String collectionSearchRenameTxtboxErrorColor();
		
        String gConnectButton();
		
		String divider();
		
		String dividerText();
		
		String privacyAlignStyle();
		
		String privacyContainer();
		
		String errorLabel();
		
		String popupStyle();
		String classContainer();
	}
	@Source("AssignPopUp.css")
	CollectionAssignCss css();

}
