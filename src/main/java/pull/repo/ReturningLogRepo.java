package pull.repo;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import org.springframework.beans.factory.annotation.Autowired;
import pull.domain.ReturningLog;
import pull.service.SendEmailVerifyEmail;
import pull.util.Db;

import java.util.HashMap;
import java.util.Map;

public class ReturningLogRepo {
    DatabaseReference returningLogRef = Db.coRef("returningLog");
    Map<String, ReturningLog> returningLogMap = new HashMap<String, ReturningLog>();
    @Autowired
    private SendEmailVerifyEmail sendEmailVerifyEmail;

    public ReturningLogRepo() {
        super();
        init();
    }

    private void init() {
        returningLogRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                ReturningLog returningLog = dataSnapshot.getValue(ReturningLog.class);
                if (returningLog != null) {
                } else {
                    System.err.println("YOU HAVE A NULL EMAIL, DICK");
                    System.err.println("WAS" + dataSnapshot.getValue());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot var1, String var2) {
            }

            @Override
            public void onCancelled(DatabaseError var1) {
            }
        });
    }
}
