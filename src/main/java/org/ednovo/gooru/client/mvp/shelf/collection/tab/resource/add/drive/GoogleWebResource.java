package org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.model.drive.GoogleDriveItemDo;
import org.ednovo.gooru.client.mvp.shelf.collection.tab.resource.add.drive.event.DriveEvent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class GoogleWebResource extends Composite {
	private static GoogleWebResourceUiBinder uiBinder = GWT
			.create(GoogleWebResourceUiBinder.class);

	interface GoogleWebResourceUiBinder extends
			UiBinder<Widget, GoogleWebResource> {
	}
	
	String folderMimeType = "application/vnd.google-apps.folder";
	String type = null;
	String dataIconType = null;
	@UiField
	Label imageIcon;
	@UiField
	HTML driveText;
	@UiField
	HTMLPanel contentPanel;
	@UiField
	HTMLPanel folderContent;
	
	GoogleDriveItemDo driveDo = new GoogleDriveItemDo();

	public GoogleWebResource(GoogleDriveItemDo driveDo) {
		initWidget(uiBinder.createAndBindUi(this));
		this.driveDo = driveDo;
		driveText.setText(driveDo.getTitle());
		driveText.getElement().setId("htmlDriveText");
		driveText.getElement().setAttribute("alt", driveDo.getTitle());
		driveText.getElement().setAttribute("title", driveDo.getTitle());
		contentPanel.getElement().setId("pnlContentPanel");
		folderContent.getElement().setId("pnlFolderContent");
		imageIcon.getElement().setId("lblImageIcon");
		contentPanel.addDomHandler(contentClick, ClickEvent.getType());
		type = driveDo.getMimeType();
		String dataType[] = type.split("\\.");
		dataIconType = dataType[0];
	}

	ClickHandler contentClick = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {
			if (type.equalsIgnoreCase(folderMimeType)) {
				folderContent(driveDo.getId());
			} else {
				AppClientFactory.fireEvent(new DriveEvent(driveDo));
			}
		}
	};

	private void folderContent(String id) {
		folderContent.clear();
	}
}
