package pull.domain;

public class TopicDay {

	private String topicKey;
	private int day;
	private String emailKey;

	public TopicDay() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TopicDay(String topicKey, int day, String emailKey) {
		super();
		this.topicKey = topicKey;
		this.day = day;
		this.emailKey = emailKey;
	}

	@Override
	public String toString() {
		return "TopicDay [topicKey=" + topicKey + ", day=" + day + ", emailKey=" + emailKey + "]";
	}

	public String getTopicKey() {
		return topicKey;
	}

	public void setTopicKey(String topicKey) {
		this.topicKey = topicKey;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public String getEmailKey() {
		return emailKey;
	}

	public void setEmailKey(String emailKey) {
		this.emailKey = emailKey;
	}

}
