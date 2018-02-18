/**
 *
 */
package org.ednovo.gooru.client.uc;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.home.HeaderUc;
import org.ednovo.gooru.client.mvp.home.library.LibraryView;
import org.ednovo.gooru.client.uc.tooltip.DiscoverToolTip;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @fileName : DeviceUc.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 26-Feb-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class DeviceUc extends Composite {

	private static DeviceUcUiBinder uiBinder = GWT
			.create(DeviceUcUiBinder.class);


	@UiField HTMLPanel ipadSectiondiv,androidSectiondiv;
	@UiField HTMLPanel msgPanel,msglinkPanel,gooruPanel,ednovoPanel,appstorePanel;
	@UiField com.google.gwt.user.client.ui.Image closeIpadBtn,closeAndriodBtn;

	SimplePanel wrapperPanel= null;
	HeaderUc headerUc= null;

	@UiField Anchor viewAnchor;

	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface DeviceUcUiBinder extends UiBinder<Widget, DeviceUc> {
	}

	Boolean isIpad = !!Navigator.getUserAgent().matches("(.*)iPad(.*)");
	Boolean isAndriod = !!Navigator.getUserAgent().matches("(.*)Android(.*)");
	Boolean isWinDskp = !!Navigator.getUserAgent().matches("(.*)NT(.*)");

	public DeviceUc(SimplePanel wrapperPanel,HeaderUc headerUc) {
		initWidget(uiBinder.createAndBindUi(this));
		this.headerUc = headerUc;
		this.wrapperPanel = wrapperPanel;

		androidSectiondiv.getElement().setId("pnlAndroidSectiondiv");
		ipadSectiondiv.getElement().setId("pnlIpadSectiondiv");

		msglinkPanel.getElement().setId("pnlMsglinkPanel");
		msgPanel.getElement().setId("pnlMsgPanel");
		closeAndriodBtn.getElement().setId("imgCloseAndriodBtn");

		closeIpadBtn.getElement().setId("imgCloseIpadBtn");
		gooruPanel.getElement().setId("pnlGooruPanel");
		ednovoPanel.getElement().setId("pnlEdnovoPanel");
		appstorePanel.getElement().setId("pnlAppstorePanel");
		viewAnchor.getElement().setId("lnkViewAnchor");

		setUiText();

		if(isAndriod && !StringUtil.IPAD_MESSAGE_Close_Click){
			ipadSectiondiv.setVisible(false);
			androidSectiondiv.setVisible(true);
			androidSectiondiv.getElement().setAttribute("style", "margin-top:-20px;");
			wrapperPanel.getElement().setAttribute("style", "margin-top:0px;");
			headerUc.getElement().getFirstChildElement().setAttribute("style", "position:relative;");
		}
	}

	@UiHandler("closeIpadBtn")
	public void onIpadCloseClick(ClickEvent clickEvent){
		  ipadSectiondiv.setVisible(false);
		  StringUtil.IPAD_MESSAGE_Close_Click = true;
		  androidSectiondiv.setVisible(false);
		  wrapperPanel.getElement().setAttribute("style", "margin-top:36px;");
		  headerUc.getElement().getFirstChildElement().setAttribute("style", "position:fixed;");
		  LibraryView.onClosingAndriodorIpaddiv();
		  DiscoverToolTip.onclickOfAndriodorIpadcloseDiv();
	}
	@UiHandler("closeAndriodBtn")
	public void onAndriodCloseClick(ClickEvent clickEvent){
		  ipadSectiondiv.setVisible(false);
		  StringUtil.IPAD_MESSAGE_Close_Click = true;
		  androidSectiondiv.setVisible(false);
		  wrapperPanel.getElement().setAttribute("style", "margin-top:36px;");
		  headerUc.getElement().getFirstChildElement().setAttribute("style", "position:fixed;");
		  LibraryView.onClosingAndriodorIpaddiv();
		  DiscoverToolTip.onclickOfAndriodorIpadcloseDiv();
	}

	/**
	 *
	 * @function setUiText
	 *
	 * @created_date : 26-Feb-2015
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
	public void setUiText(){
		  msgPanel.getElement().setInnerHTML(i18n.GL1983());
		  msgPanel.getElement().setAttribute("alt", i18n.GL1983());
		  msgPanel.getElement().setAttribute("title", i18n.GL1983());
		  msglinkPanel.getElement().setInnerHTML(i18n.GL1984());
		  msglinkPanel.getElement().setAttribute("alt", i18n.GL1984());
		  msglinkPanel.getElement().setAttribute("title",i18n.GL1984());
		  gooruPanel.getElement().setInnerHTML(i18n.GL0733());
		  gooruPanel.getElement().setAttribute("alt", i18n.GL0733());
		  gooruPanel.getElement().setAttribute("title", i18n.GL0733());
		  ednovoPanel.getElement().setInnerHTML(i18n.GL1985());
		  ednovoPanel.getElement().setAttribute("alt", i18n.GL1985());
		  ednovoPanel.getElement().setAttribute("title", i18n.GL1985());
		  appstorePanel.getElement().setInnerHTML(i18n.GL1986());
		  appstorePanel.getElement().setAttribute("alt", i18n.GL1986());
		  appstorePanel.getElement().setAttribute("title", i18n.GL1986());
		  viewAnchor.setText(i18n.GL1428());
		  viewAnchor.getElement().setAttribute("alt", i18n.GL1428());
		  viewAnchor.getElement().setAttribute("title", i18n.GL1428());

	}
}
