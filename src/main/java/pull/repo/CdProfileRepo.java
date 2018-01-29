package pull.repo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import pull.domain.CdProfile;
import pull.service.NewServiceRequest;
import pull.util.Db;

public class CdProfileRepo {
	private DatabaseReference cdProfileRef;
	private String path;
	private Map<String, CdProfile> cdProfileMap = new HashMap<String, CdProfile>();
	private ApplicationContext applicationContext;
	private NewServiceRequest newServiceRequest;

	public CdProfileRepo(String path, ApplicationContext applicationContext) {
		super();
		this.applicationContext = applicationContext;
		cdProfileRef = Db.coRef(path);
		this.path = path;
		init();
	}

	private void init() {
		if (newServiceRequest == null) {
			newServiceRequest = (NewServiceRequest) applicationContext.getBean("newServiceRequest");
		}
		cdProfileRef.addValueEventListener(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				if (null != dataSnapshot.getValue()) {
					CdProfile cdProfile = dataSnapshot.getValue(CdProfile.class);
					if (cdProfile.pending) {
						cdProfileMap.put(path + "/" + dataSnapshot.getKey(), cdProfile);
						newServiceRequest.make(cdProfile);
					}
				}
			}

			@Override
			public void onCancelled(DatabaseError arg0) {}
		});
	}
}