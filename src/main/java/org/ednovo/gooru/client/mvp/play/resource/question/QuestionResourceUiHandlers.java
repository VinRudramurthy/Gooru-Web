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
package org.ednovo.gooru.client.mvp.play.resource.question;

import java.util.List;

import org.ednovo.gooru.application.client.gin.BaseUiHandlers;
import org.ednovo.gooru.application.shared.model.player.AnswerAttemptDo;
import org.ednovo.gooru.shared.util.AttemptedAnswersDo;

public interface QuestionResourceUiHandlers extends BaseUiHandlers{
	public void createSessionItemAttempt(String contentGooruOid,int answerId,String answerAttemptStatus);
	public void createSesstionItemAttemptOe(String contentGooruOid,String answerId,String attemptStatus,String answerText);
	public void setAttemptStatus(String collectionItemId,AttemptedAnswersDo attemptAnswerDo);
	public void setAnswerAttemptSequence(int attemptSequence,int attemptStatus,int answerId);
	public void setOeQuestionAnswerText(String answerText);
	public void startHintDataLogEvent(int hintId);
	public void startExplanationDataLogEvent();
	public void saveOeQuestionAnswerDataLogEvent();
	public void setUserAttemptedQuestionTypeAndStatus(boolean isUserAttemptedResult,int questionType);
	public void setAnswerIdWithTime(Integer answerId,Integer attemptStatus,Integer attemptSequence);
	public void setAnswerIdWithTimeForMa(List<Integer> answerId,Integer attemptStatus,Integer attemptSequence);
	public void setAnswerIdWithTimeForHT(List<Integer> answerId,Integer attemptStatus,Integer attemptSequence);
	public void setExplanationIdWithTime(String explanaion);
	public void setHintIdWithTime(Integer hintId);
	public void setResourceScore(Integer score);
	public void triggerSaveOeAnswerTextDataEvent();
	public void setFibAnswerIdsWithTime(List<Integer> attemptAnswerIds,List<Integer> attemptTrySequenceArray,List<Integer> attemptStatusArray);
	public void increaseUserAttemptCount();
	public void isOeAnswerSubmited(boolean isOeAnswerSubmited);
	public void userAttemptedAnswerObject(List<AnswerAttemptDo> answerOptionAttemptList);
}
