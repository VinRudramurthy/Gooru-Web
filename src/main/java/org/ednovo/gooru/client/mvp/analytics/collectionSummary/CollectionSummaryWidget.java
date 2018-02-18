package org.ednovo.gooru.client.mvp.analytics.collectionSummary;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.analytics.CollectionSummaryMetaDataDo;
import org.ednovo.gooru.application.shared.model.analytics.PrintUserDataDO;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.mvp.analytics.util.AnalyticsUtil;
import org.ednovo.gooru.shared.util.ClientConstants;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class CollectionSummaryWidget extends Composite implements ClientConstants {

	private static CollectionSummaryWidgetUiBinder uiBinder = GWT
			.create(CollectionSummaryWidgetUiBinder.class);

	interface CollectionSummaryWidgetUiBinder extends
			UiBinder<Widget, CollectionSummaryWidget> {
	}
	@UiField Image collectionImage;
	@UiField HTMLPanel pnlCollectionLastAccessed,sessionsPnl;
	@UiField InlineLabel sessionValue,sessionText,sessionAccessedTime,collectionLastAccessedlbl,collectionTitle,collectionResourcesCount,collectionLastAccessed;
	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	/**
	 * Constructor
	 */
	public CollectionSummaryWidget() {
		initWidget(uiBinder.createAndBindUi(this));
		setStaticData();
	}
	/**
	 * This method is used to set static data.
	 */
	void setStaticData(){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				StringUtil.setAttributes(collectionImage.getElement(), "imgCollectionImage", null, null);

				StringUtil.setAttributes(sessionsPnl.getElement(), "pnlSessionsPnl", null, null);

				StringUtil.setAttributes(sessionValue.getElement(), "spnSessionValue", null, null);
				StringUtil.setAttributes(sessionText.getElement(), "spnSessionText", null, null);
				StringUtil.setAttributes(sessionAccessedTime.getElement(), "spnSessionAccessedTime", null, null);
				StringUtil.setAttributes(collectionLastAccessedlbl.getElement(), "spnCollectionLastAccessedlbl", null, null);
				StringUtil.setAttributes(collectionTitle.getElement(), "spnCollectionTitle", null, null);
				StringUtil.setAttributes(collectionResourcesCount.getElement(), "spnCollectionResourcesCount", null, null);
				StringUtil.setAttributes(collectionLastAccessed.getElement(), "spnCollectionLastAccessed", null, null);

			}
		});
	}
	/**
	 * This method is used to set the data.
	 * @param result
	 */
	public void setData(final CollectionSummaryMetaDataDo result){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				collectionImage.setVisible(true);
				sessionsPnl.setVisible(false);
				collectionLastAccessedlbl.setText(i18n.GL2271());
				if(!StringUtil.isEmpty(result.getTitle())){
					collectionTitle.setText(result.getTitle());
				}
				collectionLastAccessed.setText(AnalyticsUtil.getCreatedTime(Long.toString(result.getLastAccessed())));
				if(!StringUtil.isEmpty(result.getThumbnail())){
					collectionImage.setUrl(result.getThumbnail());
				}else{
					collectionImage.setUrl("../images/analytics/default-collection-image.png");
				}
				collectionImage.addErrorHandler(new ErrorHandler() {
					@Override
					public void onError(ErrorEvent event) {
						collectionImage.setUrl("../images/analytics/default-collection-image.png");
					}
				});
				collectionResourcesCount.setText((result.getResourceCount())+" Resources | "+result.getNonResourceCount()+" Questions");

			}
		});
	}

	/**
	 * This method is used to set analytics data.
	 * @param result
	 * @param printUserDataDO
	 */
	public void setDataAnalyticsData(final CollectionSummaryMetaDataDo result,final PrintUserDataDO printUserDataDO){
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				collectionImage.setVisible(false);
				if(printUserDataDO!=null && !StringUtil.isEmpty(printUserDataDO.getUserName())){
					sessionsPnl.setVisible(true);
					collectionTitle.setText(i18n.GL0645()+" "+i18n.GL_SPL_SEMICOLON()+" "+result.getTitle());
					collectionLastAccessedlbl.setText(SORT_BY);
					collectionLastAccessed.setText(printUserDataDO.getUserName());
					sessionAccessedTime.setText(i18n.GL2272()+" "+printUserDataDO.getSessionStartTime());
					sessionText.setText(i18n.GL2273()+" "+i18n.GL_SPL_SEMICOLON());
					sessionValue.setText(printUserDataDO.getSession());
				}else if(printUserDataDO!=null &&  printUserDataDO.getUserName()==null){
					sessionsPnl.setVisible(true);
					collectionTitle.setText(i18n.GL0645()+" "+i18n.GL_SPL_SEMICOLON()+" "+result.getTitle());
					collectionLastAccessedlbl.setText("");
					collectionLastAccessed.setText("");
					sessionAccessedTime.setText(i18n.GL2272()+" "+printUserDataDO.getSessionStartTime());
					sessionText.setText(i18n.GL2273()+" "+i18n.GL_SPL_SEMICOLON());
					sessionValue.setText(printUserDataDO.getSession());
				}else{
					sessionsPnl.setVisible(false);
					collectionTitle.setText(i18n.GL0645()+" "+i18n.GL_SPL_SEMICOLON()+" "+result.getTitle());
					collectionLastAccessedlbl.setText(SORT_BY);
					collectionLastAccessed.setText(i18n.GL2289());

				}
				collectionResourcesCount.setText(StringUtil.generateMessage(i18n.GL3258(), String.valueOf(result.getResourceCount()), String.valueOf(result.getNonResourceCount())));
			}
		});
	}
	public HTMLPanel getCollectionLastAccessPnl(){
		return pnlCollectionLastAccessed;
	}
}
