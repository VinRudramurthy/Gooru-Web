package org.ednovo.gooru.client.mvp.library.district.episd;

import org.ednovo.gooru.application.client.AppPlaceKeeper;
import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BasePlacePresenter;
import org.ednovo.gooru.client.mvp.authentication.SignUpPresenter;
import org.ednovo.gooru.client.mvp.home.AlmostDoneUc;
import org.ednovo.gooru.client.mvp.home.register.UserRegistrationPresenter;
import org.ednovo.gooru.client.mvp.library.district.DistrictPresenter;
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

public class EpisdLibraryPresenter extends BasePlacePresenter<IsEpisdLibraryView,EpisdLibraryPresenter.IsEpisdLibraryProxy> implements EpisdLibraryViewUiHandlers { 
	
	private DistrictPresenter districtPresenter;

	private static final String CALLBACK = "callback";
	
	SignUpPresenter signUpViewPresenter = null;
	
	
	@ProxyCodeSplit
	@NameToken(PlaceTokens.EPISD_LIBRARY)
	@UseGatekeeper(AppPlaceKeeper.class)
	public interface IsEpisdLibraryProxy extends ProxyPlace<EpisdLibraryPresenter> {
	}

	/**
	 * Class constructor
	 * @param userRegistrationPresenter instance of {@link UserRegistrationPresenter}
	 * @param view {@link View}
	 * @param proxy {@link Proxy}
	 */
	@Inject
	public EpisdLibraryPresenter(IsEpisdLibraryView view, IsEpisdLibraryProxy proxy, DistrictPresenter districtPresenter,SignUpPresenter signUpViewPresenter) {
		super(view, proxy);
		getView().setUiHandlers(this);
		this.districtPresenter = districtPresenter;
		this.signUpViewPresenter = signUpViewPresenter;
	}
	
	@Override
	public void onBind() {
		super.onBind();
		Window.scrollTo(0, 0);
	}
	
	@Override
	public void onReveal() {
		super.onReveal();
		Window.scrollTo(0, 0);
	}
	
	@Override
	public void onReset() {
		super.onReset();
		Window.scrollTo(0, 0);
	}
	
	@Override
	public void onHide() {
		super.onHide();
		AppClientFactory.getPlaceManager().resetLibraryEventData(PlaceTokens.EPISD_LIBRARY);
	}
	
	@Override
	public void prepareFromRequest(PlaceRequest request) {
		super.prepareFromRequest(request);
		districtPresenter.setPartnerWidget(PlaceTokens.EPISD_LIBRARY);
		if (AppClientFactory.getPlaceManager().refreshPlace()) {
			clearSlot(TYPE_FOLDERS_SLOT);
			setInSlot(TYPE_FOLDERS_SLOT, districtPresenter);
			
			if (getPlaceManager().getRequestParameter(CALLBACK) != null &&!getPlaceManager().getRequestParameter(CALLBACK).equalsIgnoreCase("signup")) {
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
		return PlaceTokens.EPISD_LIBRARY;
	}
	
}
