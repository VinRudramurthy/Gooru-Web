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
package org.ednovo.gooru.client.mvp.gshelf.coursedetails;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.AudienceView;
import org.ednovo.gooru.client.mvp.gshelf.util.CourseGradeWidget;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.util.SetStyleForProfanity;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author Search Team
 *
 */
public class CourseInfoView extends BaseViewWithHandlers<CourseInfoUiHandlers> implements IsCourseInfoView {

	private static GooruGradesViewUiBinder uiBinder = GWT.create(GooruGradesViewUiBinder.class);

	@UiTemplate("CourseInfoView.ui.xml")
	interface GooruGradesViewUiBinder extends UiBinder<Widget, CourseInfoView> {
	}	

	public MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField HTMLPanel courseInfo,pnlGradeContainer,spinnerIconContainer;
	@UiField PPanel courseTitlePpanel,tagCoursePpanel;
	@UiField UlPanel ulMainGradePanel,ulSelectedItems;
	@UiField Button saveCourseBtn,nextUnitBtn,btnK12,btnHigherEducation,btnProfessionalLearning;
	@UiField TextBox courseTitle;
	@UiField Label lblErrorMessage,lblGradeErrorMsg;
	@UiField AudienceView audienceContainer;

	Map<Integer, ArrayList<String>> selectedValues=new HashMap<Integer,ArrayList<String>>();

	CourseGradeWidget courseGradeWidget;
	public FolderDo courseObj;
	final String ACTIVE="active";

	LiPanel tempLiPanel=null;

	/**
	 * Class constructor 
	 * @param eventBus {@link EventBus}
	 */
	@Inject
	public CourseInfoView() {
		setWidget(uiBinder.createAndBindUi(this));
		courseInfo.getElement().setId("pnlCourseInfo");
		pnlGradeContainer.getElement().setId("pnlGradeContainer");
		ulMainGradePanel.getElement().setId("ulMainGradePanel");
		lblErrorMessage.setText("");
		lblErrorMessage.setVisible(false);
		courseTitle.getElement().setPropertyString("placeholder", i18n.GL3347());
		courseTitle.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				SetStyleForProfanity.SetStyleForProfanityForTextBox(courseTitle, lblErrorMessage, false);
			}
		});
		courseTitle.addKeyUpHandler(new TitleKeyUpHandler());
		courseTitle.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				courseTitle.getElement().getStyle().clearBackgroundColor();
				courseTitle.getElement().getStyle().setBorderColor("#ccc");
				lblErrorMessage.setVisible(false);
			}
		});
		btnK12.addClickHandler(new CallTaxonomy(1));
		btnHigherEducation.addClickHandler(new CallTaxonomy(2));
		btnProfessionalLearning.addClickHandler(new CallTaxonomy(3));
		courseTitlePpanel.setText(i18n.GL3472()); 
		tagCoursePpanel.setText(i18n.GL3473());
	}

	/**
	 * This class is used for validation on collection title keypress.
	 *
	 */
	private class TitleKeyUpHandler implements KeyUpHandler {
		public void onKeyUp(KeyUpEvent event) {
			lblErrorMessage.setVisible(false);
			if(courseTitle.getText().length() >= 50) {
				lblErrorMessage.setText(i18n.GL0143());
				lblErrorMessage.getElement().setAttribute("alt",i18n.GL0143());
				lblErrorMessage.getElement().setAttribute("title",i18n.GL0143());
				lblErrorMessage.setVisible(true);
			}
		}
	}
	class CallTaxonomy implements ClickHandler{
		int selectedIndex;
		CallTaxonomy(int selectedIndex){
			this.selectedIndex=selectedIndex;
		}
		@Override
		public void onClick(ClickEvent event) {
			removeGradeButtonStyleName();
			if(selectedIndex==1){
				btnK12.addStyleName(ACTIVE);
			}else if(selectedIndex==2){
				btnHigherEducation.addStyleName(ACTIVE);
			}else{
				btnProfessionalLearning.addStyleName(ACTIVE);
			}
			getUiHandlers().callTaxonomyService(selectedIndex);
		}
	}
	/**
	 * To remove hilight styles
	 */
	void removeGradeButtonStyleName() {
		btnK12.removeStyleName(ACTIVE);
		btnHigherEducation.removeStyleName(ACTIVE);
		btnProfessionalLearning.removeStyleName(ACTIVE);
	}
	/**
	 * This method will display the Grades according to the subject
	 */
	@Override
	public void showCourseDetailsBasedOnSubjectd(List<CourseSubjectDo> libraryCodeDo,final int selectedId) {
		pnlGradeContainer.clear();
		courseGradeWidget=new CourseGradeWidget(libraryCodeDo,selectedValues.get(selectedId),"course") {
			@Override
			public void setSelectedGrade(final CourseSubjectDo courseObj, final long codeId,boolean isAdd,LiPanel panel){
				if(isAdd){
					if(ulSelectedItems.getWidgetCount()>=10){
						lblGradeErrorMsg.setVisible(true);
						lblGradeErrorMsg.setText(i18n.GL3567());
					}else{
						lblGradeErrorMsg.setVisible(false);
						panel.addStyleName(ACTIVE);
						final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(courseObj.getName());
						liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
							@Override
							public void onClick(ClickEvent event) {
								//This will remove the selected value when we are trying by close button
								for(Iterator<Map.Entry<Integer,ArrayList<String>>>it=selectedValues.entrySet().iterator();it.hasNext();){
									Map.Entry<Integer, ArrayList<String>> entry = it.next();
									if(entry.getValue().contains(courseObj.getName())){
										entry.getValue().remove(courseObj.getName());
									}
								}
								removeGradeWidget(courseGradeWidget.getGradePanel(),codeId);
								liPanelWithClose.removeFromParent();
								lblGradeErrorMsg.setVisible(false);
							}
						});
						selectedValues.get(selectedId).add(courseObj.getName());
						liPanelWithClose.setId(codeId);
						liPanelWithClose.setName(courseObj.getName());
						liPanelWithClose.setRelatedId(selectedId);
						ulSelectedItems.add(liPanelWithClose);
					}
				}else{
					panel.removeStyleName(ACTIVE);
					if(selectedValues.get(selectedId).contains(courseObj.getName())){
						selectedValues.get(selectedId).remove(courseObj.getName());
					}
					removeGradeWidget(ulSelectedItems,codeId);
				}
			}
		};
		pnlGradeContainer.add(courseGradeWidget);
	}
	/**
	 * This method will remove the widget based on the codeId in the UlPanel
	 * @param ulPanel
	 * @param codeId
	 */
	public void removeGradeWidget(UlPanel ulPanel,long codeId){
		Iterator<Widget> widgets=ulPanel.iterator();
		while (widgets.hasNext()) {
			Widget widget=widgets.next();
			if(widget instanceof LiPanelWithClose){
				LiPanelWithClose obj=(LiPanelWithClose) widget;
				if(obj.getId()==codeId){
					obj.removeFromParent();
				}
			}
			if(widget instanceof LiPanel){
				LiPanel obj=(LiPanel) widget;
				if(obj.getCodeId()==codeId){
					obj.removeStyleName("active");
				}
			}
		}
	}

	@Override
	public void setCourseList(List<CourseSubjectDo> libraryCode) {
		ulMainGradePanel.clear();
		if (libraryCode.size()>0) {
			for (CourseSubjectDo libraryCodeDo : libraryCode) {
				String titleText=libraryCodeDo.getName().trim();
				if(!selectedValues.containsKey(libraryCodeDo.getSubjectId())){
					selectedValues.put(libraryCodeDo.getSubjectId(), new ArrayList<String>());
				}
				LiPanel liPanel=new LiPanel();
				Anchor title=new Anchor(titleText);
				title.addClickHandler(new ClickOnSubject(titleText,liPanel,libraryCodeDo.getSubjectId(),libraryCodeDo));
				liPanel.add(title);
				ulMainGradePanel.add(liPanel);
			}
		}
	}
	/**
	 * This inner class is used to get selected subjects grades
	 */
	public class ClickOnSubject implements ClickHandler{
		String selectedText;
		LiPanel liPanel;
		int subjectId;
		CourseSubjectDo courseSubjectDo;
		ClickOnSubject(String selectedText,LiPanel liPanel,int subjectId,CourseSubjectDo courseSubjectDo){
			this.selectedText=selectedText;
			this.liPanel=liPanel;
			this.subjectId=subjectId;
			this.courseSubjectDo=courseSubjectDo;
		}
		@Override
		public void onClick(ClickEvent event) {
			lblGradeErrorMsg.setVisible(false);
			if(tempLiPanel!=null){
				tempLiPanel.removeStyleName(ACTIVE);
				tempLiPanel=liPanel;
			}else{
				tempLiPanel=liPanel;
			}
			if(liPanel.getStyleName().contains(ACTIVE)){
				if(selectedValues.get(subjectId).size()>0){
					getUiHandlers().callCourseBasedOnSubject(subjectId, subjectId);
				}else{
					liPanel.removeStyleName(ACTIVE);
				}
			}else{
				liPanel.addStyleName(ACTIVE);
				getUiHandlers().callCourseBasedOnSubject(subjectId, subjectId);
			}
		}
	}
	@UiHandler("saveCourseBtn")
	public void clickOnSaveCourseBtn(ClickEvent saveCourseEvent){
		TreeItem currentShelfTreeWidget = getUiHandlers().getSelectedWidget();
		saveCourseBtn.addStyleName("disabled");
		saveCourseBtn.setEnabled(false);
		spinnerImageVisibility(true);
		if(validateInputs()){
			CreateDo createOrUpDate=new CreateDo();
			createOrUpDate.setAudienceIds(StringUtil.getKeys(getAudienceContainer().getSelectedValues().keySet()));
			createOrUpDate.setTitle(courseTitle.getText());
			
			lblErrorMessage.setVisible(false);
			courseTitle.removeStyleName("textAreaErrorMessage");
			getUiHandlers().checkProfanity(courseTitle.getText().trim(),false,createOrUpDate,currentShelfTreeWidget);	
		}else{
			Window.scrollTo(courseTitle.getAbsoluteLeft(), courseTitle.getAbsoluteTop()-(courseTitle.getOffsetHeight()*3));
			lblErrorMessage.setVisible(true);
			courseTitle.setStyleName("textAreaErrorMessage");
			courseTitle.addStyleName("form-control");
			lblErrorMessage.setText("Please Enter Course Title");
			resetBtns();
			spinnerImageVisibility(false);
		}

	}

	@UiHandler("nextUnitBtn")
	public void clickOnNextUnitBtn(ClickEvent saveCourseEvent){
		TreeItem treeSelectedItem  = getUiHandlers().getSelectedWidget();
		nextUnitBtn.addStyleName("disabled");
		nextUnitBtn.setEnabled(false);
		spinnerImageVisibility(true);
		if(validateInputs()){
			CreateDo createOrUpDate=new CreateDo();
			createOrUpDate.setAudienceIds(StringUtil.getKeys(getAudienceContainer().getSelectedValues().keySet()));
			createOrUpDate.setTitle(courseTitle.getText());
			
			lblErrorMessage.setVisible(false);
			courseTitle.removeStyleName("textAreaErrorMessage");
			getUiHandlers().checkProfanity(courseTitle.getText().trim(),true,createOrUpDate,treeSelectedItem);
		}else{
			Window.scrollTo(courseTitle.getAbsoluteLeft(), courseTitle.getAbsoluteTop()-(courseTitle.getOffsetHeight()*3));
			lblErrorMessage.setVisible(true);
			courseTitle.setStyleName("textAreaErrorMessage");
			courseTitle.addStyleName("form-control");
			lblErrorMessage.setText("Please Enter Course Title");
			resetBtns();
			spinnerImageVisibility(false);
		}
	}
	/**
	 * This method is used to call create and update API
	 * @param index
	 * @param isCreate
	 */
	@Override
	public void callCreateAndUpdate(boolean isCreate,boolean result,CreateDo createOrUpDate, TreeItem currentShelfTreeWidget){
		if(result){
			spinnerImageVisibility(false);
			SetStyleForProfanity.SetStyleForProfanityForTextBox(courseTitle, lblErrorMessage, result);
		}else{
			List<Integer> taxonomyList=getSelectedCourseIds();
			if(taxonomyList.size()>0){
				lblGradeErrorMsg.setVisible(false);
				createOrUpDate.setTaxonomyCourseIds(taxonomyList);
				if(courseObj!=null && courseObj.getGooruOid()!=null){
					getUiHandlers().updateCourseDetails(createOrUpDate,courseObj.getGooruOid(),isCreate,courseObj,currentShelfTreeWidget);
				}else{
					getUiHandlers().createAndSaveCourseDetails(createOrUpDate,isCreate,courseObj,currentShelfTreeWidget);
				}
			}else{
				lblGradeErrorMsg.setVisible(true);
				lblGradeErrorMsg.setText("Select at least one Course"); 
			}
		}
	}
	@Override
	public void setCouseData(FolderDo courseObj) {
		this.courseObj=courseObj;
		ulSelectedItems.clear();
		selectedValues.clear();
		removeGradeButtonStyleName();
		btnK12.addStyleName(ACTIVE);
		courseTitle.setText(courseObj==null?"":!courseObj.getTitle().equalsIgnoreCase(i18n.GL3347())?courseObj.getTitle():"");
		audienceContainer.setFolderDetails(courseObj);
		//This will push the previous selected values to map
		if(courseObj!=null && courseObj.getTaxonomyCourse()!=null){
			//To set default selection if the user is already selected any subject
			for (final CourseSubjectDo courseSubjectDo : courseObj.getTaxonomyCourse()) {
				if(selectedValues.containsKey(courseSubjectDo.getSubjectId())){
					selectedValues.get(courseSubjectDo.getSubjectId()).add(courseSubjectDo.getName());
				}else{
					selectedValues.put(courseSubjectDo.getSubjectId(), new ArrayList<String>());
					selectedValues.get(courseSubjectDo.getSubjectId()).add(courseSubjectDo.getName());
				}
				final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(courseSubjectDo.getName());
				liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						for(Iterator<Map.Entry<Integer,ArrayList<String>>>it=selectedValues.entrySet().iterator();it.hasNext();){
							Map.Entry<Integer, ArrayList<String>> entry = it.next();
							if(entry.getValue().contains(courseSubjectDo.getName())){
								entry.getValue().remove(courseSubjectDo.getName());
							}
						}
						removeGradeWidget(courseGradeWidget.getGradePanel(),courseSubjectDo.getId());
						liPanelWithClose.removeFromParent();
					}
				});
				liPanelWithClose.setId(courseSubjectDo.getId());
				liPanelWithClose.setName(courseSubjectDo.getName());
				liPanelWithClose.setRelatedId(courseSubjectDo.getSubjectId());
				ulSelectedItems.add(liPanelWithClose);
			}
		}
	}

	/**
	 * @return the courseTitle
	 */
	@Override
	public String getCourseTitle() {
		return courseTitle.getTitle();
	}
	/**
	 * This method is used to get the selected course id's
	 * @return
	 */
	public List<Integer> getSelectedCourseIds(){
		List<Integer> taxonomyCourseIds=new ArrayList<Integer>();
		Iterator<Widget> widgets=ulSelectedItems.iterator();
		List<CourseSubjectDo> courseList=new ArrayList<CourseSubjectDo>();
		while (widgets.hasNext()) {
			Widget widget=widgets.next();
			if(widget instanceof LiPanelWithClose){
				LiPanelWithClose obj=(LiPanelWithClose) widget;
				Integer intVal = (int)obj.getId();
				taxonomyCourseIds.add(intVal);
				CourseSubjectDo courseObj=new CourseSubjectDo();
				courseObj.setId((int)obj.getId());
				courseObj.setName(obj.getName());
				courseObj.setSubjectId(obj.getRelatedId());
				courseList.add(courseObj);
			}
		}
		if(courseObj!=null){
			courseObj.setTaxonomyCourse(courseList);
		}
		return taxonomyCourseIds;
	}
	@Override
	public AudienceView getAudienceContainer() {
		return audienceContainer;
	}

	@Override
	public FolderDo getCourseDetails(){
		return courseObj;
	}

	public boolean validateInputs(){
		String collectionTitleStr=courseTitle.getText().trim();
		if(collectionTitleStr.equalsIgnoreCase("")||collectionTitleStr.equalsIgnoreCase(i18n.GL3347())){
			return false;
		}else{
			return true;
		}
	}

	@UiHandler("courseTitle")
	public void courseTitleKeyUphandler(KeyUpEvent event){
		courseTitle.removeStyleName("textAreaErrorMessage");
		lblErrorMessage.setVisible(false);
	}
	
	@Override
	public void resetBtns() {
		saveCourseBtn.removeStyleName("disabled");
		saveCourseBtn.setEnabled(true);
		nextUnitBtn.removeStyleName("disabled");
		nextUnitBtn.setEnabled(true);
	}
	
	@Override
	public void spinnerImageVisibility(boolean isVisible){
		spinnerIconContainer.setVisible(isVisible); 
	}
}
