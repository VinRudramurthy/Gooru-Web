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
package org.ednovo.gooru.application.shared.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Search Team
 * 
 */
public class GwtException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8131503840164251388L;

	private List<String> errors;
	
	private int statusCode;
	
	private String errorMessage;

	public GwtException() {
		errors = new ArrayList<String>();
	}

	public GwtException(String message) {
		super(message);
	}
	
	public GwtException(int statusCode, String errorMessage) {
		this.statusCode=statusCode;
		this.errorMessage=errorMessage;
	}

	public void addError(String error) {
		errors.add(error);
	}

	public void addErrors(List<String> errors) {
		this.errors.addAll(errors);
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public void clearErrors() {
		errors.clear();
	}

	@Override
	public String getMessage() {
		if (errors != null && errors.size() > 0) {
			return errors.get(0);
		} else {
			return super.getMessage();
		}
	}
}
