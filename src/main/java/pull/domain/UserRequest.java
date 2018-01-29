package pull.domain;

public class UserRequest {
	private String uid;
	private String displayName;
	private String nsId;
	private String name;
	private String description;
	private long timestamp;
	private boolean done;
	private String comment;

	public UserRequest(ServiceRequest serviceRequest, String uid, String comment) {
		super();
		this.uid = uid;
		this.name = serviceRequest.name;
		this.description = serviceRequest.description;
		this.timestamp = serviceRequest.timestamp;
		this.comment = comment;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getNsid() {
		return nsId;
	}

	public void setNsid(String nsId) {
		this.nsId = nsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
