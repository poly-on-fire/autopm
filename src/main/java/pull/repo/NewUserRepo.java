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
import pull.domain.Profile;
import pull.domain.NewUser;
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

	public void addUser(Profile profile) {
		createNewUser(profile);
	}


	private void reject(NewUser newUser) {
		DatabaseReference ref = Db.coRef("/users/" + newUser.uid + "/profile");
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
		try {
			sendEmail.go(emailFrom, "Support at pullModel.com", newUser.email, ACCEPTANCE_TEXT,
					"Welcome!");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}


	private void createNewUser(Profile profile) {
		NewUser newUser = new NewUser();
		newUser.dist = false;
		//TODO add these back in
//		newUser.email = profile.emailAddress;
//		newUser.properName = profile.properName;
//		newUser.uid = profile.uid;
//		newUser.photoURL = profile.photoURL;
//		completeTopicInfo(newUser, profile.topicKey);
	}

	private void updateNewUser(NewUser newUser) {
		DatabaseReference ref = Db.coRef("/users/" + newUser.uid + "/profile");
		ref.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				Profile profile = dataSnapshot.getValue(Profile.class);
				//TODO add publisher level
//				if (newUser.dist) {
//					profile.dist = true;
//				}
				ref.setValue(profile);
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

	public void modify(Profile profile) {
		//TODO
//		DatabaseReference profileRef = Db.coRef("/users/" + profile.uid + "/profile");
//		profileRef.setValue(profile);
	}

	private final String REJECTION_TEXT = "Your application to clouddancer.co was rejected,";
	private final String ACCEPTANCE_TEXT = "Welcome to clouddancer.co! You should now be able to use this app.";

}
