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

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class LogoutPanelVc extends Composite{

	@UiField
	Anchor logoutAnr, anrSettings, gooruTutorialsAnr;
	@UiField
	Anchor feedbackAnr;

	@UiField
	Anchor supportAnr;

	@UiField
	FlowPanel logPanel;

	@UiField HTMLPanel mainContainer;

	public LogoutPopupVc logoutPopupVc;

	UserDo userdo;

	private static logoutPanelVcUiBinder uiBinder = GWT
			.create(logoutPanelVcUiBinder.class);

	interface logoutPanelVcUiBinder extends UiBinder<Widget, LogoutPanelVc> {
	}

	 private MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Class constructor
	 */
	public LogoutPanelVc() {

		setWidget(uiBinder.createAndBindUi(this));

		anrSettings.setText(i18n.GL0192());
		anrSettings.getElement().setId("lnkSettings");
		anrSettings.getElement().setAttribute("alt",i18n.GL0192());
		anrSettings.getElement().setAttribute("title",i18n.GL0192());
		anrSettings.setHref("#settings");

		supportAnr.setText(i18n.GL0194());
		supportAnr.getElement().setId("lnkSupport");
		supportAnr.getElement().setAttribute("alt",i18n.GL0194());
		supportAnr.getElement().setAttribute("title",i18n.GL0194());
		supportAnr.setHref("http://support.gooru.org/hc/en-us");


		gooruTutorialsAnr.setText(i18n.GL3205());
		gooruTutorialsAnr.getElement().setId("lnkGooruTutorials");
		gooruTutorialsAnr.getElement().setAttribute("alt",i18n.GL3205());
		gooruTutorialsAnr.getElement().setAttribute("title",i18n.GL3205());
		gooruTutorialsAnr.setHref("/tutorials");



		feedbackAnr.setText(i18n.GL0195());
		feedbackAnr.getElement().setId("lnkFeedback");
		feedbackAnr.getElement().setAttribute("alt",i18n.GL0195());
		feedbackAnr.getElement().setAttribute("title",i18n.GL0195());

		logoutAnr.setText(i18n.GL0197());
		logoutAnr.getElement().setId("lnkLogout");
		logoutAnr.getElement().setAttribute("alt",i18n.GL0197());
		logoutAnr.getElement().setAttribute("title",i18n.GL0197());

		logPanel.getElement().setId("fpnlLogPanel");
		triggerUserVoice();

		mainContainer.getElement().setId("headerMainPanel");
	}


	@UiHandler("anrSettings")
	public void onClickSetting(ClickEvent event){
		hide();
	}
	@UiHandler("logoutAnr")
	public void logoutPopupClicked(ClickEvent clickEvent) {
		logPanel.setVisible(false);
		Window.enableScrolling(false);
        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
        logoutPopupVc = new LogoutPopupVc();
        hide();
	}

	@UiHandler("supportAnr")
	public void supportLinkClicked(ClickEvent clickEvent) {
		MixpanelUtil.Click_On_Support();
		hide();
	}

	@UiHandler("classicGooruAnr")
	public void classicGooruClicked(ClickEvent clickEvent) {
		MixpanelUtil.Click_On_ClassicGooru();
		Window.Location.replace(AppClientFactory.getLoggedInUser().getSettings().getClassicEndPoint());
		hide();
	}

	public void show(){
		logPanel.setVisible(true);
	}
	public void hide(){
		logPanel.setVisible(false);

	}
	public boolean isShowing(){
		return logPanel.isVisible();
	}
	/**
	 * Initialise user voice feedback popup
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("feedbackAnr")
	public void feedbackPopupClick(ClickEvent clickEvent) {
		MixpanelUtil.Click_On_FeedBack();
		triggerUserVoiceFeedback();
		hide();
	}

	protected final native void triggerUserVoice() /*-{

		UserVoice = window.UserVoice || [];
		$wnd.UserVoice.push(['showTab', 'classic_widget', { mode: 'feedback', primary_color: '#cc6d00', link_color: '#007dbf', forum_id: 192782, tab_label: 'Feedback', tab_color: '#4e9746', tab_position: 'bottom-right', tab_inverted: false }]);

	}-*/;
	protected final native void triggerUserVoiceFeedback() /*-{
    var my_options = {
        mode: 'feedback',
        primary_color: '#cc6d00',
        link_color: '#007dbf',
        default_mode: 'support',
        forum_id: 34190,
        tab_label: 'Feedback & Support',
        tab_color: '#cc6d00',
        tab_position: 'middle-right',
        tab_inverted: false
    };
    $wnd.UserVoice.push(['showLightbox', 'classic_widget', my_options]);
}-*/;
}
