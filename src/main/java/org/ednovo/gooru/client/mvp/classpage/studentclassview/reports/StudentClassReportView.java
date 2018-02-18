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
package org.ednovo.gooru.client.mvp.classpage.studentclassview.reports;

import java.util.ArrayList;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.assessmentreport.AssessmentProgressReportChildView;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.widgets.SlnCourseReportView;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.reports.widgets.SlnUnitReportView;
import org.ednovo.gooru.client.uc.LoadingUc;
import org.ednovo.gooru.client.uc.SpanPanel;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *  
 */
public class StudentClassReportView extends BaseViewWithHandlers<StudentClassReportUiHandlers> implements IsStudentClassReportView {
	
	@UiField LoadingUc cropImageLoading;
	@UiField SpanPanel textLbl, currentContentName, previousContentName, nextContentName, headerLeftArrow;
	@UiField HTMLPanel topContainer, reportBodyBlock, learningMapContainer, headerLinksContainer, legendContainer;
	@UiField HTMLEventPanel previousContentPanel, currentContentPanel, nextContentPanel, allContentPanel;
	
	String allContentStr = null, previousContentStr = null, nextContentStr = null;
	
	private static final String ALL = "all";

	private static StudentClassReportViewUiBinder uiBinder = GWT
			.create(StudentClassReportViewUiBinder.class);
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	interface StudentClassReportViewUiBinder extends
			UiBinder<Widget, StudentClassReportView> {
	}

	public StudentClassReportView() {
		setWidget(uiBinder.createAndBindUi(this));
		cropImageLoading.setVisible(false);
		setDebugIds();
	}

	private void setDebugIds() {
		cropImageLoading.setLoadingText(i18n.GL1234());
		cropImageLoading.getElement().setId("loadingUcCropImageLoading");
	}
	
	@Override
	public void reset() {
		super.reset();
	}
	
	@Override
	public void setReportData(ArrayList<PlanProgressDo> dataList) {
		headerLinksContainer.setVisible(false);
		String pageType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
		reportBodyBlock.clear();
		
		int size = dataList.size();

		if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW)) {
			headerLeftArrow.setVisible(false);
			allContentPanel.removeStyleName("cursorPointer");
			for(int i=0;i<size;i++) {
				reportBodyBlock.add(new SlnCourseReportView(dataList.get(i),i+1));
			}
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW)) {
			headerLeftArrow.setVisible(true);
			allContentPanel.addStyleName("cursorPointer");
			for(int i=0;i<size;i++) {
				reportBodyBlock.add(new SlnUnitReportView(dataList.get(i),i+1));
			}
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW)) {
			headerLeftArrow.setVisible(true);
			previousContentPanel.setVisible(true);
			allContentPanel.addStyleName("cursorPointer");
			String cId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_CLASS_ID);
			String aId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_ASSESSMENT_ID);
			String courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID);
			String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID);
			String lessonId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID);
			String contentType=AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASSPAGE_CONTENT, UrlNavigationTokens.TEACHER_CLASSPAGE_ASSESSMENT);
			reportBodyBlock.add(new AssessmentProgressReportChildView(aId, cId, AppClientFactory.getGooruUid(), courseId, unitId, lessonId, contentType, null));
		}
	}
	
	@UiHandler("allContentPanel")
	public void ClickAllContentPanel(ClickEvent event) {
		String pageType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
		if(!pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW)) {
			navigateToPage(allContentStr);
		}
	}
	@UiHandler("previousContentPanel")
	public void ClickPreviousContentPanel(ClickEvent event) {
		navigateToPage(previousContentStr);
	}
	@UiHandler("nextContentPanel")
	public void ClickNextContentPanel(ClickEvent event) {
		navigateToPage(nextContentStr);
	}
	
	public void navigateToPage(String id) {
		Map<String,String> params = StringUtil.splitQuery(Window.Location.getHref());
		
		String uId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID);
		String lId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID);
		String pageView = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT);
		
		if(id.equalsIgnoreCase(ALL)) {
			if(pageView.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW)) {
				params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
				params.remove(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID);
				params.remove(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID);
			} else if(pageView.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW)) {
				params.remove(UrlNavigationTokens.TEACHER_CLASSPAGE_CONTENT);
				params.remove(UrlNavigationTokens.STUDENT_CLASSPAGE_ASSESSMENT_ID);
				params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_TAB,UrlNavigationTokens.STUDENT_CLASSPAGE_LEARNING_MAP_ITEM);
			}
		} else {
			if(pageView.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW)) {
				params.remove(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID);
				if(uId!=null&&!uId.isEmpty()) {
					params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID, id);
				}
			} else if(pageView.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW)) {
				if(lId!=null&&!lId.isEmpty()) {
					params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID, id);
				}
			}
		}
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT_VIEW, params);
	}
	
	private void setNavLinksData(String allTxt, String previousLinkTxt, String currentLinkTxt, String nextLinkTxt) {
		textLbl.setText(allTxt);
		if(previousLinkTxt==null) {
			previousContentPanel.setVisible(false);
		} else {
			previousContentPanel.setVisible(true);
		}
		if(nextLinkTxt==null) {
			nextContentPanel.setVisible(false);
		} else {
			nextContentPanel.setVisible(true);
		}
		previousContentName.setText(previousLinkTxt);
		currentContentName.setText(currentLinkTxt);
		nextContentName.setText(nextLinkTxt);
	}
	
	@Override
	public void setContentVisibility(boolean isVisible) {
		String pageType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
		cropImageLoading.setVisible(!isVisible);
		topContainer.setVisible(isVisible);
		learningMapContainer.setVisible(isVisible);
		if(!pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW)) {
			headerLinksContainer.setVisible(isVisible);
		}
	}

	@Override
	public void setMetadataContent(ArrayList<PlanProgressDo> dataList) {
		String pageType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
		allContentStr = ALL;
		
		if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW)) {
			setNavLinksData(i18n.GL3469_24(), null, null, null);
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW)) {
			String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID, null);
			setLinksData(unitId, dataList, i18n.GL3469_1(), "Unit");
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW)) {
			String lessonId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID, null);
			setLinksData(lessonId, dataList, i18n.GL3469_12(), "Lesson");
		}
	}
	
	private void setLinksData(String id, ArrayList<PlanProgressDo> dataList, String allTxt, String titleTxt) {
		if(dataList!=null) {
			currentContentPanel.setVisible(true);
			nextContentPanel.setVisible(true);
			legendContainer.setVisible(true);
			int size = dataList.size();
			int matchedCount = 0;
			String name = null, previousName = null, nextName = null;
			for(int i=0;i<size;i++) {
				PlanProgressDo planProgressDo = dataList.get(i);
				if(planProgressDo.getGooruOId().equalsIgnoreCase(id)) {
					matchedCount = i;
					break;
				}
			}
			if(matchedCount==0&&size==1) {
				previousContentStr = null;
				nextContentStr = null;
				name = titleTxt+" "+(matchedCount+1)+": "+dataList.get(matchedCount).getTitle();
			} else if(matchedCount==0&&size>1) {
				previousContentStr = null;
				nextContentStr = dataList.get(matchedCount+1).getGooruOId();
				name = titleTxt+" "+(matchedCount+1)+": "+dataList.get(matchedCount).getTitle();
				nextName = titleTxt+" "+(matchedCount+2);
			} else if(matchedCount==size-1) {
				nextContentStr = null;
				previousContentStr = dataList.get(matchedCount-1).getGooruOId();
				name = titleTxt+" "+(matchedCount+1)+": "+dataList.get(matchedCount).getTitle();
				previousName = titleTxt+" "+(matchedCount);
			} else if(matchedCount<size-1) {
				previousContentStr = dataList.get(matchedCount-1).getGooruOId();
				nextContentStr = dataList.get(matchedCount+1).getGooruOId();
				name = titleTxt+" "+(matchedCount+1)+": "+dataList.get(matchedCount).getTitle();
				nextName = titleTxt+" "+(matchedCount+2);
				previousName = titleTxt+" "+(matchedCount);
			}
			setNavLinksData(allTxt, previousName, name, nextName);
		} else {
			currentContentPanel.setVisible(false);
			nextContentPanel.setVisible(false);
			legendContainer.setVisible(false);
			String lessonName = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_NAME,i18n.GL3469_25());
			previousContentStr = "ALL";
			previousContentName.setText(lessonName);
		}
	}
}