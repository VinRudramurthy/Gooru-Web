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
/**
 *
 */
package org.ednovo.gooru.application.client.gin;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.application.shared.model.user.UserRoleDo.UserRoleType;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.MetaElement;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.Cookies;
import com.google.inject.Singleton;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
/**
 *
 * @fileName : AppClientFactory.java
 *
 * @description : This class gains access to object needed through your application.
 *
 *
 * @version : 1.0
 *
 * @date: 26-Dec-2013
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
@Singleton
public class AppClientFactory implements ClientConstants {

	public static UserDo loggedInUser;

	private static Logger clientLogger = Logger.getLogger("");

	private UserDo emailId;

	private static AppClientFactory factory;

	private AppInjector appGinjector;

	private static  boolean userflag = true ;

	private static  boolean enableScroll ;

	private static String protocol;

	private static String isClientLoggersEnabled;

	/**
	 * Class constructor
	 */
	public AppClientFactory() {
		AppClientFactory.factory = this;
	}
	/**
	 * @function getEmailId
	 *
	 * @created_date : 26-Dec-2013
	 *
	 * @description: This method will return the emailId of the logged in user.
	 *
	 * @parm(s) : @return
	 *
	 * @return : UserDo
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public static UserDo getEmailId() {
		return getClientFactory().emailId;
	}
	/**
	 *
	 * @function setEmailId
	 *
	 * @created_date : 26-Dec-2013
	 *
	 * @description :This method is used to set the user emailId.
	 *
	 * @parm(s) : @param emailId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public static void setEmailId(UserDo emailId) {
		getClientFactory().emailId = emailId;
	}
	/**
	 * Gets appclient factory.
	 * @return factory {@link AppClientFactory}
	 */
	private static AppClientFactory getClientFactory() {
		return factory;
	}
	/**
	 * Gets place manager factory.
	 * @return placemanger {@link IsPlaceManager}
	 */
	public static IsPlaceManager getPlaceManager() {
		return getInjector().getPlaceManager();
	}
	/**
	 * Gets EventBus.
	 * @return EventBus {@link EventBus}
	 */
	public static EventBus getEventBus() {
		return getInjector().getEventBus();
	}
	/**
	 * Resets the place request. ie.. sets the current place request.
	 */
	public static void resetPlace() {
		getPlaceManager().revealPlace(getPlaceManager().getCurrentPlaceRequest());
	}
	/**
	 *
	 * @function getLoggedInUser
	 *
	 * @created_date : 26-Dec-2013
	 *
	 * @description :This method is used to get the logged in user.
	 *
	 * @parm(s) : @return
	 *
	 * @return : UserDo
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public static UserDo getLoggedInUser() {
		return getClientFactory().loggedInUser;
	}
	/**
	 *
	 * @function setLoggedInUser
	 *
	 * @created_date : 26-Dec-2013
	 *
	 * @description :This method is used to set the logged in user details.
	 *
	 *
	 * @parm(s) : @param loggedInUser
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public static void setLoggedInUser(UserDo loggedInUser) {
		if (loggedInUser != null) {
			getClientFactory().loggedInUser = loggedInUser;
		}
	}
	/**
	 * Sets AppGinjector
	 * @param appGinjector {@link AppGinjector}
	 */
	public static void setAppGinjector(AppInjector appGinjector) {
		getClientFactory().appGinjector = appGinjector;
	}

	/**
	 * Gets AppGinjector
	 * @return {@link AppGinjector}
	 */
	public static AppInjector getInjector() {
		return getClientFactory().appGinjector;
	}
	/**
	 * While firing the event this is called.
	 * @param event
	 */
	public static void fireEvent(GwtEvent<?> event) {
		getEventBus().fireEvent(event);
	}
	/**
	 * Gets the current place token.
	 * @return {@link String}
	 */
	public static String getCurrentPlaceToken() {
		return getPlaceManager().getCurrentPlaceRequest().getNameToken();
	}
	/**
	 *
	 * @function isContentAdmin
	 *
	 * @created_date : 26-Dec-2013
	 *
	 * @description :This method is used to check is that logged in user is content admin or not.
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : boolean
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public static boolean isContentAdmin() {
		UserDo user = getLoggedInUser();
		if (user != null && user.getUserRoleSetString().contains(UserRoleType.CONTENT_ADMIN.getType())) {
			return true;
		}
		return false;
	}
	/**
	 *
	 * @function isAnonymous
	 *
	 * @created_date : 26-Dec-2013
	 *
	 * @description :This method is used to check the user is logged in or not .
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : boolean
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public static boolean isAnonymous() {
//		return getClientFactory().loggedInUser.getGooruUId()!=null?getClientFactory().loggedInUser.getGooruUId().equals(GOORU_ANONYMOUS):false;
		return getClientFactory().loggedInUser != null && getClientFactory().loggedInUser.getGooruUId() != null ? getClientFactory().loggedInUser.getGooruUId().equals(GOORU_ANONYMOUS) : true;
	}
	/**
	 *
	 * @function getGooruUid
	 *
	 * @created_date : 26-Dec-2013
	 *
	 * @description :This method is used to get the GooruUID of a logged in user.
	 *
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public static String getGooruUid(){
		return getClientFactory().loggedInUser.getGooruUId();
	}
	/**
	 *
	 * @function getLoginSessionToken
	 *
	 * @created_date : 26-Dec-2013
	 *
	 * @description :This method is used to get the logged in user session token.
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public static String getLoginSessionToken(){
		return getClientFactory().loggedInUser.getToken();
	}
	/**
	 *
	 * @function setBrowserWindowTitle
	 *
	 * @created_date : 26-Dec-2013
	 *
	 * @description :This method is used to set the title of the browser window.
	 *
	 *
	 * @parm(s) : @param newTitle
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public static void setBrowserWindowTitle(String newTitle) {
		if (Document.get() != null) {
			Document.get().setTitle(newTitle);
		}
	}
	/**
	 *
	 * @function getUserStatus
	 *
	 * @created_date : 26-Dec-2013
	 *
	 * @description : This method is used to get the current user stauts.
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	public static String getUserStatus() {
		return Cookies.getCookie(GOORU_ACTIVE_USER);
	}
	/**
	 *
	 * @function setMetaDataDescription
	 *
	 * @created_date : 26-Dec-2013
	 *
	 * @description :This method is used to meta data information.
	 *
	 *
	 * @parm(s) : @param newDescription
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 */
	public static void setMetaDataDescription(String newDescription) {
		NodeList<Element> tags = Document.get().getElementsByTagName("meta");
		for (int i = 0; i < tags.getLength(); i++) {
			MetaElement metaTag = ((MetaElement) tags.getItem(i));
			if (metaTag.getName().equals("description")) {
				metaTag.setContent(newDescription);
				metaTag.setId("gooru-seo-meta-content");
			}
			if (metaTag.getName().equals("author")) {
				metaTag.setContent("");
				metaTag.setId("");
			}
		}
	}
	/**
	 * Gets Logged in user flag.
	 * @return {@link boolean}
	 */
	public static boolean isUserflag() {
		return userflag;
	}
	/**
	 * Set Logged in user flag.
	 * @return {@link void}
	 */
	public static void setUserflag(boolean userflag) {
		AppClientFactory.userflag = userflag;
	}
	/**
	 * It will check the scroll enabled or not .
	 * @return {@link boolean}
	 */
	public static boolean isEnableScroll() {
		return enableScroll;
	}
	/**
	 * Enabling the scroll.
	 * @return {@link void}
	 */
	public static void setEnableScroll(boolean enableScroll) {
		AppClientFactory.enableScroll = enableScroll;
	}
	/**
	 * To get the protocol.
	 * @return {@link String}
	 */
	public static String getProtocol() {
		return protocol;
	}
	/**
	 * To set the protocol.
	 * @return {@link String}
	 */
	public static void setProtocol(String protocol) {
		AppClientFactory.protocol = protocol;
	}
	/**
	 * To set the Previous Place Request.
	 * @return {@link String}
	 */
	public static void setPreviousPlaceRequest(PlaceRequest previousRequest) {
		getInjector().getPlaceManager().setPreviousRequest(previousRequest);
	}


	/**
	 * Logging Info on client side.
	 *
	 * @param msg {@link String}
	 */
	public static void printInfoLogger(String msg) {
		if(TRUE.equals(isClientLoggersEnabled())){
			clientLogger.log(Level.INFO, msg);
		}
	}


	/**
	 * Logging Error on client side.
	 *
	 * @param msg {@link String}
	 */
	public static void printSevereLogger(String msg) {
		if(TRUE.equals(isClientLoggersEnabled())){
			clientLogger.log(Level.SEVERE, msg);
		}
	}

	/**
	 * @return the isClientLoggersEnabled
	 */
	public static String isClientLoggersEnabled() {
		return isClientLoggersEnabled;
	}
	/**
	 * @param isClientLoggersEnabled the isClientLoggersEnabled to set
	 */
	public static void setClientLoggersEnabled(String isClientLoggersEnabled) {
		AppClientFactory.isClientLoggersEnabled = isClientLoggersEnabled;
	}

	public static Logger getClientLogger(){
		return clientLogger;
	}
}


