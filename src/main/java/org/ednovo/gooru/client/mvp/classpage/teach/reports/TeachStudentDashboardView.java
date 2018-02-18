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
package org.ednovo.gooru.client.mvp.classpage.teach.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.client.CssTokens;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpage.teach.reports.course.TeachCourseReportChildView;
import org.ednovo.gooru.client.mvp.classpage.teach.reports.lesson.TeachLessonReportChildView;
import org.ednovo.gooru.client.mvp.classpage.teach.reports.unit.TeachUnitReportChildView;
import org.ednovo.gooru.client.uc.LoadingUc;
import org.ednovo.gooru.client.uc.SpanPanel;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

public class TeachStudentDashboardView extends BaseViewWithHandlers<TeachStudentDashboardUiHandler> implements IsTeachStudentDashboardView{

	@UiField LoadingUc cropImageLoading;
	@UiField HTMLPanel bodyView, heightPanel, unitLinks, scoreLinks, assessmentLinks, collectionLinks, previewLinks, assStylePanel, collStylePanel, emptyDataContainer;
	@UiField HTMLEventPanel assessmentIcon, collectionIcon,currentContentPanel, allContentPanel;
	@UiField SpanPanel textLbl, currentContentName;
	@UiField HTMLPanel topContainer, learningMapContainer, headerLinksContainer,mainContainer;
	@UiField Button btnDownload, btnPreview;
	@UiField Frame downloadFile;

	String style="", urlDomain = "";
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	private static TeachStudentDashboardViewUiBinder uiBinder = GWT.create(TeachStudentDashboardViewUiBinder.class);

	interface TeachStudentDashboardViewUiBinder extends	UiBinder<Widget, TeachStudentDashboardView> {
	}
	
	public TeachStudentDashboardView() {
		setWidget(uiBinder.createAndBindUi(this));
		setContainerVisibility(false, false, false, false, false, false, false, false);
		setDebugIds();
		assessmentIcon.addClickHandler(new ContentClickHandler(UrlNavigationTokens.TEACHER_CLASSPAGE_ASSESSMENT));
		collectionIcon.addClickHandler(new ContentClickHandler(UrlNavigationTokens.TEACHER_CLASSPAGE_COLLECTION));
	}
	
	private void setDebugIds() {
		cropImageLoading.setVisible(false);
		cropImageLoading.setLoadingText(i18n.GL1234());
		cropImageLoading.getElement().setId("loadingUcCropImageLoading");
		urlDomain=Window.Location.getProtocol()+"//"+Window.Location.getHost();
		style="<link rel='styleSheet' type='text/css' href='"+urlDomain+"/css/main-styles.min.css'>";
		downloadFile.setVisible(false);
	}

	@Override
	public Frame getFrame() {
		return downloadFile;
	}

	@Override
	public void setReportView() {
		bodyView.clear();
		String reportView = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASSPAGE_REPORT_TYPE, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
		String contentView = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASSPAGE_CONTENT, UrlNavigationTokens.TEACHER_CLASSPAGE_ASSESSMENT);
		if(reportView.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW)) {
			bodyView.add(new TeachCourseReportChildView(null));
			setContainerVisibility(true, false, false, true, false, false, false, false);
		} else if(reportView.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW)) {
			bodyView.add(new TeachUnitReportChildView(currentContentName.getText()));
			boolean isUnitLinks = true;
			if(contentView.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASSPAGE_COLLECTION)) {
				isUnitLinks = false;
			}
			setContainerVisibility(true, true, true, isUnitLinks, true, false, false, false);
			setNavLinksData(i18n.GL3469_1(),false);
		} else if(reportView.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW)) {
			bodyView.add(new TeachLessonReportChildView());
			boolean isScoreLinks = true;
			boolean isCollectionLinks = true;
			if(contentView.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASSPAGE_COLLECTION)) {
				isScoreLinks = false;
				btnPreview.setText(i18n.GL3469_36());
			} else {
				isCollectionLinks = false;
				btnPreview.setText(i18n.GL3469_35());
			}
			setContainerVisibility(true, true, true, false, false, isScoreLinks, isCollectionLinks, true);
			setNavLinksData(i18n.GL3469_12(),true);
		}
		setContentLinksHighlight(contentView);
	}
	
	private void setContainerVisibility(boolean isTopContainer, boolean isLearningMap, boolean isAllContentPanel, boolean isUnitLinks, boolean isAssessmentLinks, boolean isScoreLinks, boolean isCollectionLinks, boolean isPreviewLinks) {
		topContainer.setVisible(isTopContainer);
		learningMapContainer.setVisible(isLearningMap);
		allContentPanel.setVisible(isAllContentPanel);
		unitLinks.setVisible(isUnitLinks);
		assessmentLinks.setVisible(isAssessmentLinks);
		scoreLinks.setVisible(isScoreLinks);
		collectionLinks.setVisible(isCollectionLinks);
		previewLinks.setVisible(isPreviewLinks);
	}
	
	private void setNavLinksData(String allTxt, boolean isAssessment) {
		String contentName = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CONTENT_NAME, "");
		textLbl.setText(allTxt);
		if(isAssessment) {
			currentContentName.setText(contentName);
		}
	}
	
	public void navigateToPage() {
		Map<String,String> params = StringUtil.splitQuery(Window.Location.getHref());
		String pageView = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASSPAGE_REPORT_TYPE);
		if(pageView.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW)) {
			params.put(UrlNavigationTokens.TEACHER_CLASSPAGE_REPORT_TYPE, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
			params.remove(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID);
			params.remove(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID);
			params.remove(UrlNavigationTokens.TEACHER_CLASSPAGE_CONTENT);
		} else if(pageView.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW)) {
			params.put(UrlNavigationTokens.TEACHER_CLASSPAGE_REPORT_TYPE, UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW);
			params.remove(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID);
			params.remove(UrlNavigationTokens.STUDENT_CLASSPAGE_ASSESSMENT_ID);
			params.remove(UrlNavigationTokens.CONTENT_NAME);
		}
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.EDIT_CLASS, params);
	}
	
	private void setContentLinksHighlight(String contentView) {
		if(contentView.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASSPAGE_ASSESSMENT)) {
			assStylePanel.addStyleName(CssTokens.ACTIVE);
			collStylePanel.removeStyleName(CssTokens.ACTIVE);
		} else if(contentView.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASSPAGE_COLLECTION)) {
			assStylePanel.removeStyleName(CssTokens.ACTIVE);
			collStylePanel.addStyleName(CssTokens.ACTIVE);
		}
	}
	
	public class ContentClickHandler implements ClickHandler{
		String contentType = null;
		public ContentClickHandler(String contentType) {
			this.contentType = contentType;
		}
		
		@Override
		public void onClick(ClickEvent event) {
			PlaceRequest request = AppClientFactory.getPlaceManager().getCurrentPlaceRequest();
			request = request.with(UrlNavigationTokens.TEACHER_CLASSPAGE_CONTENT, contentType);
			AppClientFactory.getPlaceManager().revealPlace(request);
		}
	}
	
	@UiHandler("allContentPanel")
	public void ClickAllContentPanel(ClickEvent event) {
		navigateToPage();
	}

	@Override
	public void setMetadataContent(ArrayList<PlanProgressDo> dataList) {
		int size = dataList.size();
		int matchedCount = 0;
		
		String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID, "");
		
		for(int i=0;i<size;i++) {
			PlanProgressDo planProgressDo = dataList.get(i);
			if(planProgressDo.getGooruOId().equalsIgnoreCase(unitId)) {
				matchedCount = i;
				break;
			}
		}
		currentContentName.setText(i18n.GL3281()+" "+(matchedCount+1)+": "+dataList.get(matchedCount).getTitle());
	}
	
	@UiHandler("btnPreview")
	public void accessPlayer(ClickEvent event) {
		Map<String,String> params = new LinkedHashMap<String,String>();
		String token = PlaceTokens.ASSESSMENT_PLAY;
		String id = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_ASSESSMENT_ID,null);
		String collectionType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASSPAGE_CONTENT,UrlNavigationTokens.TEACHER_CLASSPAGE_ASSESSMENT);
		if(collectionType.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASSPAGE_COLLECTION)) {
			token = PlaceTokens.COLLECTION_PLAY;
		}
		params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_CLASS_ID, id);
		PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(token, params);
		AppClientFactory.getPlaceManager().revealPlace(false,placeRequest,true);
	}

	@Override
	public void enableEmptyContainer(boolean isVisible, ClasspageDo classpageDo) {
		emptyDataContainer.setVisible(isVisible);
		topContainer.setVisible(!isVisible);
		learningMapContainer.setVisible(!isVisible);
		bodyView.setVisible(!isVisible);
		emptyDataContainer.clear();
		if(classpageDo!=null) {
			boolean isNoCourse = false, isNoStudent = false;
			if(classpageDo.getCourseGooruOid()==null) {
				isNoCourse = true;
			} else if(classpageDo.getMemberCount().equalsIgnoreCase("0")) {
				isNoStudent = true;
			}
			
			TeachStudentEmptyDashboardView teachStudentEmpty = new TeachStudentEmptyDashboardView(isNoCourse, isNoStudent) {
				@Override
				public void clickBtnCourse() {
					getUiHandlers().openAddPopup();
				}
			};
			
			emptyDataContainer.add(teachStudentEmpty);
		}
	}

	@Override
	public void setContainerVisibility(boolean isVisible) {
		mainContainer.setVisible(isVisible);
		cropImageLoading.setVisible(!isVisible);
	}
	
	@UiHandler("btnDownload")
	public void clickBtnDownload(ClickEvent event) {
		setPrintIndividualSummayData(true,false);
	}
	
	public void setPrintIndividualSummayData(final boolean isClickedOnSave, final boolean isClickedOnEmail){
		String outputData = "";
		final Iterator<Widget> iterator = bodyView.iterator();
        while (iterator.hasNext()) {
            TeachLessonReportChildView reportView = (TeachLessonReportChildView) iterator.next();
            outputData = reportView.getElement().getInnerHTML().toString();
        }
		String contentName = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CONTENT_NAME, "");
		getUiHandlers().getXlsxReport(outputData.replaceAll("\"", "\\\\\""),contentName.replaceAll(" ", "_")+"_teacher_summary_report");
	}
}