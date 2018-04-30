package pull.repo;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import org.springframework.context.ApplicationContext;
import pull.domain.Topic;
import pull.service.WriteTopicToProps;
import pull.util.Db;

import java.util.HashMap;
import java.util.Map;

public class TopicRepo {
    DatabaseReference topicRef;
    DatabaseReference topicTypeFaqRef = Db.coRef("topicTypeFaq");
    String path;
    Map<String, Topic> topicMap = new HashMap<String, Topic>();

    private WriteTopicToProps writeTopicToProps;
    private ApplicationContext applicationContext;

    public TopicRepo(String path, ApplicationContext applicationContext) {
        super();
        topicRef = Db.coRef(path);
        this.path = path;
        this.applicationContext = applicationContext;
        init();
    }

    private void init() {
        if (writeTopicToProps == null) {
            writeTopicToProps = (WriteTopicToProps) applicationContext.getBean("writeTopicToProps");
        }
        topicRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                System.err.println("https://clouddancer.info/z" + dataSnapshot.getKey() + "c.html");
                if (!topicMap.containsKey(pathKey(dataSnapshot))) {
                    Topic topic = dataSnapshot.getValue(Topic.class);
                    topicMap.put(pathKey(dataSnapshot), topic);
                    writeTopicToProps.go(dataSnapshot.getKey(), topic);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                Topic topic = dataSnapshot.getValue(Topic.class);
                topicMap.put(pathKey(dataSnapshot), topic);
                System.err.println("CHANGING " + dataSnapshot.getKey());
                writeTopicToProps.go(dataSnapshot.getKey(), topic);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // TODO make it delete the property too
                topicMap.remove(pathKey(dataSnapshot));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
                //TODO no idea what use case is here
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw new RuntimeException(databaseError.getMessage());
            }
        });
    }

    private String pathKey(DataSnapshot dataSnapshot) {
        return path + "/" + dataSnapshot.getKey();
    }
}