package cdsf.domain;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Campaign {

	private String campaignType;
	private String name;
	private String category;
	private long timestamp;
	private String curiousHeadline;
	private String curiousPre;
	private String curiousVideo;
	private String curiousPost;
	private String engagedHeadline;
	private String engagedPre;
	private String engagedVideo;
	private String engagedPost;	

	public Campaign() {
		super();
	}


	public Campaign(String campaignType, String name, String category, long timestamp, String curiousHeadline,
			String curiousPre, String curiousVideo, String curiousPost, String engagedHeadline, String engagedPre,
			String engagedVideo, String engagedPost) {
		super();
		this.campaignType = campaignType;
		this.name = name;
		this.category = category;
		this.timestamp = timestamp;
		this.curiousHeadline = curiousHeadline;
		this.curiousPre = curiousPre;
		this.curiousVideo = curiousVideo;
		this.curiousPost = curiousPost;
		this.engagedHeadline = engagedHeadline;
		this.engagedPre = engagedPre;
		this.engagedVideo = engagedVideo;
		this.engagedPost = engagedPost;
	}

	@Override
	public String toString() {
		return "Campaign [campaignType=" + campaignType + ", name=" + name + ", category=" + category + ", timestamp="
				+ timestamp + ", curiousHeadline=" + curiousHeadline + ", curiousPre=" + curiousPre + ", curiousVideo="
				+ curiousVideo + ", curiousPost=" + curiousPost + ", engagedHeadline=" + engagedHeadline
				+ ", engagedPre=" + engagedPre + ", engagedVideo=" + engagedVideo + ", engagedPost=" + engagedPost
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}


	public String getCampaignType() {
		return campaignType;
	}


	public void setCampaignType(String campaignType) {
		this.campaignType = campaignType;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public long getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}


	public String getCuriousHeadline() {
		return curiousHeadline;
	}


	public void setCuriousHeadline(String curiousHeadline) {
		this.curiousHeadline = curiousHeadline;
	}


	public String getCuriousPre() {
		return curiousPre;
	}


	public void setCuriousPre(String curiousPre) {
		this.curiousPre = curiousPre;
	}


	public String getCuriousVideo() {
		return curiousVideo;
	}


	public void setCuriousVideo(String curiousVideo) {
		this.curiousVideo = curiousVideo;
	}


	public String getCuriousPost() {
		return curiousPost;
	}


	public void setCuriousPost(String curiousPost) {
		this.curiousPost = curiousPost;
	}


	public String getEngagedHeadline() {
		return engagedHeadline;
	}


	public void setEngagedHeadline(String engagedHeadline) {
		this.engagedHeadline = engagedHeadline;
	}


	public String getEngagedPre() {
		return engagedPre;
	}


	public void setEngagedPre(String engagedPre) {
		this.engagedPre = engagedPre;
	}


	public String getEngagedVideo() {
		return engagedVideo;
	}


	public void setEngagedVideo(String engagedVideo) {
		this.engagedVideo = engagedVideo;
	}


	public String getEngagedPost() {
		return engagedPost;
	}


	public void setEngagedPost(String engagedPost) {
		this.engagedPost = engagedPost;
	}

	
}
