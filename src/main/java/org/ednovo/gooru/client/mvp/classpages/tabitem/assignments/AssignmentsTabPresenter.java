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
package org.ednovo.gooru.client.mvp.classpages.tabitem.assignments;

import java.util.List;

import org.ednovo.gooru.application.client.child.ChildPresenter;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.ClasspageService;
import org.ednovo.gooru.application.client.service.ResourceServiceAsync;
import org.ednovo.gooru.application.shared.model.content.AssignmentDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.ResourceDo;
import org.ednovo.gooru.application.shared.model.content.TaskDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.classpages.event.RefreshAssignmentsListEvent;

/*
 * 
 * @fileName : AssignmentsTabPresenter.java
 *
 * @description : This is presenter widget class used in Creating Assignment and setting to UI
 *
 *
 * @version : 1.0
 *
 * @date: May 10, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class AssignmentsTabPresenter extends ChildPresenter<AssignmentsTabPresenter, IsAssignmentsTabView>{


	private ClasspageService classpageService=null;
		
	private SimpleAsyncCallback<List<CollectionDo>> getMyUserCollectionsAsyncCallback;
	
	/**
	 * Class constructor
	 * 
	 * @param childView 
	 */
	public AssignmentsTabPresenter(IsAssignmentsTabView childView) {
		super(childView);
	}
	
	/** 
	 * This method is to get the classpageService
	 */
	public ClasspageService getClasspageService() {
		return classpageService;
	}


	/** 
	 * This method is to set the classpageService
	 */
	public void setClasspageService(ClasspageService classpageService) {
		this.classpageService = classpageService;
	}
	
	/**
	 *  Delete classpage by classpage id which is mandatory   
	 * 
	 * @param classpageId  gooruOid of collection item
	 */
	public void deleteAssignment(String assignmentId) {
		 AppClientFactory.getInjector().getClasspageService().v2DeleteAssignment(assignmentId, new SimpleAsyncCallback<Void>() {

			@Override
			public void onSuccess(Void result) {
				AppClientFactory.fireEvent(new RefreshAssignmentsListEvent());
				getView().onPostCollectionDelete();
				
			}
		});
	}
	
	/**
	 * @description Update classpage by classpage id which is mandatory   
	 * 
	 * @param classpageId  gooruOid of collection item
	 */
	public void updateAssignmet(AssignmentDo assignmentDo, String assignmentId) {
		AppClientFactory.getInjector().getClasspageService().v2UpdateAssignment(assignmentDo, assignmentId, new SimpleAsyncCallback<TaskDo>(){

			@Override
			public void onSuccess(TaskDo result) {
			}
			 
		});
	}
	
	private void onBind() {
		throw new RuntimeException("Not implemented");
	}
	
	
	private void onLoad() {
		throw new RuntimeException("Not implemented");
	}
	
	public ResourceServiceAsync getResourceService() {
		
		return AppClientFactory.getInjector().getResourceService();
	}
	
	/*
	 * @description : To retrive user collections with Pagination support
	 * 
	 * @param : pageSize(limit per page), pageNum(offset)
	 */
	
	public void getUserColletionsList(Integer pageSize,Integer pageNum)
	{
		
		getResourceService().getUserCollectionList(pageSize,pageNum,true,getUserCollectionsAsyncCallback());
	}
	
	public SimpleAsyncCallback<List<CollectionDo>> getUserCollectionsAsyncCallback() {
		if (getMyUserCollectionsAsyncCallback == null) {
			
			getMyUserCollectionsAsyncCallback = new SimpleAsyncCallback<List<CollectionDo>>() {

				@Override
				public void onSuccess(List<CollectionDo> result) {
					getView().onPostUserCollections(result);
					
				}
			};
		}
		return getMyUserCollectionsAsyncCallback;
	}
	
	/*
	 * @description : Retrive all collection related to a particular assignment
	 * 
	 * @param : assignmentId
	 */
	 
	public void getAssignmentCollections(String assignmentId) {
		AppClientFactory.getInjector().getClasspageService().v2GetAssignmentCollectionsById(assignmentId, new SimpleAsyncCallback<List<ResourceDo>>() {

			@Override
			public void onSuccess(List<ResourceDo> result) {
				if (result.size()>0){
					getView().showPanel(true);
				}
				if (result.size()>=10){
					getView().disableAddNewCollection();	//If no of collections are more than or equal to 10 then disable the add new collection link
				}
				for(int i=0;i<result.size();i++){
					
					getView().insertCollectionToAssignment(result.get(i));
				}
				getView().hideLoading();	// Once all assignment inserted hide the loading animation.
			}
		});
	}
	
}
