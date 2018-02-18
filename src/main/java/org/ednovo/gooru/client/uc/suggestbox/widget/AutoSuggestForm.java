package org.ednovo.gooru.client.uc.suggestbox.widget;

import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.TextBox;
/**
 * 
 * @fileName : AutoSuggestForm.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: Jan 29, 2014
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public abstract class AutoSuggestForm extends Composite {
	FlowPanel form;
	InputListWidget txtInput = null;

	String EMAIL_REGEX = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	
	MessageProperties i18n = GWT.create(MessageProperties.class);
	
	public AutoSuggestForm(MultiWordSuggestOracle oracle) {
		form = new FlowPanel();
		form.setStyleName("form");
		initWidget(form);
		if (txtInput == null){
			 txtInput = new InputListWidget(oracle);
		}
		form.add(txtInput);
		txtInput.getTxtInputBox().setFocus(true);
	}

	/** 
	 * This method is to get the txtInput
	 */
	public InputListWidget getTxtInput() {
		return txtInput;
	}

	/** 
	 * This method is to set the txtInput
	 */
	public void setTxtInput(InputListWidget txtInput) {
		this.txtInput = txtInput;
	}

	public abstract void onSubmit(DomEvent<EventHandler> event);

	public abstract void dataObjectModel(String text);
	
	public abstract void errorMsgVisibility(boolean visibility, String emailId);
	
	public String getSelectedItemsAsString(){
		return txtInput.getSelectedItemsAsString().replaceAll("\\[", "").replaceAll("\\]", "");
	}
	public List<String> getSelectedItemsAsList(){
		return txtInput.getSelectedItemsAsList();
	}
	
	public void setAutoSuggest(MultiWordSuggestOracle oracle){
		txtInput.setAutoSuggestObject(oracle);
	}
	
	public abstract void keyPressOnTextBox(KeyPressEvent event);
	/**
	 * 
	 * @function clearAutoSuggestData 
	 * 
	 * @created_date : Jan 30, 2014
	 * 
	 * @description
	 * 		This is to clear the oracle.... (list of emailids)
	 * 
	 * 
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 *
	 */
	public void clearAutoSuggestData(){
		txtInput.clearAutoSuggestData();
	}
	
	public void clearTextBoxContainer(){
		txtInput.clearListItems();
	}
		
	/**
	 * @function getTextBoxValue 
	 * 
	 * @created_date : Jan 29, 2014
	 * 
	 * @description
	 * 
	 * 
	 * @return
	 * 
	 * @return : String
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 * 
	 *
	 * 
	*/
	
	public String getTextBoxValue() {
		return txtInput.getValue();
	}

	public class InputListWidget extends Composite {
		List<String> itemsSelected = new ArrayList<String>();
		TextBox txtInputBox = null;
		/** 
		 * This method is to get the txtInputBox
		 */
		public TextBox getTxtInputBox() {
			return txtInputBox;
		}

		/** 
		 * This method is to set the txtInputBox
		 */
		public void setTxtInputBox(TextBox txtInputBox) {
			this.txtInputBox = txtInputBox;
		}
		MultiWordSuggestOracle oracle = null;
		BulletList list=null;

		public InputListWidget(MultiWordSuggestOracle oracle) {
			FlowPanel panel = new FlowPanel();
			initWidget(panel);
			
			this.oracle = oracle;

			list = new BulletList();
			list.setStyleName("token-input-list-gooru");
			final ListItem item = new ListItem();
			item.setStyleName("token-input-input-token-gooru");
			txtInputBox = new TextBox();
			txtInputBox.getElement().setAttribute("placeholder", i18n.GL1111());
			txtInputBox.getElement()
					.setAttribute(
							"style",
							"outline-color: -moz-use-text-color; outline-style: none; outline-width: medium;");
			final SuggestBox suggestBox = new SuggestBox(oracle,
					txtInputBox);
			suggestBox.getElement().setId("suggestion_box");
			item.add(suggestBox);
			list.add(item);

			txtInputBox.addKeyUpHandler(new KeyUpHandler() {
				public void onKeyUp(KeyUpEvent event) {
					errorMsgVisibility(false, null);
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
						// only allow manual entries with @ signs (assumed email
						// addresses)
						if (txtInputBox.getValue() !=null && !txtInputBox.getValue().trim().equalsIgnoreCase("")){
//							if (txtInputBox.getValue().contains("@")){
							boolean isValidEmailId = txtInputBox.getValue().trim().matches(EMAIL_REGEX);
							if (isValidEmailId){
								deselectItem(txtInputBox, list);
							}else{
								// Show error message
								txtInputBox.setValue(txtInputBox.getValue().trim());
								errorMsgVisibility(true, txtInputBox.getValue());
							}
						}
					}
					
					if (txtInputBox.getValue().contains(",")){
						
						if (txtInputBox.getValue() !=null && !txtInputBox.getValue().equalsIgnoreCase("")){
							String emailIds[] = txtInputBox.getValue().trim().split(",");
							boolean isValid=false;
							for (int k=0; k<emailIds.length;k++){
								boolean isValidEmailId = emailIds[k].trim().matches(EMAIL_REGEX);
								if (isValidEmailId){
									isValid=true;
								}else{
									isValid=false;
									errorMsgVisibility(true, emailIds[k].trim());
									break;
								}
							}
							if (isValid){
								if (txtInputBox.getValue().contains("@")){
									deselectItem(txtInputBox, list);
								}else{
									//TODO
									errorMsgVisibility(true, txtInputBox.getValue());
								}
							}
						}
					}
					if (txtInputBox.getValue().contains(" ") ){
						String emailIds[] = txtInputBox.getValue().trim().split(" ");
						boolean isValid=false;
						for (int k=0; k<emailIds.length;k++){
							boolean isValidEmailId = emailIds[k].trim().matches(EMAIL_REGEX);
							if (isValidEmailId){
								isValid=true;
							}else{
								isValid=false;
								errorMsgVisibility(true, emailIds[k].trim());
								break;
							}
						}
						
//						boolean isValidEmailId = txtInputBox.getValue().trim().matches(EMAIL_REGEX);
						
						if (isValid){
							deselectItem(txtInputBox, list);
						}else{
							// Show error message
							txtInputBox.setValue(txtInputBox.getValue().trim());
							errorMsgVisibility(true, txtInputBox.getValue());
						}
					}
					dataObjectModel(txtInputBox.getText().trim());
				}
			});
			
			txtInputBox.addFocusHandler(new FocusHandler() {
				
				@Override
				public void onFocus(FocusEvent event) {
					
				}
			});
			
			txtInputBox.addBlurHandler(new BlurHandler() {
				
				@Override
				public void onBlur(BlurEvent event) {
					errorMsgVisibility(false, null);
					if (txtInputBox.getValue() !=null && !txtInputBox.getValue().equalsIgnoreCase("")){
						String emailIds[] = txtInputBox.getValue().trim().split(" ");
						boolean isValid=false;
						for (int k=0; k<emailIds.length;k++){
							boolean isValidEmailId = emailIds[k].trim().matches(EMAIL_REGEX);
							if (isValidEmailId){
								isValid=true;
							}else{
								isValid=false;
								errorMsgVisibility(true, emailIds[k].trim());
								break;
							}
						}
						
//						boolean isValidEmailId = txtInputBox.getValue().trim().matches(EMAIL_REGEX);
						
						if (isValid){
							deselectItem(txtInputBox, list);
						}else{
							// Show error message
							txtInputBox.setValue(txtInputBox.getValue().trim());
							errorMsgVisibility(true, txtInputBox.getValue());
						}
					}
				}
			});
			
			txtInputBox.addKeyPressHandler(new KeyPressHandler() {
				
				@Override
				public void onKeyPress(KeyPressEvent event) {
					keyPressOnTextBox(event);
					errorMsgVisibility(false, null);
//					if (txtInputBox.getValue().contains(" "))
//						deselectItem(txtInputBox, list);
				}
			});
			
			txtInputBox.addKeyDownHandler(new KeyDownHandler() {
				
				@Override
				public void onKeyDown(KeyDownEvent event) {
					if (event.getNativeKeyCode() == KeyCodes.KEY_BACKSPACE && txtInputBox.getValue().trim().length() <= 0) {
						keyPressOnTextBox(null);
						if ("".equals(txtInputBox.getValue().trim())) {
							if(list.getWidgetCount() >1){
								ListItem li = (ListItem) list.getWidget(list.getWidgetCount() - 2);
								Paragraph p = (Paragraph) li.getWidget(0);
								if (itemsSelected.contains(p.getText())) {
									itemsSelected.remove(p.getText());
								}
								list.remove(li);
								txtInputBox.setFocus(true);
							}
						}
					}
				}
			});

			suggestBox
					.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
						public void onSelection(SelectionEvent selectionEvent) {
							if (txtInputBox.getText().contains("@") || txtInputBox.getText().trim().equalsIgnoreCase("")){
								keyPressOnTextBox(null);
							}
							deselectItem(txtInputBox, list);
						}
					});

			panel.add(list);

			panel.getElement().setAttribute("onclick",
					"document.getElementById('suggestion_box').focus()");
			suggestBox.setFocus(true);
		}

		/**
		 * @function getValue 
		 * 
		 * @created_date : Jan 29, 2014
		 * 
		 * @description
		 * 
		 * 
		 * @return
		 * 
		 * @return : String
		 *
		 * @throws : <Mentioned if any exceptions>
		 *
		 * 
		 *
		 * 
		*/
		
		public String getValue() {
			return txtInputBox.getText();
		}

		public String getSelectedItemsAsString(){
			return itemsSelected.toString();
		}
		
		public List<String> getSelectedItemsAsList(){
			return itemsSelected;
		}
		
		public void setAutoSuggestObject(MultiWordSuggestOracle oracle){
			this.oracle = oracle;
		}
		public void clearAutoSuggestData(){
			oracle.clear();
		}
		
		public void deselectItem(final TextBox itemBox, final BulletList list) {
			if (itemBox.getValue() != null
					&& !"".equals(itemBox.getValue().trim())) {

				if (txtInputBox.getValue().contains(",")){
					String emailIds[] = txtInputBox.getValue().trim().split(",");
					for (int i=0; i< emailIds.length; i++){
						addText(itemBox, list, emailIds[i]);
					}
				}
				else if (txtInputBox.getValue().contains(";")){
					String emailIds[] = txtInputBox.getValue().trim().split(";");
					for (int i=0; i< emailIds.length; i++){
						addText(itemBox, list, emailIds[i]);
					}
				}else if (txtInputBox.getValue().contains(" ")){
					String emailIds[] = txtInputBox.getValue().trim().split(" ");
					for (int i=0; i< emailIds.length; i++){
						addText(itemBox, list, emailIds[i]);
					}
				}
				else{
					addText(itemBox, list, txtInputBox.getText());
				}
				itemBox.setValue("");
				itemBox.setFocus(true);
			}
		}
		public void addText(final TextBox itemBox, final BulletList list, String text){
			final ListItem displayItem = new ListItem();
			displayItem.setStyleName("token-input-token-gooru");
			Paragraph p = new Paragraph(text.trim());

			displayItem.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent clickEvent) {
					displayItem
							.addStyleName("token-input-selected-token-gooru");
				}
			});

			Span span = new Span("x");
			span.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent clickEvent) {
					keyPressOnTextBox(null);
					removeListItem(displayItem, list);
				}
			});

			displayItem.add(p);
			displayItem.add(span);
			// hold the original value of the item selected

			itemsSelected.add(text.trim());

			list.insert(displayItem, list.getWidgetCount() - 1);
		}
		public void removeListItem(ListItem displayItem, BulletList list) {
			GWT.log("Removing: "
					+ displayItem.getWidget(0).getElement().getInnerHTML(),
					null);
			itemsSelected.remove(displayItem.getWidget(0).getElement()
					.getInnerHTML());
			list.remove(displayItem);			
		}
		public void clearListItems(){
//			itemsSelected.clear();
			list.clear();
		}
	}
}