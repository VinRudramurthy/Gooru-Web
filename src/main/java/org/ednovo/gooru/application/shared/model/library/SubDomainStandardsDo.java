package org.ednovo.gooru.application.shared.model.library;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;
@JsonInclude(Include.NON_NULL)
public class SubDomainStandardsDo implements IsSerializable {

	private static final long serialVersionUID = 2411080367742513414L;
	private Integer codeId;
	private String label;
	private String code;
	private List<SubSubDomainStandardsDo> node;

	public SubDomainStandardsDo(){}

	public Integer getCodeId() {
		return codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<SubSubDomainStandardsDo> getNode() {
		return node;
	}

	public void setNode(List<SubSubDomainStandardsDo> node) {
		this.node = node;
	}




}