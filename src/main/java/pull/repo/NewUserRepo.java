package pull.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import pull.domain.TopicLookup;
import pull.domain.TopicName;
import pull.domain.CdProfile;
import pull.domain.NewUser;
import pull.domain.NsId;
import pull.domain.ServiceAction;
import pull.service.TopicDayService;
import pull.util.Db;
import pull.util.DbCodeReference;
import pull.util.SendEmail;

@Service
public class NewUserRepo {
	@Autowired
	SendEmail sendEmail;
	@Value("${EMAIL_FROM}")
	private String emailFrom;

	private DatabaseReference newUserRef;

	public NewUserRepo() {
		super();
		newUserRef = Db.coRef("/newUser");
		init();
	}

	private void init() {
		newUserRef.addChildEventListener(new ChildEventListener() {

			@Override
			public void onCancelled(DatabaseError dataSnapshot) {
			}

			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
				NewUser newUser = dataSnapshot.getValue(NewUser.class);
				if (newUser.done && !newUser.reject) {
					process(newUser);
				} else if (newUser.done && newUser.reject) {
					reject(newUser);
				}
			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
			}
		});
	}

	public void addUser(CdProfile cdProfile) {
		createNewUser(cdProfile);
	}


	private void reject(NewUser newUser) {
		DatabaseReference ref = Db.coRef("/users/" + newUser.uid + "/cdProfile");
		ref.removeValue();
		removeNewUser(newUser);
		try {
			sendEmail.go(emailFrom, "Support at pullModel.com", newUser.email, REJECTION_TEXT,
					"New User Rejected:");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void process(NewUser newUser) {
		updateNewUser(newUser);
		updateNsId(newUser);
		try {
			sendEmail.go(emailFrom, "Support at pullModel.com", newUser.email, ACCEPTANCE_TEXT,
					"Welcome!");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void updateNsId(NewUser newUser) {
		NsId nsId;
		if(newUser.dist){
			nsId= new NsId("dist");
		}else{
			nsId= new NsId("nuskin");
		}
		DatabaseReference nsIdRef = Db.coRef("/nsId/" + newUser.nsId);
		nsIdRef.setValue(nsId);
		
	}

	private void createNewUser(CdProfile cdProfile) {
		NewUser newUser = new NewUser();
		newUser.nsId = cdProfile.nsId;
		newUser.nuskin = false;
		newUser.dist = false;
		newUser.email = cdProfile.emailAddress;
		newUser.properName = cdProfile.properName;
		newUser.uid = cdProfile.uid;
		newUser.photoURL = cdProfile.photoURL;
		completeTopicInfo(newUser, cdProfile.topicKey);
	}

	private void updateNewUser(NewUser newUser) {
		DatabaseReference ref = Db.coRef("/users/" + newUser.uid + "/cdProfile");
		ref.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				CdProfile cdProfile = dataSnapshot.getValue(CdProfile.class);
				cdProfile.fresh = false;
				cdProfile.admin = false;
				cdProfile.invited = false;
				cdProfile.pending = false;
				cdProfile.nuskin = true;
				if (newUser.dist) {
					cdProfile.dist = true;
				}
				ref.setValue(cdProfile);
				removeNewUser(newUser);
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
			}
		});
	}

	private void removeNewUser(NewUser newUser) {
		DatabaseReference newUserRef = Db.coRef("/newUser/" + newUser.uid);
		newUserRef.removeValue();
	}

	private void completeTopicInfo(NewUser newUser, String topicKey) {
		DatabaseReference ref = Db.coRef("/topicLookup/" + topicKey);
		ref.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if (null != dataSnapshot.getValue()) {
					TopicLookup topicLookup = dataSnapshot.getValue(TopicLookup.class);
					newUser.sponsorName = topicLookup.sponsorName;
					newUser.sponsorNsId = topicLookup.sponsorNsId;
					newUserRef.child(newUser.uid).setValue(newUser);
				}
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
			}
		});
	}

	public void modify(CdProfile cdProfile) {
		DatabaseReference cdProfileRef = Db.coRef("/users/" + cdProfile.uid + "/cdProfile");
		cdProfileRef.setValue(cdProfile);
	}

	private final String REJECTION_TEXT = "Your application to clouddancer.co was rejected,"
			+ " probably because we didn't find the Nuskin Identifier in our downline. "
			+ "Please re-apply through the same process, only using a NuSkin identifier "
			+ "that corresponds to one signged up under the same sponsor. \n\n"
			+ "If you believe that this message was sent in error, and you wish support, "
			+ "please reply to this message";
	private final String ACCEPTANCE_TEXT = "Welcome to clouddancer.co! You should now be able to use this app.";

}
