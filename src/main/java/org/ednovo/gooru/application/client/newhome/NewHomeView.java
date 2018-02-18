/**
 *
 */
package org.ednovo.gooru.application.client.newhome;

import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.client.mvp.home.ResetPasswordVc;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 *
 * @fileName : TestView.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 26-May-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class NewHomeView extends
		BaseViewWithHandlers<NewHomeUiHandlers> implements
		IsNewHomeView, ClickHandler{

	private static NewHomeUiBinder uiBinder = GWT
			.create(NewHomeUiBinder.class);

	interface NewHomeUiBinder extends
			UiBinder<Widget, NewHomeView> {

	}

	@UiField SimplePanel panelBannerImage, panelPreSearch;


	@Inject
	public NewHomeView() {
		setWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void onClick(ClickEvent event) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setInSlot(Object slot, Widget content) {
		if (slot  == NewHomePresenter.BANNER_SLOT){
			panelBannerImage.add(content);
		}else if (slot == NewHomePresenter.PRESEARCH_SLOT){
			panelPreSearch.add(content);
		}
	}

	@Override
	public void resetPassword(final String resetToken) {
		new ResetPasswordVc(resetToken);
	}

	/* UI Handlers */
	
	@Override
	public void onLoad() {
		animate();
	}
	
	public static native void animate() /*-{
		new $wnd.WOW().init();
	}-*/;


}

