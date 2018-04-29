package pull.repo;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import org.springframework.beans.factory.annotation.Autowired;
import pull.domain.UnsubscribeLog;
import pull.service.SendEmailVerifyEmail;
import pull.util.Db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnsubscribeLogRepo {
    DatabaseReference unsubscribeLogRef = Db.coRef("unsubscribeLog");
    static List<String> unsubscribes = new ArrayList<String>();

    public UnsubscribeLogRepo() {
        super();
        init();
    }

    private void init() {
        unsubscribeLogRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {

                UnsubscribeLog unsubscribeLog = dataSnapshot.getValue(UnsubscribeLog.class);
                if (unsubscribeLog != null) {
                    unsubscribes.add(unsubscribeLog.getEmailAddress());
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
    public static boolean contains(String emailAddress){
        if(unsubscribes.contains(emailAddress)){
            return true;
        }else{
            return false;
        }
    }
}
