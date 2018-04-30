package pull.repo;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import org.springframework.beans.factory.annotation.Autowired;
import pull.domain.AbuseLog;
import pull.service.SendEmailVerifyEmail;
import pull.util.Db;

import java.util.HashMap;
import java.util.Map;

public class AbuseLogRepo {
    DatabaseReference abuseLogRef = Db.coRef("abuseLog");
    Map<String, AbuseLog> abuseLogMap = new HashMap<String, AbuseLog>();

    public AbuseLogRepo() {
        super();
        init();
    }

    private void init() {
        abuseLogRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                AbuseLog abuseLog = dataSnapshot.getValue(AbuseLog.class);
                if (abuseLog != null) {
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
