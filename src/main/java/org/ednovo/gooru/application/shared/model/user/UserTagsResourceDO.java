package org.ednovo.gooru.application.shared.model.user;

import com.google.gwt.user.client.rpc.IsSerializable; 
import java.util.List;

import org.ednovo.gooru.application.shared.model.content.ResourceFormatDo;
import org.ednovo.gooru.application.shared.model.content.SearchRatingsDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserTagsResourceDO implements IsSerializable{
	private static final long serialVersionUID = 6752607500919973286L;
	public UserTagsResourceDO(){}
	private String gooruOid;
	private String title;
	private String thumbnails;
	private String type;
	private ResourceFormatDo resourceFormat;
	private SearchRatingsDo ratings;
	private List<String> aggregator;

	private List<String> publisher;
	private int totalHintCount;

	public String getGooruOid() {
		return gooruOid;
	}
	public void setGooruOid(String gooruOid) {
		this.gooruOid = gooruOid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumbnails() {
		return thumbnails;
	}
	public void setThumbnails(String thumbnails) {
		this.thumbnails = thumbnails;
	}
	public ResourceFormatDo getResourceFormat() {
		return resourceFormat;
	}
	public void setResourceFormat(ResourceFormatDo resourceFormat) {
		this.resourceFormat = resourceFormat;
	}
	public SearchRatingsDo getRatings() {
		return ratings;
	}
	public void setRatings(SearchRatingsDo ratings) {
		this.ratings = ratings;
	}
	public List<String> getAggregator() {
		return aggregator;
	}
	public void setAggregator(List<String> aggregator) {
		this.aggregator = aggregator;
	}
	public List<String> getPublisher() {
		return publisher;
	}
	public void setPublisher(List<String> publisher) {
		this.publisher = publisher;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getTotalHintCount() {
		return totalHintCount;
	}
	public void setTotalHintCount(int totalHintCount) {
		this.totalHintCount = totalHintCount;
	}


}
