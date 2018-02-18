/**
 * 
 */
package org.ednovo.gooru.client.uc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.CollaboratorsDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.search.CollectionSearchResultDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.search.SearchResultWrapperCBundle;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author gooru
 *
 */
public class CollaboratorsUc extends Composite {

	private static CollaboratorsUcUiBinder uiBinder = GWT
			.create(CollaboratorsUcUiBinder.class);
	
	MessageProperties i18n = GWT.create(MessageProperties.class);

	interface CollaboratorsUcUiBinder extends UiBinder<Widget, CollaboratorsUc> {
	}
	
	@UiField FlowPanel teamContainer;

	/**
	 * Because this class has a default constructor, it can
	 * be used as a binder template. In other words, it can be used in other
	 * *.ui.xml files as follows:
	 * <ui:UiBinder xmlns:ui="urn:ui:com.google.gwt.uibinder"
	 *   xmlns:g="urn:import:**user's package**">
	 *  <g:**UserClassName**>Hello!</g:**UserClassName>
	 * </ui:UiBinder>
	 * Note that depending on the widget that is used, it may be necessary to
	 * implement HasHTML instead of HasText.
	 */
	public CollaboratorsUc(CollectionDo collectionDo) {
		initWidget(uiBinder.createAndBindUi(this));
		teamContainer.getElement().setId("fpnlTeamContainer");
		if(collectionDo!=null && collectionDo.getGooruOid()!=null){
			AppClientFactory.getInjector().getCollaboratorsService().getAssociatedCollaborators(collectionDo.getGooruOid(), "active", new SimpleAsyncCallback<Map<String,ArrayList<CollaboratorsDo>>>() {
				@Override
				public void onSuccess(Map<String, ArrayList<CollaboratorsDo>> result) {
			     	renderTeamNames(teamContainer, getTeamMembersNames(result.get("active")));
				}
			});
		}
	}
	
	public CollaboratorsUc(CollectionSearchResultDo collectionResultDo) {
		initWidget(uiBinder.createAndBindUi(this));
		teamContainer.getElement().setId("fpnlTeamContainer");
		if(collectionResultDo!=null && collectionResultDo.getGooruOid()!=null){
			AppClientFactory.getInjector().getCollaboratorsService().getAssociatedCollaborators(collectionResultDo.getGooruOid(), "active", new SimpleAsyncCallback<Map<String,ArrayList<CollaboratorsDo>>>() {
				@Override
				public void onSuccess(Map<String, ArrayList<CollaboratorsDo>> result) {
			     	renderTeamNames(teamContainer, getTeamMembersNames(result.get("active")));
				}
			});
		}
	}

	protected List<String> getTeamMembersNames(ArrayList<CollaboratorsDo> arrayList) {
		List<String> teamNames= new ArrayList<String>();
		for(int i=0; i<arrayList.size(); i++){
			teamNames.add(arrayList.get(i).getUsername());
		}
		return teamNames;
	}
	
	protected void renderTeamNames(FlowPanel teamContainer2,List<String> teamMembersNames) {
		teamContainer2.clear();
		FlowPanel toolTipwidgets = new FlowPanel();
		if(teamMembersNames!=null && teamMembersNames.size()>0){
			for (int count = 0; count < teamMembersNames.size(); count++) {
				Label label = new Label(teamMembersNames.get(count));
				label.setStyleName(SearchResultWrapperCBundle.INSTANCE.css().moreMetaLbl());
				toolTipwidgets.add(label);
			}
		}
		if (teamMembersNames != null && teamMembersNames.size() > 0) {
			DownToolTipWidgetUc toolTipUc = new DownToolTipWidgetUc(new Label(" "+i18n.GL1117()), toolTipwidgets);
			toolTipUc.setStyleName(PlayerBundle.INSTANCE.getPlayerStyle().teamHyperLink());
			teamContainer2.add(toolTipUc);
			toolTipUc.getTooltipPopUpUc(i18n.GL1117());
		}
	}
}
