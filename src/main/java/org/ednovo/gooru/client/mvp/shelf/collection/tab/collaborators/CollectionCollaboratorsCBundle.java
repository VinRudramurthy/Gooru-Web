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
package org.ednovo.gooru.client.mvp.shelf.collection.tab.collaborators;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * 
 * @fileName : CollectionCollaboratorsCBundle.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: Jan 23, 2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public interface CollectionCollaboratorsCBundle extends ClientBundle{
	
	static final CollectionCollaboratorsCBundle INSTANCE = GWT.create(CollectionCollaboratorsCBundle.class);
	
	public interface CollaboratorsCss extends CssResource {
		String collaborators();
		String collaboratorContainer();
		String collaboratorBg();
		String collaboratorContent();
		String collaboratorTitle();
		String collaboratorSubTitle();
		String collaboratorHeading();
		String collaboratorDesc();
		String buttonContainer();
		String collaboratorInputBox();
		String collaboratorContentPanel();
		String collaboratorPanelTitle();
		String radioButtonContainer();
		String panelCollaborators();
		String usernameCss();
		String emailIdCss();
		String radioButtonImg();
		String collaboratorBtn();
		String collaboratorSectionTitle();
		String collaboratorBorderBottom();
		String collaboratorYou();
		String photo();
		String name();
		String remove();
		String error();
		String rdPosition();
		String collabListPanel();
		String collaboratorViewPanel();
		String btnRemoveCollab();
		String buttonTooltip();
		String tooltipContainer();
		String arrowBorder();
		String arrow();
		String tooltipContent();
		
		
		/* Delete popup*/
		String popup();		
		String popupInner();
		String popupHeader();
		String header();
		String delete();
		String popupContent(); 
		String icon();
		String complete();
		String okCancel();
		String desc();
		String notes();
		String pleaseWait();
		String deleteTextBox();
		String deleteContainer();
		String tag();
		String deleteTextLable();
		
		String privacyAlignStyle();
	}
	@Source("CollectionCollaborators.css")
	CollaboratorsCss css();
}
