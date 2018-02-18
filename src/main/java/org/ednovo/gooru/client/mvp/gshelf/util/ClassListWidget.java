/**
 * 
 */
package org.ednovo.gooru.client.mvp.gshelf.util;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.client.uc.SpanPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author janamitra
 *
 */
public class ClassListWidget extends Composite {

	private static ClassListWidgetUiBinder uiBinder = GWT
			.create(ClassListWidgetUiBinder.class);

	interface ClassListWidgetUiBinder extends UiBinder<Widget, ClassListWidget> {
	}

	@UiField Anchor classNameAnch ,editClassAnch;
	
	@UiField SpanPanel studentCount;
	
	String courseId;
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
	 * @param classId 
	 * @param name 
	 * @param courseId 
	 */
	public ClassListWidget(ClasspageDo classObj, String courseId) {
		initWidget(uiBinder.createAndBindUi(this));
		this.courseId=courseId;
		classNameAnch.setText(classObj.getName());
		classNameAnch.addClickHandler(new ClassNameClickHandler(classObj.getClassUid()));
		studentCount.setText("("+classObj.getMemberCount()+" Students)");
		//editClassAnch.addClickHandler(new ClassNameClickHandler(classId));
	}
	
	public Anchor getEditClassAnchor() {
		return editClassAnch;
	}
	
	private class ClassNameClickHandler implements ClickHandler{
        String classId;
		public ClassNameClickHandler(String classId) {
			this.classId=classId;
		}

		@Override
		public void onClick(ClickEvent event) {
			Map<String, String> params= new HashMap<String, String>();
			params.put("classpageId", classId);
			params.put("c-id", courseId);
			params.put("type", "course-view");
			params.put("page-view", "teach-dashboard");
			params.put("subpage-view", "reports");
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.EDIT_CLASS,params);
		}
	}

}
