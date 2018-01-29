package pull.domain;

//{"fresh":false,"invited":false,"pending":true,"nuskin":false,"dist":false,"admin":false,"status":"pending",
//"nsId":"US00348526","emailAddress":"mary@appwriter.com","properName":"Pete Carapetyan",
//"topicKey":"-KcyN3Adg0Z6hpDgQRlR","email":"pete.carapetyan@gmail.com","uid":"iwanyTWkgtVT4yJrLgFIgjXheBP2"}

/*
 * Done as an experiment, this is the absolute minimum amount of code required to do a bean, without respect for rules
 */
public class CdProfile {
	public boolean fresh;
	public boolean invited;
	public boolean pending;
	public boolean nuskin;
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
