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
package org.ednovo.gooru.client.mvp.profilepage.tab.content.Followers;


import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.shared.model.user.UserFollowDo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ProfilePageUserInfoWidget extends Composite{

	private static ProfilePageUserInfoUiBinder uiBinder = GWT
			.create(ProfilePageUserInfoUiBinder.class);

	interface ProfilePageUserInfoUiBinder extends
			UiBinder<Widget, ProfilePageUserInfoWidget> {
	}
	@UiField Image profileThumbnailImage;
	@UiField Label userNameLabel,userCollections,userFollowDetails;
	@UiField HTMLPanel authorPanel;
	
	UserFollowDo userFollowDo=new UserFollowDo();
	String tab;
	public ProfilePageUserInfoWidget(UserFollowDo userFollowDo,String tab) {
		this.userFollowDo=userFollowDo;
		this.tab = tab;
		initWidget(uiBinder.createAndBindUi(this));
		SetData();
		authorPanel.getElement().setId("pnlAuthorPanel");
		profileThumbnailImage.getElement().setId("imgProfileThumbnailImage");
		userNameLabel.getElement().setId("lblUserNameLabel");
		userCollections.getElement().setId("lblUserCollections");
		userFollowDetails.getElement().setId("lblUserFollowDetails");
	}
	public void SetData(){
	if("following".equalsIgnoreCase(tab)){
			setView(userFollowDo);
		}
		if("followers".equalsIgnoreCase(tab)){
			setView(userFollowDo);
		}
	}
	
	public void	setView(UserFollowDo userFollowDo){
		profileThumbnailImage.setUrl(userFollowDo.getProfileImageUrl());
		profileThumbnailImage.addErrorHandler(new ErrorHandler() {
			
			@Override
			public void onError(ErrorEvent event) {
				profileThumbnailImage.setUrl("images/settings/setting-user-image.png");
				
			}
		});
		
		//userNameLabel.setText(userFollowDo.getUsername());
		userNameLabel.getElement().setId(userFollowDo.getGooruUid());

		int collectionCount= userFollowDo.getSummary().getCollection();
		userCollections.setText(collectionCount+(collectionCount==1?" Collection":" Collections"));
		userCollections.getElement().setAttribute("alt",collectionCount+(collectionCount==1?" Collection":" Collections"));
		userCollections.getElement().setAttribute("title",collectionCount+(collectionCount==1?" Collection":" Collections"));

		userFollowDetails.setText(userFollowDo.getSummary().getFollowers()+(userFollowDo.getSummary().getFollowers()==1?" Follower":" Followers")+" | " +userFollowDo.getSummary().getFollowing()+" Following");
		userFollowDetails.getElement().setAttribute("alt",userFollowDo.getSummary().getFollowers()+(userFollowDo.getSummary().getFollowers()==1?" Follower":" Followers")+" | " +userFollowDo.getSummary().getFollowing()+" Following");
		userFollowDetails.getElement().setAttribute("title",userFollowDo.getSummary().getFollowers()+(userFollowDo.getSummary().getFollowers()==1?" Follower":" Followers")+" | " +userFollowDo.getSummary().getFollowing()+" Following");
		
		String gooruUid = userNameLabel.getElement().getId();
		Anchor anchor = new Anchor();
		String userName=userFollowDo.getUsername();
		
		String token= "#"+PlaceTokens.PROFILE_PAGE+"&id="+gooruUid+"&user="+userName;
		anchor.setHref(token);
		anchor.setText(userName);
		anchor.getElement().setAttribute("style","font-size: 18px !important");		
		anchor.setTarget("_blank");
		userNameLabel.setText("");
		userNameLabel.getElement().setAttribute("alt","");
		userNameLabel.getElement().setAttribute("title","");
//		boolean isEnabled =  userFollowDo.getCustomFields() != null && userFollowDo.getCustomFields().get(0).getOptionalValue() != null && userFollowDo.getCustomFields().get(0).getOptionalValue().equalsIgnoreCase("true") ? true : false;
		boolean isEnabled = false;
		if(userFollowDo.getCustomFields() != null){
			for(int i=0;i<userFollowDo.getCustomFields().size(); i++)
			if(userFollowDo.getCustomFields().get(i).getOptionalKey().equalsIgnoreCase("show_profile_page")){
				 isEnabled = userFollowDo.getCustomFields().get(i).getOptionalValue().equalsIgnoreCase("true") ? true : false;
			}
		}
		if (isEnabled){
			userNameLabel.getElement().appendChild(anchor.getElement());
		}else{
			userNameLabel.setText(userFollowDo.getUsername());
			userNameLabel.getElement().setAttribute("alt",userFollowDo.getUsername());
			userNameLabel.getElement().setAttribute("title",userFollowDo.getUsername());
			userNameLabel.getElement().getStyle().setCursor(Cursor.DEFAULT);
		}
			
		
	}
	public void clearAll()
	{
		profileThumbnailImage.setUrl("");
		userNameLabel.setText("");
		userNameLabel.getElement().setAttribute("alt","");
		userNameLabel.getElement().setAttribute("title","");
		userCollections.setText("");
		userCollections.getElement().setAttribute("alt","");
		userCollections.getElement().setAttribute("title","");
		userFollowDetails.setText("");
		userFollowDetails.getElement().setAttribute("alt","");
		userFollowDetails.getElement().setAttribute("title","");
	}
	
}
