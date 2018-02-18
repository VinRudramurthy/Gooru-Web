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
package org.ednovo.gooru.application.shared.model.user;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Search Team
 * 
 */
@JsonInclude(Include.NON_NULL)
public class GoogleToken extends ResponseStatusDo implements Serializable {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6190331896909229201L;
	
	
	private Integer expires_in;
	private String token_type;
	private String id_token;
	private String access_token;
	private String emailId;

	/** 
	 * This method is to get the expires_in
	 */
	public Integer getExpires_in() {
		return expires_in;
	}

	/** 
	 * This method is to set the expires_in
	 */
	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}

	/** 
	 * This method is to get the token_type
	 */
	public String getToken_type() {
		return token_type;
	}

	/** 
	 * This method is to set the token_type
	 */
	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	/** 
	 * This method is to get the id_token
	 */
	public String getId_token() {
		return id_token;
	}

	/** 
	 * This method is to set the id_token
	 */
	public void setId_token(String id_token) {
		this.id_token = id_token;
	}

	/** 
	 * This method is to get the access_token
	 */
	public String getAccess_token() {
		return access_token;
	}

	/** 
	 * This method is to set the access_token
	 */
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public GoogleToken(){}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
}
