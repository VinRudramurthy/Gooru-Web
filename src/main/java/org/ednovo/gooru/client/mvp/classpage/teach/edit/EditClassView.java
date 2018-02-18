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
package org.ednovo.gooru.client.mvp.classpage.teach.edit;

import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;


/**
 * @fileName : EditClassView.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 26-Jun-2015
 *
 * @Author tumbalam
 *
 * @Reviewer: 
 */
public class EditClassView extends BaseViewWithHandlers<EditClassViewUiHandlers> implements IsEditClassView {
	
	
	/*@UiField HTMLPanel startPanel;*/
	
	@UiField Button addCourseBtn,addStudentsBtn;
	
	@UiField PPanel classPanel,studentsPanel,notesPanel;
	
	@UiField Anchor moreLink;

	private static EditClassViewUiBinder uiBinder = GWT.create(EditClassViewUiBinder.class);

	interface EditClassViewUiBinder extends UiBinder<Widget, EditClassView> {
	}
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	public EditClassView() {
		setWidget(uiBinder.createAndBindUi(this));
		
		
		classPanel.setText(i18n.GL3431());
		studentsPanel.setText(i18n.GL3431());
		notesPanel.setText(i18n.GL3432());
		moreLink.setText(i18n.GL3433());
		
		addCourseBtn.setText(i18n.GL3428());
		addStudentsBtn.setText(i18n.GL3423());
		//startPanel.setVisible(false);
	}
	
	@UiHandler("addCourseBtn")
	public void addCourseToClass(ClickEvent event){
		getUiHandlers().addCourseToClass();
	}
	
	@UiHandler("addStudentsBtn")
	public void addStudentToClass(ClickEvent event){
			PlaceRequest request = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
			request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.TEACHER_CLASS_STUDENTES);
			request = request.with(UrlNavigationTokens.TEACHER_CLASS_SUBPAGE_VIEW, UrlNavigationTokens.TEACHER_CLASS_STUDENTS_ROASTER);
			AppClientFactory.getPlaceManager().revealPlace(request);
		
	}

}
