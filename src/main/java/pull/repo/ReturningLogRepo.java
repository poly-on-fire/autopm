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

    public ReturningLogRepo() {
        super();
        init();
    }

    private void init() {
        returningLogRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                ReturningLog returningLog = dataSnapshot.getValue(ReturningLog.class);
                // TODO: figure out what the easiest approach is, and do that
                // you could just let anyone log in and then if no topic id they can't do anything?
                // for security purposes you have to send them a link nothing else is secure
//                remind the user it must be the same email address that the original verification was sent to
//                emailStart is how you look it up, index it by emailAddress
//                or when user and profile and ... created add email to a global /email cross referenced by userId
//                also how to keep this in a separate aaa component that still has no name
//                then when returning is found,
//                        either
//                send them another link by email

                if (returningLog != null) {
                } else {
                    // TODO error handling?
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
