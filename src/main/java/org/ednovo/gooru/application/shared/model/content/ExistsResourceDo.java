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

import org.ednovo.gooru.shared.util.ResourceImageUtil;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ExistsResourceDo extends ContentDo {

    /**
	 *
	 */
	private static final long serialVersionUID = 7516412172345732958L;
	private String assetURI;
    private String title;
    private String description;
    private String folder;
    private String category;
    private String explanation;
    private ThumbnailDo thumbnails;
    private ResourceTypeDo resourceType;
    private String shortenedUrlStatus;
    private String label;        //Some api's give title in label key
    private String nativeurl;    //Some api's give url in nativeUrl key
    private String gooruOid;
    private String sharing;
    private ResourceFormatDo resourceFormat;

    public String getSharing() {
		return sharing;
	}

	public void setSharing(String sharing) {
		this.sharing = sharing;
	}

	public ExistsResourceDo(){}


    public String getNativeurl() {
        return nativeurl;
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

    private String url;

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
        this.url = url;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

	public String getShortenedUrlStatus() {
		return shortenedUrlStatus;
	}

	public void setShortenedUrlStatus(String shortenedUrlStatus) {
		this.shortenedUrlStatus = shortenedUrlStatus;
	}

	public String getGooruOid() {
		return gooruOid;
	}

	public void setGooruOid(String gooruOid) {
		this.gooruOid = gooruOid;
	}

	/**
	 * This method is to get the resourceFormat
	 */
	public ResourceFormatDo getResourceFormat() {
		return resourceFormat;
	}

	/**
	 * This method is to set the resourceFormat
	 */
	public void setResourceFormat(ResourceFormatDo resourceFormat) {
		this.resourceFormat = resourceFormat;
	}



}
