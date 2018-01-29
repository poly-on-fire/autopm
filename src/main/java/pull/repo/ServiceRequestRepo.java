package pull.repo;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import pull.domain.ServiceRequest;
import pull.util.Db;
/*
 * This can be confusing, so read up:
 * 
 * A serviceRequest is specific to a user and never worked on directly, and never deleted.
 * 
 * A serviceRequest spawns a ServiceAction, which is a global list for all service requests for all users.
 * 
 * This serviceAction is where all the work is done, and hence is the focus of the admin
 */
public class ServiceRequestRepo {
	DatabaseReference serviceRequestRef;
	String path;
	Map<String, ServiceRequest> serviceRequestMap = new HashMap<String, ServiceRequest>();

	public ServiceRequestRepo(String path) {
		serviceRequestRef = Db.coRef(path);
		this.path = path;
		init();
	}

	private void init() {
		serviceRequestRef.addChildEventListener(new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                ServiceRequest serviceRequest = dataSnapshot.getValue(ServiceRequest.class);
                serviceRequestMap.put(path+"/"+dataSnapshot.getKey(), serviceRequest);
                System.err.println(serviceRequest);
			}

			@Override
			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
				ServiceRequest serviceRequest = dataSnapshot.getValue(ServiceRequest.class);
                serviceRequestMap.put(path+"/"+dataSnapshot.getKey(), serviceRequest);
			}

			@Override
			public void onChildRemoved(DataSnapshot dataSnapshot) {
				serviceRequestMap.remove(path+"/"+dataSnapshot.getKey());
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