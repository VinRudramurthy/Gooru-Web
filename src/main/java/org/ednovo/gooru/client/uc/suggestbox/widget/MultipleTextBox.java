package org.ednovo.gooru.client.uc.suggestbox.widget;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.ui.TextBoxBase;
/**
 * 
 * @fileName : MultipleTextBox.java
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
public class MultipleTextBox extends TextBoxBase {
    /**
     * Creates an empty multiple text box.
     */
    public MultipleTextBox() {
        this(Document.get().createTextInputElement(), "gwt-TextBox");
    }

    /**
     * @param element the element to be used
     */
    protected MultipleTextBox(Element element) {
        super(element);
        assert InputElement.as(element).getType().equalsIgnoreCase("text");
    }

    MultipleTextBox(Element element, String styleName) {
        super(element);
        if (styleName != null) {
            setStyleName(styleName);
        }
    }

    @Override
    public String getText() {
        String wholeString = super.getText();
        String lastString = wholeString;
        if (wholeString != null && !wholeString.trim().equals("")) {
            int lastComma = wholeString.trim().lastIndexOf(",");
            if (lastComma > 0) {
                lastString = wholeString.trim().substring(lastComma + 1);
            }
        }
        return lastString;
    }

    @Override
    public void setText(String text) {
        String wholeString = super.getText();
        if (text != null && text.equals("")) {
            super.setText(text);
        } else {
            if (wholeString != null) {
                int lastComma = wholeString.trim().lastIndexOf(",");
                if (lastComma > 0) {
                    wholeString = wholeString.trim().substring(0, lastComma);
                } else {
                    wholeString = "";
                }

                if (!wholeString.trim().endsWith(",")
                        && !wholeString.trim().equals("")) {
                    wholeString = wholeString + ", ";
                }

                wholeString = wholeString + text + ", ";
                super.setText(wholeString);
            }
        }
    }
}