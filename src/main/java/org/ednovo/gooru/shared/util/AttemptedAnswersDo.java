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
package org.ednovo.gooru.shared.util;

import com.google.gwt.user.client.rpc.IsSerializable; 
import java.util.Map;

public class AttemptedAnswersDo implements IsSerializable{
	
	private static final long serialVersionUID = 4393225749052874789L;
	
	private int questionType;
	private boolean attemptResult;
	private String answersText;
	private int answerId;
	private String[] fibAnswersList;
	private Map<Integer,Boolean> answerOptionResult;
	
	public int getQuestionType() {
		return questionType;
	}
	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}
	public boolean isAttemptResult() {
		return attemptResult;
	}
	public void setAttemptResult(boolean attemptResult) {
		this.attemptResult = attemptResult;
	}
	public String getAnswersText() {
		return answersText;
	}
	public void setAnswersText(String answersText) {
		this.answersText = answersText;
	}
	public int getAnswerId() {
		return answerId;
	}
	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	/**
	 * @return the fibAnswersList
	 */
	public String[] getFibAnswersList() {
		return fibAnswersList;
	}
	/**
	 * @param fibAnswersList the fibAnswersList to set
	 */
	public void setFibAnswersList(String[] fibAnswersList) {
		this.fibAnswersList = fibAnswersList;
	}
	public Map<Integer,Boolean> getAnswerOptionResult() {
		return answerOptionResult;
	}
	public void setAnswerOptionResult(Map<Integer,Boolean> answerOptionResult) {
		this.answerOptionResult = answerOptionResult;
	}

}
