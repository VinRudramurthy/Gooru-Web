package org.ednovo.gooru.application.shared.model.user;

import com.google.gwt.user.client.rpc.IsSerializable; 
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserFollowDo implements IsSerializable{
	private static final long serialVersionUID = 6752607500919973286L;
	private String gooruUid;
	private String username;
	private String profileImageUrl;
	private UserSummaryDo summary;
	private int totalHintCount;
	private ArrayList<CustomFieldDo> customFields;
	
	public UserFollowDo(){}

	public String getGooruUid() {
		return gooruUid;
	}

	public void setGooruUid(String gooruUid) {
		this.gooruUid = gooruUid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public UserSummaryDo getSummary() {
		return summary;
	}

	public void setSummary(UserSummaryDo summary) {
		this.summary = summary;
	}

	public int getTotalHintCount() {
		return totalHintCount;
	}

	public void setTotalHintCount(int totalHintCount) {
		this.totalHintCount = totalHintCount;
	}

	/** 
	 * This method is to get the customFields
	 */
	public List<CustomFieldDo> getCustomFields() {
		return customFields;
	}

	/** 
	 * This method is to set the customFields
	 */
	public void setCustomFields(ArrayList<CustomFieldDo> customFields) {
		this.customFields = customFields;
	}
	
}
