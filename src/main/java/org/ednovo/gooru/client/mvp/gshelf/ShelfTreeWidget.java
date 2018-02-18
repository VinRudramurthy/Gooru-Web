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

package org.ednovo.gooru.client.mvp.gshelf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.effects.FontWeightEffect;
import org.ednovo.gooru.client.mvp.shelf.collection.folders.item.EditAssessmentPopup;
import org.ednovo.gooru.client.mvp.shelf.event.CollectionAssignShareEvent;
import org.ednovo.gooru.client.mvp.shelf.event.CollectionAssignShareHandler;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.DOMUtils;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.FontStyle;
import com.google.gwt.dom.client.Style.FontWeight;
import com.google.gwt.dom.client.Style.TextAlign;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Search Team
 *
 */
public class ShelfTreeWidget extends FocusPanel {

	private static ShelfCollectionUiBinder uiBinder = GWT.create(ShelfCollectionUiBinder.class);

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	interface ShelfCollectionUiBinder extends UiBinder<Widget, ShelfTreeWidget> {
	}

	@UiField
	HTML titleLbl;

	@UiField
	FocusPanel titleFocPanel;

	@UiField HTMLPanel panelToolTip;

	private String draggedCollectionId="";

	private FolderDo collectionDo;

	private List<FolderDo> folderListDoChild;

	private PopupPanel toolTipPopupPanel=new PopupPanel();

	private static ShelfTreeWidget shelfTreeWidget;

	private boolean isEditButtonSelected = false;

	HTMLPanel htmlPanel;

	private boolean isValue=true;

	private boolean isOpen=false;

	private int level = 1;

	private int position;

	private FolderDo folderDo;

	private boolean folderIsOpened = false;

	private boolean collectionIsOpened = false;

	HashMap<String,String> urlParams = new HashMap<String,String>();

	HashMap<String,String> updateWidgetTitles = new HashMap<String,String>();

	private static final String O1_LEVEL = "o1";

	private static final String O2_LEVEL = "o2";

	private static final String O3_LEVEL = "o3";


	private String widgetType;

	private static final String ID = "id";

	private static final String ASSESSMENT = "assessment";
	private static final String FOLDER = "folder";
	private static final String COURSE = "Course";
	private static final String UNIT = "Unit";
	private static final String LESSON = "Lesson";
	private static final String COLLECTION = "collection";
	private static final String ASSESSMENT_URL = "assessment/url";

	EditAssessmentPopup editAssessmentPopup=null;


	/**
	 * Class constructor , assign the {@link CollectionDo} instance
	 *
	 * @param collectionDo
	 *            instance of {@link CollectionDo}
	 * @param nextLevel
	 */
	public ShelfTreeWidget(final FolderDo collectionDo, final int nextLevel, String type) {
		setWidget(uiBinder.createAndBindUi(this));
		if(collectionDo!=null){
			setData(collectionDo,nextLevel);
			this.folderDo=collectionDo;
			this.collectionDo=collectionDo;
			if(ASSESSMENT_URL.equalsIgnoreCase(collectionDo.getType())){
				showAssessmentUrlInfo(collectionDo);
			}
		}else{
			setData(type,nextLevel);
		}
		this.getElement().setAttribute("style", "min-height: 42px;");
		titleFocPanel.getElement().setId("focuspnlTitleFocPanel");
		titleLbl.getElement().setId("htmlTitleLbl");
		panelToolTip.getElement().setId("pnlPanelToolTip");
		titleFocPanel.addClickHandler(new ClickOnFolderItem());
		titleFocPanel.addMouseOutHandler(new MouseOutHandler() {
			@Override
			public void onMouseOut(MouseOutEvent event) {
				//myShelfEditButton.getElement().getStyle().setDisplay(Display.NONE);
				/*if(!AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.MYCOLLECTION)) {
					myShelfEditButton.getElement().getStyle().setDisplay(Display.NONE);
				}else{
					myShelfEditButton.getElement().getStyle().setDisplay(Display.NONE);
				}*/
			}
		});
		AppClientFactory.getEventBus().addHandler(CollectionAssignShareEvent.TYPE, handler);

		//AppClientFactory.getEventBus().addHandler(UpdateShelfFolderNameEvent.TYPE,updateShelfFolderName);
		/*if(ASSESSMENT_URL.equalsIgnoreCase(collectionDo.getCollectionType())){
			showAssessmentUrlInfo(collectionDo);
		}*/
	}

	/**
	 * Displays the updated info.
	 * @param result {@link FolderDo}
	 */
	public void showAssessmentUrlInfo(FolderDo result) {
		this.collectionDo = result;
		if(result.getTitle()!=null){
			titleLbl.setHTML(result.getTitle());
		}
		collectionDo.setGooruOid(result.getGooruOid());
		collectionDo.setTitle(StringUtil.isEmpty(result.getTitle())?"":result.getTitle());
		collectionDo.setUrl(StringUtil.isEmpty(result.getUrl())?"":result.getUrl());
		collectionDo.setGoals(StringUtil.isEmpty(result.getGoals())?"":result.getGoals());
		collectionDo.setSharing(StringUtil.isEmpty(result.getSharing())?"":result.getSharing());
		if(result.getSettings()!=null){
			collectionDo.getSettings().setIsLoginRequired(StringUtil.isEmpty(result.getSettings().getIsLoginRequired())?"":result.getSettings().getIsLoginRequired());
		}
	}

	public void setData(FolderDo collectionDo, int nextLevel) {
		updateData(collectionDo);

		if(collectionDo.getType()!=null)
		{
		if(collectionDo.getType().equalsIgnoreCase(COURSE)){
			titleFocPanel.addStyleName("course");
		}else if(collectionDo.getType().equalsIgnoreCase(UNIT)) {
			titleFocPanel.addStyleName("unit");
		}else if(collectionDo.getType().equalsIgnoreCase(LESSON)) {
			titleFocPanel.addStyleName("lesson");
		}else if(!collectionDo.getType().equals(FOLDER)) {
			titleFocPanel.addStyleName(COLLECTION);
		}
		}
		if(collectionDo.getCollectionType()!=null)
		{
		if(collectionDo.getCollectionType().contains(ASSESSMENT)){
			titleFocPanel.addStyleName("assessment");
		}
		}
		if(collectionDo.getSharing()!=null && !collectionDo.getSharing().equalsIgnoreCase("") && collectionDo.getSharing().equals("public")) {
			if(collectionDo.getType().equals(COLLECTION) ){
				titleFocPanel.addStyleName("publicIcon");
				panelToolTip.getElement().getStyle().clearDisplay();
			}else if(collectionDo.getType().equals(ASSESSMENT)){
				titleFocPanel.addStyleName("assesstpublicIcon");
				panelToolTip.getElement().getStyle().clearDisplay();
			}else{
				panelToolTip.getElement().getStyle().setDisplay(Display.NONE);
			}
		}else{
			panelToolTip.getElement().getStyle().setDisplay(Display.NONE);
		}

		if(collectionDo.getType()!=null)
		{
			if(collectionDo.getType().equalsIgnoreCase(COURSE)||collectionDo.getType().equalsIgnoreCase(UNIT)||collectionDo.getType().equalsIgnoreCase(LESSON)){
			setData(collectionDo.getType(),nextLevel);
		}else{
			if(nextLevel == 1) {
				titleLbl.setWidth("138px");
				titleLbl.getElement().getNextSiblingElement().removeAttribute("style");
			} else if(nextLevel == 2) {
				titleLbl.setWidth("111px");
				titleFocPanel.addStyleName("parent");
				titleLbl.getElement().getNextSiblingElement().removeAttribute("style");
			} else if(nextLevel == 3) {
				titleLbl.setWidth("82px");
				titleFocPanel.addStyleName("child");
				titleLbl.getElement().getNextSiblingElement().setAttribute("style", "left:105px;");
				//htmlToolTipContent.getParent().getElement().getPreviousSiblingElement().setAttribute("style", "left:20px;");
			} else if(nextLevel == 4) {
				titleLbl.setWidth("100px");
				titleLbl.getElement().getNextSiblingElement().setAttribute("style", "left:133px;");
				titleFocPanel.addStyleName("collectionChild");
				//htmlToolTipContent.getParent().getElement().getPreviousSiblingElement().setAttribute("style", "left:21px;");
				try {
					titleFocPanel.getParent().getParent().getParent().getParent().getElement().getStyle().setPadding(0, Unit.PX);
				} catch (Exception e){
				}
			}
		}
		}
	}

	public void setData(String type,int nextLevel) {
		if(type.equalsIgnoreCase(COURSE)){
			titleFocPanel.addStyleName("course");
		}else if(type.equalsIgnoreCase(UNIT)) {
			titleFocPanel.addStyleName("unit");
		}else if(type.equalsIgnoreCase(LESSON)) {
			titleFocPanel.addStyleName("lesson");
		}else if(!type.equalsIgnoreCase(FOLDER)) {
			titleFocPanel.addStyleName(COLLECTION);
		}
		if(nextLevel!=1 && (type.equalsIgnoreCase(COLLECTION) || type.contains(ASSESSMENT))){
			String style = nextLevel==2?"parent":"child";
			titleFocPanel.addStyleName(style);
		}
		if(nextLevel == 1) {
			titleLbl.setWidth("138px");
			titleLbl.getElement().getNextSiblingElement().removeAttribute("style");
		} else if(nextLevel == 2) {
			titleLbl.setWidth("111px");
			titleLbl.getElement().getNextSiblingElement().removeAttribute("style");
		} else if(nextLevel == 3) {
			titleLbl.setWidth("82px");
			titleLbl.getElement().getNextSiblingElement().setAttribute("style", "left:105px;");
			//htmlToolTipContent.getParent().getElement().getPreviousSiblingElement().setAttribute("style", "left:20px;");
		} else if(nextLevel == 4) {
			titleLbl.setWidth("100px");
			titleLbl.getElement().getNextSiblingElement().setAttribute("style", "left:133px;");
			titleFocPanel.addStyleName("collectionChild");
			//htmlToolTipContent.getParent().getElement().getPreviousSiblingElement().setAttribute("style", "left:21px;");
		}
	}

	CollectionAssignShareHandler handler = new CollectionAssignShareHandler() {

		@Override
		public void updateShareType(String shareType,String publishStatus,boolean isPublish,CollectionDo collec) {
           if(!isPublish){
        	   if(collectionDo.getType().equals("scollection") || collectionDo.getType().equals(COLLECTION) || collectionDo.getType().equals(ASSESSMENT)){
        		   if(titleFocPanel.getStyleName().contains("active")) {
   					if(shareType.equalsIgnoreCase("public")){
   						if(collectionDo.getCollectionType().equals(ASSESSMENT)){
   							titleFocPanel.addStyleName("assesstpublicIcon");
   						}else{
   							titleFocPanel.addStyleName("publicIcon");
   						}
   						panelToolTip.getElement().getStyle().clearDisplay();
   					}else{
   						if(titleFocPanel.getStyleName().contains("public")){
   							if(collectionDo.getCollectionType().equals(ASSESSMENT)){
   								titleFocPanel.removeStyleName("assesstpublicIcon");
   							}else{
   								titleFocPanel.removeStyleName("publicIcon");
   							}
   							panelToolTip.getElement().getStyle().setDisplay(Display.NONE);
   						}
   					}
   				}
   			}
           }
		}
	};

	public void setEmptyData() {
		htmlPanel = new HTMLPanel(i18n.GL0989());
		htmlPanel.getElement().getStyle().setTextAlign(TextAlign.CENTER);
		htmlPanel.getElement().getStyle().setMarginLeft(19, Unit.PX);
		htmlPanel.getElement().getStyle().setColor("#999999");
		htmlPanel.getElement().setId(collectionDo.getGooruOid());
	}

	/**
	 * Update collection information
	 *
	 * @param collectionDo
	 *            instance of {@link CollectionDo}
	 */
	public void updateData(FolderDo collectionDo) {
		this.collectionDo = collectionDo;
		titleLbl.setHTML(collectionDo.getTitle());
		titleLbl.getElement().setAttribute("alt", collectionDo.getTitle());
		titleLbl.getElement().setAttribute("title", collectionDo.getTitle());
	}

	public void setOpen(boolean isOpen) {
		if(isOpen) {
			titleFocPanel.addStyleName("open");
		} else {
			titleFocPanel.removeStyleName("open");
		}
		//setOpenStatus(false);
	}


	public boolean isOpen() {
		return isOpen;
	}

	public class ClickOnFolderItem implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			String type=(collectionDo!=null&&collectionDo.getType()!=null)?collectionDo.getType():getTreeWidgetType();
			if(!type.equals(FOLDER) &&!type.equalsIgnoreCase(COURSE) &&!type.equalsIgnoreCase(UNIT) &&!type.equalsIgnoreCase(LESSON)) {
				if (event.getSource().equals(titleFocPanel)) {
					MixpanelUtil.Expand_CollectionPanel();
					if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.MYCONTENT)) {
						if(isValue){
							openCollectionInShelf();
						}
						isValue=true;
					}
					setOpen();
				}
			}
		}
	}

	public void openFolderItem() {
		String type=(collectionDo!=null&&collectionDo.getType()!=null)?collectionDo.getType():getTreeWidgetType();
		if(FOLDER.equalsIgnoreCase(type) || COURSE.equalsIgnoreCase(type) || UNIT.equalsIgnoreCase(type)|| LESSON.equalsIgnoreCase(type)){
			if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.MYCONTENT) && !isEditButtonSelected) {
				openFolderInShelf();
			}
		}
	}

	public void setOpen() {
	    if (shelfTreeWidget == null || !shelfTreeWidget.equals(this)) {
            if (shelfTreeWidget != null) {
                shelfTreeWidget.setOpen(false);
                setOpenStyle(false, shelfTreeWidget.level);
            }
            shelfTreeWidget = this;
            setOpen(isOpen);
        } else if (shelfTreeWidget != null) {
            //shelfCollection.enableGlassPanel(false);
        }
	}


	/**
	 * @return collectionDo
	 */
	public FolderDo getCollectionDo() {
		return collectionDo;
	}
	/**
	 * @return folderDo
	 */
	public FolderDo getFolderDo() {
		return folderDo;
	}

	public void glowTitle() {
		new FontWeightEffect(titleLbl.getElement(), FontWeight.BOLD, FontWeight.NORMAL, 5000);
	}

	public void setWidgetPositions(int level, int position, HashMap<String,String> urlParams) {
		this.level = level;
		this.position = position;
		String gooruoId = collectionDo==null?"":collectionDo.getGooruOid();
		String title = collectionDo==null?"":collectionDo.getTitle();
		String type = collectionDo==null?getTreeWidgetType():collectionDo.getType();
		if(level==1) {
			this.urlParams.put(O1_LEVEL, collectionDo.getGooruOid());
			this.urlParams.put(COURSE, collectionDo.getTitle());
		}
		if(level==2) {
			this.urlParams.put(O1_LEVEL, urlParams.get(O1_LEVEL));
			this.urlParams.put(O2_LEVEL, gooruoId);

			this.urlParams.put(COURSE, urlParams.get(COURSE));
			this.urlParams.put(UNIT, title);
		}
		if(level==3) {
			this.urlParams.put(O1_LEVEL, urlParams.get(O1_LEVEL));
			this.urlParams.put(O2_LEVEL, urlParams.get(O2_LEVEL));
			this.urlParams.put(O3_LEVEL, gooruoId);

			this.urlParams.put(COURSE, urlParams.get(COURSE));
			this.urlParams.put(UNIT, urlParams.get(UNIT));
			this.urlParams.put(LESSON, title);
		}
		if(level==4) {
			this.urlParams.put(O1_LEVEL, urlParams.get(O1_LEVEL));
			this.urlParams.put(O2_LEVEL, urlParams.get(O2_LEVEL));
			this.urlParams.put(O3_LEVEL, urlParams.get(O3_LEVEL));
			this.urlParams.put(ID, gooruoId);

			this.urlParams.put(COURSE, urlParams.get(COURSE));
			this.urlParams.put(UNIT, urlParams.get(UNIT));
			this.urlParams.put(LESSON, urlParams.get(LESSON));
			this.urlParams.put(COLLECTION.equalsIgnoreCase(type)?COLLECTION:(ASSESSMENT.equalsIgnoreCase(type))?ASSESSMENT:ASSESSMENT_URL,  title);

//			this.urlParams.put("levelFourType", collectionDo.getTitle()+"#"+"Collection");


		}
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getPosition() {
		return position;
	}

	public boolean getFolderOpenedStatus() {
		return folderIsOpened;
	}

	public void setFolderOpenedStatus(boolean folderIsOpened) {
		this.folderIsOpened = folderIsOpened;
	}

	public boolean getCollectionOpenedStatus() {
		return collectionIsOpened;
	}

	public void setCollectionOpenedStatus(boolean collectionIsOpened) {
		this.collectionIsOpened = collectionIsOpened;
	}

	public void setCollectionItems(List<FolderDo> folderDo) {
		setAllResources(folderDo);
	}

	public void setAllResources(List<FolderDo> folderDo) {
		if (folderDo.size() > 0) {
			for(FolderDo folderItemDo : folderDo) {
				//addCollectionItem(folderItemDo, false);
			}
		} else {
//			/contentVerPanel.clear();
			htmlPanel = new HTMLPanel(i18n.GL0854());
			htmlPanel.getElement().getStyle().setTextAlign(TextAlign.CENTER);
			htmlPanel.getElement().getStyle().setMarginLeft(19, Unit.PX);
			htmlPanel.getElement().getStyle().setColor("#999999");
			htmlPanel.getElement().getStyle().setFontStyle(FontStyle.ITALIC);
			htmlPanel.getElement().setId(collectionDo.getGooruOid());
			//contentVerPanel.add(htmlPanel);
		}
	}


	public HashMap<String,String> getUrlParams() {
		return urlParams;
	}


	public void setActiveStyle(boolean isActive) {
		if(isActive){
			titleFocPanel.addStyleName("active");
		} else {
			titleFocPanel.removeStyleName("active");
		}
	}
	public void setOpenStyle(boolean isOpen, int subElementsCount){
		Element[] docElement = DOMUtils.getElementsByClassName("arrows", titleFocPanel.getElement());
		if(docElement.length>0 && !(titleFocPanel.getStyleName().contains("folderStyle-collection"))){
			if(subElementsCount == 0){
				if(docElement[0].getStyle().getDisplay() != null && docElement[0].getStyle().getDisplay().equalsIgnoreCase("block")){
					docElement[0].getStyle().setDisplay(Display.NONE);
				}
			}
			if(subElementsCount > 0){
				if(docElement[0].getStyle().getDisplay() != null && docElement[0].getStyle().getDisplay().equalsIgnoreCase("none")){
					docElement[0].getStyle().setDisplay(Display.BLOCK);
				}
			}
		}
		if(isOpen){
			titleFocPanel.addStyleName("open");
		}else {
			titleFocPanel.removeStyleName("open");
		}
	}
	public void openFolderInShelf() {
		Map<String,String> params = new HashMap<String,String>();
		HashMap<String,String> widgetTitles = new HashMap<String,String>();
		String gooruoId = collectionDo==null?null:collectionDo.getGooruOid();
		String title = collectionDo==null?"":collectionDo.getTitle();
		if(getLevel()==1) {
			params.put(O1_LEVEL, gooruoId);
			widgetTitles.put(COURSE, title);
		} else if(getLevel()==2) {
			params.put(O1_LEVEL, urlParams.get(O1_LEVEL));
			params.put(O2_LEVEL, gooruoId);

			widgetTitles.put(COURSE, urlParams.get(COURSE));
			widgetTitles.put(UNIT, title);

		} else if(getLevel()==3) {
			params.put(O1_LEVEL, urlParams.get(O1_LEVEL));
			params.put(O2_LEVEL, urlParams.get(O2_LEVEL));
			params.put(O3_LEVEL, gooruoId);

			widgetTitles.put(COURSE, urlParams.get(COURSE));
			widgetTitles.put(UNIT, urlParams.get(UNIT));
			widgetTitles.put(LESSON, title);

		}
		params.put("view", AppClientFactory.getPlaceManager().getRequestParameter("view"));
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
//		AppClientFactory.fireEvent(new SetFolderParentNameEvent(title));

	}

	public void openCollectionInShelf() {
    	Map<String,String> params = new HashMap<String,String>();
    	String gooruoId = collectionDo==null?null:collectionDo.getGooruOid();

    	if(getLevel()==2) {
			params.put(O1_LEVEL, urlParams.get(O1_LEVEL));
		} else if(getLevel()==3) {
			params.put(O1_LEVEL, urlParams.get(O1_LEVEL));
			params.put(O2_LEVEL, urlParams.get(O2_LEVEL));
		} else if(getLevel()==4){
			params.put(O1_LEVEL, urlParams.get(O1_LEVEL));
			params.put(O2_LEVEL, urlParams.get(O2_LEVEL));
			params.put(O3_LEVEL, urlParams.get(O3_LEVEL));
		}
    	params.put(ID, gooruoId);
    	params.put("view", AppClientFactory.getPlaceManager().getRequestParameter("view"));
		AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT, params);
	}
//This methods not using
/*	public void getCollectionForm() {
		if(!draggedCollectionId.isEmpty()){
			AppClientFactory.getInjector().getClasspageService().getSCollIdClasspageById(draggedCollectionId, new SimpleAsyncCallback<CollectionDo>(){
				@Override
				public void onSuccess(CollectionDo result) {
					Map<String, String> params = new HashMap<String, String>();
					params.put("collectionId", result.getGooruOid());
					params.put("draggedCollectionTitle", result.getTitle());
					params.put("droppingInto", "Folder");
					params.put("selectedFolderId", folderDo.getGooruOid());
					titleFocPanel.removeStyleName("draggingInto");
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.COLLECTION, params);
				}
			});
		}
	}

	UpdateShelfFolderNameHandler updateShelfFolderName = new UpdateShelfFolderNameHandler(){
		@Override
		public void updateShelfFolderName(String folderName,String folderId) {
			if(collectionDo.getGooruOid().equals(folderId)){
				collectionDo.setTitle(folderName);
				titleLbl.setHTML(folderName);
				titleLbl.getElement().setAttribute("alt", folderName);
				titleLbl.getElement().setAttribute("title", folderName);
			}
		}
	};*/
	/**
	 * @return the titleLbl
	 */
	public HTML getTitleLbl() {
		return titleLbl;
	}
	/**
	 * @return the titleFocPanel
	 */
	public FocusPanel getTitleFocPanel() {
		return titleFocPanel;
	}

	/**
	 * Sets the tree widget type.
	 * @param widgetType {@link String}
	 */
	public void setTreeWidgetType(String widgetType) {
		this.widgetType = widgetType;
	}

	public String getTreeWidgetType(){
		return widgetType;
	}

	public void setUpdatedWidgetsTitleType( HashMap<String,String> updateWidgetTitles) {
		this.updateWidgetTitles = updateWidgetTitles;
	}

	public HashMap<String,String> getUpdatedWidgetsTitleType() {
		return updateWidgetTitles;
	}

	public void setUrlParams(HashMap<String, String> urlParams) {
		this.urlParams = urlParams;
	}

	public void setFolderListDo(List<FolderDo> folderListDoChild) {
		this.folderListDoChild = folderListDoChild;
	}


	public List<FolderDo> getFolderListDo() {
		return folderListDoChild;
	}
}
