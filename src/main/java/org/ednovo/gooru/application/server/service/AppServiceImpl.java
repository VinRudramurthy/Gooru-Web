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
package org.ednovo.gooru.application.server.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.AppService;
import org.ednovo.gooru.application.server.annotation.ServiceURL;
import org.ednovo.gooru.application.server.request.JsonResponseRepresentation;
import org.ednovo.gooru.application.server.request.ServiceProcessor;
import org.ednovo.gooru.application.server.request.UrlToken;
import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.application.shared.model.user.V2UserDo;
import org.ednovo.gooru.shared.util.GooruConstants;
import org.ednovo.gooru.shared.util.StringUtil;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


/**
 * @author Search Team
 *
 */
@Service("appService")
@ServiceURL("/appService")
public class AppServiceImpl extends BaseServiceImpl implements AppService {

	/**
	 *
	 */
	private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

	private static final long serialVersionUID = -6736852011457993775L;

	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";


	@Override
	public UserDo getLoggedInUser() {
		UserDo user = getLoggedInUserData();

		if (user != null && !GOORU_ANONYMOUS.equals(user.getGooruUId())) {
			String userUid = user.getGooruUId();
			user = getUserInfo(userUid);
			user.setToken(getLoggedInSessionToken());
			user.setDateOfBirth(getLoggedInDateOfBirth());
		}
		setUserFilterProperties(user);
		return user;
	}


	@Override
	public UserDo signout() {return null;}


	/// V2 Apis
	@Override
	public UserDo v2Signin(String userName, String password) {
		JSONObject postData = new JSONObject();
		UserDo user = null;
		V2UserDo v2UserDo = null;
		JsonRepresentation jsonRep = null;

		String decryptedPwd = StringUtil.getDecryptedData(password);
		String content = null;
		try {
			postData.put(USERNAME, userName);
			postData.put(PASSWORD, decryptedPwd);

			String partialUrl = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_SIGNIN);
			String url = AddQueryParameter.constructQueryParams(partialUrl,GooruConstants.APIKEY,"");
			JsonResponseRepresentation jsonResponseRep = ServiceProcessor.post(url, getRestUsername(), getRestPassword(), postData.toString());
			jsonRep =jsonResponseRep.getJsonRepresentation();

			if (jsonResponseRep.getStatusCode()==200){
				content = jsonRep.getText();
				if (content.contains("{")) {
					v2UserDo = JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), V2UserDo.class);
					user = v2UserDo.getUser();
					user.setToken(v2UserDo.getToken());
					user.setStatusCode(jsonResponseRep.getStatusCode());
					user.setDateOfBirth(v2UserDo.getDateOfBirth());
					user.setAccountCreatedType(user.getAccountCreatedType());

					setUserFilterProperties(user);
					deleteLoggedInInfo();
					setLoggedInInfo(user.getToken(), user.getGooruUId(), user.getEmailId(),user.getDateOfBirth());
					AppClientFactory.setLoggedInUser(user);
					return user;
				}
			}else {
				user = new UserDo();
				user.setStatusCode(jsonResponseRep.getStatusCode());
				user.setResponseDo(jsonResponseRep.getResponseDo());
				return user;
			}
		} catch (Exception e) {
			logger.error("Exception::", e);
			throw new GwtException(e.getMessage());
		}
		throw new GwtException(content);
	}


	@Override
	public UserDo v2Signout() {
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_SIGNOUT);
		ServiceProcessor.post(url, getRestUsername(), getRestPassword());
		deleteLoggedInInfo();
		UserDo user = v2GuestSignIn();
		setUserFilterProperties(user);
		return user;
	}


	@Override
	public String getAnalyticsURL(String type, String id) {

		String analyticsUrl = getAnalyticsEndPoint() +"dashboard/#/" + type +"/"+id+"?session_token="+getLoggedInSessionToken();
		return analyticsUrl;
	}

	@Override
	public UserDo getUserFilterProperties(){
		UserDo userDo=new UserDo();
		userDo.setSettings(getFilterProperties());
		return userDo;
	}

}
