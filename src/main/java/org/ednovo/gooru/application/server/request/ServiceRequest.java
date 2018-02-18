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
package org.ednovo.gooru.application.server.request;



import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.server.AppSessionHolder;
import org.ednovo.gooru.application.server.service.BaseServiceImpl;
import org.ednovo.gooru.application.shared.exception.ServerDownException;
import org.ednovo.gooru.application.shared.model.user.ResponseStatusDo;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Encoding;
import org.restlet.data.Header;
import org.restlet.data.MediaType;
import org.restlet.data.Preference;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;
import org.restlet.util.Series;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Search Team
 *
 */
public abstract class ServiceRequest extends BaseServiceImpl{

	/**
	 *
	 */
	private static final long serialVersionUID = 1705532252739972796L;

	private static final Logger logger = LoggerFactory.getLogger(ServiceRequest.class);

	private ClientResource clientResource = null;

	private Representation representation = null;

	private static final String UP="Up";

	private static final String DOWN="Down";

	private static final String WARNING="Warning";

	private static final String CURRENT_EVENT="current-event";

	private static final String STATUS="status";

	private static final String NAME="name";

	private static final String ERROR = "Error : ";

	private static final String UNAUTHORIZED = "Unauthorized";

	protected ServiceRequest() {
	}

	public JsonResponseRepresentation execute() {
		try {
			return run();
		} catch (ResourceException exception) {
			JsonResponseRepresentation jsonResponseRepresentation=new JsonResponseRepresentation();
			int statusCode=exception.getStatus().getCode();
			jsonResponseRepresentation.setStatusCode(statusCode);
			if(statusCode==504 || statusCode==502){
				String serverStatus=getApiServerStatus();
				if(serverStatus!=null && serverStatus.equalsIgnoreCase(DOWN)){
					throw new ServerDownException(statusCode,"");
				}else if(serverStatus!=null&&serverStatus.equalsIgnoreCase(WARNING)){
					//throw new ServerDownException(statusCode,"");
				}
			}else{
				if(getClientResource().getResponse()!=null){
					ResponseStatusDo responseDo = parseJsonErrorResponse(getClientResource().getResponse().getEntity());
					jsonResponseRepresentation.setResponseDo(responseDo);
				}
				//throw new GwtException(exception.getStatus().getCode(),message);
			}

			return jsonResponseRepresentation;
		} catch (Exception exception) {
			logger.error(ERROR, exception);
			throw new RuntimeException(exception.getMessage());
		} finally {
			releaseClientResources();
		}
	}
	/**
	 *
	 * @function parseJsonErrorResponse
	 *
	 * @created_date : 22-Jan-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param errorRepresentation
	 * @parm(s) : @return
	 *
	 * @return : ResponseStatusDo
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public ResponseStatusDo parseJsonErrorResponse(Representation errorRepresentation){
		String messageString=null;

		Integer code;
		String message;
		String errorCode;
		String errorMessage;
		String status;


		ResponseStatusDo responseDo = new ResponseStatusDo();
		try{
			/**
			 *  Taking values from response header to check authorized user or not. Implemented to differentiate from blocked user or authentication issue.
			 */
			Series<Header> responseHeaders=(Series<Header>)this.clientResource.getResponseAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);
//			Series<org.restlet.engine.header.Header> responseHeaders=(Series<Header>)this.clientResource.getResponseAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);

			if(responseHeaders!=null){

				if(responseHeaders.getValues("Unauthorized")!=null){
					messageString = responseHeaders.getValues("Unauthorized");
				}else{
					messageString = errorRepresentation.getText();
				}
				JsonRepresentation jsonRepresentation=new JsonRepresentation(messageString);
				JSONObject errorObject= jsonRepresentation.getJsonObject();
				if(errorObject!=null){
					status = errorObject.isNull("status") ? null : errorObject.getString("status");
					code = errorObject.isNull("code") ? 0 : Integer.valueOf(errorObject.getString("code"));
					errorCode = errorObject.isNull("errorCode") ? null : errorObject.getString("errorCode");
					errorMessage = errorObject.isNull("errorMessage") ? null : errorObject.getString("errorMessage");


					responseDo.setCode(code);
					responseDo.setStatus(status);
					responseDo.setErrorCode(errorCode);
					responseDo.setErrorMessage(errorMessage);
				}

			}
			return responseDo;
		}catch(Exception exception){
			logger.error(ERROR, exception);
			return responseDo;
		}

	}

	protected String getApiServerStatus(){
		String serverStatus=null;
		JsonResponseRepresentation jsonResponseRep = ServiceProcessor.get(UrlToken.SERVER_STATUS_URL.getUrl());
		if(jsonResponseRep!=null){
			try {
				JSONObject serverStatusJsonObject=jsonResponseRep.getJsonRepresentation().getJsonObject();
				if (serverStatusJsonObject != null) {
					JSONObject currentEventJsonObject = serverStatusJsonObject
							.isNull(CURRENT_EVENT) ? null
							: serverStatusJsonObject
									.getJSONObject(CURRENT_EVENT);
					if (currentEventJsonObject != null) {
						JSONObject statusJsonObject = currentEventJsonObject
								.isNull(STATUS) ? null : currentEventJsonObject
								.getJSONObject(STATUS);
						if (statusJsonObject != null) {
							serverStatus = statusJsonObject.isNull(NAME) ? null
									: statusJsonObject.getString(NAME);
						}
					}
				}
			} catch (JSONException e) {
				logger.error("Exception::", e);
			}
		}
		return serverStatus;
	}

	public abstract JsonResponseRepresentation run() throws Exception;

	public StringRepresentation executeString() {
		try {
			return runString();
		} catch (ResourceException exception) {
			logger.error(ERROR, exception);
			//throw new RuntimeException(exception.getMessage());
			return new StringRepresentation("");
		} catch (Exception exception) {
			logger.error(ERROR, exception);
			throw new RuntimeException(exception.getMessage());
		} finally {
			releaseClientResources();
		}
	}

	public StringRepresentation runString() throws Exception{
		return new StringRepresentation("");
	}




	protected void releaseClientResources() {
		try {
			if (clientResource != null) {
				clientResource.release();
			}
			if (representation != null) {
				representation.release();
			}
			clientResource = null;
			representation = null;
		} catch (Exception e) {
			logger.error("Exception::", e);
		}
	}

	public ClientResource getClientResource() {
	   return clientResource;
	}

	public void setClientResource(ClientResource clientResource) {
		this.clientResource = clientResource;
		setUserAgent();
	}

	public void setUserAgent(){
		try{
			String userAgentValue=AppSessionHolder.getInstance()!=null&&AppSessionHolder.getInstance().getRequest()!=null?AppSessionHolder.getInstance().getRequest().getHeader(HeaderConstants.HEADER_USER_AGENT):"";
			if(clientResource!=null){
				clientResource.getClientInfo().setAgent(userAgentValue);
			}
		}catch(Exception e){
			logger.error("Exception::", e);
		}
	}

	public void setMediaType(MediaType mediaType){
		if(clientResource!=null){
			List<Preference<MediaType>> mediaTypes=new ArrayList<Preference<MediaType>>();
			Preference<MediaType> contentType=new Preference<MediaType>();
			contentType.setMetadata(mediaType);
			mediaTypes.add(contentType);
			clientResource.getClientInfo().setAcceptedMediaTypes(mediaTypes);
		}
	}

	/**
	 * Sets the encoding type.
	 *
	 * @param encodingType {@link Encoding}
	 */
	public void setEncodings(Encoding encodingType){
		if(clientResource!=null){
			List<Preference<Encoding>> acceptedEncodings = new ArrayList<Preference<Encoding>>();
			acceptedEncodings.add(new Preference<Encoding>(encodingType));
			clientResource.getClientInfo().setAcceptedEncodings(acceptedEncodings);
		}

	}
	/**
	 *
	 * @function setCustomHttpHeader
	 *
	 * @created_date : 15-Apr-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param headerValues
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void setCustomHttpHeader(Map<String, String> headerValues) {
		 //Setting sessionToken in Headers
		Series<Header> headers = (Series<Header>)this.clientResource.getRequestAttributes().get(HeaderConstants.ATTRIBUTE_HEADERS);

		if (headers == null) {
			headers = new Series<Header>(Header.class);
			this.clientResource.getRequestAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS, headers);
		}

		Iterator entries = headerValues.entrySet().iterator();
		while (entries.hasNext()) {
		    Map.Entry entry = (Map.Entry) entries.next();
		    String key = (String)entry.getKey();
		    String value = (String)entry.getValue();
		    headers.add(key, value);

		}
		this.clientResource.getRequestAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS, headers);
	}




	public Representation getRepresentation() {
		return representation;
	}

	public void setRepresentation(Representation representation) {
		this.representation = representation;
	}

	public static Logger getLogger() {
		return logger;
	}

	public void setBody() {
		throw new RuntimeException("Not implemented");
	}
}
