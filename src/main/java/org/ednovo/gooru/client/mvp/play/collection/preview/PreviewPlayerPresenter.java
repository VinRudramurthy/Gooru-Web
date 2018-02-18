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
package org.ednovo.gooru.client.mvp.play.collection.preview;



import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BasePlacePresenter;
import org.ednovo.gooru.application.client.service.PlayerAppServiceAsync;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.application.shared.model.content.ContentReportDo;
import org.ednovo.gooru.application.shared.model.content.ReactionDo;
import org.ednovo.gooru.application.shared.model.content.StarRatingsDo;
import org.ednovo.gooru.client.SeoTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.authentication.SignUpPresenter;
import org.ednovo.gooru.client.mvp.home.LoginPopupUc;
import org.ednovo.gooru.client.mvp.play.collection.GwtUUIDGenerator;
import org.ednovo.gooru.client.mvp.play.collection.add.AddCollectionPresenter;
import org.ednovo.gooru.client.mvp.play.collection.event.ClosePreviewPlayerEvent;
import org.ednovo.gooru.client.mvp.play.collection.event.ShowPreviewTabWidgetEvent;
import org.ednovo.gooru.client.mvp.play.collection.event.UpdatePreviewViewCountEvent;
import org.ednovo.gooru.client.mvp.play.collection.flag.CollectionFlagPresenter;
import org.ednovo.gooru.client.mvp.play.collection.info.ResourceInfoPresenter;
import org.ednovo.gooru.client.mvp.play.collection.preview.metadata.NavigationConfirmPopup;
import org.ednovo.gooru.client.mvp.play.collection.preview.metadata.PreviewPlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.play.collection.share.CollectionSharePresenter;
import org.ednovo.gooru.client.mvp.play.collection.toc.CollectionPlayerTocPresenter;
import org.ednovo.gooru.client.mvp.play.error.CollectionNonExistView;
import org.ednovo.gooru.client.mvp.play.error.ResourceNonExitView;
import org.ednovo.gooru.client.mvp.play.resource.add.AddResourceCollectionPresenter;
import org.ednovo.gooru.client.mvp.play.resource.body.ResourcePlayerMetadataPresenter;
import org.ednovo.gooru.client.mvp.play.resource.flag.ResourceFlagPresenter;
import org.ednovo.gooru.client.mvp.play.resource.narration.ResourceNarrationPresenter;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.search.event.UpdateSearchResultMetaDataEvent;
import org.ednovo.gooru.client.mvp.settings.CustomAnimation;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshCollectionInShelfListInPreviewPlayEvent;
import org.ednovo.gooru.client.mvp.shelf.event.RefreshUserShelfCollectionsEvent;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.shared.util.AttemptedAnswersDo;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.PlayerConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.annotations.NameToken;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
import com.gwtplatform.mvp.client.proxy.ProxyPlace;
import com.gwtplatform.mvp.client.proxy.RevealRootPopupContentEvent;

public class PreviewPlayerPresenter extends BasePlacePresenter<IsPreviewPlayerView, PreviewPlayerPresenter.IsPreviewPlayerProxy> implements PreviewPlayerUiHandlers,ClientConstants{

	@Inject
	private PlayerAppServiceAsync playerAppService;

    private SimpleAsyncCallback<CollectionDo> collectionDetailsAsync;

    private PreviewPlayerMetadataPresenter metadataPresenter;

    private ResourcePlayerMetadataPresenter resoruceMetadataPresenter;

    private CollectionPlayerTocPresenter collectionPlayerTocPresenter;

    private ResourceNarrationPresenter resourceNarrationPresenter;

    private CollectionSharePresenter collectionSharePresenter;

    private ResourceInfoPresenter resourceInfoPresenter;

    private CollectionFlagPresenter collectionFlagPresenter;

    private ResourceFlagPresenter resourceFlagPresenter;

    private AddResourceCollectionPresenter addResourcePresenter;

    private AddCollectionPresenter addCollectionPresenter;

    private SignUpPresenter signUpViewPresenter = null;

    private CollectionDo collectionDo=null;

    private CollectionItemDo collectionItemDo=null;

    private String collectionMetadataId=null;

    private String collectionSummaryId=null;

    private String collectionActivityEventId=null;

    private String resourceActivityEventId=null;

    private String resourceActivityResourceId=null;

    private String sessionId=null;

    private String sessionItemId=null;

    private String collectionDataLogEventId=null;

    private String collectionNewDataLogEventId=null;

    private String resourceDataLogEventId;

    private String resourceNewDataLogEventId;

    private Long collectionStartTime=0L;

    private Long newCollectionStartTime=0L;

    private Long collectionEndTime=0L;

    private Long totalTimeSpentOnSummaryPage=0L;

    private Long resourceStartTime=0L;

    private Long hintOrExplanationStartTime=0L;

    private String hintOrExplanationEventName=null;

    private String hintOrExplanationEventId=null;

    private String resourcePlayEventName=null;

	private Map<String,AttemptedAnswersDo> attemptAnswersMap=new HashMap<String,AttemptedAnswersDo>();

	private List<Integer> attemptTrySequence=new ArrayList<Integer>();

	private List<Integer> attemptStatus=new ArrayList<Integer>();

	private List<Integer> answerIds=new ArrayList<Integer>();

	private JSONObject answerIdsObject=new JSONObject();

	private JSONObject hintIdsObject=new JSONObject();

	private JSONObject explanationIdsObject=new JSONObject();

	private List<Integer> attemptTrySequenceArray=new ArrayList<Integer>();

	private List<List<JSONObject>> answerObjectArray=new ArrayList<List<JSONObject>>();

	private Integer resourceScore=0;

	private Integer collectionScore=0;

	private Integer attemptCount=0;

	private List<Integer> attemptStatusArray=new ArrayList<Integer>();

	private String questionType="RES";

	private String oeQuestionAnswerText="";

	private int hintId=0;

	private boolean isExplanationUsed=false;

	private boolean isUserAttemptedAnswer=false;

	private int userAttemptedQuestionType=0;

	private boolean isOpenEndedAnswerSubmited=true;

	private int sessionIdCreationCount=0;

	public static final  Object COLLECTION_PLAYER_TOC_PRESENTER_SLOT = new Object();

    public static final  Object METADATA_PRESENTER_SLOT = new Object();

    private static final String RESOURCE="resource";

    private static final String COLLECTION_RESOURCE_THUMBS_WIDGET_MODE="COLLECTION_RESOURCE_RATING";

    private static final String COLLECTION_RESOURCE_ADD_WIDGET="COLLECTION_RESOURCE_ADD";

    private static final String COLLECTION_RESOURCE_FLAG="COLLECTION_RESOURCE_FLAG";

    private static final String COLLECTION_FLAG="COLLECTION_FLAG";

    private static final String COLLECTION_COMMENTS="COLLECTION_COMMENTS";

    private static final String PRIVATE="private";

    private static String Star_Rating_Widget = "ratingWidget";

	private static final int CHILD_AGE=13;


    /**
	 * @return the answerIdsObject
	 */
	public JSONObject getAnswerIdsObject() {
		return answerIdsObject;
	}

	/**
	 * @param answerIdsObject the answerIdsObject to set
	 */
	public void setAnswerIdsObject(JSONObject answerIdsObject) {
		this.answerIdsObject = answerIdsObject;
	}

	/**
	 * @return the hintIdsObject
	 */
	public JSONObject getHintIdsObject() {
		return hintIdsObject;
	}

	/**
	 * @param hintIdsObject the hintIdsObject to set
	 */
	public void setHintIdsObject(JSONObject hintIdsObject) {
		this.hintIdsObject = hintIdsObject;
	}

	/**
	 * @return the explanationIdsObject
	 */
	public JSONObject getExplanationIdsObject() {
		return explanationIdsObject;
	}

	/**
	 * @param explanationIdsObject the explanationIdsObject to set
	 */
	public void setExplanationIdsObject(JSONObject explanationIdsObject) {
		this.explanationIdsObject = explanationIdsObject;
	}

	/**
	 * @return the attemptTrySequenceArray
	 */
	public List<Integer> getAttemptTrySequenceArray() {
		return attemptTrySequenceArray;
	}

	/**
	 * @param attemptTrySequenceArray the attemptTrySequenceArray to set
	 */
	public void setAttemptTrySequenceArray(List<Integer> attemptTrySequenceArray) {
		this.attemptTrySequenceArray = attemptTrySequenceArray;
	}

	/**
	 * @return the attemptStatusArray
	 */
	public List<Integer> getAttemptStatusArray() {
		return attemptStatusArray;
	}

	/**
	 * @param attemptStatusArray the attemptStatusArray to set
	 */
	public void setAttemptStatusArray(List<Integer> attemptStatusArray) {
		this.attemptStatusArray = attemptStatusArray;
	}

	/**
	 * @return the resourceScore
	 */
	public Integer getResourceScore() {
		return resourceScore;
	}

	/**
	 * @param resourceScore the resourceScore to set
	 */
	public void setResourceScore(Integer resourceScore) {
		this.resourceScore = resourceScore;
	}


	/**
	 * @return the attemptCount
	 */
	public Integer getAttemptCount() {
		return attemptCount;
	}

	/**
	 * @param attemptCount the attemptCount to set
	 */
	public void setAttemptCount(Integer attemptCount) {
		this.attemptCount = attemptCount;
	}


	/**
	 * @return the collectionScore
	 */
	public Integer getCollectionScore() {
		return collectionScore;
	}

	/**
	 * @param collectionScore the collectionScore to set
	 */
	public void setCollectionScore(Integer collectionScore) {
		this.collectionScore = collectionScore;
	}

   /**
	 * @return the isOpenEndedAnswerSubmited
	 */
	public boolean isOpenEndedAnswerSubmited() {
		return isOpenEndedAnswerSubmited;
	}

	/**
	 * @param isOpenEndedAnswerSubmited the isOpenEndedAnswerSubmited to set
	 */
	public void setOpenEndedAnswerSubmited(boolean isOpenEndedAnswerSubmited) {
		this.isOpenEndedAnswerSubmited = isOpenEndedAnswerSubmited;
	}


	/**
	 * @return the answerObjectArray
	 */
	public List<List<JSONObject>> getAnswerObjectArray() {
		return answerObjectArray;
	}

	/**
	 * @param answerObjectArray the answerObjectArray to set
	 */
	public void setAnswerObjectArray(List<List<JSONObject>> answerObjectArray) {
		this.answerObjectArray = answerObjectArray;
	}

	@Inject
	public PreviewPlayerPresenter(PreviewPlayerMetadataPresenter metadataPresenter,ResourcePlayerMetadataPresenter resoruceMetadataPresenter,
			CollectionPlayerTocPresenter collectionPlayerTocPresenter,CollectionSharePresenter collectionSharePresenter,
			ResourceInfoPresenter resourceInfoPresenter,ResourceNarrationPresenter resourceNarrationPresenter,
			EventBus eventBus,IsPreviewPlayerView view, IsPreviewPlayerProxy proxy, AddResourceCollectionPresenter addResourcePresenter,
     		AddCollectionPresenter addCollectionPresenter,CollectionFlagPresenter collectionFlagPresenter,
     		ResourceFlagPresenter resourceFlagPresenter,SignUpPresenter signUpViewPresenter) {
		super(view, proxy);
		getView().setUiHandlers(this);
		this.metadataPresenter=metadataPresenter;
		this.resoruceMetadataPresenter=resoruceMetadataPresenter;
		this.collectionPlayerTocPresenter=collectionPlayerTocPresenter;
		this.resourceNarrationPresenter=resourceNarrationPresenter;
		this.collectionSharePresenter=collectionSharePresenter;
		this.resourceInfoPresenter=resourceInfoPresenter;
		this.addCollectionPresenter=addCollectionPresenter;
		this.addResourcePresenter=addResourcePresenter;
		this.collectionFlagPresenter=collectionFlagPresenter;
		this.resourceFlagPresenter=resourceFlagPresenter;
		this.signUpViewPresenter=signUpViewPresenter;
		metadataPresenter.setPreviewPlayerPresenter(this);
		resoruceMetadataPresenter.setPreviewPlayerPresenter(this);
		resoruceMetadataPresenter.removeRatingContainer(false);
		resourceFlagPresenter.setPreviewPlayerPresenter(this);
		collectionFlagPresenter.setPreviewPlayerPresenter(this);
		collectionSharePresenter.setPreviewPlayerPresenter(this);
		collectionPlayerTocPresenter.setPreviewPlayerPresenter(this);
		addResourcePresenter.getAddCollectionViewButton().setVisible(false);
		addCollectionPresenter.getAddResourceViewButton().setVisible(false);
	}

	@ProxyCodeSplit
	@NameToken("sjkldfkal")
	public interface IsPreviewPlayerProxy extends ProxyPlace<PreviewPlayerPresenter> {
	}

	@Override
	protected void revealInParent() {
		RevealRootPopupContentEvent.fire(this, this);
	}

	@Override
	public void onBind() {
	  super.onBind();
	  addRegisteredHandler(UpdatePreviewViewCountEvent.TYPE, this);
	  addRegisteredHandler(ShowPreviewTabWidgetEvent.TYPE, this);
	  addRegisteredHandler(RefreshCollectionInShelfListInPreviewPlayEvent.TYPE, this);
	  addRegisteredHandler(ClosePreviewPlayerEvent.TYPE, this);
	  KeyDownHandler keydown=new KeyDownHandler() {
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if(getPlaceManager().getRequestParameter("rid", null)!=null && !getPlaceManager().getRequestParameter("rid", null).equalsIgnoreCase("")){
					//This will handle if tab containers any value
					if(getPlaceManager().getRequestParameter("tab", null)!=null){
						//This will handle if tab containers value as narration then locking the keyboard arrows navigation.
 						if(!NARRATION.equalsIgnoreCase(getPlaceManager().getRequestParameter("tab", null))){
							// left arrow
							if (event.getNativeKeyCode() == 37)
						    {
						    	new ShowResourceView(getPreviousButtonRequestUrl(getPlaceManager().getRequestParameter("rid", null)));
						    }
						    // right arrow
						    if (event.getNativeKeyCode() == 39)
						    {
						    	new ShowResourceView(getNextButtonRequestUrl(getPlaceManager().getRequestParameter("rid", null)));
						    }
						}
					}else{
						// left arrow
						if (event.getNativeKeyCode() == 37)
					    {
					    	new ShowResourceView(getPreviousButtonRequestUrl(getPlaceManager().getRequestParameter("rid", null)));
					    }
					    // right arrow
					    if (event.getNativeKeyCode() == 39)
					    {
					    	new ShowResourceView(getNextButtonRequestUrl(getPlaceManager().getRequestParameter("rid", null)));
					    }
					}

				}
			}
		};
		RootPanel.get().addDomHandler(keydown, KeyDownEvent.getType());
	}
	@Override
	protected void onReveal() {
	  super.onReveal();
		Document doc=Document.get();
		Element bodyelement = doc.getBody();
		bodyelement.getParentElement().setAttribute("style", "overflow:hidden");
	}
	@Override
	protected void onReset() {
	  super.onReset();
		Document doc=Document.get();
		Element bodyelement = doc.getBody();
		bodyelement.getParentElement().setAttribute("style", "overflow:hidden");
	}

	@Override
	protected void onUnbind() {
	  super.onUnbind();
	}

	@Override
	public void prepareFromRequest(PlaceRequest request) {
	  super.prepareFromRequest(request);
	  getCollectionDetails();
	}

	public void getCollectionDetails(){
		final String collectionId=getPlaceManager().getRequestParameter("id", null);
		  final String resourceId=getPlaceManager().getRequestParameter("rid", null);
		  final String tabView=getPlaceManager().getRequestParameter("tab", null);
		  final String apiKey=getPlaceManager().getRequestParameter("key", null);
		  final String view=getPlaceManager().getRequestParameter("view", null);
		  final String rootNodeId=getPlaceManager().getRequestParameter("rootNodeId", null);
		  if(this.collectionDo!=null&&this.collectionDo.getGooruOid().equals(collectionId)){
			  if(!StringUtil.isEmpty(resourceId)){
				  showResourceView(resourceId,tabView);
				  showTabWidget(tabView,collectionId,resourceId,false,false);
			  }
			  else if(!StringUtil.isEmpty(collectionId)){
				  if(view!=null&&END.equalsIgnoreCase(view)){
					  showCollectionEndView(collectionId, tabView);
					  showTabWidget(tabView,collectionId,resourceId,false,true);
				  }else{
					  showCollectionMetadataView(tabView);
					  showTabWidget(tabView,collectionId,resourceId,true,false);
				  }
			  }
		  }else{
		      if(!StringUtil.isEmpty(collectionId)){
		    	  resetCollectionPlayer();
		    	  if(getPlaceManager().getRequestParameter("view")!=null){
					}else{
			    	  sessionId=GwtUUIDGenerator.uuid();
			    	  triggerItemLoadDataLogEvent(PlayerDataLogEvents.getUnixTime(), PlayerDataLogEvents.COLLECTION,collectionId);
					}
		    	  this.playerAppService.getSimpleCollectionDetils(apiKey,collectionId,resourceId,tabView,rootNodeId,new SimpleAsyncCallback<CollectionDo>() {
		    			@Override
		    			public void onSuccess(CollectionDo collectionDo) {
							if(collectionDo.getStatusCode()!=200){
								showCollectionErrorMessage();
							}else{
								setPageTitle(collectionDo);
								showCollectionView(collectionDo,collectionId,resourceId,tabView);
							}
		    			}
		    		});
		      }
		  }
	}

	/**
	 * @function setPageTitle
	 *
	 * @description : To set the Window Title.
	 *
	 * @parm(s) : @param collectionDo {@link CollectionDo}
	 *
	*/

	protected void setPageTitle(CollectionDo collectionDo) {
		AppClientFactory.setBrowserWindowTitle(SeoTokens.COLLECTION_PLAYER_TITLE+collectionDo.getTitle());
	}

	public void showCollectionView(CollectionDo collectionDo,String collectionId,String resourceId, String tabView){
		  this.collectionDo=collectionDo;
		  if(collectionId==null || collectionId.equalsIgnoreCase("")){
			  return;
		  }
		  else{
			  if(resourceId==null || resourceId.equalsIgnoreCase("")){
				  String view=getPlaceManager().getRequestParameter("view", null);
				  if(view!=null&&END.equalsIgnoreCase(view)){
					  showCollectionEndView(collectionId, tabView);
					  showTabWidget(tabView,collectionId,resourceId,false,true);
				  }else{
					 showCollectionMetadataView(tabView);
					 showTabWidget(tabView,collectionId,resourceId,true,false);
				 }
			  }else{
				  showResourceView(resourceId,tabView);
				  showTabWidget(tabView,collectionId,resourceId,false,false);
			  }
		  }
	}

	public PlayerAppServiceAsync getPlayerAppService() {
		return playerAppService;
	}

	public void setPlayerAppService(PlayerAppServiceAsync playerAppService) {
		this.playerAppService = playerAppService;
	}

	public SimpleAsyncCallback<CollectionDo> getCollectionDetailsAsync() {
		return collectionDetailsAsync;
	}

	public void setCollectionDetailsAsync(SimpleAsyncCallback<CollectionDo> collectionDetailsAsync) {
		this.collectionDetailsAsync = collectionDetailsAsync;
	}
	protected void setCollectionDetails(CollectionDo collectionDo){
		if(collectionDo!=null){
			getView().setResourceTitle(collectionDo.getTitle());
		}
	}
	public void showCollectionMetadataView(String tabView){
		this.collectionItemDo=null;
		this.collectionSummaryId=null;
		setCollectionDetails(collectionDo);
		getView().hidePlayerButtons(true, collectionDo.getGooruOid());
		metadataPresenter.displayAuthorDetails(true);
		metadataPresenter.setCollectionMetadata(collectionDo);
		showSignupPopup();
		setOpenEndedAnswerSubmited(true);
		if(this.collectionMetadataId!=null){
			if(this.collectionMetadataId.equalsIgnoreCase(collectionDo.getGooruOid())){
				return;
			}
		}
		this.collectionMetadataId=collectionDo.getGooruOid();
		getProfilUserVisibility(collectionDo.getUser().getGooruUId());
		getView().setStudentViewLink();
		clearIframeContent();
		metadataPresenter.setPreviewHomePresenter();
		if(!AppClientFactory.isAnonymous()){
			metadataPresenter.getFlagedReport(collectionDo.getGooruOid());
		}
		stopResourceDataLogFromHomePage();
		// if resource event is not stoped , then this method
        //will call resource stop event and collection stop data log event.
		attemptAnswersMap.clear();
		setUserAttemptedQuestionTypeAndStatus(false,0);
		resetAnswerLists();
		setCollectionScore(0);
		setInSlot(METADATA_PRESENTER_SLOT, metadataPresenter,false);
	}
	public void showResourceView(String collectionItemId,String tabView) {
		CollectionItemDo collectionItemDo=getCollectionItemDo(collectionItemId);
		if(collectionItemDo!=null){
		this.collectionMetadataId=null;
		this.collectionSummaryId=null;
		showSignupPopup();
		if(this.collectionItemDo!=null){
			if(this.collectionItemDo.getCollectionItemId().equalsIgnoreCase(collectionItemDo.getCollectionItemId())){
				makeButtonActive(tabView);
				return;
			}
		}
		//TODO need to check is collection sharable or not, need to enable narration button if narration exist.
		boolean isSharable=true;
		if(PRIVATE.equalsIgnoreCase(collectionDo.getSharing())){
			isSharable=false;
		}
		getView().hidePlayerButtons(false, collectionDo.getGooruOid());
		if(tabView!=null&&NARRATION.equals(tabView)){
			enablePlayerButton(true,true, isSharable, true, true,true);
			makeButtonActive(tabView);
		}else{
			enablePlayerButton(true,true, isSharable, false, true,true);
			makeButtonActive(tabView);
		}
		clearIframeContent();
		this.collectionItemDo=collectionItemDo;
		clearSlot(COLLECTION_PLAYER_TOC_PRESENTER_SLOT);
		updateResourceViewCount(collectionItemDo.getResource().getGooruOid(),collectionItemDo.getViews().toString(),RESOURCE,collectionItemId);
		createPlayerDataLogs();
		setTotalTimeSpentOnSummaryPage();
		PlaceRequest nextResoruceRequest=getNextButtonRequestUrl(collectionItemId);
		PlaceRequest previousResoruceRequest=getPreviousButtonRequestUrl(collectionItemId);
		getView().setResourceTitle(collectionDo.getTitle());
		setUserAttemptedQuestionTypeAndStatus(false,0);
		if(!AppClientFactory.isAnonymous()){
			isResourceContentReported(collectionItemDo.getResource().getGooruOid());
		}
		resoruceMetadataPresenter.showResourceWidget(collectionItemDo,nextResoruceRequest,previousResoruceRequest);
		if(!AppClientFactory.isAnonymous()){
			resoruceMetadataPresenter.removeRatingContainer(false);
			resoruceMetadataPresenter.setReaction(collectionItemDo);
			resoruceMetadataPresenter.setResourceStarRatings(collectionItemDo);
		}else{
			resoruceMetadataPresenter.clearStarRatings();
		}
		setOpenEndedAnswerSubmited(true);
		setInSlot(METADATA_PRESENTER_SLOT, resoruceMetadataPresenter);
		}
		else
		{
			enablePlayerButton(false, false, false, false, false, false);
			getView().getPlayerBodyContainer().clear();
			getView().getPlayerBodyContainer().add(new ResourceNonExitView());
			getView().getNavigationContainer().getElement().getStyle().setProperty("display", "block");

		}
	}
	public void showCollectionEndView(String collectionId,String tabView) {
		this.collectionMetadataId=null;
		this.collectionItemDo=null;
		MixpanelUtil.Preview_Player_Reach_End();
		getView().hidePlayerButtons(true, collectionDo.getGooruOid());
		metadataPresenter.displayAuthorDetails(false);
		metadataPresenter.setCollectionMetadata(collectionDo);
		metadataPresenter.getFlagedReport(collectionId);
		showSignupPopup();
		setOpenEndedAnswerSubmited(true);
		if(this.collectionSummaryId!=null){
			if(this.collectionSummaryId.equalsIgnoreCase(collectionDo.getGooruOid())){
				return;
			}
		}
		this.collectionSummaryId=collectionDo.getGooruOid();
		clearSlot(COLLECTION_PLAYER_TOC_PRESENTER_SLOT);
		getView().setResourceTitle(collectionDo.getTitle());
		getView().setStudentViewLink();
		clearIframeContent();
		if(!AppClientFactory.isAnonymous()){
			metadataPresenter.getFlagedReport(collectionDo.getGooruOid());
		}
		stopResourceDataLog();
		resetAnswerLists();
		stopCollectionDataLog();
		setUserAttemptedQuestionTypeAndStatus(false,0);
		updateSession(sessionId);
		metadataPresenter.setPreviewEndPresenter();
		setInSlot(METADATA_PRESENTER_SLOT, metadataPresenter,false);
	}

	public void makeButtonActive(String tabView){
		if(tabView!=null){
			if(ADD.equalsIgnoreCase(tabView)){
				getView().clearActiveButton(false,true, true, true, true,false);
				getView().makeButtonActive(true, false,false, false, false,false);
			}
			else if(INFO.equalsIgnoreCase(tabView)){
				getView().clearActiveButton(true,false, true, true, true,false);
				getView().makeButtonActive(false,true, false, false, false,false);
			}
			else if(SHARE.equalsIgnoreCase(tabView)){

				getView().clearActiveButton(true,true, false, true, true,false);
				getView().makeButtonActive(false,false, true, false, false,false);
			}
			else if(NARRATION.equalsIgnoreCase(tabView)){
				getView().clearActiveButton(true,true, true, false, true,false);
				getView().makeButtonActive(false,false, false, true, false,false);
			}
			else if(NAVIGATION.equalsIgnoreCase(tabView)){
				getView().clearActiveButton(true,true, true, true, false,false);
				getView().makeButtonActive(false,false, false, false, true,false);
			}else if(FLAG.equalsIgnoreCase(tabView)){
				getView().clearActiveButton(true,true, true, true, true,false);
				getView().makeButtonActive(false,false, false, false, false,true);
			}
		}
	}

	public void showTabWidget(String tabView,String collectionId,String resourceId,boolean isCollectionHome,boolean isCollectionEnd){
		if(tabView==null||tabView.equals("")){
			getView().clearActiveButton(true,true, true, true, true,true);
			new CustomAnimation(getView().getNavigationContainer()).run(400);
		}
		else if(ADD.equals(tabView)){
			MixpanelUtil.mixpanelEvent("Player_Click_Add");
			if(AppClientFactory.isAnonymous()){
				clearSlot(COLLECTION_PLAYER_TOC_PRESENTER_SLOT);
				String checkCallbackParam = AppClientFactory.getPlaceManager().getRequestParameter("callback");
				if(checkCallbackParam == null || checkCallbackParam.isEmpty())
				{
				showLoginPopupWidget(COLLECTION_RESOURCE_ADD_WIDGET);
				}
			}else{
				 if(isCollectionHome||isCollectionEnd){
					 addCollectionPresenter.getAddResourceViewButton().setVisible(false);
					 setAddCollectionView(collectionId);
				 }else{
					 addCollectionPresenter.getAddResourceViewButton().setVisible(true);
					 setAddResourcesView(collectionId, resourceId);
				 }
			}
		 }
		else if(NAVIGATION.equals(tabView)){
			 setNavigationResourcesView(collectionId,resourceId,isCollectionHome);
		 }else if(SHARE.equals(tabView)){
			 setCollectionShareView(collectionId,resourceId);
		 }else if(NARRATION.equals(tabView)){
			 showNarrationPopup(resourceId);
		 }else if(INFO.equals(tabView)){
			 setResourceInfoView(resourceId);
		 }
		 else if(FLAG.equals(tabView)){
			 if(AppClientFactory.isAnonymous()&&(isCollectionHome||isCollectionEnd)){
				 clearSlot(COLLECTION_PLAYER_TOC_PRESENTER_SLOT);
					String checkCallbackParam = AppClientFactory.getPlaceManager().getRequestParameter("callback");
					if(checkCallbackParam == null || checkCallbackParam.isEmpty())
					{
					showLoginPopupWidget(COLLECTION_FLAG);
					}

			 }else if(AppClientFactory.isAnonymous()){
				 clearSlot(COLLECTION_PLAYER_TOC_PRESENTER_SLOT);
					String checkCallbackParam = AppClientFactory.getPlaceManager().getRequestParameter("callback");
					if(checkCallbackParam == null || checkCallbackParam.isEmpty())
					{
					showLoginPopupWidget(COLLECTION_RESOURCE_FLAG);
					}

			 }else{
				 if(isCollectionHome||isCollectionEnd){
					 setCollectionFlagView(collectionId);
				 }else{
					 setResourceFlagView(resourceId);
				 }
			}
		}else{
			 getView().getNavigationContainer().clear();
		 }
	}
	protected void showSignupPopup() {
		if (AppClientFactory.getPlaceManager().getRequestParameter("callback") != null && AppClientFactory.getPlaceManager().getRequestParameter("callback").equalsIgnoreCase("signup")) {
			//To show SignUp (Registration popup)
			if (AppClientFactory.isAnonymous()){
				Window.enableScrolling(false);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
				String type = AppClientFactory.getPlaceManager().getRequestParameter("type") ;
				int displayScreen =AppClientFactory.getPlaceManager().getRequestParameter("type") !=null  ? Integer.parseInt(type) : 1;
				signUpViewPresenter.displayPopup(displayScreen);
				addToPopupSlot(signUpViewPresenter);
			}
		}
	}
	public class showAddResourceView implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			final String collectionId=getPlaceManager().getRequestParameter("id", null);
			  final String resourceId=getPlaceManager().getRequestParameter("rid", null);
			setAddResourcesView(collectionId,resourceId);
		}
	}
	public class showAddCollectionView implements ClickHandler{
		@Override
		public void onClick(ClickEvent event) {
			final String collectionId=getPlaceManager().getRequestParameter("id", null);
			setAddCollectionView(collectionId);
		}
	}
	public void isResourceContentReported(String resourceGooruId){
		 if(!AppClientFactory.isAnonymous()){
			getContentReport(resourceGooruId);
		 }
	}
	public CollectionItemDo getCollectionItemDo(String collectionItemId){
		if(!StringUtil.isEmpty(collectionItemId)&&collectionDo!=null&&collectionDo.getGooruOid()!=null){
			for(int i=0;i<collectionDo.getCollectionItems().size();i++){
				CollectionItemDo collectionItemDo=collectionDo.getCollectionItems().get(i);
				if(collectionItemId.equalsIgnoreCase(collectionItemDo.getCollectionItemId())){
					collectionItemDo.setItemSequence(i+1);
					return collectionItemDo;
				}
			}
		}
		return null;
	}

	public void updatCollectionViewsCount(){
		if(collectionDo!=null&&collectionDo.getGooruOid()!=null){
			if(!StringUtil.isEmpty(collectionDo.getViews())){
				String viewsCount=collectionDo.getViews();
				Integer viewsCounts=Integer.parseInt(viewsCount);
				collectionDo.setViews(StringUtil.toString(viewsCounts));
				metadataPresenter.setViewCount(StringUtil.toString(viewsCounts));
				try{
		    	  	AppClientFactory.fireEvent(new UpdateSearchResultMetaDataEvent(String.valueOf(viewsCounts), collectionDo.getGooruOid(), "views"));
		         }
				catch(Exception ex){
					AppClientFactory.printSevereLogger("PreviewPlayerPresenter : updatCollectionViewsCount : "+ex.getMessage());
				}
			}
		}
	}
	public void updateViewCount(String collectionItemId){
		if(!StringUtil.isEmpty(collectionItemId)&&collectionDo!=null&&collectionDo.getGooruOid()!=null){
			if(collectionDo.getCollectionItems()!=null && collectionDo.getCollectionItems().size()>0){
				for(int i=0;i<collectionDo.getCollectionItems().size();i++){
					CollectionItemDo collectionItemDo=collectionDo.getCollectionItems().get(i);
					if(collectionItemId.equalsIgnoreCase(collectionItemDo.getCollectionItemId())){
						if(collectionItemDo.getResource()!=null &&!StringUtil.isEmpty(collectionItemDo.getResource().getViews())){
							String viewsCount=collectionItemDo.getResource().getViews();
							Integer viewsCounts=Integer.parseInt(viewsCount)+1;
							collectionItemDo.getResource().setViews(viewsCounts.toString());
							resourceInfoPresenter.updateViewsCount(viewsCounts.toString());
						}
						return;
					}
				}
			}
		}
	}
	public void setResourceFlagView(String resourceId){
		CollectionItemDo collectionItemDo=getCollectionItemDo(resourceId);
		resourceFlagPresenter.setFlagView(collectionItemDo);
		clearSlot(COLLECTION_PLAYER_TOC_PRESENTER_SLOT);
		addToPopupSlot(resourceFlagPresenter);
	}
	public void setCollectionFlagView(String collectionId){
		clearSlot(COLLECTION_PLAYER_TOC_PRESENTER_SLOT);
		collectionFlagPresenter.displayCollectionFlagData(collectionDo);
		addToPopupSlot(collectionFlagPresenter);
	}
	public PlaceRequest getNextButtonRequestUrl(String collectionItemId){
		if(!StringUtil.isEmpty(collectionItemId)&&collectionDo!=null&&collectionDo.getGooruOid()!=null){
			if(collectionDo.getCollectionItems()!=null && collectionDo.getCollectionItems().size()>0){
				for(int i=0;i<collectionDo.getCollectionItems().size();i++){
					CollectionItemDo collectionItemDo=collectionDo.getCollectionItems().get(i);
					if(collectionItemId.equalsIgnoreCase(collectionItemDo.getCollectionItemId())){
						if(collectionDo.getCollectionItems().size()==(i+1)){
							Map<String,String> params = new LinkedHashMap<String,String>();
							params.put("id", collectionDo.getGooruOid());
							params = PreviewPlayerPresenter.setConceptPlayerParameters(params);
							params.put("view", "end");
							PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
							return placeRequest;
						}else{
							collectionItemDo=collectionDo.getCollectionItems().get(i+1);
							return getResourcePlaceRequest(collectionItemDo);
						}
					}
				}
			}
		}
		return null;
	}
	public PlaceRequest getPreviousButtonRequestUrl(){
		if(collectionDo!=null&&collectionDo.getGooruOid()!=null){
			if(collectionDo.getCollectionItems()!=null && collectionDo.getCollectionItems().size()>0){
				CollectionItemDo collectionItemDo=collectionDo.getCollectionItems().get(collectionDo.getCollectionItems().size()-1);
				return getResourcePlaceRequest(collectionItemDo);
			}else{
				Map<String,String> params = new LinkedHashMap<String,String>();
				params.put("id", collectionDo.getGooruOid());
				params = PreviewPlayerPresenter.setConceptPlayerParameters(params);
				PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
				return placeRequest;
			}
		}
		return null;
	}
	public PlaceRequest getPreviousButtonRequestUrl(String collectionItemId){
		if(!StringUtil.isEmpty(collectionItemId)&&collectionDo!=null&&collectionDo.getGooruOid()!=null){
			if(collectionDo.getCollectionItems()!=null && collectionDo.getCollectionItems().size()>0){
				for(int i=0;i<collectionDo.getCollectionItems().size();i++){
					CollectionItemDo collectionItemDo=collectionDo.getCollectionItems().get(i);
					if(collectionItemId.equalsIgnoreCase(collectionItemDo.getCollectionItemId())){
						if(i==0){
							Map<String,String> params = new LinkedHashMap<String,String>();
							params.put("id", collectionDo.getGooruOid());
							params = PreviewPlayerPresenter.setConceptPlayerParameters(params);
							PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
							return placeRequest;
						}else{
							collectionItemDo=collectionDo.getCollectionItems().get(i-1);
							return getResourcePlaceRequest(collectionItemDo);
						}
					}
				}
			}
		}
		return null;
	}
	public void revealResourceView(CollectionItemDo collectionItemDo){
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("id", collectionDo.getGooruOid());
		params = PreviewPlayerPresenter.setConceptPlayerParameters(params);

		if(!StringUtil.isEmpty(collectionItemDo.getNarration())){
			params.put("rid", collectionItemDo.getCollectionItemId());
			params.put("tab", "narration");
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest);
		}else{
			params.put("rid", collectionItemDo.getCollectionItemId());
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
			AppClientFactory.getPlaceManager().revealPlace(false, placeRequest);
		}
	}

	public PlaceRequest getResourcePlaceRequest(CollectionItemDo collectionItemDo){
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("id", collectionDo.getGooruOid());
		params = PreviewPlayerPresenter.setConceptPlayerParameters(params);
		if(!StringUtil.isEmpty(collectionItemDo.getNarration())){
			params.put("rid", collectionItemDo.getCollectionItemId());
			params.put("tab", "narration");
			return AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		}else{
			params.put("rid", collectionItemDo.getCollectionItemId());
			return AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
		}

	}

	public void setAddResourcesView(String collectionId,String resourceId){
		addResourcePresenter.setCollectionItemData(collectionId, getCollectionItemDo(resourceId));
		setInSlot(COLLECTION_PLAYER_TOC_PRESENTER_SLOT, addResourcePresenter,false);
		new CustomAnimation(getView().getNavigationContainer()).run(400);
	}
	public void setAddCollectionView(String collectionId){
		addCollectionPresenter.setCollectionDo(collectionDo);
		setInSlot(COLLECTION_PLAYER_TOC_PRESENTER_SLOT, addCollectionPresenter,false);
		new CustomAnimation(getView().getNavigationContainer()).run(400);
	}

	public void setNavigationResourcesView(String collectionId,String resourceId,boolean isCollectionHome){
		collectionPlayerTocPresenter.setNavigationResources(collectionDo,isCollectionHome);
		collectionPlayerTocPresenter.setResourceActive(collectionId, resourceId, isCollectionHome);
		setInSlot(COLLECTION_PLAYER_TOC_PRESENTER_SLOT, collectionPlayerTocPresenter,false);
		new CustomAnimation(getView().getNavigationContainer()).run(400);

	}
	public void showNarrationPopup(String resourceId){
		CollectionItemDo collectionItemDo=getCollectionItemDo(resourceId);
		clearSlot(COLLECTION_PLAYER_TOC_PRESENTER_SLOT);
		resourceNarrationPresenter.setNarrationMetadata(collectionItemDo,collectionDo.getUser().getUsernameDisplay(),collectionDo.getUser().getGooruUId());
		try{
			if(Document.get().getElementById("playerid")!=null){
		         final Element myPlayer = Document.get().getElementById("playerid");
		         if(myPlayer.getPropertyString("src").contains("youtube")){
		        	pauseVideo(myPlayer);
		         }
			}
		}catch(Exception e){
			AppClientFactory.printSevereLogger("PreviewPlayerPresenter : showNarrationPopup : "+e.getMessage());
		}
		addToPopupSlot(resourceNarrationPresenter);
	}

	public void setResourceInfoView(String resourceId){
		CollectionItemDo collectionItemDo=getCollectionItemDo(resourceId);
		resourceInfoPresenter.setMycollectionTitle(collectionDo.getTitle());
		AppClientFactory.getInjector().getPlayerAppService().getResourceInfoDetails(null, collectionItemDo.getResource().getGooruOid(), null, new SimpleAsyncCallback<CollectionItemDo>() {
			@Override
			public void onSuccess(CollectionItemDo result) {
				resourceInfoPresenter.setResoruceDetails(result);
			}
		});
		setInSlot(COLLECTION_PLAYER_TOC_PRESENTER_SLOT, resourceInfoPresenter,false);
		new CustomAnimation(getView().getNavigationContainer()).run(400);
	}
	public void setCollectionShareView(String collectionId,String resourceId){
	   collectionSharePresenter.showShareView(resourceId!=null&&!resourceId.equalsIgnoreCase("")?true:false);
       if(resourceId!=null&&!resourceId.equalsIgnoreCase("")){
           CollectionItemDo collectionItemDo=getCollectionItemDo(resourceId);
           collectionSharePresenter.setResourceShareData(collectionItemDo.getResource().getGooruOid());
           collectionSharePresenter.showResourceData(collectionItemDo);
       }else{
    	   collectionSharePresenter.setCollectionShareData(collectionDo);
       }
       setInSlot(COLLECTION_PLAYER_TOC_PRESENTER_SLOT, collectionSharePresenter,false);
       new CustomAnimation(getView().getNavigationContainer()).run(400);
	}


	public void clearTabSlot(){
		clearSlot(COLLECTION_PLAYER_TOC_PRESENTER_SLOT);
	}

	public void enablePlayerButton(boolean isAddButtonEnable,boolean isInfoButtonEnable, boolean isShareButtonEnable, boolean isNarrationButtonEnable, boolean isNavigationButtonEnable,boolean isFlagButtonEnable){
		getView().enablePlayerButton(isAddButtonEnable,isInfoButtonEnable, isShareButtonEnable, isNarrationButtonEnable, isNavigationButtonEnable,isFlagButtonEnable);
	}


	@Override
	public void updateViewsCount() {
		updateResourceViewCount(collectionDo.getGooruOid(),collectionDo.getViews(),RESOURCE);
	}

	public void createPlayerDataLogs(){
		if(collectionActivityEventId!=null&&!collectionActivityEventId.isEmpty()){
			stopResourceDataLog();
			resetAnswerLists();
			createResourceDataLog();
			createSessionItem();
		}else{
			if(collectionDo!=null){
				collectionActivityEventId=GwtUUIDGenerator.uuid();
				collectionDataLogEventId=GwtUUIDGenerator.uuid();
				collectionNewDataLogEventId=GwtUUIDGenerator.uuid();
				collectionStartTime=PlayerDataLogEvents.getUnixTime();
				newCollectionStartTime=collectionStartTime;
				PlayerDataLogEvents.collectionPlayStartEvent(collectionDataLogEventId, PlayerDataLogEvents.COLLECTION_PLAY_EVENT_NAME, "", PlayerDataLogEvents.OPEN_SESSION_STATUS, collectionDo.getGooruOid(),
						PlayerDataLogEvents.START_EVENT_TYPE, collectionStartTime, collectionStartTime, 0L, AppClientFactory.getLoginSessionToken(), AppClientFactory.getGooruUid());
				startPlayerActivityEvent(collectionActivityEventId, "", PlayerConstants.COLLECTION_EVENT_NAME, collectionDo.getGooruOid(), collectionDo.getGooruOid(), PlayerConstants.COLLECTION_CONTEXT+collectionDo.getGooruOid(), getUserAgent());
				if(sessionIdCreationCount==1){
					sessionId=null;
				}
				String parentGooruOid=null,mode="collection";
				if(!AppClientFactory.getPlaceManager().getRequestParameter("cid","").equals("")){
					parentGooruOid=AppClientFactory.getPlaceManager().getRequestParameter("cid", null);
					mode="class";
				}
				createSession(collectionDo.getGooruOid(),parentGooruOid,mode);
				sessionIdCreationCount=1;
			}
		}
	}

	public void createResourceDataLog(){
		resourceActivityEventId=GwtUUIDGenerator.uuid();
		resourceActivityResourceId=collectionItemDo.getResource().getGooruOid();
		String resourceContext=PlayerConstants.COLLECTION_CONTEXT+collectionDo.getGooruOid()+"/"+resourceActivityResourceId;
		startPlayerActivityEvent(resourceActivityEventId, collectionActivityEventId, PlayerConstants.RESOURCE_EVENT_NAME, collectionDo.getGooruOid(), resourceActivityResourceId, resourceContext, getUserAgent());
		startResourceInsightDataLog();
	}

	public void stopResourceDataLog(){
		if(resourceActivityResourceId!=null){
			String resourceContext=PlayerConstants.COLLECTION_CONTEXT+collectionDo.getGooruOid()+"/"+resourceActivityResourceId;
			stopPlayerActivityEvent(resourceActivityEventId, collectionActivityEventId, PlayerConstants.RESOURCE_EVENT_NAME, collectionDo.getGooruOid(), resourceActivityResourceId, resourceContext, getUserAgent());
			stopResourceInsightDataLog();
			resourceActivityResourceId=null;
		}
	}

	public void stopResourceDataLogFromHomePage(){
		if(resourceActivityResourceId!=null){
			String resourceContext=PlayerConstants.COLLECTION_CONTEXT+collectionDo.getGooruOid()+"/"+resourceActivityResourceId;
			stopPlayerActivityEvent(resourceActivityEventId, collectionActivityEventId, PlayerConstants.RESOURCE_EVENT_NAME, collectionDo.getGooruOid(), resourceActivityResourceId, resourceContext, getUserAgent());
			stopResourceInsightDataLog();
			resourceActivityResourceId=null;
			stopCollectionDataLog();
			collectionActivityEventId=null;
			collectionEndTime=0L;
			totalTimeSpentOnSummaryPage=0L;
		}
	}

	public void stopCollectionDataLog(){
		if(collectionActivityEventId!=null){
			stopPlayerActivityEvent(collectionActivityEventId, "", PlayerConstants.COLLECTION_EVENT_NAME, collectionDo.getGooruOid(), collectionDo.getGooruOid(), PlayerConstants.COLLECTION_CONTEXT+collectionDo.getGooruOid(), getUserAgent());
			collectionEndTime=PlayerDataLogEvents.getUnixTime();
			PlayerDataLogEvents.collectionPlayStartEvent(collectionDataLogEventId, PlayerDataLogEvents.COLLECTION_PLAY_EVENT_NAME, "", PlayerDataLogEvents.OPEN_SESSION_STATUS, collectionDo.getGooruOid(),
					PlayerDataLogEvents.STOP_EVENT_TYPE, collectionStartTime, collectionEndTime, collectionEndTime-collectionStartTime-totalTimeSpentOnSummaryPage, AppClientFactory.getLoginSessionToken(), AppClientFactory.getGooruUid());
			triggerCollectionNewDataLogStartStopEvent(collectionStartTime,collectionEndTime,PlayerDataLogEvents.STOP_EVENT_TYPE,0);// TODO need to implement score
		}
	}

	public void stopCollectionDataLogs(){
		if(this.collectionDo!=null){
			stopResourceDataLogFromHomePage();
		}
	}

	public void startResourceInsightDataLog(){
		resourceDataLogEventId=GwtUUIDGenerator.uuid();
		resourceNewDataLogEventId=GwtUUIDGenerator.uuid();
		resourceStartTime=PlayerDataLogEvents.getUnixTime();
		if(collectionItemDo!=null && collectionItemDo.getResource()!=null){
			if(ASSESSMENT_QUESTION.equalsIgnoreCase(collectionItemDo.getResource().getResourceType().getName())){
				questionType=PlayerDataLogEvents.getQuestionType(collectionItemDo.getResource().getType());
				if(collectionItemDo.getResource().getType()==6){
					resourcePlayEventName=PlayerDataLogEvents.COLLECTION_RESOURCE_OE_EVENT_NAME;
				}else{
					resourcePlayEventName=PlayerDataLogEvents.COLLECTION_QUESTION_PLAY_EVENT_NAME;
				}
			}else{
				resourcePlayEventName=PlayerDataLogEvents.COLLECTION_RESOUCE_PLAY_EVENT_NAME;
			}
		}
		PlayerDataLogEvents.resourcePlayStartStopEvent(resourceDataLogEventId, resourcePlayEventName, collectionDataLogEventId,resourceActivityResourceId,collectionDo.getGooruOid(), PlayerDataLogEvents.START_EVENT_TYPE, resourceStartTime,
				resourceStartTime, 0L,AppClientFactory.getLoginSessionToken(), AppClientFactory.getGooruUid(),attemptTrySequence,attemptStatus, answerIds,oeQuestionAnswerText,oeQuestionAnswerText.length());
		triggerCollectionItemNewDataLogStartStopEvent(resourceActivityResourceId,resourceStartTime, resourceStartTime, PlayerDataLogEvents.START_EVENT_TYPE, 0,questionType);
	}

	public void stopResourceInsightDataLog(){
		stopHintOrExplanationEvent();
		Long resourceEndTime=PlayerDataLogEvents.getUnixTime();
		PlayerDataLogEvents.resourcePlayStartStopEvent(resourceDataLogEventId, resourcePlayEventName, collectionDataLogEventId,resourceActivityResourceId,collectionDo.getGooruOid(), PlayerDataLogEvents.STOP_EVENT_TYPE, resourceStartTime,
				resourceEndTime,resourceEndTime-resourceStartTime,AppClientFactory.getLoginSessionToken(), AppClientFactory.getGooruUid(),attemptTrySequence,attemptStatus, answerIds,oeQuestionAnswerText,oeQuestionAnswerText.length());
		triggerCollectionItemNewDataLogStartStopEvent(resourceActivityResourceId,resourceStartTime, resourceEndTime, PlayerDataLogEvents.STOP_EVENT_TYPE, 0, questionType);
	}

	public void createSessionItem(){
		if(collectionItemDo!=null){
			createSessionItem(sessionId, collectionItemDo.getCollectionItemId(), collectionItemDo.getResource().getGooruOid(),collectionItemDo.getResource().getTypeName(),"open");
		}
	}

	public void resetcollectionActivityEventId(){
		this.collectionActivityEventId=null;
		collectionEndTime=0L;
		totalTimeSpentOnSummaryPage=0L;
	}

	public void updateResourceViewCount(String gooruId,String viewsCount,String resourceType){
		this.playerAppService.updateViewCount(gooruId, viewsCount, resourceType, new SimpleAsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
				updatCollectionViewsCount();
			}
		});
	}
	public void updateResourceViewCount(String gooruId,String viewsCount,String resourceType,final String collectionItemId){
		this.playerAppService.updateViewCount(gooruId, viewsCount, resourceType, new SimpleAsyncCallback<String>() {
			@Override
			public void onSuccess(String result) {
					updateViewCount(collectionItemId);
			}
		});
	}

	public void startPlayerActivityEvent(String activityEventId,String activityParentEventId,String eventName,String gooruOid,String resourceGooruOid,
			String context,String userAgent){
		this.playerAppService.startActivityPlayerLog(activityEventId, activityParentEventId, eventName, gooruOid,
				resourceGooruOid, context, userAgent, new SimpleAsyncCallback<String>() {
			@Override
			public void onSuccess(String activityEventId) {

			}
		});
	}
	public void stopPlayerActivityEvent(String activityEventId,String activityParentEventId,String eventName,String gooruOid,String resourceGooruOid,
			String context,String userAgent){
	}

	public void createSession(String collectionGooruOid,String parentGooruOid,String mode){
//		this.playerAppService.createSessionTracker(collectionGooruOid, parentGooruOid,mode,new SimpleAsyncCallback<String>() {
//			@Override
//			public void onSuccess(String sessionId) {
				sessionId = GwtUUIDGenerator.uuid();
				PreviewPlayerPresenter.this.sessionId=sessionId;
				triggerCollectionNewDataLogStartStopEvent(collectionStartTime,collectionStartTime,PlayerDataLogEvents.START_EVENT_TYPE,0);
				createResourceDataLog();
				if(collectionItemDo!=null){
					createSessionItem(sessionId, collectionItemDo.getCollectionItemId(), collectionItemDo.getResource().getGooruOid(),collectionItemDo.getResource().getTypeName(),"open");
				}
//			}
//		});
	}

	public void createSessionItem(String sessionTrackerId,String collectionItemId, String resourceGooruOid,String questionType, String status){
		this.playerAppService.createSessionItemInCollection(sessionTrackerId, collectionItemId, resourceGooruOid,questionType, status, new SimpleAsyncCallback<String>() {
			@Override
			public void onSuccess(String sessionItemId) {
				PreviewPlayerPresenter.this.sessionItemId=sessionItemId;
			}
		});
	}

	public void createSessionItemAttempt(String contentGooruOid,int answerId, String attemptResult){
		this.playerAppService.createSessionItemAttemptTry(contentGooruOid,sessionId, sessionItemId, answerId, attemptResult, new SimpleAsyncCallback<String>() {
			@Override
			public void onSuccess(String sessionItemId) {}
		});
	}

	public void createSessionItemAttemptOe(String contentGooruOid,String answerId,String attemptStatus,String attemptAnswerResult){
		this.playerAppService.createSessionItemAttemptTryForOe(contentGooruOid,sessionId, sessionItemId, answerId,attemptStatus,attemptAnswerResult, new SimpleAsyncCallback<String>() {
			@Override
			public void onSuccess(String sessionItemId) {}
		});
	}

	public void updateSession(String sessionTrackerId){
		if(sessionTrackerId!=null){
			this.playerAppService.updateSessionInCollection(sessionTrackerId, new SimpleAsyncCallback<String>() {
				@Override
				public void onSuccess(String sessionItemId) {}
			});
		}
	}

	public void setUserAttemptResult(String collectionItemId,AttemptedAnswersDo attemptAnswersDo){
		attemptAnswersMap.put(collectionItemId, attemptAnswersDo);
	}

	public void removeUserAttemptResult(){
		attemptAnswersMap.remove(collectionItemDo.getCollectionItemId());
	}

	public Map<String, AttemptedAnswersDo> getAttemptAnswersMap() {
		return attemptAnswersMap;
	}

	public void increaseUserAttemptCount(){
		setAttemptCount(getAttemptCount()+1);
	}

	public String getUserAgent(){
		return Window.Navigator.getUserAgent();
	}

	public void resetAnswerLists(){
		attemptTrySequence.clear();
		attemptStatus.clear();
		answerIds.clear();
		oeQuestionAnswerText="";
		isExplanationUsed=false;
		hintId=0;
		hintOrExplanationEventName=null;
		questionType="RES";
		hintIdsObject=new JSONObject();
		explanationIdsObject=new JSONObject();
		answerIdsObject=new JSONObject();
		answerObjectArray.clear();
		attemptStatusArray.clear();
		attemptTrySequenceArray.clear();
		setResourceScore(0);
		setAttemptCount(0);
	}

	public void setAnswerAttemptSequence(int attemptSequence,int attemptStatus,int answerId){
		this.attemptTrySequence.add(attemptSequence);
		this.attemptStatus.add(attemptStatus);
		this.answerIds.add(answerId);
	}

	public void setOeQuestionAnswerText(String oeAnswerText){
		this.oeQuestionAnswerText=oeAnswerText;
	}
	public void startHintDataLogEvent(int hintId){
		stopHintOrExplanationEvent();
		this.hintId=hintId;
		hintOrExplanationStartTime=PlayerDataLogEvents.getUnixTime();
		hintOrExplanationEventId=GwtUUIDGenerator.uuid();
		if(collectionItemDo!=null && collectionItemDo.getResource()!=null){
			if(ASSESSMENT_QUESTION.equalsIgnoreCase(collectionItemDo.getResource().getResourceType().getName())){
				if(collectionItemDo.getResource().getType()==6){
					hintOrExplanationEventName=PlayerDataLogEvents.COLLECTION_RESOURCE_OE_HINT_EVENT_NAME;
				}else{
					hintOrExplanationEventName=PlayerDataLogEvents.COLLECTION_RESOURCE_HINT_EVENT_NAME;
				}
			}
		}
		PlayerDataLogEvents.hintsButtonDataLogEvent(hintOrExplanationEventId, hintOrExplanationEventName, resourceDataLogEventId, resourceActivityResourceId,
				collectionDo.getGooruOid(), PlayerDataLogEvents.START_EVENT_TYPE, hintOrExplanationStartTime, hintOrExplanationStartTime, 0L, AppClientFactory.getLoginSessionToken(),
				AppClientFactory.getGooruUid(), 0, hintId,attemptTrySequence,attemptStatus, answerIds,oeQuestionAnswerText,oeQuestionAnswerText.length(),isExplanationUsed);
	}


	public void startExplanationDataLogEvent(){
		stopHintOrExplanationEvent();
		isExplanationUsed=true;
		hintOrExplanationEventId=GwtUUIDGenerator.uuid();
		hintOrExplanationStartTime=PlayerDataLogEvents.getUnixTime();
		if(collectionItemDo!=null && collectionItemDo.getResource()!=null){
			if(ASSESSMENT_QUESTION.equalsIgnoreCase(collectionItemDo.getResource().getResourceType().getName())){
				if(collectionItemDo.getResource().getType()==6){
					hintOrExplanationEventName=PlayerDataLogEvents.COLLECTION_RESOURCE_OE_EXPLANATION_EVENT_NAME;
				}else{
					hintOrExplanationEventName=PlayerDataLogEvents.COLLECTION_RESOURCE_EXPLANATION_EVENT_NAME;
				}
			}
		}
		PlayerDataLogEvents.explanationButtonDataLogEvent(hintOrExplanationEventId, hintOrExplanationEventName, resourceDataLogEventId, resourceActivityResourceId,
				collectionDo.getGooruOid(), PlayerDataLogEvents.START_EVENT_TYPE, hintOrExplanationStartTime,hintOrExplanationStartTime, 0L, AppClientFactory.getLoginSessionToken(),
				AppClientFactory.getGooruUid(), isExplanationUsed,attemptTrySequence,attemptStatus, answerIds,oeQuestionAnswerText,oeQuestionAnswerText.length(),hintId);
	}

	public void saveOeQuestionAnswerDataLogEvent(){
		stopHintOrExplanationEvent();
		hintOrExplanationEventName=null;
		String submitEventId=GwtUUIDGenerator.uuid();
		Long answerEndTime=PlayerDataLogEvents.getUnixTime();
		PlayerDataLogEvents.submitOeAnswerDataLogEvent(submitEventId, PlayerDataLogEvents.COLLECTION_RESOURCE_OE_SUBMIT_EVENT_NAME, resourceDataLogEventId,
				resourceActivityResourceId,resourceStartTime,answerEndTime, answerEndTime-resourceStartTime,
				AppClientFactory.getLoginSessionToken(), AppClientFactory.getGooruUid(),attemptTrySequence,attemptStatus, answerIds,oeQuestionAnswerText,oeQuestionAnswerText.length(),collectionDo.getGooruOid());
	}

	public void stopHintOrExplanationEvent(){
		if(hintOrExplanationEventName!=null){
			Long endTime=PlayerDataLogEvents.getUnixTime();
			Long spendTime=endTime-hintOrExplanationStartTime;
			if(hintOrExplanationEventName.equals(PlayerDataLogEvents.COLLECTION_RESOURCE_EXPLANATION_EVENT_NAME)){
				PlayerDataLogEvents.explanationButtonDataLogEvent(hintOrExplanationEventId, PlayerDataLogEvents.COLLECTION_RESOURCE_EXPLANATION_EVENT_NAME, resourceDataLogEventId, resourceActivityResourceId,
						collectionDo.getGooruOid(), PlayerDataLogEvents.STOP_EVENT_TYPE, hintOrExplanationStartTime,endTime, spendTime, AppClientFactory.getLoginSessionToken(),
						AppClientFactory.getGooruUid(), true,attemptTrySequence,attemptStatus, answerIds,oeQuestionAnswerText,oeQuestionAnswerText.length(),hintId);
			}else if(hintOrExplanationEventName.equals(PlayerDataLogEvents.COLLECTION_RESOURCE_HINT_EVENT_NAME)){
				PlayerDataLogEvents.hintsButtonDataLogEvent(hintOrExplanationEventId, PlayerDataLogEvents.COLLECTION_RESOURCE_HINT_EVENT_NAME, resourceDataLogEventId, resourceActivityResourceId,
						collectionDo.getGooruOid(), PlayerDataLogEvents.STOP_EVENT_TYPE, hintOrExplanationStartTime, endTime, spendTime,  AppClientFactory.getLoginSessionToken(),
						AppClientFactory.getGooruUid(), 0, hintId,attemptTrySequence,attemptStatus, answerIds,oeQuestionAnswerText,oeQuestionAnswerText.length(),isExplanationUsed);
			}
			else if(hintOrExplanationEventName.equals(PlayerDataLogEvents.COLLECTION_RESOURCE_OE_EXPLANATION_EVENT_NAME)){
				PlayerDataLogEvents.explanationButtonDataLogEvent(hintOrExplanationEventId, PlayerDataLogEvents.COLLECTION_RESOURCE_OE_EXPLANATION_EVENT_NAME, resourceDataLogEventId, resourceActivityResourceId,
						collectionDo.getGooruOid(), PlayerDataLogEvents.STOP_EVENT_TYPE, hintOrExplanationStartTime,endTime, spendTime, AppClientFactory.getLoginSessionToken(),
						AppClientFactory.getGooruUid(), true,attemptTrySequence,attemptStatus, answerIds,oeQuestionAnswerText,oeQuestionAnswerText.length(),hintId);
			}else if(hintOrExplanationEventName.equals(PlayerDataLogEvents.COLLECTION_RESOURCE_OE_HINT_EVENT_NAME)){
				PlayerDataLogEvents.hintsButtonDataLogEvent(hintOrExplanationEventId, PlayerDataLogEvents.COLLECTION_RESOURCE_OE_HINT_EVENT_NAME, resourceDataLogEventId, resourceActivityResourceId,
						collectionDo.getGooruOid(), PlayerDataLogEvents.STOP_EVENT_TYPE, hintOrExplanationStartTime, endTime, spendTime, AppClientFactory.getLoginSessionToken(),
						AppClientFactory.getGooruUid(), 0, hintId,attemptTrySequence,attemptStatus, answerIds,oeQuestionAnswerText,oeQuestionAnswerText.length(),isExplanationUsed);
			}
		}
	}

	public void getProfilUserVisibility(final String gooruUid){
		this.playerAppService.getUserProfileVisibility(gooruUid, new SimpleAsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				if(result){
					metadataPresenter.setUserProfileName(gooruUid);
				}
			}
		});
	}
	public void getContentReport(String associatedGooruOid) {
		playerAppService.getContentReport(associatedGooruOid, AppClientFactory.getGooruUid(), new SimpleAsyncCallback<ArrayList<ContentReportDo>>() {
			@Override
			public void onSuccess(ArrayList<ContentReportDo> result) {
				String gooruFlagId="";
				if(result!=null&&result.size()>0){
					for(int i =0;i<result.size();i++){
						if(result.get(i).getDeleteContentGooruOid()!=null){
							gooruFlagId = gooruFlagId+result.get(i).getDeleteContentGooruOid();
							if(result.size()!=(i+1)){
								gooruFlagId=gooruFlagId+",";
							}
						}
					}
					getView().makeFlagButtonOrange();
					resourceFlagPresenter.setContentDeleteIds(gooruFlagId);
				 }
			}
		});


	}

	@Override
	public String getViewToken() {
		return PlaceTokens.PREVIEW_PLAY;
	}


	@Override
	public void showLoginPopupWidget(String widgetMode){
		LoginPopupUc popup =new  LoginPopupUc() {
			@Override
			public void onLoginSuccess() {

			}
		};
		popup.setWidgetMode(widgetMode);
		popup.setGlassEnabled(true);
	}

	public void updateAddResourceCollectionWidget(String collectionId){
		addResourcePresenter.updateWorkSpaceLink(collectionId);
	}

	@Override
	public void refreshCollectionInShelfListInPlay(String collectionId) {
		updateAddResourceCollectionWidget(collectionId);
	}

	@Override
	public void updateResourceThumbsRating(final int userThumbsRataing) {
		String resourceGooruOid=getPlaceManager().getRequestParameter("rid", null);
		if(resourceGooruOid!=null){
		 CollectionItemDo colectionItemDo=getCollectionItemDo(resourceGooruOid);
		 resourceGooruOid=colectionItemDo.getResource().getGooruOid();
		}else{
			resourceGooruOid=getPlaceManager().getRequestParameter("id", null);
		}
	}

	public void updateThumbsRatingView(int userThumbsRataing) {
		updateResourceLikes(userThumbsRataing);
	}

	public void updateResourceLikes(int userThumbsRataing){
		String resourceGooruOid=getPlaceManager().getRequestParameter("rid", null);
		if(resourceGooruOid!=null){
		 CollectionItemDo collectionItemDo=getCollectionItemDo(resourceGooruOid);
		    int resourceLikes=collectionItemDo.getRating().getVotesUp();
			int userRating=collectionItemDo.getResource().getUserRating();
			if(userThumbsRataing==1){
				resourceLikes=resourceLikes+1;
			}else if((userThumbsRataing==0||userThumbsRataing==-1)&&userRating==1){
				resourceLikes=resourceLikes-1;
			}
			resourceLikes=resourceLikes<0?0:resourceLikes;
			resourceInfoPresenter.updateLikesCount(resourceLikes);
			collectionItemDo.getResource().setUserRating(userThumbsRataing);
			collectionItemDo.getRating().setVotesUp(resourceLikes);
		}else{
			resourceGooruOid=getPlaceManager().getRequestParameter("id", null);
			 int resourceLikes=collectionDo.getMetaInfo().getRating().getVotesUp();
			 int userRating=collectionDo.getUserRating();
			if(userThumbsRataing==1){
				resourceLikes=resourceLikes+1;
			}else if((userThumbsRataing==0||userThumbsRataing==-1)&&userRating==1){
				resourceLikes=resourceLikes-1;
			}
			resourceLikes=resourceLikes<0?0:resourceLikes;
			metadataPresenter.updateLikesCount(resourceLikes);
			collectionDo.setUserRating(userThumbsRataing);
			collectionDo.getMetaInfo().getRating().setVotesUp(resourceLikes);
		}
	}

	@Override
	public void showTabWidget(String widgetMode,boolean isLoginRequestCancel) {
		Map<String,String> params = new LinkedHashMap<String,String>();
		params.put("id", collectionDo.getGooruOid());
		params = PreviewPlayerPresenter.setConceptPlayerParameters(params);
		if(isLoginRequestCancel){
			String collectionItemId=getPlaceManager().getRequestParameter("rid", null);
			if(collectionItemId!=null){
				params.put("rid", collectionItemDo.getCollectionItemId());
				PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
				AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
			}else{
				String view=getPlaceManager().getRequestParameter("view", null);
				if(view!=null){
					params.put("view", "end");
				}
				PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(PlaceTokens.PREVIEW_PLAY, params);
				AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, true);
			}
		}else if(widgetMode==null && !isLoginRequestCancel ){//This will handle the click event on the signup button (as we are not passing any widget).
			metadataPresenter.setPlayerLoginStatusHandler(true);//This will enable the comments.
		}
		else if(!isLoginRequestCancel&&COLLECTION_RESOURCE_THUMBS_WIDGET_MODE.equalsIgnoreCase(widgetMode)){
		}else if(!isLoginRequestCancel&&COLLECTION_RESOURCE_FLAG.equalsIgnoreCase(widgetMode)){
			String collectionItemId=getPlaceManager().getRequestParameter("rid", null);
			CollectionItemDo collectionItemDo=getCollectionItemDo(collectionItemId);
			isResourceContentReported(collectionItemDo.getResource().getGooruOid());
			setResourceFlagView(collectionItemId);
			metadataPresenter.setPlayerLoginStatusHandler(true);
		}else if(!isLoginRequestCancel&&COLLECTION_RESOURCE_ADD_WIDGET.equalsIgnoreCase(widgetMode)){
			String collectionItemId=getPlaceManager().getRequestParameter("rid", null);
			if(collectionItemId!=null){
				CollectionItemDo collectionItemDo=getCollectionItemDo(collectionItemId);
				isResourceContentReported(collectionItemDo.getResource().getGooruOid());
				setAddResourcesView(getPlaceManager().getRequestParameter("id", null), collectionItemId);
			}else{
				setAddCollectionView(getPlaceManager().getRequestParameter("id", null));
			}
			metadataPresenter.setPlayerLoginStatusHandler(true);
		}else if(!isLoginRequestCancel&&COLLECTION_FLAG.equalsIgnoreCase(widgetMode)){
			metadataPresenter.getFlagedReport(collectionDo.getGooruOid());
			setCollectionFlagView(collectionDo.getGooruOid());
			metadataPresenter.setPlayerLoginStatusHandler(true);
		}else if(!isLoginRequestCancel&&REACTIONWIDGET.equalsIgnoreCase(widgetMode)){
			isResourceContentReaction(collectionItemDo.getResource().getGooruOid());
			metadataPresenter.setPlayerLoginStatusHandler(true);
		}else if(!isLoginRequestCancel&&COLLECTION_COMMENTS.equalsIgnoreCase(widgetMode)){
			metadataPresenter.setPlayerLoginStatusHandler(true);
		}else if(!isLoginRequestCancel&&STAR_RATING_WIDGET.equalsIgnoreCase(widgetMode)){
			isResourceContentRating(collectionItemDo.getResource().getGooruOid());
		}
	}

	/**
	 * Gets the respective resource ratings rated by the user.
	 * @param resourceGooruId {@link String}
	 */
	private void isResourceContentRating(String resourceGooruId) {
		if(!AppClientFactory.isAnonymous()){
			if(isChildAccount()){
				resoruceMetadataPresenter.childLoggedIn(true);
			}else{
				resoruceMetadataPresenter.childLoggedIn(false);
				getContentRating(resourceGooruId);
			}
		}
	}


	private void isResourceContentReaction(String resourceGooruId) {
		if(!AppClientFactory.isAnonymous()){
			getContentReaction(resourceGooruId);
		}
	}

	/**
	 * Get reaction API is called and gets respective reaction.
	 * @param collectionItemDo {@link CollectionItemDo}
	 */
	private void getContentReaction(String resourceGooruId) {
		AppClientFactory.getInjector().getPlayerAppService().getResourceReaction(collectionItemDo.getResource().getGooruOid(),AppClientFactory.getGooruUid(), new SimpleAsyncCallback<ArrayList<ReactionDo>>() {
			@Override
			public void onSuccess(ArrayList<ReactionDo> result) {
				String gooruReactionId="";
				if(result.size()==0){
					resoruceMetadataPresenter.getView().setDefaultReaction();
				}else{
					for(int i =0;i<result.size();i++){
						gooruReactionId = gooruReactionId+result.get(i).getDeleteReactionGooruOid();
						if(result.size()!=(i+1)){
							gooruReactionId=gooruReactionId+",";
						}
					}
					resoruceMetadataPresenter.getView().setReaction(result.get(0),gooruReactionId);
				}

			}
		});
	}

	/**
	 * Get ratings API is called and gets respective ratings.
	 * @param resourceGooruId {@link String}
	 */
	private void getContentRating(String resourceGooruId) {
		AppClientFactory.getInjector().getPlayerAppService().getResourceRatingWithReviews(collectionItemDo.getResource().getGooruOid(), AppClientFactory.getGooruUid(),0, new SimpleAsyncCallback<ArrayList<StarRatingsDo>>() {
			@Override
			public void onSuccess(ArrayList<StarRatingsDo> result) {
				if(result!=null&&result.size()>0){
					resoruceMetadataPresenter.getView().setUserStarRatings(result.get(0),false);
				}else{
					resoruceMetadataPresenter.getView().setUserStarRatings(null,false);
				}

			}
		});

	}


	public void updateFlagImageOnHomeView(){
		metadataPresenter.getFlagedReport(collectionDo.getGooruOid());
	}
	public void getCollection(){
		String collectionId=getPlaceManager().getRequestParameter("id", null);
		final String rootNodeId=getPlaceManager().getRequestParameter("rootNodeId", null);
		 this.playerAppService.getSimpleCollectionDetils(null,collectionId,null,null, rootNodeId, new SimpleAsyncCallback<CollectionDo>() {
 			@Override
 			public void onSuccess(CollectionDo collectionDo) {
				if(collectionDo.getStatusCode()!=200){
					showCollectionErrorMessage();
				}else{
					PreviewPlayerPresenter.this.collectionDo=collectionDo;
	 				updateThumbsRatingView(collectionDo.getUserRating());
				}
 			}
 		});
	}

	public void updateFlagImageOnEndView(){
		metadataPresenter.getFlagedReport(collectionDo.getGooruOid());
	}

	/**
	 * Sets collection not exists view.
	 */

	protected void showCollectionErrorMessage(){
		clearSlot(METADATA_PRESENTER_SLOT);
		enablePlayerButton(false, false, false, false, false, false);
		setOpenEndedAnswerSubmited(true);
		getView().getPlayerBodyContainer().clear();
		getView().getPlayerBodyContainer().add(new CollectionNonExistView());
	}
	public void resetCollectionPlayer(){
		if(collectionDo!=null){
			AppClientFactory.fireEvent(new RefreshUserShelfCollectionsEvent());
			stopCollectionDataLogs();
			getView().hidePlayerButtons(true, "");
			getView().hideStudentViewButton();
			collectionDo=null;
			collectionItemDo=null;
		    collectionMetadataId=null;
		    collectionSummaryId=null;
		    collectionActivityEventId=null;
		    resourceActivityEventId=null;
		    resourceActivityResourceId=null;
		    sessionId=null;
		    sessionItemId=null;
		    collectionDataLogEventId=null;
		    resourceDataLogEventId=null;
		    collectionEndTime=0L;
			totalTimeSpentOnSummaryPage=0L;
		    collectionStartTime=0L;
		    newCollectionStartTime=0L;
		    resourceStartTime=0L;
		    hintOrExplanationStartTime=0L;
		    hintOrExplanationEventName=null;
		    hintOrExplanationEventId=null;
		    resourcePlayEventName=null;
		    attemptAnswersMap.clear();
		    attemptTrySequence.clear();
		    attemptStatus.clear();
		    answerIds.clear();
		    oeQuestionAnswerText="";
			hintId=0;
			questionType="RES";
			hintIdsObject=new JSONObject();
			explanationIdsObject=new JSONObject();
			answerIdsObject=new JSONObject();
			answerObjectArray.clear();
			attemptStatusArray.clear();
			attemptTrySequenceArray.clear();
			setResourceScore(0);
			setAttemptCount(0);
			setCollectionScore(0);
			isExplanationUsed=false;
			sessionIdCreationCount=0;
			getView().setResourceTitle("");
			enablePlayerButton(false, false, false, false, false,false);
			clearTabSlot();
			clearIframeContent();
			clearSlot(METADATA_PRESENTER_SLOT);
			metadataPresenter.resetMetadataFields();
			resoruceMetadataPresenter.resetResourceMetaData();
			resourceInfoPresenter.resetResourceInfo();
			collectionPlayerTocPresenter.clearNavigationPanel();
			setOpenEndedAnswerSubmited(false);
		}

	}
	private class ShowResourceView{
		public ShowResourceView(final PlaceRequest resourceRequest){
			if(!isOpenEndedAnswerSubmited()){
				new NavigationConfirmPopup() {
					@Override
					public void navigateToNextResource() {
						super.hide();
						AppClientFactory.getPlaceManager().revealPlace(false, resourceRequest,true);
					}
				};
			}else{
				AppClientFactory.getPlaceManager().revealPlace(false, resourceRequest,true);
			}
		}
	}
	public static native void pauseVideo(Element myPlayer) /*-{
 		myPlayer.pauseVideo();
	}-*/;
	public static native void playVideo(Element myPlayer) /*-{
  	myPlayer.playVideo();
   }-*/;

	@Override
	public void closePreviewPlayer(boolean isClose) {
		getView().closePreviewPlayer();
	}
	public void clearIframeContent(){
		resoruceMetadataPresenter.clearIfrmaeContent();
	}

	public static Map<String,String> setConceptPlayerParameters(Map<String,String> params) {
		String classpageItemId=AppClientFactory.getPlaceManager().getRequestParameter("cid", null);
		String subject = AppClientFactory.getPlaceManager().getRequestParameter("subject", null);
		String lessonId = AppClientFactory.getPlaceManager().getRequestParameter("lessonId", null);
		String library = AppClientFactory.getPlaceManager().getRequestParameter("library", null);
		String page=AppClientFactory.getPlaceManager().getRequestParameter("page", null);
		String rootNodeId=AppClientFactory.getPlaceManager().getRequestParameter("rootNodeId", null);
		String libraryGooruOid=AppClientFactory.getPlaceManager().getRequestParameter("lid", null);
		String eventId=AppClientFactory.getPlaceManager().getRequestParameter("eventid", null);
		String courseId=AppClientFactory.getPlaceManager().getRequestParameter("courseId", null);
		String unitId=AppClientFactory.getPlaceManager().getRequestParameter("unitId", null);
		String isStudent=AppClientFactory.getPlaceManager().getRequestParameter("isStudent", null);

		String folderId = AppClientFactory.getPlaceManager().getRequestParameter("folderId",null);
		String folderItemId = AppClientFactory.getPlaceManager().getRequestParameter("folderItemId",null);

		if(classpageItemId!=null){
			params.put("cid", classpageItemId);
		}
		if(page!=null){
			params.put("page", page);
		}
		if(subject!=null) {
			params.put("subject", subject);
		}
		if(lessonId!=null) {
			params.put("lessonId", lessonId);
		}
		if(library!=null) {
			params.put("library", library);
		}
		if(rootNodeId!=null) {
			params.put("rootNodeId", rootNodeId);
		}
		if(libraryGooruOid!=null) {
			params.put("lid", libraryGooruOid);
		}
		if(eventId!=null) {
			params.put("eventid", eventId);
		}
		if(folderId!=null) {
			params.put("folderId", folderId);
		}
		if(folderItemId!=null) {
			params.put("folderItemId", folderItemId);
		}
		if(unitId!=null) {
			params.put("unitId", unitId);
		}
		if(courseId!=null) {
			params.put("courseId", courseId);
		}
		if(isStudent!=null) {
			params.put("isStudent", isStudent);
		}
		return params;
	}

	public static String setConceptPlayerParameters() {
		String anchorLink = "";
		String classpageItemId=AppClientFactory.getPlaceManager().getRequestParameter("cid", null);
		String subject = AppClientFactory.getPlaceManager().getRequestParameter("subject", null);
		String lessonId = AppClientFactory.getPlaceManager().getRequestParameter("lessonId", null);
		String folderId = AppClientFactory.getPlaceManager().getRequestParameter("folderId", null);
		String folderItemId = AppClientFactory.getPlaceManager().getRequestParameter("folderItemId", null);
		String library = AppClientFactory.getPlaceManager().getRequestParameter("library", null);
		String page=AppClientFactory.getPlaceManager().getRequestParameter("page", null);
		String rootNodeId=AppClientFactory.getPlaceManager().getRequestParameter("rootNodeId", null);
		String libraryGooruOid=AppClientFactory.getPlaceManager().getRequestParameter("lid", null);
		String eventId=AppClientFactory.getPlaceManager().getRequestParameter("eventid", null);
		String unitId=AppClientFactory.getPlaceManager().getRequestParameter("unitId", null);
		String isStudent=AppClientFactory.getPlaceManager().getRequestParameter("isStudent", null);
		String courseId=AppClientFactory.getPlaceManager().getRequestParameter("courseId", null);
		if(classpageItemId!=null){
			anchorLink += "&cid="+classpageItemId;
		}
		if(page!=null){
			anchorLink += "&page="+page;
		}
		if(subject!=null) {
			anchorLink += "&subject="+subject;
		}
		if(lessonId!=null) {
			anchorLink += "&lessonId="+lessonId;
		}
		if(folderId!=null) {
			anchorLink += "&folderId="+folderId;
		}
		if(folderItemId!=null) {
			anchorLink += "&folderItemId="+folderItemId;
		}
		if(library!=null) {
			anchorLink += "&library="+library;
		}
		if(rootNodeId!=null) {
			anchorLink += "&rootNodeId="+rootNodeId;
		}
		if(libraryGooruOid!=null) {
			anchorLink += "&lid="+libraryGooruOid;
		}
		if(eventId!=null) {
			anchorLink += "&eventid="+eventId;
		}

		if(unitId!=null) {
			anchorLink += "&unitId="+unitId;
		}

		if(isStudent!=null) {
			anchorLink += "&isStudent="+isStudent;
		}

		if(courseId!=null) {
			anchorLink += "&courseId="+courseId;
		}

		return anchorLink;
	}

	/**
	 * @return the isUserAttemptedAnswer
	 */
	public boolean isUserAttemptedAnswer() {
		return isUserAttemptedAnswer;
	}

	/**
	 * @param isUserAttemptedAnswer the isUserAttemptedAnswer to set
	 */
	public void setUserAttemptedAnswer(boolean isUserAttemptedAnswer) {
		this.isUserAttemptedAnswer = isUserAttemptedAnswer;
	}

   /**
	 * @return the userAttemptedQuestionType
	 */
	public int getUserAttemptedQuestionType() {
		return userAttemptedQuestionType;
	}

	/**
	 * @param userAttemptedQuestionType the userAttemptedQuestionType to set
	 */
	public void setUserAttemptedQuestionType(int userAttemptedQuestionType) {
		this.userAttemptedQuestionType = userAttemptedQuestionType;
	}

	public void setUserAttemptedQuestionTypeAndStatus(boolean isUserAttemptedThisQuestion,int questionType){
		setUserAttemptedAnswer(isUserAttemptedThisQuestion);
		setUserAttemptedQuestionType(questionType);
	}
	public void triggerSaveOeAnswerTextDataEvent(){
		String oeDataLogEventId=GwtUUIDGenerator.uuid();
		Long oeStartTime=PlayerDataLogEvents.getUnixTime();
		triggerSaveOeAnswerTextDataEvent(oeDataLogEventId,resourceActivityResourceId,oeStartTime,oeStartTime,0);
	}
	public void triggerCollectionNewDataLogStartStopEvent(Long collectionStartTime,Long collectionEndTime,String eventType,Integer score){
		JSONObject collectionDataLog=new JSONObject();
		collectionDataLog.put(PlayerDataLogEvents.EVENTID, new JSONString(collectionNewDataLogEventId));
		collectionDataLog.put(PlayerDataLogEvents.EVENTNAME, new JSONString(PlayerDataLogEvents.COLLECTION_PLAY));
		collectionDataLog.put(PlayerDataLogEvents.SESSION, PlayerDataLogEvents.getDataLogSessionObject(sessionId));
		collectionDataLog.put(PlayerDataLogEvents.STARTTIME, new JSONNumber(collectionStartTime));
		collectionDataLog.put(PlayerDataLogEvents.ENDTIME, new JSONNumber(collectionEndTime));
		collectionDataLog.put(PlayerDataLogEvents.USER, PlayerDataLogEvents.getDataLogUserObject());
		String classpageId=AppClientFactory.getPlaceManager().getDataLogClasspageId();
		String classpageEventId=AppClientFactory.getPlaceManager().getClasspageEventId();
		String path="";
		if(classpageId!=null&&!classpageId.equals("")){
			path=classpageId+"/"+collectionDo.getGooruOid();
		}else{
			path=AppClientFactory.getPlaceManager().getFolderIds()+collectionDo.getGooruOid();
		}
		String playerMode=AppClientFactory.getPlaceManager().getPlayerMode();
		int totalQuestionsCount = "start".equalsIgnoreCase(eventType) ? 0 : collectionDo.getCollectionItems().size();
		collectionDataLog.put(PlayerDataLogEvents.CONTEXT, PlayerDataLogEvents.getDataLogContextObject(collectionDo.getGooruOid(), classpageId, classpageEventId, eventType, playerMode,"",null,path,null, totalQuestionsCount));
		collectionDataLog.put(PlayerDataLogEvents.VERSION,PlayerDataLogEvents.getDataLogVersionObject());
		int viewCount = "start".equalsIgnoreCase(eventType) ? 0 : 1;
		collectionDataLog.put(PlayerDataLogEvents.METRICS,PlayerDataLogEvents.getDataLogMetricsObject(collectionEndTime-newCollectionStartTime, getCollectionScore(), viewCount));
		collectionDataLog.put(PlayerDataLogEvents.PAYLOADOBJECT,new JSONString(new JSONObject().toString()));
		PlayerDataLogEvents.collectionStartStopEvent(collectionDataLog);
	}
	public void triggerCollectionItemNewDataLogStartStopEvent(String resourceId,Long resourceStartTime,Long resourceEndTime,String eventType,Integer score,String questionType){
		JSONObject collectionDataLog=new JSONObject();
		collectionDataLog.put(PlayerDataLogEvents.EVENTID, new JSONString(resourceNewDataLogEventId));
		collectionDataLog.put(PlayerDataLogEvents.EVENTNAME, new JSONString(PlayerDataLogEvents.COLLECTION_RESOURCE_PLAY));
		collectionDataLog.put(PlayerDataLogEvents.SESSION, PlayerDataLogEvents.getDataLogSessionObject(sessionId));
		collectionDataLog.put(PlayerDataLogEvents.STARTTIME, new JSONNumber(resourceStartTime));
		collectionDataLog.put(PlayerDataLogEvents.ENDTIME, new JSONNumber(resourceEndTime));
		collectionDataLog.put(PlayerDataLogEvents.USER, PlayerDataLogEvents.getDataLogUserObject());
		String questionTypeString=questionType.equals("RES")?"resource":"question";
		String classpageId=AppClientFactory.getPlaceManager().getDataLogClasspageId();
		String path="";
		if(classpageId!=null&&!classpageId.equals("")){
			path=classpageId+"/"+collectionDo.getGooruOid()+"/"+resourceId;
		}else{
			path=AppClientFactory.getPlaceManager().getFolderIds()+collectionDo.getGooruOid()+"/"+resourceId;
		}
		String playerMode=AppClientFactory.getPlaceManager().getPlayerMode();
		int totalQuestionsCount = "start".equalsIgnoreCase(eventType) ? 0 : collectionDo.getCollectionItems().size();
		collectionDataLog.put(PlayerDataLogEvents.CONTEXT, PlayerDataLogEvents.getDataLogContextObject(resourceId,collectionDo.getGooruOid(), collectionNewDataLogEventId, eventType, playerMode,questionTypeString,null,path,null, totalQuestionsCount));
		collectionDataLog.put(PlayerDataLogEvents.VERSION,PlayerDataLogEvents.getDataLogVersionObject());
		int viewCount = "start".equalsIgnoreCase(eventType) ? 0 : 1;
		collectionDataLog.put(PlayerDataLogEvents.METRICS,PlayerDataLogEvents.getDataLogMetricsObject(resourceEndTime-resourceStartTime, getResourceScore(), viewCount));
		collectionDataLog.put(PlayerDataLogEvents.PAYLOADOBJECT,PlayerDataLogEvents.getDataLogPayLoadObject(questionType,oeQuestionAnswerText,attemptStatusArray,attemptTrySequenceArray,answerIdsObject,hintIdsObject,explanationIdsObject,getAttemptCount(),answerObjectArray,null));
		PlayerDataLogEvents.collectionStartStopEvent(collectionDataLog);
	}
	public void triggerSaveOeAnswerTextDataEvent(String eventId,String resourceId,Long oeStartTime,Long oeEndTime,int score){
		JSONObject collectionDataLog=new JSONObject();
		collectionDataLog.put(PlayerDataLogEvents.EVENTID, new JSONString(eventId));
		collectionDataLog.put(PlayerDataLogEvents.EVENTNAME, new JSONString(PlayerDataLogEvents.COLLECTION_RESOURCE_SAVE));
		collectionDataLog.put(PlayerDataLogEvents.SESSION, PlayerDataLogEvents.getDataLogSessionObject(sessionId));
		collectionDataLog.put(PlayerDataLogEvents.STARTTIME, new JSONNumber(oeStartTime));
		collectionDataLog.put(PlayerDataLogEvents.ENDTIME, new JSONNumber(oeEndTime));
		collectionDataLog.put(PlayerDataLogEvents.USER, PlayerDataLogEvents.getDataLogUserObject());
		String classpageId=AppClientFactory.getPlaceManager().getDataLogClasspageId();
		String path="";
		if(classpageId!=null&&!classpageId.equals("")){
			path=classpageId+"/"+collectionDo.getGooruOid()+"/"+resourceId;
		}else{
			path=AppClientFactory.getPlaceManager().getFolderIds()+collectionDo.getGooruOid()+"/"+resourceId;
		}
		String playerMode=AppClientFactory.getPlaceManager().getPlayerMode();
		collectionDataLog.put(PlayerDataLogEvents.CONTEXT, PlayerDataLogEvents.getDataLogContextObject(resourceId,collectionDo.getGooruOid(), resourceNewDataLogEventId, "", playerMode,"question",null,path,null,0));
		collectionDataLog.put(PlayerDataLogEvents.VERSION,PlayerDataLogEvents.getDataLogVersionObject());
		collectionDataLog.put(PlayerDataLogEvents.METRICS,PlayerDataLogEvents.getDataLogMetricsObject(oeEndTime-oeStartTime, 0,0));
		collectionDataLog.put(PlayerDataLogEvents.PAYLOADOBJECT,PlayerDataLogEvents.getDataLogPayLoadObject(questionType,oeQuestionAnswerText,attemptStatusArray,attemptTrySequenceArray,answerIdsObject,hintIdsObject,explanationIdsObject,getAttemptCount(),answerObjectArray,null));
		PlayerDataLogEvents.collectionStartStopEvent(collectionDataLog);
	}
	public void triggerReactiontDataLogEvent(String resourceId,Long reactionStartTime,Long reactionEndTime,String reactionType,String eventName){
		JSONObject collectionDataLog=new JSONObject();
		collectionDataLog.put(PlayerDataLogEvents.EVENTID, new JSONString(GwtUUIDGenerator.uuid()));
		collectionDataLog.put(PlayerDataLogEvents.EVENTNAME, new JSONString(eventName));
		collectionDataLog.put(PlayerDataLogEvents.SESSION, PlayerDataLogEvents.getDataLogSessionObject(sessionId));
		collectionDataLog.put(PlayerDataLogEvents.STARTTIME, new JSONNumber(reactionStartTime));
		collectionDataLog.put(PlayerDataLogEvents.ENDTIME, new JSONNumber(reactionEndTime));
		collectionDataLog.put(PlayerDataLogEvents.USER, PlayerDataLogEvents.getDataLogUserObject());
		String questionTypeString=questionType.equals("RES")?"resource":"question";
		String classpageId=AppClientFactory.getPlaceManager().getDataLogClasspageId();
		String path="";
		if(classpageId!=null&&!classpageId.equals("")){
			path=classpageId+"/"+collectionDo.getGooruOid()+"/"+resourceId;
		}else{
			path=AppClientFactory.getPlaceManager().getFolderIds()+collectionDo.getGooruOid()+"/"+resourceId;
		}
		String playerMode=AppClientFactory.getPlaceManager().getPlayerMode();

		collectionDataLog.put(PlayerDataLogEvents.CONTEXT, PlayerDataLogEvents.getDataLogContextObject(resourceId,collectionDo.getGooruOid(), resourceNewDataLogEventId, "", playerMode,questionTypeString,reactionType,path,null,0));
		collectionDataLog.put(PlayerDataLogEvents.VERSION,PlayerDataLogEvents.getDataLogVersionObject());
		collectionDataLog.put(PlayerDataLogEvents.METRICS,PlayerDataLogEvents.getDataLogMetricsObject(reactionEndTime-reactionStartTime, 0,0));
		collectionDataLog.put(PlayerDataLogEvents.PAYLOADOBJECT,PlayerDataLogEvents.getDataLogPayLoadObject(reactionType));
		PlayerDataLogEvents.collectionStartStopEvent(collectionDataLog);
	}

	public void triggerItemLoadDataLogEvent(Long startTime,String itemType,String collectionId){
		JSONObject collectionDataLog=new JSONObject();
		collectionDataLog.put(PlayerDataLogEvents.EVENTID, new JSONString(GwtUUIDGenerator.uuid()));
		collectionDataLog.put(PlayerDataLogEvents.EVENTNAME, new JSONString(PlayerDataLogEvents.ITEM_LOAD));
		collectionDataLog.put(PlayerDataLogEvents.SESSION, PlayerDataLogEvents.getDataLogSessionObject(sessionId));
		collectionDataLog.put(PlayerDataLogEvents.STARTTIME, new JSONNumber(startTime));
		collectionDataLog.put(PlayerDataLogEvents.ENDTIME, new JSONNumber(startTime));
		collectionDataLog.put(PlayerDataLogEvents.METRICS,PlayerDataLogEvents.getDataLogMetricsObject(startTime-startTime));
		collectionDataLog.put(PlayerDataLogEvents.VERSION,PlayerDataLogEvents.getDataLogVersionObject());
		collectionDataLog.put(PlayerDataLogEvents.PAYLOADOBJECT,PlayerDataLogEvents.getItemLoadDataLogPayLoadObject(itemType));
		collectionDataLog.put(PlayerDataLogEvents.USER, PlayerDataLogEvents.getDataLogUserObject());
		String classpageId=AppClientFactory.getPlaceManager().getDataLogClasspageId();
		String classpageEventId=AppClientFactory.getPlaceManager().getClasspageEventId();
		String path="";
		if(classpageId!=null&&!classpageId.equals("")){
			path=classpageId+"/"+collectionId;
		}else{
			path=AppClientFactory.getPlaceManager().getFolderIds()+collectionId;
		}
		String playerMode=AppClientFactory.getPlaceManager().getPlayerMode();
		collectionDataLog.put(PlayerDataLogEvents.CONTEXT,PlayerDataLogEvents.getDataLogContextObjectForItemLoad(collectionId, "", classpageEventId, classpageId, "", playerMode, path, null, PlayerDataLogEvents.COLLECTION_LOAD_URL));
		PlayerDataLogEvents.collectionStartStopEvent(collectionDataLog);
	}


	public void triggerItemFlagDataLogEvent(Long startTime,String itemType,String flagText,ArrayList<String> contentReportList,String itemGooruOid,String collectionItemId){
		JSONObject collectionDataLog=new JSONObject();
		collectionDataLog.put(PlayerDataLogEvents.EVENTID, new JSONString(GwtUUIDGenerator.uuid()));
		collectionDataLog.put(PlayerDataLogEvents.EVENTNAME, new JSONString(PlayerDataLogEvents.ITEM_FLAG));
		collectionDataLog.put(PlayerDataLogEvents.SESSION, PlayerDataLogEvents.getDataLogSessionObject(sessionId));
		collectionDataLog.put(PlayerDataLogEvents.STARTTIME, new JSONNumber(startTime));
		collectionDataLog.put(PlayerDataLogEvents.ENDTIME, new JSONNumber(startTime));
		collectionDataLog.put(PlayerDataLogEvents.METRICS,PlayerDataLogEvents.getDataLogMetricsObject(startTime-startTime));
		collectionDataLog.put(PlayerDataLogEvents.VERSION,PlayerDataLogEvents.getDataLogVersionObject());
		collectionDataLog.put(PlayerDataLogEvents.USER, PlayerDataLogEvents.getDataLogUserObject());
		collectionDataLog.put(PlayerDataLogEvents.PAYLOADOBJECT,PlayerDataLogEvents.getItemFlagDataLogPayLoadObject(itemType,flagText,contentReportList));
		String classpageId=AppClientFactory.getPlaceManager().getDataLogClasspageId();
		String classpageEventId=AppClientFactory.getPlaceManager().getClasspageEventId();
		String path="";
		if(!StringUtil.isEmpty(classpageId)){
			path=classpageId+"/"+collectionDo.getGooruOid();
			if(getPlaceManager().getRequestParameter("rid")!=null){
				path=path+"/"+itemGooruOid;
			}
		}else{
			path=AppClientFactory.getPlaceManager().getFolderIds()+collectionDo.getGooruOid();
			if(getPlaceManager().getRequestParameter("rid")!=null){
				path=path+"/"+itemGooruOid;
			}
		}
		String playerMode=AppClientFactory.getPlaceManager().getPlayerMode();
		String parentEventId=itemType.equals(PlayerDataLogEvents.COLLECTION)?classpageEventId:collectionNewDataLogEventId!=null?collectionNewDataLogEventId:"";
		String parentGooruId=itemType.equals(PlayerDataLogEvents.COLLECTION)?classpageId:collectionDo.getGooruOid();
		collectionDataLog.put(PlayerDataLogEvents.CONTEXT,PlayerDataLogEvents.getDataLogContextObjectForItemLoad(itemGooruOid, collectionItemId, parentEventId, parentGooruId, "", playerMode, path, null, PlayerDataLogEvents.COLLECTION_FLAG_URL));
		PlayerDataLogEvents.collectionStartStopEvent(collectionDataLog);
	}

	public void triggerShareDatalogEvent(String resourceGooruOid,String collectionItemId, String itemType,String shareType, boolean confirmStatus){
		String classpageId=AppClientFactory.getPlaceManager().getDataLogClasspageId();
		String path="";
		if(classpageId!=null&&!classpageId.equals("")){
			path=classpageId+"/"+collectionDo.getGooruOid()+"/"+resourceGooruOid;
		}else{
			path=AppClientFactory.getPlaceManager().getFolderIds()+collectionDo.getGooruOid()+"/"+resourceGooruOid;
		}
		String playerMode=AppClientFactory.getPlaceManager().getPlayerMode();
		PlayerDataLogEvents.triggerItemShareDataLogEvent(resourceGooruOid, collectionItemId, null,collectionDo.getGooruOid(), "", sessionId, itemType, shareType, confirmStatus, playerMode, path, null);
	}

	public void triggerCollectionShareDataEvent( String collectionId, String itemType, String shareType, boolean confirmStatus){
		String classpageId=AppClientFactory.getPlaceManager().getDataLogClasspageId();
		String path="";
		if(!StringUtil.isEmpty(classpageId)){
			path=classpageId+"/"+collectionDo.getGooruOid();
		}else{
			path=AppClientFactory.getPlaceManager().getFolderIds()+collectionDo.getGooruOid();
		}
		String playerMode=AppClientFactory.getPlaceManager().getPlayerMode();
		PlayerDataLogEvents.triggerItemShareDataLogEvent(collectionId, "",null, classpageId, "", sessionId, itemType, shareType, confirmStatus, playerMode, path, null);
	}

	public void setTotalTimeSpentOnSummaryPage(){
		if(collectionEndTime!=0L){
			Long currentTime=PlayerDataLogEvents.getUnixTime();
			totalTimeSpentOnSummaryPage=totalTimeSpentOnSummaryPage+(currentTime-collectionEndTime);
			newCollectionStartTime=resourceStartTime;
		}
	}


	/**
	 * Checks weather the logged in user is child or not.
	 * @return isChild {@link Boolean}
	 */
	private boolean isChildAccount() {
		Date convertedDOB = null;
		boolean isChild=false;
		int loggedInUserAge = 0;
		com.google.gwt.i18n.client.DateTimeFormat dateFormat = com.google.gwt.i18n.client.DateTimeFormat.getFormat("yyyy-MM-dd hh:mm:ss.S");
		if(AppClientFactory.getLoggedInUser().getDateOfBirth()!=null&&!AppClientFactory.getLoggedInUser().getDateOfBirth().equals("")){
			convertedDOB = dateFormat.parse(AppClientFactory.getLoggedInUser().getDateOfBirth());
			loggedInUserAge = getAge(convertedDOB);
			if(loggedInUserAge<=CHILD_AGE){
				isChild=true;
			}else if(loggedInUserAge>CHILD_AGE){
				isChild=false;
			}
		}

		return isChild;
	}

	private int getAge(Date birthDate) {
		long ageInMillis = new Date().getTime() - birthDate.getTime();
		Date age = new Date(ageInMillis);
		return age.getYear() - 70;
	}
}
