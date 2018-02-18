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
package org.ednovo.gooru.client.mvp.classpage.teach.edit.content;

import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClassLessonDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.CssTokens;
import org.ednovo.gooru.client.uc.H2Panel;
import org.ednovo.gooru.client.uc.H4Panel;
import org.ednovo.gooru.client.uc.PPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


/**
 * @fileName : EditClassContentView.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 02-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer: 
 */
public class EditClassContentView extends BaseViewWithHandlers<EditClassContentViewUiHandler> implements IsEditClassContentView {
	
	
	@UiField TextBox scoreTextBox;
	
	@UiField Button saveBtn,createCourseBtn;
	
	@UiField Label errorLabel,saveLblText;
	
	@UiField HTMLPanel coursePanel;
	
	@UiField InlineLabel titleLbl,assessmentsLbl,collectionLbl;
	
	@UiField H4Panel assignedCourse,lessonPacingPanel;
	
	@UiField H2Panel pacingWorks;
	
	@UiField PPanel courseAssigned,studentsNotes,minimumScrPanel,miniNotesPanel,pacingNotes;
	
	ClasspageDo classpageDo;
	
	int miniScore;

	String unitId;
	
	boolean hasClickedOnDropDwn = false;
	
	MessageProperties i18n = GWT.create(MessageProperties.class);
	
	List<Long> collectionIds = new ArrayList<Long>();
	
	List<ClassLessonDo> classLessonDos = new ArrayList<ClassLessonDo>();
	
	boolean visiblity;
	

	private static EditClassContentViewUiBinder uiBinder = GWT.create(EditClassContentViewUiBinder.class);

	interface EditClassContentViewUiBinder extends
			UiBinder<Widget, EditClassContentView> {
	}

	public EditClassContentView() {
		setWidget(uiBinder.createAndBindUi(this));
		setId();
		saveBtn.setEnabled(false);
		saveBtn.addStyleName(CssTokens.DISABLED);
		coursePanel.setVisible(false);
		errorLabel.setVisible(false);
		
		scoreTextBox.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				String score = scoreTextBox.getText();
				if(!scoreTextBox.isReadOnly()){
					if(score.isEmpty()){
						errorLabel.setText("Please enter the minimum score");
						errorLabel.getElement().getStyle().setColor("orange");
						errorLabel.setVisible(true);
						saveEnabled(false);
						saveBtn.addStyleName(CssTokens.DISABLED);
					}else if(score != null || score != ""){
						if(Integer.parseInt(score) >100 || Integer.parseInt(score) <0){
							errorLabel.setText(i18n.GL3425());
							errorLabel.getElement().getStyle().setColor("orange");
							errorLabel.setVisible(true);
							saveEnabled(false);
							saveBtn.addStyleName(CssTokens.DISABLED);
						}else{
							miniScore=Integer.valueOf(score);
							saveEnabled(true);
							saveBtn.removeStyleName(CssTokens.DISABLED);
							errorLabel.setVisible(false);
						}
					}
				}
			}
		});
		scoreTextBox.setMaxLength(3);
		scoreTextBox.getElement().setAttribute("maxlength", "3");
		scoreTextBox.addKeyPressHandler(new NumbersOnly());
		createCourseBtn.addClickHandler(new AddCourseHandler());
	}
	
	@UiHandler("saveBtn")
	public void saveClass(ClickEvent event){
		String score = Long.toString(miniScore);
		saveLblText.setVisible(true);
		saveBtn.setVisible(false);
		saveEnabled(false);
		saveBtn.addStyleName(CssTokens.DISABLED);
		getUiHandlers().updateClass(score);
	}
	
	private class NumbersOnly implements KeyPressHandler{

		@Override
		public void onKeyPress(KeyPressEvent event) {
			
			if(!scoreTextBox.isReadOnly()){
				if (!Character.isDigit(event.getCharCode()) 
	                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_TAB 
	                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE
	                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_SHIFT
	                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_ENTER
	                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_LEFT
	                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_RIGHT
	                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_DELETE){
	                ((TextBox) event.getSource()).cancelKey();
	                
	            }else if(event.getNativeEvent().getKeyCode() == 46){
					((TextBox) event.getSource()).cancelKey();
				}else{
	            	saveEnabled(true);
	    			saveBtn.removeStyleName(CssTokens.DISABLED);
	    			errorLabel.setVisible(false);
	            }
			}
		}
	}
	
	public void saveEnabled(boolean isEnabled){
		saveBtn.setEnabled(isEnabled);
	}
	
	public void setId(){
		
		saveBtn.setText(i18n.GL0141());
		saveBtn.getElement().setId("saveBtnId");
		saveBtn.getElement().setAttribute("alt",i18n.GL0141());
		saveBtn.getElement().setAttribute("title",i18n.GL0141());
		
		errorLabel.getElement().setId("errorLblId");
		saveLblText.setText(i18n.GL3426());
		saveLblText.getElement().setId("saveLblTxtId");
		saveLblText.setVisible(false);
		
		assignedCourse.setText(i18n.GL3440());
		courseAssigned.setText(i18n.GL3441());
		studentsNotes.setText(i18n.GL3442());
		createCourseBtn.setText(i18n.GL3443());
		lessonPacingPanel.setText(i18n.GL3444());
		minimumScrPanel.setText(i18n.GL3445());
		miniNotesPanel.setText(i18n.GL3446());
		pacingWorks.setText(i18n.GL3447());
		pacingNotes.setText(i18n.GL3448());
		assessmentsLbl.setText(i18n.GL3449());
		collectionLbl.setText(i18n.GL3450());
		
	}
	@Override
	public void setClassData(ClasspageDo classpageDo) {
		this.classpageDo=classpageDo;
		this.visiblity=classpageDo.isVisibility();
		if(classpageDo.getMinimumScore() >0){
			scoreTextBox.setText(classpageDo.getMinimumScore()+"");
		}else{
			scoreTextBox.setText("0");
		}
		
	}


	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.content.IsEditClassContentView#setUpdateClass(org.ednovo.gooru.application.shared.model.content.ClasspageDo)
	 */
	@Override
	public void setUpdateClass(ClasspageDo result) {
		classpageDo=result;
		saveLblText.setVisible(false);
		saveBtn.setVisible(true);
	}

	@Override
	public void clearAllErrorLabel() {
		errorLabel.setVisible(false);
		setClassData(classpageDo);
		saveEnabled(false);
		saveBtn.addStyleName(CssTokens.DISABLED);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.content.IsEditClassContentView#setCourseData(org.ednovo.gooru.application.shared.model.folder.FolderDo)
	 */
	@Override
	public void setCourseData(FolderDo result) {
		coursePanelVisiblity(true);
		if(result != null){
			titleLbl.setText(result.getTitle());
			titleLbl.getElement().setId(result.getGooruOid());
			titleLbl.getElement().setAttribute("alt",result.getTitle());
			titleLbl.getElement().setAttribute("title",result.getTitle());
		}
	}

		
	private void coursePanelVisiblity(boolean isVisible) {
		createCourseBtn.setVisible(!isVisible);
		scoreTextBox.setReadOnly(!isVisible);
		coursePanel.setVisible(isVisible);
		saveEnabled(false);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.content.IsEditClassContentView#addCourseData()
	 */
	@Override
	public void addCourseData() {
		coursePanelVisiblity(false);
	}
	
	private class AddCourseHandler implements ClickHandler{

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
		 */
		@Override
		public void onClick(ClickEvent event) {
			getUiHandlers().addCourseToClass();
		}
		
	}
	
}
