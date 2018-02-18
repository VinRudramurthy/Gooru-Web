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
package org.ednovo.gooru.client.mvp.home;

import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.search.AutoSuggestKeywordSearchDo;
import org.ednovo.gooru.application.shared.model.search.SearchDo;
import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.client.GooruCBundle;
import org.ednovo.gooru.client.SearchAsyncCallback;
import org.ednovo.gooru.client.SeoTokens;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpages.event.ClearClasspageListEvent;
import org.ednovo.gooru.client.mvp.classpages.event.ClearClasspageListHandler;
import org.ednovo.gooru.client.mvp.classpages.event.DeleteClasspageListEvent;
import org.ednovo.gooru.client.mvp.classpages.event.DeleteClasspageListHandler;
import org.ednovo.gooru.client.mvp.gsearch.IsGooruSearchView;
import org.ednovo.gooru.client.mvp.gshelf.LoadMyContentEvent;
import org.ednovo.gooru.client.mvp.home.event.HeaderTabType;
import org.ednovo.gooru.client.mvp.home.event.HomeEvent;
import org.ednovo.gooru.client.mvp.search.event.ConfirmStatusPopupEvent;
import org.ednovo.gooru.client.mvp.search.event.ConfirmStatusPopupHandler;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderHandler;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexHandler;
import org.ednovo.gooru.client.uc.AppMultiWordSuggestOracle;
import org.ednovo.gooru.client.uc.AppSuggestBox;
import org.ednovo.gooru.client.uc.BPanel;
import org.ednovo.gooru.client.uc.BrowserAgent;
import org.ednovo.gooru.client.uc.HeaderPanel;
import org.ednovo.gooru.client.uc.UlPanel;
import org.ednovo.gooru.client.uc.tooltip.DiscoverToolTipUc;
import org.ednovo.gooru.client.uc.tooltip.StudyNowToolTip;
import org.ednovo.gooru.client.uc.tooltip.StudyToolTip;
import org.ednovo.gooru.client.ui.HTMLEventPanel;
import org.ednovo.gooru.client.util.MixpanelUtil;
import org.ednovo.gooru.client.util.PlayerDataLogEvents;
import org.ednovo.gooru.shared.util.DataLogEvents;
import org.ednovo.gooru.shared.util.GwtUUIDGenerator;
import org.ednovo.gooru.shared.util.StringUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.AnchorElement;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.ScrollEvent;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.proxy.PlaceRequest;

/**
 * @author Search Team
 *
 */
public class HeaderUc extends Composite
	 {

	private static HeaderUcUiBinder uiBinder = GWT
			.create(HeaderUcUiBinder.class);

	interface HeaderUcUiBinder extends UiBinder<Widget, HeaderUc> {
	}

	private MessageProperties i18n = GWT.create(MessageProperties.class);

	ClearClasspageListHandler clearHandler = new ClearClasspageListHandler() {

		@Override
		public void clearClasspage() {
			clearClasspageList();
		}
	};

	SetHeaderHandler setHeader = new SetHeaderHandler() {

		@Override
		public void setHeaderEvent(final UserDo userDo) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {
					AppClientFactory
					.setBrowserWindowTitle(SeoTokens.HOME_TITLE_LOGGEDIN);
					AppClientFactory
							.setMetaDataDescription(SeoTokens.HOME_META_DESCRIPTION);
					setLoggedInUser(userDo);
				}
			});

		}
	};

	SetHeaderZIndexHandler setZindex = new SetHeaderZIndexHandler() {

		@Override
		public void setHeaderZIndex(final int value, final boolean isClearZIndex) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {

					Document doc = Document.get();
					if (isClearZIndex) {
						try {
							if(doc.getElementById("headerMainPanel") != null){
								doc.getElementById("headerMainPanel").getStyle().clearZIndex();
							}
						} catch (Exception ex) {
							AppClientFactory.printSevereLogger("HeaderUc setHeaderZIndex:::"+ex.getMessage());
						}
					} else {
						try {
							if(doc.getElementById("headerMainPanel") != null){
								doc.getElementById("headerMainPanel").getStyle().setZIndex(value);
							}
						} catch (Exception e) {
							AppClientFactory.printSevereLogger("HeaderUc setHeaderZIndex:::"+e);
						}
					}
				}
			});
		}

	};

	ConfirmStatusPopupHandler confirmUser = new ConfirmStatusPopupHandler() {

		@Override
		public void setVisibility(boolean value) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {

					if (AppClientFactory.getLoggedInUser() != null && AppClientFactory.getLoggedInUser().getConfirmStatus() == 0) {
						acctActivationPl.setVisible(true);
					} else {
						acctActivationPl.setVisible(false);
					}

					if (AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(
							PlaceTokens.HOME)) {

					} else {
						acctActivationPl.getElement().getStyle().clearMarginTop();
					}

				}
			});
		}
	};

	private static final int TOOLTIP_DELAY_TIME = 1000;

	private String name;

	@UiField(provided = true)
	public static AppSuggestBox editSearchTxtBox;

	@UiField
	FlowPanel editSearchInputFloPanel, signUpInfo;

	@UiField
	FlowPanel logInfoFloPanel;

	@UiField
	Button editSearchBtn, registerLinkLbl;

	@UiField
	Anchor resendEmailAncr;

	@UiField
	Label logoutDownArrowLbl, loginLink, confirmEmailText,gooruLabel;

	@UiField
	HTMLEventPanel acctActivationPl;

	@UiField
	Image imgUserProfile;

	@UiField
	Button toggleButton;

	DiscoverToolTipUc discoverToolTip;

	static String stadardCode="";

	boolean isGooruGuidePanelOpen = false;

	private boolean isOpenSettingDropDown = true;

	private boolean isSettingIcon = false;

	private boolean isClicked = false;

	boolean isEnter = false;

	private boolean isClassCodePopupOpen = true;

	private boolean isStudyNow = false;

	private boolean hasAutoSelected = false;

	boolean hasClasses = false;

	@UiField
	HTMLPanel settingOptionsPopup;

	@UiField HTMLPanel discovertooltippop;

	@UiField static HTMLPanel myClassesPop;

	String classpageId = "";

	private LogoutPanelVc logoutPanelVc;

	@UiField
	AnchorElement gooruLearning;

	@UiField
	HTMLPanel headerSearchBarVerPanel;

	@UiField
	FlowPanel headerSearchBarFloPanel;

	@UiField

	public static HTMLPanel mainDotsPanel, mainInnerDotsPanel;

	@UiField
	BPanel dropDownImg;
	@UiField
	UlPanel dotsPanel;
	@UiField
	public static HTMLPanel dropDownImgforDashboard, panelArrow;

	@UiField
	Anchor discoverLink, organizeLink, teachLink, studyLink, loggedInfoLbl;

	@UiField
	Label thanksLbl;

	@UiField
	HTMLEventPanel discoverLinkContainer, organizeLinkContainer,organizeLinkMain,teachLinkMain,discoverLinkMain,
			teachLinkContainer, studyLinkContainer, LoginLinkContainer;

	Anchor noneMenu = null;

	@UiField
	HeaderPanel headerMainPanel;

	private UserDo userDo;

	private StudyNowToolTip studyNowToolTip;
	private static boolean addedAccounts = false;

	private PopupPanel toolTipPopupPanel = new PopupPanel();
	private SearchDo<AutoSuggestKeywordSearchDo> autoSuggestKeywordDo = new SearchDo<AutoSuggestKeywordSearchDo>();
	private SearchAsyncCallback<SearchDo<AutoSuggestKeywordSearchDo>> autoKeyWordSuggestionAsyncCallback;

	private static final String GOORU_UID = "gooruUid";

	private static final String ACCOUNT_TYPE = "accountType";
	private AppMultiWordSuggestOracle autokeySuggestOracle;
	String searchData = "";

	private String discoverLinkUrl = null;

	private String stadardsListCode= "";


	private static String DEFAULT_PROFILE_IMAGE = "images/settings/setting-user-image.png";

	/**
	 * Class constructor , set logged in user , gooru classic view link
	 */
	@SuppressWarnings("deprecation")
	public HeaderUc() {

		autokeySuggestOracle = new AppMultiWordSuggestOracle(true);
		setEditSearchTxtBox(new AppSuggestBox(autokeySuggestOracle) {
			@Override
			public HandlerRegistration addClickHandler(ClickHandler handler) {
				return null;
			}

			@Override
			public void keyAction(String text,KeyUpEvent event) {
				MixpanelUtil.Search_autocomplete_select();
				autokeySuggestOracle.clear();
				text.replaceAll("-<n> Gooru Search</n>", "");
				autoSuggestKeywordDo.setQuery(text);
				searchData = getEditSearchTxtBox().getText();
				autoSuggestKeywordDo.setType("resource");
				if (text != null && text.trim().length() > 2) {
					requestAutoSuggestKeyword(autoSuggestKeywordDo);
				} else {
					getEditSearchTxtBox().hideSuggestionList();
				}

				if (event.getNativeKeyCode() == (char) KeyCodes.KEY_ENTER) {
					getEditSearchTxtBox().hideSuggestionList();
				}
			}
		});
		SelectionHandler<SuggestOracle.Suggestion> se=new SelectionHandler<SuggestOracle.Suggestion>() {

			@Override
			public void onSelection(final SelectionEvent<Suggestion> event) {
				final String searchText = event.getSelectedItem().getDisplayString().replaceAll("-<n> Gooru Search</n>", "");
				editSearchTxtBox.setText(searchText.trim());
				GWT.runAsync(new SimpleRunAsyncCallback() {
					@Override
					public void onSuccess() {
						Window.enableScrolling(true);
						AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
						if (editSearchTxtBox.getText() != null
								&& editSearchTxtBox.getText().length() > 0) {
							if(!getEditSearchTxtBox().getText().trim().equalsIgnoreCase("*"))
							{
							if(getEditSearchTxtBox().getText().length()>2)
							{
							gooruLabel.setVisible(false);
							MixpanelUtil.Perform_Search(editSearchTxtBox.getText().trim()
									.toLowerCase(), "HeaderUc");
							Map<String, String> params = new HashMap<String, String>();
							params = updateParams(params);
							savePlaceRequest();
							if (AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(
									PlaceTokens.SEARCH_RESOURCE)) {
								AppClientFactory.getPlaceManager().revealPlace(
										PlaceTokens.SEARCH_RESOURCE, params);
							} else {
								String queryVal = params.get("query");
								// queryVal = queryVal.replaceAll("%5C1", "&");
								Map<String, String> map = params;
							//	queryVal.replaceAll("-<n> Gooru Search</n>", "");
								map.put("query", queryVal);
								editSearchTxtBox.setText(queryVal);
								AppClientFactory.getPlaceManager().revealPlace(
										PlaceTokens.SEARCH_COLLECTION, map);
							}
							editSearchTxtBox.setText("");
							AppClientFactory.fireEvent(new HomeEvent(HeaderTabType.DISCOVER));
							editSearchTxtBox.hideSuggestionList();
							getEditSearchTxtBox().setText(searchText.trim());
							}
							else
							{
								gooruLabel.setVisible(true);
								getEditSearchTxtBox().setText("");
								gooruLabel.setText(i18n.GL3583());
							}
						}
						else
						{
							gooruLabel.setVisible(true);
							getEditSearchTxtBox().setText("");
							gooruLabel.setText(i18n.GL3581());
						}
						}
						else
						{
							//else is for * query search.
							if(getEditSearchTxtBox().getText().isEmpty())
							{
								gooruLabel.setVisible(true);
								getEditSearchTxtBox().setText("");
								gooruLabel.setText(i18n.GL3582());
							}
						}

						hasAutoSelected = true;
						MixpanelUtil.mixpanelEvent("Select_Autocomplete_Search");
					}
				});
			}
		};
		getEditSearchTxtBox().addSelectionHandler(se);
		getEditSearchTxtBox().setPopupStyleName("shelfEditSearchTextBox");
		Window.addWindowScrollHandler(new Window.ScrollHandler() {
			@Override
			public void onWindowScroll(ScrollEvent event) {
				getEditSearchTxtBox().hideSuggestionList();
			}
		});
		initWidget(uiBinder.createAndBindUi(this));
		gooruLabel.setVisible(false);
		headerMainPanel.getElement().setAttribute("id", "headerMainPanel");

		logoutPanelVc = new LogoutPanelVc();
		settingOptionsPopup.add(logoutPanelVc);

		editSearchInputFloPanel.setVisible(false);
		LoginLinkContainer.setVisible(false);
		loggedInfoLbl.setVisible(false);
		loggedInfoLbl.getElement().getStyle().clearWidth();
		imgUserProfile.setVisible(false);
		logoutDownArrowLbl.setVisible(false);
		discovertooltippop.getElement().getStyle().setDisplay(Display.NONE);
		imgUserProfile.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				imgUserProfile.setUrl(DEFAULT_PROFILE_IMAGE);
			}
		});

		editSearchInputFloPanel.setVisible(false);
		this.switchToClassicView();
		/*
		 * headerSearchBarVerPanel.setCellVerticalAlignment(
		 * headerSearchBarFloPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		 */

		noneMenu = new Anchor();

		discoverLink.getParent().getElement().setId("LinkheaderElement1");
		organizeLink.getParent().getElement().setId("LinkheaderElement2");
		teachLink.getParent().getElement().setId("LinkheaderElement3");
		studyLink.getParent().getElement().setId("LinkheaderElement4");
		loggedInfoLbl.getParent().getElement().setId("LinkheaderElement5");
		discoverLink.getElement().setAttribute("data-toggle", "dropdown");

		discoverLink.getParent().setStyleName(GooruCBundle.INSTANCE.css().menu());

		activateAllMenu();

		discoverLinkContainer
				.addClickHandler(new OnClickDiscoverEventHandler());

		organizeLinkContainer
				.addClickHandler(new OnClickOrganizeEventHandler());

		teachLinkContainer.addClickHandler(new OnClickTeachEventHandler());

		studyLinkContainer.addClickHandler(new studyClickHandler());

		getEditSearchTxtBox().getElement().setId("txtEditSearch");
		editSearchBtn.getElement().setId("btnEditSearch");
		editSearchBtn.getElement().setAttribute("style","padding:7px 9px 9px 7px");
		editSearchBtn.setText(i18n.GL0176());
		editSearchBtn.getElement().setAttribute("alt", i18n.GL0176());
		editSearchBtn.getElement().setAttribute("title", i18n.GL0176());

		confirmEmailText.getElement().setAttribute("style",
				"float: left;font-size:11px;");
		confirmEmailText.setText(i18n.GL1248());
		confirmEmailText.getElement().setId("lblConfirmEmailText");
		confirmEmailText.getElement().setAttribute("alt", i18n.GL1248());
		confirmEmailText.getElement().setAttribute("title", i18n.GL1248());

		resendEmailAncr.getElement().setAttribute("style",
				"float: left;font-size:11px;");
		resendEmailAncr.setText(i18n.GL1249());
		resendEmailAncr.getElement().setId("lnkResendEmail");
		resendEmailAncr.getElement().setAttribute("alt", i18n.GL1249());
		resendEmailAncr.getElement().setAttribute("title", i18n.GL1249());
		toggleButton.getElement().setAttribute("data-target",
				"#bs-example-navbar-collapse-1");

		getEditSearchTxtBox().getElement().setAttribute("placeholder",
				i18n.GL0177());

		discoverLink.setText(i18n.GL1748_1());
		discoverLink.getElement().setId("lblDiscoverLink");
		discoverLink.getElement().setAttribute("alt", i18n.GL1748_1());
		discoverLink.getElement().setAttribute("title", i18n.GL1748_1());

		organizeLink.setText(i18n.GL1752());
		organizeLink.getElement().setId("lblOrganizeLink");
		organizeLink.getElement().setAttribute("alt", i18n.GL1752());
		organizeLink.getElement().setAttribute("title", i18n.GL1752());

		teachLink.setText(i18n.GL1753());
		teachLink.getElement().setId("lblTeachLink");
		teachLink.getElement().setAttribute("alt", i18n.GL1753());
		teachLink.getElement().setAttribute("title", i18n.GL1753());

		studyLink.setText(i18n.GL0182());// not used.
		studyLink.getElement().setId("lblStudyLink");
		studyLink.getElement().setAttribute("alt", i18n.GL0182());
		studyLink.getElement().setAttribute("title", i18n.GL0182());

		loggedInfoLbl.setText(i18n.GL0183());
		loggedInfoLbl.getElement().setId("lblLoggedInfo");


		loggedInfoLbl.getElement().setAttribute("alt",i18n.GL0183());
		loggedInfoLbl.getElement().setAttribute("title",i18n.GL0183());

		registerLinkLbl.setText(i18n.GL0186());
		registerLinkLbl.getElement().setId("btnRegister");
		registerLinkLbl.getElement().setAttribute("alt", i18n.GL0186());
		registerLinkLbl.getElement().setAttribute("title", i18n.GL0186());

		loginLink.setText(i18n.GL0187());
		loginLink.getElement().setId("lblLogin");
		loginLink.getElement().setAttribute("alt", i18n.GL0187());
		loginLink.getElement().setAttribute("title", i18n.GL0187());

		headerSearchBarVerPanel.getElement()
				.setId("vsbHeaderSearchBarVerPanel");
		headerSearchBarFloPanel.getElement().setId(
				"fpnlHeaderSearchBarFloPanel");
		editSearchTxtBox.getElement().setId("tbautoEditSearchTxtBox");
		StringUtil.setAttributes(editSearchTxtBox, true);
		mainDotsPanel.getElement().setId("pnlMainDotsPanel");
		mainInnerDotsPanel.getElement().setId("bs-example-navbar-collapse-1");
		dotsPanel.getElement().setId("pnlDotsPanel");

		dropDownImg.getElement().setId("pnlDropDownImg");
		dropDownImgforDashboard.getElement().getStyle().setMarginTop(14, Unit.PX);
		dropDownImgforDashboard.setVisible(false);
		panelArrow.setVisible(false);
		signUpInfo.getElement().setId("fpnlSignUpInfo");
		logoutDownArrowLbl.getElement().setId("lblLogoutDownArrow");
		logInfoFloPanel.getElement().setId("fpnlLogInfoFloPanel");
		acctActivationPl.getElement().setId("epnlAcctActivationPl");

		discoverToolTip = new DiscoverToolTipUc();
		discoverToolTip.getElement().getStyle().setZIndex(99);
		discovertooltippop.add(discoverToolTip);

		discoverLinkMain.addMouseOverHandler(new DiscoverMouseOver());
		discoverLinkMain.addMouseOutHandler(new DiscoverMouseOut());

		studyLinkContainer.setVisible(false);
		ClickHandler eve1 = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				GWT.runAsync(new SimpleRunAsyncCallback() {

					@Override
					public void onSuccess() {

						if (!isSettingIcon) {
							isOpenSettingDropDown = true;
							settingOptionsPopup.setVisible(false);
							logoutPanelVc.hide();
						} else {
							isSettingIcon = false;
						}
						if (!isStudyNow && studyNowToolTip != null) {
							isClassCodePopupOpen = true;
							studyNowToolTip.hide();
							studyNowToolTip.getClassCodeTxtBox().setText("");
						} else {
							isStudyNow = false;
						}

					}
				});
			}
		};
		acctActivationPl.setVisible(false);
		resendEmailAncr.addClickHandler(new ResendEmailConfirmation());

		if (!AppClientFactory.isAnonymous()) {
			if (AppClientFactory.getLoggedInUser() != null && AppClientFactory.getLoggedInUser().getConfirmStatus() == 0) {
				acctActivationPl.setVisible(true);
			}
		} else {
			acctActivationPl.setVisible(false);
		}

		ClickHandler dotsClick = new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				getEditSearchTxtBox().setText("");
			}
		};
		mainDotsPanel.addDomHandler(dotsClick, ClickEvent.getType());

		AppClientFactory.getEventBus().addHandler(SetHeaderEvent.TYPE,
				setHeader);
		AppClientFactory.getEventBus().addHandler(SetHeaderZIndexEvent.TYPE,
				setZindex);
		AppClientFactory.getEventBus().addHandler(ClearClasspageListEvent.TYPE,
				clearHandler);
		AppClientFactory.getEventBus().addHandler(ConfirmStatusPopupEvent.TYPE,
				confirmUser);

		AppClientFactory.getEventBus().addHandler(
				DeleteClasspageListEvent.TYPE, deleteHandler);

		RootPanel.get().addDomHandler(eve1, ClickEvent.getType());

		discoverLinkUrl = null;
		gooruLearning.setId("lnkeleGooruLearning");
		manageDotsMenuSelection(noneMenu);
		String emailId = AppClientFactory.getPlaceManager()
				.getRequestParameter("emailId");

		if (emailId != null) {

			AppClientFactory
					.getInjector()
					.getUserService()
					.getRefershToken(
							AppClientFactory.getLoggedInUser().getGooruUId(),
							new AsyncCallback<String>() {

								@Override
								public void onSuccess(String result) {
									// StringUtil.consoleLog("Header UC RefershToken..."+result);
									UserDo user = AppClientFactory
											.getLoggedInUser();
									user.setRefreshToken(result);
									AppClientFactory.setLoggedInUser(user);

								}

								@Override
								public void onFailure(Throwable caught) {
									// StringUtil.consoleLog("Header UC onFailure...");
								}
							});
		}

		//Handling specify case for IE.
		if (!BrowserAgent.isDevice() && BrowserAgent.getWebBrowserClient().equalsIgnoreCase("ie")){
			editSearchBtn.setVisible(true);
			headerMainPanel.getElement().getStyle().setWidth(50, Unit.PX);
		}
		editSearchTxtBox.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				gooruLabel.setVisible(false);
				int key=event.getNativeEvent().getKeyCode();
				if(key==KeyCodes.KEY_ENTER){
					String searchText = editSearchTxtBox.getText();
					searchText = searchText.replaceAll("-<n> Gooru Search</n>", "");
					editSearchTxtBox.setText(searchText.trim());
				}
			}
		});
	}

	public void clearClasspageList() {
		studyNowToolTip = null;
	}

	/**
	 * Signin using the popup
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("loginLink")
	public void onLinkPopupClicked(ClickEvent clickEvent) {
		final LoginPopupUc popup = new LoginPopupUc(this) {
			@Override
			public void onLoginSuccess() {

			}
		};
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, false));
				popup.setGlassEnabled(true);
				popup.center();
				popup.show();
				if (name != null) {
					if (name.equalsIgnoreCase("teach")) {
						popup.setNameToken(PlaceTokens.EDIT_CLASSPAGE);
					} else if (name.equalsIgnoreCase("organize")) {
						popup.setNameToken(PlaceTokens.MYCONTENT);
					}
					name = null;
				}

			}
		});
	}

	/*
	 * Selection Control DOTS panel
	 *
	 * @param Label
	 */

	public void manageDotsMenuSelection(Anchor dotsLink) {

		for (int i = 1; i <=5; i++) {

			if (userDo != null
					&& !userDo.getUserUid().equals(
							AppClientFactory.GOORU_ANONYMOUS)) {
				try {
					Document.get()
							.getElementById("LinkheaderElement" + i)
							.removeClassName("menuActive");
					Document.get()
							.getElementById("LinkheaderElement" + i)
							.removeClassName("menu");

					Document.get().getElementById("LinkheaderElement" + i)
							.addClassName("menu");
				} catch (Exception e) {
					AppClientFactory.printSevereLogger("HeaderUc manageDotsMenuSelection:::"+e);
				}

			} else {
				activateOnlyDiscover();

			}

		}
		if (dotsLink.equals(noneMenu)) {

		} else {

			dotsLink.getParent().setStyleName("menuActive");
		}
	}

	public void activateOnlyDiscover() {
		organizeLink.getParent().setStyleName("menu");
		teachLink.getParent().setStyleName("menu");
		studyLink.getParent().setStyleName("menu");
		loggedInfoLbl.getParent().setStyleName("menu");
		discoverLink.getParent().setStyleName("menu");
		if (logoutPanelVc != null) {
			if (logoutPanelVc.isShowing()) {
				logoutPanelVc.hide();
			}
			if (logoutPanelVc.logoutPopupVc != null) {
				if (logoutPanelVc.logoutPopupVc.appPopUp != null) {
					if (logoutPanelVc.logoutPopupVc.appPopUp.isShowing()) {
						logoutPanelVc.logoutPopupVc.appPopUp.hide();
					}
				}
			}

		}

	}

	public void activateAllMenu() {
		discoverLink.getParent().setStyleName("menu");
		organizeLink.getParent().setStyleName("menu");
		teachLink.getParent().setStyleName("menu");
		studyLink.getParent().setStyleName("menu");
		loggedInfoLbl.getParent().setStyleName("menu");
	}

	/**
	 * Register using the popup
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("registerLinkLbl")
	public void onRegisterPopupClicked(ClickEvent clickEvent) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				MixpanelUtil.Arrive_Register_popup();

				DataLogEvents.signUp(GwtUUIDGenerator.uuid(), "home",
						PlayerDataLogEvents.getUnixTime(), PlayerDataLogEvents.getUnixTime(), "");

				Map<String, String> map = StringUtil.splitQuery(Window.Location
						.getHref());
				if (map.containsKey("query")) {
					String queryVal = AppClientFactory.getPlaceManager()
							.getRequestParameter("query");
					queryVal = queryVal.replace("+", " ");
					map.put("query", queryVal);
					editSearchTxtBox.setText(queryVal);
				}
				if (map.containsKey("flt.subjectName")) {
					String subjectNameVal = AppClientFactory.getPlaceManager()
							.getRequestParameter("flt.subjectName");
					subjectNameVal = subjectNameVal.replace("+", " ");
					map.put("flt.subjectName", subjectNameVal);
				}
				map.put("callback", "signup");
				map.put("type", "1");

				PlaceRequest placeRequest=AppClientFactory.getPlaceManager().preparePlaceRequest(AppClientFactory.getCurrentPlaceToken(), map);
				AppClientFactory.getPlaceManager().revealPlace(false, placeRequest, false);



			}
		});
	}

	/* Click event hanlder for Menu items */
	public class OnClickDiscoverEventHandler implements ClickHandler {

		@Override
		public void onClick(final ClickEvent event) {
			gooruLabel.setVisible(false);
			Window.enableScrolling(true);
			Cookies.setCookie("searchvalue", "");
			AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, true));
			Element e = null;
			if ((e = event.getRelativeElement()) != null) {
				if (e.getInnerHTML() != null
						&& e.getInnerHTML().contains("gwt-Label"))
					MixpanelUtil.Click_Discover_LandingPage();
			}
			manageDotsMenuSelection(discoverLink);
			if ((discoverLinkUrl != null)
					&& (!discoverLinkUrl.contains("featured-contributors"))) {
				Map<String, String> params = StringUtil
						.splitQuery(discoverLinkUrl);
				AppClientFactory.getPlaceManager().revealPlace(
						PlaceTokens.DISCOVER, params);
			} else {
				AppClientFactory.getPlaceManager().revealPlace(
						PlaceTokens.DISCOVER);
			}

		}

	}

	public class OnClickOrganizeEventHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {
					gooruLabel.setVisible(false);
					AppClientFactory.setPreviousPlaceRequest(AppClientFactory
							.getPlaceManager().getCurrentPlaceRequest());
					Storage stockStore = Storage.getLocalStorageIfSupported();

					if (stockStore != null) {
						stockStore.setItem("tabKey", "resourceTab");
					}
					name = "organize";
					if (AppClientFactory.isAnonymous()) {
						Window.enableScrolling(true);
					} else {
						Window.enableScrolling(false);
					}
					AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, true));
					manageDotsMenuSelection(organizeLink);
					if(AppClientFactory.getCurrentPlaceToken().equals(PlaceTokens.MYCONTENT)){
						AppClientFactory.fireEvent(new LoadMyContentEvent("Course"));
					}else{
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.MYCONTENT);
					}

				}
			});
		}
	}

	public class OnClickTeachEventHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {
					gooruLabel.setVisible(false);
					Window.enableScrolling(true);
					AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, true));
					if (userDo != null && !userDo.getUserUid().equals(AppClientFactory.GOORU_ANONYMOUS)) {
						Map<String, String> params = new HashMap<String, String>();
						params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_PAGE_DIRECT, UrlNavigationTokens.MYCLASS);
						AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.CLASSHOME,params);
					} else {
						name = "teach";
						Window.enableScrolling(true);
						// onLinkPopupClicked(null);
						// TODO need to show new logout page....
						AppClientFactory.getPlaceManager().redirectPlace(PlaceTokens.STUDY);
					}

				}
			});

		}

	}

	public class OnClickDashBoardEventHandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {
					gooruLabel.setVisible(false);
					name = "dashboard";

					Window.enableScrolling(true);

					AppClientFactory.fireEvent(new SetHeaderZIndexEvent(98, true));
					manageDotsMenuSelection(loggedInfoLbl);
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.DASHBOARD);

				}
			});
		}
	}

	/* On mouse over and out handlers */
	public class DiscoverMouseOver implements MouseOverHandler {
		@Override
		public void onMouseOver(MouseOverEvent event) {

			discovertooltippop.setVisible(true);
		}
	}

	public class DiscoverMouseOut implements MouseOutHandler {

		@Override
		public void onMouseOut(MouseOutEvent event) {
			discovertooltippop.setVisible(false);
		}
	}

	public class TeachMouseOver implements MouseOverHandler {

		@Override
		public void onMouseOver(final MouseOverEvent event) {
			if (!AppClientFactory.isAnonymous()) {
				myClassesPop.setVisible(true);

			}
		}
	}

	public class TeachMouseOut implements MouseOutHandler {

		@Override
		public void onMouseOut(MouseOutEvent event) {
			if (!AppClientFactory.isAnonymous()) {

				myClassesPop.setVisible(false);
			}
		}

	}

	public class StudyMouseOver implements MouseOverHandler {

		@Override
		public void onMouseOver(final MouseOverEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {

					toolTipPopupPanel.clear();
					toolTipPopupPanel.setWidget(new StudyToolTip());
					toolTipPopupPanel.setStyleName("");
					if (event.getSource().equals(studyLink)) {
						toolTipPopupPanel.setPopupPosition(event.getRelativeElement()
								.getAbsoluteLeft() - 101, event.getRelativeElement()
								.getAbsoluteTop() + 25);
					} else {
						toolTipPopupPanel.setPopupPosition(event.getRelativeElement()
								.getAbsoluteLeft() - 85, event.getRelativeElement()
								.getAbsoluteTop() + 41);
					}
					toolTipPopupPanel.show();

				}
			});
		}
	}

	public class StudyMouseOut implements MouseOutHandler {

		@Override
		public void onMouseOut(MouseOutEvent event) {
			toolTipPopupPanel.hide();
		}
	}

	/**
	 *
	 * @function manageDotsMenuSelectionFromEvent
	 *
	 * @created_date : 17-Feb-2015
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param tabType
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void manageDotsMenuSelectionFromEvent(HeaderTabType tabType) {

		if (tabType == HeaderTabType.DISCOVER) {
			manageDotsMenuSelection(discoverLink);
		} else if (tabType == HeaderTabType.ORGANIZE) {
			manageDotsMenuSelection(organizeLink);
		} else if (tabType == HeaderTabType.TEACH) {
			manageDotsMenuSelection(teachLink);
		} else if (tabType == HeaderTabType.STUDY) {
			manageDotsMenuSelection(studyLink);
		} else if (tabType == HeaderTabType.USERNAME) {
			manageDotsMenuSelection(loggedInfoLbl);
		} else if (tabType == HeaderTabType.HOME) {

		} else {
			manageDotsMenuSelection(noneMenu);
		}

	}

	/**
	 * View about goorulearning link , logout link
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("logoutDownArrowLbl")
	public void logoutPanel(ClickEvent clickEvent) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				isSettingIcon = true;
				if(logoutPanelVc.isShowing()){
					settingOptionsPopup.setVisible(false);
					logoutPanelVc.hide();
					isOpenSettingDropDown = true;

				}else if(isOpenSettingDropDown){
					settingOptionsPopup.setVisible(true);
					logoutPanelVc.show();
					isOpenSettingDropDown = false;

				}


			}
		});
	}

	/**
	 * View sign out popup
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("loggedInfoLbl")
	public void signoutPanel(final ClickEvent clickEvent) {
		final Element e = clickEvent.getRelativeElement();

		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				Window.enableScrolling(true);
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(99, true));

				if (e  != null) {
					if (e.getInnerHTML() != null
							&& e.getInnerHTML().contains("gwt-Label"))
						MixpanelUtil.Click_Discover_LandingPage();
				}
				manageDotsMenuSelection(loggedInfoLbl);
				if (userDo!= null){
					Map<String, String> params = new HashMap<String, String>();
					params.put("id", userDo.getGooruUId());
					params.put("user", userDo.getUsername());
					MixpanelUtil.Click_On_UserName();
					AppClientFactory.getPlaceManager().revealPlace(
							PlaceTokens.PROFILE_PAGE, params);
				}
			}
		});



	}

	/**
	 * Search resource by entered keyword
	 *
	 * @param clickEvent
	 *            instance of {@link ClickEvent}
	 */
	@UiHandler("editSearchBtn")
	public void OnSearchClick(ClickEvent clickEvent) {
		GWT.runAsync(new SimpleRunAsyncCallback() {
			@Override
			public void onSuccess() {
				AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
				if (getEditSearchTxtBox().getText() != null
						&& getEditSearchTxtBox().getText().length() > 0) {
				if(!getEditSearchTxtBox().getText().trim().equalsIgnoreCase("*"))
				{
					if(getEditSearchTxtBox().getText().length()>2)
					{
					savePlaceRequest();
					MixpanelUtil.Perform_Search(getEditSearchTxtBox().getText().trim()
							.toLowerCase(), "HeaderUc");
					Map<String, String> params = new HashMap<String, String>();
					params = updateParams(params);
					if (AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(
							PlaceTokens.SEARCH_RESOURCE)) {
						AppClientFactory.getPlaceManager().revealPlace(
								PlaceTokens.SEARCH_RESOURCE, params);
					} else {
						String queryVal = params.get("query");
						// queryVal = queryVal.replaceAll("%5C1", "&");
						Map<String, String> map = params;
						map.put("query", queryVal);
						editSearchTxtBox.setText(queryVal);

						AppClientFactory.getPlaceManager().revealPlace(
								PlaceTokens.SEARCH_COLLECTION, params);
					}
					AppClientFactory.fireEvent(new HomeEvent(HeaderTabType.NONE));
					getEditSearchTxtBox().hideSuggestionList();
					}
					else
					{
						gooruLabel.setVisible(true);
						getEditSearchTxtBox().setText("");
						gooruLabel.setText(i18n.GL3583());
					}
				}
				else
				{
					gooruLabel.setVisible(true);
					getEditSearchTxtBox().setText("");
					gooruLabel.setText(i18n.GL3581());
				}
				}else{
					//else is for * query search.

					if(getEditSearchTxtBox().getText().isEmpty())
					{
						gooruLabel.setVisible(true);
						getEditSearchTxtBox().setText("");
						gooruLabel.setText(i18n.GL3582());
					}
				}

				if(AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(PlaceTokens.SHELF)){

					MixpanelUtil.mixpanelEvent("Perform_Search_FromOrganize");
				} else if (AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(
						PlaceTokens.STUDY)) {
					MixpanelUtil.mixpanelEvent("Perform_Search_FromStudy");
				} else if (AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(
						PlaceTokens.SETTINGS)) {
					MixpanelUtil.mixpanelEvent("Perform_Search_FromSettings");
				} else if (AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(
						PlaceTokens.PROFILE_PAGE)) {
					MixpanelUtil.mixpanelEvent("Perform_Search_FromProfile");
				} else if (AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(
						PlaceTokens.EDIT_CLASSPAGE)) {
					MixpanelUtil.mixpanelEvent("Perform_Search_FromTeach");
				} else if (AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(
						PlaceTokens.HOME)) {
					MixpanelUtil.mixpanelEvent("Perform_Search_FromLandingPage");
					if (AppClientFactory.isAnonymous()) {
						MixpanelUtil
								.mixpanelEvent("Perform_Search_FromLandingPage_Loggedout");
					} else {
						MixpanelUtil
								.mixpanelEvent("Perform_Search_FromLandingPage_Loggedin");
					}
					if (AppClientFactory.getPlaceManager().getRequestParameter(
							"courseId") != null) {
						MixpanelUtil.mixpanelEvent("Perform_Search_FromCoursePage");
					}
					if (AppClientFactory.getPlaceManager().getRequestParameter("page") != null
							&& AppClientFactory.getPlaceManager()
									.getRequestParameter("page")
									.equalsIgnoreCase("featured-contributors")) {
						MixpanelUtil
								.mixpanelEvent("Perform_Search_FromContributorsPage");
					}
				}
				if (hasAutoSelected) {
					MixpanelUtil.mixpanelEvent("Select_Autocomplete_Search");
				}


			}
		});
	}

	public void savePlaceRequest() {
		String currentPlaceToken = AppClientFactory.getPlaceManager()
				.getCurrentPlaceRequest().getNameToken();
		if (currentPlaceToken.equals(PlaceTokens.SEARCH_COLLECTION)
				|| currentPlaceToken.equals(PlaceTokens.SEARCH_RESOURCE)) {
		} else {
			AppClientFactory.getPlaceManager()
					.setSearchMovedPlaceRequest(
							AppClientFactory.getPlaceManager()
									.getCurrentPlaceRequest());
		}
	}

	/**
	 * Set pagination for search
	 *
	 * @param params
	 *            variable for Map<String,String>
	 * @return pagination values
	 */
	public Map<String, String> updateParams(Map<String, String> params) {
		params.clear();
		params.put("query", getEditSearchText());
		String currentPlaceToken=AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken();
		String collectionType = AppClientFactory.getPlaceManager().getRequestParameter(IsGooruSearchView.COLLECTIONTYPE_FLT,null);
		if(collectionType!=null){
			params.put(IsGooruSearchView.COLLECTIONTYPE_FLT, collectionType);
		}else{
			params.put(IsGooruSearchView.COLLECTIONTYPE_FLT, "collection");
		}
		params.put("category", "All");
		if(currentPlaceToken.equals(PlaceTokens.SEARCH_RESOURCE)){
			params.put(IsGooruSearchView.RATINGS_FLT, "5,4,3,2,1,0");
		}
		return params;
	}

	/**
	 * @param text
	 *            Search keyword
	 */
	public void setEditSearchText(String text) {
		getEditSearchTxtBox().setText(text);
	}

	/**
	 * Set loggedIn user details
	 *
	 * @param userDo
	 *            instance of {@link UserDo}
	 */
	public void setLoggedInUser(final UserDo userDo) {
		this.userDo = userDo;
		activateAllMenu();
		if (userDo != null && userDo.getUserUid() != null
				&& !userDo.getUserUid()
						.equals(AppClientFactory.GOORU_ANONYMOUS)) {

			int flag = userDo.getViewFlag() != null ? userDo.getViewFlag() : 0;

			loggedInfoLbl.setText(userDo.getUsername());
			loggedInfoLbl.setTitle(userDo.getUsername());

			loggedInfoLbl.getElement().setAttribute("alt", userDo.getUsername());
			imgUserProfile.setUrl(userDo.getProfileImageUrl() != "" && userDo.getProfileImageUrl() !=null ? userDo.getProfileImageUrl()+ "?" + Math.random() : DEFAULT_PROFILE_IMAGE);

			imgUserProfile.getElement().setAttribute("onerror","imgError(this);");
			imgUserProfile.setAltText(userDo.getUsername());
			imgUserProfile.getElement().setId("imgUserProfile");
			imgUserProfile.getElement().setAttribute("alt",
					userDo.getUsername());
			imgUserProfile.getElement().setAttribute("title",
					userDo.getUsername());
			imgUserProfile.setVisible(true);

			LoginLinkContainer.setVisible(true);
			loggedInfoLbl.setVisible(true);

			logoutDownArrowLbl.setVisible(true);
			logInfoFloPanel.setVisible(false);
			if (AppClientFactory.getLoggedInUser() != null && AppClientFactory.getLoggedInUser().getConfirmStatus() == 0) {
				acctActivationPl.setVisible(true);
			}
			mainDotsPanel.setVisible(true);

			this.switchToClassicView();

		} else {
			mainDotsPanel.getElement().getStyle().clearWidth();
			mainInnerDotsPanel.getElement().getStyle().clearWidth();

			LoginLinkContainer.setVisible(false);
			loggedInfoLbl.setVisible(false);
			loggedInfoLbl.getElement().getStyle().clearWidth();
			logoutDownArrowLbl.setVisible(false);
			acctActivationPl.setVisible(false);
			logInfoFloPanel.setVisible(true);
			// mainDotsPanel.setVisible(false);
			// goToClasicGooruPanel.setVisible(false);
			// goToClasicGooruPanel.getElement().getStyle().setVisibility(Visibility.HIDDEN);
			// activateOnlyDiscover();
			// gooruGuideImgLbl.addStyleName(GooruCBundle.INSTANCE.css().loggedInGooruGuideImg());
			// gooruClassicViewLbl.setStyleName(GooruCBundle.INSTANCE.css().gooruClassicView());

		}
		if (AppClientFactory.getLoggedInUser() != null) {
			gooruLearning.setHref(AppClientFactory.getLoggedInUser()
					.getSettings() != null ? AppClientFactory.getLoggedInUser()
					.getSettings().getHomeEndPoint() : "");
		}
		try {
			String googleAnaltics = userDo.getSettings()
					.getGoogleAnalticsExtraAccounts();
			if (GWT.isScript() && googleAnaltics != null
					&& !googleAnaltics.startsWith("$") && !addedAccounts) {
				String[] accounts = googleAnaltics.split(",");
				for (String account : accounts) {
					String[] accountParams = account.split(":");
					if (accountParams.length == 2) {
						AppClientFactory.getInjector().getGoogleAnalytics()
								.addAccount(accountParams[0], accountParams[1]);
					}
				}
				addedAccounts = true;
			}
		} catch (Exception e) {
			AppClientFactory.printSevereLogger("HeaderUc setLoggedInUser:::::"+e);
		}
	}

	public void showMarketingPopup(UserDo userDo) {
//		new ImprovedGooruPopUpView();
	}

	/**
	 * @return search keyword
	 */
	public String getEditSearchText() {
		String searchText = getEditSearchTxtBox().getText();
		if (searchText != null && searchText.length() > 0) {
			return searchText;
		} else {
			return null;
		}
	}


	/**
	 * @author Search Team
	 *
	 */
	private class SearchKeyDownHandler implements KeyDownHandler {

		@Override
		public void onKeyDown(final KeyDownEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {
					gooruLabel.setVisible(false);
					if (event.getNativeKeyCode() == (char) KeyCodes.KEY_ENTER) {
						if (getEditSearchTxtBox().getText() != null
								&& getEditSearchTxtBox().getText().length() > 0) {
							if(!getEditSearchTxtBox().getText().trim().equalsIgnoreCase("*"))
							{
							if(getEditSearchTxtBox().getText().length()>2)
							{
							if (AppClientFactory.getCurrentPlaceToken()
									.equalsIgnoreCase(PlaceTokens.EDIT_CLASSPAGE)) {
								MixpanelUtil.Perform_Search_FromTeach();

							} else if (AppClientFactory.getCurrentPlaceToken()
									.equalsIgnoreCase(PlaceTokens.SHELF)) {
								MixpanelUtil.Perform_Search_FromOrganize();
							}
							if (hasAutoSelected) {
								MixpanelUtil
										.mixpanelEvent("Select_Autocomplete_Search");
							}

							MixpanelUtil.Perform_Search(getEditSearchTxtBox().getText()
									.trim().toLowerCase(), "HeaderUc");
							savePlaceRequest();
							Map<String, String> params = new HashMap<String, String>();
							params = updateParams(params);
							if (AppClientFactory.getCurrentPlaceToken()
									.equalsIgnoreCase(PlaceTokens.COLLECTION_SEARCH)) {
								AppClientFactory.getPlaceManager().revealPlace(
										PlaceTokens.COLLECTION_SEARCH, params);
							} else {
								String queryVal = params.get("query");
								// queryVal = queryVal.replaceAll("%5C1", "&");
								Map<String, String> map = params;
								map.put("query", queryVal);
								AppClientFactory.getPlaceManager().revealPlace(
										PlaceTokens.SEARCH_RESOURCE, params);
							}

							if ((AppClientFactory.getCurrentPlaceToken()
									.equalsIgnoreCase(PlaceTokens.SHELF))
									|| (AppClientFactory.getCurrentPlaceToken()
											.equalsIgnoreCase(PlaceTokens.STUDY) || (AppClientFactory
											.getCurrentPlaceToken()
											.equalsIgnoreCase(PlaceTokens.EDIT_CLASSPAGE)))
									|| (AppClientFactory.getCurrentPlaceToken()
											.equalsIgnoreCase(PlaceTokens.SETTINGS))
									|| (AppClientFactory.getCurrentPlaceToken()
											.equalsIgnoreCase(PlaceTokens.PROFILE_PAGE))) {
								getEditSearchTxtBox().setText("");
							}

							AppClientFactory.fireEvent(new HomeEvent(
									HeaderTabType.DISCOVER));
							getEditSearchTxtBox().hideSuggestionList();
							}
							else
							{
								gooruLabel.setVisible(true);
								getEditSearchTxtBox().setText("");
								gooruLabel.setText(i18n.GL3583());
							}
							}
							else
							{
								gooruLabel.setVisible(true);
								getEditSearchTxtBox().setText("");
								gooruLabel.setText(i18n.GL3581());
							}
						}
					}


				}
			});
		}
	}


	/**
	 * @author Search Team
	 *
	 */
	private class studyClickHandler implements ClickHandler {

		@Override
		public void onClick(final ClickEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {
					gooruLabel.setVisible(false);
					isStudyNow = true;
					if (isClassCodePopupOpen) {
						if (studyNowToolTip != null && studyNowToolTip.isShowing()) {
							studyNowToolTip.hide();
							studyNowToolTip.getClassCodeTxtBox().setText("");
						} else {
							if (AppClientFactory.isAnonymous()) {
								isEnter = true;
								if (studyNowToolTip == null) {
									studyNowToolTip = new StudyNowToolTip();
								} else if (logInfoFloPanel.isVisible()) {
									if (studyNowToolTip != null) {
										studyNowToolTip.getLblTitle().setVisible(true);
										studyNowToolTip.getClassStudyList().clear();

									}
								}
							} else {
								if (studyNowToolTip == null || isEnter) {
									isEnter = false;
									studyNowToolTip = new StudyNowToolTip();
								}
							}
							studyNowToolTip.refreshClasslist();
							studyNowToolTip.getElement().getStyle()
									.setBackgroundColor("transparent");
							studyNowToolTip.getElement().getStyle()
									.setPosition(Position.ABSOLUTE);
							studyNowToolTip
									.setPopupPosition(
											event.getRelativeElement()
													.getAbsoluteLeft()
													- (AppClientFactory.isAnonymous() ? 115
															: 134),
											event.getRelativeElement().getAbsoluteTop()
													+ (AppClientFactory.isAnonymous() ? 23
															: 33));
							studyNowToolTip.show();
							getEditSearchTxtBox().setText("");
							ClickHandler handler = new ClickHandler() {
								@Override
								public void onClick(ClickEvent event) {
									studyNowToolTip.show();
									isStudyNow = true;
								}
							};
							studyNowToolTip.addDomHandler(handler, ClickEvent.getType());
						}
					} else {
						studyNowToolTip.hide();
						studyNowToolTip.getClassCodeTxtBox().setText("");
						isClassCodePopupOpen = true;
					}

					 Window.addWindowScrollHandler(new Window.ScrollHandler() {
				       public void onWindowScroll(Window.ScrollEvent scrollEvent) {
				    	   studyNowToolTip.getElement().getStyle()
							.setPosition(Position.FIXED);
		 		    	   studyNowToolTip.setPopupPosition(848,33);
				       }
				    });


				}
			});
		}
	}

	/**
	 * View or hide search bar in header
	 *
	 * @param enabled
	 *            to view or hide
	 */
	public void setSearchBarEnabled(boolean enabled) {
		editSearchInputFloPanel.setVisible(enabled);
	}


	/**
	 * Set logout popup location
	 *
	 * @param left
	 *            value to set popup location from left
	 * @param top
	 *            value to set popup location from top
	 */
	public void showLogoutPopup(int left, int top) {
	/*	logoutPanelVc.setWidth("80px");
		logoutPanelVc.setStyleName(GooruCBundle.INSTANCE.css().logoutPopup());
		logoutPanelVc.setPopupPosition(left, top);
		logoutPanelVc.show();
		isOpenSettingDropDown = false;
*/	}



	public void openGooruGuide() {

	}

	/**
	 * Allow the user to go classic view of gooru
	 */
	public void switchToClassicView() {

	}

	public void showTeachPanelAsPopup(final int left, int top) {


	}

	DeleteClasspageListHandler deleteHandler = new DeleteClasspageListHandler() {

		@Override
		public void deleteClasspage(final String classpageId) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {


					if (AppClientFactory.getCurrentPlaceToken().equals(
							PlaceTokens.EDIT_CLASSPAGE)) {
					} else {
						/*if (studyNowToolTip != null) {
							studyNowToolTip.removeClasspageItem(classpageId);
						} else {
							studyNowToolTip = new StudyNowToolTip();
						}*/
					}


				}
			});
		}
	};

	/**
	 * ReSend confirmation mail to user.
	 *
	 * @param params
	 *            contains gooruUid, accountType
	 */

	public class ResendEmailConfirmation implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {
					gooruLabel.setVisible(false);
					Map<String, String> params = new HashMap<String, String>();
					params.put(GOORU_UID, userDo.getGooruUId());

					if (userDo.getAccountTypeId() == 2
							|| userDo.getAccountTypeId() == 1) {
						params.put(ACCOUNT_TYPE, "Parent");
					} else {
						params.put(ACCOUNT_TYPE, "NonParent");
					}

					AppClientFactory
							.getInjector()
							.getUserService()
							.resendConfirmationMail(params,
									new SimpleAsyncCallback<Void>() {
										@Override
										public void onSuccess(Void result) {

										}
									});

				}
			});
		}

	}

	public void requestAutoSuggestKeyword(
			SearchDo<AutoSuggestKeywordSearchDo> searchDo) {
		getAutoSuggestionKeyWordAsyncCallback().execute(searchDo);
	}

	public void setAutoKeyWordSuggestions(
			SearchDo<AutoSuggestKeywordSearchDo> autoSuggestKeywordDo) {
		autokeySuggestOracle.clear();
		this.autoSuggestKeywordDo = autoSuggestKeywordDo;
		autokeySuggestOracle.add(searchData+i18n.GL0146());
		if (this.autoSuggestKeywordDo.getSearchResults() != null) {
			for (AutoSuggestKeywordSearchDo autoSuggestKeywordSearchDo : autoSuggestKeywordDo
					.getSearchResults()) {
				autokeySuggestOracle.add(autoSuggestKeywordSearchDo
						.getKeyword());
			}
		}
		getEditSearchTxtBox().showSuggestionList();

	}

	/**
	 * @return suggestion standards for the collection as map string
	 */
	public SearchAsyncCallback<SearchDo<AutoSuggestKeywordSearchDo>> getAutoSuggestionKeyWordAsyncCallback() {
		if (autoKeyWordSuggestionAsyncCallback == null) {
			autoKeyWordSuggestionAsyncCallback = new SearchAsyncCallback<SearchDo<AutoSuggestKeywordSearchDo>>() {

				@Override
				protected void run(SearchDo<AutoSuggestKeywordSearchDo> searchDo) {
					AppClientFactory.getInjector().getSearchService()
							.getSuggestedAutokeyword(searchDo, this);

				}

				@Override
				public void onCallSuccess(
						SearchDo<AutoSuggestKeywordSearchDo> result) {
					setAutoKeyWordSuggestions(result);

				}

			};
		}
		return autoKeyWordSuggestionAsyncCallback;
	}

	public static AppSuggestBox getEditSearchTxtBox() {
		return editSearchTxtBox;
	}

	public void setEditSearchTxtBox(AppSuggestBox editSearchTxtBox) {
		this.editSearchTxtBox = editSearchTxtBox;
	}

	public void setDiscoverLinkFromLibrary(String discoverLink) {
		this.discoverLinkUrl = discoverLink;
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		getEditSearchTxtBox().setFocus(true);
		//This will set the search keyword after refreshing the page
		Scheduler.get().scheduleDeferred(new ScheduledCommand(){
			@Override
			public void execute() {
				String queryVal=AppClientFactory.getPlaceManager().getRequestParameter("query");
				getEditSearchTxtBox().setText(queryVal);
				editSearchTxtBox.setText(queryVal);
			}
		});
	}

	/**
	 *
	 * @function OpenClasspageEdit
	 *
	 * @created_date : Aug 15, 2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param gooruOId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void OpenClasspageEdit(String gooruOId, String gooruUid) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("pageNum", "0");
		params.put("pageSize", "10");
		params.put("pos", "1");
		if (gooruUid.equals(AppClientFactory.getLoggedInUser().getGooruUId())) {
			params.put("classpageId", gooruOId);
			AppClientFactory.getPlaceManager().revealPlace(
					PlaceTokens.EDIT_CLASSPAGE, params);
		} else {
			params.put("id", gooruOId);
			AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT,
					params);
		}
	}

	public static void closeClassContainer() {
		if (myClassesPop != null) {
			myClassesPop.setVisible(false);
		}
	}


	private void changeQueryParams() {
		String searchText = editSearchTxtBox.getText();
		searchText= searchText.replaceAll("-<n> Gooru Search</n>", "");
		editSearchTxtBox.setText(searchText.trim());
		Window.enableScrolling(true);
		AppClientFactory.fireEvent(new SetHeaderZIndexEvent(0, true));
		if (editSearchTxtBox.getText() != null && editSearchTxtBox.getText().length() > 0) {
			if(!getEditSearchTxtBox().getText().trim().equalsIgnoreCase("*"))
			{
			if(getEditSearchTxtBox().getText().length()>2)
			{
			MixpanelUtil.Perform_Search(editSearchTxtBox.getText().trim().toLowerCase(),"HeaderUc");
			Map<String, String> params = new HashMap<String, String>();
			params = updateParams(params);
			savePlaceRequest();
			if (AppClientFactory.getCurrentPlaceToken().equalsIgnoreCase(
					PlaceTokens.COLLECTION_SEARCH)) {
				AppClientFactory.getPlaceManager().revealPlace(
						PlaceTokens.COLLECTION_SEARCH, params);
			} else {
				String queryVal = params.get("query");
				//queryVal = queryVal.replaceAll("%5C1", "&");
				Map<String, String> map = params;
				map.put("query", queryVal);
				editSearchTxtBox.setText(queryVal);
				AppClientFactory.getPlaceManager().revealPlace(
						PlaceTokens.SEARCH_RESOURCE, map);
			}
			editSearchTxtBox.setText("");
			AppClientFactory.fireEvent(new HomeEvent(HeaderTabType.DISCOVER));
			editSearchTxtBox.hideSuggestionList();
			getEditSearchTxtBox().setText(searchText.trim());
			}
			else
			{
				gooruLabel.setVisible(true);
				getEditSearchTxtBox().setText("");
				gooruLabel.setText(i18n.GL3583());
			}
			}
			else
			{
				gooruLabel.setVisible(true);
				getEditSearchTxtBox().setText("");
				gooruLabel.setText(i18n.GL3581());
			}
		}
		else
		{
			//else is for * query search.
			if(getEditSearchTxtBox().getText().isEmpty())
			{
				gooruLabel.setVisible(true);
				getEditSearchTxtBox().setText("");
				gooruLabel.setText(i18n.GL3582());
			}
		}

		hasAutoSelected=true;
		MixpanelUtil.mixpanelEvent("Select_Autocomplete_Search");
	}

	public void updateHeaderProfileImage(String imageUrl) {
		imgUserProfile.setUrl(imageUrl+ "?" + Math.random());
	}

	@UiHandler("toggleButton")
	public void toggleButtonClick(ClickEvent event){
		invokeToggleMenuContainer();
	}
	public static native void invokeToggleMenuContainer() /*-{
		$wnd.showToggleMenu();
	}-*/;

}
