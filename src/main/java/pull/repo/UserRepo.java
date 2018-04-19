package pull.repo;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import org.springframework.context.ApplicationContext;
import pull.util.Db;

import java.util.HashMap;
import java.util.Map;

public class UserRepo {
    DatabaseReference ref;
    Map<String, TopicRepo> topicRepoMap = new HashMap<String, TopicRepo>();
    Map<String, EmailMetaRepo> emailMetaRepoMap = new HashMap<String, EmailMetaRepo>();
    Map<String, EmailLogRepo> emailLogRepoMap = new HashMap<String, EmailLogRepo>();
    Map<String, ServiceRequestRepo> serviceRequestRepoMap = new HashMap<String, ServiceRequestRepo>();
    Map<String, ProfileRepo> profileRepoMap = new HashMap<String, ProfileRepo>();
    Map<String, TopicEmailsRepo> topicEmailsRepoMap = new HashMap<String, TopicEmailsRepo>();
    Map<String, SubmitRoleRepo> submitRoleRepoMap = new HashMap<String, SubmitRoleRepo>();
    Map<String, RoleRepo> roleRepoMap = new HashMap<String, RoleRepo>();
    String userKey;
    ApplicationContext applicationContext;

    /*
     * The idea behind this class is that user listeners need to exist somewhere
     * so every time a user adds a child, this class creates a new listener for that child
     * or, it does if it is interested in listening to that child. Sometimes, it's not interested.
     */
    public UserRepo(String key, ApplicationContext applicationContext) {
        super();
        this.applicationContext = applicationContext;
        this.userKey = key;
        ref = Db.coRef("users/" + key);
        init();
    }

    private void init() {

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                dispatch(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                dispatch(dataSnapshot.getKey());
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

    private void dispatch(String key) {
        String path = "users/" + userKey + "/" + key;
        switch (key) {
            case "topic":
                if (!topicRepoMap.containsKey(path)) {
                    topicRepoMap.put(path, new TopicRepo(path, applicationContext));
                }
                break;
            case "topicName":
                // not interested
                break;
            case "emailContent":
                // not interested
                break;
            case "logins":
                // not interested
                break;
            case "serviceRequest":
                if (!serviceRequestRepoMap.containsKey(path)) {
                    serviceRequestRepoMap.put(path, new ServiceRequestRepo(path));
                }
                break;
            case "profile":
                if (!profileRepoMap.containsKey(path)) {
                    profileRepoMap.put(path, new ProfileRepo(path, applicationContext));
                }
                break;
            case "topicEmails":
                if (!topicEmailsRepoMap.containsKey(path)) {
                    topicEmailsRepoMap.put(path, new TopicEmailsRepo(path, applicationContext));
                }
                break;
            case "emailMeta":
                if (!emailMetaRepoMap.containsKey(path)) {
                    emailMetaRepoMap.put(path, new EmailMetaRepo(path, applicationContext));
                }
                break;
            case "submitRole":
                if (!submitRoleRepoMap.containsKey(path)) {
                    submitRoleRepoMap.put(path, new SubmitRoleRepo(path, applicationContext));
                }
                break;
            case "role":
                if (!roleRepoMap.containsKey(path)) {
                    roleRepoMap.put(path, new RoleRepo(path, applicationContext));
                }
                break;
            default:
                System.err.println("DIDN'T LOOK FOR " + key + " PLEASE FIX ME");
        }
    }
}
