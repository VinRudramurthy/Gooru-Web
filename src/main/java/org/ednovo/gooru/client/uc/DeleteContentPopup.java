/*******************************************************************************
 * Copyright 2013 Ednovo d/b/a Gooru. All rights reserved.
 * 
 *  http://www.goorulearning.org/
 * 
 *  Permission is hereby granted, free of charge, to any person obtaining
 *  a copy of this software and associated documentation files (the
 *  "Software"), to deal in the Software without restriction, including
 *  without limitation the rights to use, copy, modify, merge, publish,
 *  distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to
 *  the following conditions:
 * 
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 * 
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 *  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 *  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 *  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/

package org.ednovo.gooru.client.uc;

import java.util.ArrayList;
import java.util.List;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Cursor;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public abstract class DeleteContentPopup extends PopupPanel {
	
	
	@UiField TextBox txtConfirmAction;
	
	@UiField Button btnNegitive, btnPositive;
	
	@UiField Label lblTitle,lblRemoving;
	
	@UiField H3Panel h3Panel;
	
	@UiField H5Panel h5Panel;
	
	@UiField Image closeBtn;
	
	@UiField SimpleCheckBox chkBox1, chkBox2, chkBox3;
	
	@UiField PPanel paragraphPnl;
	
	@UiField LabelPanel checkboxG12,checkboxG13,checkboxG14;
	
	
	
	@UiTemplate("DeleteContentPopup.ui.xml")
	interface Binder extends UiBinder<Widget, DeleteContentPopup> {

	}
	private static final Binder binder = GWT.create(Binder.class);
	private MessageProperties i18n = GWT.create(MessageProperties.class);
	
	private boolean isValidate=false;
	
	private String deleteCode=null;
	
	List<SimpleCheckBox> chkBoxList = new ArrayList<SimpleCheckBox>();
	
	public DeleteContentPopup(){
		super(false);
		add(binder.createAndBindUi(this));
		this.setGlassEnabled(true);
				
		h3Panel.setVisible(false);
		lblRemoving.setVisible(false);
		lblRemoving.getElement().getStyle().setMargin(26, Unit.PX);
		txtConfirmAction.setVisible(false);
		setButtonVisibility(true);
		setElementId();
		
		txtConfirmAction.addKeyUpHandler(new ValidateConfirmText());
		txtConfirmAction.getElement().getStyle().setColor("#515151");
		txtConfirmAction.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				if(!txtConfirmAction.getText().isEmpty()){
					if(txtConfirmAction.getText().toLowerCase().equalsIgnoreCase(i18n.GL1175().toLowerCase())){
						txtConfirmAction.setText("");
						txtConfirmAction.getElement().getStyle().setColor("#000000");
					}
				}
				
			}
		});
		
		txtConfirmAction.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				if(txtConfirmAction.getText().isEmpty()){
					txtConfirmAction.getElement().getStyle().setColor("#515151");
				}
				
			}
		});
		StringUtil.setAttributes(txtConfirmAction, true);
		btnNegitive.setText(StringUtil.generateMessage(i18n.GL0142()));
		btnNegitive.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0142()));
		btnNegitive.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0142()));
		
		btnPositive.setText(StringUtil.generateMessage(i18n.GL0190()));
		btnPositive.getElement().setAttribute("alt",StringUtil.generateMessage(i18n.GL0190()));
		btnPositive.getElement().setAttribute("title",StringUtil.generateMessage(i18n.GL0190()));
		
		/*lblDeleteText.setText(i18n.GL2189());
		StringUtil.setAttributes(lblDeleteText.getElement(), "lblDeleteText", null, "lblDeleteText");*/
		
		Window.enableScrolling(false);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, false));
		this.center();

	}
	
	public void setPositiveButtonText(String text) {
		btnPositive.setText(text);
		btnPositive.getElement().setAttribute("alt",text);
		btnPositive.getElement().setAttribute("title",text);
	}
	
	public void setNegitiveButtonText(String text) {
		btnNegitive.setText(text);
		btnNegitive.getElement().setAttribute("alt",text);
		btnNegitive.getElement().setAttribute("title",text);
	}
	
	public void setPleaseWaitText(String text){
		lblRemoving.setText(text);
		lblRemoving.getElement().setAttribute("alt",text);
		lblRemoving.getElement().setAttribute("title",text);
	}
	
	public void setPopupTitle(String title) {
		lblTitle.setText(title);
		lblTitle.getElement().setAttribute("alt",title);
		lblTitle.getElement().setAttribute("title",title);
	}
	
	
	private void setButtonVisibility(boolean visibility) {
		btnPositive.setVisible(visibility);
		btnNegitive.setVisible(visibility);
	}
	
	
	private void setElementId() {
		btnPositive.getElement().setId("btnPositive");
		btnNegitive.getElement().setId("btnNegitive");
		lblTitle.getElement().setId("lblTitle");
		txtConfirmAction.getElement().setId("txtConfirmAction");
		lblRemoving.getElement().setId("lblRemoving");
		closeBtn.getElement().getStyle().setCursor(Cursor.POINTER);
		
		h5Panel.setText("Warning: this cannot be undone");
		paragraphPnl.setText("Please type in delete");
		chkBoxList.add(chkBox1);
		chkBoxList.add(chkBox2);
		chkBoxList.add(chkBox3);
		
		chkBox1.getElement().setId("checkboxG12");
		chkBox2.getElement().setId("checkboxG13");
		chkBox3.getElement().setId("checkboxG14");
		
		checkboxG12.getElement().setAttribute("for", "checkboxG12");
		checkboxG13.getElement().setAttribute("for", "checkboxG13");
		checkboxG14.getElement().setAttribute("for", "checkboxG14");
		
		chkBox1.setValue(false);
		chkBox2.setValue(false);
		chkBox3.setValue(false);
		if(AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.EDIT_CLASS)){
			checkboxG12.setText(i18n.GL3575());
			checkboxG13.setText(i18n.GL3576());
			checkboxG14.setText(i18n.GL3577());
			checkboxG13.getElement().getStyle().setLineHeight(18, Unit.PX);
		}else{
			checkboxG12.setText(i18n.GL3579());
			checkboxG13.setText(i18n.GL3573());
			checkboxG14.setText(i18n.GL3574());
		}
		
	}
	
	public void setDeleteValidate(String deleteCode){
		isValidate = true;
		this.deleteCode = deleteCode;
		
		txtConfirmAction.setVisible(true);
		
		btnPositive.setEnabled(false);
		btnPositive.getElement().addClassName("disabled");
	}
	
	
	private class ValidateConfirmText implements KeyUpHandler {

		@Override
		public void onKeyUp(KeyUpEvent event) {
			if (isValidate){
				if (txtConfirmAction.getText().trim().equalsIgnoreCase(deleteCode) && isUserCheckedAllOptions()) {
					btnPositive.getElement().removeClassName("disabled");
					btnPositive.setEnabled(true);
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
						if (lblRemoving.getText()!=null && !lblRemoving.getText().equalsIgnoreCase("")){
							setButtonVisibility(false);
							showIsRemoving();
						}
						onClickPositiveButton(null);
					}
				}else{
					setPositiveBtnStatus(false);
				}
			}
		}
	}
	
	@UiHandler("chkBox1")
	public void onChkBox1Clicked(ClickEvent event){
		if(isUserCheckedAllOptions()&& txtConfirmAction.getText().trim().equalsIgnoreCase(deleteCode)){
			setPositiveBtnStatus(true);
		}else{
			setPositiveBtnStatus(false);
		}
	}
	
	@UiHandler("chkBox2")
	public void onChkBox2Clicked(ClickEvent event){
		if(isUserCheckedAllOptions()&& txtConfirmAction.getText().trim().equalsIgnoreCase(deleteCode)){
			setPositiveBtnStatus(true);
		}else{
			setPositiveBtnStatus(false);
		}
	}
	
	@UiHandler("chkBox3")
	public void onChkBox3Clicked(ClickEvent event){
		if(isUserCheckedAllOptions()&& txtConfirmAction.getText().trim().equalsIgnoreCase(deleteCode)){
			setPositiveBtnStatus(true);
		}else{
			setPositiveBtnStatus(false);
		}
	}
	
	public void setPositiveBtnStatus(boolean status) {
		if(status){
			btnPositive.getElement().removeClassName("disabled");
			btnPositive.setEnabled(status);
		}else{
			btnPositive.getElement().addClassName("disabled");
			btnPositive.setEnabled(status);
		}
		
		
	}

	@UiHandler("btnPositive")
	public void onPostitiveClickEvent(ClickEvent event){
		if (lblRemoving.getText()!=null && !lblRemoving.getText().equalsIgnoreCase("")){
			showIsRemoving();
			setButtonVisibility(false);
		}
		onClickPositiveButton(null);
	}
	
	public boolean isUserCheckedAllOptions() {
		boolean flag = false;
		for(int i=0;i<3;i++){
			if(chkBoxList.get(i).getValue()){
				flag = true;
			}else{
				flag=false;
				break;
			}
		}
		return flag;
	}

	@UiHandler("btnNegitive")
	public void onNegitiveClickEvent(ClickEvent event){
		onClickNegitiveButton(event);
	}
	
	/**
	 * 
	 * @param event
	 */
	@UiHandler("closeBtn")
	public void onColseButtonClicked(ClickEvent event){
		hide();
		Window.enableScrolling(true);
	}
	
	
	
	public void showIsRemoving() {
		lblRemoving.setVisible(true);
	}
	
	public void setNotes(String notes){
		h3Panel.setVisible(true);
		h3Panel.setText(notes);
	}
	
	/* Abstract methods to handle button events*/
	public abstract void onClickPositiveButton(ClickEvent event);

	public abstract void onClickNegitiveButton(ClickEvent event);

}
