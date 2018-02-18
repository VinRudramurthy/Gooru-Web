package org.ednovo.gooru.application.shared.model.library;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class ProfanityDo implements IsSerializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 123216063464914174L;

	private Integer count;
	private boolean found;
	private String text;
	
	public ProfanityDo(){}



	/** 
	 * This method is to get the count
	 */
	public Integer getCount() {
		return count;
	}



	/** 
	 * This method is to set the count
	 */
	public void setCount(Integer count) {
		this.count = count;
	}



	/** 
	 * This method is to get the found
	 */
	public boolean isFound() {
		return found;
	}


	/** 
	 * This method is to set the found
	 */
	public void setFound(boolean found) {
		this.found = found;
	}

	/** 
	 * This method is to get the text
	 */
	public String getText() {
		return text;
	}

	/** 
	 * This method is to set the text
	 */
	public void setText(String text) {
		this.text = text;
	}
}