package pull.domain;

public class TopicSignup {
	private String emailAddress;
	private String topicKey;
	private boolean verified;
	private long timeStamp;
	private String groupName;
	public TopicSignup() {
		super();
	}

	public TopicSignup(String emailAddress, String topicKey, boolean verified, long timeStamp) {
		super();
		this.emailAddress = emailAddress;
		this.topicKey = topicKey;
		this.verified = verified;
		this.timeStamp = timeStamp;
	}
	public TopicSignup(String emailAddress, String topicKey, boolean verified, long timeStamp, String groupName) {
		super();
		this.emailAddress = emailAddress;
		this.topicKey = topicKey;
		this.verified = verified;
		this.timeStamp = timeStamp;
		this.groupName = groupName;
	}


	@Override
	public String toString() {
		return "TopicSignup [emailAddress=" + emailAddress + ", verified=" + verified + ", timeStamp="
				+ timeStamp + "]";
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTopicKey() {
		return topicKey;
	}

	public void setTopicKey(String topicKey) {
		this.topicKey = topicKey;
	}


	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

}
