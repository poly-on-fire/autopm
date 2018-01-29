package pull.domain;

public class EmailMeta {
	private String topicKey;
	private String topicName;
	private String topicType;
	private String topicExtract;
	private int day;
	private String subject;
	private String subjectExtract;
	private String contentExtract;
	private String productCategory;

	public EmailMeta() {
		super();
	}


	public EmailMeta(String topicKey, String topicName, String topicType, String topicExtract, int day,
			String subject, String subjectExtract, String contentExtract, String productCategory) {
		super();
		this.topicKey = topicKey;
		this.topicName = topicName;
		this.topicType = topicType;
		this.topicExtract = topicExtract;
		this.day = day;
		this.subject = subject;
		this.subjectExtract = subjectExtract;
		this.contentExtract = contentExtract;
		this.productCategory = productCategory;
	}


	@Override
	public String toString() {
		return "EmailMeta [topicKey=" + topicKey + ", topicName=" + topicName + ", topicType="
				+ topicType + ", topicExtract=" + topicExtract + ", day=" + day + ", subject=" + subject
				+ ", subjectExtract=" + subjectExtract + ", contentExtract=" + contentExtract + ", productCategory="
				+ productCategory + "]";
	}


	public String getSubjectExtract() {
		return subjectExtract;
	}


	public void setSubjectExtract(String subjectExtract) {
		this.subjectExtract = subjectExtract;
	}


	public String getTopicKey() {
		return topicKey;
	}

	public void setTopicKey(String topicKey) {
		this.topicKey = topicKey;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public String getTopicType() {
		return topicType;
	}

	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}

	public String getTopicExtract() {
		return topicExtract;
	}

	public void setTopicExtract(String topicExtract) {
		this.topicExtract = topicExtract;
	}

	public String getContentExtract() {
		return contentExtract;
	}

	public void setContentExtract(String contentExtract) {
		this.contentExtract = contentExtract;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

}