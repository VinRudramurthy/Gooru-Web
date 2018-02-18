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
package org.ednovo.gooru.client.mvp.assessments.play.resource.framebreaker;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollectionItemDo;
import org.ednovo.gooru.client.uc.PlayerBundle;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.shared.util.ClientConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiConstructor;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
/**
 *
 * @fileName : ResourcePlayerMetadataView.java
 *
 * @description :
 *		This class is used when a particular resource is marked as Linked out.
 *
 * @version : 1.0
 *
 * @date: Jan 2, 2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class ResourceFrameBreakerView extends Composite implements ClientConstants{

	@UiField Button btnResourceLink;
	@UiField Label lblGooruFieldTrip, lblGooruFieldTripDescUnforseen, lblDontForget, lblGooruFieldTripDescOriginal,supportTip;//learnMoreLbl
	@UiField Image imgFieldTrip;
	@UiField FlowPanel resoruceFrameBrakerContainer;
	@UiField HTMLPanel resourceCategory;

	CollectionItemDo collectionItemDo = null;

	private static final String DEFULT_IMAGE_PREFIX = "images/default-";

	private static final String PNG = ".png";
	String defaultResourceCategory="";
	private static ResourceFrameBreakerViewUiBinder uiBinder = GWT.create(ResourceFrameBreakerViewUiBinder.class);

	interface ResourceFrameBreakerViewUiBinder extends UiBinder<Widget, ResourceFrameBreakerView> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);
	/**
	 * Default constructor
	 */
	public ResourceFrameBreakerView(){
		initWidget(uiBinder.createAndBindUi(this));
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		setLabelsAndIds();
	}
	/**
	 *
	 * @param collectionItemDo
	 */
	@UiConstructor
	public ResourceFrameBreakerView(final CollectionItemDo collectionItemDo,boolean isGoogleFile){
		initWidget(uiBinder.createAndBindUi(this));
		PlayerBundle.INSTANCE.getPlayerStyle().ensureInjected();
		this.collectionItemDo = collectionItemDo;
		if(!isGoogleFile){
			setLabelsAndIds();
			supportTip.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().supportTip());
			supportTip.setText(i18n.GL1453());
			supportTip.getElement().setId("lblSupportTip");
			supportTip.getElement().setAttribute("alt",i18n.GL1453());
			supportTip.getElement().setAttribute("title",i18n.GL1453());
			btnResourceLink.addStyleName(PlayerBundle.INSTANCE.getPlayerStyle().btnResourceLink());
		}else{
			if(btnResourceLink!=null){
			btnResourceLink.removeFromParent();
			}
		}
		if(collectionItemDo!=null && collectionItemDo.getResource()!=null){
			imgFieldTrip.setUrl(collectionItemDo.getResource().getThumbnails() != null ? collectionItemDo.getResource().getThumbnails().getUrl() : DEFULT_IMAGE_PREFIX + defaultResourceCategory.toLowerCase() + PNG);
			if(collectionItemDo.getResource().getResourceFormat()!=null){
				defaultResourceCategory = collectionItemDo.getResource().getResourceFormat().getDisplayName()!=null?collectionItemDo.getResource().getResourceFormat().getDisplayName():"";
				resourceCategory.addStyleName(getResourceTypeImage(collectionItemDo.getResource().getResourceFormat().getDisplayName()!=null?collectionItemDo.getResource().getResourceFormat().getDisplayName():""));
			}
			if(defaultResourceCategory!=null){
				if(LESSON.equalsIgnoreCase(defaultResourceCategory)||TEXTBOOK.equalsIgnoreCase(defaultResourceCategory)||HANDOUT.equalsIgnoreCase(defaultResourceCategory))
				{
					defaultResourceCategory=defaultResourceCategory.replaceAll("Lesson", "Text").replaceAll("Textbook", "Text").replaceAll("Handout", "Text").replaceAll("lesson", "Text").replaceAll("textbook", "Text").replaceAll("handout", "Text");
				}
				if(SLIDE.equalsIgnoreCase(defaultResourceCategory))
				{
					defaultResourceCategory=defaultResourceCategory.replaceAll("Slide","Image").replaceAll("slide","Image");
				}
				if(EXAM.equalsIgnoreCase(defaultResourceCategory) || CHALLENGE.equalsIgnoreCase(defaultResourceCategory)||WEBSITE.equalsIgnoreCase(defaultResourceCategory))
				{
					defaultResourceCategory=defaultResourceCategory.replaceAll("Exam","Webpage").replaceAll("Challenge", "Webpage").replaceAll("exam","Webpage").replaceAll("challenge", "Webpage");
				}
			}
			imgFieldTrip.addErrorHandler(new ErrorHandler() {

				@Override
				public void onError(ErrorEvent event) {
					imgFieldTrip.setUrl(DEFULT_IMAGE_PREFIX + defaultResourceCategory.toLowerCase() + PNG);
				}
			});
		}

	}
	public void setFileDeletedMessage(){
		if(btnResourceLink!=null){
		btnResourceLink.removeFromParent();
		}
		lblGooruFieldTrip.getElement().getStyle().setPaddingLeft(50, Unit.PX);
		lblGooruFieldTrip.getElement().getStyle().setPaddingRight(50, Unit.PX);
		lblGooruFieldTrip.setText(i18n.GL2169());
	}
	public void setFilePermissionMessage(){
		lblGooruFieldTrip.getElement().getStyle().setPaddingLeft(50, Unit.PX);
		lblGooruFieldTrip.getElement().getStyle().setPaddingRight(50, Unit.PX);
		lblGooruFieldTrip.setText(i18n.GL2169());
	}
	/**
	 *
	 * @function setLabelsAndIds
	 *
	 * @created_date : Jan 2, 2014
	 *
	 * @description
	 * 		To set Labels and Id for buttons.
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 */
	private void setLabelsAndIds() {
		btnResourceLink.setText(i18n.GL0548());
		btnResourceLink.getElement().setId("btnResourceLink");
		btnResourceLink.getElement().setAttribute("alt",i18n.GL0548());
		btnResourceLink.getElement().setAttribute("title",i18n.GL0548());

		lblGooruFieldTrip.setText(i18n.GL0549());
		lblGooruFieldTrip.getElement().setId("lblGooruFieldTrip");
		lblGooruFieldTrip.getElement().setAttribute("alt",i18n.GL0549());
		lblGooruFieldTrip.getElement().setAttribute("title",i18n.GL0549());

		lblGooruFieldTripDescOriginal.setText("");
		lblGooruFieldTripDescOriginal.getElement().setId("lblGooruFieldTripDescOriginal");
		lblGooruFieldTripDescOriginal.getElement().setAttribute("alt","");
		lblGooruFieldTripDescOriginal.getElement().setAttribute("title","");

		lblDontForget.setText(i18n.GL0551());
		lblDontForget.getElement().setId("lblDontForget");
		lblDontForget.getElement().setAttribute("alt",i18n.GL0551());
		lblDontForget.getElement().setAttribute("title",i18n.GL0551());

		imgFieldTrip.getElement().setId("imgFieldTrip");
		imgFieldTrip.setUrl("images/framebraker/field-trip.png");

		resoruceFrameBrakerContainer.getElement().setId("fpnlResoruceFrameBrakerContainer");
		resourceCategory.getElement().setId("pnlResourceCategory");
		lblGooruFieldTripDescUnforseen.getElement().setId("lblGooruFieldTripDescUnforseen");
		supportTip.getElement().setId("lblSupportTip");
	}
	/**
	 * default method.
	 */
	public void onLoad(){
		super.onLoad();
	}

	/**
	 *
	 * @function openResurceLink
	 *
	 * @created_date : Jan 2, 2014
	 *
	 * @description
	 * 		To open original resource link in new tab.
	 *
	 * @parm(s) : @param ClickEvent
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 */
	@UiHandler("btnResourceLink")
	public void openResurceLink(ClickEvent event){
		MixpanelUtil.mixpanelEvent("Player_Click_Linked_Out_Resource");
		Window.open(collectionItemDo.getResource().getUrl(), "_blank", "");
	}

	/**
	 *
	 * @function getResourceTypeImage
	 *
	 * @created_date : Jan 2, 2014
	 *
	 * @description
	 *
	 * @parm(s) : @param resourceType
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 */
	public String getResourceTypeImage(String resourceType){
		if(VIDEO.equalsIgnoreCase(resourceType)||VIDEOS.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().videoResourceType();
		}else if(INTERACTIVE.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().interactiveResourceType();
		}
		else if(WEBSITE.equalsIgnoreCase(resourceType)||WEBPAGE.equalsIgnoreCase(resourceType)||CHALLENGE.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().websiteResourceType();
		}
		else if(SLIDE.equalsIgnoreCase(resourceType)||IMAGE.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().imageResourceType();
		}
		else if(TEXTBOOK.equalsIgnoreCase(resourceType)||LESSON.toLowerCase().equalsIgnoreCase(resourceType)||HANDOUT.equalsIgnoreCase(resourceType)||TEXT.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().textResourceType();
		}
		else if(QUESTION.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().questionResourceType();
		}
		else if(AUDIO.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().audioResourceType();
		}else if(OTHER.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().otherResourceType();
		}
		else {
			return PlayerBundle.INSTANCE.getPlayerStyle().otherResourceType();
		}
	}

	/**
	 *
	 * @function getResourceDefaultImage
	 *
	 * @created_date : Jan 2, 2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param resourceType
	 * @parm(s) : @return
	 *
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 */

	public String getResourceDefaultImage(String resourceType){
		if(VIDEO.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().videoResourceDefault();
		}else if(INTERACTIVE.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().interactiveResourceDefault();
		}
		else if(WEBSITE.equalsIgnoreCase(resourceType)||WEBPAGE.equalsIgnoreCase(resourceType)||EXAM.equalsIgnoreCase(resourceType)||CHALLENGE.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().websiteResourceDefault();
		}
		else if(SLIDE.equalsIgnoreCase(resourceType)||IMAGE.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().imageResourceDefault();
		}
		else if(TEXTBOOK.equalsIgnoreCase(resourceType)||LESSON.toLowerCase().equalsIgnoreCase(resourceType)||HANDOUT.equalsIgnoreCase(resourceType)||TEXT.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().textResourceDefault();
		}
		else if(QUESTION.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().questionResourceDefault();
		}
		else if(AUDIO.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().audioResourceDefault();
		}else if(OTHER.equalsIgnoreCase(resourceType)){
			return PlayerBundle.INSTANCE.getPlayerStyle().otherResourceDefault();
		}else {
			return PlayerBundle.INSTANCE.getPlayerStyle().websiteResourceDefault();
		}
	}

}
