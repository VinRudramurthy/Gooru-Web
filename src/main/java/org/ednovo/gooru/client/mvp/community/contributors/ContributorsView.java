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
package org.ednovo.gooru.client.mvp.community.contributors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.library.LibraryUserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


/**
 *
 * @fileName : ContributorsView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 07-Dec-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class ContributorsView extends
		BaseViewWithHandlers<ContributorsUiHandlers> implements
		IsContributorsView {

	private static ContributorsViewUiBinder uiBinder = GWT
			.create(ContributorsViewUiBinder.class);

	private static String CONTRIBUTORS_DATA = "contributorsData";
	ArrayList<LibraryUserDo> contributorsList = new ArrayList<LibraryUserDo>();

	interface ContributorsViewUiBinder extends
			UiBinder<Widget, ContributorsView> {
	}

	@UiField
	ContributorsCBundle fc;

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField
	HTMLPanel panelFeaturedContributors, panelContributorsList;
	@UiField
	Anchor ancView;

	@UiField
	Label lblPleaseWait;

	private Storage stockStore = Storage.getLocalStorageIfSupported();

	/**
	 * Class constructor
	 */
	public ContributorsView() {
		setWidget(uiBinder.createAndBindUi(this));

		setIds();
//		getContributorsList();
	}

	/**
	 * @function setIds
	 *
	 * @created_date : Jul 28, 2014
	 *
	 * @description
	 *
	 *
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void setIds() {
		Map<String, String> urlPara = new HashMap<String, String>();
		urlPara.put("page", "featured-contributors");

		ancView.setText(i18n.GL2050());
		String url = StringUtil.generateMessage(PlaceTokens.COMMUNITY, urlPara);
		url = url.replaceAll("%26", "&");
		ancView.setHref("#" + url);
		StringUtil.setAttributes(ancView.getElement(), "ancView",
				i18n.GL2050(), i18n.GL2050());
	}

	@Override
	public void onLoad() {
		onLoad();
	}

	/**
	 *
	 * @function getContributorsList
	 *
	 * @created_date : Jul 28, 2014
	 *
	 * @description
	 *
	 *
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	@Override
	public void getContributorsList() {

		String map = null;
		lblPleaseWait.setVisible(false);
		if (stockStore != null && stockStore.getItem(CONTRIBUTORS_DATA) != null){
			map = stockStore.getItem(CONTRIBUTORS_DATA);
			deserializeCollaboratorsList(map);
		} else {
			AppClientFactory.getInjector().getLibraryService().getLibraryContributorsUsers("community",new SimpleAsyncCallback<String>() {
				@Override
				public void onSuccess(String result) {
					if (stockStore != null) {
						stockStore.setItem(CONTRIBUTORS_DATA,
								result);
					}
					deserializeCollaboratorsList(result);
				}
			});
		}
	}
	/**
	 *
	 * @function deserializeCollaboratorsList
	 *
	 * @created_date : 28-Apr-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param jsonString
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	void deserializeCollaboratorsList(String jsonString){
		AppClientFactory.getInjector().getLibraryService().deserializeCollaborators(jsonString, new SimpleAsyncCallback<ArrayList<LibraryUserDo>>() {

			@Override
			public void onSuccess(ArrayList<LibraryUserDo> result) {
				contributorsList = result;
				displayContributors(result);
			}
		});
	}


	/**
	 *
	 * @function displayContributors
	 *
	 * @created_date : Jul 28, 2014
	 *
	 * @description
	 *
	 *
	 * @param result
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void displayContributors(ArrayList<LibraryUserDo> result) {
		int count=0;
		panelContributorsList.clear();
		for (int i = 0; count < 8; i++) {
			if (result.get(i).getGooruUId() != null
					&& !result.get(i).getGooruUId().equalsIgnoreCase("")
					&& !result.get(i).getUsername()
							.equalsIgnoreCase("elaine.tu")
					&& !result.get(i).getUsername().equalsIgnoreCase("bennya")) {
				final Image img = new Image();
				img.setUrl(AppClientFactory.getLoggedInUser().getSettings()
						.getProfileImageUrl()
						+ result.get(i).getGooruUId() + ".png");
				img.addErrorHandler(new ErrorHandler() {

					@Override
					public void onError(ErrorEvent event) {
						img.setUrl("images/settings/setting-user-image.png");
					}
				});

				img.getElement().addClassName(fc.profileImage());
				panelContributorsList.add(img);

				count++;
			}
		}
	}

}
