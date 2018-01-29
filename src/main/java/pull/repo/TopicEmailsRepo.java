package pull.repo;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.btrg.uti.StringUtils_;
import org.springframework.context.ApplicationContext;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import pull.domain.TopicEmails;
import pull.domain.TopicSignup;
import pull.domain.EmailStart;
import pull.service.NewTopicEmailsRequest;
import pull.util.Db;

public class TopicEmailsRepo {
	private DatabaseReference topicEmailsRef;
	private String path;
	private Map<String, TopicEmails> topicEmailseMap = new HashMap<String, TopicEmails>();

	private ApplicationContext applicationContext;
	private NewTopicEmailsRequest newTopicEmailsRequest;

	public TopicEmailsRepo(String path, ApplicationContext applicationContext) {
		super();
		this.applicationContext = applicationContext;
		topicEmailsRef = Db.coRef(path);
		this.path = path;
		init();
	}

	private void init() {
		if (newTopicEmailsRequest == null) {
			newTopicEmailsRequest = (NewTopicEmailsRequest) applicationContext
					.getBean("newTopicEmailsRequest");
		}
		topicEmailsRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				TopicEmails topicEmails = dataSnapshot.getValue(TopicEmails.class);
				pushEmails(topicEmails);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
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

	protected void pushEmails(TopicEmails topicEmails) {
		if (topicEmails.getEmails() == null || topicEmails.getGroupName() == null
				|| topicEmails.getMySpecificTopic() == null || !topicEmails.getEmails().contains("@")
				|| 0 == topicEmails.getGroupName().trim().length()
				|| 0 == topicEmails.getMySpecificTopic().trim().length()) {
			return;
		}
		String[] emails = topicEmails.getEmails().split(",");
		for (String email : emails) {
			Db.coRef("emailStart").push().setValue(new EmailStart(email, topicEmails.getMySpecificTopic(),
					StringUtils_.yyMMdd(new Date()), null));
			Db.coRef("topicSignup").push().setValue(new TopicSignup(email, topicEmails.getMySpecificTopic(),
					true, new Date().getTime(), topicEmails.getGroupName()));
			;
		}
	}
}