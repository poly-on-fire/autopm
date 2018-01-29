package pull.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.firebase.database.DatabaseReference;

import pull.domain.EmailOut;
import pull.util.Db;

@Service
public class EmailQueue {

	private DatabaseReference emailRef;
	private Map<String, EmailOut> emailMap = new HashMap<String, EmailOut>();
	private String topicKey;


	@SuppressWarnings("unused")
	public EmailQueue(){
		super();
	}
	
	public EmailQueue(String topicKey) {
		super();
		this.topicKey = topicKey;
		emailRef= Db.coRef("email");
	}

	public void add(EmailOut email) {
		DatabaseReference newEmailTopicDateRef = emailRef.push();
		newEmailTopicDateRef.setValue(email);
	}

	public List<EmailOut> getList() {
		return (List<EmailOut>) emailMap.values();
	}

	public Map<String, EmailOut> getMap() {
		return emailMap;
	}

	public void update(String key, EmailOut email) {
		emailRef.child(key).setPriority(email);
	}

	public void delete(String key) {
		emailRef.child(key).removeValue();
	}
}
