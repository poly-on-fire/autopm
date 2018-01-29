package pull.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import pull.domain.EmailMeta;
import pull.service.TopicDayService;
import pull.util.Db;

public class EmailMetaRepo {

	private DatabaseReference emailMetaRef;
	private String path;
	private ApplicationContext applicationContext;
	private TopicDayService topicDayService;

	public EmailMetaRepo(String path, ApplicationContext applicationContext) {
		super();
		this.applicationContext = applicationContext;
		emailMetaRef = Db.coRef(path);
		this.path = path;
		init();
	}

	private void init() {
		if (topicDayService == null) {
			topicDayService = (TopicDayService) applicationContext.getBean("topicDayService");
		}
		emailMetaRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				EmailMeta emailMeta = dataSnapshot.getValue(EmailMeta.class);
				writeTopicDay(dataSnapshot.getRef().getPath().toString(), emailMeta);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
				EmailMeta emailMeta = dataSnapshot.getValue(EmailMeta.class);
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
			}

			@Override
			public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {
				throw new RuntimeException(databaseError.getMessage());
			}
		});
	}

	protected void writeTopicDay(String emailMetaPath, EmailMeta emailMeta) {
		topicDayService.upsertEmailMeta(emailMetaPath, emailMeta);
	}

//	public void add(EmailMeta emailMeta) {
//		DatabaseReference newEmailLookupRef = emailMetaRef.push();
//		newEmailLookupRef.setValue(emailMeta);
//	}
//
//	public void update(String key, EmailMeta emailMeta) {
//		emailMetaRef.child(key).setPriority(emailMeta);
//	}

	public void delete(String key) {
		emailMetaRef.child(key).removeValue();
	}

}
