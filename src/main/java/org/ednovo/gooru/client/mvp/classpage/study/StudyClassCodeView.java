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
package org.ednovo.gooru.client.mvp.classpage.study;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.child.ChildView;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.client.CssTokens;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpages.newclasspage.NewClassPopupView;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.socialshare.SentEmailSuccessVc;
import org.ednovo.gooru.client.uc.AlertMessageUc;
import org.ednovo.gooru.client.uc.H2Panel;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.client.uc.PPanel;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


/**
 * @fileName : StudyClassCodeView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 21-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class StudyClassCodeView extends ChildView<StudyClassCodePresenter> implements IsStudyClassCodeView{


	@UiField Anchor moreLinkAnr,courseAnr;

	@UiField TextBoxWithPlaceholder txtClassCode;

	@UiField Button codeBtnEnter,btnCreateClass;

	@UiField H3Panel studentHeader,createPanel,courseContentPanel,classCodePanel;

	@UiField H2Panel teacherHeader;

	@UiField PPanel hintPanel,courseNotesPanel;

	@UiField InlineLabel noteOne,noteTwo,noteThree;

	MessageProperties i18n = GWT.create(MessageProperties.class);

	private NewClassPopupView newPopup = null;

	AlertMessageUc alertMessageUc;

	private boolean isValid=true;

	Button goBtn;

	private static StudyClassCodeViewUiBinder uiBinder = GWT.create(StudyClassCodeViewUiBinder.class);

	interface StudyClassCodeViewUiBinder extends	UiBinder<Widget, StudyClassCodeView> {
	}

	public StudyClassCodeView() {
		initWidget(uiBinder.createAndBindUi(this));
		setIds();
		setPresenter(new StudyClassCodePresenter(this));
		btnCreateClass.addClickHandler(new CreateNewClass());
		codeBtnEnter.addClickHandler(new OnEnterClassCodeOpen(codeBtnEnter, txtClassCode));
		Window.enableScrolling(true);
	}


	private void setIds() {

		btnCreateClass.setText(i18n.GL1771());
		btnCreateClass.getElement().setId("btnCreateClass");
		btnCreateClass.getElement().setAttribute("alt",i18n.GL1771());
		btnCreateClass.getElement().setAttribute("title",i18n.GL1771());

		moreLinkAnr.setText(i18n.GL3462_12());
		moreLinkAnr.setHref("http://support.gooru.org/hc/en-us/sections/201152845");
		moreLinkAnr.setTarget("_blank");




		txtClassCode.setPlaceholder(i18n.GL1785());
		txtClassCode.getElement().setId("txtCode");

		codeBtnEnter.setText(i18n.GL0213());
		codeBtnEnter.getElement().setId("codeBtnEnter");
		codeBtnEnter.getElement().setAttribute("alt",i18n.GL0213());
		codeBtnEnter.getElement().setAttribute("title",i18n.GL0213());


		studentHeader.setText(i18n.GL3450_1());
		studentHeader.getElement().setId("studentHeaderId");


		teacherHeader.setText(i18n.GL3450_2());
		teacherHeader.getElement().setId("teacherHeaderId");


		createPanel.setText(i18n.GL3450_3());
		createPanel.getElement().setId("createPanelId");


		hintPanel.setText(i18n.GL3450_4());
		hintPanel.getElement().setId("hintPanelId");


		courseContentPanel.setText(i18n.GL3450_5());
		courseContentPanel.getElement().setId("courseContentPanelId");


		courseNotesPanel.setText(i18n.GL3450_6());
		courseNotesPanel.getElement().setId("courseNotesPanelId");


		classCodePanel.setText(i18n.GL3450_7());
		classCodePanel.getElement().setId("classCodePanelId");


		noteOne.setText(i18n.GL_GRR_NUMERIC_ONE());
		noteOne.getElement().setId("noteOneId");


		noteTwo.setText(i18n.GL_GRR_NUMERIC_TWO());
		noteTwo.getElement().setId("noteTwoId");


		noteThree.setText(i18n.GL_GRR_NUMERIC_THREE());
		noteThree.getElement().setId("noteThreeId");


		courseAnr.setText(i18n.GL3450_8());
		courseAnr.getElement().setId("courseAnrId");


		txtClassCode.addFocusHandler(new FocusHandler() {

			@Override
			public void onFocus(FocusEvent event) {
				txtClassCode.getElement().addClassName("textTransform");
			}
		});
		txtClassCode.addBlurHandler(new BlurHandler() {

			@Override
			public void onBlur(BlurEvent event) {
				if (txtClassCode.getText().length() > 0 ){

				}else{
					txtClassCode.getElement().removeClassName("textTransform");
				}
			}
		});



	}

	public void setEnterBtnVisiblity(Button enterBtn,boolean isVisible){
		if(isVisible){
			enterBtn.setEnabled(!isVisible);
			enterBtn.addStyleName(CssTokens.DISABLED);
		}else{
			enterBtn.setEnabled(!isVisible);
			enterBtn.removeStyleName(CssTokens.DISABLED);
		}
	}

	private class CreateNewClass implements ClickHandler{

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
		 */
		@Override
		public void onClick(ClickEvent event) {
			MixpanelUtil.ClickOnNewClassPage();
			newPopup = new NewClassPopupView() {

				@Override
				public void createNewClasspage(String title, String grade, boolean sharing) {
					try{
						getPresenter().createNewClass(title,grade,sharing);
					}catch(Exception e){
						e.printStackTrace();
					}

				}
			};
		}

	}

	private class OnEnterClassCodeOpen implements ClickHandler{

		Button enterBtn;
		TextBoxWithPlaceholder classCodeTxt;

		public OnEnterClassCodeOpen(Button enterBtn,TextBoxWithPlaceholder classCodeTxt){
			this.enterBtn=enterBtn;
			this.classCodeTxt=classCodeTxt;
			goBtn=enterBtn;
		}

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
		 */
		@Override
		public void onClick(ClickEvent event) {
			setEnterBtnVisiblity(enterBtn,true);
			if (classCodeTxt.getText().trim().equalsIgnoreCase("") || classCodeTxt.getText().trim() == null){
				alertMessageUc=new AlertMessageUc(i18n.GL0061(), new Label(i18n.GL0243()));
				ClickHandler alertHandler=new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						isValid=false;
						setEnterBtnVisiblity(enterBtn,false);
					}
				};
				alertMessageUc.appPopUp.addDomHandler(alertHandler, ClickEvent.getType());

				alertMessageUc.okButton.addClickHandler(new ClickHandler() {

					@Override
					public void onClick(ClickEvent event) {
						isValid=false;
						setEnterBtnVisiblity(enterBtn,false);
					}
				});
				return;
			}
			MixpanelUtil.ClickOnStudyNow();
			getPresenter().getClassData(classCodeTxt.getText().trim());
		}

	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.study.IsStudyClassCodeView#setCreatedClass(org.ednovo.gooru.application.shared.model.content.ClasspageDo)
	 */
	@Override
	public void setCreatedClass(ClasspageDo result) {
		if(result != null){
			String[] uri=result.getUri().split("/");
			final String classpageId =  uri[uri.length-1];
			OpenClasspageEdit(classpageId, PlaceTokens.EDIT_CLASS);
			newPopup.ClosePopup();
		}

	}

	private void OpenClasspageEdit(String gooruOId, String token){
		Map<String, String> params = new HashMap<String, String>();
		params.put(UrlNavigationTokens.CLASSPAGEID, gooruOId);
		params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.TEACHER_CLASS_SETTINGS);
		params.put(UrlNavigationTokens.TEACHER_CLASS_SUBPAGE_VIEW, UrlNavigationTokens.TEACHER_CLASS_CONTENT_SUB_SCORE);
		AppClientFactory.getPlaceManager().revealPlace(token, params);
	}


	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.study.IsStudyClassCodeView#setClassData(org.ednovo.gooru.application.shared.model.content.ClasspageDo)
	 */
	@Override
	public void setClassData(ClasspageDo result) {

		setEnterBtnVisiblity(goBtn,false);

		 String classUid = null;
		 String status = null;
		 boolean sharing = false;

		 if(result.getClassType()!=null) {
			 if(result.getClassType().equalsIgnoreCase("new-class")) {
				 classUid = result.getClassUid();
				 status = result.getStatus();
				 sharing = result.isVisibility();
			 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
				 classUid = result.getGooruOid();
				 status = result.getMeta().getStatus();
				 if(result.getSharing()!=null&&result.getSharing().equalsIgnoreCase("public")) {
					 sharing = true;
				 }
			 }
		 }

		 if(classUid==null){
			Window.enableScrolling(false);
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
			alertMessageUc=new AlertMessageUc(i18n.GL0061(), new Label(i18n.GL0244()));
			ClickHandler alertHandler=new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					isValid=false;

				}
			};
			alertMessageUc.appPopUp.addDomHandler(alertHandler, ClickEvent.getType());

			alertMessageUc.okButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					isValid=false;
				}
			});
		}else if(result.getUser().getGooruUId().equalsIgnoreCase(AppClientFactory.getGooruUid()))
		{
			 if(result.getClassType()!=null) {
				 Map<String, String> params = new HashMap<String, String>();
				 if(result.getClassType().equalsIgnoreCase("new-class")) {
						params.put("id",classUid);
						if(result.getCourseGooruOid() != null){
							params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
						}
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
				 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
						params.put("id",classUid);
						params.put("pageSize","5");
						params.put("pageNum","0");
						params.put("pos","1");
						params.put("b","true");
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
				 }
			 }
			 txtClassCode.setText("");
			if(alertMessageUc!=null)
			alertMessageUc.hide();
		}
		 else if(!sharing){
			if(result.getUser().getGooruUId().equalsIgnoreCase(AppClientFactory.getGooruUid()))
			{
				if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
					MixpanelUtil.Click_Study_LandingPage();
				}

				 if(result.getClassType()!=null) {
					 Map<String, String> params = new HashMap<String, String>();
					 if(result.getClassType().equalsIgnoreCase("new-class")) {
							if(result.getCourseGooruOid() != null){
								params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
							}
							params.put("id",result.getClassUid());
							AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
					 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
							params.put("id",classUid);
							params.put("pageSize","5");
							params.put("pageNum","0");
							params.put("pos","1");
							params.put("b","true");
							AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
					 }
				 }
				 txtClassCode.setText("");
				if(alertMessageUc!=null)
				alertMessageUc.hide();
			}
			else if(status!=null&&status.equalsIgnoreCase("active"))
			{
				if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
					MixpanelUtil.Click_Study_LandingPage();
				}

				 if(result.getClassType()!=null) {
					 Map<String, String> params = new HashMap<String, String>();
					 if(result.getClassType().equalsIgnoreCase("new-class")) {
							if(result.getCourseGooruOid() != null){
								params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
							}
							params.put("id",result.getClassUid());
							AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
					 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
							params.put("id",result.getGooruOid());
							params.put("pageSize","5");
							params.put("pageNum","0");
							params.put("pos","1");
							AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
					 }
				 }

				 txtClassCode.setText("");
				if(alertMessageUc!=null)
				alertMessageUc.hide();
			}
			else if(status!=null&&status.equalsIgnoreCase("pending"))
			{
				if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.HOME)) {
					MixpanelUtil.Click_Study_LandingPage();
				}

				 if(result.getClassType()!=null) {
					 Map<String, String> params = new HashMap<String, String>();
					 if(result.getClassType().equalsIgnoreCase("new-class")) {
							if(result.getCourseGooruOid() != null){
								params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
							}
							params.put("id",result.getClassUid());
							AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
					 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
				    	   new SentEmailSuccessVc(i18n.GL1177(), i18n.GL1535_1());
					 }
				 }

				 txtClassCode.setText("");
				if(alertMessageUc!=null)
				alertMessageUc.hide();
			}
			else
			{
				       if(AppClientFactory.isAnonymous()){
				    	   new SentEmailSuccessVc(i18n.GL1177(), i18n.GL1535());
				       }else{
				    	   new SentEmailSuccessVc(i18n.GL1177(), i18n.GL1535_1());
				       }
			}

		}
		else
		{

			 if(result.getClassType()!=null) {
				 Map<String, String> params = new HashMap<String, String>();
				 if(result.getClassType().equalsIgnoreCase("new-class")) {
						if(result.getCourseGooruOid() != null){
							params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, result.getCourseGooruOid());
						}
						params.put("id",result.getClassUid());
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW,params);
				 } else if (result.getClassType().equalsIgnoreCase("old-class")) {
						params.put("id",result.getGooruOid());
						params.put("pageSize","5");
						params.put("pageNum","0");
						params.put("pos","1");
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,params);
				 }
			 }

			 txtClassCode.setText("");
			if(alertMessageUc!=null)
			alertMessageUc.hide();
		}
		setEnterBtnVisiblity(goBtn,false);
	}

	@UiHandler("moreLinkAnr")
	public void onClickSupport(ClickEvent event){
	}

	@UiHandler("courseAnr")
	public void onCourseCreate(ClickEvent event){
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT);
	}

}
