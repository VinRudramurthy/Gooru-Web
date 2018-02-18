package org.ednovo.gooru.client.mvp.gshelf.coursedetails;

import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.client.uc.SpanPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class ContentVisibilityWidget extends Composite {

	@UiField HTMLPanel allClassPanel, tableItems;
	@UiField SpanPanel className;
	@UiField Button btnPublish;
	@UiField Anchor anrAllClasses;
	
	private static ContentVisibilityWidgetUiBinder uiBinder = GWT
			.create(ContentVisibilityWidgetUiBinder.class);

	interface ContentVisibilityWidgetUiBinder extends
			UiBinder<Widget, ContentVisibilityWidget> {
	}

	public ContentVisibilityWidget(ClasspageDo classObj, String courseId) {
		initWidget(uiBinder.createAndBindUi(this));
		className.setText(classObj.getName());
		setIds();
		setUnitData();
	}

	private void setIds() {
		allClassPanel.getElement().setId("shareContentInClass");
	}
	
	public Anchor getAnrAllClasses() {
		return anrAllClasses;
	}
	
	private void setUnitData() {
		tableItems.clear();
		for(int i=0;i<5;i++) {
			final ContentVisibilityItemWidget unitWidget = new ContentVisibilityItemWidget("unit", "Unit "+(i+1));
			unitWidget.getLblContentName().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					setLessonData(unitWidget);
				}
			});
			tableItems.add(unitWidget);
		}
	}
	
	public void setLessonData(ContentVisibilityItemWidget contentWidget) {
		for(int i=0;i<3;i++) {
			final ContentVisibilityItemWidget lessonWidget = new ContentVisibilityItemWidget("lesson", "Lesson "+(i+1));
			lessonWidget.getLblContentName().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					setCollectionData(lessonWidget);
				}
			});
			contentWidget.getRowItem().add(lessonWidget);
		}
	}
	
	public void setCollectionData(ContentVisibilityItemWidget contentWidget) {
		for(int i=0;i<3;i++) {
			final ContentVisibilityItemWidget collectionWidget = new ContentVisibilityItemWidget("collection", "Collection "+(i+1));
			contentWidget.getRowItem().add(collectionWidget);
		}
	}
}