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
package org.ednovo.gooru.application.server.service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ednovo.gooru.application.server.AppSessionHolder;
import org.ednovo.gooru.application.server.request.JsonResponseRepresentation;
import org.ednovo.gooru.application.server.request.ServiceProcessor;
import org.ednovo.gooru.application.server.request.UrlToken;
import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.model.user.FilterSettings;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.application.shared.model.user.V2UserDo;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.PropertiesCache;
import org.ednovo.gooru.shared.util.ResourceImageUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * @author Search Team
 *
 */
public class BaseServiceImpl extends GwtAbstractServiceImpl implements RemoteService,ClientConstants {

	/**
	 *
	 */
	private static final long serialVersionUID = 7437954157376886661L;

	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

	private String headerApiKey = null;

	private String youtubeApiKey = null;

	private String youtubeApiUrl = null;


	private String apiKey = null;

	@Resource(name = "restConstants")
	private Properties restConstants;

	public BaseServiceImpl() {

	}

	@Override
	protected void onBeforeRequestDeserialized(String serializedRequest) {
		super.onBeforeRequestDeserialized(serializedRequest);
		AppSessionHolder.getInstance().setRequest(getThreadLocalRequest());
		AppSessionHolder.getInstance().setResponse(getThreadLocalResponse());
	}

	public static Logger getLogger() {
		return logger;
	}

	public String getProductionSwitchDate(){
		return  getPropertyByKey(PRODUCTION_SWITCH);
	}

	public String getDataApiKey(){
		return  getPropertyByKey(DATA_LOG_API_KEY);
	}

	public String getGoogleSignin(){
		return  getPropertyByKey(GOOGLE_SIGNIN);
	}

	public String getClientSideLoggersStatus(){
		return  getPropertyByKey(ENABLE_CLIENT_LOGGERS);
	}

	public String getDriveGoogle(){
		return  getPropertyByKey(GOOGLE_DRIVE);
	}

	public String getWhatsNewMosLink(){
		return  getPropertyByKey(WHATS_NEW_MOS_LINK);
	}

	public String getWhatsNewFibLink(){
		return  getPropertyByKey(WHATS_NEW_FIB_LINK);
	}

	public String getMosLink(){
		return  getPropertyByKey(MOS_LINK);
	}

	public String getRestEndPoint() {
		String restEndPoint =  getPropertyByKey(REST_ENDPOINT);
		if(getHttpRequest() != null && getHttpRequest().getScheme().equalsIgnoreCase(HTTPS)) {
			restEndPoint = restEndPoint.replaceAll(HTTP, HTTPS);
		}
		return restEndPoint;
	}

	public String getSearchEndPoint() {
		String searchEndPoint =  getPropertyByKey(SEARCH_ENDPOINT);
		if(getHttpRequest() != null && getHttpRequest().getScheme().equalsIgnoreCase(HTTPS)) {
			searchEndPoint = searchEndPoint.replaceAll(HTTP, HTTPS);
		}
		return searchEndPoint;
	}

	public String getRestUsername() {
		return  getPropertyByKey(REST_USERNAME);
	}

	public String getRestPassword() {
		return  getPropertyByKey(REST_PASSWORD);
	}

	public String getSearchUsername() {
		return  getPropertyByKey(SEARCH_USERNAME);
	}

	public String getSearchPassword() {
		return  getPropertyByKey(SEARCH_PASSWORD);
	}

	public String getYouTubeApiUrl(){
		if (youtubeApiUrl == null){
			youtubeApiUrl = getPropertyByKey(YOUTUBE_API_URL);
		}
		return youtubeApiUrl;

	}

	public String getYoutubeApiKey(){
		if (youtubeApiKey == null){
			youtubeApiKey = getPropertyByKey(YOUTUBE_API_KEY);
		}
		return youtubeApiKey;
	}

	public String getApiKey() {
		if (headerApiKey == null){
			headerApiKey = getPropertyByKey(API_KEY);
		}
		return headerApiKey;
	}

	public String getHomeEndPoint() {
		String homeEndPoint =  getPropertyByKey(HOME_ENDPOINT);
		if(getHttpRequest() != null && getHttpRequest().getScheme().equalsIgnoreCase(HTTPS)) {
			homeEndPoint = homeEndPoint.replaceAll(HTTP, HTTPS);
			ResourceImageUtil.protocol=HTTPS;
		}
		return homeEndPoint;
	}

	public String getAnalyticsEndPoint() {
		String analyticsEndPoint =  getPropertyByKey(ANALYTICS_ENDPOINT);
		if(getHttpRequest() != null && getHttpRequest().getScheme().equalsIgnoreCase(HTTPS)) {
			analyticsEndPoint = analyticsEndPoint.replaceAll(HTTP, HTTPS);
			ResourceImageUtil.protocol=HTTPS;
		}
		return analyticsEndPoint;
	}

	public String getHomeEndPointForEmbed() {
		return  getPropertyByKey(HOME_ENDPOINT);
	}

	public String getRestEndPointForEmbed() {
		return  getPropertyByKey(REST_ENDPOINT);
	}

	public String getDocViewerHome() {
		String docViewerHome =  getPropertyByKey(DOCVIEWER_HOME);
		if(getHttpRequest() != null && getHttpRequest().getScheme().equalsIgnoreCase(HTTPS)) {
			docViewerHome = docViewerHome.replaceAll(HTTP, HTTPS);
		}
		return docViewerHome;
	}

	public String getDocViewerPoint() {
		return  getPropertyByKey(DOCVIEWER_CACHE);
	}

	public String getProfileImageUrl() {
		String profileImageUrl =  getPropertyByKey(PROFILE_IMAGE_RESPOSITORY_URL);
		if(getHttpRequest() != null && getHttpRequest().getScheme().equalsIgnoreCase(HTTPS)) {
			profileImageUrl = profileImageUrl.replaceAll(HTTP, HTTPS);
		}
		return profileImageUrl;
	}

	public String getFacebookAppId(){
		return  getPropertyByKey(FACEBOOK_APP_ID);
	}

	public String getFacebookFeedUrl(){
		return  getPropertyByKey(FACEBOOK_FEED_URL);
	}

	public String getTaxonomyPreferences(){
		return  getPropertyByKey(TAXONOMY_PREFERENCES);
	}

	public String getCdnEndPoint() {
		String cdnEndPoint =  getPropertyByKey(CDN_ENDPOINT);
		if(getHttpRequest() != null && getHttpRequest().getScheme().equalsIgnoreCase(HTTPS)) {
			cdnEndPoint = cdnEndPoint.replaceAll(HTTP, HTTPS);
		}
		return cdnEndPoint;
	}

	public String getRedirectUrl(){
		String serverRedirectUrl =  getPropertyByKey(SERVER_REDIRECT_URL);
		return serverRedirectUrl;
	}

	public String getGoogleRestEndPoint(){
		return  getPropertyByKey(GOOGLE_RESTENDPOINT);
	}

	public String getStoriesUrl(){
		return  getPropertyByKey(STORIES_URL);
	}

	public String showStoriesSection(){
		return  getPropertyByKey(SHOW_STORIES);
	}

	public String getCommunityLibaryGooruOid(){
		return  getPropertyByKey(COMMUNITY_LIBRARY_ID);

	}
	protected static Integer stringtoInteger(JSONObject jsonObject, String key) {
		if (jsonObject != null && jsonObject.has(key)) {
			String value = null;
			try {
				value = jsonObject.getString(key);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
			return value != null ? Integer.parseInt(value) : null;
		} else {
			return null;
		}
	}

	protected static Form generateFormFromParamters(Map<String, String> params) {
		Form form = new Form();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			form.add(entry.getKey(), entry.getValue());
		}
		return form;
	}



	protected void setUserFilterProperties(UserDo user) {
		user.setSettings(getFilterProperties());
	}

	protected FilterSettings getFilterProperties() {
		FilterSettings filterProperties = new FilterSettings();
		filterProperties.setRestEndPoint(getRestEndPoint());
		filterProperties.setHomeEndPoint(getHomeEndPoint());
		filterProperties.setDomainName(getDomainName());
		filterProperties.setClassicEndPoint(getClassicEndPoint());
		filterProperties.setDocViewerHome(getDocViewerHome());
		filterProperties.setDocViewerPoint(getDocViewerPoint());
		filterProperties.setApiKeyPoint(getApiKey());
		filterProperties.setGoogleAnalticsAdditionalAccounts(getGoogleAnalticsAdditionalAccounts());
		filterProperties.setGoogleSignin(getGoogleSignin());
		filterProperties.setProfileImageUrl(getProfileImageUrl());
		filterProperties.setCdnEndPoint(getCdnEndPoint());
		filterProperties.setDataApiKey(getDataApiKey());
		filterProperties.setGooruReleaseVersion(getGooruReleaseVersion());
		filterProperties.setFacebookAppId(getFacebookAppId());
		filterProperties.setFacebookFeedUrl(getFacebookFeedUrl());
		filterProperties.setTaxonomyPreferences(getTaxonomyPreferences());
		filterProperties.setAnalyticsEndPoint(getAnalyticsEndPoint());
		filterProperties.setCommunityLibraryGooruOid(getCommunityLibaryGooruOid());
		return filterProperties;
	}

	public String getDomainName() {
		return  getPropertyByKey(DOMAIN_NAME);
	}

	public String getClassicEndPoint() {
		return  getPropertyByKey(CLASSIC_ENDPOINT);
	}

	public String getGoogleAnalticsAdditionalAccounts() {
		return  getPropertyByKey(GOOGLE_ANALTICS_ADDITIONAL_ACCOUNTS);
	}

	public String getGooruReleaseVersion() {
		return  getPropertyByKey(GOORU_RELEASE_VERSION);
	}

	protected String getLoggedInSessionToken() {
		String token = getCookie(GOORU_SESSION_TOKEN);
		if (token == null) {
			token = getHttpRequest() != null ? (String) getHttpRequest().getSession().getAttribute(GOORU_SESSION_TOKEN) : null;
			//Fix for handling when cookie get disabled.
			if (token == null || token.equalsIgnoreCase("null")) {
				UserDo user = v2GuestSignIn();
				token = (user != null) ? user.getToken() : token;
				if (getHttpRequest()!=null){
					getHttpRequest().getSession().setAttribute(GOORU_SESSION_TOKEN, token);
				}
			}
			setLoggedInSessionToken(token);
		}
		return token;
	}

	protected String getLoggedInAccessToken() {
		String token = getCookie(GOORU_ACCESS_TOKEN);
		if (token == null) {
			token = (String) getHttpRequest().getSession().getAttribute(GOORU_ACCESS_TOKEN);
			//Fix for handling when cookie get disabled.
			if (token == null || token.equalsIgnoreCase("null")) {
				getHttpRequest().getSession().setAttribute(GOORU_ACCESS_TOKEN, token);
			}
			setLoggedInAccessToken(token);
		}
		return token;
	}
	protected void setLoggedInAccessToken(String accessToken) {
		Cookie cookie = new Cookie(GOORU_ACCESS_TOKEN, accessToken);
		cookie.setDomain(AppSessionHolder.getInstance().getRequest().getServerName());
		cookie.setPath(COOKIE_PATH);
		if (accessToken != null && accessToken.length() > 0) {
			cookie.setMaxAge(COOKIE_AGE);
		} else {
			cookie.setMaxAge(0);
		}
		getHttpResponse().addCookie(cookie);
		if (accessToken != null) {
			getHttpRequest().getSession().setAttribute(GOORU_ACCESS_TOKEN, accessToken);
		} else {
			getHttpRequest().getSession().removeAttribute(GOORU_ACCESS_TOKEN);
		}
	}
	private String getCookie(String name) {
		Cookie[] cookies = getHttpRequest() != null && getHttpRequest().getCookies() != null ? getHttpRequest().getCookies() : null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	protected String getGuestSessionToken(String restEndPointFromEmbed){
		UserDo user = v2GuestSignInForEmbed(restEndPointFromEmbed);
		String token = (user != null) ? user.getToken() : "";
		return token;
	}
	protected void setLoggedInSessionToken(String sessionToken) {
		if (getHttpRequest() != null){
			Cookie cookie = new Cookie(GOORU_SESSION_TOKEN, sessionToken);
			cookie.setDomain(AppSessionHolder.getInstance().getRequest() != null ? AppSessionHolder.getInstance().getRequest().getServerName() : null);
			cookie.setPath(COOKIE_PATH);
	//		cookie.setHttpOnly(true);
			if (sessionToken != null && sessionToken.length() > 0) {
				cookie.setMaxAge(COOKIE_AGE);
			} else {
				cookie.setMaxAge(0);
			}
			getHttpResponse().addCookie(cookie);

			if (sessionToken != null) {
				getHttpRequest().getSession().setAttribute(GOORU_SESSION_TOKEN, sessionToken);
			} else {
				getHttpRequest().getSession().removeAttribute(GOORU_SESSION_TOKEN);
			}
		}
	}
	/**
	 *
	 * @function getPropertyByKey
	 *
	 * @created_date : 15-Apr-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param keyName
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected String getPropertyByKey(String keyName) {
		return PropertiesCache.getInstance().getProperty(keyName);
	}


	protected void setUserActiveStatus(String userStatus){
		Cookie cookie = new Cookie(GOORU_ACTIVE_USER, userStatus);
		cookie.setDomain(AppSessionHolder.getInstance().getRequest().getServerName());
		cookie.setPath(COOKIE_PATH);
		cookie.setMaxAge(COOKIE_AGE);
		getHttpResponse().addCookie(cookie);
	}

	protected void setUserInActiveStatus(String userStatus){
		Cookie cookie = new Cookie(GOORU_ACTIVE_USER, userStatus);
		cookie.setDomain(AppSessionHolder.getInstance().getRequest().getServerName());
		cookie.setPath(COOKIE_PATH);
		cookie.setMaxAge(COOKIE_AGE);
		getHttpResponse().addCookie(cookie);
	}

	protected void setLoggedInInfo(String sessionToken, String loggedInUserUid, String loggedInEmailId,String dateOfBirth) {
		setLoggedInSessionToken(sessionToken);
		setLoggedInUserUid(loggedInUserUid);
		setLoggedInEmailId(loggedInEmailId);
		setLoggedInDateOfBirth(dateOfBirth);
		setUserActiveStatus("active");
	}


	protected void deleteLoggedInInfo() {
		Cookie[] cookies = getHttpRequest().getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(GOORU_SESSION_TOKEN)) {
					cookie.setMaxAge(0);
					cookie.setPath(COOKIE_PATH);
					getHttpResponse().addCookie(cookie);
				}
				else if(cookie.getName().equals(GOORU_ACTIVE_USER)){
					setUserInActiveStatus("in-active");
				}
			}
		}
		getHttpRequest().getSession().removeAttribute(GOORU_SESSION_TOKEN);
		setLoggedInUserUid(null);
	}
	/**
	 *
	 * @function getLoggedInUserData
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : UserDo
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected UserDo getLoggedInUserData() {
		String sessionSessionToken = (String) getHttpRequest().getSession().getAttribute(GOORU_SESSION_TOKEN);
		String cookieSessionToken = getCookie(GOORU_SESSION_TOKEN);
		String loggedInUserUid = (String) getHttpRequest().getSession().getAttribute(SIGNED_USER_UID);

		boolean sessionTimedOutUser = sessionSessionToken == null && cookieSessionToken != null;
		boolean switchedUser = sessionSessionToken != null && cookieSessionToken != null && !cookieSessionToken.equals(sessionSessionToken);
		boolean inUser = sessionSessionToken != null && cookieSessionToken != null && cookieSessionToken.equals(sessionSessionToken);

		UserDo user = null;
		if (sessionTimedOutUser || switchedUser || inUser) {
			user = v2GetUserInfoByToken(cookieSessionToken);
		} else if (!inUser) {
			user = v2GuestSignIn();
		}
		if (user != null) {
			deleteLoggedInInfo();
			loggedInUserUid = user.getGooruUId();
			setLoggedInInfo(cookieSessionToken == null ? user.getToken() : cookieSessionToken, user.getGooruUId(),getLoggedInEmailId(),getLoggedInDateOfBirth());
		}
		return user;
	}

	/**
	 *
	 * @function getLoggedInUserUid
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected String getLoggedInUserUid() {
		String sessionSessionToken = (String) getHttpRequest().getSession().getAttribute(GOORU_SESSION_TOKEN);
		String cookieSessionToken = getCookie(GOORU_SESSION_TOKEN);
		String loggedInUserUid = (String) getHttpRequest().getSession().getAttribute(SIGNED_USER_UID);

		boolean sessionTimedOutUser = sessionSessionToken == null && cookieSessionToken != null;
		boolean switchedUser = sessionSessionToken != null && cookieSessionToken != null && !cookieSessionToken.equals(sessionSessionToken);
		boolean inUser = sessionSessionToken != null && cookieSessionToken != null && cookieSessionToken.equals(sessionSessionToken);

		UserDo user = null;

		if (sessionTimedOutUser || switchedUser) {
			user = v2GetUserInfoByToken(cookieSessionToken);
		} else if (!inUser) {
			user = v2GuestSignIn();
		}
		if (user != null) {
			deleteLoggedInInfo();
			loggedInUserUid = user.getGooruUId();
			setLoggedInInfo(cookieSessionToken == null ? user.getToken() : cookieSessionToken, user.getGooruUId(),getLoggedInEmailId(),getLoggedInDateOfBirth());
		}
		return loggedInUserUid;
	}
	/**
	 *
	 * @function setLoggedInUserUid
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param loggedInUserUid
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected void setLoggedInUserUid(String loggedInUserUid) {
		if (getHttpRequest()!= null){
			if (loggedInUserUid != null) {
				getHttpRequest().getSession().setAttribute(SIGNED_USER_UID, loggedInUserUid);
			} else {
				getHttpRequest().getSession().removeAttribute(SIGNED_USER_UID);
			}
		}
	}
	/**
	 *
	 * @function getLoggedInEmailId
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected String getLoggedInEmailId() {
		String loggedInEmailId = (String) getHttpRequest().getSession().getAttribute(SIGNED_USER_EMAILID);
		return loggedInEmailId;
	}
	/**
	 *
	 * @function setLoggedInEmailId
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param loggedInEmailId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected void setLoggedInEmailId(String loggedInEmailId) {
		if (getHttpRequest() != null){
			if (loggedInEmailId != null) {
				getHttpRequest().getSession().setAttribute(SIGNED_USER_EMAILID, loggedInEmailId);
			} else {
				getHttpRequest().getSession().removeAttribute(SIGNED_USER_EMAILID);
			}
		}
	}
	/**
	 *
	 * @function getLoggedInDateOfBirth
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected String getLoggedInDateOfBirth() {
		String loggedInDob = (String) getHttpRequest().getSession().getAttribute(SIGNED_USER_DOB);
		return loggedInDob;
	}
	/**
	 *
	 * @function setLoggedInDateOfBirth
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param dateOfBirth
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected void setLoggedInDateOfBirth(String dateOfBirth) {
		if (getHttpRequest()!=null){
			if (dateOfBirth != null) {
				getHttpRequest().getSession().setAttribute(SIGNED_USER_DOB, dateOfBirth);
			} else {
				getHttpRequest().getSession().removeAttribute(SIGNED_USER_DOB);
			}
		}
	}
	/**
	 *
	 * @function isLoggedInUserAnonymous
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : boolean
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected boolean isLoggedInUserAnonymous() {
		return getLoggedInUserUid() == null || getLoggedInUserUid().equals(GOORU_ANONYMOUS);
	}

	/**
	 *
	 * @function getUserInfo
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param userUid
	 * @parm(s) : @return
	 *
	 * @return : UserDo
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected UserDo getUserInfo(String userUid) {
		UserDo userDo = null;
		String sessionToken = getLoggedInSessionToken();
		if (sessionToken != null) {
			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.GET_USER, userUid);
			JsonRepresentation jsonRep = null;
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
			jsonRep =jsonResponseRep.getJsonRepresentation();
			try {
				String data = jsonRep.getJsonObject().getJSONObject("user").toString();
				userDo = JsonDeserializer.deserialize(data, UserDo.class);

			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return userDo;
	}

	/**
	 *
	 * @function getHttpRequest
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : HttpServletRequest
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected HttpServletRequest getHttpRequest() {
		return AppSessionHolder.getInstance().getRequest();
	}
	/**
	 *
	 * @function getHttpResponse
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : HttpServletResponse
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected HttpServletResponse getHttpResponse() {
		return AppSessionHolder.getInstance().getResponse();
	}
	/**
	 *
	 * @function v2GuestSignIn
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : UserDo
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected UserDo v2GuestSignIn() {
		UserDo user = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GUEST_SIGNIN);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword());
		JsonRepresentation jsonRep = null;
		jsonRep =jsonResponseRep.getJsonRepresentation();
		if (jsonResponseRep.getStatusCode() != 200){
			logger.info("jsonResponseRep.getResponseDo().getErrorMessage() : "+jsonResponseRep.getResponseDo().getErrorMessage());
		}
		try {
			logger.info("jsonRep.getText() : "+jsonRep.getText());
			setLoggedInSessionToken(jsonRep.getJsonObject().getString(TOKEN));
			V2UserDo v2UserDo = JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), V2UserDo.class);
			user = v2UserDo.getUser();
			user.setToken(v2UserDo.getToken());
			setLoggedInUserUid(user.getGooruUId());
			setLoggedInEmailId("");
			setLoggedInDateOfBirth("");
		} catch (JSONException e) {
			logger.error("Exception::", e);
		} catch (IOException e) {
			logger.error("Exception::", e);
		}
		return user;
	}
	/**
	 *
	 * @function v2GuestSignInForEmbed
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param restEndPointFromEmbed
	 * @parm(s) : @return
	 *
	 * @return : UserDo
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected UserDo v2GuestSignInForEmbed(String restEndPointFromEmbed){
		UserDo user = null;
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(restEndPointFromEmbed, UrlToken.V2_GUEST_SIGNIN);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword());
		jsonRep =jsonResponseRep.getJsonRepresentation();
		try {
			V2UserDo v2UserDo = JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), V2UserDo.class);
			user = v2UserDo.getUser();
			getLogger().info("v2GuestSignInForEmbed::"+v2UserDo.getUser().getToken());
			getLogger().info("v2GuestSignInForEmbedtoken::"+v2UserDo.getToken());
			user.setToken(v2UserDo.getUser().getToken() != null ? v2UserDo.getUser().getToken() : v2UserDo.getToken());

		} catch (JSONException e) {
			logger.error("Exception::", e);
		}
		return user;
	}

	/**
	 *
	 * @function v2GetUserInfoByToken
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param token
	 * @parm(s) : @return
	 *
	 * @return : UserDo
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	protected UserDo v2GetUserInfoByToken(String token) {
		UserDo userDo = null;
		try {
			String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_GET_USER_BY_SESSIONTOKEN, getLoggedInSessionToken());
			JsonRepresentation jsonRep = null;
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
			jsonRep =jsonResponseRep.getJsonRepresentation();
			if (jsonResponseRep.getResponseDo() != null ){
				getLogger().info("jsonResponseRep.getResponseDo().getErrorCode() : "+jsonResponseRep.getResponseDo().getErrorCode());
			}else{
				String data = jsonRep.getJsonObject().toString();
				userDo = JsonDeserializer.deserialize(data, UserDo.class);
				userDo.setToken(token);
				setUserFilterProperties(userDo);
			}
		} catch (Exception e) {
			getLogger().error(USER_INFO_FAILED_ON_TOKEN + token);
		}
		return userDo;
	}
	/**
	 *
	 * @function getIpAddress
	 *
	 * @created_date : 28-May-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public String getIpAddress(){
		return getHttpRequest().getRemoteAddr();
	}
}
