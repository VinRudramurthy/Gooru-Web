package org.ednovo.gooru.client.mvp.gshelf.collectiondetails.widgets;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.folder.FolderDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.uc.PPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;

public class LanguageView extends Composite {

	@UiField HTMLPanel mainContainer;
	
	@UiField TextArea languegeObjective;
	@UiField PPanel errorPanel;
	
	boolean isProfanity;
	
	private static LanguageViewUiBinder uiBinder = GWT
			.create(LanguageViewUiBinder.class);

	interface LanguageViewUiBinder extends	UiBinder<Widget, LanguageView> {
	}

	static MessageProperties i18n = GWT.create(MessageProperties.class);
	
	
	public LanguageView() {
		initWidget(uiBinder.createAndBindUi(this));
		mainContainer.getElement().setId("languageObjective");
		errorPanel.setVisible(false);
		languegeObjective.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent event) {
				// TODO Auto-generated method stub
				isProfanity=false;
				errorPanel.setVisible(false);
				languegeObjective.removeStyleName("textAreaErrorMessage");
				checkProfanity(languegeObjective.getText());

				
			}
		});
		
	
		
	}
	
	public String getLanguageObjective(){
		
		if(!isProfanity){
			return languegeObjective.getText();
		}else{
			return "";
		}
		
	}
	
	
	public void checkProfanity(String textValue){
		final Map<String, String> parms = new HashMap<String, String>();
		parms.put("text",textValue);
		AppClientFactory.getInjector().getResourceService().checkProfanity(parms, new SimpleAsyncCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean value) {
				isProfanity=value;
				if(isProfanity){
					errorPanel.setVisible(true);
					languegeObjective.addStyleName("textAreaErrorMessage");
				}
			}
		});
	}
	
	public void reset(){
		languegeObjective.setText("");
	
	}
	
	public void setLanguageObjective(FolderDo folderDo){
		reset();
		if(folderDo!=null){
			isProfanity=false;
			errorPanel.setVisible(false);
			languegeObjective.removeStyleName("textAreaErrorMessage");
			languegeObjective.setText(folderDo.getLanguageObjective());
		}
	}
	
}
