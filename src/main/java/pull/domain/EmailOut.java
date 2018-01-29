package pull.domain;

public class EmailOut {
	private String emailAddress;
	private String emailKey;
	private String topicKey;
	private String hashCode;
	private String date;

	public EmailOut() {
		super();
	}

	public EmailOut(String emailAddress, String emailKey, String topicKey, String hashCode, String date) {
		super();
		this.emailAddress = emailAddress;
		this.emailKey = emailKey;
		this.topicKey = topicKey;
		this.hashCode = hashCode;
		this.date = date;
	}

	@Override
	public String toString() {
		return "EmailOut [emailAddress=" + emailAddress + ", emailKey=" + emailKey + ", topicKey=" + topicKey
				+ ", hashCode=" + hashCode + ", date=" + date + "]";
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getEmailKey() {
		return emailKey;
	}

	public void setEmailKey(String emailKey) {
		this.emailKey = emailKey;
	}

	public String getTopicKey() {
		return topicKey;
	}

	public void setTopicKey(String topicKey) {
		this.topicKey = topicKey;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	


}
