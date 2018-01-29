package pull.domain;

public class TopicEmails {
	private String emails;
	private String groupName;
	private String mySpecificTopic;

	public TopicEmails(String emails, String groupName, String mySpecificTopic) {
		super();
		this.emails = emails;
		this.groupName = groupName;
		this.mySpecificTopic = mySpecificTopic;
	}

	public TopicEmails() {
		super();
	}
	
	public String getEmails() {
		return emails;
	}

	public void setEmails(String emails) {
		this.emails = emails;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMySpecificTopic() {
		return mySpecificTopic;
	}

	public void setMySpecificTopic(String mySpecificTopic) {
		this.mySpecificTopic = mySpecificTopic;
	}

}
