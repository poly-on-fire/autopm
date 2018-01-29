package pull.domain;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Topic {

	private String topicType;
	private String name;
	private String category;
	private long timestamp;
	private String aHeadline;
	private String aPre;
	private String aVideo;
	private String aPost;
	private String bHeadline;
	private String bPre;
	private String bVideo;
	private String bPost;	
	private int[] days;

	public Topic() {
		super();
	}


	public Topic(String topicType, String name, String category, long timestamp, String aHeadline,
			String aPre, String aVideo, String aPost, String bHeadline, String bPre,
			String bVideo, String bPost) {
		super();
		this.topicType = topicType;
		this.name = name;
		this.category = category;
		this.timestamp = timestamp;
		this.aHeadline = aHeadline;
		this.aPre = aPre;
		this.aVideo = aVideo;
		this.aPost = aPost;
		this.bHeadline = bHeadline;
		this.bPre = bPre;
		this.bVideo = bVideo;
		this.bPost = bPost;
	}

	@Override
	public String toString() {
		return "Topic [topicType=" + topicType + ", name=" + name + ", category=" + category + ", timestamp="
				+ timestamp + ", aHeadline=" + aHeadline + ", aPre=" + aPre + ", aVideo="
				+ aVideo + ", aPost=" + aPost + ", bHeadline=" + bHeadline
				+ ", bPre=" + bPre + ", bVideo=" + bVideo + ", bPost=" + bPost
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}


	public String getTopicType() {
		return topicType;
	}


	public void setTopicType(String topicType) {
		this.topicType = topicType;
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


	public String getAHeadline() {
		return aHeadline;
	}


	public void setAHeadline(String aHeadline) {
		this.aHeadline = aHeadline;
	}


	public String getAPre() {
		return aPre;
	}


	public void setAPre(String aPre) {
		this.aPre = aPre;
	}


	public String getAVideo() {
		return aVideo;
	}


	public void setAVideo(String aVideo) {
		this.aVideo = aVideo;
	}


	public String getAPost() {
		return aPost;
	}


	public void setAPost(String aPost) {
		this.aPost = aPost;
	}


	public String getBHeadline() {
		return bHeadline;
	}


	public void setBHeadline(String bHeadline) {
		this.bHeadline = bHeadline;
	}


	public String getBPre() {
		return bPre;
	}


	public void setBPre(String bPre) {
		this.bPre = bPre;
	}


	public String getBVideo() {
		return bVideo;
	}


	public void setBVideo(String bVideo) {
		this.bVideo = bVideo;
	}


	public String getBPost() {
		return bPost;
	}


	public void setBPost(String bPost) {
		this.bPost = bPost;
	}


	public int[] getDays() {
		return days;
	}


	public void setDays(int[] days) {
		this.days = days;
	}

	
}
