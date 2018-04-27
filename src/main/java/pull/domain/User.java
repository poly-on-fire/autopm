package pull.domain;

import com.google.firebase.database.IgnoreExtraProperties;

//TODO what is this used for? What about the extra commented out properties? etc etc
@IgnoreExtraProperties
public class User {
//	private HashMap<String, Object> topic;
//	private HashMap<String, Object> topicName;
//	private HashMap<String, Object> emailContent;
//	private HashMap<String, Object> emailLookup;

    public String topic;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String topic) {
        this.topic = topic;
    }

	@Override
	public String toString() {
		return "User [topic=" + topic + "]";
	}

}