package org.ednovo.gooru.application.shared.model.content;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class ContentReportDo implements IsSerializable
{
	
	private static final long serialVersionUID = 1L;
	private String assocGooruOid;
	private String freeText=null;
	private String deleteContentGooruOid=null;
	private ArrayList<String> contentReportList;
	
	public ContentReportDo(){}
	
	public String getAssocGooruOid() {
		return assocGooruOid;
	}
	public void setAssocGooruOid(String assocGooruOid) {
		this.assocGooruOid = assocGooruOid;
	}
	public String getFreeText() {
		return freeText;
	}
	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}
	public ArrayList<String> getContentReportList() {
		return contentReportList;
	}
	public void setContentReportList(ArrayList<String> contentReportList) {
		this.contentReportList = contentReportList;
	}
	public String getDeleteContentGooruOid() {
		return deleteContentGooruOid;
	}
	public void setDeleteContentGooruOid(String deleteContentGooruOid) {
		this.deleteContentGooruOid = deleteContentGooruOid;
	}

}
