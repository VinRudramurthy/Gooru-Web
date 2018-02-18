package org.ednovo.gooru.application.shared.model.code;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class PaginateDo implements IsSerializable
{
	private static final long serialVersionUID = 1L;

	private Integer totalRows;

	public PaginateDo(){

		}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

}
