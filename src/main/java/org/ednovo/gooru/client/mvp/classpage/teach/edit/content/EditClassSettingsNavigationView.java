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

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.CssTokens;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.uc.AlertMessageUc;
import org.ednovo.gooru.client.uc.DeleteContentPopup;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;


/**
 * @fileName : EditClassSettingsNavigationView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 06-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class EditClassSettingsNavigationView extends BaseViewWithHandlers<EditClassSettingsNavigationUiHandler> implements IsEditClassSettingsNavigationView{

	@UiField LiPanel classInfo,minLiPnl,studentsPnl/*,settLiPanel*/;

	@UiField Anchor classInfoAnr,minmumScoreAnr,studentsAnr/*,contentSettingsAnr*/;

	@UiField SimplePanel bodyView;
	
	ClasspageDo classpageDo;
	
	MessageProperties i18n = GWT.create(MessageProperties.class);
	
	private static EditClassSettingsNavigationViewUiBinder uiBinder = GWT.create(EditClassSettingsNavigationViewUiBinder.class);

	interface EditClassSettingsNavigationViewUiBinder extends	UiBinder<Widget, EditClassSettingsNavigationView> {
	}

	public EditClassSettingsNavigationView() {
		setWidget(uiBinder.createAndBindUi(this));
		setIds();
		//setActiveStyles();
	}

	public void setIds(){
		minmumScoreAnr.setText(i18n.GL3435());
		minmumScoreAnr.getElement().setId("contentPacingId");
		minmumScoreAnr.getElement().setAttribute("alt",i18n.GL3435());
		minmumScoreAnr.getElement().setAttribute("title",i18n.GL3435());
		
		studentsAnr.setText("Students");
		studentsAnr.getElement().setId("studentsAnrId");
		studentsAnr.getElement().setAttribute("alt",i18n.GL3403());
		studentsAnr.getElement().setAttribute("title",i18n.GL3403());

		/*contentSettingsAnr.setText(i18n.GL3404());
		contentSettingsAnr.getElement().setId("contentSettingAnrId");
		contentSettingsAnr.getElement().setAttribute("alt",i18n.GL3404());
		contentSettingsAnr.getElement().setAttribute("title",i18n.GL3404());*/

		classInfoAnr.setText(i18n.GL3420());
		classInfoAnr.getElement().setId("contentSettingAnrId");
		classInfoAnr.getElement().setAttribute("alt",i18n.GL3420());
		classInfoAnr.getElement().setAttribute("title",i18n.GL3420());


		classInfoAnr.addClickHandler(new SubNavigationTabHandler(UrlNavigationTokens.TEACHER_CLASS_SETTINGS_INFO,classInfo));
		minmumScoreAnr.addClickHandler(new SubNavigationTabHandler(UrlNavigationTokens.TEACHER_CLASS_CONTENT_SUB_SCORE,minLiPnl));
		studentsAnr.addClickHandler(new SubNavigationTabHandler(UrlNavigationTokens.TEACHER_CLASS_STUDENTS_ROASTER,studentsPnl));
		//settLiPanel.addClickHandler(new SubNavigationTabHandler(UrlNavigationTokens.TEACHER_CLASS_CONTENT_SUB_SETTINGS,settLiPanel));
		
	}

	@Override
	public void setActiveStyles(){
		String subPageView = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASS_SUBPAGE_VIEW,"");
		if(subPageView.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASS_SETTINGS_INFO)){
			classInfo.setStyleName(CssTokens.ACTIVE);
			minLiPnl.removeStyleName(CssTokens.ACTIVE);
			studentsPnl.removeStyleName(CssTokens.ACTIVE);
			
		}else if(subPageView.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASS_CONTENT_SUB_SCORE)){
			minLiPnl.setStyleName(CssTokens.ACTIVE);
			classInfo.removeStyleName(CssTokens.ACTIVE);
			studentsPnl.removeStyleName(CssTokens.ACTIVE);
		}else if(subPageView.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASS_STUDENTS_ROASTER)){
			studentsPnl.addStyleName(CssTokens.ACTIVE);
			classInfo.removeStyleName(CssTokens.ACTIVE);
			minLiPnl.removeStyleName(CssTokens.ACTIVE);
		}

	}

	public class SubNavigationTabHandler implements ClickHandler{

		String subView;
		LiPanel liPanel;

		public SubNavigationTabHandler(String subView,LiPanel liPanel){
			this.subView=subView;
			this.liPanel=liPanel;
		}


		@Override
		public void onClick(ClickEvent event) {
			minLiPnl.removeStyleName(CssTokens.ACTIVE);
			studentsPnl.removeStyleDependentName(CssTokens.ACTIVE);
			//settLiPanel.removeStyleName(CssTokens.ACTIVE);
			classInfo.removeStyleName(CssTokens.ACTIVE);
			liPanel.addStyleName(CssTokens.ACTIVE);
			PlaceRequest request = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
			Map<String, String> params = StringUtil.splitQuery(Window.Location.getHref());
			params.remove(UrlNavigationTokens.TEACHER_CLASSPAGE_REPORT_TYPE);
			params.put(UrlNavigationTokens.TEACHER_CLASS_SUBPAGE_VIEW, subView);
			AppClientFactory.getPlaceManager().revealPlace(request,params);
			
			getUiHandlers().setClearAllPanel();
		//	request = request.with(name, value);
			
			
		//	AppClientFactory.getPlaceManager().revealPlace(false, request, false);
		}

	}

	@Override
	public void addToSlot(Object slot, Widget content) {
		super.addToSlot(slot, content);
		if (content != null) {
			bodyView.setWidget(content);
		}

	}

	/*@UiHandler("studentPreviewbtn")
	public void navigateStudentPreview(ClickEvent event) {
		PlaceRequest request = new PlaceRequest(PlaceTokens.STUDENT_VIEW);
		String id = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID,"");
		String cId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID,"");
		request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_CLASS_ID, id);
		request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, cId);
		request = request.with(UrlNavigationTokens.TEACHER_PREVIEW_MODE, UrlNavigationTokens.TRUE);
		request = request.with(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
		AppClientFactory.getPlaceManager().revealPlace(request);
	}*/

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.content.IsEditClassSettingsNavigationView#setCourseData(org.ednovo.gooru.application.shared.model.folder.FolderDo)
	 */
	@Override
	public void setCourseData(FolderDo result) {
		coursePanelVisiblity(true);
	}
	
	public void coursePanelVisiblity(boolean visiblity){
		
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.content.IsEditClassSettingsNavigationView#setAddCourseData()
	 */
	@Override
	public void setAddCourseData() { 
		coursePanelVisiblity(false);
	}
	
	private  class AddCourseClickhandler implements ClickHandler{

		/* (non-Javadoc)
		 * @see com.google.gwt.event.dom.client.ClickHandler#onClick(com.google.gwt.event.dom.client.ClickEvent)
		 */
		@Override
		public void onClick(ClickEvent event) {
			getUiHandlers().addCourseToClass();
		}
		
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.content.IsEditClassSettingsNavigationView#setClassData(org.ednovo.gooru.application.shared.model.content.ClasspageDo)
	 */
	@Override
	public void setClassData(ClasspageDo classpageDo) {
		this.classpageDo=classpageDo;
		String studentCount = "("+classpageDo.getMemberCount()+")";
		studentsAnr.setText(i18n.GL3344() +" " +studentCount);
	}
}