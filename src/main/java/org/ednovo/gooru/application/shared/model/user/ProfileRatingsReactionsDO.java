package org.ednovo.gooru.application.shared.model.user;

import com.google.gwt.user.client.rpc.IsSerializable; 

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonInclude(Include.NON_NULL)
public class ProfileRatingsReactionsDO implements IsSerializable {
	private static final long serialVersionUID = 1L;
	ProfileRatingsReactionsDO(){}
	
	private int copiedCount;
	private int reviewCount;
	private int commentCount;
	private int shareCount;
	
	private int countOfMeh;
	private int countOfICanUnderstand;
	private int countOfIDoNotUnderstand;
	private int countOfINeedHelp;
	private int countOfICanExplain;
	
	private int countOfRating1;
	private int countOfRating2;
	private int countOfRating3;
	private int countOfRating4;
	private int countOfRating5;
	public int getCopiedCount() {
		return copiedCount;
	}
	public void setCopiedCount(int copiedCount) {
		this.copiedCount = copiedCount;
	}
	public int getReviewCount() {
		return reviewCount;
	}
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public int getShareCount() {
		return shareCount;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	public int getCountOfMeh() {
		return countOfMeh;
	}
	public void setCountOfMeh(int countOfMeh) {
		this.countOfMeh = countOfMeh;
	}
	public int getCountOfICanUnderstand() {
		return countOfICanUnderstand;
	}
	public void setCountOfICanUnderstand(int countOfICanUnderstand) {
		this.countOfICanUnderstand = countOfICanUnderstand;
	}
	public int getCountOfIDoNotUnderstand() {
		return countOfIDoNotUnderstand;
	}
	public void setCountOfIDoNotUnderstand(int countOfIDoNotUnderstand) {
		this.countOfIDoNotUnderstand = countOfIDoNotUnderstand;
	}
	public int getCountOfINeedHelp() {
		return countOfINeedHelp;
	}
	public void setCountOfINeedHelp(int countOfINeedHelp) {
		this.countOfINeedHelp = countOfINeedHelp;
	}
	public int getCountOfICanExplain() {
		return countOfICanExplain;
	}
	public void setCountOfICanExplain(int countOfICanExplain) {
		this.countOfICanExplain = countOfICanExplain;
	}
	public int getCountOfRating1() {
		return countOfRating1;
	}
	public void setCountOfRating1(int countOfRating1) {
		this.countOfRating1 = countOfRating1;
	}
	public int getCountOfRating2() {
		return countOfRating2;
	}
	public void setCountOfRating2(int countOfRating2) {
		this.countOfRating2 = countOfRating2;
	}
	public int getCountOfRating3() {
		return countOfRating3;
	}
	public void setCountOfRating3(int countOfRating3) {
		this.countOfRating3 = countOfRating3;
	}
	public int getCountOfRating4() {
		return countOfRating4;
	}
	public void setCountOfRating4(int countOfRating4) {
		this.countOfRating4 = countOfRating4;
	}
	public int getCountOfRating5() {
		return countOfRating5;
	}
	public void setCountOfRating5(int countOfRating5) {
		this.countOfRating5 = countOfRating5;
	}
}
