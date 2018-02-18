package org.ednovo.gooru.client.mvp.library.partner.tical;

import org.ednovo.gooru.application.client.AppPlaceKeeper;
import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BasePlacePresenter;
import org.ednovo.gooru.client.mvp.authentication.SignUpPresenter;
import org.ednovo.gooru.client.mvp.home.AlmostDoneUc;
import org.ednovo.gooru.client.mvp.home.register.UserRegistrationPresenter;
import org.ednovo.gooru.client.mvp.library.partner.PartnerLibraryPresenter;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;

import com.google.gwt.user.client.Window;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.View;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.annotations.UseGatekeeper;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;

public class TicalLibraryPresenter extends BasePlacePresenter<IsTicalLibraryView, TicalLibraryPresenter.IsTicalLibraryProxy> implements TicalLibraryUiHandlers {

	
	private PartnerLibraryPresenter partnerLibraryPresenter;
	
	private static final String CALLBACK = "callback";
	
	SignUpPresenter signUpViewPresenter = null;

	@ProxyCodeSplit
	@NameToken(PlaceTokens.TICAL)
	@UseGatekeeper(AppPlaceKeeper.class)
	public interface IsTicalLibraryProxy extends ProxyPlace<TicalLibraryPresenter> {
	}
	
	/**
	 * Class constructor
	 * @param userRegistrationPresenter instance of {@link UserRegistrationPresenter}
	 * @param view {@link View}
	 * @param proxy {@link Proxy}
	 */
	@Inject
	public TicalLibraryPresenter(IsTicalLibraryView view, IsTicalLibraryProxy proxy, PartnerLibraryPresenter partnerLibraryPresenter, SignUpPresenter signUpViewPresenter) {
		super(view, proxy);
		getView().setUiHandlers(this);
		this.partnerLibraryPresenter = partnerLibraryPresenter;
		this.signUpViewPresenter = signUpViewPresenter;
	}
	
	@Override
	public void onBind() {
		super.onBind();
	}
	
	@Override
	public void onReveal() {
		super.onReveal();
		Window.scrollTo(0, 0);
	}
	
	@Override
	public void onReset() {
		super.onReset();
	}
	@Override
	public void onHide() {
		super.onHide();
		AppClientFactory.getPlaceManager().resetLibraryEventData(PlaceTokens.TICAL);
	}
	
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		long startTime = System.currentTimeMillis();
		if (AppClientFactory.getPlaceManager().refreshPlace()) {
			clearSlot(TYPE_FOLDERS_SLOT);
			setInSlot(TYPE_FOLDERS_SLOT, partnerLibraryPresenter);
			partnerLibraryPresenter.setPartnerWidget();
		}
		if (getPlaceManager().getRequestParameter(CALLBACK) != null && getPlaceManager().getRequestParameter(CALLBACK).equalsIgnoreCase("signup")) {
		    //To show SignUp (Registration popup)
		    if (AppClientFactory.isAnonymous()){
		        Window.enableScrolling(false);
		        AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		        String type = getPlaceManager().getRequestParameter("type") ;
		        int displayScreen =getPlaceManager().getRequestParameter("type") !=null  ? Integer.parseInt(type) : 1;
		        signUpViewPresenter.displayPopup(displayScreen);
		        addToPopupSlot(signUpViewPresenter);
		    }
		}
		
		int flag = AppClientFactory.getLoggedInUser().getViewFlag();
		final String loginType = AppClientFactory.getLoggedInUser().getLoginType() !=null ? AppClientFactory.getLoggedInUser().getLoginType() : "";
		if(!AppClientFactory.isAnonymous() && flag==0 &&  !loginType.equalsIgnoreCase("Credential")) {
			AlmostDoneUc update = new AlmostDoneUc(AppClientFactory.getLoggedInUser().getEmailId(), AppClientFactory.getLoggedInUser());
			update.setGlassEnabled(true);
			update.show();
			update.center();
		}
	}
	
	@Override
	public String getViewToken() {
		return PlaceTokens.DISCOVER;
	}
}
