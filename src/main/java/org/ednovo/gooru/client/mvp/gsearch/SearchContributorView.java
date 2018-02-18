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
package org.ednovo.gooru.client.mvp.gsearch;

import java.util.ArrayList;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.search.AutoSuggestContributorSearchDo;
import org.ednovo.gooru.client.uc.LiPanel;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.ui.HTMLEventPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 * @param <T>
 *            type of ResourceSearchResultDo
 */
public abstract class SearchContributorView extends PopupPanel{
	
	
	private static SearchContributorViewUiBinder uiBinder = GWT
			.create(SearchContributorViewUiBinder.class);
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	interface SearchContributorViewUiBinder extends
	UiBinder<Widget, SearchContributorView> {
	}
	
	@UiField HTMLPanel contributorDropDown;
	@UiField LiPanel userTxtLbl,organizationTxtLbl,userContainerLiPanel,organizationContainer;
	@UiField InlineLabel noResultsFoundLbl;
	@UiField UlPanel resultsPnl,noResultsPnl;
	
	/**
	 * Constructor
	 */
	public SearchContributorView(){
		setWidget(uiBinder.createAndBindUi(this));
		contributorDropDown.getElement().setId("contributorDropDown");
		userTxtLbl.getElement().setInnerText("Users");
		organizationTxtLbl.getElement().setInnerText("Organizations");
		noResultsFoundLbl.getElement().setInnerText("No Matches Found");
		this.setAutoHideEnabled(true);
		this.setStyleName("newpopupstyle");
	 }
	/**
	 * This method is used to set the data to auto suggested contributor popup.
	 * This will hide the contributors popup if no results found
	 * @param contributorSearchList
	 * @param contributorTxtBox
	 */
	public void setData(ArrayList<AutoSuggestContributorSearchDo> contributorSearchList,final TextBox contributorTxtBox) {
		userContainerLiPanel.clear();
		organizationContainer.clear();
		if(contributorSearchList.size()>0){
			noResultsPnl.setVisible(false);
			resultsPnl.setVisible(true);
			contributorDropDown.setVisible(true);
			for(int i=0;i<contributorSearchList.size();i++){
				for(int j=0;j<contributorSearchList.get(i).getUsers().size();j++){
					HTMLEventPanel usersContainer = new HTMLEventPanel("");
					final Image usersImage = new Image();
					final InlineLabel userNameLbl = new InlineLabel();
					usersImage.setUrl(contributorSearchList.get(i).getUsers().get(j).getProfileImage());
					usersImage.addErrorHandler(new ErrorHandler() {
						@Override
						public void onError(ErrorEvent event) {
							usersImage.setUrl("images/profilepage/user-profile-pic.png");
						}
					});
					usersImage.setStyleName("userImageStyle");
					userNameLbl.getElement().setInnerText(contributorSearchList.get(i).getUsers().get(j).getName());
					usersContainer.clear();
					usersContainer.add(usersImage);
					usersContainer.add(userNameLbl);
					usersContainer.setStyleName("anchorPanel");
					usersContainer.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							contributorTxtBox.setText(userNameLbl.getText());
							callCollectionsContributorSearch("user");
							contributorDropDown.setVisible(false);
						}
					});
					userContainerLiPanel.add(usersContainer);
				}
				for(int k=0;k<contributorSearchList.get(i).getOrganization().size();k++){
					HTMLEventPanel libraryContainer = new HTMLEventPanel("");
					final Image libraryImage = new Image();
					final InlineLabel libraryNameLbl = new InlineLabel();
					libraryImage.setUrl(contributorSearchList.get(i).getOrganization().get(k).getImage());
					libraryImage.addErrorHandler(new ErrorHandler() {
						@Override
						public void onError(ErrorEvent event) {
							libraryImage.setUrl("images/profilepage/user-profile-pic.png");
						}
					});
					libraryImage.setStyleName("userImageStyle");
					libraryNameLbl.getElement().setInnerText(contributorSearchList.get(i).getOrganization().get(k).getName());
					libraryContainer.clear();
					libraryContainer.add(libraryImage);
					libraryContainer.add(libraryNameLbl);
					libraryContainer.setStyleName("anchorPanel");
					libraryContainer.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							contributorTxtBox.setText(libraryNameLbl.getText());
							callCollectionsContributorSearch("organization");
							contributorDropDown.setVisible(false);
						}
					});
					organizationContainer.add(libraryContainer);
				}
			}
		}else{
			resultsPnl.setVisible(false);
			noResultsPnl.setVisible(true);
		}
	}
	
	public UlPanel getNoResultsPnl() {
		return noResultsPnl;
	}
	public UlPanel getResultsPnl() {
		return resultsPnl;
	}
	/**
	 * This method is called on click of user or organization data from the contributors popup and to send the type of contributor
	 * @param type
	 */
	public abstract void callCollectionsContributorSearch(String type);
}
