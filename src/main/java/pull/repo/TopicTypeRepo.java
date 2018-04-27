package pull.repo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import pull.domain.TopicType;
import pull.domain.TopicTypeFaq;
import pull.util.Db;

public class TopicTypeRepo {
	DatabaseReference topicTypeRef = Db.coRef("topicType");
	DatabaseReference topicTypeFaqRef = Db.coRef("topicTypeFaq");
	Map<String, TopicType> map = new HashMap<String, TopicType>();

	public TopicTypeRepo() {
		init();
	}

	private void init() {
		topicTypeRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				TopicType topicType = dataSnapshot.getValue(TopicType.class);
				map.put(dataSnapshot.getKey(), topicType);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
				TopicType topicType = dataSnapshot.getValue(TopicType.class);
				map.put(dataSnapshot.getKey(), topicType);
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				map.remove(dataSnapshot.getKey());
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

	public void add(String name, String faq, boolean hasLeadCapturePage, boolean hasSalesPage,
			boolean hasOptionalVslPage) {
		DatabaseReference newTopicRef = topicTypeRef.push();
		newTopicRef.setValue(new TopicType(name, hasLeadCapturePage, hasSalesPage, hasOptionalVslPage));
		topicTypeFaqRef.child(name).setValue(new TopicTypeFaq(faq));
	}

	public List<TopicType> getList() {
		return (List<TopicType>) map.values();
	}

	public Map<String, TopicType> getMap() {
		return map;
	}

	public void update(String key, TopicType topicType) {
		topicTypeRef.child(key).setPriority(topicType);
	}

	public void delete(String key) {
		topicTypeRef.child(key).removeValue();
	}

}
