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
package org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.classpages.PlanContentDo;
import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.application.shared.model.content.StandardFo;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.assessmentchild.SlmAssessmentChildView;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.widgets.StudentClassLearningMapContainer;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.widgets.StudentClassLessonContainer;
import org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.widgets.StudentEmptyClassContainer;
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *  
 */
public class StudentClassLearningMapView extends BaseViewWithHandlers<StudentClassLearningMapUiHandlers> implements IsStudentClassLearningMapView {

	@UiField HTMLPanel containerData, learningMapContainer, headerLinksContainer, topBackLinkBox, standardsBlock, standardsBlockList;
	@UiField SpanPanel allContentTxt, currentContentName, previousContentName, nextContentName, headerLeftArrow;
	@UiField HTMLEventPanel allContentPanel, previousContentPanel, nextContentPanel;
	@UiField HTMLPanel learnMapScore, colorPanel, learingHeaderBlock;
	
	@UiField LoadingUc cropImageLoading;
	
	@UiField StudentEmptyClassContainer emptyContainer;
	
	String allContentStr = null, previousContentStr = null, nextContentStr = null;
	
	private static final String ALL = "all";
	
	private static StudentClassLearningMapViewUiBinder uiBinder = GWT.create(StudentClassLearningMapViewUiBinder.class);
	
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	interface StudentClassLearningMapViewUiBinder extends UiBinder<Widget, StudentClassLearningMapView> {}
	
	public StudentClassLearningMapView() {
		setWidget(uiBinder.createAndBindUi(this));
		setDebugIds();
	}
	
	private void setDebugIds() {
		cropImageLoading.setLoadingText(i18n.GL1234());
		cropImageLoading.getElement().setId("loadingUcCropImageLoading");
		setEmptyContainerVisiblity(false);
	}
	
	@Override
	public void reset() {
		super.reset();
	}
	
	public void getContentData(ArrayList<PlanProgressDo> dataList, String status, String userId) {
		String pageType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
		String pageView = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_TAB, UrlNavigationTokens.STUDENT_CLASSPAGE_LEARNING_MAP_ITEM);
		
		learningMapContainer.clear();
		String page = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_PREVIEW_MODE, UrlNavigationTokens.FALSE);
		if(page.equalsIgnoreCase(UrlNavigationTokens.TRUE)) {
			setPreviewClassMode(true);
			containerData.addStyleName("margin-top-20");
		} else {
			containerData.removeStyleName("margin-top-20");
		}
		
		int size = dataList.size();
		setScoreMapVisiblity(true);
		if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW)) {
			setTextPanelsVisiblity(false,true,false,false);
			if(size>0) {
				for(int i=0;i<size;i++) {
					learningMapContainer.add(new StudentClassLearningMapContainer(dataList.get(i), i));
				}
				setContentVisiblity(true);
			} else {
				setEmptyContainerVisiblity(true);
			}
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW)) {
			setTextPanelsVisiblity(true,true,false,true);
			if(size>0) {
				for(int i=0;i<size;i++) {
					learningMapContainer.add(new StudentClassLessonContainer(dataList.get(i), i+1, status, userId));
				}
				setContentVisiblity(true);
			} else {
				containerData.setVisible(true);
				setEmptyContainerVisiblity(true);
			}
		} else {
			setEmptyContainerVisiblity(true);
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
	
	@Override
	public void setContent(ArrayList<PlanProgressDo> dataList, String status, String userId) {
		getContentData(dataList, status, userId);
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
				params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW);
				params.remove(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID);
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
	
	@Override
	public void setContentVisiblity(boolean isVisible) {
		containerData.setVisible(isVisible);
		cropImageLoading.setVisible(!isVisible);
		emptyContainer.setVisible(false);
		if(!isVisible) {
			standardsBlock.setVisible(false);
		}
	}
	
	private void setScoreMapVisiblity(boolean isVisible) {
		learnMapScore.setVisible(isVisible);
	}

	private void setTextPanelsVisiblity(boolean isHeaderVisible, boolean isTopLinkVisible, boolean isStandardsVisible, boolean isArrowVisible) {
		headerLinksContainer.setVisible(isHeaderVisible);
		topBackLinkBox.setVisible(isTopLinkVisible);
		standardsBlock.setVisible(isStandardsVisible);
		headerLeftArrow.setVisible(isArrowVisible);
	}
	
	private void setNavLinksData(String allTxt, String previousLinkTxt, String currentLinkTxt, String nextLinkTxt) {
		allContentTxt.setText(allTxt);
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
	public void setPreviewClassMode(boolean isPreview) {
		String pageType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
		if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW)) {
			learingHeaderBlock.setVisible(false);
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW)) {
			learingHeaderBlock.setVisible(true);
			colorPanel.setVisible(false);
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW)) {
			learingHeaderBlock.setVisible(true);
			colorPanel.setVisible(false);
		}
	}
	
	@Override
	public void setMetadataContent(ArrayList<PlanProgressDo> dataList) {
		String pageType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
		allContentStr = ALL;
		
		if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW)) {
			allContentPanel.removeStyleName("cursorPointer");
			setNavLinksData(i18n.GL3469_1(), null, null, null);
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW)) {
			allContentPanel.addStyleName("cursorPointer");
			String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID, null);
			setLinksData(unitId, dataList, i18n.GL3469_1(), "Unit");
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW)) {
			allContentPanel.addStyleName("cursorPointer");
			String lessonId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID, null);
			setLinksData(lessonId, dataList, i18n.GL3469_12(), "Lesson");
		}
	}
	
	private void setLinksData(String id, ArrayList<PlanProgressDo> dataList, String allTxt, String titleTxt) {
		int size = dataList.size();
		int matchedCount = 0;
		String contentGooruIds = "";
		String name = null, previousName = null, nextName = null;
		for(int i=0;i<size;i++) {
			PlanProgressDo planProgressDo = dataList.get(i);
			if(planProgressDo.getGooruOId().equalsIgnoreCase(id)) {
				matchedCount = i;
				break;
			}
		}
		if(titleTxt!=null&&titleTxt.equalsIgnoreCase("Lesson")) {
			PlanProgressDo planProgressDo = dataList.get(matchedCount);
			int contentSize = planProgressDo.getItem().size();
			for(int j=0;j<contentSize;j++) {
				String gooruOid = planProgressDo.getItem().get(j).getGooruOId();
				contentGooruIds = contentGooruIds + gooruOid +",";
			}
			if(contentGooruIds!=null&&!contentGooruIds.isEmpty()) {
				getUiHandlers().getLessonPlanData(contentGooruIds);
			} else {
				setLessonContent(null, null, null,0);
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
	}

	@Override
	public void setLessonContent(PlanContentDo collectionList, String status, String userId, int minimumScore) {
		learningMapContainer.clear();
		String page = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_PREVIEW_MODE, UrlNavigationTokens.FALSE);
		if(page.equalsIgnoreCase(UrlNavigationTokens.TRUE)) {
			setPreviewClassMode(true);
			containerData.addStyleName("margin-top-20");
		} else {
			containerData.removeStyleName("margin-top-20");
		}
		
		int size = 0;
		
		if(collectionList!=null&&collectionList.getItems()!=null) {
			size = collectionList.getItems().size();
		}
		boolean isStandards = false;
		if(collectionList!=null&&collectionList.getStandards()!=null&&collectionList.getStandards().size()>0) {
			isStandards = true;
			List<StandardFo> list = collectionList.getStandards();
			standardsBlockList.clear();
			for(int i=0;i<list.size();i++) {
				Label standard = new Label(list.get(i).getCode());
				standard.addStyleName("lessonStandard");
				standardsBlockList.add(standard);
			}
		}
		setTextPanelsVisiblity(true,true,isStandards,true);
		if(size>0) {
			setScoreMapVisiblity(true);
			for(int i=0;i<size;i++) {
				learningMapContainer.add(new SlmAssessmentChildView(collectionList.getItems().get(i), status, userId, minimumScore));
			}
			setContentVisiblity(true);
		} else {
			containerData.setVisible(true);
			setEmptyContainerVisiblity(true);
		}
		
	}

	@Override
	public void setEmptyContainerVisiblity(boolean isVisible) {
		cropImageLoading.setVisible(!isVisible);
		emptyContainer.setVisible(isVisible);
	}
	
	@Override
	public void setEmptyContainerText(String userName) {
		emptyContainer.setUserName(userName);
	}

	@Override
	public void showProgressMapBar(boolean isVisible) {
		colorPanel.setVisible(isVisible);
	}
}