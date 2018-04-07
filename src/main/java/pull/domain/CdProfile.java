package pull.domain;

/*
 * Done as an experiment, this is the absolute minimum amount of code required to do a bean, without respect for rules
 */
public class CdProfile {
	public boolean fresh;
	public boolean invited;
	public boolean pending;
	public boolean dist;
	public boolean admin;
	public String email;
	public String emailAddress;
	public String uid;
	public String displayName;
	public String photoURL;
	public String nsId;
	public String properName;
	public String topicKey;
	public String uplineNsId;
	public String status;
	public String nsLevel;
	public int joinDate;

	public CdProfile() {
		super();
	}

}
