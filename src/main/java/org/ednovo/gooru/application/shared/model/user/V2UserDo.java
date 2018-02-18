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
/**
 *
 */
package org.ednovo.gooru.application.shared.model.user;

import org.ednovo.gooru.application.shared.model.code.CodeDo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @fileName : V2UserDo.java
 *
 * @description :
 *
 *
 * @version : 1.0
 *
 * @date: Sep 27, 2013
 *
 * @Author Gooru Team
 *
 * @Reviewer:
 */
@JsonInclude(Include.NON_NULL)
public class V2UserDo extends ResponseStatusDo implements IsSerializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4651422937333536440L;

	private String createdOn;
	private String restEndPoint;
	private String token;
	private UserDo user;
	private String dateOfBirth;
	private String emailConfirmStatus;
	private String accountType;
	private ProfileV2Do profile;
	private String password;
	private GenderDo gender;
	private String userType;
	private String externalId;
	private CodeDo codeDo;
	//private List<CodeDo> courses;
	private String aboutMe;
	private String grade;
	private Integer isPublisherRequestPending;

	private CityDo city;
	private CountryDo country;
	private ProvinceDo province;

	private UserMetaDo meta;

	public V2UserDo(){}

	public String getEmailConfirmStatus() {
		return emailConfirmStatus;
	}
	public void setEmailConfirmStatus(String emailConfirmStatus) {
		this.emailConfirmStatus = emailConfirmStatus;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	/**
	 * This method is to get the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
	}
	/**
	 * This method is to set the createdOn
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	/**
	 * This method is to get the restEndPoint
	 */
	public String getRestEndPoint() {
		return restEndPoint;
	}
	/**
	 * This method is to set the restEndPoint
	 */
	public void setRestEndPoint(String restEndPoint) {
		this.restEndPoint = restEndPoint;
	}
	/**
	 * This method is to get the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * This method is to set the token
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * This method is to get the user
	 */
	public UserDo getUser() {
		return user;
	}
	/**
	 * This method is to set the user
	 */
	public void setUser(UserDo user) {
		this.user = user;
	}
	/**
	 * This method is to get the user date of birth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * This method is to set the user dateofbirth
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public ProfileV2Do getProfile() {
		return profile;
	}
	public void setProfile(ProfileV2Do profile) {
		this.profile = profile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * This method is to get the gender
	 */
	public GenderDo getGender() {
		return gender;
	}

	/**
	 * This method is to set the gender
	 */
	public void setGender(GenderDo gender) {
		this.gender = gender;
	}

	/**
	 * This method is to get the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * This method is to set the userType
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * This method is to get the externalId
	 */
	public String getExternalId() {
		return externalId;
	}

	/**
	 * This method is to set the externalId
	 */
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	/**
	 * This method is to get the courses
	 *//*
	public List<CodeDo> getCourses() {
		return courses;
	}

	*//**
	 * This method is to set the courses
	 *//*
	public void setCourses(List<CodeDo> courses) {
		this.courses = courses;
	}
*/
	/**
	 * This method is to get the aboutMe
	 */
	public String getAboutMe() {
		return aboutMe;
	}

	/**
	 * This method is to set the aboutMe
	 */
	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	/**
	 * This method is to get the grade
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * This method is to set the grade
	 */
	public void setGrade(String grade) {
		this.grade = grade;
	}

	/**
	 * This method is to get the isPublisherRequestPending
	 */
	public Integer getIsPublisherRequestPending() {
		return isPublisherRequestPending;
	}

	/**
	 * This method is to set the isPublisherRequestPending
	 */
	public void setIsPublisherRequestPending(Integer isPublisherRequestPending) {
		this.isPublisherRequestPending = isPublisherRequestPending;
	}

	/**
	 * This method is to get the city
	 */
	public CityDo getCity() {
		return city;
	}

	/**
	 * This method is to set the city
	 */
	public void setCity(CityDo city) {
		this.city = city;
	}

	/**
	 * This method is to get the country
	 */
	public CountryDo getCountry() {
		return country;
	}

	/**
	 * This method is to set the country
	 */
	public void setCountry(CountryDo country) {
		this.country = country;
	}

	/**
	 * This method is to get the province
	 */
	public ProvinceDo getProvince() {
		return province;
	}

	/**
	 * This method is to set the province
	 */
	public void setProvince(ProvinceDo province) {
		this.province = province;
	}
	/**
	 * This method is to get the meta data
	 */
	public UserMetaDo getMeta() {
		return meta;
	}
	/**
	 * This method is to set the meta data
	 */
	public void setMeta(UserMetaDo meta) {
		this.meta = meta;
	}
	/**
	 * This method is to get the course
	 */
	public CodeDo getCodeDo() {
		return codeDo;
	}
	/**
	 * This method is to set the course
	 */
	public void setCodeDo(CodeDo codeDo) {
		this.codeDo = codeDo;
	}





}
