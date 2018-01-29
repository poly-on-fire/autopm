package pull.domain;

public class EmailStart {
	private String emailAddress;
	private String topicKey;
	private String date;
	private String by;

	public EmailStart() {
		super();
	}

	public EmailStart(String emailAddress, String topicKey, String date, String by) {
		super();
		this.emailAddress = emailAddress;
		this.topicKey = topicKey;
		this.date = date;
		this.by = by;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getTopicKey() {
		return topicKey;
	}

	public void setTopicKey(String topicKey) {
		this.topicKey = topicKey;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

}
