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
package org.ednovo.gooru.client.mvp.classpages.tabitem.assignments.collections;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.uc.DateBoxUc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
/**
 * 
 * @fileName : EditToolBarView.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 07-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class EditToolBarView extends Composite{
	
	@UiField Button saveButton,cancelButton;
	
	@UiField HTMLPanel dueDatePanel,dueDateText;
	
	@UiField Label savingText;
	
	public DateBoxUc dateBoxUc;
	
	private static EditToolBarViewUiBinder uiBinder = GWT.create(EditToolBarViewUiBinder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);
	
	public interface EditToolBarViewUiBinder extends UiBinder<Widget, EditToolBarView> {}
	
	public EditToolBarView(boolean isDuedateEdited){
		initWidget(uiBinder.createAndBindUi(this));
		setStaticTexts();
		savingText.setVisible(false);
		if(isDuedateEdited){
			dueDatePanel.clear();
			dateBoxUc = new DateBoxUc(false, false,false);
			dueDatePanel.add(dateBoxUc);
		}
	}
	/**
	 * 
	 * @function setStaticTexts 
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
	public void setStaticTexts(){
		cancelButton.setText(i18n.GL0142());
		cancelButton.getElement().setId("btnCancel");
		cancelButton.getElement().setAttribute("alt",i18n.GL0142());
		cancelButton.getElement().setAttribute("title",i18n.GL0142());
		
		saveButton.setText(i18n.GL0141());
		saveButton.getElement().setId("btnSave");
		saveButton.getElement().setAttribute("alt",i18n.GL0141());
		saveButton.getElement().setAttribute("title",i18n.GL0141());
		
		savingText.setText(i18n.GL0808());
		savingText.getElement().setId("lblSaving");
		savingText.getElement().setAttribute("alt",i18n.GL0808());
		savingText.getElement().setAttribute("title",i18n.GL0808());
		dueDatePanel.getElement().setId("pnlDueDatePanel");
		dueDateText.getElement().setId("pnlDueDate");
	}
}
