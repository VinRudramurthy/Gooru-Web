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
package org.ednovo.gooru.application.shared.model.user;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.google.gwt.user.client.rpc.IsSerializable;

import org.ednovo.gooru.application.shared.model.code.ProfileCodeDo;

public class ProfileDo implements IsSerializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 6752607500919973286L;
	private UserDo user;
	private String grade;
	private String subject;
	private Integer birthDate;
	private Integer birthMonth;
	private Integer birthYear;
	private String aboutMe;
	private String teachingExperience;
	private String teachingIn;
	private String teachingMethodology;
	private String highestDegree;
	private String postGraduation;
	private String graduation;
	private String highSchool;
	private byte[] pictureBlob;
	private byte[] thumbnailBlob;
	private Integer isPublisherRequestPending;
	private String pictureFormat;
	private Date dateOfBirth;
	private Date childDateOfBirth;
	private String website;
	private String facebook;
	private String twitter;
	private String userType;
	private CityDo city;
	private CountryDo country;
	private ProvinceDo province;
	private String school;
	private String profileId;
	private String externalId;
	private GenderDo gender;


	private Set<ProfileCodeDo> courses = new HashSet<ProfileCodeDo>();

	public ProfileDo(){}

	public UserDo getUser() {
		return user;
	}
	public void setUser(UserDo user) {
		this.user = user;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public Integer getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Integer birthDate) {
		this.birthDate = birthDate;
	}
	public Integer getBirthMonth() {
		return birthMonth;
	}
	public void setBirthMonth(Integer birthMonth) {
		this.birthMonth = birthMonth;
	}
	public Integer getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}
	public String getAboutMe() {
		return aboutMe;
	}
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public String getTeachingExperience() {
		return teachingExperience;
	}
	public void setTeachingExperience(String teachingExperience) {
		this.teachingExperience = teachingExperience;
	}
	public String getTeachingIn() {
		return teachingIn;
	}
	public void setTeachingIn(String teachingIn) {
		this.teachingIn = teachingIn;
	}
	public String getTeachingMethodology() {
		return teachingMethodology;
	}
	public void setTeachingMethodology(String teachingMethodology) {
		this.teachingMethodology = teachingMethodology;
	}
	public String getHighestDegree() {
		return highestDegree;
	}
	public void setHighestDegree(String highestDegree) {
		this.highestDegree = highestDegree;
	}
	public String getPostGraduation() {
		return postGraduation;
	}
	public void setPostGraduation(String postGraduation) {
		this.postGraduation = postGraduation;
	}
	public String getGraduation() {
		return graduation;
	}
	public void setGraduation(String graduation) {
		this.graduation = graduation;
	}
	public String getHighSchool() {
		return highSchool;
	}
	public void setHighSchool(String highSchool) {
		this.highSchool = highSchool;
	}
	public byte[] getPictureBlob() {
		return pictureBlob;
	}
	public void setPictureBlob(byte[] pictureBlob) {
		this.pictureBlob = pictureBlob;
	}
	public byte[] getThumbnailBlob() {
		return thumbnailBlob;
	}
	public void setThumbnailBlob(byte[] thumbnailBlob) {
		this.thumbnailBlob = thumbnailBlob;
	}
	public Integer getIsPublisherRequestPending() {
		return isPublisherRequestPending;
	}
	public void setIsPublisherRequestPending(Integer isPublisherRequestPending) {
		this.isPublisherRequestPending = isPublisherRequestPending;
	}
	public String getPictureFormat() {
		return pictureFormat;
	}
	public void setPictureFormat(String pictureFormat) {
		this.pictureFormat = pictureFormat;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public Date getChildDateOfBirth() {
		return childDateOfBirth;
	}
	public void setChildDateOfBirth(Date childDateOfBirth) {
		this.childDateOfBirth = childDateOfBirth;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public CityDo getCity() {
		return city;
	}
	public void setCity(CityDo city) {
		this.city = city;
	}
	public CountryDo getCountry() {
		return country;
	}
	public void setCountry(CountryDo country) {
		this.country = country;
	}
	public ProvinceDo getProvince() {
		return province;
	}
	public void setProvince(ProvinceDo province) {
		this.province = province;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getProfileId() {
		return profileId;
	}
	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public GenderDo getGender() {
		return gender;
	}
	public void setGender(GenderDo gender) {
		this.gender = gender;
	}
	public Set<ProfileCodeDo> getCourses() {
		return courses;
	}
	public void setCourses(Set<ProfileCodeDo> courses) {
		this.courses = courses;
	}



}
