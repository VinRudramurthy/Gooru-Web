package org.ednovo.gooru.application.shared.model.content;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.gwt.user.client.rpc.IsSerializable;

@JsonInclude(Include.NON_NULL)
public class customFieldValuesDO implements IsSerializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 private String cfDuration;
	 private String cfHost;
	 private String cfAge;
	 private String cfSchoolLevel;
	 private String cfGooruCourse;
	 private String cfCountryCode;
	 private String cfLanguageCode;
	 private String cfDataType;
	 private String cfAuthor;
	 private String cfCopyrightHolder;
	 private String	cfKeywords;
	 private String cfAccessHazard;
	 private String cfAccessMode;
	 private String cfMediaFeature;
	 private String cfControlFlexibility;
	 private String	cfReadingLevel;
	 private String cfEducationalAlignment;
	 private String cfOER;
	 private String cfAds;
	 private String cfEndUser;
	 private String cfLearningMode;
	 

	public customFieldValuesDO() {
		
	}
	
	 public String getCfEndUser() {
			return cfEndUser;
		}


		public void setCfEndUser(String cfEndUser) {
			this.cfEndUser = cfEndUser;
		}


		public String getCfLearningMode() {
			return cfLearningMode;
		}


		public void setCfLearningMode(String cfLearningMode) {
			this.cfLearningMode = cfLearningMode;
		}

	public String getCfAds() {
		return cfAds;
	}

	public void setCfAds(String cfAds) {
		this.cfAds = cfAds;
	}

	public String getCfOER() {
		return cfOER;
	}

	public void setCfOER(String cfOER) {
		this.cfOER = cfOER;
	}

	public String getCfEducationalAlignment() {
		return cfEducationalAlignment;
	}

	public void setCfEducationalAlignment(String cfEducationalAlignment) {
		this.cfEducationalAlignment = cfEducationalAlignment;
	}

	public String getCfReadingLevel() {
		return cfReadingLevel;
	}

	public void setCfReadingLevel(String cfReadingLevel) {
		this.cfReadingLevel = cfReadingLevel;
	}

	public String getCfControlFlexibility() {
		return cfControlFlexibility;
	}

	public void setCfControlFlexibility(String cfControlFlexibility) {
		this.cfControlFlexibility = cfControlFlexibility;
	}

	
	public String getCfDuration() {
		return cfDuration;
	}

	public void setCfDuration(String cfDuration) {
		this.cfDuration = cfDuration;
	}

	public String getCfHost() {
		return cfHost;
	}

	public void setCfHost(String cfHost) {
		this.cfHost = cfHost;
	}

	public String getCfAge() {
		return cfAge;
	}


	public void setCfAge(String cfAge) {
		this.cfAge = cfAge;
	}


	public String getCfSchoolLevel() {
		return cfSchoolLevel;
	}


	public void setCfSchoolLevel(String cfSchoolLevel) {
		this.cfSchoolLevel = cfSchoolLevel;
	}


	public String getCfCountryCode() {
		return cfCountryCode;
	}


	public void setCfCountryCode(String cfCountryCode) {
		this.cfCountryCode = cfCountryCode;
	}


	public String getCfLanguageCode() {
		return cfLanguageCode;
	}


	public void setCfLanguageCode(String cfLanguageCode) {
		this.cfLanguageCode = cfLanguageCode;
	}


	public String getCfDataType() {
		return cfDataType;
	}


	public void setCfDataType(String cfDataType) {
		this.cfDataType = cfDataType;
	}


	public String getCfAuthor() {
		return cfAuthor;
	}


	public void setCfAuthor(String cfAuthor) {
		this.cfAuthor = cfAuthor;
	}


	public String getCfCopyrightHolder() {
		return cfCopyrightHolder;
	}


	public void setCfCopyrightHolder(String cfCopyrightHolder) {
		this.cfCopyrightHolder = cfCopyrightHolder;
	}


	public String getCfKeywords() {
		return cfKeywords;
	}


	public void setCfKeywords(String cfKeywords) {
		this.cfKeywords = cfKeywords;
	}


	public String getCfAccessHazard() {
		return cfAccessHazard;
	}


	public void setCfAccessHazard(String cfAccessHazard) {
		this.cfAccessHazard = cfAccessHazard;
	}


	public String getCfAccessMode() {
		return cfAccessMode;
	}


	public void setCfAccessMode(String cfAccessMode) {
		this.cfAccessMode = cfAccessMode;
	}


	public String getCfMediaFeature() {
		return cfMediaFeature;
	}


	public void setCfMediaFeature(String cfMediaFeature) {
		this.cfMediaFeature = cfMediaFeature;
	}

	/**
	 * @return the cfGooruCourse
	 */
	public String getCfGooruCourse() {
		return cfGooruCourse;
	}

	/**
	 * @param cfGooruCourse the cfGooruCourse to set
	 */
	public void setCfGooruCourse(String cfGooruCourse) {
		this.cfGooruCourse = cfGooruCourse;
	}

    
	
}
