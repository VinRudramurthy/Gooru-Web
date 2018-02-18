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
package org.ednovo.gooru.client.mvp.profilepage.tab.content.tags;

import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.UserTagsDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class ProfileUserTagView extends Composite{

	private static ProfileUserTagViewUiBinder uiBinder = GWT
			.create(ProfileUserTagViewUiBinder.class);

	interface ProfileUserTagViewUiBinder extends
	UiBinder<Widget, ProfileUserTagView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	List<UserTagsDo> userTagDo = new ArrayList<UserTagsDo>();

	@UiField HTMLPanel userTagsConatiner,tagTextMessage;
	@UiField InlineLabel TagTextMessage;
	HTMLPanel followingContainer;
	HTMLPanel tagResourceContainer;
	@UiField ScrollPanel userTagScrollPanel;
	String limit = "10";
	int totalTagCount = 0;

	int totalHintCount;

	public ProfileUserTagView(List<UserTagsDo> userTagDo,HTMLPanel followingContainer, HTMLPanel tagResourceContainer) {
		initWidget(uiBinder.createAndBindUi(this));
		this.userTagDo = userTagDo;
		totalTagCount=0;
		totalTagCount =userTagDo.size();
		totalHintCount = userTagDo.get(0).getTotalHitCount();

		this.followingContainer = followingContainer;
		this.tagResourceContainer = tagResourceContainer;
		setData();
	}
	@UiHandler("userTagScrollPanel")
	public void dragFollowingScrollPanel(ScrollEvent event) {
		if (userTagScrollPanel.getVerticalScrollPosition() == userTagScrollPanel.getMaximumVerticalScrollPosition() && totalTagCount<totalHintCount) {
			AppClientFactory.getInjector().getUserService().getUserAddedContentTagSummary(AppClientFactory.getPlaceManager().getRequestParameter("id"),Integer.toString(totalTagCount),limit,new SimpleAsyncCallback<List<UserTagsDo>>() {

				@Override
				public void onSuccess(List<UserTagsDo> result) {

					if(result.size()>0){
						totalTagCount = totalTagCount+result.size();
						for(int i=0;i<result.size();i++){
							ProfileUserTagWidget profileUserTagWidget =new ProfileUserTagWidget(result.get(i),followingContainer,tagResourceContainer);
							userTagsConatiner.add(profileUserTagWidget);
						}

					}
				}
			});
		}
	}
	public void setData(){
		userTagsConatiner.clear();
		TagTextMessage.setText(i18n.GL1915());
		TagTextMessage.getElement().setId("lblTagTextMessage");
		TagTextMessage.getElement().setAttribute("alt",i18n.GL1915());
		TagTextMessage.getElement().setAttribute("title",i18n.GL1915());

		tagTextMessage.getElement().setInnerHTML(i18n.GL1937());
		tagTextMessage.getElement().setId("pnltagTextMessage");
		tagTextMessage.getElement().setAttribute("alt",i18n.GL1937());
		tagTextMessage.getElement().setAttribute("title",i18n.GL1937());

		userTagScrollPanel.getElement().setId("sbUserTagScrollPanel");
		userTagScrollPanel.getElement().getStyle().setHeight(190, Unit.PX);
		userTagsConatiner.getElement().setId("pnlUserTagsConatiner");

		for(int i=0;i<userTagDo.size();i++){
			ProfileUserTagWidget profileUserTagWidget =new ProfileUserTagWidget(userTagDo.get(i),followingContainer,tagResourceContainer);
			userTagsConatiner.add(profileUserTagWidget);
		}
	}
	
	
}


