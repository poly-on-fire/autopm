package pull.repo;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import pull.domain.TopicName;
import pull.util.Db;

// TODO why do I have this class? So I build a list of topics for that user, but to what end? Who looks at it?
public class TopicNameRepo {
	DatabaseReference topicNameRef;
	String path;
	Map<String, TopicName> topicNameMap = new HashMap<String, TopicName>();

	public TopicNameRepo(String path) {
		topicNameRef = Db.coRef(path);
		this.path = path;
		init();
	}

	private void init() {
		topicNameRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                TopicName topicName = dataSnapshot.getValue(TopicName.class);
                topicNameMap.put(path+"/"+dataSnapshot.getKey(), topicName);
//                System.err.println(topicName);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
				TopicName topicName = dataSnapshot.getValue(TopicName.class);
                topicNameMap.put(path+"/"+dataSnapshot.getKey(), topicName);
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				topicNameMap.remove(path+"/"+dataSnapshot.getKey());
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
}