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
public class AverageReactionsWidget extends Composite {
	
	private static AverageReactionsWidgetUiBinder uiBinder = GWT
			.create(AverageReactionsWidgetUiBinder.class);
	
	private MessageProperties i18n = GWT.create(MessageProperties.class);

	interface AverageReactionsWidgetUiBinder extends
	UiBinder<Widget, AverageReactionsWidget> {
	}
	
	@UiField public HTMLPanel averageReactionsPanel;

	public AverageReactionsWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	private String removeHtmlTags(String html){
		html = html.replaceAll("</p>", " ").replaceAll("<p>", "").replaceAll("<br data-mce-bogus=\"1\">", "").replaceAll("<br>", "").replaceAll("</br>", "");
		return html;
	}
}