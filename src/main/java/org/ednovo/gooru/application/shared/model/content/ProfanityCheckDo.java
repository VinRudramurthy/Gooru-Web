
/**
 * 
*/
package org.ednovo.gooru.application.shared.model.content;

import com.google.gwt.user.client.rpc.IsSerializable;


/**
 * @fileName : ProfanityModel.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 24-Jan-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class ProfanityCheckDo implements IsSerializable{

	/**
	 * 
	 */
	public ProfanityCheckDo() {
	}
	public String questionID;
	public String questionText;
	public boolean questionValue;
	/** 
	 * This method is to get the questionID
	 */
	public String getQuestionID() {
		return questionID;
	}
	/** 
	 * This method is to set the questionID
	 */
	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}
	/** 
	 * This method is to get the questionText
	 */
	public String getQuestionText() {
		return questionText;
	}
	/** 
	 * This method is to set the questionText
	 */
	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}
	/** 
	 * This method is to get the questionValue
	 */
	public boolean isQuestionValue() {
		return questionValue;
	}
	/** 
	 * This method is to set the questionValue
	 */
	public void setQuestionValue(boolean questionValue) {
		this.questionValue = questionValue;
	}
}
