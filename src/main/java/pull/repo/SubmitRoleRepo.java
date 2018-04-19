package pull.repo;

import com.google.firebase.database.*;
import org.springframework.context.ApplicationContext;
import pull.domain.Role;
import pull.domain.SubmitRole;
import pull.util.Db;

public class SubmitRoleRepo {
    private DatabaseReference submitRoleRef;
    private String path;
    private ApplicationContext applicationContext;

    public SubmitRoleRepo(String path, ApplicationContext applicationContext) {
        super();
        this.applicationContext = applicationContext;
        submitRoleRef = Db.coRef(path);
        this.path = path;
        init();
    }


    private void init() {
        submitRoleRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                SubmitRole submitRole = dataSnapshot.getValue(SubmitRole.class);
                System.out.println(submitRole.toString());
                something(submitRole);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                SubmitRole submitRole = dataSnapshot.getValue(SubmitRole.class);
                System.out.println(submitRole.toString());
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

	private void something(SubmitRole submitRole) {
		DatabaseReference ref = Db.coRef("/users/" + submitRole.uid );
		ref.addListenerForSingleValueEvent(new ValueEventListener() {

			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
			    if(!dataSnapshot.child("role").exists()&&!submitRole.processed){
                    ref.child("role").setValue(submitRole.role);
                    submitRole.processed=true;
                    ref.child("submitRole").setValue(submitRole);
//                    TODO add some of the fields from submitRole to profile as fields
                    //TODO maybe dedupe the submitRole stuff especially if same day same values
                }
			}

			@Override
			public void onCancelled(DatabaseError arg0) {
			}
		});
	}

    private void somethingMore(Role role){
        System.out.println("ABOUT TO ADD "+ role.toString());
    }

    //cruft leftover from it's previous usage as NewUserRepo
//	private void init() {
//		submitRoleRef.addChildEventListener(new ChildEventListener() {
//
//			@Override
//			public void onCancelled(DatabaseError dataSnapshot) {
//			}
//
//			@Override
//			public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
//			}
//
//			@Override
//			public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
//				SubmitRole submitRole = dataSnapshot.getValue(SubmitRole.class);
//				System.out.println(submitRole.toString());
////				if (submitRole.done && !submitRole.reject) {
////					process(submitRole);
////				} else if (submitRole.done && submitRole.reject) {
////					reject(submitRole);
////				}
//			}
//
//			@Override
//			public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
//			}
//
//			@Override
//			public void onChildRemoved(DataSnapshot dataSnapshot) {
//			}
//		});
//	}

//	public void addUser(Profile profile) {
//		createNewUser(profile);
//	}
//
//
//	private void reject(SubmitRole submitRole) {
//		DatabaseReference ref = Db.coRef("/users/" + submitRole.uid + "/profile");
//		ref.removeValue();
//		removeNewUser(submitRole);
//		try {
//			sendEmail.go(emailFrom, "Support at pullModel.com", submitRole.email, REJECTION_TEXT,
//					"New User Rejected:");
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	private void process(SubmitRole submitRole) {
//		updateNewUser(submitRole);
//		try {
//			sendEmail.go(emailFrom, "Support at pullModel.com", submitRole.email, ACCEPTANCE_TEXT,
//					"Welcome!");
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//
//	private void createNewUser(Profile profile) {
//		SubmitRole submitRole = new SubmitRole();
//		submitRole.dist = false;
//		//TODO add these back in
////		submitRole.email = profile.emailAddress;
////		submitRole.properName = profile.properName;
////		submitRole.uid = profile.uid;
////		submitRole.photoURL = profile.photoURL;
////		completeTopicInfo(submitRole, profile.topicKey);
//	}
//
//	private void updateNewUser(SubmitRole submitRole) {
//		DatabaseReference ref = Db.coRef("/users/" + submitRole.uid + "/profile");
//		ref.addListenerForSingleValueEvent(new ValueEventListener() {
//
//			@Override
//			public void onDataChange(DataSnapshot dataSnapshot) {
//				Profile profile = dataSnapshot.getValue(Profile.class);
//				//TODO add publisher level
////				if (submitRole.dist) {
////					profile.dist = true;
////				}
//				ref.setValue(profile);
//				removeNewUser(submitRole);
//			}
//
//			@Override
//			public void onCancelled(DatabaseError arg0) {
//			}
//		});
//	}
//
//	private void removeNewUser(SubmitRole submitRole) {
//		DatabaseReference newUserRef = Db.coRef("/submitRole/" + submitRole.uid);
//		newUserRef.removeValue();
//	}
//
//	private void completeTopicInfo(SubmitRole submitRole, String topicKey) {
//		DatabaseReference ref = Db.coRef("/topicLookup/" + topicKey);
//		ref.addListenerForSingleValueEvent(new ValueEventListener() {
//
//			@Override
//			public void onDataChange(DataSnapshot dataSnapshot) {
//				if (null != dataSnapshot.getValue()) {
//					TopicLookup topicLookup = dataSnapshot.getValue(TopicLookup.class);
//					submitRole.sponsorName = topicLookup.sponsorName;
//					submitRole.sponsorNsId = topicLookup.sponsorNsId;
//					newUserRef.child(submitRole.uid).setValue(submitRole);
//				}
//			}
//
//			@Override
//			public void onCancelled(DatabaseError arg0) {
//			}
//		});
//	}
//
//	public void modify(Profile profile) {
//		//TODO
////		DatabaseReference profileRef = Db.coRef("/users/" + profile.uid + "/profile");
////		profileRef.setValue(profile);
//	}
//
//	private final String REJECTION_TEXT = "Your application to clouddancer.co was rejected,";
//	private final String ACCEPTANCE_TEXT = "Welcome to clouddancer.co! You should now be able to use this app.";

}
