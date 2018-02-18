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
package org.ednovo.gooru.client.mvp.gshelf.util;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.folder.CourseSummaryDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.effects.FadeInAndOut;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.suggestbox.widget.Paragraph;
import org.ednovo.gooru.client.uc.tooltip.GlobalToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public abstract class ContentWidgetWithMove extends Composite {

	private static ContentWidgetWithMoveUiBinder uiBinder = GWT
			.create(ContentWidgetWithMoveUiBinder.class);

	interface ContentWidgetWithMoveUiBinder extends
			UiBinder<Widget, ContentWidgetWithMove> {
	}
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField Label lblTopArrow,lblDownArrow,lblIndex,lblImage;
	@UiField TextBox txtMoveTextBox;
	@UiField H3Panel h3CourseTitle;
	@UiField InlineLabel spnUnitsCount,spnLessonsCount,spnCollectionsCount,spnAssessmentsCount,spnResourcesCount,spnQuestionsCount;
	@UiField HTMLPanel pnlArrows,pnlMoveToEdit;
	@UiField Paragraph pTitle;
	@UiField HTMLEventPanel pnlTitleContainer;
	
	final String COURSE="Course",UNIT="Unit",LESSON="Lesson",FOLDER="Folder",COLLECTION="Collection",ASSESSMENTURL="Assessment/url",ASSESSMENT="Assessment";
	
	private PopupPanel toolTipPopupPanel=new PopupPanel(true);
	
	String type;
	/**
	 * This constructor is used to set data.
	 * @param index
	 * @param type
	 */
	public ContentWidgetWithMove(int index,String type,FolderDo folderObj) {
		initWidget(uiBinder.createAndBindUi(this));
		this.type=type;
		
		if(folderObj!=null && folderObj.getTitle()!=null){
			h3CourseTitle.setText(folderObj.getTitle());
		}
		lblTopArrow.addClickHandler(new ArrowClickHandler(false));
		lblDownArrow.addClickHandler(new ArrowClickHandler(true));
		spnResourcesCount.setVisible(false);
		spnQuestionsCount.setVisible(false);
		if(folderObj.getSummary()!=null){
			setData(folderObj.getSummary(),type);
		}
		int indexVal=index+1;
		if(indexVal==1){
			lblTopArrow.setVisible(false);
		}
		pnlArrows.setVisible(true);
		pnlMoveToEdit.setVisible(false);
	
		if(ASSESSMENTURL.equalsIgnoreCase(folderObj.getType())){
			pTitle.setText(i18n.GL3007());
		}else{
			pTitle.setText(StringUtil.capitalizeFirstLetter(folderObj!=null?(folderObj.getType()!=null?folderObj.getType():""):""));
		}
		lblIndex.setText(indexVal+"");
		txtMoveTextBox.setText(indexVal+"");
		txtMoveTextBox.getElement().setAttribute("index",index+"");
		
		//For old reorder we need to pass the collection item id, for coruse and other reorder we need to pass the gooruOid.
		String view=AppClientFactory.getPlaceManager().getRequestParameter("view",null);
		if(FOLDER.equalsIgnoreCase(view)){
			txtMoveTextBox.getElement().setAttribute("moveId",folderObj.getCollectionItemId()+"");
		}else{
			txtMoveTextBox.getElement().setAttribute("moveId",folderObj.getCollectionItemId()+"");
		}
		txtMoveTextBox.getElement().setAttribute("moveGooruOId",folderObj.getGooruOid()+"");
		txtMoveTextBox.getElement().setAttribute("moveParentGooruOId",folderObj.getParentGooruOid()+"");
		txtMoveTextBox.addKeyPressHandler(new HasNumbersOnly()); 
		txtMoveTextBox.addKeyUpHandler(new ReorderText()); 
		//This blur handler reset the previous value when the text box value is empty.
		txtMoveTextBox.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				String enteredString=txtMoveTextBox.getText().toString().trim();
				String currentWidgetString=txtMoveTextBox.getElement().getAttribute("index").trim();
				int enteredVal=0;
				if(!enteredString.isEmpty()){
					enteredVal=Integer.valueOf(enteredString);
				}else if(enteredString.isEmpty() || enteredVal==0){
					int currentIndex=(Integer.parseInt(currentWidgetString)+1);
					if(currentIndex==1 || Integer.parseInt(currentWidgetString)==0){
						lblDownArrow.setVisible(true);
					}else{
						lblTopArrow.setVisible(true);
						lblDownArrow.setVisible(true);
					}
					txtMoveTextBox.setText(currentIndex+"");
				}
				checkBlurHandler(enteredVal,ContentWidgetWithMove.this);
			}
		});
	}
	/**
	 * This inner class used for to restrict text box values to have only numbers
	 *
	 */

	public class HasNumbersOnly implements KeyPressHandler {
		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (!Character.isDigit(event.getCharCode()) 
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_TAB 
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_SHIFT
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_ENTER
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_LEFT
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_RIGHT
					&& event.getNativeEvent().getKeyCode() != KeyCodes.KEY_DELETE){
				((TextBox) event.getSource()).cancelKey();
			}
			if (event.getNativeEvent().getKeyCode() == 46
					|| event.getNativeEvent().getKeyCode() == 37) {
				((TextBox) event.getSource()).cancelKey();
			}
		}
	}
	/**
	 * This inner class used for disabling up and down arrow based on user entered reorder value.
	 */
	public class ReorderText implements KeyUpHandler {
		@Override
		public void onKeyUp(KeyUpEvent event) {
			String enteredString=txtMoveTextBox.getText().toString().trim();
			String currentWidgetString=txtMoveTextBox.getElement().getAttribute("index");
			if(!enteredString.isEmpty()){
				int enteredValue=Integer.parseInt(enteredString);
				int currentWidgetValue=Integer.parseInt(currentWidgetString)+1;
				if(currentWidgetValue==enteredValue){
					lblDownArrow.setVisible(true);
					lblTopArrow.setVisible(true);
				}else if(currentWidgetValue>enteredValue){
					lblDownArrow.setVisible(false);
					lblTopArrow.setVisible(true);
				}else{
					lblTopArrow.setVisible(false);
					lblDownArrow.setVisible(true);
				}
				if(enteredValue<=0){
					//if moving position is zero hiding the values
					lblTopArrow.setVisible(false);
					lblDownArrow.setVisible(false);
					toolTipPopupPanel.clear();
					toolTipPopupPanel.setWidget(new GlobalToolTip(StringUtil.generateMessage(i18n.GL3004(),enteredValue+"")));
					toolTipPopupPanel.setStyleName("");
					toolTipPopupPanel.setPopupPosition(ContentWidgetWithMove.this.getTextBox().getAbsoluteLeft(), ContentWidgetWithMove.this.getTextBox().getAbsoluteTop()+10);
					toolTipPopupPanel.getElement().getStyle().setZIndex(9999);
					toolTipPopupPanel.show();
					new FadeInAndOut(toolTipPopupPanel.getElement(), 10200);
				}
				checkKeyUpHandler(enteredValue,ContentWidgetWithMove.this);
			}
		}
	}
	/**
	 * This method is used to set count for Units,Lessons,Collections and Assessments.
	 * @param courseSummaryDo 
	 */
	public void setData(CourseSummaryDo courseSummaryDo,String typeVal){
		if(courseSummaryDo.getUnitCount()!=null && courseSummaryDo.getUnitCount()>0){
			spnUnitsCount.setText(i18n.GL3279()+" ("+courseSummaryDo.getUnitCount()+")");
		}
		if(courseSummaryDo.getLessonCount()!=null && courseSummaryDo.getLessonCount()>0){
			spnLessonsCount.setText(i18n.GL3280()+" ("+courseSummaryDo.getLessonCount()+")");
		}
		if(courseSummaryDo.getCollectionCount()!=null && courseSummaryDo.getCollectionCount()>0){
			spnCollectionsCount.setText(i18n.GL1754()+" ("+courseSummaryDo.getCollectionCount()+")");
		}
		if(courseSummaryDo.getAssessmentCount()!=null && courseSummaryDo.getAssessmentCount()>0){
			spnAssessmentsCount.setText(i18n.GL1325()+" ("+courseSummaryDo.getAssessmentCount()+")");
		}
		if(courseSummaryDo.getResourceCount()!=null && courseSummaryDo.getResourceCount()>0){
			spnResourcesCount.setText(i18n.GL1755()+" ("+courseSummaryDo.getResourceCount()+")");
		}
		if(courseSummaryDo.getResourceCount()!=null && courseSummaryDo.getQuestionCount()>0){
			spnQuestionsCount.setText(i18n.GL2290()+" ("+courseSummaryDo.getQuestionCount()+")");
		}
	}
	/**
	 * This method is used for enabling and disabling the counts
	 * @param typeVal
	 */
	public void enableAndDisableCount(String typeVal){
		lblImage.setStyleName("courseImage");
		hideAllCounts();
		spnUnitsCount.setVisible(true);
		if(UNIT.equalsIgnoreCase(typeVal)){
			spnLessonsCount.setVisible(true);
			lblImage.setStyleName("unitImage");
		}else if(LESSON.equalsIgnoreCase(typeVal)){
			lblImage.setStyleName("lessonImage");
			spnCollectionsCount.setVisible(true);
			spnAssessmentsCount.setVisible(true);
			//spnUnitsCount.setVisible(false);
			//spnLessonsCount.setVisible(false);
		}else if(FOLDER.equalsIgnoreCase(typeVal)){
			lblImage.setStyleName("folderImage");
			//spnUnitsCount.setVisible(false);
			//spnLessonsCount.setVisible(false);
			pnlArrows.setVisible(true);
			//pnlMoveToEdit.setVisible(true);
		}else if(COLLECTION.equalsIgnoreCase(typeVal) || ASSESSMENTURL.equalsIgnoreCase(typeVal) || ASSESSMENT.equalsIgnoreCase(typeVal)){
			spnQuestionsCount.setVisible(true);
			if(COLLECTION.equalsIgnoreCase(typeVal)){
				lblImage.setStyleName("collectionImage");
				spnResourcesCount.setVisible(true);
			}else if(ASSESSMENT.equalsIgnoreCase(typeVal)){
				lblImage.setStyleName("assessmentImage");
				spnResourcesCount.setVisible(false);
			}else{
				lblImage.setStyleName("assessmentImage");
				spnResourcesCount.setVisible(false);
				spnQuestionsCount.setVisible(false);
			}
			pnlArrows.setVisible(true);
		}
	}
	public void hideAllCounts(){
		spnUnitsCount.setVisible(false);
		spnLessonsCount.setVisible(false);
		spnCollectionsCount.setVisible(false);
		spnAssessmentsCount.setVisible(false);
		spnResourcesCount.setVisible(false);
		spnQuestionsCount.setVisible(false);
	}
	/**
	 * This inner class will handle the click event on the Arrows
	 */
	class ArrowClickHandler implements ClickHandler{
		boolean isDownArrow;
		ArrowClickHandler(boolean isDownArrow){
			this.isDownArrow=isDownArrow;
		}
		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {
				@Override
				public void onSuccess() {
					String movingPosition=txtMoveTextBox.getText().toString().trim();
					String currentWidgetPosition=txtMoveTextBox.getElement().getAttribute("index").trim();
					String moveId=txtMoveTextBox.getElement().getAttribute("moveId");
					String moveGooruOId=txtMoveTextBox.getElement().getAttribute("moveGooruOId");
					String moveParentGooruOid=txtMoveTextBox.getElement().getAttribute("moveParentGooruOId");
					if(!movingPosition.isEmpty()){
						int movingValue=Integer.parseInt(movingPosition);
						if(movingValue<=0){
							//if moving position is zero hiding the values
							lblTopArrow.setVisible(false);
							lblDownArrow.setVisible(false);
						}else{
							int currentWidgetValue=Integer.parseInt(currentWidgetPosition);
							//This one will execute when user enter a number in text field and click on either up and down arrow.
							if(movingValue!=(currentWidgetValue+1)){
								moveWidgetPosition(movingPosition,currentWidgetPosition,isDownArrow,moveId,moveGooruOId,moveParentGooruOid);
							}else if(movingValue==(currentWidgetValue+1)){
								//This one will execute when user directly clicks on either up and down arrow.
								if(isDownArrow){
									moveWidgetPosition((movingValue+1)+"",currentWidgetPosition,isDownArrow,moveId,moveGooruOId,moveParentGooruOid);
								}else{
									moveWidgetPosition((movingValue-1)+"",currentWidgetPosition,isDownArrow,moveId,moveGooruOId,moveParentGooruOid);
								}
							}
						}
					}
				}
			});
		}
	}
	
	public H3Panel getH3Panel(){
		return h3CourseTitle;
	}
	public TextBox getTextBox(){
		return txtMoveTextBox;
	}
	public HTMLEventPanel getTitleContainer(){
		return pnlTitleContainer;
	}
	public Label getTopArrow(){
		return lblTopArrow;
	}
	public Label getDownArrow(){
		return lblDownArrow;
	}
	public Label getIndexLabel(){
		return lblIndex;
	}
	public abstract void checkKeyUpHandler(int position,ContentWidgetWithMove contentWidgetWithMove);
	public abstract void checkBlurHandler(int position,ContentWidgetWithMove contentWidgetWithMove);
	public abstract void moveWidgetPosition(String movingPosition,String currentWidgetPosition,boolean isDownArrow,String moveId,String moveGooruOId,String moveParentGooruOid);
}
