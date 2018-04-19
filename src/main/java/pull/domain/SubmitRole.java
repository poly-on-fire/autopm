package pull.domain;

public class SubmitRole {
	public boolean userEmailVerified;
	public boolean processed;
	public String uid;
	public String properName;
	public String userDisplayName;
	public String userEmail;
	public String emailAddress;
	public String topicKey;
	public String userPhotoURL;
	public long timestamp;
	public String userProvider;
	public String date;
	public Role role;

	@Override
	public String toString() {
		return "SubmitRole{" +
				"uid=" + uid +
				"userEmailVerified=" + userEmailVerified +
				", properName='" + properName + '\'' +
				", userDisplayName='" + userDisplayName + '\'' +
				", userEmail='" + userEmail + '\'' +
				", emailAddress='" + emailAddress + '\'' +
				", topicKey='" + topicKey + '\'' +
				", userPhotoURL='" + userPhotoURL + '\'' +
				", timestamp=" + timestamp +
				", userProvider='" + userProvider + '\'' +
				", date='" + date + '\'' +
				", role=" + role.toString() +
				'}';
	}
}
