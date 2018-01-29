package pull.domain;

public class EmailLog {
	private String emailAddress;
	private String topicName;
	private String properName;
	private String topicKey;
	private long timeStamp;


	public EmailLog() {
		super();
		// TODO Auto-generated constructor stub
	}


	public EmailLog(String emailAddress, String topicName, String properName, String topicKey, long timeStamp) {
		super();
		this.emailAddress = emailAddress;
		this.topicName = topicName;
		this.properName = properName;
		this.topicKey = topicKey;
		this.timeStamp = timeStamp;
	}


	@Override
	public String toString() {
		return "EmailLog [emailAddress=" + emailAddress + ", topicName=" + topicName + ", properName="
				+ properName + ", topicKey=" + topicKey + ", timeStamp=" + timeStamp + "]";
	}


	public String getEmailAddress() {
		return emailAddress;
	}


	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}


	public String getTopicName() {
		return topicName;
	}


	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}


	public String getProperName() {
		return properName;
	}

	public void setProperName(String properName) {
		this.properName = properName;
	}

	public String getTopicKey() {
		return topicKey;
	}

	public void setTopicKey(String refKey) {
		this.topicKey = refKey;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

}
