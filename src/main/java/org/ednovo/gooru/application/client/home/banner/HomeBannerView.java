package org.ednovo.gooru.application.client.home.banner;
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
import java.util.Random;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
/**
 *
 * @fileName : ViewMorePeopleView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 16-Jun-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class HomeBannerView extends BaseViewWithHandlers<HomeBannerUiHandlers> implements IsHomeBannerView,ClientConstants {

	private static HomeBannerViewUiBinder uiBinder = GWT
			.create(HomeBannerViewUiBinder.class);

	interface HomeBannerViewUiBinder extends
			UiBinder<Widget, HomeBannerView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@UiField Image imgBanner;
	@UiField Label lblPageTitle1,lblPageTitle2,lblPageTitle3;
	@UiField HTMLPanel panelTitleContainer;

	public HomeBannerView() {
		setWidget(uiBinder.createAndBindUi(this));

		if (AppClientFactory.loggedInUser != null){
			imgBanner.setUrl(AppClientFactory.getLoggedInUser().getSettings().getCdnEndPoint() +"/images/newlandingpage/"+generateRandomNumber()+".jpg");
		}else{
			imgBanner.setUrl("images/newlandingpage/"+generateRandomNumber()+".jpg");
		}

		lblPageTitle1.setText(i18n.GL3284_2());
		StringUtil.setAttributes(lblPageTitle1.getElement(), "lblPageTitle", "", "");
		lblPageTitle2.setText(i18n.GL3284_3());
		StringUtil.setAttributes(lblPageTitle2.getElement(), "lblPageTitle", "", "");
		lblPageTitle3.setText(i18n.GL3284_4());
		StringUtil.setAttributes(lblPageTitle3.getElement(), "lblPageTitle", "", "");

		StringUtil.setAttributes(panelTitleContainer.getElement(), "panelTitleContainer", i18n.GL3284_1(), i18n.GL3284_1());


	}


	/**
	 *
	 * @function generateRandomNumber
	 *
	 * @created_date : 18-Jun-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : int
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private int generateRandomNumber(){
		int num = 1;
		int max = 4;
		int min = 1;
		// NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();
	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    num = rand.nextInt((max - min) + 1) + min;
		return num;
	}

}
