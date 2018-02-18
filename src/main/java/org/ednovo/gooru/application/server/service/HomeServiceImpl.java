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

import java.util.List;

import org.ednovo.gooru.application.client.service.HomeService;
import org.ednovo.gooru.application.server.annotation.ServiceURL;
import org.ednovo.gooru.application.server.deserializer.FeaturedContentDeSerializer;
import org.ednovo.gooru.application.server.request.JsonResponseRepresentation;
import org.ednovo.gooru.application.server.request.ServiceProcessor;
import org.ednovo.gooru.application.server.request.UrlToken;
import org.ednovo.gooru.application.server.serializer.JsonDeserializer;
import org.ednovo.gooru.application.shared.exception.GwtException;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.featured.FeaturedCollectionContentDo;
import org.json.JSONException;
import org.restlet.data.Form;
import org.restlet.ext.json.JsonRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gwt.core.shared.GWT;

@Service("homeService")
@ServiceURL("/homeService")
public class HomeServiceImpl extends BaseServiceImpl implements HomeService {

private static  Logger logger =LoggerFactory.getLogger(HomeServiceImpl.class);

	@Autowired
	FeaturedContentDeSerializer featuredContentDeSerializer;

	private static final long serialVersionUID = -8562571170804242471L;

	@Override
	public List<FeaturedCollectionContentDo> getFeaturedThemeCollection(String themeType) {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(), UrlToken.V2_FEATURED_THEME_COLLECTIONS, themeType);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(url, getRestUsername(), getRestPassword());
		jsonRep = jsonResponseRep.getJsonRepresentation();
		return featuredContentDeSerializer.deSerializer(jsonRep);
	}

	@Override
	public void updateUserDetails(String userNameValue, String userRoleValue)throws GwtException {
		JsonRepresentation jsonRep = null;
		String url = UrlGenerator.generateUrl(getRestEndPoint(),UrlToken.UPDATE_USER,getLoggedInUserUid());
		Form form = new Form();
		form.add("username", userNameValue);
		form.add("userrole", userRoleValue);
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.put(url, getRestUsername(), getRestPassword(),form);
		jsonRep = jsonResponseRep.getJsonRepresentation();
	}

	public CollectionDo deserializeCollection(JsonRepresentation jsonRep) {
		if (jsonRep != null && jsonRep.getSize() != -1) {
			try {
				GWT.log("jsonResp:>>"+jsonRep.toString());
				return JsonDeserializer.deserialize(jsonRep.getJsonObject().toString(), CollectionDo.class);
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return new CollectionDo();
	}

	public String whatsNewMosLink(){
		return getWhatsNewMosLink();
	}

	public String whatsNewFibLink(){
		return getWhatsNewFibLink();
	}

	public String mosLink(){
		return getMosLink();
	}

	public String getClientIpAddress(){
		return getIpAddress();
	}

	@Override
	public String getRedirectServerUrl() throws GwtException {

		return getRedirectUrl();
	}
}
