package org.ednovo.gooru.client.mvp.home;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.mvp.faq.CopyRightPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsAndPolicyVc;
import org.ednovo.gooru.client.mvp.faq.TermsOfUse;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LogoutFooter extends Composite {

	@UiField Anchor achTerms, achPrivacy,achCopyright;
	
	private static LogoutFooterUiBinder uiBinder = GWT
			.create(LogoutFooterUiBinder.class);

	interface LogoutFooterUiBinder extends UiBinder<Widget, LogoutFooter> {
	}

	public LogoutFooter() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("achTerms")
	public void onClickTermsLink(ClickEvent envent){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));

				TermsOfUse termsOfUse = new TermsOfUse(){

					@Override
					public void openParentPopup() {


					}

				};
				termsOfUse.show();
				termsOfUse.center();

			}
		});
	}
	@UiHandler("achPrivacy")
	public void onClickPrivacyLink(ClickEvent envent){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
				TermsAndPolicyVc termsAndPolicyVc = new TermsAndPolicyVc(false) {

					@Override
					public void openParentPopup() {

					}
				};
				termsAndPolicyVc.show();
				termsAndPolicyVc.center();

			}
		});
	}

	@UiHandler("achCopyright")
	public void onClickCopyrightLink(ClickEvent envent){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));

				CopyRightPolicyVc copyRightPolicy = new CopyRightPolicyVc() {

					@Override
					public void openParentPopup() {
						//No need to set.
					}
				};
				copyRightPolicy.center();
				copyRightPolicy.show();
			}
		});
	}
}