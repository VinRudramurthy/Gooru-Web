/**
 *
 */
package org.ednovo.gooru.client.uc;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.shelf.collection.CollectionTabTitleVc;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author ibc
 *
 */
public abstract class CollectionAnalyticsUc extends PopupPanel {

	private static CollectionAnalyticsUcUiBinder uiBinder = GWT
			.create(CollectionAnalyticsUcUiBinder.class);

	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface CollectionAnalyticsUcUiBinder extends
			UiBinder<Widget, CollectionAnalyticsUc> {
	}

	/* HTML5 Storage implementation for tab persistance */
	private Storage stockStore = null;

	CollectionTabTitleVc presentTab = null;

	@UiField Label analyticsHeaderLbl,closeButton;

	@UiField FlowPanel analyticsBodyLbl,headerPanel,contentPanel;

	public CollectionAnalyticsUc(final String gooruOid ,String collectionName, CollectionTabTitleVc presentTab) {
		setWidget(uiBinder.createAndBindUi(this));
		if (presentTab!=null){
			this.presentTab = presentTab;
		}
		headerPanel.getElement().setId("fpnlHeaderPanel");
		contentPanel.getElement().setId("fpnlContentPanel");
		analyticsBodyLbl.getElement().setId("fpnlAnalyticsBodyLbl");
		this.getElement().getStyle().setWidth(100, Unit.PCT);
		this.getElement().getStyle().setHeight(100, Unit.PCT);
		this.getElement().getStyle().setPadding(0, Unit.PX);
		this.getElement().getStyle().setZIndex(99999);

		final int height = Window.getClientHeight()-40;
		analyticsHeaderLbl.getElement().setId("lblAnalyticsHeaderLbl");
		analyticsHeaderLbl.setText(i18n.GL0831()+" "+i18n.GL_GRR_Hyphen()+" "+collectionName);
		analyticsHeaderLbl.getElement().setAttribute("alt", i18n.GL0831()+" "+i18n.GL_GRR_Hyphen()+" "+collectionName);
		analyticsHeaderLbl.getElement().setAttribute("title", i18n.GL0831()+" "+i18n.GL_GRR_Hyphen()+" "+collectionName);
		closeButton.getElement().setId("lblCloseButton");
		Window.scrollTo(0, 0);

		AppClientFactory.getInjector().getAppService().getAnalyticsURL("collection", gooruOid, new SimpleAsyncCallback<String>() {

			@Override
			public void onSuccess(String analyticsUrl) {
				Frame resourcePreviewFrame = new Frame(analyticsUrl);
				resourcePreviewFrame.getElement().getStyle().setBorderWidth(0, Unit.PX);
				resourcePreviewFrame.getElement().getStyle().setBorderStyle(BorderStyle.NONE);
				resourcePreviewFrame.getElement().getStyle().setWidth(100, Unit.PCT);
				resourcePreviewFrame.getElement().getStyle().setHeight(height, Unit.PX);
				analyticsBodyLbl.add(resourcePreviewFrame);
			}
		});


     	this.setAutoHideOnHistoryEventsEnabled(true);
     	this.center();
     	this.show();
	}
	/**
	 *
	 * @function clickOnCancelLabel
	 *
	 * @description : To close the popup.
	 *
	 * @parm(s) : @param clickEvent {@link ClickEvent}
	 *
	 */
	@UiHandler("closeButton")
	public void clickOnCancelLabel(ClickEvent clickEvent){
		if (presentTab!=null){
     		setPersistantTabFlag(presentTab.toString());
		}
		setDefaultTab();
		hide();
		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
	}

	/**
	 * Sets the incoming tabFlag into Persistant store
	 *
	 * @param flag
	 *            generated when tabs are being switched
	 */
	public void setPersistantTabFlag(String flag) {

		stockStore = Storage.getLocalStorageIfSupported();
		if (stockStore != null) {
			stockStore.setItem("tabKey", flag);
		}

	}

	public abstract void setDefaultTab();

}
