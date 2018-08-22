package pull.repo;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import org.btrg.uti.StringUtils_;
import org.springframework.context.ApplicationContext;
import pull.domain.EmailStart;
import pull.domain.TopicEmails;
import pull.service.NewTopicEmailsRequest;
import pull.util.Db;
import pull.util.EmailAsPathReady;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TopicEmailsRepo {
    private DatabaseReference topicEmailsRef;
    private String path;
    private Map<String, TopicEmails> topicEmailseMap = new HashMap<String, TopicEmails>();

    private ApplicationContext applicationContext;
    private NewTopicEmailsRequest newTopicEmailsRequest;

    public TopicEmailsRepo(String path, ApplicationContext applicationContext) {
        super();
        this.applicationContext = applicationContext;
        topicEmailsRef = Db.coRef(path);
        this.path = path;
        init();
    }

    private void init() {
        if (newTopicEmailsRequest == null) {
            newTopicEmailsRequest = (NewTopicEmailsRequest) applicationContext
                    .getBean("newTopicEmailsRequest");
        }
        topicEmailsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                TopicEmails topicEmails = dataSnapshot.getValue(TopicEmails.class);
                pushEmails(topicEmails);
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

    protected void pushEmails(TopicEmails topicEmails) {
        if (topicEmails.getEmails() == null || topicEmails.getGroupName() == null
                || topicEmails.getMySpecificTopic() == null || !topicEmails.getEmails().contains("@")
                || 0 == topicEmails.getGroupName().trim().length()
                || 0 == topicEmails.getMySpecificTopic().trim().length()) {
            return;
        }
        String[] emails = topicEmails.getEmails().split(",");
        for (String email : emails) {
            Db.coRef("emailStart").child(topicEmails.getMySpecificTopic() + ":" + EmailAsPathReady.convert(email)).setValue(new EmailStart(email, topicEmails.getMySpecificTopic(),
                    StringUtils_.yyMMdd(new Date()), null));
            // August 2018 this was found already removed after fixing another bug. Should it be commented out? Or is it a part of the process which keeps account of the what to unsubscribe from?
//			Db.coRef("topicSignup").child(topicEmails.getMySpecificTopic() +":"+email).setValue(new TopicSignup(email, topicEmails.getMySpecificTopic(),
//					true, new Date().getTime(), topicEmails.getGroupName()));
            ;
        }
    }
}