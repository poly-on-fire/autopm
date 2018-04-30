package pull.repo;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pull.domain.EmailStart;
import pull.service.TopicDayService;
import pull.util.Db;


@Service
public class EmailStartRepo {
    @Value("${run.naked}")
    private boolean runNaked;

    DatabaseReference emailStartRef;
    String path = "emailStart";

    @Autowired
    private TopicDayService topicDayService;

    public EmailStartRepo() {
        super();
        emailStartRef = Db.coRef(path);
        init();
    }

    private void init() {
        // TODO this fails to be true when set, fix that, but not very high priority
        if(runNaked) {
            return;
        }
        emailStartRef.addChildEventListener(new ChildEventListener() {

            @Override
            public void onCancelled(DatabaseError dataSnapshot) {
            }

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                addEmailTopicDates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }
        });
    }

    private void addEmailTopicDates(DataSnapshot dataSnapshot) {
        EmailStart emailStart = dataSnapshot.getValue(EmailStart.class);
        if (!UnsubscribeLogRepo.contains(emailStart.getEmailAddress())) {
            String topicKey = emailStart.getTopicKey();
            topicDayService.upsertEmailStart(emailStart);
        }
    }

    public void add(EmailStart emailStart) {
        DatabaseReference newEmailTopicDateByRef = emailStartRef.push();
        newEmailTopicDateByRef.setValue(emailStart);
    }

    public void delete(String key) {
        emailStartRef.child(key).removeValue();
    }
}
