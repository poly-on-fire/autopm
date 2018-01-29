package pull.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import pull.domain.EmailStart;
import pull.service.TopicDayService;
import pull.util.Db;


@Service
public class EmailStartRepo {

	DatabaseReference emailStartRef;
	String path = "emailStart";

	@Autowired
	private TopicDayService topicDayService;

	public EmailStartRepo() {
		super();
		emailStartRef = Db.coRef(path);
		init();
	}

	private void init() {
		emailStartRef.addChildEventListener(new ChildEventListener() {

			@Override
			public void onCancelled(DatabaseError dataSnapshot) {
			}

			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				addEmailTopicDates(dataSnapshot);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
			}
		});
	}

	private void addEmailTopicDates(DataSnapshot dataSnapshot) {
		EmailStart emailStart = dataSnapshot.getValue(EmailStart.class);
		String topicKey = emailStart.getTopicKey();
		topicDayService.upsertEmailStart(emailStart);
	}

	public void add(EmailStart emailStart) {
		DatabaseReference newEmailTopicDateByRef = emailStartRef.push();
		newEmailTopicDateByRef.setValue(emailStart);
	}

//	public void add(String emailAddress, String topicKey, String date, String by) {
//		EmailStart emailStart = new EmailStart(emailAddress, topicKey, date, by);
//	}


//	public void update(String key, EmailStart emailStart) {
////		emailStartRef.child(key).setPriority(emailStart);
//	}

	public void delete(String key) {
		emailStartRef.child(key).removeValue();
	}
}
