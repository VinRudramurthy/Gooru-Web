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
package org.ednovo.gooru.client.mvp.authentication.afterthirteen;


import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.user.V2UserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.authentication.uc.ThankPopUpForUpdateProfile;
import org.ednovo.gooru.client.mvp.home.event.SetUpdateProfileImageEvent;
import org.ednovo.gooru.client.mvp.image.upload.ImageUploadPresenter;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;

/**
 *
 * @fileName : SignUpCompleteProfilePresenter.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 06-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class SignUpCompleteProfilePresenter extends PresenterWidget<IsSignUpCompleteProfile> implements SignUpCompleteProfileUiHandler {

	private ImageUploadPresenter imageUploadPresenter;

	@Inject
	public SignUpCompleteProfilePresenter(EventBus eventBus,
			IsSignUpCompleteProfile view,ImageUploadPresenter imageUploadPresenter) {
			super(eventBus, view);
			getView().setUiHandlers(this);
			this.imageUploadPresenter = imageUploadPresenter;
	}
	@Override
	public void onBind() {
		super.onBind();
		addRegisteredHandler(SetUpdateProfileImageEvent.TYPE, this);
	}
	@Override
	public void onReveal() {
		super.onReveal();
	}
	public void showUploadProfileImageWidget(){
		imageUploadPresenter.showUploadTypeWidgets(false);
		addToPopupSlot(imageUploadPresenter);
		imageUploadPresenter.setProfileImage(false);
		imageUploadPresenter.setCollectionImage(false);
		imageUploadPresenter.setEditResourceImage(false);
		imageUploadPresenter.setUdateProfileImage(true);
		imageUploadPresenter.setAnswerImage(false);
	}
	@Override
	public void displayView(){
		getView().displayView();
	}
	@Override
	public void setUpdateProfileImage(String imageUrl) {
		getView().setUpdateProfileImage(imageUrl);
	}
	@Override
	public void updateProfile(String fname,String lname,String aboutMe,String password) {
		AppClientFactory.getInjector().getUserService().updateV2ProfileDo("", "", fname, lname, aboutMe,password, "","",true, null, new SimpleAsyncCallback<V2UserDo>() {

			@Override
			public void onSuccess(V2UserDo result) {
				getView().getUpdateButton().setVisible(false);
				ThankPopUpForUpdateProfile thankPopUpForUpdateProfile=new ThankPopUpForUpdateProfile();
				thankPopUpForUpdateProfile.show();
			}
		});
	}



}
