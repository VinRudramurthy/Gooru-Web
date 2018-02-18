package org.ednovo.gooru.client.mvp.profilepage.tab.content.Followers;


import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.UserFollowDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class ProfilePagefollowingView extends Composite {

	private static ProfilePagefollowingViewUiBinder uiBinder = GWT
			.create(ProfilePagefollowingViewUiBinder.class);

	interface ProfilePagefollowingViewUiBinder extends
			UiBinder<Widget, ProfilePagefollowingView> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField HTMLPanel userConatiner;
	@UiField InlineLabel follwingTextMessage,follwingTextMessageDes;
	
	@UiField Button seeMorebtn;
	
	List<UserFollowDo> userFollowDo;
	
	private Integer pageInitialLimit = 20;
	private Integer offsetLimit = 0;

	
	String tab;
	String limit = "20";
	int totalUserCount  = 0;
	
	int totalHitCount = 0;
	
	public ProfilePagefollowingView(List<UserFollowDo> userFollowDo,String tab) {
		initWidget(uiBinder.createAndBindUi(this));
		this.userFollowDo = userFollowDo;
		this.tab = tab;
		totalUserCount =0;
		totalUserCount  =userFollowDo.size();
		if(userFollowDo != null && userFollowDo.size()>0)
		{
		totalHitCount = userFollowDo.get(0).getTotalHintCount();
		}
		setData();

	}
	public void setData(){
		
		follwingTextMessage.setText(i18n.GL1913());
		follwingTextMessage.getElement().setId("spnFollwingTextMessage");
		follwingTextMessage.getElement().setAttribute("alt",i18n.GL1913());
		follwingTextMessage.getElement().setAttribute("title",i18n.GL1913());
		follwingTextMessageDes.setText(i18n.GL1914_1());
		follwingTextMessageDes.getElement().setId("spnFollwingTextMessageDes");
		follwingTextMessageDes.getElement().setAttribute("alt",i18n.GL1914_1());
		follwingTextMessageDes.getElement().setAttribute("title",i18n.GL1914_1());
		
		follwingTextMessageDes.getElement().setAttribute("style", "font-weight: normal;");
		userConatiner.clear();
		
		if(totalHitCount>pageInitialLimit)
		{
			seeMorebtn.setVisible(true);
		}
		else
		{
			seeMorebtn.setVisible(false);
		}
		
		for(int i=0; i< userFollowDo.size(); i++){
			ProfilePageUserInfoWidget profilePageUserInfo=new ProfilePageUserInfoWidget(userFollowDo.get(i),tab);
			userConatiner.add(profilePageUserInfo);
		}
		userConatiner.getElement().setId("pnlUserConatiner");
		seeMorebtn.getElement().setId("btnSeeMorebtn");
		
	}
	@UiHandler("seeMorebtn")
	public void onSeeMoreClick(ClickEvent event) {
	
		offsetLimit = pageInitialLimit;
		AppClientFactory.getInjector().getUserService().getFollowedOnUsers(AppClientFactory.getPlaceManager().getRequestParameter("id"),Integer.toString(offsetLimit),limit, new SimpleAsyncCallback<List<UserFollowDo>>() {
			
			@Override
			public void onSuccess(List<UserFollowDo> result) {
			if(result.size()>0){
			//	totalUserCount  = totalUserCount +result.size();
				
				pageInitialLimit = pageInitialLimit + 20;
				if(totalUserCount>pageInitialLimit)
				{
					seeMorebtn.setVisible(true);
				}
				else
				{
					seeMorebtn.setVisible(false);
				}

				
				for(int i=0; i< result.size(); i++){
					ProfilePageUserInfoWidget profilePageUserInfo=new ProfilePageUserInfoWidget(result.get(i),tab);
					userConatiner.add(profilePageUserInfo);
				}
				}
			}
		});
		
	}
}
