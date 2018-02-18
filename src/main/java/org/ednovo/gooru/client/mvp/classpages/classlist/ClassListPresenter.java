
/**
 *
*/
package org.ednovo.gooru.client.mvp.classpages.classlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ednovo.gooru.application.client.gin.AppClientFactory;
import org.ednovo.gooru.application.client.service.ClasspageServiceAsync;
import org.ednovo.gooru.application.shared.model.content.ClasspageDo;
import org.ednovo.gooru.application.shared.model.content.CollaboratorsDo;
import org.ednovo.gooru.application.shared.model.content.CollectionDo;
import org.ednovo.gooru.application.shared.model.content.StudentsAssociatedListDo;
import org.ednovo.gooru.client.SimpleAsyncCallback;
import org.ednovo.gooru.client.mvp.home.event.HeaderTabType;
import org.ednovo.gooru.client.mvp.home.event.HomeEvent;

import com.google.gwt.event.shared.EventBus;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.PresenterWidget;


/**
 * @fileName : ClassListPresenter.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: 13-Mar-2014
 *
 * @Author Gooru Team
 *
 * @Reviewer: Gooru Team
 */
public class ClassListPresenter extends PresenterWidget<IsClassListView> implements ClassListUiHandlers{


	private SimpleAsyncCallback<StudentsAssociatedListDo> collabAsyncCallback;

	private SimpleAsyncCallback<StudentsAssociatedListDo> membersActiveAsyncCallback;

	private SimpleAsyncCallback<ArrayList<CollaboratorsDo>> addMembersAsyncCallback;


	@Inject
	private ClasspageServiceAsync classpageServiceAsync;

	/**
	 * @param eventBus
	 * @param view
	 */
	@Inject
	public ClassListPresenter(EventBus eventBus, IsClassListView view) {
		super(eventBus, view);
		getView().setUiHandlers(this);
	}
	public void setClassPageDo(ClasspageDo classpageDo){
		getView().setClassPageDo(classpageDo);
	}
	public void setShareUrl(Map<String, String> shortenUrl){
		getView().setShareUrl(shortenUrl);
	}

	@Override
	public void onReveal() {
		super.onReveal();
		AppClientFactory.fireEvent(new HomeEvent(HeaderTabType.TEACH));
	}

	@Override
	protected void onBind() {
		setAddMembersAsyncCallback(new SimpleAsyncCallback<ArrayList<CollaboratorsDo>>() {
			@Override
			public void onSuccess(ArrayList<CollaboratorsDo> result) {
				getView().displayInvitationSuccessPopUp(result.size());
				//If the same user email id is invited the result size will be "0" at that time we are enabling the invite button.
				if(result.size()==0){
					getView().createAutoSuggestBox();
					getView().getLblPleaseWait().setVisible(false);
					getView().getInviteButton().setEnabled(true);
					getView().getInviteButton().getElement().removeClassName("disabled");
					getView().getInviteButton().setVisible(true);
				}else{
					//Display pending members list.
					getView().displayPendingMembersList(result, true, result.size(),false,false);
				}
			}
		});
	}
	@Override
	public void updateClassPageInfo(String classPageId,String collectionType, String title, String shareType){
		AppClientFactory.getInjector().getClasspageService().v2UpdateClassPageByid(classPageId,collectionType, title, shareType, new SimpleAsyncCallback<CollectionDo>() {
			@Override
			public void onSuccess(CollectionDo result) {
			}
		});

	}

	@Override
	public void removeUserFromClass(final ClasspageDo classpageDo, String emailId, final int pendingOffSet, final boolean pendingFlag,final MembersViewVc membersViewVc){
		AppClientFactory.getInjector().getClasspageService().removeStudentFromClass(classpageDo.getClasspageCode(), classpageDo.getSharing(), emailId,  new SimpleAsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				getView().removePendiUserWidget(membersViewVc,pendingFlag);
			}
		});
	}
	/**
	 *
	 * @function insertUserAfterDeletionForPending
	 *
	 * @created_date : 07-Dec-2014
	 *
	 * @description
	 *
	 *
	 * @parm(s) : @param gooruOid
	 * @parm(s) : @param offSet
	 * @parm(s) : @param pageSize
	 * @parm(s) : @param statusType
	 * @parm(s) : @param pendingFlag
	 *
	 * @return : void
	 *
	 * @throws : <Mentioned if any exceptions>
	 *
	 *
	 *
	 *
	 */
	public void insertUserAfterDeletionForPending(final String gooruOid,final int offSet, int pageSize,String statusType, final boolean pendingFlag){
        this.classpageServiceAsync.getAssociatedStudentListByCode(gooruOid, offSet, 1, statusType, new SimpleAsyncCallback<StudentsAssociatedListDo>() {
                @Override
                public void onSuccess(StudentsAssociatedListDo result) {
                	if(result.getSearchResults().size()>0){
                		if(pendingFlag)
                		{
                		getView().insertPendingUserAfterDeletion(result.getSearchResults().get(0), false, result.getTotalHitCount(),0,false);
                		}
                		else
                		{
                		getView().insertActiveUserAfterDeletion(result.getSearchResults().get(0), false, result.getTotalHitCount(),0);
                		}
                	}
                }
        });
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
	 * @param collectionId
	 *
	 */
	@Override
	public void getMembersListByCollectionId(String classCode, int offSet, int pageSize, String statusType,final boolean increasePageNum) {
		getClasspageServiceAsync().getAssociatedStudentListByCode(classCode, offSet,  pageSize,  statusType, new SimpleAsyncCallback<StudentsAssociatedListDo>() {

			@Override
			public void onSuccess(StudentsAssociatedListDo result) {
				//Display all members in pending list.
				getView().displayPendingMembersList(result.getSearchResults(), false, result.getTotalHitCount(),increasePageNum,false);

			}
		});
	}

	@Override
	public void addMembers(String classpageId, List<String> emailIds){
		getClasspageServiceAsync().inviteStudentToClass(classpageId, emailIds, getAddMembersAsyncCallback());
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
	 * @see org.ednovo.gooru.client.mvp.classpages.classlist.ClassListUiHandlers#getActiveMembersListByCollectionId(java.lang.String, int, int, java.lang.String)
	 */
	@Override
	public void getActiveMembersListByCollectionId(String classCode, int offSet, int pageSize, String statusType,final boolean increasePageNum,final boolean getPendingMembers) {
		getClasspageServiceAsync().getActiveAssociatedStudentListByCode(classCode, offSet,  pageSize,  statusType, new SimpleAsyncCallback<StudentsAssociatedListDo>() {
			@Override
			public void onSuccess(StudentsAssociatedListDo result) {
				//Display all members in active list.
				getView().displayActiveMembersList(result.getSearchResults(), false, result.getTotalHitCount(),increasePageNum);
				if(getPendingMembers){
					getView().getPendingMembersList();
				}
			}
		});
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

}
