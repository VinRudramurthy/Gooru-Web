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
package org.ednovo.gooru.client.mvp.classpages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.ednovo.gooru.application.client.PlaceTokens;
import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.shared.i18n.MessageProperties;
import org.ednovo.gooru.application.shared.model.content.AssignmentDo;
import org.ednovo.gooru.application.shared.model.content.AttachToDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.ClasspageListDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.TaskDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.SimpleRunAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;
import org.ednovo.gooru.client.mvp.classpages.event.RefreshClasspageListEvent;
import org.ednovo.gooru.client.mvp.classpages.event.RefreshClasspageListHandler;
import org.ednovo.gooru.client.mvp.classpages.event.UpdateClasspageTitleEvent;
import org.ednovo.gooru.client.mvp.classpages.event.UpdateClasspageTitleHandler;
import org.ednovo.gooru.client.mvp.classpages.newclasspage.NewClassPopupView;
import org.ednovo.gooru.client.mvp.classpages.newclasspage.NewClasspagePopupView;
import org.ednovo.gooru.client.mvp.classpages.studentView.StudentAssignmentView;
import org.ednovo.gooru.client.mvp.home.HeaderUc;
import org.ednovo.gooru.client.mvp.search.event.SetHeaderZIndexEvent;
import org.ednovo.gooru.client.mvp.socialshare.SentEmailSuccessVc;
import org.ednovo.gooru.client.uc.AlertMessageUc;
import org.ednovo.gooru.client.uc.TextBoxWithPlaceholder;
import org.ednovo.gooru.client.util.MixpanelUtil;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasMouseOutHandlers;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @fileName : ClasspageListVc.java
 *
 * @description : This class is used to display the List of Classpaes on
 *              clicking on Teach Tab.
 *
 * @version : 1.0
 *
 * @date: Aug 14, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
public class ClasspageListVc extends Composite implements HasMouseOutHandlers{

	@UiField
	Label lblTitle;
	@UiField
	Anchor ancNewClasspage;

	@UiField HTMLPanel mainContainer;

	@UiField
	Button enterLbl;

	@UiField
	TextBoxWithPlaceholder classCodeTxtBox;

	ClasspageListDo classpageListDo = null;

	Map<String, ClasspageDo> classpageList = new HashMap<String, ClasspageDo>();
	ArrayList<String> listClasspage = new ArrayList<String>();

	AlertMessageUc alertMessageUc;

	private boolean isValid = true;

	private int limit = 10;
	private int offSet = 0;
	private int tmpOffSet = 0;
	private boolean toClear = false;
	private boolean isApiCalling = false;
	private boolean whileDeleting = false;
	private int resultSize = 0;

	private static ClasspageListVcUiBinder uiBinder = GWT.create(ClasspageListVcUiBinder.class);
	interface ClasspageListVcUiBinder extends UiBinder<Widget, ClasspageListVc> {
	}

	@UiField(provided = true)
	ClasspageListPopupViewCBundle res;

	public MessageProperties i18n = GWT.create(MessageProperties.class);

	
	private NewClassPopupView newPopup = null;



	/**
	 * Class constructor
	 */
	public ClasspageListVc(boolean isClasspageRefreshed,
			String deletedClasspageId) {

		this.res = ClasspageListPopupViewCBundle.INSTANCE;
		res.css().ensureInjected();
		setWidget(uiBinder.createAndBindUi(this));
		ancNewClasspage.getElement().setId("lnkNewClasspage");


		RefreshClasspageListHandler refreshHandler = new RefreshClasspageListHandler() {

			@Override
			public void refreshClasspage() {
				toClear = true;
				offSet = 0;
			}
		};

		UpdateClasspageTitleHandler updateTitleHandler = new UpdateClasspageTitleHandler() {

			@Override
			public void updateClasspageTitle(String classpageId,
					String classpageTitle) {
			}
		};

		AppClientFactory.getEventBus().addHandler(
				RefreshClasspageListEvent.TYPE, refreshHandler);
		AppClientFactory.getEventBus().addHandler(
				UpdateClasspageTitleEvent.TYPE, updateTitleHandler);

		setLabels();
		toClear = true;
		mainContainer.getElement().setId("headerMainPanel");
	}


	/**
	 *
	 * @function setLabels
	 *
	 * @created_date : Aug 14, 2013
	 *
	 * @description This method is used to set the Label text.
	 *
	 * @parm(s) :
	 *
	 * @return : void
	 *
	 *
	 *
	 *
	 *
	 */
	private void setLabels() {
		ancNewClasspage.setText(i18n.GL0115());
		ancNewClasspage.getElement().setId("lnkNewClassPage");
		ancNewClasspage.getElement().setAttribute("alt",i18n.GL0115());
		ancNewClasspage.getElement().setAttribute("title",i18n.GL0115());
		enterLbl.addClickHandler(new OnEnterClassCodeClick());
		enterLbl.setText(i18n.GL1065());
		enterLbl.getElement().setId("btnEnter");
		enterLbl.getElement().setAttribute("alt",i18n.GL1065());
		enterLbl.getElement().setAttribute("title",i18n.GL1065());
		classCodeTxtBox.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				setButtonStatus("active");
			}
		});
		classCodeTxtBox.addKeyUpHandler(new KeyUpHandler() {

			@Override
			public void onKeyUp(KeyUpEvent event) {
				setButtonStatus("active");
			}
		});
		classCodeTxtBox.setText("");
		classCodeTxtBox.getElement().setAttribute("maxlength", "10");
		classCodeTxtBox.getElement().setId("txtClassCode");
		classCodeTxtBox.setPlaceholder(i18n.GL1762_1());

		setButtonStatus("active");
		lblTitle.getElement().setId("lblTitle");
	}
	/**
	 *
	 * @fileName : ClasspageListVc.java
	 *
	 * @description :
	 *
	 *
	 * @version : 1.0
	 *
	 * @date: 06-Dec-2014
	 *
	 * @Author Gooru Team
	 *
	 * @Reviewer:
	 */
	public class OnEnterClassCodeClick implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			GWT.runAsync(new SimpleRunAsyncCallback() {

				@Override
				public void onSuccess() {
					setButtonStatus("active");
					if (classCodeTxtBox.getText().trim().equalsIgnoreCase("")
							|| classCodeTxtBox.getText().trim() == null) {
						alertMessageUc = new AlertMessageUc(i18n.GL0061(), new Label(i18n.GL0243()));
						ClickHandler alertHandler = new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								isValid = false;
								setButtonStatus("");
							}
						};
						alertMessageUc.appPopUp.addDomHandler(alertHandler,
								ClickEvent.getType());

						alertMessageUc.okButton.addClickHandler(new ClickHandler() {

							@Override
							public void onClick(ClickEvent event) {
								isValid = false;
								setButtonStatus("");
							}
						});
						return;
					}

					MixpanelUtil.ClickOnStudyNow();
					AppClientFactory
							.getInjector()
							.getClasspageService()
							.v2getClasspageByCode(classCodeTxtBox.getText().trim(),
									new SimpleAsyncCallback<CollectionDo>() {
										@Override
										public void onSuccess(CollectionDo result) {
											setButtonStatus("");
											if (result.getGooruOid() == null) {
												Window.enableScrolling(false);
												AppClientFactory
														.fireEvent(new SetHeaderZIndexEvent(
																98, false));
												alertMessageUc = new AlertMessageUc(
														i18n.GL0061(), new Label(i18n.GL0244()));
												ClickHandler alertHandler = new ClickHandler() {

													@Override
													public void onClick(ClickEvent event) {
														isValid = false;

													}
												};
												alertMessageUc.appPopUp.addDomHandler(
														alertHandler,
														ClickEvent.getType());

												alertMessageUc.okButton
														.addClickHandler(new ClickHandler() {

															@Override
															public void onClick(
																	ClickEvent event) {
																isValid = false;
															}
														});
											} else if (result
													.getCreator()
													.getGooruUId()
													.equalsIgnoreCase(
															AppClientFactory
																	.getGooruUid())) {
												if (AppClientFactory
														.getCurrentPlaceToken().equals(
																PlaceTokens.HOME)) {
													MixpanelUtil
															.Click_Study_LandingPage();
												}

												Map<String, String> params = new HashMap<String, String>();
												params.put("id", result.getGooruOid());
												params.put("pageSize", "10");
												params.put("pageNum", "0");
												params.put("pos", "1");
												AppClientFactory.getPlaceManager()
														.revealPlace(
																PlaceTokens.STUDENT,
																params);
												classCodeTxtBox.setText("");
												if (alertMessageUc != null)
													alertMessageUc.hide();
											} else if (result.getSharing()
													.equalsIgnoreCase("private")) {

												if (result
														.getCreator()
														.getGooruUId()
														.equalsIgnoreCase(
																AppClientFactory
																		.getGooruUid())) {
													if (AppClientFactory
															.getCurrentPlaceToken()
															.equals(PlaceTokens.HOME)) {
														MixpanelUtil
																.Click_Study_LandingPage();
													}

													Map<String, String> params = new HashMap<String, String>();
													params.put("id",
															result.getGooruOid());
													params.put("pageSize", "10");
													params.put("pageNum", "0");
													params.put("pos", "1");
													AppClientFactory
															.getPlaceManager()
															.revealPlace(
																	PlaceTokens.STUDENT,
																	params);
													classCodeTxtBox.setText("");
													if (alertMessageUc != null)
														alertMessageUc.hide();

													StudentAssignmentView
															.setPrivatePage();

												} else if (result.getStatus()
														.equalsIgnoreCase("active")) {
													if (AppClientFactory
															.getCurrentPlaceToken()
															.equals(PlaceTokens.HOME)) {
														MixpanelUtil
																.Click_Study_LandingPage();
													}

													Map<String, String> params = new HashMap<String, String>();
													params.put("id",
															result.getGooruOid());
													params.put("pageSize", "10");
													params.put("pageNum", "0");
													params.put("pos", "1");
													AppClientFactory
															.getPlaceManager()
															.revealPlace(
																	PlaceTokens.STUDENT,
																	params);
													classCodeTxtBox.setText("");
													if (alertMessageUc != null)
														alertMessageUc.hide();

													StudentAssignmentView
															.setPrivatePageActive();

												} else if (result.getStatus()
														.equalsIgnoreCase("pending")) {
													if (AppClientFactory
															.getCurrentPlaceToken()
															.equals(PlaceTokens.HOME)) {
														MixpanelUtil
																.Click_Study_LandingPage();
													}

													Map<String, String> params = new HashMap<String, String>();
													params.put("id",
															result.getGooruOid());
													params.put("pageSize", "10");
													params.put("pageNum", "0");
													params.put("pos", "1");
													AppClientFactory
															.getPlaceManager()
															.revealPlace(
																	PlaceTokens.STUDENT,
																	params);
													classCodeTxtBox.setText("");
													if (alertMessageUc != null)
														alertMessageUc.hide();

													//StudentAssignmentView.setPrivatePagePending();

												} else {
													if (AppClientFactory.isAnonymous()) {
														new SentEmailSuccessVc(i18n.GL1177(),
																i18n.GL1535());
													} else {
														new SentEmailSuccessVc(i18n.GL1177(),
																i18n.GL1535_1());
													}
												}

											} else {
												toClear = true;
												if (AppClientFactory
														.getCurrentPlaceToken().equals(
																PlaceTokens.HOME)) {
													MixpanelUtil
															.Click_Study_LandingPage();
												}

												Map<String, String> params = new HashMap<String, String>();
												params.put("id", result.getGooruOid());
												params.put("pageSize", "10");
												params.put("pageNum", "0");
												params.put("pos", "1");
												AppClientFactory.getPlaceManager()
														.revealPlace(
																PlaceTokens.STUDENT,
																params);
												classCodeTxtBox.setText("");
												if (alertMessageUc != null)
													alertMessageUc.hide();

												if (result
														.getCreator()
														.getGooruUId()
														.equalsIgnoreCase(
																AppClientFactory
																		.getGooruUid())) {
													/*StudentAssignmentView
															.setPublicPage();*/
												} else if (result.getStatus()
														.equalsIgnoreCase("active")) {
													/*StudentAssignmentView
															.setPublicPageActive();*/
												} else {
												/*	StudentAssignmentView
															.setPublicPagePending();*/
												}

											}
											setButtonStatus("");
										}

									});
				}
			});

		}
	}

	/**
	 *
	 * @function getAllClasspages
	 *
	 * @created_date : Aug 15, 2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param offSet
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void getAllClasspages(final String offSet,
			final boolean isClasspageRefreshed, final String deletedClasspageId) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				AppClientFactory
						.getInjector()
						.getClasspageService()
						.v2GetAllClass(String.valueOf(limit), offSet,
								new SimpleAsyncCallback<ClasspageListDo>() {
									@Override
									public void onSuccess(ClasspageListDo result) {
										classpageListDo = result;
										listClasspages(result, isClasspageRefreshed,
												deletedClasspageId);
									}
								});
			}
		});
	}

	/**
	 *
	 * @function listClasspages
	 *
	 * @created_date : Aug 15, 2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param result
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void listClasspages(final ClasspageListDo result,
			final boolean isClasspageRefershed, final String deletedClasspageId) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				isApiCalling = false;
				if (classpageListDo != null) {
					resultSize = classpageListDo.getSearchResults() != null ? classpageListDo
							.getSearchResults().size() : 0;
				} else {
					resultSize = 0;
				}
				if (resultSize > 0) {
					if (toClear) {
						toClear = false;
						classpageList.clear();
					}
					for (int i = 0; i < resultSize; i++) {
						String classpageId = classpageListDo.getSearchResults().get(i).getGooruOid();
						//classpageList.put(classpageId, classpageListDo.getSearchResults().get(i));
						listClasspage.add(classpageId);
					}
				} else {
					// Set no classpage info, if there are not classpages.
					if (toClear) {
					}
					offSet = tmpOffSet;
					Element element = Document.get().getElementById("lblLoading");
					if (element != null) {
						element.removeFromParent();
					}
					if (whileDeleting) {
						whileDeleting = false;

					}
				}
				if (isClasspageRefershed) {
					removeClasspageItem(deletedClasspageId);
				}
			}
		});
	}



	/**
	 *
	 * @function onClickNewClasspage
	 *
	 * @created_date : Aug 21, 2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param event
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	@UiHandler("ancNewClasspage")
	public void onClickNewClasspage(ClickEvent event) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				MixpanelUtil.ClickOnNewClassPage();
				HeaderUc.closeClassContainer();
				newPopup = new NewClassPopupView() {
					
					@Override
					public void createNewClasspage(String title, String grade,	boolean sharing) {
						
						MixpanelUtil.Create_NewClasspage();
						CollectionDo collectionDo = new CollectionDo();
						collectionDo.setTitle(title);
						collectionDo.setCollectionType("classpage");
						AppClientFactory.getInjector().getClasspageService().createClass(title, grade, sharing,	new SimpleAsyncCallback<ClasspageDo>() {

											@Override
											public void onSuccess(ClasspageDo result) {
												final String classpageId = result.getUri();
												String[] uri=result.getUri().split("/");
												String id=  uri[uri.length-1];
												String title = result.getName();
												/*AssignmentDo assignmentDo = new AssignmentDo();
												assignmentDo.setClasspageId(classpageId);

												TaskDo taskDo = new TaskDo();
												taskDo.setTitle(i18n.GL0121());
												taskDo.setTypeName("assignment");
												assignmentDo.setTask(taskDo);

												AttachToDo attachToDo = new AttachToDo();
												attachToDo.setId(classpageId);
												attachToDo.setType("classpage");*/

												//assignmentDo.setAttachTo(attachToDo);
												listClasspage.add(0, id);

												classpageList.put(id, result);
												OpenClasspageEdit(id);
												newPopup.ClosePopup();

												/*AppClientFactory
														.getInjector()
														.getClasspageService()
														.v2CreateAssignment(
																assignmentDo,
																new SimpleAsyncCallback<AssignmentDo>() {

																	@Override
																	public void onSuccess(
																			AssignmentDo result) {
																		// Assig to
																		// classpage.
																		OpenClasspageEdit(classpageId);
																		newPopup.ClosePopup();
																	}
																});*/
											}
										});
					}
				};
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
	private void OpenClasspageEdit(final String gooruOId) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				setClassapageItemSeleted(gooruOId);
				AppClientFactory.getInjector().getClasspageService().v3GetClassById(gooruOId, new SimpleAsyncCallback<ClasspageDo>() {
					@Override
					public void onSuccess(ClasspageDo result) {
						if(result.getUser().getGooruUId().equalsIgnoreCase(AppClientFactory.getLoggedInUser().getGooruUId()))
						{
							Map<String, String> params = new HashMap<String, String>();
							params.put(UrlNavigationTokens.CLASSPAGEID, gooruOId);
						    AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.EDIT_CLASS, params);
						}
						else
						{
							Map<String, String> params = new HashMap<String, String>();
							params.put("id", gooruOId);
							params.put("pageNum", "0");
							params.put("pageSize", "10");
							params.put("pos", "1");
						    AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDENT, params);
						}
					}
				});
			}
		});
	}

	/**
	 *
	 * @function setClassapageItemSeleted
	 *
	 * @created_date : Aug 15, 2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param classpageId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void setClassapageItemSeleted(final String classpageId) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {

				for (int i = 0; i < listClasspage.size(); i++) {
					Element element = Document.get().getElementById(
							listClasspage.get(i));
					if (element != null) {
						element.setClassName(res.css().classpageTitleHeader());
					}
				}
				if (classpageId != null && !classpageId.equalsIgnoreCase("")) {
					Element element = Document.get().getElementById(classpageId);
					if (element != null) {
						element.setClassName(res.css().classpageTitleHeaderActive());
					}
				}
			}
		});
	}



	/**
	 *
	 * @function removeClasspageItem
	 *
	 * @created_date : Aug 15, 2013
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param classpageId
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void removeClasspageItem(final String classpageId) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				String nextClasspageId = null;
				int listCount = listClasspage.size();
				for (int i = 0; i < listClasspage.size(); i++) {
					if (listClasspage.get(i).equalsIgnoreCase(classpageId)) {
						if (i == (listCount - 1)) {
							if ((listCount - 1) > 0) {
								nextClasspageId = listClasspage.get(i - 1);
							} else {
								nextClasspageId = null;
							}

						} else {
							nextClasspageId = listClasspage.get(i + 1);
						}
						listClasspage.remove(i);
						classpageList.remove(classpageId);
					} else {
						nextClasspageId = listClasspage.get(0);
					}
				}
				if (nextClasspageId != null) {
					OpenClasspageEdit(nextClasspageId);
				} else {
					AppClientFactory.getPlaceManager().revealPlace(PlaceTokens.STUDY);
				}
			}
		});
	}
	/**
	 *
	 * @function setButtonStatus
	 *
	 * @created_date : 06-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param status
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	private void setButtonStatus(final String status) {
		GWT.runAsync(new SimpleRunAsyncCallback() {

			@Override
			public void onSuccess() {
				if (status.equalsIgnoreCase("active")) {
					enterLbl.getElement().removeClassName("disabled");
					enterLbl.setEnabled(true);
				} else {
					enterLbl.getElement().addClassName("disabled");
					enterLbl.setEnabled(false);
				}
			}
		});
	}

	@Override
	public HandlerRegistration addMouseOutHandler(MouseOutHandler handler) {
		return addDomHandler(handler, MouseOutEvent.getType());
	}

}
