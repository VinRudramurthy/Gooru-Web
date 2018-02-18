
package org.ednovo.gooru.client.util;

import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.client.mvp.shelf.collection.CollectionCBundle;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.ui.TinyMCE;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.BorderStyle;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

/**
 * @fileName : SetStyleForProfanity.java
 *
 * @description : 
 *
 *
 * @version : 1.0
 *
 * @date: 21-Jan-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class SetStyleForProfanity {
	
    static MessageProperties i18n = GWT.create(MessageProperties.class); 
  
    /**
     * This method will set the profanity error message for TextBoxWithPlaceholder control
     * @param textbox
     * @param errorlabel
     * @param value
     */
    public static void SetStyleForProfanityForTextBoxWithPlaceholder(TextBoxWithPlaceholder textbox,Label errorlabel,boolean value) {
		CollectionCBundle.INSTANCE.css().ensureInjected();
		if (value){
			textbox.getElement().getStyle().setBorderColor("orange");
			textbox.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			textbox.getElement().getStyle().setBorderWidth(1, Unit.PX);
			
			errorlabel.setText(i18n.GL0554());
			errorlabel.setVisible(true);
			errorlabel.setStyleName(CollectionCBundle.INSTANCE.css().mandatoryLabelError());
		}else{
			textbox.getElement().getStyle().clearBorderColor();
			textbox.getElement().getStyle().clearBorderStyle();
			textbox.getElement().getStyle().clearBorderWidth();
			errorlabel.setVisible(false);
		}
	}
    /**
     * This method will set the profanity error message for TextBox control
     * @param textbox
     * @param errorlabel
     * @param value
     */
	public static void SetStyleForProfanityForTextBox(TextBox textbox,Label errorlabel,boolean value) {
		CollectionCBundle.INSTANCE.css().ensureInjected();
		if (value){
			textbox.getElement().getStyle().setBorderColor("orange");
			textbox.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			textbox.getElement().getStyle().setBorderWidth(1, Unit.PX);
			
			errorlabel.setText(i18n.GL0554());
			errorlabel.setVisible(true);
			errorlabel.setStyleName(CollectionCBundle.INSTANCE.css().mandatoryLabelError());
		}else{
			textbox.getElement().getStyle().clearBorderColor();
			textbox.getElement().getStyle().clearBorderStyle();
			textbox.getElement().getStyle().clearBorderWidth();
			errorlabel.setVisible(false);
		}
	}
	/**
     * This method will set the profanity error message for TextArea control
     * @param textbox
     * @param errorlabel
     * @param value
     */
	public static void SetStyleForProfanityForTextArea(TextArea textArea,Label errorlabel,boolean value) {
		CollectionCBundle.INSTANCE.css().ensureInjected();
		if (value){
			textArea.getElement().getStyle().setBorderColor("orange");
			textArea.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			textArea.getElement().getStyle().setBorderWidth(1, Unit.PX);
			errorlabel.setText(i18n.GL0554());
			errorlabel.setVisible(true);
			errorlabel.setStyleName(CollectionCBundle.INSTANCE.css().mandatoryLabelError());
		}else{
			textArea.getElement().getStyle().clearBorderColor();
			textArea.getElement().getStyle().clearBorderStyle();
			textArea.getElement().getStyle().clearBorderWidth();
			textArea.getElement().getStyle().setBorderColor("#ccc");
			errorlabel.setVisible(false);
		}
	}
	/**
     * This method will set the profanity error message for RichTextArea control
     * @param textbox
     * @param errorlabel
     * @param value
     */
	public static void SetStyleForProfanityForRichTextArea(RichTextArea richTextArea,Label errorlabel,boolean value) {
		CollectionCBundle.INSTANCE.css().ensureInjected();
		if (value){
			richTextArea.getElement().getStyle().setBorderColor("orange");
			richTextArea.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			richTextArea.getElement().getStyle().setBorderWidth(1, Unit.PX);
			errorlabel.setText(i18n.GL0554());
			errorlabel.setVisible(true);
			errorlabel.setStyleName(CollectionCBundle.INSTANCE.css().mandatoryLabelError());
		}else{
			richTextArea.getElement().getStyle().clearBorderColor();
			richTextArea.getElement().getStyle().clearBorderStyle();
			richTextArea.getElement().getStyle().clearBorderWidth();
			richTextArea.getElement().getStyle().setBorderColor("#ccc");
			errorlabel.setVisible(false);
		}
	}
	/**
     * This method will set the profanity error message for TextBox control
     * @param textbox
     * @param errorlabel
     * @param value
     */
	public static void SetStyleForProfanityForSearchRenameCollTextBox(TextBox copycollectionTextbox, Label errorLabel,boolean isHavingBadWords) {
		CollectionCBundle.INSTANCE.css().ensureInjected();
		if (isHavingBadWords){
			copycollectionTextbox.getElement().getStyle().setBorderColor("orange");
			copycollectionTextbox.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			copycollectionTextbox.getElement().getStyle().setBorderWidth(1, Unit.PX);
			errorLabel.setText(i18n.GL0554());
			errorLabel.setVisible(true);
			errorLabel.setStyleName(CollectionCBundle.INSTANCE.css().searchRenameCollProfanityErrLbl());
		}else{
			copycollectionTextbox.getElement().getStyle().clearBorderColor();
			copycollectionTextbox.getElement().getStyle().clearBorderStyle();
			copycollectionTextbox.getElement().getStyle().clearBorderWidth();
			copycollectionTextbox.getElement().getStyle().setBorderColor("#ccc");
			errorLabel.setVisible(false);
		}
	}
	/**
     * This method will set the profanity error message for TinyMCE control
     * @param textbox
     * @param errorlabel
     * @param value
     */
	public static void SetStyleForProfanityForTinyMCE(TinyMCE tinyMCE,Label errorlabel,boolean value) {
		CollectionCBundle.INSTANCE.css().ensureInjected();
		if (value){
			tinyMCE.getElement().getStyle().setBorderColor("orange");
			tinyMCE.getElement().getStyle().setBorderStyle(BorderStyle.SOLID);
			tinyMCE.getElement().getStyle().setBorderWidth(1, Unit.PX);
			errorlabel.setText(i18n.GL0554());
			errorlabel.setVisible(true);
			errorlabel.setStyleName(CollectionCBundle.INSTANCE.css().mandatoryLabelError());
		}else{
			tinyMCE.getElement().getStyle().clearBorderColor();
			tinyMCE.getElement().getStyle().clearBorderStyle();
			tinyMCE.getElement().getStyle().clearBorderWidth();
			tinyMCE.getElement().getStyle().setBorderColor("#ccc");
			errorlabel.setVisible(false);
		}
	}
}
