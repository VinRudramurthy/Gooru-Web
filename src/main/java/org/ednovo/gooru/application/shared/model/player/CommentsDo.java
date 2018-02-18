package org.ednovo.gooru.application.shared.model.player;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class CommentsDo implements IsSerializable
{
	
	private static final long serialVersionUID = 1L;
	private String comment;
	private String commentUid;
	private String createdOn;
	private String lastModifiedOn;
	private String gooruOid;
	private CommentsUserInfoDo commentorUid;
	private Integer statusCode;
	
	public CommentsDo(){}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getCommentUid() {
		return commentUid;
	}
	public void setCommentUid(String commentUid) {
		this.commentUid = commentUid;
	}
	public String getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	public String getGooruOid() {
		return gooruOid;
	}
	public void setGooruOid(String gooruOid) {
		this.gooruOid = gooruOid;
	}
	public CommentsUserInfoDo getCommentorUid() {
		return commentorUid;
	}
	public void setCommentorUid(CommentsUserInfoDo commentorUid) {
		this.commentorUid = commentorUid;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	/** 
	 * This method is to get the lastModifiedOn
	 */
	public String getLastModifiedOn() {
		return lastModifiedOn;
	}
	/** 
	 * This method is to set the lastModifiedOn
	 */
	public void setLastModifiedOn(String lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	
}
