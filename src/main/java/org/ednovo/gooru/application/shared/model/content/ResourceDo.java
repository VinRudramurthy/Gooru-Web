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
package org.ednovo.gooru.application.shared.model.content;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.ednovo.gooru.application.shared.model.user.UserDo;
import org.ednovo.gooru.shared.util.ResourceImageUtil;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ResourceDo extends ContentDo {
    private static final long serialVersionUID = 3421728485329612613L;
    private String assetURI;
    private String title;
    private String description;
    private String folder;
    private String category;
    private String thumbnail;
    private String explanation;
    private ThumbnailDo thumbnails;
    private ResourceTypeDo resourceType;
    private ResourceSourceDo resourceSource;
	private String views;
    private Integer votesUp;
    private LicenseDo license;
    private String grade;
    private String mediaType;
    private List<StandardFo> skills;
	private String gooruOid;

    private String encodedUrl;

    private ResourceFormatDo resourceFormat;


    private ArrayList<ResourceTagsDo> resourceTags;

    public ResourceDo(){}

    private TreeSet<QuestionAnswerDo> answers;

    private ArrayList<AssetsDo> assets;

    private TrackActivityDo trackActivity;

	private TreeSet<QuestionHintsDo> hints;
    private Integer type;
    private String label;        //Some api's give title in label key
    private String nativeurl;    //Some api's give url in nativeUrl key
    private String id;
    private String typeName;

    private String goals;

    private Boolean hasFrameBreaker;

    private String questionText;

    private Integer userRating;

    private Date createdOn;

	private SearchRatingsDo ratings;

    /** need to change these after api is finalized  **/

	private String  educationalRole;

	private String interactivityType;

	private String gooruSubject;

	private String contributor;

	private List<String> keywords;

	private List<String> readinglevel;

	private List<String> gooruCourse;

	private String mobilefriendlyness;

	private List<String> acessmode;

	private List<ListValuesDo> mediaFeature;

	private List<String> accessibilityAPI;

	private List<String> aggregator;

	private List<String> publisher;

	private List<String> host;

	private customFieldValuesDO customFieldValues;

	private UserDo user;

	private String url;

	private String hlType;
	private boolean singleCorrectAnswer;
	private ArrayList<checkboxSelectedDo> momentsOfLearning;
	private ArrayList<checkboxSelectedDo> educationalUse;
	private List<checkboxSelectedDo> depthOfKnowledge;
	private List<checkboxSelectedDo> accessHazard;



	public List<String> getPublisher() {
		return publisher;
	}
	public void setPublisher(List<String> publisher) {
		this.publisher = publisher;
	}

	public List<String> getAggregator() {
		return aggregator;
	}
	public void setAggregator(List<String> aggregator) {
		this.aggregator = aggregator;
	}
	public customFieldValuesDO getCustomFieldValues() {
		return customFieldValues;
	}
	public void setCustomFieldValues(customFieldValuesDO customFieldValues) {
		this.customFieldValues = customFieldValues;
	}
	public List<String> getAccessibilityAPI() {
		return accessibilityAPI;
	}
	public void setAccessibilityAPI(List<String> accessibilityAPI) {
		this.accessibilityAPI = accessibilityAPI;
	}

	public String getHasadaptation() {
		return hasadaptation;
	}
	public void setHasadaptation(String hasadaptation) {
		this.hasadaptation = hasadaptation;
	}
	public String getIsadaptation() {
		return isadaptation;
	}
	public void setIsadaptation(String isadaptation) {
		this.isadaptation = isadaptation;
	}

	private String hasadaptation;

	private String isadaptation;


	public List<ListValuesDo> getMediaFeature() {
		return mediaFeature;
	}
	public void setMediaFeature(List<ListValuesDo> mediaFeature) {
		this.mediaFeature = mediaFeature;
	}
	public List<String> getAcessmode() {
		return acessmode;
	}
	public void setAcessmode(List<String> acessmode) {
		this.acessmode = acessmode;
	}
	public String getMobilefriendlyness() {
		return mobilefriendlyness;
	}
	public void setMobilefriendlyness(String mobilefriendlyness) {
		this.mobilefriendlyness = mobilefriendlyness;
	}
	public List<String> getGooruCourse() {
		return gooruCourse;
	}
	public void setGooruCourse(List<String> gooruCourse) {
		this.gooruCourse = gooruCourse;
	}
	public List<String> getReadinglevel() {
		return readinglevel;
	}
	public void setReadinglevel(List<String> readinglevel) {
		this.readinglevel = readinglevel;
	}
	public List<String> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<String> keywords) {
		this.keywords = keywords;
	}
	public String getContributor() {
		return contributor;
	}
	public void setContributor(String contributor) {
		this.contributor = contributor;
	}
	public String getGooruSubject() {
		return gooruSubject;
	}
	public void setGooruSubject(String gooruSubject) {
		this.gooruSubject = gooruSubject;
	}

	public String getInteractivityType() {
		return interactivityType;
	}
	public void setInteractivityType(String interactivityType) {
		this.interactivityType = interactivityType;
	}
	public String getEducationalRole() {
		return educationalRole;
	}
	public void setEducationalRole(String educationalRole) {
		this.educationalRole = educationalRole;
	}

	/** need to change the above **/





	public List<checkboxSelectedDo> getAccessHazard() {
		return accessHazard;
	}
	public void setAccessHazard(List<checkboxSelectedDo> accessHazard) {
		this.accessHazard = accessHazard;
	}
	/**
	 * This method is to get the encodedUrl
	 */
	public String getEncodedUrl() {
		return encodedUrl;
	}
	/**
	 * This method is to set the encodedUrl
	 */
	public void setEncodedUrl(String encodedUrl) {
		this.encodedUrl = encodedUrl;
	}


    /**
	 * This method is to get the goals
	 */
	public String getGoals() {
		return goals;
	}

	/**
	 * This method is to set the goals
	 */
	public void setGoals(String goals) {
		this.goals = goals;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNativeurl() {
        return nativeurl;
    }

    public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public void setNativeurl(String nativeurl) {
        this.nativeurl = nativeurl;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ResourceTypeDo getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceTypeDo resourceType) {
        this.resourceType = resourceType;
    }

    public String getAssetURI() {
        return assetURI;
    }

    public void setAssetURI(String assertURI) {
        this.assetURI = assertURI;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public void setThumbnails(ThumbnailDo thumbnails) {
        this.thumbnails = thumbnails;
    }

    public ThumbnailDo getThumbnails() {
        return thumbnails;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        if (getCategory() != null && getCategory().equalsIgnoreCase("Video")) {
            return ResourceImageUtil.youtubeImageLink(ResourceImageUtil.getYoutubeVideoId(getUrl()));
        } else if (getThumbnails() != null) {
            return getThumbnails().getUrl();
        }
        return null;
    }

    public void setUrl(String url) {
    	if(url==null) {
    		url = "";
    	}
        this.url = url;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    /*public ArrayList<QuestionAnswerDo> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<QuestionAnswerDo> answers) {
        this.answers = answers;
    }*/

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    /*public ArrayList<QuestionHintsDo> getHints() {
        return hints;
    }

    public void setHints(ArrayList<QuestionHintsDo> hints) {
        this.hints = hints;
    }*/

    public TreeSet<QuestionAnswerDo> getAnswers() {
        return answers;
    }

    public void setAnswers(TreeSet<QuestionAnswerDo> answers) {
        this.answers = answers;
    }

    public TreeSet<QuestionHintsDo> getHints() {
        return hints;
    }

    public void setHints(TreeSet<QuestionHintsDo> hints) {
        this.hints = hints;
    }

	public ArrayList<AssetsDo> getAssets() {
		return assets;
	}

	public void setAssets(ArrayList<AssetsDo> assets) {
		this.assets = assets;
	}

	/**
	 * This method is to get the trackActivity
	 */
	public TrackActivityDo getTrackActivity() {
		return trackActivity;
	}

	/**
	 * This method is to set the trackActivity
	 */
	public void setTrackActivity(TrackActivityDo trackActivity) {
		this.trackActivity = trackActivity;

	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public Integer getVotesUp() {
		return votesUp;
	}

	public void setVotesUp(Integer votesUp) {
		this.votesUp = votesUp;
	}

	public LicenseDo getLicense() {
		return license;
	}

	public void setLicense(LicenseDo license) {
		this.license = license;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
   public ResourceSourceDo getResourceSource() {
		return resourceSource;
	}

	public void setResourceSource(ResourceSourceDo resourceSource) {
		this.resourceSource = resourceSource;
	}

	public Boolean getHasFrameBreaker() {
		return hasFrameBreaker;
	}

	public void setHasFrameBreaker(Boolean hasFrameBreaker) {
		this.hasFrameBreaker = hasFrameBreaker;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public Integer getUserRating() {
		return userRating;
	}

	public void setUserRating(Integer userRating) {
		this.userRating = userRating;
	}
	public ResourceFormatDo getResourceFormat() {
		return resourceFormat;
	}
	public void setResourceFormat(ResourceFormatDo resourceFormat) {
		this.resourceFormat = resourceFormat;
	}
	//These are the setters and getters to get created date
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public ArrayList<checkboxSelectedDo> getMomentsOfLearning() {
		return momentsOfLearning;
	}
	public void setMomentsOfLearning(ArrayList<checkboxSelectedDo> momentsOfLearning) {
		this.momentsOfLearning = momentsOfLearning;
	}
	public ArrayList<checkboxSelectedDo> getEducationalUse() {
		return educationalUse;
	}
	public void setEducationalUse(ArrayList<checkboxSelectedDo> educationalUse) {
		this.educationalUse = educationalUse;
	}
	public List<checkboxSelectedDo> getDepthOfKnowledge() {
		return depthOfKnowledge;
	}
	public void setDepthOfKnowledge(List<checkboxSelectedDo> depthOfKnowledge) {
		this.depthOfKnowledge = depthOfKnowledge;
	}

	public UserDo getUser() {
		return user;
	}
	public void setUser(UserDo user) {
		this.user = user;
	}
	public SearchRatingsDo getRatings() {
		return ratings;
	}
	public void setRatings(SearchRatingsDo ratings) {
		this.ratings = ratings;

	}
	public List<String> getHost() {
		return host;
	}
	public void setHost(List<String> host) {
		this.host = host;
	}
	public ArrayList<ResourceTagsDo> getResourceTags() {
		return resourceTags;
	}
	public void setResourceTags(ArrayList<ResourceTagsDo> resourceTags) {
		this.resourceTags = resourceTags;
	}
	public List<StandardFo> getSkills() {
		return skills;
	}
	public void setSkills(List<StandardFo> skills) {
		this.skills = skills;
	}
	public String getGooruOid() {
		return gooruOid;
	}
	public void setGooruOid(String gooruOid) {
		this.gooruOid = gooruOid;
	}

	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	/**
	 * @return the typeName
	 */
	public String getTypeName() {
		return typeName;
	}
	/**
	 * @param typeName the typeName to set
	 */
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getHlType() {
		return hlType;
	}
	public boolean isSingleCorrectAnswer() {
		return singleCorrectAnswer;
	}
	public void setHlType(String hlType) {
		this.hlType = hlType;
	}
	public void setSingleCorrectAnswer(boolean singleCorrectAnswer) {
		this.singleCorrectAnswer = singleCorrectAnswer;
	}


}