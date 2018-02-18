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
package org.ednovo.gooru.application.shared.model.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class QuestionAnswerDo implements IsSerializable,Comparable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String answerText;
	private String answerType;
	private int sequence;
	private boolean isCorrect;
	private Integer answerId;

	public QuestionAnswerDo(){}

	public String getAnswerText() {
		return answerText;
	}
	public String getAnswerType() {
		return answerType;
	}
	public int getSequence() {
		return sequence;
	}
	public boolean isIsCorrect() {
		return isCorrect;
	}
	public void setAnswerText(String answerText) {
		this.answerText = answerText;
	}
	public void setAnswerType(String answerType) {
		this.answerType = answerType;
	}
	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	public void setIsCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}


	@Override
	public int compareTo(Object o) {

		 if (this.sequence == ((QuestionAnswerDo) o).sequence)
	           return 0;
	       else if ((this.sequence) > ((QuestionAnswerDo) o).sequence)
	           return 1;
	       else
	           return -1;
	}
	public Integer getAnswerId() {
		return answerId;
	}
	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

}
