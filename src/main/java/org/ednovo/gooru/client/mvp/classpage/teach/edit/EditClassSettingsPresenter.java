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
package org.ednovo.gooru.client.mvp.classpage.teach.edit;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpage.event.UpdateClassTitleEvent;
import org.ednovo.gooru.client.mvp.image.upload.ImageUploadPresenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;


/**
 * @fileName : EditClassSettingsPresenter.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 01-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class EditClassSettingsPresenter extends PresenterWidget<IsEditClassSettingsView> implements EditClassSettingsViewUiHandler{

	private ImageUploadPresenter imageUploadPresenter;

	private SimpleAsyncCallback<Map<String, String>> shareUrlGenerationAsyncCallback;

	MessageProperties i18n = GWT.create(MessageProperties.class);

	@Inject
	public EditClassSettingsPresenter(EventBus eventBus,IsEditClassSettingsView view,ImageUploadPresenter imageUploadPresenter) {
		super(eventBus, view);
		getView().setUiHandlers(this);
		this.imageUploadPresenter=imageUploadPresenter;
	}

	@Override
	public void onBind() {
		super.onBind();
		setShareUrlGenerationAsyncCallback(new SimpleAsyncCallback<Map<String, String>>() {
			@Override
			public void onSuccess(Map<String, String> shortenUrl) {
				getView().setShortenUrl(shortenUrl);
			}
		});

	}

	@Override
	public void onReveal() {
		super.onReveal();
	}

	@Override
	protected void onHide() {
		super.onHide();
	}


	@Override
	public void showImageUploadWidget() {
		String classpageId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
		imageUploadPresenter.setCollectionImage(false);
		imageUploadPresenter.setClassPageImage(true);
		imageUploadPresenter.setEditResourceImage(false);
		imageUploadPresenter.setAnswerImage(false);
		imageUploadPresenter.setClasspageId(classpageId);
		addToPopupSlot(imageUploadPresenter);
	}


	public void setClassData(ClasspageDo classpageDo) {
		getView().setData(classpageDo);
		final String classpageId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
		checkDataStatus(classpageId);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.EditClassSettingsViewUiHandler#generateShareLink(java.lang.String)
	 */
	@Override
	public void generateShareLink(String classUid) {
		try{
			String courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID);
			Map<String, String> params = new HashMap<String, String>();
			params.put("type", AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken());
			if(courseId != null){
				params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, courseId);
			}
			AppClientFactory.getInjector().getSearchService().getShortenShareUrl(classUid, params, getShareUrlGenerationAsyncCallback());
		}catch(Exception e){
			AppClientFactory.printSevereLogger("EditClassSessingsPresenter : generateShareLink : "+e.getMessage());
		}
	}

	/**
	 * This method is to get the shareUrlGenerationAsyncCallback
	 */
	public SimpleAsyncCallback<Map<String, String>> getShareUrlGenerationAsyncCallback() {
		return shareUrlGenerationAsyncCallback;
	}

	/**
	 * This method is to set the shareUrlGenerationAsyncCallback
	 */
	public void setShareUrlGenerationAsyncCallback(SimpleAsyncCallback<Map<String, String>> shareUrlGenerationAsyncCallback) {
		this.shareUrlGenerationAsyncCallback = shareUrlGenerationAsyncCallback;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.EditClassSettingsViewUiHandler#updateClass(org.ednovo.gooru.application.shared.model.content.ClasspageDo)
	 */
	@Override
	public void updateClass(final String title,String grade,String sharing,String fileName) {
		String classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
		if(classId != null){
			AppClientFactory.getInjector().getClasspageService().v3UpdateClass(classId, title,grade,fileName,sharing,null,null, new AsyncCallback<ClasspageDo>() {

				@Override
				public void onSuccess(ClasspageDo result) {
					getView().setUpdateClassData(result);
					AppClientFactory.getEventBus().fireEvent(new UpdateClassTitleEvent(title));
				}

				@Override
				public void onFailure(Throwable caught) {

				}
			});
		}
	}

	@Override
	public void deleteClass(final String classpageId) {
		AppClientFactory.getInjector().getClasspageService().V3DeleteClass(classpageId, new AsyncCallback<Integer>() {
			@Override
			public void onFailure(Throwable caught) {
				getView().onErrorPopup(i18n.GL3578());
			}

			@Override
			public void onSuccess(Integer result) {
				if(result == 200){
				   getView().onDeleteClassSuccess();
				}else{
					getView().onErrorPopup(i18n.GL3578());
				}
			}
		});
	}

	@Override
	public void checkDataStatus(String classpageId) {
		AppClientFactory.getInjector().getClasspageService().getClassUsageDataSignal(classpageId, null, new AsyncCallback<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				getView().onErrorPopup(i18n.GL3578());
			}

			@Override
			public void onSuccess(Boolean result) {
				getView().enableDeleteBtn(result);
			}
		});
	}

}
