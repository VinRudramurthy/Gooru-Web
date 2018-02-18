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
package org.ednovo.gooru.client.mvp.home.library.contributors;
/**
 * 
 * @fileName : LibraryContributorsView.java
 *
 * @description : 
 *
 * @version : 1.0
 *
 * @date: 03-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */

import java.util.ArrayList;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.library.LibraryUserDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class LibraryContributorsView extends Composite{

	@UiField HTMLPanel container,contributorsText;
	@UiField HTMLPanel featuredEducatorsPanel;
	
	private static LibraryContributorsViewUiBinder uiBinder = GWT
			.create(LibraryContributorsViewUiBinder.class);

	interface LibraryContributorsViewUiBinder extends
			UiBinder<Widget, LibraryContributorsView> {
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	public LibraryContributorsView(String placeToken) {
		initWidget(uiBinder.createAndBindUi(this));
		contributorsText.getElement().setInnerText(i18n.GL1182());
		contributorsText.getElement().setId("pnlContributorsText");
		contributorsText.getElement().setAttribute("alt",i18n.GL1182());
		contributorsText.getElement().setAttribute("title",i18n.GL1182());
		featuredEducatorsPanel.getElement().setId("pnlFeaturedEducatorsPanel");
		getFeaturedUsers(placeToken);
	}
	
	/**
	 * 
	 * @param placeToken 
	 * @function getFeaturedUsers 
	 * 
	 * @created_date : 06-Dec-2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : 
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	private void getFeaturedUsers(final String placeToken) {
		AppClientFactory.getInjector().getLibraryService().getLibraryFeaturedUsers(placeToken, new SimpleAsyncCallback<ArrayList<LibraryUserDo>>() {
			@Override
			public void onSuccess(ArrayList<LibraryUserDo> userDoList) {
				setFeaturedUsers(userDoList,placeToken);
			}
		});
	}
	
	/**
	 * 
	 * @param placeToken 
	 * @function setFeaturedUsers 
	 * 
	 * @created_date : 06-Dec-2013
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param userDoList
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	private void setFeaturedUsers(ArrayList<LibraryUserDo> userDoList, String placeToken) {
		MixpanelUtil.mixpanelEvent("View_ContributorsPage");
		container.getElement().setId("container");
		for(int i=0; i<userDoList.size();i++) {
			featuredEducatorsPanel.add(new LibraryContributor(userDoList.get(i),placeToken));
		}
	}
}
