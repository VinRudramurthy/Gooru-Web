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
/**
 * 
*/
package org.ednovo.gooru.client.mvp.home.library;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


/**
 * @fileName : LibraryBannerView.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 02-Dec-2013
 *
 * @Author IBC
 *
 * @Reviewer: 
 */
public class LibraryBannerView extends Composite{

	@UiField HTMLPanel partnerLogo, landingBannerInner, findLbl, shareLbl, measureLbl, contributeLbl, fourSteps;
	@UiField Label headerTag;
	@UiField Label subHeaderTag;
	@UiField LibraryStyleBundle libraryStyle;
	
	private static LibraryBannerViewUiBinder uiBinder = GWT
			.create(LibraryBannerViewUiBinder.class);

	interface LibraryBannerViewUiBinder extends
			UiBinder<Widget, LibraryBannerView> {
	}

	public LibraryBannerView(String placeToken) {
		initWidget(uiBinder.createAndBindUi(this));
		getLandingBannerText(placeToken);
	}
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	@Override
	public void onLoad() {
		landingBannerInner.getElement().setId("landingBannerInner");
		partnerLogo.getElement().setId("pnlPartnerLogo");
		headerTag.getElement().setId("lblHeaderTag");
		subHeaderTag.getElement().setId("lblSubHeaderTag");
		fourSteps.getElement().setId("pnlFourSteps");
		findLbl.getElement().setId("pnlFindLbl");
		shareLbl.getElement().setId("pnlShareLbl");
		measureLbl.getElement().setId("pnlMeasureLbl");
		contributeLbl.getElement().setId("pnlContributeLbl");		
	}
	
	private void getLandingBannerText(String placeToken) {
		if(placeToken.contains(PlaceTokens.DISCOVER)) {
			setLandingBannerText(i18n.GL0522(),i18n.GL0523(),i18n.GL0524(),i18n.GL0525(),i18n.GL0526(),i18n.GL0527(),i18n.GL0528(),i18n.GL0529(),i18n.GL0530(),i18n.GL0531());
			partnerLogo.setVisible(false);
			fourSteps.getElement().getStyle().setBackgroundColor("#000000");
			fourSteps.setVisible(false);
			headerTag.getElement().getStyle().clearPaddingTop();
		} else if(placeToken.contains(PlaceTokens.RUSD_LIBRARY)) {
			setLandingBannerText(i18n.GL0532(),i18n.GL0533(),i18n.GL0534(),i18n.GL0535(),i18n.GL0536(),i18n.GL0537(),i18n.GL0538(),i18n.GL0539(),i18n.GL0540(),i18n.GL0541());
			fourSteps.getElement().getStyle().setBackgroundColor("#000000");
			fourSteps.setVisible(false);
			partnerLogo.setStyleName(libraryStyle.rusdPartnerLogo());
			partnerLogo.setVisible(true);
			headerTag.getElement().getStyle().clearPaddingTop();
		}else if(placeToken.contains(PlaceTokens.EPISD_LIBRARY)) {
			setLandingBannerText(i18n.GL3501(),i18n.GL0533(),i18n.GL0534(),"","","","","","","");
			fourSteps.getElement().getStyle().setBackgroundColor("#000000");
			fourSteps.setVisible(false);
			partnerLogo.setStyleName(libraryStyle.episdPartnerLogo());
			partnerLogo.setVisible(true);
			headerTag.getElement().getStyle().clearPaddingTop();
		} else if(placeToken.contains(PlaceTokens.SAUSD_LIBRARY)) {
			setLandingBannerText(i18n.GL1902(),i18n.GL1903(),i18n.GL1904(),i18n.GL1905(),i18n.GL1906(),i18n.GL1907(),i18n.GL1908(),i18n.GL1909(),i18n.GL1910(),i18n.GL1911());
			fourSteps.getElement().getStyle().setBackgroundColor("#000000");
			fourSteps.setVisible(false);
			partnerLogo.setStyleName(libraryStyle.sausdPartnerLogo());
			partnerLogo.setVisible(true);
			headerTag.getElement().getStyle().clearPaddingTop();
		}else if (placeToken.contains(PlaceTokens.COMMUNITY)){
			setLandingBannerText(i18n.GL2046(),i18n.GL2047(),i18n.GL0524(),i18n.GL0525(),i18n.GL0526(),i18n.GL0527(),i18n.GL0528(),i18n.GL0529(),i18n.GL0530(),i18n.GL0531());
			fourSteps.getElement().getStyle().setBackgroundColor("#000000");
			fourSteps.setVisible(false);
			partnerLogo.setVisible(false);
			headerTag.getElement().getStyle().setPaddingTop(65, Unit.PX);
		}else if(placeToken.contains(PlaceTokens.YCGL_LIBRARY)) {
//			setLandingBannerText("Yuma Country Library","This library showcases collections created by teachers from 9 districts throughout Yuma County. A true education collaboration!",i18n.GL0534(),i18n.GL0535(),i18n.GL0536(),i18n.GL0537(),i18n.GL0538(),i18n.GL0539(),i18n.GL0540(),i18n.GL0541());
			fourSteps.getElement().getStyle().setBackgroundColor("#000000");
			fourSteps.setVisible(false);
			partnerLogo.setStyleName(libraryStyle.rusdPartnerLogo());
			partnerLogo.setVisible(true);
			headerTag.getElement().getStyle().clearPaddingTop();
		}
	}
	
	private void setLandingBannerText(String headerMsg, String subHeaderMsg, String findInlineMsg, String findMsg, String shareInlineMsg, String shareMsg, 
			String measureInlineMsg, String measureMsg, String contributeInlineMsg, String contributeMsg) {
			headerTag.setText(headerMsg);
			headerTag.getElement().setAttribute("alt",headerMsg);
			headerTag.getElement().setAttribute("title",headerMsg);
			
			subHeaderTag.setText(subHeaderMsg);
			subHeaderTag.getElement().setAttribute("alt",subHeaderMsg);
			subHeaderTag.getElement().setAttribute("title",subHeaderMsg);
			
			
			InlineLabel findInlineLbl = new InlineLabel(findInlineMsg);
			InlineLabel shareInlineLbl = new InlineLabel(shareInlineMsg);
			InlineLabel measureInlineLbl = new InlineLabel(measureInlineMsg);
			InlineLabel contributeInlineLbl = new InlineLabel(contributeInlineMsg);
			
			Label findLblMsg = new Label(findMsg);
			Label shareLblMsg = new Label(shareMsg);
			Label measureLblMsg = new Label(measureMsg);
			Label contributeLblMsg = new Label(contributeMsg);
			findLblMsg.setStyleName(libraryStyle.bannerSpanBlock());
			shareLblMsg.setStyleName(libraryStyle.bannerSpanBlock());
			measureLblMsg.setStyleName(libraryStyle.bannerSpanBlock());
			contributeLblMsg.setStyleName(libraryStyle.bannerSpanBlock());
			
			findLbl.add(findInlineLbl);
			findLbl.add(findLblMsg);
			
			shareLbl.add(shareInlineLbl);
			shareLbl.add(shareLblMsg);
			
			measureLbl.add(measureInlineLbl);
			measureLbl.add(measureLblMsg);
			
			contributeLbl.add(contributeInlineLbl);
			contributeLbl.add(contributeLblMsg);
	}
}