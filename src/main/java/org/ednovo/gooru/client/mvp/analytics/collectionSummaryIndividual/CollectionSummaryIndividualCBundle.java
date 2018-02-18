package org.ednovo.gooru.client.mvp.analytics.collectionSummaryIndividual;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.CssResource.NotStrict;

public interface CollectionSummaryIndividualCBundle extends ClientBundle{
	static final CollectionSummaryIndividualCBundle INSTANCE = GWT.create(CollectionSummaryIndividualCBundle.class);
	public interface CollectionSummaryIndividualCss extends CssResource{
		    String alignCenterAndBackground();
		    String alignLeft();
		    String setMarginAuto();
	        String reaction_explain1();
	        String reaction_understand1();
	        String reaction_mean1();
	        String reaction_dontunderstand1();
	        String reaction_needhelp1();
	        String reaction_redneedhelp();
	        String reaction_reddontunderstand();
	        String correct_legend_one();
	        String correct_legend_other();
	        String incorrect_legend();
	        String floatLeft();
	        String resource_monitor_header_title();
	        
	        String category_new_type_audio();
	        String category_new_type_image();
	        String category_new_type_other();
	        String category_new_type_interactive();
	        String category_new_type_question();
	        String category_new_type_text();
	        String category_new_type_video();
	        String category_new_type_webpage();
	        
	        String setProgressBar();
	        String resourceBreakDownTimeSpent();
	        String viewResponseTextOpended();
	        String displayMessageTextForScoredQuestions();
	        String displayMessageTextForOEQuestions();
	        
	        String setOETextPopupCenter();
	        String setGlassStyleName();
	        String setEmailGlassStyleName();
	        String setEmailPopupCenter();
	        @ClassName("no-gutter")
	        String no_gutter();
	        String individualScore();
	        String collectionOverViewpnl();
	        String collectionOverviewText();
	        String collectionTimeSpentDetails();
	        String collectionBreakDown_infobox();
	        String collectionBreakDown_infobox_titles();
	        String filterDropDown();
	        String printWidget();
	        String showEmptyMessage();
	        String correct_incorrect_text();
	}
	@NotStrict
	@Source("collectionSummaryIndividual.css")
	CollectionSummaryIndividualCss css();
	
	@NotStrict
	@Source("res_collectionSummaryIndividual.css")
	CollectionSummaryIndividualCss getResponsiveStyle();
	
	@NotStrict
	@Source("res_collectionSummaryIndividual1.css")
	CollectionSummaryIndividualCss getResponsive1Style();
}