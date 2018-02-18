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
package org.ednovo.gooru.client.uc;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @fileName : UserProfileUc.java
 *
 * @description : This class used to show the Popup whe we mouse over on User
 *              Name.
 *
 * @version : 1.0
 *
 * @date: Jul 25, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */

public class UserProfileUc extends Composite{

	private static UserProfileUcUiBinder uiBinder = GWT
			.create(UserProfileUcUiBinder.class);

	interface UserProfileUcUiBinder extends UiBinder<Widget, UserProfileUc> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	Label userNameLbl, userProfileDesc;

	@UiField
	Image profileImage;

	String aboutUser;

	private static final String PROFILE_DEFAULT_IMAGE ="./images/profilepage/user-profile-popup-pic-small.png";

	public UserProfileUc(String userName, String aboutMe, String thumbnailImage) {
		initWidget(uiBinder.createAndBindUi(this));
		this.aboutUser = aboutMe;
		if (this.aboutUser != null) {
			if (this.aboutUser.length() > 60) {
				this.aboutUser = this.aboutUser.substring(0, 60) + "...";
			}
		}
		userNameLbl.setText(i18n.GL1053());
		userNameLbl.getElement().setId("lblUserNameLbl");
		userNameLbl.getElement().setAttribute("alt", i18n.GL1053());
		userNameLbl.getElement().setAttribute("title", i18n.GL1053());
		userProfileDesc.setText(i18n.GL1054());
		userProfileDesc.getElement().setId("lblUserProfileDesc");
		userProfileDesc.getElement().setAttribute("alt", i18n.GL1054());
		userProfileDesc.getElement().setAttribute("title", i18n.GL1054());

		userNameLbl.setText(userName);
		userNameLbl.getElement().setAttribute("alt", userName);
		userNameLbl.getElement().setAttribute("title", userName);
		userProfileDesc.setText(this.aboutUser);
		userProfileDesc.getElement().setAttribute("alt", this.aboutUser);
		userProfileDesc.getElement().setAttribute("title", this.aboutUser);
		profileImage.addErrorHandler(new ProfileDefaultImage());

		if (!thumbnailImage.equalsIgnoreCase("") || thumbnailImage != null) {
			setUserProfileImageUrl(thumbnailImage);
		}
		profileImage.setAltText(userName);
		profileImage.setTitle(userName);
		profileImage.getElement().setId("imgProfileImage");
		profileImage.getElement().setAttribute("alt", userName);
		profileImage.getElement().setAttribute("title", userName);
	}

	private class ProfileDefaultImage implements ErrorHandler {
		@Override
		public void onError(ErrorEvent event) {
			profileImage.setUrl(PROFILE_DEFAULT_IMAGE);
		}
	}

	public void setUserProfileImageUrl(String imageUrl) {
		double randomNumber = Math.random();
		profileImage.setUrl(imageUrl + "?p=" + randomNumber);
	}
}
