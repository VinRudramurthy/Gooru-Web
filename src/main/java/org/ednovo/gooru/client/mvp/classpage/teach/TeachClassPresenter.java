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
package org.ednovo.gooru.client.mvp.classpage.teach;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BasePlacePresenter;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpage.teach.TeachClassPresenter.IsTeachClassProxy;
import org.ednovo.gooru.client.mvp.classpage.teach.edit.content.EditClassSettingsNavigationPresenter;
import org.ednovo.gooru.client.mvp.classpage.teach.reports.TeachStudentDashboardPresenter;

import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;


/**
 * @fileName : TeachClassPresenter.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 26-Jun-2015
 *
 * @Author tumbalam
 *
 * @Reviewer: 
 */
public class TeachClassPresenter extends BasePlacePresenter<IsTeachClassView, IsTeachClassProxy> implements TeachClassViewUiHandlers{
	
	EditClassSettingsNavigationPresenter editClassSettingsNavigationPresenter;
	
	TeachStudentDashboardPresenter teachStudentDashboardPresenter;
	
	String classpageId="";
	
	ClasspageDo classpageDo = null;
	
	boolean isReveal = false;
	
	@ProxyCodeSplit
	@NameToken(PlaceTokens.EDIT_CLASS)
	public interface IsTeachClassProxy extends ProxyPlace<TeachClassPresenter> {
	}

	@Inject
	public TeachClassPresenter(IsTeachClassView view,IsTeachClassProxy proxy,EditClassSettingsNavigationPresenter editClassSettingsNavigationPresenter,TeachStudentDashboardPresenter teachStudentDashboardPresenter){
		super(view, proxy);
		getView().setUiHandlers(this);
		this.editClassSettingsNavigationPresenter=editClassSettingsNavigationPresenter;
		this.teachStudentDashboardPresenter = teachStudentDashboardPresenter;
	}
	private SimpleAsyncCallback<CollectionDo> collectionAsyncCallback;
	
	@Override
	public String getViewToken() {
		throw new RuntimeException("Not implemented");
	}
	
	@Override
	protected void onReveal() {
		getClassDetails();
	}
	
	@Override
	public void onBind() {
		super.onBind();
		Window.enableScrolling(true);
	}
	
	@Override
	protected void onReset() {
       String courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID,null);
       if(courseId !=null && classpageDo != null && classpageDo.getCourseGooruOid() == null){
    	   getClassDetails();
       } else {
    	   if(classpageDo!=null) {
    	       loadNavigationPage();
    	   }
       }
       getView().setNavaigationTab();
	}
	
	public void getClassDetails(){
		
		classpageId = getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
		if(classpageId != null){
			AppClientFactory.getInjector().getClasspageService().v3GetClassById(classpageId, new SimpleAsyncCallback<ClasspageDo>() {

				@Override
				public void onSuccess(ClasspageDo result) {
					classpageDo=result;
					getView().setClassHeaderView(result);
					editClassSettingsNavigationPresenter.setClassDetails(result); 
					editClassSettingsNavigationPresenter.getCourseData();
					loadNavigationPage();
				}
				
			});
		}
	}
	
	

	/** 
	 * This method is to get the collectionAsyncCallback
	 */
	public SimpleAsyncCallback<CollectionDo> getCollectionAsyncCallback() {
		return collectionAsyncCallback;
	}

	/** 
	 * This method is to set the collectionAsyncCallback
	 */
	public void setCollectionAsyncCallback(SimpleAsyncCallback<CollectionDo> collectionAsyncCallback) {
		this.collectionAsyncCallback = collectionAsyncCallback;
	}
	
	public void  loadNavigationPage(){
		String loadPage = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT,"");
		try{
			clearSlot(CLASS_CONTENT_TAB);
			clearSlot(CLASS_DASHBOARD_TAB);
			if(loadPage.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASS_SETTINGS)){
				addToSlot(CLASS_CONTENT_TAB, editClassSettingsNavigationPresenter);
				editClassSettingsNavigationPresenter.loadNavigationPage();
			}else if(loadPage.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASS_DASHBOARD)){
				addToSlot(CLASS_DASHBOARD_TAB, teachStudentDashboardPresenter);
				teachStudentDashboardPresenter.setClassDetails(classpageDo);
				teachStudentDashboardPresenter.loadNavigationPage();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
