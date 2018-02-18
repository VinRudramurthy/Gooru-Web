package org.ednovo.gooru.application.shared.model.library;

import java.util.ArrayList;

import org.ednovo.gooru.application.shared.model.content.ThumbnailDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;
@JsonInclude(Include.NON_NULL)
public class StandardCourseDo implements IsSerializable {

	private static final long serialVersionUID = 2411080367742513414L;
	private Integer codeId;
	private String label;
	private String code;
	private ThumbnailDo thumbnails;
	private ArrayList<CourseDo> course;

	public StandardCourseDo(){}

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

	public ThumbnailDo getThumbnails() {
		return thumbnails;
	}

	public void setThumbnails(ThumbnailDo thumbnails) {
		this.thumbnails = thumbnails;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ArrayList<CourseDo> getCourse() {
		return course;
	}

	public void setCourse(ArrayList<CourseDo> course) {
		this.course = course;
	}


}