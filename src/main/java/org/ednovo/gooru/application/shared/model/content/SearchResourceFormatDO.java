package org.ednovo.gooru.application.shared.model.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;
@JsonInclude(Include.NON_NULL)
public class SearchResourceFormatDO implements IsSerializable {
	
	private static final long serialVersionUID = -6528540769336671670L;
	
	private String value;
	
	public SearchResourceFormatDO(){}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
