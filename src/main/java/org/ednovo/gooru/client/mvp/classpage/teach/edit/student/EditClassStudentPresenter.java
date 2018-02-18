
/**
 *
*/
package org.ednovo.gooru.client.mvp.classpage.teach.edit.student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.ClasspageServiceAsync;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.CollaboratorsDo;
import org.ednovo.gooru.application.shared.model.content.StudentsAssociatedListDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.UrlNavigationTokens;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;


/**
 * @fileName : EditClassStudentPresenter.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 03-Jul-2015
 *
 * @Author tumbalam
 *
 * @Reviewer:
 */
public class EditClassStudentPresenter extends PresenterWidget<IsEditClassStudentView> implements EditClassStudentViewUiHandler{

	private SimpleAsyncCallback<Map<String, String>> shareUrlGenerationAsyncCallback;

	private SimpleAsyncCallback<StudentsAssociatedListDo> collabAsyncCallback;

	private SimpleAsyncCallback<StudentsAssociatedListDo> membersActiveAsyncCallback;

	private SimpleAsyncCallback<ArrayList<CollaboratorsDo>> addMembersAsyncCallback;

	private int pageSize = 20;

	private int  activeListPageNum=0;

	@Inject
	private ClasspageServiceAsync classpageServiceAsync;

	StudentsAssociatedListDo studentsAssociatedListDo;

	List<String> emailId;

	@Inject
	public EditClassStudentPresenter(EventBus eventBus,IsEditClassStudentView view){
		super(eventBus, view);
		getView().setUiHandlers(this);
	}



	@Override
	public void onBind() {
		super.onBind();

		setShareUrlGenerationAsyncCallback(new SimpleAsyncCallback<Map<String, String>>() {
			@Override
			public void onSuccess(Map<String, String> shortenUrl) {
				getView().setShortenUrl(shortenUrl);
			}
		});
		setAddMembersAsyncCallback(new SimpleAsyncCallback<ArrayList<CollaboratorsDo>>() {
			@Override
			public void onSuccess(ArrayList<CollaboratorsDo> result) {
				getView().displayInvitationSuccessPopUp(result.size());
				//If the same user email id is invited the result size will be "0" at that time we are enabling the invite button.
				for(int i=0;i<emailId.size();i++){
					CollaboratorsDo collaboratorsDo =new CollaboratorsDo();
					collaboratorsDo.setEmailId(emailId.get(i).toString().replaceAll("\"",""));
					collaboratorsDo.setEmail(emailId.get(i).toString().replaceAll("\"",""));
					result.add(collaboratorsDo);
				}
				if(result.size()==0){
					getView().createAutoSuggestBox();
					getView().getLblPleaseWait().setVisible(false);
					getView().getInviteButton().setEnabled(true);
					getView().getInviteButton().getElement().removeClassName("disabled");
					getView().getInviteButton().setVisible(true);
					//getView().getPendingMembersList();
				}else{
					//Display pending members list.

					getView().displayPendingMembersList(result, true, result.size(),false,true);
				}
			}
		});

	}



	@Override
	public void onReveal() {
		/*String classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
		if(classId != null){
			generateShareLink(classId);
			getActiveMembersListByCollectionId(classId,pageSize*activeListPageNum, pageSize, "active",true,true,false);
		}*/
		/*String pageType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASS_SUBPAGE_VIEW,"");
		if(pageType.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASS_CONTENT_SUB_REPORTS)) {
			getView().setReportView();
		} else {
			getView().setRoasterView();
			String classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
			if(classId != null){
				generateShareLink(classId);
				getActiveMembersListByCollectionId(classId,pageSize*activeListPageNum, pageSize, "active",true,true,false);
			}
		}*/
	}

	@Override
	public void onReset() {
		/*String pageType = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.TEACHER_CLASS_SUBPAGE_VIEW,"");
		if(pageType.equalsIgnoreCase(UrlNavigationTokens.TEACHER_CLASS_CONTENT_SUB_REPORTS)) {
			getView().setReportView();
		} else {
			getView().setRoasterView();
		}*/
	}

	@Override
	protected void onHide() {
		super.onHide();
	}

	public void setClassDetails(ClasspageDo classpageDo) {
		getView().setClassView(classpageDo);
	}

	/**
	 * This method is to get the shareUrlGenerationAsyncCallback
	 */
	public SimpleAsyncCallback<Map<String, String>> getShareUrlGenerationAsyncCallback() {
		return shareUrlGenerationAsyncCallback;
	}

	/**
	 * This method is to set the shareUrlGenerationAsyncCallback
	 */
	public void setShareUrlGenerationAsyncCallback(
			SimpleAsyncCallback<Map<String, String>> shareUrlGenerationAsyncCallback) {
		this.shareUrlGenerationAsyncCallback = shareUrlGenerationAsyncCallback;
	}

	@Override
	public void generateShareLink(String classpageId) {
		try{
			String courseId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID);
			Map<String, String> params = new HashMap<String, String>();
			params.put("type", AppClientFactory.getPlaceManager().getCurrentPlaceRequest().getNameToken());
			if(courseId != null){
				params.put(UrlNavigationTokens.STUDENT_CLASSPAGE_COURSE_ID, courseId);
			}
			AppClientFactory.getInjector().getSearchService().getShortenShareUrl(classpageId, params, getShareUrlGenerationAsyncCallback());
		}catch(Exception e){
			AppClientFactory.printSevereLogger("EditClassStudentPresenter : generateShareLink :"+e.getMessage());
		}
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.student.EditClassStudentViewUiHandler#addStudents(java.lang.String, java.util.List)
	 */
	@Override
	public void addStudents(String classpageId, List<String> emailIds) {
		emailId=emailIds;
		getClasspageServiceAsync().inviteStudentToClass(classpageId, emailIds, getAddMembersAsyncCallback());
	}

	/**
	 * This method is to get the collabAsyncCallback
	 */
	public SimpleAsyncCallback<StudentsAssociatedListDo> getCollabAsyncCallback() {
		return collabAsyncCallback;
	}

	/**
	 * This method is to set the collabAsyncCallback
	 */
	public void setCollabAsyncCallback(SimpleAsyncCallback<StudentsAssociatedListDo> collabAsyncCallback) {
		this.collabAsyncCallback = collabAsyncCallback;
	}

	/**
	 * This method is to get the membersActiveAsyncCallback
	 */
	public SimpleAsyncCallback<StudentsAssociatedListDo> getMembersActiveAsyncCallback() {
		return membersActiveAsyncCallback;
	}

	/**
	 * This method is to set the membersActiveAsyncCallback
	 */
	public void setMembersActiveAsyncCallback(
			SimpleAsyncCallback<StudentsAssociatedListDo> membersActiveAsyncCallback) {
		this.membersActiveAsyncCallback = membersActiveAsyncCallback;
	}

	/**
	 * This method is to get the addMembersAsyncCallback
	 */
	public SimpleAsyncCallback<ArrayList<CollaboratorsDo>> getAddMembersAsyncCallback() {
		return addMembersAsyncCallback;
	}

	/**
	 * This method is to set the addMembersAsyncCallback
	 */
	public void setAddMembersAsyncCallback(SimpleAsyncCallback<ArrayList<CollaboratorsDo>> addMembersAsyncCallback) {
		this.addMembersAsyncCallback = addMembersAsyncCallback;
	}

	/**
	 * This method is to get the classpageServiceAsync
	 */
	public ClasspageServiceAsync getClasspageServiceAsync() {
		return classpageServiceAsync;
	}
	/**
	 * This method is to set the classpageServiceAsync
	 */
	public void setClasspageServiceAsync(ClasspageServiceAsync classpageServiceAsync) {
		this.classpageServiceAsync = classpageServiceAsync;
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.student.EditClassStudentViewUiHandler#getActiveMembersListByCollectionId(java.lang.String, int, int, java.lang.String, boolean, boolean)
	 */
	@Override
	public void getActiveMembersListByCollectionId(String classCode,int offSet, int pageSize, String statusType,final boolean increasePageNum, final boolean getPendingMembers,final boolean isNew) {

		getClasspageServiceAsync().getActiveAssociatedStudentInClassListByCode(classCode, offSet,  pageSize,  statusType, new SimpleAsyncCallback<StudentsAssociatedListDo>() {
			@Override
			public void onSuccess(StudentsAssociatedListDo result) {
				//Display all members in active list.
				studentsAssociatedListDo=result;
				getView().displayActiveMembersList(result.getSearchResult(), isNew, result.getTotalHitCount(),increasePageNum);
				if(getPendingMembers){
					getView().getPendingMembersList();
				}
			}
		});
	}

	@Override
	public void getMembersListByCollectionId(String classCode, int offSet, int pageSize, String statusType,final boolean increasePageNum,final boolean isNew) {

		getClasspageServiceAsync().getAssociatedPendingStudentListByCode(classCode, offSet,  pageSize,  statusType, new SimpleAsyncCallback<StudentsAssociatedListDo>() {

			@Override
			public void onSuccess(StudentsAssociatedListDo result) {
				//Display all members in pending list.
				getView().displayPendingMembersList(result.getSearchResult(),isNew, result.getTotalHitCount(),increasePageNum,false);

			}
		});
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.student.EditClassStudentViewUiHandler#removePendingUserFromCalss(org.ednovo.gooru.application.shared.model.content.ClasspageDo, java.lang.String, int, boolean, org.ednovo.gooru.client.mvp.classpage.teach.edit.student.MembersViewVc)
	 */
	@Override
	public void removePendingUserFromCalss(final ClasspageDo classpageDo, String emailId, final int pendingOffSet, final boolean pendingFlag,final MembersViewVc membersViewVc) {
		AppClientFactory.getInjector().getClasspageService().removePendingStudentFromClass(classpageDo.getClassUid(), classpageDo.isVisibility(), emailId,  new SimpleAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				getView().removePendingUserWidget(membersViewVc,pendingFlag);
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.student.EditClassStudentViewUiHandler#removeActiveUserFromClass(org.ednovo.gooru.application.shared.model.content.ClasspageDo, java.lang.String, int, boolean, org.ednovo.gooru.client.mvp.classpage.teach.edit.student.MembersViewVc)
	 */
	@Override
	public void removeActiveUserFromClass(final ClasspageDo classpageDo,	String emailId, int pendingOffSet, final boolean pendingFlag,final MembersViewVc membersViewVc) {
		AppClientFactory.getInjector().getClasspageService().removeActiveStudentFromClass(classpageDo.getClassUid(), classpageDo.isVisibility(), emailId,  new SimpleAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				getView().removePendingUserWidget(membersViewVc,pendingFlag);
			}
		});
	}



	/* (non-Javadoc)
	 * @see org.ednovo.gooru.client.mvp.classpage.teach.edit.student.EditClassStudentViewUiHandler#getMembersDetails()
	 */
	@Override
	public void getMembersDetails() {
		String classId = AppClientFactory.getPlaceManager().getRequestParameter(UrlNavigationTokens.CLASSPAGEID);
		if(classId != null){
			generateShareLink(classId);
			getActiveMembersListByCollectionId(classId,pageSize*activeListPageNum, pageSize, "active",true,true,false);
		}
	}

}
