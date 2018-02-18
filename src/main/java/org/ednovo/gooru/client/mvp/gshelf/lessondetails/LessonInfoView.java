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
package org.ednovo.gooru.client.mvp.gshelf.lessondetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.code.CourseSubjectDo;
import org.ednovo.gooru.application.shared.model.folder.CreateDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.library.DomainStandardsDo;
import org.ednovo.gooru.application.shared.model.library.SubDomainStandardsDo;
import org.ednovo.gooru.application.shared.model.library.SubSubDomainStandardsDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.gshelf.collectiondetails.StandardsCodeDecView;
import org.ednovo.gooru.client.mvp.gshelf.util.AssessmentPopupWidget;
import org.ednovo.gooru.client.mvp.gshelf.util.CourseGradeWidget;
import org.ednovo.gooru.client.mvp.gshelf.util.LiPanelWithClose;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.SetStyleForProfanity;

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
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Event.NativePreviewEvent;
import com.google.gwt.user.client.Event.NativePreviewHandler;
import com.google.gwt.user.client.Window;
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
public class LessonInfoView extends BaseViewWithHandlers<LessonInfoUiHandlers> implements IsLessonInfoView {

	private static final LessonViewUiBinder uiBinder = GWT.create(LessonViewUiBinder.class);

	@UiTemplate("LessonInfoView.ui.xml")
	interface LessonViewUiBinder extends UiBinder<Widget, LessonInfoView> {
	}

	@UiField HTMLPanel lessonInfo,standardsUI,pnlStandards,spinnerIconContainer;
	@UiField TextBox lessonTitle;
	@UiField PPanel lessonNamePpanel;
	@UiField UlPanel standardsDropListValues;
	@UiField HTMLEventPanel btnStandardsBrowse, taxonomyToggleBtn;
	@UiField Button saveLessonBtn,btnSaveAndCreateCollection,btnSaveAndCreateAssessment,taxonomyBtn;
	@UiField Label lblErrorMessage,lblLessonErrorMsg,lblCountMsg;
	@UiField UlPanel ulSelectedItems;


	AssessmentPopupWidget assessmentPopup;

	private static final MessageProperties i18n = GWT.create(MessageProperties.class);

	String[] standardsTypesArray = new String[]{i18n.GL3379(),i18n.GL3322(),i18n.GL3323(),i18n.GL3324(),i18n.GL3325(),i18n.GL3321()};
	List<Integer> selectedValues=new ArrayList<>();

	final String ACTIVE="active";
	final String COLLECTION="collection";
	final String ASSESSMENT="assessment";
	private static final String ASSESSMENT_URL = "assessment/url";
	private static final String O1_LEVEL = "o1";
	private static final String O2_LEVEL = "o2";
	CourseGradeWidget courseGradeWidget;
	public FolderDo courseObj;
	
	private boolean isCCSSAvailable =false;
	private boolean isNGSSAvailable =false;
	private boolean isTEKSAvailable =false;
	private boolean isCAAvailable =false;
	
	String USER_META_ACTIVE_FLAG = "userMetaActiveFlag";

	List<LiPanelWithClose> lessonLiPanelWithCloseArray = new ArrayList<>();

	/**
	 * Class constructor
	 * @param eventBus {@link EventBus}
	 */
	@Inject
	public LessonInfoView() {
		setWidget(uiBinder.createAndBindUi(this));
		lessonInfo.getElement().setId("pnlLessonInfo");
		lessonInfo.getElement().setAttribute("style", "min-height:"+Window.getClientHeight()+"px");
		lblErrorMessage.setText("Please Enter Valid Lesson Name");
		lblErrorMessage.setVisible(false);
		Window.addResizeHandler(new ResizeHandler() {
			@Override
			public void onResize(ResizeEvent event) {
				lessonInfo.getElement().setAttribute("style", "min-height:"+Window.getClientHeight()+"px");
			}
		});

		taxonomyBtn.addClickHandler(new OnClickTaxonomy());
		taxonomyToggleBtn.addClickHandler(new OnClickTaxonomy());
		Event.addNativePreviewHandler(new NativePreviewHandler() {
                        @Override
			public void onPreviewNativeEvent(NativePreviewEvent event) {
				hideDropDown(event);
			}
		});
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
		lessonTitle.getElement().setPropertyString("placeholder", i18n.GL3365());
		lessonTitle.addBlurHandler(new BlurHandler() {
			@Override
			public void onBlur(BlurEvent event) {
				SetStyleForProfanity.SetStyleForProfanityForTextBox(lessonTitle, lblErrorMessage, false);
			}
		});
		lessonTitle.addKeyUpHandler(new TitleKeyUpHandler());
		lessonTitle.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				lessonTitle.getElement().getStyle().clearBackgroundColor();
				lessonTitle.getElement().getStyle().setBorderColor("#ccc");
				lblErrorMessage.setVisible(false);
			}
		});
		lessonNamePpanel.setText(i18n.GL3478());
	}

	/**
	 * This class is used for validation on collection title keypress.
	 *
	 */
	private class TitleKeyUpHandler implements KeyUpHandler {
		public void onKeyUp(KeyUpEvent event) {
			lblErrorMessage.setVisible(false);
			if(lessonTitle.getText().length() >= 50) {
				lblErrorMessage.setText(i18n.GL0143());
				lblErrorMessage.getElement().setAttribute("alt",i18n.GL0143());
				lblErrorMessage.getElement().setAttribute("title",i18n.GL0143());
				lblErrorMessage.setVisible(true);
			}
		}
	}
        @Override
	public void displayStandardsList(final List<DomainStandardsDo> standardsList){
		standardsUI.clear();
		final String selValues = getSelectedStandards().toString();
	        	for(DomainStandardsDo standardsList1 : standardsList){
	            Boolean flgLevelOne = false;
	            if (standardsList1.getCode().contains("Math")) {
	                flgLevelOne = true;
	            }
	            final StandardsCodeDecView standardsCode = new StandardsCodeDecView(standardsList1.getCode(), standardsList1.getLabel(), flgLevelOne);
	            final DomainStandardsDo domainStand = standardsList1;
	            if (domainStand.getTypeId()!=null && !(standardsList1.getCode().contains("ELA"))) {
	                if(domainStand.getTypeId().equals(1)){
	                    
	                }
	                else if(domainStand.getTypeId().equals(2)){
	                    standardsCode.getWidgetContainer().getElement().getStyle().setPaddingLeft(35, Unit.PX);
	                }else{
	                    standardsCode.getWidgetContainer().getElement().getStyle().setPaddingLeft(70, Unit.PX);
	                }
	            }
	            standardsCode.getWidgetContainer().getElement().setId(domainStand.getCodeId().toString());
	            if (selValues.contains(standardsList1.getCodeId().toString())) {
	                standardsCode.getWidgetContainer().addStyleName("active");
	            }
	            standardsCode.getWidgetContainer().addClickHandler(new ClickHandler() {
	                @Override
	                public void onClick(ClickEvent event) {
	                    if(!standardsCode.getWidgetContainer().getStyleName().contains("active")){
	                    	if(ulSelectedItems.getWidgetCount()>=15){
	                    		lblLessonErrorMsg.setVisible(true);
	                    		lblLessonErrorMsg.setText(i18n.GL3569());
		                	}else{
		                		lblLessonErrorMsg.setVisible(false);
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
		                                lblLessonErrorMsg.setVisible(false);
		                            }
		                        });
		                        //selectedValues.add(domainStand.getCodeId());
		                        liPanelWithClose.setId(domainStand.getCodeId());
		                        liPanelWithClose.setName(domainStand.getCode());
		                        liPanelWithClose.setDifferenceId(3);
		                        liPanelWithClose.setRelatedId(domainStand.getCodeId());
		                        ulSelectedItems.add(liPanelWithClose);
		                	}
					}else{
						standardsCode.getWidgetContainer().removeStyleName("active");
						removeGradeWidget(ulSelectedItems,domainStand.getCodeId());
						lblLessonErrorMsg.setVisible(false);
					}
				}
			});
	         standardsUI.add(standardsCode);
	         displaySubStandardsList(standardsList1.getNode());
	     }
	}

	public void displaySubStandardsList(final List<SubDomainStandardsDo> standardsList){
		//	standardsUI.clear();
		final String selValues = getSelectedStandards().toString();
            for (SubDomainStandardsDo standardsList1 : standardsList){
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
	                    		lblLessonErrorMsg.setVisible(true);
	                    		lblLessonErrorMsg.setText(i18n.GL3569());
		                	}else{
		                		lblLessonErrorMsg.setVisible(false);
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
	                                    lblLessonErrorMsg.setVisible(false);
	                                }
	                            });
	                            //selectedValues.add(domainStand.getCodeId());
	                            liPanelWithClose.setId(domainStand.getCodeId());
	                            liPanelWithClose.setName(domainStand.getCode());
	                            liPanelWithClose.setDifferenceId(3);
	                            liPanelWithClose.setRelatedId(domainStand.getCodeId());
	                            ulSelectedItems.add(liPanelWithClose);
		                	}
                        }else{
                            standardsCode.getWidgetContainer().removeStyleName("active");
                            removeGradeWidget(ulSelectedItems,domainStand.getCodeId());
                            lblLessonErrorMsg.setVisible(false);
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
        for(SubSubDomainStandardsDo standardsList1 : standardsList) {
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
                    		lblLessonErrorMsg.setVisible(true);
                    		lblLessonErrorMsg.setText(i18n.GL3569());
	                	}else{
	                		lblLessonErrorMsg.setVisible(false);
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
	                                lblLessonErrorMsg.setVisible(false);
	                            }
	                        });
	                        //selectedValues.add(domainStand.getCodeId());
	                        liPanelWithClose.setId(domainStand.getCodeId());
	                        liPanelWithClose.setName(domainStand.getCode());
	                        liPanelWithClose.setDifferenceId(3);
	                        liPanelWithClose.setRelatedId(domainStand.getCodeId());
	                        ulSelectedItems.add(liPanelWithClose);
	                	}
                    }else{
                        standardsCode.getWidgetContainer().removeStyleName("active");
                        removeGradeWidget(ulSelectedItems,domainStand.getCodeId());
                        lblLessonErrorMsg.setVisible(false);
                    }
                }
            });
            standardsUI.add(standardsCode);
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
            if(liPanel.getElement().getAttribute("style")!=null && !liPanel.getElement().getAttribute("style").equalsIgnoreCase("opacity:0.5;")){
		            liPanel.addClickHandler(new ClickHandler() {
		                @Override
		                public void onClick(ClickEvent event) {
						String standardsVal = event.getRelativeElement().getAttribute("id");
						String standardsDesc = event.getRelativeElement().getAttribute("standarddesc");
						lessonLiPanelWithCloseArray.clear();
						for(int i=0;i<ulSelectedItems.getWidgetCount();i++){
							lessonLiPanelWithCloseArray.add((LiPanelWithClose) ulSelectedItems.getWidget(i));
						}
						getUiHandlers().showStandardsPopup(standardsVal,standardsDesc, lessonLiPanelWithCloseArray);
					}
				});
            }
            standardsDropListValues.add(liPanel);
        }
	}
	@UiHandler("saveLessonBtn")
	public void clickOnSaveCourseBtn(ClickEvent saveCourseEvent){
		TreeItem currentShelfTreeWidget = getUiHandlers().getSelectedWidget();
		String courseId=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
		String unitId=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL,null);
		saveLessonBtn.addStyleName("disabled");
		saveLessonBtn.setEnabled(false);
		spinnerImageVisibility(true);
		if (validateInputs()) {
			final CreateDo createOrUpDate=new CreateDo(); 
			createOrUpDate.setTitle(lessonTitle.getText());
			createOrUpDate.setStandardIds(getSelectedStandards());
			createOrUpDate.setTaxonomyCourseIds(getSelectedCourseIds());
			createOrUpDate.setSubdomainIds(getSelectedSubDomainIds());
			lblErrorMessage.setVisible(false);
			lessonTitle.removeStyleName("textAreaErrorMessage");
			getUiHandlers().checkProfanity(lessonTitle.getText().trim(),false,null,createOrUpDate,courseId,unitId,currentShelfTreeWidget);
		}else{
			Window.scrollTo(lessonTitle.getAbsoluteLeft(), lessonTitle.getAbsoluteTop()-(lessonTitle.getOffsetHeight()*3));
			lessonTitle.setStyleName("textAreaErrorMessage");
			lessonTitle.addStyleName("form-control");	
			lblErrorMessage.setVisible(true);
			resetBtns();
			spinnerImageVisibility(false);
		}
	}
	@UiHandler("btnSaveAndCreateCollection")
	public void clickOnSaveAndCreateCollection(ClickEvent saveCourseEvent){
		TreeItem currentShelfTreeWidget = getUiHandlers().getSelectedWidget();
		if(currentShelfTreeWidget.getChildCount()<10){
			String courseId=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
			String unitId=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL,null);
			btnSaveAndCreateCollection.addStyleName("disabled");
			btnSaveAndCreateCollection.setEnabled(false);
			spinnerImageVisibility(true);
			if (validateInputs()) {
				final CreateDo createOrUpDate=new CreateDo(); 
				createOrUpDate.setTitle(lessonTitle.getText());
				createOrUpDate.setStandardIds(getSelectedStandards());
				createOrUpDate.setTaxonomyCourseIds(getSelectedCourseIds());
				createOrUpDate.setSubdomainIds(getSelectedSubDomainIds());
				lblErrorMessage.setVisible(false);
				lessonTitle.removeStyleName("textAreaErrorMessage");
				getUiHandlers().checkProfanity(lessonTitle.getText().trim(),true,COLLECTION,createOrUpDate,courseId,unitId,currentShelfTreeWidget);
			}else{
				Window.scrollTo(lessonTitle.getAbsoluteLeft(), lessonTitle.getAbsoluteTop()-(lessonTitle.getOffsetHeight()*3));
				lessonTitle.setStyleName("textAreaErrorMessage");
				lessonTitle.addStyleName("form-control");	
				lblErrorMessage.setVisible(true);
				resetBtns();
				spinnerImageVisibility(false);
			}
		}else{
			btnSaveAndCreateCollection.addStyleName("disabled");
			lblCountMsg.setText(i18n.GL3491());
		}
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
		if(courseObj!=null){
			courseObj.setTaxonomyCourse(courseList);
		}
		return taxonomyCourseIds;
	}
	@UiHandler("btnSaveAndCreateAssessment")
	public void clickOnSaveAndCreateAssessment(ClickEvent saveCourseEvent){
		final TreeItem currentShelfTreeWidget = getUiHandlers().getSelectedWidget();
		if(currentShelfTreeWidget.getChildCount()<10){
			Window.enableScrolling(false);
			final String courseId=AppClientFactory.getPlaceManager().getRequestParameter(O1_LEVEL,null);
			final String unitId=AppClientFactory.getPlaceManager().getRequestParameter(O2_LEVEL,null);
			if(validateInputs()){
				final CreateDo createOrUpDate=new CreateDo(); 
				createOrUpDate.setTitle(lessonTitle.getText());
				createOrUpDate.setStandardIds(getSelectedStandards());
				createOrUpDate.setTaxonomyCourseIds(getSelectedCourseIds());
				createOrUpDate.setSubdomainIds(getSelectedSubDomainIds());
				assessmentPopup=new AssessmentPopupWidget() {
					@Override
					public void clickOnNoramlAssessmentClick() {
						spinnerImageVisibility(true);
						assessmentPopup.hide();
						Window.enableScrolling(true);
						//This will display the normal assessment info
						getUiHandlers().checkProfanity(lessonTitle.getText().trim(),true,ASSESSMENT,createOrUpDate,courseId,unitId,currentShelfTreeWidget);
					}
					@Override
					public void clickOnExternalAssessmentClick() {
						spinnerImageVisibility(true);
						assessmentPopup.hide();
						Window.enableScrolling(true);
						//This will display the external assessment info
						getUiHandlers().checkProfanity(lessonTitle.getText().trim(),true,ASSESSMENT_URL,createOrUpDate,courseId,unitId,currentShelfTreeWidget);
					}
				};
				assessmentPopup.setGlassEnabled(true);
				assessmentPopup.show();
				assessmentPopup.center();
			}else{
				Window.scrollTo(lessonTitle.getAbsoluteLeft(), lessonTitle.getAbsoluteTop()-(lessonTitle.getOffsetHeight()*3));
				lessonTitle.setStyleName("textAreaErrorMessage");
				lessonTitle.addStyleName("form-control");	
				lblErrorMessage.setVisible(true);
				resetBtns();
			}
		}else{
			btnSaveAndCreateAssessment.addStyleName("disabled");
			lblCountMsg.setText(i18n.GL3491());
		}
	}
	@Override
	public void callCreateAndUpdate(boolean isCreate,boolean result,String type,CreateDo createOrUpDate,String courseId,String unitId,TreeItem currentShelfTreeWidget){
		if(result){
			spinnerImageVisibility(false);
			SetStyleForProfanity.SetStyleForProfanityForTextBox(lessonTitle, lblErrorMessage, result);
		}else{
			if(courseObj!=null && courseObj.getGooruOid()!=null){
				getUiHandlers().updateLessonDetails(createOrUpDate,courseObj.getGooruOid(),isCreate,type,courseObj,currentShelfTreeWidget);
			}else{
				getUiHandlers().createAndSaveLessonDetails(createOrUpDate,isCreate,type,courseId,unitId,currentShelfTreeWidget);
			}
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
					CourseSubjectDo courseObjLocal=new CourseSubjectDo();
					selectedValues.add((int)obj.getId());
					courseObjLocal.setId((int)obj.getId());
					courseObjLocal.setCode(obj.getName());
					courseObjLocal.setSubjectId(obj.getRelatedId());
					courseList.add(courseObjLocal);
				}
			}
		}
		if(courseObj!=null){
			courseObj.setStandards(courseList);
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
		if(courseObj!=null){
			courseObj.setSubdomain(courseList);
		}
		return taxonomyCourseIds;
	}

	@Override
	public void setLessonInfoData(FolderDo folderObj) {
		this.courseObj=folderObj;
		ulSelectedItems.clear();
		selectedValues.clear();
		standardsUI.clear();
		lessonTitle.setText(folderObj==null?"":!folderObj.getTitle().equalsIgnoreCase(i18n.GL3365())?folderObj.getTitle():"");
		lblCountMsg.setText("");
		if(folderObj!=null){
			if(folderObj.getStandards()!=null && folderObj.getStandards().size()>0){
				//Render the existing standards
				for(final CourseSubjectDo courseSubjectDo : folderObj.getStandards()) {
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
						    lblLessonErrorMsg.setVisible(false);
						}
					});
					liPanelWithClose.setId(courseSubjectDo.getId());
					liPanelWithClose.setName(courseSubjectDo.getCode());
					liPanelWithClose.setDifferenceId(3);
					ulSelectedItems.add(liPanelWithClose);
				}
			}
			if(folderObj!=null && folderObj.getTaxonomyCourse()!=null){
				for (final CourseSubjectDo courseSubjectDo : folderObj.getTaxonomyCourse()) {
					final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(courseSubjectDo.getName());
					liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							liPanelWithClose.removeFromParent();
						    lblLessonErrorMsg.setVisible(false);
						}
					});
					liPanelWithClose.setId(courseSubjectDo.getId());
					liPanelWithClose.setName(courseSubjectDo.getName());
					liPanelWithClose.setRelatedId(courseSubjectDo.getSubjectId());
					liPanelWithClose.setDifferenceId(1);
					ulSelectedItems.add(liPanelWithClose);
				}
			}
			if(folderObj!=null && folderObj.getSubdomain()!=null){
				for (final CourseSubjectDo courseSubjectDo : folderObj.getSubdomain()) {
					final LiPanelWithClose liPanelWithClose=new LiPanelWithClose(courseSubjectDo.getName());
					liPanelWithClose.getCloseButton().addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							liPanelWithClose.removeFromParent();
						    lblLessonErrorMsg.setVisible(false);
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
		getUiHandlers().callCourseInfoTaxonomy();
	}
	/**
	 * This method will remove the widget based on the codeId in the UlPanel
	 * @param ulPanel
	 * @param codeId
	 */
	public void removeGradeWidget(UlPanel ulPanel,Integer codeId){
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

	private class OnClickTaxonomy implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			lessonLiPanelWithCloseArray.clear();
			for(int i=0;i<ulSelectedItems.getWidgetCount();i++){
				lessonLiPanelWithCloseArray.add((LiPanelWithClose) ulSelectedItems.getWidget(i));
			}
			getUiHandlers().invokeTaxonomyPopup("Lesson",lessonLiPanelWithCloseArray);
		}
	}

	/**
	 * Adds the selected domains from the taxonomy popup into lesson info view.
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
            if (lessonTitle.getText() == null) {
                return false;
            }
		String collectionTitleStr=lessonTitle.getText().trim();
		if(collectionTitleStr.equalsIgnoreCase("")||collectionTitleStr.equalsIgnoreCase(i18n.GL3365())){
			return false;
		}else{
			return true;
		}
	}

	@UiHandler("lessonTitle")
	public void collectionTitleKeyUphandler(KeyUpEvent event){
		lessonTitle.removeStyleName("textAreaErrorMessage");
		lblErrorMessage.setVisible(false);
	}
	/**
	 * Adds the selected domains from the taxonomy popup into unit info view.
	 */
	@Override
	public void resetBtns() {
		saveLessonBtn.removeStyleName("disabled");
		saveLessonBtn.setEnabled(true);
		btnSaveAndCreateCollection.removeStyleName("disabled");
		btnSaveAndCreateCollection.setEnabled(true);
		btnSaveAndCreateAssessment.removeStyleName("disabled");
		btnSaveAndCreateAssessment.setEnabled(true);
	}
	@Override
	public HTMLPanel getStadardsPanel(){
		return pnlStandards;
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
//				if(selValues.contains(standard.get("selectedCodeId"))){
//					selectedValues.remove(standard.get("selectedCodeId"));
//				}
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
}