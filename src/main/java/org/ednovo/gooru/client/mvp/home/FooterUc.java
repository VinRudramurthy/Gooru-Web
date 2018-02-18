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
package org.ednovo.gooru.client.mvp.home;

import java.util.Date;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.GooruCBundle;
import org.ednovo.gooru.client.mvp.faq.CopyRightPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsAndPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsOfUse;
import org.ednovo.gooru.client.mvp.search.event.SetFooterEvent;
import org.ednovo.gooru.client.mvp.search.event.SetFooterHandler;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class FooterUc extends Composite {

	private static FooterUcUiBinder uiBinder = GWT.create(FooterUcUiBinder.class);

	@UiTemplate("FooterUc.ui.xml")
	interface FooterUcUiBinder extends UiBinder<Widget, FooterUc> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	SetFooterHandler setFooter=new SetFooterHandler(){

		@Override
		public void setFooterEvent(String placeName) {
//			setMargins(placeName);
		}


	};

	@UiField(provided = true)
	GooruCBundle res;

	@UiField
	Anchor aboutGooruAnr;

	/*@UiField
	Anchor featuresAnr;*/

	@UiField
	Anchor communityAnr;

	@UiField
	Anchor supportAnr;

	@UiField(provided = true)
	Anchor termsAndPolicyAnr;

	@UiField(provided = true)
	Anchor privacyAndPolicyAnr;

	@UiField(provided = true)
	Anchor copyRightAnr;

	@UiField
	Anchor careersAnr;//mixpanelLink;

	@UiField
	Anchor contactUsAnr;

	@UiField Label copyRightYearText;

	private TermsAndPolicyVc termsAndPolicyVc;

	private CopyRightPolicyVc copyRightPolicy;

	private TermsOfUse termsOfUse;


	@UiField
	FlowPanel goorulandingFooterContainer, innerFooterDiv;


	/**
	 * Class constructor
	 */
	public FooterUc() {
		this.res = GooruCBundle.INSTANCE;
		res.css().ensureInjected();
		termsAndPolicyVc = new TermsAndPolicyVc(false) {

			@Override
			public void openParentPopup() {

			}
		};
		termsAndPolicyAnr = new Anchor();
		copyRightAnr = new Anchor();
		privacyAndPolicyAnr=new Anchor();
		copyRightPolicy = new CopyRightPolicyVc() {

			@Override
			public void openParentPopup() {
				//No need to set.
			}
		};
		termsOfUse=new TermsOfUse(){

			@Override
			public void openParentPopup() {
				// TODO Auto-generated method stub

			}

		};


		/**
		 * Added click handler for showing Terms ans Policy popup in footer
		 **/
		privacyAndPolicyAnr.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
				termsAndPolicyVc.show();
				termsAndPolicyVc.center();
			}
		});
		termsAndPolicyAnr.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
				termsOfUse.show();
				termsOfUse.center();
			}
		});

		/**
		 * Added click handler for showing copy right popup in footer
		 **/
		copyRightAnr.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
				copyRightPolicy.show();

				copyRightPolicy.center();
			}
		});

		initWidget(uiBinder.createAndBindUi(this));
		aboutGooruAnr.setText(i18n.GL1242());
		aboutGooruAnr.getElement().setAttribute("alt",i18n.GL1242());
		aboutGooruAnr.getElement().setAttribute("title",i18n.GL1242());

		communityAnr.setText(i18n.GL3333());
		communityAnr.getElement().setAttribute("alt",i18n.GL3333());
		communityAnr.getElement().setAttribute("title",i18n.GL3333());

		supportAnr.setText(i18n.GL0194());
		supportAnr.getElement().setAttribute("alt",i18n.GL0194());
		supportAnr.getElement().setAttribute("title",i18n.GL0194());

		termsAndPolicyAnr.setText(i18n.GL0872());
		termsAndPolicyAnr.getElement().setAttribute("alt",i18n.GL0872());
		termsAndPolicyAnr.getElement().setAttribute("title",i18n.GL0872());

		privacyAndPolicyAnr.setText(i18n.GL0873());
		privacyAndPolicyAnr.getElement().setId("lnkPrivacyAndPolicyAnr");
		privacyAndPolicyAnr.getElement().setAttribute("alt",i18n.GL0873());
		privacyAndPolicyAnr.getElement().setAttribute("title",i18n.GL0873());

		copyRightAnr.setText(i18n.GL0875());
		copyRightAnr.getElement().setAttribute("alt",i18n.GL0875());
		copyRightAnr.getElement().setAttribute("title",i18n.GL0875());

		careersAnr.setText(i18n.GL1244());
		careersAnr.getElement().setAttribute("alt",i18n.GL1244());
		careersAnr.getElement().setAttribute("title",i18n.GL1244());

		contactUsAnr.setText(i18n.GL1245());
		contactUsAnr.getElement().setAttribute("alt",i18n.GL1245());
		contactUsAnr.getElement().setAttribute("title",i18n.GL1245());

//
		Date todaysYear = new Date();
		String copyRight = i18n.GL1246() + "" + (todaysYear.getYear() + 1900);
		copyRightYearText.setText(copyRight);
		copyRightYearText.getElement().setId("lblCopyRightYearText");
		copyRightYearText.getElement().setAttribute("alt",copyRight);
		copyRightYearText.getElement().setAttribute("title",copyRight);

//		mixpanelLink.setTarget("_blank");
//		mixpanelLink.setHref("https://mixpanel.com/f/partner");

		aboutGooruAnr.setHref("http://about.gooru.org/");
		aboutGooruAnr.setTarget("_blank");
		//featuresAnr.setHref("http://about.gooru.org/product/overview/");
		//featuresAnr.setTarget("_blank");
		communityAnr.setHref("http://about.gooru.org/educators/stories/");
		communityAnr.setTarget("_blank");
		supportAnr.setHref("http://support.gooru.org/hc/en-us");
		supportAnr.setTarget("_blank");
		careersAnr.setHref("http://about.gooru.org/about/careers/");
		careersAnr.setTarget("_blank");
		contactUsAnr.setHref("http://about.gooru.org/contact/");
		contactUsAnr.setTarget("_blank");

//		mixpanelLink.getElement().setId("lnkMixpanelLink");
		aboutGooruAnr.getElement().setId("lnkAboutGooru");
		communityAnr.getElement().setId("lnkCommunity");
		supportAnr.getElement().setId("lnkSupport");
		termsAndPolicyAnr.getElement().setId("lnkTermsAndPolicy");
		copyRightAnr.getElement().setId("lnkCopyRight");
		careersAnr.getElement().setId("lnkCareers");
		contactUsAnr.getElement().setId("lnkcontactUs");
		innerFooterDiv.getElement().setId("fpnlInnerFooterDiv");

		AppClientFactory.getEventBus().addHandler(SetFooterEvent.TYPE,setFooter);
	}

	public void setMargins(String placeName){
		goorulandingFooterContainer.getElement().getStyle().clearMargin();
		goorulandingFooterContainer.getElement().getStyle().clearTop();
		goorulandingFooterContainer.getElement().getStyle().clearPosition();
		goorulandingFooterContainer.getElement().getStyle().setDisplay(Display.BLOCK);
		if (placeName.equals(PlaceTokens.FOLDERS) || placeName.equals(PlaceTokens.EDIT_FOLDERS) || placeName.equals(PlaceTokens.SHELF)){
			goorulandingFooterContainer.getElement().getStyle().setMargin(304, Unit.PX);
			goorulandingFooterContainer.getElement().getStyle().setMarginBottom(4, Unit.PX);
			goorulandingFooterContainer.getElement().getStyle().setTop(75, Unit.PX);
			goorulandingFooterContainer.getElement().getStyle().setMarginLeft(252, Unit.PX);
			goorulandingFooterContainer.getElement().getStyle().setPosition(Position.RELATIVE);
		} else if(placeName.equals(PlaceTokens.PROFILE_PAGE)) {
			goorulandingFooterContainer.getElement().getStyle().setDisplay(Display.NONE);
		}
	}

	//For setting the width of the footer from PPP page
	public void setFooterWidth() {
		innerFooterDiv.setWidth("860px");
	}

	@UiHandler("supportAnr")
	public void onClickSupport(ClickEvent event){
		MixpanelUtil.mixpanelEvent("Footer_Click_Support");
	}
}
