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
package org.ednovo.gooru.client.mvp.classpages.studentView;

/**
 * 
 */
import java.util.List;

import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.child.ChildPresenter;
import org.ednovo.gooru.client.gin.AppClientFactory;
import org.ednovo.gooru.client.service.ClasspageService;
import org.ednovo.gooru.client.service.ResourceServiceAsync;
import org.ednovo.gooru.shared.model.content.ResourceDo;

/**
 * @author Search Team
 * 
 */
public class AssignmentsPresenter extends ChildPresenter<AssignmentsPresenter, IsAssignmentsView> {


	ClasspageService classpageService=null;
	
	private SimpleAsyncCallback<List<ResourceDo>> getMyUserCollectionsAsyncCallback;
	
	/**
	 * Class constructor
	 * 
	 * @param childView 
	 */
	public AssignmentsPresenter(IsAssignmentsView childView) {
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
	
	private void onBind() {
		throw new RuntimeException("Not implemented");
	}
	
	
	private void onLoad() {
		throw new RuntimeException("Not implemented");
	}
	
	public ResourceServiceAsync getResourceService() {
		
		return AppClientFactory.getInjector().getResourceService();
	}
	
	/*public SimpleAsyncCallback<List<CollectionDo>> getUserCollectionsAsyncCallback() {
		if (getMyUserCollectionsAsyncCallback == null) {
			getMyUserCollectionsAsyncCallback = new SimpleAsyncCallback<List<CollectionDo>>() {

				@Override
				public void onSuccess(List<CollectionDo> result) {
					getView().onPostUserCollections(result);
					if (result.size()>0){
						getView().showPanel(true);
					}
					if (result.size()>=10){
						getView().disableAddNewCollection();
					}
				}
			};
		}
		return getMyUserCollectionsAsyncCallback;
	}*/
	 
	public void getAssignmentCollections(String assignmentId) {
		AppClientFactory.getInjector().getClasspageService().v2GetAssignmentCollectionsById(assignmentId, new SimpleAsyncCallback<List<ResourceDo>>() {

			@Override
			public void onSuccess(List<ResourceDo> result) {
				/*if (result.size()>0){
					getView().showPanel(true);
				}
				if (result.size()>=10){
					getView().disableAddNewCollection();
				}*/
				if(result.size()==0){
					getView().emptyAssignment();
				}
				for(int i=0;i<result.size();i++){
					getView().insertCollectionToAssignment(result.get(i));
				}
			}
		});
	}
}
