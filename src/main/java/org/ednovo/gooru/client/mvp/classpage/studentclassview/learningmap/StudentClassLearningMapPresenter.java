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
import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.classpages.PlanContentDo;
import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;
import com.gwtplatform.mvp.client.View;

/**
 * @author Search Team
 *
 */
public class StudentClassLearningMapPresenter extends PresenterWidget<IsStudentClassLearningMapView> implements StudentClassLearningMapUiHandlers {
	/**
	 * Class constructor
	 * 
	 * @param eventBus {@link EventBus}
	 * @param view {@link View}
	 */
	
	private ClasspageDo classpageDo = null;
	
	@Inject
	public StudentClassLearningMapPresenter(EventBus eventBus, IsStudentClassLearningMapView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}

	@Override
	public void onBind() {
		super.onBind();
		
	}

	@Override
	public void onReveal() {
		super.onReveal();
		getView().setContentVisiblity(false);
	}

	@Override
	protected void onHide() {
		super.onHide();
	}
	
	@Override
	public void onReset() {
		String classGooruOid = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, null);
        if(classGooruOid!=null) {
                getView().setContentVisiblity(false);
        }
	}
	
	public void setData() {
		String pageType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW);
		String classUId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_CLASS_ID, null);
		String classGooruOid = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, null);
		String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID, null);
		String lessonId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID, null);
		
		if(classGooruOid==null) {
			getView().setContent(new ArrayList<PlanProgressDo>(), "","");
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_VIEW)) {
			if(classGooruOid!=null) {
				AppClientFactory.getInjector().getClasspageService().getStudentPlanProgressData(classUId, classGooruOid, null, null, "plan", null, new SimpleAsyncCallback<ArrayList<PlanProgressDo>>() {
					@Override
					public void onSuccess(ArrayList<PlanProgressDo> dataList) {
						getView().setMetadataContent(dataList);
						getView().setContent(dataList, getClasspageDo().getStatus(),getClasspageDo().getUser().getGooruUId());
					}
					@Override
					public void onFailure(Throwable caught) {
						getView().setContentVisiblity(true);
					}
				});
			}
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_VIEW)) {
			if(classGooruOid!=null) {
				AppClientFactory.getInjector().getClasspageService().getStudentPlanProgressData(classUId, classGooruOid, null, null, "plan", null, new SimpleAsyncCallback<ArrayList<PlanProgressDo>>() {
					@Override
					public void onSuccess(ArrayList<PlanProgressDo> dataList) {
						getView().setMetadataContent(dataList);
					}
					@Override
					public void onFailure(Throwable caught) {
						getView().setContentVisiblity(true);
					}
				});
			}
			if(classGooruOid!=null&&unitId!=null) {
				AppClientFactory.getInjector().getClasspageService().getStudentPlanProgressData(classUId, classGooruOid, unitId, null, "plan", null, new SimpleAsyncCallback<ArrayList<PlanProgressDo>>() {
					@Override
					public void onSuccess(ArrayList<PlanProgressDo> dataList) {
						getView().setContent(dataList, getClasspageDo().getStatus(),getClasspageDo().getUser().getGooruUId());
					}
					@Override
					public void onFailure(Throwable caught) {
						getView().setContentVisiblity(true);
					}
				});
			}
		} else if(pageType.equalsIgnoreCase(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_VIEW)) {
			if(classGooruOid!=null&&unitId!=null&&lessonId!=null) {
				AppClientFactory.getInjector().getClasspageService().getStudentPlanProgressData(classUId, classGooruOid, unitId, null, "plan", null, new SimpleAsyncCallback<ArrayList<PlanProgressDo>>() {
					@Override
					public void onSuccess(ArrayList<PlanProgressDo> dataList) {
						getView().setMetadataContent(dataList);
					}
					@Override
					public void onFailure(Throwable caught) {
						getView().setContentVisiblity(true);
					}
				});
			}
		}
	}
	
	@Override
	public void getLessonPlanData(String contentGooruIds) {
		final String classUId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_CLASS_ID, null);
		final String classGooruOid = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, null);
		final String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID, null);
		final String lessonId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID, null);
		final Map<String,String> queryParams = new HashMap<String,String>();
		queryParams.put("contentGooruIds", contentGooruIds);
		
		if(classGooruOid!=null&&unitId!=null&&lessonId!=null) {
			AppClientFactory.getInjector().getClasspageService().getClasspageCollections(classUId, classGooruOid, unitId, lessonId, "assessment", new SimpleAsyncCallback<PlanContentDo>() {
				@Override
				public void onSuccess(final PlanContentDo collectionList) {
					final Map<String,PlanProgressDo> data = new HashMap<String, PlanProgressDo>();
					AppClientFactory.getInjector().getClasspageService().getStudentPlanProgressData(classUId, classGooruOid, unitId, lessonId, "plan", queryParams, new SimpleAsyncCallback<ArrayList<PlanProgressDo>>() {
						@Override
						public void onSuccess(ArrayList<PlanProgressDo> dataList) {
							for(int i=0;i<dataList.size();i++) {
								data.put(dataList.get(i).getGooruOId(), dataList.get(i));
							}
							if(collectionList.getItems()!=null&&collectionList.getItems().size()>0) {
								ArrayList<PlanContentDo> contentList = collectionList.getItems();
								for(int i=0;i<contentList.size();i++) {
									contentList.get(i).setProgress(data.get(contentList.get(i).getGooruOid()));
								}
								collectionList.setItems(contentList);
							}
							getView().setLessonContent(collectionList,getClasspageDo().getStatus(),getClasspageDo().getUser().getGooruUId(),getClasspageDo().getMinimumScore());
						}
						@Override
						public void onFailure(Throwable caught) {
							getView().setContentVisiblity(true);
						}
					});					
				}
				@Override
				public void onFailure(Throwable caught) {
					getView().setContentVisiblity(true);
				}
			});
		}
		
	}
	
	public ClasspageDo getClasspageDo() {
		return classpageDo;
	}

	public void setClasspageDo(ClasspageDo classpageDo) {
		this.classpageDo = classpageDo;
		getView().setEmptyContainerText(classpageDo.getUser().getUsername());
	}

	public void showProgressMapBar(boolean isVisible) {
		getView().showProgressMapBar(isVisible);
	}
	
}