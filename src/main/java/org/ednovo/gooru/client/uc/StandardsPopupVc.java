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
package org.ednovo.gooru.client.uc;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @fileName : StandardsPopupVc.java
 *
 * @description :
 *	This class is used to show the Standards in Popup.
 *
 * @version : 1.0
 *
 * @date: Aug 2, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class StandardsPopupVc extends PopupPanel  {

//	public static final String STANDARD_CODE = i18n.GL1049;

//	public static final String STANDARD_DESCRIPTION =i18n.GL0904.toLowerCase();

	@UiField
	ScrollPanel spanelStandardsPanel;

	@UiField
	HTMLPanel mainHtmlPanel,standardsText;

	@UiField Label cancelButton;

	Iterator<Map<String, String>> iterator = null;

	List<Map<String, String>> standards = null;

	@UiTemplate("StandardsPopupVc.ui.xml")
	interface Binder extends UiBinder<Widget, StandardsPopupVc> {

	}

	private static final Binder binder = GWT.create(Binder.class);

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 *
	 * @param standards
	 */
	public StandardsPopupVc(List<Map<String, String>> standards,boolean isStandards) {
		super(false);
		add(binder.createAndBindUi(this));
		this.setGlassEnabled(true);
		standardsText.getElement().setId("pnlStandardsText");
		if(isStandards){
			standardsText.getElement().setInnerHTML(i18n.GL0575());
			standardsText.getElement().setAttribute("alt", i18n.GL0575());
			standardsText.getElement().setAttribute("title", i18n.GL0575());
		}else{
			standardsText.getElement().setInnerHTML(i18n.GL3199());
			standardsText.getElement().setAttribute("alt", i18n.GL3199());
			standardsText.getElement().setAttribute("title", i18n.GL3199());
		}
		cancelButton.getElement().setId("lblCancelButton");
		spanelStandardsPanel.getElement().setId("sbSpanelStandardsPanel");
		mainHtmlPanel.getElement().setId("pnlMainHtmlPanel");
		this.standards = standards;
		this.iterator = standards.iterator();
		this.getElement().setAttribute("style", "z-index:99999");
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));

		setStandards();

		this.center();
	}
	/**
	 *
	 * @function setStandards
	 *
	 * @created_date : Aug 2, 2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : {Will be used standards}
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void setStandards() {
		int count = 0;
		if (iterator != null) {
			while (this.iterator.hasNext()) {
				Map<String, String> standard = this.iterator.next();

				String stdCode = standard.get("code");
				String stdDec = standard.get("description");

				final HTMLPanel standardsPanel = new HTMLPanel("");
				standardsPanel.setStyleName("Uc-divContainer");
				standardsPanel.getElement().getStyle().setMarginBottom(5, Unit.PX);

				final Label standardsLabel = new Label(stdCode);
				standardsLabel.setStyleName("Uc-searchStandard");

				final Label standardsDescLabel = new Label(stdDec);
				standardsDescLabel.setStyleName("Uc-standardsDesc");

				standardsPanel.add(standardsLabel);
				standardsPanel.add(standardsDescLabel);
				mainHtmlPanel.add(standardsPanel);

				count++;
			}
		}else{
		}
	}

	/**
	 * Added click handler to hide the login popup.
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("cancelButton")
	public void onCancelClicked(ClickEvent clickEvent) {
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.ASSESSMENT_PLAY) ||AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equalsIgnoreCase(PlaceTokens.COLLECTION_PLAY)){
			Window.enableScrolling(false);
		}else{
			Window.enableScrolling(true);
		}
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		hide();
	}
}
