package pull.repo;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.springframework.context.ApplicationContext;
import pull.domain.Profile;
import pull.service.NewServiceRequest;
import pull.util.Db;

import java.util.HashMap;
import java.util.Map;

public class ProfileRepo {
	private DatabaseReference profileRef;
	private String path;
	private Map<String, Profile> profileMap = new HashMap<String, Profile>();
	private ApplicationContext applicationContext;
	private NewServiceRequest newServiceRequest;

	public ProfileRepo(String path, ApplicationContext applicationContext) {
		super();
		this.applicationContext = applicationContext;
		profileRef = Db.coRef(path);
		this.path = path;
		init();
	}

	private void init() {
		if (newServiceRequest == null) {
			newServiceRequest = (NewServiceRequest) applicationContext.getBean("newServiceRequest");
		}
		profileRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if (null != dataSnapshot.getValue()) {
					Profile profile = dataSnapshot.getValue(Profile.class);
					if (profile.promotion) {
						profileMap.put(path + "/" + dataSnapshot.getKey(), profile);
						newServiceRequest.make(profile);
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError arg0) {}
		});
	}
}