package org.ednovo.gooru.application.server.request;

import java.io.Serializable;

import org.ednovo.gooru.application.shared.model.user.ResponseStatusDo;
import org.restlet.ext.json.JsonRepresentation;

/**
* @fileName : JsonResponseRepresentation.java
*
* @description : This class used for to set and get status code.
*
* @version : 1.0
*
* @date:  December, 2013.
*
* @Author: Gooru Team.
*
* @Reviewer: Gooru Team.
*/
public class JsonResponseRepresentation  implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private Integer statusCode=200;

	private JsonRepresentation jsonRepresentation;

	private ResponseStatusDo responseDo=null;

	/**
	 * @return the statusCode
	 */
	public Integer getStatusCode() {
		return statusCode;
	}
	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @return the jsonRepresentation
	 */
	public JsonRepresentation getJsonRepresentation() {
		return jsonRepresentation;
	}
	/**
	 * @param jsonRepresentation the jsonRepresentation to set
	 */
	public void setJsonRepresentation(JsonRepresentation jsonRepresentation) {
		this.jsonRepresentation = jsonRepresentation;
	}
	/**
	 * This method is to get the responseDo
	 */
	public ResponseStatusDo getResponseDo() {
		return responseDo;
	}
	/**
	 * This method is to set the responseDo
	 */
	public void setResponseDo(ResponseStatusDo responseDo) {
		this.responseDo = responseDo;
	}


}
