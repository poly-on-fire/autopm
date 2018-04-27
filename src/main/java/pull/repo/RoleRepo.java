package pull.repo;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import org.springframework.context.ApplicationContext;
import pull.domain.Role;
import pull.util.Db;

public class RoleRepo {

	private DatabaseReference roleRef;
	private String path;
	private ApplicationContext applicationContext;

	public RoleRepo(String path, ApplicationContext applicationContext) {
		super();
		this.applicationContext = applicationContext;
		roleRef = Db.coRef(path);
		this.path = path;
		init();
	}

	private void init() {
		roleRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
				//wrong! errors out
//				Role role = dataSnapshot.getValue(Role.class);
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


	public void add(Role role) {
		DatabaseReference newRoleLookupRef = roleRef.push();
		newRoleLookupRef.setValue(role);
	}

	public void delete(String key) {
		roleRef.child(key).removeValue();
	}

}
