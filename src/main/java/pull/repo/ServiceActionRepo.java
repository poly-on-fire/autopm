package pull.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import pull.domain.ServiceAction;
import pull.service.TopicDayService;
import pull.util.Db;

@Service
public class ServiceActionRepo {

	private DatabaseReference serviceActionRef;
	private String PATH = "serviceAction";

	@Autowired
	private TopicDayService topicDayService;

	public ServiceActionRepo() {
		super();
		serviceActionRef = Db.coRef(PATH);
		init();
	}

	private void init() {
		serviceActionRef.addChildEventListener(new ChildEventListener() {

			@Override
			public void onCancelled(DatabaseError dataSnapshot) {
			}

			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				addServiceActionItem(dataSnapshot);
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

	private void addServiceActionItem(DataSnapshot dataSnapshot) {
		ServiceAction serviceAction = dataSnapshot.getValue(ServiceAction.class);
//		String topicKey = serviceAction.getTopicKey();
//		topicDayService.upsertServiceAction(serviceAction);
	}

	public void add(ServiceAction serviceAction) {
		DatabaseReference newEmailTopicDateByRef = serviceActionRef.push();
		newEmailTopicDateByRef.setValue(serviceAction);
	}


	public void delete(String key) {
		serviceActionRef.child(key).removeValue();
	}
}
