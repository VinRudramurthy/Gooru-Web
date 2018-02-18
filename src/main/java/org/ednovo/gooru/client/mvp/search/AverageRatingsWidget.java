/**
 * 
 */
package org.ednovo.gooru.client.mvp.search;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Sravanthi
 *
 */
public class AverageRatingsWidget extends Composite {
	
	private static AverageRatingsWidgetUiBinder uiBinder = GWT
			.create(AverageRatingsWidgetUiBinder.class);
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	interface AverageRatingsWidgetUiBinder extends
	UiBinder<Widget, AverageRatingsWidget> {
	}
	
	@UiField public HTMLPanel averageRatingsPanel;

	public AverageRatingsWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	private String removeHtmlTags(String html){
		html = html.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
		return html;
	}
}