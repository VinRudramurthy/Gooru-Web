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
package org.ednovo.gooru.client.mvp.gshelf.collectiondetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.library.DomainStandardsDo;
import org.ednovo.gooru.application.shared.model.library.SubDomainStandardsDo;
import org.ednovo.gooru.application.shared.model.library.SubSubDomainStandardsDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.AudienceView;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.DepthKnowledgeView;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets.LanguageView;
import org.ednovo.gooru.client.mvp.gshelf.util.CourseGradeWidget;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.SetStyleForProfanity;
import org.ednovo.gooru.shared.util.InfoUtil;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.EventTarget;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author Search Team
 *
 */
public class CollectionInfoView extends BaseViewWithHandlers<CollectionInfoUiHandlers> implements IsCollectionInfoView {

	private static CollectionInfoViewUiBinder uiBinder = GWT.create(CollectionInfoViewUiBinder.class);

	@UiTemplate("CollectionInfoView.ui.xml")
	interface CollectionInfoViewUiBinder extends UiBinder<Widget, CollectionInfoView> {
	}


	@UiField HTMLPanel collectionInfo,newdok,newtype,centurySkillContainer,standardsUI,thumbnailImageContainer,pnlStandards,spinnerIconContainer;

	@UiField TextBox collectionTitle;
	@UiField Button saveCollectionBtn,uploadImageLbl,taxonomyBtn;
	@UiField TextArea learningObjective;
	@UiField Label lblGradeErrorMsg,lblErrorMessage, lblErrorMessageForLO,newlbl;
	@UiField Image collThumbnail;
	@UiField Anchor dok,centurySkills,languageObj;
	@UiField HTMLEventPanel btnStandardsBrowse,taxonomyToggleBtn;
	@UiField UlPanel standardsDropListValues;
	@UiField DepthKnowledgeView depthOfKnowledgeContainer;
	@UiField LanguageView languageObjectiveContainer;

	@UiField AudienceView audienceContainer;
	@UiField PPanel colltitle,collimagetitle,tagcollectiontitle;
	@UiField UlPanel ulSelectedItems;
	@UiField PPanel learningObjPpanel;
	private boolean isLanguageObjectInfo=false;
	private boolean isCenturySkillsInfo=false;
	private boolean isDepthOfKnlzeInfo = false;


	private static final MessageProperties i18n = GWT.create(MessageProperties.class);
	List<Integer> selectedValues=new ArrayList<>();

	List<LiPanelWithClose> collectionLiPanelWithCloseArray = new ArrayList<>();

	String[] standardsTypesArray = new String[]{i18n.GL3379(),i18n.GL3322(),i18n.GL3323(),i18n.GL3324(),i18n.GL3325(),i18n.GL3321()};

	private String type="";

	private static final String ASSESSMENT = "assessment";

	private static final String DEFULT_ASSESSMENT_IMG = "images/default-assessment-image -160x120.png";

	private static final String DEFULT_COLLECTION_IMG = "images/default-collection-image-160x120.png";

	final String COLLECTION = "collection";

	CourseGradeWidget courseGradeWidget;
	public FolderDo courseObjG;
	final String ACTIVE="active";
	
	private boolean isCCSSAvailable =false;
	private boolean isNGSSAvailable =false;
	private boolean isTEKSAvailable =false;
	private boolean isCAAvailable =false;
	
	String USER_META_ACTIVE_FLAG = "userMetaActiveFlag";


	/**
	 * Class constructor
	 * @param eventBus {@link EventBus}
	 */
	@Inject
	public CollectionInfoView() {
		setWidget(uiBinder.createAndBindUi(this));
		collectionInfo.getElement().setId("pnlCollectionInfo");
		lblErrorMessage.setVisible(false);
		collectionTitle.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				SetStyleForProfanity.SetStyleForProfanityForTextBox(collectionTitle, lblErrorMessage, false);
			}
		});
		collectionTitle.addKeyUpHandler(new TitleKeyUpHandler(1));
		collectionTitle.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				collectionTitle.getElement().getStyle().clearBackgroundColor();
				collectionTitle.getElement().getStyle().setBorderColor("#ccc");
				lblErrorMessage.setVisible(false);
			}
		});
		
		depthOfKnowledgeContainer.setVisible(false);
		languageObjectiveContainer.setVisible(false);
		centurySkillContainer.setVisible(false);
		uploadImageLbl.setText(i18n.GL0912());
		
		taxonomyBtn.addClickHandler(new OnClickTaxonomy());
		taxonomyToggleBtn.addClickHandler(new OnClickTaxonomy());
		btnStandardsBrowse.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getAddStandards();
				if(!standardsDropListValues.getElement().getAttribute("style").equalsIgnoreCase("display:block;")){
					standardsDropListValues.getElement().setAttribute("style", "display:block;");
				}else{
					standardsDropListValues.getElement().removeAttribute("style");
				}
			}
		});
		Event.addNativePreviewHandler(new NativePreviewHandler() {
                @Override
	        public void onPreviewNativeEvent(NativePreviewEvent event) {
	        	hideDropDown(event);
	          }
	    });
		dok.addClickHandler(new dokClickHandlers());
		centurySkills.addClickHandler(new CenturySkillsClickHandlers());
		languageObj.addClickHandler(new Language_ObjectiveClickHandlers());
		collThumbnail.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				collThumbnail.setUrl((COLLECTION.equalsIgnoreCase(CollectionInfoView.this.type))?DEFULT_COLLECTION_IMG:DEFULT_ASSESSMENT_IMG);
			}
		});
		collThumbnail.getElement().setId("mycollectionUploadImage");
		learningObjective.addKeyUpHandler(new TitleKeyUpHandler(2));
		learningObjective.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				learningObjective.getElement().getStyle().clearBackgroundColor();
				learningObjective.getElement().getStyle().setBorderColor("#ccc");
				lblErrorMessageForLO.setVisible(false);
			}
		});
		learningObjective.addKeyDownHandler(new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				restrictKeyLimit(event, learningObjective, learningObjective.getText(), lblErrorMessageForLO);
			}
		});	
		
		learningObjPpanel.setText(i18n.GL3484());
	}
	public void resetErrorMessages(){
		learningObjective.getElement().getStyle().clearBackgroundColor();
		learningObjective.getElement().getStyle().setBorderColor("#ccc");
		lblErrorMessageForLO.setVisible(false);
		collectionTitle.getElement().getStyle().clearBackgroundColor();
		collectionTitle.getElement().getStyle().setBorderColor("#ccc");
		lblErrorMessage.setVisible(false);
		collectionTitle.removeStyleName("textAreaErrorMessage");
	}
	private void restrictKeyLimit(KeyDownEvent event, TextArea textArea, String text, Label errorLabelToDisplay) {
		if(text.trim().length()<=999) {
			errorLabelToDisplay.setVisible(false);	 
		} else if(text.trim().length()>998) {
			if(event==null) {
				textArea.cancelKey();
				errorLabelToDisplay.setVisible(true);
				errorLabelToDisplay.setText(i18n.GL0143());	
				errorLabelToDisplay.getElement().setAttribute("alt",i18n.GL0143());
				errorLabelToDisplay.getElement().setAttribute("title",i18n.GL0143());
			} else {
				if(event.isControlKeyDown() || event.isShiftKeyDown() ||
						((event.getNativeEvent().getKeyCode() == KeyCodes.KEY_UP)) || 
						((event.getNativeEvent().getKeyCode() == KeyCodes.KEY_LEFT)) || 
						((event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DOWN)) || 
						((event.getNativeEvent().getKeyCode() == KeyCodes.KEY_RIGHT)) || 
						((event.getNativeEvent().getKeyCode() == KeyCodes.KEY_BACKSPACE)) || 
						((event.getNativeEvent().getKeyCode() == KeyCodes.KEY_DELETE))) {
					if(text.trim().length()<=1000) {
						errorLabelToDisplay.setVisible(false);	 
					}
				} else {
					textArea.cancelKey();
					errorLabelToDisplay.setVisible(true);
					errorLabelToDisplay.setText(i18n.GL0143());
					errorLabelToDisplay.getElement().setAttribute("alt",i18n.GL0143());
					errorLabelToDisplay.getElement().setAttribute("title",i18n.GL0143());
				}
			}
		}
	}
	/**
	 * This class is used for validation on collection title keypress.
	 *
	 */
	private class TitleKeyUpHandler implements KeyUpHandler {
		int value;
		TitleKeyUpHandler(int value){
			this.value=value;
		}
		public void onKeyUp(KeyUpEvent event) {
			if(value==1){
				lblErrorMessage.setVisible(false);
				if(collectionTitle.getText().length() >= 50) {
					lblErrorMessage.setText(i18n.GL0143());
					lblErrorMessage.getElement().setAttribute("alt",i18n.GL0143());
					lblErrorMessage.getElement().setAttribute("title",i18n.GL0143());
					lblErrorMessage.setVisible(true);
				}
			}else if(value==2){
				lblErrorMessageForLO.setVisible(false);
				if(learningObjective.getText().length() >= 1000) {
					lblErrorMessageForLO.setText(i18n.GL0143());
					lblErrorMessageForLO.getElement().setAttribute("alt",i18n.GL0143());
					lblErrorMessageForLO.getElement().setAttribute("title",i18n.GL0143());
					lblErrorMessageForLO.setVisible(true);
				}
			}
		}
	}
	/**
	 * This inner class is used to get selected subjects grades
	 */
	class ClickOnSubject implements ClickHandler{
		String selectedText;
		LiPanel liPanel;
		int subjectId;
		ClickOnSubject(String selectedText,LiPanel liPanel,int subjectId){
			this.selectedText=selectedText;
			this.liPanel=liPanel;
			this.subjectId=subjectId;
		}
		@Override
		public void onClick(ClickEvent event) {
			if(liPanel.getStyleName().contains(ACTIVE)){
				liPanel.removeStyleName(ACTIVE);
			}else{
				liPanel.addStyleName(ACTIVE);
			}
		}
	}

	@Override
	public void setCollectionType(String collectionType) {
		if(collectionType.equalsIgnoreCase("collection"))
		{
			collThumbnail.setUrl(DEFULT_COLLECTION_IMG);
		}
		else
		{
			collThumbnail.setUrl(DEFULT_ASSESSMENT_IMG);
		}

	}
        @Override
	public void displayStandardsList(final List<DomainStandardsDo> standardsList){
		standardsUI.clear();
		final String selValues = getSelectedStandards().toString();
            for (DomainStandardsDo standardsListItem : standardsList) {
                Boolean flgLevelOne = false;
                if (standardsListItem.getCode().contains("Math")) {
                    flgLevelOne = true;
                }
                final StandardsCodeDecView standardsCode = new StandardsCodeDecView(standardsListItem.getCode(), standardsListItem.getLabel(), flgLevelOne);
                final DomainStandardsDo domainStand = standardsListItem;
                if (domainStand.getTypeId()!=null && !(standardsListItem.getCode().contains("ELA"))) {
                    if(domainStand.getTypeId().equals(1))
                    {

                    }
                    else if(domainStand.getTypeId().equals(2))
                    {
                        standardsCode.getWidgetContainer().getElement().getStyle().setPaddingLeft(35, Unit.PX);
                    }
                    else
                    {
                        standardsCode.getWidgetContainer().getElement().getStyle().setPaddingLeft(70, Unit.PX);
                    }
                }
                standardsCode.getWidgetContainer().getElement().setId(domainStand.getCodeId().toString());
                if (selValues.contains(standardsListItem.getCodeId().toString())) {
                    standardsCode.getWidgetContainer().addStyleName("active");
                }
                standardsCode.getWidgetContainer().addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        if(!standardsCode.getWidgetContainer().getStyleName().contains("active")){
                        	if(ulSelectedItems.getWidgetCount()>=15){
                        		lblGradeErrorMsg.setVisible(true);
	                    		lblGradeErrorMsg.setText(i18n.GL3569());
		                	}else{
		                		lblGradeErrorMsg.setVisible(false);
	                            standardsCode.getWidgetContainer().addStyleName("active");
	                            standardsCode.getWidgetContainer().getElement().setId(domainStand.getCodeId().toString());
	                            if(!selValues.contains(domainStand.getCodeId().toString())){
	                                selectedValues.add(domainStand.getCodeId());
	                            }
	                            final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(domainStand.getCode());
	                            liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
	                                @Override
	                                public void onClick(ClickEvent event) {
	                                    //This will remove the selected value when we are trying by close button
	                                    if(selValues.contains(domainStand.getCodeId().toString())){
	                                        selectedValues.remove(domainStand.getCodeId());
	                                    }
	                                    standardsCode.removeStyleName("active");
	                                    removeGradeWidget(ulSelectedItems,domainStand.getCodeId());
	                                    liPanelWithClose.removeFromParent();
	                                    lblGradeErrorMsg.setVisible(false);
	                                }
	                            });
	                            //selectedValues.add(domainStand.getCodeId());
	                            liPanelWithClose.setId(domainStand.getCodeId());
	                            liPanelWithClose.setName(domainStand.getCode());
	                            liPanelWithClose.setRelatedId(domainStand.getCodeId());
	                            liPanelWithClose.setDifferenceId(3);
	                            liPanelWithClose.getElement().setAttribute("tag", "taxonomy");
	                            ulSelectedItems.add(liPanelWithClose);
		                	}
					}else{
						standardsCode.getWidgetContainer().removeStyleName("active");
						removeGradeWidget(ulSelectedItems,domainStand.getCodeId());
						lblGradeErrorMsg.setVisible(false);
					}
				}
			});
                standardsUI.add(standardsCode);
                displaySubStandardsList(standardsListItem.getNode());
            }
	}
        
        
        
        
	/**
	 * This method is used to get the selected Std id's
	 * @return
	 */
	public List<Integer> getSelectedStandards(){
		List<Integer> taxonomyCourseIds=new ArrayList<>();
		Iterator<Widget> widgets=ulSelectedItems.iterator();
		List<CourseSubjectDo> courseList=new ArrayList<>();
		while (widgets.hasNext()) {
			Widget widget=widgets.next();
			if(widget instanceof LiPanelWithClose){
				LiPanelWithClose obj=(LiPanelWithClose) widget;
				if(obj.getDifferenceId()==3){
					Integer intVal = (int)obj.getId();
					taxonomyCourseIds.add(intVal);
					CourseSubjectDo courseObj=new CourseSubjectDo();
					selectedValues.add((int)obj.getId());
					courseObj.setId((int)obj.getId());
					courseObj.setCode(obj.getName());
					courseObj.setSubjectId(obj.getRelatedId());
					courseList.add(courseObj);
				}
			}
		}
		if(courseObjG!=null){
			courseObjG.setStandards(courseList);
		}

		return taxonomyCourseIds;
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
				if(obj.getDifferenceId()==1){
					Integer intVal = (int)obj.getId();
					taxonomyCourseIds.add(intVal);
					CourseSubjectDo courseObj=new CourseSubjectDo();
					courseObj.setId((int)obj.getId());
					courseObj.setName(obj.getName());
					courseObj.setSubjectId(obj.getRelatedId());
					courseList.add(courseObj);
				}
			}
		}
		if(courseObjG!=null){
			courseObjG.setTaxonomyCourse(courseList);
		}
		return taxonomyCourseIds;
	}
	
	
	/**
	 * This method is used to get the selected Domain id's
	 * @return
	 */
	public List<Integer> getSelectedSubDomainIds(){
		List<Integer> taxonomyCourseIds=new ArrayList<Integer>();
		Iterator<Widget> widgets=ulSelectedItems.iterator();
		List<CourseSubjectDo> courseList=new ArrayList<CourseSubjectDo>();
		while (widgets.hasNext()) {
			Widget widget=widgets.next();
			if(widget instanceof LiPanelWithClose){
				LiPanelWithClose obj=(LiPanelWithClose) widget;
				if(obj.getDifferenceId()==2){
					Integer intVal = (int)obj.getId();
					taxonomyCourseIds.add(intVal);
					CourseSubjectDo courseObj=new CourseSubjectDo();
					courseObj.setId((int)obj.getId());
					courseObj.setName(obj.getName());
					courseObj.setSubjectId(obj.getRelatedSubjectId());
					courseObj.setCourseId(obj.getRelatedId());
					courseList.add(courseObj);
				}
			}
		}
		if(courseObjG!=null){
			courseObjG.setSubdomain(courseList);
		}
		return taxonomyCourseIds;
	}

	public void displaySubStandardsList(final List<SubDomainStandardsDo> standardsList){
		//	standardsUI.clear();
			final String selValues = getSelectedStandards().toString();

            for (SubDomainStandardsDo standardsList1 : standardsList) {
                final StandardsCodeDecView standardsCode = new StandardsCodeDecView(standardsList1.getCode(), standardsList1.getLabel(), false);
                final SubDomainStandardsDo domainStand = standardsList1;
                standardsCode.getWidgetContainer().getElement().getStyle().setPaddingLeft(35, Unit.PX);
                standardsCode.getWidgetContainer().getElement().setId(domainStand.getCodeId().toString());
                if (selValues.contains(standardsList1.getCodeId().toString())) {
                    standardsCode.getWidgetContainer().addStyleName("active");
                }
                standardsCode.getWidgetContainer().addClickHandler(new ClickHandler() {

                    @Override
                    public void onClick(ClickEvent event) {
                        if(!standardsCode.getWidgetContainer().getStyleName().contains("active")){
                        	if(ulSelectedItems.getWidgetCount()>=15){
                        		lblGradeErrorMsg.setVisible(true);
                        		lblGradeErrorMsg.setText(i18n.GL3569());
		                	}else{
		                		lblGradeErrorMsg.setVisible(false);
	                            standardsCode.getWidgetContainer().addStyleName("active");
	                            if(!selValues.contains(domainStand.getCodeId().toString())){
	                                selectedValues.add(domainStand.getCodeId());
	                            }
	                            final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(domainStand.getCode());
	                            liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
	                                @Override
	                                public void onClick(ClickEvent event) {
	                                    //This will remove the selected value when we are trying by close button
	                                    if(selValues.contains(domainStand.getCodeId().toString())){
	                                        selectedValues.remove(domainStand.getCodeId());
	                                    }
	                                    standardsCode.removeStyleName("active");
	                                    removeGradeWidget(ulSelectedItems,domainStand.getCodeId());
	                                    liPanelWithClose.removeFromParent();
	                                	lblGradeErrorMsg.setVisible(false);
	                                }
	                            });
	                            //selectedValues.add(domainStand.getCodeId());
	                            liPanelWithClose.setId(domainStand.getCodeId());
	                            liPanelWithClose.setName(domainStand.getCode());
	                            liPanelWithClose.setDifferenceId(3);
	                            liPanelWithClose.setRelatedId(domainStand.getCodeId());
	                            liPanelWithClose.getElement().setAttribute("tag", "taxonomy");
	                            ulSelectedItems.add(liPanelWithClose);
		                	}
                        }else{
                            standardsCode.getWidgetContainer().removeStyleName("active");
                            removeGradeWidget(ulSelectedItems,domainStand.getCodeId());
                        	lblGradeErrorMsg.setVisible(false);
                        }
                    }
                });
                standardsUI.add(standardsCode);
                displaySubSubStandardsList(standardsList1.getNode());
            }
	}
	public void displaySubSubStandardsList(final List<SubSubDomainStandardsDo> standardsList){
			//standardsUI.clear();
		final String selValues = getSelectedStandards().toString();
            for (SubSubDomainStandardsDo standardsList1 : standardsList) {
                final StandardsCodeDecView standardsCode = new StandardsCodeDecView(standardsList1.getCode(), standardsList1.getLabel(), false);
                final SubSubDomainStandardsDo domainStand = standardsList1;
                standardsCode.getWidgetContainer().getElement().getStyle().setPaddingLeft(70, Unit.PX);
                standardsCode.getWidgetContainer().getElement().setId(domainStand.getCodeId().toString());
                if (selValues.contains(standardsList1.getCodeId().toString())) {
                    standardsCode.getWidgetContainer().addStyleName("active");
                }
                standardsCode.getWidgetContainer().addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
                        if(!standardsCode.getWidgetContainer().getStyleName().contains("active")){
                        	if(ulSelectedItems.getWidgetCount()>=15){
                        		lblGradeErrorMsg.setVisible(true);
                        		lblGradeErrorMsg.setText(i18n.GL3569());
		                	}else{
		                		lblGradeErrorMsg.setVisible(false);
	                            standardsCode.getWidgetContainer().addStyleName("active");
	                            if(!selValues.contains(domainStand.getCodeId().toString())){
	                                selectedValues.add(domainStand.getCodeId());
	                            }
	                            final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(domainStand.getCode());
	                            liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
	                                @Override
	                                public void onClick(ClickEvent event) {
	                                    //This will remove the selected value when we are trying by close button
	                                    if(selValues.contains(domainStand.getCodeId().toString())){
	                                        selectedValues.remove(domainStand.getCodeId());
	                                    }
	                                    standardsCode.removeStyleName("active");
	                                    removeGradeWidget(ulSelectedItems,domainStand.getCodeId());
	                                    liPanelWithClose.removeFromParent();
	                                    lblGradeErrorMsg.setVisible(false);
	                                }
	                            });
	                            //selectedValues.add(domainStand.getCodeId());
	                            liPanelWithClose.setId(domainStand.getCodeId());
	                            liPanelWithClose.setName(domainStand.getCode());
	                            liPanelWithClose.setRelatedId(domainStand.getCodeId());
	                            liPanelWithClose.setDifferenceId(3);
	                            liPanelWithClose.getElement().setAttribute("tag", "taxonomy");
	                            ulSelectedItems.add(liPanelWithClose);
		                	}
                        }else{
                            standardsCode.getWidgetContainer().removeStyleName("active");
                            removeGradeWidget(ulSelectedItems,domainStand.getCodeId());
                            lblGradeErrorMsg.setVisible(false);
                        }
                    }
                });
                standardsUI.add(standardsCode);
            }
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
	public final void populateStandardValues(){
		standardsDropListValues.clear();
            for (String standardsTypesArray1 : standardsTypesArray) {
                List<String> standardsDescriptionList = Arrays.asList(standardsTypesArray1.split(","));
                LiPanel liPanel = new LiPanel();
                for(int j=0; j<standardsDescriptionList.size(); j++){
                    HTMLPanel headerDiv = new HTMLPanel("");
                    if(j==0){
                        if(standardsDescriptionList.get(j).equalsIgnoreCase("CA SS")){
                            liPanel.getElement().setId("CA");
                        }else if(standardsDescriptionList.get(j).equalsIgnoreCase("LWMCS")){
                            liPanel.getElement().setId("B21");
                        }else{
                            liPanel.getElement().setId(standardsDescriptionList.get(j));
                        }
                       
                        if((!isCCSSAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("CCSS")){
          		    	  liPanel.getElement().setAttribute("style", "opacity:0.5;"); 	  
          		        }
          		      else if((!isCAAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("CA SS"))
          		        {
          		    	  liPanel.getElement().setAttribute("style", "opacity:0.5;");
          		        }
          		      else if((!isNGSSAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("NGSS")){
          		    	  liPanel.getElement().setAttribute("style", "opacity:0.5;");
          		        }
          		      else if((!isTEKSAvailable) && standardsDescriptionList.get(j).equalsIgnoreCase("TEKS")){
          		    	  liPanel.getElement().setAttribute("style", "opacity:0.5;");
          		        }
                        
                        headerDiv.setStyleName("liPanelStyle");
                    }else{
                    	if(standardsDescriptionList.get(j).equalsIgnoreCase("College Career and Civic Life"))
                    	{
                    		standardsDescriptionList.set(j, "College, Career, and Civic Life");
                            headerDiv.setStyleName("liPanelStylenonBold");
                            liPanel.getElement().setAttribute("standarddesc", "College, Career, and Civic Life");
                    	}
                    	else
                    	{
                        headerDiv.setStyleName("liPanelStylenonBold");
                        liPanel.getElement().setAttribute("standarddesc", standardsDescriptionList.get(j));
                    	}
                    }
                    headerDiv.getElement().setInnerHTML(standardsDescriptionList.get(j));
                    liPanel.add(headerDiv);
                }
                if(liPanel.getElement().getAttribute("style")!=null && !liPanel.getElement().getAttribute("style").equalsIgnoreCase("opacity:0.5;"))
                {
                liPanel.addClickHandler(new ClickHandler() {
                    @Override
                    public void onClick(ClickEvent event) {
					String standardsVal = event.getRelativeElement().getAttribute("id");
					String standardsDesc = event.getRelativeElement().getAttribute("standarddesc");
					collectionLiPanelWithCloseArray.clear();
					for(int i=0;i<ulSelectedItems.getWidgetCount();i++){
						collectionLiPanelWithCloseArray.add((LiPanelWithClose) ulSelectedItems.getWidget(i));
					}
					getUiHandlers().showStandardsPopup(standardsVal,standardsDesc, collectionLiPanelWithCloseArray);
				}
			});
                }
                standardsDropListValues.add(liPanel);
            }
	}

	public void setDetaultImage(String collectionType){
		collThumbnail.setUrl(COLLECTION.equalsIgnoreCase(collectionType)?DEFULT_COLLECTION_IMG:DEFULT_ASSESSMENT_IMG);
	}

	@Override
	public void setCouseData(final FolderDo courseObj, String type) {
		this.courseObjG=courseObj;
		standardsUI.clear();
		resetDOK_Century_Lang();

		depthOfKnowledgeContainer.setFolderDo(courseObj);
		audienceContainer.setFolderDetails(courseObj);
		getUiHandlers().getCenturySkillsPresenters().getView().setFolderDo(courseObj);
		languageObjectiveContainer.setLanguageObjective(courseObj);
		this.type = type;

		ulSelectedItems.clear();
		selectedValues.clear();
		resetErrorMessages();
		
		if(courseObj!=null){
			courseObjG.setCollectionType(type);
			if(courseObj.getThumbnails()!=null){
				collThumbnail.setUrl(courseObj.getThumbnails().getUrl());
			}else{
				setDetaultImage(courseObj.getType());
			}
		}
		if(courseObj!=null){
            if(courseObj.getStandards()!=null && courseObj.getStandards().size()>0){
                //Render the existing standards
            	for(final CourseSubjectDo courseSubjectDo : courseObj.getStandards()) {
					final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(courseSubjectDo.getCode());
					liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							for(int i=0;i<selectedValues.size();i++) {
							     if((selectedValues.get(i)).equals(courseSubjectDo.getId())){
							    	 selectedValues.remove(courseSubjectDo.getId());
							    	 Element element = Document.get().getElementById(courseSubjectDo.getId().toString());
							    	 if(element!=null){
							 			element.removeClassName("active");
							 		}
							     }
							 }
							removeGradeWidget(ulSelectedItems,courseSubjectDo.getId());
							liPanelWithClose.removeFromParent();
							lblGradeErrorMsg.setVisible(false);
						}
					});
					liPanelWithClose.setId(courseSubjectDo.getId());
					liPanelWithClose.setName(courseSubjectDo.getCode());
					liPanelWithClose.setDifferenceId(3);
					liPanelWithClose.getElement().setAttribute("tag", "taxonomy");
					ulSelectedItems.add(liPanelWithClose);
				}
            }
			if(courseObj!=null && courseObj.getTaxonomyCourse()!=null){
				for (final CourseSubjectDo courseSubjectDo : courseObj.getTaxonomyCourse()) {
					final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(courseSubjectDo.getName());
					liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							liPanelWithClose.removeFromParent();
							lblGradeErrorMsg.setVisible(false);
						}
					});
					liPanelWithClose.setId(courseSubjectDo.getId());
					liPanelWithClose.setName(courseSubjectDo.getName());
					liPanelWithClose.setRelatedId(courseSubjectDo.getSubjectId());
					liPanelWithClose.setDifferenceId(1);
					ulSelectedItems.add(liPanelWithClose);
				}
			}
			if(courseObj!=null && courseObj.getSubdomain()!=null){
				for (final CourseSubjectDo courseSubjectDo : courseObj.getSubdomain()) {
					final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(courseSubjectDo.getName());
					liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							liPanelWithClose.removeFromParent();
							lblGradeErrorMsg.setVisible(false);
						}
					});
					liPanelWithClose.setId(courseSubjectDo.getId());
					liPanelWithClose.setName(courseSubjectDo.getName());
					liPanelWithClose.setRelatedId(courseSubjectDo.getCourseId());
					liPanelWithClose.setRelatedSubjectId(courseSubjectDo.getSubjectId());
					liPanelWithClose.setDifferenceId(2);
					ulSelectedItems.add(liPanelWithClose);
				}
			}
        }
		setStaticData(type);
        if (courseObj == null ) {
            if (COLLECTION.equalsIgnoreCase(type) || ASSESSMENT.equalsIgnoreCase(type)) {
            	collectionTitle.removeStyleName("textAreaErrorMessage");
                collectionTitle.setText("");
                lblErrorMessage.setText("");
                lblErrorMessageForLO.setText("");
            }
        } else {
        	if(!i18n.GL3367().equalsIgnoreCase(courseObj.getTitle()) && !i18n.GL3396().equalsIgnoreCase(courseObj.getTitle()) && !"UntitledExternalAssessment".equalsIgnoreCase(courseObj.getTitle())){
        		 collectionTitle.setText(courseObj.getTitle());
        	}
        }
		learningObjective.setText(courseObj!=null?(courseObj.getDescription()!=null?courseObj.getDescription():""):"");
		collThumbnail.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				collThumbnail.setUrl((COLLECTION.equalsIgnoreCase(CollectionInfoView.this.type))?DEFULT_COLLECTION_IMG:DEFULT_ASSESSMENT_IMG);
			}
		});
		getUiHandlers().callCourseInfoTaxonomy();
	}

	public void setStaticData(String type){
		if(type.equalsIgnoreCase(ASSESSMENT)){
			colltitle.setText(i18n.GL3381());
			StringUtil.setAttributes(colltitle.getElement(), i18n.GL3381(), i18n.GL3381());
			collimagetitle.setText(i18n.GL3382());
			thumbnailImageContainer.setStyleName("assessmentThumbnail");
			tagcollectiontitle.setText(i18n.GL3385());
			saveCollectionBtn.setText(i18n.GL3386());
			collectionTitle.getElement().setPropertyString("placeholder",i18n.GL3396());
		}else{
			collectionTitle.getElement().setPropertyString("placeholder",i18n.GL3367());
			colltitle.setText(i18n.GL3380());
			StringUtil.setAttributes(colltitle.getElement(), i18n.GL3380(), i18n.GL3380());
			collimagetitle.setText(i18n.GL3383());
			thumbnailImageContainer.setStyleName("collectionThumbnail");
			tagcollectiontitle.setText(i18n.GL3384());
			saveCollectionBtn.setText(i18n.GL3368());
		}
	}
	@UiHandler("saveCollectionBtn")
	public void clickOnSaveCourseBtn(ClickEvent saveCourseEvent){
		TreeItem currentShelfTreeWidget = getUiHandlers().getSelectedWidget();
		saveCollectionBtn.addStyleName("disabled");
		saveCollectionBtn.setEnabled(false);
		spinnerImageVisibility(true);
		if(validateInputs()){
			CreateDo createOrUpDate=new CreateDo();
			createOrUpDate.setTitle(collectionTitle.getText());
			createOrUpDate.setDescription(learningObjective.getText());
			createOrUpDate.setCollectionType(type);
			createOrUpDate.setStandardIds(getSelectedStandards());
			createOrUpDate.setSubdomainIds(getSelectedSubDomainIds());
			createOrUpDate.setTaxonomyCourseIds(getSelectedCourseIds());
			createOrUpDate.setAudienceIds(StringUtil.getKeys(getAudienceContainer().getSelectedValues().keySet()));
			createOrUpDate.setDepthOfKnowledgeIds(StringUtil.getKeys(getDepthOfKnowledgeContainer().getSelectedValue().keySet()));
			createOrUpDate.setSkillIds(StringUtil.getKeysLong(getUiHandlers().getCenturySkillsPresenters().getView().getSelectedValuesFromAutoSuggest().keySet()));
			createOrUpDate.setLanguageObjective(getLanguageObjectiveContainer().getLanguageObjective());

			ThumbnailDo thumbnailObj = new ThumbnailDo();
			thumbnailObj.setUrl(collThumbnail.getUrl());
			createOrUpDate.setThumbnails(thumbnailObj);
			Element element=Document.get().getElementById("mycollectionUploadImage");
			if(element.getAttribute("filename")!=null){
				createOrUpDate.setMediaFilename(element.getAttribute("filename"));
			}
			lblErrorMessage.setVisible(false);
			collectionTitle.removeStyleName("textAreaErrorMessage");
			getUiHandlers().checkProfanity(collectionTitle.getText().trim(),true,0,type,createOrUpDate,currentShelfTreeWidget);
		}else{
			Window.scrollTo(collectionTitle.getAbsoluteLeft(), collectionTitle.getAbsoluteTop()-(collectionTitle.getOffsetHeight()*3));
			lblErrorMessage.setVisible(true);
			collectionTitle.addStyleName("textAreaErrorMessage");
			resetBtns();
			spinnerImageVisibility(false);
		}
	}

	@UiHandler("uploadImageLbl")
	public void clickOnUploadImg(ClickEvent saveCourseEvent){
		CreateDo createOrUpDate=new CreateDo();
		createOrUpDate.setTitle(collectionTitle.getText());
		createOrUpDate.setDescription(learningObjective.getText());
		createOrUpDate.setCollectionType(type);
		createOrUpDate.setDescription(learningObjective.getText());
		getUiHandlers().uploadCollectionImage(createOrUpDate);
	}

	/**
	 * This method is used to call create and update API
	 * @param index
	 * @param isCreate
	 */
	@Override
	public void callCreateAndUpdate(boolean isCreate, Boolean result, int index,String collectionType,CreateDo createOrUpDate,TreeItem currentShelfTreeWidget) {
		String title=collectionTitle.getText().trim();
		if((result && index==0)||(title.equalsIgnoreCase("")&&index==0)){
			SetStyleForProfanity.SetStyleForProfanityForTextBox(collectionTitle, lblErrorMessage, result);
			spinnerImageVisibility(false);
		}else if(result && index==1){
			SetStyleForProfanity.SetStyleForProfanityForTextArea(learningObjective, lblErrorMessageForLO, result);
			spinnerImageVisibility(false);
		}else{
			if(index==0){
				getUiHandlers().checkProfanity(createOrUpDate.getDescription().trim(),true,1,collectionType,createOrUpDate,currentShelfTreeWidget);
			}else if(index==1){
				if(courseObjG!=null && courseObjG.getGooruOid()!=null){
					getUiHandlers().updateCourseDetails(createOrUpDate,courseObjG.getGooruOid(),isCreate,courseObjG,currentShelfTreeWidget);
				}else{
					getUiHandlers().createAndSaveCourseDetails(createOrUpDate,isCreate,currentShelfTreeWidget);
				}
			}
		}
	}

	private class dokClickHandlers implements ClickHandler{
		public dokClickHandlers() {
		}
		@Override
		public void onClick(ClickEvent event) {
			setSelectedDepathOfKnowledge();
		}
	}
	private class CenturySkillsClickHandlers implements ClickHandler{
		public CenturySkillsClickHandlers() {
		}
		@Override
		public void onClick(ClickEvent event) {
			setSelectedCenturySkills();
		}
	}
	private class Language_ObjectiveClickHandlers implements ClickHandler{
		public Language_ObjectiveClickHandlers() {
		}
		@Override
		public void onClick(ClickEvent event) {
			setSelectedLanguageObjective();
		}
	}
	protected void setDepthOfKnlze() {
		List<String> depthofknowledgedetails = new ArrayList<>();
		if(courseObjG.getDepthOfKnowledge()!=null){
			if(courseObjG.getDepthOfKnowledge().size()>0){
				for(int i=0;i<courseObjG.getDepthOfKnowledge().size();i++){
					if(courseObjG.getDepthOfKnowledge().get(i).isSelected())
					{
						depthofknowledgedetails.add(courseObjG.getDepthOfKnowledge().get(i).getValue());
						isDepthOfKnlzeInfo = true;
					}
				}
				InfoUtil.setDepthofknowledgeDetails(depthofknowledgedetails, newtype, newlbl, newdok);
				//dKnowledgePanel.setVisible(true);
			}else{
				newdok.setVisible(false);
				isDepthOfKnlzeInfo = false;
			}
		}else{
			newdok.setVisible(false);
			isDepthOfKnlzeInfo = false;
		}
	}

	public void setSelectedDepathOfKnowledge(){
		if(isDepthOfKnlzeInfo){
			dok.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3376());
			depthOfKnowledgeContainer.setVisible(false);
			isDepthOfKnlzeInfo=false;
		}else{
			dok.setText("-"+"  "+i18n.GL3376());
			depthOfKnowledgeContainer.setVisible(true);
			isDepthOfKnlzeInfo=true;
		}
	}
	public void setSelectedCenturySkills(){
		if(isCenturySkillsInfo){
			centurySkills.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3377());
			centurySkillContainer.setVisible(false);
			isCenturySkillsInfo=false;
		}else{
			centurySkills.setText("-"+"  "+i18n.GL3377());
			centurySkillContainer.setVisible(true);
			isCenturySkillsInfo=true;
		}
	}
	/**
	 * This method is used to get the selected course id's
	 * @return
	 */
	public List<Integer> getSelectedStandardsIds(){
		List<Integer> taxonomyCourseIds=new ArrayList<>();
		Iterator<Widget> widgets=ulSelectedItems.iterator();
		while (widgets.hasNext()) {
			Widget widget=widgets.next();
			if(widget instanceof LiPanelWithClose){
				LiPanelWithClose obj=(LiPanelWithClose) widget;
				Integer intVal = (int)obj.getId();
				taxonomyCourseIds.add(intVal);
			}
		}
		return taxonomyCourseIds;
	}
	public void setSelectedLanguageObjective(){
		if(isLanguageObjectInfo){
			languageObj.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3378());
			languageObjectiveContainer.setVisible(false);
			isLanguageObjectInfo=false;
		}else{
			languageObj.setText("-"+"  "+i18n.GL3378());
			languageObjectiveContainer.setVisible(true);
			isLanguageObjectInfo=true;
		}
	}
	@Override
	public DepthKnowledgeView getDepthOfKnowledgeContainer() {
		return depthOfKnowledgeContainer;
	}
	@Override
	public LanguageView getLanguageObjectiveContainer() {
		return languageObjectiveContainer;
	}
	@Override
	public HTMLPanel getCenturySkillContainer() {
		return centurySkillContainer;
	}
	@Override
	public AudienceView getAudienceContainer() {
		return audienceContainer;
	}
	@Override
	public void setInSlot(Object slot, Widget content) {
		super.setInSlot(slot, content);
		if(slot==CollectionInfoPresenter.CENTURYSKILLS){
			getCenturySkillContainer().clear();
			getCenturySkillContainer().add(content);
		}
	}


	private class OnClickTaxonomy implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			collectionLiPanelWithCloseArray.clear();
			for(int i=0;i<ulSelectedItems.getWidgetCount();i++){
				collectionLiPanelWithCloseArray.add((LiPanelWithClose) ulSelectedItems.getWidget(i));
			}
			getUiHandlers().invokeTaxonomyPopup("collection",collectionLiPanelWithCloseArray);
		}
	}

	/*@Override
	public void addTaxonomyData(UlPanel selectedUlContainer) {
		Iterator<Widget> widgets = selectedUlContainer.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof LiPanelWithClose){
				ulSelectedItems.add(widget);
			}
		}
	}*/

	@Override
	public void setCollectionImage(String url, String mediaFileName) {
		Element element=Document.get().getElementById("mycollectionUploadImage");
		element.removeAttribute("src");
		element.setAttribute("src", url+"?id="+Math.random());
		element.setAttribute("filename", mediaFileName);
	}

	@Override
	public FolderDo getFolderDo(){
		return courseObjG;
	}

	public void resetDOK_Century_Lang(){
		languageObj.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3378());
		languageObjectiveContainer.setVisible(false);
		isLanguageObjectInfo=false;
		centurySkills.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3377());
		centurySkillContainer.setVisible(false);
		isCenturySkillsInfo=false;
		dok.setText(i18n.GL_SPL_PLUS()+" "+i18n.GL3376());
		depthOfKnowledgeContainer.setVisible(false);
		isDepthOfKnlzeInfo=false;

	}

	/**
	 * Adds the selected domains from the taxonomy popup into collection info view.
	 */
	@Override
	public void addTaxonomyData(List<LiPanelWithClose> liPanelWithCloseArray,List<LiPanelWithClose> removedLiPanelWithCloseArray) {
		for(int i=0;i<liPanelWithCloseArray.size();i++){
			if(isWidgetExists(liPanelWithCloseArray.get(i).getId())){
				ulSelectedItems.add(liPanelWithCloseArray.get(i));
			}

			if(i<removedLiPanelWithCloseArray.size()){
				removeFromUlSelectedItemsContainer(removedLiPanelWithCloseArray.get(i).getId());
			}
		}

	}

	/**
	 * Checks the selected widgets in info view got from taxonomy popup.
	 * @param id
	 * @return
	 */
	private boolean isWidgetExists(long id) {
		boolean flag = true;
		Iterator<Widget> widgets = ulSelectedItems.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof LiPanelWithClose && ((LiPanelWithClose) widget).getId() == id){
				flag = false;
			}
		}
		return flag;
	}


	/**
	 * Removes the widget, which has been removed from taxonomy popup from info view
	 * @param removeWidgetId
	 */
	private void removeFromUlSelectedItemsContainer(long removeWidgetId) {
		Iterator<Widget> widgets = ulSelectedItems.iterator();
		while(widgets.hasNext()){
			Widget widget = widgets.next();
			if(widget instanceof LiPanelWithClose && ((LiPanelWithClose) widget).getId() == removeWidgetId){
				widget.removeFromParent();
			}
		}
	}

	protected void hideDropDown(NativePreviewEvent event) {
		if(event.getTypeInt()==Event.ONCLICK){
    		Event nativeEvent = Event.as(event.getNativeEvent());
        	boolean target=eventTargetsPopup(nativeEvent);
        	if(!target){
        		standardsDropListValues.getElement().removeAttribute("style");
        	}
    	}
	}

	private boolean eventTargetsPopup(NativeEvent event) {
		EventTarget target = event.getEventTarget();
		if (Element.is(target)) {
			return standardsDropListValues.getElement().isOrHasChild(Element.as(target))||standardsDropListValues.getElement().isOrHasChild(Element.as(target));
		}
		return false;
	}

    public boolean validateInputs(){
        if (collectionTitle.getText().trim().equalsIgnoreCase("") || collectionTitle.getText().trim().equalsIgnoreCase(i18n.GL3367())){
        	lblErrorMessage.setText("collection".equalsIgnoreCase(type)?"Please Enter Collection Title":"Please Enter Assessment Title");
            return false;
        }
        boolean isValidSting = StringUtil.checkItContainesURL(collectionTitle.getText());
        if(isValidSting){
        	lblErrorMessage.setVisible(true);
			lblErrorMessage.setText(i18n.GL0323());
        	return false;
        }else{
        	lblErrorMessage.setVisible(false);
        	return true;
        }
    }

	@UiHandler("collectionTitle")
	public void collectionTitleKeyUphandler(KeyUpEvent event){
		collectionTitle.removeStyleName("textAreaErrorMessage");
		lblErrorMessage.setVisible(false);
	}
	@Override
	public void resetBtns() {
		saveCollectionBtn.removeStyleName("disabled");
		saveCollectionBtn.setEnabled(true);
	}
	@Override
	public HTMLPanel getStadardsPanel(){
		return pnlStandards;
	}
	@Override
	public Image getCollThumbnail() {
		return collThumbnail;
	}
	public void setCollThumbnail(Image collThumbnail) {
		this.collThumbnail = collThumbnail;
	}

	public void displaySelectedStandards(List<Map<String,String>> standListArray){
		for (int i=0;i<standListArray.size();i++){
			final Map<String, String> standard = standListArray.get(i);
			if (!selectedValues.contains(standard.get("selectedCodeId"))){
				ulSelectedItems.add(generateLiPanel(standard, "standards"));
			}
		}
	}
	private LiPanelWithClose generateLiPanel(final Map<String, String> standard, String tagValue) {
		final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(standard.get("selectedCodeVal"));
		liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				//This will remove the selected value when we are trying by close button
				removeGradeWidget(ulSelectedItems,Long.parseLong(standard.get("selectedCodeId")));
				liPanelWithClose.removeFromParent();
			}
		});
		selectedValues.add(Integer.parseInt(standard.get("selectedCodeId")));
		liPanelWithClose.setId(Long.parseLong(standard.get("selectedCodeId")));
		liPanelWithClose.setName(standard.get("selectedCodeVal"));
		liPanelWithClose.setRelatedId(Integer.parseInt(standard.get("selectedCodeId")));
		liPanelWithClose.setDifferenceId(Integer.parseInt(standard.get("selectedDifferenceId")));
		liPanelWithClose.getElement().setAttribute("tag", tagValue);
		return liPanelWithClose;
	}
	public void checkStandarsList(List<String> standarsPreferencesList) {
		
		
		if(standarsPreferencesList!=null){
			if(standarsPreferencesList.contains("CCSS")){
				isCCSSAvailable = true;
			}else{
				isCCSSAvailable = false;
			}
			if(standarsPreferencesList.contains("NGSS")){
				isNGSSAvailable = true;
			}else{
				isNGSSAvailable = false;
			}
			if(standarsPreferencesList.contains("TEKS")){
				isTEKSAvailable = true;
			}else{
				isTEKSAvailable = false;
			}
			if(standarsPreferencesList.contains("CA")){
				isCAAvailable = true;
			}else{
				isCAAvailable = false;
			}
		}

		populateStandardValues();
	}

	public void getAddStandards() {
		if(!AppClientFactory.isAnonymous()){
			AppClientFactory.getInjector().getUserService().getUserProfileV2Details(AppClientFactory.getLoggedInUser().getGooruUId(),
				USER_META_ACTIVE_FLAG,
				new SimpleAsyncCallback<ProfileDo>() {
					@Override
					public void onSuccess(final ProfileDo profileObj) {
						if(profileObj.getUser().getMeta() != null && profileObj.getUser().getMeta().getTaxonomyPreference() != null && profileObj.getUser().getMeta().getTaxonomyPreference().getCode() != null){
							checkStandarsList(profileObj.getUser().getMeta().getTaxonomyPreference().getCode());
						}
					}

				});
		}else{
			isCCSSAvailable = true;
			isNGSSAvailable = true;
			isCAAvailable = true;
			isTEKSAvailable = false;
		}
	}
	
	@Override
	public void spinnerImageVisibility(boolean isVisible){
		spinnerIconContainer.setVisible(isVisible); 
	}
	
	@Override
	public void setStandardsValue(List<StandardFo> standardFoObj)
	{
        if(standardFoObj!=null && standardFoObj.size()>0){
            //Render the existing standards
        	for(final StandardFo courseSubjectDo : standardFoObj) {
				final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(courseSubjectDo.getCode());
				liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						for(int i=0;i<selectedValues.size();i++) {
						     if((selectedValues.get(i)).equals(courseSubjectDo.getId())){
						    	 selectedValues.remove(courseSubjectDo.getId());
						    	 Element element = Document.get().getElementById(courseSubjectDo.getId().toString());
						    	 if(element!=null){
						 			element.removeClassName("active");
						 		}
						     }
						 }
						removeGradeWidget(ulSelectedItems,courseSubjectDo.getId());
						liPanelWithClose.removeFromParent();
						lblGradeErrorMsg.setVisible(false);
					}
				});
				liPanelWithClose.setId(courseSubjectDo.getId());
				liPanelWithClose.setName(courseSubjectDo.getCode());
				liPanelWithClose.setDifferenceId(3);
				liPanelWithClose.getElement().setAttribute("tag", "taxonomy");
				ulSelectedItems.add(liPanelWithClose);
			}
        }
	}
	/*@UiHandler("cropImage")
	public void onClickOfCropImage(ClickEvent event){
		getUiHandlers().displayCropImage(collThumbnail.getUrl());
	}*/
}

