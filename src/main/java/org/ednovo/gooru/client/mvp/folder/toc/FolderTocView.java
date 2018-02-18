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
 
package org.ednovo.gooru.client.mvp.folder.toc;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.gin.BaseViewWithHandlers;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.application.shared.model.folder.FolderTocDo;
import org.ednovo.gooru.application.shared.model.user.ProfileDo;
import org.ednovo.gooru.client.mvp.folder.toc.util.FolderCollectionView;
import org.ednovo.gooru.client.mvp.search.AddResourceContainerView.CollectionTreeItem;
import org.ednovo.gooru.client.mvp.shelf.list.TreeMenuImages;
import org.ednovo.gooru.client.uc.H2Panel;
import org.ednovo.gooru.client.uc.H3Panel;
import org.ednovo.gooru.shared.util.Constants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.UIObject;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;
/**
 * @fileName : FolderTocView.java
 *
 * @description : 
 *
 * @version : 1.3
 *
 * @date: 06-02-2015
 *
 * @Author Gooru Team
 *
 * @Reviewer: 
 */
public class FolderTocView extends BaseViewWithHandlers<FolderTocUiHandlers> implements IsFolderTocView {

	private static FolderTocViewUiBinder uiBinder = GWT
			.create(FolderTocViewUiBinder.class);

	interface FolderTocViewUiBinder extends UiBinder<Widget, FolderTocView> {
	}
	private static MessageProperties i18n = GWT.create(MessageProperties.class);
	
	@UiField HTMLPanel floderTreeContainer,marginDiv,bannerImagePanel,profileBannerPanel,bannerLogoImageContainer,whiteBgContainer,sharePanel,
	folderTocMainPnl;
	@UiField Label lblBigIdeas,lblEssentalQuestions,lblPerformanceTasks,shareLbl;
	@UiField H3Panel lblFolderTitle;
	@UiField Button btnBackToPrevious;
	@UiField H2Panel bannerTitle,userTitle;
	@UiField Image logoImage,bannerImage,profImage,errorPageImg;
	//@UiField Anchor mainTitle,firstTitle;
	@UiField TextBox shareTxtBox;
	
	@UiField HTMLPanel bigIdeasPanel,essentialPanel,performancePanel,breadCrumbsPanel,mainContainer,pageNotFoundPanel;
	
	@UiField Hidden myHiddenField;
	
	final String FOLDER = "folder";
	final String COLLECTION = "collection";
	final String ASSESSMENT = "assessment";
	final String SCOLLECTION="scollection";
	private static final String USER_ID = "userId";
	private static final String BACK2TOC = "backToToc";
	private static final String EMPTY_FOLDER = i18n.GL3198();
	private static final String SHORTEN_URL = "shortenUrl";
	private static final String ID = "id";
	private static final String PARENT_ID = "parentId";
	private static final String LIBRARY_NAME = "libName";
	private static final String TYPE = "type";

	private static final String FEATURED = "Featured";
	
	private Map<String, List<String>> bannerVal;
	PlaceRequest placeRequest =null;
		
	private Tree folderTocTree = new Tree(new TreeMenuImages()) {
		@Override
		public void onBrowserEvent(Event event) {
			int eventType = DOM.eventGetType(event);
			if (!(eventType == Event.ONKEYUP || eventType == Event.ONKEYPRESS || eventType == Event.ONKEYDOWN)) {
				super.onBrowserEvent(event);
			}
		}
		 public void onLoad(){
			 super.onLoad();
			 DOM.setStyleAttribute(folderTocTree.getElement(), "position", "static");
		 }
	};
	private CollectionTreeItem previousSelectedItem = null;
	private FolderTreeItem currentFolderSelectedTreeItem = null;
	private FolderTreeItem previousFolderSelectedTreeItem = null;
	
	public FolderTocView() {
		setWidget(uiBinder.createAndBindUi(this));
		folderTocMainPnl.getElement().setId("folderTocMainPnl");
		FolderContainerCBundle.INSTANCE.css().ensureInjected();
		setData();
		bannerImage.setVisible(false);
		bannerImagePanel.setVisible(false);
		profileBannerPanel.setVisible(false);
		pageNotFoundPanel.setVisible(false);
		//This will handle the window resize
		Window.addResizeHandler(new ResizeLogicEvent());
		setBannerStaticImages();
		
	}

	/**
	 * This method is used to set folder TOC Data.
	 */
	public void setData() {
		Window.scrollTo(0, 0);
		floderTreeContainer.clear();
		floderTreeContainer.add(folderTocTree);
		folderTocTree.addItem(getLoadingImage());
		int windowHeight=Window.getClientHeight()-(177+52); 
		whiteBgContainer.getElement().setAttribute("style", "min-height:"+windowHeight+"px");
		folderTocTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
			@Override
			public void onSelection(SelectionEvent<TreeItem> event) {
				final TreeItem item = (TreeItem) event.getSelectedItem();
				Widget folderWidget = item.getWidget();
				FolderTreeItem folderTreeItemWidget = null;
				if (folderWidget instanceof FolderTreeItem) {
					folderTreeItemWidget = (FolderTreeItem) folderWidget;
					if (folderTreeItemWidget.isOpen()) {
						folderTreeItemWidget.removeStyleName(FolderContainerCBundle.INSTANCE.css().open());
						folderTreeItemWidget.arrowLabel.removeStyleName(FolderContainerCBundle.INSTANCE.css().arrowActive());
						folderTreeItemWidget.setOpen(false);
					} else {
						folderTreeItemWidget.addStyleName(FolderContainerCBundle.INSTANCE.css().open());
						folderTreeItemWidget.arrowLabel.addStyleName(FolderContainerCBundle.INSTANCE.css().arrowActive());
						folderTreeItemWidget.setOpen(true);
					}
					removePreviousSelectedItem();
					currentFolderSelectedTreeItem = folderTreeItemWidget;
					previousFolderSelectedTreeItem = currentFolderSelectedTreeItem;
					currentFolderSelectedTreeItem
							.addStyleName(FolderContainerCBundle.INSTANCE
									.css().selected());
					TreeItem parent = item.getParentItem();
					item.getTree().setSelectedItem(null, false); 
					if (!folderTreeItemWidget.isApiCalled()) {
						folderTreeItemWidget.setApiCalled(true);
						item.addItem(loadingTreeItem());
						getFolderItems(item, folderTreeItemWidget.getGooruOid());
					}
					if (parent != null)
						parent.setSelected(false); 
						item.setState(!item.getState(), false);
				} 
			}
		});
		btnBackToPrevious.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String lastAccessedUrl=Cookies.getCookie(BACK2TOC)!=null?Cookies.getCookie(BACK2TOC):"";
				String[] placeToken=lastAccessedUrl.split("#");
				Map<String, String> params = new HashMap<String, String>();
				if(placeToken.length>1){
						AppClientFactory.getPlaceManager().revealPlace(placeToken[0], StringUtil.splitQuery(lastAccessedUrl));
				}else{
					//If we are viewing TOC from library 
					AppClientFactory.getPlaceManager().revealPlace(placeToken[0], params);
			  }
			}
		});
		shareTxtBox.addClickHandler(new OnTextBoxClick());
		shareTxtBox.setReadOnly(true);
	}
	
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.folder.toc.IsFolderTocView#setBannerStaticImages()
	 */
	@Override
	public void setBannerStaticImages() {
		bannerVal= new HashMap<String, List<String>>();
		bannerVal.put(PlaceTokens.RUSD_LIBRARY, Arrays.asList("background: url(../images/library/landing-image-rusd.png) -7px -47px no-repeat;",i18n.GL0532(),Constants.RUSD_LOGO));
		
		bannerVal.put(PlaceTokens.EPISD_LIBRARY, Arrays.asList("background: url(../images/library/landing-image-episd.png) -7px -47px no-repeat;",i18n.GL0532(),Constants.EPISD_LOGO));
		
		bannerVal.put(PlaceTokens.YCGL_LIBRARY, Arrays.asList("background: url(../images/library/district/landing-image-ycgl.png) -7px -47px no-repeat; background-position: center center; ",i18n.GL3287_1(),Constants.YCGL_LOGO));
		
		bannerVal.put(PlaceTokens.CORE_LIBRARY, Arrays.asList("background: url(../images/library/district/landing-image-rusd_orange.png) no-repeat;",i18n.GL2108(),Constants.CORE_LOGO));
		
		bannerVal.put(PlaceTokens.YESD_LIBRARY, Arrays.asList("background: url(../images/library/district/YumaOneBanner.png) no-repeat; background-position: center center;","Yuma Country Library",Constants.YUMA_ONE_LOGO));
		
		bannerVal.put(PlaceTokens.LPS, Arrays.asList("background: url(../images/library/district/landing-image-rusd_purple.png) no-repeat;",i18n.GL2053(), Constants.LPS_LOGO));
		bannerVal.put(PlaceTokens.LUSD, Arrays.asList("background: url(../images/library/district/landing-image-lusd.png) 0px 0px no-repeat;background-size: auto 130% !important;",i18n.GL2181(), Constants.LUSD_LOGO));
		bannerVal.put(PlaceTokens.VALVERDE, Arrays.asList("background: url(../images/library/district/landing-image-valverde.png)-7px -50px no-repeat;background-size: auto 128% !important;height: 220px; margin-top: -12px;",i18n.GL2061(), Constants.VALVERDE_LOGO));
		bannerVal.put(PlaceTokens.SUSD, Arrays.asList("background: url(../images/library/district/landing-image-susd.png) -7px -63px no-repeat;background-size: auto 131% !important; height: 220px;",i18n.GL2058(), Constants.SUSD_LOGO));
		bannerVal.put(PlaceTokens.SAUSD_LIBRARY, Arrays.asList("background: url(../images/library/district/landing-image-sausd.png) 0px -7px no-repeat;",i18n.GL1902(), Constants.SAUSD_LOGO));
		 
		bannerVal.put(PlaceTokens.AUTODESK, Arrays.asList("background: url("+Constants.AUTODESK_BANNER +") center no-repeat; height: 230px;",i18n.GL2027(),""));
		bannerVal.put(PlaceTokens.CCST_Cal_TAC, Arrays.asList("background: url("+Constants.CCST_BANNER +") center no-repeat; height: 230px;",i18n.GL2179(),""));
		bannerVal.put(PlaceTokens.NGC, Arrays.asList("background: url("+Constants.NGC_BANNER +") center -38px no-repeat; height: 245px;",i18n.GL2030(),""));
		bannerVal.put(PlaceTokens.ASPIRE_EPACS, Arrays.asList("background: url("+Constants.ASPIRE_BANNER +") center no-repeat; height: 230px;",i18n.GL3107(),""));
		bannerVal.put(PlaceTokens.ESYP, Arrays.asList("background: url("+Constants.ESYP_BANNER +") center -50px no-repeat;height: 230px;background-size: 100% 301px !important;",i18n.GL2174(),""));
		bannerVal.put(PlaceTokens.FTE, Arrays.asList("background: url("+Constants.FTE_BANNER +") center no-repeat;height: 241px;",i18n.GL2029(),""));
		bannerVal.put(PlaceTokens.LESSONOPOLY, Arrays.asList("background: url("+Constants.LESSONOPOLY_BANNER +") center -37px no-repeat;height: 235px;",i18n.GL2032(),""));
		bannerVal.put(PlaceTokens.LIFEBOARD, Arrays.asList("background: url("+Constants.LIFEBOARD_BANNER +") center no-repeat;height: 230px;","",""));
		bannerVal.put(PlaceTokens.FINCAPINC, Arrays.asList("background: url("+Constants.FINCAP_BANNER +") center -50px no-repeat; height: 232px;",i18n.GL2033(),""));
		bannerVal.put(PlaceTokens.GEOEDUCATION, Arrays.asList("background: url("+Constants.GEOEDU_BANNER +") center -50px no-repeat;background-size: 100% 301px !important; height: 228px;",i18n.GL2041(),""));
		bannerVal.put(PlaceTokens.ONR, Arrays.asList("background: url("+Constants.ONR_BANNER +") center no-repeat;height: 230px;",i18n.GL2028(),""));
		bannerVal.put(PlaceTokens.PSDPAL, Arrays.asList("background: url("+Constants.PSDPAL_BANNER +") center -37px no-repeat;height: 245px;",i18n.GL2034(),""));
		bannerVal.put(PlaceTokens.TICAL, Arrays.asList("background: url("+Constants.TICAL_BANNER +") center -50px no-repeat;background-size: 100% 301px !important; height: 230px;",i18n.GL2186(),""));
		bannerVal.put(PlaceTokens.WSPWH, Arrays.asList("background: url("+Constants.WSPH_BANNER +") center -48px no-repeat;height: 230px;",i18n.GL2031(),""));
		bannerVal.put(PlaceTokens.YOUTHVOICES, Arrays.asList("background: url("+Constants.YOUTH_VOICES_BANNER +") center no-repeat;height: 230px;",i18n.GL2040(),""));
	}
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.folder.toc.IsFolderTocView#setFolderItems(org.ednovo.gooru.shared.model.folder.FolderTocDo)
	 */
	@Override
	public void setFolderItems(FolderTocDo  foldersTocObj) {
		folderTocTree.clear();
		showPageNotFound(false);
		setFolderMetaData(foldersTocObj);
		if(foldersTocObj!=null){
			List<FolderDo> foldersArrayList=foldersTocObj.getCollectionItems();
			 if(foldersArrayList.size()>0){
				 for(int i=0;i<foldersArrayList.size();i++){
					 FolderDo floderDo=foldersArrayList.get(i);
					 if(FOLDER.equalsIgnoreCase(floderDo.getType())){
						 TreeItem folderItem=new TreeItem(new FolderTreeItem(null,floderDo.getTitle(),floderDo.getGooruOid()));
						 folderTocTree.addItem(folderItem);
						 adjustTreeItemStyle(folderItem,floderDo.getType(),0);
					 }else if(COLLECTION.equalsIgnoreCase(floderDo.getType()) || ASSESSMENT.equalsIgnoreCase(floderDo.getType()) || SCOLLECTION.equalsIgnoreCase(floderDo.getType())){
						 TreeItem folderItem=new TreeItem(new FolderCollectionView(null,floderDo,null));
						 folderTocTree.addItem(folderItem);
						 adjustTreeItemStyle(folderItem,floderDo.getType(),0);
					 }
				 }
				 floderTreeContainer.clear();
				 floderTreeContainer.add(folderTocTree);
			 }else{
				 Label emptyLbl= new Label();
				 emptyLbl.setText(EMPTY_FOLDER);
				 emptyLbl.setStyleName(FolderContainerCBundle.INSTANCE.css().emptyFolderStyle());
				 floderTreeContainer.clear();
				 floderTreeContainer.add(emptyLbl);	
			 }
		}
		
	}
	/**
	 * This method will set the folder meta data on the toc page.
	 * @param foldersTocObj
	 */
	void setFolderMetaData(FolderTocDo  foldersTocObj){
			PlaceRequest placeRequest=AppClientFactory.getPlaceManager().getPreviousRequest();

			//This is used for handling folder toc back button code
			if(placeRequest!=null && !PlaceTokens.ASSESSMENT_PLAY.equalsIgnoreCase(placeRequest.getNameToken()) && !PlaceTokens.COLLECTION_PLAY.equalsIgnoreCase(placeRequest.getNameToken()) && !PlaceTokens.FOLDER_TOC.equalsIgnoreCase(placeRequest.getNameToken())){
				String paramerersString="";
				Set<String> parameters=placeRequest.getParameterNames();
				if(parameters.size()>0){
					for (String paramString : parameters) {
						paramerersString=paramerersString+paramString+"="+placeRequest.getParameter(paramString, null)+"&";
					}
				}
				Cookies.removeCookie(BACK2TOC);
				if(StringUtil.isEmpty(paramerersString)){
					//If we click the view all or list all button form the library
					Cookies.setCookie(BACK2TOC,placeRequest.getNameToken());
				}else{ 
					//If we click the view all or list all button form the my collection or profile 
					Cookies.setCookie(BACK2TOC,placeRequest.getNameToken()+"#"+paramerersString);
				}
			}
			if(foldersTocObj!=null){
				if(!StringUtil.isEmpty(foldersTocObj.getIdeas())){
					bigIdeasPanel.setVisible(true);
					lblBigIdeas.getElement().setInnerHTML(foldersTocObj.getIdeas());
				}else{
				    bigIdeasPanel.setVisible(false);
				}
				if(!StringUtil.isEmpty(foldersTocObj.getQuestions())){
					essentialPanel.setVisible(true);
					lblEssentalQuestions.getElement().setInnerHTML(foldersTocObj.getQuestions());
				}else{
					essentialPanel.setVisible(false);
				}
				if(!StringUtil.isEmpty(foldersTocObj.getPerformanceTasks())){
					performancePanel.setVisible(true);
					lblPerformanceTasks.getElement().setInnerHTML(foldersTocObj.getPerformanceTasks());
				}else{
					performancePanel.setVisible(false);
				}
				lblFolderTitle.setText(StringUtil.truncateText(StringUtil.removeHtml(foldersTocObj.getTitle()), 50));
				String profId= AppClientFactory.getPlaceManager().getRequestParameter(USER_ID, null);
				if(profId!=null){
					getUiHandlers().getProfilePageDetails(profId);
				}
				setBreadCrumbsText(bannerTitle.getText(),lblFolderTitle.getText());
			}
			
		}
	/**
	 * @function adjustTreeItemStyle 
	 * 
	 * @created_date : 10-02-2015
	 * 
	 * @description
	 * 
	 * @parm(s) : @param uiObject
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
   	private  void adjustTreeItemStyle(final UIObject uiObject, String itemType, int folderLevel) {
	      if (uiObject instanceof TreeItem) {
	         if (uiObject != null && uiObject.getElement() != null) {
	            Element element = uiObject.getElement();  
				 if(FOLDER.equalsIgnoreCase(itemType)){
						if(folderLevel>=2){
							 element.getStyle().setPaddingLeft(28, Unit.PX);
					            element.getStyle().setMarginLeft(0, Unit.PX);
					            if(folderLevel>2)
					            {
					            Element element1 = uiObject.getElement().getParentElement().getParentElement();
					            element1.getStyle().setPaddingLeft(28, Unit.PX);
				 		        element1.getStyle().setMarginLeft(0, Unit.PX);
					            }
						}
						else
						{
							if(folderLevel == 1)
							{
					           element.getStyle().setPaddingLeft(0, Unit.PX);
					           element.getStyle().setMarginLeft(-10, Unit.PX);
							}
							else
							{
					           element.getStyle().setPaddingLeft(0, Unit.PX);
					           element.getStyle().setMarginLeft(0, Unit.PX);
							}

						}
				 }else if(COLLECTION.equalsIgnoreCase(itemType) || ASSESSMENT.equalsIgnoreCase(itemType) || SCOLLECTION.equalsIgnoreCase(itemType)){
					 if(folderLevel>=2){
						 element.getStyle().setPaddingLeft(69, Unit.PX);
				            element.getStyle().setMarginLeft(0, Unit.PX);
				            
				            Element element1 = uiObject.getElement().getParentElement().getParentElement();
				            element1.getStyle().setPaddingLeft(28, Unit.PX);
			 		        element1.getStyle().setMarginLeft(0, Unit.PX);
			
							}
					 else
					 {
					  element.getStyle().setPaddingLeft(28, Unit.PX);
		              element.getStyle().setMarginLeft(0, Unit.PX);
					 }
					 if(folderLevel==2)
					 {
				            Element element1 = uiObject.getElement().getParentElement().getParentElement();
				            element1.getStyle().setPaddingLeft(0, Unit.PX);
			 		        element1.getStyle().setMarginLeft(-12, Unit.PX);
					 }
			
				 }

	         }
	      } else {
	         if (uiObject != null && uiObject.getElement() != null && uiObject.getElement().getParentElement() != null
	               && uiObject.getElement().getParentElement().getParentElement() != null
	               && uiObject.getElement().getParentElement().getParentElement().getStyle() != null) {
	            Element element = uiObject.getElement().getParentElement().getParentElement();

	 	           element.getStyle().setPadding(0, Unit.PX);
		           element.getStyle().setMarginLeft(1, Unit.PX);
	            
	         }
	      }
   	}

	/**
	 * 
	 * @fileName : FolderTocView.java
	 *
	 * @description : 
	 *
	 * @version : 1.3
	 *
	 * @date: 10-02-2015
	 *
	 * @Author Gooru
	 *
	 * @Reviewer:
	 */
	public class FolderTreeItem extends Composite{
		private FlowPanel folderContainer=null;
		private String gooruOid=null;
		Label floderName=null;
		Label arrowLabel=null;
		private boolean isOpen=false;
		private boolean isApiCalled=false;
		private int folerLevel=1;
		public FolderTreeItem(){
			initWidget(folderContainer=new FlowPanel());
			folderContainer.setStyleName(FolderContainerCBundle.INSTANCE.css().folderLevel());
			arrowLabel=new Label();
			arrowLabel.setStyleName(FolderContainerCBundle.INSTANCE.css().folderTitlearrow());
			folderContainer.add(arrowLabel);
			floderName=new Label();
			floderName.setStyleName(FolderContainerCBundle.INSTANCE.css().folderTitleStyle());
			folderContainer.add(floderName);
		}
		public FolderTreeItem(String levelStyleName,String folderTitle,String gooruOid){
			this();
			if(levelStyleName!=null){
				folderContainer.addStyleName(levelStyleName);
			}
			this.gooruOid=gooruOid;
			floderName.setText(folderTitle);
		}
		public boolean isOpen() {
			return isOpen;
		}
		public void setOpen(boolean isOpen) {
			this.isOpen = isOpen;
		}
		public String getGooruOid(){
			return gooruOid;
		}
		public boolean isApiCalled() {
			return isApiCalled;
		}
		public void setApiCalled(boolean isApiCalled) {
			this.isApiCalled = isApiCalled;
		}
		public int getFolerLevel() {
			return folerLevel;
		}
		public void setFolerLevel(int folerLevel) {
			this.folerLevel = folerLevel;
		}
	}
	private void removePreviousSelectedItem() {
		if (previousFolderSelectedTreeItem != null) {
			previousFolderSelectedTreeItem
					.removeStyleName(FolderContainerCBundle.INSTANCE.css()
							.selected());
		}
		if (previousSelectedItem != null) {
			previousSelectedItem
					.removeStyleName(FolderContainerCBundle.INSTANCE.css()
							.selected());
		}
	}
	/**
	 * This method is used to get the child folder and collections.
	 * @param item
	 * @param parentId
	 */
	private void getFolderItems(TreeItem item, String parentId) {
		getUiHandlers().getFolderItems(item, parentId);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.folder.toc.IsFolderTocView#setFolderItems(com.google.gwt.user.client.ui.TreeItem, org.ednovo.gooru.shared.model.folder.FolderTocDo, java.lang.String)
	 */
	@Override
	public void setFolderItems(TreeItem item,FolderTocDo foldersTocObj,String parentId) {
		displayWorkspaceData(item, foldersTocObj,parentId);
	}
	/**
	 * This method is used to set folders and collections for selected tree item.
	 * @param item
	 * @param folderListDo
	 */
	private void displayWorkspaceData(TreeItem item,FolderTocDo foldersTocObj,String parentId) {
		//This will remove the loading text for the child items.
		if(item.getChildCount() > 0){
			item.getChild(0).remove();
		}
		//This will set the data to the selected tree item.
		if(foldersTocObj!=null){
			List<FolderDo> foldersArrayList=foldersTocObj.getCollectionItems();
			if (foldersArrayList != null && foldersArrayList.size() > 0) {
					FolderTreeItem folderTreeItemWidget = (FolderTreeItem) item
							.getWidget();
					int folderLevel = folderTreeItemWidget.getFolerLevel();
					for (int i = 0; i < foldersArrayList.size(); i++) {
						FolderDo floderDo = foldersArrayList.get(i);
						 if(FOLDER.equalsIgnoreCase(floderDo.getType())){
								String styleName = FolderContainerCBundle.INSTANCE.css().child();
								FolderTreeItem innerFolderTreeItem = new FolderTreeItem(styleName, floderDo.getTitle(),floderDo.getGooruOid());
								innerFolderTreeItem.setFolerLevel(folderLevel + 1);
								TreeItem folderItem = new TreeItem(innerFolderTreeItem);
								item.addItem(folderItem);
								adjustTreeItemStyle(folderItem,floderDo.getType(),folderLevel);
						 }else if(COLLECTION.equalsIgnoreCase(floderDo.getType()) || ASSESSMENT.equalsIgnoreCase(floderDo.getType()) || SCOLLECTION.equalsIgnoreCase(floderDo.getType())){
							 	TreeItem folderItem = new TreeItem(new  FolderCollectionView(null,floderDo,parentId));
								folderItem.getElement().removeAttribute("style");
							 	item.addItem(folderItem);
								adjustTreeItemStyle(folderItem,floderDo.getType(),folderLevel);
						 }
					}
					item.setState(folderTreeItemWidget.isOpen());
			}else{
				Label emptyLbl= new Label();
				emptyLbl.setText(EMPTY_FOLDER);
				emptyLbl.setStyleName(FolderContainerCBundle.INSTANCE.css().emptySubFolderStyle());
				item.addItem(emptyLbl);
			}
		}
	}
	/**
	 * To clear the Tree panels
	 */
	@Override
	public void clearTocData() {
		floderTreeContainer.clear();
		currentFolderSelectedTreeItem=null;
		previousSelectedItem=null;
	}
	/**
	 * This method is used to display loading text to the user.
	 * @return
	 */
	public TreeItem loadingTreeItem() {
		Label loadingText = new Label(i18n.GL3120());
		loadingText.setStyleName(FolderContainerCBundle.INSTANCE.css().loadingText());
		return new TreeItem(loadingText);
	}
	/**
	 * This method is used to display loading text to the user.
	 * @return
	 */
	public TreeItem getLoadingImage() {
		HTMLPanel loadingImg= new HTMLPanel(i18n.GL3120());
		loadingImg.setStyleName(FolderContainerCBundle.INSTANCE.css().loadingImageMainDiv());
		Label loadingText = new Label("");
		loadingText.setStyleName(FolderContainerCBundle.INSTANCE.css().loadingImageForSelfEdit());
		loadingImg.add(loadingText);
		return new TreeItem(loadingImg);
	}
	/**
	 * This method will give the margin value based on the window width and it will set to the main div (For Responsive UI)
	 * @return
	 */
	public int getTotalViewableWidth(){
		int totalClientWidth=Window.getClientWidth();
		int getMargin=0;
		if(totalClientWidth>767){
			getMargin=35;
		}else{
			getMargin=10;
		}
		return getMargin;
	}
	/**
	 * This inner class is used to handle the resize logic
	 */
	public class ResizeLogicEvent implements ResizeHandler{
		@Override
		public void onResize(ResizeEvent event) {
			int windowHeight=Window.getClientHeight()-(177+52); 
			whiteBgContainer.getElement().setAttribute("style", "min-height:"+windowHeight+"px");
			marginDiv.getElement().getStyle().setMarginTop(0, Unit.PX);
			marginDiv.getElement().getStyle().setMarginBottom(0,  Unit.PX);
			marginDiv.getElement().getStyle().setMarginLeft(getTotalViewableWidth(),  Unit.PX);
			marginDiv.getElement().getStyle().setMarginRight(getTotalViewableWidth(),  Unit.PX);
		}
	}
	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.folder.toc.IsFolderTocView#setBannerImages()
	 */
	@Override
	public void setBannerImages(){
		String placetoken=AppClientFactory.getPlaceManager().getRequestParameter(LIBRARY_NAME,null);
		bannerImage.setVisible(false);
		hideProfileOrLibraryPanel(false);
		if(!StringUtil.isEmpty(placetoken)){
			if(placetoken.equalsIgnoreCase("rusd")||AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.EPISD_LIBRARY)){ 
				bannerLogoImageContainer.getElement().getStyle().setBottom(6, Unit.EM);
			}
			else
			{
				bannerLogoImageContainer.getElement().getStyle().setBottom(17, Unit.PX);
			}
			bannerImagePanel.getElement().setAttribute("style", bannerVal.get(placetoken).get(0));
			bannerTitle.setText(convertHtml(bannerVal.get(placetoken).get(1)));
			if(bannerVal.get(placetoken).get(2).equals("")){
				bannerTitle.setStyleName(FolderContainerCBundle.INSTANCE.css().bannerImageTitle());
				bannerLogoImageContainer.setVisible(false);
			}else{
				bannerLogoImageContainer.setVisible(true);
				logoImage.setUrl(bannerVal.get(placetoken).get(2));
				bannerTitle.getElement().getStyle().clearBackgroundColor();
				bannerTitle.removeStyleName(FolderContainerCBundle.INSTANCE.css().bannerImageTitle());
			}
			setBreadCrumbsText(bannerTitle.getText(),lblFolderTitle.getText());
			
		}
	}
	/**
	 * To convert String to HTML.
	 * @param title {@link String}, banner title
	 * @return converted html
	 */
	private String convertHtml(String title) {
		HTML html = new HTML();
		html.setHTML(title);
		return html.getText();
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.folder.toc.IsFolderTocView#setCourseBanner(org.ednovo.gooru.shared.model.folder.FolderDo)
	 */
	@Override
	public void setCourseBanner(FolderDo folderDo) {
		hideProfileOrLibraryPanel(false);
		bannerImagePanel.setVisible(true);
		bannerImage.setStyleName(FolderContainerCBundle.INSTANCE.css().displayNoneStyle());
		bannerTitle.setText(convertHtml(folderDo.getTitle()));
		bannerTitle.setStyleName(FolderContainerCBundle.INSTANCE.css().bannerImageTitle());
		String placetoken=AppClientFactory.getPlaceManager().getRequestParameter(LIBRARY_NAME,null);
		if(!bannerVal.get(placetoken).get(2).equals("")){
			bannerLogoImageContainer.setVisible(true);
			logoImage.setUrl(bannerVal.get(placetoken).get(2));
		}else{
			bannerLogoImageContainer.setVisible(false);
		}
		bannerImagePanel.getElement().setAttribute("style", "background: url("+"\""+folderDo.getThumbnails().getUrl()+"\"" +") no-repeat center -50px; background-size: 100% auto !important;height: 198px;");
		bannerImage.setUrl(folderDo.getThumbnails().getUrl());
		setBreadCrumbsText(bannerTitle.getText(),lblFolderTitle.getText());
		bannerImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				setBannerImages();
			}
		});

		
	}
	/**
	 * To hide the panels based on Placetokens
	 */
	@Override
	public void hidePanels() {
		bannerImagePanel.setVisible(false);
		profileBannerPanel.setVisible(false);
		breadCrumbsPanel.setVisible(false);
	}
	/**
	 * To set the User profile details
	 * @param profileDo {@link ProfileDo}
	 */
	@Override
	public void setProfileBannerDetails(ProfileDo profileDo) {
		hideProfileOrLibraryPanel(true);
		String userName=profileDo.getUser()==null?"":(profileDo.getUser().getUsernameDisplay()==null?"":profileDo.getUser().getUsernameDisplay());
		String profileImage=profileDo.getUser()==null?"":(profileDo.getUser().getProfileImageUrl()==null?"":profileDo.getUser().getProfileImageUrl());
		userTitle.setText(userName);
		profImage.setUrl(profileImage);
		profImage.addErrorHandler(new ErrorHandler() {
			@Override
			public void onError(ErrorEvent event) {
				profImage.setUrl("images/profilepage/user-profile-pic.png");
			}
		});
		setBreadCrumbsText(userTitle.getText(),lblFolderTitle.getText());
	}
	/**
	 * To set the text of folder BreadCrumbs
	 */
	private void setBreadCrumbsText(String mTitle,String fTitle) {
		/*mainTitle.setText(mTitle);
		firstTitle.setText(fTitle);*/
	}
	/**
	 * To show profile page details in Toc
	 */
	@Override
	public void showProfileBanner() {
		hideProfileOrLibraryPanel(true);
		//setProfileBannerDetails();
	}
	/**
	 * To hide the bannerImage panels 
	 * @param isVisible {@link Boolean}
	 */
	public void hideProfileOrLibraryPanel(boolean isVisible){
		bannerImagePanel.setVisible(!isVisible);
		profileBannerPanel.setVisible(isVisible);
		breadCrumbsPanel.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.folder.toc.IsFolderTocView#setBackButtonText(java.lang.String)
	 */
	@Override
	public void setBackButtonText(Map<String, String> backTxtParams) {
		String backBtnTxt="";
		if(backTxtParams.containsKey(LIBRARY_NAME)){
			backBtnTxt=i18n.GL3170();
		}else if(backTxtParams.containsKey(USER_ID)){
			backBtnTxt=i18n.GL3171();
		}else{
			backBtnTxt=i18n.GL3172();
		}
		btnBackToPrevious.setText(i18n.GL3165()+" "+backBtnTxt);
	}

	@Override
	public Tree getTreePanel() {
		folderTocTree.clear();
		folderTocTree.addItem(getLoadingImage());
		return folderTocTree;
	}

	@Override
	public void setBitlyLink(Map<String, String> shareResult) {
		shareTxtBox.setText(shareResult.get(SHORTEN_URL));
	}
	
	/**
	 * Selected shareLink textBox data.
	 */
	public class OnTextBoxClick implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			shareTxtBox.selectAll();
			shareTxtBox.setFocus(true);
		}
	}
	@Override
	public void setBreadCrumbs(final String key, String value, String separator) {
		Label routeLbl= new Label();
		routeLbl.setStyleName(FolderContainerCBundle.INSTANCE.css().breadCrumbsStyle());
		routeLbl.setText(" "+value+" "+separator);
		routeLbl.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				getUiHandlers().getTocFolders(key);
				String libName=AppClientFactory.getPlaceManager().getRequestParameter(LIBRARY_NAME,null);
				String parentId=AppClientFactory.getPlaceManager().getRequestParameter(PARENT_ID,null);
				if(libName!=null && parentId!=null){
					getUiHandlers().getFolderMetaData(key);
				}
			}
		});
		breadCrumbsPanel.add(routeLbl);
	}

	@Override
	public void setBreadCrumbs(Map<String, String> result) {
		breadCrumbsPanel.clear();
		int size=0;
		boolean haveBreadCrumbs=false;
		for (Map.Entry<String, String> entry : result.entrySet())
		{
			if(!entry.getValue().equals(FEATURED)){
				 setBreadCrumbs(entry.getKey(), entry.getValue(), "> ");
			}
		    size++;
		    haveBreadCrumbs=true;
		}
		if(size==result.size() && haveBreadCrumbs){
			Label presentTile=new Label();
			presentTile.setStyleName(FolderContainerCBundle.INSTANCE.css().displayInlineStyle());
			presentTile.setText(lblFolderTitle.getText());
			breadCrumbsPanel.add(presentTile);
		}

	}
   /**
    * To show the Page doesn't exist
    */
	@Override
	public void showPageNotFound(boolean isVisible){
		mainContainer.setVisible(!isVisible);
		pageNotFoundPanel.setVisible(isVisible);
	}
	
	@UiHandler("errorPageImg")
	public void clickOnErrorImg(ClickEvent clickEvent){
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.HOME);
	}
	
	/**
	* Gets the name of the used browser.
	*/
	public static native String getBrowserName() /*-{
	    return navigator.userAgent.toLowerCase();
	}-*/;
	
	/**
	* Returns true if the current browser is Firefox.
	*/
	public static boolean isFirefoxBrowser() {
	    return getBrowserName().toLowerCase().contains("firefox");
	}
	
}
