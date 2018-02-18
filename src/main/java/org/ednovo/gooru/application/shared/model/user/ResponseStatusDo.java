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

import com.google.gwt.user.client.rpc.IsSerializable; 

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * @fileName : ResponseStatusDo.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 22-Jan-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
@JsonInclude(Include.NON_NULL)
public class ResponseStatusDo implements IsSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1024383487509688923L;

	private Integer code;
	private String status;
	
	private String errorCode;
	private String errorMessage;
	
	public ResponseStatusDo(){}
	/** 
	 * This method is to get the code
	 */
	public Integer getCode() {
		return code;
	}
	/** 
	 * This method is to set the code
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	/** 
	 * This method is to get the status
	 */
	public String getStatus() {
		return status;
	}
	/** 
	 * This method is to set the status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 
	 * @function getErrorCode 
	 * 
	 * @created_date : 22-Jan-2015
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
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * 
	 * @function setErrorCode 
	 * 
	 * @created_date : 22-Jan-2015
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param errorCode
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * 
	 * @function getErrorMessage 
	 * 
	 * @created_date : 22-Jan-2015
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
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * 
	 * @function setErrorMessage 
	 * 
	 * @created_date : 22-Jan-2015
	 * 
	 * @description
	 * 
	 * 
	 * @parm(s) : @param errorMessage
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
