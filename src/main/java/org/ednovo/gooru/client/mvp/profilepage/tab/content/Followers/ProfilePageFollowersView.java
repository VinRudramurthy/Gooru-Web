package org.ednovo.gooru.client.mvp.profilepage.tab.content.Followers;

import java.util.ArrayList;
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

public class ProfilePageFollowersView extends Composite{

	private static ProfilePageFollowersViewUiBinder uiBinder = GWT
			.create(ProfilePageFollowersViewUiBinder.class);

	interface ProfilePageFollowersViewUiBinder extends
			UiBinder<Widget, ProfilePageFollowersView> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField HTMLPanel followersUserConatiner;
	List<UserFollowDo> userFollowDo = new ArrayList<UserFollowDo>();
	@UiField InlineLabel followersTextMessage,followersTextMessageNormal;
	String tab;
	@UiField Button seeMorebtn;
	
	String limit = "20";
	int totalUserCount  = 0;
	int totalHitCount;
	
	private Integer pageInitialLimit = 20;
	private Integer offsetLimit = 0;
	
	public ProfilePageFollowersView(List<UserFollowDo> userFollowDo,String tab) {
		this.tab = tab;
		this.userFollowDo = userFollowDo;
		initWidget(uiBinder.createAndBindUi(this));
		totalUserCount =0;
		totalUserCount  =userFollowDo.size();
		if(userFollowDo != null && userFollowDo.size()>0)
		{
		totalHitCount = userFollowDo.get(0).getTotalHintCount();
		}
		setData();

	}
	public void setData()
	{
		
		followersTextMessage.setText(i18n.GL1914());
		followersTextMessage.getElement().setId("spnFollowersTextMessage");
		followersTextMessage.getElement().setAttribute("alt",i18n.GL1914());
		followersTextMessage.getElement().setAttribute("title",i18n.GL1914());
		
		followersTextMessage.getElement().setAttribute("style", "font-weight:bold");
		followersTextMessageNormal.setText(i18n.GL1914_1());
		followersTextMessageNormal.getElement().setId("spnFollowersTextMessageNormal");
		followersTextMessageNormal.getElement().setAttribute("alt",i18n.GL1914_1());
		followersTextMessageNormal.getElement().setAttribute("title",i18n.GL1914_1());
		
		followersUserConatiner.clear();
		
		if(totalHitCount>pageInitialLimit)
		{
			seeMorebtn.setVisible(true);
		}
		else
		{
			seeMorebtn.setVisible(false);
		}
		
		for(int i=0; i< userFollowDo.size(); i++){
			ProfilePageUserInfoWidget profilePageUserInfoWidget=new ProfilePageUserInfoWidget(userFollowDo.get(i),tab);
			followersUserConatiner.add(profilePageUserInfoWidget);
		}
		
		followersUserConatiner.getElement().setId("pnlFollowersUserConatiner");
		seeMorebtn.getElement().setId("btnSeeMorebtn");
	}
	@UiHandler("seeMorebtn")
	public void onSeeMoreClick(ClickEvent event) {
		offsetLimit = pageInitialLimit;
			AppClientFactory.getInjector().getUserService().getFollowedByUsers(AppClientFactory.getPlaceManager().getRequestParameter("id"),Integer.toString(offsetLimit),limit, new SimpleAsyncCallback<List<UserFollowDo>>() {

			@Override
			public void onSuccess(List<UserFollowDo> result) {
			
				if(result.size()>0){
				
					pageInitialLimit = pageInitialLimit + 20;
					if(totalHitCount>pageInitialLimit)
					{
						seeMorebtn.setVisible(true);
					}
					else
					{
						seeMorebtn.setVisible(false);
					}
					
					for(int i=0; i< result.size(); i++){
						ProfilePageUserInfoWidget profilePageUserInfoWidget=new ProfilePageUserInfoWidget(result.get(i),tab);
						followersUserConatiner.add(profilePageUserInfoWidget);
					}
				
				}
			}
		});
	
	}

}
