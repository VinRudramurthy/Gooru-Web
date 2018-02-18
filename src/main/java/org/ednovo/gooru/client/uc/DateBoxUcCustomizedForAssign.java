package org.ednovo.gooru.client.uc;

import java.util.Date;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;

/**
 * @author Search Team
 *
 */
public class DateBoxUcCustomizedForAssign extends FlowPanel {

	private TextBox dateBox;

	private Label calendarIcon;

	private DatePickerUc datePickerUc;
//	private DatePicker datePicker;

	private static MessageProperties i18n = GWT.create(MessageProperties.class);

	boolean isRegistration = true;

	private static final String AFTER_CURRENT_DATE = i18n.GL1504();

	private static final String BEFORE_CURRENT_DATE = i18n.GL1505();

	/**
	 * Class constructor
	 */
	public DateBoxUcCustomizedForAssign(boolean isRegistration,boolean isSmall,boolean isStudent) {
		super();
		this.isRegistration = isRegistration;

		calendarIcon = new Label();
		calendarIcon.setStyleName("Uc-gooruCalendarIcon");

		dateBox = new TextBox();
		dateBox.getElement().setId("tbBirthday");
		dateBox.setReadOnly(true);
		if (!isRegistration){
				this.setStyleName("Uc-gooruDateBoxAssignment");
/*				dateBox.getElement().getStyle().setWidth(271, Unit.PX);
*/		}else{
			dateBox.getElement().setAttribute("Placeholder", i18n.GL0211());
			if (!isSmall){
				this.setStyleName("Uc-gooruDateBox");
			}else{
				calendarIcon.addStyleName("Uc-iconPosition");
			}
		}
		if (AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.ASSESSMENT_PLAY.toString()) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.COLLECTION_PLAY.toString()) ||AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.PREVIEW_PLAY.toString()) || AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken().equals(PlaceTokens.RESOURCE_PLAY.toString())){
        	this.getElement().getStyle().setZIndex(9999999);
        }else{
        	this.getElement().getStyle().clearZIndex();
        }

		datePickerUc = new DatePickerUc(isRegistration);
		dateBox.setStyleName("Uc-gooruDateText");
		if(!isStudent){
		calendarIcon.addClickHandler(new OnIconClick());
		}
		this.add(dateBox);
		this.add(calendarIcon);
		datePickerUc.getDoneButton().addClickHandler(new OnDoneClick());
		datePickerUc.getTodayButton().addClickHandler(new OnTodayClick());
		datePickerUc.getDatePicker().addValueChangeHandler(new OnDateChange());
		datePickerUc.hide();
		datePickerUc.listYear.addChangeHandler(new OnYearChange());
		datePickerUc.listMonths.addChangeHandler(new OnMonthChange());

	}

	private class OnIconClick implements ClickHandler {
		@Override

		public void onClick(ClickEvent event) {
			//datePickerUc.reset();
			int left = calendarIcon.getAbsoluteLeft() - 153;
			int top = dateBox.getAbsoluteTop() - 212;
			showDatePickerPopup(left, top);
			boolean isRegistration=false;
			if(!datePickerUc.isRegistration){
				datePickerUc.reset();
			}

		}

	}

	private class OnTodayClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			datePickerUc.setYear();
			datePickerUc.setMonth(true);
			datePickerUc.getDatePicker().setCurrentMonth(new Date());
			Date selectedDate = new Date();
			String dateString = DateTimeFormat.getFormat("MM/dd/yyyy").format(selectedDate);
			dateBox.setText(dateString);
		}
	}

	private class OnMonthChange implements ChangeHandler {
		@Override
		public void onChange(ChangeEvent event) {
			String selectedMonth = (Integer.parseInt(datePickerUc.listMonths.getValue(datePickerUc.listMonths.getSelectedIndex())) + 1) + "";

			dateBox.setText(dateBox.getText() != null && !dateBox.getText().isEmpty() ?  (selectedMonth.length() == 1 ? "0" + selectedMonth  : selectedMonth)  + dateBox.getText().substring(2) : "");
		}
	}

	private class OnYearChange implements ChangeHandler {
		@Override
		public void onChange(ChangeEvent event) {
			GWT.log("On Year Change");
			dateBox.setText(dateBox.getText() != null && !dateBox.getText().isEmpty() ? dateBox.getText().substring(0, 6) + datePickerUc.listYear.getItemText(datePickerUc.listYear.getSelectedIndex()) : "");
		}
	}

	private class OnDoneClick implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			datePickerUc.hide();
		}
	}

	public boolean dateValidation(){
		boolean isValid = isRegistration ? hasValidateDate() : hasValidateForDate();
		if (isValid) {
			if (!(dateBox.getText() == null || (dateBox.getText() != null && dateBox.getText().isEmpty()))) {
				datePickerUc.hide();
				return true;
			}
			return false;
		} else {
			if (isRegistration){
				new AlertContentUc(i18n.GL0065(), AFTER_CURRENT_DATE);
			}else{
				new AlertContentUc(i18n.GL0065(), BEFORE_CURRENT_DATE);
			}
			return false;
		}
	}

	private class OnDateChange implements ValueChangeHandler<Date> {
		@Override
		public void onValueChange(ValueChangeEvent<Date> event) {

			try {
				Date selectedDate = datePickerUc.getDatePicker().getValue();
//				Date selectedDate = event.getValue();
				if(datePickerUc.isRegistration){
					if(selectedDate.before(new Date())||selectedDate.equals(new Date())){
						datePickerUc.listMonths.clear();
						if(selectedDate.getYear()==new Date().getYear()){
							datePickerUc.setMonth(true);
						}else{
							datePickerUc.setMonth(false);
						}
						datePickerUc.listMonths.setSelectedIndex(selectedDate.getMonth());
						datePickerUc.listYear.setSelectedIndex(selectedDate.getYear()-10);
						String dateString = DateTimeFormat.getFormat("MM/dd/yyyy").format(selectedDate);
						String selectedMonth = (Integer.parseInt(datePickerUc.listMonths.getValue(datePickerUc.listMonths.getSelectedIndex())) + 1) + "";
						dateBox.setText((selectedMonth.length() == 1 ? "0" + selectedMonth  : selectedMonth)  + dateString.substring(2));
						Element errorMessageElement=Document.get().getElementById("datePickerErrorMessageLabel");
						errorMessageElement.getStyle().setDisplay(Display.NONE);
					}
				}else{
					if(selectedDate.after(new Date())||selectedDate.equals(new Date())){
						datePickerUc.listMonths.clear();
						if(selectedDate.getYear()==new Date().getYear()){
							datePickerUc.setMonth(true);
						datePickerUc.listMonths.setSelectedIndex(selectedDate.getMonth()-new Date().getMonth());
						}else{
							datePickerUc.setMonth(false);
							datePickerUc.listMonths.setSelectedIndex(selectedDate.getMonth());
						}
						int currentYear =(new Date().getYear()+1900);
						int yearCount = currentYear-1900;
						datePickerUc.listYear.setSelectedIndex(Integer.parseInt(datePickerUc.listYear.getValue(datePickerUc.listYear.getSelectedIndex()))-yearCount);
						String dateString = DateTimeFormat.getFormat("MM/dd/yyyy").format(selectedDate);

						String selectedMonth = (Integer.parseInt(datePickerUc.listMonths.getValue(datePickerUc.listMonths.getSelectedIndex())) + 1) + "";

						dateBox.setText((selectedMonth.length() == 1 ? "0" + selectedMonth  : selectedMonth)  + dateString.substring(2));
						Element errorMessageElement=Document.get().getElementById("datePickerErrorMessageLabel");
						errorMessageElement.getStyle().setDisplay(Display.NONE);

					}
				}
			} catch (Exception e) {
				AppClientFactory.printSevereLogger("DateBoxUcCustomizedForAssign OnDateChange:::"+e);
			}
		}
	}


	/**
	 * @return selected date
	 */
	public String getDate() {
		Date selectedDate = datePickerUc.getDatePicker().getValue();
		String dateString = DateTimeFormat.getFormat("MM/dd/yyyy").format(selectedDate);

		return dateString;

	}

	/**
	 * @return selected year
	 */
	public int getYear() {
		Date selectedDate = datePickerUc.getDatePicker().getValue();
		int year = selectedDate.getYear();
return year;
	}

	public Date getValue() {
		return datePickerUc.getDatePicker().getValue();
	}

	/**
	 * View datePickerPopup
	 *
	 * @param left
	 *            position of the popup
	 * @param top
	 *            position of the popup
	 */
	public void showDatePickerPopup(int left, int top) {
		datePickerUc.setPopupPosition(left, top);
		datePickerUc.show();

	}

	/**
	 * @return true if date is valid else false
	 */
	public boolean hasValidateDate() {
		boolean isValid = true;
		Date current = new Date();
		Date selected = getDatePickerUc().getDatePicker().getValue();
		if (dateBox.getText() != null && dateBox.getText().length() > 0 && selected.after(current)) {
			 isValid = false;
		}
		return isValid;
	}

	/**
	 * @return true if date is valid else false
	 */
	public boolean hasValidateForDate() {
		boolean isValid = true;
		Date current = new Date();
		Date selected = getDatePickerUc().getDatePicker().getValue();
		if (dateBox.getText() != null && dateBox.getText().length() > 0 && selected.before(current)) {
			 isValid = false;
		}
		return isValid;
	}

	public TextBox getDateBox() {
		return dateBox;
	}

	public Button getDoneButton() {

		return datePickerUc.getDoneButton();
	}

	public DatePickerUc getDatePickerUc() {
		return datePickerUc;
	}
}
