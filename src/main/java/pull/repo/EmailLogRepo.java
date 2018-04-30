package pull.repo;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.btrg.uti.StringUtils_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import pull.domain.TopicSignup;
import pull.domain.EmailLog;
import pull.domain.EmailStart;
import pull.service.SendEmailVerifyEmail;
import pull.util.Db;

@Service
public class EmailLogRepo {
	DatabaseReference emailLogRef = Db.coRef("emailLog");
	Map<String, EmailLog> emailLogMap = new HashMap<String, EmailLog>();
	@Autowired
	private SendEmailVerifyEmail sendEmailVerifyEmail;

	public EmailLogRepo() {
		super();
		init();
	}

	private void init() {
		emailLogRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				EmailLog emailLog = dataSnapshot.getValue(EmailLog.class);
				if (emailLog != null) {
					sendEmailVerifyEmail.go(emailLog);
					writeAndDelete(emailLog, dataSnapshot.getKey());
				} else {
					//TODO meaningful error handling
					System.err.println("YOU HAVE A NULL EMAIL, DICK");
					System.err.println("WAS" + dataSnapshot.getValue());
				}
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
				//TODO is there ever a chance that this would change once created?
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				emailLogMap.remove(dataSnapshot.getKey());
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

	public void add(String emailName, String topic, String properName, String refKey) {
		emailLogRef.push().setValue(new EmailLog(emailName, topic, properName, refKey, new Date().getTime()));
	}

	public List<EmailLog> getList() {
		return (List<EmailLog>) emailLogMap.values();
	}

	public Map<String, EmailLog> getMap() {
		return emailLogMap;
	}

	public void update(String key, EmailLog emailLog) {
		emailLogRef.child(key).setPriority(emailLog);
	}

	public void delete(String key) {
		emailLogRef.child(key).removeValue();
	}

	private void writeAndDelete(EmailLog emailLog, String key) {
		Db.coRef("emailStart").push()
				.setValue(new EmailStart(emailLog.getEmailAddress(), emailLog.getTopicKey(), yymmdd(), null));
		Db.coRef("topicSignup").push().setValue(
				new TopicSignup(emailLog.getEmailAddress(), emailLog.getTopicKey(), true, new Date().getTime()));
		delete(key);
	}

	private String yymmdd() {
		return StringUtils_.yyMMdd(new Date());
	}
}
