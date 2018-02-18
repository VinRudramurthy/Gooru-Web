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
package org.ednovo.gooru.client.mvp.classpages.home;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BasePlacePresenter;
import org.ednovo.gooru.application.client.service.ClasspageServiceAsync;
import org.ednovo.gooru.application.shared.model.content.ClasspageListDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.client.SeoTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.authentication.SignUpPresenter;
import org.ednovo.gooru.client.mvp.classpages.home.ClassHomePresenter.IsClassHomeProxy;
import org.ednovo.gooru.client.mvp.home.AlmostDoneUc;
import org.ednovo.gooru.client.mvp.home.event.HeaderTabType;
import org.ednovo.gooru.client.mvp.home.event.HomeEvent;
import org.ednovo.gooru.client.mvp.search.event.ConfirmStatusPopupEvent;
import org.ednovo.gooru.client.mvp.search.event.SetFooterEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
/**
 *
 * @fileName : ClassHomePresenter.java
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
public class ClassHomePresenter extends BasePlacePresenter<IsClassHomeView, IsClassHomeProxy> implements ClassHomeUiHandlers {


	@Inject
	private ClasspageServiceAsync classpageServiceAsync;

	private SimpleAsyncCallback<CollectionDo> collectionAsyncCallback;

	private static final String CALLBACK = "callback";

	SignUpPresenter signUpViewPresenter = null;

	@ProxyCodeSplit
	@NameToken(PlaceTokens.CLASSHOME)
	public interface IsClassHomeProxy extends ProxyPlace<ClassHomePresenter> {
	}


	@Inject
	public ClassHomePresenter(SignUpPresenter signUpViewPresenter,IsClassHomeView view,
			IsClassHomeProxy proxy) {
		super(view, proxy);
		getView().setUiHandlers(this);
		this.signUpViewPresenter = signUpViewPresenter;

		//AppClientFactory.fireEvent(new HomeEvent(HeaderTabType.TEACH));
	}

	@Override
	public void onBind() {
		super.onBind();
		Window.enableScrolling(true);
		Window.scrollTo(0, 0);
	}
	/**
	 *
	 * @function callBackMethods
	 *
	 * @created_date : 07-Dec-2014
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
	private void callBackMethods(){
		getView().callServiceRequestsToBindData();
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
	protected void onReveal() {
		super.onReveal();
		Window.enableScrolling(true);
		Window.scrollTo(0, 0);
		AppClientFactory.setBrowserWindowTitle(SeoTokens.STUDY_TITLE);
		AppClientFactory.setMetaDataDescription(SeoTokens.HOME_META_DESCRIPTION);
		AppClientFactory.fireEvent(new HomeEvent(HeaderTabType.TEACH));
		AppClientFactory.fireEvent(new SetFooterEvent(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken()));
		//Call Event for Setting Confirm popup
		AppClientFactory.fireEvent(new ConfirmStatusPopupEvent(true));
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
		callBackMethods();
	}

	@Override
	protected void onReset() {
		super.onReset();
		Window.enableScrolling(true);
		Window.scrollTo(0, 0);
	}

	@Override
	public String getViewToken() {

		return PlaceTokens.STUDY;
	}

	public void setcollectionAsyncCallback(SimpleAsyncCallback<CollectionDo> collectionAsyncCallback) {
		this.collectionAsyncCallback = collectionAsyncCallback;
	}

	/**
	 * This method is to get the assignmentListAsyncCallback
	 */
	public SimpleAsyncCallback<CollectionDo> getcollectionAsyncCallback() {
		return collectionAsyncCallback;
	}

	public ClasspageServiceAsync getclasspageServiceAsync() {
		return classpageServiceAsync;
	}

	@Override
	public void gotoStudentsView(String tbvalue) {
		this.getclasspageServiceAsync().v2getClasspageByCode(tbvalue, getcollectionAsyncCallback());

	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpages.home.ClassHomeUiHandlers#getV1TeachStudy(java.lang.String, java.lang.String)
	 */
	@Override
	public void getV1TeachStudy(String limit, String offset) {
		AppClientFactory.getInjector().getClasspageService().v2GetAllClass("10", "0",new SimpleAsyncCallback<ClasspageListDo>() {

			@Override
			public void onSuccess(ClasspageListDo result) {
				getView().setClassVisiblityData(result);
			}
			
		});
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpages.home.ClassHomeUiHandlers#getV2TeachStudy()
	 */
	@Override
	public void getV2TeachStudy() {
		AppClientFactory.getInjector().getClasspageService().v3GetAllClass(new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				
			}

			@Override
			public void onSuccess(Boolean result) {
				getView().setClassesData(result);
			}
			
		});
	}

}
