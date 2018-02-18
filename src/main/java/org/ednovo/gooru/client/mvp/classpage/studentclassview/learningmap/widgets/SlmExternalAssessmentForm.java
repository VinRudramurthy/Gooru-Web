package org.ednovo.gooru.client.mvp.classpage.studentclassview.learningmap.widgets;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.classpages.PlanProgressDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.play.collection.GwtUUIDGenerator;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.json.client.JSONBoolean;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class SlmExternalAssessmentForm extends Composite {

	@UiField HTMLEventPanel submit;

	@UiField TextBox scoreTextBox;

	@UiField TextArea evidence;

	@UiField Anchor submitTxt, inProgressTxt;

	@UiField InlineLabel scoreErrorLbl, evidenceErrorLbl, scoreLbl, evidenceLbl;

	private static final String ERROR_LBL_COLOR = "#EE1E7B";

	int attemptedScore;

	private PlanProgressDo planProgressDo = null;

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	private static SlmExternalAssessmentFormUiBinder uiBinder = GWT
			.create(SlmExternalAssessmentFormUiBinder.class);

	interface SlmExternalAssessmentFormUiBinder extends
			UiBinder<Widget, SlmExternalAssessmentForm> {
	}

	public SlmExternalAssessmentForm(PlanProgressDo planProgressDo) {
		initWidget(uiBinder.createAndBindUi(this));
		this.planProgressDo = planProgressDo;
		setIds();
		if(planProgressDo.getEvidence()!=null) {
			evidence.setText(planProgressDo.getEvidence());
		}
		if(planProgressDo.getViews()>0) {
			scoreTextBox.setText(planProgressDo.getScoreInPercentage()+"");
		}
	}

	private void setIds() {
		evidenceErrorLbl.setVisible(false);
		scoreErrorLbl.setVisible(false);
		scoreTextBox.setMaxLength(3);
		setButtonVisibility(true);
		scoreLbl.setText(i18n.GL2288());
		evidenceLbl.setText(i18n.GL3469_8());
		scoreTextBox.addKeyPressHandler(new NumbersOnly());
		scoreTextBox.addKeyUpHandler(new ValidateScore());
		scoreTextBox.addBlurHandler(new ValidateScoreBlur());
		scoreTextBox.getElement().setAttribute("placeholder", i18n.GL3469_15());
		evidence.getElement().setAttribute("placeholder", i18n.GL3469_16());
		scoreTextBox.getElement().getStyle().setFontSize(13, Unit.PX);
		evidence.getElement().getStyle().setFontSize(13, Unit.PX);
		submit.addClickHandler(new SaveData());
	}

	private class SaveData implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			checkProfanity();
		}
	}

	private class ValidateScore implements KeyUpHandler{
		@Override
		public void onKeyUp(KeyUpEvent event) {
			validateScore();
		}
	}

	private class ValidateScoreBlur implements BlurHandler{
		@Override
		public void onBlur(BlurEvent event) {
			validateScore();
		}
	}

	private class NumbersOnly implements KeyPressHandler{
		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (!Character.isDigit(event.getCharCode())
                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_TAB
                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_BACKSPACE
                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_SHIFT
                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_ENTER
                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_LEFT
                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_RIGHT
                    && event.getNativeEvent().getKeyCode() != KeyCodes.KEY_DELETE){
                ((TextBox) event.getSource()).cancelKey();
            }
		}
	}

	private boolean validateScore() {
		boolean isScore = false;
		String score = scoreTextBox.getText();
		if(score.isEmpty()){
			scoreTextBox.getElement().getStyle().setBorderColor(ERROR_LBL_COLOR);
		}else if(score != null || score != ""){
			if(Integer.parseInt(score) >100 || Integer.parseInt(score) <0){
				scoreErrorLbl.setVisible(true);
				scoreTextBox.getElement().getStyle().setBorderColor(ERROR_LBL_COLOR);
			}else{
				scoreErrorLbl.setVisible(false);
				scoreTextBox.getElement().getStyle().setBorderColor("");
				attemptedScore=Integer.valueOf(score);
				isScore = true;
			}
		}
		return isScore;
	}

	private void validateScoreEvidence() {
		boolean isScore = false, isEvidence = false;
		isScore = validateScore();

		String evidenceStr = evidence.getText();
		if(evidenceStr.isEmpty()){
			evidence.getElement().getStyle().setBorderColor(ERROR_LBL_COLOR);
			evidenceErrorLbl.setVisible(false);
		}else if(evidenceStr != null && evidenceStr.length()>0){
			if(evidenceStr.length()>250) {
				evidenceErrorLbl.setText(i18n.GL0143());
				evidenceErrorLbl.setVisible(true);
			} else {
				evidenceErrorLbl.setVisible(false);
				evidence.getElement().getStyle().setBorderColor("");
				isEvidence = true;
			}
		}
		if(isScore==true&&isEvidence==true) {
			logDataEvent();
		} else {
			setButtonVisibility(true);
		}
	}

	public void setButtonVisibility(boolean isVisible) {
		submitTxt.setVisible(isVisible);
		inProgressTxt.setVisible(!isVisible);
	}

	private void checkProfanity() {
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("text", evidence.getText());
		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean value) {
				if (value){
					evidenceErrorLbl.setText(i18n.GL0554());
					evidenceErrorLbl.setVisible(true);
					setButtonVisibility(true);
				}else{
					setButtonVisibility(false);
					validateScoreEvidence();
				}
			}
		});
	}
	
	private void logDataEvent() {
			try
			{
				JSONObject collectionDataLog=new JSONObject();
				collectionDataLog.put(PlayerDataLogEvents.EVENTID, new JSONString(GwtUUIDGenerator.uuid()));
				collectionDataLog.put(PlayerDataLogEvents.EVENTNAME, new JSONString(PlayerDataLogEvents.COLLECTION_PLAY));
				collectionDataLog.put(PlayerDataLogEvents.SESSION, PlayerDataLogEvents.getDataLogSessionObject(GwtUUIDGenerator.uuid()));
				collectionDataLog.put(PlayerDataLogEvents.STARTTIME, new JSONNumber(PlayerDataLogEvents.getUnixTime()));
				collectionDataLog.put(PlayerDataLogEvents.ENDTIME, new JSONNumber(PlayerDataLogEvents.getUnixTime()));
				collectionDataLog.put(PlayerDataLogEvents.USER, PlayerDataLogEvents.getDataLogUserObject());

				String gooruOid= planProgressDo.getGooruOId();

				String classpageId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_CLASS_ID,null);
				String courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID,null);
				String unitId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_UNIT_ID,null);
				String lessonId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_LESSON_ID,null);

				JSONObject contextMap=new JSONObject();
				try{
					contextMap.put(PlayerDataLogEvents.CONTENTGOORUID, new JSONString(gooruOid));
					contextMap.put(PlayerDataLogEvents.PARENTGOORUID, new JSONString(""));
					contextMap.put(PlayerDataLogEvents.CLASSGOORUID, new JSONString(classpageId));
					contextMap.put(PlayerDataLogEvents.PARENTEVENTID, new JSONString(""));
					contextMap.put(PlayerDataLogEvents.TYPE, new JSONString(PlayerDataLogEvents.STOP_EVENT_TYPE));
					contextMap.put(PlayerDataLogEvents.RESOURCETYPE, new JSONString(""));
					contextMap.put(PlayerDataLogEvents.CLIENTSOURCE, new JSONString("web"));
					contextMap.put(PlayerDataLogEvents.PATH, new JSONString("student-view&u-id="+unitId+"&id="+classpageId+"&c-id="+courseId+"&l-id="+lessonId+"&page-view=lesson-view"));
					contextMap.put(PlayerDataLogEvents.PAGELOCATION, new JSONString("student-view"));
					contextMap.put(PlayerDataLogEvents.COURSEID, new JSONString(courseId));
					contextMap.put(PlayerDataLogEvents.UNITID, new JSONString(unitId));
					contextMap.put(PlayerDataLogEvents.LESSONID, new JSONString(lessonId));
					contextMap.put(PlayerDataLogEvents.COLLECTIONTYPE, new JSONString("assessment/url"));
					contextMap.put(PlayerDataLogEvents.TOTALQUESTIONSCOUNT, new JSONNumber(0));
					contextMap.put(PlayerDataLogEvents.MODE, new JSONString(PlayerDataLogEvents.STUDY));
					contextMap.put(PlayerDataLogEvents.EVIDENCE, new JSONString(evidence.getText()));
					contextMap.put(PlayerDataLogEvents.ISSTUDENT, JSONBoolean.getInstance(true));
				}catch(Exception e){
					 e.printStackTrace();
				}
				collectionDataLog.put(PlayerDataLogEvents.CONTEXT, new JSONString(contextMap.toString()));

				JSONObject versionMap=new JSONObject();
				try{
					versionMap.put(PlayerDataLogEvents.LOGAPI, new JSONString("0.1"));
				}catch(Exception e){
					 AppClientFactory.printSevereLogger("SimExternalAssessmentForm : logDataEvent 1 : "+e.getMessage());
				}
				collectionDataLog.put(PlayerDataLogEvents.VERSION, new JSONString(versionMap.toString()));

				JSONObject metricsMap=new JSONObject();
				try{
					metricsMap.put(PlayerDataLogEvents.TOTALTIMESPENTINMS, new JSONNumber(0));
					metricsMap.put(PlayerDataLogEvents.SCORE,new JSONNumber(0));
					metricsMap.put(PlayerDataLogEvents.VIEWSCOUNT, new JSONNumber(1));
					String scoreStr = scoreTextBox.getText();
					scoreStr = scoreStr.replace(".","");
					metricsMap.put(PlayerDataLogEvents.SCORE_IN_PERCENTAGE,new JSONNumber(Integer.parseInt(scoreStr)));
				}catch(Exception e){
					 AppClientFactory.printSevereLogger("SimExternalAssessmentForm : logDataEvent 2: "+e.getMessage());
				}

				collectionDataLog.put(PlayerDataLogEvents.METRICS, new JSONString(metricsMap.toString()));
				JSONObject playLoad=new JSONObject();
				collectionDataLog.put(PlayerDataLogEvents.PAYLOADOBJECT,new JSONString(playLoad.toString()));
				PlayerDataLogEvents.collectionStartStopEvent(collectionDataLog);
			} catch(Exception ex) {
				ex.printStackTrace();
			} finally {
				 Timer t = new Timer() {
				      @Override
				      public void run() {
				    	  setButtonVisibility(true);
				    	  submitTxt.setText(i18n.GL3469_14());
				      }
				 };
				 t.schedule(2000);
			}
	}
}