package org.ednovo.gooru.client.mvp.community;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.mvp.home.ResetPasswordVc;
import org.ednovo.gooru.client.mvp.home.library.LibraryView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @fileName : CommunityView.java
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
public class CommunityView extends BaseViewWithHandlers<CommunityUiHandlers> implements IsCommunityView {

	@UiField HTMLPanel landingpagePanel;

	LibraryView libraryView = null;


	private static LandingPageViewUiBinder uiBinder = GWT.create(LandingPageViewUiBinder.class);

	interface LandingPageViewUiBinder extends UiBinder<Widget, CommunityView> {
	}

	/**
	 * Class constructor
	 */
	public CommunityView() {
		setWidget(uiBinder.createAndBindUi(this));
		libraryView = new LibraryView(PlaceTokens.DISCOVER);
//		libraryView.getCourseTabs().setVisible(false);
		Window.scrollTo(0, 0);
		landingpagePanel.add(libraryView);
		landingpagePanel.getElement().setId("pnlPartnerPanel");
		//landingpagePanel.getElement().getStyle().setMarginTop(39, Unit.PX);
	}

	@Override
	public void loadFeaturedContributors(String callBack, String placeToken) {
		libraryView.loadContributorsPage(callBack,placeToken);
	}
	@Override
	public void resetPassword(final String resetToken) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				new ResetPasswordVc(resetToken);

			}
		});
	}

}
