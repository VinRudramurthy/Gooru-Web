package org.ednovo.gooru.client.mvp.play.collection.end;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;

public interface CollectionPlaySummaryCBundle extends ClientBundle{
	static final CollectionPlaySummaryCBundle INSTANCE = GWT.create(CollectionPlaySummaryCBundle.class);
	public interface CollectionSummaryCss extends CssResource{
			String container();
			String collectionsProgressContainerMain();
			String loadingImageMainDiv();
			String loadingImageForSelfEdit();
			String summary_title();
			String collectionSummaryDetailsWrapper();
			String left_container();
			String question_resource_details();
			String collectionLastAccessed();
			String gooru_session_data_content();
			String collection_last_accessed();
			String d_filter_session_text();
			String session_data();
			String filterDropDownSessions();
			String htmlpanlProgress();
			String pnlSummary();
			String sessionpnl();
			String resource_in_collection_teacher_summary();
			String collection_session_panel();
			String collection_image();
			
	}
	@NotStrict
	@Source("collectionSummary.css")
	CollectionSummaryCss css();
	
	@NotStrict
	@Source("res_collectionSummary.css")
	CollectionSummaryCss getResponsiveStyle();
	
	@NotStrict
	@Source("res_collectionSummary1.css")
	CollectionSummaryCss getResponsive1Style();
}
