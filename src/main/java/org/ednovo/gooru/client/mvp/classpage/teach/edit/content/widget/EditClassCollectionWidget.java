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
package org.ednovo.gooru.client.mvp.classpage.teach.edit.content.widget;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.ClassLessonDo;
import org.ednovo.gooru.client.mvp.classpage.event.SetCollectionTypeVisibilityEvent;
import org.ednovo.gooru.client.uc.PPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;


/**
 * @fileName : EditClassCollectionWidget.java
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
public class EditClassCollectionWidget extends Composite {
	
	@UiField PPanel titlePanel;
	
	@UiField CheckBox visiblityLbl;
	
	private long collectionId = 0L;

	private static EditClassCollectionWidgetUiBinder uiBinder = GWT.create(EditClassCollectionWidgetUiBinder.class);

	interface EditClassCollectionWidgetUiBinder extends UiBinder<Widget, EditClassCollectionWidget> {
	}

	
	public EditClassCollectionWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public EditClassCollectionWidget(ClassLessonDo lessonDo) {
		initWidget(uiBinder.createAndBindUi(this));
		
		titlePanel.setText(lessonDo.getTitle());
		collectionId = lessonDo.getCollectionId();
		titlePanel.getElement().setId(Long.toString(collectionId));
		titlePanel.getElement().setAttribute("alt",lessonDo.getTitle());
		titlePanel.getElement().setAttribute("title",lessonDo.getTitle());
		
		visiblityLbl.getElement().setId("visibltyCheckBoxId");
		
		if(lessonDo.isVisibility()){
			visiblityLbl.setValue(true);
		}
		
		if(visiblityLbl.isChecked()){
			visiblityLbl.setEnabled(false);
			visiblityLbl.getElement().getStyle().setCursor(Cursor.DEFAULT);
		}else{
			visiblityLbl.setEnabled(true);
			visiblityLbl.getElement().getStyle().clearCursor();
			visiblityLbl.addClickHandler(new VisiblityChangeHandler(lessonDo.getCollectionId(),visiblityLbl));
		}
	}
	
	private class VisiblityChangeHandler implements ClickHandler{
		
		long collectionId;
		
		CheckBox visiblityLbl;
		
		public VisiblityChangeHandler(long collectionId,CheckBox visiblityLbl){
			this.collectionId=collectionId;
			this.visiblityLbl=visiblityLbl;
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
		 */
		@Override
		public void onClick(ClickEvent event) {
			visiblityLbl.setEnabled(false);
			AppClientFactory.getEventBus().fireEvent(new SetCollectionTypeVisibilityEvent(collectionId));
		}
		
	}
	
	public void setChecked() {
		visiblityLbl.setChecked(true);
		visiblityLbl.setEnabled(false);
		visiblityLbl.getElement().getStyle().setCursor(Cursor.DEFAULT);
	}
	
	public long getCollectionId() {
		return collectionId;
	}
	
}
