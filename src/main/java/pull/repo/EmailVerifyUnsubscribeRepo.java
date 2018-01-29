package pull.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import pull.domain.TopicSignup;
import pull.service.WriteTopicToProps;
import pull.util.Db;

@Service
public class EmailVerifyUnsubscribeRepo {

	private DatabaseReference emailVerifyUnsubscribeRef;
	private String path = "emailVerifyUnsubscrib";
	private Map<String, TopicSignup> emailVerifyUnsubscribeMap = new HashMap<String, TopicSignup>();

	@Autowired
	private WriteTopicToProps writeTopicToProps;

	public EmailVerifyUnsubscribeRepo() {
		super();
		emailVerifyUnsubscribeRef = Db.coRef(path);
		init();
	}

	private void init() {
		emailVerifyUnsubscribeRef.addChildEventListener(new ChildEventListener() {


			@Override
			public void onCancelled(DatabaseError dataSnapshot) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				TopicSignup emailVerifyUnsubscribe = dataSnapshot.getValue(TopicSignup.class);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void add(TopicSignup emailVerifyUnsubscribe) {
		DatabaseReference newEmailVerifyUnsubscribeRef = emailVerifyUnsubscribeRef.push();
		newEmailVerifyUnsubscribeRef.setValue(emailVerifyUnsubscribe);
	}

	public List<TopicSignup> getList() {
		return (List<TopicSignup>) emailVerifyUnsubscribeMap.values();
	}

	public Map<String, TopicSignup> getMap() {
		return emailVerifyUnsubscribeMap;
	}

	public void update(String key, TopicSignup emailVerifyUnsubscribe) {
		emailVerifyUnsubscribeRef.child(key).setPriority(emailVerifyUnsubscribe);
	}

	public void delete(String key) {
		emailVerifyUnsubscribeRef.child(key).removeValue();
	}
}
