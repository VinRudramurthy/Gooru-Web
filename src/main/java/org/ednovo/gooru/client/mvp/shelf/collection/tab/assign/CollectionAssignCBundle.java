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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.assign;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;



/**
 * @author Search Team
 *
 */
public interface CollectionAssignCBundle extends ClientBundle{
	
	static final CollectionAssignCBundle INSTANCE = GWT.create(CollectionAssignCBundle.class);
	
	public interface  CollectionAssignCss extends CssResource{
	
		/////Assign Tab
		String mainContainer();
		
		String labelText();

		String dropdownContainer();

		String controlsContainer();

		String labelTitleText();
		
		String directionsErrorLbl();

		String placeHolderText();

		String arrow();

		String assignmentsContainer();

		String buttonAssignContainer();

		String scrollPanelContainer();
		
		String classpageTitleText();
		
		String selectedClasspageText();
		
		String disableAssignButon();
		
		String activeAssignButton();
		//Success Popup.
		String assignDesc();

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
		
		String conent();
		
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
		
		String contentSuccess();
		
		String assignClassPageBtn();
		
		String assignmentLabel();
		
		String directionsTextArea();
		
		String duedateContainer();
		
		String charLimitStyle();
	}
	@Source("CollectionAssign.css")
	CollectionAssignCss css();

}
